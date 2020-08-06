package POJOSerialization;

import java.awt.List;
import java.util.ArrayList;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
public class OAuthSerealization {
	@Test
public void serialization() {
	RestAssured.baseURI="https://rahulshettyacademy.com";
	String createURI="/maps/api/place/add/json";
	AddPlace addp= new AddPlace();
	addp.setAccuracy(50);
	addp.setAddress("29, side layout, cohen 09");
	addp.setLanguage("French-IN");
	addp.setName("Frontline house");
	addp.setPhone_number("(+91) 983 893 3937");
	addp.setWebsite("https://rahulshettyacademy.com");

	ArrayList<String> list =new ArrayList<String>();
	list.add("shoe park");
	list.add("shop");
	addp.setTypes(list);
	
	Location loc= new Location();
	loc.setLat(-38.383494);
	loc.setLng(33.427362);
	addp.setLocation(loc);
	
	String response = given().queryParam("key","qaclick123")
			.body(addp).when().post(createURI).
			then().log().all().assertThat()
			.statusCode(200).extract()
			.response().asString();
	System.out.println("Actual Response:- "+response);
	
	}
}
