package runners;

//import io.cucumber.api.CucumberOptions;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
//src/test/stepDefinition/stepDef
@CucumberOptions(
        features = "src/test/resources/features/JsonTest.feature", glue = {"stepDefinition"},
        plugin = {"pretty", "html:target/cucumber-reports"},
        tags = {"@jsonTest"}, monochrome = true
)

public class TestRunner {

}