package de.metas.contracts.commission.commissioninstance.services.repos;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance.CommissionInstanceBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare.SalesCommissionShareBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigFactory;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService.CommissionRecords;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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

@Repository
public class CommissionInstanceRepository
{
	private final CommissionRecordStagingService commissionRecordStagingService;
	private final CommissionConfigFactory commissionConfigFactory;

	public CommissionInstanceRepository(
			@NonNull final CommissionConfigFactory commissionConfigFactory,
			@NonNull final CommissionRecordStagingService commissionInstanceRecordStagingService)
	{
		this.commissionRecordStagingService = commissionInstanceRecordStagingService;
		this.commissionConfigFactory = commissionConfigFactory;
	}

	public CommissionInstance getForCommissionInstanceId(@NonNull final CommissionInstanceId commissionInstanceId)
	{
		final CommissionRecords stagingRecords = commissionRecordStagingService.retrieveRecordsForInstanceId(ImmutableList.of(commissionInstanceId), true/* onlyActive */);

		final I_C_Commission_Instance instanceRecord = stagingRecords.getInstanceRecordIdToInstance().get(commissionInstanceId.getRepoId());
		final CommissionInstance instance = createCommissionInstance(instanceRecord, stagingRecords);
		return instance;
	}

	public ImmutableList<CommissionInstance> getForInvoiceCandidateId(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final CommissionRecords records = commissionRecordStagingService.retrieveRecordsForInvoiceCandidateId(ImmutableList.of(invoiceCandidateId), true/* onlyActive */);

		final List<I_C_Commission_Instance> instanceRecords = records.getIcRecordIdToInstanceRecords().get(invoiceCandidateId.getRepoId());
		if (instanceRecords.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<CommissionInstance> result = ImmutableList.builder();
		for (final I_C_Commission_Instance instanceRecord : instanceRecords)
		{
			final CommissionInstance instance = createCommissionInstance(instanceRecord, records);
			result.add(instance);
		}
		return result.build();
	}

	private CommissionInstance createCommissionInstance(
			@NonNull final I_C_Commission_Instance instanceRecord,
			@NonNull final CommissionRecords stagingRecords)
	{
		final CommissionInstanceId commissionInstanceId = CommissionInstanceId.ofRepoId(instanceRecord.getC_Commission_Instance_ID());

		final List<I_C_Commission_Share> shareRecords = stagingRecords.getShareRecordsForInstanceRecordId(commissionInstanceId);

		final ImmutableList<FlatrateTermId> flatrateTermIds = shareRecords.stream()
				.map(I_C_Commission_Share::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		final CommissionConfig commissionConfig = commissionConfigFactory.createFor(flatrateTermIds);

		final CommissionInstanceBuilder instanceBuilder = CommissionInstance.builder()
				.id(commissionInstanceId)
				.config(commissionConfig)
				.currentTriggerData(createCommissionTriggerData(instanceRecord));

		for (final I_C_Commission_Share shareRecord : shareRecords)
		{
			final SalesCommissionShareBuilder shareBuilder = createShareBuilder(shareRecord);

			final ImmutableList<I_C_Commission_Fact> salesFactRecords = stagingRecords.getSalesFactRecordsForShareRecordId(shareRecord.getC_Commission_Share_ID());
			shareBuilder.facts(createFacts(salesFactRecords));

			final SalesCommissionShare share = shareBuilder.build();
			instanceBuilder.share(share);
		}
		return instanceBuilder.build();
	}

	public Beneficiary createBeneficiary(final int c_BPartner_SalesRep_ID)
	{
		return Beneficiary.of(BPartnerId.ofRepoId(c_BPartner_SalesRep_ID));
	}

	private CommissionTriggerData createCommissionTriggerData(@NonNull final I_C_Commission_Instance instanceRecord)
	{
		return CommissionTriggerData.builder()
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(instanceRecord.getC_Invoice_Candidate_ID()))
				.forecastedPoints(CommissionPoints.of(instanceRecord.getPointsBase_Forecasted()))
				.invoiceablePoints(CommissionPoints.of(instanceRecord.getPointsBase_Invoiceable()))
				.invoicedPoints(CommissionPoints.of(instanceRecord.getPointsBase_Invoiced()))
				.timestamp(TimeUtil.asInstant(instanceRecord.getMostRecentTriggerTimestamp()))
				.build();
	}

	private SalesCommissionShareBuilder createShareBuilder(@NonNull final I_C_Commission_Share shareRecord)
	{
		final SalesCommissionShareBuilder share = SalesCommissionShare.builder()
				.beneficiary(Beneficiary.of(BPartnerId.ofRepoId(shareRecord.getC_BPartner_SalesRep_ID())))
				.level(HierarchyLevel.of(shareRecord.getLevelHierarchy()));

		return share;
	}

	private ImmutableList<SalesCommissionFact> createFacts(@NonNull final ImmutableList<I_C_Commission_Fact> factRecords)
	{
		final ImmutableList.Builder<SalesCommissionFact> facts = ImmutableList.builder();

		for (final I_C_Commission_Fact factRecord : factRecords)
		{
			final SalesCommissionFact fact = SalesCommissionFact.builder()
					.points(CommissionPoints.of(factRecord.getCommissionPoints()))
					.state(SalesCommissionState.valueOf(factRecord.getCommission_Fact_State()))
					.timestamp(Instant.parse(factRecord.getCommissionFactTimestamp()))
					.build();
			facts.add(fact);
		}
		return facts.build();
	}

	public CommissionInstanceId save(@NonNull final CommissionInstance instance)
	{
		final CommissionInstanceId instanceIdOrNull = instance.getId();
		final CommissionRecords stagingRecords;
		if (instanceIdOrNull == null)
		{
			stagingRecords = CommissionRecords.EMPTY;
		}
		else
		{
			stagingRecords = commissionRecordStagingService.retrieveRecordsForInstanceId(ImmutableList.of(instanceIdOrNull), true/* onlyActive */);

		}
		final CommissionTriggerData triggerData = instance.getCurrentTriggerData();
		final InvoiceCandidateId invoiceCandidateId = triggerData.getInvoiceCandidateId();

		final I_C_Commission_Instance commissionInstanceRecord = loadOrNewInstanceRecord(instance);

		if (triggerData.isInvoiceCandidateWasDeleted())
		{
			commissionInstanceRecord.setC_Invoice_Candidate_ID(-1);
		}
		else
		{
			commissionInstanceRecord.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		}
		commissionInstanceRecord.setMostRecentTriggerTimestamp(TimeUtil.asTimestamp(triggerData.getTimestamp()));
		commissionInstanceRecord.setPointsBase_Forecasted(triggerData.getForecastedPoints().toBigDecimal());
		commissionInstanceRecord.setPointsBase_Invoiceable(triggerData.getInvoiceablePoints().toBigDecimal());
		commissionInstanceRecord.setPointsBase_Invoiced(triggerData.getInvoicedPoints().toBigDecimal());

		propagateAdditionalColumns(invoiceCandidateId, commissionInstanceRecord);

		saveRecord(commissionInstanceRecord);

		final CommissionInstanceId commissionInstanceId = CommissionInstanceId.ofRepoId(commissionInstanceRecord.getC_Commission_Instance_ID());

		final ImmutableMap<SalesCommissionShare, I_C_Commission_Share> shareToShareRecord = syncShareRecords(
				instance.getShares(),
				commissionInstanceId,
				instance.getConfig(),
				stagingRecords);

		for (final SalesCommissionShare share : instance.getShares())
		{
			createNewFactRecords(share.getFacts(), shareToShareRecord.get(share).getC_Commission_Share_ID(), stagingRecords);
		}
		return commissionInstanceId;
	}

	/** Set columns that are not needed by/relevant to the domain model but are needed for the the UI. */
	private void propagateAdditionalColumns(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			@NonNull final I_C_Commission_Instance commissionInstanceRecord)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = loadOutOfTrx(invoiceCandidateId, I_C_Invoice_Candidate.class);
		commissionInstanceRecord.setBill_BPartner_ID(invoiceCandidateRecord.getBill_BPartner_ID());
		commissionInstanceRecord.setC_Order_ID(invoiceCandidateRecord.getC_Order_ID());
	}

