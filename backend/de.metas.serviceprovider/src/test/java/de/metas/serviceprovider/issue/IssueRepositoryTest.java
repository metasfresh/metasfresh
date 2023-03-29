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

import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.metas.serviceprovider.issue.IssueServiceTest.prepareDataContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class IssueRepositoryTest
{
	private IQueryBL queryBL;
	private IssueRepository issueRepository;

	private IssueEntity MOCK_ISSUE_ENTITY;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		queryBL = Services.get(IQueryBL.class);
		issueRepository = new IssueRepository(queryBL, ModelCacheInvalidationService.newInstanceForUnitTesting());
		MOCK_ISSUE_ENTITY = IssueTestHelper.createMockIssueEntity();
	}

	@Test
	public void save()
	{
		//1. new record test
		issueRepository.save(MOCK_ISSUE_ENTITY);

		final IssueEntity storedEntity = issueRepository.getById(MOCK_ISSUE_ENTITY.getIssueId());

		assertThat(storedEntity).isEqualTo(MOCK_ISSUE_ENTITY);
	}

	/**
	 * Given the following issue hierarchy:
	 * <p>
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 * <p>
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
