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
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_01;
import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_30;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ISSUE_NO;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT;
import static de.metas.serviceprovider.TestConstants.MOCK_MILESTONE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PROJECT_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_SEARCH_KEY;
import static de.metas.serviceprovider.TestConstants.MOCK_UOM_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_VALUE;
import static de.metas.serviceprovider.issue.IssueServiceTest.prepareDataContext;
import static org.junit.Assert.assertEquals;

public class IssueRepositoryTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IModelCacheInvalidationService modelCacheInvalidationService =  Services.get(IModelCacheInvalidationService.class);

	private final IssueRepository issueRepository = new IssueRepository(queryBL, modelCacheInvalidationService);

	private final IssueEntity MOCK_ISSUE_ENTITY = IssueEntity.builder()
			.assigneeId(MOCK_USER_ID)
			.orgId(MOCK_ORG_ID)
			.externalProjectReferenceId(MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE)
			.projectId(MOCK_PROJECT_ID)
			.effortUomId(MOCK_UOM_ID)
			.milestoneId(MOCK_MILESTONE_ID)
			.budgetedEffort(BigDecimal.ONE)
			.estimatedEffort(BigDecimal.ONE)
			.roughEstimation(BigDecimal.ONE)
			.issueEffort(Effort.ofNullable(MOCK_EFFORT_1_30))
			.aggregatedEffort(Effort.ofNullable(MOCK_EFFORT_1_30))
			.name(MOCK_NAME)
			.searchKey(MOCK_SEARCH_KEY)
			.description(MOCK_DESCRIPTION)
			.type(IssueType.EXTERNAL)
			.isEffortIssue(true)
			.latestActivityOnSubIssues(MOCK_INSTANT)
			.latestActivityOnIssue(MOCK_INSTANT)
			.plannedUATDate(MOCK_DATE_2020_03_01)
			.deliveryPlatform(MOCK_VALUE)
			.status(Status.IN_PROGRESS)
			.externalIssueNo(BigDecimal.valueOf(MOCK_EXTERNAL_ISSUE_NO))
			.externalIssueURL(MOCK_EXTERNAL_URL)
			.build();


	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void save()
	{
		//1. new record test
		issueRepository.save(MOCK_ISSUE_ENTITY);

		IssueEntity storedEntity = issueRepository.getById(MOCK_ISSUE_ENTITY.getIssueId());

		Assert.assertEquals(storedEntity, MOCK_ISSUE_ENTITY);
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
	 * When {@link IssueRepository#buildUpStreamIssueHierarchy(IssueId)} for 8
	 * Then return: IssueHierarchy(root=1) with nodes: [1,2,4,8]
	 */
	@Test
	public void buildUpStreamIssueHierarchy()
	{
		//given
		prepareDataContext();

		//when
		final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(IssueId.ofRepoId(8));

		//then
		final List<IssueEntity> nodeList = issueHierarchy.listIssues();

		assertEquals(nodeList.size(), 4);
		assertEquals(nodeList.get(0).getIssueId(), IssueId.ofRepoId(1));
		assertEquals(nodeList.get(1).getIssueId(), IssueId.ofRepoId(2));
		assertEquals(nodeList.get(2).getIssueId(), IssueId.ofRepoId(4));
		assertEquals(nodeList.get(3).getIssueId(), IssueId.ofRepoId(8));
	}

}
