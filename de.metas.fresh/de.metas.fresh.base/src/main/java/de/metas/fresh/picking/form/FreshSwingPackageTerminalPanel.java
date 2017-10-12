/**
 *
 */
package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
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

import org.adempiere.util.Check;

import com.google.common.base.Supplier;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.fresh.picking.FreshPackingDetailsMd;
import de.metas.fresh.picking.FreshProductKey;
import de.metas.fresh.picking.PackingMaterialKey;
import de.metas.fresh.picking.PackingMaterialLayout;
import de.metas.fresh.picking.PickingSlotKey;
import de.metas.fresh.picking.form.swing.FreshSwingPackageItems;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.picking.terminal.form.swing.AbstractPackageDataPanel;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminal;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;
import de.metas.picking.terminal.form.swing.SwingPackageBoxesItems;

/**
 * Packing window main panel (second window)
 *
 * @author cg
 *
 */
public class FreshSwingPackageTerminalPanel extends AbstractPackageTerminalPanel
{
	/**
	 * Please select a picking slot first
	 */
	private static final String ERR_SELECT_PICKING_SLOT = "@SelectPickingSlot@";

	// private static final String ERR_SELECT_PRODUCT = "@SelectProduct@";

	public FreshSwingPackageTerminalPanel(final ITerminalContext terminalContext, final AbstractPackageTerminal parent)
	{
		super(terminalContext, parent);
	}

	@Override
	protected AbstractPackageDataPanel createPackageDataPanel()
	{
		return new FreshSwingPackageDataPanel(this);
	}

	@Override
	protected SwingPackageBoxesItems createProductKeysPanel()
	{
		return new FreshSwingPackageItems(this);
	}

	@Override
	protected IKeyLayout createPackingMaterialsKeyLayout()
	{
		final PackingMaterialLayout packingMaterialsLayout = new PackingMaterialLayout(getTerminalContext());
		return packingMaterialsLayout;
	}

	@Override
	protected final PackingMaterialLayout getPackingMaterialsKeyLayout()
	{
		return (PackingMaterialLayout)super.getPackingMaterialsKeyLayout();
	}

	@Override
	public void dynInit()
	{
		// product data
		final ITerminalLabel productData = getTerminalFactory().createLabel(getParent().getPickingOKPanel().getLabelData(), false);
		productData.setFont(14f);

		SwingTerminalFactory.addChild(panelCenter, productData, "dock north, wrap, align left");
		SwingTerminalFactory.addChild(panelCenter, getPickingData(), "dock north, wrap");
		SwingTerminalFactory.addChild(panelCenter, getPackingMaterialsKeyLayoutPanel(), "dock north, wrap, hmin 60");
		SwingTerminalFactory.addChild(panelCenter, getProductKeysPanel().getButtonsPanel(), "dock north, wrap");
		SwingTerminalFactory.addChild(panelCenter, getProductKeysPanel().getProductsKeyLayoutPanel(), "dock center, wrap");

		// add components in main panel
		add(getProductKeysPanel(), "dock east, w 45%");
		add(panelCenter, "dock center, push, w 55%");
	}

	@Override
	public FreshSwingPackageDataPanel getPickingData()
	{
		return (FreshSwingPackageDataPanel)super.getPickingData();
	}

	/**
	 * When user presses on Packing Material (e.g. IFCO button) this method is called.
	 */
	@Override
	public void keyPressed(final ITerminalKey key)
	{
		if (isDisposed())
		{
			return;
		}

		//
		// Key pressed: Packing Material Key
		if (key instanceof PackingMaterialKey)
		{
			final PackingMaterialKey packingMaterialKey = (PackingMaterialKey)key;
			onPackingMaterialKeyPressed(packingMaterialKey);
		}
		//
		// Key pressed: Picking Slot Key
		else if (key instanceof PickingSlotKey)
		{
			final PickingSlotKey pickingSlotKey = (PickingSlotKey)key;
			onPickingSlotKeyPressed(pickingSlotKey);
		}
		//
		// Key pressed: Product Key
		else if (key instanceof FreshProductKey)
		{
			final FreshProductKey productKey = (FreshProductKey)key;
			onProductKeyPressed(productKey);
		}
		else
		{
			// TODO: figure out when this code is invoked because in my tests it seems it's never invoked.
			final AbstractPackageDataPanel pickingData = getPickingData();
			pickingData.validateModel();
			pickingData.setReadOnly(true);
		}
	}

