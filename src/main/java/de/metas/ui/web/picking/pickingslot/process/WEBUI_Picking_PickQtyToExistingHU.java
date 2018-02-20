package de.metas.ui.web.picking.pickingslot.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
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
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_PickQtyToExistingHU
		extends WEBUI_Picking_With_M_Source_HU_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	
	@Autowired
	private PickingConfigRepository pickingConfigRepo;
	
	@Autowired
	private PickingCandidateService pickingCandidateService;

	private static final String PARAM_QTY_CU = "QtyCU";
	@Param(parameterName = PARAM_QTY_CU, mandatory = true)
	private BigDecimal qtyCU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		if (pickingSlotRow.isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS));
		}

		if (!checkSourceHuPrecondition())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		
		final boolean isAllowOverdelivery = pickingConfigRepo.getPickingConfig().isAllowOverDelivery();

		pickingCandidateService.addQtyToHU()
				.qtyCU(qtyCU)
				.targetHUId(pickingSlotRow.getHuId())
				.pickingSlotId(pickingSlotRow.getPickingSlotId())
				.shipmentScheduleId(getView().getCurrentShipmentScheduleId())
				.isAllowOverdelivery(isAllowOverdelivery)
				.build()
				.performAndGetQtyPicked();

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	/**
	 * Returns the {@code qtyToDeliver} value of the currently selected shipment schedule, or {@code null}.
	 */
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (!Objects.equals(PARAM_QTY_CU, parameter.getColumnName()))
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}

		final I_M_ShipmentSchedule shipmentSchedule = getView().getCurrentShipmentSchedule(); // can't be null
		//qty to deliver - picked qty)
		return shipmentSchedule.getQtyToDeliver().subtract(shipmentSchedule.getQtyPickList()); 
	}

}
