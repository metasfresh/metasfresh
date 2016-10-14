package de.metas.handlingunits.client.terminal.select.model;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.List;
import java.util.UUID;

import org.adempiere.util.Check;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKeyByNameComparator;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * Plain key layout for {@link I_M_Warehouse}s.
 *
 * To add keys to this layout you can use {@link #setKeysFromBPartnerIds(List)}.
 *
 * @author cg
 *
 */
public class WarehouseKeyLayout extends DefaultKeyLayout
{
	private final Color DEFAULT_Color = new Color(-6724045, false); // brown

	private final String keyLayoutId;

	public WarehouseKeyLayout(final ITerminalContext tc)
	{
		super(tc);

		keyLayoutId = getClass().getName() + "-" + UUID.randomUUID();
		setRows(2);
		setColumns(3);
		setDefaultColor(DEFAULT_Color);
	}

	@Override
	public String getId()
	{
		return keyLayoutId;
	}

	public void createAndSetKeysFromWarehouses(final List<I_M_Warehouse> warehouses)
	{
		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {
					if (warehouses == null || warehouses.isEmpty())
					{
						final List<ITerminalKey> keys = Collections.emptyList();
						setKeys(keys);
						return null;
					}

					//
					// Create Keys
					final List<ITerminalKey> keys = new ArrayList<ITerminalKey>();
					for (final I_M_Warehouse warehouse : warehouses)
					{
						final WarehouseKey key = new WarehouseKey(getTerminalContext(), warehouse);
						keys.add(key);
					}

					//
					// Sort keys by Name
					Collections.sort(keys, TerminalKeyByNameComparator.instance);

					//
					// Set new Keys list
					setKeys(keys);
					return null;
				});
	}

	/**
	 * Simulates user press on a warehouse key.
	 *
	 * To be used mainly in testing
	 *
	 * @param warehouse
	 */
	public void pressKey(final I_M_Warehouse warehouse)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");
		for (final ITerminalKey key : getKeys())
		{
			final WarehouseKey warehouseKey = (WarehouseKey)key;
			if (warehouseKey.getM_Warehouse_ID() == warehouse.getM_Warehouse_ID())
			{
				setSelectedKey(warehouseKey);

				// Fire listeners
				keyReturned(warehouseKey);

				// stop here
				return;
			}
		}

		throw new IllegalStateException("No Key was found for " + warehouse);
	}
}
