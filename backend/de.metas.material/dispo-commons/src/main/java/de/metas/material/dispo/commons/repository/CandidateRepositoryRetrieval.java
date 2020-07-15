package de.metas.material.dispo.commons.repository;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.commons.repository.repohelpers.DemandDetailRepoHelper;
import de.metas.material.dispo.commons.repository.repohelpers.PurchaseDetailRepoHelper;
import de.metas.material.dispo.commons.repository.repohelpers.RepositoryCommons;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class CandidateRepositoryRetrieval
{
	/**
	 * Load and return <b>the</b> single record this has the given {@code id} as parentId.
	 *
	 * @param parentId
	 * @return
	 */
	public Optional<Candidate> retrieveSingleChild(@NonNull final CandidateId parentId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_MD_Candidate candidateRecord = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, parentId.getRepoId())
				.create()
				.firstOnly(I_MD_Candidate.class);

		if (candidateRecord == null)
		{
			return Optional.empty();
		}

		return fromCandidateRecord(candidateRecord);
	}

	/**
	 * @return never {@code null}
	 */
	public List<Candidate> retrieveGroup(final MaterialDispoGroupId groupId)
	{
		if (groupId == null)
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, groupId.toInt())
				.addNotEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.orderBy(I_MD_Candidate.COLUMN_MD_Candidate_ID)
				.create()
				.stream()
				.map(r -> fromCandidateRecord(r).get())
				.collect(Collectors.toList());
	}

	@VisibleForTesting
	Optional<Candidate> fromCandidateRecord(final I_MD_Candidate candidateRecordOrNull)
	{
		if (candidateRecordOrNull == null || isNew(candidateRecordOrNull) || candidateRecordOrNull.getMD_Candidate_ID() <= 0)
		{
			return Optional.empty();
		}

		final CandidateBuilder builder = createAndInitializeBuilder(candidateRecordOrNull);

		final CandidateBusinessCase businessCase = getBusinesCaseOrNull(candidateRecordOrNull);
		builder.businessCase(businessCase);

		final ProductionDetail productionDetailOrNull = createProductionDetailOrNull(candidateRecordOrNull);
		final DistributionDetail distributionDetailOrNull = createDistributionDetailOrNull(candidateRecordOrNull);
		final PurchaseDetail purchaseDetailOrNull = PurchaseDetailRepoHelper.getSingleForCandidateRecordOrNull(candidateRecordOrNull);

		final int hasProductionDetail = productionDetailOrNull == null ? 0 : 1;
		final int hasDistributionDetail = distributionDetailOrNull == null ? 0 : 1;
		final int hasPurchaseDetail = purchaseDetailOrNull == null ? 0 : 1;

		Check.errorIf(hasProductionDetail + hasDistributionDetail + hasPurchaseDetail > 1,
				"A candidate may not have both a distribution, production and a production detail; candidateRecord={}", candidateRecordOrNull);

		final DemandDetail demandDetailOrNull = createDemandDetailOrNull(candidateRecordOrNull);

		final BusinessCaseDetail businessCaseDetail = CoalesceUtil.coalesce(productionDetailOrNull, distributionDetailOrNull, purchaseDetailOrNull, demandDetailOrNull);
		builder.businessCaseDetail(businessCaseDetail);
		if (hasProductionDetail > 0 || hasDistributionDetail > 0 || hasPurchaseDetail > 0)
		{
			builder.additionalDemandDetail(demandDetailOrNull);
		}

		builder.transactionDetails(createTransactionDetails(candidateRecordOrNull));

		return Optional.of(builder.build());
	}

	private CandidateBusinessCase getBusinesCaseOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		CandidateBusinessCase subType = null;
		if (!Check.isEmpty(candidateRecord.getMD_Candidate_BusinessCase()))
		{
			subType = CandidateBusinessCase.valueOf(candidateRecord.getMD_Candidate_BusinessCase());
		}
		return subType;
	}

	private CandidateBuilder createAndInitializeBuilder(@NonNull final I_MD_Candidate candidateRecord)
	{
		final Timestamp dateProjected = Preconditions.checkNotNull(candidateRecord.getDateProjected(),
				"Given parameter candidateRecord needs to have a not-null dateProjected; candidateRecord=%s",
				candidateRecord);
		final String md_candidate_type = Preconditions.checkNotNull(candidateRecord.getMD_Candidate_Type(),
				"Given parameter candidateRecord needs to have a not-null MD_Candidate_Type; candidateRecord=%s",
				candidateRecord);

		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				candidateRecord.getM_Product_ID(),
				getEffectiveStorageAttributesKey(candidateRecord),
				candidateRecord.getM_AttributeSetInstance_ID());

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.productDescriptor(productDescriptor)
				.quantity(candidateRecord.getQty())
				.warehouseId(WarehouseId.ofRepoId(candidateRecord.getM_Warehouse_ID()))
				.customerId(BPartnerId.ofRepoIdOrNull(candidateRecord.getC_BPartner_Customer_ID()))

				// make sure to add a Date and not a Timestamp to avoid confusing Candidate's equals() and hashCode() methods
				.date(TimeUtil.asInstant(dateProjected))
				.build();

		final CandidateBuilder candidateBuilder = Candidate.builder()
				.id(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(candidateRecord.getAD_Client_ID(), candidateRecord.getAD_Org_ID()))
				.seqNo(candidateRecord.getSeqNo())
				.type(CandidateType.ofCode(md_candidate_type))

				// if the record has a group id, then set it.
				.groupId(MaterialDispoGroupId.ofIntOrNull(candidateRecord.getMD_Candidate_GroupId()))
				.materialDescriptor(materialDescriptor);

		if (candidateRecord.getMD_Candidate_Parent_ID() > 0)
		{
			candidateBuilder.parentId(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_Parent_ID()));
		}
		return candidateBuilder;
	}

	private static AttributesKey getEffectiveStorageAttributesKey(@NonNull final I_MD_Candidate candidateRecord)
	{
		final AttributesKey attributesKey;
		if (Check.isEmpty(candidateRecord.getStorageAttributesKey(), true))
		{
			attributesKey = AttributesKey.ALL;
		}
		else
		{
			attributesKey = AttributesKey.ofString(candidateRecord.getStorageAttributesKey());
		}
		return attributesKey;
	}

	private static ProductionDetail createProductionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Prod_Detail //
		productionDetailRecord = RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Prod_Detail.class);
		if (productionDetailRecord == null)
		{
			return null;
		}

		return ProductionDetail.builder()
				.advised(Flag.of(productionDetailRecord.isAdvised()))
				.pickDirectlyIfFeasible(Flag.of(productionDetailRecord.isPickDirectlyIfFeasible()))
				.description(productionDetailRecord.getDescription())
				.plantId(ResourceId.ofRepoIdOrNull(productionDetailRecord.getPP_Plant_ID()))
				.productBomLineId(productionDetailRecord.getPP_Product_BOMLine_ID())
				.productPlanningId(productionDetailRecord.getPP_Product_Planning_ID())
				.ppOrderId(productionDetailRecord.getPP_Order_ID())
				.ppOrderLineId(productionDetailRecord.getPP_Order_BOMLine_ID())
				.ppOrderDocStatus(DocStatus.ofNullableCode(productionDetailRecord.getPP_Order_DocStatus()))
				.qty(productionDetailRecord.getPlannedQty())
				.build();
	}

	private static DistributionDetail createDistributionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Dist_Detail distributionDetail = //
				RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Dist_Detail.class);
		if (distributionDetail == null)
		{
			return null;
		}

		return DistributionDetail.forDistributionDetailRecord(distributionDetail);
	}

	private static DemandDetail createDemandDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Demand_Detail demandDetailRecord = //
				RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Demand_Detail.class);
		if (demandDetailRecord == null)
		{
			return null;
		}

		return DemandDetailRepoHelper.forDemandDetailRecord(demandDetailRecord);
	}

	private static List<TransactionDetail> createTransactionDetails(@NonNull final I_MD_Candidate candidateRecord)
	{
		final List<I_MD_Candidate_Transaction_Detail> transactionDetailRecords = //
				RepositoryCommons.createCandidateDetailQueryBuilder(candidateRecord, I_MD_Candidate_Transaction_Detail.class)
						.list();

		final ImmutableList.Builder<TransactionDetail> result = ImmutableList.builder();
		for (final I_MD_Candidate_Transaction_Detail transactionDetailRecord : transactionDetailRecords)
		{
			final TransactionDetail transactionDetail = TransactionDetail
					.builder()
					.quantity(transactionDetailRecord.getMovementQty())
					.storageAttributesKey(getEffectiveStorageAttributesKey(candidateRecord))
					.attributeSetInstanceId(candidateRecord.getM_AttributeSetInstance_ID())
					.transactionId(transactionDetailRecord.getM_Transaction_ID())
					.resetStockPInstanceId(ResetStockPInstanceId.ofRepoIdOrNull(transactionDetailRecord.getAD_PInstance_ResetStock_ID()))
					.stockId(transactionDetailRecord.getMD_Stock_ID())
					.transactionDate(TimeUtil.asInstant(transactionDetailRecord.getTransactionDate()))
					.complete(true)
					.build();
			result.add(transactionDetail);
		}
		return result.build();
	}

	public Candidate retrieveLatestMatchOrNull(@NonNull final CandidatesQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = RepositoryCommons.mkQueryBuilder(query);

		final I_MD_Candidate candidateRecordOrNull = addOrderingLatestFirst(queryBuilderWithoutOrdering)
				.create()
				.first();

		return fromCandidateRecord(candidateRecordOrNull).orElse(null);
	}

	private static IQueryBuilder<I_MD_Candidate> addOrderingLatestFirst(
			@NonNull final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering)
	{
		return queryBuilderWithoutOrdering
				.orderBy()
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_SeqNo)
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID)
				.endOrderBy();
	}

	public List<Candidate> retrieveOrderedByDateAndSeqNo(@NonNull final CandidatesQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = RepositoryCommons.mkQueryBuilder(query);

		final Stream<I_MD_Candidate> candidateRecords = addOrderingYoungestFirst(queryBuilderWithoutOrdering)
				.create()
				.stream();

		return candidateRecords
				.map(record -> fromCandidateRecord(record).get())
				.collect(Collectors.toList());
	}

	private IQueryBuilder<I_MD_Candidate> addOrderingYoungestFirst(final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering)
	{
		return queryBuilderWithoutOrdering
				.orderBy()
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_SeqNo)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID)
				.endOrderBy();
	}

	public List<Candidate> retrieveCandidatesForPPOrderId(final int ppOrderId)
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.productionDetailsQuery(ProductionDetailsQuery.builder()
						.ppOrderId(ppOrderId)
						.build())
				.build();
		return retrieveOrderedByDateAndSeqNo(query);
	}
}
