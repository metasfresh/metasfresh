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

import org.adempiere.util.Check;
import org.compiere.model.I_C_Order;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * A terminal key that encapsulates a (purchase) order.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */ class PurchaseOrderKey extends TerminalKey
{
	private final String id;
	private final String docNo;
	private final KeyNamePair value;
	private final int orderId;

	/* package */ PurchaseOrderKey(final ITerminalContext terminalContext, final I_C_Order order)
	{
		super(terminalContext);

		Check.assumeNotNull(order, "order not null");

		id = "C_Order#" + order.getC_Order_ID();
		docNo = order.getDocumentNo();
		orderId = order.getC_Order_ID();
		value = new KeyNamePair(orderId, docNo);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return docNo;
	}

	@Override
	public String getTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getC_Order_ID()
	{
		return orderId;
	}
}
