/**
 *
 */
package de.metas.fresh.picking.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.compiere.apps.form.FormFrame;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalPanel;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.fresh.picking.PackingDetailsModel;
import de.metas.fresh.picking.PackingMaterialKey;
import de.metas.fresh.picking.PackingMaterialLayout;
import de.metas.fresh.picking.PickingSlotKey;
import de.metas.fresh.picking.ProductKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.picking.service.PackingItemsMap;
import de.metas.quantity.Capacity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import net.miginfocom.swing.MigLayout;

/**
 * Packing window main panel (second window)
 */
public class SwingPackingTerminalPanel implements ITerminalPanel, ITerminalBasePanel
{
	public static SwingPackingTerminalPanel cast(final ITerminalBasePanel panel)
	{
		return (SwingPackingTerminalPanel)panel;
	}

	private static final Logger logger = LogManager.getLogger(SwingPackingTerminalPanel.class);

	private static final String ERR_SELECT_PICKING_SLOT = "@SelectPickingSlot@";
	// private static final String ERR_SELECT_PRODUCT = "@SelectProduct@";

	private final ITerminalContext tc;
	private final SwingPackingTerminal parentPackageTerminal;

	/**
	 * {@link ITerminalKeyListener} implementation which forwards all events to {@link #keyPressed(ITerminalKey)}.
	 */
	protected final ITerminalKeyListener terminalKeyListener2keyPressed = new TerminalKeyListenerAdapter()
	{
		@Override
		public void keyReturned(final ITerminalKey key)
		{
			keyPressed(key);
		}
	};

	private final IContainer panel;
	private final IContainer panelCenter;

	private FormFrame frame;
	private final ITerminalKeyPanel packingMaterialsPanel;
	private final SwingPickingSlotsPanel pickingSlotsPanel;
	private final SwingPackingTerminalToolbar toolbarPanel;

	private boolean disposing = false;
	private boolean disposed = false;

	public SwingPackingTerminalPanel(@NonNull final ITerminalContext terminalContext, final SwingPackingTerminal parent)
	{
		tc = terminalContext;
		final ITerminalFactory factory = tc.getTerminalFactory();

		parentPackageTerminal = parent;

		panel = factory.createContainer();
		panelCenter = factory.createContainer();

		// NOTE: init order is important because of our fucked-up architecture. This order was tested on only.
		pickingSlotsPanel = new SwingPickingSlotsPanel(this);
		toolbarPanel = new SwingPackingTerminalToolbar(this);
		packingMaterialsPanel = createPackingMaterialsKeyLayoutPanel();

		SwingTerminalFactory.getUI(panel).setLayout(new MigLayout("ins 5 5 5 5", // Layout Constraints
				"shrink 10, grow", // Column constraints
				"grow, shrink 10"));

		SwingTerminalFactory.getUI(panelCenter).setLayout(new MigLayout("ins 5 5 5 5", // Layout Constraints
				"[grow][shrink 10]", // Column constraints
				"[grow][shrink 10]"));

		terminalContext.addToDisposableComponents(this);
	}

	private final ITerminalKeyPanel createPackingMaterialsKeyLayoutPanel()
	{
		final PackingMaterialLayout packingMaterialsLayout = new PackingMaterialLayout(getTerminalContext());
		packingMaterialsLayout.setBasePanel(this);
		getTerminalContext().addToDisposableComponents(packingMaterialsLayout);

		final ITerminalKeyPanel packingMaterialsLayoutPanel = getTerminalFactory()
				.createTerminalKeyPanel(packingMaterialsLayout, terminalKeyListener2keyPressed);

		return packingMaterialsLayoutPanel;
	}

	/** @return Packing window (second window) */
	public SwingPackingTerminal getParent()
	{
		return parentPackageTerminal;
	}

	public PackingItemsMap getPackingItems()
	{
		return getParent().getPackingItems();
	}

	public void setPackingItems(final PackingItemsMap packingItems)
	{
		getParent().setPackingItems(packingItems);
	}

	private PackingDetailsModel getPackingDetailsModel()
	{
		return getParent().getPackingDetailsModel();
	}

