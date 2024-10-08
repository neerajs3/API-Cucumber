package com.api.stepdefinition;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.api.model.Booking;
import com.api.utils.ResponseHandler;
import com.api.utils.TestContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

public class CreateBookingStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(CreateBookingStepdefinition.class);

	public CreateBookingStepdefinition(TestContext context) {
		this.context = context;
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
		validateBookingData(new JSONObject(bookingData), booking);
	}

	private void validateBookingData(JSONObject bookingData, Booking booking) {
		LOG.info(bookingData);
	
		assertNotNull(booking.getBookingid(),"Booking ID missing");
		assertEquals( bookingData.get("firstname"), booking.getBooking().getFirstname(),"First Name did not match");
		assertEquals(bookingData.get("lastname"), booking.getBooking().getLastname(),"Last Name did not match");
		assertEquals( bookingData.get("totalprice"), booking.getBooking().getTotalprice(),"Total Price did not match");
		assertEquals( bookingData.get("depositpaid"), booking.getBooking().getDepositpaid(),"Deposit Paid did not match");
		assertEquals( bookingData.get("additionalneeds"), booking.getBooking().getAdditionalneeds(),"Additional Needs did not match");
		assertEquals( bookingData.get("checkin"), booking.getBooking().getBookingdates().getCheckin(),"Check in Date did not match");
		assertEquals( bookingData.get("checkout"), booking.getBooking().getBookingdates().getCheckout(),"Check out Date did not match");
	}



}
