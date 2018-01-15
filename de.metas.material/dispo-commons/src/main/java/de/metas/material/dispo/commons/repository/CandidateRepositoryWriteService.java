package de.metas.material.dispo.commons.repository;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.MaterialDescriptor;
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
public class CandidateRepositoryWriteService
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

	public Candidate updateCandidate(@NonNull final Candidate candidate)
	{
		Check.errorIf(candidate.getId() <= 0, "The candidate parameter needs to have Id>0; candidate={}", candidate);
		final CandidatesQuery query = CandidatesQuery.fromId(candidate.getId());

		return addOrUpdate(query, candidate, false);
	}

	private Candidate addOrUpdate(@NonNull final Candidate candidate, final boolean preserveExistingSeqNoAndParentId)
	{
		final CandidatesQuery query = CandidatesQuery.fromCandidate(candidate, preserveExistingSeqNoAndParentId);
		return addOrUpdate(query, candidate, preserveExistingSeqNoAndParentId);
	}

	private Candidate addOrUpdate(
			@NonNull final CandidatesQuery singleCandidateOrNullQuery,
			@NonNull final Candidate candidate,
			final boolean preserveExistingSeqNoAndParentId)
	{
		final I_MD_Candidate oldCandidateRecord = RepositoryCommons
				.mkQueryBuilder(singleCandidateOrNullQuery)
				.create()
				.firstOnly(I_MD_Candidate.class);

		final BigDecimal oldqty = oldCandidateRecord != null ? oldCandidateRecord.getQty() : BigDecimal.ZERO;
		final BigDecimal qtyDelta = candidate.getQuantity().subtract(oldqty);

		final I_MD_Candidate synchedRecord = updateOrCreateCandidateRecord(
				oldCandidateRecord,
				candidate,
				preserveExistingSeqNoAndParentId);
		save(synchedRecord); // save now, because we need to have MD_Candidate_ID > 0

		setFallBackSeqNoAndGroupIdIfNeeded(synchedRecord);

		addOrReplaceProductionDetail(candidate, synchedRecord);

		addOrReplaceDistributionDetail(candidate, synchedRecord);

		addOrReplaceDemandDetail(candidate, synchedRecord);

		addOrReplaceTransactionDetail(candidate, synchedRecord);

		return createNewCandidateWithIdsFromRecord(candidate, synchedRecord).withQuantity(qtyDelta);
	}

	/**
	 * Writes the given {@code candidate}'s properties to the given {@code candidateRecord}, but does not save that record.
	 *
	 * @param candidateRecord
	 * @param candidate
	 * @return either returns the record contained in the given candidateRecord (but updated) or a new record.
	 */
	private I_MD_Candidate updateOrCreateCandidateRecord(
			final I_MD_Candidate candidateRecord,
			@NonNull final Candidate candidate,
			final boolean preserveExistingSeqNo)
	{
		Preconditions.checkState(
				candidateRecord == null
						|| isNew(candidateRecord)
						|| candidate.getId() <= 0
						|| Objects.equals(candidateRecord.getMD_Candidate_ID(), candidate.getId()),
				"The given MD_Candidate is not new and its ID is different from the ID of the given Candidate; MD_Candidate=%s; candidate=%s",
				candidateRecord, candidate);

		final I_MD_Candidate candidateRecordToUse = candidateRecord == null ? newInstance(I_MD_Candidate.class) : candidateRecord;

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

		candidateRecord.setAD_Org_ID(candidate.getOrgId());
		candidateRecord.setMD_Candidate_Type(candidate.getType().toString());
		candidateRecord.setM_Warehouse_ID(materialDescriptor.getWarehouseId());

		candidateRecord.setC_BPartner_ID(materialDescriptor.getBPartnerId());

		candidateRecord.setM_Product_ID(materialDescriptor.getProductId());
		candidateRecord.setM_AttributeSetInstance_ID(materialDescriptor.getAttributeSetInstanceId());

		candidateRecord.setStorageAttributesKey(computeStorageAttributesKeyToStore(materialDescriptor));

		candidateRecord.setQty(candidate.getQuantity());
		candidateRecord.setDateProjected(new Timestamp(materialDescriptor.getDate().getTime()));

		if (candidate.getBusinessCase() != null)
		{
			candidateRecord.setMD_Candidate_BusinessCase(candidate.getBusinessCase().toString());
		}

		if (candidate.getParentId() > 0)
		{
			candidateRecord.setMD_Candidate_Parent_ID(candidate.getParentId());
		}

		final boolean candidateHasSeqNoToSync = candidate.getSeqNo() > 0;
		final boolean candidateRecordHasNoSeqNo = candidateRecord.getSeqNo() <= 0;
		if (candidateHasSeqNoToSync
				&& (candidateRecordHasNoSeqNo || !preserveExistingSeqNo))
		{
			candidateRecord.setSeqNo(candidate.getSeqNo());

		}

		if (candidate.getGroupId() > 0)
		{
			candidateRecord.setMD_Candidate_GroupId(candidate.getGroupId());
		}

		if (candidate.getStatus() != null)
		{
			candidateRecord.setMD_Candidate_Status(candidate.getStatus().toString());
		}
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
		final ProductionDetail productionDetail = candidate.getProductionDetail();
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
		}
		else
		{
			productionDetailRecordToUpdate = existingDetail;
		}

		productionDetailRecordToUpdate.setIsAdvised(productionDetail.isAdvised());
		productionDetailRecordToUpdate.setDescription(productionDetail.getDescription());
		productionDetailRecordToUpdate.setPP_Plant_ID(productionDetail.getPlantId());
		productionDetailRecordToUpdate.setPP_Product_BOMLine_ID(productionDetail.getProductBomLineId());
		productionDetailRecordToUpdate.setPP_Product_Planning_ID(productionDetail.getProductPlanningId());
		productionDetailRecordToUpdate.setPP_Order_ID(productionDetail.getPpOrderId());
		productionDetailRecordToUpdate.setPP_Order_BOMLine_ID(productionDetail.getPpOrderLineId());
		productionDetailRecordToUpdate.setPP_Order_DocStatus(productionDetail.getPpOrderDocStatus());
		productionDetailRecordToUpdate.setPlannedQty(productionDetail.getPlannedQty());
		productionDetailRecordToUpdate.setActualQty(productionDetail.getActualQty());

		save(productionDetailRecordToUpdate);
	}

	private void addOrReplaceDistributionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		final DistributionDetail distributionDetail = candidate.getDistributionDetail();
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
		}
		else
		{
			detailRecordToUpdate = existingDetail;
		}

		detailRecordToUpdate.setDD_NetworkDistributionLine_ID(distributionDetail.getNetworkDistributionLineId());
		detailRecordToUpdate.setPP_Plant_ID(distributionDetail.getPlantId());
		detailRecordToUpdate.setPP_Product_Planning_ID(distributionDetail.getProductPlanningId());
		detailRecordToUpdate.setDD_Order_ID(distributionDetail.getDdOrderId());
		detailRecordToUpdate.setDD_OrderLine_ID(distributionDetail.getDdOrderLineId());
		detailRecordToUpdate.setDD_Order_DocStatus(distributionDetail.getDdOrderDocStatus());
		detailRecordToUpdate.setM_Shipper_ID(distributionDetail.getShipperId());
		detailRecordToUpdate.setPlannedQty(distributionDetail.getPlannedQty());
		detailRecordToUpdate.setActualQty(distributionDetail.getPlannedQty());

		save(detailRecordToUpdate);
	}

	@VisibleForTesting
	void addOrReplaceDemandDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
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
		detailRecordToUpdate.setM_ForecastLine_ID(demandDetail.getForecastLineId());
		detailRecordToUpdate.setM_ShipmentSchedule_ID(demandDetail.getShipmentScheduleId());
		detailRecordToUpdate.setC_OrderLine_ID(demandDetail.getOrderLineId());
		detailRecordToUpdate.setC_SubscriptionProgress_ID(demandDetail.getSubscriptionProgressId());

		save(detailRecordToUpdate);
	}

	@VisibleForTesting
	void addOrReplaceTransactionDetail(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate synchedRecord)
	{
		for (final TransactionDetail transactionDetail : candidate.getTransactionDetails())
		{
			final I_MD_Candidate_Transaction_Detail detailRecordToUpdate;

			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final I_MD_Candidate_Transaction_Detail existingDetail = //
					queryBL.createQueryBuilder(I_MD_Candidate_Transaction_Detail.class)
							.addOnlyActiveRecordsFilter()
							.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_MD_Candidate_ID, synchedRecord.getMD_Candidate_ID())
							.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_M_Transaction_ID, transactionDetail.getTransactionId())
							.create()
							.firstOnly(I_MD_Candidate_Transaction_Detail.class);

			if (existingDetail == null)
			{
				detailRecordToUpdate = newInstance(I_MD_Candidate_Transaction_Detail.class, synchedRecord);
				detailRecordToUpdate.setMD_Candidate(synchedRecord);
				detailRecordToUpdate.setM_Transaction_ID(transactionDetail.getTransactionId());
			}
			else
			{
				detailRecordToUpdate = existingDetail;
			}
			detailRecordToUpdate.setMovementQty(transactionDetail.getQuantity());
			save(detailRecordToUpdate);
		}
	}

	private Candidate createNewCandidateWithIdsFromRecord(
			@NonNull final Candidate candidate,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		return candidate
				.withId(candidateRecord.getMD_Candidate_ID())
				.withParentId(candidateRecord.getMD_Candidate_Parent_ID())
				.withGroupId(candidateRecord.getMD_Candidate_GroupId())
				.withSeqNo(candidateRecord.getSeqNo());
	}
}
