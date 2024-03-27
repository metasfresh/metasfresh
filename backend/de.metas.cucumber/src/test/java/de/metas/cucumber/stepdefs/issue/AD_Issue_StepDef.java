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
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.common.rest_api.v1.issue.JsonCreateIssueResponse;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.error.AdIssueId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_PInstance;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AD_Issue_StepDef
{
	private final AD_Issue_StepDefData issueTable;

	private PInstanceId pInstanceId;
	private final TestContext testContext;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_Issue_StepDef(
			@NonNull final AD_Issue_StepDefData issueTable,
			@NonNull final TestContext testContext)
	{
		this.issueTable = issueTable;
		this.testContext = testContext;
	}

	@Given("I_AD_PInstance with id {int} is created")
	public void new_PInstanceId_is_created(final int pInstanceIdString)
	{
		pInstanceId = PInstanceId.ofRepoId(pInstanceIdString);
		final I_AD_PInstance pInstance = InterfaceWrapperHelper.newInstance(I_AD_PInstance.class);
		pInstance.setAD_PInstance_ID(pInstanceId.getRepoId());
		pInstance.setAD_Process_ID(150);
		pInstance.setRecord_ID(0);
		InterfaceWrapperHelper.save(pInstance);
	}

	@Then("a new metasfresh AD issue is created")
	public void new_metasfresh_ad_issue_is_created() throws IOException
	{
		final String responseJson = testContext.getApiResponse().getContent();
		final ObjectMapper mapper = new ObjectMapper();

		final JsonCreateIssueResponse response = mapper.readValue(responseJson, JsonCreateIssueResponse.class);
		final List<JsonErrorItem> requestErrorItemList = mapper.readValue(testContext.getRequestPayload(), JsonError.class).getErrors();
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

	@And("validate AD_Issue")
	public void validate_AD_Issue(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Issue.COLUMNNAME_AD_Issue_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_AD_Issue issue = issueTable.get(issueIdentifier);

			final String issueSummary = DataTableUtil.extractStringForColumnName(row, I_AD_Issue.COLUMNNAME_IssueSummary);
			final boolean containsSummary = issue.getIssueSummary().contains(issueSummary);
			assertThat(containsSummary).isTrue();
		}
	}
}