	public ImmutableList<PickingSlotKey> getAvailablePickingSlots()
	{
		return getPackingDetailsModel().getAvailablePickingSlots();
	}

	public ImmutableList<PackingMaterialKey> getAvailablePackingMaterialKeys()
	{
		return getPackingDetailsModel().getAvailablePackingMaterialKeys();
	}

	private SwingPickingSlotsPanel getPickingSlotsPanel()
	{
		return pickingSlotsPanel;
	}

	public PickingSlotKey getSelectedPickingSlotKey()
	{
		final SwingPickingSlotsPanel pickingSlotsPanel = getPickingSlotsPanel();
		if (pickingSlotsPanel == null) // might be null during initialization
		{
			return null;
		}
		return pickingSlotsPanel.getSelectedPickingSlotKey();
	}

	private ITerminalKeyPanel getProductsKeyLayoutPanel()
	{
		final SwingPickingSlotsPanel pickingSlotsPanel = getPickingSlotsPanel();
		return pickingSlotsPanel.getProductsKeyLayoutPanel();
	}

	public ProductKey getSelectedProductKey()
	{
		return getPickingSlotsPanel().getSelectedProduct();
	}

	public List<ProductKey> getAllProductKeys()
	{
		return getPickingSlotsPanel().getAllProductKeys();
	}

	private IContainer getButtonsPanel()
	{
		return getPickingSlotsPanel().getButtonsPanel();
	}

	public ITerminalButton getCloseCurrentHUButton()
	{
		return getPickingSlotsPanel().getbCloseCurrentHU();
	}

	public void setQtyFieldReadOnly(final boolean readOnly)
	{
		getPickingSlotsPanel().setQtyFieldReadOnly(readOnly);
	}

	private ITerminalKeyPanel getPackingMaterialsPanel()
	{
		return packingMaterialsPanel;
	}

	private final PackingMaterialLayout getPackingMaterialsKeyLayout()
	{
		return PackingMaterialLayout.cast(getPackingMaterialsPanel().getKeyLayout());
	}

	public void init(final int windowNo, final FormFrame frame)
	{
		this.frame = frame;
		frame.setMinimumSize(new Dimension(1024, 760));
		frame.setMaximumSize(new Dimension(1024, 760));	// cg: maximum size should be 1024x768 : see task 03520
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setJMenuBar(null);

		frame.setTitle(Services.get(IMsgBL.class).getMsg(getTerminalContext().getCtx(), "PackageTerminal"));

		try
		{
			dynInit();
			frame.getContentPane().add(SwingTerminalFactory.getUI(panel), BorderLayout.CENTER);
		}
		catch (final Exception ex)
		{
			logger.warn("init", ex);
			dispose();
			return;
		}
	}

	private void dynInit()
	{
		// product data
		final ITerminalLabel productData = getTerminalFactory().createLabel(getParent().getPickingOKPanel().getLabelData(), false);
		productData.setFont(14f);

		SwingTerminalFactory.addChild(panelCenter, productData, "dock north, wrap, align left");
		SwingTerminalFactory.addChild(panelCenter, getToolbar(), "dock north, wrap");
		SwingTerminalFactory.addChild(panelCenter, getPackingMaterialsPanel(), "dock north, wrap, hmin 60");
		SwingTerminalFactory.addChild(panelCenter, getButtonsPanel(), "dock north, wrap");
		SwingTerminalFactory.addChild(panelCenter, getProductsKeyLayoutPanel(), "dock center, wrap");

		// add components in main panel
		add(getPickingSlotsPanel(), "dock east, w 45%");
		add(panelCenter, "dock center, push, w 55%");
	}

	private SwingPackingTerminalToolbar getToolbar()
	{
		return toolbarPanel;
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
		else if (key instanceof ProductKey)
		{
			final ProductKey productKey = (ProductKey)key;
			onProductKeyPressed(productKey);
		}
		else
		{
			// TODO: figure out when this code is invoked because in my tests it seems it's never invoked.
			final SwingPackingTerminalToolbar toolbar = getToolbar();
			toolbar.setReadOnly(true);
		}
	}

