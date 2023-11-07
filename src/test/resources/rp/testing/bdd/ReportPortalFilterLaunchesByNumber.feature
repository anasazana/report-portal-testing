Feature: Filter launches by number

  Background: I select "Launch number" filter parameter
    Given I select "Launch number" filter parameter

  @SmokeTest @RegressionTest
 Scenario Outline: Filter launches with number equal to
    When I set Launch number filter parameter condition to "Equals" and number to <Number>
    Then Filtered results should contain <NumberOfFilteredResults> launches where number "Equals" <Number>
    Examples:
      |Number|NumberOfFilteredResults|
      |0     |0                      |
      |1     |1                      |
      |3     |1                      |
      |5     |1                      |
      |6     |0                      |

  @SmokeTest @RegressionTest
  Scenario Outline: Filter launches with number greater than or equal to
    When I set Launch number filter parameter condition to "Greater than or equal" and number to <Number>
    Then Filtered results should contain <NumberOfFilteredResults> launches where number "Greater than or equal" <Number>
    Examples:
      |Number|NumberOfFilteredResults|
      |0     |5                      |
      |1     |5                      |
      |3     |3                      |
      |5     |1                      |
      |6     |0                      |

  @SmokeTest @RegressionTest
  Scenario Outline: Filter launches with number less than or equal to
    When I set Launch number filter parameter condition to "Less than or equal" and number to <Number>
    Then Filtered results should contain <NumberOfFilteredResults> launches where number "Less than or equal" <Number>
    Examples:
      |Number|NumberOfFilteredResults|
      |0     |0                      |
      |1     |1                      |
      |3     |3                      |
      |5     |5                      |
      |6     |5                      |