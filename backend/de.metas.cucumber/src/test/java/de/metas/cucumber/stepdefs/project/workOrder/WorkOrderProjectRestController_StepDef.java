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

package de.metas.cucumber.stepdefs.project.workOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponses;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.project.ProjectId_StepDefData;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class WorkOrderProjectRestController_StepDef
{
	private static final String ENDPOINT_API_V2_WORK_ORDER = "api/v2/project/workorder";
	private static final String NEXT_DOC_SEQ_NO_PLACEHOLDER = "nextDocNo";

	private final ProjectId_StepDefData projectIdTable;
	private final C_Project_WO_Step_StepDefData projectWOStepTable;
	private final TestContext testContext;

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public WorkOrderProjectRestController_StepDef(
			@NonNull final ProjectId_StepDefData projectIdTable,
			@NonNull final C_Project_WO_Step_StepDefData projectWOStepTable,
			@NonNull final TestContext testContext)
	{
		this.projectIdTable = projectIdTable;
		this.projectWOStepTable = projectWOStepTable;
		this.testContext = testContext;
	}

	@And("process work order project upsert response")
	public void process_wo_project_upsert_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonWorkOrderProjectUpsertResponse workOrderProjectUpsertResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonWorkOrderProjectUpsertResponse.class);

		final Map<String, String> row = table.asMaps().get(0);

		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ProjectId projectId = ProjectId.ofRepoIdOrNull(JsonMetasfreshId.toValue(workOrderProjectUpsertResponse.getMetasfreshId()));
		assertThat(projectId).isNotNull();

		projectIdTable.putOrReplace(projectIdentifier, projectId);

		final String stepIdentifiers = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(stepIdentifiers))
		{
			final ImmutableList<String> stepIdentifiersList = StepDefUtil.extractIdentifiers(stepIdentifiers);
			assertThat(workOrderProjectUpsertResponse.getSteps()).isNotNull();
			final List<JsonWorkOrderStepUpsertResponse> steps = workOrderProjectUpsertResponse.getSteps(); 
			assertThat(steps).isNotNull();
			assertThat(stepIdentifiersList.size()).isEqualTo(steps.size());

			for (int index = 0; index < stepIdentifiersList.size(); index++)
			{
				final I_C_Project_WO_Step stepRecord = InterfaceWrapperHelper.load(steps.get(index).getMetasfreshId().getValue(), I_C_Project_WO_Step.class);
				
				projectWOStepTable.putOrReplace(stepIdentifiersList.get(index), stepRecord);
			}
		}
	}

	@And("validate the following exception message was thrown by the work order project upsert endpoint")
	public void validate_wo_project_upsert_response(@NonNull final DataTable table)
	{
		final String apiResponse = testContext.getApiResponse().getContent();

		final Map<String, String> row = table.asMaps().get(0);

		final String expectedApiResponse = DataTableUtil.extractStringForColumnName(row, "ExceptionMessage");

		assertThat(apiResponse).contains(expectedApiResponse);
	}

	@And("build 'GET' work order project endpoint path with the following id:")
	public void build_get_wo_project_endpoint_path(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final ProjectId projectId = projectIdTable.get(projectIdentifier);

		testContext.setEndpointPath(ENDPOINT_API_V2_WORK_ORDER + "/" + projectId.getRepoId());
	}

	@And("validate work order project 'GET' response")
	public void process_wo_project_get_response(@NonNull final String response) throws JsonProcessingException
	{
		final JsonWorkOrderProjectResponse jsonWorkOrderProjectResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonWorkOrderProjectResponse.class);

		final JsonApiResponse expectedJsonApiResponse = mapper.readValue(response, JsonApiResponse.class);

		final String endpointResponse = mapper.writeValueAsString(expectedJsonApiResponse.getEndpointResponse());

		final JsonWorkOrderProjectResponse expectedJsonWorkOrderProjectResponse = mapper.readValue(endpointResponse, JsonWorkOrderProjectResponse.class);

		validateJsonWorkOrderProjectResponse(jsonWorkOrderProjectResponse, expectedJsonWorkOrderProjectResponse);
	}

	@And("validate work order project 'JsonWorkOrderProjectResponses' response")
	public void process_wo_project_get_by_query_response(@NonNull final String response) throws JsonProcessingException
	{
		final JsonWorkOrderProjectResponses jsonWorkOrderProjectResponses = mapper.readValue(testContext.getApiResponse().getContent(), JsonWorkOrderProjectResponses.class);

		final JsonApiResponse expectedJsonApiResponse = mapper.readValue(response, JsonApiResponse.class);

		final String endpointResponse = mapper.writeValueAsString(expectedJsonApiResponse.getEndpointResponse());

		final JsonWorkOrderProjectResponses expectedJsonWorkOrderProjectResponses = mapper.readValue(endpointResponse, JsonWorkOrderProjectResponses.class);

		validateJsonWorkOrderProjectResponses(jsonWorkOrderProjectResponses, expectedJsonWorkOrderProjectResponses);
	}

	private void validateJsonWorkOrderProjectResponses(
			@NonNull final JsonWorkOrderProjectResponses jsonWorkOrderProjectResponses,
			@NonNull final JsonWorkOrderProjectResponses expectedJsonWorkOrderProjectResponses)
	{
		final List<JsonWorkOrderProjectResponse> jsonWorkOrderProjectResponseList = jsonWorkOrderProjectResponses.getProjects();
		final List<JsonWorkOrderProjectResponse> expectedJsonWorkOrderProjectResponseList = expectedJsonWorkOrderProjectResponses.getProjects();

		final int jsonWorkOrderProjectResponseListSize = jsonWorkOrderProjectResponseList.size();
		assertThat(jsonWorkOrderProjectResponseListSize).isEqualTo(expectedJsonWorkOrderProjectResponseList.size());

		for (int i = 0; i < jsonWorkOrderProjectResponseListSize; i++)
		{
			validateJsonWorkOrderProjectResponse(jsonWorkOrderProjectResponseList.get(i), expectedJsonWorkOrderProjectResponseList.get(i));
		}
	}

	private void validateJsonWorkOrderObjectsUnderTestResponseList(
			@NonNull final JsonWorkOrderProjectResponse jsonWorkOrderProjectResponse,
			@NonNull final JsonWorkOrderProjectResponse expectedJsonWorkOrderProjectResponse)
	{
		final List<JsonWorkOrderObjectsUnderTestResponse> objectsUnderTest = jsonWorkOrderProjectResponse.getObjectsUnderTest();
		final List<JsonWorkOrderObjectsUnderTestResponse> expectedObjectsUnderTest = expectedJsonWorkOrderProjectResponse.getObjectsUnderTest();

		assertThat(objectsUnderTest).isNotNull();
		assertThat(expectedObjectsUnderTest).isNotNull();

		final int objectsUnderTestSize = objectsUnderTest.size();
		assertThat(objectsUnderTestSize).isEqualByComparingTo(expectedObjectsUnderTest.size());

		for (int i = 0; i < objectsUnderTestSize; i++)
		{
			validateJsonWorkOrderObjectsUnderTestResponse(objectsUnderTest.get(i), expectedObjectsUnderTest.get(i));
		}
	}

	private void validateJsonWorkOrderStepResponseList(
			@NonNull final JsonWorkOrderProjectResponse jsonWorkOrderProjectResponse,
			@NonNull final JsonWorkOrderProjectResponse expectedJsonWorkOrderProjectResponse)
	{
		final List<JsonWorkOrderStepResponse> projectSteps = jsonWorkOrderProjectResponse.getSteps();
		final List<JsonWorkOrderStepResponse> expectedSteps = expectedJsonWorkOrderProjectResponse.getSteps();

		assertThat(projectSteps).isNotNull();
		assertThat(expectedSteps).isNotNull();

		final int projectStepsSize = projectSteps.size();
		assertThat(projectStepsSize).isEqualByComparingTo(expectedSteps.size());

		for (int i = 0; i < projectStepsSize; i++)
		{
			validateJsonWorkOrderStepResponse(projectSteps.get(i), expectedSteps.get(i));
		}
	}

	private void validateJsonWorkOrderResourceResponseList(
			@NonNull final JsonWorkOrderStepResponse jsonWorkOrderStepResponse,
			@NonNull final JsonWorkOrderStepResponse expectedJsonWorkOrderStepResponse)
	{
		final List<JsonWorkOrderResourceResponse> projectStepResources = jsonWorkOrderStepResponse.getResources();
		final List<JsonWorkOrderResourceResponse> expectedStepResources = expectedJsonWorkOrderStepResponse.getResources();

		if (projectStepResources == null)
		{
			assertThat(expectedStepResources).isNull();	
		}
		else
		{
			assertThat(projectStepResources).isNotNull();
			assertThat(expectedStepResources).isNotNull();

			final int projectStepResourcesSize = projectStepResources.size();
			assertThat(projectStepResourcesSize).isEqualByComparingTo(expectedStepResources.size());

			for (int i = 0; i < projectStepResourcesSize; i++)
			{
				validateJsonWorkOrderResourceResponse(projectStepResources.get(i), expectedStepResources.get(i));
			}
		}
	}

	private void validateJsonWorkOrderProjectResponse(
			@NonNull final JsonWorkOrderProjectResponse jsonWorkOrderProjectResponse,
			@NonNull final JsonWorkOrderProjectResponse expectedJsonWorkOrderProjectResponse)
	{
		if (!expectedJsonWorkOrderProjectResponse.getValue().equalsIgnoreCase(NEXT_DOC_SEQ_NO_PLACEHOLDER))
		{
			assertThat(jsonWorkOrderProjectResponse.getValue()).isEqualTo(expectedJsonWorkOrderProjectResponse.getValue());
		}
		else
		{
			assertThat(jsonWorkOrderProjectResponse.getValue())
					.isEqualTo(getDocSeqNumber(ProjectTypeId.ofRepoId(jsonWorkOrderProjectResponse.getProjectTypeId().getValue())));
		}

		if (!expectedJsonWorkOrderProjectResponse.getName().equalsIgnoreCase(NEXT_DOC_SEQ_NO_PLACEHOLDER))
		{
			assertThat(jsonWorkOrderProjectResponse.getName()).isEqualTo(expectedJsonWorkOrderProjectResponse.getName());
		}
		else
		{
			assertThat(jsonWorkOrderProjectResponse.getName())
					.isEqualTo(getDocSeqNumber(ProjectTypeId.ofRepoId(jsonWorkOrderProjectResponse.getProjectTypeId().getValue())));
		}

		assertThat(jsonWorkOrderProjectResponse.getProjectTypeId()).isEqualTo(expectedJsonWorkOrderProjectResponse.getProjectTypeId());
		assertThat(jsonWorkOrderProjectResponse.getPriceListVersionId()).isEqualTo(expectedJsonWorkOrderProjectResponse.getPriceListVersionId());
		assertThat(jsonWorkOrderProjectResponse.getCurrencyCode()).isEqualTo(expectedJsonWorkOrderProjectResponse.getCurrencyCode());
		assertThat(jsonWorkOrderProjectResponse.getSalesRepId()).isEqualTo(expectedJsonWorkOrderProjectResponse.getSalesRepId());
		assertThat(jsonWorkOrderProjectResponse.getDescription()).isEqualTo(expectedJsonWorkOrderProjectResponse.getDescription());
		assertThat(jsonWorkOrderProjectResponse.getDateContract()).isEqualTo(expectedJsonWorkOrderProjectResponse.getDateContract());
		assertThat(jsonWorkOrderProjectResponse.getDateFinish()).isEqualTo(expectedJsonWorkOrderProjectResponse.getDateFinish());
		assertThat(jsonWorkOrderProjectResponse.getBpartnerId()).isEqualTo(expectedJsonWorkOrderProjectResponse.getBpartnerId());
		assertThat(jsonWorkOrderProjectResponse.getProjectReferenceExt()).isEqualTo(expectedJsonWorkOrderProjectResponse.getProjectReferenceExt());
		assertThat(jsonWorkOrderProjectResponse.getProjectParentId()).isEqualTo(expectedJsonWorkOrderProjectResponse.getProjectParentId());
		assertThat(jsonWorkOrderProjectResponse.getOrgCode()).isEqualTo(expectedJsonWorkOrderProjectResponse.getOrgCode());
		assertThat(jsonWorkOrderProjectResponse.getIsActive()).isEqualTo(expectedJsonWorkOrderProjectResponse.getIsActive());
		assertThat(jsonWorkOrderProjectResponse.getDateOfProvisionByBPartner()).isEqualTo(expectedJsonWorkOrderProjectResponse.getDateOfProvisionByBPartner());
		assertThat(jsonWorkOrderProjectResponse.getWoOwner()).isEqualTo(expectedJsonWorkOrderProjectResponse.getWoOwner());
		assertThat(jsonWorkOrderProjectResponse.getPoReference()).isEqualTo(expectedJsonWorkOrderProjectResponse.getPoReference());
		assertThat(jsonWorkOrderProjectResponse.getBpartnerDepartment()).isEqualTo(expectedJsonWorkOrderProjectResponse.getBpartnerDepartment());
		assertThat(jsonWorkOrderProjectResponse.getBpartnerTargetDate()).isEqualTo(expectedJsonWorkOrderProjectResponse.getBpartnerTargetDate());
		assertThat(jsonWorkOrderProjectResponse.getWoProjectCreatedDate()).isEqualTo(expectedJsonWorkOrderProjectResponse.getWoProjectCreatedDate());

		validateJsonWorkOrderStepResponseList(jsonWorkOrderProjectResponse, expectedJsonWorkOrderProjectResponse);
		validateJsonWorkOrderObjectsUnderTestResponseList(jsonWorkOrderProjectResponse, expectedJsonWorkOrderProjectResponse);
	}

	private void validateJsonWorkOrderStepResponse(
			@NonNull final JsonWorkOrderStepResponse jsonWorkOrderStepResponse,
			@NonNull final JsonWorkOrderStepResponse expectedJsonWorkOrderStepResponse)
	{
		assertThat(jsonWorkOrderStepResponse.getName()).isEqualTo(expectedJsonWorkOrderStepResponse.getName());
		assertThat(jsonWorkOrderStepResponse.getDescription()).isEqualTo(expectedJsonWorkOrderStepResponse.getDescription());
		assertThat(jsonWorkOrderStepResponse.getSeqNo()).isEqualTo(expectedJsonWorkOrderStepResponse.getSeqNo());
		assertThat(jsonWorkOrderStepResponse.getDateStart()).isEqualTo(expectedJsonWorkOrderStepResponse.getDateStart());
		assertThat(jsonWorkOrderStepResponse.getDateEnd()).isEqualTo(expectedJsonWorkOrderStepResponse.getDateEnd());
		assertThat(jsonWorkOrderStepResponse.getExternalId()).isEqualTo(expectedJsonWorkOrderStepResponse.getExternalId());
		assertThat(jsonWorkOrderStepResponse.getWoPartialReportDate()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoPartialReportDate());
		assertThat(jsonWorkOrderStepResponse.getWoPlannedResourceDurationHours()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoPlannedResourceDurationHours());
		assertThat(jsonWorkOrderStepResponse.getDeliveryDate()).isEqualTo(expectedJsonWorkOrderStepResponse.getDeliveryDate());
		assertThat(jsonWorkOrderStepResponse.getWoTargetStartDate()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoTargetStartDate());
		assertThat(jsonWorkOrderStepResponse.getWoTargetEndDate()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoTargetEndDate());
		assertThat(jsonWorkOrderStepResponse.getWoPlannedPersonDurationHours()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoPlannedPersonDurationHours());
		assertThat(jsonWorkOrderStepResponse.getWoStepStatus()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoStepStatus());
		assertThat(jsonWorkOrderStepResponse.getWoFindingsReleasedDate()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoFindingsReleasedDate());
		assertThat(jsonWorkOrderStepResponse.getWoFindingsCreatedDate()).isEqualTo(expectedJsonWorkOrderStepResponse.getWoFindingsCreatedDate());

		validateJsonWorkOrderResourceResponseList(jsonWorkOrderStepResponse, expectedJsonWorkOrderStepResponse);
	}

	private void validateJsonWorkOrderResourceResponse(
			@NonNull final JsonWorkOrderResourceResponse jsonWorkOrderResourceResponse,
			@NonNull final JsonWorkOrderResourceResponse expectedJsonWorkOrderResourceResponse)
	{
		assertThat(jsonWorkOrderResourceResponse.getAssignDateFrom()).isEqualTo(expectedJsonWorkOrderResourceResponse.getAssignDateFrom());
		assertThat(jsonWorkOrderResourceResponse.getAssignDateTo()).isEqualTo(expectedJsonWorkOrderResourceResponse.getAssignDateTo());
		assertThat(jsonWorkOrderResourceResponse.getIsActive()).isEqualTo(expectedJsonWorkOrderResourceResponse.getIsActive());
		assertThat(jsonWorkOrderResourceResponse.getResourceId()).isEqualTo(expectedJsonWorkOrderResourceResponse.getResourceId());
		assertThat(jsonWorkOrderResourceResponse.getIsAllDay()).isEqualTo(expectedJsonWorkOrderResourceResponse.getIsAllDay());
		assertThat(jsonWorkOrderResourceResponse.getDuration()).isEqualTo(expectedJsonWorkOrderResourceResponse.getDuration());
		assertThat(jsonWorkOrderResourceResponse.getDurationUnit()).isEqualTo(expectedJsonWorkOrderResourceResponse.getDurationUnit());
		assertThat(jsonWorkOrderResourceResponse.getTestFacilityGroupName()).isEqualTo(expectedJsonWorkOrderResourceResponse.getTestFacilityGroupName());
		assertThat(jsonWorkOrderResourceResponse.getExternalId()).isEqualTo(expectedJsonWorkOrderResourceResponse.getExternalId());
	}

	private void validateJsonWorkOrderObjectsUnderTestResponse(
			@NonNull final JsonWorkOrderObjectsUnderTestResponse jsonWorkOrderObjectsUnderTestResponse,
			@NonNull final JsonWorkOrderObjectsUnderTestResponse expectedJsonWorkOrderObjectsUnderTestResponse)
	{
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getNumberOfObjectsUnderTest()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getNumberOfObjectsUnderTest());
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getExternalId()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getExternalId());
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoDeliveryNote()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoDeliveryNote());
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoManufacturer()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoManufacturer());
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoObjectType()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoObjectType());
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoObjectName()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoObjectName());
		assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoObjectWhereabouts()).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoObjectWhereabouts());
	}

	@NonNull
	private String getDocSeqNumber(@NonNull final ProjectTypeId projectTypeId)
	{
		final I_C_ProjectType projectType = InterfaceWrapperHelper.load(projectTypeId, I_C_ProjectType.class);

		assertThat(projectType).isNotNull();
		assertThat(projectType.getAD_Sequence_ProjectValue_ID()).isGreaterThan(0);

		final I_AD_Sequence adSequence = InterfaceWrapperHelper.load(projectType.getAD_Sequence_ProjectValue_ID(), I_AD_Sequence.class);

		return String.valueOf(adSequence.getCurrentNext() - 1);

	}
}
