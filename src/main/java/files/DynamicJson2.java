package files;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson2 {

	@Test
	public void addBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payLoad.AddBook())
				.when()
				.post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		
		//String id=ReusableMethods.rawToJson(response,"ID");		
		//JsonPath js=new JsonPath(response);
		JsonPath js=ReusableMethods.rawToJson2(response);	
		String id=js.get("ID");
		System.out.println(id);
		
		
		

	}

}
