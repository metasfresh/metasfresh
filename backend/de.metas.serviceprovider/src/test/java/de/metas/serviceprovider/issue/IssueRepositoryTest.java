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
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.label.IssueLabelRepository;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static de.metas.serviceprovider.TestConstants.MOCK_BUD_6_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_30;
import static de.metas.serviceprovider.TestConstants.MOCK_EST_4_25_LABEL;
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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IModelCacheInvalidationService modelCacheInvalidationService =  Services.get(IModelCacheInvalidationService.class);

	private final IssueRepository issueRepository = new IssueRepository(queryBL, new IssueLabelRepository(queryBL), modelCacheInvalidationService);

	private final ImmutableList<IssueLabel> MOCK_LABELS =
			ImmutableList.of(IssueLabel.builder().value(MOCK_VALUE).orgId(MOCK_ORG_ID).build(),
					IssueLabel.builder().value(MOCK_EST_4_25_LABEL).orgId(MOCK_ORG_ID).build());

	private final IssueEntity MOCK_ISSUE_ENTITY = IssueEntity.builder()
			.assigneeId(MOCK_USER_ID)
			.orgId(MOCK_ORG_ID)
			.projectId(MOCK_PROJECT_ID)
			.effortUomId(MOCK_UOM_ID)
			.milestoneId(MOCK_MILESTONE_ID)
			.budgetedEffort(BigDecimal.ONE)
			.estimatedEffort(BigDecimal.ONE)
			.issueEffort(MOCK_EFFORT_1_30)
			.aggregatedEffort(MOCK_EFFORT_1_30)
			.name(MOCK_NAME)
			.searchKey(MOCK_SEARCH_KEY)
			.description(MOCK_DESCRIPTION)
			.type(IssueType.EXTERNAL)
			.isEffortIssue(true)
			.processed(true)
			.externalIssueNo(BigDecimal.valueOf(MOCK_EXTERNAL_ISSUE_NO))
			.externalIssueURL(MOCK_EXTERNAL_URL)
			.issueLabels(MOCK_LABELS)
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
		issueRepository.saveWithLabels(MOCK_ISSUE_ENTITY);

		IssueEntity storedEntity = issueRepository.getById(MOCK_ISSUE_ENTITY.getIssueId(), true);

		Assert.assertEquals(storedEntity, MOCK_ISSUE_ENTITY);

		//2. update labels for existing record
		final IssueEntity withDiffLabel = MOCK_ISSUE_ENTITY
				.toBuilder()
				.issueLabels(ImmutableList.of(
						IssueLabel.builder().value(MOCK_EST_4_25_LABEL).orgId(MOCK_ORG_ID).build(),
						IssueLabel.builder().orgId(MOCK_ORG_ID).value(MOCK_BUD_6_LABEL).build()))
				.build();

		issueRepository.saveWithLabels(withDiffLabel);

		storedEntity = issueRepository.getById(withDiffLabel.getIssueId(), true);

		Assert.assertEquals(storedEntity, withDiffLabel);
	}

}
