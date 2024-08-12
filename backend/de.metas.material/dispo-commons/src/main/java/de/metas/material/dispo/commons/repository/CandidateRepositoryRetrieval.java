package de.metas.material.dispo.commons.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.CandidatesGroup;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.StockChangeDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.commons.repository.repohelpers.DemandDetailRepoHelper;
import de.metas.material.dispo.commons.repository.repohelpers.PurchaseDetailRepoHelper;
import de.metas.material.dispo.commons.repository.repohelpers.RepositoryCommons;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;

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
	private final DimensionService dimensionService;
	private final StockChangeDetailRepo stockChangeDetailRepo;

	public CandidateRepositoryRetrieval(
			@NonNull final DimensionService dimensionService,
			@NonNull final StockChangeDetailRepo stockChangeDetailRepo)
	{
		this.dimensionService = dimensionService;
		this.stockChangeDetailRepo = stockChangeDetailRepo;
	}

	/**
	 * Load and return <b>the</b> single record this has the given {@code id} as parentId.
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
	public CandidatesGroup retrieveGroup(final MaterialDispoGroupId groupId)
	{
		if (groupId == null)
		{
			return CandidatesGroup.EMPTY;
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
				.collect(CandidatesGroup.collect());
	}

	@VisibleForTesting
	Optional<Candidate> fromCandidateRecord(final I_MD_Candidate candidateRecordOrNull)
	{
		if (candidateRecordOrNull == null || isNew(candidateRecordOrNull) || candidateRecordOrNull.getMD_Candidate_ID() <= 0)
		{
			return Optional.empty();
		}

		final CandidateBuilder builder = createAndInitializeBuilder(candidateRecordOrNull);

		builder.businessCase(extractBusinessCaseOrNull(candidateRecordOrNull));

		final CandidateId candidateId = CandidateId.ofRepoId(candidateRecordOrNull.getMD_Candidate_ID());
		final ProductionDetail productionDetailOrNull = retrieveProductionDetailOrNull(candidateId);
		final DistributionDetail distributionDetailOrNull = retrieveDistributionDetailOrNull(candidateId);
		final PurchaseDetail purchaseDetailOrNull = PurchaseDetailRepoHelper.getSingleForCandidateRecordOrNull(candidateId);
		final StockChangeDetail stockChangeDetailOrNull = stockChangeDetailRepo.getSingleForCandidateRecordOrNull(candidateId);

		final int hasProductionDetail = productionDetailOrNull == null ? 0 : 1;
		final int hasDistributionDetail = distributionDetailOrNull == null ? 0 : 1;
		final int hasPurchaseDetail = purchaseDetailOrNull == null ? 0 : 1;
		final int hasStockChangeDetail = stockChangeDetailOrNull == null ? 0 : 1;

		Check.errorIf(hasProductionDetail + hasDistributionDetail + hasPurchaseDetail + hasStockChangeDetail > 1,
				"A candidate may not have both a distribution, production, production detail and a hasStockChangeDetail; candidateRecord={}", candidateRecordOrNull);

		final DemandDetail demandDetailOrNull = retrieveDemandDetailOrNull(candidateId);

		final BusinessCaseDetail businessCaseDetail = CoalesceUtil.coalesce(productionDetailOrNull, distributionDetailOrNull, purchaseDetailOrNull,
				demandDetailOrNull, stockChangeDetailOrNull);
		builder.businessCaseDetail(businessCaseDetail);
		if (hasProductionDetail > 0 || hasDistributionDetail > 0 || hasPurchaseDetail > 0)
		{
			builder.additionalDemandDetail(demandDetailOrNull);
		}

		builder.transactionDetails(getTransactionDetails(candidateRecordOrNull));
		builder.dimension(dimensionService.getFromRecord(candidateRecordOrNull));

		return Optional.of(builder.build());
	}

	@Nullable
	private static CandidateBusinessCase extractBusinessCaseOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		return CandidateBusinessCase.ofNullableCode(candidateRecord.getMD_Candidate_BusinessCase());
	}

	private static CandidateBuilder createAndInitializeBuilder(@NonNull final I_MD_Candidate candidateRecord)
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
				.reservedForCustomer(candidateRecord.isReservedForCustomer())
				.date(TimeUtil.asInstant(dateProjected))
				.build();

		final MinMaxDescriptor minMaxDescriptor = MinMaxDescriptor.builder()
				.min(candidateRecord.getReplenish_MinQty())
				.max(candidateRecord.getReplenish_MaxQty())
				.build();

		final CandidateBuilder candidateBuilder = Candidate.builder()
				.id(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(candidateRecord.getAD_Client_ID(), candidateRecord.getAD_Org_ID()))
				.seqNo(candidateRecord.getSeqNo())
				.type(CandidateType.ofCode(md_candidate_type))

				// if the record has a group id, then set it.
				.groupId(MaterialDispoGroupId.ofIntOrNull(candidateRecord.getMD_Candidate_GroupId()))
				.materialDescriptor(materialDescriptor)
				.minMaxDescriptor(minMaxDescriptor)
				.simulated(isSimulated(candidateRecord))
				.lotForLot(candidateRecord.getIsLotForLot());

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

	@Nullable
	private static ProductionDetail retrieveProductionDetailOrNull(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate_Prod_Detail productionDetailRecord = RepositoryCommons.retrieveSingleCandidateDetail(candidateId, I_MD_Candidate_Prod_Detail.class);
		return productionDetailRecord == null ? null : fromRecord(productionDetailRecord);
	}

	private static ProductionDetail fromRecord(final I_MD_Candidate_Prod_Detail productionDetailRecord)
	{
		return ProductionDetail.builder()
				.advised(Flag.of(productionDetailRecord.isAdvised()))
				.pickDirectlyIfFeasible(Flag.of(productionDetailRecord.isPickDirectlyIfFeasible()))
				.description(productionDetailRecord.getDescription())
				.plantId(ResourceId.ofRepoIdOrNull(productionDetailRecord.getPP_Plant_ID()))
				.productBomLineId(productionDetailRecord.getPP_Product_BOMLine_ID())
				.productPlanningId(productionDetailRecord.getPP_Product_Planning_ID())
				.ppOrderRef(extractPPOrderRef(productionDetailRecord))
				.ppOrderDocStatus(DocStatus.ofNullableCode(productionDetailRecord.getPP_Order_DocStatus()))
				.qty(productionDetailRecord.getPlannedQty())
				.build();
	}

	@Nullable
	private static PPOrderRef extractPPOrderRef(final I_MD_Candidate_Prod_Detail record)
	{
		final int ppOrderCandidateId = record.getPP_Order_Candidate_ID();
		final PPOrderId ppOrderId = PPOrderId.ofRepoIdOrNull(record.getPP_Order_ID());
		if (ppOrderCandidateId <= 0 && ppOrderId == null)
		{
			return null;
		}

		return PPOrderRef.builder()
				.ppOrderCandidateId(ppOrderCandidateId)
				.ppOrderLineCandidateId(record.getPP_OrderLine_Candidate_ID())
				.ppOrderId(ppOrderId)
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoIdOrNull(record.getPP_Order_BOMLine_ID()))
				.build();
	}

	@Nullable
	private static DistributionDetail retrieveDistributionDetailOrNull(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate_Dist_Detail distributionDetail = RepositoryCommons.retrieveSingleCandidateDetail(candidateId, I_MD_Candidate_Dist_Detail.class);
		return distributionDetail == null ? null : DistributionDetail.ofRecord(distributionDetail);
	}

	@Nullable
	private static DemandDetail retrieveDemandDetailOrNull(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate_Demand_Detail demandDetailRecord = RepositoryCommons.retrieveSingleCandidateDetail(candidateId, I_MD_Candidate_Demand_Detail.class);
		return demandDetailRecord == null ? null : DemandDetailRepoHelper.forDemandDetailRecord(demandDetailRecord);
	}

	@NonNull
	private static List<TransactionDetail> fromRecords(
			@NonNull final List<I_MD_Candidate_Transaction_Detail> transactionDetailRecords,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		final ImmutableList.Builder<TransactionDetail> result = ImmutableList.builder();
		for (final I_MD_Candidate_Transaction_Detail transactionDetailRecord : transactionDetailRecords)
		{
			final TransactionDetail transactionDetail = fromRecord(transactionDetailRecord, candidateRecord);
			result.add(transactionDetail);
		}
		return result.build();
	}

	private static TransactionDetail fromRecord(final I_MD_Candidate_Transaction_Detail transactionDetailRecord, final @NonNull I_MD_Candidate candidateRecord)
	{
		return TransactionDetail.builder()
				.quantity(transactionDetailRecord.getMovementQty())
				.storageAttributesKey(getEffectiveStorageAttributesKey(candidateRecord))
				.attributeSetInstanceId(candidateRecord.getM_AttributeSetInstance_ID())
				.transactionId(transactionDetailRecord.getM_Transaction_ID())
				.resetStockPInstanceId(ResetStockPInstanceId.ofRepoIdOrNull(transactionDetailRecord.getAD_PInstance_ResetStock_ID()))
				.stockId(transactionDetailRecord.getMD_Stock_ID())
				.transactionDate(TimeUtil.asInstant(transactionDetailRecord.getTransactionDate()))
				.complete(true)
				.build();
	}

	@Nullable
	public Candidate retrieveLatestMatchOrNull(@NonNull final CandidatesQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = RepositoryCommons.mkQueryBuilder(query);

		final I_MD_Candidate candidateRecordOrNull = addOrderingLatestFirst(queryBuilderWithoutOrdering)
				.create()
				.first();

		return fromCandidateRecord(candidateRecordOrNull).orElse(null);
	}

	@NonNull
	public Optional<Candidate> retrieveLatestMatch(@NonNull final CandidatesQuery query)
	{
		return Optional.ofNullable(retrieveLatestMatchOrNull(query));
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
		return retrieveForQueryBuilder(queryBuilderWithoutOrdering);
	}

	/**
	 * Only use this method in testing
	 */
	@VisibleForTesting
	public List<Candidate> retrieveAllNotStockOrderedByDateAndSeqNoFor(@NonNull final ProductId productId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addNotEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_M_Product_ID, productId);

		return retrieveForQueryBuilder(queryBuilderWithoutOrdering);
	}

	@NonNull
	private List<Candidate> retrieveForQueryBuilder(@NonNull final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering)
	{
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

	public List<Candidate> retrieveCandidatesForPPOrderId(@NonNull final PPOrderId ppOrderId)
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.productionDetailsQuery(ProductionDetailsQuery.builder()
						.ppOrderId(ppOrderId)
						.build())
				.build();
		return retrieveOrderedByDateAndSeqNo(query);
	}

	@NonNull
	private static List<TransactionDetail> getTransactionDetails(@NonNull final I_MD_Candidate candidateRecord)
	{
		final CandidateId candidateId = CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID());
		final List<I_MD_Candidate_Transaction_Detail> transactionDetailRecords = RepositoryCommons
				.createCandidateDetailQueryBuilder(candidateId, I_MD_Candidate_Transaction_Detail.class)
				.list();

		return fromRecords(transactionDetailRecords, candidateRecord);
	}

	private static boolean isSimulated(@NonNull final I_MD_Candidate candidateRecord)
	{
		return X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated.equals(candidateRecord.getMD_Candidate_Status());
	}
}
