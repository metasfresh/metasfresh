package de.metas.ui.web.picking.pickingslot;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidatesQuery;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery.MatchingSourceHusQueryBuilder;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.DefaultHUEditorViewFactory;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import de.metas.ui.web.handlingunits.SqlHUEditorViewRepository;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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
 * This class is used by {@link PickingSlotViewRepository} and provides the HUs that are related to picking.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class PickingHURowsRepository
{
	private static final Logger logger = LogManager.getLogger(PickingHURowsRepository.class);

	private final ExtendedMemorizingSupplier<HUEditorViewRepository> huEditorRepoSupplier;
	private final PickingCandidateRepository pickingCandidatesRepo;
	private final PickingCandidateService pickingCandidateService;

	@Autowired
	public PickingHURowsRepository(
			@NonNull final DefaultHUEditorViewFactory huEditorViewFactory,
			@NonNull final PickingCandidateRepository pickingCandidatesRepo,
			@NonNull final HUReservationService huReservationService,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this(
				() -> createDefaultHUEditorViewRepository(huEditorViewFactory, huReservationService),
				pickingCandidatesRepo,
				pickingCandidateService);
	}

	private static SqlHUEditorViewRepository createDefaultHUEditorViewRepository(
			@NonNull final DefaultHUEditorViewFactory huEditorViewFactory,
			@NonNull final HUReservationService huReservationService)
	{
		return SqlHUEditorViewRepository.builder()
				.windowId(PickingConstants.WINDOWID_PickingSlotView)
				// BIG optimization: attributes are not needed for picking
				// known side effect: HUEditorRow.serialNo won't be set... but we don't needed neither
				//.attributesProvider(HUEditorRowAttributesProvider.builder().readonly(true).build())
				.sqlViewBinding(huEditorViewFactory.getSqlViewBinding())
				.huReservationService(huReservationService)
				.build();
	}

	/**
	 * Creates an instance using the given {@code huEditorRepo}. Intended for testing.
	 */
	@VisibleForTesting
	PickingHURowsRepository(
			@NonNull final Supplier<HUEditorViewRepository> huEditorRepoSupplier,
			@NonNull final PickingCandidateRepository pickingCandidatesRepo,
			@NonNull final PickingCandidateService pickingCandidateService)
	{
		this.huEditorRepoSupplier = ExtendedMemorizingSupplier.of(huEditorRepoSupplier);
		this.pickingCandidatesRepo = pickingCandidatesRepo;
		this.pickingCandidateService = pickingCandidateService;
	}

	private HUEditorViewRepository getHUEditorViewRepository()
	{
		return huEditorRepoSupplier.get();
	}

	/**
	 * Retrieve the union of all HUs that match any one of the given shipment schedule IDs and that are flagged to be fine picking source HUs.
	 */
	public List<HUEditorRow> retrieveSourceHUs(@NonNull final PickingSlotRepoQuery query)
	{
		final HUEditorViewRepository huEditorRepo = getHUEditorViewRepository();
		final MatchingSourceHusQuery matchingSourceHUsQuery = createMatchingSourceHusQuery(query);
		final Set<HuId> sourceHUIds = SourceHUsService.get().retrieveMatchingSourceHUIds(matchingSourceHUsQuery);
		return huEditorRepo.retrieveHUEditorRows(sourceHUIds, HUEditorRowFilter.ALL);
	}

	@VisibleForTesting
	static MatchingSourceHusQuery createMatchingSourceHusQuery(@NonNull final PickingSlotRepoQuery query)
	{
		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);

		final I_M_ShipmentSchedule currentShipmentSchedule = shipmentSchedulesRepo.getById(query.getCurrentShipmentScheduleId(), I_M_ShipmentSchedule.class);

		final Set<ShipmentScheduleId> allShipmentScheduleIds = Check.isEmpty(query.getShipmentScheduleIds())
				? ImmutableSet.of(query.getCurrentShipmentScheduleId())
				: query.getShipmentScheduleIds();

		final Set<ProductId> productIds = shipmentSchedulesRepo.getProductIdsByShipmentScheduleIds(allShipmentScheduleIds);

		final MatchingSourceHusQueryBuilder builder = MatchingSourceHusQuery.builder()
				.productIds(productIds);

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final WarehouseId effectiveWarehouseId = currentShipmentSchedule != null ? shipmentScheduleEffectiveBL.getWarehouseId(currentShipmentSchedule) : null;
		builder.warehouseIds(ImmutableSet.of(effectiveWarehouseId));
		return builder.build();
	}

	/**
	 *
	 * @param pickingCandidatesQuery determines which {@code M_ShipmentSchedule_ID}s this is about,<br>
	 *            and also (optionally) if the returned rows shall have picking candidates with a certain status.
	 *
	 * @return a multi-map where the keys are {@code M_PickingSlot_ID}s and the value is a list of HUEditorRows which also contain with the respective {@code M_Picking_Candidate}s' {@code processed} states.
	 */
	public ListMultimap<PickingSlotId, PickedHUEditorRow> retrievePickedHUsIndexedByPickingSlotId(@NonNull final PickingCandidatesQuery pickingCandidatesQuery)
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidatesRepo.query(pickingCandidatesQuery);
		return retrievePickedHUsIndexedByPickingSlotId(pickingCandidates);
	}

	private ListMultimap<PickingSlotId, PickedHUEditorRow> retrievePickedHUsIndexedByPickingSlotId(@NonNull final List<PickingCandidate> pickingCandidates)
	{
		final ImmutableList<PickingCandidate> pickingCandidateToTakeIntoAccount = pickingCandidates.stream()
				.filter(pickingCandidate -> !pickingCandidate.isRejectedToPick())
				.filter(pickingCandidate -> pickingCandidate.getPickFrom().getHuId() != null)
				.filter(pickingCandidate -> pickingCandidate.getPickingSlotId() != null)
				.collect(ImmutableList.toImmutableList());

		final PickingCandidateHURowsProvider huRowsProvider = PickingCandidateHURowsProvider.builder()
				.huEditorViewRepository(getHUEditorViewRepository())
				.pickingCandidatesRepo(pickingCandidatesRepo)
				.pickingCandidateService(pickingCandidateService)
				.pickingCandidates(pickingCandidateToTakeIntoAccount)
				.build();

		final ImmutableMap<HuId, PickedHUEditorRow> huId2EditorRow = huRowsProvider.getForPickingCandidates();

		final HashSet<HuId> seenHUIds = new HashSet<>();

		final ImmutableListMultimap.Builder<PickingSlotId, PickedHUEditorRow> builder = ImmutableListMultimap.builder();
		for (final PickingCandidate pickingCandidate : pickingCandidateToTakeIntoAccount)
		{
			final HuId huId = pickingCandidate.getPickFrom().getHuId();
			if (seenHUIds.contains(huId))
			{
				continue;
			}

			final PickedHUEditorRow huEditorRow = huId2EditorRow.get(pickingCandidate.getPickFrom().getHuId());
			if (huEditorRow == null)
			{
				continue;
			}

			seenHUIds.addAll(huEditorRow.getHuEditorRow().getAllHuIds());
			builder.put(pickingCandidate.getPickingSlotId(), huEditorRow);
		}

		return builder.build();
	}

	public ListMultimap<PickingSlotId, PickedHUEditorRow> //
			retrieveAllPickedHUsIndexedByPickingSlotId(@NonNull final List<I_M_PickingSlot> pickingSlots)
	{
		final SetMultimap<PickingSlotId, HuId> //
		huIdsByPickingSlotId = Services.get(IHUPickingSlotDAO.class).retrieveAllHUIdsIndexedByPickingSlotId(pickingSlots);

		final HUEditorViewRepository huEditorRepo = getHUEditorViewRepository();
		huEditorRepo.warmUp(ImmutableSet.copyOf(huIdsByPickingSlotId.values()));

		return huIdsByPickingSlotId
				.entries()
				.stream()
				.map(pickingSlotAndHU -> {
					final PickingSlotId pickingSlotId = pickingSlotAndHU.getKey();
					final HuId huId = pickingSlotAndHU.getValue();

					final HUEditorRow huEditorRow = huEditorRepo.retrieveForHUId(huId);

					Check.assumeNotNull(huEditorRow, "HUEditorRow cannot be null if huId is provided!");

					return GuavaCollectors.entry(pickingSlotId, PickedHUEditorRow.ofProcessedRow(huEditorRow));
				})
				.collect(GuavaCollectors.toImmutableListMultimap());
	}
}
