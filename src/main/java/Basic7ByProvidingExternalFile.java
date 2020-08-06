import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import PayloadData.LibraryPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
// Basic7ByProvidingExternalFile++Addbook.jsonfile++StaticJson
public class Basic7ByProvidingExternalFile {
	@Test
	public void libraryDetails() throws IOException {
		// Base URL
				RestAssured.baseURI = "http://216.10.245.166";
				String createURI = "/Library/Addbook.php";
				JsonPath js = new JsonPath(LibraryPayload.addBook());
		
		//Create Request When it is in Soft Coded value of isbn, aisle
				String response = given().log().all().header("Content-type", "Application/json")
						.body(getSourceFile("D:\\Java\\RestAssuredBySujit\\AddbookDetails.json"))
						.when().post(createURI).then().log().all()
						.assertThat().statusCode(200).extract().response().asString();
				JsonPath js1 = new JsonPath(response);
				String getId = js1.getString("ID");
				System.out.println("Book id is:- " + getId);
			}
	
	public static String getSourceFile(String filePath) throws IOException{
		return new String (Files.readAllBytes(Paths.get(filePath)));
	}
}
