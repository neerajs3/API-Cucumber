@bookerAPI @createBooking
Feature: To create a new booking in restful-booker

  @createBookingDataTable
  Scenario Outline: To create new booking using cucumber Data Table
    Given user has access to endpoint "/booking"
    When user creates a booking
      | firstname   | lastname   | totalprice   | depositpaid   | checkin   | checkout   | additionalneeds   |
      | <firstname> | <lastname> | <totalprice> | <depositpaid> | <checkin> | <checkout> | <additionalneeds> |
    Then user should get the response code 200

    Examples: 
      | firstname | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds |
      | John      | Doe      |       1200 | true        | 2021-05-05 | 2021-05-15 | Breakfast       |
      | Jane      | Doe      |       2400 | false       | 2021-06-01 | 2021-07-10 | Dinner          |
