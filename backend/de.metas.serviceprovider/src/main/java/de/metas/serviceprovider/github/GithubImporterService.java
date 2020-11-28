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
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
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
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.label.IssueLabel;
import de.metas.serviceprovider.external.project.ExternalProjectReference;
import de.metas.serviceprovider.external.project.ExternalProjectRepository;
import de.metas.serviceprovider.external.project.GetExternalProjectRequest;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.external.reference.GetReferencedIdRequest;
import de.metas.serviceprovider.github.label.LabelService;
import de.metas.serviceprovider.github.label.ProcessedLabel;
import de.metas.serviceprovider.github.link.GithubIssueLink;
import de.metas.serviceprovider.github.link.GithubIssueLinkMatcher;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.importer.IssueImporter;
import de.metas.serviceprovider.issue.importer.info.ImportIssueInfo;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;
import de.metas.serviceprovider.issue.importer.info.ImportMilestoneInfo;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static de.metas.serviceprovider.github.GithubImporterConstants.CHUNK_SIZE;
import static de.metas.serviceprovider.github.GithubImporterConstants.HOUR_UOM_ID;
import static de.metas.serviceprovider.github.GithubImporterConstants.LABEL_DATE_FORMAT;
import static de.metas.serviceprovider.issue.importer.ImportConstants.IMPORT_LOG_MESSAGE_PREFIX;

@Service
public class GithubImporterService implements IssueImporter
{
	private static final Logger log = LogManager.getLogger(GithubImporterService.class);

	private final ReentrantLock lock = new ReentrantLock();

	private final ImportQueue<ImportIssueInfo> importIssuesQueue;
	private final GithubClient githubClient;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final IssueRepository issueRepository;
	private final ExternalProjectRepository externalProjectRepository;
	private final LabelService labelService;

	public GithubImporterService(final ImportQueue<ImportIssueInfo> importIssuesQueue, final GithubClient githubClient, final ExternalReferenceRepository externalReferenceRepository, final IssueRepository issueRepository, final ExternalProjectRepository externalProjectRepository, final LabelService labelService)
	{
		this.importIssuesQueue = importIssuesQueue;
		this.githubClient = githubClient;
		this.externalReferenceRepository = externalReferenceRepository;
		this.issueRepository = issueRepository;
		this.externalProjectRepository = externalProjectRepository;
		this.labelService = labelService;
	}

