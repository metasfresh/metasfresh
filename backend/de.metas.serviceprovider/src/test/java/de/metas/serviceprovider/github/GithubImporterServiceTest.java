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
import com.google.common.collect.ImmutableSet;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.issue.tracking.github.api.v3.model.FetchIssueByIdRequest;
import de.metas.issue.tracking.github.api.v3.model.GithubMilestone;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.model.Label;
import de.metas.issue.tracking.github.api.v3.model.RetrieveIssuesRequest;
import de.metas.issue.tracking.github.api.v3.service.GithubClient;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.github.label.LabelService;
import de.metas.serviceprovider.github.link.GithubIssueLinkMatcher;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.issue.importer.info.ImportMilestoneInfo;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.HashMap;

import static de.metas.serviceprovider.TestConstants.MOCK_AUTH_TOKEN;
import static de.metas.serviceprovider.TestConstants.MOCK_BUD_6_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_AND_TIME_ISO_8601;
import static de.metas.serviceprovider.TestConstants.MOCK_DESCRIPTION;
import static de.metas.serviceprovider.TestConstants.MOCK_EST_4_25_LABEL;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ISSUE_NO;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_OWNER;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_PROJECT_TYPE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_REFERENCE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_SYSTEM;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT;
import static de.metas.serviceprovider.TestConstants.MOCK_ISSUE_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PARENT_EXTERNAL_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_PARENT_ISSUE_NO;
import static de.metas.serviceprovider.TestConstants.MOCK_PARENT_ISSUE_URL;
import static de.metas.serviceprovider.TestConstants.MOCK_PROJECT_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_VALUE;
import static de.metas.serviceprovider.github.GithubImporterConstants.CHUNK_SIZE;
import static de.metas.serviceprovider.issue.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;
import static de.metas.serviceprovider.issue.importer.ImportConstants.ISSUE_QUEUE_CAPACITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class GithubImporterServiceTest
{
	private final GithubClient mockGithubClient = Mockito.mock(GithubClient.class);

	private final ImportQueue<ImportIssueInfo> importIssuesQueue =
			new ImportQueue<>(ISSUE_QUEUE_CAPACITY,IMPORT_LOG_MESSAGE_PREFIX);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IModelCacheInvalidationService modelCacheInvalidationService =  Services.get(IModelCacheInvalidationService.class);

	private final ExternalReferenceRepository externalReferenceRepository = new ExternalReferenceRepository(queryBL);

	private final IssueRepository issueRepository = new IssueRepository(queryBL, modelCacheInvalidationService);

	private final ExternalProjectRepository externalProjectRepository = new ExternalProjectRepository(queryBL);

	private final LabelService labelService = new LabelService();

	private final GithubImporterService githubImporterService =
			new GithubImporterService(importIssuesQueue, mockGithubClient, externalReferenceRepository, issueRepository, externalProjectRepository, labelService);
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void importIssue()
	{
		//Given
		final ImportIssuesRequest importIssuesRequest = buildMockImportIssuesRequest(null);

		final Issue mockIssue_1 = buildMockIssue(MOCK_EXTERNAL_ISSUE_NO, MOCK_DESCRIPTION, MOCK_EXTERNAL_ID);

		mockGithubClientFetchIssues(mockIssue_1);

		final HashMap<GithubIdSearchKey, String> seendIds = new HashMap<>();

		//when
		githubImporterService.importIssues(importIssuesRequest, seendIds);

		//then
		assertEquals(seendIds.size(), 1);
		final GithubIdSearchKey idSearchKey = GithubIdSearchKey.builder()
				.issueNo(MOCK_EXTERNAL_ISSUE_NO.toString())
				.repository(MOCK_EXTERNAL_REFERENCE)
				.repositoryOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.build();

		assertEquals(seendIds.get(idSearchKey), MOCK_EXTERNAL_ID);

		final ImmutableList<ImportIssueInfo> importIssueInfos = importIssuesQueue.drainAll();

		assertEquals(importIssueInfos.size(), 1);
		final ImportIssueInfo issueInfo = importIssueInfos.get(0);

		assertEquals(issueInfo.getBudget(), BigDecimal.valueOf(6));
		assertEquals(issueInfo.getEstimation(), BigDecimal.valueOf(4.25));
		assertEquals(issueInfo.getDescription(), MOCK_DESCRIPTION);
		assertEquals(issueInfo.getExternalIssueId().getId(), MOCK_EXTERNAL_ID);
		assertEquals(issueInfo.getExternalIssueId().getExternalSystem(), ExternalSystem.GITHUB);
		assertEquals(issueInfo.getExternalIssueNo(), MOCK_EXTERNAL_ISSUE_NO);
		assertEquals(issueInfo.getExternalIssueURL(), MOCK_EXTERNAL_URL);
		assertEquals(issueInfo.getName(), MOCK_NAME);
		assertEquals(issueInfo.getOrgId(), MOCK_ORG_ID);
		assertEquals(issueInfo.getProjectId(), MOCK_PROJECT_ID);
		assertEquals(issueInfo.getExternalProjectType(), MOCK_EXTERNAL_PROJECT_TYPE);

		checkMilestoneAndLabels(issueInfo);
	}

	@Test
	public void importIssuesWithParent()
	{
		//given
		prepareDataContext();

		final ImportIssuesRequest importIssuesRequest = buildMockImportIssuesRequest(buildMockGithubIssueLinkMatcher());

		final Issue mockIssue_1 = buildMockIssue(MOCK_EXTERNAL_ISSUE_NO, MOCK_PARENT_ISSUE_URL, MOCK_EXTERNAL_ID);
		final Issue mockIssue_2 = buildMockIssue(MOCK_PARENT_ISSUE_NO, MOCK_ISSUE_URL, MOCK_PARENT_EXTERNAL_ID);

		mockGithubClientFetchIssues(mockIssue_1);

		final FetchIssueByIdRequest fetchParentRequest = getMockFetchIssueByIdRequest(MOCK_PARENT_ISSUE_NO);
		when(mockGithubClient.fetchIssueById(fetchParentRequest)).thenReturn(mockIssue_2);

		final HashMap<GithubIdSearchKey, String> seenIds = new HashMap<>();

		//when
		githubImporterService.importIssues(importIssuesRequest, seenIds);

		//then
		checkSeenIds(seenIds);

		checkImportIssueInfoQueue(importIssuesQueue.drainAll());
	}

	private void mockGithubClientFetchIssues(final Issue mockIssue)
	{
		final RetrieveIssuesRequest retrieveIssuesRequest_1 = getMockRetrieveIssueRequest(1);
		final RetrieveIssuesRequest retrieveIssuesRequest_2 = getMockRetrieveIssueRequest(2);

		when(mockGithubClient.fetchIssues(retrieveIssuesRequest_1)).thenReturn(ImmutableList.of(mockIssue));
		when(mockGithubClient.fetchIssues(retrieveIssuesRequest_2)).thenReturn(ImmutableList.of());
	}

	private RetrieveIssuesRequest getMockRetrieveIssueRequest(final int pageIndex)
	{
		return RetrieveIssuesRequest.builder()
				.pageIndex(pageIndex)
				.pageSize(CHUNK_SIZE)
				.repositoryId(MOCK_EXTERNAL_REFERENCE)
				.repositoryOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.oAuthToken(MOCK_AUTH_TOKEN)
				.build();
	}

	private FetchIssueByIdRequest getMockFetchIssueByIdRequest(final Integer issueNo)
	{
		return FetchIssueByIdRequest.builder()
				.repositoryId(MOCK_EXTERNAL_REFERENCE)
				.repositoryOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.oAuthToken(MOCK_AUTH_TOKEN)
				.issueNumber(issueNo.toString())
				.build();
	}

	private Issue buildMockIssue(final Integer issueNo, final String description, final String externalId)
	{
		final ImmutableList<Label> labels = ImmutableList.of(
				Label.builder().name(MOCK_VALUE).build(),
				Label.builder().name(MOCK_BUD_6_LABEL).build(),
				Label.builder().name(MOCK_EST_4_25_LABEL).build()
		);

		final GithubMilestone githubMilestone = GithubMilestone
				.builder()
				.description(MOCK_DESCRIPTION)
				.title(MOCK_NAME)
				.dueDate(MOCK_DATE_AND_TIME_ISO_8601)
				.htmlUrl(MOCK_EXTERNAL_URL)
				.id(MOCK_EXTERNAL_ID)
				.dueDate(MOCK_DATE_AND_TIME_ISO_8601)
				.build();

		return Issue.builder()
				.htmlUrl(MOCK_EXTERNAL_URL)
				.body(description)
				.id(externalId)
				.number(issueNo)
				.title(MOCK_NAME)
				.labelList(labels)
				.githubMilestone(githubMilestone)
				.build();
	}

	private ImportIssuesRequest buildMockImportIssuesRequest(final GithubIssueLinkMatcher githubIssueLinkMatcher)
	{
		return ImportIssuesRequest
				.builder()
				.externalProjectReferenceId(MOCK_EXTERNAL_PROJECT_REFERENCE_ID_ACTIVE)
				.externalProjectType(MOCK_EXTERNAL_PROJECT_TYPE)
				.issueNoList(ImmutableList.of())
				.oAuthToken(MOCK_AUTH_TOKEN)
				.orgId(MOCK_ORG_ID)
				.projectId(MOCK_PROJECT_ID)
				.repoOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.repoId(MOCK_EXTERNAL_REFERENCE)
				.githubIssueLinkMatcher(githubIssueLinkMatcher)
				.build();
	}

	private GithubIssueLinkMatcher buildMockGithubIssueLinkMatcher()
	{
		return GithubIssueLinkMatcher.of(ImmutableSet.of(MOCK_EXTERNAL_PROJECT_OWNER), ImmutableSet.of(MOCK_EXTERNAL_REFERENCE));
	}

	private void prepareDataContext()
	{
		final I_S_ExternalProjectReference projectRecord = InterfaceWrapperHelper.newInstance(I_S_ExternalProjectReference.class);
		projectRecord.setProjectType(MOCK_EXTERNAL_PROJECT_TYPE.getValue());
		projectRecord.setExternalSystem(MOCK_EXTERNAL_SYSTEM.getValue());
		projectRecord.setAD_Org_ID(MOCK_ORG_ID.getRepoId());
		projectRecord.setC_Project_ID(MOCK_PROJECT_ID.getRepoId());
		projectRecord.setExternalReference(MOCK_EXTERNAL_REFERENCE);
		projectRecord.setExternalProjectOwner(MOCK_EXTERNAL_PROJECT_OWNER);

		InterfaceWrapperHelper.saveRecord(projectRecord);
	}

	private void checkSeenIds(final HashMap<GithubIdSearchKey, String> seendIds)
	{
		final GithubIdSearchKey childIdSearchKey = GithubIdSearchKey.builder()
				.issueNo(MOCK_EXTERNAL_ISSUE_NO.toString())
				.repository(MOCK_EXTERNAL_REFERENCE)
				.repositoryOwner(MOCK_EXTERNAL_PROJECT_OWNER)
				.build();
		final GithubIdSearchKey parentIdSearchKey = childIdSearchKey.toBuilder().issueNo(MOCK_PARENT_ISSUE_NO.toString()).build();

		assertEquals(seendIds.size(), 2);
		assertEquals(seendIds.get(childIdSearchKey), MOCK_EXTERNAL_ID);
		assertEquals(seendIds.get(parentIdSearchKey), MOCK_PARENT_EXTERNAL_ID);
	}

	private void checkImportIssueInfoQueue(final ImmutableList<ImportIssueInfo> importIssueInfos)
	{
		//only 2 issues should be in the queue, even though the parent issue references its kid in description
		// the kid won't be retrieved again.
		assertEquals(importIssueInfos.size(), 2);

		//the parent issue should be first in queue
		final ImportIssueInfo parentIssue = importIssueInfos.get(0);

		assertEquals(parentIssue.getBudget(), BigDecimal.valueOf(6));
		assertEquals(parentIssue.getEstimation(), BigDecimal.valueOf(4.25));
		assertEquals(parentIssue.getDescription(), MOCK_ISSUE_URL);
		assertEquals(parentIssue.getExternalIssueId().getId(), MOCK_PARENT_EXTERNAL_ID);
		assertEquals(parentIssue.getExternalIssueId().getExternalSystem(), ExternalSystem.GITHUB);
		assertEquals(parentIssue.getExternalIssueNo(), MOCK_PARENT_ISSUE_NO);
		assertEquals(parentIssue.getExternalIssueURL(), MOCK_EXTERNAL_URL);
		assertEquals(parentIssue.getName(), MOCK_NAME);
		assertEquals(parentIssue.getOrgId(), MOCK_ORG_ID);
		assertEquals(parentIssue.getProjectId(), MOCK_PROJECT_ID);
		assertEquals(parentIssue.getExternalProjectType(), MOCK_EXTERNAL_PROJECT_TYPE);

		checkMilestoneAndLabels(parentIssue);

		//check child
		final ImportIssueInfo childIssue = importIssueInfos.get(1);
		assertEquals(childIssue.getBudget(), BigDecimal.valueOf(6));
		assertEquals(childIssue.getEstimation(), BigDecimal.valueOf(4.25));
		assertEquals(childIssue.getDescription(), MOCK_PARENT_ISSUE_URL);
		assertEquals(childIssue.getExternalIssueId().getId(), MOCK_EXTERNAL_ID);
		assertEquals(childIssue.getExternalIssueId().getExternalSystem(), ExternalSystem.GITHUB);
		assertEquals(childIssue.getExternalIssueNo(), MOCK_EXTERNAL_ISSUE_NO);
		assertEquals(childIssue.getExternalIssueURL(), MOCK_EXTERNAL_URL);
		assertEquals(childIssue.getName(), MOCK_NAME);
		assertEquals(childIssue.getOrgId(), MOCK_ORG_ID);
		assertEquals(childIssue.getProjectId(), MOCK_PROJECT_ID);
		assertEquals(childIssue.getExternalProjectType(), MOCK_EXTERNAL_PROJECT_TYPE);
		assertEquals(childIssue.getExternalParentIssueId().getId(), MOCK_PARENT_EXTERNAL_ID);

		checkMilestoneAndLabels(childIssue);
	}

	private void checkMilestoneAndLabels(final ImportIssueInfo issueInfo)
	{
		final ImportMilestoneInfo milestone = issueInfo.getMilestone();
		assertNotNull(milestone);
		assertEquals(milestone.getName(), MOCK_NAME);
		assertEquals(milestone.getDescription(), MOCK_DESCRIPTION);
		assertEquals(milestone.getExternalId().getId(), MOCK_EXTERNAL_ID);
		assertEquals(milestone.getExternalId().getExternalSystem(), ExternalSystem.GITHUB);
		assertEquals(milestone.getExternalURL(), MOCK_EXTERNAL_URL);
		assertEquals(milestone.getOrgId(), MOCK_ORG_ID);
		assertEquals(milestone.getDueDate(), MOCK_INSTANT);

		final ImmutableList<IssueLabel> issueLabels = issueInfo.getIssueLabels();
		assertNotNull(issueLabels);
		assertEquals(issueLabels.size(), 3);

		assertEquals(issueLabels.get(0).getValue(), MOCK_VALUE);
		assertEquals(issueLabels.get(1).getValue(), MOCK_BUD_6_LABEL);
		assertEquals(issueLabels.get(2).getValue(), MOCK_EST_4_25_LABEL);
	}
}
