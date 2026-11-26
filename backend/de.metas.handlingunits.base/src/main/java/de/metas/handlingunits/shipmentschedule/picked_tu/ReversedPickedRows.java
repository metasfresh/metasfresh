package de.metas.handlingunits.shipmentschedule.picked_tu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.handlingunits.picking.job.model.PickedHUsBySchedule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.inout.QtyPickedRecords;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.picking.job.service.HUWithPickOnTheFlyStatus;
import de.metas.handlingunits.picking.job.service.ReopenPickingJobRequest;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import de.metas.common.util.CoalesceUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

/**
 * The rows that were assigned to a reversed shipment: what is read about them is read once and shared by their steps.
 * <p>
 * A row that only references its TU (non-virtual TU, no {@code VHU_ID}) stands for the CUs of that TU that its schedule picked.
 */
class ReversedPickedRows
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	private final TUContentLoader tuContentLoader = new TUContentLoader();
	private final IMutableHUContext huContext = huContextFactory.createMutableHUContext();

	private final QtyPickedRecords rows;
	private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedulesById = new HashMap<>();
	private final Map<HuId, I_M_HU> tusById = new HashMap<>();
	private final PickedHUsBySchedule pickedHuIdsBySchedule;
	private final Map<ScheduleTUKey, List<TUContentCU>> cusByTU = new HashMap<>();
	private final Map<I_M_ShipmentSchedule_QtyPicked, ScheduleTUKey> tuKeyByTUOnlyRow = new IdentityHashMap<>();

	/**
	 * @param pickedHuIdsBySchedule the HUs that the picking of each schedule recorded as picked
	 */
	ReversedPickedRows(
			@NonNull final QtyPickedRecords rows,
			@NonNull final PickedHUsBySchedule pickedHuIdsBySchedule)
	{
		this.rows = rows;
		this.pickedHuIdsBySchedule = pickedHuIdsBySchedule;
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			final ScheduleTUKey tuKey = extractTUKeyOfTUOnlyRowOrNull(row);
			if (tuKey != null)
			{
				tuKeyByTUOnlyRow.put(row, tuKey);
				cusByTU.computeIfAbsent(tuKey, this::retrieveCUsOfTU);
			}
		}
	}

	Set<ShipmentScheduleId> getScheduleIds()
	{
		return rows.stream()
				.filter(row -> !getCuIds(row).isEmpty())
				.map(row -> ShipmentScheduleId.ofRepoId(row.getM_ShipmentSchedule_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * One entry per HU: the reopen maps its requests by HU id and rejects a duplicate. An HU might be in multiple
	 * rows if shipments are completed and reversed multiple times.
	 */
	ReopenPickingJobRequest toReopenRequest()
	{
		final Map<HuId, HUWithPickOnTheFlyStatus> husById = new LinkedHashMap<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			for (final HuId cuId : getCuIds(row))
			{
				husById.putIfAbsent(cuId, HUWithPickOnTheFlyStatus.builder()
						.anonymousHuPickedOnTheFly(row.isAnonymousHuPickedOnTheFly())
						.huId(cuId)
						.build());
			}
		}
		return ReopenPickingJobRequest.builder()
				.shipmentScheduleIds(getScheduleIds())
				.huInfoList(ImmutableList.copyOf(husById.values()))
				.build();
	}

	/**
	 * @return the rows to restore: the rows whose CU (or, for a TU-only row, its TU or one of its CUs) is covered by a picking
	 * job; a TU-only row is replaced by one row per CU that it stands for
	 */
	QtyPickedRecords toRowsToRestore(@NonNull final Set<HuId> huIdsCoveredByPickingJobs)
	{
		final QtyPickedRecords coveredRows = rows.filter(row -> isCovered(row, huIdsCoveredByPickingJobs));
		final Set<HuId> cuIdsOfRowsWithCU = coveredRows.extractCUIds();
		final Map<ScheduleTUKey, TUBudget> budgetByTU = new HashMap<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : coveredRows)
		{
			final ScheduleTUKey tuKey = tuKeyByTUOnlyRow.get(row);
			if (tuKey != null)
			{
				budgetByTU.computeIfAbsent(tuKey, key -> new TUBudget(productBL.getStockUOM(key.getProductId()))).add(row);
			}
		}

		final List<I_M_ShipmentSchedule_QtyPicked> result = new ArrayList<>();
		final Set<ScheduleTUKey> doneTUs = new LinkedHashSet<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : coveredRows)
		{
			final ScheduleTUKey tuKey = tuKeyByTUOnlyRow.get(row);
			if (tuKey == null)
			{
				result.add(row);
			}
			else if (doneTUs.add(tuKey))
			{
				result.addAll(toRowsOfCUs(row, tuKey, budgetByTU.get(tuKey), cuIdsOfRowsWithCU));
			}
		}
		return QtyPickedRecords.of(result);
	}

	private boolean isCovered(@NonNull final I_M_ShipmentSchedule_QtyPicked row, @NonNull final Set<HuId> huIdsCoveredByPickingJobs)
	{
		final ScheduleTUKey tuKey = tuKeyByTUOnlyRow.get(row);
		if (tuKey != null && huIdsCoveredByPickingJobs.contains(tuKey.getTuId()))
		{
			return true;
		}
		return getCuIds(row).stream().anyMatch(huIdsCoveredByPickingJobs::contains);
	}

	/**
	 * One row per CU of the TU that the reversed rows of the schedule picked, as many as their quantity covers
	 * (whole CUs, in the order the TU lists them: when the CU sizes do not add up to the quantity picked, less is restored).
	 * A TU shared by several schedules or picks gets no more restored than the reversed rows of this schedule picked from it.
	 * The rows are not saved: they say what is to be restored for each CU.
	 */
	private List<I_M_ShipmentSchedule_QtyPicked> toRowsOfCUs(
			@NonNull final I_M_ShipmentSchedule_QtyPicked tuOnlyRow,
			@NonNull final ScheduleTUKey tuKey,
			@NonNull final TUBudget budget,
			@NonNull final Set<HuId> cuIdsOfRowsWithCU)
	{
		final List<I_M_ShipmentSchedule_QtyPicked> result = new ArrayList<>();
		for (final TUContentCU cu : cusByTU.get(tuKey))
		{
			final HuId cuId = cu.getCuId();
			if (cuIdsOfRowsWithCU.contains(cuId) || !budget.take(cu.getQty()))
			{
				continue;
			}
			result.add(toRowOfCU(tuOnlyRow, tuKey, cu, budget));
		}
		return result;
	}

	private I_M_ShipmentSchedule_QtyPicked toRowOfCU(
			@NonNull final I_M_ShipmentSchedule_QtyPicked tuOnlyRow,
			@NonNull final ScheduleTUKey tuKey,
			@NonNull final TUContentCU cu,
			@NonNull final TUBudget budget)
	{
		final I_M_ShipmentSchedule_QtyPicked rowOfCU = newInstance(I_M_ShipmentSchedule_QtyPicked.class, tuOnlyRow);
		rowOfCU.setAD_Org_ID(tuOnlyRow.getAD_Org_ID());
		rowOfCU.setM_ShipmentSchedule_ID(tuOnlyRow.getM_ShipmentSchedule_ID());
		rowOfCU.setVHU_ID(cu.getCuId().getRepoId());
		rowOfCU.setM_TU_HU_ID(tuOnlyRow.getM_TU_HU_ID());
		rowOfCU.setM_LU_HU_ID(tuOnlyRow.getM_LU_HU_ID());
		rowOfCU.setIsAnonymousHuPickedOnTheFly(tuOnlyRow.isAnonymousHuPickedOnTheFly());
		rowOfCU.setQtyPicked(cu.getQty().toBigDecimal());

		// the CU's weight comes from the CU itself, as the reopen of a completed job takes it; a CU without weight
		// (net weight 0, e.g. picked by count) takes its share of the weight of the reversed rows
		final Quantity cuWeight = TUContentLoader.extractCatchWeightOrNull(huContext, tuKey.getProductId(), cu);
		final Quantity catchWeight = CoalesceUtil.coalesceSuppliers(() -> cuWeight, () -> budget.takeWeightShareOrNull(cu.getQty()));
		if (catchWeight != null)
		{
			rowOfCU.setCatch_UOM_ID(catchWeight.getUomId().getRepoId());
			rowOfCU.setQtyDeliveredCatch(catchWeight.toBigDecimal());
		}
		return rowOfCU;
	}

	private Set<HuId> getCuIds(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
	{
		final HuId cuId = HuId.ofRepoIdOrNull(row.getVHU_ID());
		if (cuId != null)
		{
			return ImmutableSet.of(cuId);
		}
		final ScheduleTUKey tuKey = tuKeyByTUOnlyRow.get(row);
		if (tuKey == null)
		{
			return ImmutableSet.of();
		}
		return cusByTU.get(tuKey).stream()
				.map(TUContentCU::getCuId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private ScheduleTUKey extractTUKeyOfTUOnlyRowOrNull(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
	{
		final HuId tuId = HuId.ofRepoIdOrNull(row.getM_TU_HU_ID());
		if (HuId.ofRepoIdOrNull(row.getVHU_ID()) != null || tuId == null || handlingUnitsBL.isVirtual(getTU(tuId)))
		{
			return null;
		}
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(row.getM_ShipmentSchedule_ID());
		final I_M_ShipmentSchedule schedule = schedulesById.computeIfAbsent(scheduleId, shipmentScheduleBL::getById);
		return new ScheduleTUKey(scheduleId, tuId, ProductId.ofRepoId(schedule.getM_Product_ID()));
	}

	private I_M_HU getTU(@NonNull final HuId tuId)
	{
		return tusById.computeIfAbsent(tuId, handlingUnitsBL::getById);
	}

	private List<TUContentCU> retrieveCUsOfTU(@NonNull final ScheduleTUKey tuKey)
	{
		final I_M_HU tu = getTU(tuKey.getTuId());
		return tuContentLoader.retrieveCUsPickedBySchedule(tu, tuKey.getProductId(), pickedHuIdsBySchedule.getPickedHuIds(tuKey.getShipmentScheduleId()), huContext.getHUStorageFactory())
				.stream()
				.map(cu -> toTUContentCU(cu, tuKey.getProductId()))
				.collect(ImmutableList.toImmutableList());
	}

	private TUContentCU toTUContentCU(@NonNull final I_M_HU cu, @NonNull final ProductId productId)
	{
		final IHUProductStorage storage = huContext.getHUStorageFactory().getStorage(cu).getProductStorage(productId);
		return TUContentCU.ofStorage(storage);
	}

	/**
	 * What the reversed TU-only rows of one (schedule, TU) picked and have not given back yet.
	 */
	private static class TUBudget
	{
		private Quantity qtyLeft;
		private Quantity qtyTotal;
		@Nullable private Quantity weightTotal;
		private Quantity qtyShared;
		@Nullable private Quantity weightShared;
		private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		TUBudget(@NonNull final I_C_UOM stockUOM)
		{
			this.qtyLeft = Quantity.zero(stockUOM);
			this.qtyTotal = qtyLeft;
			this.qtyShared = qtyLeft;
		}

		void add(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
		{
			final Quantity qty = Quantity.of(row.getQtyPicked(), qtyLeft.getUOM()); // QtyPicked is in the stock UOM of the product
			qtyLeft = qtyLeft.add(qty);
			qtyTotal = qtyTotal.add(qty);
			if (row.getCatch_UOM_ID() > 0)
			{
				final Quantity weight = Quantity.of(row.getQtyDeliveredCatch(), uomDAO.getById(UomId.ofRepoId(row.getCatch_UOM_ID())));
				weightTotal = weightTotal == null ? weight : weightTotal.add(weight);
			}
		}

		/**
		 * @return true (and reduces what is left) if {@code qty} is still left to give back
		 */
		boolean take(@NonNull final Quantity qty)
		{
			if (qtyLeft.isLessThan(qty))
			{
				return false;
			}
			qtyLeft = qtyLeft.subtract(qty);
			return true;
		}

		/**
		 * Hands out the share of the rows' catch weight that {@code qty} of their quantity stands for. The shares are rounded
		 * cumulatively (see {@link TUPickedRowsAllocator#weightShareOfFirst}), so none is negative and together they never exceed the rows' weight.
		 *
		 * @return that share; null if the rows have no weight
		 */
		@Nullable
		Quantity takeWeightShareOrNull(@NonNull final Quantity qty)
		{
			if (weightTotal == null || qtyTotal.signum() <= 0)
			{
				return null;
			}
			final Quantity weightSharedBefore = CoalesceUtil.coalesceNotNull(weightShared, weightTotal.toZero());
			qtyShared = qtyShared.add(qty);
			weightShared = TUPickedRowsAllocator.weightShareOfFirst(weightTotal, qtyShared, qtyTotal);
			return weightShared.subtract(weightSharedBefore);
		}
	}
}
