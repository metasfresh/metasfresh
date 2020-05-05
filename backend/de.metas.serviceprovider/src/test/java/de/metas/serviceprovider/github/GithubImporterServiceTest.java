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

package de.metas.serviceprovider.github;

import com.google.common.collect.ImmutableList;
import de.metas.issue.tracking.github.api.v3.model.GithubMilestone;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.model.Label;
import de.metas.issue.tracking.github.api.v3.model.RetrieveIssuesRequest;
import de.metas.issue.tracking.github.api.v3.service.GithubClient;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetailType;
import de.metas.serviceprovider.importer.ImportIssuesQueue;
import de.metas.serviceprovider.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.milestone.Milestone;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static de.metas.serviceprovider.TestConstants.MOCK_BUG_6_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_ISO_8601;
import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EST_4_25_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ISSUE_NO;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_OWNER;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_TYPE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_REFERENCE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_OAUTH_TOKEN;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PROJECT_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_VALUE;
import static de.metas.serviceprovider.github.GithubImporterConstants.CHUNK_SIZE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class GithubImporterServiceTest
{
	private final GithubClient mockGithubClient = Mockito.mock(GithubClient.class);
	private final ImportIssuesQueue importIssuesQueue = new ImportIssuesQueue();
	private final GithubImporterService githubImporterService = new GithubImporterService(importIssuesQueue, mockGithubClient);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void importIssue()
	{
		//Given
		final ImportIssuesRequest importIssuesRequest = ImportIssuesRequest
				.builder()
				.externalProjectType(MOCK_EXTERNAL_PROJECT_TYPE)
				.issueNoList(ImmutableList.of())
				.oAuthToken(MOCK_OAUTH_TOKEN)
				.orgId(MOCK_ORG_ID)
				.projectId(MOCK_PROJECT_ID)
				.repoOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.repoId(MOCK_EXTERNAL_REFERENCE)
				.build();

		mockGithubClientFetchIssues();

		//when
		githubImporterService.importIssues(importIssuesRequest);

		//then
		final ImmutableList<ImportIssueInfo> importIssueInfos = importIssuesQueue.drainAll();

		assertEquals(importIssueInfos.size(), 1);
		final ImportIssueInfo issueInfo = importIssueInfos.get(0);

		assertEquals(issueInfo.getBudget(), BigDecimal.valueOf(6));
		assertEquals(issueInfo.getEstimation(), BigDecimal.valueOf(4.25));
		assertEquals(issueInfo.getDescription(), MOCK_DESCRIPTION);
		assertEquals(issueInfo.getExternalIssueId(), MOCK_EXTERNAL_ID);
		assertEquals(issueInfo.getExternalIssueNo(), MOCK_EXTERNAL_ISSUE_NO);
		assertEquals(issueInfo.getExternalIssueURL(), MOCK_EXTERNAL_URL);
		assertEquals(issueInfo.getName(), MOCK_NAME);
		assertEquals(issueInfo.getOrgId(), MOCK_ORG_ID);
		assertEquals(issueInfo.getProjectId(), MOCK_PROJECT_ID);
		assertEquals(issueInfo.getExternalProjectType(), MOCK_EXTERNAL_PROJECT_TYPE);

		final Milestone milestone = issueInfo.getMilestone();
		assertNotNull(milestone);
		assertEquals(milestone.getName(), MOCK_NAME);
		assertEquals(milestone.getDescription(), MOCK_DESCRIPTION);
		assertEquals(milestone.getExternalId(), MOCK_EXTERNAL_ID);
		assertEquals(milestone.getExternalURL(), MOCK_EXTERNAL_URL);
		assertEquals(milestone.getOrgId(), MOCK_ORG_ID);
		assertEquals(milestone.getDueDate(), MOCK_INSTANT);

		final ImmutableList<ExternalIssueDetail> issueDetails = issueInfo.getExternalIssueDetails();
		assertNotNull(issueDetails);
		assertEquals(issueDetails.size(), 3);

		assertEquals(issueDetails.get(0).getValue(), MOCK_VALUE);
		assertEquals(issueDetails.get(1).getValue(), MOCK_BUG_6_LABEL);
		assertEquals(issueDetails.get(2).getValue(), MOCK_EST_4_25_LABEL);
		issueDetails.forEach(externalIssueDetail -> assertEquals(externalIssueDetail.getType(), ExternalIssueDetailType.LABEL));
	}

	private void mockGithubClientFetchIssues()
	{
		final ImmutableList<Label> labels = ImmutableList.of(
				Label.builder().name(MOCK_VALUE).build(),
				Label.builder().name(MOCK_BUG_6_LABEL).build(),
				Label.builder().name(MOCK_EST_4_25_LABEL).build()
		);

		final GithubMilestone githubMilestone = GithubMilestone
				.builder()
				.description(MOCK_DESCRIPTION)
				.title(MOCK_NAME)
				.dueDate(MOCK_DATE_ISO_8601)
				.htmlUrl(MOCK_EXTERNAL_URL)
				.id(MOCK_EXTERNAL_ID)
				.dueDate(MOCK_DATE_ISO_8601)
				.build();

		final Issue issue = Issue.builder()
				.htmlUrl(MOCK_EXTERNAL_URL)
				.body(MOCK_DESCRIPTION)
				.id(MOCK_EXTERNAL_ID)
				.number(MOCK_EXTERNAL_ISSUE_NO)
				.title(MOCK_NAME)
				.labelList(labels)
				.githubMilestone(githubMilestone)
				.build();

		final RetrieveIssuesRequest retrieveIssuesRequest_1 = getMockRetrieveIssueRequest(1);
		final RetrieveIssuesRequest retrieveIssuesRequest_2 = getMockRetrieveIssueRequest(2);

		when(mockGithubClient.fetchIssues(retrieveIssuesRequest_1)).thenReturn(ImmutableList.of(issue));
		when(mockGithubClient.fetchIssues(retrieveIssuesRequest_2)).thenReturn(ImmutableList.of());
	}

	private RetrieveIssuesRequest getMockRetrieveIssueRequest(final int pageIndex)
	{
		return RetrieveIssuesRequest.builder()
				.pageIndex(pageIndex)
				.pageSize(CHUNK_SIZE)
				.repositoryId(MOCK_EXTERNAL_REFERENCE)
				.repositoryOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.oAuthToken(MOCK_OAUTH_TOKEN)
				.build();
	}

}
