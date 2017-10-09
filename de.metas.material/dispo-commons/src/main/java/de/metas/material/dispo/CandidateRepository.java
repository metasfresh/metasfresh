package de.metas.material.dispo;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.ecs.xhtml.code;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.Candidate.CandidateBuilder;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.event.MaterialDescriptor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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
public class CandidateRepository
{
	/**
	 * Stores the given {@code candidate}.
	 * If there is already an existing candidate in the store, it is loaded, its fields are updated and the result is saved.<br>
	 * If the given {@code candidate} specifies a {@link Candidate#getSeqNo()}, then that value will be persisted, even if there is already a different value stored in the underlying {@link I_MD_Candidate} record.
	 *
	 * @return a candidate with
	 *         <ul>
	 *         <li>the {@code id} of the persisted data record</li>
	 *         <li>the {@code groupId} of the persisted data record. This is either the given {@code candidate}'s {@code groupId} or the given candidate's ID (in case the given candidate didn't have a groupId)</li>
	 *         <li>the {@code parentId} of the persisted data record or {@code null} if the persisted record didn't exist or has a parentId of zero.
	 *         <li>the {@code seqNo} The rules are similar to groupId, but if there was a persisted {@link I_MD_Candidate} with a different seqno, that different seqno might also be returned, depending on the {@code preserveExistingSeqNo} parameter.</li>
	 *         <li>the quantity <b>delta</b> of the persisted data record before the update was made</li>
	 *         </ul>
	 */
	public Candidate addOrUpdateOverwriteStoredSeqNo(@NonNull final Candidate candidate)
	{
		return addOrUpdate(candidate, false);
	}

	/**
	 * Similar to {@link #addOrUpdateOverwriteStoredSeqNo(Candidate)}, but the given {@code candidate}'s {@code seqNo} (if specified at all!) will only be persisted if none is stored yet.
	 * 
	 * @param candidate
	 * @return
	 */
	public Candidate addOrUpdatePreserveExistingSeqNo(@NonNull final Candidate candidate)
	{
		return addOrUpdate(candidate, true);
	}

	private Candidate addOrUpdate(@NonNull final Candidate candidate, final boolean preserveExistingSeqNo)
	{
		final Optional<I_MD_Candidate> oldCandidateRecord = retrieveExact(candidate);

		final BigDecimal oldqty = oldCandidateRecord.isPresent() ? oldCandidateRecord.get().getQty() : BigDecimal.ZERO;
		final BigDecimal qtyDelta = candidate.getQuantity().subtract(oldqty);

		final I_MD_Candidate synchedRecord = syncToRecord(oldCandidateRecord, candidate, preserveExistingSeqNo);
		save(synchedRecord); // save now, because we need to have MD_Candidate_ID > 0

		setFallBackSeqNoAndGroupIdIfNeeded(synchedRecord);

		if (candidate.getSubType() == SubType.PRODUCTION && candidate.getProductionDetail() != null)
		{
			addOrRecplaceProductionDetail(candidate, synchedRecord);
		}

		if (candidate.getSubType() == SubType.DISTRIBUTION && candidate.getDistributionDetail() != null)
		{
			addOrRecplaceDistributionDetail(candidate, synchedRecord);
		}

		if (candidate.getDemandDetail() != null)
		{
			// we do this independently of the type; the demand info might be needed by many records, not just by the "first" demand record
			addOrRecplaceDemandDetail(candidate, synchedRecord);
		}

		return createNewCandidateWithIdsFromRecord(candidate, synchedRecord)
				.withQuantity(qtyDelta);
	}

	public void setFallBackSeqNoAndGroupIdIfNeeded(@NonNull final I_MD_Candidate synchedRecord)
	{
		if (synchedRecord.getSeqNo() <= 0)
		{
			synchedRecord.setSeqNo(synchedRecord.getMD_Candidate_ID());
		}
		if (synchedRecord.getMD_Candidate_GroupId() <= 0)
		{
			synchedRecord.setMD_Candidate_GroupId(synchedRecord.getMD_Candidate_ID());
		}
		save(synchedRecord);
	}

