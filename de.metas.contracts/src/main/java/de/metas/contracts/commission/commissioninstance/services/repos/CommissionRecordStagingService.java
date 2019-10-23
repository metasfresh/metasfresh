package de.metas.contracts.commission.commissioninstance.services.repos;

import static de.metas.util.lang.CoalesceUtil.coalesce;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService.CommissionRecords.CommissionRecordsBuilder;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
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
class CommissionRecordStagingService
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	CommissionRecords retrieveRecordsForInstanceId(
			@NonNull final Collection<CommissionInstanceId> commissionInstanceIds,
			final boolean onlyActive)
	{
		final IQueryBuilder<I_C_Commission_Instance> instanceQueryBuilder = createInstanceQueryBuilder(onlyActive)
				.addInArrayFilter(I_C_Commission_Instance.COLUMN_C_Commission_Instance_ID, commissionInstanceIds);

		return retrieveRecords(instanceQueryBuilder.create(), onlyActive);
	}

	CommissionRecords retrieveRecordsForInvoiceCandidateId(
			@NonNull final Collection<InvoiceCandidateId> invoiceCandidateIds,
			final boolean onlyActive)
	{
		// ------------------ I_C_Commission_Instance
		final IQueryBuilder<I_C_Commission_Instance> instanceQueryBuilder = createInstanceQueryBuilder(onlyActive)
				.addInArrayFilter(I_C_Commission_Instance.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);

		return retrieveRecords(instanceQueryBuilder.create(), onlyActive);
	}

	private IQueryBuilder<I_C_Commission_Instance> createInstanceQueryBuilder(final boolean onlyActive)
	{
		final IQueryBuilder<I_C_Commission_Instance> instanceQueryBuilder = queryBL
				.createQueryBuilder(I_C_Commission_Instance.class);
		if (onlyActive)
		{
			instanceQueryBuilder.addOnlyActiveRecordsFilter();
		}
		return instanceQueryBuilder;
	}

	private CommissionRecords retrieveRecords(
			@NonNull final IQuery<I_C_Commission_Instance> instanceRecordQuery,
			final boolean onlyActive)
	{
		final List<I_C_Commission_Instance> instanceRecords = instanceRecordQuery.list();

		final ImmutableListMultimap<Integer, I_C_Commission_Instance> icRecordIdToInstanceRecords = Multimaps.index(instanceRecords, I_C_Commission_Instance::getC_Invoice_Candidate_ID);
		final ImmutableMap<Integer, I_C_Commission_Instance> instanceRecordIdToInstance = Maps.uniqueIndex(instanceRecords, I_C_Commission_Instance::getC_Commission_Instance_ID);
		final ImmutableSet<Integer> intanceRecordIds = instanceRecordIdToInstance.keySet();

		final CommissionRecordsBuilder commissionRecords = CommissionRecords.builder()
				.icRecordIdToInstanceRecords(icRecordIdToInstanceRecords)
				.instanceRecordIdToInstance(instanceRecordIdToInstance);

		// ------------------ I_C_Commission_Share
		final IQueryBuilder<I_C_Commission_Share> shareQueryBuilder = queryBL
				.createQueryBuilder(I_C_Commission_Share.class)
				.addInArrayFilter(I_C_Commission_Share.COLUMN_C_Commission_Instance_ID, intanceRecordIds);
		if (onlyActive)
		{
			shareQueryBuilder.addOnlyActiveRecordsFilter();
		}
		final List<I_C_Commission_Share> shareRecords = shareQueryBuilder.create().list();

		final ImmutableListMultimap<Integer, I_C_Commission_Share> instanceRecordIdToShareRecords = Multimaps.index(shareRecords, I_C_Commission_Share::getC_Commission_Instance_ID);
		commissionRecords.instanceRecordIdToShareRecords(instanceRecordIdToShareRecords);

		final ImmutableList<Integer> shareRecordIds = CollectionUtils.extractDistinctElements(shareRecords, I_C_Commission_Share::getC_Commission_Share_ID);

		// ------------------ I_C_Commission_Fact
		final IQueryBuilder<I_C_Commission_Fact> factQueryBuilder = queryBL
				.createQueryBuilder(I_C_Commission_Fact.class)
				.addInArrayFilter(I_C_Commission_Fact.COLUMN_C_Commission_Share_ID, shareRecordIds)
				.addInArrayFilter(I_C_Commission_Fact.COLUMN_Commission_Fact_State, SalesCommissionState.allRecordCodes());
		if (onlyActive)
		{
			shareQueryBuilder.addOnlyActiveRecordsFilter();
		}
		final List<I_C_Commission_Fact> factRecords = factQueryBuilder.create().list();

		final ImmutableListMultimap<Integer, I_C_Commission_Fact> shareRecordIdToFactRecords = Multimaps.index(factRecords, I_C_Commission_Fact::getC_Commission_Share_ID);
		commissionRecords.shareRecordIdToFactRecords(shareRecordIdToFactRecords);

		return commissionRecords.build();
	}

	@lombok.Value
	static class CommissionRecords
	{
		final static CommissionRecords EMPTY = CommissionRecords.builder().build();

		ImmutableListMultimap<Integer, I_C_Commission_Instance> icRecordIdToInstanceRecords;
		ImmutableMap<Integer, I_C_Commission_Instance> instanceRecordIdToInstance;

		@Getter(AccessLevel.NONE)
		ImmutableListMultimap<Integer, I_C_Commission_Share> instanceRecordIdToShareRecords;

		@Getter(AccessLevel.NONE)
		ImmutableListMultimap<Integer, I_C_Commission_Fact> shareRecordIdToSalesFactRecords;

		@Builder
		private CommissionRecords(
				@Nullable final ImmutableListMultimap<Integer, I_C_Commission_Instance> icRecordIdToInstanceRecords,
				@Nullable final ImmutableMap<Integer, I_C_Commission_Instance> instanceRecordIdToInstance,
				@Nullable final ImmutableListMultimap<Integer, I_C_Commission_Share> instanceRecordIdToShareRecords,
				@Nullable final ImmutableListMultimap<Integer, I_C_Commission_Fact> shareRecordIdToFactRecords)
		{
			this.icRecordIdToInstanceRecords = coalesce(icRecordIdToInstanceRecords, ImmutableListMultimap.of());
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
