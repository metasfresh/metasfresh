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

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetailType;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetailsRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ISSUE_NO;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_MILESTONE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PROJECT_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_SEARCH_KEY;
import static de.metas.serviceprovider.TestConstants.MOCK_UOM_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_VALUE;

public class IssueRepositoryTest
{
	private final IssueRepository issueRepository = new IssueRepository(new ExternalIssueDetailsRepository());

	private final ImmutableList<ExternalIssueDetail> MOCK_EXTERNAL_DETAILS =
			ImmutableList.of(ExternalIssueDetail.builder()
					.type(ExternalIssueDetailType.LABEL)
					.value(MOCK_VALUE)
					.orgId(MOCK_ORG_ID)
					.build());

	private final IssueEntity MOCK_ISSUE_ENTITY = IssueEntity.builder()
			.assigneeId(MOCK_USER_ID)
			.orgId(MOCK_ORG_ID)
			.projectId(MOCK_PROJECT_ID)
			.effortUomId(MOCK_UOM_ID)
			.milestoneId(MOCK_MILESTONE_ID)
			.budgetedEffort(BigDecimal.ONE)
			.estimatedEffort(BigDecimal.ONE)
			.name(MOCK_NAME)
			.searchKey(MOCK_SEARCH_KEY)
			.description(MOCK_DESCRIPTION)
			.type(IssueType.EXTERNAL)
			.isEffortIssue(true)
			.processed(true)
			.externalIssueNo(MOCK_EXTERNAL_ISSUE_NO)
			.externalIssueURL(MOCK_EXTERNAL_URL)
			.externalIssueDetails(MOCK_EXTERNAL_DETAILS)
			.build();


	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void save()
	{
		issueRepository.save(MOCK_ISSUE_ENTITY);

		final IssueEntity storedEntity = issueRepository.getById(MOCK_ISSUE_ENTITY.getIssueId(), true);

		Assert.assertEquals(storedEntity, MOCK_ISSUE_ENTITY);
	}

}
