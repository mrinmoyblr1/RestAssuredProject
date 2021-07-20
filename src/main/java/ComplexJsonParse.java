import org.testng.Assert;
import files.payLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		JsonPath js = new JsonPath(payLoad.CoursePrice());

		// Print No of courses returned by API
		// getInt() will return the count of any object
		// We only can apply this getInt() on the Array.
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// Print Title of the first course
		String firstTitle = js.getString("courses[0].title");
		System.out.println(firstTitle);

		// Print All course titles and their respective Prices
		String courseName = "";
		int coursePrice = 0;

		for (int i = 0; i < count; i++) {
			courseName = js.getString("courses[" + i + "].title");
			coursePrice = js.getInt("courses[" + i + "].price");
			System.out.println(courseName + " : " + coursePrice);
		}

		// Print no of copies sold by RPA Course

		for (int i = 0; i < count; i++) {
			courseName = js.getString("courses[" + i + "].title");
			if (courseName.contentEquals("RPA")) {
				System.out.print("RPA Course sold: ");
				System.out.println(js.getInt("courses[" + i + "].copies"));
				break;
			}

		}
		// Verify if Sum of all Course prices matches with Purchase Amount
		int totalPrice = 0;
		int price = 0;
		int copies = 0;

		for (int i = 0; i < count; i++) {
			price = js.getInt("courses[" + i + "].price");
			copies = js.getInt("courses[" + i + "].copies");
			totalPrice = totalPrice + (price * copies);
		}

		System.out.println(totalPrice);
		Assert.assertEquals(totalPrice, js.getInt("dashboard.purchaseAmount"));

	}
}
