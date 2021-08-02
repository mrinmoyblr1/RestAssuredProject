package restAssured;
import io.restassured.RestAssured;
//We need to import below two static packages manually. given() belong to this static package
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import files.ReusableMethods;
//We need to import this package manually. equalTo("APP") is belong to this static package
import files.payLoad;

public class Basics {
	public static void main(String[] args) throws IOException {
		// Validate if the Add Place API is working fine
		// Rest Assured will work on the below mentioned 3 principals
		// given: All the input details
		// when: Submit the API - Resources and HTTP Method will go as part of this
		// when()
		// then: Validate the Response
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get(
						"C:\\Users\\Mrinmoy\\eclipse-workspace-2\\RestAssuredProject\\src\\main\\java\\files\\AddPlace.json"))))
				// Getting the input Payload from external JSON file(AddPlace.json).

				.when().post("maps/api/place/add/json").then().assertThat().statusCode(200).log().all()
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response()
				.asString();

		// System.out.println(response);
		String placeID = ReusableMethods.rawToJson(response, "place_id");
		System.out.println(placeID);

		// Update place PUT HTTP Method
		String newAddress = "Summar Walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "    \"place_id\": \"" + placeID + "\",\r\n" + "    \"address\": \"" + newAddress
						+ "\",\r\n" + "    \"key\": \"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		// This is body() assertion

		// Get Place HTTP Method
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();

		String actualAddress = ReusableMethods.rawToJson(getPlaceResponse, "address");
		Assert.assertEquals(actualAddress, newAddress);
		if (actualAddress.equalsIgnoreCase(newAddress)) {
			System.out.println("Success...........");
		}
	}
}
