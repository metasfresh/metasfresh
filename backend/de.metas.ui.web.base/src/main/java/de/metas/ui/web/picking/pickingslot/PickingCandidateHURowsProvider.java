/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.picking.pickingslot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.order.OrderId;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Value
public class PickingCandidateHURowsProvider
{
	@NonNull ImmutableMap<HuId, Boolean> topLevelHUId2ProcessedFlag;
	@NonNull ImmutableMap<HuId, HUEditorRow> huEditorRows;
	@NonNull PickingCandidateRepository pickingCandidatesRepo;
	@NonNull PickingCandidateService pickingCandidateService;

	@Builder
	public PickingCandidateHURowsProvider(
			@NonNull final PickingCandidateRepository pickingCandidatesRepo,
			@NonNull final HUEditorViewRepository huEditorViewRepository,
			@NonNull final PickingCandidateService pickingCandidateService,
			@NonNull final ImmutableList<PickingCandidate> pickingCandidates)
	{
		this.pickingCandidatesRepo = pickingCandidatesRepo;
		this.pickingCandidateService = pickingCandidateService;

		final ImmutableMap<HuId, Boolean> huId2ProcessedFlag = getHUId2ProcessedFlag(pickingCandidates);

		this.huEditorRows = huEditorViewRepository.retrieveHUEditorRows(huId2ProcessedFlag.keySet(), HUEditorRowFilter.ALL)
				.stream()
				.collect(ImmutableMap.toImmutableMap(HUEditorRow::getHuId, Function.identity()));

		this.topLevelHUId2ProcessedFlag = this.huEditorRows.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(HUEditorRow::getHuId,
													 huEditorRow -> huId2ProcessedFlag.get(huEditorRow.getHuId())));
	}

	@NonNull
	public ImmutableMap<HuId, PickedHUEditorRow> getForPickingCandidates()
	{
		final ImmutableMap<HuId, ImmutableSet<OrderId>> huId2OrderIds = getHUId2OpenPickingOrderIds();

		return topLevelHUId2ProcessedFlag.keySet()
				.stream()
				.map(topLevelHUId -> {
					final HUEditorRow editorRow = huEditorRows.get(topLevelHUId);
					final boolean isProcessed = topLevelHUId2ProcessedFlag.get(topLevelHUId);

					if (isProcessed)
					{
						return PickedHUEditorRow.ofProcessedRow(editorRow);
					}
					else
					{
						final ImmutableMap<HuId, ImmutableSet<OrderId>> huId2PickedOrderIds = editorRow.getAllHuIds()
								.stream()
								.collect(ImmutableMap.toImmutableMap(Function.identity(),
																	 huId -> Optional.ofNullable(huId2OrderIds.get(huId))
																			 .orElseGet(ImmutableSet::of)));
						return PickedHUEditorRow.ofUnprocessedRow(editorRow, huId2PickedOrderIds);
					}
				})
				.collect(ImmutableMap.toImmutableMap(PickedHUEditorRow::getHUId, Function.identity()));
	}

	@NonNull
	private ImmutableMap<HuId, ImmutableSet<OrderId>> getHUId2OpenPickingOrderIds()
	{
		final ImmutableSet<HuId> allHuIds = huEditorRows.values()
				.stream()
				.map(HUEditorRow::getAllHuIds)
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());

		return pickingCandidateService.getOpenPickingOrderIdsByHuId(allHuIds);
	}

	@NonNull
	private static ImmutableMap<HuId, Boolean> getHUId2ProcessedFlag(@NonNull final ImmutableList<PickingCandidate> pickingCandidates)
	{
		final HashMap<HuId, Boolean> huId2ProcessedFlag = new HashMap<>();

		pickingCandidates.stream()
				.filter(pickingCandidate -> pickingCandidate.getPickingSlotId() != null)
				.filter(pickingCandidate -> !pickingCandidate.isRejectedToPick())
				.filter(pickingCandidate -> pickingCandidate.getPickFrom().getHuId() != null)
				.forEach(pickingCandidate -> huId2ProcessedFlag.merge(pickingCandidate.getPickFrom().getHuId(),
																	  PickingCandidateHURowsProvider.isPickingCandidateProcessed(pickingCandidate),
																	  (oldValue, newValue) -> oldValue || newValue));

		return ImmutableMap.copyOf(huId2ProcessedFlag);
	}

	private static boolean isPickingCandidateProcessed(@NonNull final PickingCandidate pc)
	{
		final PickingCandidateStatus status = pc.getProcessingStatus();
		if (PickingCandidateStatus.Closed.equals(status))
		{
			return true;
		}
		else if (PickingCandidateStatus.Processed.equals(status))
		{
			return true;
		}
		else if (PickingCandidateStatus.Draft.equals(status))
		{
			return false;
		}
		else
		{
			throw new AdempiereException("Unexpected M_Picking_Candidate.Status=" + status).setParameter("pc", pc);
		}
	}
}
