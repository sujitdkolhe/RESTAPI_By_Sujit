package POJOSerialization;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {
		public static void main(String[] args) {
		
	RestAssured.baseURI="https://rahulshettyacademy.com";
	String creatURI="/maps/api/place/add/json";
	
	AddPlace addp =new AddPlace();
	addp.setAccuracy(50);
	addp.setAddress("29, side layout, cohen 09");
	addp.setLanguage("French-IN");
	addp.setPhone_number("(+91) 983 893 3937");
	addp.setWebsite("https://rahulshettyacademy.com");
	addp.setName("Frontline house");
	
	List<String> list =new ArrayList<String>();
	list.add("shoe park");
	list.add("shop");
	addp.setTypes(list);
	
	Location loc =new Location();
	loc.setLat(-38.383494);
	loc.setLng(33.427362);
	addp.setLocation(loc);

	 RequestSpecification request =new RequestSpecBuilder()
			 .setBaseUri(RestAssured.baseURI).addQueryParam("key", "qaclick123")
			 .setContentType(ContentType.JSON).build();
	 	 
	ResponseSpecification responsespec =new ResponseSpecBuilder()
			.expectStatusCode(200).expectContentType(ContentType.JSON).build();
	
    RequestSpecification addRequest=given().spec(request).body(addp);

	Response response =addRequest.when().post(creatURI).
	then().spec(responsespec).extract().response();

	String responseString=response.asString();
	System.out.println(responseString);
		}
	}
