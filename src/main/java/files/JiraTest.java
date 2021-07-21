package files;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8081";
		SessionFilter session = new SessionFilter();
		// This SessionFilter class will help to deal with Sessions. Simply we can pass
		// the Session to this class
		// and we can user the object of this class whenever we want to make use of
		// Sessions.

		// Login to Jira API
		String response = given().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\": \"mrinmoyblr1\",\r\n" + "    \"password\": \"Anjali@12\"\r\n" + "}")
				.log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response()
				.asString();

		System.out.println("==================================================");

		// Adding comments to a Bug in Jira

		String expectedMessage = "Hi, how are you?";
		String addCommentResponse = given().pathParam("Key", "10008").log().all()
				.header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"" + expectedMessage + "\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{Key}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		// Here {Key} will be replaced by the path parameter from the
		// .pathParam("Key","10008") methods

		JsonPath js = new JsonPath(addCommentResponse);
		String commentID = js.getString("id");

		System.out.println("==================================================");

		// Add Attachment
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("Key", "10008")
				.header("Content-Type", "multipart/form-data")
				// Whenever we will use .multiPart() for attachment the we have to provide the
				// Header as .header("Content-Type","multipart/form-data")
				.multiPart("file", new File(
						"C:\\Users\\Mrinmoy\\eclipse-workspace-2\\RestAssuredProject\\src\\main\\java\\files\\Jira.txt"))
				.when().post("/rest/api/2/issue/{Key}/attachments").then().log().all().assertThat().statusCode(200);

		// Get issue details
		String issueDetails = given().filter(session).pathParam("Key", "10008").queryParam("fields", "comment").log()
				.all().when().get("/rest/api/2/issue/{Key}").then().log().all().extract().response().asString();

		// System.out.println(issueDetails);

		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount = js1.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentsCount; i++) {
			String commentIDIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIDIssue.equalsIgnoreCase(commentID)) {
				String message = js1.get("fields.comment.comments[" + i + "].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, expectedMessage);
				break;
			}
		}

		// String id = js.getString("fields.comment.comments.id[0]");
		// System.out.println(js.getString("fields.comment.comments.size()"));

		// String body = js.getString("fields.comment.comments.body[0]");
		// System.out.println(id);
		// System.out.println(body);

	}

}
