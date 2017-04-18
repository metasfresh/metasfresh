package de.metas.handlingunits.client.terminal.pporder.model;

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
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.DefaultKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * Plain key layout for {@link I_PP_Order_BOMMLine}s.
 *
 * @author cg
 *
 */
public class OrderBOMLineKeyLayout extends DefaultKeyLayout
{
	private final String keyLayoutId;

	public OrderBOMLineKeyLayout(final ITerminalContext tc)
	{
		super(tc);

		keyLayoutId = getClass().getName() + "-" + UUID.randomUUID();
		setRows(2);
		setColumns(4);
	}

	@Override
	public String getId()
	{
		return keyLayoutId;
	}

	@Override
	public Color getDefaultColor(final ITerminalKey key)
	{
		Check.assumeInstanceOf(key, OrderBOMLineKey.class, "key");
		final OrderBOMLineKey orderLineKey = (OrderBOMLineKey)key;
		return orderLineKey.getColor();
	}

	public void createAndSetKeysFromBOMLines(final List<I_PP_Order_BOMLine> lines)
	{
		// gh #458: pass the actual business logic to the super class which also will handle the ITerminalContextReferences.
		disposeCreateDetachReverences(
				() -> {
					if (lines == null || lines.isEmpty())
					{
						final List<ITerminalKey> keys = Collections.emptyList();
						setKeys(keys);
						return null;
					}

					//
					// Create Keys
					final List<ITerminalKey> keys = new ArrayList<ITerminalKey>();
					for (final I_PP_Order_BOMLine bomLine : lines)
					{
						final OrderBOMLineKey key = new OrderBOMLineKey(getTerminalContext(), bomLine);
						keys.add(key);
					}

					// Set new Keys list
					setKeys(keys);
					return null;
				});
	}

	public List<I_PP_Order_BOMLine> getOrderBOMLinesForIssuing()
	{
		return getKeys(OrderBOMLineKey.class)
				.stream()
				.filter(OrderBOMLineKey::isForIssuing)
				.map(OrderBOMLineKey::getPP_Order_BOMLine)
				.collect(ImmutableList.toImmutableList());
	}
	
	public List<I_PP_Order_BOMLine> getOrderBOMLinesForReceiving()
	{
		return getKeys(OrderBOMLineKey.class)
				.stream()
				.filter(OrderBOMLineKey::isForReceiving)
				.map(OrderBOMLineKey::getPP_Order_BOMLine)
				.collect(ImmutableList.toImmutableList());
	}
}
