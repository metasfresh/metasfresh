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

package de.metas.serviceprovider.issue;

import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.serviceprovider.external.label.IssueLabelRepository;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_00;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_30;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_2_30;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_SEARCH_KEY;
import static de.metas.serviceprovider.TestConstants.MOCK_UOM_ID;
import static org.junit.Assert.assertEquals;

public class IssueEffortServiceTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IModelCacheInvalidationService modelCacheInvalidationService =  Services.get(IModelCacheInvalidationService.class);
	private final IssueLabelRepository issueLabelRepository = new IssueLabelRepository(queryBL);
	private final IssueRepository issueRepository = new IssueRepository(queryBL, issueLabelRepository, modelCacheInvalidationService);
	private final IssueHierarchyService issueHierarchyService = new IssueHierarchyService(issueRepository);
	private final IssueEffortService issueEffortService = new IssueEffortService(issueRepository, issueHierarchyService);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Given the following issue hierarchy:
	 *
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 * with all issues having {@code aggregatedEffort} = 1:30 (not a real case but it's fine for testing purposes)
	 * When: {@link IssueEffortService#addAggregatedEffort(IssueId, Effort)} for IssueId 8 and effort 1:00
	 * Then:
	 *  - issues [1,2,4,8] will have a new {@code aggregatedEffort} namely 2:30
	 *  - meanwhile [3,5,6,7] will not be altered
	 */
	@Test
	public void addAggregatedEffort()
	{
		//Given
		prepareDataContext();

		final Effort effort_1_00 = Effort.ofNullable(MOCK_EFFORT_1_00);

		//when
		issueEffortService.addAggregatedEffort(IssueId.ofRepoId(8), effort_1_00);

		//then
		assertEquals(issueRepository.getById(IssueId.ofRepoId(8), false).getAggregatedEffort(), MOCK_EFFORT_2_30);
		assertEquals(issueRepository.getById(IssueId.ofRepoId(4), false).getAggregatedEffort(), MOCK_EFFORT_2_30);
		assertEquals(issueRepository.getById(IssueId.ofRepoId(2), false).getAggregatedEffort(), MOCK_EFFORT_2_30);
		assertEquals(issueRepository.getById(IssueId.ofRepoId(1), false).getAggregatedEffort(), MOCK_EFFORT_2_30);

		assertEquals(issueRepository.getById(IssueId.ofRepoId(7), false).getAggregatedEffort(), MOCK_EFFORT_1_30);
		assertEquals(issueRepository.getById(IssueId.ofRepoId(6), false).getAggregatedEffort(), MOCK_EFFORT_1_30);
		assertEquals(issueRepository.getById(IssueId.ofRepoId(5), false).getAggregatedEffort(), MOCK_EFFORT_1_30);
		assertEquals(issueRepository.getById(IssueId.ofRepoId(3), false).getAggregatedEffort(), MOCK_EFFORT_1_30);

	}


	@Test
	public void addIssueEffort()
	{
		//given
		prepareDataContext();

		//when
		issueEffortService.addIssueEffort(IssueId.ofRepoId(1), Effort.ofNullable(MOCK_EFFORT_1_00));

		//then
		final IssueEntity issueEntity = issueRepository.getById(IssueId.ofRepoId(1), false);

		assertEquals(issueEntity.getIssueEffort(), MOCK_EFFORT_2_30);
	}

	/**
	 * Builds a Issue hierarchy as described below:
	 *
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 *
	 */
	static void prepareDataContext()
	{
		buildAndStoreIssueRecord(IssueId.ofRepoId(1), null);
		buildAndStoreIssueRecord(IssueId.ofRepoId(2), IssueId.ofRepoId(1));
		buildAndStoreIssueRecord(IssueId.ofRepoId(3), IssueId.ofRepoId(1));
		buildAndStoreIssueRecord(IssueId.ofRepoId(4), IssueId.ofRepoId(2));
		buildAndStoreIssueRecord(IssueId.ofRepoId(5), IssueId.ofRepoId(3));
		buildAndStoreIssueRecord(IssueId.ofRepoId(6), IssueId.ofRepoId(4));
		buildAndStoreIssueRecord(IssueId.ofRepoId(7), IssueId.ofRepoId(4));
		buildAndStoreIssueRecord(IssueId.ofRepoId(8), IssueId.ofRepoId(4));
	}

	private static void buildAndStoreIssueRecord(final IssueId issueId, final IssueId parentIssueId)
	{
		final I_S_Issue record = InterfaceWrapperHelper.newInstance(I_S_Issue.class);

		record.setS_Issue_ID(issueId.getRepoId());
		record.setS_Parent_Issue_ID(NumberUtils.asInt(parentIssueId, -1));

		record.setAD_Org_ID(MOCK_ORG_ID.getRepoId());
		record.setName(MOCK_NAME);
		record.setValue(MOCK_SEARCH_KEY);
		record.setIssueType(IssueType.EXTERNAL.getValue());
		record.setEffort_UOM_ID(MOCK_UOM_ID.getRepoId());
		record.setAggregatedEffort(MOCK_EFFORT_1_30);
		record.setIssueEffort(MOCK_EFFORT_1_30);

		InterfaceWrapperHelper.saveRecord(record);
	}
}
