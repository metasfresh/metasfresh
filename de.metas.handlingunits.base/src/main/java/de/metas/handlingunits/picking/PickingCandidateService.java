package de.metas.handlingunits.picking;

import java.util.List;
import java.util.OptionalInt;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.pickingCandidateCommands.AddHUToPickingSlotCommand;
import de.metas.handlingunits.picking.pickingCandidateCommands.AddQtyToHUCommand;
import de.metas.handlingunits.picking.pickingCandidateCommands.AddQtyToHUCommand.AddQtyToHUCommandBuilder;
import de.metas.handlingunits.picking.pickingCandidateCommands.ClosePickingCandidateCommand;
import de.metas.handlingunits.picking.pickingCandidateCommands.ClosePickingCandidateCommand.ClosePickingCandidateCommandBuilder;
import de.metas.handlingunits.picking.pickingCandidateCommands.ProcessPickingCandidateCommand;
import de.metas.handlingunits.picking.pickingCandidateCommands.RemoveHUFromPickingSlotCommand;
import de.metas.handlingunits.picking.pickingCandidateCommands.RemoveQtyFromHUCommand;
import de.metas.handlingunits.picking.pickingCandidateCommands.RemoveQtyFromHUCommand.RemoveQtyFromHUCommandBuilder;
import de.metas.handlingunits.picking.pickingCandidateCommands.UnProcessPickingCandidateCommand;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
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
public class PickingCandidateService
{
	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;

	public PickingCandidateService(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final HuId2SourceHUsService sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
	}

	public void addHUToPickingSlot(final int huId, final int pickingSlotId, final int shipmentScheduleId)
	{
		AddHUToPickingSlotCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.huId(huId)
				.pickingSlotId(pickingSlotId)
				.shipmentScheduleId(shipmentScheduleId)
				.build()
				.perform();
	}

	public AddQtyToHUCommandBuilder addQtyToHU()
	{
		return AddQtyToHUCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository);
	}

	public RemoveQtyFromHUCommandBuilder removeQtyFromHU()
	{
		return RemoveQtyFromHUCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository);
	}

	public void removeHUFromPickingSlot(final int huId)
	{
		RemoveHUFromPickingSlotCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.huId(huId)
				.build()
				.perform();
	}

	/**
	 * For the given {@code huIds}, this method does two things:
	 * <ul>
	 * <li>Retrieves the source HUs (if any) of the the given {@code huIds} and if they are empty creates a snapshot and destroys them</li>
	 * <li>selects the {@link I_M_Picking_Candidate}s that reference those HUs
	 * and have {@code status == 'IP'} (in progress) and updates them to {@code status='PR'} (processed).
	 * No model interceptors etc will be fired.</li>
	 * </ul>
	 */
	public void processForHUIds(@NonNull final List<Integer> huIds, final int pickingSlotId, final OptionalInt shipmentScheduleId)
	{
		//
		// Process those picking candidates
		final ProcessPickingCandidateCommand processCmd = ProcessPickingCandidateCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.huIds(huIds)
				.pickingSlotId(pickingSlotId)
				.shipmentScheduleId(shipmentScheduleId.orElse(-1))
				.build();
		processCmd.perform();

		//
		// Automatically close those processed picking candidates which are NOT on a rack system picking slot. (gh2740)
		ClosePickingCandidateCommand.builder()
				.pickingCandidates(processCmd.getProcessedPickingCandidates())
				.pickingSlotIsRackSystem(false)
				.build()
				.perform();
	}

	public void unprocessForHUId(final int huId)
	{
		UnProcessPickingCandidateCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.huId(huId)
				.build()
				.perform();
	}

	public ClosePickingCandidateCommandBuilder prepareCloseForShipmentSchedules(@NonNull final List<Integer> shipmentScheduleIds)
	{
		final List<I_M_Picking_Candidate> pickingCandidates = pickingCandidateRepository.retrievePickingCandidatesByShipmentScheduleIdsAndStatus(shipmentScheduleIds, X_M_Picking_Candidate.STATUS_PR);
		return ClosePickingCandidateCommand.builder()
				.pickingCandidates(pickingCandidates);
	}

	public void inactivateForHUId(final int huId)
	{
		Check.assume(huId > 0, "huId > 0");
		
		pickingCandidateRepository.retrievePickingCandidatesByHUIds(ImmutableList.of(huId))
				.forEach(pickingCandidate -> {
					pickingCandidate.setIsActive(false);
					pickingCandidate.setStatus(X_M_Picking_Candidate.STATUS_CL);
					InterfaceWrapperHelper.save(pickingCandidate);
				});

	}
}
