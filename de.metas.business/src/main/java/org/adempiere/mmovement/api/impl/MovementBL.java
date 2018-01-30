package org.adempiere.mmovement.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;

import de.metas.product.IProductBL;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.quantity.Quantity;

public class MovementBL implements IMovementBL
{
	@Override
	public I_C_UOM getC_UOM(final I_M_MovementLine movementLine)
	{
		return Services.get(IProductBL.class).getStockingUOM(movementLine.getM_Product_ID());
	}

	@Override
	public Quantity getMovementQty(final I_M_MovementLine movementLine, final I_C_UOM uom)
	{
		final BigDecimal movementQtyValue = movementLine.getMovementQty();
		final I_C_UOM movementQtyUOM = getC_UOM(movementLine);
		final Quantity movementQty = new Quantity(movementQtyValue, movementQtyUOM);
		
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final I_M_Product product = movementLine.getM_Product();
		final IUOMConversionContext uomConversionCtx = uomConversionBL.createConversionContext(product);
		
		return uomConversionBL.convertQuantityTo(movementQty, uomConversionCtx, uom);
	}

	@Override
	public void setMovementQty(final I_M_MovementLine movementLine, final BigDecimal movementQty, final I_C_UOM uom)
	{
		final I_M_Product product = movementLine.getM_Product();
		final I_C_UOM uomTo = getC_UOM(movementLine);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final BigDecimal movementQtyConv = uomConversionBL.convertQty(product, movementQty, uom, uomTo);

		movementLine.setMovementQty(movementQtyConv);
	}

	@Override
	public void setC_Activities(final I_M_MovementLine movementLine)
	{
		final I_M_Product product = movementLine.getM_Product();
		final IContextAware contextAwareMovement = InterfaceWrapperHelper.getContextAware(movementLine);

		final I_C_Activity productActivity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextAwareMovement, movementLine.getAD_Org(), product);

		final I_M_Locator locatorFrom = movementLine.getM_Locator();

		final I_C_Activity activityFrom = getActivity(locatorFrom, productActivity);

		movementLine.setC_ActivityFrom(activityFrom);

		final I_M_Locator locatorTo = movementLine.getM_LocatorTo();

		final I_C_Activity activityTo = getActivity(locatorTo, productActivity);

		movementLine.setC_Activity(activityTo);

		InterfaceWrapperHelper.save(movementLine);
	}

	/**
	 * Take the activity from the warehouse, if it exists. Otherwise, take the one from product
	 * 
	 * @param locator
	 * @param productActivity
	 * @return
	 */
	private I_C_Activity getActivity(final I_M_Locator locator, final I_C_Activity productActivity)
	{
		final org.adempiere.warehouse.model.I_M_Warehouse warehouse = InterfaceWrapperHelper.create(locator.getM_Warehouse(), org.adempiere.warehouse.model.I_M_Warehouse.class);

		final I_C_Activity warehouseActivity = warehouse.getC_Activity();

		if (warehouseActivity != null)
		{
			return warehouseActivity;
		}
		return productActivity;
	}

	@Override
	public boolean isReversal(final I_M_Movement movement)
	{
		Check.assumeNotNull(movement, "movement not null");
		
		// Check if we have a counter-part reversal document
		final int reversalId = movement.getReversal_ID();
		if (reversalId <= 0)
		{
			return false;
		}
		
		// Make sure this is the actual reversal and not the original document which was reversed
		// i.e. this document was created after the reversal (so Reversal_ID is less than this document's ID)
		final int movementId = movement.getM_Movement_ID();
		final boolean reversal = movementId > reversalId;
		return reversal;
	}
}
