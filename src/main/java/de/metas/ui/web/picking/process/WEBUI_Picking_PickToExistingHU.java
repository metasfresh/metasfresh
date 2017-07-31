package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_HU;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.PickingCandidateCommand;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.picking.PickingSlotViewFactory;
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
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_PickToExistingHU
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired
	private PickingCandidateCommand pickingCandidateCommand;
	
	private static final String PARAM_QTY_CU = "QtyCU";
	@Param(parameterName = PARAM_QTY_CU, mandatory = true)
	private BigDecimal qtyCU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_HU));
		}
		
		if (pickingSlotRow.isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS));
		}


// let's decide later, if we allow qty to be added out of thin air in case of "force"
// for now,the qty should come from an exiting HU that's somewhere in this warehouse		
//		final I_M_ShipmentSchedule shipmentSchedule = getView().getShipmentSchedule();
//		final String deliveryRule = Services.get(IShipmentScheduleEffectiveBL.class).getDeliveryRule(shipmentSchedule);
//		if (!Objects.equals(X_M_ShipmentSchedule.DELIVERYRULE_Force, deliveryRule))
//		{
//			return ProcessPreconditionsResolution.reject("deliveryRule must be 'force'");
//		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final int huId = pickingSlotRow.getHuId();
		final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		final int shipmentScheduleId = getView().getShipmentScheduleId();

		pickingCandidateCommand.addQtyToHU(qtyCU, huId, pickingSlotId, shipmentScheduleId);

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	@Override
	protected PickingSlotView getView()
	{
		return PickingSlotView.cast(super.getView());
	}

	@Override
	protected PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
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

		final I_M_ShipmentSchedule shipmentSchedule = getView().getShipmentSchedule(); // can't be null
		return shipmentSchedule.getQtyToDeliver();
	}

}
