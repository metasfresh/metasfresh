package de.metas.ui.web.picking.pickingslot.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKING_SLOT;

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
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.util.WEBUI_ProcessHelper;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
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
public class WEBUI_Picking_PickQtyToNewHU
		extends WEBUI_Picking_With_M_Source_HU_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired
	private PickingCandidateService pickingCandidateService;

	private static final String PARAM_M_HU_PI_Item_Product_ID = I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID, mandatory = true)
	private I_M_HU_PI_Item_Product huPIItemProduct;

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
		if (!pickingSlotRow.isPickingSlotRow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKING_SLOT));
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

		final I_M_HU hu = createAndAddHU(pickingSlotRow);
		addPickedQuantity(hu, pickingSlotRow);

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	private void addPickedQuantity(
			@NonNull final I_M_HU hu,
			@NonNull final PickingSlotRow pickingSlotRow)
	{
		if (qtyCU.signum() > 0)
		{
			pickingCandidateService.addQtyToHU()
					.qtyCU(qtyCU)
					.targetHUId(hu.getM_HU_ID())
					.pickingSlotId(pickingSlotRow.getPickingSlotId())
					.shipmentScheduleId(getView().getCurrentShipmentScheduleId())
					.build()
					.performAndGetQtyPicked();
		}
	}

	private I_M_HU createAndAddHU(@NonNull final PickingSlotRow pickingSlotRow)
	{
		final I_M_Locator pickingSlotLocator = getPickingSlotLocator(pickingSlotRow);

		// Create a new empty TU
		final I_M_HU hu = createTU(huPIItemProduct, pickingSlotLocator);

		// Add the TU to picking slot (as candidate)
		final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		final int shipmentScheduleId = getView().getCurrentShipmentScheduleId();

		pickingCandidateService.addHUToPickingSlot(hu.getM_HU_ID(), pickingSlotId, shipmentScheduleId);

		return hu;
	}

	/**
	 * 
	 * @return a list of PI item products that match the selected shipment schedule's product and partner, sorted by name.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_Product_ID, dependsOn = {}, numericKey = true, lookupTableName = I_M_HU_PI_Item_Product.Table_Name)
	private LookupValuesList getM_HU_PI_Item_Products()
	{
		final Properties ctx = getCtx();
		final I_M_ShipmentSchedule shipmentSchedule = getView().getCurrentShipmentSchedule(); // can't be null

		return WEBUI_ProcessHelper.retrieveHUPIItemProducts(ctx,
				shipmentSchedule.getM_Product(),
				shipmentSchedule.getC_BPartner(),
				true); // includeVirtualItem = true..similar case as with production
	}

	/**
	 * Returns the {@code qtyToDeliver} value of the currently selected shipment schedule, or {@code null}.
	 */
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_QTY_CU, parameter.getColumnName()))
		{
			final I_M_ShipmentSchedule shipmentSchedule = getView().getCurrentShipmentSchedule(); // can't be null
			return shipmentSchedule.getQtyToDeliver(); // TODO: get the "better" value from teo, when it's available
		}
		else if (Objects.equals(PARAM_M_HU_PI_Item_Product_ID, parameter.getColumnName()))
		{
			final I_M_ShipmentSchedule shipmentSchedule = getView().getCurrentShipmentSchedule();
			final I_M_HU_PI_Item_Product huPIItemProduct = shipmentSchedule.getM_HU_PI_Item_Product();
			if (huPIItemProduct == null)
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}

			return IntegerLookupValue.of(huPIItemProduct.getM_HU_PI_Item_Product_ID(), huPIItemProduct.getName());
		}

		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	private static final I_M_Locator getPickingSlotLocator(final PickingSlotRow pickingSlotRow)
	{
		final int pickingSlotWarehouseId = pickingSlotRow.getPickingSlotWarehouseId();
		if (pickingSlotWarehouseId <= 0)
		{
			throw new AdempiereException("Picking slot with M_PickingSlot_ID=" + pickingSlotRow.getPickingSlotId() + " has no warehouse configured");
		}
		final I_M_Warehouse pickingSlotWarehouse = InterfaceWrapperHelper.loadOutOfTrx(pickingSlotWarehouseId, I_M_Warehouse.class);
		final I_M_Locator pickingSlotLocator = Services.get(IWarehouseBL.class).getDefaultLocator(pickingSlotWarehouse);
		return pickingSlotLocator;
	}

	/**
	 * Creates a new M_HU within the processe's interited trx.
	 * 
	 * @param itemProduct
	 * @param locator
	 * @return
	 */
	private static final I_M_HU createTU(
			@NonNull final I_M_HU_PI_Item_Product itemProduct,
			@NonNull final I_M_Locator locator)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		final I_M_HU_PI huPI = itemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		return huTrxBL.createHUContextProcessorExecutor()
				.call(huContext -> handlingUnitsDAO.createHUBuilder(huContext)
						.setM_HU_Item_Parent(null) // no parent
						.setM_HU_PI_Item_Product(itemProduct)
						.setM_Locator(locator)

						// we are going to load from a "real" source HU onto this HU, so both shall be active. Otherwise it would look as if stuff was vanishing for the source HU
						.setHUStatus(X_M_HU.HUSTATUS_Active)

						.create(huPI));
	}
}
