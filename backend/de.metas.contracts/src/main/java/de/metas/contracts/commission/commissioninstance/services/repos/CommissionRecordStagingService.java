package de.metas.contracts.commission.commissioninstance.services.repos;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline.SalesInvoiceLineDocumentId;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService.CommissionStagingRecords.CommissionStagingRecordsBuilder;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

/** This service is intended exclusively for {@link CommissionInstanceRepository}, to avoid n+1 problems. */
@Service
@VisibleForTesting
public class CommissionRecordStagingService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	CommissionStagingRecords retrieveRecordsForInstanceId(@NonNull final Collection<CommissionInstanceId> commissionInstanceIds)
	{
		final IQueryBuilder<I_C_Commission_Instance> instanceQueryBuilder = createInstanceQueryBuilder()
				.addInArrayFilter(I_C_Commission_Instance.COLUMN_C_Commission_Instance_ID, commissionInstanceIds);

		return retrieveRecords(instanceQueryBuilder.create());
	}

	CommissionStagingRecords retrieveRecordsForInvoiceCandidateId(@NonNull final Collection<CommissionTriggerDocumentId> commissionTriggerDocumentIds)
	{
		// ------------------ I_C_Commission_Instance
		final IQueryBuilder<I_C_Commission_Instance> instanceQueryBuilder = createInstanceQueryBuilder();

		final ImmutableListMultimap<Class<? extends CommissionTriggerDocumentId>, CommissionTriggerDocumentId> classToIds = Multimaps.index(commissionTriggerDocumentIds, CommissionTriggerDocumentId::getClass);

		final ICompositeQueryFilter<I_C_Commission_Instance> inArrayfilters = queryBL
				.createCompositeQueryFilter(I_C_Commission_Instance.class)
				.setJoinOr();

		if (classToIds.containsKey(SalesInvoiceCandidateDocumentId.class))
		{
			final ImmutableList<RepoIdAware> invoiceCandidateIds = classToIds.get(SalesInvoiceCandidateDocumentId.class)
					.stream()
					.map(CommissionTriggerDocumentId::getRepoIdAware)
					.collect(ImmutableList.toImmutableList());
			inArrayfilters.addInArrayFilter(I_C_Commission_Instance.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);
		}
		if (classToIds.containsKey(SalesInvoiceLineDocumentId.class))
		{
			final ImmutableList<RepoIdAware> invoiceLineIds = classToIds.get(SalesInvoiceLineDocumentId.class)
					.stream()
					.map(CommissionTriggerDocumentId::getRepoIdAware)
					.collect(ImmutableList.toImmutableList());
			inArrayfilters.addInArrayFilter(I_C_Commission_Instance.COLUMN_C_InvoiceLine_ID, invoiceLineIds);
		}

		instanceQueryBuilder.filter(inArrayfilters);

		return retrieveRecords(instanceQueryBuilder.create());
	}

	private IQueryBuilder<I_C_Commission_Instance> createInstanceQueryBuilder()
	{
		final IQueryBuilder<I_C_Commission_Instance> instanceQueryBuilder = queryBL
				.createQueryBuilder(I_C_Commission_Instance.class)
				.addOnlyActiveRecordsFilter();

		return instanceQueryBuilder;
	}

	private CommissionStagingRecords retrieveRecords(@NonNull final IQuery<I_C_Commission_Instance> instanceRecordQuery)
	{
		// ------------------ I_C_Commission_Instance
		final List<I_C_Commission_Instance> instanceRecords = instanceRecordQuery.list();

		final ImmutableMap<CommissionTriggerDocumentId, I_C_Commission_Instance> documentIdToInstanceRecords = Maps.uniqueIndex(
				instanceRecords,
				CommissionInstanceRepoTools::extractCommissionTriggerDocumentId);
		final ImmutableMap<Integer, I_C_Commission_Instance> instanceRecordIdToInstance = Maps.uniqueIndex(instanceRecords, I_C_Commission_Instance::getC_Commission_Instance_ID);
		final ImmutableSet<Integer> intanceRecordIds = instanceRecordIdToInstance.keySet();

		final CommissionStagingRecordsBuilder commissionRecords = CommissionStagingRecords.builder()
				.documentIdToInstanceRecords(documentIdToInstanceRecords)
				.instanceRecordIdToInstance(instanceRecordIdToInstance);

		// ------------------ I_C_Commission_Share
		final IQueryBuilder<I_C_Commission_Share> shareQueryBuilder = queryBL
				.createQueryBuilder(I_C_Commission_Share.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Commission_Share.COLUMN_C_Commission_Instance_ID, intanceRecordIds)
				.orderBy(I_C_Commission_Share.COLUMN_LevelHierarchy); // it's very important that we process them in their correct orders

		final List<I_C_Commission_Share> shareRecords = shareQueryBuilder
				.create()
				.list();

		// note that the share records' ordering is preserved in the multimap
		final ImmutableListMultimap<Integer, I_C_Commission_Share> instanceRecordIdToShareRecords = Multimaps.index(shareRecords, I_C_Commission_Share::getC_Commission_Instance_ID);
		commissionRecords.instanceRecordIdToShareRecords(instanceRecordIdToShareRecords);

		final ImmutableList<Integer> shareRecordIds = CollectionUtils.extractDistinctElements(shareRecords, I_C_Commission_Share::getC_Commission_Share_ID);

		// ------------------ I_C_Commission_Fact
		final IQueryBuilder<I_C_Commission_Fact> factQueryBuilder = queryBL
				.createQueryBuilder(I_C_Commission_Fact.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Commission_Fact.COLUMN_C_Commission_Share_ID, shareRecordIds)
				.addInArrayFilter(I_C_Commission_Fact.COLUMN_Commission_Fact_State, SalesCommissionState.allRecordCodes());

		final List<I_C_Commission_Fact> factRecords = factQueryBuilder.create().list();

		final ImmutableListMultimap<Integer, I_C_Commission_Fact> shareRecordIdToFactRecords = Multimaps.index(factRecords, I_C_Commission_Fact::getC_Commission_Share_ID);
		commissionRecords.shareRecordIdToFactRecords(shareRecordIdToFactRecords);

		return commissionRecords.build();
	}

	@lombok.Value
	static class CommissionStagingRecords
	{
		final static CommissionStagingRecords EMPTY = CommissionStagingRecords.builder().build();

		ImmutableMap<CommissionTriggerDocumentId, I_C_Commission_Instance> documentIdToInstanceRecords;
		ImmutableMap<Integer, I_C_Commission_Instance> instanceRecordIdToInstance;

		@Getter(AccessLevel.NONE)
		ImmutableListMultimap<Integer, I_C_Commission_Share> instanceRecordIdToShareRecords;

		@Getter(AccessLevel.NONE)
		ImmutableListMultimap<Integer, I_C_Commission_Fact> shareRecordIdToSalesFactRecords;

		@Builder
		private CommissionStagingRecords(
				@Nullable final ImmutableMap<CommissionTriggerDocumentId, I_C_Commission_Instance> documentIdToInstanceRecords,
				@Nullable final ImmutableMap<Integer, I_C_Commission_Instance> instanceRecordIdToInstance,
				@Nullable final ImmutableListMultimap<Integer, I_C_Commission_Share> instanceRecordIdToShareRecords,
				@Nullable final ImmutableListMultimap<Integer, I_C_Commission_Fact> shareRecordIdToFactRecords)
		{
			this.documentIdToInstanceRecords = coalesce(documentIdToInstanceRecords, ImmutableMap.of());
			this.instanceRecordIdToInstance = coalesce(instanceRecordIdToInstance, ImmutableMap.of());
			this.instanceRecordIdToShareRecords = coalesce(instanceRecordIdToShareRecords, ImmutableListMultimap.of());
			this.shareRecordIdToSalesFactRecords = coalesce(shareRecordIdToFactRecords, ImmutableListMultimap.of());
		}

		ImmutableList<I_C_Commission_Share> getShareRecordsForInstanceRecordId(@NonNull final CommissionInstanceId commissionInstanceId)
		{
			return instanceRecordIdToShareRecords.get(commissionInstanceId.getRepoId());
		}

		ImmutableList<I_C_Commission_Fact> getSalesFactRecordsForShareRecordId(int commissionShareRecordId)
		{
			return shareRecordIdToSalesFactRecords.get(commissionShareRecordId);
		}
	}
}
