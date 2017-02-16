Feature: Retrieve full ISO country codes

  With the ambition to ...

  @old
  Scenario: Get full name for ISO country code
    Given I have access to the system
    When I enter the country code "SE"
    Then the full name of the country should be "Sweden"
    And the alpha 3 code should be "SWE"

  @new
  Scenario Outline: Requesting a country by alpha 2 code should yield the correct full name and alpha 3 code

    Given I have access to the system

    When I enter the country code "<alpha2Code>"
    Then the full name of the country should be "<fullName>"
    And the alpha 3 code should be "<alpha3Code>"

    Examples:
      | alpha2Code | fullName | alpha3Code |
      | SE         | Sweden   | SWE        |
      | DK         | Denmark  | DNK        |