	public void start(@NonNull final ImmutableList<ImportIssuesRequest> requestList)
	{
		acquireLock();
		final HashMap<GithubIdSearchKey, String> seenExternalIdsByKey = new HashMap<>();

		try
		{
			final Stopwatch stopWatch = Stopwatch.createStarted();

			requestList.forEach(request -> {
				if (request.importByIds())
				{
					importIssuesById(request, seenExternalIdsByKey);
				}
				else
				{
					importIssues(request, seenExternalIdsByKey);
				}
			});
			Loggables.withLogger(log, Level.INFO).addLog(" {} GithubImporterService#start() finished work in {}. ",
					IMPORT_LOG_MESSAGE_PREFIX, stopWatch.stop());
		}
		catch (final Exception ex)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(IMPORT_LOG_MESSAGE_PREFIX + ex.getMessage(), ex);

			throw AdempiereException.wrapIfNeeded(ex)
					.appendParametersToMessage()
					.setParameter("requestList", requestList);
		}
		finally
		{
			releaseLock();
		}
	}

	@VisibleForTesting
	protected void importIssues(@NonNull final ImportIssuesRequest importIssuesRequest, @NonNull final HashMap<GithubIdSearchKey, String> seenExternalIdsByKey )
	{
		int chunkIndex = 1;

		boolean areRemainingIssues = true;

		while (areRemainingIssues)
		{
			log.info(" {} Retrieving issues from repoId:{}, owner:{}, chunkIndex: {}", IMPORT_LOG_MESSAGE_PREFIX, importIssuesRequest.getRepoId(),
					importIssuesRequest.getRepoOwner(), chunkIndex);

			final RetrieveIssuesRequest retrieveIssuesRequest = RetrieveIssuesRequest.builder()
					.oAuthToken(importIssuesRequest.getOAuthToken())
					.repositoryOwner(importIssuesRequest.getRepoOwner())
					.repositoryId(importIssuesRequest.getRepoId())
					.pageSize(CHUNK_SIZE)
					.pageIndex(chunkIndex)
					.dateFrom(importIssuesRequest.getDateFrom())
					.build();

			final ImmutableList<Issue> issues = githubClient.fetchIssues(retrieveIssuesRequest);

			chunkIndex++;

			areRemainingIssues = !issues.isEmpty();

			issues.stream()
					.map(issue -> buildImportIssueInfo(issue, importIssuesRequest, seenExternalIdsByKey))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.forEach(importIssuesQueue::add);
		}
	}

	private void importIssuesById(@NonNull final ImportIssuesRequest importIssuesRequest,
			                      @NonNull final  HashMap<GithubIdSearchKey, String> seenExternalIdsByKey)
	{
		if (!importIssuesRequest.importByIds())
		{
			throw new AdempiereException("GithubImporterService.importIssuesById() should be called only for importIssuesRequest.importByIds() requests!")
					.appendParametersToMessage()
					.setParameter("ImportIssuesRequest", importIssuesRequest);
		}

		importIssuesRequest.getIssueNoList()
				.stream()
				.map(issueNo -> FetchIssueByIdRequest.builder()
						.oAuthToken(importIssuesRequest.getOAuthToken())
						.repositoryOwner(importIssuesRequest.getRepoOwner())
						.repositoryId(importIssuesRequest.getRepoId())
						.issueNumber(issueNo)
						.build())
				.map(githubClient::fetchIssueById)
				.map(issue -> buildImportIssueInfo(issue, importIssuesRequest, seenExternalIdsByKey))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(importIssuesQueue::add);
	}

	@NonNull
	private Optional<ImportIssueInfo> buildImportIssueInfo(@NonNull final Issue issue,
			                                     @NonNull final ImportIssuesRequest importIssuesRequest,
			 									 @NonNull final  HashMap<GithubIdSearchKey, String> seenExternalIdsByKey)
	{
		final GithubIdSearchKey githubIdSearchKey = GithubIdSearchKey
				.builder()
				.repository(importIssuesRequest.getRepoId())
				.repositoryOwner(importIssuesRequest.getRepoOwner())
				.issueNo(String.valueOf(issue.getNumber()))
				.build();

		if (seenExternalIdsByKey.put(githubIdSearchKey, issue.getId()) != null)
		{
			//means it was already imported once in this process run so it will be skipped
			return Optional.empty();
		}

		final ImportIssueInfo.ImportIssueInfoBuilder importInfoBuilder = ImportIssueInfo
				.builder()
				.externalProjectReferenceId(importIssuesRequest.getExternalProjectReferenceId())
				.externalProjectType(importIssuesRequest.getExternalProjectType())
				.externalIssueId(ExternalId.of(ExternalSystem.GITHUB, issue.getId()))
				.externalIssueURL(issue.getHtmlUrl())
				.externalIssueNo(issue.getNumber())
				.name(issue.getTitle())
				.description(issue.getBody())
				.orgId(importIssuesRequest.getOrgId())
				.projectId(importIssuesRequest.getProjectId())
				.effortUomId(HOUR_UOM_ID);

		if (issue.getGithubMilestone() != null)
		{
			importInfoBuilder.milestone(buildMilestone(issue.getGithubMilestone(), importIssuesRequest.getOrgId()));
		}

		if (issue.getAssignee() != null)
		{
			importInfoBuilder.assigneeId(getUserIdByExternalId(issue.getAssignee().getId()));
		}

		processLabels(issue.getLabelList(), importInfoBuilder, importIssuesRequest.getOrgId());

		if (ResourceState.CLOSED.getValue().equals(issue.getState()))
		{
			importInfoBuilder.status(Status.CLOSED);
		}

		lookForParentIssue(importIssuesRequest, importInfoBuilder, seenExternalIdsByKey, issue.getBody());

		return Optional.of(importInfoBuilder.build());
	}

	@NonNull
	private ImportMilestoneInfo buildMilestone(@NonNull final GithubMilestone githubMilestone, @NonNull final OrgId orgId)
	{
		return ImportMilestoneInfo.builder()
				.name(githubMilestone.getTitle())
				.description(githubMilestone.getDescription())
				.externalURL(githubMilestone.getHtmlUrl())
				.externalId(ExternalId.of(ExternalSystem.GITHUB, githubMilestone.getId()))
				.processed(ResourceState.CLOSED.getValue().equals(githubMilestone.getState()))
				.dueDate(githubMilestone.getDueDate() != null ? Instant.parse(githubMilestone.getDueDate()) : null)
				.orgId(orgId)
				.build();
	}

	@NonNull
	private Optional<LocalDate> getDateFromLabel(@NonNull final String labelDateStr)
	{
		LocalDate date = null;
		try
		{
			date = LocalDate.from(LABEL_DATE_FORMAT.parse(labelDateStr));
		}
		catch (final Exception e)
		{
			log.error("{} : cannot extract date from : {}",
					IMPORT_LOG_MESSAGE_PREFIX, labelDateStr, e);
		}

		return Optional.ofNullable(date);
	}

	@Nullable
	private UserId getUserIdByExternalId(@NonNull final String externalUserId)
	{
		final Integer userId = externalReferenceRepository.getReferencedRecordIdOrNullBy(
				GetReferencedIdRequest.builder()
						.externalSystem(ExternalSystem.GITHUB)
						.externalReference(externalUserId)
						.externalReferenceType(ExternalReferenceType.USER_ID)
						.build());

		return UserId.ofRepoIdOrNullIfSystem(userId);
	}

	private void lookForParentIssue(@NonNull final ImportIssuesRequest importIssuesRequest,
			                        @NonNull final ImportIssueInfo.ImportIssueInfoBuilder issueInfoBuilder,
			                        @NonNull final  HashMap<GithubIdSearchKey, String> seenExternalIdsByKey,
			                        @Nullable final String description)
	{
		final GithubIssueLinkMatcher linkMatcher = importIssuesRequest.getGithubIssueLinkMatcher();

		if (linkMatcher == null || Check.isBlank(description))
		{
			Loggables.withLogger(log, Level.DEBUG)
					.addLog(" {} Skipped searching for parent issue due to missing info! GithubIssueLinkMatcher:"
							+ " {}, issue.description: {} ", IMPORT_LOG_MESSAGE_PREFIX, linkMatcher, description );
			return;
		}

		final Optional<GithubIssueLink> parentIssueLink = linkMatcher.getFirstMatch(description);

		if (!parentIssueLink.isPresent())
		{
			Loggables.withLogger(log, Level.DEBUG)
					.addLog(" {} No match found for parent issue! GithubIssueLinkMatcher: "
							+ "{}, issue.description: {} ", IMPORT_LOG_MESSAGE_PREFIX, linkMatcher, description );
			return;
		}

		//first check if the parent exists in mf
		final Optional<IssueEntity> issueEntity =
				issueRepository.getByExternalURLOptional(parentIssueLink.get().getUrl());

		if (issueEntity.isPresent())
		{
			Loggables.withLogger(log, Level.DEBUG)
					.addLog(" {} Parent issue found in mf for URL: {}, parentIssueId: {}"
							, IMPORT_LOG_MESSAGE_PREFIX, parentIssueLink.get().getUrl(), issueEntity.get().getIssueId() );

			issueInfoBuilder.parentIssueId(issueEntity.get().getIssueId());
		}
		//if it doesn't try to get the externalId of the parent
		else
		{
			getExternalParentId(importIssuesRequest, parentIssueLink.get().getGithubIdSearchKey(), seenExternalIdsByKey)
					.ifPresent(issueInfoBuilder::externalParentIssueId);
		}
	}

	private Optional<ExternalId> getExternalParentId(@NonNull final ImportIssuesRequest initialRequest,
													 @NonNull final GithubIdSearchKey parentIdSearchKey,
													 @NonNull final  HashMap<GithubIdSearchKey, String> seenExternalIdsByKey)
	{
		//first check if it's in the current importing queue
		final String parentExternalId = seenExternalIdsByKey.get(parentIdSearchKey);

		if (!Check.isBlank(parentExternalId))
		{
			Loggables.withLogger(log, Level.DEBUG)
					.addLog(" {} Parent issue found in the current importing queue for searchKey: {}, parentIssueExternalId: {}"
							, IMPORT_LOG_MESSAGE_PREFIX, parentIdSearchKey, parentExternalId );

			return Optional.of(ExternalId.of(ExternalSystem.GITHUB, parentExternalId));
		}
		//if it isn't try to import the parent
		else
		{
			return importParentIssue(initialRequest, parentIdSearchKey, seenExternalIdsByKey);
		}
	}

	private Optional<ExternalId> importParentIssue(@NonNull final ImportIssuesRequest initialRequest,
			                             @NonNull final GithubIdSearchKey parentIdSearchKey,
			                             @NonNull final  HashMap<GithubIdSearchKey, String> seenExternalIdsByKey)
	{
		final GetExternalProjectRequest getExternalProjectRequest = GetExternalProjectRequest
				.builder()
				.externalProjectOwner(parentIdSearchKey.getRepositoryOwner())
				.externalReference(parentIdSearchKey.getRepository())
				.externalSystem(ExternalSystem.GITHUB)
				.build();

		final Optional<ExternalProjectReference> externalProjectReference = externalProjectRepository.getByRequestOptional(getExternalProjectRequest);

		if (!externalProjectReference.isPresent())
		{
			Loggables.withLogger(log, Level.WARN).addLog("No externalProjectReference found for getExternalProjectRequest: " + getExternalProjectRequest);
			return Optional.empty();
		}

		final ImportIssuesRequest importIssuesRequest = ImportIssuesRequest
				.builder()
				.orgId(externalProjectReference.get().getOrgId())
				.externalProjectReferenceId(externalProjectReference.get().getExternalProjectReferenceId())
				.projectId(externalProjectReference.get().getProjectId())
				.repoId(externalProjectReference.get().getExternalProjectReference())
				.repoOwner(externalProjectReference.get().getProjectOwner())
				.externalProjectType(externalProjectReference.get().getExternalProjectType())
				.oAuthToken(initialRequest.getOAuthToken())
				.githubIssueLinkMatcher(initialRequest.getGithubIssueLinkMatcher())
				.issueNoList(ImmutableList.of(parentIdSearchKey.getIssueNo()))
				.build();

		importIssuesById(importIssuesRequest, seenExternalIdsByKey);

		final String parentExternalId = seenExternalIdsByKey.get(parentIdSearchKey);

		if (parentExternalId != null)
		{
			Loggables.withLogger(log, Level.DEBUG)
					.addLog(" {} Parent issue retrieved and added in the importing queue for searchKey: {}, parentIssueExternalId: {}"
							, IMPORT_LOG_MESSAGE_PREFIX, parentIdSearchKey, parentExternalId );

			return Optional.of(ExternalId.of(ExternalSystem.GITHUB, parentExternalId));
		}

		return Optional.empty();
	}

	private void acquireLock()
	{
		final boolean lockAcquired = lock.tryLock();

		if (!lockAcquired)
		{
			throw new AdempiereException("The import is already running!");
		}

		log.debug(" {} GithubImporterService: lock acquired, starting the import!", IMPORT_LOG_MESSAGE_PREFIX);
	}

	private void releaseLock()
	{
		lock.unlock();

		log.debug(" {} GithubImporterService: lock released!", IMPORT_LOG_MESSAGE_PREFIX);
	}

	private void processLabels(final List<Label> labelList,
			@NonNull final ImportIssueInfo.ImportIssueInfoBuilder importIssueInfoBuilder,
			@NonNull final OrgId orgId)
	{
		final ImmutableList<ProcessedLabel> processedLabels = labelService.processLabels(labelList);

		final List<IssueLabel> issueLabelList = new ArrayList<>();
		final List<String> deliveryPlatformList = new ArrayList<>();
		BigDecimal budget = BigDecimal.ZERO;
		BigDecimal estimation = BigDecimal.ZERO;
		BigDecimal roughEstimation = BigDecimal.ZERO;
		Optional<Status> status = Optional.empty();
		Optional<LocalDate> plannedUATDate = Optional.empty();
		Optional<LocalDate> deliveredDate = Optional.empty();

		for (final ProcessedLabel label : processedLabels)
		{
			switch (label.getLabelType())
			{
				case ESTIMATION:
					estimation = estimation.add(NumberUtils.asBigDecimal(label.getExtractedValue(), BigDecimal.ZERO));
					break;
				case ROUGH_EST:
					roughEstimation = roughEstimation.add(NumberUtils.asBigDecimal(label.getExtractedValue(), BigDecimal.ZERO));
					break;
				case BUDGET:
					budget = budget.add(NumberUtils.asBigDecimal(label.getExtractedValue(), BigDecimal.ZERO));
					break;
				case STATUS:
					status = status.isPresent() ? status : Status.ofCodeOptional(label.getExtractedValue());
					break;
				case DELIVERY_PLATFORM:
					deliveryPlatformList.add(label.getExtractedValue());
					break;
				case PLANNED_UAT:
					plannedUATDate = plannedUATDate.isPresent() ? plannedUATDate : getDateFromLabel(label.getExtractedValue());
					break;
				case DELIVERED_DATE:
					deliveredDate = deliveredDate.isPresent() ? deliveredDate : getDateFromLabel(label.getExtractedValue());
					break;
				default:
					// nothing to do for UNKNOWN label types
			}

			issueLabelList.add(IssueLabel.builder().value(label.getLabel()).orgId(orgId).build());

		}

		importIssueInfoBuilder.budget(budget);
		importIssueInfoBuilder.estimation(estimation);
		importIssueInfoBuilder.roughEstimation(roughEstimation);
		importIssueInfoBuilder.issueLabels(ImmutableList.copyOf(issueLabelList));

		status.ifPresent(importIssueInfoBuilder::status);
		plannedUATDate.ifPresent(importIssueInfoBuilder::plannedUATDate);
		deliveredDate.ifPresent(importIssueInfoBuilder::deliveredDate);

		if (!deliveryPlatformList.isEmpty())
		{
			importIssueInfoBuilder.deliveryPlatform(Joiner.on(",").join(deliveryPlatformList));
		}
	}
}
