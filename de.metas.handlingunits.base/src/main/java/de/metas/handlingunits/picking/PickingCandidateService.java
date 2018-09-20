package de.metas.handlingunits.picking;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
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
import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.picking.api.PickingSlotId;
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

	@Autowired
	private PickingConfigRepository pickingConfigRepository;

	public PickingCandidateService(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final HuId2SourceHUsService sourceHUsRepository)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;
	}

	public void addHUToPickingSlot(final HuId huId, final PickingSlotId pickingSlotId, final ShipmentScheduleId shipmentScheduleId)
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

	public void removeHUFromPickingSlot(final HuId huId)
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
	 * <li>selects the picking candidates that reference those HUs
	 * and have {@code status == 'IP'} (in progress) and updates them to {@code status='PR'} (processed).
	 * No model interceptors etc will be fired.</li>
	 * </ul>
	 */
	public void processForHUIds(
			@NonNull final Set<HuId> huIds,
			final PickingSlotId pickingSlotId,
			@Nullable final ShipmentScheduleId shipmentScheduleId)
	{
		//
		// Process those picking candidates
		final ProcessPickingCandidateCommand processCmd = ProcessPickingCandidateCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingConfigRepository(pickingConfigRepository)
				.huIds(huIds)
				.pickingSlotId(pickingSlotId)
				.shipmentScheduleId(shipmentScheduleId)
				.build();
		processCmd.perform();

		//
		// Automatically close those processed picking candidates which are NOT on a rack system picking slot. (gh2740)
		ClosePickingCandidateCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidates(processCmd.getProcessedPickingCandidates())
				.pickingSlotIsRackSystem(false)
				.build()
				.perform();
	}

	public void unprocessForHUId(final HuId huId)
	{
		UnProcessPickingCandidateCommand.builder()
				.sourceHUsRepository(sourceHUsRepository)
				.pickingCandidateRepository(pickingCandidateRepository)
				.huId(huId)
				.build()
				.perform();
	}

	public ClosePickingCandidateCommandBuilder prepareCloseForShipmentSchedules(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByShipmentScheduleIdsAndStatus(
				shipmentScheduleIds,
				PickingCandidateStatus.Processed);
		return ClosePickingCandidateCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.pickingCandidates(pickingCandidates);
	}

	public void inactivateForHUId(@NonNull final HuId huId)
	{
		inactivateForHUIds(ImmutableSet.of(huId));
	}

	public void inactivateForHUIds(final Collection<HuId> huIds)
	{
		// tolerate empty
		if (huIds.isEmpty())
		{
			return;
		}

		pickingCandidateRepository.inactivateForHUIds(huIds);
	}

	public boolean isHuIdPicked(@NonNull final HuId huId)
	{
		return pickingCandidateRepository.isHuIdPicked(huId);
	}

}
