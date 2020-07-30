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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_MovementLine;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class MovementBL implements IMovementBL
{
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	
	@Override
	public I_C_UOM getC_UOM(final I_M_MovementLine movementLine)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		return productBL.getStockUOM(movementLine.getM_Product_ID());
	}

	@Override
	public Quantity getMovementQty(final I_M_MovementLine movementLine, final I_C_UOM uom)
	{
		final BigDecimal movementQtyValue = movementLine.getMovementQty();
		final I_C_UOM movementQtyUOM = getC_UOM(movementLine);
		final Quantity movementQty = new Quantity(movementQtyValue, movementQtyUOM);

		final int productId = movementLine.getM_Product_ID();
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(productId);

		return uomConversionBL.convertQuantityTo(movementQty, uomConversionCtx, uom);
	}

	@Override
	public void setMovementQty(final I_M_MovementLine movementLine, final BigDecimal movementQty, final I_C_UOM uom)
	{
		final ProductId productId = ProductId.ofRepoId(movementLine.getM_Product_ID());
		final I_C_UOM uomTo = getC_UOM(movementLine);

		final BigDecimal movementQtyConv = uomConversionBL.convertQty(productId, movementQty, uom, uomTo);

		movementLine.setMovementQty(movementQtyConv);
	}

	@Override
	public void setC_Activities(final I_M_MovementLine movementLine)
	{
		final ActivityId productActivityId = Services.get(IProductActivityProvider.class).retrieveActivityForAcct(
				ClientId.ofRepoId(movementLine.getAD_Client_ID()),
				OrgId.ofRepoId(movementLine.getAD_Org_ID()),
				ProductId.ofRepoId(movementLine.getM_Product_ID()));

		final I_M_Locator locatorFrom = movementLine.getM_Locator();
		final ActivityId activityFromId = getActivity(locatorFrom, productActivityId);

		movementLine.setC_ActivityFrom_ID(ActivityId.toRepoId(activityFromId));

		final I_M_Locator locatorTo = movementLine.getM_LocatorTo();
		final ActivityId activityToId = getActivity(locatorTo, productActivityId);

		movementLine.setC_Activity_ID(ActivityId.toRepoId(activityToId));

		InterfaceWrapperHelper.save(movementLine);
	}

	/**
	 * @return the activity from the warehouse, if it exists. Otherwise, return the defaultValue.
	 */
	private ActivityId getActivity(@NonNull final I_M_Locator locator, final ActivityId defaultActivityId)
	{
		final org.adempiere.warehouse.model.I_M_Warehouse warehouse = InterfaceWrapperHelper.create(locator.getM_Warehouse(), org.adempiere.warehouse.model.I_M_Warehouse.class);

		final ActivityId warehouseActivityId = ActivityId.ofRepoIdOrNull(warehouse.getC_Activity_ID());
		if (warehouseActivityId != null)
		{
			return warehouseActivityId;
		}
		return defaultActivityId;
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
	
	@Override
	public void voidMovement(final I_M_Movement movement)
	{
		docActionBL.processEx(movement, IDocument.ACTION_Void, IDocument.STATUS_Reversed);
	}
}
