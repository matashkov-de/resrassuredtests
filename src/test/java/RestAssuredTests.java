import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests {

    @Test
    public void registrationSuccessfulTest() {
        //request: https://reqres.in/api/register
        //check: Статус 200
        //check: "token" = "QpwL5tke4Pnpja7X4"

        String regData = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"pistol\" }";
        given()
                .body(regData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void userNotFoundTest() {
        //request: https://reqres.in/api/users/23
        //check: Статус 404
        //check: response пустой

        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    public void userNameIsNotEmptyTest() {
        //request: https://reqres.in/api/users/23
        //check: Статус 200
        //check: Имена пользователей заполнены

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.first_name", notNullValue());
    }

    @Test
    public void userIdCheckTest() {
        //request: https://reqres.in/api/users
        //check: Статус 201
        //check: ИД работника не 0
        String userData = "{ \"name\": \"morpheus\", " +
                "\"job\": \"leader\" }";

        given()
                .body(userData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void deleteUserTest() {
        //request: https://reqres.in/api/users/2
        //check: Статус 204
        //check: Данные о пользователе удалились

        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204)
                .body(emptyString());
    }
}