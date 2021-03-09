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

package de.metas.cucumber.stepdefs.issue;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.issue.JsonCreateIssueResponse;
import de.metas.cucumber.stepdefs.APIResponse;
import de.metas.cucumber.stepdefs.RESTUtil;
import de.metas.error.AdIssueId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_PInstance;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AD_Issue_StepDef
{
	private PInstanceId pInstanceId;
	private String userAuthToken;
	private APIResponse apiResponse;
	private String apiRequest;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public static final String endpointPath = "api/process/$pInstanceId/externalstatus/error";

	@Given("the existing user has a valid API token")
	public void the_existing_user_has_the_authtoken() throws IOException
	{
		userAuthToken = RESTUtil.getAuthToken("metasfresh", "WebUI");
	}

	@When("the metasfresh REST-API endpoint for issue creation receives a request with the payload")
	public void metasfresh_rest_api_endpoint_api_external_ref_receives_get_request_with_the_payload(
			final String payload) throws IOException
	{
		apiRequest = payload;
		apiResponse = RESTUtil.performHTTPRequest(endpointPath.replace("$pInstanceId", String.valueOf(pInstanceId.getRepoId())),
												  "POST", payload, userAuthToken);
	}

	@Given("I_AD_PInstance with id {string} is created")
	public void new_PInstanceId_is_created(final String PInstanceIdString)
	{
		pInstanceId = PInstanceId.ofRepoId(Integer.parseInt(PInstanceIdString));
		final I_AD_PInstance pInstance = InterfaceWrapperHelper.newInstance(I_AD_PInstance.class);
		pInstance.setAD_PInstance_ID(pInstanceId.getRepoId());
		pInstance.setAD_Process_ID(150);
		pInstance.setRecord_ID(0);
		InterfaceWrapperHelper.save(pInstance);
	}

	@Then("a new metasfresh AD issue is created")
	public void new_metasfresh_ad_issue_is_created() throws IOException
	{
		final String responseJson = apiResponse.getContent();
		final ObjectMapper mapper = new ObjectMapper();

		final JsonCreateIssueResponse response = mapper.readValue(responseJson, JsonCreateIssueResponse.class);
		final List<JsonErrorItem> requestErrorItemList = mapper.readValue(apiRequest, JsonError.class).getErrors();
		assertThat(requestErrorItemList).isNotNull();
		final JsonErrorItem jsonErrorItem = requestErrorItemList.get(0);
		assertThat(jsonErrorItem).isNotNull();

		final AdIssueId adIssueId = AdIssueId.ofRepoId(response.getIds().get(0).getIssueId().getValue());
		final I_AD_Issue issue = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Issue_ID, adIssueId)
				.create()
				.firstOnlyOptional(I_AD_Issue.class).orElse(null);

		final I_AD_Org adOrg = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, jsonErrorItem.getOrgCode())
				.create()
				.firstOnly(I_AD_Org.class);

		final OrgId orgId = adOrg != null ? OrgId.ofRepoId(adOrg.getAD_Org_ID()) : OrgId.ANY;

		assertThat(issue).isNotNull();
		assertThat(issue.getStackTrace()).isEqualTo(jsonErrorItem.getStackTrace().replace("java.lang.", ""));
		assertThat(issue.getSourceMethodName()).isEqualTo(jsonErrorItem.getSourceMethodName());
		assertThat(issue.getSourceClassName()).isEqualTo(jsonErrorItem.getSourceClassName());
		assertThat(issue.getIssueSummary()).isEqualTo(jsonErrorItem.getMessage());
		assertThat(issue.getIssueCategory()).isEqualTo(jsonErrorItem.getIssueCategory());
		assertThat(issue.getAD_PInstance_ID()).isEqualTo(pInstanceId.getRepoId());
		assertThat(orgId).isNotNull();
		assertThat(issue.getAD_Org_ID()).isEqualTo(orgId.getRepoId());

	}
}
