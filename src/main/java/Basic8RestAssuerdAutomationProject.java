import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import java.io.File;
import org.testng.Assert;
import org.testng.annotations.Test;

import PayloadData.Basic8Payload;


public class Basic8RestAssuerdAutomationProject {
	@Test
	public void generateIssues() {

// Base URI
		RestAssured.baseURI = "http://localhost:8085";
		
// Endpoint 
		String loginURI = "/rest/auth/1/session";
		String createBugURI = "/rest/api/2/issue";
		String createStoryURI = "/rest/api/2/issue";
		String commentURI = "/rest/api/2/issue/{id}/comment";
		String addAttachmentURI = "/rest/api/2/issue/{id}/attachments";
		String getCommentURI = "/rest/api/2/issue/{id}";
		
// A session filter can be used record the session id returned from 
// the server as well as automatically apply this session id in subsequent requests. 
		SessionFilter session = new SessionFilter();
		
// Login credential		
		String username = "sujitdkolhe";
		String password = "Sujitkolhe1@";
		
// Returns a formatted string using the specified format string .
		String loginCrediential = String.format(Basic8Payload.loginCrediential(), username, password);

// Post Method------>  Login to Jira Dashboard
		String loginResponse = given().log().all()
				.header("Content-Type", "application/json")
				.body(loginCrediential).filter(session)
				.when().post(loginURI)
				.then().log().all().assertThat()
				.statusCode(200).extract()
				.response().asString();
		System.out.println("Login Response is :- " + loginResponse);
		
		//Post Method - Creating Bug
		given().log().all().contentType(ContentType.JSON)
		  .body(Basic8Payload.CreateBugPayoad())
		  .filter(session)
		  .when().post(createBugURI) 
		  .then().log().all() 
		  .assertThat().statusCode(201)
		  .extract().body().asString();
		
		//Post Method - Creating Story
		String responseId=given().log().all().contentType(ContentType.JSON)
				.body(Basic8Payload.CreateStoryPayoad())
				.filter(session)
				.when().post(createStoryURI)
				.then().log().all()
				.assertThat().statusCode(201)
				.extract().body().asString();

		//Post Method - AddComment 
		String bodyMessage = "Hi How are you";
		String message = String.format(Basic8Payload.commentOnIssue(), bodyMessage);
		System.out.println("Massage is :-" + message);
		JsonPath js = new JsonPath(responseId);
		String storyId= js.getString("id");
		System.out.println("Login Id is :-  " + storyId);
		String createCommentURI = String.format(commentURI, storyId);
				
		String addCommentResponse = given().pathParam("id", storyId).log().all()
	                    .header("Content-Type", "application/json")
	                    .body(message)
	                    .filter(session)
	                    .when().post(createCommentURI)
	                    .then().log().all()
	                    .assertThat().statusCode(201)
	                    .extract().response().asString();
				System.out.println("Comment is:-"+addCommentResponse);
		
		JsonPath js1 = new JsonPath(addCommentResponse);
		int commentID = js1.getInt("id");
		System.out.println("Comment Id is :-  " + commentID);

		// Post Method -Add Attachment
		String AttachmentURI = String.format(addAttachmentURI, storyId);
		File file = new File("C:\\Users\\HP\\Desktop\\sujit resume 2020 NewE.pdf");
						given().log().all().header("X-Atlassian-Token", "no-check")
						.filter(session)
						.pathParam("id", storyId)
						.header("Content-Type", "multipart/form-data")
						.multiPart(file)
						.when().post(AttachmentURI)
						.then().log().all()
						.assertThat().statusCode(200);

		// Get method -Get issue
		String issueDetails = given().log().all()
							.filter(session)
							.pathParam("id", storyId)
							.when().get(getCommentURI)
							.then().log().all()
							.extract().response().asString();
		System.out.println("Issue Details is :- " + issueDetails);
		JsonPath js2 = new JsonPath(issueDetails);
		int CommentCount = js2.getInt("fields.comment.comments.size()");
		for (int i = 0; i < CommentCount; i++) {
			String commentsId = js2.getString("fields.comment.comments[" + i + "].id");
			System.out.println("Comments are:- " + commentsId);
			if (commentsId.equals(commentID)) {
				String massages = js2.getString("fields.comment.comments[" + i + "].body");
				System.out.println("massages are:-  " + massages);
				Assert.assertEquals(massages, bodyMessage);
			}
		}
	}
}