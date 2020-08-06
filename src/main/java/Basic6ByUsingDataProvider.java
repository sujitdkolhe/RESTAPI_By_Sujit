import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PayloadData.LibraryPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Basic6ByUsingDataProvider {
	@Test(dataProvider="Bookdata")
	public void libraryDetails(String isbn,String aisle) {
// Base URL
		RestAssured.baseURI = "http://216.10.245.166";
		String createURI = "/Library/Addbook.php";

		JsonPath js = new JsonPath(LibraryPayload.addBook());
		String getBookDetails = String.format(LibraryPayload.addBook(), isbn, aisle);

//Create Request When it is in Soft Coded value of isbn, aisle
		String response = given().log().all().header("Content-type", "Application/json")
				.body(getBookDetails).when()
				.post(createURI).then().log().all().assertThat()
				.statusCode(200).extract().response().asString();
		JsonPath js1 = new JsonPath(response);
		String getId = js1.getString("ID");
		System.out.println("Book id is:- " + getId);
	}
	
	@DataProvider(name = "Bookdata")
	public Object[][] getData() {
		return new Object[][] { { "ggjdhd", "123" },
			                    { "ddlfda", "548" },
			                    { "fssdag", "364" } 
			                  };
	}
}

