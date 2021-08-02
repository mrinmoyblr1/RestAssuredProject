package restAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.dataDriven;

//We need to import below two static packages manually. given() belong to this static package
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.ReusableMethods;
//We need to import this package manually. equalTo("APP") is belong to this static package
import files.payLoad;

@Test
public class ExcelDriven {
	public void addBook() throws FileNotFoundException, IOException {

		dataDriven d = new dataDriven();
		ArrayList data = d.getData("RestAPIBook");

		// Create JSON from HashMap
		HashMap<String, Object> map = new HashMap();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));

		// We have to below for nested JSON case
//		Map<String, Object> map2=new HashMap();
//		map2.put("lat","123");
//		map2.put("lng","321");		
//		map.put("location",map2);		

		RestAssured.baseURI = "http://216.10.245.166";

		String response = given().log().all().header("Content-Type", "application/json").body(map).when()
				.post("/Library/Addbook.php").then().assertThat().statusCode(200).log().all().extract().response()
				.asString();

		String ID = ReusableMethods.rawToJson(response, "ID");
		System.out.println(ID);

		// ==========================================
		given().log().all().header("Content-Type", "application/json")
				.body("{\n" + "    \"ID\": \"" + ID + "\"\n" + "}").when().post("Library/DeleteBook.php").then()
				.assertThat().statusCode(200).log().all().extract().response();

	}
}
