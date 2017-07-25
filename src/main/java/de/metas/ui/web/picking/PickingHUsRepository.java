package de.metas.ui.web.picking;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributesProvider;
import de.metas.ui.web.handlingunits.HUEditorViewRepository;
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

@Component
/* package */class PickingHUsRepository
{
	private final HUEditorViewRepository huEditorRepo;

	public PickingHUsRepository()
	{
		this(HUEditorViewRepository.builder()
				.windowId(PickingConstants.WINDOWID_PickingSlotView)
				.referencingTableName(I_M_PickingSlot.Table_Name)
				.attributesProvider(HUEditorRowAttributesProvider.builder().readonly(true).build())
				.build());
	}

	@VisibleForTesting
	/* package */ PickingHUsRepository(final HUEditorViewRepository huEditorRepo)
	{
		this.huEditorRepo = huEditorRepo;
	}

	/**
	 * 
	 * @param pickingSlotRowQuery determines which shipment schedule ID this is about, and also (optionally) if the returned rows shall have picking candidates with a certain status.
	 * 
	 * @return a multi-map where the keys are {@code M_PickingSlot_ID}s and the value is a list of HUEditorRows with the respective picking candidates' processed status values.
	 */
	public ListMultimap<Integer, PickingSlotHUEditorRow> retrieveHUsIndexedByPickingSlotId(@NonNull final PickingSlotRepoQuery pickingSlotRowQuery)
	{
		// configure the query builder
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Picking_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID, pickingSlotRowQuery.getShipmentScheduleId());

		switch (pickingSlotRowQuery.getPickingCandidates())
		{
			case DONT_CARE:
				queryBuilder.addNotEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, X_M_Picking_Candidate.STATUS_CL); // even if we don't care, we *do not* want to show closed picking candidates
				break;
			case ONLY_PROCESSED:
				queryBuilder.addEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, X_M_Picking_Candidate.STATUS_PR);
				break;
			case ONLY_UNPROCESSED:
				queryBuilder.addEqualsFilter(I_M_Picking_Candidate.COLUMN_Status, X_M_Picking_Candidate.STATUS_IP);
				break;
			default:
				Check.errorIf(true, "Query has unexpected pickingCandidates={}; query={}", pickingSlotRowQuery.getPickingCandidates(), pickingSlotRowQuery);
		}

		// execute the query and process the result
		final Map<Integer, IPair<Integer, Boolean>> huId2pickingSlotId = queryBuilder
				.create()
				.stream(I_M_Picking_Candidate.class)
				.collect(ImmutableMap.toImmutableMap(
						I_M_Picking_Candidate::getM_HU_ID, // key function
						pc -> ImmutablePair.of(pc.getM_PickingSlot_ID(), isPickingCandidateProcessed(pc))) // value function
		);

		final List<HUEditorRow> huRows = huEditorRepo.retrieveHUEditorRows(
				huId2pickingSlotId.keySet());

		final ListMultimap<Integer, PickingSlotHUEditorRow> result = huRows.stream()
				.map(huRow -> GuavaCollectors.entry(
						huId2pickingSlotId.get(huRow.getM_HU_ID()).getLeft(), // the results key, i.e. M_PickingSlot_ID
						new PickingSlotHUEditorRow(// the result's values
								huRow, // the actual row
								huId2pickingSlotId.get(huRow.getM_HU_ID()).getRight()) // M_Picking_Candidate.Processed
				))
				.collect(GuavaCollectors.toImmutableListMultimap());

		return result;
	}

	private boolean isPickingCandidateProcessed(@NonNull final I_M_Picking_Candidate pc)
	{
		final String status = pc.getStatus();
		if (X_M_Picking_Candidate.STATUS_CL.equals(status))
		{
			return true;
		}
		else if (X_M_Picking_Candidate.STATUS_PR.equals(status))
		{
			return true;
		}
		else if (X_M_Picking_Candidate.STATUS_IP.equals(status))
		{
			return false;
		}

		Check.errorIf(true, "Unexpected M_Picking_Candidate.Status={}; pc={}", status, pc);
		return false;
	}

	/**
	 * Immutable pojo that contains the HU editor as retrieved from {@link HUEditorViewRepository} plus the the {@code processed} value from the respective {@link I_M_Picking_Candidate}.
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	// the fully qualified annotations are a workaround for a javac problem with maven
	@lombok.Data
	@lombok.AllArgsConstructor
	public static class PickingSlotHUEditorRow
	{
		private final HUEditorRow huEditor;

		private final boolean processed;
	}
}
