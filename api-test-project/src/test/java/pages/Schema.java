package pages;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Schema {

    public static void validatePetSchema(Response response) {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("pet-schema.json"));
    }
}
