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

import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.issue.IssueService;
import de.metas.serviceprovider.issue.Status;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.uom.UomId;
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

	public S_Issue(
			final ExternalReferenceRepository externalReferenceRepository,
			final IssueService issueService,
			final IssueRepository issueRepository)
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
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_Issue_ID(), ExternalServiceReferenceType.ISSUE_ID);
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

		final UomId uomId = UomId.ofRepoId(record.getEffort_UOM_ID());

		final Quantity currentInvoicableEffort = Quantitys.of(record.getInvoiceableChildEffort(), uomId)
				.add(Quantitys.of(record.getInvoiceableEffort(), uomId));
		final Quantity oldInvoicableEffort = Quantitys.of(oldRecord.getInvoiceableChildEffort(), uomId)
				.add(Quantitys.of(oldRecord.getInvoiceableEffort(), uomId));

		final HandleParentChangedRequest handleParentChangedRequest = HandleParentChangedRequest
				.builder()
				.currentParentId(IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID()))
				.currentAggregatedEffort(Effort.ofNullable(record.getAggregatedEffort()))
				.oldAggregatedEffort(Effort.ofNullable(oldRecord.getAggregatedEffort()))
				.currentInvoicableEffort(currentInvoicableEffort)
				.oldInvoicableEffort(oldInvoicableEffort)
				.oldParentId(IssueId.ofRepoIdOrNull(oldRecord.getS_Parent_Issue_ID()))
				.latestActivity(latestActivity)
				.build();

		issueService.handleParentChanged(handleParentChangedRequest);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_S_Issue.COLUMNNAME_AD_Org_ID)
	public void inactivateLinkedExternalReferences(@NonNull final I_S_Issue record)
	{
		externalReferenceRepository.updateOrgIdByRecordIdAndType(record.getS_Issue_ID(), ExternalServiceReferenceType.ISSUE_ID, OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = I_S_Issue.COLUMNNAME_S_Parent_Issue_ID)
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = I_S_Issue.COLUMNNAME_Processed)
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

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW },
			ifColumnsChanged = { I_S_Issue.COLUMNNAME_S_Parent_Issue_ID, I_S_Issue.COLUMNNAME_IssueType })
	public void setParentIssueInProgress(@NonNull final I_S_Issue record)
	{
		if (!record.isEffortIssue())
		{
			return;
		}

		final IssueId parentIssueId = IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID());
		if (parentIssueId == null)
		{
			return;
		}

		final IssueEntity parentIssue = issueRepository.getById(parentIssueId);
		if (parentIssue.isEffortIssue())
		{
			return;
		}

		if (!Status.NEW.equals(parentIssue.getStatus()))
		{
			return;
		}

		parentIssue.setStatus(Status.IN_PROGRESS);
		issueRepository.save(parentIssue);
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

	private boolean isBudgetChildForParentEffort(@NonNull final I_S_Issue record)
	{
		return !record.isEffortIssue()
				&& record.getS_Parent_Issue() != null
				&& record.getS_Parent_Issue().isEffortIssue();
	}
}
