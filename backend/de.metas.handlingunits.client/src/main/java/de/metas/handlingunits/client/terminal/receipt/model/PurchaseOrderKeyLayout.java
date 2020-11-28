package de.metas.handlingunits.client.terminal.receipt.model;

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

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * Plain key layout for {@link I_C_Order}s.
 *
 * To add keys to this layout you can use {@link #setKeysFromOrderIds(List)}.
 *
 * @author cg
 *
 */
public class PurchaseOrderKeyLayout extends DefaultKeyLayout
{
	private final Color DEFAULT_Color = Color.ORANGE;
	private final String keyLayoutId;

	public PurchaseOrderKeyLayout(final ITerminalContext tc)
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

	/**
	 * Disposes the currently existing keys. This is our responsibility, not the {@link ITerminalContext}'s - see {@link PurchaseOrderKey#PurchaseOrderKey(ITerminalContext, I_C_BPartner)} for further information.
	 * Then it creates new keys for the given <code>orders</code> and adds them to this layout.
	 *
	 * @param bPartners
	 */
	public void createAndSetKeysFromOrders(final List<I_C_Order> orders)
	{
		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {
					if (orders == null || orders.isEmpty())
					{
						final List<ITerminalKey> keys = Collections.emptyList();
						setKeys(keys);
						return null;
					}

					//
					// Create Keys
					final List<ITerminalKey> keys = new ArrayList<ITerminalKey>();
					for (final I_C_Order order : orders)
					{
						final PurchaseOrderKey key = new PurchaseOrderKey(getTerminalContext(), order);
						keys.add(key);
					}

					// Set new Keys list
					setKeys(keys);
					return null;
				});
	}
}
