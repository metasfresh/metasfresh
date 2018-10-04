/**
 *
 */
package de.metas.fresh.picking;

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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.fresh.picking.form.SwingPackingTerminalPanel;
import de.metas.util.Check;

/**
 * @author cg
 *
 */
public class PackingMaterialLayout extends KeyLayout
{
	public static PackingMaterialLayout cast(final IKeyLayout keyLayout)
	{
		return (PackingMaterialLayout)keyLayout;
	}

	private static final Color COLOR_Default = Color.GRAY;
	private static final Color COLOR_MatchedForProductKey = Color.GREEN;

	public PackingMaterialLayout(final ITerminalContext tc)
	{
		super(tc);

		// NOTE: we better set 2 rows from the start because, initally there will be no keys displayed
		// and after that, when the keys will be added, it will luck ugly and with scroll bars if we would have more than 1 row.
		setRows(2);

		setColumns(3);

		setDefaultColor(COLOR_Default);
	}

	@Override
	public String getId()
	{
		return "PackingMaterial#" + 102; // FIXME: why 102?
	}

	@Override
	protected ImmutableList<ITerminalKey> createKeys()
	{
		//
		// If there is no picking slot selected => display nothing to user
		final PickingSlotKey pickingSlotKey = getSelectedPickingSlotKey();
		if (pickingSlotKey == null)
		{
			return ImmutableList.of();
		}

		//
		// Get the products keys which needs to be checked for compatibility of a packing material key
		final ProductKey productKey = getSelectedProductKey();
		final List<ProductKey> productKeysToCheckForCompatibility;
		if (productKey == null)
		{
			productKeysToCheckForCompatibility = getAllProductKeys();
		}
		else
		{
			productKeysToCheckForCompatibility = Collections.singletonList(productKey);
		}
		if (productKeysToCheckForCompatibility.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Start with all available Packing Material Keys,
		// and then take out those which are not compatible.
		final ArrayList<ITerminalKey> packingMaterialKeys = new ArrayList<>(getAvailablePackingMaterialKeys());
		final ITerminalContext terminalContext = getTerminalContext();
		for (final Iterator<ITerminalKey> it = packingMaterialKeys.iterator(); it.hasNext();)
		{
			final PackingMaterialKey packingMaterialKey = (PackingMaterialKey)it.next();

			// Filter out PackingMaterialKey if not compatible with current PickingSlot
			if (pickingSlotKey != null && !pickingSlotKey.isCompatible(packingMaterialKey))
			{
				it.remove();
				continue;
			}

			// Filter out PackingMaterialKey if not compatible with our product keys
			if (!packingMaterialKey.isCompatibleWithAll(terminalContext, productKeysToCheckForCompatibility))
			{
				it.remove();
				continue;
			}
		}

		return ImmutableList.copyOf(packingMaterialKeys);
	}

	private PickingSlotKey getSelectedPickingSlotKey()
	{
		return getPackingTerminalPanel().getSelectedPickingSlotKey();
	}

	private ProductKey getSelectedProductKey()
	{
		return getPackingTerminalPanel().getSelectedProductKey();
	}

	private List<ProductKey> getAllProductKeys()
	{
		return getPackingTerminalPanel().getAllProductKeys();
	}

	private ImmutableList<PackingMaterialKey> getAvailablePackingMaterialKeys()
	{
		return getPackingTerminalPanel().getAvailablePackingMaterialKeys();
	}

	private SwingPackingTerminalPanel getPackingTerminalPanel()
	{
		return SwingPackingTerminalPanel.cast(super.getBasePanel());
	}

	@Override
	public void setBasePanel(final ITerminalBasePanel basePanel)
	{
		Check.assumeInstanceOfOrNull(basePanel, SwingPackingTerminalPanel.class, "basePanel");
		super.setBasePanel(basePanel);
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

	/**
	 * Gets default {@link PackingMaterialKey} background color. i.e.
	 * <ul>
	 * <li>{@link #COLOR_MatchedForProductKey}, if the current selected {@link ProductKey} has exactly the same default packing materials as this key has
	 * <li>else {@link #COLOR_Default}
	 * </ul>
	 */
	@Override
	public Color getDefaultColor(final ITerminalKey key)
	{
		if (!PackingMaterialKey.class.isInstance(key))
		{
			return super.getDefaultColor(key);
		}
		final PackingMaterialKey packingMaterialKey = (PackingMaterialKey)key;

		final ProductKey selectedProduct = getSelectedProductKey();
		if (!packingMaterialKey.hasSamePackingMaterials(selectedProduct))
		{
			return super.getDefaultColor(key);
		}

		return COLOR_MatchedForProductKey;
	}
}
