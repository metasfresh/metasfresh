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
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	public void the_existing_user_has_the_authtoken(@NonNull final String userLogin, @NonNull final String roleName)
	{
		userAuthToken = RESTUtil.getAuthToken(userLogin, roleName);
	}

	@When("a {string} request with the below payload is sent to the metasfresh REST-API {string} and fulfills with {string} status code")
	public void metasfresh_rest_api_endpoint_receives_a_request_responds_with_code_for_payload(
			@NonNull final String verb,
			@NonNull final String endpointPath,
			@NonNull final String statusCode,
			@NonNull final String payload) throws IOException
	{
		testContext.setRequestPayload(payload);

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.statusCode(Integer.parseInt(statusCode))
				.authToken(userAuthToken)
				.payload(payload)
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("a {string} request is sent to metasfresh REST-API with endpointPath from context and fulfills with {string} status code")
	public void metasfresh_rest_api_endpoint_receives_a_request_from_context_responds_with_code_for_payload(
			@NonNull final String verb,
			@NonNull final String statusCode) throws IOException
	{
		final String endpointPath = testContext.getEndpointPath();

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.statusCode(Integer.parseInt(statusCode))
				.additionalHeaders(testContext.getHttpHeaders())
				.authToken(userAuthToken)
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the payload")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload(
			@NonNull final String endpointPath,
			@NonNull final String verb,
			@NonNull final String payload) throws IOException
	{
		testContext.setRequestPayload(payload);

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.authToken(userAuthToken)
				.payload(payload)
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the payload from context and responds with {string} status code")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload_from_context(
			@NonNull final String endpointPath,
			@NonNull final String verb,
			@NonNull final String statusCode) throws IOException
	{
		final String payload = testContext.getRequestPayload();

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.authToken(userAuthToken)
				.payload(payload)
				.statusCode(Integer.parseInt(statusCode))
				.additionalHeaders(testContext.getHttpHeaders())
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_without_payload(
			@NonNull final String endpointPath,
			@NonNull final String verb) throws IOException
	{
		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.authToken(userAuthToken)
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the headers from context, expecting status={string}")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_request_with_additional_headers(
			@NonNull final String endpointPath,
			@NonNull final String verb,
			@NonNull final String status) throws IOException
	{
		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.authToken(userAuthToken)
				.additionalHeaders(testContext.getHttpHeaders())
				.statusCode(Integer.parseInt(status))
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("a {string} request is sent to metasfresh REST-API with endpointPath and payload from context and fulfills with {string} status code")
	public void metasfresh_rest_api_endpoint_receives_endpointPath_and_request_from_context_responds_with_code_for_payload(
			@NonNull final String verb,
			@NonNull final String statusCode) throws IOException
	{
		final String endpointPath = testContext.getEndpointPath();
		final String payload = testContext.getRequestPayload();

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.statusCode(Integer.parseInt(statusCode))
				.additionalHeaders(testContext.getHttpHeaders())
				.payload(payload)
				.authToken(userAuthToken)
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@When("a {string} request with the below payload and headers from context is sent to the metasfresh REST-API {string} and fulfills with {string} status code")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_request_with_additional_headers_and_below_payload(
			@NonNull final String verb,
			@NonNull final String endpointPath,
			@NonNull final String statusCode,
			@NonNull final String payload) throws IOException
	{
		testContext.setRequestPayload(payload);

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.statusCode(Integer.parseInt(statusCode))
				.authToken(userAuthToken)
				.additionalHeaders(testContext.getHttpHeaders())
				.payload(payload)
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
		testContext.setApiResponse(apiResponse);
	}

	@Then("the metasfresh REST-API responds with")
	public void the_metasfresh_REST_API_responds_with(@NonNull final String expectedResponse) throws JSONException
	{
		JSONAssert.assertEquals(expectedResponse, apiResponse.getContent(), JSONCompareMode.LENIENT);
	}

	@When("invoke {string} {string} with response code {string}")
	public void invoke_httpMethod_with_url(
			@NonNull final String verb,
			@NonNull final String endpointPath,
			@NonNull final String responseCode) throws IOException
	{
		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.authToken(userAuthToken)
				.statusCode(Integer.parseInt(responseCode))
				.build();

		apiResponse = RESTUtil.performHTTPRequest(request);
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

	@And("the actual response body is empty")
	public void validate_empty_response_body()
	{
		final String responseJson = testContext.getApiResponse().getContent();

		assertThat(responseJson).isBlank();
	}

	@And("the actual non JSON response body is")
	public void validate_non_JSON_response_body(@NonNull final String responseBody)
	{
		final String content = testContext.getApiResponse().getContent();

		assertThat(content).isEqualTo(responseBody);
	}

	@When("add HTTP header")
	public void add_http_header(@NonNull final DataTable dataTable)
	{
		final Map<String, String> additionalHeaders = new HashMap<>();
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String key = DataTableUtil.extractStringForColumnName(row, "Key");
			final String value = DataTableUtil.extractStringForColumnName(row, "Value");

			additionalHeaders.put(key, value);
		}

		testContext.setHttpHeaders(additionalHeaders);
	}

	@And("following http headers are set on context")
	public void add_http_headers(@NonNull final DataTable dataTable)
	{
		final Map<String, String> customHeaders = new HashMap<>();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String headerName = DataTableUtil.extractStringForColumnName(row, "Name");
			final String headerValue = DataTableUtil.extractStringForColumnName(row, "Value");

			customHeaders.put(headerName, headerValue);
		}

		testContext.setHttpHeaders(customHeaders);
	}
}
