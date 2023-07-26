package de.metas.contracts.commission.commissioninstance.services.repos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Payer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance.CommissionInstanceBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionSettingsLineId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShareId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.services.CommissionConfigProvider;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionRecordStagingService.CommissionStagingRecords;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionConfig;
import de.metas.contracts.commission.model.I_C_Commission_Fact;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private static final Logger logger = LogManager.getLogger(CommissionInstanceRepository.class);

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final CommissionRecordStagingService commissionRecordStagingService;
	private final CommissionConfigProvider commissionConfigProvider;

	public CommissionInstanceRepository(
			@NonNull final CommissionRecordStagingService commissionInstanceRecordStagingService,
			@NonNull final CommissionConfigProvider commissionConfigProvider)
	{
		this.commissionRecordStagingService = commissionInstanceRecordStagingService;
		this.commissionConfigProvider = commissionConfigProvider;
	}

	public CommissionInstance getById(@NonNull final CommissionInstanceId commissionInstanceId)
	{
		final CommissionStagingRecords stagingRecords = commissionRecordStagingService.retrieveRecordsForInstanceId(ImmutableList.of(commissionInstanceId));

		final I_C_Commission_Instance instanceRecord = stagingRecords.getInstanceRecordIdToInstance().get(commissionInstanceId.getRepoId());
		final CommissionInstance instance = loadCommissionInstance(instanceRecord, stagingRecords);
		return instance;
	}

	public Optional<CommissionInstance> getByDocumentId(@NonNull final CommissionTriggerDocumentId commissionTriggerDocumentId)
	{
		final CommissionStagingRecords records = commissionRecordStagingService.retrieveRecordsForTriggerDocumentId(commissionTriggerDocumentId);

		final I_C_Commission_Instance instanceRecord = records.getDocumentIdToInstanceRecords().get(commissionTriggerDocumentId);
		if (instanceRecord == null)
		{
			logger.debug("Found no C_Commission_Instance record for commissionTriggerDocumentId={}", commissionTriggerDocumentId);
			return Optional.empty();
		}

		final CommissionInstance instance = loadCommissionInstance(instanceRecord, records);
		return Optional.of(instance);
	}

	private CommissionInstance loadCommissionInstance(
			@NonNull final I_C_Commission_Instance instanceRecord,
			@NonNull final CommissionStagingRecords stagingRecords)
	{
		final CommissionInstanceId commissionInstanceId = CommissionInstanceId.ofRepoId(instanceRecord.getC_Commission_Instance_ID());

		final List<I_C_Commission_Share> shareRecords = stagingRecords.getShareRecordsForInstanceRecordId(commissionInstanceId);

		final ImmutableList<FlatrateTermId> flatrateTermIds = shareRecords.stream()
				.map(I_C_Commission_Share::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		final CommissionConfigProvider.ConfigRequestForExistingInstance request = CommissionConfigProvider.ConfigRequestForExistingInstance.builder()
				.contractIds(flatrateTermIds)
				.customerBPartnerId(BPartnerId.ofRepoId(instanceRecord.getBill_BPartner_ID()))
				.salesProductId(ProductId.ofRepoId(instanceRecord.getM_Product_Order_ID()))
				.build();

		final ImmutableMap<FlatrateTermId, CommissionConfig> contractId2Config = commissionConfigProvider.createForExistingInstance(request);

		final CommissionInstanceBuilder instanceBuilder = CommissionInstance.builder()
				.id(commissionInstanceId)
				.currentTriggerData(extractCommissionTriggerData(instanceRecord));

		for (final I_C_Commission_Share shareRecord : shareRecords)
		{
			final CommissionShare.CommissionShareBuilder shareBuilder = createShareBuilder(shareRecord);

			final FlatrateTermId contractId = FlatrateTermId.ofRepoId(shareRecord.getC_Flatrate_Term_ID());
			final CommissionConfig config = contractId2Config.get(contractId);
			shareBuilder.config(config);

			final ImmutableList<I_C_Commission_Fact> salesFactRecords = stagingRecords.getSalesFactRecordsForShareRecordId(shareRecord.getC_Commission_Share_ID());
			shareBuilder.facts(createFacts(salesFactRecords));

			final CommissionShare share = shareBuilder.build();
			instanceBuilder.share(share);
		}
		return instanceBuilder.build();
	}

	public Beneficiary createBeneficiary(final int c_BPartner_SalesRep_ID)
	{
		return Beneficiary.of(BPartnerId.ofRepoId(c_BPartner_SalesRep_ID));
	}

	private CommissionTriggerData extractCommissionTriggerData(@NonNull final I_C_Commission_Instance instanceRecord)
	{
		final CommissionTriggerType triggerType = CommissionTriggerType.ofCode(instanceRecord.getCommissionTrigger_Type());

		final CommissionTriggerDocumentId triggerDocumentId = CommissionInstanceRepoTools.extractCommissionTriggerDocumentId(instanceRecord);

		return CommissionTriggerData.builder()
				.orgId(OrgId.ofRepoId(instanceRecord.getAD_Org_ID()))
				.triggerType(triggerType)
				.triggerDocumentId(triggerDocumentId)
				.triggerDocumentDate(TimeUtil.asLocalDate(instanceRecord.getCommissionDate()))
				.forecastedBasePoints(CommissionPoints.of(instanceRecord.getPointsBase_Forecasted()))
				.invoiceableBasePoints(CommissionPoints.of(instanceRecord.getPointsBase_Invoiceable()))
				.invoicedBasePoints(CommissionPoints.of(instanceRecord.getPointsBase_Invoiced()))
				.tradedCommissionPercent(Percent.ZERO)
				.timestamp(TimeUtil.asInstant(instanceRecord.getMostRecentTriggerTimestamp()))
				.build();
	}

	private CommissionShare.CommissionShareBuilder createShareBuilder(@NonNull final I_C_Commission_Share shareRecord)
	{
		final CommissionShare.CommissionShareBuilder share = CommissionShare.builder()
				.id(CommissionShareId.ofRepoId(shareRecord.getC_Commission_Share_ID()))
				.beneficiary(Beneficiary.of(BPartnerId.ofRepoId(shareRecord.getC_BPartner_SalesRep_ID())))
				.level(HierarchyLevel.of(shareRecord.getLevelHierarchy()))
				.soTrx(SOTrx.ofBoolean(shareRecord.isSOTrx()))
				.payer(Payer.of(BPartnerId.ofRepoId(shareRecord.getC_BPartner_Payer_ID())))
				;

		return share;
	}

	private ImmutableList<CommissionFact> createFacts(@NonNull final ImmutableList<I_C_Commission_Fact> factRecords)
	{
		final ImmutableList.Builder<CommissionFact> facts = ImmutableList.builder();

		for (final I_C_Commission_Fact factRecord : factRecords)
		{
			final CommissionFact fact = CommissionFact.builder()
					.points(CommissionPoints.of(factRecord.getCommissionPoints()))
					.state(CommissionState.valueOf(factRecord.getCommission_Fact_State()))
					.timestamp(TimeUtil.deserializeInstant(factRecord.getCommissionFactTimestamp()))
					.build();
			facts.add(fact);
		}
		return facts.build();
	}

	public CommissionInstanceId save(@NonNull final CommissionInstance instance)
	{
		final CommissionInstanceId instanceIdOrNull = instance.getId();
		final CommissionStagingRecords stagingRecords;
		if (instanceIdOrNull == null)
		{
			stagingRecords = CommissionStagingRecords.EMPTY;
		}
		else
		{
			stagingRecords = commissionRecordStagingService.retrieveRecordsForInstanceId(ImmutableList.of(instanceIdOrNull));

		}
		final CommissionTriggerData triggerData = instance.getCurrentTriggerData();

		final I_C_Commission_Instance commissionInstanceRecord = loadOrNewInstanceRecord(instance.getId());

		if (triggerData.isInvoiceCandidateWasDeleted())
		{
			commissionInstanceRecord.setC_Invoice_Candidate_ID(-1);
			commissionInstanceRecord.setC_Invoice_ID(-1);
			commissionInstanceRecord.setC_InvoiceLine_ID(-1);
		}
		else
		{
			final CommissionTriggerType triggerType = triggerData.getTriggerType();
			final CommissionTriggerDocumentId triggerDocumentId = triggerData.getTriggerDocumentId();
			switch (triggerType)
			{
				case InvoiceCandidate:
					final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.cast(triggerDocumentId.getRepoIdAware());
					commissionInstanceRecord.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
					propagateAdditionalColumns(invoiceCandidateId, commissionInstanceRecord);
					break;
				case SalesInvoice:
					final InvoiceLineId invoiceLineId = InvoiceLineId.cast(triggerDocumentId.getRepoIdAware());
					commissionInstanceRecord.setC_InvoiceLine_ID(invoiceLineId.getRepoId());
					propagateAdditionalColumns(invoiceLineId, commissionInstanceRecord);
					break;
				case SalesCreditmemo:
					final InvoiceLineId creditMemoInvoiceLineId = InvoiceLineId.cast(triggerDocumentId.getRepoIdAware());
					commissionInstanceRecord.setC_InvoiceLine_ID(creditMemoInvoiceLineId.getRepoId());
					propagateAdditionalColumns(creditMemoInvoiceLineId, commissionInstanceRecord);
					break;
				case MediatedOrder:
					final OrderLineId orderLineId = OrderLineId.cast(triggerDocumentId.getRepoIdAware());
					commissionInstanceRecord.setC_OrderLine_ID(orderLineId.getRepoId());
					propagateAdditionalColumns(orderLineId, commissionInstanceRecord);
					break;
				default:
					throw new AdempiereException("Unexpected triggerType=" + triggerType);
			}
			commissionInstanceRecord.setCommissionTrigger_Type(triggerType.getCode());
		}

		final OrgId orgId = instance.getCurrentTriggerData().getOrgId();

		commissionInstanceRecord.setAD_Org_ID(orgId.getRepoId());
		commissionInstanceRecord.setMostRecentTriggerTimestamp(TimeUtil.asTimestamp(triggerData.getTimestamp()));
		commissionInstanceRecord.setCommissionDate(TimeUtil.asTimestamp(triggerData.getTriggerDocumentDate()));
		commissionInstanceRecord.setPointsBase_Forecasted(triggerData.getForecastedBasePoints().toBigDecimal());
		commissionInstanceRecord.setPointsBase_Invoiceable(triggerData.getInvoiceableBasePoints().toBigDecimal());
		commissionInstanceRecord.setPointsBase_Invoiced(triggerData.getInvoicedBasePoints().toBigDecimal());

		saveRecord(commissionInstanceRecord);

		final CommissionInstanceId commissionInstanceId = CommissionInstanceId.ofRepoId(commissionInstanceRecord.getC_Commission_Instance_ID());

		final ImmutableMap<CommissionShare, I_C_Commission_Share> shareToShareRecord = syncShareRecords(
				instance.getShares(),
				commissionInstanceId,
				orgId,
				stagingRecords);

		for (final CommissionShare share : instance.getShares())
		{
			createNewFactRecords(
					share.getFacts(),
					shareToShareRecord.get(share).getC_Commission_Share_ID(),
					orgId,
					stagingRecords);
		}
		return commissionInstanceId;
	}

	private void propagateAdditionalColumns(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			@NonNull final I_C_Commission_Instance commissionInstanceRecord)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = loadOutOfTrx(invoiceCandidateId, I_C_Invoice_Candidate.class);
		commissionInstanceRecord.setPOReference(invoiceCandidateRecord.getPOReference());
		commissionInstanceRecord.setBill_BPartner_ID(invoiceCandidateRecord.getBill_BPartner_ID());
		commissionInstanceRecord.setM_Product_Order_ID(invoiceCandidateRecord.getM_Product_ID());
		commissionInstanceRecord.setC_Order_ID(invoiceCandidateRecord.getC_Order_ID());
	}

	private void propagateAdditionalColumns(
			@NonNull final InvoiceLineId invoiceLineId,
			@NonNull final I_C_Commission_Instance commissionInstanceRecord)
	{
		final I_C_InvoiceLine invoiceLineRecord = loadOutOfTrx(invoiceLineId, I_C_InvoiceLine.class);
		commissionInstanceRecord.setPOReference(invoiceLineRecord.getC_Invoice().getPOReference());
		commissionInstanceRecord.setBill_BPartner_ID(invoiceLineRecord.getC_Invoice().getC_BPartner_ID());
		commissionInstanceRecord.setC_Invoice_ID(invoiceLineRecord.getC_Invoice_ID());
		commissionInstanceRecord.setM_Product_Order_ID(invoiceLineRecord.getM_Product_ID());
	}

	private ImmutableMap<CommissionShare, I_C_Commission_Share> syncShareRecords(
			@NonNull final ImmutableList<CommissionShare> shares,
			@NonNull final CommissionInstanceId commissionInstanceId,
			@NonNull final OrgId orgId,
			@NonNull final CommissionStagingRecords records)
	{
		final ImmutableList<I_C_Commission_Share> shareRecords = records.getShareRecordsForInstanceRecordId(commissionInstanceId);

		// noteth that we have a UC to make sure that instanceId, level and contract are unique
		final ImmutableMap<ArrayKey, I_C_Commission_Share> instanceRecordIdAndLevelToShareRecord = Maps.uniqueIndex(shareRecords, r -> ArrayKey.of(r.getC_Commission_Instance_ID(), r.getLevelHierarchy(), r.getC_Flatrate_Term_ID()));

		final ImmutableMap.Builder<CommissionShare, I_C_Commission_Share> result = ImmutableMap.builder();

		final HashSet<CommissionShare> unPersistedShares = new HashSet<>(shares);
		final HashSet<I_C_Commission_Share> shareRecordsToDelete = new HashSet<>(shareRecords);

		for (final CommissionShare share : shares)
		{
			final ArrayKey instanceAndLevelKey = ArrayKey.of(commissionInstanceId.getRepoId(), share.getLevel().toInt(), share.getContract().getId().getRepoId());
			final I_C_Commission_Share shareRecordOrNull = instanceRecordIdAndLevelToShareRecord.get(instanceAndLevelKey);
			if (shareRecordOrNull != null)
			{
				shareRecordsToDelete.remove(shareRecordOrNull);
			}
			final I_C_Commission_Share shareRecord = createOrUpdateShareRecord(
					share,
					commissionInstanceId,
					orgId,
					shareRecordOrNull);

			result.put(share, shareRecord);
			unPersistedShares.remove(share);
		}

		for (final CommissionShare share : unPersistedShares)
		{
			final I_C_Commission_Share shareRecord = createOrUpdateShareRecord(
					share,
					commissionInstanceId,
					orgId,
					null/* shareRecordOrNull */);
			result.put(share, shareRecord);
		}

		shareRecordsToDelete.forEach(InterfaceWrapperHelper::delete);

		return result.build();
	}

	private I_C_Commission_Share createOrUpdateShareRecord(
			@NonNull final CommissionShare share,
			@NonNull final CommissionInstanceId commissionInstanceId,
			@NonNull final OrgId orgId,
			@Nullable final I_C_Commission_Share shareRecordOrNull)
	{
		final I_C_Commission_Share shareRecordToUse = Optional.ofNullable(shareRecordOrNull)
				.orElseGet(() -> {
					final I_C_Commission_Share newShare = newInstance(I_C_Commission_Share.class);
					newShare.setC_Commission_Instance_ID(commissionInstanceId.getRepoId());
					newShare.setLevelHierarchy(share.getLevel().toInt());
					newShare.setIsSimulation(share.getContract().isSimulation());
					return newShare;
				});

		shareRecordToUse.setAD_Org_ID(orgId.getRepoId());
		shareRecordToUse.setIsSOTrx(share.getSoTrx().isSales());

		shareRecordToUse.setC_BPartner_Payer_ID(share.getPayer().getBPartnerId().getRepoId());
		shareRecordToUse.setC_BPartner_SalesRep_ID(share.getBeneficiary().getBPartnerId().getRepoId());
		shareRecordToUse.setC_Flatrate_Term_ID(share.getContract().getId().getRepoId());
		shareRecordToUse.setCommission_Product_ID(share.getCommissionProductId().getRepoId());
		shareRecordToUse.setPointsSum_Forecasted(share.getForecastedPointsSum().toBigDecimal());
		shareRecordToUse.setPointsSum_Invoiceable(share.getInvoiceablePointsSum().toBigDecimal());
		shareRecordToUse.setPointsSum_Invoiced(share.getInvoicedPointsSum().toBigDecimal());

		MediatedCommissionConfig.castOrEmpty(share.getConfig())
				.ifPresent(mediatedConfig -> shareRecordToUse.setC_MediatedCommissionSettingsLine_ID(mediatedConfig.getMediatedCommissionSettingsLineId().getRepoId()));

		HierarchyContract.castOrEmpty(share.getContract())
				.ifPresent(hierarchyContract -> shareRecordToUse.setC_CommissionSettingsLine_ID(CommissionSettingsLineId.toRepoId(hierarchyContract.getCommissionSettingsLineId())));

		saveRecord(shareRecordToUse);

		return shareRecordToUse;
	}

	private void createNewFactRecords(
			@NonNull final ImmutableList<CommissionFact> facts,
			final int commissionShareRecordId,
			@NonNull final OrgId orgId,
			@NonNull final CommissionStagingRecords records)
	{
		final ImmutableList<I_C_Commission_Fact> factRecords = records.getSalesFactRecordsForShareRecordId(commissionShareRecordId);

		final ImmutableMap<ArrayKey, I_C_Commission_Fact> idAndTypeAndTimestampToFactRecord = Maps.uniqueIndex(
				factRecords,
				r -> ArrayKey.of(r.getC_Commission_Share_ID(), r.getCommission_Fact_State(), r.getCommissionFactTimestamp()));

		for (final CommissionFact fact : facts)
		{
			final I_C_Commission_Fact factRecordOrNull = idAndTypeAndTimestampToFactRecord.get(
					ArrayKey.of(commissionShareRecordId, fact.getState().toString(), TimeUtil.serializeInstant(fact.getTimestamp())));
			if (factRecordOrNull != null)
			{
				continue;
			}
			final I_C_Commission_Fact factRecord = newInstance(I_C_Commission_Fact.class);
			factRecord.setAD_Org_ID(orgId.getRepoId());
			factRecord.setC_Commission_Share_ID(commissionShareRecordId);
			factRecord.setCommissionPoints(fact.getPoints().toBigDecimal());
			factRecord.setCommission_Fact_State(fact.getState().toString());
			factRecord.setCommissionFactTimestamp(TimeUtil.serializeInstant(fact.getTimestamp()));
			saveRecord(factRecord);
		}
	}

	@NonNull
	private I_C_Commission_Instance loadOrNewInstanceRecord(@Nullable final CommissionInstanceId instanceId)
	{
		return loadOrNew(instanceId, I_C_Commission_Instance.class);
	}

	private void propagateAdditionalColumns(
			@NonNull final OrderLineId orderLineId,
			@NonNull final I_C_Commission_Instance commissionInstanceRecord)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);
		final I_C_Order order =  orderDAO.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		commissionInstanceRecord.setPOReference(order.getPOReference());
		commissionInstanceRecord.setBill_BPartner_ID(orderLine.getC_BPartner_ID());
		commissionInstanceRecord.setC_Order_ID(orderLine.getC_Order_ID());
		commissionInstanceRecord.setM_Product_Order_ID(orderLine.getM_Product_ID());
	}

}
