@jsonTest
Feature: JsonTest

 @TC01
 Scenario Outline:Verify the json file exists and its length
 Given user stores json file at location "<FileLocation>"
 When verify json file exists
 Then verify json file has length of "<length>"
 And Verify json file has length not equal to "25"
 Examples:
 | FileLocation |length |
 |src/test/resources/JsonFiles/my_json.json | 24 |

 @TC02
 Scenario Outline:Verify that each key IsClosed has value false
 Given user stores json file at location "<FileLocation>"
 When verify isClosed value is "<isClosed>" for all resource blocks
 Examples:
 | FileLocation | isClosed |
 |src/test/resources/JsonFiles/my_json.json | false |

 @TC03
 Scenario Outline:Verify that Mar2020 resource block has value 2020-03-31 in a key EndDate
 Given user stores json file at location "<FileLocation>"
 When verify "Mar2020" resource block has End Date as "<EndDate>"
 Examples:
 | FileLocation | EndDate |
 |src/test/resources/JsonFiles/my_json.json |2020-03-31 |

 @TC04
 Scenario Outline:Verify user changes value for IsClosed key from false to true for Mar2020 resource block
 Given user stores json file at location "<FileLocation>"
 When Verify user changes value for "Mar2020" IsClosed key from "<currentflag>" to "<newFlag>"
 Then verify changed value is reflected as "<newFlag>"
 Examples:
 | FileLocation | currentflag | newFlag |
 |src/test/resources/JsonFiles/my_json.json | false | true |
