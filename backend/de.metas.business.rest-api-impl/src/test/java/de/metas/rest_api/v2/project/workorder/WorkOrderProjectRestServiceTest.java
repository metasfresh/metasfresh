/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.project.workorder;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonResponseUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonDurationUnit;
import de.metas.common.rest_api.v2.project.workorder.JsonWOStepStatus;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectUnderTestUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderObjectsUnderTestResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertItemRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertResponse;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.service.ProjectService;
import de.metas.project.workorder.project.WOProjectRepository;
import de.metas.project.workorder.resource.WOProjectResourceRepository;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.project.workorder.step.WOProjectStepRepository;
import de.metas.project.workorder.undertest.WorkOrderProjectObjectUnderTestRepository;
import de.metas.resource.ResourceService;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_C_Project_WO_ObjectUnderTest;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.X_C_ProjectType.PROJECTCATEGORY_WorkOrderJob;
import static org.mockito.ArgumentMatchers.any;

class WorkOrderProjectRestServiceTest
{
	private final PriceListId priceListId = PriceListId.ofRepoId(111);
	private final String nextValue = "123";

	private WorkOrderProjectRestService workOrderProjectRestService;

	private I_C_ProjectType projectType;
	private String orgValue;
	private I_S_Resource resource;
	private PriceListVersionId priceListVersionId;
	private CurrencyId currencyId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone();
		orgValue = Services.get(IOrgDAO.class).retrieveOrgValue(orgId);

		projectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		projectType.setProjectCategory(PROJECTCATEGORY_WorkOrderJob);
		projectType.setName("projectTypeName");
		InterfaceWrapperHelper.save(projectType);

		final I_S_ResourceType resourceType = InterfaceWrapperHelper.newInstance(I_S_ResourceType.class);
		InterfaceWrapperHelper.save(resourceType);

		resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		resource.setS_ResourceType_ID(resourceType.getS_ResourceType_ID());
		resource.setInternalName("internalName");
		resource.setValue("resourceValue");

