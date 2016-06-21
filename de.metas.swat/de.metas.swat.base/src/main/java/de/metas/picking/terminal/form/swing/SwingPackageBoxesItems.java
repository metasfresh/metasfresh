/**
 * 
 */
package de.metas.picking.terminal.form.swing;

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


import java.awt.Event;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.base.Supplier;

import de.metas.adempiere.form.PackingDetailsMd;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.PackingTreeModel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.ITerminalTree;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.TerminalKeyPanel;
import de.metas.adempiere.form.terminal.swing.TerminalSubPanel;
import de.metas.picking.terminal.BoxKey;
import de.metas.picking.terminal.BoxLayout;
import de.metas.picking.terminal.ProductKey;
import de.metas.picking.terminal.ProductLayout;
import de.metas.picking.terminal.Utils;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * Contains:
 * <ul>
 * <li>the product's quantity field which will be used to specify how much we will need to pack
 * <li>Action buttons: Pack qty (Hinzuf), Unpack qty etc
 * <li>product keys layout: {@link #getProductsKeyLayout()}
 * <li>picking slot keys layout: {@link #getPickingSlotsKeyLayout()}
 * 
 * @author cg
 * 
 */
public class SwingPackageBoxesItems extends TerminalSubPanel implements PropertyChangeListener
{
	protected static final String ERR_NO_PRODUCT_SELECTED = "@NoProductSelected@";
	protected static final String ERR_NO_BOX_SELECTED = "@NoBoxSelected@";

	private static final String ERR_CLOSED_PACKAGE = "@ClosedPackage@";
	private static final String ERR_UNPACKED_PRODUCT = "@UnpackedProduct@";
	private static final String ERR_PACKED_PRODUCT = "@PackedProduct@";

	private ITerminalNumericField fQty;
	private IContainer buttonsPanel;
	private ITerminalButton bAdd;
	private ITerminalButton bRemove;
	private ITerminalButton bAddAll;
	private ITerminalButton bRemoveAll;

	public static final String ACTION_Add = "Pack_Add";
	public static final String ACTION_AddAll = "Pack_AddAll";
	public static final String ACTION_Remove = "Pack_Remove";
	public static final String ACTION_RemoveAll = "Pack_RemoveAll";

	private ITerminalKeyPanel productsKeyLayoutPanel;
	private ITerminalKeyPanel pickingSlotsKeyLayoutPanel;

	private IKeyLayout pickingSlotsKeyLayout;
	private ProductLayout productsKeyLayout;

	public SwingPackageBoxesItems(final AbstractPackageTerminalPanel basePanel)
	{
		super(basePanel);
	}

	/**
	 * @return Packing window main panel (second window)
	 */
	public AbstractPackageTerminalPanel getPackageTerminalPanel()
	{
		return (AbstractPackageTerminalPanel)super.getTerminalBasePanel();
	}

	/**
	 * 
	 * @return button size constraints; never return null
	 */
	protected String getButtonSize()
	{
		return Utils.getButtonSize();
	}

	/**
	 * @return currently selected ProductKey or null
	 */
	public ProductKey getSelectedProduct()
	{
		final ITerminalKeyPanel productsKeyLayoutPanel = getProductsKeyLayoutPanel();
		if (productsKeyLayoutPanel == null)
		{
			return null;
		}

		final ProductKey selectedProductKey = (ProductKey)productsKeyLayoutPanel.getSelectedKey();
		return selectedProductKey;
	}

	private class ProductsKeyListener extends TerminalKeyListenerAdapter
	{

		@SuppressWarnings("deprecation")
		@Override
		public void keyReturned(final ITerminalKey key)
		{
			getTerminalBasePanel().keyPressed(key);
		}

	}

	private class PickingSlotsKeyListener extends TerminalKeyListenerAdapter
	{

		@SuppressWarnings("deprecation")
		@Override
		public void keyReturned(final ITerminalKey key)
		{
			getTerminalBasePanel().keyPressed(key);
		}

	}

	/**
	 * @return buttons panel which contains actions like Pack(Hinzuf.) etc.
	 */
	public final IContainer getButtonsPanel()
	{
		return buttonsPanel;
	}

	protected final ITerminalButton getButtonAdd()
	{
		return bAdd;
	}

	public final ITerminalKeyPanel getProductsKeyLayoutPanel()
	{
		return productsKeyLayoutPanel;
	}

	public final ITerminalKeyPanel getPickingSlotsKeyLayoutPanel()
	{
		return pickingSlotsKeyLayoutPanel;
	}

	public IKeyLayout getPickingSlotsKeyLayout()
	{
		return pickingSlotsKeyLayout;
	}

	public ProductLayout getProductsKeyLayout()
	{
		return productsKeyLayout;
	}

	protected QtyListener createQtyListener()
	{
		return new QtyListener(this);
	}

	@Override
	protected final void init()
	{
		initComponents();
		initLayout();
	}

	protected void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		//
		// Buttons from buttons panel
		{
			// Qty
			fQty = factory.createTerminalNumericField("", DisplayType.Quantity, 14f, true, false, getButtonSize());
			fQty.setEditable(true);
			final QtyListener listener = createQtyListener();
			fQty.addListener(listener);

			// Add
			bAdd = createButtonAction(SwingPackageBoxesItems.ACTION_Add, KeyStroke.getKeyStroke(KeyEvent.VK_F11, Event.F11), 17f);
			bAdd.setEnabled(false);
			bAdd.addListener(this);

			// Remove
			bRemove = createButtonAction(SwingPackageBoxesItems.ACTION_Remove, KeyStroke.getKeyStroke(KeyEvent.VK_F13, Event.F3), 17f);
			bRemove.setEnabled(false);
			bRemove.addListener(this);

			// Add All / Remove All
			createAddRemoveAll();
		}
		//
		// Buttons Panel (Add, Add All, Remove, Remove All)
		{
			buttonsPanel = getTerminalFactory().createContainer();
			if (bAddAll != null)
			{
				buttonsPanel.add(bAddAll, getButtonSize());
			}
			buttonsPanel.add(bAdd, getButtonSize());
			buttonsPanel.add(fQty, "");
			buttonsPanel.add(bRemove, getButtonSize());
			if (bRemoveAll != null)
			{
				buttonsPanel.add(bRemoveAll, getButtonSize() + ", wrap");
			}
		}

		//
		// Products Key Layout
		{
			productsKeyLayout = createProductsKeyLayout();
			productsKeyLayout.setBasePanel(p_basePanel);
			productsKeyLayout.setRows(2);
			productsKeyLayout.setSelectedBox(null);

			productsKeyLayoutPanel = factory.createTerminalKeyPanel(productsKeyLayout, new ProductsKeyListener());
			// productsKeyLayoutPanel.setAllowKeySelection(true); // NOTE: not needed, it's supposed to be already configured on productsKeyLayout
		}

		//
		// Picking Slot Key Layout
		{
			pickingSlotsKeyLayout = createPickingSlotsKeyLayout();
			pickingSlotsKeyLayoutPanel = factory.createTerminalKeyPanel(pickingSlotsKeyLayout, new PickingSlotsKeyListener());
			pickingSlotsKeyLayoutPanel.setAllowKeySelection(true);
		}

	}

	protected void initLayout()
	{
		add(getPickingSlotsKeyLayoutPanel(), "dock north, hmin 45%, hmax 45%");

		// add buttons
		add(buttonsPanel, "dock center, hmin 10%, hmax 10%");

		// add products panel
		add(productsKeyLayoutPanel, "dock south, hmin 35%, hmax 35%, gaptop 5");
	}

	protected void createAddRemoveAll()
	{
		bAddAll = createButtonAction(SwingPackageBoxesItems.ACTION_AddAll, KeyStroke.getKeyStroke(KeyEvent.VK_F12, Event.F12), 0f);
		bAddAll.setEnabled(false);
		bAddAll.addListener(this);

		bRemoveAll = createButtonAction(SwingPackageBoxesItems.ACTION_RemoveAll, KeyStroke.getKeyStroke(KeyEvent.VK_F14, Event.F7), 0f);
		bRemoveAll.setEnabled(false);
		bRemoveAll.addListener(this);
	}

	protected ProductLayout createProductsKeyLayout()
	{
		return new ProductLayout(getTerminalContext());
	}

	protected IKeyLayout createPickingSlotsKeyLayout()
	{
		final IKeyLayout boxLayout = new BoxLayout(getTerminalContext());
		boxLayout.setRows(2);
		boxLayout.setBasePanel(getTerminalBasePanel());
		return boxLayout;
	}

	public void refresh(final boolean resetProducts, final boolean resetBoxes)
	{
		final AbstractPackageTerminalPanel terminalPanel = getPackageTerminalPanel();
		final BoxLayout boxLayout = (BoxLayout)getPickingSlotsKeyLayout();

		if (resetBoxes)
		{
			boxLayout.resetKeys();
			final ArrayList<DefaultMutableTreeNode> lockedNodes = ((PackingDetailsMd)terminalPanel.getModel()).getPackingTreeModel().fetchLockedNodes();
			if (!lockedNodes.isEmpty())
			{
				for (final DefaultMutableTreeNode node : lockedNodes)
				{
					final BoxKey boxKey = boxLayout.getBoxKey(node);
					boxKey.setStatus(boxKey.updateBoxStatus(PackingStates.ready));
					boxKey.setReady(true);
				}
			}

			final ITerminalKeyPanel boxKeyPanel = getPickingSlotsKeyLayoutPanel();
			boxKeyPanel.removeLayout(boxLayout.getId());
			boxKeyPanel.addPOSKeyLayout(boxLayout);
			boxKeyPanel.showPOSKeyKayout(boxLayout);
			boxKeyPanel.onKeySelected(null);

			getProductsKeyLayout().setSelectedBox(null);
			terminalPanel.getPickingData().getbDelete().setEnabled(false);
			terminalPanel.getPickingData().getbClose().setEnabled(false);
		}
		boxLayout.checkKeyState();

		if (resetProducts)
		{
			final ProductLayout prodLayout = getProductsKeyLayout();
			final boolean boxReady;
			final boolean boxClosed;
			if (((TerminalKeyPanel)getPickingSlotsKeyLayoutPanel()).getSelectedKey() == null)
			{
				boxReady = true;
				boxClosed = false;
			}
			else
			{
				final BoxKey bk = (BoxKey)((TerminalKeyPanel)getPickingSlotsKeyLayoutPanel()).getSelectedKey();
				boxReady = bk.isReady();
				boxClosed = bk.getStatus().getName().equals(PackingStates.closed.name());
			}
			//
			prodLayout.resetKeys();
			getProductsKeyLayoutPanel().removeLayout(prodLayout.getId());
			//
			prodLayout.setEnabledKeys(!boxReady);
			prodLayout.setEnabledKeys(!boxClosed); // if the box is closed, no product can be add it; so this flag is the most important
			//
			getProductsKeyLayoutPanel().addPOSKeyLayout(prodLayout);
			getProductsKeyLayoutPanel().showPOSKeyKayout(prodLayout);
		}
	}

	public final void setEnableAddRemoveButtons(final boolean enabled)
	{
		setEnableAddButton(enabled);
		setEnableRemoveButton(enabled);
	}

	public final void setEnableAddButton(final boolean enabled)
	{
		bAdd.setEnabled(enabled);
		if (bAddAll != null)
		{
			bAddAll.setEnabled(enabled);
		}
	}

	public final void setEnableRemoveButton(final boolean enabled)
	{
		bRemove.setEnabled(enabled);
		if (bRemoveAll != null)
		{
			bRemoveAll.setEnabled(enabled);
		}
	}

	/**
	 * Method called when user presses {@link #bAdd}, {@link #bAddAll}, {@link #bRemove}, {@link #bRemoveAll} buttons.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		//
		// NOTE: Implementing classes do NOT necessarily call super.propertyChange()!!!
		// Please do not assume that customer-specific overrides will call it
		//

		final BoxKey selected = (BoxKey)getPickingSlotsKeyLayoutPanel().getSelectedKey();
		final ProductKey selectedProduct = getSelectedProduct();

		if (selected == null)
		{
			warn(SwingPackageBoxesItems.ERR_NO_BOX_SELECTED);
			return;
		}

		if (PackingStates.closed.name().equals(selected.getStatus().getName()))
		{
			warn(SwingPackageBoxesItems.ERR_CLOSED_PACKAGE);
			return;
		}

		final boolean deleteBin;
		if (PackingStates.open.name().equals(selected.getStatus().getName()))
		{
			deleteBin = false;
		}
		else
		{
			deleteBin = true;
		}
		oldUsedBin = selected.getNode();

		final AbstractPackageTerminalPanel terminalPanel = getPackageTerminalPanel();
		final PackingDetailsMd model = (PackingDetailsMd)terminalPanel.getModel();
		final PackingTreeModel packingTreeModel = model.getPackingTreeModel();

		if (SwingPackageBoxesItems.ACTION_Add.equals(evt.getNewValue()))
		{
			if (selectedProduct == null)
			{
				warn(SwingPackageBoxesItems.ERR_NO_PRODUCT_SELECTED);
				return;
			}
			else if (selectedProduct.getBoxNo() != PackingItemsMap.KEY_UnpackedItems)
			{
				warn(SwingPackageBoxesItems.ERR_UNPACKED_PRODUCT);
				return;
			}
			else
			{
				final Enumeration<DefaultMutableTreeNode> en = packingTreeModel.getUnPackedItems().children();
				DefaultMutableTreeNode node = null;
				searchNode: while (en.hasMoreElements())
				{
					final DefaultMutableTreeNode currentChild = en.nextElement();
					final Object userObj = currentChild.getUserObject();
					if (userObj instanceof LegacyPackingItem)
					{
						final LegacyPackingItem pack = (LegacyPackingItem)userObj;
						if (pck == pack && pck.getProductId() == pack.getProductId())
						{
							pck = pack;
							node = currentChild;
							break searchNode;
						}
					}
				}
				packingTreeModel.movePackItem(node, oldUsedBin, selectedProduct.getQty());
			}
		}
		else if (SwingPackageBoxesItems.ACTION_Remove.equals(evt.getNewValue()))
		{
			if (selectedProduct == null)
			{
				warn(SwingPackageBoxesItems.ERR_NO_PRODUCT_SELECTED);
				return;
			}
			else if (selectedProduct.getBoxNo() == PackingItemsMap.KEY_UnpackedItems)
			{
				warn(SwingPackageBoxesItems.ERR_PACKED_PRODUCT);
				return;
			}
			else
			{
				final DefaultMutableTreeNode node = packingTreeModel.findPackingItemNode(oldUsedBin, pck);
				packingTreeModel.removePackedItem(Env.getCtx(), node, oldUsedBin, selectedProduct.getQty(), deleteBin);
				((SwingPackageTerminalPanel)terminalPanel).refresh(false, true, true);
			}
		}
		else if (SwingPackageBoxesItems.ACTION_AddAll.equals(evt.getNewValue()))
		{
			final Enumeration<DefaultMutableTreeNode> en = packingTreeModel.getUnPackedItems().children();
			final List<DefaultMutableTreeNode> listNodes = new ArrayList<DefaultMutableTreeNode>();
			while (en.hasMoreElements())
			{
				final DefaultMutableTreeNode currentChild = en.nextElement();
				final Object userObj = currentChild.getUserObject();
				if (userObj instanceof LegacyPackingItem)
				{
					listNodes.add(currentChild);
				}
			}

			for (final DefaultMutableTreeNode node : listNodes)
			{
				final LegacyPackingItem pack = (LegacyPackingItem)node.getUserObject();
				packingTreeModel.movePackItem(node, oldUsedBin, pack.getQtySum());
			}
		}
		else if (SwingPackageBoxesItems.ACTION_RemoveAll.equals(evt.getNewValue()))
		{
			final Enumeration<DefaultMutableTreeNode> en = oldUsedBin.breadthFirstEnumeration();
			final List<DefaultMutableTreeNode> listNodes = new ArrayList<DefaultMutableTreeNode>();
			while (en.hasMoreElements())
			{
				final DefaultMutableTreeNode currentChild = en.nextElement();
				final Object userObj = currentChild.getUserObject();
				if (userObj instanceof LegacyPackingItem)
				{
					listNodes.add(currentChild);
				}
			}

			for (final DefaultMutableTreeNode node : listNodes)
			{
				final LegacyPackingItem pack = (LegacyPackingItem)node.getUserObject();
				packingTreeModel.removePackedItem(Env.getCtx(), node, oldUsedBin, pack.getQtySum(), deleteBin);
			}
			terminalPanel.refresh(false, true, true);
		}
		packingTreeModel.reload();
		getTree().expandTree(true);
		terminalPanel.keyPressed(selected);
	}

	private ITerminalTree getTree()
	{
		final AbstractPackageTerminalPanel terminalPanel = getPackageTerminalPanel();
		final SwingPackageTerminalPanel packingPanel = (SwingPackageTerminalPanel)terminalPanel;
		return packingPanel.getTree();
	}

	/**
	 * Sets qty field from given qty string.
	 * 
	 * NOTE: this method
	 * <ul>
	 * <li>will set the quantity only if the string is not null
	 * <li>will not fire change events
	 * </ul>
	 * 
	 * @param qty
	 */
	public final void setQtyData(final String qty)
	{
		if (qty != null)
		{
			BigDecimal bQty = new BigDecimal(qty);
			if (bQty.scale() != 0)
			{
				bQty = bQty.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			final ITerminalNumericField qtyField = getQtyField();
			qtyField.setValue(bQty, false);
		}
	}

	/**
	 * Sets qty field using the value provided by given supplier.
	 * 
	 * In case supplier fails, ZERO will be set and the exception will be propagated.
	 * 
	 * @param qtySupplier
	 */
	public final void setQty(final Supplier<BigDecimal> qtySupplier)
	{
		boolean qtySet = false;
		try
		{
			final BigDecimal qty = qtySupplier.get();
			setQty(qty);
			qtySet = true;
		}
		finally
		{
			if (!qtySet)
			{
				setQty(BigDecimal.ZERO);
			}
		}
	}

	/**
	 * Sets given quantity to quantity field.
	 * 
	 * NOTE: it will also fire the change event.
	 * 
	 * @param qty
	 */
	public final void setQty(final BigDecimal qty)
	{
		getQtyField().setValue(qty);
	}

	/**
	 * @return current quantity from quantity field
	 */
	public final BigDecimal getQty()
	{
		return getQtyField().getValue();
	}

	/**
	 * Sets the entire qty field (actual number field, plus button, minus button) to be read-only or editable.
	 * @param readOnly
	 * @see #setQtyFieldReadOnly(boolean, boolean, boolean)
	 */
	public final void setQtyFieldReadOnly(final boolean readOnly)
	{
		getQtyField().setEditable(!readOnly);
	}
	
	/**
	 * Sets the qty field components to be readonly or not
	 * 
	 * @param plus set read-only state for plus button
	 * @param minus set read-only state for minus button
	 * @param field set read-only state for field
	 */
	public void setQtyFieldReadOnly(final boolean plus, final boolean minus, final boolean field)
	{
		final ITerminalNumericField qtyField = getQtyField();
		qtyField.setMinusRO(minus);
		qtyField.setPlusRO(plus);
		qtyField.setNumberRO(field);
	}


	/**
	 * @return the quantity field; never returns null
	 */
	private final ITerminalNumericField getQtyField()
	{
		return fQty;
	}

	private IPackingItem pck = null;

	public final IPackingItem getPck()
	{
		return pck;
	}

	public final void setPck(final IPackingItem pck)
	{
		this.pck = pck;
	}

	DefaultMutableTreeNode oldUsedBin;

	public DefaultMutableTreeNode getOldUsedBin()
	{
		return oldUsedBin;
	}

	public void setOldUsedBin(final DefaultMutableTreeNode oldUsedBin)
	{
		this.oldUsedBin = oldUsedBin;
	}

	/**
	 * Check maximum quantity permitted into number pad
	 * 
	 * @param packedQty - quantity packed from that item
	 * @param unpackedQty - quantity unpacked from that item
	 * @param newQty - quantity entered
	 * @param qty - moved quantity
	 * @param insideBox
	 */
	boolean checkMaxQty(final BigDecimal packedQty, final BigDecimal unpackedQty, final BigDecimal newQty, final BigDecimal qty, final boolean insideBox)
	{
		boolean warn = false;
		if (insideBox)
		{
			if (qty.signum() > 0 && packedQty.compareTo(newQty) < 0)
			{
				warn = true;
			}
			else if (qty.signum() < 0 && unpackedQty.compareTo(newQty.subtract(packedQty)) < 0)
			{
				// the box more than we
				// have
				// available
				warn = true;
			}
		}
		else
		{
			if (qty.signum() < 0 && packedQty.add(unpackedQty).compareTo(newQty) < 0)
			{
				// box more than exists
				warn = true;
			}
		}

		return warn;
	}

	public void warn(final String message)
	{
		getTerminalFactory().showWarning(this, ITerminalFactory.TITLE_WARN, new TerminalException(message));
	}
}
