/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.material.dispo.commons.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.IdConstants;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.StockChangeDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DeleteCandidatesQuery;
import de.metas.material.dispo.commons.repository.repohelpers.PurchaseDetailRepoHelper;
import de.metas.material.dispo.commons.repository.repohelpers.RepositoryCommons;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.mforecast.IForecastDAO;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.util.TimeUtil;
import org.reflections.util.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static de.metas.common.util.IdConstants.toRepoId;
import static de.metas.material.dispo.model.X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class CandidateRepositoryWriteService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final DimensionService dimensionService;
	private final StockChangeDetailRepo stockChangeDetailRepo;
	private final IForecastDAO forecastDAO = Services.get(IForecastDAO.class);
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public CandidateRepositoryWriteService(
			@NonNull final DimensionService dimensionService,
			@NonNull final StockChangeDetailRepo stockChangeDetailRepo,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.dimensionService = dimensionService;
		this.stockChangeDetailRepo = stockChangeDetailRepo;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	/**
	 * Stores the given {@code candidate}.
	 * If there is already an existing candidate in the store, it is loaded, its fields are updated and the result is saved.<br>
	 * To generally find out, which properties are used to search for an existing candidate, check out {@link CandidatesQuery#fromCandidate(Candidate, boolean)}
	 * If the given {@code candidate} specifies a {@link Candidate#getSeqNo()}, then that value will be persisted, even if there is already a different value stored in the underlying {@link I_MD_Candidate} record.
	 *
	 * @return a candidate with
	 * <ul>
	 * <li>the {@code id} of the persisted data record</li>
	 * <li>the {@code groupId} of the persisted data record. This is either the given {@code candidate}'s {@code groupId} or the given candidate's ID (in case the given candidate didn't have a groupId)</li>
	 * <li>the {@code parentId} of the persisted data record or {@code null} if the persisted record didn't exist or has a parentId of zero.
	 * <li>the {@code seqNo}: the rules are similar to groupId, but if there was a persisted {@link I_MD_Candidate} with a different seqno, that different seqno might also be returned, depending on the {@code preserveExistingSeqNo} parameter.</li>
	 * <li>the quantity <b>delta</b> of the persisted data record before the update was made</li>
	 * </ul>
	 */
	public SaveResult addOrUpdateOverwriteStoredSeqNo(@NonNull final Candidate candidate)
	{
		return addOrUpdate(candidate, false/* preserveExistingSeqNoAndParentId */);
	}

	/**
	 * Similar to {@link #addOrUpdateOverwriteStoredSeqNo(Candidate)}, but the given {@code candidate}'s {@code seqNo} (if specified at all!) will only be persisted if none is stored yet.
	 */
	public SaveResult addOrUpdatePreserveExistingSeqNo(@NonNull final Candidate candidate)
	{
		return addOrUpdate(candidate, true);
	}

	/**
	 * @param candidate candidate that we know does not exist, so there is no existing candidate to update
	 */
	public SaveResult add(@NonNull final Candidate candidate)
	{
		return addOrUpdate(
				CandidatesQuery.FALSE /*make sure we don't find anything to update*/,
				candidate, false/*doesn't matter*/);
	}

	public SaveResult updateCandidateById(@NonNull final Candidate candidate)
	{
		Check.errorIf(candidate.getId().isNull(), "The candidate parameter needs to have an id; candidate={}", candidate);
		final CandidatesQuery query = CandidatesQuery.fromId(candidate.getId());

		return addOrUpdate(query, candidate, false);
	}

	@Value
	@Builder
	public static class SaveResult
	{
		/**
		 * The saved candidate.
		 */
		@NonNull
		Candidate candidate;

		@Nullable
		DateAndSeqNo previousTime;

		@Nullable
		BigDecimal previousQty;

		public BigDecimal getQtyDelta()
		{
			if (previousQty == null)
			{
				return candidate.getQuantity();
			}
			return candidate.getQuantity().subtract(previousQty);
		}

		/**
		 * @return {@code true} if before the save, there already was a record with a different date.
		 */
		public boolean isDateMoved()
		{
			if (previousTime == null)
			{
				return false;
			}
			return !Objects.equals(previousTime, DateAndSeqNo.ofCandidate(candidate));
		}

		public boolean isDateMovedBackwards()
		{
			if (previousTime == null)
			{
				return false;
			}
			return previousTime.isAfter(DateAndSeqNo.ofCandidate(candidate));
		}

		public boolean isDateMovedForwards()
		{
			if (previousTime == null)
			{
				return false;
			}
			return previousTime.isBefore(DateAndSeqNo.ofCandidate(candidate));
		}

		/**
		 * @return {@code true} there was no record before the save, or the record's date was changed.
		 */
		public boolean isDateChanged()
		{
			if (previousTime == null)
			{
				return true;
			}
			return !Objects.equals(previousTime, DateAndSeqNo.ofCandidate(candidate));
		}

		public boolean isQtyChanged()
		{
			if (previousQty == null)
			{
				return true;
			}
			return previousQty.compareTo(candidate.getQuantity()) != 0;
		}

		// TODO figure out if we really need this
		public Candidate toCandidateWithQtyDelta()
		{
			return candidate.withQuantity(getQtyDelta());
		}

		public Candidate toCandidateWithUpdateInfo()
		{
			return candidate.toBuilder()
					.deltaQuantity(getQtyDelta())
					.updated(true)
					.build();
		}

		/**
		 * Convenience method that returns a new instance whose included {@link Candidate} has the given id.
		 */
		public SaveResult withCandidateId(@Nullable final CandidateId candidateId)
		{
			return SaveResult
					.builder()
					.candidate(candidate.toBuilder().id(candidateId).build())
					.previousQty(previousQty)
					.previousTime(previousTime)
					.build();
		}

		/**
		 * Convenience method that returns a new instance with negated candidate quantity and previousQty
		 */
		public SaveResult withNegatedQuantity()
		{
			return SaveResult
					.builder()
					.candidate(candidate.withNegatedQuantity())
					.previousQty(previousQty == null ? null : previousQty.negate())
					.previousTime(previousTime)
					.build();
		}
	}

	private SaveResult addOrUpdate(@NonNull final Candidate candidate, final boolean preserveExistingSeqNoAndParentId)
	{
		final CandidatesQuery query = CandidatesQuery.fromCandidate(candidate, false/* includeParentId */);
		return addOrUpdate(query, candidate, preserveExistingSeqNoAndParentId);
	}

	private SaveResult addOrUpdate(
			@NonNull final CandidatesQuery singleCandidateQuery,
			@NonNull final Candidate candidate,
			final boolean preserveExistingSeqNoAndParentId)
	{
		final I_MD_Candidate oldCandidateRecord = RepositoryCommons
				.mkQueryBuilder(singleCandidateQuery)
				.create()
				.firstOnly(I_MD_Candidate.class);

		final BigDecimal previousQty = oldCandidateRecord == null ? null : oldCandidateRecord.getQty();

		final DateAndSeqNo previousTime;
		if (oldCandidateRecord != null)
		{
			previousTime = DateAndSeqNo
					.builder()
					.date(TimeUtil.asInstantNonNull(oldCandidateRecord.getDateProjected()))
					.seqNo(oldCandidateRecord.getSeqNo())
					.build();
		}
		else
		{
			previousTime = null;
		}

		final I_MD_Candidate syncedRecord = updateOrCreateCandidateRecord(
				oldCandidateRecord,
				candidate,
				preserveExistingSeqNoAndParentId);
		save(syncedRecord); // save now, because we need to have MD_Candidate_ID > 0

		setFallBackSeqNoAndGroupIdIfNeeded(syncedRecord);

		addOrReplaceProductionDetail(candidate, syncedRecord);

		addOrReplaceDistributionDetail(candidate, syncedRecord);

		addOrReplaceDemandDetail(candidate, syncedRecord);

		addOrReplacePurchaseDetail(candidate, syncedRecord);

		addOrReplaceTransactionDetail(candidate, syncedRecord);

		addOrReplaceStockChangeDetail(candidate, syncedRecord);

		final Candidate savedCandidate = createNewCandidateWithIdsFromRecord(candidate, syncedRecord);

		// add a log message to be shown in the event log
		final String verb = oldCandidateRecord == null ? "created" : "updated";
		Loggables.addLog(
				"addOrUpdate - {} candidateId={}; type={};\nsingleCandidateOrNullQuery={};\n\npreserveExistingSeqNoAndParentId={};\n\ncandidate={}",
				verb, savedCandidate.getId().getRepoId(), savedCandidate.getType().toString(), singleCandidateQuery, preserveExistingSeqNoAndParentId, savedCandidate);

		return SaveResult
				.builder()
				.candidate(savedCandidate)
				.previousQty(previousQty)
				.previousTime(previousTime)
				.build();
	}

	/**
	 * Writes the given {@code candidate}'s properties to the given {@code candidateRecord}, but does not save that record.
	 *
	 * @return either returns the record contained in the given candidateRecord (but updated) or a new record.
	 */
	private I_MD_Candidate updateOrCreateCandidateRecord(
			@Nullable final I_MD_Candidate candidateRecord,
			@NonNull final Candidate candidate,
			final boolean preserveExistingSeqNo)
	{
		Preconditions.checkState(
				candidateRecord == null
						|| isNew(candidateRecord)
						|| candidate.getId().isNull()
						|| Objects.equals(candidateRecord.getMD_Candidate_ID(), candidate.getId().getRepoId()),
				"The given MD_Candidate is not new and its ID is different from the ID of the given Candidate; MD_Candidate=%s; candidate=%s",
				candidateRecord, candidate);

		final I_MD_Candidate candidateRecordToUse = CoalesceUtil.coalesceNotNull(candidateRecord, newInstance(I_MD_Candidate.class));

		updateCandidateRecordFromCandidate(candidateRecordToUse, candidate, preserveExistingSeqNo);

		return candidateRecordToUse;
	}

	private void setFallBackSeqNoAndGroupIdIfNeeded(@NonNull final I_MD_Candidate synchedRecord)
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

	@VisibleForTesting
	void updateCandidateRecordFromCandidate(
			@NonNull final I_MD_Candidate candidateRecord,
			@NonNull final Candidate candidate,
			final boolean preserveExistingSeqNo)
	{
		final MaterialDescriptor materialDescriptor = candidate.getMaterialDescriptor();

		candidateRecord.setAD_Org_ID(candidate.getOrgId().getRepoId());
		candidateRecord.setMD_Candidate_Type(candidate.getType().getCode());
		candidateRecord.setM_Warehouse_ID(WarehouseId.toRepoId(materialDescriptor.getWarehouseId()));

		candidateRecord.setC_BPartner_Customer_ID(BPartnerId.toRepoId(materialDescriptor.getCustomerId()));
		candidateRecord.setIsReservedForCustomer(materialDescriptor.isReservedForCustomer());

		candidateRecord.setM_Product_ID(materialDescriptor.getProductId());
		candidateRecord.setM_AttributeSetInstance_ID(materialDescriptor.getAttributeSetInstanceId());

		candidateRecord.setStorageAttributesKey(computeStorageAttributesKeyToStore(materialDescriptor));

		final BigDecimal quantity = candidate.getQuantity();
		candidateRecord.setQty(stripZerosAfterTheDigit(quantity));
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(materialDescriptor.getDate()));

		candidateRecord.setReplenish_MinQty(candidate.getMinMaxDescriptor().getMin());
		candidateRecord.setReplenish_MaxQty(candidate.getMinMaxDescriptor().getMax());

		final String lotForLot = candidate.getLotForLot();
		if(!Utils.isEmpty(lotForLot))
		{
			candidateRecord.setIsLotForLot(candidate.getLotForLot());
		}

		final DemandDetail demandDetail = candidate.getDemandDetail();

		if (demandDetail != null)
		{
			final int forecastLineId = demandDetail.getForecastLineId();
			if (IdConstants.toRepoId(forecastLineId) > 0)
			{
				final I_M_ForecastLine forecastLine = forecastDAO.getForecastLineById(forecastLineId);

				final Dimension forecastLineDimension = dimensionService.getFromRecord(forecastLine);
				dimensionService.updateRecord(candidateRecord, forecastLineDimension);
			}
		}

		updateCandidateRecordFromDemandDetail(candidateRecord, demandDetail);

		if (candidate.getBusinessCase() != null)
		{
			candidateRecord.setMD_Candidate_BusinessCase(candidate.getBusinessCase().toString());
		}

		if (!candidate.getParentId().isNull())
		{
			candidateRecord.setMD_Candidate_Parent_ID(candidate.getParentId().getRepoId());
		}

		final boolean candidateHasSeqNoToSync = candidate.getSeqNo() > 0;
		final boolean candidateRecordHasNoSeqNo = candidateRecord.getSeqNo() <= 0;
		if (candidateHasSeqNoToSync
				&& (candidateRecordHasNoSeqNo || !preserveExistingSeqNo))
		{
			candidateRecord.setSeqNo(candidate.getSeqNo());
		}

		if (candidate.getGroupId() != null)
		{
			candidateRecord.setMD_Candidate_GroupId(candidate.getGroupId().toInt());
		}

		final BigDecimal fulfilledQty = candidate
				.getTransactionDetails()
				.stream()
				.map(TransactionDetail::getQuantity)
				.reduce(ZERO, BigDecimal::add);
		candidateRecord.setQtyFulfilled(fulfilledQty);

		final boolean typeImpliesProcessedDone =
				candidate.getType().equals(CandidateType.INVENTORY_UP)
						|| candidate.getType().equals(CandidateType.INVENTORY_DOWN)
						|| candidate.getType().equals(CandidateType.ATTRIBUTES_CHANGED_FROM)
						|| candidate.getType().equals(CandidateType.ATTRIBUTES_CHANGED_TO);

		if (candidate.isSimulated())
		{
			candidateRecord.setMD_Candidate_Status(X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated);
		}
		else if (fulfilledQty.compareTo(candidateRecord.getQty()) >= 0 || typeImpliesProcessedDone)
		{
			candidateRecord.setMD_Candidate_Status(X_MD_Candidate.MD_CANDIDATE_STATUS_Processed);
		}
		else
		{
			candidateRecord.setMD_Candidate_Status(X_MD_Candidate.MD_CANDIDATE_STATUS_Planned);
		}
	}

	/**
	 * Update the demand related reference columns, but don't reset them to zero, unless the respective ID is {@link IdConstants#NULL_REPO_ID}.
	 * <p>
	 * Note that we have them as physical columns for performance reasons.
	 */
	private void updateCandidateRecordFromDemandDetail(
			@NonNull final I_MD_Candidate candidateRecord,
			@Nullable final DemandDetail demandDetail)
	{
		if (demandDetail == null)
		{
			return;
		}

		final boolean demandDetailWouldResetOrderId = demandDetail.getOrderId() == 0 && candidateRecord.getC_OrderSO_ID() > 0;
		if (!demandDetailWouldResetOrderId)
		{
			candidateRecord.setC_OrderSO_ID(IdConstants.toRepoId(demandDetail.getOrderId()));
		}

		final boolean demandDetailWouldResetForecastId = demandDetail.getForecastId() == 0 && candidateRecord.getM_Forecast_ID() > 0;
		if (!demandDetailWouldResetForecastId)
		{
			candidateRecord.setM_Forecast_ID(IdConstants.toRepoId(demandDetail.getForecastId()));
		}

		final boolean demandDetailWouldResetShipmentScheduleId = demandDetail.getShipmentScheduleId() == 0 && candidateRecord.getM_ShipmentSchedule_ID() > 0;
		if (!demandDetailWouldResetShipmentScheduleId)
		{
			candidateRecord.setM_ShipmentSchedule_ID(IdConstants.toRepoId(demandDetail.getShipmentScheduleId()));
		}
	}

	private BigDecimal stripZerosAfterTheDigit(final BigDecimal quantity)
	{
		final BigDecimal stripTrailingZeros = quantity.stripTrailingZeros();
		if (stripTrailingZeros.scale() < 0)
		{
			return stripTrailingZeros.setScale(0, RoundingMode.UNNECESSARY);
		}
		return stripTrailingZeros;
	}

	@NonNull
	private String computeStorageAttributesKeyToStore(@NonNull final MaterialDescriptor materialDescriptor)
	{
		final AttributesKey attributesKey = materialDescriptor.getStorageAttributesKey();

		if (Objects.equals(attributesKey, AttributesKey.ALL))
		{
			return AttributesKey.NONE.getAsString(); // i.e. "-1002", never NULL
		}
		else
		{
			return attributesKey.getAsString();
		}
	}

	private void addOrReplaceProductionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final ProductionDetail productionDetail = ProductionDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (productionDetail == null)
		{
			return;
		}

		final I_MD_Candidate_Prod_Detail productionDetailRecordToUpdate;
		final I_MD_Candidate_Prod_Detail existingDetail = RepositoryCommons.retrieveSingleCandidateDetail(synchedRecord, I_MD_Candidate_Prod_Detail.class);
		if (existingDetail == null)
		{
			productionDetailRecordToUpdate = newInstance(I_MD_Candidate_Prod_Detail.class, synchedRecord);
			productionDetailRecordToUpdate.setMD_Candidate(synchedRecord);
			productionDetailRecordToUpdate.setIsPickDirectlyIfFeasible(productionDetail.getPickDirectlyIfFeasible().isTrue());
			productionDetailRecordToUpdate.setIsAdvised(productionDetail.getAdvised().isTrue());
		}
		else
		{
			productionDetailRecordToUpdate = existingDetail;

			if (productionDetail.getPickDirectlyIfFeasible().isUpdateExistingRecord())
			{
				productionDetailRecordToUpdate.setIsPickDirectlyIfFeasible(productionDetail.getPickDirectlyIfFeasible().isTrue());
			}
			if (productionDetail.getAdvised().isUpdateExistingRecord())
			{
				productionDetailRecordToUpdate.setIsAdvised(productionDetail.getAdvised().isTrue());
			}
		}

		productionDetailRecordToUpdate.setDescription(productionDetail.getDescription());
		productionDetailRecordToUpdate.setPP_Plant_ID(ResourceId.toRepoId(productionDetail.getPlantId()));
		productionDetailRecordToUpdate.setPP_Product_BOMLine_ID(productionDetail.getProductBomLineId());
		productionDetailRecordToUpdate.setPP_Product_Planning_ID(productionDetail.getProductPlanningId());
		productionDetailRecordToUpdate.setPP_Order_ID(productionDetail.getPpOrderId());
		productionDetailRecordToUpdate.setPP_Order_Candidate_ID(productionDetail.getPpOrderCandidateId());
		productionDetailRecordToUpdate.setPP_OrderLine_Candidate_ID(productionDetail.getPpOrderLineCandidateId());
		productionDetailRecordToUpdate.setPP_Order_BOMLine_ID(productionDetail.getPpOrderLineId());
		productionDetailRecordToUpdate.setPP_Order_DocStatus(DocStatus.toCodeOrNull(productionDetail.getPpOrderDocStatus()));
		productionDetailRecordToUpdate.setPlannedQty(productionDetail.getQty());
		productionDetailRecordToUpdate.setActualQty(candidate.computeActualQty());

		save(productionDetailRecordToUpdate);
	}

	private void addOrReplaceDistributionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final DistributionDetail distributionDetail = DistributionDetail.castOrNull(candidate.getBusinessCaseDetail());
		if (distributionDetail == null)
		{
			return;
		}

		final I_MD_Candidate_Dist_Detail detailRecordToUpdate;
		final I_MD_Candidate_Dist_Detail existingDetail = RepositoryCommons.retrieveSingleCandidateDetail(synchedRecord, I_MD_Candidate_Dist_Detail.class);
		if (existingDetail == null)
		{
			detailRecordToUpdate = newInstance(I_MD_Candidate_Dist_Detail.class, synchedRecord);
			detailRecordToUpdate.setMD_Candidate(synchedRecord);
			detailRecordToUpdate.setIsPickDirectlyIfFeasible(distributionDetail.getPickDirectlyIfFeasible().isTrue());
		}
		else
		{
			detailRecordToUpdate = existingDetail;
			if (distributionDetail.getPickDirectlyIfFeasible().isUpdateExistingRecord())
			{
				detailRecordToUpdate.setIsPickDirectlyIfFeasible(distributionDetail.getPickDirectlyIfFeasible().isTrue());
			}
		}

		detailRecordToUpdate.setIsAdvised(distributionDetail.isAdvised());
		detailRecordToUpdate.setDD_NetworkDistributionLine_ID(distributionDetail.getNetworkDistributionLineId());
		detailRecordToUpdate.setPP_Plant_ID(distributionDetail.getPlantId());
		detailRecordToUpdate.setPP_Product_Planning_ID(distributionDetail.getProductPlanningId());
		detailRecordToUpdate.setDD_Order_ID(distributionDetail.getDdOrderId());
		detailRecordToUpdate.setDD_OrderLine_ID(distributionDetail.getDdOrderLineId());
		detailRecordToUpdate.setDD_Order_DocStatus(distributionDetail.getDdOrderDocStatus());
		detailRecordToUpdate.setM_Shipper_ID(distributionDetail.getShipperId());
		detailRecordToUpdate.setPlannedQty(distributionDetail.getQty());
		detailRecordToUpdate.setActualQty(candidate.computeActualQty());

		save(detailRecordToUpdate);
	}

	@VisibleForTesting
	void addOrReplaceDemandDetail(@NonNull final Candidate candidate, @NonNull final I_MD_Candidate synchedRecord)
	{
		if (candidate.getDemandDetail() == null)
		{
			return; // nothing to do
		}

		final I_MD_Candidate_Demand_Detail detailRecordToUpdate;
		final I_MD_Candidate_Demand_Detail existingDetail = RepositoryCommons.retrieveSingleCandidateDetail(synchedRecord, I_MD_Candidate_Demand_Detail.class);
		if (existingDetail == null)
		{
			detailRecordToUpdate = newInstance(I_MD_Candidate_Demand_Detail.class, synchedRecord);
			detailRecordToUpdate.setMD_Candidate(synchedRecord);
		}
		else
		{
			detailRecordToUpdate = existingDetail;
		}

		final DemandDetail demandDetail = candidate.getDemandDetail();
		detailRecordToUpdate.setM_ForecastLine_ID(toRepoId(demandDetail.getForecastLineId()));
		detailRecordToUpdate.setM_ShipmentSchedule_ID(toRepoId(demandDetail.getShipmentScheduleId()));
		detailRecordToUpdate.setC_OrderLine_ID(toRepoId(demandDetail.getOrderLineId()));
		detailRecordToUpdate.setC_SubscriptionProgress_ID(toRepoId(demandDetail.getSubscriptionProgressId()));
		detailRecordToUpdate.setM_InOutLine_ID(toRepoId(demandDetail.getInOutLineId()));

		detailRecordToUpdate.setPlannedQty(demandDetail.getQty());
		detailRecordToUpdate.setActualQty(candidate.computeActualQty());

		save(detailRecordToUpdate);
	}

	private void addOrReplacePurchaseDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final PurchaseDetail purchaseDetail = PurchaseDetail.castOrNull(candidate.getBusinessCaseDetail());
		PurchaseDetailRepoHelper.save(purchaseDetail, synchedRecord);
	}

	@VisibleForTesting
	void addOrReplaceTransactionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		for (final TransactionDetail transactionDetail : candidate.getTransactionDetails())
		{
			final I_MD_Candidate_Transaction_Detail detailRecordToUpdate;

			final ICompositeQueryFilter<I_MD_Candidate_Transaction_Detail> //
					transactionOrPInstanceId = queryBL
					.createCompositeQueryFilter(I_MD_Candidate_Transaction_Detail.class)
					.setJoinOr();
			if (transactionDetail.getTransactionId() > 0)
			{
				transactionOrPInstanceId.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_M_Transaction_ID, transactionDetail.getTransactionId());
			}
			if (transactionDetail.getResetStockPInstanceId() != null)
			{
				transactionOrPInstanceId.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_AD_PInstance_ResetStock_ID, transactionDetail.getResetStockPInstanceId().getRepoId());
			}

			final I_MD_Candidate_Transaction_Detail existingDetail = //
					queryBL.createQueryBuilder(I_MD_Candidate_Transaction_Detail.class)
							.addOnlyActiveRecordsFilter()
							.filter(transactionOrPInstanceId)
							.create()
							.firstOnly(I_MD_Candidate_Transaction_Detail.class);

			if (existingDetail == null)
			{
				detailRecordToUpdate = newInstance(I_MD_Candidate_Transaction_Detail.class, synchedRecord);
				detailRecordToUpdate.setMD_Candidate(synchedRecord);
				detailRecordToUpdate.setM_Transaction_ID(transactionDetail.getTransactionId());
				detailRecordToUpdate.setAD_PInstance_ResetStock_ID(ResetStockPInstanceId.toRepoId(transactionDetail.getResetStockPInstanceId()));
				detailRecordToUpdate.setMD_Stock_ID(transactionDetail.getStockId());
				detailRecordToUpdate.setMD_Candidate_RebookedFrom_ID(CandidateId.toRepoId(transactionDetail.getRebookedFromCandidateId()));
			}
			else
			{
				detailRecordToUpdate = existingDetail;
			}
			detailRecordToUpdate.setTransactionDate(TimeUtil.asTimestamp(transactionDetail.getTransactionDate()));
			detailRecordToUpdate.setMovementQty(transactionDetail.getQuantity());
			save(detailRecordToUpdate);
		}
	}

	private void addOrReplaceStockChangeDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final StockChangeDetail stockChangeDetail = StockChangeDetail.castOrNull(candidate.getBusinessCaseDetail());
		stockChangeDetailRepo.saveOrUpdate(stockChangeDetail, synchedRecord);
	}

	private Candidate createNewCandidateWithIdsFromRecord(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		return candidate
				.withId(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID()))
				.withParentId(CandidateId.ofRepoIdOrNull(candidateRecord.getMD_Candidate_Parent_ID()))
				.withGroupId(MaterialDispoGroupId.ofIntOrNull(candidateRecord.getMD_Candidate_GroupId()))
				.withSeqNo(candidateRecord.getSeqNo());
	}

	/**
	 * @return DeleteResult for STOCK candidate
	 */
	@NonNull
	public DeleteResult deleteCandidateById(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate candidateRecord = load(candidateId, I_MD_Candidate.class);

		final boolean isStock = MD_CANDIDATE_TYPE_STOCK.equals(candidateRecord.getMD_Candidate_Type());

		if (isStock)
		{
			throw new AdempiereException("STOCK Candidate must be deleted together with the actual MD_Candidate!");
		}

		final CandidateId stockCandidateId = getStockCandidateIdForCandidate(candidateRecord);

		final I_MD_Candidate stockCandidate = load(stockCandidateId, I_MD_Candidate.class);

		final DeleteResult deleteResult = buildDeleteResultForCandidate(stockCandidate);

		if (candidateRecord.getMD_Candidate_Parent_ID() > 0)
		{
			deleteCandidatesAndDetailsByQuery(DeleteCandidatesQuery.builder()
					.candidateId(candidateId)
					.build());
			deleteCandidatesAndDetailsByQuery(DeleteCandidatesQuery.builder()
					.candidateId(stockCandidateId)
					.build());
		}
		else
		{
			deleteCandidatesAndDetailsByQuery(DeleteCandidatesQuery.builder()
					.candidateId(stockCandidateId)
					.build());
			deleteCandidatesAndDetailsByQuery(DeleteCandidatesQuery.builder()
					.candidateId(candidateId)
					.build());
		}

		return deleteResult;
	}

	/**
	 * @return DeleteResult for STOCK candidate
	 */
	@NonNull
	public DeleteResult deleteCandidateAndDetailsById(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate candidateRecord = load(candidateId, I_MD_Candidate.class);

		final boolean isStock = MD_CANDIDATE_TYPE_STOCK.equals(candidateRecord.getMD_Candidate_Type());

		if (isStock)
		{
			throw new AdempiereException("STOCK Candidate must be deleted together with the actual MD_Candidate!");
		}

		final CandidateId stockCandidateId = getStockCandidateIdForCandidate(candidateRecord);

		final I_MD_Candidate stockCandidate = load(stockCandidateId, I_MD_Candidate.class);

		final DeleteResult deleteResult = buildDeleteResultForCandidate(stockCandidate);

		if (candidateRecord.getMD_Candidate_Parent_ID() <= 0)
		{
			deleteCandidatesAndDetailsByQuery(DeleteCandidatesQuery.builder()
													  .candidateId(candidateId)
													  .build());
		}
		else
		{
			deleteCandidatesAndDetailsByQuery(DeleteCandidatesQuery.builder()
													  .candidateId(stockCandidateId)
													  .build());
		}

		return deleteResult;
	}

	@NonNull
	public Set<CandidateId> deleteCandidatesAndDetailsByQuery(@NonNull final DeleteCandidatesQuery deleteCandidatesQuery)
	{
		final Set<CandidateId> alreadyDeletedIds = new HashSet<>();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = queryBL.createQueryBuilder(I_MD_Candidate.class);

		if (deleteCandidatesQuery.getIsActive() != null)
		{
			queryBuilder.addEqualsFilter(I_MD_Candidate.COLUMNNAME_IsActive, deleteCandidatesQuery.getIsActive());
		}

		if (Check.isNotBlank(deleteCandidatesQuery.getStatus()))
		{
			queryBuilder.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Status, deleteCandidatesQuery.getStatus());
		}

		if (deleteCandidatesQuery.getCandidateId() != null)
		{
			queryBuilder.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID, deleteCandidatesQuery.getCandidateId());
		}

		queryBuilder
				.orderByDescending(I_MD_Candidate.COLUMNNAME_MD_Candidate_Parent_ID)
				.create()
				.iterateAndStreamIds(CandidateId::ofRepoId)
				.filter(candidateId -> !alreadyDeletedIds.contains(candidateId))
				.forEach(candidateId -> deleteCandidateById(candidateId, alreadyDeletedIds));

		return alreadyDeletedIds;
	}

	@NonNull
	private DeleteResult deleteCandidateById(@NonNull final CandidateId candidateId, @NonNull final Set<CandidateId> alreadySeenIds)
	{
		alreadySeenIds.add(candidateId);

		final I_MD_Candidate candidateRecord = InterfaceWrapperHelper.load(candidateId, I_MD_Candidate.class);

		deleteRelatedRecordsForId(candidateRecord, alreadySeenIds);

		final DeleteResult deleteResult = new DeleteResult(candidateId,
				DateAndSeqNo
						.builder()
						.date(TimeUtil.asInstantNonNull(candidateRecord.getDateProjected()))
						.seqNo(candidateRecord.getSeqNo())
						.build(),
				candidateRecord.getQty());

		deleteRecord(candidateRecord);

		return deleteResult;
	}

	private void deleteRelatedRecordsForId(
			@NonNull final I_MD_Candidate candidate,
			@NonNull final Set<CandidateId> alreadySeenIds)
	{
		final CandidateId parentCandidateId = CandidateId.ofRepoIdOrNull(candidate.getMD_Candidate_Parent_ID());

		if (parentCandidateId != null
				&& parentCandidateId.getRepoId() != candidate.getMD_Candidate_ID()
				&& !alreadySeenIds.contains(parentCandidateId))
		{
			// remove parent link
			candidate.setMD_Candidate_Parent_ID(-1);
			saveRecord(candidate);

			deleteCandidateById(parentCandidateId, alreadySeenIds);
		}

		final CandidateId candidateId = CandidateId.ofRepoId(candidate.getMD_Candidate_ID());

		deleteChildCandidates(candidateId, alreadySeenIds);

		deleteDemandDetailsRecords(candidateId);
		deleteDistDetailsRecords(candidateId);
		deleteProdDetailsRecords(candidateId);
		deletePurchaseDetailsRecords(candidateId);
		deleteStockChangeDetailsRecords(candidateId);
		deleteTransactionDetailsRecords(candidateId);
	}

	private void deleteDemandDetailsRecords(@NonNull final CandidateId candidateId)
	{
		queryBL.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMN_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.delete();
	}

	private void deleteDistDetailsRecords(@NonNull final CandidateId candidateId)
	{
		queryBL.createQueryBuilder(I_MD_Candidate_Dist_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, candidateId)
				.create()
				.delete();
	}

	private void deleteProdDetailsRecords(@NonNull final CandidateId candidateId)
	{
		queryBL.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.delete();
	}

	private void deletePurchaseDetailsRecords(@NonNull final CandidateId candidateId)
	{
		queryBL.createQueryBuilder(I_MD_Candidate_Purchase_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Purchase_Detail.COLUMN_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.delete();
	}

	private void deleteStockChangeDetailsRecords(@NonNull final CandidateId candidateId)
	{
		queryBL.createQueryBuilder(I_MD_Candidate_StockChange_Detail.class)
				.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMN_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.delete();
	}

	private void deleteTransactionDetailsRecords(@NonNull final CandidateId candidateId)
	{
		queryBL.createQueryBuilder(I_MD_Candidate_Transaction_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.delete();
	}

	private void deleteChildCandidates(@NonNull final CandidateId candidateId, @NonNull final Set<CandidateId> alreadySeenIds)
	{
		queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Parent_ID, candidateId.getRepoId())
				.create()
				.iterateAndStreamIds(CandidateId::ofRepoId)
				.filter(childCandidateId -> !alreadySeenIds.contains(childCandidateId))
				.forEach(childCandidateId -> deleteCandidateById(childCandidateId, alreadySeenIds));
	}

	public void deactivateSimulatedCandidates()
	{
		queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Status, X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_MD_Candidate.COLUMNNAME_IsActive, false)
				.execute();
	}

	@NonNull
	private CandidateId getStockCandidateIdForCandidate(@NonNull final I_MD_Candidate candidate)
	{
		Check.assume(!MD_CANDIDATE_TYPE_STOCK.equals(candidate.getMD_Candidate_Type()), "The given Candidate is a Stock Candidate!");

		if (candidate.getMD_Candidate_Parent_ID() > 0)
		{
			return CandidateId.ofRepoId(candidate.getMD_Candidate_Parent_ID());
		}

		final CandidateId candidateId = CandidateId.ofRepoId(candidate.getMD_Candidate_ID());
		return candidateRepositoryRetrieval.retrieveSingleChild(candidateId)
				.orElseThrow(() -> new AdempiereException("No stock found for CandidateId!")
						.appendParametersToMessage()
						.setParameter("CandidateId", candidateId))
				.getId();
	}

	@NonNull
	private DeleteResult buildDeleteResultForCandidate(@NonNull final I_MD_Candidate candidateRecord)
	{
		return new DeleteResult(CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID()),
																 DateAndSeqNo
																		 .builder()
																		 .date(TimeUtil.asInstant(candidateRecord.getDateProjected()))
																		 .seqNo(candidateRecord.getSeqNo())
																		 .build(),
																 candidateRecord.getQty());
	}

	@Value
	public static class DeleteResult
	{
		@NonNull
		CandidateId candidateId;

		@NonNull
		DateAndSeqNo previousTime;

		@NonNull
		BigDecimal previousQty;
	}

}
