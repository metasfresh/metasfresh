/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.project.budget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResourceResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertResponse;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.project.ProjectId_StepDefData;
import de.metas.project.ProjectId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.model.I_C_Project;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class BudgetProjectRestController_StepDef
{
	private static final String ENDPOINT_API_V2_BUDGET = "api/v2/project/budget";

	private final TestContext testContext;
	private final ProjectId_StepDefData projectIdTable;

	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	public BudgetProjectRestController_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final ProjectId_StepDefData projectIdTable)
	{
		this.testContext = testContext;
		this.projectIdTable = projectIdTable;
	}

	@Then("validate budget project 'GET' response")
	public void process_budget_project_get_response(@NonNull final String response) throws JsonProcessingException
	{
		final JsonBudgetProjectResponse jsonBudgetProjectResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonBudgetProjectResponse.class);

		final JsonApiResponse expectedJsonApiResponse = mapper.readValue(response, JsonApiResponse.class);

		final String endpointResponse = mapper.writeValueAsString(expectedJsonApiResponse.getEndpointResponse());

		final JsonBudgetProjectResponse expectedJsonBudgetProjectResponse = mapper.readValue(endpointResponse, JsonBudgetProjectResponse.class);

		validateJsonBudgetProjectResponse(jsonBudgetProjectResponse, expectedJsonBudgetProjectResponse);
	}

	@Then("process budget project upsert response")
	public void process_budget_project_upsert_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonBudgetProjectUpsertResponse budgetProjectUpsertResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonBudgetProjectUpsertResponse.class);

		final Map<String, String> row = table.asMaps().get(0);

		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(budgetProjectUpsertResponse.getProjectId()));
		assertThat(projectId).isNotNull();

		projectIdTable.putOrReplace(projectIdentifier, projectId);
	}

	@And("build 'GET' budget project endpoint path with the following id:")
	public void build_get_bugdget_project_endpoint_path(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ProjectId projectId = projectIdTable.get(projectIdentifier);

		testContext.setEndpointPath(ENDPOINT_API_V2_BUDGET + "/" + projectId.getRepoId());
	}

	private void validateJsonBudgetProjectResponse(
			@NonNull final JsonBudgetProjectResponse jsonBudgetProjectResponse,
			@NonNull final JsonBudgetProjectResponse expectedJsonBudgetProjectResponse)
	{
		assertThat(jsonBudgetProjectResponse.getOrgCode()).isEqualTo(expectedJsonBudgetProjectResponse.getOrgCode());
		assertThat(jsonBudgetProjectResponse.getCurrencyCode()).isEqualTo(expectedJsonBudgetProjectResponse.getCurrencyCode());
		assertThat(jsonBudgetProjectResponse.getName()).isEqualTo(expectedJsonBudgetProjectResponse.getName());
		assertThat(jsonBudgetProjectResponse.getValue()).isEqualTo(expectedJsonBudgetProjectResponse.getValue());
		assertThat(jsonBudgetProjectResponse.getIsActive()).isEqualTo(expectedJsonBudgetProjectResponse.getIsActive());
		assertThat(jsonBudgetProjectResponse.getPriceListVersionId()).isEqualTo(expectedJsonBudgetProjectResponse.getPriceListVersionId());
		assertThat(jsonBudgetProjectResponse.getDescription()).isEqualTo(expectedJsonBudgetProjectResponse.getDescription());
		assertThat(jsonBudgetProjectResponse.getProjectParentId()).isEqualTo(expectedJsonBudgetProjectResponse.getProjectParentId());
		assertThat(jsonBudgetProjectResponse.getProjectTypeId()).isEqualTo(expectedJsonBudgetProjectResponse.getProjectTypeId());
		assertThat(jsonBudgetProjectResponse.getProjectReferenceExt()).isEqualTo(expectedJsonBudgetProjectResponse.getProjectReferenceExt());
		assertThat(jsonBudgetProjectResponse.getBpartnerId()).isEqualTo(expectedJsonBudgetProjectResponse.getBpartnerId());
		assertThat(jsonBudgetProjectResponse.getSalesRepId()).isEqualTo(expectedJsonBudgetProjectResponse.getSalesRepId());
		assertThat(jsonBudgetProjectResponse.getDateContract()).isEqualTo(expectedJsonBudgetProjectResponse.getDateContract());
		assertThat(jsonBudgetProjectResponse.getDateFinish()).isEqualTo(expectedJsonBudgetProjectResponse.getDateFinish());
		assertThat(jsonBudgetProjectResponse.getExtendedProps()).isEqualTo(expectedJsonBudgetProjectResponse.getExtendedProps());

		final List<JsonBudgetProjectResourceResponse> projectResources = jsonBudgetProjectResponse.getProjectResources();
		final List<JsonBudgetProjectResourceResponse> expectedProjectResources = expectedJsonBudgetProjectResponse.getProjectResources();

		final int projectResourcesSize = jsonBudgetProjectResponse.getProjectResources().size();
		assertThat(projectResourcesSize).isEqualByComparingTo(expectedProjectResources.size());

		for (int i = 0; i < projectResourcesSize; i++)
		{
			validateJsonBudgetProjectResourceResponse(projectResources.get(i), expectedProjectResources.get(i));
		}
	}

	private void validateJsonBudgetProjectResourceResponse(
			@NonNull final JsonBudgetProjectResourceResponse jsonBudgetProjectResourceResponse,
			@NonNull final JsonBudgetProjectResourceResponse expectedJsonBudgetProjectResourceResponse)
	{
		assertThat(jsonBudgetProjectResourceResponse.getUomTimeId()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getUomTimeId());
		assertThat(jsonBudgetProjectResourceResponse.getDateStartPlan()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getDateStartPlan());
		assertThat(jsonBudgetProjectResourceResponse.getDateFinishPlan()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getDateFinishPlan());
		assertThat(jsonBudgetProjectResourceResponse.getDescription()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getDescription());
		assertThat(jsonBudgetProjectResourceResponse.getPlannedAmt()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getPlannedAmt());
		assertThat(jsonBudgetProjectResourceResponse.getCurrencyCode()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getCurrencyCode());
		assertThat(jsonBudgetProjectResourceResponse.getPlannedDuration()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getPlannedDuration());
		assertThat(jsonBudgetProjectResourceResponse.getPricePerTimeUOM()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getPricePerTimeUOM());
		assertThat(jsonBudgetProjectResourceResponse.getResourceGroupId()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getResourceGroupId());
		assertThat(jsonBudgetProjectResourceResponse.getResourceId()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getResourceId());
		assertThat(jsonBudgetProjectResourceResponse.getExternalId()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getExternalId());
		assertThat(jsonBudgetProjectResourceResponse.getIsActive()).isEqualTo(expectedJsonBudgetProjectResourceResponse.getIsActive());
	}
}
