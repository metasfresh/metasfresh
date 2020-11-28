package de.metas.handlingunits.client.terminal.ddorder.model;

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

import org.eevolution.model.I_DD_Order;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class DDOrderKeyLayout extends DefaultKeyLayout
{
	private final Color DEFAULT_Color = Color.ORANGE;
	private final String keyLayoutId;

	public DDOrderKeyLayout(final ITerminalContext tc)
	{
		super(tc);

		keyLayoutId = getClass().getName() + "-" + UUID.randomUUID();
		setRows(2);
		setColumns(5);
		setDefaultColor(DEFAULT_Color);
	}

	@Override
	public String getId()
	{
		return keyLayoutId;
	}

	public void createAndSetKeysFromDDOrders(final List<I_DD_Order> ddOrders)
	{
		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {
					if (ddOrders == null || ddOrders.isEmpty())
					{
						final List<ITerminalKey> keys = Collections.emptyList();
						setKeys(keys);
						return null;
					}

					//
					// Create Keys
					final List<DDOrderKey> keys = new ArrayList<>();
					for (final I_DD_Order ddOrder : ddOrders)
					{
						final DDOrderKey key = new DDOrderKey(getTerminalContext(), ddOrder);
						keys.add(key);
					}

					//
					// Sort the keys
					Collections.sort(keys, ITerminalKey.COMPARATOR_ByName);

					// Set new Keys list
					setKeys(keys);
					return null;
				});
	}

}
