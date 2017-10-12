package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_PROCESSED_RECORDS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
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

/**
 * Unprocesses the processed {@link I_M_Picking_Candidate} of the currently selected TU.<br>
 * Unprocessing means that
 * <ul>
 * <li>the HU is changed from status "picked" to "active" (even if it was only "planned" before the candidate was processed!)</li>
 * <li>the HU is deassociated with its shipment schedule (changes QtyPicked and QtyToDeliver)</li>
 * </ul>
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_M_Picking_Candidate_Unprocess
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private PickingCandidateService pickingCandidateService;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		if (!pickingSlotRow.isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_PROCESSED_RECORDS));
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow rowToProcess = getSingleSelectedRow();
		final I_M_HU hu = load(rowToProcess.getHuId(), I_M_HU.class);
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = retrieveQtyPickedRecords(hu);

		assertHuIsNotShipped(hu, qtyPickedList);
		assertHuIsPicked(hu);

		// if everything is OK, go ahead
		qtyPickedList.forEach(InterfaceWrapperHelper::delete);
		updateHuStatus(hu);
		pickingCandidateService.setCandidatesInProgress(ImmutableList.of(hu.getM_HU_ID()));

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	private List<I_M_ShipmentSchedule_QtyPicked> retrieveQtyPickedRecords(@NonNull final I_M_HU hu)
	{
		final List<I_M_ShipmentSchedule> scheds = Services.get(IQueryBL.class).createQueryBuilder(I_M_Picking_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Candidate.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.andCollect(I_M_Picking_Candidate.COLUMN_M_ShipmentSchedule_ID)
				.create()
				.list();

		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);

		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = new ArrayList<>();
		for (final I_M_ShipmentSchedule shipmentSchedule : scheds)
		{
			qtyPickedList.addAll(huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(shipmentSchedule, hu, ITrx.TRXNAME_ThreadInherited));
		}
		return qtyPickedList;
	}

	/**
	 * Check if there is already a shipment.
	 * 
	 * @param hu
	 * @param qtyPickedList
	 */
	private void assertHuIsNotShipped(
			@NonNull final I_M_HU hu, 
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList)
	{
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (qtyPicked.getM_InOutLine_ID() > 0)
			{
				throw new AdempiereException(MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P, new Object[] { hu.getValue(), qtyPicked.getM_InOutLine().getM_InOut().getDocumentNo() });
			}
		}
	}

	/**
	 * Generally verify the HU's status.
	 * 
	 * @param hu
	 */
	private void assertHuIsPicked(@NonNull final I_M_HU hu)
	{
		if (X_M_HU.HUSTATUS_Picked.equals(hu.getHUStatus()))
		{
			return;
		}
		final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
		final String currentStatusTrl = adReferenceDAO.retrieveListNameTrl(getCtx(), X_M_HU.HUSTATUS_AD_Reference_ID, hu.getHUStatus());
		final String pickedStatusTrl = adReferenceDAO.retrieveListNameTrl(getCtx(), X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Picked);
		throw new AdempiereException(MSG_WEBUI_PICKING_WRONG_HU_STATUS_3P, new Object[] { hu.getValue(), currentStatusTrl, pickedStatusTrl });
	}

	private void updateHuStatus(@NonNull final I_M_HU hu)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IMutableHUContext huContext = huContextFactory.createMutableHUContext(getCtx(), ITrx.TRXNAME_ThreadInherited);
		handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
		handlingUnitsDAO.saveHU(hu);
	}

	@Override
	protected PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

}
