import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class RestAssuredImpl {
    //https://api.restful-api.dev/objects

    /*
     * RestAssured punya 3 method utama yaitu given, when, then
     * given : untuk menyiapkan request (header, body, parameter)
     * when : untuk mengirim request (get, post, put, delete)
     * then : untuk verifikasi response (status code, body, header)
    */

    String idObject;

    @Test
    public void testGetAllObjects(){
        given()
            .baseUri("https://api.restful-api.dev/objects")
            .header("Content-Type", "application/json")
        .when()
            .get()
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void sendObjects(){
        String requestBody = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2019,\n" + //
                        "      \"price\": 1849.99,\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"1 TB\"\n" + //
                        "   }\n" + //
                        "}";
          
        given()
            .baseUri("https://api.restful-api.dev/objects")
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post()
        .then()
            .statusCode(200)
            .log().all();
    }

    public String sendObjectsReturnId(){
        String requestBody = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2019,\n" + //
                        "      \"price\": 1849.99,\n" + //
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"1 TB\"\n" + //
                        "   }\n" + //
                        "}";
          
        /*
         * jsonpath : untuk mengambil data dari response body
         * {
                "id": "ff8081819782e69e01993928a9343657",
                "name": "Apple MacBook Pro 16",
                "createdAt": "2025-09-11T14:23:10.132+00:00",
                "data": {
                    "year": 2019,
                    "price": 1849.99,
                    "CPU model": "Intel Core i9",
                    "Hard disk size": "1 TB"
                }
            }
         */

        Response response = given()
            .baseUri("https://api.restful-api.dev/objects")
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .post();
        
        idObject = response.jsonPath().getString("id");
        return idObject;
    }

    @Test()
    public void deletObjectsById(){
        if(idObject == null){
            idObject = sendObjectsReturnId();
        }

        System.out.println("baseUri : https://api.restful-api.dev/objects/"+idObject);

        given()
            .baseUri("https://api.restful-api.dev/objects/"+idObject)
            .header("Content-Type", "application/json")
        .when()
            .delete()
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void testGetObjectsById(){
        given()
            .baseUri("https://api.restful-api.dev/objects/ff8081819782e69e01993928a9343657")
            .header("Content-Type", "application/json")
        .when()
            .get()
        .then()
            .statusCode(200)
            .log().all();
    }

    @Test
    public void testUpdateObjectsById(){
        String requestBody = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16 Updated\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2020,\n" + //
                        "      \"price\": 2000.00,\n" + // 
                        "      \"CPU model\": \"Intel Core i9\",\n" + //
                        "      \"Hard disk size\": \"2 TB\"\n" + //
                        "   }\n" + //
                        "}";
        given()
            .baseUri("https://api.restful-api.dev/objects/ff8081819782e69e01993928a9343657")
            .header("Content-Type", "application/json")
            .body(requestBody)
        .when()
            .put()
        .then()
            .statusCode(200)
            .log().all(); 
    }         
}