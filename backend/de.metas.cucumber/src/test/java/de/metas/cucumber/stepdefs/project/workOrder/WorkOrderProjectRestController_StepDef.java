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
import de.metas.common.rest_api.v2.JsonApiResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponses;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.project.ProjectTypeId;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.CURRENCY_CODE;
import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_CODE;
import static org.assertj.core.api.Assertions.*;

public class WorkOrderProjectRestController_StepDef
{
	private static final String ENDPOINT_API_V2_WORK_ORDER = "api/v2/project/workorder";
	private static final String NEXT_DOC_SEQ_NO_PLACEHOLDER = "nextDocNo";

	private final C_Project_StepDefData projectTable;
	private final C_Project_WO_Step_StepDefData projectWOStepTable;
	private final C_Project_WO_Resource_StepDefData woResourceTable;
	private final C_Project_WO_ObjectUnderTest_StepDefData woObjectUnderTestTable;

	private final TestContext testContext;

	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public WorkOrderProjectRestController_StepDef(
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_Project_WO_Step_StepDefData projectWOStepTable,
			@NonNull final C_Project_WO_Resource_StepDefData woResourceTable,
			@NonNull final C_Project_WO_ObjectUnderTest_StepDefData woObjectUnderTestTable,
			@NonNull final TestContext testContext)
	{
		this.projectTable = projectTable;
		this.projectWOStepTable = projectWOStepTable;
		this.woResourceTable = woResourceTable;
		this.woObjectUnderTestTable = woObjectUnderTestTable;
		this.testContext = testContext;
	}

