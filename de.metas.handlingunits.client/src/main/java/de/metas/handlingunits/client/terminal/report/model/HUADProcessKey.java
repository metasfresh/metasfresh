package de.metas.handlingunits.client.terminal.report.model;

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
import org.compiere.model.I_AD_Process;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class HUADProcessKey extends TerminalKey
{
	private final int adProcessId;
	private final String id;
	private final String name;
	private final KeyNamePair value;

	public HUADProcessKey(final ITerminalContext terminalContext, final I_AD_Process process)
	{
		super(terminalContext);

		Check.assumeNotNull(process, "process not null");
		this.adProcessId = process.getAD_Process_ID();

		final int processId = process.getAD_Process_ID();
		id = getClass().getName() + "-" + processId;

		final String processName = process.getName();
		value = new KeyNamePair(processId, processName);

		name = processName;
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
		return I_AD_Process.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getAD_Process_ID()
	{
		return adProcessId; 
	}
}
