package tests;

import org.junit.jupiter.api.Test;
import pages.Data;
import pages.Schema;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PetApiTest {

    @Test
    public void createPet() {
        int randomId = Data.getRandomId();
        String randomStatus = Data.getRandomStatus();
        String category = Data.getCategoryJson(Data.getRandomId(), "Cat");
        String tag = Data.getTagJson(Data.getRandomId(), "Cute");
        String requestBody = Data.getPetRequestBody(randomId, "Luna", randomStatus, category, tag);

        baseURI = "https://petstore.swagger.io/v2";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Request Body: " + requestBody);
        System.out.println("Response: " + response.asString());

        Schema.validatePetSchema(response);
        Assertions.assertEquals(randomId, response.jsonPath().getInt("id"));
        Assertions.assertEquals("Luna", response.jsonPath().getString("name"));
    }

    @Test
    public void createPetInvalidId() {
        String requestBody = "{" +
                "\"id\":\"ali\",\"category\":{\"id\":934,\"name\":\"cat\"},\"name\":\"Badem\"," +
                "\"photoUrls\":[\"string\"],\"tags\":[{\"id\":1082,\"name\":\"cute\"}]," +
                "\"status\":\"available\"}";
        baseURI = "https://petstore.swagger.io/v2";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(500)
                .extract().response();
        System.out.println("Request Body: " + requestBody);
        System.out.println("Response: " + response.asString());
        String responseBody = response.getBody().asString();
        JsonObject jsonObject = new JsonObject();
        jsonObject.getAsJsonObject(responseBody);
        jsonObject.has("message");


    }

    @Test
    public void createPetBadRequest() {
        String requestBody = "{ \"name\": , \"status\": \"available\" }";
        baseURI = "https://petstore.swagger.io/v2";
        Response response  = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(400)
                .extract().response();
        System.out.println("Request Body: " + requestBody);
        System.out.println("Response: " + response.asString());
    }

    @Test
    public void cretePetWithoutName() {
        baseURI = "https://petstore.swagger.io/v2";

        String requestBody = Data.getPetRequestBody(Data.getRandomId(), "", Data.getRandomStatus(),
                Data.getCategoryJson(1, "cat"), Data.getTagJson(1, "cute"));

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void createPetWrongId() {

        baseURI = "https://petstore.swagger.io/v2";
        String requestBody =
                Data.getPetRequestBody(-123, "Marvel",
                        Data.getRandomStatus(),
                        Data.getCategoryJson(1, "cat"),
                        Data.getTagJson(1, "cute"));

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void createPetWithoutValues() {

        baseURI = "https://petstore.swagger.io/v2";
        String requestBody = "{}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void getPet() {

        baseURI = "https://petstore.swagger.io/v2";

        int randomId = Data.getRandomId();
        String randomName = "Nazli";
        String category = Data.getCategoryJson(1, "cat");
        String status = Data.getRandomStatus();
        String tag = Data.getTagJson(1, "cute");
        String requestBody = Data.getPetRequestBody(randomId, randomName, status, category, tag);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pet/" + randomId)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Request Body: " + requestBody);
        System.out.println("Response: " + response.asString());

        Schema.validatePetSchema(response);
        Assertions.assertEquals(randomName, response.jsonPath().getString("name"));
        Assertions.assertEquals(status, response.jsonPath().getString("status"));
    }

    @Test
    public void getPetNotFound() {

        Response response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .pathParam("petId", 9999)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();

    }

    @Test
    public void updatePet() {
        int randomId = Data.getRandomId();
        String category = Data.getCategoryJson(1, "cat");
        String tag = Data.getTagJson(1, "cute");
        String requestBody = Data.getPetRequestBody(randomId, "Pamuk", "available", category, tag);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        String updatedRequestBody = Data.getPetRequestBody(randomId, "Pamuk", "sold", category, tag);
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(updatedRequestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Response: " + response.asString());

        Schema.validatePetSchema(response);
        Assertions.assertEquals("Pamuk", response.jsonPath().getString("name"));
        Assertions.assertEquals("sold", response.jsonPath().getString("status"));
    }

    @Test
    public void updatePetInvalidId() {
        String requestBody = Data.getPetRequestBody(-1, "UpdatedLuna", "available",
                Data.getCategoryJson(1, "cat"), Data.getTagJson(1, "cute"));

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(requestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void updatePetMissingFields() {
        String requestBody = "{ \"id\": 11 }";

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(requestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(400);
    }

    @Test
    public void deletePet() {
        int randomId = Data.getRandomId();
        String category = Data.getCategoryJson(1, "cat");
        String tag = Data.getTagJson(1, "cute");
        String requestBody = Data.getPetRequestBody(randomId, "Cimen", "available", category, tag);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        Response  response = given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .delete("/pet/" + randomId)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.asString());

         response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .pathParam("petId", randomId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void deletePetNotFound() {
        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .delete("/pet/9999")
                .then()
                .statusCode(404);
    }

    @Test
    public void getPetInvalidIdFormat() {
        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .get("/pet/invalidId")
                .then()
                .statusCode(400);
    }

    @Test
    public void updatePetSuccessChangeStatus() {
        int randomId = Data.getRandomId();
        String category = Data.getCategoryJson(1, "cat");
        String tag = Data.getTagJson(1, "cute");
        String requestBody = Data.getPetRequestBody(randomId, "Luna", "available", category, tag);

        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200).extract().response();
        System.out.println(response.asString());


        String updatedRequestBody = Data.getPetRequestBody(randomId, "Luna", "pending", category, tag);
        response = given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(updatedRequestBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.asString());

        Schema.validatePetSchema(response);
        Assertions.assertEquals("pending", response.jsonPath().getString("status"));

        response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .pathParam("petId", randomId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.asString());
    }

    @Test
    public void deletePetDeleted() {
        int randomId = Data.getRandomId();
        String category = Data.getCategoryJson(1, "cat");
        String tag = Data.getTagJson(1, "cute");
        String requestBody = Data.getPetRequestBody(randomId, "Badem", "available", category, tag);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .delete("/pet/" + randomId)
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .delete("/pet/" + randomId)
                .then()
                .statusCode(404);
    }
}
