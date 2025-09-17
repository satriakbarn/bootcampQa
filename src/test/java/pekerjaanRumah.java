import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;
import static io.restassured.RestAssured.given;


public class pekerjaanRumah {
    private String token;
    private int id;
    Response response;

    @BeforeMethod
    public void url(){
                RestAssured.baseURI="https://restful-booker.herokuapp.com";
    }

    @AfterMethod
    public void statusCode(){


        if(response!= null){
            response=null;
        }
    }

    @Test(priority = 1)
    public void createToken(){

        String bodyReqToken = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";

        Response response =given()
                .basePath("/auth")
                .header("Content-Type","application/json")
                .body(bodyReqToken)
        .when()
                .post();

        response.prettyPrint();
        token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        Assert.assertNotNull(token);
        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println("Status Code : "+response.getStatusCode());
    }


   @Test (priority = 2)
    public void createBooking(){
       String bodyReqCreate = "{\n" +
               "    \"firstname\" : \"Jin\",\n" +
               "    \"lastname\" : \"Brown\",\n" +
               "    \"totalprice\" : 111,\n" +
               "    \"depositpaid\" : true,\n" +
               "    \"bookingdates\" : {\n" +
               "        \"checkin\" : \"2018-01-01\",\n" +
               "        \"checkout\" : \"2019-01-01\"\n" +
               "    },\n" +
               "    \"additionalneeds\" : \"Breakfast\"\n" +
               "}";

        response = given()
               .basePath("/booking")
               .header("Content-Type" ,"application/json")
               .body(bodyReqCreate)
        .when()
               .post();

       response.prettyPrint();
       id=response.jsonPath().getInt("bookingid");
       System.out.println("Status Code : "+response.getStatusCode());
       Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test(priority = 3)
    public void updateBooking(){

        String bodyPut="{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        response = given()
                .basePath("/booking/{id}")
                .pathParams("id",id)
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie", "token=" + token)
                .body(bodyPut)
        .when()
                .put();
        response.prettyPrint();
        System.out.println("Status Code : "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test (priority = 4)
    public void partialUpdateBooking(){

        String bodyPatch="{\n" +
                "    \"firstname\" : \"James\",\n" +
                "    \"lastname\" : \"Brown\"\n" +
                "}";
        response = given()
                .basePath("/booking/{id}")
                .pathParams("id",id)
                .header("Content-Type", "application/json")
                .header("Accept","application/json")
                .header("Cookie", "token=" + token)
                .body(bodyPatch)
         .when()
                .patch();
        System.out.println(token);
        response.prettyPrint();
        System.out.println("Status Code : "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 5)
    public void getBooking(){
        response = given()
                .basePath("/booking/{id}")
                .pathParams("id", id)
                .when()
                .get();
        response.prettyPrint();
        System.out.println("Status Code : "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test(priority = 6)
    public void deleteBooking(){
        response = given()
                .basePath("/booking/{id}")
                .pathParams("id",id)
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
        .when()
                .delete();
        System.out.println(token);
        response.prettyPrint();
        System.out.println("Status Code : "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),201);
    }

    @Test(priority = 7)
    public void getBookingIds(){

        response=given().
                basePath("/booking")
                .when()
                .get();
        List<String> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(bookingIds.size() > 1, "Jumlah kurang dari 1");
        System.out.println("Status Code : "+response.getStatusCode());
        response.prettyPrint();

    }


    @Test(priority = 8)
    public void ping(){
        response = given()
                .basePath("/ping")
        .when()
                .get();
        response.prettyPrint();
        System.out.println("Status Code : "+response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(),201);
    }

}