package OAuth2;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTest {
	@Test
	public void oAuthResponse() throws InterruptedException {
		String accessServerURI = "https://www.googleapis.com/oauth2/v4/token";
		String actualResponseURI = "https://rahulshettyacademy.com/getCourse.php";

           // From 2020 Google not allowing to automated any Gmail logins//

//	WebDriverManager.chromedriver().setup();
//	WebDriver driver = new ChromeDriver();
//	driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyjdss");
//	Thread.sleep(5000);
//	String getUrl=driver.getCurrentUrl();
//	System.out.println(getUrl);
//	driver.get(getUrl);
//	driver.findElement(By.cssSelector("input[type='email']")).sendKeys("sujitdkolhe");
//	driver.findElement(By.xpath("//span[text()='Next']")).click();
//	Thread.sleep(5000);
//	driver.findElement(By.cssSelector(("input[type='password']"))).sendKeys("Sujitkolhe1@");
//	driver.findElement(By.cssSelector(("input[type='password']"))).sendKeys(Keys.ENTER);
//	String loginUrl=driver.getCurrentUrl();
		
		String loginURL = "https://rahulshettyacademy.com/getCourse.php?state=verifyjdss&code=4%2F1wFdnthptaRWSTvbw2bFgSnxWu2kKwGAmu-5uf9mSbHI-JEIIHAj-BxYFQFTy94nM_B_bv-LmFcbjlmx1dDVgzA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode = loginURL.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);

		String accessTokenResponse = given().urlEncodingEnabled(false)
				.queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").
				when().log().all().post(accessServerURI).asString();
		System.out.println(accessTokenResponse);
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");

		String actualResponse = given().contentType("application/json")
				.queryParam("access_token", accessToken).expect()
				.defaultParser(Parser.JSON).when().get(actualResponseURI).asString();
		System.out.println("Actual response is :-" + actualResponse);

	}
}