package restAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import files.payLoad;
import io.restassured.path.json.JsonPath;
public class SumValidation {
	@Test
	public void sumValidation() {
		JsonPath js = new JsonPath(payLoad.CoursePrice());
		int count = js.getInt("courses.size()");
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