	private final void onPackingMaterialKeyPressed(final PackingMaterialKey packingMaterialKey)
	{
		//
		// Get selected picking slot
		final PickingSlotKey pickingSlotKey = getSelectedPickingSlotKey();
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
		final SwingPickingSlotsPanel pickingSlotsPanel = getPickingSlotsPanel();
		pickingSlotsPanel.setSelectedPickingSlotKey(pickingSlotKey);
		pickingSlotsPanel.refreshProducts();

		//
		// Also reset the packing material keys because maybe not all are compatible with our picking slot
		getPackingMaterialsKeyLayout().resetKeys();

		// Trigger product selected event
		// => this will update the add/remove/qty etc buttons status
		onProductKeyPressed(pickingSlotsPanel.getSelectedProduct());
	}

	private final void onProductKeyPressed(final ProductKey productKey)
	{
		// Get selected picking slot
		final PickingSlotKey pickingSlotKey = getSelectedPickingSlotKey();

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
			Check.assume(productKey == getSelectedProductKey(),
					"Invalid selected product key (current key pressed={}, current selected key={}",
					productKey, getSelectedProductKey());

			final boolean hasOpenHU = pickingSlotKey.hasOpenHU();
			final boolean hasSelectedProductKey = productKey != null;

			enableAddRemoveButtons = hasOpenHU && hasSelectedProductKey;
			enableHUEditorButton = !hasOpenHU && hasSelectedProductKey;
			editableQtyField = hasOpenHU && hasSelectedProductKey;

			//
			// Suggest how much Qty to use initially.
			// User will be able to change it.
			getPickingSlotsPanel().setQty(() -> calculateQtyToSet(pickingSlotKey, productKey));
		}

		//
		// Update buttons and Qty fields statuses
		{
			final SwingPickingSlotsPanel buttonsPanel = getPickingSlotsPanel();
			buttonsPanel.setEnableAddRemoveButtons(enableAddRemoveButtons);
			buttonsPanel.setEnableHUEditorButton(enableHUEditorButton);
			buttonsPanel.setEnableDistributeQtyToNewHUsButton();

			getToolbar().setEditable(editableQtyField); // Qty field
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
	private final BigDecimal calculateQtyToSet(final PickingSlotKey pickingSlotKey, final ProductKey productKey)
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
		final Capacity pickingSlotCapacity = pickingSlotKey.getHUTotalCapacity(productKey.getProductId(), productKey.getQtyUnallocatedUOM());
		if (pickingSlotCapacity != null
				&& !pickingSlotCapacity.isInfiniteCapacity())
		{
			final BigDecimal qtyCapacity = pickingSlotCapacity.toBigDecimal();
			return qtyCapacity.min(qtyUnallocated); // qtyCapacity, but if it's bigger then our qty to allocate, then qty to allocate would be smart to set
		}

		//
		// Fallback: return the whole unallocated qty
		return qtyUnallocated;
	}

	@Override
	public Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public void logout()
	{
		getParent().getPickingOKPanel().logout();
	}

	@Override
	public FormFrame getComponent()
	{
		return frame;
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return tc.getTerminalFactory();
	}

	@Override
	public void add(final IComponent component, final Object constraints)
	{
		SwingTerminalFactory.addChild(panel, component, constraints);
	}

	@Override
	public void addAfter(final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		SwingTerminalFactory.addChildAfter(panel, component, componentBefore, constraints);
	}

	@Override
	public void remove(final IComponent component)
	{
		SwingTerminalFactory.removeChild(panel, component);
	}

	@Override
	public void removeAll()
	{
		panel.removeAll();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	@Override
	public final boolean isDisposed()
	{
		return disposing || disposed;
	}

	@Override
	public final void dispose()
	{
		if (disposing || disposed)
		{
			return;
		}

		disposing = true;
		try
		{
			if (frame != null)
			{
				frame.dispose();
				frame = null;
			}
		}
		finally
		{
			disposing = false;
			disposed = true;
		}
	}
}
