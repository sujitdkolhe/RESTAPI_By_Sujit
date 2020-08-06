import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import PayloadData.Payload;
//Basic2+Payload
public class Basic2 {
	@Test
	public static void BasicMethods() {
		// given-All input details
		// when- Submit the API,resources,http method
		// then-Validate the response
		// Goal is ---->Add place--->Update place with new address----->Get place to validate if new address is present in response
		
		//Create Method or Add place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(Payload.addPlace()).when().post("maps/api/place/add/json")
				.then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)")
				.extract().response().asString();
		System.out.println(response);
		
		 //Extract place ID 
		JsonPath js = new JsonPath(response);// To parse json
		String place_Id=js.getString("place_id");// you can get string from - status,place_id, scope,reference,id also
		System.out.println("Place_ID:-  "+place_Id);
				
		JsonPath js2 = new JsonPath(Payload.addPlace());
		String getAdress=js2.getString("address");
		String newaddress="Summer Walk , Africa";
		//Replacing new address with old address
		String getPlaceAddress=String.format(newaddress, getAdress);
		System.out.println("New Address is:-  "+getPlaceAddress);
		
        //Update place with new address
//		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
//		.body("{\r\n" + 
//				"\"place_id\":\""+place_Id+"\",\r\n" + 
//				"\"address\":\"70 Summer walk, USA\",\r\n" + 
//				"\"key\":\"qaclick123\"\r\n" + 
//				"}\r\n" + 
//				"").when().put("maps/api/place/update/json")
//		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //We can Update place with new address with this method also
String updateAdress=String.format(Payload.updateAdress(), place_Id,getPlaceAddress);
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(updateAdress).when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		
		//Get Place Address
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id", place_Id)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1= new JsonPath(getPlaceResponse);
		String actualAdress=js1.getString("address");
		System.out.println("Actual Adress:- "+actualAdress);
		Assert.assertEquals(actualAdress, newaddress);
		
	}
}
