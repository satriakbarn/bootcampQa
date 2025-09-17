import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class RestAssuredImplAdv {
    /*
     * 1. Test tanpa authentication
     * 2. Test dengan authentication
     * 3. Test POST, GET, DELETE, PUT
     * 4. Test dengan validasi response body
     */

    /*
     * User Account
     * {
            "userName": "afteroffice2@gmail.com",
            "password": "AfterOffice@123"
        }

        {
        "userID": "0da7426c-da11-4228-9693-28831e0834b1",
        "username": "afteroffice2@gmail.com",
        "books": []
        } 
     */

    String token;

    @BeforeClass
    public void generate_token(){
        System.out.println("Generate Token");
        String requestBody = "{\n" + //
                        "  \"userName\": \"afteroffice2@gmail.com\",\n" + //
                        "  \"password\": \"AfterOffice@123\"\n" + //
                        "}";
        Response response = 
                given()
                    .baseUri("https://demoqa.com/Account/v1/GenerateToken")
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .body(requestBody)
                .when()
                    .post();
        token = response.jsonPath().getString("token");
    }


    @Test
    public void verify_get_books(){
        //https://demoqa.com/BookStore/v1/Books
        given()
            .baseUri("https://demoqa.com/BookStore/v1/Books")
            .header("accept", "application/json")
        .when()
            .get()
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test()
    public void add_books_to_collection(){
        System.out.println("Add Books to Collection");
        String requestBody = "{\n" + //
                        "  \"userId\": \"0da7426c-da11-4228-9693-28831e0834b1\",\n" + //
                        "  \"collectionOfIsbns\": [\n" + //
                        "    {\n" + //
                        "      \"isbn\": \"9781491904244\"\n" + //
                        "    }\n" + //
                        "  ]\n" + //
                        "}";
        Response response = given()
            .baseUri("https://demoqa.com/BookStore/v1/Books")
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
        .when()
            .post();

        Assert.assertEquals(response.statusCode(), 201, "Status code should be 200");
        Assert.assertTrue(response.jsonPath().getString("books[0].isbn").equals("9781491904244"), "ISBN should be 9781491904244");
    }

    @Test(dependsOnMethods = {"add_books_to_collection"})
    public void delete_books_from_collection(){
        System.out.println("Delete Books from Collection");
        String requestBody = "{\n" + //
                        "  \"isbn\": \"9781491904244\",\n" + //
                        "  \"userId\": \"0da7426c-da11-4228-9693-28831e0834b1\"\n" + //
                        "}";
        Response response = given()
            .baseUri("https://demoqa.com/BookStore/v1/Book")
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
        .when()
            .delete();
        Assert.assertEquals(response.statusCode(), 204, "Status code should be 204");
    }
}
