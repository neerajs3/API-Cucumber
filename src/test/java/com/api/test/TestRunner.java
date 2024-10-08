package com.api.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.CucumberOptions.SnippetType;

@CucumberOptions(
		/*plugin = { "pretty:target/cucumber/cucumber.txt",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber/cucumber.json",
		"com.api.utils.MyTestListener" }, */
		features = {
				"src/test/resources/features" },
		glue = { "com.api.stepdefinition" }

		, monochrome = true,
		snippets = SnippetType.CAMELCASE, 
		tags = "@bookerAPI"

)
public class TestRunner extends AbstractTestNGCucumberTests {

}