package POJODeserialzation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthDeserialzation {
	@Test
	public void oAuthResponse() throws InterruptedException {
		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String accessServerURI = "https://www.googleapis.com/oauth2/v4/token";
		String actualResponseURI = "https://rahulshettyacademy.com/getCourse.php";

		String loginURL = "https://rahulshettyacademy.com/getCourse.php?state=verifyjdss&code=4%2F1wFdnthptaRWSTvbw2bFgSnxWu2kKwGAmu-5uf9mSbHI-JEIIHAj-BxYFQFTy94nM_B_bv-LmFcbjlmx1dDVgzA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode = loginURL.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);

		String accessTokenResponse = given().urlEncodingEnabled(false).queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code")
				.when().log().all().post(accessServerURI).asString();
		System.out.println(accessTokenResponse);
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");

		GetCourse gc = given().queryParam("access _token", accessToken)
				.expect().defaultParser(Parser.JSON)
				.when().get(actualResponseURI).as(GetCourse.class);
		System.out.println("Courses are :-" + gc.getLinkedIn());
		System.out.println("Instructor are :-" + gc.getInstructor());
		gc.getCourses().getApi().get(1).getCourseTitle();
		List<Api> apiCourses = gc.getCourses().getApi();

		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI  webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		ArrayList<String> al = new ArrayList<String>();
		List<WebAutomation> wa = gc.getCourses().getWebautomatiom();
		for (int j = 0; j < wa.size(); j++) {
			al.add(wa.get(j).getCourseTitle());

		}
		List<String> expectedList = Arrays.asList(courseTitles);

		Assert.assertTrue(al.equals(expectedList));
	}
}
