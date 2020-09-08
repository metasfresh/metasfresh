package de.metas.handlingunits.picking.candidate.commands;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.ShipmentScheduleId;
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
 * Unprocess picking candidate.
 *
 * The status will be changed from Processed to InProgress.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class UnProcessPickingCandidateCommand
{
	private static final AdMessageKey MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P = AdMessageKey.of("WEBUI_Picking_Wrong_HU_Status");
	private static final AdMessageKey MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P = AdMessageKey.of("WEBUI_Picking_Already_Shipped");

	private final transient IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);

	private final HuId2SourceHUsService sourceHUsRepository;
	private final PickingCandidateRepository pickingCandidateRepository;
	private final HuId _huId;

	private I_M_HU _hu = null; // lazy

	@Builder
	public UnProcessPickingCandidateCommand(
			@NonNull final HuId2SourceHUsService sourceHUsRepository,
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final HuId huId)
	{
		this.sourceHUsRepository = sourceHUsRepository;
		this.pickingCandidateRepository = pickingCandidateRepository;

		_huId = huId;
	}

	public void perform()
	{
		final I_M_HU hu = getM_HU();
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final List<PickingCandidate> pickingCandidates = pickingCandidateRepository.getByHUIds(ImmutableSet.of(huId));

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = retrieveQtyPickedRecords(pickingCandidates);

		assertHUIsNotShipped(qtyPickedList);
		assertHUIsPicked(hu);

		// if everything is OK, go ahead
		pickingCandidates.forEach(this::unCloseIfNeeded);
		qtyPickedList.forEach(InterfaceWrapperHelper::delete);
		updateHUStatusToActive(hu);

		restoreHUsFromSourceHUs(huId);

		pickingCandidates.forEach(this::changeStatusToDraftAndSave);
	}

	@NonNull
	private I_M_HU getM_HU()
	{
		if (_hu == null)
		{
			_hu = Services.get(IHandlingUnitsDAO.class).getById(_huId);
		}
		return _hu;
	}

	private void unCloseIfNeeded(final PickingCandidate pickingCandidate)
	{
		if (!PickingCandidateStatus.Closed.equals(pickingCandidate.getProcessingStatus()))
		{
			return;
		}

		UnClosePickingCandidateCommand.builder()
				.pickingCandidate(pickingCandidate)
				.build()
				.perform();
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedRecords(final List<PickingCandidate> pickingCandidates)
	{
		final Set<ShipmentScheduleId> shipmentScheduleIds = pickingCandidates.stream()
				.map(PickingCandidate::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());

		return huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(getM_HU())
				.stream()
				.filter(qtyPickedRecord -> shipmentScheduleIds.contains(ShipmentScheduleId.ofRepoIdOrNull(qtyPickedRecord.getM_ShipmentSchedule_ID())))
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
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

		final IMutableHUContext huContext = huContextFactory.createMutableHUContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
		handlingUnitsDAO.saveHU(hu);
	}

	private void restoreHUsFromSourceHUs(final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final SourceHUsService sourceHUsService = SourceHUsService.get();

		final Collection<I_M_HU> sourceHUs = sourceHUsRepository.retrieveActualSourceHUs(ImmutableSet.of(huId));
		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (!handlingUnitsBL.isDestroyed(sourceHU))
			{
				continue;
			}
			sourceHUsService.restoreHuFromSourceHuMarkerIfPossible(sourceHU);
		}
	}

	private void changeStatusToDraftAndSave(final PickingCandidate pickingCandidate)
	{
		pickingCandidate.changeStatusToDraft();
		pickingCandidateRepository.save(pickingCandidate);
	}
}
