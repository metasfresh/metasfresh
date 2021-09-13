/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.common.JsonTestResponse;
import de.metas.cucumber.stepdefs.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class REST_API_StepDef
{
	private String userAuthToken;
	private APIResponse apiResponse;

	private final TestContext testContext;

	public REST_API_StepDef(final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@Given("the existing user with login {string} receives a random a API token for the existing role with name {string}")
	public void the_existing_user_has_the_authtoken(@NonNull final String userLogin, @NonNull final String roleName) throws IOException
	{
		userAuthToken = RESTUtil.getAuthToken(userLogin, roleName);
	}

	@When("a {string} request with the below payload is sent to the metasfresh REST-API {string} and fulfills with {string} status code")
	public void metasfresh_rest_api_endpoint_receives_a_request_responds_with_code_for_payload(
			final String verb,
			final String endpointPath,
			final String statusCode,
			final String payload) throws IOException
	{
		testContext.setRequestPayload(payload);

		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, payload, userAuthToken, Integer.parseInt(statusCode));
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the payload")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload(
			final String endpointPath,
			final String verb,
			final String payload) throws IOException
	{
		testContext.setRequestPayload(payload);

		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, payload, userAuthToken, null);
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the payload from context and responds with {string} status code")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload_from_context(
			final String endpointPath,
			final String verb,
			final String statusCode) throws IOException
	{
		final String payload = testContext.getRequestPayload();

		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, payload, userAuthToken, Integer.parseInt(statusCode));
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_without_payload(
			final String endpointPath,
			final String verb) throws IOException
	{
		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, null, userAuthToken, null);
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the headers from context")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_request_with_additional_headers(
			final String endpointPath,
			final String verb) throws IOException
	{
		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, null, userAuthToken, null, testContext.getHttpHeaders());
		testContext.setApiResponse(apiResponse);
	}

	@Then("the metasfresh REST-API responds with")
	public void the_metasfresh_REST_API_responds_with(@NonNull final String expectedResponse) throws JSONException
	{
		JSONAssert.assertEquals(expectedResponse, apiResponse.getContent(), JSONCompareMode.LENIENT);
	}

	@When("invoke {string} {string} with response code {string}")
	public void invoke_httpMethod_with_url(@NonNull final String verb, @NonNull final String endpointPath, @NonNull final String responseCode) throws IOException
	{
		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, null, userAuthToken, Integer.parseInt(responseCode));
		testContext.setApiResponse(apiResponse);
	}

	@And("the actual response body is")
	public void validate_response_body(@NonNull final String responseBody) throws JsonProcessingException
	{
		final ObjectMapper mapper = new ObjectMapper();

		final String responseJson = testContext.getApiResponse().getContent();
		final JsonTestResponse apiResponse = mapper.readValue(responseJson, JsonTestResponse.class);

		final JsonTestResponse mappedResponseBody = mapper.readValue(responseBody, JsonTestResponse.class);

		assertThat(apiResponse.getMessageBody()).isEqualTo(mappedResponseBody.getMessageBody());
	}
}
