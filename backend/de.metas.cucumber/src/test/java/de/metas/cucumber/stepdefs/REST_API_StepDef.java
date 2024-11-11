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
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonTestResponse;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.compiere.util.Evaluatees;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class REST_API_StepDef
{
	private String userAuthToken;

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
		final String payloadResolved = resolveContextVariables(payload);
		testContext.setRequestPayload(payloadResolved);

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.statusCode(Integer.parseInt(statusCode))
				.authToken(userAuthToken)
				.payload(payloadResolved)
				.build();

		performHTTPRequest(request);
	}

	private String resolveContextVariables(final String string)
	{
		if (Check.isBlank(string))
		{
			return string;
		}

		final HashMap<String, String> variables = testContext.getVariables();
		if (variables == null || variables.isEmpty())
		{
			return string;
		}

		return StringExpressionCompiler.instance.compile(string).evaluate(Evaluatees.ofMap(variables), OnVariableNotFound.Preserve);
	}

	private void performHTTPRequest(final APIRequest request) throws IOException
	{
		APIResponse apiResponse = RESTUtil.performHTTPRequest(request);
		apiResponse = apiResponse.withContent(resolveContextVariables(apiResponse.getContent()));

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

		performHTTPRequest(request);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the payload")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload(
			@NonNull final String endpointPath,
			@NonNull final String verb,
			@NonNull final String payload) throws IOException
	{
		final String payloadResolved = resolveContextVariables(payload);
		testContext.setRequestPayload(payloadResolved);

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.authToken(userAuthToken)
				.payload(payloadResolved)
				.build();

		performHTTPRequest(request);
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
				.build();

		performHTTPRequest(request);
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

		performHTTPRequest(request);
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

		performHTTPRequest(request);
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

		performHTTPRequest(request);
	}

	@When("a {string} request with the below payload and headers from context is sent to the metasfresh REST-API {string} and fulfills with {string} status code")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_request_with_additional_headers_and_below_payload(
			@NonNull final String verb,
			@NonNull final String endpointPath,
			@NonNull final String statusCode,
			@NonNull final String payload) throws IOException
	{
		final String payloadResolved = resolveContextVariables(payload);
		testContext.setRequestPayload(payloadResolved);

		final APIRequest request = APIRequest.builder()
				.endpointPath(endpointPath)
				.verb(verb)
				.statusCode(Integer.parseInt(statusCode))
				.authToken(userAuthToken)
				.additionalHeaders(testContext.getHttpHeaders())
				.payload(payloadResolved)
				.build();

		performHTTPRequest(request);
	}

	@Then("the metasfresh REST-API responds with")
	public void the_metasfresh_REST_API_responds_with(@NonNull final String expectedResponse) throws JSONException
	{
		final String expectedResponseResolved = resolveContextVariables(expectedResponse);
		JSONAssert.assertEquals(expectedResponseResolved, testContext.getApiResponseBodyAsString(), JSONCompareMode.LENIENT);
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

		performHTTPRequest(request);
	}

	@And("the actual response body is")
	public void validate_response_body(@NonNull final String expectedResponseBodyString) throws JsonProcessingException
	{
		final JsonTestResponse actualResponseBody = testContext.getApiResponseBodyAs(JsonTestResponse.class);
		final JsonTestResponse expectedResponseBody = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(expectedResponseBodyString, JsonTestResponse.class);

		assertThat(actualResponseBody.getMessageBody()).isEqualTo(expectedResponseBody.getMessageBody());
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
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String key = DataTableUtil.extractStringForColumnName(tableRow, "Key");
		final String value = DataTableUtil.extractStringForColumnName(tableRow, "Value");

		final Map<String, String> additionalHeaders = new HashMap<>();

		additionalHeaders.put(key, value);

		testContext.setHttpHeaders(additionalHeaders);
	}

	@And("put REST context variables")
	public void putContextVariables(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::putContextVariable);
	}

	public void putContextVariable(@NonNull final DataTableRow row)
	{
		@NonNull final String variableName = row.getAsString("Name");
		String value = row.getAsOptionalString("Value").orElse("");

		final boolean isResolveVars = row.getAsOptionalBoolean("Resolve").orElse(true);
		if (isResolveVars)
		{
			value = resolveContextVariables(value);
		}

		testContext.setVariable(variableName, value);
	}

}
