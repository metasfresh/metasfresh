package de.metas.adempiere.form.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import de.metas.adempiere.form.AbstractClientUI;
import de.metas.adempiere.form.IClientUIInstance;

/**
 * Providing a simple IClientUI implementation that can be automatically loaded by {@link org.adempiere.util.Services#get(Class)} in the early stages of Adempiere startup.<br>
 * This client implementation is supposed to be replaced with a "real" client implementation during adempiere-startup.
 * 
 * Also, this implementation is used on server side.
 *
 * @author ts
 *
 */
public class ClientUI extends AbstractClientUI
{
	@Override
	public IClientUIInstance createInstance()
	{
		throw new UnsupportedOperationException("Not implemented: ClientUI.createInstance()");
	}

	@Override
	protected IClientUIInstance getCurrentInstance()
	{
		throw new UnsupportedOperationException("Not implemented: ClientUI.getCurrentInstance()");
	}

	@Override
	public String getClientInfo()
	{
		// implementation basically copied from SwingClientUI
		final String javaVersion = System.getProperty("java.version");
		return new StringBuilder("!! NO UI REGISTERED YET !!, java.version=").append(javaVersion).toString();
	}

	@Override
	public void showWindow(final Object model)
	{
		throw new UnsupportedOperationException("Not implemented: ClientUI.showWindow()");
	}

	@Override
	public void showURL(final String url)
	{
		System.err.println("Showing URL is not supported on server side: " + url);
	}
}
