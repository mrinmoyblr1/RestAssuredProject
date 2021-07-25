package restAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class oAuthTest_BK {
	public static void main(String[] args) throws InterruptedException {

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
		String url = "https://rahulshettyacademy.com/getCourse.php?state=mkbmkbmkb&code=4%2F0AX4XfWj8Ra6lmK3GP9VwyecVblihD4aFb7fLnFbuQal0Z"
				+ "r2H04d1didmvxQaITDfNRrr0g&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt"
				+ "=none";

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
		String response = given().queryParam("access_token", accesToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);
		JsonPath js1 = new JsonPath(response);

	}
}