	private ImmutableMap<SalesCommissionShare, I_C_Commission_Share> syncShareRecords(
			@NonNull final ImmutableList<SalesCommissionShare> shares,
			@NonNull final CommissionInstanceId commissionInstanceId,
			@NonNull final CommissionConfig config,
			@NonNull final CommissionRecords records)
	{
		final ImmutableList<I_C_Commission_Share> shareRecords = records.getShareRecordsForInstanceRecordId(commissionInstanceId);

		// noteth that we have a UC to make sure that instanceId and level are unique
		final ImmutableMap<ArrayKey, I_C_Commission_Share> instanceRecordIdAndLevelToShareRecord = Maps.uniqueIndex(shareRecords, r -> ArrayKey.of(r.getC_Commission_Instance_ID(), r.getLevelHierarchy()));

		final ImmutableMap.Builder<SalesCommissionShare, I_C_Commission_Share> result = ImmutableMap.builder();

		final HashSet<SalesCommissionShare> unPersistedShares = new HashSet<>(shares);
		final HashSet<I_C_Commission_Share> shareRecordsToDelete = new HashSet<>(shareRecords);

		for (final SalesCommissionShare share : shares)
		{
			final ArrayKey instanceAndLevelKey = ArrayKey.of(commissionInstanceId.getRepoId(), share.getLevel().toInt());
			final I_C_Commission_Share shareRecordOrNull = instanceRecordIdAndLevelToShareRecord.get(instanceAndLevelKey);
			if (shareRecordOrNull != null)
			{
				shareRecordsToDelete.remove(shareRecordOrNull);
			}
			final I_C_Commission_Share shareRecord = createOrUpdateShareRecord(
					config.getContractFor(share.getBeneficiary()),
					commissionInstanceId,
					share,
					shareRecordOrNull);

			result.put(share, shareRecord);
			unPersistedShares.remove(share);
		}

		for (final SalesCommissionShare share : unPersistedShares)
		{
			final I_C_Commission_Share shareRecord = createOrUpdateShareRecord(
					config.getContractFor(share.getBeneficiary()),
					commissionInstanceId,
					share,
					null/* shareRecordOrNull */);
			result.put(share, shareRecord);
		}

		shareRecordsToDelete.forEach(InterfaceWrapperHelper::delete);

		return result.build();
	}

