package de.metas.inventory.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;

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

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.engine.IDocument;
import de.metas.inventory.IInventoryBL;
import de.metas.quantity.Quantity;
import lombok.NonNull;

public class InventoryBL implements IInventoryBL
{
	@VisibleForTesting
	public static final String SYSCONFIG_QuickInput_Charge_ID = "de.metas.adempiere.callout.M_Inventory.QuickInput.C_Charge_ID";

	@Override
	public int getDefaultInternalChargeId()
	{
		final Properties ctx = Env.getCtx();
		final int chargeId = Services.get(ISysConfigBL.class).getIntValue(
				SYSCONFIG_QuickInput_Charge_ID,
				-1, // defaultValue
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
		if (chargeId <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_SysConfig_ID@: " + InventoryBL.SYSCONFIG_QuickInput_Charge_ID);
		}

		return chargeId;
	}

	@Override
	public void addDescription(@NonNull final I_M_Inventory inventory, final String descriptionToAdd)
	{
		final String description = inventory.getDescription();
		if (Check.isEmpty(description, true))
		{
			inventory.setDescription(description);
		}
		else
		{
			inventory.setDescription(description + " | " + description);
		}
	}

	@Override
	public void addDescription(@NonNull final I_M_InventoryLine inventoryLine, final String descriptionToAdd)
	{
		final String description = inventoryLine.getDescription();
		if (Check.isEmpty(description, true))
		{
			inventoryLine.setDescription(description);
		}
		else
		{
			inventoryLine.setDescription(description + " | " + description);
		}
	}

	@Override
	public boolean isComplete(final I_M_Inventory inventory)
	{
		final String docStatus = inventory.getDocStatus();
		return IDocument.STATUS_Completed.equals(docStatus)
				|| IDocument.STATUS_Closed.equals(docStatus)
				|| IDocument.STATUS_Reversed.equals(docStatus);
	}

	@Override
	public Quantity getMovementQty(final I_M_InventoryLine inventoryLine)
	{
		final I_C_UOM uom = loadOutOfTrx(inventoryLine.getC_UOM_ID(), I_C_UOM.class);

		if (isInternalUseInventory(inventoryLine))
		{
			final BigDecimal qtyValue = inventoryLine.getQtyInternalUse().negate();
			return Quantity.of(qtyValue, uom);
		}
		else
		{
			BigDecimal qtyValue = inventoryLine.getQtyCount().subtract(inventoryLine.getQtyBook());
			return Quantity.of(qtyValue, uom);
		}
	}

	@Override
	public Quantity getMovementQtyInStockingUOM(final I_M_InventoryLine inventoryLine)
	{
		final Quantity movementQty = getMovementQty(inventoryLine);
		return Services.get(IUOMConversionBL.class).convertToProductUOM(movementQty, inventoryLine.getM_Product_ID());
	}

	@Override
	public boolean isInternalUseInventory(final I_M_InventoryLine inventoryLine)
	{
		/*
		 * TODO: need to add M_Inventory.IsInternalUseInventory flag
		 * see FR [ 1879029 ] Added IsInternalUseInventory flag to M_Inventory table
		 * MInventory parent = getParent();
		 * return parent != null && parent.isInternalUseInventory();
		 */
		return inventoryLine.getQtyInternalUse().signum() != 0;
	}

	@Override
	public boolean isSOTrx(final I_M_InventoryLine inventoryLine)
	{
		return getMovementQty(inventoryLine).signum() < 0;
	}
}
