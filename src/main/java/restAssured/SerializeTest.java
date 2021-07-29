package restAssured;
import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.List;
public class SerializeTest {
	public static void main(String[] args) {
		AddPlace p = new AddPlace();
		Location l = new Location();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setPhone_number("+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setName("Frontline house");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("Key", "qaclick123").body(p).when()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println(response);
	}
}
