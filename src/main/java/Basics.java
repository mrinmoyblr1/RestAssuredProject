import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
//We need to import this one manually. given() belong to this static package

public class Basics {

	public static void main(String[] args) {
		// Validate is the Add Place API is working fine
		// Rest Assured will work on the below mentioned 3 principals
		//given: All the input details 
		//when: Submit the API - Resources and HTTP Method will go as part of this when()
		//then: Validate the Response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"    \"location\": {\r\n" + 
				"        \"lat\": -38.383494,\r\n" + 
				"        \"lng\": 33.427362\r\n" + 
				"    },\r\n" + 
				"    \"accuracy\": 50,\r\n" + 
				"    \"name\": \"Rahul Shetty Academy\",\r\n" + 
				"    \"phone_number\": \"(+91) 983 893 3937\",\r\n" + 
				"    \"address\": \"29, side layout, cohen 09\",\r\n" + 
				"    \"types\": [\r\n" + 
				"        \"shoe park\",\r\n" + 
				"        \"shop\"\r\n" + 
				"    ],\r\n" + 
				"    \"website\": \"https://rahulshettyacademy.com\",\r\n" + 
				"    \"language\": \"French-IN\"\r\n" + 
				"}").when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
		
		
		
		

	}

}
