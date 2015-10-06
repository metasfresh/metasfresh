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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;
import org.eevolution.model.I_DD_Order;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class DDOrderKey extends TerminalKey
{

	private final String id;
	private final String docNo;
	private final KeyNamePair value;
	private final int ddOrderId;

	public DDOrderKey(final ITerminalContext terminalContext, final I_DD_Order ddOrder)
	{
		super(terminalContext);

		Check.assumeNotNull(ddOrder, "order not null");

		id = "DD_Order#" + ddOrder.getDD_Order_ID();
		docNo = ddOrder.getDocumentNo();
		ddOrderId = ddOrder.getDD_Order_ID();
		value = new KeyNamePair(ddOrderId, docNo);
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
		return I_DD_Order.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getDD_Order_ID()
	{
		return ddOrderId;
	}

}
