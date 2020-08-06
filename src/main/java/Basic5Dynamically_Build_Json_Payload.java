import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import PayloadData.LibraryPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
//LibraryPayload++Basic5Dynamically_Build_Json_Payload
public class Basic5Dynamically_Build_Json_Payload {
	@Test
	public void libraryDetails() {
// Base URL
		RestAssured.baseURI = "http://216.10.245.166";
		String createURI="/Library/Addbook.php";
		String getBookId_URI="Library/GetBook.php?ID=%s";
		String getAuthorName="Library/GetBook.php?AuthorName=%s";
		String deleteURI="/Library/DeleteBook.php";
		
		String isbn="Sujit143";
		String aisle="149";
		String actualID=isbn+aisle;
		JsonPath js=new JsonPath(LibraryPayload.addBook());
		String getBookDetails=String.format(LibraryPayload.addBook(), isbn,aisle);
		
//Create Request When it is in Hard Coded value of isbn, aisle
		/*String response=given().log().all().header("Content-type","Application/json")
		.body(LibraryPayload.addBook())
		.when().post(createURI)
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js1=new JsonPath(response);
		String getId=js1.getString("ID");
		System.out.println("Book id is:- "+getId);*/
				
//Create Request When it is in Soft Coded value of isbn, aisle
		String response=given().log().all().header("Content-type","Application/json")
					.body(getBookDetails)
					.when().post(createURI)
					.then().log().all().assertThat().statusCode(200).body("Msg",equalTo("successfully added")).extract().response().asString();
		JsonPath js1=new JsonPath(response);
		String getId=js1.getString("ID");
		System.out.println("Book id is:- "+getId);
		Assert.assertEquals(actualID, getId);
				
		// Get ID and verify this id type of book available (by book_name)
		String bookId=String.format(getBookId_URI, getId);
		String responseBookName=given().log().all().header("Content-type", "Application/json")
				.when().get(bookId)
				.then().log().all().assertThat().statusCode(200).extract().asString();
		JsonPath js2=new JsonPath(responseBookName);
		String getBookName=js2.getString("book_name");
		System.out.println("Book name is:-"+getBookName);
		
		// Get Author name (author name gives 'null' value)
		String getAuthor=js1.getString("author");
		String authorName=String.format(getAuthorName, getAuthor);
		System.out.println("Author Name is :-"+authorName);
		String AuthorResponse=given().log().all().when().get(authorName)
		.then().assertThat().extract().response().asString();
		
		JsonPath js3=new JsonPath(AuthorResponse);
		String author=js3.getString("author");
		System.out.println("Author Name is:-"+getAuthorName);
		//delete response
		String deleteResponse=String.format(LibraryPayload.deleteResponse(), actualID);
		String del_Response=given().log().all().header("Content-type", "Application/json")
				.body(deleteResponse)
		.when().delete(deleteURI)
		.then().log().all().assertThat().statusCode(200)
		.body("msg",equalTo("book is successfully deleted")).extract().asString();
System.out.println("Delete Response is :" + del_Response);
		
	}
}
