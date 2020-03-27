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

import ch.qos.logback.classic.Level;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.issue.tracking.github.api.v3.model.FetchIssueByIdRequest;
import de.metas.issue.tracking.github.api.v3.model.GithubMilestone;
import de.metas.issue.tracking.github.api.v3.model.Issue;
import de.metas.issue.tracking.github.api.v3.model.Label;
import de.metas.issue.tracking.github.api.v3.model.ResourceState;
import de.metas.issue.tracking.github.api.v3.model.RetrieveIssuesRequest;
import de.metas.issue.tracking.github.api.v3.service.GithubClient;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.issuedetails.ExternalIssueDetail;
import de.metas.serviceprovider.importer.ImportIssuesQueue;
import de.metas.serviceprovider.importer.ImportService;
import de.metas.serviceprovider.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.milestone.Milestone;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.LabelType.BUDGET;
import static de.metas.issue.tracking.github.api.v3.GitHubApiConstants.LabelType.ESTIMATION;
import static de.metas.serviceprovider.external.issuedetails.ExternalIssueDetailType.LABEL;
import static de.metas.serviceprovider.github.GithubImporterConstants.CHUNK_SIZE;
import static de.metas.serviceprovider.github.GithubImporterConstants.HOUR_UOM_ID;
import static de.metas.serviceprovider.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;

@Service
public class GithubImporterService implements ImportService
{
	private static final Logger log = LogManager.getLogger(GithubImporterService.class);

	private final ReentrantLock lock = new ReentrantLock();

	private final ImportIssuesQueue importIssuesQueue;
	private final GithubClient githubClient;
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	public GithubImporterService(final ImportIssuesQueue importIssuesQueue, final GithubClient githubClient)
	{
		this.importIssuesQueue = importIssuesQueue;
		this.githubClient = githubClient;
	}