	private I_C_Commission_Share createOrUpdateShareRecord(
			@NonNull final CommissionContract contract,
			@NonNull final CommissionInstanceId commissionInstanceId,
			@NonNull final SalesCommissionShare share,
			@Nullable final I_C_Commission_Share shareRecordOrNull)
	{
		I_C_Commission_Share shareRecordToUse = shareRecordOrNull;
		if (shareRecordToUse == null)
		{
			shareRecordToUse = newInstance(I_C_Commission_Share.class);
			shareRecordToUse.setC_Commission_Instance_ID(commissionInstanceId.getRepoId());
			shareRecordToUse.setLevelHierarchy(share.getLevel().toInt());
		}
		shareRecordToUse.setC_BPartner_SalesRep_ID(share.getBeneficiary().getBPartnerId().getRepoId());
		shareRecordToUse.setC_Flatrate_Term_ID(contract.getId().getRepoId());
		shareRecordToUse.setPointsSum_Forecasted(share.getForecastedPointsSum().toBigDecimal());
		shareRecordToUse.setPointsSum_Invoiceable(share.getInvoiceablePointsSum().toBigDecimal());
		shareRecordToUse.setPointsSum_Invoiced(share.getInvoicedPointsSum().toBigDecimal());
		saveRecord(shareRecordToUse);

		return shareRecordToUse;
	}

	private void createNewFactRecords(
			@NonNull final ImmutableList<SalesCommissionFact> facts,
			final int commissionShareRecordId,
			@NonNull final CommissionRecords records)
	{
		final ImmutableList<I_C_Commission_Fact> factRecords = records.getSalesFactRecordsForShareRecordId(commissionShareRecordId);

		final ImmutableMap<ArrayKey, I_C_Commission_Fact> idAndTypeAndTimestampToFactRecord = Maps.uniqueIndex(
				factRecords,
				r -> ArrayKey.of(r.getC_Commission_Share_ID(), r.getCommission_Fact_State(), r.getCommissionFactTimestamp()));

		for (final SalesCommissionFact fact : facts)
		{
			final I_C_Commission_Fact factRecordOrNull = idAndTypeAndTimestampToFactRecord.get(
					ArrayKey.of(commissionShareRecordId, fact.getState().toString(), fact.getTimestamp().toString()));
			if (factRecordOrNull != null)
			{
				continue;
			}
			final I_C_Commission_Fact factRecord = newInstance(I_C_Commission_Fact.class);
			factRecord.setC_Commission_Share_ID(commissionShareRecordId);
			factRecord.setCommissionPoints(fact.getPoints().toBigDecimal());
			factRecord.setCommission_Fact_State(fact.getState().toString());
			factRecord.setCommissionFactTimestamp(fact.getTimestamp().toString());
			saveRecord(factRecord);
		}
	}

	private I_C_Commission_Instance loadOrNewInstanceRecord(@NonNull final CommissionInstance instance)
	{
		final I_C_Commission_Instance commissionInstanceRecord = loadOrNew(instance.getId(), I_C_Commission_Instance.class);
		return commissionInstanceRecord;
	}
}
