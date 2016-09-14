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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import de.metas.adempiere.form.IPackingDetailsModel;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.PackingTreeModel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalTree;
import de.metas.adempiere.form.terminal.TerminalKeyPanel;
import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.picking.terminal.BoxKey;
import de.metas.picking.terminal.BoxLayout;
import de.metas.picking.terminal.ProductKey;
import de.metas.picking.terminal.ProductLayout;
import de.metas.picking.terminal.Utils.PackingStates;


/**
 * @author cg
 *
 */

public class QtyListener implements PropertyChangeListener
{
	/**
	 * 
	 */
	private final SwingPackageBoxesItems swingPackageBoxesItems;

	/**
	 * @param swingPackageBoxesItems
	 */
	public QtyListener(final SwingPackageBoxesItems swingPackageBoxesItems)
	{
		super();
		this.swingPackageBoxesItems = swingPackageBoxesItems;
	}
	
	public SwingPackageBoxesItems getPackageBoxesItems()
	{
		return swingPackageBoxesItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		//
		// NOTE: Implementing classes do NOT necessarily call super.propertyChange()!!!
		// Please do not assume that customer-specific overrides will call it
		//
		
		// If new value is null or value not changed, we keep the old package and do nothing
		if (evt.getNewValue() == null || evt.getNewValue().equals(evt.getOldValue()) || BigDecimal.ZERO.equals(evt.getOldValue()))
		{
			return;
		}
		//
		final IPackingDetailsModel model = getPackageTerminalPanel().getModel();

		BoxKey selected = (BoxKey)((TerminalKeyPanel)this.swingPackageBoxesItems.getPickingSlotsKeyLayoutPanel()).getSelectedKey();

		if (selected != null)
		{
			this.swingPackageBoxesItems.oldUsedBin = selected.getNode();
		}
		else
		{
			return;
		}
		
		final boolean deleteBin;
		if (PackingStates.open.name().equals(selected.getStatus().getName()) || PackingStates.closed.name().equals(selected.getStatus().getName()))
		{
			deleteBin = false;
		}
		else
		{
			deleteBin = true;
		}
		BigDecimal oldQty = (BigDecimal)evt.getOldValue();
		// if the user is using numpad, then the old value is null and also the all algorithm changes
		boolean isUsingNumPad = false;
		ProductKey selectedProduct = (ProductKey)((TerminalKeyPanel)this.swingPackageBoxesItems.getProductsKeyLayoutPanel()).getSelectedKey();
		if (oldQty == null)
		{
			oldQty = selectedProduct.getQty();
			isUsingNumPad = true;
		}
		BigDecimal newQty = null;
		if (evt.getNewValue() instanceof Long)
			newQty = new BigDecimal((Long)evt.getNewValue());
		else
			newQty = (BigDecimal)evt.getNewValue();
		BigDecimal qty = oldQty.subtract(newQty);
		// cg: task 03006 : we need to restrict the quantity the user can input
		BigDecimal unpackedQty = getPackageTerminalPanel().getParent().getQtyUnpacked(selectedProduct.getPackingItem());

		final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
		BigDecimal packedQty = packingTreeModel.getQtyPackingItem(selectedProduct.getPackingItem(), this.swingPackageBoxesItems.oldUsedBin);

		DefaultMutableTreeNode node = null;
		if (true == isUsingNumPad) // logic for when the user uses numpad
		{
			if (this.swingPackageBoxesItems.checkMaxQty(packedQty, unpackedQty, newQty, qty, selectedProduct.getBoxNo() != PackingItemsMap.KEY_UnpackedItems))
			{
				this.swingPackageBoxesItems.warn("Max.Qty");
				return;
			}
			// case when the selected product is from the outside of the box
			else if (selectedProduct.getBoxNo() == PackingItemsMap.KEY_UnpackedItems)
			{

				// case when we pop into the box
				if (qty.signum() > 0)
				{
					Enumeration<DefaultMutableTreeNode> en = packingTreeModel.getUnPackedItems().children();
					searchNode: while (en.hasMoreElements())
					{
						DefaultMutableTreeNode currentChild = en.nextElement();
						Object userObj = currentChild.getUserObject();
						if (userObj instanceof LegacyPackingItem)
						{
							LegacyPackingItem pack = (LegacyPackingItem)userObj;
							if (this.swingPackageBoxesItems.getPck() == pack && this.swingPackageBoxesItems.getPck().getProductId() == pack.getProductId())
							{
								this.swingPackageBoxesItems.setPck(pack);
								node = currentChild;
								break searchNode;
							}
						}
					}
					//
					packingTreeModel.movePackItem(node, this.swingPackageBoxesItems.oldUsedBin, qty);
				}
				// case when we pop out from the box
				else
				{
					node = packingTreeModel.findPackingItemNode(this.swingPackageBoxesItems.oldUsedBin, this.swingPackageBoxesItems.getPck());
					packingTreeModel.removePackedItem(Env.getCtx(), node, this.swingPackageBoxesItems.oldUsedBin, qty.abs(), deleteBin);
					if (packedQty.equals(qty.abs()))
						packedQty = BigDecimal.ZERO;
				}

			}
			// case when the selected product is the one from the box
			else
			{
				// the case when we put all the products into the box
				if (newQty.abs().compareTo(unpackedQty.add(packedQty)) == 0)
				{
					Enumeration<DefaultMutableTreeNode> en = packingTreeModel.getUnPackedItems().children();
					searchNode: while (en.hasMoreElements())
					{
						DefaultMutableTreeNode currentChild = en.nextElement();
						Object userObj = currentChild.getUserObject();
						if (userObj instanceof LegacyPackingItem)
						{
							LegacyPackingItem pack = (LegacyPackingItem)userObj;
							if (this.swingPackageBoxesItems.getPck().getProductId() == pack.getProductId())
							{
								this.swingPackageBoxesItems.setPck(pack);
								node = currentChild;
								break searchNode;
							}
						}
					}
					packingTreeModel.movePackItem(node, this.swingPackageBoxesItems.oldUsedBin, qty.abs());
				}
				else
				{
					node = packingTreeModel.findPackingItemNode(this.swingPackageBoxesItems.oldUsedBin, this.swingPackageBoxesItems.getPck());
					packingTreeModel.removePackedItem(Env.getCtx(), node, this.swingPackageBoxesItems.oldUsedBin, qty, deleteBin);
				}
			}
		}
		// case of using buttons
		else
		{
			// case when we pop into the box
			if (qty.signum() < 0)
			{
				Enumeration<DefaultMutableTreeNode> en = packingTreeModel.getUnPackedItems().children();
				searchNode: while (en.hasMoreElements())
				{
					DefaultMutableTreeNode currentChild = en.nextElement();
					Object userObj = currentChild.getUserObject();
					if (userObj instanceof LegacyPackingItem)
					{
						LegacyPackingItem pack = (LegacyPackingItem)userObj;
						if (this.swingPackageBoxesItems.getPck() == pack && this.swingPackageBoxesItems.getPck().getProductId() == pack.getProductId())
						{
							this.swingPackageBoxesItems.setPck(pack);
							node = currentChild;
							break searchNode;
						}
					}
				}
				packingTreeModel.movePackItem(node, this.swingPackageBoxesItems.oldUsedBin, qty.negate());
			}

			// case when we pop out from the box
			else
			{
				node = packingTreeModel.findPackingItemNode(this.swingPackageBoxesItems.oldUsedBin, this.swingPackageBoxesItems.getPck());
				packingTreeModel.removePackedItem(Env.getCtx(), node, this.swingPackageBoxesItems.oldUsedBin, qty, deleteBin);
			}
		}

		//
		packingTreeModel.reload();
		getPackageTerminalPanel().updatePackingItemPanel(this.swingPackageBoxesItems.getPck(), this.swingPackageBoxesItems.oldUsedBin, true);
		getTree().expandTree(true);
		// block datapanel
		boolean resetBoxes = false;
		if (newQty.signum() == 0 || (qty.signum() < 0 && newQty.compareTo(BigDecimal.valueOf(2)) == 0) || (packedQty.signum() == 0 && newQty.signum() < 0))
		{
			((SwingPackageDataPanel)getPackageTerminalPanel().getPickingData()).setDataValues("", "0", "0", "0", false, "");
			getPackageTerminalPanel().getPickingData().setReadOnly(true);
			resetBoxes = true;
			//
			ProductLayout prodLayout = this.swingPackageBoxesItems.getProductsKeyLayout();
			prodLayout.setSelectedBox(null);
			getPackageTerminalPanel().refresh(false, resetBoxes, true);
		}
		else
		{
			//
			getPackageTerminalPanel().refresh(false, resetBoxes, true);
			// get selected key
			TerminalKeyPanel keypanel = (TerminalKeyPanel)this.swingPackageBoxesItems.getProductsKeyLayoutPanel();
			ProductKey pkey = this.swingPackageBoxesItems.getSelectedProduct();
			for (ITerminalKey key : keypanel.getKeys())
			{
				if (key instanceof ProductKey)
				{
					ProductKey productKey = (ProductKey)key;
					if (pkey != null && pkey.getBoxNo() == productKey.getBoxNo() && pkey.getValue().equals(productKey.getValue()))
					{
						keypanel.onKeySelected(productKey);
						final IPackingItem pi = productKey.getPackingItem();
						final I_M_Product product = pi.getM_Product();

						final BigDecimal productWeight = product.getWeight();
						// if the product has no weight info, make the field editable
						final boolean productHasNoWeightInfo = productWeight == null || productWeight.signum() == 0;
						final I_C_OrderLine ol = InterfaceWrapperHelper.create(pi.getShipmentSchedules().iterator().next().getC_OrderLine(), I_C_OrderLine.class);
						String desc;
						if (ol.isIndividualDescription())
						{
							desc = ol.getProductDescription();
						}
						else
						{
							desc = "";
						}
						((SwingPackageDataPanel)getPackageTerminalPanel().getPickingData()).setDataValues(product.getValue() + " (" + product.getName() + ")",
								pi.getQtySum().toString(),
								pi.getQtySum().multiply(product.getVolume()).toString(),
								pi.computeWeight().toString(),
								productHasNoWeightInfo,
								desc);
					}
				}
			}
		}

		// get selected key
		TerminalKeyPanel keypanel = (TerminalKeyPanel)this.swingPackageBoxesItems.getPickingSlotsKeyLayoutPanel();
		if (keypanel.getKeys() != null)
		{
			for (ITerminalKey key : keypanel.getKeys())
			{
				if (key instanceof BoxKey)
				{
					BoxKey boxKey = (BoxKey)key;
					if (resetBoxes)
						continue;
					if (selected.getBoxNo() == boxKey.getBoxNo() && selected.getValue().equals(boxKey.getValue()))
					{
						keypanel.onKeySelected(boxKey);
					}
				}
			}
		}
		((BoxLayout)this.swingPackageBoxesItems.getPickingSlotsKeyLayout()).checkKeyState();
		getPackageTerminalPanel().getPickingData().getbSave().setEnabled(false);
	}
	
	private SwingPackageTerminalPanel getPackageTerminalPanel()
	{
		return (SwingPackageTerminalPanel)this.swingPackageBoxesItems.getPackageTerminalPanel();
	}
	
	private ITerminalTree getTree()
	{
		final SwingPackageTerminalPanel packingPanel = getPackageTerminalPanel();
		return packingPanel.getTree();
	}
}
