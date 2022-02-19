package stepDefinition;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.json.Json;

import java.io.*;

public class stepDef {

    File file;
    FileWriter tempfile;
    Object obj;
    JSONObject jsonObject;
    JSONParser jsonParser;
    JSONArray resourceArray;

    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("^user stores json file at location \"([^\"]*)\"$")
    public void user_stores_json_file_at_location(String filePath) throws Throwable {
        try {
            file = new File(filePath);
            tempfile = new FileWriter("src/test/resources/JsonFiles/my_json1.json",false);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @When("^verify json file exists$")
    public void verify_json_file_exists() throws Throwable {
        boolean flag = file.exists();
        if(flag)
        {
            scenario.write("JSON file exists");
            Assert.assertTrue( flag);
        }
        else {
            Assert.fail();
        }
    }

    @Then("^verify json file has length of \"([^\"]*)\"$")
    public void verify_json_file_has_length_of(String arg1) throws Throwable {
        initializeJsonObjectAndArray();
        int size = resourceArray.size();
        Assert.assertEquals("JSON file length is not matching: ", 24, size);
        if (size == 24) {
            scenario.write("Size of json is 24");
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }

    @Then("^Verify json file has length not equal to \"([^\"]*)\"$")
    public void verify_json_file_has_length_not_equal_to(int length) throws Throwable {
        initializeJsonObjectAndArray();

        int expectedsize = length;
        int size = resourceArray.size();
        if (size != expectedsize) {
            scenario.write("Size of json is not equal to 25");
            Assert.assertTrue(true);
        } else {
            scenario.write("Size of json is 25");
            Assert.fail();
        }
    }

    @When("^verify isClosed value is \"([^\"]*)\" for all resource blocks$")
    public void verifyIsClosedValueIsForAllResourceBlocks(boolean expectedflag) throws Throwable {
        initializeJsonObjectAndArray();

        for (int i = 0; i < resourceArray.size(); i++) {
            JSONObject resourceObject = ((JSONObject) resourceArray.get(i));
            JSONObject resourceObject1 = (JSONObject) resourceObject.get("resource");
            Object js = resourceObject1.get("IsClosed");

            Boolean isClosedFlag = new Boolean(false);
            isClosedFlag = (Boolean) resourceObject1.get("IsClosed");
            if (isClosedFlag.equals(expectedflag)) {
                scenario.write("Actual value for isClosed key for resource block " + i + " is '" + isClosedFlag + "' matching with expected value: " + expectedflag);
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        }
    }

    @When("^verify \"([^\"]*)\" resource block has End Date as \"([^\"]*)\"$")
    public void verifyResourceBlockHasEndDateAs(String expectedDesc, String expectedEndDate) throws Throwable {

        initializeJsonObjectAndArray();
        for (int i = 0; i < resourceArray.size(); i++) {
            JSONObject resourceObject = ((JSONObject) resourceArray.get(i));
            JSONObject resourceObject1 = (JSONObject) resourceObject.get("resource");

            Object js = resourceObject1.get("IsClosed");
            String desc = (String) resourceObject1.get("Description");
            String strEndDate;
            if (desc.equalsIgnoreCase(expectedDesc)) {
                strEndDate = (String) resourceObject1.get("EndDate");
                if (strEndDate.equalsIgnoreCase(expectedEndDate)) {
                    scenario.write("For block " + expectedDesc + " End Date is correct: " + strEndDate);
                } else {
                    scenario.write("For block " + expectedDesc + " End Date is not correct");
                    Assert.fail();
                }
                break;
            }
        }
    }


    @When("^Verify user changes value for \"([^\"]*)\" IsClosed key from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void verifyUserChangesValueForIsClosedKeyFromTo(String expectedDesc, String currentflag, String newflag) throws Throwable {
        initializeJsonObjectAndArray();
        try {
            for (int i = 0; i < resourceArray.size(); i++) {
                JSONObject resourceObject = ((JSONObject) resourceArray.get(i));
                JSONObject resourceObject1 = (JSONObject) resourceObject.get("resource");

                String desc = (String) resourceObject1.get("Description");

                if (desc.equalsIgnoreCase(expectedDesc)) {
                    resourceObject1.put("IsClosed", true);
                }
            }
            jsonObject.writeJSONString(tempfile);
            tempfile.flush();
            tempfile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("^verify changed value is reflected as \"([^\"]*)\"$")
    public void verifyChangedValueIsReflectedAs(boolean expectedFlag) throws Throwable {

        try {
            for (int i = 0; i < resourceArray.size(); i++) {
                JSONObject resourceObject = ((JSONObject) resourceArray.get(i));
                JSONObject resourceObject1 = (JSONObject) resourceObject.get("resource");

                String desc = (String) resourceObject1.get("Description");
                if(desc.equalsIgnoreCase("Mar2020"))
                {
                    Boolean isClosedFlag = new Boolean(false);
                    isClosedFlag = (Boolean) resourceObject1.get("IsClosed");
                    if(isClosedFlag.equals(true))
                    {
                        scenario.write("Updated value for isClosed for description "+desc+ " is reflected correctly "+isClosedFlag);
                        Assert.assertTrue(true);
                    }
                    else {
                        scenario.write("Value for isClosed for description "+desc+ " is updated to "+isClosedFlag);
                        Assert.fail();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void initializeJsonObjectAndArray() throws IOException, ParseException {
        try {
            jsonParser = new JSONParser();
            obj = jsonParser.parse(new FileReader(file));
            jsonObject = (JSONObject) obj;
            resourceArray = (JSONArray) jsonObject.get("resource");
        }
        catch(Exception e)
        {
            scenario.write("Exception: "+e.getMessage());
        }
    }
}