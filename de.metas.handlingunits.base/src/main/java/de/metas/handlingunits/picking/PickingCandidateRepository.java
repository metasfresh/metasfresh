package de.metas.handlingunits.picking;

import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Dedicated DAO'ish class centered around {@link I_M_Picking_Candidate}s
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class PickingCandidateRepository
{
	// private static final Logger logger = LogManager.getLogger(PickingCandidateRepository.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	public PickingCandidate getById(@NonNull final PickingCandidateId id)
	{
		return toPickingCandidate(getRecordById(id));
	}

	public List<PickingCandidate> getByIds(@NonNull final Set<PickingCandidateId> ids)
	{
		return getRecordsByIds(ids)
				.stream()
				.map(this::toPickingCandidate)
				.collect(ImmutableList.toImmutableList());
	}

	private I_M_Picking_Candidate getRecordById(@NonNull final PickingCandidateId id)
	{
		final I_M_Picking_Candidate record = load(id, I_M_Picking_Candidate.class);
		if (record == null)
		{
			throw new AdempiereException("@NotFound@ @M_Picking_Candidate_ID@: " + id);
		}
		return record;
	}

	private List<I_M_Picking_Candidate> getRecordsByIds(@NonNull final Set<PickingCandidateId> ids)
	{
		return loadByRepoIdAwares(ids, I_M_Picking_Candidate.class);
	}

	public void saveAll(@NonNull final Collection<PickingCandidate> candidates)
	{
		candidates.forEach(this::save);
	}

	public void save(@NonNull final PickingCandidate candidate)
	{
		final I_M_Picking_Candidate record;
		if (candidate.getId() == null)
		{
			record = newInstance(I_M_Picking_Candidate.class);
		}
		else
		{
			record = getRecordById(candidate.getId());
		}

		updateRecord(record, candidate);
		saveRecord(record);

		candidate.markSaved(PickingCandidateId.ofRepoId(record.getM_Picking_Candidate_ID()));
	}

	private PickingCandidate toPickingCandidate(final I_M_Picking_Candidate record)
	{
		final I_C_UOM uom = uomsRepo.getById(record.getC_UOM_ID());
		final Quantity qtyPicked = Quantity.of(record.getQtyPicked(), uom);

		final BigDecimal qtyReview = !isNull(record, I_M_Picking_Candidate.COLUMNNAME_QtyReview) ? record.getQtyReview() : null;

		return PickingCandidate.builder()
				.id(PickingCandidateId.ofRepoId(record.getM_Picking_Candidate_ID()))
				//
				.status(PickingCandidateStatus.ofCode(record.getStatus()))
				.pickStatus(PickingCandidatePickStatus.ofCode(record.getPickStatus()))
				.approvalStatus(PickingCandidateApprovalStatus.ofCode(record.getApprovalStatus()))
				//
				.pickFromHuId(HuId.ofRepoIdOrNull(record.getPickFrom_HU_ID()))
				.qtyPicked(qtyPicked)
				.qtyReview(qtyReview)
				//
				.packToInstructionsId(HuPackingInstructionsId.ofRepoIdOrNull(record.getPackTo_HU_PI_ID()))
				.packedToHuId(HuId.ofRepoIdOrNull(record.getM_HU_ID()))
				//
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.pickingSlotId(PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID()))
				//
				.build();
	}

	private static void updateRecord(final I_M_Picking_Candidate record, final PickingCandidate from)
	{
		record.setStatus(from.getStatus().getCode());
		record.setPickStatus(from.getPickStatus().getCode());
		record.setApprovalStatus(from.getApprovalStatus().getCode());

		record.setPickFrom_HU_ID(HuId.toRepoId(from.getPickFromHuId()));

		record.setQtyPicked(from.getQtyPicked().getAsBigDecimal());
		record.setC_UOM_ID(from.getQtyPicked().getUOMId());
		record.setQtyReview(from.getQtyReview());

		record.setPackTo_HU_PI_ID(HuPackingInstructionsId.toRepoId(from.getPackToInstructionsId()));
		record.setM_HU_ID(HuId.toRepoId(from.getPackedToHuId()));

		record.setM_ShipmentSchedule_ID(from.getShipmentScheduleId().getRepoId());
		record.setM_PickingSlot_ID(PickingSlotId.toRepoId(from.getPickingSlotId()));
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIdsByPickingCandidateIds(final Collection<PickingCandidateId> pickingCandidateIds)
	{
		if (pickingCandidateIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_Picking_Candidate_ID, pickingCandidateIds)
				.addNotNull(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID)
				.create()
				.listDistinct(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, Integer.class)
				.stream()
				.map(ShipmentScheduleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public List<PickingCandidate> getByHUIds(@NonNull final Collection<HuId> huIds)
	{
		// tolerate empty
		if (huIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return retrievePickingCandidatesByHUIdsQuery(huIds)
				.create()
				.stream()
				.map(this::toPickingCandidate)
				.collect(ImmutableList.toImmutableList());
	}

	private IQueryBuilder<I_M_Picking_Candidate> retrievePickingCandidatesByHUIdsQuery(@NonNull final Collection<HuId> huIds)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_Picking_Candidate> queryBuilder = queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter();

		queryBuilder.addCompositeQueryFilter()
				.setJoinOr()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_PickFrom_HU_ID, huIds)
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, huIds); // pack HUs

		return queryBuilder;
	}

	public Stream<PickingCandidate> streamByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.orderBy(I_M_Picking_Candidate.COLUMN_M_Picking_Candidate_ID) // just to have a predictable order
				.create()
				.stream()
				.map(this::toPickingCandidate);
	}

	public Optional<PickingCandidate> getByShipmentScheduleIdAndHuIdAndPickingSlotId(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@NonNull final HuId huId,
			@Nullable final PickingSlotId pickingSlotId)
	{
		final I_M_Picking_Candidate existingRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.create()
				.firstOnly(I_M_Picking_Candidate.class);

		return Optional.ofNullable(existingRecord)
				.map(this::toPickingCandidate);
	}

	public void deletePickingCandidates(@NonNull final Collection<PickingCandidate> candidates)
	{
		final Set<PickingCandidateId> ids = candidates.stream()
				.map(PickingCandidate::getId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		if (ids.isEmpty())
		{
			return;
		}

		final List<I_M_Picking_Candidate> records = loadByRepoIdAwares(ids, I_M_Picking_Candidate.class);
		InterfaceWrapperHelper.deleteAll(records);
	}

	public List<PickingCandidate> getByShipmentScheduleIdAndStatus(
			@NonNull ShipmentScheduleId shipmentScheduleId,
			@NonNull final PickingCandidateStatus status)
	{
		return getByShipmentScheduleIdsAndStatus(ImmutableSet.of(shipmentScheduleId), status);
	}

	public List<PickingCandidate> getByShipmentScheduleIdsAndStatus(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds,
			@NonNull final PickingCandidateStatus status)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_Status, status.getCode())
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream(I_M_Picking_Candidate.class)
				.map(this::toPickingCandidate)
				.collect(ImmutableList.toImmutableList());

	}

	public boolean hasNotClosedCandidatesForPickingSlot(final PickingSlotId pickingSlotId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotId)
				.addNotEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, PickingCandidateStatus.Closed.getCode())
				.create()
				.match();
	}

	public void inactivateForHUIds(@NonNull final Collection<HuId> huIds)
	{
		Check.assumeNotEmpty(huIds, "huIds is not empty");

		retrievePickingCandidatesByHUIdsQuery(huIds)
				.create()
				.update(record -> {
					markAsInactiveNoSave(record);
					return IQueryUpdater.MODEL_UPDATED;

				});
	}

	private void markAsInactiveNoSave(I_M_Picking_Candidate record)
	{
		record.setIsActive(false);
		record.setStatus(PickingCandidateStatus.Closed.getCode());
	}

	public TableRecordReference toTableRecordReference(@NonNull final PickingCandidate candidate)
	{
		final PickingCandidateId id = candidate.getId();
		if (id == null)
		{
			throw new AdempiereException("Candidate is not saved: " + candidate);
		}
		return toTableRecordReference(id);
	}

	public TableRecordReference toTableRecordReference(@NonNull final PickingCandidateId id)
	{
		return TableRecordReference.of(I_M_Picking_Candidate.Table_Name, id.getRepoId());
	}

	public List<PickingCandidate> query(@NonNull final PickingCandidatesQuery pickingCandidatesQuery)
	{
		// configure the query builder
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Picking_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter();

		//
		// Shipment schedules
		queryBuilder.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, pickingCandidatesQuery.getShipmentScheduleIds());

		//
		// Not Closed + Not Rack System Picking slots
		if (pickingCandidatesQuery.isOnlyNotClosedOrNotRackSystem())
		{
			final IHUPickingSlotDAO huPickingSlotsRepo = Services.get(IHUPickingSlotDAO.class);
			final Set<PickingSlotId> rackSystemPickingSlotIds = huPickingSlotsRepo.retrieveAllPickingSlotIdsWhichAreRackSystems();
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addNotEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, PickingCandidateStatus.Closed.getCode())
					.addNotInArrayFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, rackSystemPickingSlotIds);
		}

		//
		// Picking slot Barcode filter
		final String pickingSlotBarcode = pickingCandidatesQuery.getPickingSlotBarcode();
		if (!Check.isEmpty(pickingSlotBarcode, true))
		{
			final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
			final Set<PickingSlotId> pickingSlotIds = pickingSlotDAO.retrievePickingSlotIds(PickingSlotQuery.builder()
					.barcode(pickingSlotBarcode)
					.build());
			if (pickingSlotIds.isEmpty())
			{
				return ImmutableList.of();
			}

			queryBuilder.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_PickingSlot_ID, pickingSlotIds);
		}

		//
		// HU filter: not already shipped
		if (!pickingCandidatesQuery.isIncludeShippedHUs())
		{
			final IQuery<I_M_HU> husQuery = queryBL.createQueryBuilder(I_M_HU.class)
					.addNotEqualsFilter(I_M_HU.COLUMNNAME_HUStatus, X_M_HU.HUSTATUS_Shipped) // not already shipped (https://github.com/metasfresh/metasfresh-webui-api/issues/647)
					.create();

			// PickFrom HU
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Picking_Candidate.COLUMN_PickFrom_HU_ID, null)
					.addInSubQueryFilter(I_M_Picking_Candidate.COLUMN_PickFrom_HU_ID, I_M_HU.COLUMN_M_HU_ID, husQuery);

			// Picked HU
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, null)
					.addInSubQueryFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, I_M_HU.COLUMN_M_HU_ID, husQuery);
		}

		//
		// Execute query & Fetch picking candidates
		return queryBuilder
				.orderBy(I_M_Picking_Candidate.COLUMNNAME_M_Picking_Candidate_ID)
				.create()
				.stream()
				.map(this::toPickingCandidate)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return {@code true} if the given HU is referenced by an active picking candidate.
	 */
	public boolean isHuIdPicked(@NonNull final HuId huId)
	{
		final boolean isAlreadyPicked = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.match();
		return isAlreadyPicked;
	}
	
	public List<PickingCandidate> getByShipmentScheduleIds(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream(I_M_Picking_Candidate.class)
				.map(this::toPickingCandidate)
				.collect(ImmutableList.toImmutableList());

	}

}
