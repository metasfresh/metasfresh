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

package de.metas.serviceprovider.issue.interceptor;

import de.metas.logging.LogManager;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueService;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

@Interceptor(I_S_Issue.class)
@Callout(I_S_Issue.class)
@Component
public class S_Issue
{
	private final Logger log = LogManager.getLogger(getClass());

	private final ExternalReferenceRepository externalReferenceRepository;
	private final IssueService issueService;
	private final IssueRepository issueRepository;

	public S_Issue(final ExternalReferenceRepository externalReferenceRepository, final IssueService issueService, final IssueRepository issueRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.issueService = issueService;
		this.issueRepository = issueRepository;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(@NonNull final I_S_Issue record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_Issue_ID(), ExternalReferenceType.ISSUE_ID);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_S_Issue.COLUMNNAME_S_Parent_Issue_ID)
	public void recomputeAggregatedEffortWhenParentChanged(@NonNull final I_S_Issue record)
	{
		final I_S_Issue oldRecord = InterfaceWrapperHelper.createOld(record, I_S_Issue.class);

		final Instant latestActivity = Stream.of(record.getLatestActivity(), record.getLatestActivityOnSubIssues())
										.filter(Objects::nonNull)
										.map(Timestamp::toInstant)
										.max(Instant::compareTo)
										.orElse(null);

		final HandleParentChangedRequest handleParentChangedRequest = HandleParentChangedRequest
				.builder()
				.currentParentId(IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID()))
				.currentEffort(Effort.ofNullable(record.getAggregatedEffort()))
				.oldParentId(IssueId.ofRepoIdOrNull(oldRecord.getS_Parent_Issue_ID()))
				.oldEffort(Effort.ofNullable(oldRecord.getAggregatedEffort()))
				.latestActivity(latestActivity)
				.build();

		issueService.handleParentChanged(handleParentChangedRequest);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_S_Issue.COLUMNNAME_IsActive)
	public void reactivateLinkedExternalReferences(@NonNull final I_S_Issue record)
	{
		if (record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_Issue_ID(), ExternalReferenceType.ISSUE_ID, record.isActive());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_S_Issue.COLUMNNAME_IsActive)
	public void inactivateLinkedExternalReferences(@NonNull final I_S_Issue record)
	{
		if (!record.isActive())
		{
			externalReferenceRepository.updateIsActiveByRecordIdAndType(record.getS_Issue_ID(), ExternalReferenceType.ISSUE_ID, record.isActive());
		}
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW}, ifColumnsChanged = I_S_Issue.COLUMNNAME_S_Parent_Issue_ID)
	public void overwriteParentIssueId(@NonNull final I_S_Issue record)
	{
		if (isParentAlreadyInHierarchy(record)
				|| parentAlreadyHasAnEffortIssueAssigned(record)
				|| isBudgetChildForParentEffort(record))
		{
			log.warn("*** Overwriting S_Issue.S_Parent_Issue_ID to null! S_Issue_ID: {} S_Parent_Issue_ID: {}! "
					, record.getS_Issue_ID(), record.getS_Parent_Issue_ID());

			record.setS_Parent_Issue_ID(-1);
		}
	}

	@CalloutMethod(columnNames = I_S_Issue.COLUMNNAME_S_Parent_Issue_ID)
	public void validateParent(@NonNull final I_S_Issue record)
	{
		if (isParentAlreadyInHierarchy(record))
		{
			throw new AdempiereException("The parentIssueID is already present in the hierarchy!")
					.appendParametersToMessage()
					.setParameter("ParentIssueId", record.getS_Parent_Issue_ID())
					.setParameter("TargetIssueId", record.getS_Issue_ID());
		}

		if (parentAlreadyHasAnEffortIssueAssigned(record))
		{
			throw new AdempiereException("There is already an Effort issue created for the requested parent!")
					.appendParametersToMessage()
					.setParameter("ParentIssueId", record.getS_Parent_Issue_ID())
					.setParameter("TargetIssueId", record.getS_Issue_ID());
		}

		if (isBudgetChildForParentEffort(record))
		{
			throw new AdempiereException("A budget issue cannot have an effort one as parent!")
					.appendParametersToMessage()
					.setParameter("ParentIssueId", record.getS_Parent_Issue_ID())
					.setParameter("TargetIssueId", record.getS_Issue_ID());
		}
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW}, ifColumnsChanged = I_S_Issue.COLUMNNAME_Processed)
	public void setProcessedTimestamp(@NonNull final I_S_Issue record)
	{
		final I_S_Issue oldRecord = InterfaceWrapperHelper.createOld(record, I_S_Issue.class);

		if (oldRecord != null && oldRecord.isProcessed())
		{
			return;//nothing to do; it means the processed timestamp was already set
		}

		if (record.isProcessed())
		{
			record.setProcessedDate(TimeUtil.asTimestamp(Instant.now()));
		}
	}

	private boolean isParentAlreadyInHierarchy(@NonNull final I_S_Issue record)
	{
		final IssueId currentIssueID = IssueId.ofRepoIdOrNull(record.getS_Issue_ID());
		if (currentIssueID == null)
		{
			return false; //means it's a new record so it doesn't have any children
		}

		final IssueId parentIssueID = IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID());

		if (parentIssueID != null)
		{
			final IssueHierarchy issueHierarchy = issueRepository.buildUpStreamIssueHierarchy(parentIssueID);

			return issueHierarchy.hasNodeForId(currentIssueID);
		}

		return false;
	}

	private boolean parentAlreadyHasAnEffortIssueAssigned(@NonNull final I_S_Issue record)
	{
		if (record.isEffortIssue())
		{
			final I_S_Issue parentIssue = record.getS_Parent_Issue();

			if (parentIssue != null && !parentIssue.isEffortIssue())
			{
				return issueRepository.getDirectlyLinkedSubIssues(IssueId.ofRepoId(parentIssue.getS_Issue_ID()))
						.stream()
						.anyMatch(IssueEntity::isEffortIssue);
			}
		}

		return false;
	}

	private boolean isBudgetChildForParentEffort(@NonNull  final I_S_Issue record)
	{
		return !record.isEffortIssue()
				&& record.getS_Parent_Issue() != null
				&& record.getS_Parent_Issue().isEffortIssue();
	}

}
