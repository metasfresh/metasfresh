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

package de.metas.cucumber.stepdefs.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.money.JsonMoney;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.budget.JsonBudgetResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.resource.S_Resource_Group_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.organization.IOrgDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_Resource_Group;
import org.json.JSONException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class WorkOrderAndBudgetProjectRestController_StepDef
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

	private final C_ProjectType_StepDefData projectTypeTable;
	private final S_Resource_StepDefData resourceTable;
	private final S_Resource_Group_StepDefData resourceGroupTable;
	private final TestContext testContext;

	public WorkOrderAndBudgetProjectRestController_StepDef(
			@NonNull final C_ProjectType_StepDefData projectTypeTable,
			@NonNull final S_Resource_StepDefData resourceTable,
			@NonNull final S_Resource_Group_StepDefData resourceGroupTable,
			@NonNull final TestContext testContext
	)
	{
		this.projectTypeTable = projectTypeTable;
		this.resourceTable = resourceTable;
		this.resourceGroupTable = resourceGroupTable;
		this.testContext = testContext;
	}

	@And("store JsonWorkOrderProjectUpsertRequest in context")
	public void storeJsonWorkOrderProjectUpsertRequestInContext(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String projectTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_ProjectType.COLUMNNAME_C_ProjectType_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_ProjectType projectType = projectTypeTable.get(projectTypeIdentifier);

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_S_Resource resource = resourceTable.get(resourceIdentifier);

			final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
			projectRequest.setCurrencyId(JsonMetasfreshId.of(currencyDAO.getByCurrencyCode(CurrencyCode.EUR).getId().getRepoId()));
			projectRequest.setProjectIdentifier("ext-externalId");
			projectRequest.setBpartnerDepartment("bpartnerDepartment");
			projectRequest.setBPartnerTargetDate(LocalDate.parse("2022-08-20"));
			projectRequest.setDateContract(LocalDate.parse("2022-08-10"));
			projectRequest.setDateFinish(LocalDate.parse("2022-08-21"));
			projectRequest.setDescription("description");
			projectRequest.setIsActive(false);
			projectRequest.setName("name");
			projectRequest.setOrgCode(orgDAO.retrieveOrgValue(StepDefConstants.ORG_ID));
			projectRequest.setPOReference("POReference");
			projectRequest.setProjectReferenceExt("projectReferenceExt");
			projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
			projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
			projectRequest.setWoOwner("woOwner");
			projectRequest.setWOProjectCreatedDate(LocalDate.parse("2022-07-15"));

			final JsonWorkOrderStepUpsertRequest stepRequest1 = new JsonWorkOrderStepUpsertRequest();
			stepRequest1.setDateEnd(LocalDate.parse("2022-08-02"));
			stepRequest1.setDateStart(LocalDate.parse("2022-07-22"));
			stepRequest1.setDeliveryDate(LocalDate.parse("2022-08-05"));
			stepRequest1.setExternalId(JsonExternalId.of("stepRequest1-externalId"));
			stepRequest1.setName("stepRequest1-name");
			stepRequest1.setSeqNo(40);
			stepRequest1.setWOFindingsCreatedDate(LocalDate.parse("2022-08-01"));
			stepRequest1.setWOFindingsReleasedDate(LocalDate.parse("2022-08-03"));
			stepRequest1.setWoPartialReportDate(LocalDate.parse("2022-08-02"));
			stepRequest1.setWOPlannedPersonDurationHours(20);
			stepRequest1.setWOStepStatus(10);
			stepRequest1.setWOTargetEndDate(LocalDate.parse("2022-07-31"));
			stepRequest1.setWOTargetStartDate(LocalDate.parse("2022-07-21"));

			projectRequest.setSteps(ImmutableList.of(stepRequest1));

			final JsonWorkOrderResourceUpsertRequest resourceRequest1 = new JsonWorkOrderResourceUpsertRequest();
			resourceRequest1.setResourceIdentifier("int-" + resource.getInternalName());
			resourceRequest1.setTestFacilityGroupName("testFacilityGroupName");
			resourceRequest1.setExternalId(JsonExternalId.of("resourceRequest1-externalId"));
			resourceRequest1.setDurationUnit("h");
			resourceRequest1.setDuration(BigDecimal.TEN);
			resourceRequest1.setAssignDateFrom(LocalDate.parse("2022-08-07"));
			resourceRequest1.setAssignDateTo(LocalDate.parse("2022-08-08"));
			resourceRequest1.setActive(false);
			resourceRequest1.setAllDay(true);

			stepRequest1.setResourceRequests(ImmutableList.of(resourceRequest1));

			final String payload = mapper.writeValueAsString(projectRequest);

			testContext.setRequestPayload(payload);
		}
	}

	@Then("validate JsonWorkOrderProjectUpsertResponse coming back after POST request")
	public void validateJsonWorkOrderProjectUpsertResponse() throws JsonProcessingException
	{
		final String responseContent = testContext.getApiResponse().getContent();

		assertThat(responseContent).isNotBlank();

		final JsonWorkOrderProjectUpsertResponse response = mapper.readValue(responseContent, JsonWorkOrderProjectUpsertResponse.class);

		assertThat(response).isNotNull();
	}

	@And("store JsonBudgetProjectUpsertRequest in context")
	public void storeJsonBudgetProjectUpsertRequestInContext(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String projectTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_ProjectType.COLUMNNAME_C_ProjectType_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_ProjectType projectType = projectTypeTable.get(projectTypeIdentifier);

			final String resourceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_S_Resource resource = resourceTable.get(resourceIdentifier);

			final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
			final int uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code)).getRepoId();

			final String resourceGroupIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_S_Resource_Group.COLUMNNAME_S_Resource_Group_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_S_Resource_Group resourceGroup = resourceGroupTable.get(resourceGroupIdentifier);

			final JsonBudgetProjectUpsertRequest projectRequest = new JsonBudgetProjectUpsertRequest();
			projectRequest.setProjectIdentifier("ext-externalId");
			projectRequest.setValue("Budget Project");
			projectRequest.setName("Budget Project");
			projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
			projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
			projectRequest.setCurrencyId(JsonMetasfreshId.of(currencyDAO.getByCurrencyCode(CurrencyCode.EUR).getId().getRepoId()));
			projectRequest.setOrgCode(orgDAO.retrieveOrgValue(StepDefConstants.ORG_ID));
			projectRequest.setIsActive(false);

			final JsonMoney plannedAmt = new JsonMoney();
			plannedAmt.setAmount(BigDecimal.valueOf(500));
			plannedAmt.setCurrencyCode(CurrencyCode.EUR.toThreeLetterCode());

			final JsonBudgetResourceUpsertRequest resourceRequest1 = new JsonBudgetResourceUpsertRequest();
			resourceRequest1.setDateFinishPlan(LocalDate.parse("2022-07-12"));
			resourceRequest1.setDateStartPlan(LocalDate.parse("2022-07-02"));
			resourceRequest1.setPlannedDuration(BigDecimal.valueOf(500));
			resourceRequest1.setDescription("Resource Description");
			resourceRequest1.setPricePerTimeUOM(BigDecimal.valueOf(5));
			resourceRequest1.setResourceIdentifier("int-" + resource.getInternalName());
			resourceRequest1.setUomTimeId(JsonMetasfreshId.of(uomId));
			resourceRequest1.setActive(false);
			resourceRequest1.setPlannedAmt(plannedAmt);
			resourceRequest1.setResourceGroupId(JsonMetasfreshId.of(resourceGroup.getS_Resource_Group_ID()));

			projectRequest.setResources(ImmutableList.of(resourceRequest1));

			final String payload = mapper.writeValueAsString(projectRequest);

			testContext.setRequestPayload(payload);
		}
	}

	@And("validate JsonBudgetProjectResourceResponse coming back after POST request")
	public void validateJsonBudgetProjectResourceResponse() throws JsonProcessingException
	{
		final String responseContent = testContext.getApiResponse().getContent();

		assertThat(responseContent).isNotBlank();

		final JsonBudgetProjectUpsertResponse response = mapper.readValue(responseContent, JsonBudgetProjectUpsertResponse.class);

		assertThat(response).isNotNull();
	}

	@And("append id from response to project endpoint path {string} and store it to context")
	public void buildGetProjectEndpointPath(@NonNull final String endpointPath) throws JsonProcessingException
	{
		final String responseContent = testContext.getApiResponse().getContent();

		final JsonNode jsonNode = mapper.readTree(responseContent);

		testContext.setEndpointPath(endpointPath + "/" + jsonNode.get("projectId").asText());
	}

	@And("validate JsonWorkOrderProjectResponse coming back after GET request")
	public void validateJsonWorkOrderProjectResponse() throws JsonProcessingException, JSONException
	{
		final String responseContent = testContext.getApiResponse().getContent();

		final JsonWorkOrderProjectResponse response = mapper.readValue(responseContent, JsonWorkOrderProjectResponse.class);
		assertThat(response).isNotNull();

		final String postRequestPayload = testContext.getRequestPayload();

		final JsonWorkOrderProjectUpsertRequest request = mapper.readValue(postRequestPayload, JsonWorkOrderProjectUpsertRequest.class);
		assertThat(request).isNotNull();

		assertThat(response.getCurrencyId()).isEqualTo(request.getCurrencyId());
		assertThat(response.getBPartnerId()).isEqualTo(request.getBusinessPartnerId());
		assertThat(response.getOrgCode()).isEqualTo(request.getOrgCode());
		assertThat(response.getPriceListVersionId()).isEqualTo(request.getPriceListVersionId());
		assertThat(response.getProjectReferenceExt()).isEqualTo(request.getProjectReferenceExt());
		assertThat(response.getProjectTypeId()).isEqualTo(request.getProjectTypeId());
		assertThat(response.getDateContract()).isEqualTo(request.getDateContract().toString());
		assertThat(response.getDateFinish()).isEqualTo(request.getDateFinish().toString());
		assertThat(response.getDescription()).isEqualTo(request.getDescription());
		assertThat(response.getIsActive()).isEqualTo(request.getIsActive());
		assertThat(response.getName()).isEqualTo(request.getName());

		final Iterator<JsonWorkOrderStepUpsertRequest> requestStepsIterator = request.getSteps().listIterator();
		final Iterator<JsonWorkOrderStepResponse> responseStepsIterator = response.getSteps().listIterator();

		while (requestStepsIterator.hasNext() && responseStepsIterator.hasNext())
		{
			final JsonWorkOrderStepUpsertRequest stepRequest = requestStepsIterator.next();
			final JsonWorkOrderStepResponse stepResponse = responseStepsIterator.next();

			assertThat(stepResponse.getName()).isEqualTo(stepRequest.getName());
			assertThat(stepResponse.getSeqNo()).isEqualTo(stepRequest.getSeqNo());
			assertThat(stepResponse.getDescription()).isEqualTo(stepRequest.getDescription());
			assertThat(stepResponse.getDateEnd()).isEqualTo(stepRequest.getDateEnd().toString());
			assertThat(stepResponse.getDateStart()).isEqualTo(stepRequest.getDateStart().toString());
			assertThat(stepResponse.getExternalId()).isEqualTo(stepRequest.getExternalId().getValue());

			final Iterator<JsonWorkOrderResourceUpsertRequest> requestResourceIterator = stepRequest.getResourceRequests().listIterator();
			final Iterator<JsonWorkOrderResourceResponse> responseResourceIterator = stepResponse.getResources().listIterator();

			while (requestResourceIterator.hasNext() && responseResourceIterator.hasNext())
			{
				final JsonWorkOrderResourceUpsertRequest resourceRequest = requestResourceIterator.next();
				final JsonWorkOrderResourceResponse resourceResponse = responseResourceIterator.next();

				assertThat(resourceResponse.getAssignDateFrom()).isEqualTo(resourceRequest.getAssignDateFrom().toString());
				assertThat(resourceResponse.getAssignDateTo()).isEqualTo(resourceRequest.getAssignDateTo().toString());
				assertThat(resourceResponse.getDuration()).isEqualTo(resourceRequest.getDuration());
				assertThat(resourceResponse.getDurationUnit()).isEqualTo(resourceRequest.getDurationUnit());
				assertThat(resourceResponse.getIsActive()).isEqualTo(resourceRequest.getIsActive());
				assertThat(resourceResponse.getIsAllDay()).isEqualTo(resourceRequest.getIsAllDay());
			}
		}
	}

	@And("validate JsonBudgetProjectResponse coming back after GET request")
	public void validateJsonBudgetProjectResponse() throws JsonProcessingException
	{
		final String responseContent = testContext.getApiResponse().getContent();

		final JsonBudgetProjectResponse response = mapper.readValue(responseContent, JsonBudgetProjectResponse.class);

		final String postRequestPayload = testContext.getRequestPayload();

		final JsonBudgetProjectUpsertRequest request = mapper.readValue(postRequestPayload, JsonBudgetProjectUpsertRequest.class);
	}

	@NonNull
	private JsonWorkOrderProjectResponse convertWorkOrderJsonRequestToJsonResponse(@NonNull final JsonWorkOrderProjectUpsertRequest request)
	{
		final JsonWorkOrderProjectResponse.JsonWorkOrderProjectResponseBuilder responseBuilder = JsonWorkOrderProjectResponse.builder()
				.orgCode(request.getOrgCode())
				.isActive(request.getIsActive())
				.value("request.getValue()")
				.name(request.getName())
				.projectReferenceExt(request.getProjectReferenceExt())
				.description(request.getDescription())
				.projectTypeId(request.getProjectTypeId())
				.projectParentId(request.getProjectParentId())
				.salesRepId(request.getSalesRepId())
				.priceListVersionId(request.getPriceListVersionId())
				.currencyId(request.getCurrencyId())
				.bPartnerId(request.getBusinessPartnerId())
				.dateContract(request.getDateContract().toString())
				.dateFinish(request.getDateFinish().toString());

		final ImmutableList.Builder<JsonWorkOrderStepResponse> stepListBuilder = ImmutableList.builder();

		request.getSteps().forEach(stepRequest -> {
			final JsonWorkOrderStepResponse.JsonWorkOrderStepResponseBuilder stepResponseBuilder = JsonWorkOrderStepResponse.builder()
					.name(stepRequest.getName())
					.seqNo(stepRequest.getSeqNo())
					.description(stepRequest.getDescription())
					.dateStart(stepRequest.getDateStart().toString())
					.dateEnd(stepRequest.getDateEnd().toString());

			stepRequest.getResourceRequests().forEach(resourceRequest -> {
				final JsonWorkOrderResourceResponse resourceResponse = JsonWorkOrderResourceResponse.builder()
						.externalId(resourceRequest.getExternalId().getValue())
						.isAllDay(resourceRequest.getIsAllDay())
						.isActive(resourceRequest.getIsActive())
						.durationUnit(resourceRequest.getDurationUnit())
						.duration(resourceRequest.getDuration())
						.assignDateFrom(resourceRequest.getAssignDateFrom().toString())
						.assignDateTo(resourceRequest.getAssignDateTo().toString())
						.build();

				stepResponseBuilder.resource(resourceResponse);
			});

			stepListBuilder.add(stepResponseBuilder.build());
		});

		responseBuilder.steps(stepListBuilder.build());

		return responseBuilder.build();
	}

}