		InterfaceWrapperHelper.save(resource);

		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_M_PriceList_Version priceListVersion = createPriceListVersion(priceListId, currencyId);
		priceListVersionId = PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());

		final WOProjectRepository workOrderProjectRepository = new WOProjectRepository();

		final ProjectService mockProjectService = Mockito.mock(ProjectService.class);
		Mockito.when(mockProjectService.getNextProjectValue(any())).thenReturn(nextValue);

		final WOProjectStepRepository workOrderProjectStepRepository = new WOProjectStepRepository();
		final WorkOrderProjectObjectUnderTestRepository workOrderProjectObjectUnderTestRepository = new WorkOrderProjectObjectUnderTestRepository();

		final WorkOrderProjectObjectUnderTestRestService workOrderProjectObjectUnderTestRestService = new WorkOrderProjectObjectUnderTestRestService(workOrderProjectRepository, workOrderProjectObjectUnderTestRepository);
		final WOProjectResourceRepository workOrderProjectResourceRepository = new WOProjectResourceRepository();
		final ResourceService resourceService = ResourceService.newInstanceForJUnitTesting();

		final WOProjectStepRepository woProjectStepRepository = new WOProjectStepRepository();

		final WorkOrderProjectResourceRestService workOrderProjectResourceRestService = new WorkOrderProjectResourceRestService(
				workOrderProjectStepRepository,
				workOrderProjectResourceRepository,
				resourceService);

		final WorkOrderProjectStepRestService workOrderProjectStepRestService = new WorkOrderProjectStepRestService(
				workOrderProjectRepository,
				workOrderProjectStepRepository,
				workOrderProjectResourceRestService,
				woProjectStepRepository);

		final WorkOrderMapper workOrderMapper = new WorkOrderMapper(mockProjectService);

		workOrderProjectRestService = new WorkOrderProjectRestService(workOrderProjectRepository,
																	  new ProjectTypeRepository(),
																	  workOrderMapper,
																	  workOrderProjectObjectUnderTestRestService,
																	  workOrderProjectStepRestService);
	}

	@Test
	void createWOProject_withAllFieldsSet()
	{
		// JsonWorkOrderProjectUpsertRequest
		final String projectExternalId = "projectReferenceExt";
		final String projectValue = "projectValue";
		final String projectName = "projectName";
		final JsonMetasfreshId salesRepId = JsonMetasfreshId.of(UserId.METASFRESH.getRepoId());
		final String projectDescription = "projectDescription";
		final LocalDate dateContract = LocalDate.parse("2022-08-10");
		final LocalDate dateFinish = LocalDate.parse("2022-08-21");
		final JsonMetasfreshId bpartnerId = JsonMetasfreshId.of(BusinessTestHelper.createBPartner("test").getC_BPartner_ID());
		final String bpartnerDepartment = "bpartnerDepartment";
		final String woOwner = "woOwner";
		final String poReference = "POReference";
		final LocalDate bpartnerTargetDate = LocalDate.parse("2022-08-20");
		final LocalDate woProjectCreatedDate = LocalDate.parse("2022-07-15");
		final LocalDate dateOfProvisionByBPartner = LocalDate.parse("2022-08-15");

		final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
		projectRequest.setIdentifier("ext-" + projectExternalId);
		projectRequest.setValue(projectValue);
		projectRequest.setName(projectName);
		projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		projectRequest.setPriceListVersionId(JsonMetasfreshId.of(priceListVersionId.getRepoId()));
		projectRequest.setCurrencyCode(CurrencyCode.EUR.toThreeLetterCode());
		projectRequest.setSalesRepId(salesRepId);
		projectRequest.setDescription(projectDescription);
		projectRequest.setDateContract(dateContract);
		projectRequest.setDateFinish(dateFinish);
		projectRequest.setBpartnerId(bpartnerId);
		projectRequest.setProjectReferenceExt(projectExternalId);
		projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		projectRequest.setBpartnerDepartment(bpartnerDepartment);
		projectRequest.setWoOwner(woOwner);
		projectRequest.setPoReference(poReference);
		projectRequest.setBpartnerTargetDate(bpartnerTargetDate);
		projectRequest.setWoProjectCreatedDate(woProjectCreatedDate);
		projectRequest.setIsActive(false);
		projectRequest.setOrgCode(orgValue);
		projectRequest.setDateOfProvisionByBPartner(dateOfProvisionByBPartner);

		// JsonWorkOrderStepUpsertRequest
		final String stepIdentifier = "1111";
		final String stepName = "stepName";
		final String stepDescription = "stepDescription";
		final Integer stepSeqNo = 40;
		final LocalDate dateStart = LocalDate.parse("2022-07-22");
		final LocalDate dateEnd = LocalDate.parse("2022-08-02");
		final LocalDate woPartialReportDate = LocalDate.parse("2022-08-01");
		final Integer woPlannedResourceDurationHours = 3;
		final LocalDate deliveryDate = LocalDate.parse("2022-08-05");
		final LocalDate woTargetStartDate = LocalDate.parse("2022-07-21");
		final LocalDate woTargetEndDate = LocalDate.parse("2022-07-31");
		final Integer woPlannedPersonDurationHours = 20;
		final JsonWOStepStatus woStepStatus = JsonWOStepStatus.CANCELED;
		final LocalDate woFindingsReleasedDate = LocalDate.parse("2022-08-03");
		final LocalDate woFindingsCreatedDate = LocalDate.parse("2022-08-01");

		final JsonWorkOrderStepUpsertItemRequest stepRequest = new JsonWorkOrderStepUpsertItemRequest();
		stepRequest.setIdentifier("ext-" + stepIdentifier);
		stepRequest.setName(stepName);
		stepRequest.setDescription(stepDescription);
		stepRequest.setSeqNo(stepSeqNo);
		stepRequest.setDateStart(dateStart);
		stepRequest.setDateEnd(dateEnd);
		stepRequest.setWoPartialReportDate(woPartialReportDate);
		stepRequest.setWoPlannedResourceDurationHours(woPlannedResourceDurationHours);
		stepRequest.setDeliveryDate(deliveryDate);
		stepRequest.setWoTargetStartDate(woTargetStartDate);
		stepRequest.setWoTargetEndDate(woTargetEndDate);
		stepRequest.setWoPlannedPersonDurationHours(woPlannedPersonDurationHours);
		stepRequest.setWoStepStatus(woStepStatus);
		stepRequest.setWoFindingsReleasedDate(woFindingsReleasedDate);
		stepRequest.setWoFindingsCreatedDate(woFindingsCreatedDate);
		stepRequest.setExternalId(stepIdentifier);

		projectRequest.setSteps(ImmutableList.of(stepRequest));

		// JsonWorkOrderResourceUpsertRequest
		final String resourceInternalName = resource.getInternalName();
		final LocalDate assignDateFrom = LocalDate.parse("2022-08-07");
		final LocalDate assignDateTo = LocalDate.parse("2022-08-08");
		final String testFacilityGroupName = "testFacilityGroupName";

		final JsonWorkOrderResourceUpsertItemRequest resourceRequest = new JsonWorkOrderResourceUpsertItemRequest();
		resourceRequest.setResourceIdentifier("int-" + resourceInternalName);
		resourceRequest.setAssignDateFrom(assignDateFrom);
		resourceRequest.setAssignDateTo(assignDateTo);
		resourceRequest.setActive(false);
		resourceRequest.setIsAllDay(true);
		resourceRequest.setDuration(BigDecimal.TEN);
		resourceRequest.setDurationUnit(JsonDurationUnit.Day);
		resourceRequest.setTestFacilityGroupName(testFacilityGroupName);
		resourceRequest.setExternalId(stepIdentifier);

		stepRequest.setResources(ImmutableList.of(resourceRequest));

		// JsonWorkOrderObjectUnderTestUpsertRequest
		final String objectUnderTestIdentifier = "3333";
		final Integer numberOfObjectsUnderTest = 100;
		final String woDeliveryNote = "woDeliveryNote";
		final String woManufacturer = "woManufacturer";
		final String woObjectType = "woObjectType";
		final String woObjectName = "woObjectName";
		final String woObjectWhereabouts = "woObjectWhereabouts";

		final JsonWorkOrderObjectUnderTestUpsertItemRequest objectUnderTestUpsertRequest = new JsonWorkOrderObjectUnderTestUpsertItemRequest();
		objectUnderTestUpsertRequest.setNumberOfObjectsUnderTest(numberOfObjectsUnderTest);
		objectUnderTestUpsertRequest.setWoDeliveryNote(woDeliveryNote);
		objectUnderTestUpsertRequest.setWoManufacturer(woManufacturer);
		objectUnderTestUpsertRequest.setWoObjectType(woObjectType);
		objectUnderTestUpsertRequest.setWoObjectName(woObjectName);
		objectUnderTestUpsertRequest.setWoObjectWhereabouts(woObjectWhereabouts);
		objectUnderTestUpsertRequest.setExternalId(objectUnderTestIdentifier);
		objectUnderTestUpsertRequest.setIdentifier("ext-" + objectUnderTestIdentifier);

		projectRequest.setObjectsUnderTest(ImmutableList.of(objectUnderTestUpsertRequest));

		final JsonWorkOrderProjectUpsertResponse responseBody = workOrderProjectRestService.upsertWOProject(projectRequest);
		assertThat(responseBody).isNotNull();

		final JsonWorkOrderProjectResponse data = workOrderProjectRestService.getWorkOrderProjectById(ProjectId.ofRepoId(responseBody.getMetasfreshId().getValue()));
		assertThat(data.getProjectId()).isNotNull();

		assertThat(data.getProjectId()).isEqualTo(responseBody.getMetasfreshId());
		assertThat(data.getValue()).isEqualTo(projectValue);
		assertThat(data.getName()).isEqualTo(projectName);
		assertThat(data.getProjectTypeId()).isEqualTo(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		assertThat(data.getPriceListVersionId()).isEqualTo(JsonMetasfreshId.of(priceListVersionId.getRepoId()));
		assertThat(data.getCurrencyCode()).isEqualTo(CurrencyCode.EUR.toThreeLetterCode());
		assertThat(data.getSalesRepId()).isEqualTo(salesRepId);
		assertThat(data.getDescription()).isEqualTo(projectDescription);
		assertThat(data.getDateContract()).isEqualTo(dateContract);
		assertThat(data.getDateFinish()).isEqualTo(dateFinish);
		assertThat(data.getBpartnerId()).isEqualTo(bpartnerId);
		assertThat(data.getProjectReferenceExt()).isEqualTo(projectExternalId);
		assertThat(data.getBpartnerDepartment()).isEqualTo(bpartnerDepartment);
		assertThat(data.getWoOwner()).isEqualTo(woOwner);
		assertThat(data.getPoReference()).isEqualTo(poReference);
		assertThat(data.getBpartnerTargetDate()).isEqualTo(bpartnerTargetDate);
		assertThat(data.getWoProjectCreatedDate()).isEqualTo(woProjectCreatedDate);
		assertThat(data.getIsActive()).isEqualTo(false);
		assertThat(data.getOrgCode()).isEqualTo(orgValue);
		assertThat(data.getDateOfProvisionByBPartner()).isEqualTo(dateOfProvisionByBPartner);
		assertThat(data.getProjectParentId()).isNull();

		assertThat(data.getSteps()).isNotNull();
		assertThat(data.getSteps().size()).isEqualTo(1);

		final JsonWorkOrderStepResponse stepResponse = data.getSteps().get(0);

		assertThat(stepResponse.getProjectId()).isEqualTo(data.getProjectId());
		assertThat(stepResponse.getName()).isEqualTo(stepName);
		assertThat(stepResponse.getDescription()).isEqualTo(stepDescription);
		assertThat(stepResponse.getSeqNo()).isEqualTo(stepSeqNo);
		assertThat(stepResponse.getDateStart()).isEqualTo(dateStart);
		assertThat(stepResponse.getDateEnd()).isEqualTo(dateEnd);
		assertThat(stepResponse.getWoPartialReportDate()).isEqualTo(woPartialReportDate);
		assertThat(stepResponse.getWoPlannedResourceDurationHours()).isEqualTo(woPlannedResourceDurationHours);
		assertThat(stepResponse.getDeliveryDate()).isEqualTo(deliveryDate);
		assertThat(stepResponse.getWoTargetStartDate()).isEqualTo(woTargetStartDate);
		assertThat(stepResponse.getWoTargetEndDate()).isEqualTo(woTargetEndDate);
		assertThat(stepResponse.getWoPlannedPersonDurationHours()).isEqualTo(woPlannedPersonDurationHours);
		assertThat(stepResponse.getWoStepStatus()).isEqualTo(woStepStatus);
		assertThat(stepResponse.getWoFindingsReleasedDate()).isEqualTo(woFindingsReleasedDate);
		assertThat(stepResponse.getWoFindingsCreatedDate()).isEqualTo(woFindingsCreatedDate);
		assertThat(stepResponse.getExternalId()).isEqualTo(stepIdentifier);

		assertThat(stepResponse.getResources()).isNotNull();
		assertThat(stepResponse.getResources().size()).isEqualTo(1);

		final JsonWorkOrderResourceResponse resourceResponse = stepResponse.getResources().get(0);

		assertThat(resourceResponse.getResourceId()).isEqualTo(JsonMetasfreshId.of(resource.getS_Resource_ID()));
		assertThat(resourceInternalName).isEqualTo(resource.getInternalName());
		assertThat(resourceResponse.getAssignDateFrom()).isEqualTo(assignDateFrom);
		assertThat(resourceResponse.getAssignDateTo()).isEqualTo(assignDateTo);
		assertThat(resourceResponse.getIsActive()).isEqualTo(false);
		assertThat(resourceResponse.getIsAllDay()).isEqualTo(true);
		assertThat(resourceResponse.getDuration()).isEqualTo(BigDecimal.TEN);
		assertThat(resourceResponse.getDurationUnit()).isEqualTo(JsonDurationUnit.Day);
		assertThat(resourceResponse.getTestFacilityGroupName()).isEqualTo(testFacilityGroupName);
		assertThat(resourceResponse.getExternalId()).isEqualTo(stepIdentifier);

		assertThat(data.getObjectsUnderTest()).isNotNull();
		assertThat(data.getObjectsUnderTest().size()).isEqualTo(1);

		final JsonWorkOrderObjectsUnderTestResponse objectsUnderTestResponse = data.getObjectsUnderTest().get(0);

		assertThat(objectsUnderTestResponse.getExternalId()).isEqualTo(objectUnderTestIdentifier);
		assertThat(objectsUnderTestResponse.getNumberOfObjectsUnderTest()).isEqualTo(numberOfObjectsUnderTest);
		assertThat(objectsUnderTestResponse.getWoDeliveryNote()).isEqualTo(woDeliveryNote);
		assertThat(objectsUnderTestResponse.getWoManufacturer()).isEqualTo(woManufacturer);
		assertThat(objectsUnderTestResponse.getWoObjectType()).isEqualTo(woObjectType);
		assertThat(objectsUnderTestResponse.getWoObjectName()).isEqualTo(woObjectName);
		assertThat(objectsUnderTestResponse.getWoObjectWhereabouts()).isEqualTo(woObjectWhereabouts);
	}

	@Test
	void createWOProject_withNoSteps_withNoObjectsUnderTest_MandatoryFieldsOnly()
	{
		// JsonWorkOrderProjectUpsertRequest
		final String projectExternalId = "projectReferenceExt";

		final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
		projectRequest.setIdentifier("ext-" + projectExternalId);
		projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		projectRequest.setProjectReferenceExt(projectExternalId);
		projectRequest.setCurrencyCode(CurrencyCode.EUR.toThreeLetterCode());
		projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);

		//when
		final JsonWorkOrderProjectUpsertResponse responseBody = workOrderProjectRestService.upsertWOProject(projectRequest);
		assertThat(responseBody).isNotNull();

		//then
		final JsonWorkOrderProjectResponse storedProject = workOrderProjectRestService.getWorkOrderProjectById(ProjectId.ofRepoId(responseBody.getMetasfreshId().getValue()));

		//project
		assertThat(storedProject.getProjectId()).isNotNull();

		assertThat(storedProject.getProjectId()).isEqualTo(responseBody.getMetasfreshId());
		assertThat(storedProject.getValue()).isEqualTo(nextValue);
		assertThat(storedProject.getName()).isEqualTo(nextValue);
		assertThat(storedProject.getProjectTypeId()).isEqualTo(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		assertThat(storedProject.getIsActive()).isEqualTo(true);
		assertThat(storedProject.getProjectReferenceExt()).isEqualTo(projectExternalId);
		assertThat(storedProject.getOrgCode()).isEqualTo("0");
		assertThat(storedProject.getCurrencyCode()).isEqualTo(CurrencyCode.EUR.toThreeLetterCode());

		assertThat(storedProject.getPriceListVersionId()).isNull();
		assertThat(storedProject.getSalesRepId()).isNull();
		assertThat(storedProject.getDescription()).isNull();
		assertThat(storedProject.getDateContract()).isNull();
		assertThat(storedProject.getDateFinish()).isNull();
		assertThat(storedProject.getBpartnerId()).isNull();
		assertThat(storedProject.getBpartnerDepartment()).isNull();
		assertThat(storedProject.getWoOwner()).isNull();
		assertThat(storedProject.getPoReference()).isNull();
		assertThat(storedProject.getBpartnerTargetDate()).isNull();
		assertThat(storedProject.getWoProjectCreatedDate()).isNull();
		assertThat(storedProject.getDateOfProvisionByBPartner()).isNull();
		assertThat(storedProject.getProjectParentId()).isNull();

		//steps
		assertThat(storedProject.getSteps()).isNotNull();
		assertThat(storedProject.getSteps().size()).isEqualTo(0);

		//objects under test
		assertThat(storedProject.getObjectsUnderTest()).isNotNull();
		assertThat(storedProject.getObjectsUnderTest().size()).isEqualTo(0);
	}

	@Test
	void createWOProject_withSteps_WithResources_withObjectsUnderTest_MandatoryFieldsOnly()
	{
		// JsonWorkOrderProjectUpsertRequest
		final String projectExternalId = "projectReferenceExt";

		final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
		projectRequest.setIdentifier("ext-" + projectExternalId);
		projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		projectRequest.setProjectReferenceExt(projectExternalId);
		projectRequest.setCurrencyCode(CurrencyCode.EUR.toThreeLetterCode());
		projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);

		// JsonWorkOrderStepUpsertRequest
		final String stepIdentifier = "1111";
		final String stepName = "stepName";

		final LocalDate stepStartDate = LocalDate.parse("2022-07-05");
		final LocalDate stepEndDate = LocalDate.parse("2022-07-12");
		final JsonWorkOrderStepUpsertItemRequest stepRequest = new JsonWorkOrderStepUpsertItemRequest();
		stepRequest.setIdentifier("ext-" + stepIdentifier);
		stepRequest.setName(stepName);
		stepRequest.setExternalId(stepIdentifier);
		stepRequest.setDateStart(stepStartDate);
		stepRequest.setDateEnd(stepEndDate);

		projectRequest.setSteps(ImmutableList.of(stepRequest));

		// JsonWorkOrderResourceUpsertRequest
		final String resourceInternalName = resource.getInternalName();
		final LocalDate assignDateFrom = LocalDate.parse("2022-08-07");
		final LocalDate assignDateTo = LocalDate.parse("2022-08-08");

		final JsonWorkOrderResourceUpsertItemRequest resourceRequest = new JsonWorkOrderResourceUpsertItemRequest();
		resourceRequest.setResourceIdentifier("int-" + resourceInternalName);
		resourceRequest.setAssignDateFrom(assignDateFrom);
		resourceRequest.setAssignDateTo(assignDateTo);

		stepRequest.setResources(ImmutableList.of(resourceRequest));

		// JsonWorkOrderObjectUnderTestUpsertRequest
		final String objectUnderTestIdentifier = "3333";
		final Integer numberOfObjectsUnderTest = 100;

		final JsonWorkOrderObjectUnderTestUpsertItemRequest objectUnderTestUpsertRequest = new JsonWorkOrderObjectUnderTestUpsertItemRequest();
		objectUnderTestUpsertRequest.setNumberOfObjectsUnderTest(numberOfObjectsUnderTest);
		objectUnderTestUpsertRequest.setExternalId(objectUnderTestIdentifier);
		objectUnderTestUpsertRequest.setIdentifier("ext-" + objectUnderTestIdentifier);

		projectRequest.setObjectsUnderTest(ImmutableList.of(objectUnderTestUpsertRequest));

		//when
		final JsonWorkOrderProjectUpsertResponse responseBody = workOrderProjectRestService.upsertWOProject(projectRequest);
		assertThat(responseBody).isNotNull();

		//then
		final JsonWorkOrderProjectResponse storedProject = workOrderProjectRestService.getWorkOrderProjectById(ProjectId.ofRepoId(responseBody.getMetasfreshId().getValue()));

		//project
		assertThat(storedProject.getProjectId()).isNotNull();

		assertThat(storedProject.getProjectId()).isEqualTo(responseBody.getMetasfreshId());
		assertThat(storedProject.getValue()).isEqualTo(nextValue);
		assertThat(storedProject.getName()).isEqualTo(nextValue);
		assertThat(storedProject.getProjectTypeId()).isEqualTo(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		assertThat(storedProject.getIsActive()).isEqualTo(true);
		assertThat(storedProject.getProjectReferenceExt()).isEqualTo(projectExternalId);
		assertThat(storedProject.getOrgCode()).isEqualTo("0");
		assertThat(storedProject.getCurrencyCode()).isEqualTo(CurrencyCode.EUR.toThreeLetterCode());

		assertThat(storedProject.getPriceListVersionId()).isNull();
		assertThat(storedProject.getSalesRepId()).isNull();
		assertThat(storedProject.getDescription()).isNull();
		assertThat(storedProject.getDateContract()).isNull();
		assertThat(storedProject.getDateFinish()).isNull();
		assertThat(storedProject.getBpartnerId()).isNull();
		assertThat(storedProject.getBpartnerDepartment()).isNull();
		assertThat(storedProject.getWoOwner()).isNull();
		assertThat(storedProject.getPoReference()).isNull();
		assertThat(storedProject.getBpartnerTargetDate()).isNull();
		assertThat(storedProject.getWoProjectCreatedDate()).isNull();
		assertThat(storedProject.getDateOfProvisionByBPartner()).isNull();
		assertThat(storedProject.getProjectParentId()).isNull();

		//steps
		assertThat(storedProject.getSteps()).isNotNull();
		assertThat(storedProject.getSteps().size()).isEqualTo(1);

		final JsonWorkOrderStepResponse stepResponse = storedProject.getSteps().get(0);

		assertThat(stepResponse.getStepId()).isEqualTo(responseBody.getSteps().get(0).getMetasfreshId());
		assertThat(stepResponse.getExternalId()).isEqualTo(stepIdentifier);
		assertThat(stepResponse.getProjectId()).isEqualTo(storedProject.getProjectId());
		assertThat(stepResponse.getSeqNo()).isEqualTo(10);
		assertThat(stepResponse.getName()).isEqualTo(stepName);
		assertThat(stepResponse.getWoPlannedResourceDurationHours()).isEqualTo(0);
		assertThat(stepResponse.getWoPlannedPersonDurationHours()).isEqualTo(0);
		assertThat(stepResponse.getDateEnd()).isEqualTo(stepEndDate);
		assertThat(stepResponse.getDateStart()).isEqualTo(stepStartDate);

		assertThat(stepResponse.getDescription()).isNull();
		assertThat(stepResponse.getWoPartialReportDate()).isNull();
		assertThat(stepResponse.getDeliveryDate()).isNull();
		assertThat(stepResponse.getWoTargetStartDate()).isNull();
		assertThat(stepResponse.getWoTargetEndDate()).isNull();
		assertThat(stepResponse.getWoStepStatus()).isNull();
		assertThat(stepResponse.getWoFindingsReleasedDate()).isNull();
		assertThat(stepResponse.getWoFindingsCreatedDate()).isNull();

		//resources
		assertThat(stepResponse.getResources()).isNotNull();
		assertThat(stepResponse.getResources().size()).isEqualTo(1);

		final JsonWorkOrderResourceResponse resourceResponse = stepResponse.getResources().get(0);

		assertThat(resourceResponse.getWoResourceId()).isEqualTo(responseBody.getSteps().get(0).getResources().get(0).getMetasfreshId());
		assertThat(resourceResponse.getResourceId()).isEqualTo(JsonMetasfreshId.of(resource.getS_Resource_ID()));
		assertThat(resourceInternalName).isEqualTo(resource.getInternalName());
		assertThat(resourceResponse.getAssignDateFrom()).isEqualTo(assignDateFrom);
		assertThat(resourceResponse.getAssignDateTo()).isEqualTo(assignDateTo);
		assertThat(resourceResponse.getIsActive()).isEqualTo(true);
		assertThat(resourceResponse.getIsAllDay()).isEqualTo(false);

		assertThat(resourceResponse.getDuration()).isEqualTo(BigDecimal.ZERO);
		assertThat(resourceResponse.getDurationUnit()).isEqualTo(JsonDurationUnit.Hour);

		assertThat(resourceResponse.getTestFacilityGroupName()).isNull();
		assertThat(resourceResponse.getExternalId()).isNull();

		//objects under test
		assertThat(storedProject.getObjectsUnderTest()).isNotNull();
		assertThat(storedProject.getObjectsUnderTest().size()).isEqualTo(1);

		final JsonWorkOrderObjectsUnderTestResponse objectsUnderTestResponse = storedProject.getObjectsUnderTest().get(0);

		assertThat(objectsUnderTestResponse.getObjectUnderTestId()).isEqualTo(responseBody.getObjectsUnderTest().get(0).getMetasfreshId());
		assertThat(objectsUnderTestResponse.getExternalId()).isEqualTo(objectUnderTestIdentifier);
		assertThat(objectsUnderTestResponse.getNumberOfObjectsUnderTest()).isEqualTo(numberOfObjectsUnderTest);
		assertThat(objectsUnderTestResponse.getWoDeliveryNote()).isNull();
		assertThat(objectsUnderTestResponse.getWoManufacturer()).isNull();
		assertThat(objectsUnderTestResponse.getWoObjectType()).isNull();
		assertThat(objectsUnderTestResponse.getWoObjectName()).isNull();
		assertThat(objectsUnderTestResponse.getWoObjectWhereabouts()).isNull();
	}

	@Test
	void updateWOProject()
	{
		final String projectName = "projectName";
		final String projectExternalId = "444443";
		final String externalReference = "444444";

		final I_C_Project project = createProjectRecord()
				.name(projectName)
				.projectTypeId(ProjectTypeId.ofRepoId(projectType.getC_ProjectType_ID()))
				.externalId(projectExternalId)
				.externalReference(externalReference)
				.projectCategory(PROJECTCATEGORY_WorkOrderJob)
				.active(true)
				.build();

		final ProjectId projectId = ProjectId.ofRepoId(project.getC_Project_ID());
		final String projectWoStepName = "projectWoStepName";
		final String externalIdStep = "33333";

		final I_C_Project_WO_Step projectWoStep = createProjectWOStepRecord()
				.projectId(projectId)
				.name(projectWoStepName)
				.externalId(externalIdStep)
				.build();

		final WOProjectStepId woProjectStepId = WOProjectStepId.ofRepoId(projectId, projectWoStep.getC_Project_WO_Step_ID());
		final LocalDate assignDateFrom = LocalDate.parse("2022-08-01");
		final LocalDate assignDateTo = LocalDate.parse("2022-08-09");

		final I_C_Project_WO_Resource projectWoResource = createProjectWOResourceRecord()
				.woProjectStepId(woProjectStepId)
				.resourceId(ResourceId.ofRepoId(resource.getS_Resource_ID()))
				.externalId(externalIdStep)
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(BigDecimal.valueOf(8))
				.durationUnit("D")
				.build();

		final String externalIdObjectUnderTest = "22222";
		final int numberOfObjectsUnderTest = 15;

		final I_C_Project_WO_ObjectUnderTest objectUnderTest = createProjectWOObjectUnderTestRecord()
				.projectId(projectId)
				.numberOfObjectsUnderTest(numberOfObjectsUnderTest)
				.externalId(externalIdObjectUnderTest)
				.build();

		final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
		projectRequest.setIdentifier("ext-" + project.getExternalId());
		projectRequest.setProjectReferenceExt(project.getC_Project_Reference_Ext());
		projectRequest.setName("newProjectName");
		projectRequest.setIsActive(false);
		projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);

		final LocalDate stepStartDate = LocalDate.parse("2022-07-05");
		final LocalDate stepEndDate = LocalDate.parse("2022-07-12");
		final JsonWorkOrderStepUpsertItemRequest stepRequest = new JsonWorkOrderStepUpsertItemRequest();
		stepRequest.setIdentifier("ext-" + projectWoStep.getExternalId());
		stepRequest.setExternalId(projectWoStep.getExternalId());
		stepRequest.setDateEnd(stepEndDate);
		stepRequest.setDateStart(stepStartDate);
		stepRequest.setName("newStepName");

		projectRequest.setSteps(ImmutableList.of(stepRequest));

		final JsonWorkOrderResourceUpsertItemRequest resourceRequest = new JsonWorkOrderResourceUpsertItemRequest();
		resourceRequest.setResourceIdentifier("int-" + resource.getInternalName());
		resourceRequest.setExternalId(projectWoResource.getExternalId());
		resourceRequest.setActive(false);

		final LocalDate newAssignDateFrom = LocalDate.parse("2022-08-05");
		final LocalDate newAssignDateTo = LocalDate.parse("2022-08-12");

		resourceRequest.setAssignDateFrom(newAssignDateFrom);
		resourceRequest.setAssignDateTo(newAssignDateTo);

		stepRequest.setResources(ImmutableList.of(resourceRequest));

		final JsonWorkOrderObjectUnderTestUpsertItemRequest objectUnderTestUpsertRequest = new JsonWorkOrderObjectUnderTestUpsertItemRequest();
		objectUnderTestUpsertRequest.setIdentifier("ext-" + objectUnderTest.getExternalId());
		objectUnderTestUpsertRequest.setExternalId(objectUnderTest.getExternalId());
		objectUnderTestUpsertRequest.setNumberOfObjectsUnderTest(52);

		projectRequest.setObjectsUnderTest(ImmutableList.of(objectUnderTestUpsertRequest));

		final JsonWorkOrderProjectUpsertResponse responseBody = workOrderProjectRestService.upsertWOProject(projectRequest);
		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getMetasfreshId()).isEqualTo(JsonMetasfreshId.of(project.getC_Project_ID()));
		assertThat(responseBody.getIdentifier()).isEqualTo("ext-" + projectExternalId);
		assertThat(responseBody.getSyncOutcome()).isEqualTo(JsonResponseUpsertItem.SyncOutcome.UPDATED);

		//validate project
		final I_C_Project projectToValidate = InterfaceWrapperHelper.load(project.getC_Project_ID(), I_C_Project.class);
		assertThat(projectToValidate.getName()).isEqualTo("newProjectName");
		assertThat(projectToValidate.isActive()).isEqualTo(false);

		//validate projectStep
		assertThat(responseBody.getSteps()).isNotNull();
		assertThat(responseBody.getSteps().size()).isEqualTo(1);

		final JsonWorkOrderStepUpsertResponse stepUpsertResponse = responseBody.getSteps().get(0);
		assertThat(stepUpsertResponse.getSyncOutcome()).isEqualTo(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		assertThat(stepUpsertResponse.getIdentifier()).isEqualTo("ext-" + projectWoResource.getExternalId());

		final I_C_Project_WO_Step projectWoStepToValidate = InterfaceWrapperHelper.load(stepUpsertResponse.getMetasfreshId().getValue(), I_C_Project_WO_Step.class);
		assertThat(projectWoStepToValidate.getExternalId()).isEqualTo(externalIdStep);
		assertThat(projectWoStepToValidate.getName()).isEqualTo("newStepName");

		//validate projectResource
		assertThat(stepUpsertResponse.getResources()).isNotNull();
		assertThat(stepUpsertResponse.getResources().size()).isEqualTo(1);

		final JsonWorkOrderResourceUpsertResponse resourceResponse = stepUpsertResponse.getResources().get(0);
		assertThat(resourceResponse.getSyncOutcome()).isEqualTo(JsonResponseUpsertItem.SyncOutcome.UPDATED);
		assertThat(resourceResponse.getIdentifier()).isEqualTo("int-" + resource.getInternalName());

		final I_C_Project_WO_Resource projectWoResourceToValidate = InterfaceWrapperHelper.load(projectWoResource.getC_Project_WO_Resource_ID(), I_C_Project_WO_Resource.class);
		assertThat(projectWoResourceToValidate.isActive()).isEqualTo(false);
		assertThat(projectWoResourceToValidate.getAssignDateFrom()).isEqualTo(TimeUtil.asTimestamp(newAssignDateFrom));
		assertThat(TimeUtil.asInstant(projectWoResourceToValidate.getAssignDateTo())).isEqualTo(TimeUtil.asEndOfDayInstant(newAssignDateTo, ZoneId.systemDefault()));

		//validate projectObjectUnderTest
		assertThat(responseBody.getObjectsUnderTest()).isNotNull();
		assertThat(responseBody.getObjectsUnderTest().size()).isEqualTo(1);

		final JsonWorkOrderObjectUnderTestUpsertResponse objectUnderTestUpsertResponse = responseBody.getObjectsUnderTest().get(0);
		assertThat(objectUnderTestUpsertResponse.getIdentifier()).isEqualTo("ext-" + objectUnderTest.getExternalId());
		assertThat(objectUnderTestUpsertResponse.getMetasfreshId()).isEqualTo(JsonMetasfreshId.of(objectUnderTest.getC_Project_WO_ObjectUnderTest_ID()));
		assertThat(objectUnderTestUpsertResponse.getSyncOutcome()).isEqualTo(JsonResponseUpsertItem.SyncOutcome.UPDATED);

		final I_C_Project_WO_ObjectUnderTest objectUnderTestToValidate = InterfaceWrapperHelper.load(objectUnderTest.getC_Project_WO_ObjectUnderTest_ID(), I_C_Project_WO_ObjectUnderTest.class);
		assertThat(objectUnderTestToValidate.getNumberOfObjectsUnderTest()).isEqualTo(52);
	}

	@Test
	void updateWOProject_doNothing()
	{
		final String projectName = "projectName";
		final String projectExternalId = "88887";
		final String externalReference = "88888";

		final I_C_Project project = createProjectRecord()
				.name(projectName)
				.projectTypeId(ProjectTypeId.ofRepoId(projectType.getC_ProjectType_ID()))
				.externalReference(externalReference)
				.externalId(projectExternalId)
				.active(true)
				.projectCategory(PROJECTCATEGORY_WorkOrderJob)
				.build();

		final ProjectId projectId = ProjectId.ofRepoId(project.getC_Project_ID());
		final String projectWoStepName = "projectWoStepName";
		final String externalIdStep = "44444";

		final I_C_Project_WO_Step projectWoStep = createProjectWOStepRecord()
				.projectId(projectId)
				.name(projectWoStepName)
				.externalId(externalIdStep)
				.build();

		final WOProjectStepId woProjectStepId = WOProjectStepId.ofRepoId(projectId, projectWoStep.getC_Project_WO_Step_ID());
		final LocalDate assignDateFrom = LocalDate.parse("2022-08-07");
		final LocalDate assignDateTo = LocalDate.parse("2022-08-08");

		final I_C_Project_WO_Resource projectWoResource = createProjectWOResourceRecord()
				.woProjectStepId(woProjectStepId)
				.resourceId(ResourceId.ofRepoId(resource.getS_Resource_ID()))
				.externalId(externalIdStep)
				.assignDateFrom(assignDateFrom)
				.assignDateTo(assignDateTo)
				.duration(BigDecimal.ONE)
				.durationUnit("D")
				.build();

		final String externalIdObjectUnderTest = "3333";
		final int numberOfObjectsUnderTest = 5;

		final I_C_Project_WO_ObjectUnderTest objectUnderTest = createProjectWOObjectUnderTestRecord()
				.projectId(projectId)
				.numberOfObjectsUnderTest(numberOfObjectsUnderTest)
				.externalId(externalIdObjectUnderTest)
				.build();

		final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
		projectRequest.setIdentifier("ext-" + project.getExternalId());
		projectRequest.setProjectReferenceExt(project.getC_Project_Reference_Ext());
		projectRequest.setName("newProjectName");
		projectRequest.setIsActive(false);
		projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		projectRequest.setSyncAdvise(SyncAdvise.JUST_CREATE_IF_NOT_EXISTS);

		final JsonWorkOrderStepUpsertItemRequest stepRequest = new JsonWorkOrderStepUpsertItemRequest();
		stepRequest.setIdentifier("ext-" + projectWoStep.getExternalId());
		stepRequest.setExternalId(projectWoStep.getExternalId());
		stepRequest.setName("newStepName");

		final JsonWorkOrderResourceUpsertItemRequest resourceRequest = new JsonWorkOrderResourceUpsertItemRequest();
		resourceRequest.setResourceIdentifier("int-" + resource.getInternalName());
		resourceRequest.setExternalId(projectWoResource.getExternalId());
		resourceRequest.setActive(false);

		final JsonWorkOrderObjectUnderTestUpsertItemRequest objectUnderTestUpsertRequest = new JsonWorkOrderObjectUnderTestUpsertItemRequest();
		objectUnderTestUpsertRequest.setIdentifier("ext-" + objectUnderTest.getExternalId());
		objectUnderTestUpsertRequest.setExternalId(objectUnderTest.getExternalId());
		objectUnderTestUpsertRequest.setNumberOfObjectsUnderTest(52);

		final JsonWorkOrderProjectUpsertResponse responseBody = workOrderProjectRestService.upsertWOProject(projectRequest);
		assertThat(responseBody).isNotNull();
		assertThat(responseBody.getMetasfreshId()).isEqualTo(JsonMetasfreshId.of(project.getC_Project_ID()));
		assertThat(responseBody.getIdentifier()).isEqualTo("ext-" + projectExternalId);
		assertThat(responseBody.getSyncOutcome()).isEqualTo(JsonResponseUpsertItem.SyncOutcome.NOTHING_DONE);
		assertThat(responseBody.getSteps()).isNull();
		assertThat(responseBody.getObjectsUnderTest()).isNull();

		final I_C_Project projectToValidate = InterfaceWrapperHelper.load(project.getC_Project_ID(), I_C_Project.class);
		assertThat(projectToValidate.getName()).isEqualTo(projectName);
		assertThat(projectToValidate.isActive()).isEqualTo(true);

		final I_C_Project_WO_Step projectWoStepToValidate = InterfaceWrapperHelper.load(projectWoStep.getC_Project_WO_Step_ID(), I_C_Project_WO_Step.class);
		assertThat(projectWoStepToValidate.getName()).isEqualTo(projectWoStepName);
		assertThat(projectWoStepToValidate.getExternalId()).isEqualTo(externalIdStep);

		final I_C_Project_WO_Resource projectWoResourceToValidate = InterfaceWrapperHelper.load(projectWoResource.getC_Project_WO_Resource_ID(), I_C_Project_WO_Resource.class);
		assertThat(projectWoResourceToValidate.isActive()).isEqualTo(true);
		assertThat(projectWoResourceToValidate.getAssignDateFrom()).isEqualTo(TimeUtil.asTimestamp(assignDateFrom));

		final I_C_Project_WO_ObjectUnderTest objectUnderTestToValidate = InterfaceWrapperHelper.load(objectUnderTest.getC_Project_WO_ObjectUnderTest_ID(), I_C_Project_WO_ObjectUnderTest.class);
		assertThat(objectUnderTestToValidate.getNumberOfObjectsUnderTest()).isEqualTo(numberOfObjectsUnderTest);
	}

	@Builder(builderMethodName = "createProjectRecord")
	private I_C_Project createProject(
			@NonNull final String name,
			@NonNull final ProjectTypeId projectTypeId,
			@Nullable final String externalReference,
			@Nullable final String externalId,
			@Nullable final Boolean active,
			@Nullable final String projectCategory)
	{
		final I_C_Project project = InterfaceWrapperHelper.newInstance(I_C_Project.class);

		project.setName(name);
		project.setValue(name);
		project.setC_Currency_ID(currencyId.getRepoId());
		project.setC_ProjectType_ID(projectTypeId.getRepoId());
		project.setProjectCategory(projectCategory);

		Optional.ofNullable(externalReference)
				.ifPresent(project::setC_Project_Reference_Ext);

		final Boolean isActive = CoalesceUtil.coalesceNotNull(active, Boolean.TRUE);
		project.setIsActive(isActive);

		project.setExternalId(externalId);
		
		InterfaceWrapperHelper.save(project);

		return project;
	}

	@Builder(builderMethodName = "createProjectWOStepRecord")
	private I_C_Project_WO_Step createProjectWOStep(
			@NonNull final ProjectId projectId,
			@NonNull final String name,
			@Nullable final String externalId)
	{
		final I_C_Project_WO_Step projectWOStep = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Step.class);

		projectWOStep.setC_Project_ID(projectId.getRepoId());
		projectWOStep.setName(name);
		projectWOStep.setExternalId(externalId);
		projectWOStep.setDateEnd(Timestamp.from(Instant.ofEpochMilli(0)));
		projectWOStep.setDateStart(Timestamp.from(Instant.ofEpochMilli(1)));

		InterfaceWrapperHelper.save(projectWOStep);

		return projectWOStep;
	}

	@Builder(builderMethodName = "createProjectWOResourceRecord")
	private I_C_Project_WO_Resource createProjectWOResource(
			@NonNull final WOProjectStepId woProjectStepId,
			@NonNull final ResourceId resourceId,
			@NonNull final LocalDate assignDateTo,
			@NonNull final LocalDate assignDateFrom,
			@NonNull final BigDecimal duration,
			@NonNull final String durationUnit,
			@Nullable final String externalId)
	{
		final I_C_Project_WO_Resource projectWoResource = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource.class);

		projectWoResource.setC_Project_ID(woProjectStepId.getProjectId().getRepoId());
		projectWoResource.setC_Project_WO_Step_ID(woProjectStepId.getRepoId());
		projectWoResource.setS_Resource_ID(resourceId.getRepoId());
		projectWoResource.setExternalId(externalId);
		projectWoResource.setAssignDateTo(TimeUtil.asTimestamp(assignDateTo));
		projectWoResource.setAssignDateFrom(TimeUtil.asTimestamp(assignDateFrom));
		projectWoResource.setDuration(duration);
		projectWoResource.setDurationUnit(durationUnit);

		InterfaceWrapperHelper.save(projectWoResource);

		return projectWoResource;
	}

	@Builder(builderMethodName = "createProjectWOObjectUnderTestRecord")
	private I_C_Project_WO_ObjectUnderTest createProjectWOObjectUnderTest(
			@NonNull final ProjectId projectId,
			final int numberOfObjectsUnderTest,
			@Nullable final String externalId)
	{
		final I_C_Project_WO_ObjectUnderTest objectUnderTest = InterfaceWrapperHelper.newInstance(I_C_Project_WO_ObjectUnderTest.class);

		objectUnderTest.setC_Project_ID(projectId.getRepoId());
		objectUnderTest.setNumberOfObjectsUnderTest(numberOfObjectsUnderTest);
		objectUnderTest.setExternalId(externalId);

		InterfaceWrapperHelper.save(objectUnderTest);

		return objectUnderTest;
	}

	@NonNull
	private I_M_PriceList_Version createPriceListVersion(@NonNull final PriceListId priceListId, @NonNull final CurrencyId currencyId)
	{
		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setM_PriceList_ID(priceListId.getRepoId());
		priceList.setC_Currency_ID(currencyId.getRepoId());
		saveRecord(priceList);

		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);

		priceListVersion.setM_PriceList_ID(priceListId.getRepoId());
		priceListVersion.setValidFrom(Timestamp.from(Instant.now()));

		saveRecord(priceListVersion);

		return priceListVersion;
	}
}