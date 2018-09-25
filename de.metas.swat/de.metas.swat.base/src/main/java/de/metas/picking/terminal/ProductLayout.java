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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Color;
import java.util.List;
import java.util.Objects;

import de.metas.adempiere.form.terminal.IKeyLayoutSelectionModelAware;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.service.PackingItemsMap;
import de.metas.picking.service.PackingSlot;
import de.metas.picking.terminal.Utils.PackingStates;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;

/**
 * @author cg
 * 
 */
public class ProductLayout extends KeyLayout implements IKeyLayoutSelectionModelAware
{

	public static final Color DEFAULT_Color = Color.GREEN;

	public ProductLayout(final ITerminalContext tc)
	{
		super(tc);
		setColumns(3);
		setDefaultColor(DEFAULT_Color);
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
		throw new UnsupportedOperationException();
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

	protected PackingStates getProductState(final IPackingItem pck, final PackingSlot boxNo)
	{
		final PackingItemsMap packItems = getPackageTerminalPanel().getPackItems();
		boolean unpacked = boxNo.isUnpacked();
		boolean packed = packItems.streamPackedItems()
				.anyMatch(item -> isMatching(item, pck));
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

	private final boolean isMatching(final IPackingItem item1, final IPackingItem item2)
	{
		return Objects.equals(item1.getProductId(), item2.getProductId())
				&& isSameBPartner(item1, item2)
				&& isSameBPLocation(item1, item2);

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
