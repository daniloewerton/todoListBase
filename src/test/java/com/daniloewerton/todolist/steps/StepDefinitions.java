import com.daniloewerton.todolist.CucumberTestConfiguration;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class StepDefinitions extends CucumberTestConfiguration {

    @When("^eu fizer uma requisição para \"([^\"]*)\"$")
    public void umaRequestParaMinhaFeature(final String endpointPath) {
        log.info("Acessando o endpoint {}", endpointPath);
    }

    @Then("tem que retornar {string}")
    public void temQueRetornar(int status) {
        Assertions.assertEquals(200, status);
    }
}