	public Candidate createNewCandidateWithIdsFromRecord(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		final Integer parentId = candidateRecord.getMD_Candidate_Parent_ID() > 0
				? candidateRecord.getMD_Candidate_Parent_ID()
				: null;

		return candidate
				.withId(candidateRecord.getMD_Candidate_ID())
				.withParentId(parentId)
				.withGroupId(candidateRecord.getMD_Candidate_GroupId())
				.withSeqNo(candidateRecord.getSeqNo());
	}

	/**
	 * Updates the qty of the given candidate.
	 * Differs from {@link #addOrUpdateOverwriteStoredSeqNo(Candidate)} in that
	 * no matching id done, and if there is no existing persisted record, then an exception is thrown. Instead, it just updates the underyling persisted record of the given {@code candidateToUpdate}.
	 *
	 *
	 * @param candidateToUpdate the candidate to update. Needs to have {@link Candidate#getId() > 0}.
	 *
	 * @return a copy of the given {@code candidateToUpdate} with the quantity being a delta, similar to the return value of {@link #addOrUpdate(Candidate, boolean)}.
	 */
	public Candidate updateQty(@NonNull final Candidate candidateToUpdate)
	{
		Preconditions.checkState(candidateToUpdate.getId() != null && candidateToUpdate.getId() > 0, "Parameter 'candidateToUpdate' has Id=%s; candidateToUpdate=%s", candidateToUpdate.getId(), candidateToUpdate);

		final I_MD_Candidate candidateRecord = load(candidateToUpdate.getId(), I_MD_Candidate.class);
		final BigDecimal oldQty = candidateRecord.getQty();

		candidateRecord.setQty(candidateToUpdate.getQuantity());
		save(candidateRecord);

		final BigDecimal qtyDelta = candidateToUpdate.getQuantity().subtract(oldQty);

		return candidateToUpdate.withQuantity(qtyDelta);
	}

