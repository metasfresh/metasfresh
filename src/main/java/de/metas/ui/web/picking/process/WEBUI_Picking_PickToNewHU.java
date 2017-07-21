package de.metas.ui.web.picking.process;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.util.WEBUI_ProcessHelper;
import de.metas.ui.web.picking.PickingCandidateCommand;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.picking.PickingSlotViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
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
public class WEBUI_Picking_PickToNewHU extends ViewBasedProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired
	private PickingCandidateCommand pickingCandidateCommand;

	@Param(parameterName = I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, mandatory = true)
	private I_M_HU_PI_Item_Product huPIItemProduct;

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

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final I_M_Locator pickingSlotLocator = getPickingSlotLocator(pickingSlotRow);

		// Create a new empty TU
		final I_M_HU hu = createTU(huPIItemProduct, pickingSlotLocator);

		// Add the TU to picking slot (as candidate)
		final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		final int shipmentScheduleId = getView().getShipmentScheduleId();
		pickingCandidateCommand.addHUToPickingSlot(hu.getM_HU_ID(), pickingSlotId, shipmentScheduleId);

		// add the qty
		if (qtyCU.signum() > 0)
		{
			pickingCandidateCommand.addQtyToHU(qtyCU, hu.getM_HU_ID(), pickingSlotId, shipmentScheduleId);
		}

		invalidateView();
		invalidateParentView();

		invalidateView();

		return MSG_OK;
	}

	/**
	 * 
	 * @return a list of PI item products that match the selected shipment schedule's product and partner, sorted by name.
	 */
	@ProcessParamLookupValuesProvider(parameterName = I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, dependsOn = {}, numericKey = true, lookupTableName = I_M_HU_PI_Item_Product.Table_Name)
	private LookupValuesList getM_HU_PI_Item_Products()
	{
		final Properties ctx = getCtx();
		final I_M_ShipmentSchedule shipmentSchedule = getView().getShipmentSchedule(); // can't be null

		return WEBUI_ProcessHelper.retrieveHUPIItemProducts(ctx,
				shipmentSchedule.getM_Product(),
				shipmentSchedule.getC_BPartner());
	}

	/**
	 * Returns the {@code qtyToDeliver} value of the currently selected shipment schedule, or {@code null}.
	 */
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_QTY_CU, parameter.getColumnName()))
		{
			final I_M_ShipmentSchedule shipmentSchedule = getView().getShipmentSchedule(); // can't be null
			return shipmentSchedule.getQtyToDeliver(); // TODO: get the "better" value from teo, when it's available
		}
		else if (Objects.equals(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, parameter.getColumnName()))
		{
			// if i return and in or IntegerLookupValue, i get
			// org.adempiere.exceptions.AdempiereException: No active process found in this thread
			// final I_M_ShipmentSchedule shipmentSchedule = getView().getShipmentSchedule(); // can't be null
			// return shipmentSchedule.getM_HU_PI_Item_Product_ID();

			// final I_M_HU_PI_Item_Product huPIItemProduct = shipmentSchedule.getM_HU_PI_Item_Product();
			// final IntegerLookupValue lookupValue = IntegerLookupValue.of(huPIItemProduct.getM_HU_PI_Item_Product_ID(), huPIItemProduct.getName());

			// if i return LookupValuesList, i get
			// Cannot convert M_HU_PI_Item_Product_ID's value 'LookupValuesList{values=[IntegerLookupValue{id=2004054, displayName=constant{value=IFCO 6416 x 12.50 kg}}]}' (class de.metas.ui.web.window.datatypes.LookupValuesList) to class de.metas.ui.web.window.datatypes.LookupValue$IntegerLookupValue
			// return LookupValuesList.fromNullable(lookupValue);
			// return lookupValue;
		}

		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	private static final I_M_Locator getPickingSlotLocator(final PickingSlotRow pickingSlotRow)
	{
		final int pickingSlotWarehouseId = pickingSlotRow.getPickingSlotWarehouseId();
		if (pickingSlotWarehouseId <= 0)
		{
			throw new AdempiereException("Picking slot has no warehouse configured");
		}
		final I_M_Warehouse pickingSlotWarehouse = InterfaceWrapperHelper.loadOutOfTrx(pickingSlotWarehouseId, I_M_Warehouse.class);
		final I_M_Locator pickingSlotLocator = Services.get(IWarehouseBL.class).getDefaultLocator(pickingSlotWarehouse);
		return pickingSlotLocator;
	}

	private static final I_M_HU createTU(@NonNull final I_M_HU_PI_Item_Product itemProduct, @NonNull final I_M_Locator locator)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		final I_M_HU_PI huPI = itemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		return huTrxBL.createHUContextProcessorExecutor()
				.call(huContext -> handlingUnitsDAO.createHUBuilder(huContext)
						.setM_HU_Item_Parent(null) // no parent
						.setM_HU_PI_Item_Product(itemProduct)
						.setM_Locator(locator)
						.setHUStatus(X_M_HU.HUSTATUS_Planning)
						.create(huPI));
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

}
