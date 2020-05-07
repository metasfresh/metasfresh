package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import de.metas.logging.LogManager;
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

	private final List<I_M_Picking_Candidate> pickingCandidates;
	private final Boolean pickingSlotIsRackSystem;
	private final boolean failOnError;

	@Builder
	private ClosePickingCandidateCommand(
			@NonNull final Collection<I_M_Picking_Candidate> pickingCandidates,
			@Nullable final Boolean pickingSlotIsRackSystem,
			@Nullable final Boolean failOnError)
	{
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
		pickingCandidates.stream()
				.map(I_M_Picking_Candidate::getM_PickingSlot_ID)
				.distinct()
				.forEach(huPickingSlotBL::releasePickingSlotIfPossible);
	}

	private boolean isEligible(final I_M_Picking_Candidate pickingCandidate)
	{
		if (!X_M_Picking_Candidate.STATUS_PR.equals(pickingCandidate.getStatus()))
		{
			return false;
		}

		if (pickingSlotIsRackSystem != null && pickingSlotIsRackSystem != huPickingSlotDAO.isPickingRackSystem(pickingCandidate.getM_PickingSlot_ID()))
		{
			return false;
		}

		return true;
	}

	private void close(final I_M_Picking_Candidate pickingCandidate)
	{
		try
		{
			final int pickingSlotId = pickingCandidate.getM_PickingSlot_ID();
			final I_M_PickingSlot pickingSlot = load(pickingSlotId, I_M_PickingSlot.class);
			huPickingSlotBL.addToPickingSlotQueue(pickingSlot, pickingCandidate.getM_HU());

			markCandidateAsClosed(pickingCandidate);
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

	private void markCandidateAsClosed(final I_M_Picking_Candidate pickingCandidate)
	{
		pickingCandidate.setStatus(X_M_Picking_Candidate.STATUS_CL);
		InterfaceWrapperHelper.save(pickingCandidate);
	}
}
