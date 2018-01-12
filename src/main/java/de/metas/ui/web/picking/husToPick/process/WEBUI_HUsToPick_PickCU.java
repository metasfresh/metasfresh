package de.metas.ui.web.picking.husToPick.process;

import static de.metas.ui.web.handlingunits.WEBUI_HU_Constants.MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public class WEBUI_HUsToPick_PickCU extends HUsToPickViewBasedProcess implements IProcessPrecondition, IProcessParametersCallout
{

	private static final String MSG_InvalidProduct = "de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_PickCU.InvalidProduct";

	// services
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	private static final String PARAM_M_Product_ID = "M_Product_ID";
	/**
	 * Scanned product, used to validate it against shipment schedule's product.
	 * This parameter is not mandatory because we want to allow the SysAdm to just hide the field in case we don't need this validation.
	 */
	@Param(parameterName = PARAM_M_Product_ID, mandatory = false)
	private int scannedProductId;

	/**
	 * Qty CU to be picked
	 */
	@Param(parameterName = "QtyCU", mandatory = true)
	private BigDecimal qtyCU;

	private transient I_M_Product _shipmentScheduleProduct; // lazy

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final HUEditorRow huRow = getSingleSelectedRow();
		if (!isEligible(huRow))
		{
			final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU);
			return ProcessPreconditionsResolution.reject(reason);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_M_Product_ID.equals(parameterName))
		{
			// Make sure user scanned the right product
			getProduct();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException("QtyCU");
		}

		pickCUs();

		invalidateAndGoBackToPickingSlotsView();
		return MSG_OK;
	}

	private I_M_Product getProduct()
	{
		final I_M_Product shipmentScheduleProduct = getShipmentScheduleProduct();

		//
		// Assert scanned product is matching the shipment schedule product.
		// NOTE: scannedProductId might be not set, in case we deactivate the process parameter.
		if (scannedProductId > 0 && scannedProductId != shipmentScheduleProduct.getM_Product_ID())
		{
			final I_M_Product scannedProduct = loadOutOfTrx(scannedProductId, I_M_Product.class);
			final String scannedProductStr = scannedProduct != null ? scannedProduct.getName() : "?";
			final String expectedProductStr = shipmentScheduleProduct.getName();
			throw new AdempiereException(MSG_InvalidProduct, new Object[] { scannedProductStr, expectedProductStr });
		}

		return shipmentScheduleProduct;
	}

	private I_M_Product getShipmentScheduleProduct()
	{
		if (_shipmentScheduleProduct == null)
		{
			final PickingSlotView pickingSlotsView = getPickingSlotView();
			final I_M_ShipmentSchedule currentShipmentSchedule = pickingSlotsView.getCurrentShipmentSchedule();
			final int shipmentScheduleProductId = currentShipmentSchedule.getM_Product_ID();
			_shipmentScheduleProduct = loadOutOfTrx(shipmentScheduleProductId, I_M_Product.class);
		}
		return _shipmentScheduleProduct;
	}

	private void pickCUs()
	{
		final I_M_Product product = getProduct();
		final I_C_UOM uom = productBL.getStockingUOM(product);
		final Date date = SystemTime.asDate();

		final int huIdToSplit = retrieveHUIdToSplit();
		final List<I_M_HU> splitHUs = HUSplitBuilderCoreEngine.builder()
				.huToSplit(load(huIdToSplit, I_M_HU.class))
				.requestProvider(huContext -> AllocationUtils.createAllocationRequestBuilder()
						.setHUContext(huContext)
						.setProduct(product)
						.setQuantity(qtyCU, uom)
						.setDate(date)
						.setFromReferencedModel(null) // N/A
						.setForceQtyAllocation(false)
						.create())
				.destination(HUProducerDestination.ofVirtualPI())
				.build()
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		final I_M_HU splitCU = ListUtils.singleElement(splitHUs);
		addHUIdToCurrentPickingSlot(splitCU.getM_HU_ID());
	}

	private int retrieveHUIdToSplit()
	{
		return retrieveEligibleHUEditorRows()
				.map(HUEditorRow::getM_HU_ID)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Only one HU shall be selected")));
	}
}
