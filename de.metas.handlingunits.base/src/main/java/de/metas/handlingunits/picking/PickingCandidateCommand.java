package de.metas.handlingunits.picking;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.pickingCandidateCommands.AddHUToPickingSlot;
import de.metas.handlingunits.picking.pickingCandidateCommands.AddQtyToHU;
import de.metas.handlingunits.picking.pickingCandidateCommands.RemoveQtyFromHU;
import de.metas.handlingunits.picking.pickingCandidateCommands.SetCandidatesClosed;
import de.metas.handlingunits.picking.pickingCandidateCommands.SetCandidatesInProgress;
import de.metas.handlingunits.picking.pickingCandidateCommands.SetCandidatesProcessed;
import de.metas.quantity.Quantity;
import lombok.Builder.Default;
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

@Service
public class PickingCandidateCommand
{
	private final SourceHUsRepository sourceHUsRepository;

	private PickingCandidateRepository pickingCandidateRepository;

	public PickingCandidateCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final SourceHUsRepository sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
	}

	public void addHUToPickingSlot(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		new AddHUToPickingSlot(pickingCandidateRepository).perform(huId, pickingSlotId, shipmentScheduleId);
	}

	/**
	 * 
	 * @param qtyCU
	 * @param huId
	 * @param pickingSlotId
	 * @param shipmentScheduleId
	 * 
	 * @return the quantity that was effectively added. We can only add the quantity that's still left in our source HUs.
	 */
	public Quantity addQtyToHU(@NonNull final AddQtyToHURequest addQtyToHURequest)
	{
		return new AddQtyToHU(pickingCandidateRepository).perform(addQtyToHURequest);
	}

	@lombok.Value
	@lombok.Builder
	public static final class AddQtyToHURequest
	{
		@NonNull
		@Default
		BigDecimal qtyCU = Quantity.QTY_INFINITE;

		@NonNull
		Integer huId;

		@NonNull
		Integer pickingSlotId;

		@NonNull
		Integer shipmentScheduleId;
	}

	public void removeQtyFromHU(@NonNull final RemoveQtyFromHURequest removeQtyFromHURequest)
	{
		new RemoveQtyFromHU(sourceHUsRepository, pickingCandidateRepository).perform(removeQtyFromHURequest);
	}

	@lombok.Value
	@lombok.Builder
	public static final class RemoveQtyFromHURequest
	{
		@NonNull
		@Default
		BigDecimal qtyCU = IHUCapacityDefinition.INFINITY;

		@NonNull
		Integer huId;

		@NonNull
		Integer pickingSlotId;

		@NonNull
		Integer productId;
	}

	public void removeHUFromPickingSlot(final int huId)
	{
		pickingCandidateRepository.deletePickingCandidate(huId);
	}

	/**
	 * For the given {@code huIds}, this method does two things:
	 * <ul>
	 * <li>Retrieves the source HUs (if any) of the the given {@code huIds} and if they are empty creates a snapshot and destroys them</li>
	 * <li>selects the {@link I_M_Picking_Candidate}s that reference those HUs
	 * and have {@code status == 'IP'} (in progress) and updates them to {@code status='PR'} (processed).
	 * No model interceptors etc will be fired.</li>
	 * </ul>
	 * 
	 * @param huIds
	 * 
	 * @return the number of updated {@link I_M_Picking_Candidate}s
	 */
	public int setCandidatesProcessed(@NonNull final List<Integer> huIds)
	{
		return new SetCandidatesProcessed(sourceHUsRepository).perform(huIds);
	}

	public int setCandidatesInProgress(@NonNull final List<Integer> huIds)
	{
		return new SetCandidatesInProgress(sourceHUsRepository).perform(huIds);
	}

	/**
	 * For the given {@code shipmentScheduleIds}, this method selects the {@link I_M_Picking_Candidate}s that reference those HUs
	 * and have {@code status == 'PR'} (processed) and updates them to {@code status='CL'} (closed)<br>
	 * <b>and</b> adds the respective candidates to the picking slot queue.<br>
	 * Closed candidates are not shown in the webui's picking view.
	 * <p>
	 * Note: no model interceptors etc are fired when this method is called.
	 * 
	 * @param huIds
	 * 
	 * @return the number of updated {@link I_M_Picking_Candidate}s
	 */
	public void setCandidatesClosed(@NonNull final List<Integer> shipmentScheduleIds)
	{
		new SetCandidatesClosed().perform(shipmentScheduleIds);
	}
}
