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

package de.metas.serviceprovider.milestone;

import de.metas.serviceprovider.model.I_S_Milestone;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_SEARCH_KEY;
import static org.junit.Assert.assertEquals;

public class MilestoneRepositoryTest
{
	private final MilestoneRepository milestoneRepository = new MilestoneRepository();

	private final Milestone MOCK_MILESTONE = Milestone.builder()
			.orgId(MOCK_ORG_ID)
			.name(MOCK_NAME)
			.value(MOCK_SEARCH_KEY)
			.description(MOCK_DESCRIPTION)
			.externalURL(MOCK_EXTERNAL_URL)
			.processed(true)
			.dueDate(MOCK_INSTANT)
			.build();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void save()
	{
		milestoneRepository.save(MOCK_MILESTONE);

		final I_S_Milestone record = InterfaceWrapperHelper.load(MOCK_MILESTONE.getMilestoneId(), I_S_Milestone.class);

		assertEqualsRecord(record, MOCK_MILESTONE);
	}

	private void assertEqualsRecord(final I_S_Milestone record, final Milestone milestone)
	{

		assertEquals(record.getS_Milestone_ID(), milestone.getMilestoneId().getRepoId());
		assertEquals(record.getAD_Org_ID(), milestone.getOrgId().getRepoId());
		assertEquals(record.getDescription(), milestone.getDescription());
		assertEquals(record.getExternalUrl(), milestone.getExternalURL());
		assertEquals(record.getValue(), milestone.getValue());
		assertEquals(record.getName(), milestone.getName());
		assertEquals(record.getMilestone_DueDate(), Timestamp.from(milestone.getDueDate()));
		assertEquals(record.isProcessed(), milestone.isProcessed());
	}
}
