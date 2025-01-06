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
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.organization.OrgId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.serviceprovider.external.project.ExternalProjectReferenceId;
import de.metas.serviceprovider.issue.agg.key.impl.IssueEffortKeyBuilder;
import de.metas.serviceprovider.issue.hierarchy.IssueHierarchy;
import de.metas.serviceprovider.milestone.MilestoneId;
import de.metas.serviceprovider.model.I_S_EffortControl;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.timebooking.Effort;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Node;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class IssueRepository
{
	private final IQueryBL queryBL;
	private final ModelCacheInvalidationService modelCacheInvalidationService;

	public IssueRepository(final IQueryBL queryBL, final ModelCacheInvalidationService modelCacheInvalidationService)
	{
		this.queryBL = queryBL;
		this.modelCacheInvalidationService = modelCacheInvalidationService;
	}

	public void save(@NonNull final IssueEntity issueEntity)
	{
		final I_S_Issue record = buildRecord(issueEntity);

		InterfaceWrapperHelper.saveRecord(record);

		final IssueId issueId = IssueId.ofRepoId(record.getS_Issue_ID());

		issueEntity.setIssueId(issueId);
	}

	/**
	 * Retrieves the record identified by the given issue ID.
	 *
	 * @param issueId Issue ID
	 * @return issue entity
	 * @throws AdempiereException in case no record was found for the given ID.
	 */
	@NonNull
	public IssueEntity getById(@NonNull final IssueId issueId)
	{
		final I_S_Issue record = getRecordOrNull(issueId);

		if (record == null)
		{
			throw new AdempiereException("No S_Issue record was found for the given ID")
					.appendParametersToMessage()
					.setParameter("S_Issue_Id", issueId);
		}

		return ofRecord(record);
	}

	@NonNull
	public Optional<IssueEntity> getByIdOptional(@NonNull final IssueId issueId)
	{
		final I_S_Issue record = getRecordOrNull(issueId);

		return record != null
				? Optional.of(ofRecord(record))
				: Optional.empty();
	}

	@NonNull
	public Optional<IssueEntity> getByExternalURLOptional(@NonNull final String externalURL)
	{
		return queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Issue.COLUMNNAME_IssueURL, externalURL)
				.create()
				.firstOnlyOptional(I_S_Issue.class)
				.map(IssueRepository::ofRecord);
	}

	@NonNull
	public ImmutableList<IssueEntity> getDirectlyLinkedSubIssues(@NonNull final IssueId parentIssueId)
	{
		return queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Issue.COLUMNNAME_S_Parent_Issue_ID, parentIssueId)
				.create()
				.list()
				.stream()
				.map(IssueRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public void invalidateCacheForIds(@NonNull final ImmutableList<IssueId> issueIds)
	{
		if (issueIds.isEmpty())
		{
			return;//nothing to invalidate
		}

		final List<Integer> recordIds = issueIds.stream().map(IssueId::getRepoId).collect(Collectors.toList());

		final CacheInvalidateMultiRequest multiRequest =
				CacheInvalidateMultiRequest.fromTableNameAndRecordIds(I_S_Issue.Table_Name, recordIds);

		modelCacheInvalidationService.invalidate(multiRequest, ModelCacheInvalidationTiming.AFTER_CHANGE);
	}

	/**
	 * Creates an IssueHierarchy containing only the nodes from
	 * the given issue to root.
	 * <p>
	 * e.g
	 * Given the following issue hierarchy:
	 * ----1----
	 * ---/-\---
	 * --2---3--
	 * --|---|--
	 * --4---5--
	 * /-|-\----
	 * 6-7-8----
	 * <p>
	 * when {@code buildUpStreamIssueHierarchy(8)}
	 * it will return IssueHierarchy(root=1) with nodes: [1,2,4,8]
	 *
	 * @param issueId Id to build up stream for.
	 * @return {@code IssueHierarchy} with all nodes from the given Id to root.
	 */
	@NonNull
	public IssueHierarchy buildUpStreamIssueHierarchy(@NonNull final IssueId issueId)
	{
		final HashSet<IssueId> seenIds = new HashSet<>();

		Node<IssueEntity> currentNode = Node.of(getById(issueId), new ArrayList<>());
		seenIds.add(issueId);

		Optional<IssueId> parentIssueId = Optional.ofNullable(currentNode.getValue().getParentIssueId());

		while (parentIssueId.isPresent() && !seenIds.contains(parentIssueId.get()))
		{
			seenIds.add(parentIssueId.get()); //infinite loop protection

			final IssueEntity parentIssueEntity = getById(parentIssueId.get());

			final Node<IssueEntity> parentNode = Node.of(parentIssueEntity, Collections.singletonList(currentNode));

			currentNode.setParent(parentNode);

			currentNode = parentNode;
			parentIssueId = Optional.ofNullable(currentNode.getValue().getParentIssueId());
		}

		return IssueHierarchy.of(currentNode);
	}

	@NonNull
	public Stream<IssueEntity> streamIssuesWithOpenEffort()
	{
		final IQuery<I_S_Issue> notProcessedIssues = queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_S_Issue.COLUMNNAME_EffortAggregationKey)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_Processed, false)
				.create();

		final IQueryFilter<I_S_EffortControl> notProcessedEffortControlFilter = queryBL.createCompositeQueryFilter(I_S_EffortControl.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_S_EffortControl.COLUMNNAME_EffortAggregationKey, I_S_Issue.COLUMNNAME_EffortAggregationKey, notProcessedIssues);

		final IQuery<I_S_EffortControl> notProcessedEffortControlQuery = queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addFilter(notProcessedEffortControlFilter)
				.create();

		final IQuery<I_S_EffortControl> allEffortControlQuery = queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addOnlyActiveRecordsFilter()
				.create();

		final IQueryFilter<I_S_Issue> issueWithOpenEffortControlFilter = queryBL.createCompositeQueryFilter(I_S_Issue.class)
				.addInSubQueryFilter(I_S_Issue.COLUMNNAME_EffortAggregationKey, I_S_EffortControl.COLUMNNAME_EffortAggregationKey, notProcessedEffortControlQuery);

		final IQueryFilter<I_S_Issue> issueWithNoControlFilter = queryBL.createCompositeQueryFilter(I_S_Issue.class)
				.addNotInSubQueryFilter(I_S_Issue.COLUMNNAME_EffortAggregationKey, I_S_EffortControl.COLUMNNAME_EffortAggregationKey, allEffortControlQuery);

		final ICompositeQueryFilter<I_S_Issue> issueWithOpenEffortControlOrNoControlFilter = queryBL.createCompositeQueryFilter(I_S_Issue.class)
				.setJoinOr()
				.addFilter(issueWithOpenEffortControlFilter)
				.addFilter(issueWithNoControlFilter);

		return queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_S_Issue.COLUMNNAME_C_Project_ID)
				.addNotNull(I_S_Issue.COLUMNNAME_C_Activity_ID)
				.addFilter(issueWithOpenEffortControlOrNoControlFilter)
				.create()
				.iterateAndStream()
				.map(IssueRepository::ofRecord);
	}

	public void setAggregationKeyIfMissing()
	{
		final IssueEffortKeyBuilder effortKeyBuilder = new IssueEffortKeyBuilder();

		queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_S_Issue.COLUMNNAME_C_Project_ID)
				.addNotNull(I_S_Issue.COLUMNNAME_C_Activity_ID)
				.addEqualsFilter(I_S_Issue.COLUMNNAME_EffortAggregationKey, null)
				.create()
				.iterateAndStream()
				.peek(issueWithNoAggregation -> {
					final String aggregationKey = effortKeyBuilder.buildKey(issueWithNoAggregation);
					issueWithNoAggregation.setEffortAggregationKey(aggregationKey);
				})
				.forEach(InterfaceWrapperHelper::save);
	}

	@NonNull
	public List<IssueEntity> geyByQuery(@NonNull final IssueQuery query)
	{
		return buildQuery(query)
				.create()
				.iterateAndStream()
				.map(IssueRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Iterator<I_S_Issue> iterateRecordsByQuery(@NonNull final IssueQuery query)
	{
		return buildQuery(query)
				.create()
				.iterate(I_S_Issue.class);
	}

	@NonNull
	public I_S_Issue getRecordById(@NonNull final IssueId issueId)
	{
		return Optional.ofNullable(getRecordOrNull(issueId))
				.orElseThrow(()-> new AdempiereException("Missing S_Issue record for id:")
				.appendParametersToMessage()
				.setParameter("IssueId", issueId));
	}

	@NonNull
	public static IssueEntity ofRecord(@NonNull final I_S_Issue record)
	{
		final IssueType issueType = IssueType
				.getTypeByValue(record.getIssueType())
				.orElseThrow(() -> new AdempiereException("Unknown IssueType!").appendParametersToMessage()
						.setParameter("I_S_Issue", record));

		final Status status = Status.ofCodeOptional(record.getStatus()).orElse(Status.NEW);

		return IssueEntity.builder()
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoIdOrNull(record.getS_ExternalProjectReference_ID()))
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.issueId(IssueId.ofRepoId(record.getS_Issue_ID()))
				.parentIssueId(IssueId.ofRepoIdOrNull(record.getS_Parent_Issue_ID()))
				.effortUomId(UomId.ofRepoId(record.getEffort_UOM_ID()))
				.milestoneId(MilestoneId.ofRepoIdOrNull(record.getS_Milestone_ID()))
				.assigneeId(UserId.ofRepoIdOrNullIfSystem(record.getAD_User_ID()))
				.name(record.getName())
				.searchKey(record.getValue())
				.description(record.getDescription())
				.type(issueType)
				.status(status)
				.deliveryPlatform(record.getDeliveryPlatform())
				.plannedUATDate(TimeUtil.asLocalDate(record.getPlannedUATDate()))
				.isEffortIssue(record.isEffortIssue())
				.estimatedEffort(record.getEstimatedEffort())
				.budgetedEffort(record.getBudgetedEffort())
				.roughEstimation(record.getRoughEstimation())
				.issueEffort(Effort.ofNullable(record.getIssueEffort()))
				.aggregatedEffort(Effort.ofNullable(record.getAggregatedEffort()))
				.invoicableChildEffort(Quantitys.of(record.getInvoiceableChildEffort(), UomId.ofRepoId(record.getEffort_UOM_ID())))
				.latestActivityOnIssue(TimeUtil.asInstant(record.getLatestActivity()))
				.latestActivityOnSubIssues(TimeUtil.asInstant(record.getLatestActivityOnSubIssues()))
				.externalIssueNo(record.getExternalIssueNo())
				.externalIssueURL(record.getIssueURL())
				.processed(record.isProcessed())
				.deliveredDate(TimeUtil.asLocalDate(record.getDeliveredDate()))
				.processedTimestamp(TimeUtil.asInstant(record.getProcessedDate()))
				.costCenterActivityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.externallyUpdatedAt(TimeUtil.asInstant(record.getExternallyUpdatedAt()))
				.invoiceableHours(record.getInvoiceableEffort())
				.invoicingErrorMsg(record.getInvoicingErrorMsg())
				.isInvoicingError(record.isInvoicingError())
				.build();
	}

	@Nullable
	private I_S_Issue getRecordOrNull(@NonNull final IssueId issueId)
	{
		return queryBL
				.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Issue.COLUMNNAME_S_Issue_ID, issueId.getRepoId())
				.create()
				.firstOnly(I_S_Issue.class);
	}

	@NonNull
	private IQueryBuilder<I_S_Issue> buildQuery(@NonNull final IssueQuery query)
	{
		final IQueryBuilder<I_S_Issue> queryBuilder = queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Issue.COLUMNNAME_AD_Org_ID, query.getOrgId())
				.addEqualsFilter(I_S_Issue.COLUMNNAME_C_Activity_ID, query.getCostCenterId())
				.addEqualsFilter(I_S_Issue.COLUMNNAME_C_Project_ID, query.getProjectId());

		if (query.getEffortIssue() != null)
		{
			queryBuilder.addEqualsFilter(I_S_Issue.COLUMNNAME_IsEffortIssue, query.getEffortIssue());
		}

		if (query.getProcessed() != null)
		{
			queryBuilder.addEqualsFilter(I_S_Issue.COLUMNNAME_Processed, query.getProcessed());
		}

		return queryBuilder;
	}

	@NonNull
	private static I_S_Issue buildRecord(@NonNull final IssueEntity issueEntity)
	{
		final I_S_Issue record =
				InterfaceWrapperHelper.loadOrNew(issueEntity.getIssueId(), I_S_Issue.class);

		record.setAD_Org_ID(issueEntity.getOrgId().getRepoId());
		record.setAD_User_ID(NumberUtils.asInt(issueEntity.getAssigneeId(), -1));
		record.setC_Project_ID(NumberUtils.asInt(issueEntity.getProjectId(), -1));
		record.setS_ExternalProjectReference_ID(NumberUtils.asInt(issueEntity.getExternalProjectReferenceId(), -1));
		record.setS_Parent_Issue_ID(NumberUtils.asInt(issueEntity.getParentIssueId(), -1));

		record.setProcessed(issueEntity.isProcessed());

		record.setName(issueEntity.getName());
		record.setValue(issueEntity.getSearchKey());
		record.setDescription(issueEntity.getDescription());

		record.setIssueType(issueEntity.getType().getValue());
		record.setIsEffortIssue(issueEntity.isEffortIssue());

		record.setS_Milestone_ID(NumberUtils.asInt(issueEntity.getMilestoneId(), -1));
		record.setRoughEstimation(issueEntity.getRoughEstimation());
		record.setEstimatedEffort(issueEntity.getEstimatedEffort());
		record.setBudgetedEffort(issueEntity.getBudgetedEffort());
		record.setEffort_UOM_ID(issueEntity.getEffortUomId().getRepoId());
		record.setIssueEffort(issueEntity.getIssueEffort().getHmm());
		record.setAggregatedEffort(issueEntity.getAggregatedEffort().getHmm());
		record.setInvoiceableChildEffort(Quantity.toBigDecimal(issueEntity.getInvoicableChildEffort()));

		record.setLatestActivityOnSubIssues(TimeUtil.asTimestamp(issueEntity.getLatestActivityOnSubIssues()));
		record.setLatestActivity(TimeUtil.asTimestamp(issueEntity.getLatestActivityOnIssue()));

		if (issueEntity.getStatus() != null)
		{
			record.setStatus(issueEntity.getStatus().getCode());
		}

		record.setPlannedUATDate(TimeUtil.asTimestamp(issueEntity.getPlannedUATDate()));

		record.setDeliveryPlatform(issueEntity.getDeliveryPlatform());
		record.setDeliveredDate(TimeUtil.asTimestamp(issueEntity.getDeliveredDate()));

		record.setExternalIssueNo(issueEntity.getExternalIssueNo());
		record.setIssueURL(issueEntity.getExternalIssueURL());

		record.setC_Activity_ID(ActivityId.toRepoId(issueEntity.getCostCenterActivityId()));

		record.setExternallyUpdatedAt(TimeUtil.asTimestamp(issueEntity.getExternallyUpdatedAt()));

		record.setInvoiceableEffort(issueEntity.getInvoiceableHours());

		record.setInvoicingErrorMsg(issueEntity.getInvoicingErrorMsg());
		record.setIsInvoicingError(issueEntity.isInvoicingError());

		return record;
	}
}
