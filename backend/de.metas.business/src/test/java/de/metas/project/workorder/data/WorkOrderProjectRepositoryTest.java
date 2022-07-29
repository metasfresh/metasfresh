/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.data;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.organization.OrgId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.workorder.WOProjectStepRepository;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.assertj.core.api.Assertions.*;

class WorkOrderProjectRepositoryTest
{
	private WorkOrderProjectRepository workOrderProjectRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IDocumentNoBuilderFactory documentNoBuilderFactory = Mockito.mock(IDocumentNoBuilderFactory.class);
		final WorkOrderProjectStepRepository workOrderProjectStepRepository = new WorkOrderProjectStepRepository(new WOProjectStepRepository(), new WorkOrderProjectResourceRepository());

		workOrderProjectRepository = new WorkOrderProjectRepository(documentNoBuilderFactory,
																	new ProjectTypeRepository(),
																	workOrderProjectStepRepository,
																	new WorkOrderProjectObjectUnderTestRepository());
	}

	@Test
	void saveNew()
	{
		final WOProject woProject = WOProject.builder()
				.orgId(OrgId.ofRepoId(10))
				.name("name")
				.projectStep(WOProjectStep.builder().name("name")
									 .projectResource(WOProjectResource.builder()
															  .assignDateFrom(Instant.parse("2022-07-01T10:15:30.00Z"))
															  .assignDateTo(Instant.parse("2022-07-14T10:15:30.00Z"))
															  .build())
									 .build())
				.build();
		final WOProject result = workOrderProjectRepository.save(woProject);

		assertThat(result.getProjectId()).isNotNull();

		final I_C_Project projectRecord = InterfaceWrapperHelper.load(result.getProjectId(), I_C_Project.class);

	}

	@Test
	void saveExisting()
	{

	}
}