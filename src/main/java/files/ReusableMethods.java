package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	public static String rawToJson(String response, String text) {
		JsonPath js = new JsonPath(response); // for parsing JSON
		String returnText = js.getString(text);

		return returnText;

	}

}
