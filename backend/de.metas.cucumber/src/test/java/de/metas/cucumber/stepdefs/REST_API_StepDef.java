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

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.IOException;

public class REST_API_StepDef
{
	private String userAuthToken;
	private APIResponse apiResponse;

	@Given("the existing user with login {string} receives a random a API token for the existing role with name {string}")
	public void the_existing_user_has_the_authtoken(@NonNull final String userLogin, @NonNull final String roleName) throws IOException
	{
		userAuthToken = RESTUtil.getAuthToken(userLogin,roleName);
	}

	@When("the metasfresh REST-API endpoint path {string} receives a {string} request with the payload")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload(
			final String endpointPath,
			final String verb,
			final String payload) throws IOException
	{
		apiResponse = RESTUtil.performHTTPRequest(endpointPath, verb, payload, userAuthToken);
	}

	@Then("the metasfresh REST-API responds with")
	public void the_metasfresh_REST_API_responds_with(@NonNull final String expectedResponse) throws JSONException
	{
		JSONAssert.assertEquals(expectedResponse, apiResponse.getContent(), JSONCompareMode.LENIENT);
	}

}
