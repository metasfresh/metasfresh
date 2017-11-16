package de.metas.material.dispo.commons.repository;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateSubType;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.product.IProductBL;
import de.metas.product.model.I_M_Product;
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
	@VisibleForTesting
	static final String SQL_SELECT_AVAILABLE_STOCK = "SELECT COALESCE(SUM(Qty),0) "
			+ "FROM de_metas_material_dispo.MD_Candidate_Latest_v "
			+ "WHERE "
			+ "(M_Warehouse_ID=? OR ? <= 0) AND "
			+ "M_Product_ID=? AND "
			+ "StorageAttributesKey LIKE ? AND "
			+ "DateProjected <= ?";

	/**
	 * Load and return <b>the</b> single record this has the given {@code id} as parentId.
	 *
	 * @param parentId
	 * @return
	 */
	public Optional<Candidate> retrieveSingleChild(@NonNull final Integer parentId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_MD_Candidate candidateRecord = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, parentId)
				.create()
				.firstOnly(I_MD_Candidate.class);

		if (candidateRecord == null)
		{
			return Optional.empty();
		}

		return fromCandidateRecord(candidateRecord);
	}

	/**
	 *
	 * @param groupId
	 * @return
	 */
	public List<Candidate> retrieveGroup(final Integer groupId)
	{
		if (groupId == null)
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, groupId)
				.orderBy().addColumn(I_MD_Candidate.COLUMN_MD_Candidate_ID).endOrderBy()
				.create()
				.stream().map(r -> fromCandidateRecord(r).get())
				.collect(Collectors.toList());
	}

	@VisibleForTesting
	Optional<Candidate> fromCandidateRecord(final I_MD_Candidate candidateRecordOrNull)
	{
		if (candidateRecordOrNull == null || isNew(candidateRecordOrNull))
		{
			return Optional.empty();
		}

		final CandidateBuilder builder = createAndInitializeBuilder(candidateRecordOrNull);

		final CandidateSubType subType = getSubTypeOrNull(candidateRecordOrNull);
		builder.subType(subType);

		if (subType == CandidateSubType.PRODUCTION)
		{
			builder.productionDetail(createProductionDetailOrNull(candidateRecordOrNull));
		}
		else if (subType == CandidateSubType.DISTRIBUTION)
		{
			builder.distributionDetail(createDistributionDetailOrNull(candidateRecordOrNull));
		}

		builder.demandDetail(createDemandDetailOrNull(candidateRecordOrNull));

		builder.transactionDetails(retrieveTransactionDetails(candidateRecordOrNull));

		return Optional.of(builder.build());
	}

	private CandidateSubType getSubTypeOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		CandidateSubType subType = null;
		if (!Check.isEmpty(candidateRecord.getMD_Candidate_SubType()))
		{
			subType = CandidateSubType.valueOf(candidateRecord.getMD_Candidate_SubType());
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
		final String storageAttributesKey = Preconditions.checkNotNull(candidateRecord.getStorageAttributesKey(),
				"Given parameter storageAttributesKey needs to have a not-null StorageAttributesKey; candidateRecord=%s",
				candidateRecord);

		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(
				candidateRecord.getM_Product_ID(),
				storageAttributesKey,
				candidateRecord.getM_AttributeSetInstance_ID());

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builderForCompleteDescriptor()
				.productDescriptor(productDescriptor)
				.quantity(candidateRecord.getQty())
				.warehouseId(candidateRecord.getM_Warehouse_ID())
				// make sure to add a Date and not a Timestamp to avoid confusing Candidate's equals() and hashCode() methods
				.date(new Date(dateProjected.getTime()))
				.build();

		final CandidateBuilder candidateBuilder = Candidate.builder()
				.id(candidateRecord.getMD_Candidate_ID())
				.clientId(candidateRecord.getAD_Client_ID())
				.orgId(candidateRecord.getAD_Org_ID())
				.seqNo(candidateRecord.getSeqNo())
				.type(CandidateType.valueOf(md_candidate_type))

				// if the record has a group id, then set it.
				.groupId(candidateRecord.getMD_Candidate_GroupId())
				.materialDescriptor(materialDescriptor);

		if (candidateRecord.getMD_Candidate_Parent_ID() > 0)
		{
			candidateBuilder.parentId(candidateRecord.getMD_Candidate_Parent_ID());
		}
		return candidateBuilder;
	}

	private ProductionDetail createProductionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Prod_Detail productionDetail = RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Prod_Detail.class);
		if (productionDetail == null)
		{
			return null;
		}
		final ProductionDetail productionCandidateDetail = ProductionDetail.builder()
				.description(productionDetail.getDescription())
				.plantId(productionDetail.getPP_Plant_ID())
				.productBomLineId(productionDetail.getPP_Product_BOMLine_ID())
				.productPlanningId(productionDetail.getPP_Product_Planning_ID())
				.uomId(productionDetail.getC_UOM_ID())
				.ppOrderId(productionDetail.getPP_Order_ID())
				.ppOrderLineId(productionDetail.getPP_Order_BOMLine_ID())
				.ppOrderDocStatus(productionDetail.getPP_Order_DocStatus())
				.build();
		return productionCandidateDetail;
	}

	private DistributionDetail createDistributionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Dist_Detail distributionDetail = RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Dist_Detail.class);
		if (distributionDetail == null)
		{
			return null;
		}

		final DistributionDetail distributionCandidateDetail = DistributionDetail.builder()
				.networkDistributionLineId(distributionDetail.getDD_NetworkDistributionLine_ID())
				.productPlanningId(distributionDetail.getPP_Product_Planning_ID())
				.plantId(distributionDetail.getPP_Plant_ID())
				.ddOrderId(distributionDetail.getDD_Order_ID())
				.ddOrderLineId(distributionDetail.getDD_OrderLine_ID())
				.ddOrderDocStatus(distributionDetail.getDD_Order_DocStatus())
				.shipperId(distributionDetail.getM_Shipper_ID())
				.build();
		return distributionCandidateDetail;
	}

	private static DemandDetail createDemandDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Demand_Detail demandDetailRecord = RepositoryCommons.retrieveSingleCandidateDetail(candidateRecord, I_MD_Candidate_Demand_Detail.class);
		if (demandDetailRecord == null)
		{
			return null;
		}

		return DemandDetail.forDemandDetailRecord(demandDetailRecord);
	}

	private List<TransactionDetail> retrieveTransactionDetails(@NonNull final I_MD_Candidate candidateRecord)
	{
		final List<I_MD_Candidate_Transaction_Detail> transactionDetailRecords = //
				RepositoryCommons.createCandidateDetailQueryBuilder(candidateRecord, I_MD_Candidate_Transaction_Detail.class)
						.list();

		final ImmutableList.Builder<TransactionDetail> result = ImmutableList.builder();
		for (final I_MD_Candidate_Transaction_Detail transactionDetailRecord : transactionDetailRecords)
		{
			result.add(TransactionDetail.fromTransactionDetailRecord(transactionDetailRecord));
		}
		return result.build();
	}

	public Candidate retrieveLatestMatchOrNull(@NonNull final CandidatesQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering = RepositoryCommons.mkQueryBuilder(query);

		final I_MD_Candidate candidateRecordOrNull = addOrderingLastestFirst(queryBuilderWithoutOrdering)
				.create()
				.first();

		return fromCandidateRecord(candidateRecordOrNull).orElse(null);
	}

	private IQueryBuilder<I_MD_Candidate> addOrderingLastestFirst(
			@NonNull final IQueryBuilder<I_MD_Candidate> queryBuilderWithoutOrdering)
	{
		return queryBuilderWithoutOrdering
				.orderBy()
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_SeqNo)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID)
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

	@NonNull
	public BigDecimal retrieveAvailableStock(@NonNull final MaterialDescriptor materialDescriptor)
	{
		return retrieveAvailableStock(MaterialDescriptorQuery.builder()
				.warehouseId(materialDescriptor.getWarehouseId())
				.date(materialDescriptor.getDate())
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.build());
	}

	@NonNull
	public BigDecimal retrieveAvailableStock(@NonNull final MaterialDescriptorQuery query)
	{
		final String storageAttributesKeyLikeExpression = RepositoryCommons
				.prepareStorageAttributesKeyForLikeExpression(
						query.getStorageAttributesKey());

		final BigDecimal result = DB.getSQLValueBDEx(
				ITrx.TRXNAME_ThreadInherited,
				SQL_SELECT_AVAILABLE_STOCK,
				new Object[] {
						query.getWarehouseId(), query.getWarehouseId(),
						query.getProductId(),
						"%" + storageAttributesKeyLikeExpression + "%",
						query.getDate() });

		return result;
	}

	public I_C_UOM getStockingUOM(final int productId)
	{
		final I_C_UOM uom = Services.get(IProductBL.class).getStockingUOM(load(productId, I_M_Product.class));
		return uom;
	}
}
