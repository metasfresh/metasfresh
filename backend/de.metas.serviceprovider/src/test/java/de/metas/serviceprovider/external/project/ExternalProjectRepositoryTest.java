/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.external.project;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_OWNER;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_REFERENCE_ID_INACTIVE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_TYPE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_REFERENCE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_SYSTEM;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PROJECT_ID;
import static org.junit.Assert.assertEquals;

public class ExternalProjectRepositoryTest
{
	private final IQueryBL queryBL =  Services.get(IQueryBL.class);
	private final ExternalProjectRepository externalProjectRepository = new ExternalProjectRepository(queryBL);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		prepareMockExternalProjectRecord(MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE, true);
		prepareMockExternalProjectRecord(MOCK_EXTERNAL_PROJECT_REFERENCE_ID_INACTIVE, false);
	}

	private void prepareMockExternalProjectRecord(final ExternalProjectReferenceId id, final boolean isActive)
	{
		final I_S_ExternalProjectReference record = InterfaceWrapperHelper.newInstance(I_S_ExternalProjectReference.class);

		record.setAD_Org_ID(MOCK_ORG_ID.getRepoId());
		record.setC_Project_ID(MOCK_PROJECT_ID.getRepoId());
		record.setExternalProjectOwner(MOCK_EXTERNAL_PROJECT_OWNER);
		record.setExternalReference(MOCK_EXTERNAL_REFERENCE);
		record.setExternalSystem(MOCK_EXTERNAL_SYSTEM.getValue());
		record.setIsActive(isActive);
		record.setProjectType(MOCK_EXTERNAL_PROJECT_TYPE.getValue());
		record.setS_ExternalProjectReference_ID(id.getRepoId());

		InterfaceWrapperHelper.saveRecord(record);
	}

	@Test
	public void getById()
	{
		final ExternalProjectReference externalProjectReference =
				externalProjectRepository.getById(MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE);

		assertEquals(externalProjectReference.getOrgId(), MOCK_ORG_ID);
		assertEquals(externalProjectReference.getProjectId(), MOCK_PROJECT_ID);
		assertEquals(externalProjectReference.getProjectOwner(), MOCK_EXTERNAL_PROJECT_OWNER);
		assertEquals(externalProjectReference.getExternalProjectReference(), MOCK_EXTERNAL_REFERENCE);
		assertEquals(externalProjectReference.getExternalProjectType(), MOCK_EXTERNAL_PROJECT_TYPE);
	}

	@Test
	public void getByExternalSystem()
	{
		final ImmutableList<ExternalProjectReference> records = externalProjectRepository.getByExternalSystem(MOCK_EXTERNAL_SYSTEM);

		assertEquals(records.size(), 1);
		assertEquals(records.get(0).getOrgId(), MOCK_ORG_ID);
		assertEquals(records.get(0).getProjectId(), MOCK_PROJECT_ID);
		assertEquals(records.get(0).getProjectOwner(), MOCK_EXTERNAL_PROJECT_OWNER);
		assertEquals(records.get(0).getExternalProjectReference(), MOCK_EXTERNAL_REFERENCE);
		assertEquals(records.get(0).getExternalProjectType(), MOCK_EXTERNAL_PROJECT_TYPE);
	}
}
