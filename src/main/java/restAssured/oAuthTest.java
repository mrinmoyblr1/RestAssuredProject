package restAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourses;
import pojo.WebAutomation;
public class oAuthTest {
	public static void main(String[] args) throws InterruptedException, IOException {
		String[] coursesTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mrinmoy\\Selenium_Driver\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		driver.get(
//				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=mkbmkbmkb");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("mrinmoy.blr10@gmail.com");
//		Thread.sleep(5000);
//		driver.findElement(By.cssSelector(".VfPpkd-vQzf8d")).click();
//		Thread.sleep(5000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("mrinmoy.blr10@gmail.com");
//		Thread.sleep(5000);
		// String url = driver.getCurrentUrl();
		File file = new File(
				"C:\\Users\\Mrinmoy\\eclipse-workspace-2\\RestAssuredProject\\src\\main\\java\\files\\OAuth.properties");
		FileInputStream fis = new FileInputStream(file);
		Properties p = new Properties();
		p.load(fis);
		// String url =
		// "https://rahulshettyacademy.com/getCourse.php?state=mkbmkbmkb&code=4%2F0AX4XfWiFlokLWNc8IrkvCDFcOo-wszRRWw_3icIFxo7WyAYQyMr3_TBcJoQdEnOAtv2AZg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String url = p.getProperty("url");
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println(code);
		
		
		// To get access_token
		String accessTokenResponse = given().urlEncodingEnabled(false).queryParam("code", code)
				// This .urlEncodingEnabled(false) will not allow to change the string even
				// though % will be there in the code
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accessTokenResponse);
		String accesToken = js.getString("access_token");
		
		
		// To get the response from rahulshettyacademy.com
		GetCourses gc = given().queryParam("access_token", accesToken).expect().defaultParser(Parser.JSON).when()
				// Here we have to mentioned what type of format of the response will be
				// provided. is it JSON or XML?
				// This .expect().defaultParser(Parser.JSON) will perform this task
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
		
		
		// Here we need to provide the .as(GetCourses.class). This is the POJO class.
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println("The getCourseTitle:  ");
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		List<Api> apiCourses = gc.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		ArrayList<String> list = new ArrayList<String>();
		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		for (int i = 0; i < webAutomationCourses.size(); i++) {
			list.add(webAutomationCourses.get(i).getCourseTitle());
		}
		
		
		// To convert Array to ArrayList
		List<String> expectedList=Arrays.asList(coursesTitles);
		Assert.assertEquals(expectedList, list);
		Assert.assertTrue(list.equals(expectedList));
	}
}
