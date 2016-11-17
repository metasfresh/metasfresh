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

import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * A terminal key that encapsulates a (vendor) bPartner.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class VendorKey extends TerminalKey
{
	private final String id;
	private final String name;
	private final KeyNamePair value;
	private final int bpartnerId;

	/**
	 *
	 * @param terminalContext
	 * @param order
	 */
	/* package */ VendorKey(final ITerminalContext terminalContext, final I_C_BPartner bpartner)
	{
		super(terminalContext);

		Check.assumeNotNull(bpartner, "bpartner not null");

		id = "C_BPartner#" + bpartner.getC_BPartner_ID();
		name = bpartner.getName();

		bpartnerId = bpartner.getC_BPartner_ID();
		value = new KeyNamePair(bpartnerId, name);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return name;
	}

	@Override
	public String getTableName()
	{
		return I_C_BPartner.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}
}
