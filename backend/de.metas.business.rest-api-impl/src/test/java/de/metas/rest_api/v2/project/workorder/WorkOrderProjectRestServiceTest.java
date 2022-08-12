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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderResourceUpsertRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderStepUpsertRequest;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.WOProjectStepRepository;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.project.workorder.data.WorkOrderProjectResourceRepository;
import de.metas.project.workorder.data.WorkOrderProjectStepRepository;
import de.metas.resource.ResourceService;
import de.metas.test.SnapshotFunctionFactory;
import de.metas.util.Services;
import io.github.jsonSnapshot.SnapshotMatcher;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.I_R_StatusCategory;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_C_ProjectType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

class WorkOrderProjectRestServiceTest
{
	private WorkOrderProjectRestService workOrderProjectRestService;
	private I_C_ProjectType projectType;

	private I_R_StatusCategory requestStatusCategory;
	private String orgValue;
	private I_S_Resource resource;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone();
		orgValue = Services.get(IOrgDAO.class).retrieveOrgValue(orgId);

		requestStatusCategory = newInstance(I_R_StatusCategory.class);
		save(requestStatusCategory);

		projectType = newInstance(I_C_ProjectType.class);
		projectType.setProjectCategory(X_C_ProjectType.PROJECTCATEGORY_WorkOrderJob);
		projectType.setR_StatusCategory_ID(requestStatusCategory.getR_StatusCategory_ID());
		save(projectType);

		final I_S_ResourceType resourceType = newInstance(I_S_ResourceType.class);
		save(resourceType);

		resource = newInstance(I_S_Resource.class);
		resource.setS_ResourceType_ID(resourceType.getS_ResourceType_ID());
		resource.setInternalName("internalName");
		resource.setValue("resourceValue");
		
		InterfaceWrapperHelper.save(resource);

		final IDocumentNoBuilderFactory documentNoBuilderFactory = Mockito.mock(IDocumentNoBuilderFactory.class);
		final IDocumentNoBuilder documentNoBuilder = Mockito.mock(IDocumentNoBuilder.class);
		Mockito.when(documentNoBuilderFactory.createValueBuilderFor(any())).thenReturn(documentNoBuilder);
		Mockito.when(documentNoBuilder.setFailOnError(anyBoolean())).thenReturn(documentNoBuilder);
		Mockito.when(documentNoBuilder.build()).thenReturn("123");

		final WorkOrderProjectStepRepository workOrderProjectStepRepository = new WorkOrderProjectStepRepository(
				new WOProjectStepRepository(),
				new WorkOrderProjectResourceRepository());

		final ProjectTypeRepository projectTypeRepository = new ProjectTypeRepository();
		final WorkOrderProjectRepository workOrderProjectRepository = new WorkOrderProjectRepository(documentNoBuilderFactory, projectTypeRepository, workOrderProjectStepRepository);

		final ResourceService resourceService = ResourceService.newInstanceForJUnitTesting();
		final WorkOrderProjectJsonToInternalConverter workOrderProjectJsonToInternalConverter = new WorkOrderProjectJsonToInternalConverter(resourceService);
		workOrderProjectRestService = new WorkOrderProjectRestService(workOrderProjectRepository, projectTypeRepository, workOrderProjectJsonToInternalConverter);
	}

	@BeforeAll
	static void initStatic()
	{
		SnapshotMatcher.start(AdempiereTestHelper.SNAPSHOT_CONFIG, SnapshotFunctionFactory.newFunction());

		LogManager.setLoggerLevel(de.metas.rest_api.v2.product.ProductsRestController.class, Level.ALL);
	}

	@AfterAll
	static void afterAll()
	{
		SnapshotMatcher.validateSnapshots();
	}

	@Test
	void upsertWOProject()
	{
		final JsonWorkOrderProjectUpsertRequest projectRequest = new JsonWorkOrderProjectUpsertRequest();
		projectRequest.setCurrencyId(JsonMetasfreshId.of(BusinessTestHelper.getEURCurrencyId().getRepoId()));
		projectRequest.setProjectIdentifier("ext-externalId");
		projectRequest.setBpartnerDepartment("bpartnerDepartment");
		projectRequest.setBPartnerTargetDate(LocalDate.parse("2022-08-20"));
		projectRequest.setDateContract(LocalDate.parse("2022-08-10"));
		projectRequest.setDateFinish(LocalDate.parse("2022-08-21"));
		projectRequest.setDescription("description");
		projectRequest.setIsActive(false);
		projectRequest.setName("name");
		projectRequest.setOrgCode(orgValue);
		projectRequest.setPOReference("POReference");
		projectRequest.setProjectReferenceExt("projectReferenceExt");
		projectRequest.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));
		projectRequest.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		projectRequest.setValue("value");
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

		final JsonWorkOrderProjectUpsertResponse responseBody = workOrderProjectRestService.upsertWOProject(projectRequest);
		assertThat(responseBody).isNotNull();

		final JsonWorkOrderProjectResponse data = workOrderProjectRestService.getWorkOrderProjectDataById(ProjectId.ofRepoId(responseBody.getProjectId().getValue()));
		SnapshotMatcher.expect(data).toMatchSnapshot();
	}
}