package de.metas.handlingunits.picking.pickingCandidateCommands;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
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
 * Unprocess picking candidate.
 * 
 * The status will be changed from Processed to InProgress.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class UnProcessPickingCandidateCommand
{
	private static final String MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P = "WEBUI_Picking_Wrong_HU_Status";
	private static final String MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P = "WEBUI_Picking_Already_Shipped";

	private final transient IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);

	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final int huId;

	private I_M_HU _hu = null; // lazy

	@Builder
	public UnProcessPickingCandidateCommand(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			final int huId)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;

		Preconditions.checkArgument(huId > 0, "huId > 0");
		this.huId = huId;
	}

	public void perform()
	{
		final I_M_HU hu = getM_HU();
		final List<I_M_Picking_Candidate> pickingCandidates = pickingCandidateRepository.retrievePickingCandidatesByHUIds(ImmutableList.of(hu.getM_HU_ID()));

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = retrieveQtyPickedRecords(pickingCandidates);

		assertHUIsNotShipped(qtyPickedList);
		assertHUIsPicked(hu);

		// if everything is OK, go ahead
		pickingCandidates.forEach(this::convertToStatusProcessed);
		qtyPickedList.forEach(InterfaceWrapperHelper::delete);
		updateHUStatusToActive(hu);

		restoreHUsFromSourceHUs(hu.getM_HU_ID());

		pickingCandidates.forEach(this::markCandidateAsInProgress);
	}

	@NonNull
	private I_M_HU getM_HU()
	{
		if (_hu == null)
		{
			_hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
		}
		return _hu;
	}

	private void convertToStatusProcessed(final I_M_Picking_Candidate pickingCandidate)
	{
		if (X_M_Picking_Candidate.STATUS_IP.equals(pickingCandidate.getStatus()))
		{
			// already in progress => nothing to do
		}
		else if (X_M_Picking_Candidate.STATUS_PR.equals(pickingCandidate.getStatus()))
		{
			// already status processed => nothing to do
		}
		else if (X_M_Picking_Candidate.STATUS_CL.equals(pickingCandidate.getStatus()))
		{
			UnClosePickingCandidateCommand.builder()
					.pickingCandidate(pickingCandidate)
					.build()
					.perform();
		}
		else
		{
			throw new AdempiereException("Cannot converted candidate to status Processed").setParameter("pickingCandidate", pickingCandidate);
		}
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedRecords(final List<I_M_Picking_Candidate> pickingCandidates)
	{
		final Set<Integer> shipmentScheduleIds = pickingCandidates.stream()
				.map(I_M_Picking_Candidate::getM_ShipmentSchedule_ID)
				.collect(ImmutableSet.toImmutableSet());

		return huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(getM_HU()).stream()
				.filter(qtyPickedRecord -> shipmentScheduleIds.contains(qtyPickedRecord.getM_ShipmentSchedule_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Check if there is already a shipment.
	 *
	 * @param hu
	 * @param qtyPickedList
	 */
	private void assertHUIsNotShipped(@NonNull final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList)
	{
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (qtyPicked.getM_InOutLine_ID() > 0)
			{
				final I_M_HU hu = getM_HU();
				throw new AdempiereException(MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P, new Object[] { hu.getValue(), qtyPicked.getM_InOutLine().getM_InOut().getDocumentNo() });
			}
		}
	}

	/**
	 * Generally verify the HU's status.
	 *
	 * @param hu
	 */
	private void assertHUIsPicked(@NonNull final I_M_HU hu)
	{
		final String huStatus = hu.getHUStatus();
		if (X_M_HU.HUSTATUS_Picked.equals(huStatus)
				|| X_M_HU.HUSTATUS_Active.equals(huStatus))
		{
			// NOTE: we are also tolerating Active status
			// because the HU is changed to Picked only when the picking candidate is Closed.
			// Until then it's status is Active.
			return;
		}

		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
		final Properties ctx = Env.getCtx();
		final String currentStatusTrl = adReferenceDAO.retrieveListNameTrl(ctx, X_M_HU.HUSTATUS_AD_Reference_ID, huStatus);
		final String pickedStatusTrl = adReferenceDAO.retrieveListNameTrl(ctx, X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Picked)
				+ ", " + adReferenceDAO.retrieveListNameTrl(ctx, X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Active);
		throw new AdempiereException(MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P, new Object[] { hu.getValue(), currentStatusTrl, pickedStatusTrl });
	}

	private void updateHUStatusToActive(@NonNull final I_M_HU hu)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IMutableHUContext huContext = huContextFactory.createMutableHUContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
		handlingUnitsDAO.saveHU(hu);
	}

	private void restoreHUsFromSourceHUs(final int huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(ImmutableList.of(huId));
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!handlingUnitsBL.isDestroyed(sourceHU))
			{
				continue;
			}
			SourceHUsService.get().restoreHuFromSourceHuMarkerIfPossible(sourceHU);
		}
	}

	private void markCandidateAsInProgress(final I_M_Picking_Candidate pickingCandidate)
	{
		pickingCandidate.setStatus(X_M_Picking_Candidate.STATUS_IP);
		InterfaceWrapperHelper.save(pickingCandidate);
	}
}
