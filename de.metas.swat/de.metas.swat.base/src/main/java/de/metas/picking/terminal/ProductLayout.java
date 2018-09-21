/**
 * 
 */
package de.metas.picking.terminal;

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


import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModelAware;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.legacy.form.LegacyPackingItem;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.PackingItemsMapKey;
import de.metas.picking.terminal.Utils.PackingStates;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;

/**
 * @author cg
 * 
 */
public class ProductLayout extends KeyLayout implements IKeyLayoutSelectionModelAware
{

	public static final Color DEFAULT_Color = Color.GREEN;

	private BoxKey selectedBox = null;

	public ProductLayout(final ITerminalContext tc)
	{
		super(tc);
		setColumns(3);
		setDefaultColor(DEFAULT_Color);
	}

	public BoxKey getSelectedBox()
	{
		return selectedBox;
	}

	public void setSelectedBox(BoxKey selectedKey)
	{
		this.selectedBox = selectedKey;
	}

	@Override
	public String getId()
	{
		return "ProductLayout#" + 100;
	}

	public AbstractPackageTerminalPanel getPackageTerminalPanel()
	{
		return ((AbstractPackageTerminalPanel)getBasePanel());
	}

	@Override
	public List<ITerminalKey> createKeys()
	{
		// TODO: get rid of this fucked up, legacy code (if is not needed on )
		
		final PackingItemsMap map = getPackageTerminalPanel().getPackItems();
		final Map<Integer, DefaultMutableTreeNode> boxes = getPackageTerminalPanel().getBoxes();
		final List<ITerminalKey> list = new ArrayList<>();

		final boolean isSelectedBox = getSelectedBox() != null;

		// get objects from box 0: means unpacked items
		final List<IPackingItem> unpacked = map.getUnpackedItems();
		// add to layout unpacked items
		{
			if (!(unpacked == null || unpacked.isEmpty()))
			{
				for (int i = 0; i < unpacked.size(); i++)
				{
					final IPackingItem apck = unpacked.get(i);
					final LegacyPackingItem pck = (LegacyPackingItem)apck;

					final ProductId productId = pck.getProductId();
					de.metas.adempiere.model.I_C_POSKey key = InterfaceWrapperHelper.create(getCtx(), de.metas.adempiere.model.I_C_POSKey.class, ITrx.TRXNAME_None);
					if (!key.isActive())
						continue;

					String tableName = I_M_Product.Table_Name;
					key.setName(Services.get(IProductBL.class).getProductName(productId));

					ProductKey tk = new ProductKey(getTerminalContext(), key, tableName, productId);
					tk.setBoxNo(PackingItemsMapKey.UNPACKED);
					tk.setPackingItem(pck);
					tk.setUsedBin(boxes.get(PackingItemsMapKey.UNPACKED));
					tk.setStatus(getProductState(pck, PackingItemsMapKey.UNPACKED));
					tk.setEnabledKey(isSelectedBox);
					list.add(tk);
				}
			}
		}

		if (isSelectedBox)
		{
			// put to layout items from selected box
			List<IPackingItem> selected = map.get(getSelectedBox().getBoxNo());
			if (selected != null && !selected.isEmpty())
			{
				for (int i = 0; i < selected.size(); i++)
				{
					final IPackingItem apck = selected.get(i);
					final LegacyPackingItem pck = (LegacyPackingItem)apck;

					final ProductId productId = pck.getProductId();
					de.metas.adempiere.model.I_C_POSKey key = InterfaceWrapperHelper.create(getCtx(), de.metas.adempiere.model.I_C_POSKey.class, ITrx.TRXNAME_None);
					key.setName(Services.get(IProductBL.class).getProductName(productId));
					if (!key.isActive())
						continue;

					String tableName = I_M_Product.Table_Name;

					ProductKey tk = new ProductKey(getTerminalContext(), key, tableName, productId);
					tk.setBoxNo(getSelectedBox().getBoxNo());
					tk.setPackingItem(pck);
					tk.setUsedBin(boxes.get(getSelectedBox().getBoxNo()));
					tk.setStatus(getProductState(pck, getSelectedBox().getBoxNo()));
					tk.setEnabledKey(!pck.isClosed());
					list.add(tk);
				}
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	public boolean isText()
	{
		return false;
	}

	protected PackingStates getProductState(final IPackingItem pck, final PackingItemsMapKey boxNo)
	{
		if (pck.isClosed())
		{
			return PackingStates.closed; // freezed
		}

		final PackingItemsMap packItems = getPackageTerminalPanel().getPackItems();
		boolean unpacked = false;
		boolean packed = boxNo.isUnpacked();

		for (Entry<PackingItemsMapKey, List<IPackingItem>> e : packItems.entrySet())
		{
			if (e.getKey().isUnpacked())
			{
				// skip unpacked items
				continue;
			}

			final List<IPackingItem> products = e.getValue();
			for (final IPackingItem item : products)
			{
				if (item.getProductId() == pck.getProductId() && isSameBPartner(item, pck) && isSameBPLocation(item, pck))
				{
					packed = true;
				}
			}
		}
		//
		PackingStates state = PackingStates.unpacked;
		if (packed && unpacked)
		{
			state = PackingStates.partiallypacked;
		}
		else if (!packed && unpacked)
		{
			state = PackingStates.unpacked;
		}
		else if (packed && !unpacked)
		{
			state = PackingStates.packed;
		}

		return state;
	}

	public boolean isSameBPartner(final IPackingItem item1, final IPackingItem item2)
	{
		return true;
	}

	public boolean isSameBPLocation(final IPackingItem item1, final IPackingItem item2)
	{
		return true;
	}
}
