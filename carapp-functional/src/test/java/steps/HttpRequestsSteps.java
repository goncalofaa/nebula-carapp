package steps;

import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.*;

import static io.restassured.RestAssured.given;

@Transactional
public class HttpRequestsSteps {

    private static Response response;
    private static String jsonString;


    @When("A get request is made to {string} endpoint")
    public void requestTo(String endpoint){
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        response = request.get(endpoint);
    }

    @When("A post request is made to {string} endpoint with a car being {string}")
    public void requestToWithBody(String endpoint, String body){
        List<Object> bodyList = new ArrayList<>();
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("brand", body.split(", ")[0]);
        bodyMap.put("model", body.split(", ")[1]);
        bodyMap.put("year", Integer.parseInt(body.split(", ")[2]) );
        bodyMap.put("price", Integer.parseInt(body.split(", ")[3]));
        bodyMap.put("mileage", Integer.parseInt(body.split(", ")[4]));
        bodyMap.put("colour", body.split(", ")[5]);
        bodyList.add(bodyMap);
        Gson gson = new Gson();
        String bodyJson = gson.toJson(bodyList);
        RequestSpecification request = given().body(bodyJson);
        request.header("Content-Type", "application/json");
        response = request.post(endpoint);
    }

    @Then("A body of {string} is received")
    public void bodyReceived(String body){
        jsonString = response.asString();
        Assert.assertEquals(body,jsonString);
    }

    @And("A status code of {int} is received")
    public void statusCodeReceived(int code){
        Assert.assertEquals(code, response.getStatusCode());
    }

    @Then("A delete request is made to the last inserted car")
    public void deleteRequest() {
        String endpoint = "cars/admin";
        RequestSpecification request = given();
        request.header("Content-Type", "application/json");
        List<String> body = Arrays.stream(request.when().get(endpoint).then().extract().response().body().asPrettyString().split("\"id\":")).toList();
        String id = body.get(body.size() - 1).split(",")[0];
        response = request.delete(endpoint+"/"+id);
    }
}
