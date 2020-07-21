/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.serviceprovider.external.label;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.IssueTestHelper;
import de.metas.serviceprovider.model.I_S_IssueLabel;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import static de.metas.serviceprovider.TestConstants.MOCK_BUD_6_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_EST_4_25_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_ISSUE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_VALUE;
import static org.junit.Assert.assertEquals;

public class IssueLabelRepositoryTest
{
	private final IssueLabelRepository issueLabelRepository = new IssueLabelRepository(Services.get(IQueryBL.class));

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void persistLabels()
	{
		//given
		IssueTestHelper.buildAndStoreIssueRecord(MOCK_ISSUE_ID, null, null, null, null);
		buildAndStoreLabel(MOCK_BUD_6_LABEL);
		buildAndStoreLabel(MOCK_VALUE);

		//when
		issueLabelRepository.persistLabels(MOCK_ISSUE_ID, getMockLabels());

		//then
		final ImmutableList<I_S_IssueLabel> storedLabels = issueLabelRepository.getRecordsByIssueId(MOCK_ISSUE_ID);

		assertEquals(2, storedLabels.size());
		assertEquals(storedLabels.get(0).getS_Issue_ID(), MOCK_ISSUE_ID.getRepoId());
		assertEquals(storedLabels.get(0).getAD_Org_ID(), MOCK_ORG_ID.getRepoId());
		assertEquals(storedLabels.get(0).getLabel(), MOCK_VALUE);

		assertEquals(storedLabels.get(1).getS_Issue_ID(), MOCK_ISSUE_ID.getRepoId());
		assertEquals(storedLabels.get(1).getAD_Org_ID(), MOCK_ORG_ID.getRepoId());
		assertEquals(storedLabels.get(1).getLabel(), MOCK_EST_4_25_LABEL);
	}

	private ImmutableList<IssueLabel> getMockLabels()
	{
		return ImmutableList.of(IssueLabel.builder().value(MOCK_VALUE).orgId(MOCK_ORG_ID).build(),
						IssueLabel.builder().value(MOCK_EST_4_25_LABEL).orgId(MOCK_ORG_ID).build());
	}

	private void buildAndStoreLabel(final String label)
	{
		final I_S_IssueLabel record = InterfaceWrapperHelper.newInstance(I_S_IssueLabel.class);
		record.setAD_Org_ID(MOCK_ORG_ID.getRepoId());
		record.setS_Issue_ID(MOCK_ISSUE_ID.getRepoId());
		record.setLabel(label);

		InterfaceWrapperHelper.saveRecord(record);
	}
}