	private void addOrRecplaceProductionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final I_MD_Candidate_Prod_Detail detailRecordToUpdate;
		final I_MD_Candidate_Prod_Detail existingDetail = retrieveProductionDetail(synchedRecord);
		if (existingDetail == null)
		{
			detailRecordToUpdate = newInstance(I_MD_Candidate_Prod_Detail.class, synchedRecord);
			detailRecordToUpdate.setMD_Candidate(synchedRecord);
		}
		else
		{
			detailRecordToUpdate = existingDetail;
		}
		final ProductionCandidateDetail productionDetail = candidate.getProductionDetail();
		detailRecordToUpdate.setDescription(productionDetail.getDescription());
		detailRecordToUpdate.setPP_Plant_ID(productionDetail.getPlantId());
		detailRecordToUpdate.setPP_Product_BOMLine_ID(productionDetail.getProductBomLineId());
		detailRecordToUpdate.setPP_Product_Planning_ID(productionDetail.getProductPlanningId());
		detailRecordToUpdate.setC_UOM_ID(productionDetail.getUomId());
		detailRecordToUpdate.setPP_Order_ID(productionDetail.getPpOrderId());
		detailRecordToUpdate.setPP_Order_BOMLine_ID(productionDetail.getPpOrderLineId());
		detailRecordToUpdate.setPP_Order_DocStatus(productionDetail.getPpOrderDocStatus());
		save(detailRecordToUpdate);
	}

	private void addOrRecplaceDistributionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final I_MD_Candidate_Dist_Detail detailRecordToUpdate;
		final I_MD_Candidate_Dist_Detail existingDetail = retrieveDistributionDetail(synchedRecord);
		if (existingDetail == null)
		{
			detailRecordToUpdate = newInstance(I_MD_Candidate_Dist_Detail.class, synchedRecord);
			detailRecordToUpdate.setMD_Candidate(synchedRecord);
		}
		else
		{
			detailRecordToUpdate = existingDetail;
		}
		final DistributionCandidateDetail distributionDetail = candidate.getDistributionDetail();
		detailRecordToUpdate.setDD_NetworkDistributionLine_ID(distributionDetail.getNetworkDistributionLineId());
		detailRecordToUpdate.setPP_Plant_ID(distributionDetail.getPlantId());
		detailRecordToUpdate.setPP_Product_Planning_ID(distributionDetail.getProductPlanningId());
		detailRecordToUpdate.setDD_Order_ID(distributionDetail.getDdOrderId());
		detailRecordToUpdate.setDD_OrderLine_ID(distributionDetail.getDdOrderLineId());
		detailRecordToUpdate.setDD_Order_DocStatus(distributionDetail.getDdOrderDocStatus());
		detailRecordToUpdate.setM_Shipper_ID(distributionDetail.getShipperId());
		save(detailRecordToUpdate);
	}

	private I_MD_Candidate_Dist_Detail retrieveDistributionDetail(@NonNull final I_MD_Candidate candidateRecord)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_MD_Candidate_Dist_Detail existingDetail = queryBL.createQueryBuilder(I_MD_Candidate_Dist_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, candidateRecord.getMD_Candidate_ID())
				.create()
				.firstOnly(I_MD_Candidate_Dist_Detail.class); // we have a UC in place..
		return existingDetail;
	}

	private I_MD_Candidate_Prod_Detail retrieveProductionDetail(@NonNull final I_MD_Candidate candidateRecord)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_MD_Candidate_Prod_Detail existingDetail = queryBL.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID, candidateRecord.getMD_Candidate_ID())
				.create()
				.firstOnly(I_MD_Candidate_Prod_Detail.class); // we have a UC in place..
		return existingDetail;
	}

	private void addOrRecplaceDemandDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		if (candidate.getDemandDetail() == null || candidate.getDemandDetail().getOrderLineId() <= 0)
		{
			return; // nothing to do
		}

		final I_MD_Candidate_Demand_Detail detailRecordToUpdate;
		final I_MD_Candidate_Demand_Detail existingDetail = retrieveDemandDetail(synchedRecord);
		if (existingDetail == null)
		{
			detailRecordToUpdate = newInstance(I_MD_Candidate_Demand_Detail.class, synchedRecord);
			detailRecordToUpdate.setMD_Candidate(synchedRecord);
		}
		else
		{
			detailRecordToUpdate = existingDetail;
		}
		final DemandCandidateDetail demandDetail = candidate.getDemandDetail();
		detailRecordToUpdate.setC_OrderLine_ID(demandDetail.getOrderLineId());
		save(detailRecordToUpdate);
	}

	private static I_MD_Candidate_Demand_Detail retrieveDemandDetail(@NonNull final I_MD_Candidate synchedRecord)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_MD_Candidate_Demand_Detail existingDetail = queryBL.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMN_MD_Candidate_ID, synchedRecord.getMD_Candidate_ID())
				.create()
				.firstOnly(I_MD_Candidate_Demand_Detail.class); // TODO we don't yet have a UC in place..
		return existingDetail;
	}

	public Optional<Candidate> retrieve(@NonNull final Candidate candidate)
	{
		final I_MD_Candidate candidateRecordOrNull = retrieveExact(candidate).orElse(null);

		return fromCandidateRecord(candidateRecordOrNull);
	}

	/**
	 * Load and return the candidate with the given ID.
	 *
	 * @param id
	 * @return
	 */
	public Candidate retrieve(@NonNull final Integer id)
	{
		final I_MD_Candidate candidateRecord = load(id, I_MD_Candidate.class);
		return fromCandidateRecord(candidateRecord).get();
	}

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

	/**
	 * Retrieves the <b>one</b> record that matches the given candidate's
	 * <ul>
	 * <li>type</li>
	 * <li>warehouse</li>
	 * <li>product</li>
	 * <li>date</li>
	 * <li>tableId and record (only if set)</li>
	 * <li>demand details</li>
	 * <li>production details: if {@link Candidate#getProductionDetail()} is {@code null}, then only records without product detail are selected.<br>
	 * If it's not null and either a product plan ID or BOM line ID is set, then only records with a matching detail record are selected. Note that those two don't change (unlike ppOrder ID and ppOrder BOM line ID which can change from zero to an actual reference)</li>
	 * <li>distribution details:if {@link Candidate#getDistributionDetail()} is {@link code null}, then only records without product detail are selected.<br>
	 * If it's not null and either a product plan ID or network distribution line ID is set, then only records with a matching detail record are selected. Note that those two don't change (unlike ddOrder ID and ddOrderLine ID which can change from zero to an actual reference)</li>
	 * </ul>
	 *
	 * @param candidate
	 * @return
	 */
	@VisibleForTesting
	/* package */ Optional<I_MD_Candidate> retrieveExact(@NonNull final Candidate candidate)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final MaterialDescriptor materialDescr = candidate.getMaterialDescr();

		final IQueryBuilder<I_MD_Candidate> builder = queryBL
				.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, candidate.getType().toString())
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, materialDescr.getWarehouseId())
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, materialDescr.getProductId())
				.addEqualsFilter(I_MD_Candidate.COLUMN_DateProjected, materialDescr.getDate());

		addReferenceToQueryBuilder(candidate, builder);

		addDemandDetailToBuilder(candidate, builder);

		// filter by productionDetail; if there is none, *don't* ignore, but filter for "not-existing"
		addProductionDetailToFilter(candidate, builder);

		// filter by distributionDetail; if there is none, *don't* ignore, but filter for "not-existing"
		addDistributionDetailToFilter(candidate, builder);

		final I_MD_Candidate candidateRecord = builder
				.create()
				.firstOnly(I_MD_Candidate.class); // note that we have a UC to make sure there is just one

		return Optional.ofNullable(candidateRecord);
	}

	private void addReferenceToQueryBuilder(final Candidate candidate, final IQueryBuilder<I_MD_Candidate> builder)
	{
		final TableRecordReference referencedRecord = candidate.getReference();
		if (referencedRecord != null)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_AD_Table_ID, referencedRecord.getAD_Table_ID());
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_Record_ID, referencedRecord.getRecord_ID());
		}
	}

	/**
	 * filter by demand detail ignore if there is none!
	 *
	 * @param candidate
	 * @param builder
	 */
	private void addDemandDetailToBuilder(
			final Candidate candidate,
			final IQueryBuilder<I_MD_Candidate> builder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final DemandCandidateDetail demandDetail = candidate.getDemandDetail();
		if (demandDetail == null)
		{
			return;
		}

		final IQueryBuilder<I_MD_Candidate_Demand_Detail> demandDetailsSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
				.addOnlyActiveRecordsFilter();

		final boolean hasOrderLine = demandDetail.getOrderLineId() > 0;
		if (hasOrderLine)
		{
			demandDetailsSubQueryBuilder
					.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMN_C_OrderLine_ID, demandDetail.getOrderLineId());
		}

		final boolean hasForecastLine = demandDetail.getForecastLineId() > 0;
		if (hasForecastLine)
		{
			demandDetailsSubQueryBuilder
					.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMN_M_ForecastLine_ID, demandDetail.getForecastLineId());
		}

		if (hasOrderLine || hasForecastLine)
		{
			builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID,
					I_MD_Candidate_Demand_Detail.COLUMN_MD_Candidate_ID,
					demandDetailsSubQueryBuilder.create());
		}
	}

	private void addProductionDetailToFilter(
			final Candidate candidate,
			final IQueryBuilder<I_MD_Candidate> builder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ProductionCandidateDetail productionDetail = candidate.getProductionDetail();

		final IQueryBuilder<I_MD_Candidate_Prod_Detail> productDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.addOnlyActiveRecordsFilter();

		if (productionDetail == null)
		{
			builder.addNotInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID, productDetailSubQueryBuilder.create());
		}
		else
		{
			boolean doFilter = false;
			if (productionDetail.getProductPlanningId() > 0)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Product_Planning_ID, productionDetail.getProductPlanningId());
				doFilter = true;
			}
			if (productionDetail.getProductBomLineId() > 0)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Product_BOMLine_ID, productionDetail.getProductBomLineId());
				doFilter = true;
			}
			if (doFilter)
			{
				builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID, productDetailSubQueryBuilder.create());
			}
		}
	}

	private void addDistributionDetailToFilter(
			final Candidate candidate,
			final IQueryBuilder<I_MD_Candidate> builder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final DistributionCandidateDetail distributionDetail = candidate.getDistributionDetail();

		final IQueryBuilder<I_MD_Candidate_Dist_Detail> distDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Dist_Detail.class)
				.addOnlyActiveRecordsFilter();

		if (distributionDetail == null)
		{
			builder.addNotInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, distDetailSubQueryBuilder.create());
		}
		else
		{
			boolean doFilter = false;
			if (distributionDetail.getProductPlanningId() > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_PP_Product_Planning_ID, distributionDetail.getProductPlanningId());
				doFilter = true;
			}
			if (distributionDetail.getNetworkDistributionLineId() > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_DD_NetworkDistributionLine_ID, distributionDetail.getNetworkDistributionLineId());
				doFilter = true;
			}
			if (doFilter)
			{
				builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, distDetailSubQueryBuilder.create());
			}
		}
	}

	/**
	 * Writes the given {@code candidate}'s properties to the given {@code candidateRecord}, but does not save that record.
	 *
	 * @param candidateRecord
	 * @param candidate
	 * @return either returns the record contained in the given candidateRecord (but updated) or a new record.
	 */
	private I_MD_Candidate syncToRecord(
			@NonNull final Optional<I_MD_Candidate> candidateRecord,
			@NonNull final Candidate candidate,
			final boolean preserveExistingSeqNo)
	{
		Preconditions.checkState(
				!candidateRecord.isPresent()
						|| isNew(candidateRecord.get())
						|| candidate.getId() == null
						|| Objects.equals(candidateRecord.get().getMD_Candidate_ID(), candidate.getId()),
				"The given MD_Candidate is not new and its ID is different from the ID of the given Candidate; MD_Candidate=%s; candidate=%s",
				candidateRecord, candidate);

		final MaterialDescriptor materialDescr = candidate.getMaterialDescr();

		final I_MD_Candidate candidateRecordToUse = candidateRecord.orElse(newInstance(I_MD_Candidate.class));
		candidateRecordToUse.setAD_Org_ID(candidate.getOrgId());
		candidateRecordToUse.setMD_Candidate_Type(candidate.getType().toString());
		candidateRecordToUse.setM_Warehouse_ID(materialDescr.getWarehouseId());
		candidateRecordToUse.setM_Product_ID(materialDescr.getProductId());
		candidateRecordToUse.setQty(candidate.getQuantity());
		candidateRecordToUse.setDateProjected(new Timestamp(materialDescr.getDate().getTime()));

		if (candidate.getSubType() != null)
		{
			candidateRecordToUse.setMD_Candidate_SubType(candidate.getSubType().toString());
		}

		if (candidate.getParentId() != null)
		{
			candidateRecordToUse.setMD_Candidate_Parent_ID(candidate.getParentId());
		}

		// if the candidate has a SeqNo to sync and
		// if candidateRecordToUse does not yet have one, or if the existing seqNo is not protected by 'preserveExistingSeqNo', then (over)write it.
		if (candidate.getSeqNo() != null)
		{
			if (candidateRecordToUse.getSeqNo() <= 0 || !preserveExistingSeqNo)
			{
				candidateRecordToUse.setSeqNo(candidate.getSeqNo());
			}
		}

		final ITableRecordReference referencedRecord = candidate.getReference();
		if (referencedRecord != null)
		{
			candidateRecordToUse.setAD_Table_ID(referencedRecord.getAD_Table_ID());
			candidateRecordToUse.setRecord_ID(referencedRecord.getRecord_ID());
		}

		if (candidate.getGroupId() != null)
		{
			candidateRecordToUse.setMD_Candidate_GroupId(candidate.getGroupId());
		}

		if (candidate.getStatus() != null)
		{
			candidateRecordToUse.setMD_Candidate_Status(candidate.getStatus().toString());
		}

		return candidateRecordToUse;
	}

	private Optional<Candidate> fromCandidateRecord(final I_MD_Candidate candidateRecordOrNull)
	{
		if (candidateRecordOrNull == null || isNew(candidateRecordOrNull))
		{
			return Optional.empty();
		}

		final CandidateBuilder builder = createAndInitializeBuilder(candidateRecordOrNull);

		final SubType subType = getSubTypeOrNull(candidateRecordOrNull);
		builder.subType(subType);

		if (subType == SubType.PRODUCTION)
		{
			builder.productionDetail(createProductionDetailOrNull(candidateRecordOrNull));
		}
		else if (subType == SubType.DISTRIBUTION)
		{
			builder.distributionDetail(createDistributionDetailOrNull(candidateRecordOrNull));
		}

		builder.demandDetail(createDemandDetailOrNull(candidateRecordOrNull));

		return Optional.of(builder.build());
	}

	private SubType getSubTypeOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		SubType subType = null;
		if (!Check.isEmpty(candidateRecord.getMD_Candidate_SubType()))
		{
			subType = SubType.valueOf(candidateRecord.getMD_Candidate_SubType());
		}
		return subType;
	}

	private CandidateBuilder createAndInitializeBuilder(@NonNull final I_MD_Candidate candidateRecord)
	{
		final MaterialDescriptor materialDescr = MaterialDescriptor.builder()
				.productId(candidateRecord.getM_Product_ID())
				.quantity(candidateRecord.getQty())
				.warehouseId(candidateRecord.getM_Warehouse_ID())
				// make sure to add a Date and not a Timestamp to avoid confusing Candidate's equals() and hashCode() methods
				.date(new Date(candidateRecord.getDateProjected().getTime()))
				.build();

		final CandidateBuilder builder = Candidate.builder()
				.id(candidateRecord.getMD_Candidate_ID())
				.clientId(candidateRecord.getAD_Client_ID())
				.orgId(candidateRecord.getAD_Org_ID())
				.seqNo(candidateRecord.getSeqNo())
				.type(Type.valueOf(candidateRecord.getMD_Candidate_Type()))

				// if the record has a group id, then set it. otherwise set null, because a "vanilla" candidate without groupId also has null here (null and not zero)
				.groupId(candidateRecord.getMD_Candidate_GroupId() <= 0 ? null : candidateRecord.getMD_Candidate_GroupId())
				.materialDescr(materialDescr);

		if (candidateRecord.getMD_Candidate_Parent_ID() > 0)
		{
			builder.parentId(candidateRecord.getMD_Candidate_Parent_ID());
		}

		if (candidateRecord.getRecord_ID() > 0)
		{
			builder.reference(TableRecordReference.ofReferenced(candidateRecord));
		}
		return builder;
	}

	private ProductionCandidateDetail createProductionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Prod_Detail productionDetail = retrieveProductionDetail(candidateRecord);
		if (productionDetail == null)
		{
			return null;
		}
		final ProductionCandidateDetail productionCandidateDetail = ProductionCandidateDetail.builder()
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

	private DistributionCandidateDetail createDistributionDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Dist_Detail distributionDetail = retrieveDistributionDetail(candidateRecord);
		if (distributionDetail == null)
		{
			return null;
		}

		final DistributionCandidateDetail distributionCandidateDetail = DistributionCandidateDetail.builder()
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

	private static DemandCandidateDetail createDemandDetailOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Demand_Detail demandDetailRecord = retrieveDemandDetail(candidateRecord);
		if (demandDetailRecord == null)
		{
			return null;
		}

		return DemandCandidateDetail.forOrderLineIdAndForecastLineId(
				demandDetailRecord.getC_OrderLine_ID(),
				demandDetailRecord.getM_ForecastLine_ID());
	}

	/**
	 *
	 * @param segment
	 * @return the "oldest" stock candidate that matches the given {@code segment}.
	 */
	public Optional<Candidate> retrieveLatestMatch(@NonNull final CandidatesSegment segment)
	{
		final IQueryBuilder<I_MD_Candidate> builder = mkQueryBuilder(segment);

		final I_MD_Candidate candidateRecordOrNull = builder
				.orderBy()
				// there can be many stock candidates with the same DateProjected, because e.g. a to of sales orders can all have the same promised date and time
				// therefore we need to filter by both dateprojected and md-candidate-id
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnDescending(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID)
				.endOrderBy()
				.create()
				.first();

		return fromCandidateRecord(candidateRecordOrNull);
	}

	public List<Candidate> retrieveMatchesOrderByDateAndSeqNo(@NonNull final CandidatesSegment segment)
	{
		final IQueryBuilder<I_MD_Candidate> builder = mkQueryBuilder(segment);

		final Stream<I_MD_Candidate> candidateRecords = builder
				.orderBy()
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_DateProjected)
				.addColumnAscending(I_MD_Candidate.COLUMNNAME_SeqNo)
				.endOrderBy()
				.create()
				.stream();

		return candidateRecords
				.map(record -> fromCandidateRecord(record).get())
				.collect(Collectors.toList());
	}

	/**
	 * turns the given segment into the "where part" of a big query builder. Does not specify the ordering.
	 *
	 * @param segment
	 * @return
	 */
	private IQueryBuilder<I_MD_Candidate> mkQueryBuilder(final CandidatesSegment segment)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate> builder = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter();

		switch (segment.getDateOperator())
		{
			case until:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, segment.getDate());
				break;
			case from:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.GREATER_OR_EQUAL, segment.getDate());
				break;
			case after:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.GREATER, segment.getDate());
				break;
			default:
				Check.errorIf(true, "segment has unexpected DateOperator {}; segment={}", segment.getDateOperator(), segment);
				break;
		}

		if (segment.getType() != null)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, segment.getType().toString());
		}

		if (segment.getProductId() != null)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, segment.getProductId());
		}

		if (segment.getWarehouseId() != null)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, segment.getWarehouseId());
		}

		if (segment.getParentProductId() != null || segment.getParentWarehouseId() != null)
		{
			final IQueryBuilder<I_MD_Candidate> parentBuilder = queryBL.createQueryBuilder(I_MD_Candidate.class)
					.addOnlyActiveRecordsFilter();

			if (segment.getParentProductId() != null)
			{
				parentBuilder.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, segment.getParentProductId());
			}

			if (segment.getParentWarehouseId() != null)
			{
				parentBuilder.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, segment.getParentWarehouseId());
			}

			// restrict our set of matches to those records that reference a parent record which have the give product and/or warehouse.
			builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, I_MD_Candidate.COLUMN_MD_Candidate_ID, parentBuilder.create());
		}

		if (segment.getReference() != null)
		{
			builder
					.addEqualsFilter(I_MD_Candidate.COLUMN_AD_Table_ID, segment.getReference().getAD_Table_ID())
					.addEqualsFilter(I_MD_Candidate.COLUMN_Record_ID, segment.getReference().getRecord_ID());
		}
		return builder;
	}

	/**
	 * Deletes all records that reference the given {@code referencedRecord}.
	 *
	 * @param reference
	 */
	public void deleteForReference(@NonNull final TableRecordReference referencedRecord)
	{
		mkReferencedRecordFilter(referencedRecord)
				.create()
				.delete();
	}

	private IQueryBuilder<I_MD_Candidate> mkReferencedRecordFilter(final TableRecordReference reference)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_AD_Table_ID, reference.getAD_Table_ID())
				.addEqualsFilter(I_MD_Candidate.COLUMN_Record_ID, reference.getRecord_ID());
	}
}
