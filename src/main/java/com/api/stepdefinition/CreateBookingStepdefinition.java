package com.api.stepdefinition;
import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;

import com.api.model.Booking;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateBookingStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(CreateBookingStepdefinition.class);

	public CreateBookingStepdefinition(TestContext context) {
		this.context = context;
	}
	
	@Given("user has access to endpoint {string}")
	public void userHasAccessToEndpoint(String endpoint) {		
		context.session.put("endpoint", endpoint);
	}

	@When("user creates a booking")
	public void userCreatesABooking(DataTable dataTable) {
		Map<String,String> bookingData = dataTable.asMaps().get(0);
		JSONObject bookingBody = new JSONObject();
		bookingBody.put("firstname", bookingData.get("firstname"));
		bookingBody.put("lastname", bookingData.get("lastname"));
		bookingBody.put("totalprice", Integer.valueOf(bookingData.get("totalprice")));
		bookingBody.put("depositpaid", Boolean.valueOf(bookingData.get("depositpaid")));
		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", (bookingData.get("checkin")));
		bookingDates.put("checkout", (bookingData.get("checkout")));
		bookingBody.put("bookingdates", bookingDates);
		bookingBody.put("additionalneeds", bookingData.get("additionalneeds"));

		context.response = context.requestSetup().body(bookingBody.toString())
				.when().post(context.session.get("endpoint").toString());

		Booking booking = ResponseHandler.deserializedResponse(context.response, Booking.class);
		assertNotNull(booking, "Booking not created");
		LOG.info("Newly created booking ID: "+booking.getBookingid());
		context.session.put("bookingID", booking.getBookingid());
	}
	
	@Then("user should get the response code {int}")
	public void userShpuldGetTheResponseCode(Integer statusCode) {
			
		Assert.assertEquals(String.valueOf(context.response.getStatusCode()), String.valueOf(statusCode));
	}

}