	@And("process work order project upsert response")
	public void process_wo_project_upsert_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonWorkOrderProjectUpsertResponse workOrderProjectUpsertResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonWorkOrderProjectUpsertResponse.class);

		final Map<String, String> row = table.asMaps().get(0);

		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_C_Project woProject = InterfaceWrapperHelper.load(workOrderProjectUpsertResponse.getMetasfreshId().getValue(), I_C_Project.class);
		assertThat(woProject).isNotNull();

		projectTable.putOrReplace(projectIdentifier, woProject);

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

		final String woResourceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(woResourceIdentifier))
		{
			final List<JsonWorkOrderStepUpsertResponse> stepResponse = workOrderProjectUpsertResponse.getSteps();
			assertThat(stepResponse).isNotNull();
			assertThat(stepResponse).hasSize(1);
			final List<JsonWorkOrderResourceUpsertResponse> woResourcesResponse = stepResponse.get(0).getResources();

			assertThat(woResourcesResponse).hasSize(1);

			final I_C_Project_WO_Resource woResource = InterfaceWrapperHelper.load(woResourcesResponse.get(0).getMetasfreshId().getValue(), I_C_Project_WO_Resource.class);

			woResourceTable.putOrReplace(woResourceIdentifier, woResource);
		}

		final String woObjUnderTestIdentifiers = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Project_WO_ObjectUnderTest.COLUMNNAME_C_Project_WO_ObjectUnderTest_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(woObjUnderTestIdentifiers))
		{
			final ImmutableList<String> woObjUnderTestIdentifierList = StepDefUtil.extractIdentifiers(woObjUnderTestIdentifiers);
			assertThat(workOrderProjectUpsertResponse.getObjectsUnderTest()).isNotNull();

			final List<JsonWorkOrderObjectUnderTestUpsertResponse> objUnderTestList = workOrderProjectUpsertResponse.getObjectsUnderTest();
			assertThat(objUnderTestList).isNotNull();
			assertThat(woObjUnderTestIdentifierList.size()).isEqualTo(objUnderTestList.size());

			for (int index = 0; index < woObjUnderTestIdentifierList.size(); index++)
			{
				final I_C_Project_WO_ObjectUnderTest stepRecord = InterfaceWrapperHelper.load(objUnderTestList.get(index).getMetasfreshId().getValue(), I_C_Project_WO_ObjectUnderTest.class);

				woObjectUnderTestTable.putOrReplace(woObjUnderTestIdentifierList.get(index), stepRecord);
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

		final I_C_Project project = projectTable.get(projectIdentifier);
		assertThat(project).isNotNull();

		testContext.setEndpointPath(ENDPOINT_API_V2_WORK_ORDER + "/" + project.getC_Project_ID());
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

		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(objectsUnderTest).isNotNull();
		softly.assertThat(expectedObjectsUnderTest).isNotNull();

		final int objectsUnderTestSize = objectsUnderTest.size();
		softly.assertThat(objectsUnderTestSize).isEqualByComparingTo(expectedObjectsUnderTest.size());

		for (int i = 0; i < objectsUnderTestSize; i++)
		{
			validateJsonWorkOrderObjectsUnderTestResponse(objectsUnderTest.get(i), expectedObjectsUnderTest.get(i));
		}

		softly.assertAll();
	}

	private void validateJsonWorkOrderStepResponseList(
			@NonNull final JsonWorkOrderProjectResponse jsonWorkOrderProjectResponse,
			@NonNull final JsonWorkOrderProjectResponse expectedJsonWorkOrderProjectResponse)
	{
		final List<JsonWorkOrderStepResponse> projectSteps = jsonWorkOrderProjectResponse.getSteps();
		final List<JsonWorkOrderStepResponse> expectedSteps = expectedJsonWorkOrderProjectResponse.getSteps();

		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(projectSteps).isNotNull();
		softly.assertThat(expectedSteps).isNotNull();

		final int projectStepsSize = projectSteps.size();
		softly.assertThat(projectStepsSize).isEqualByComparingTo(expectedSteps.size());

		for (int i = 0; i < projectStepsSize; i++)
		{
			validateJsonWorkOrderStepResponse(projectSteps.get(i), expectedSteps.get(i));
		}

		softly.assertAll();
	}

	private void validateJsonWorkOrderResourceResponseList(
			@NonNull final JsonWorkOrderStepResponse jsonWorkOrderStepResponse,
			@NonNull final JsonWorkOrderStepResponse expectedJsonWorkOrderStepResponse)
	{
		final List<JsonWorkOrderResourceResponse> projectStepResources = jsonWorkOrderStepResponse.getResources();
		final List<JsonWorkOrderResourceResponse> expectedStepResources = expectedJsonWorkOrderStepResponse.getResources();

		final SoftAssertions softly = new SoftAssertions();

		if (projectStepResources == null)
		{
			softly.assertThat(expectedStepResources).isNull();
		}
		else
		{
			softly.assertThat(projectStepResources).isNotNull();
			softly.assertThat(expectedStepResources).isNotNull();

			final int projectStepResourcesSize = projectStepResources.size();
			softly.assertThat(projectStepResourcesSize).isEqualByComparingTo(expectedStepResources.size());

			for (int i = 0; i < projectStepResourcesSize; i++)
			{
				validateJsonWorkOrderResourceResponse(projectStepResources.get(i), expectedStepResources.get(i));
			}
		}

		softly.assertAll();
	}

	private void validateJsonWorkOrderProjectResponse(
			@NonNull final JsonWorkOrderProjectResponse jsonWorkOrderProjectResponse,
			@NonNull final JsonWorkOrderProjectResponse expectedJsonWorkOrderProjectResponse)
	{
		final SoftAssertions softly = new SoftAssertions();

		if (!expectedJsonWorkOrderProjectResponse.getValue().equalsIgnoreCase(NEXT_DOC_SEQ_NO_PLACEHOLDER))
		{
			softly.assertThat(jsonWorkOrderProjectResponse.getValue()).as(I_C_Project.COLUMNNAME_Value).isEqualTo(expectedJsonWorkOrderProjectResponse.getValue());
		}
		else
		{
			softly.assertThat(jsonWorkOrderProjectResponse.getValue()).as(I_C_Project.COLUMNNAME_Value)
					.isEqualTo(getDocSeqNumber(ProjectTypeId.ofRepoId(jsonWorkOrderProjectResponse.getProjectTypeId().getValue())));
		}

		if (!expectedJsonWorkOrderProjectResponse.getName().equalsIgnoreCase(NEXT_DOC_SEQ_NO_PLACEHOLDER))
		{
			softly.assertThat(jsonWorkOrderProjectResponse.getName()).as(I_C_Project.COLUMNNAME_Name).isEqualTo(expectedJsonWorkOrderProjectResponse.getName());
		}
		else
		{
			softly.assertThat(jsonWorkOrderProjectResponse.getName()).as(I_C_Project.COLUMNNAME_Name)
					.isEqualTo(getDocSeqNumber(ProjectTypeId.ofRepoId(jsonWorkOrderProjectResponse.getProjectTypeId().getValue())));
		}

		softly.assertThat(jsonWorkOrderProjectResponse.getProjectTypeId()).as(I_C_Project.COLUMNNAME_C_ProjectType_ID).isEqualTo(expectedJsonWorkOrderProjectResponse.getProjectTypeId());
		softly.assertThat(jsonWorkOrderProjectResponse.getPriceListVersionId()).as(I_C_Project.COLUMNNAME_M_PriceList_Version_ID).isEqualTo(expectedJsonWorkOrderProjectResponse.getPriceListVersionId());
		softly.assertThat(jsonWorkOrderProjectResponse.getCurrencyCode()).as(CURRENCY_CODE).isEqualTo(expectedJsonWorkOrderProjectResponse.getCurrencyCode());
		softly.assertThat(jsonWorkOrderProjectResponse.getSalesRepId()).as(I_C_Project.COLUMNNAME_SalesRep_ID).isEqualTo(expectedJsonWorkOrderProjectResponse.getSalesRepId());
		softly.assertThat(jsonWorkOrderProjectResponse.getDescription()).as(I_C_Project.COLUMNNAME_Description).isEqualTo(expectedJsonWorkOrderProjectResponse.getDescription());
		softly.assertThat(jsonWorkOrderProjectResponse.getDateContract()).as(I_C_Project.COLUMNNAME_DateContract).isEqualTo(expectedJsonWorkOrderProjectResponse.getDateContract());
		softly.assertThat(jsonWorkOrderProjectResponse.getDateFinish()).as(I_C_Project.COLUMNNAME_DateFinish).isEqualTo(expectedJsonWorkOrderProjectResponse.getDateFinish());
		softly.assertThat(jsonWorkOrderProjectResponse.getBpartnerId()).as(I_C_Project.COLUMNNAME_C_BPartner_ID).isEqualTo(expectedJsonWorkOrderProjectResponse.getBpartnerId());
		softly.assertThat(jsonWorkOrderProjectResponse.getProjectReferenceExt()).as(I_C_Project.COLUMNNAME_C_Project_Reference_Ext).isEqualTo(expectedJsonWorkOrderProjectResponse.getProjectReferenceExt());
		softly.assertThat(jsonWorkOrderProjectResponse.getProjectParentId()).as(I_C_Project.COLUMNNAME_C_Project_Parent_ID).isEqualTo(expectedJsonWorkOrderProjectResponse.getProjectParentId());
		softly.assertThat(jsonWorkOrderProjectResponse.getOrgCode()).as(ORG_CODE).isEqualTo(expectedJsonWorkOrderProjectResponse.getOrgCode());
		softly.assertThat(jsonWorkOrderProjectResponse.getIsActive()).as(I_C_Project.COLUMNNAME_IsActive).isEqualTo(expectedJsonWorkOrderProjectResponse.getIsActive());
		softly.assertThat(jsonWorkOrderProjectResponse.getDateOfProvisionByBPartner()).as(I_C_Project.COLUMNNAME_DateOfProvisionByBPartner).isEqualTo(expectedJsonWorkOrderProjectResponse.getDateOfProvisionByBPartner());
		softly.assertThat(jsonWorkOrderProjectResponse.getWoOwner()).as(I_C_Project.COLUMNNAME_WOOwner).isEqualTo(expectedJsonWorkOrderProjectResponse.getWoOwner());
		softly.assertThat(jsonWorkOrderProjectResponse.getPoReference()).as(I_C_Project.COLUMNNAME_POReference).isEqualTo(expectedJsonWorkOrderProjectResponse.getPoReference());
		softly.assertThat(jsonWorkOrderProjectResponse.getBpartnerDepartment()).as(I_C_Project.COLUMNNAME_BPartnerDepartment).isEqualTo(expectedJsonWorkOrderProjectResponse.getBpartnerDepartment());
		softly.assertThat(jsonWorkOrderProjectResponse.getBpartnerTargetDate()).as(I_C_Project.COLUMNNAME_BPartnerTargetDate).isEqualTo(expectedJsonWorkOrderProjectResponse.getBpartnerTargetDate());
		softly.assertThat(jsonWorkOrderProjectResponse.getWoProjectCreatedDate()).as(I_C_Project.COLUMNNAME_WOProjectCreatedDate).isEqualTo(expectedJsonWorkOrderProjectResponse.getWoProjectCreatedDate());

		if (expectedJsonWorkOrderProjectResponse.getSpecialistConsultantId() != null)
		{
			softly.assertThat(jsonWorkOrderProjectResponse.getSpecialistConsultantId()).as(I_C_Project.COLUMNNAME_Specialist_Consultant_ID).isEqualTo(expectedJsonWorkOrderProjectResponse.getSpecialistConsultantId());
		}

		if (Check.isNotBlank(expectedJsonWorkOrderProjectResponse.getInternalPriority()))
		{
			softly.assertThat(jsonWorkOrderProjectResponse.getInternalPriority()).as(I_C_Project.COLUMNNAME_InternalPriority).isEqualTo(expectedJsonWorkOrderProjectResponse.getInternalPriority());
		}

		validateJsonWorkOrderStepResponseList(jsonWorkOrderProjectResponse, expectedJsonWorkOrderProjectResponse);
		validateJsonWorkOrderObjectsUnderTestResponseList(jsonWorkOrderProjectResponse, expectedJsonWorkOrderProjectResponse);

		softly.assertAll();
	}

	private void validateJsonWorkOrderStepResponse(
			@NonNull final JsonWorkOrderStepResponse jsonWorkOrderStepResponse,
			@NonNull final JsonWorkOrderStepResponse expectedJsonWorkOrderStepResponse)
	{
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(jsonWorkOrderStepResponse.getName()).as(I_C_Project_WO_Step.COLUMNNAME_Name).isEqualTo(expectedJsonWorkOrderStepResponse.getName());
		softly.assertThat(jsonWorkOrderStepResponse.getDescription()).as(I_C_Project_WO_Step.COLUMNNAME_Description).isEqualTo(expectedJsonWorkOrderStepResponse.getDescription());
		softly.assertThat(jsonWorkOrderStepResponse.getSeqNo()).as(I_C_Project_WO_Step.COLUMNNAME_SeqNo).isEqualTo(expectedJsonWorkOrderStepResponse.getSeqNo());
		softly.assertThat(jsonWorkOrderStepResponse.getDateStart()).as(I_C_Project_WO_Step.COLUMNNAME_DateStart).isEqualTo(expectedJsonWorkOrderStepResponse.getDateStart());
		softly.assertThat(jsonWorkOrderStepResponse.getDateEnd()).as(I_C_Project_WO_Step.COLUMNNAME_DateEnd).isEqualTo(expectedJsonWorkOrderStepResponse.getDateEnd());
		softly.assertThat(jsonWorkOrderStepResponse.getExternalId()).as(I_C_Project_WO_Step.COLUMNNAME_ExternalId).isEqualTo(expectedJsonWorkOrderStepResponse.getExternalId());
		softly.assertThat(jsonWorkOrderStepResponse.getWoPartialReportDate()).as(I_C_Project_WO_Step.COLUMNNAME_WOPartialReportDate).isEqualTo(expectedJsonWorkOrderStepResponse.getWoPartialReportDate());
		softly.assertThat(jsonWorkOrderStepResponse.getWoPlannedResourceDurationHours()).as(I_C_Project_WO_Step.COLUMNNAME_WOPlannedResourceDurationHours).isEqualTo(expectedJsonWorkOrderStepResponse.getWoPlannedResourceDurationHours());
		softly.assertThat(jsonWorkOrderStepResponse.getDeliveryDate()).as(I_C_Project_WO_Step.COLUMNNAME_WODeliveryDate).isEqualTo(expectedJsonWorkOrderStepResponse.getDeliveryDate());
		softly.assertThat(jsonWorkOrderStepResponse.getWoTargetStartDate()).as(I_C_Project_WO_Step.COLUMNNAME_WOTargetStartDate).isEqualTo(expectedJsonWorkOrderStepResponse.getWoTargetStartDate());
		softly.assertThat(jsonWorkOrderStepResponse.getWoTargetEndDate()).as(I_C_Project_WO_Step.COLUMNNAME_WOTargetEndDate).isEqualTo(expectedJsonWorkOrderStepResponse.getWoTargetEndDate());
		softly.assertThat(jsonWorkOrderStepResponse.getWoPlannedPersonDurationHours()).as(I_C_Project_WO_Step.COLUMNNAME_WOPlannedPersonDurationHours).isEqualTo(expectedJsonWorkOrderStepResponse.getWoPlannedPersonDurationHours());
		softly.assertThat(jsonWorkOrderStepResponse.getWoStepStatus()).as(I_C_Project_WO_Step.COLUMNNAME_WOStepStatus).isEqualTo(expectedJsonWorkOrderStepResponse.getWoStepStatus());
		softly.assertThat(jsonWorkOrderStepResponse.getWoFindingsReleasedDate()).as(I_C_Project_WO_Step.COLUMNNAME_WOFindingsReleasedDate).isEqualTo(expectedJsonWorkOrderStepResponse.getWoFindingsReleasedDate());
		softly.assertThat(jsonWorkOrderStepResponse.getWoFindingsCreatedDate()).as(I_C_Project_WO_Step.COLUMNNAME_WOFindingsCreatedDate).isEqualTo(expectedJsonWorkOrderStepResponse.getWoFindingsCreatedDate());

		validateJsonWorkOrderResourceResponseList(jsonWorkOrderStepResponse, expectedJsonWorkOrderStepResponse);

		softly.assertAll();
	}

	private void validateJsonWorkOrderResourceResponse(
			@NonNull final JsonWorkOrderResourceResponse jsonWorkOrderResourceResponse,
			@NonNull final JsonWorkOrderResourceResponse expectedJsonWorkOrderResourceResponse)
	{
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(jsonWorkOrderResourceResponse.getAssignDateFrom()).as(I_C_Project_WO_Resource.COLUMNNAME_AssignDateFrom).isEqualTo(expectedJsonWorkOrderResourceResponse.getAssignDateFrom());
		softly.assertThat(jsonWorkOrderResourceResponse.getAssignDateTo()).as(I_C_Project_WO_Resource.COLUMNNAME_AssignDateTo).isEqualTo(expectedJsonWorkOrderResourceResponse.getAssignDateTo());
		softly.assertThat(jsonWorkOrderResourceResponse.getIsActive()).as(I_C_Project_WO_Resource.COLUMNNAME_IsActive).isEqualTo(expectedJsonWorkOrderResourceResponse.getIsActive());
		softly.assertThat(jsonWorkOrderResourceResponse.getResourceId()).as(I_C_Project_WO_Resource.COLUMNNAME_S_Resource_ID).isEqualTo(expectedJsonWorkOrderResourceResponse.getResourceId());
		softly.assertThat(jsonWorkOrderResourceResponse.getIsAllDay()).as(I_C_Project_WO_Resource.COLUMNNAME_IsAllDay).isEqualTo(expectedJsonWorkOrderResourceResponse.getIsAllDay());
		softly.assertThat(jsonWorkOrderResourceResponse.getDuration()).as(I_C_Project_WO_Resource.COLUMNNAME_Duration).isEqualTo(expectedJsonWorkOrderResourceResponse.getDuration());
		softly.assertThat(jsonWorkOrderResourceResponse.getDurationUnit()).as(I_C_Project_WO_Resource.COLUMNNAME_DurationUnit).isEqualTo(expectedJsonWorkOrderResourceResponse.getDurationUnit());
		softly.assertThat(jsonWorkOrderResourceResponse.getTestFacilityGroupName()).as(I_C_Project_WO_Resource.COLUMNNAME_WOTestFacilityGroupName).isEqualTo(expectedJsonWorkOrderResourceResponse.getTestFacilityGroupName());
		softly.assertThat(jsonWorkOrderResourceResponse.getExternalId()).as(I_C_Project_WO_Resource.COLUMNNAME_ExternalId).isEqualTo(expectedJsonWorkOrderResourceResponse.getExternalId());

		softly.assertAll();
	}

	private void validateJsonWorkOrderObjectsUnderTestResponse(
			@NonNull final JsonWorkOrderObjectsUnderTestResponse jsonWorkOrderObjectsUnderTestResponse,
			@NonNull final JsonWorkOrderObjectsUnderTestResponse expectedJsonWorkOrderObjectsUnderTestResponse)
	{
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getNumberOfObjectsUnderTest()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_NumberOfObjectsUnderTest).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getNumberOfObjectsUnderTest());
		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getExternalId()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_ExternalId).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getExternalId());
		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoDeliveryNote()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_WODeliveryNote).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoDeliveryNote());
		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoManufacturer()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_WOManufacturer).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoManufacturer());
		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoObjectType()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_WOObjectType).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoObjectType());
		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoObjectName()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_WOObjectName).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoObjectName());
		softly.assertThat(jsonWorkOrderObjectsUnderTestResponse.getWoObjectWhereabouts()).as(I_C_Project_WO_ObjectUnderTest.COLUMNNAME_WOObjectWhereabouts).isEqualTo(expectedJsonWorkOrderObjectsUnderTestResponse.getWoObjectWhereabouts());

		softly.assertAll();
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
