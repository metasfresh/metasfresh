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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.picked_tu.ShipmentScheduleQtyPickedSplitter.CandidatePart;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Creates the shipment candidates of picked {@code M_ShipmentSchedule_QtyPicked} rows.
 * <p>
 * Rows that point to a TU (non-virtual) without a {@code VHU_ID} are collected per (shipment schedule, TU): a TU whose content was
 * picked in several steps is expanded to one candidate per attribute combination of its CUs, so that the shipment gets one line per
 * combination, also when the picked quantities are equal. A row alone is handled by {@link #createCandidatesOfRowAlone}.
 * <p>
 * One instance serves one call: it caches what it reads.
 */
public class QtyPickedCandidatesCreator
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final TUContentLoader tuContentLoader = new TUContentLoader();

	@NonNull private final IHUContext huContext;
	@NonNull private final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse;
	@NonNull private final SetMultimap<ShipmentScheduleId, HuId> pickedHuIdsBySchedule;
	@NonNull private final ShipmentScheduleQtyPickedSplitter splitter;

	private final Map<HuId, Boolean> isTUByHuId = new HashMap<>();
	private final Map<ShipmentScheduleId, I_M_ShipmentSchedule> schedulesById = new HashMap<>();

	/**
	 * @param pickedHuIdsBySchedule the HUs that the picking of each schedule recorded (see {@link TUContentLoader#retrieveCUsPickedBySchedule});
	 *                              empty if nothing is known
	 */
	public QtyPickedCandidatesCreator(
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse,
			@NonNull final SetMultimap<ShipmentScheduleId, HuId> pickedHuIdsBySchedule)
	{
		this.huContext = huContext;
		this.qtyTypeToUse = qtyTypeToUse;
		this.pickedHuIdsBySchedule = pickedHuIdsBySchedule;
		this.splitter = new ShipmentScheduleQtyPickedSplitter(huContext, qtyTypeToUse);
	}

	/**
	 * @return the candidates of the rows, in the order of the rows: the candidates of a TU come where the first row of that TU is.
	 * This order does not decide which candidates share a shipment line.
	 */
	public List<ShipmentScheduleWithHU> createCandidates(@NonNull final List<I_M_ShipmentSchedule_QtyPicked> rows)
	{
		final LinkedHashMap<ScheduleTUKey, List<I_M_ShipmentSchedule_QtyPicked>> rowsByTU = new LinkedHashMap<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			final ScheduleTUKey tuKey = extractTUKeyOrNull(row);
			if (tuKey != null)
			{
				rowsByTU.computeIfAbsent(tuKey, key -> new ArrayList<>()).add(row);
			}
		}

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		final Set<ScheduleTUKey> doneTUs = new LinkedHashSet<>();
		for (final I_M_ShipmentSchedule_QtyPicked row : rows)
		{
			final ScheduleTUKey tuKey = extractTUKeyOrNull(row);
			if (tuKey == null)
			{
				result.addAll(createCandidatesOfRowAlone(row));
			}
			else if (doneTUs.add(tuKey))
			{
				result.addAll(createCandidatesForTU(tuKey, rowsByTU.get(tuKey)));
			}
		}
		return result.build();
	}

	@Nullable
	private ScheduleTUKey extractTUKeyOrNull(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
	{
		final HuId tuId = HuId.ofRepoIdOrNull(row.getM_TU_HU_ID());
		if (tuId == null || !isTU(tuId))
		{
			return null;
		}
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(row.getM_ShipmentSchedule_ID());
		return new ScheduleTUKey(scheduleId, tuId, ProductId.ofRepoId(getSchedule(scheduleId).getM_Product_ID()));
	}

	private boolean isTU(@NonNull final HuId huId)
	{
		return isTUByHuId.computeIfAbsent(huId, id -> !handlingUnitsBL.isVirtual(handlingUnitsBL.getById(id)));
	}

	private I_M_ShipmentSchedule getSchedule(@NonNull final ShipmentScheduleId scheduleId)
	{
		return schedulesById.computeIfAbsent(scheduleId, shipmentScheduleBL::getById);
	}

	/**
	 * @param rows all active rows of the schedule that point to the TU
	 */
	private List<ShipmentScheduleWithHU> createCandidatesForTU(
			@NonNull final ScheduleTUKey tuKey,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> rows)
	{
		// A row that already references its CU (e.g. from an earlier generation) is not a TU-only row and is not part of the content to hand out.
		final List<I_M_ShipmentSchedule_QtyPicked> rowsWithCU = rows.stream()
				.filter(row -> HuId.ofRepoIdOrNull(row.getVHU_ID()) != null)
				.collect(ImmutableList.toImmutableList());
		final Set<HuId> cuIdsOfRowsWithCU = rowsWithCU.stream()
				.map(row -> HuId.ofRepoId(row.getVHU_ID()))
				.collect(ImmutableSet.toImmutableSet());
		final List<I_M_ShipmentSchedule_QtyPicked> tuOnlyRows = rows.stream()
				.filter(row -> HuId.ofRepoIdOrNull(row.getVHU_ID()) == null)
				.sorted(Comparator.comparingInt(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_QtyPicked_ID))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		for (final I_M_ShipmentSchedule_QtyPicked row : rowsWithCU)
		{
			result.addAll(createCandidatesOfRowAlone(row));
		}
		if (!tuOnlyRows.isEmpty())
		{
			result.addAll(createCandidatesForTUOnlyRows(tuKey, tuOnlyRows, cuIdsOfRowsWithCU));
		}
		return result.build();
	}

	private List<ShipmentScheduleWithHU> createCandidatesForTUOnlyRows(
			@NonNull final ScheduleTUKey tuKey,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> tuOnlyRows,
			@NonNull final Set<HuId> cuIdsToSkip)
	{
		final I_M_HU tu = handlingUnitsBL.getById(tuKey.getTuId());
		final Predicate<AttributeId> attributeSplitsShipmentLine = tuContentLoader.attributeSplitsShipmentLine(getSchedule(tuKey.getShipmentScheduleId()));
		final List<TUContentGroup> contentGroups = retrieveContentGroups(tu, tuKey, cuIdsToSkip, attributeSplitsShipmentLine);

		// One combination only in the whole TU (CUs of other products do not count): there is nothing to split, so the rows
		// keep the handling they had before, the line takes the TU's attributes. CUs that other schedules or rows of their own
		// stand for count: they decide which attributes the TU shows.
		final boolean hasSeveralCombinations = contentGroups.size() > 1 || countCombinationsOfProduct(tu, tuKey, attributeSplitsShipmentLine) > 1;
		if (hasSeveralCombinations && rowsPickedWholeContent(tuOnlyRows, contentGroups))
		{
			return createCandidatesPerContentGroup(tuOnlyRows, contentGroups, tuKey.getProductId());
		}

		// The TU content is not exactly what these rows picked (e.g. a TU shared with another schedule): each row alone.
		return tuOnlyRows.stream()
				.flatMap(row -> createCandidatesOfRowAlone(row).stream())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return how many different attribute combinations (as far as they split the shipment line) the CUs of the TU that hold the
	 * schedule's product have, whoever picked them
	 */
	private int countCombinationsOfProduct(
			@NonNull final I_M_HU tu,
			@NonNull final ScheduleTUKey tuKey,
			@NonNull final Predicate<AttributeId> attributeSplitsShipmentLine)
	{
		final List<I_M_HU> cus = tuContentLoader.retrieveCUsHoldingProduct(tu, tuKey.getProductId(), huContext.getHUStorageFactory());
		return tuContentLoader.groupStoragesByAttributeCombination(cus, tuKey.getProductId(), huContext, attributeSplitsShipmentLine)
				.keySet()
				.size();
	}

	/**
	 * @return the content of the TU per attribute combination (first-seen order of its CUs, which follows pick order),
	 * without the CUs that rows already reference
	 */
	private List<TUContentGroup> retrieveContentGroups(
			@NonNull final I_M_HU tu,
			@NonNull final ScheduleTUKey tuKey,
			@NonNull final Set<HuId> cuIdsToSkip,
			@NonNull final Predicate<AttributeId> attributeSplitsShipmentLine)
	{
		final List<I_M_HU> cus = tuContentLoader.retrieveCUsPickedBySchedule(
						tu,
						tuKey.getProductId(),
						pickedHuIdsBySchedule.get(tuKey.getShipmentScheduleId()),
						huContext.getHUStorageFactory())
				.stream()
				.filter(cu -> !cuIdsToSkip.contains(HuId.ofRepoId(cu.getM_HU_ID())))
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap<AttributesKey, IHUProductStorage> storagesByAttributes =
				tuContentLoader.groupStoragesByAttributeCombination(cus, tuKey.getProductId(), huContext, attributeSplitsShipmentLine);
		return storagesByAttributes.asMap().values().stream()
				.map(storages -> storages.stream()
						.map(storage -> new TUContentCU(storage.getM_HU(), storage.getQtyInStockingUOM()))
						.collect(ImmutableList.toImmutableList()))
				.map(TUContentGroup::new)
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean rowsPickedWholeContent(
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> tuOnlyRows,
			@NonNull final List<TUContentGroup> contentGroups)
	{
		if (contentGroups.isEmpty() || tuOnlyRows.stream().anyMatch(row -> row.getQtyPicked().signum() < 0))
		{
			return false;
		}
		final Quantity contentQty = contentGroups.stream()
				.map(TUContentGroup::totalQty)
				.reduce(Quantity::add)
				.get();
		final Quantity pickedQty = tuOnlyRows.stream()
				.map(row -> Quantity.of(row.getQtyPicked(), contentQty.getUOM())) // both in the stock UOM
				.reduce(Quantity::add)
				.get();
		return contentQty.qtyAndUomCompareToEquals(pickedQty);
	}

	private List<ShipmentScheduleWithHU> createCandidatesPerContentGroup(
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> tuOnlyRows,
			@NonNull final List<TUContentGroup> contentGroups,
			@NonNull final ProductId productId)
	{
		final TUPickedRowsAllocator allocator = new TUPickedRowsAllocator(contentGroups, cu -> extractCatchWeightOrNull(productId, cu));
		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		for (final I_M_ShipmentSchedule_QtyPicked row : tuOnlyRows)
		{
			final List<TUPickedRowsAllocator.Part> parts = allocator.allocate(
					new TUPickedRowsAllocator.Row(Quantity.of(row.getQtyPicked(), contentGroups.get(0).uom()), extractCatchWeightOrNull(row)));
			if (parts.isEmpty())
			{
				// a row without quantity gets its candidate as on every other path
				result.addAll(ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(row, huContext, qtyTypeToUse));
			}
			for (int i = 0; i < parts.size(); i++)
			{
				final TUPickedRowsAllocator.Part part = parts.get(i);
				result.add(splitter.saveRowAndCreateCandidate(
						row,
						i == 0,
						new CandidatePart(part.getQty(), part.getWholeCU() == null ? null : part.getWholeCU().getCu(), part.getGroup().firstCU()),
						part.getCatchWeight()));
			}
		}
		return result.build();
	}

	/**
	 * @return the weight of the CU, {@code null} if the product has no catch weight or the CU has none (net weight 0, e.g. picked by count)
	 */
	@Nullable
	private Quantity extractCatchWeightOrNull(@NonNull final ProductId productId, @NonNull final TUContentCU cu)
	{
		final StockQtyAndUOMQty qtys = CatchWeightHelper.extractQtys(huContext, productId, cu.getQty(), cu.getCu());
		return qtys == null ? null : qtys.getUOMQtyOpt().filter(weight -> weight.signum() > 0).orElse(null);
	}

	@Nullable
	private Quantity extractCatchWeightOrNull(@NonNull final I_M_ShipmentSchedule_QtyPicked row)
	{
		if (row.getCatch_UOM_ID() <= 0)
		{
			return null;
		}
		return Quantity.of(CoalesceUtil.coalesceNotNull(row.getQtyDeliveredCatch(), BigDecimal.ZERO), uomDAO.getById(UomId.ofRepoId(row.getCatch_UOM_ID())));
	}

	/**
	 * Handles a row on its own, without knowing the other rows of its TU.
	 * <p>
	 * A pick via PickingJobPickCommand records only M_TU_HU_ID (VHU_ID stays NULL). If that TU holds CUs of several attribute
	 * combinations and the row picked all of its content, the row is split into one row per combination.
	 */
	public List<ShipmentScheduleWithHU> createCandidatesOfRowAlone(@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPicked)
	{
		final HuId vhuId = HuId.ofRepoIdOrNull(qtyPicked.getVHU_ID());
		final HuId tuId = HuId.ofRepoIdOrNull(qtyPicked.getM_TU_HU_ID());
		final HuId luId = HuId.ofRepoIdOrNull(qtyPicked.getM_LU_HU_ID());
		final HuId huToInspectId = CoalesceUtil.coalesce(vhuId, tuId, luId);
		if (huToInspectId == null)
		{
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		final I_M_HU huToInspect = handlingUnitsBL.getById(huToInspectId);
		if (handlingUnitsBL.isVirtual(huToInspect))
		{
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		final I_M_ShipmentSchedule schedule = getSchedule(ShipmentScheduleId.ofRepoId(qtyPicked.getM_ShipmentSchedule_ID()));
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());

		// One pass: for each CU compute its UseInASI attribute combination once, then emit one
		// (combination, productStorage) entry per non-empty product storage of that CU.
		// Filter by product so CUs carrying a different product in the same TU are excluded.
		// UOM consistency is guaranteed: qtyPicked links to one M_ShipmentSchedule which has one
		// M_Product_ID and therefore one stocking UOM; all filtered storages carry that same product
		// and are in the same UOM, so no conversion is needed.
		// Split only by attributes whitelisted in M_ShipmentSchedule_AttributeConfig: the same criterion
		// the M_InOutLine aggregation uses (ShipmentScheduleWithHU#computeAttributeValues).
		final List<I_M_HU> cus = handlingUnitsDAO.retrieveIncludedHUs(huToInspect).stream()
				.filter(handlingUnitsBL::isVirtual)
				.collect(ImmutableList.toImmutableList());
		final ImmutableListMultimap<AttributesKey, IHUProductStorage> storagesByAttributes =
				tuContentLoader.groupStoragesByAttributeCombination(cus, productId, huContext, tuContentLoader.attributeSplitsShipmentLine(schedule));

		if (storagesByAttributes.isEmpty())
		{
			// No CUs found for this product: nothing to expand, use default candidate.
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		// Case 1: find the CU whose qty exactly matches QtyPicked: one candidate using that CU.
		// Using the specific CU as override ensures its UseInASI attributes are read from the CU
		// itself rather than from the destination TU, which may carry mixed/null attribute values.
		// UOM is consistent (same product, same stocking UOM), so BigDecimal comparison is safe.
		final BigDecimal pickedQtyBD = qtyPicked.getQtyPicked();
		final IHUProductStorage exactMatchStorage = storagesByAttributes.values().stream()
				.filter(storage -> storage.getQty().getAsBigDecimal().compareTo(pickedQtyBD) == 0)
				.findFirst()
				.orElse(null);
		if (exactMatchStorage != null)
		{
			return ImmutableList.of(ShipmentScheduleWithHU.ofShipmentScheduleQtyPickedForVHU(
					qtyPicked, huContext, exactMatchStorage.getM_HU(), exactMatchStorage.getQty(), qtyTypeToUse));
		}

		// Case 2: whole-TU pick: the QtyPicked covers the entire TU content (one pick for all CUs).
		// Guard: if the picked qty does not equal the total qty across all CU storages, this QtyPicked covers
		// only part of the TU and another QtyPicked record covers the rest: fall back to a single
		// default candidate so each QtyPicked is not independently inflated by the group sums.
		final boolean totalQtyEqualsPickedQty = storagesByAttributes.values().stream()
				.map(IHUProductStorage::getQty)
				.reduce(Quantity::add)
				.filter(total -> total.qtyAndUomCompareToEquals(Quantity.of(pickedQtyBD, total.getUOM())))
				.isPresent();
		if (!totalQtyEqualsPickedQty)
		{
			return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(qtyPicked, huContext, qtyTypeToUse);
		}

		return splitWholeTUPick(qtyPicked, storagesByAttributes);
	}

	/**
	 * Splits the single whole-TU pick into one M_ShipmentSchedule_QtyPicked row per attribute combination,
	 * so every resulting M_InOutLine (one per distinct UseInASI attribute set) binds its OWN allocation, exactly
	 * as the multi-pick path already does. Sharing one row across combinations would leave all but one line with no
	 * QtyPicked, breaking qty-sync (M_InOutLine.onMovementQtyChange) and HU-based package/content resolution.
	 * The original row is reused for the first combination; a copy (same TU/LU) is split off for each further one.
	 */
	private List<ShipmentScheduleWithHU> splitWholeTUPick(
			@NonNull final I_M_ShipmentSchedule_QtyPicked qtyPicked,
			@NonNull final ImmutableListMultimap<AttributesKey, IHUProductStorage> storagesByAttributes)
	{
		final List<List<IHUProductStorage>> groups = ImmutableList.copyOf(storagesByAttributes.asMap().values()).stream()
				.map(ImmutableList::copyOf)
				.collect(ImmutableList.toImmutableList());
		final List<Quantity> groupQtys = groups.stream()
				.map(group -> group.stream().map(IHUProductStorage::getQty).reduce(Quantity::add).get())
				.collect(ImmutableList.toImmutableList());
		final Quantity rowCatchWeight = extractCatchWeightOrNull(qtyPicked);
		final List<Quantity> catchWeightShares = rowCatchWeight != null ? TUPickedRowsAllocator.splitByQty(rowCatchWeight, groupQtys) : null;

		final ImmutableList.Builder<ShipmentScheduleWithHU> result = ImmutableList.builder();
		for (int groupIndex = 0; groupIndex < groups.size(); groupIndex++)
		{
			final List<IHUProductStorage> group = groups.get(groupIndex);
			// Any CU of the group supplies the group's (shared) attribute set: the candidate/line reads its
			// attributes from it. All members carry the same attributes, so the choice is arbitrary.
			final IHUProductStorage attributeSourceStorage = group.get(0);
			final I_M_HU attributeSourceCU = attributeSourceStorage.getM_HU();
			final Quantity groupQty = groupQtys.get(groupIndex);

			// Reference the CU on the row only when the group is a single CU: then the row's qty equals that
			// CU's qty exactly, so it is accurate and each CU is referenced by at most one row. For a multi-CU
			// group keep VHU_ID null: the group's summed qty must not be attributed to one CU (that would
			// over-state it and mis-restore it on reversal). Either way the representative CU is the candidate's
			// attribute source (in-memory override) so the line's attributes are read from it.
			result.add(splitter.saveRowAndCreateCandidate(
					qtyPicked,
					groupIndex == 0,
					new CandidatePart(groupQty, group.size() == 1 ? attributeSourceCU : null, attributeSourceCU),
					catchWeightShares == null ? null : catchWeightShares.get(groupIndex)));
		}
		return result.build();
	}
}

