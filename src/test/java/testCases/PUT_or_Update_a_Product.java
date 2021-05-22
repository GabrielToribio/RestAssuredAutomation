package testCases;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PUT_or_Update_a_Product {
	SoftAssert softAssert = new SoftAssert();

	@Test
	public void update_a_Product() {

		/*
		 * { "name": "Automation Basics 2.0, 7th Edition", "description":
		 * "Give me your money.", "price": "299", "category_id": "6" }
		 */
		HashMap payload = new HashMap();
		payload.put("id", "1561");
		payload.put("name", "Automation Basics 2.0, 7th Ed.");
		payload.put("description", "Give me your money.");
		payload.put("price", "299");
		payload.put("category_id", "6");

		Response response = 
				given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Content-Type", "application/json; charset=UTF-8")
					.body(payload).
				when()
					.put("/update.php").
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
		System.out.println("Success Message: " + successMessage); //
		softAssert.assertEquals(successMessage, "Product was updated.", "Product updating failure");

		softAssert.assertAll();
	}

//	@Test
	public void read_a_Product() {
		Response response = 
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json; charset=UTF-8")
			.queryParam("id", "1561")
		.when()
			.get("read_one.php")
		.then()
			.extract().response();
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code: " + statusCode);
		softAssert.assertEquals(statusCode, 200); 
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body: " + responseBody);
		
		JsonPath js = new JsonPath(responseBody);
		String productID = js.getString("id");
		System.out.println("Product ID: " + productID);
		softAssert.assertEquals(productID, "1561", "Product ID does not match");
		
		String productName = js.getString("name");
		System.out.println("Product name: " + productName);
		softAssert.assertEquals(productName, "Automation Basics 2.0, 7th Edition");
		
		String productPrice = js.getString("price");
		System.out.println("Product Price: " + productPrice);
		softAssert.assertEquals(productPrice, "299");
		
		softAssert.assertAll();
	}
}
