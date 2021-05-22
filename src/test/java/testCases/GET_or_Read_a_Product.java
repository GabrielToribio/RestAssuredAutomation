package testCases;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GET_or_Read_a_Product {
	SoftAssert softAssert = new SoftAssert();
	
	@Test
	public void read_a_Product() {
		Response response = 
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.header("Content-Type", "application/json; charset=UTF-8")
			.queryParam("id", "1474")
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
//		Assert.assertEquals(productID, "1474");
		softAssert.assertEquals(productID, "1444", "Product ID does not match");
		
		String productName = js.getString("name");
		System.out.println("Product name: " + productName);
//		Assert.assertEquals(productName, "HP Laptop 3.0 ");
		softAssert.assertEquals(productName, "HP Laptop 3.0 ");
		
		String productPrice = js.getString("price");
		System.out.println("Product Price: " + productPrice);
//		Assert.assertEquals(productPrice, "899");
		softAssert.assertEquals(productPrice, "899");
		
		softAssert.assertAll();
	}
	
}
