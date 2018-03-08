package de.metas.adempiere.callout;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Properties;

import org.adempiere.minventory.api.IInventoryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_M_Inventory;

/**
 * Callout for {@link I_M_Inventory} table
 * 
 * @author tsa
 * 
 */
public class M_Inventory extends CalloutEngine
{
	
	/**
	 * On {@link I_M_Inventory#COLUMNNAME_QuickInput_Product_ID} changes.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param field
	 * @param value
	 * @return
	 */
	public String onQuickInput_Product_ID(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField field, final Object value)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		evalQuickInput(ctx, WindowNo, mTab);

		selectFocus(mTab);

		return NO_ERROR;
	}

	/**
	 * On {@link I_M_Inventory#COLUMNNAME_QuickInput_QtyInternalGain} changes.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param field
	 * @param value
	 * @return
	 */
	public String onQuickInput_QtyInternalUse(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField field, final Object value)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		evalQuickInput(ctx, WindowNo, mTab);

		selectFocus(mTab);

		return NO_ERROR;
	}

	/**
	 * Evaluates {@link I_M_Inventory} table and if possible creates a new {@link I_M_InventoryLine} and clears quick input fields
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 */
	private void evalQuickInput(final Properties ctx, final int WindowNo, final GridTab mTab)
	{
		final I_M_Inventory inventory = InterfaceWrapperHelper.create(mTab, I_M_Inventory.class);

		final int productId = inventory.getQuickInput_Product_ID();
		if (productId <= 0)
		{
			return;
		}

		final BigDecimal qtyInternalGain = inventory.getQuickInput_QtyInternalGain();
		if (qtyInternalGain == null || qtyInternalGain.signum() == 0)
		{
			return;
		}

		addInventoryLine(inventory, productId, qtyInternalGain);

		// clear the values in the inventory window
		// using invokeLater because at the time of this callout invocation we
		// are most probably in the mights of something that might prevent
		// changes to the actual swing component
		clearQuickInputFieldsLater(WindowNo, mTab);

		refreshTabAndIncludedTabs(mTab);
	}

	/**
	 * Creates a new inventory line
	 * 
	 * @param inventory
	 * @param productId
	 * @param qtyPlus
	 */
	private void addInventoryLine(final I_M_Inventory inventory, final int productId, final BigDecimal qtyPlus)
	{
		final int chargeId = Services.get(IInventoryBL.class).getDefaultInternalChargeId();

		final I_M_Warehouse warehouse = inventory.getM_Warehouse();
		final I_M_Locator locator = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);

		final I_M_InventoryLine line = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class, inventory);
		line.setAD_Org_ID(inventory.getAD_Org_ID());
		line.setM_Inventory_ID(inventory.getM_Inventory_ID());
		line.setIsActive(true);
		// line.setLine(Line); // shall be auto
		// line.setInventoryType(InventoryType); // applies only for physical inventory
		line.setM_Locator_ID(locator.getM_Locator_ID());
		line.setM_Product_ID(productId);
		
		final BigDecimal qtyInternalUse = qtyPlus.negate();
		line.setQtyInternalUse(qtyInternalUse);
		line.setProcessed(false);
		line.setC_Charge_ID(chargeId);
		InterfaceWrapperHelper.save(line);
	}

	/**
	 * Checks quick input fields and focus the first one which is empty (i.e. where user needs to input data).
	 * 
	 * @param mTab
	 */
	private void selectFocus(final GridTab mTab)
	{
		final I_M_Inventory inventory = InterfaceWrapperHelper.create(mTab, I_M_Inventory.class);

		final Integer productId = inventory.getQuickInput_Product_ID();
		if (productId <= 0)
		{
			mTab.getField(I_M_Inventory.COLUMNNAME_QuickInput_Product_ID).requestFocus();
			return;
		}

		final BigDecimal qty = inventory.getQuickInput_QtyInternalGain();
		if (qty == null || qty.signum() <= 0)
		{
			// product has been set, but qty hasn't
			mTab.getField(I_M_Inventory.COLUMNNAME_QuickInput_QtyInternalGain).requestFocus();
			return;
		}

	}

	/**
	 * Reset quick input fields.
	 * 
	 * Same as {@link #clearQuickInputFields(GridTab)} but it will be done in UI's events queue thread.
	 * 
	 * @param windowNo
	 * @param mTab
	 */
	public static void clearQuickInputFieldsLater(final int windowNo, final GridTab mTab)
	{
		// clear the values in the inventory window
		// using invokeLater because at the time of this callout invocation we
		// are most probably in the mights of something that might prevent
		// changes to the actual swing component
		Services.get(IClientUI.class).invokeLater(windowNo, new Runnable()
		{
			@Override
			public void run()
			{
				clearQuickInputFields(mTab);
			}
		});
	}

	/**
	 * Reset quick input fieldsO
	 * 
	 * @param mTab
	 */
	public static void clearQuickInputFields(final GridTab mTab)
	{
		final I_M_Inventory inventory = InterfaceWrapperHelper.create(mTab, I_M_Inventory.class);
		inventory.setQuickInput_Product_ID(-1);
		inventory.setQuickInput_QtyInternalGain(null);

		// these changes will be propagated to the GUI component
		mTab.setValue(I_M_Inventory.COLUMNNAME_QuickInput_Product_ID, null);
		mTab.setValue(I_M_Inventory.COLUMNNAME_QuickInput_QtyInternalGain, null);
		mTab.dataSave(true);
	}

	/**
	 * Refreshes given tab and all included tabs.
	 * 
	 * @param gridTab
	 */
	private void refreshTabAndIncludedTabs(final GridTab gridTab)
	{
		gridTab.dataRefreshRecursively();
		for (final GridTab includedTab : gridTab.getIncludedTabs())
		{
			includedTab.dataRefreshAll();
		}
		gridTab.dataRefresh();
	}
}
