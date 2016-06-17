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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.util.Env;

import de.metas.adempiere.form.AvailableBins;
import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.PackingDetailsMd;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.UsedBin;
import de.metas.picking.terminal.Utils;

/**
 * @author cg
 * 
 */
public class SwingPackageTerminal extends AbstractPackageTerminal
{
	public SwingPackageTerminal(final SwingPickingOKPanel pickingOKPanel, final PackingDetailsMd model)
	{
		super(pickingOKPanel, model);
		createBoxes(model);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void createBoxes(final Object model)
	{
		final PackingDetailsMd packingDetailsModel = (PackingDetailsMd)model;
		
		final PackingItemsMap packItems = new PackingItemsMap();
		final Map<Integer, DefaultMutableTreeNode> boxes = new HashMap<Integer, DefaultMutableTreeNode>();
		final List<DefaultMutableTreeNode> availBoxes = new ArrayList<DefaultMutableTreeNode>();

		final Enumeration<DefaultMutableTreeNode> enu = packingDetailsModel.getPackingTreeModel().getUsedBins().children();
		int boxNo = 1;
		while (enu.hasMoreElements())
		{
			DefaultMutableTreeNode currentChild = enu.nextElement();

			// get boxes
			Object userObj = currentChild.getUserObject();
			if (userObj instanceof UsedBin)
			{
				boxes.put(boxNo, currentChild);
			}

			// get packing items
			List<IPackingItem> itemList = new ArrayList<>();
			Enumeration<DefaultMutableTreeNode> enumProd = currentChild.children();
			while (enumProd.hasMoreElements())
			{
				DefaultMutableTreeNode child = enumProd.nextElement();

				// create products per box
				Object obj = child.getUserObject();
				if (obj instanceof LegacyPackingItem)
				{
					final LegacyPackingItem item = (LegacyPackingItem)obj;
					itemList.add(item);
				}
			}
			packItems.put(boxNo, itemList);
			boxNo++;
		}
		// put unpacked products
		final Enumeration<DefaultMutableTreeNode> unpacked = packingDetailsModel.getPackingTreeModel().getUnPackedItems().children();
		List<IPackingItem> itemList = new ArrayList<>();
		while (unpacked.hasMoreElements())
		{
			DefaultMutableTreeNode currentChild = unpacked.nextElement();
			Object obj = currentChild.getUserObject();
			if (obj instanceof LegacyPackingItem)
			{
				final LegacyPackingItem item = (LegacyPackingItem)obj;
				itemList.add(item);
			}
		}
		if (itemList != null && itemList.size() != 0)
		{
			packItems.put(PackingItemsMap.KEY_UnpackedItems, itemList);
		}

		// get available boxes
		final Enumeration<DefaultMutableTreeNode> available = packingDetailsModel.getPackingTreeModel().getAvailableBins().children();
		while (available.hasMoreElements())
		{
			DefaultMutableTreeNode currentChild = available.nextElement();

			Object userObj = currentChild.getUserObject();
			if (userObj instanceof AvailableBins)
			{
				availBoxes.add(currentChild);
			}
		}
		
		setPackingItems(packItems);
		setBoxes(boxes);
		setAvailableBoxes(availBoxes);
	}
	
	@Override
	public PackingDetailsMd getPackingDetailsModel()
	{
		return (PackingDetailsMd)super.getPackingDetailsModel();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public BigDecimal getQtyUnpacked(IPackingItem pck)
	{
		final PackingDetailsMd packingDetailsModel = getPackingDetailsModel();
		Enumeration<DefaultMutableTreeNode> unpacked = packingDetailsModel.getPackingTreeModel().getUnPackedItems().children();
		BigDecimal qty = Env.ZERO;
		while (unpacked.hasMoreElements())
		{
			DefaultMutableTreeNode currentChild = unpacked.nextElement();
			Object obj = currentChild.getUserObject();
			if (obj instanceof LegacyPackingItem)
			{
				final LegacyPackingItem item = (LegacyPackingItem)obj;
				if (pck.getProductId() == item.getProductId())
				{
					qty =  qty.add(item.getQtySum());
				}
			}
		}
		
		return qty;
	}
	
	@Override
	public AbstractPackageTerminalPanel createPackageTerminalPanel()
	{
		final SwingPackageTerminalPanel packageTerminalPanel = new SwingPackageTerminalPanel(getTerminalContext(), this);
		
		// we saving the tree and in this way we assure that only one user can see this specific tree
		// save only if is not groupped by product
		Utils.savePackingTree(packageTerminalPanel);
		
		packageTerminalPanel.getPickingData().validateModel();
		
		return packageTerminalPanel;
	}
}
