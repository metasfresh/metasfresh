package de.metas.printing.spi.impl;

/*
 * #%L
 * de.metas.printing.base
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
import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.printing.spi.IPrintJobBatchMonitor;

public class UserConfirmationPrintJobMonitor extends AbstractPrintJobMonitor
{
	private final IClientUIInstance clientUI;

	public UserConfirmationPrintJobMonitor()
	{
		this(Services.get(IClientUI.class).createInstance());
	}

	public UserConfirmationPrintJobMonitor(final IClientUIInstance clientUI)
	{
		Check.assumeNotNull(clientUI, "clientUI not null");

		this.clientUI = clientUI;
	}

	@Override
	public IPrintJobBatchMonitor createBatchMonitor()
	{
		return new UserConfirmationPrintJobBatchMonitor(clientUI);
	}
}
