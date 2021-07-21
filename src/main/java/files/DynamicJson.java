package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DynamicJson {
	// Adding New Book
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payLoad.AddBook(isbn, aisle)).when().post("Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();
		// String id=ReusableMethods.rawToJson(response,"ID");
		// JsonPath js=new JsonPath(response);
		JsonPath js = ReusableMethods.rawToJson2(response);
		String id = js.get("ID");
		System.out.println(id);

	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		// new Object[][];
		return new Object[][] { { "NEW008", "9997" }, { "NEW009", "9997" }, { "NEW010", "9997" } };

	}

}
