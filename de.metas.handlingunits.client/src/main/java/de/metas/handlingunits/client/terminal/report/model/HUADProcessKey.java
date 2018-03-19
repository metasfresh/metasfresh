package de.metas.handlingunits.client.terminal.report.model;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.model.InterfaceWrapperHelper;

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
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.process.IADProcessDAO;

public class HUADProcessKey extends TerminalKey
{
	private final int adProcessId;
	private final String id;
	private final String name;
	private final KeyNamePair value;

	public HUADProcessKey(final ITerminalContext terminalContext, final int adProcessId)
	{
		super(terminalContext);

		id = getClass().getName() + "-" + adProcessId;

		value = extractKeyNamePair(adProcessId);
		this.adProcessId = adProcessId;
		name = value.getName();
	}

	private static final KeyNamePair extractKeyNamePair(final int adProcessId)
	{
		Check.assume(adProcessId > 0, "processId > 0");

		final I_AD_Process process = Services.get(IADProcessDAO.class).retrieveProcessById(adProcessId);
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class);

		String caption = processTrl.getName();
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			caption += " (" + adProcessId + ")";
		}

		return KeyNamePair.of(adProcessId, caption);
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
