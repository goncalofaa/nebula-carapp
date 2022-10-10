package steps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class HttpRequestsSteps {
    private static Response response;
    private static String jsonString;

    @When("A request is made to {string} endpoint")
    public void requestTo(String endpoint){
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.get(endpoint);
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
}