	public void start(@NonNull final ImmutableList<ImportIssuesRequest> requestList)
	{
		acquireLock();
		try
		{
			final Stopwatch stopWatch = Stopwatch.createStarted();

			requestList.forEach(request -> {
				if (request.importByIds())
				{
					importIssuesById(request);
				}
				else
				{
					importIssues(request);
				}
			});
			log.info(IMPORT_LOG_MESSAGE_PREFIX + " GithubImporterService#start() finished work in {} sec. ", stopWatch.stop().elapsed(TimeUnit.SECONDS));
		}
		catch (final Exception ex)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(IMPORT_LOG_MESSAGE_PREFIX + ex.getMessage(), ex);
		}
		finally
		{
			releaseLock();
		}
	}

	private void importIssues(@NonNull final ImportIssuesRequest importIssuesRequest )
	{
		int chunkIndex = 1;

		boolean areRemainingIssues = true;

		while (areRemainingIssues)
		{
			log.info(IMPORT_LOG_MESSAGE_PREFIX + "Retrieving issues from repoId:{}, owner:{}, chunkIndex: {}", importIssuesRequest.getRepoId(),
					importIssuesRequest.getRepoOwner(), chunkIndex);

			final RetrieveIssuesRequest retrieveIssuesRequest = RetrieveIssuesRequest.builder()
					.oAuthToken(importIssuesRequest.getOAuthToken())
					.repositoryOwner(importIssuesRequest.getRepoOwner())
					.repositoryId(importIssuesRequest.getRepoId())
					.pageSize(CHUNK_SIZE)
					.pageIndex(chunkIndex)
					.build();

			final ImmutableList<Issue> issues = githubClient.fetchIssues(retrieveIssuesRequest);

			chunkIndex++;

			areRemainingIssues = !issues.isEmpty();

			issues.stream()
					.map(issue -> buildImportIssueInfo(issue, importIssuesRequest))
					.forEach(importIssuesQueue::add);
		}
	}

	private void importIssuesById(@NonNull final ImportIssuesRequest importIssuesRequest)
	{
		importIssuesRequest.getIssueNoList()
				.stream()
				.map(issueNo -> FetchIssueByIdRequest.builder()
						.oAuthToken(importIssuesRequest.getOAuthToken())
						.repositoryOwner(importIssuesRequest.getRepoOwner())
						.repositoryId(importIssuesRequest.getRepoId())
						.issueNumber(issueNo)
						.build())
				.map(githubClient::fetchIssueById)
				.map(issue -> buildImportIssueInfo(issue, importIssuesRequest))
				.forEach(importIssuesQueue::add);
	}

	@NonNull
	private ImportIssueInfo buildImportIssueInfo(@NonNull final Issue issue,@NonNull final ImportIssuesRequest importIssuesRequest)
	{
		final ImportIssueInfo.ImportIssueInfoBuilder importInfoBuilder = ImportIssueInfo
				.builder()
				.externalProjectType(importIssuesRequest.getExternalProjectType())
				.externalIssueId(issue.getId())
				.externalIssueURL(issue.getHtmlUrl())
				.externalIssueNo(issue.getNumber())
				.name(issue.getTitle())
				.description(issue.getBody())
				.processed(ResourceState.CLOSED.getValue().equals(issue.getState()))
				.orgId(importIssuesRequest.getOrgId())
				.effortUomId(HOUR_UOM_ID);

		if (issue.getGithubMilestone() != null)
		{
			importInfoBuilder.milestone(buildMilestone(issue.getGithubMilestone()));
		}

		if (issue.getAssignee() != null)
		{
			final UserId userId = userDAO.retrieveUserIdByLogin(issue.getAssignee().getLogin());

			importInfoBuilder.assigneeId(userId);
		}

		processLabels(issue.getLabelList(), importInfoBuilder, importIssuesRequest.getOrgId());

		return importInfoBuilder.build();
	}

	private Milestone buildMilestone(@NonNull final GithubMilestone githubMilestone)
	{
		return Milestone.builder()
				.name(githubMilestone.getTitle())
				.description(githubMilestone.getDescription())
				.externalId(githubMilestone.getId())
				.processed(ResourceState.CLOSED.getValue().equals(githubMilestone.getState()))
				.dueDate(githubMilestone.getDueDate())
				.value(githubMilestone.getTitle())
				.orgId(OrgId.ANY)
				.build();
	}


	private void processLabels(final List<Label> labelList,
			@NonNull final ImportIssueInfo.ImportIssueInfoBuilder importIssueInfoBuilder,
			@NonNull final OrgId orgId)
	{
		final List<ExternalIssueDetail> externalIssueDetailList = new ArrayList<>();
		BigDecimal budget = BigDecimal.ZERO;
		BigDecimal estimation = BigDecimal.ZERO;

		if (!Check.isEmpty(labelList))
		{
			for (final Label label : labelList)
			{
				budget = budget.add(getValueFromLabel(label, BUDGET.getPattern()));
				estimation = estimation.add(getValueFromLabel(label, ESTIMATION.getPattern()));

				final ExternalIssueDetail externalIssueDetail = ExternalIssueDetail.builder()
						.type(LABEL)
						.detailValue(label.getName())
						.orgId(orgId)
						.build();

				externalIssueDetailList.add(externalIssueDetail);
			}
		}

		importIssueInfoBuilder.budget(budget);
		importIssueInfoBuilder.estimation(estimation);
		importIssueInfoBuilder.externalIssueDetails(externalIssueDetailList);
	}


	@NonNull
	private BigDecimal getValueFromLabel(final Label label, final Pattern valuePattern)
	{
		final Matcher matcher = valuePattern.matcher(label.getName());

		return matcher.matches() ? NumberUtils.asBigDecimal(matcher.group(1)) : BigDecimal.ZERO;

	}

	private void acquireLock()
	{
		final boolean lockAcquired = lock.tryLock();

		if (!lockAcquired)
		{
			throw new AdempiereException("The import is already running!");
		}

		log.debug(IMPORT_LOG_MESSAGE_PREFIX + " GithubImporterService: lock acquired, starting the import!");
	}

	private void releaseLock()
	{
		lock.unlock();

		log.debug(IMPORT_LOG_MESSAGE_PREFIX + " GithubImporterService: lock released!");
	}
}
