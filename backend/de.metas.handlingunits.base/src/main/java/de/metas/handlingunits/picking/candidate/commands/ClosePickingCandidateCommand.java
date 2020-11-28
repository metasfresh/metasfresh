package de.metas.handlingunits.picking.candidate.commands;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * Close picking candidate.
 * 
 * The status will be changed from Processed to Closed.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ClosePickingCandidateCommand
{
	private static final transient Logger logger = LogManager.getLogger(ClosePickingCandidateCommand.class);
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final transient IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);

	private final PickingCandidateRepository pickingCandidateRepository;

	private final List<PickingCandidate> pickingCandidates;
	private final Boolean pickingSlotIsRackSystem;
	private final boolean failOnError;

	@Builder
	private ClosePickingCandidateCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final Collection<PickingCandidate> pickingCandidates,
			@Nullable final Boolean pickingSlotIsRackSystem,
			@Nullable final Boolean failOnError)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;

		// NOTE: tolerate empty pickingCandidates list.
		this.pickingCandidates = ImmutableList.copyOf(pickingCandidates);
		this.pickingSlotIsRackSystem = pickingSlotIsRackSystem;

		this.failOnError = failOnError != null ? failOnError : true;
	}

	/**
	 * Note to dev: keep in sync with {@link UnClosePickingCandidateCommand#perform()}
	 */
	public void perform()
	{
		pickingCandidates.stream()
				.filter(this::isEligible)
				.forEach(this::close);

		//
		// Release the picking slots
		final Set<PickingSlotId> pickingSlotIds = PickingCandidate.extractPickingSlotIds(pickingCandidates);
		huPickingSlotBL.releasePickingSlotsIfPossible(pickingSlotIds);
	}

	private boolean isEligible(final PickingCandidate pickingCandidate)
	{
		return pickingCandidate.isProcessed()
				&& (pickingSlotIsRackSystem == null || pickingSlotIsRackSystem == isPickingSlotRackSystem(pickingCandidate));
	}

	private boolean isPickingSlotRackSystem(final PickingCandidate pickingCandidate)
	{
		final PickingSlotId pickingSlotId = pickingCandidate.getPickingSlotId();
		return pickingSlotId != null && huPickingSlotDAO.isPickingRackSystem(pickingSlotId);
	}

	private void close(final PickingCandidate pickingCandidate)
	{
		try
		{
			pickingCandidate.assertProcessed();
			
			final PickingSlotId pickingSlotId = pickingCandidate.getPickingSlotId();
			if (pickingSlotId != null)
			{
				huPickingSlotBL.addToPickingSlotQueue(pickingSlotId, pickingCandidate.getPackedToHuId());
			}

			changeStatusToProcessedAndSave(pickingCandidate);
		}
		catch (final Exception ex)
		{
			if (failOnError)
			{
				throw AdempiereException.wrapIfNeeded(ex).setParameter("pickingCandidate", pickingCandidate);
			}
			else
			{
				logger.warn("Failed closing {}. Skipped", pickingCandidate, ex);
			}
		}
	}

	private void changeStatusToProcessedAndSave(final PickingCandidate pickingCandidate)
	{
		pickingCandidate.changeStatusToClosed();
		pickingCandidateRepository.save(pickingCandidate);
	}
}
