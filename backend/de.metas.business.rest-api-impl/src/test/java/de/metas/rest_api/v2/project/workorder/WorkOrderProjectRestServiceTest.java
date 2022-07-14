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

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectRequest;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertResponse;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.WOProjectStepRepository;
import de.metas.project.workorder.data.WorkOrderProjectRepository;
import de.metas.project.workorder.data.WorkOrderProjectResourceRepository;
import de.metas.project.workorder.data.WorkOrderProjectStepRepository;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_ProjectType;
import org.compiere.model.X_C_ProjectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

class WorkOrderProjectRestServiceTest
{
	private WorkOrderProjectRestService workOrderProjectRestService;
	private I_C_ProjectType projectType;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		projectType = InterfaceWrapperHelper.newInstance(I_C_ProjectType.class);
		projectType.setProjectCategory(X_C_ProjectType.PROJECTCATEGORY_WorkOrderJob);
		InterfaceWrapperHelper.save(projectType);

		final IDocumentNoBuilderFactory documentNoBuilderFactory = Mockito.mock(IDocumentNoBuilderFactory.class);
		final IDocumentNoBuilder documentNoBuilder = Mockito.mock(IDocumentNoBuilder.class);
		Mockito.when(documentNoBuilderFactory.createValueBuilderFor(any())).thenReturn(documentNoBuilder);
		Mockito.when(documentNoBuilder.setFailOnError(anyBoolean())).thenReturn(documentNoBuilder);
		Mockito.when(documentNoBuilder.build()).thenReturn("123");

		final WorkOrderProjectStepRepository workOrderProjectStepRepository = new WorkOrderProjectStepRepository(
				new WOProjectStepRepository(),
				new WorkOrderProjectResourceRepository());

		final WorkOrderProjectRepository workOrderProjectRepository = new WorkOrderProjectRepository(documentNoBuilderFactory, new ProjectTypeRepository(), workOrderProjectStepRepository);


		workOrderProjectRestService = new WorkOrderProjectRestService(workOrderProjectRepository);
	}

	@Test
	void upsertWOProject()
	{
		final JsonWorkOrderProjectRequest request = new JsonWorkOrderProjectRequest();
		request.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		request.setProjectTypeId(JsonMetasfreshId.of(projectType.getC_ProjectType_ID()));

		final JsonWorkOrderProjectUpsertResponse result = workOrderProjectRestService.upsertWOProject(request);
		assertThat(result).isNotNull();
	}
}