	private final void onPackingMaterialKeyPressed(final PackingMaterialKey packingMaterialKey)
	{
		//
		// Get selected picking slot
		final FreshSwingPackageItems productsKeyPanel = getProductKeysPanel();
		final PickingSlotKey pickingSlotKey = productsKeyPanel.getSelectedPickingSlotKey();
		if (pickingSlotKey == null)
		{
			throw new TerminalException(ERR_SELECT_PICKING_SLOT);
		}

		// NOTE: having a product selected is no longer necesary (fresh_08844)
		// final FreshProductKey productKey = productsKeyPanel.getSelectedProduct();
		// if (productKey == null)
		// {
		// throw new TerminalException(ERR_SELECT_PRODUCT);
		// }

		//
		// Create new HU
		pickingSlotKey.createHU(packingMaterialKey);

		// re-confirm the selection
		keyPressed(pickingSlotKey);
	}

	private final void onPickingSlotKeyPressed(final PickingSlotKey pickingSlotKey)
	{
		final FreshSwingPackageItems productsKeyPanel = getProductKeysPanel();
		productsKeyPanel.setSelectedPickingSlotKey(pickingSlotKey);
		productsKeyPanel.refreshProducts();

		//
		// Also reset the packing material keys because maybe not all are compatible with our picking slot
		getPackingMaterialsKeyLayout().resetKeys();

		// Trigger product selected event
		// => this will update the add/remove/qty etc buttons status
		onProductKeyPressed(productsKeyPanel.getSelectedProduct());
	}

	private final void onProductKeyPressed(final FreshProductKey productKey)
	{
		// Get selected picking slot
		final FreshSwingPackageItems productsKeyPanel = getProductKeysPanel();
		final PickingSlotKey pickingSlotKey = productsKeyPanel.getSelectedPickingSlotKey();

		// Button and Qty field statuses
		final boolean enableAddRemoveButtons;
		final boolean editableQtyField;
		final boolean enableHUEditorButton;

		//
		// No selected picking slot
		if (pickingSlotKey == null)
		{
			enableAddRemoveButtons = false;
			enableHUEditorButton = false;
			editableQtyField = false;
		}
		//
		// We have a selected picking slot
		else
		{
			// NOTE: guard against inconsistencies. i think we can safely remove this in future
			Check.assume(productKey == productsKeyPanel.getSelectedProduct(),
					"Invalid selected product key (current key pressed={}, current selected key={}",
					productKey, productsKeyPanel.getSelectedProduct());

			final boolean hasOpenHU = pickingSlotKey.hasOpenHU();
			final boolean hasSelectedProductKey = productKey != null;

			enableAddRemoveButtons = hasOpenHU && hasSelectedProductKey;
			enableHUEditorButton = !hasOpenHU && hasSelectedProductKey;
			editableQtyField = hasOpenHU && hasSelectedProductKey;

			//
			// Suggest how much Qty to use initially.
			// User will be able to change it.
			productsKeyPanel.setQty(new Supplier<BigDecimal>()
			{

				@Override
				public BigDecimal get()
				{
					return calculateQtyToSet(pickingSlotKey, productKey);
				}
			});
		}

		//
		// Update buttons and Qty fields statuses
		{
			productsKeyPanel.setEnableAddRemoveButtons(enableAddRemoveButtons);
			productsKeyPanel.setEnableHUEditorButton(enableHUEditorButton);
			productsKeyPanel.setEnableDistributeQtyToNewHUsButton();
			getPickingData().setEditable(editableQtyField); // Qty field
		}

		//
		// Reset packing material keys
		getPackingMaterialsKeyLayout().resetKeys();
	}

	/**
	 * Calculate the Qty to be used to initialize the Qty Field when user is pressing on a product key.
	 * 
	 * @param pickingSlotKey
	 * @param productKey
	 * @return qty to be used to initialize the Qty Field
	 */
	private final BigDecimal calculateQtyToSet(final PickingSlotKey pickingSlotKey, final FreshProductKey productKey)
	{
		if (productKey == null)
		{
			return BigDecimal.ZERO;
		}
		final BigDecimal qtyUnallocated = productKey.getQtyUnallocated(2);

		//
		// Case: we have ZERO qty to allocate
		if (qtyUnallocated.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		//
		// Case: our picking slot has a finite capacity defined
		// => use the capacity
		final IHUCapacityDefinition pickingSlotCapacity = pickingSlotKey.getHUTotalCapacity(productKey.getM_Product(), productKey.getQtyUnallocatedUOM());
		if (pickingSlotCapacity != null
				&& !pickingSlotCapacity.isInfiniteCapacity())
		{
			final BigDecimal qtyCapacity = pickingSlotCapacity.getCapacity();
			return qtyCapacity.min(qtyUnallocated); // qtyCapacity, but if it's bigger then our qty to allocate, then qty to allocate would be smart to set
		}

		//
		// Fallback: return the whole unallocated qty
		return qtyUnallocated;
	}

	@Override
	public FreshSwingPackageItems getProductKeysPanel()
	{
		return (FreshSwingPackageItems)super.getProductKeysPanel();
	}

	@Override
	public FreshPackingDetailsMd getModel()
	{
		return (FreshPackingDetailsMd)super.getModel();
	}
}
