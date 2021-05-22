package testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PUT_or_Update_a_Product2 {
	SoftAssert softAssert = new SoftAssert();

	@Test
	public void delete_a_Product() {

		HashMap payload = new HashMap();
		payload.remove("id", "1560");

		Response response = 
				given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type", "application/json; charset=UTF-8")
					.body(payload).
				when()
					.delete("/delete.php").
				then()
					.extract()
					.response();

		//Assertions
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		softAssert.assertEquals(statusCode, 200);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);
		
		JsonPath js = new JsonPath(responseBody);
		String successMessage = js.getString("message");
		System.out.println("Success Message: " + successMessage);
		softAssert.assertEquals(successMessage, "Product was deleted.", "Product updating failure");

		softAssert.assertAll();
	}
}
