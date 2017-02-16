@new
Feature: Retrieve full ISO country codes

  With the ambition to ...

  Scenario: Get full name for ISO country code
    Given I have access to the system
    When I enter the country code "SE"
    Then the full name of the country should be "Sweden"
    And the alpha 3 code should be "SWE"
