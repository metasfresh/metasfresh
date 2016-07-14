package de.metas.adempiere.form.impl;

import java.io.InputStream;

import org.slf4j.Logger;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import de.metas.adempiere.form.AbstractClientUIInstance;
import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.adempiere.form.IClientUIAsyncInvoker;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;
import de.metas.logging.LogManager;

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
	private final PlainClientUIInstance instance = new PlainClientUIInstance();

	@Override
	public IClientUIInstance createInstance()
	{
		return instance;
	}

	@Override
	protected IClientUIInstance getCurrentInstance()
	{
		return instance;
	}

	private static final class PlainClientUIInstance extends AbstractClientUIInstance
	{
		private static final Logger logger = LogManager.getLogger(ClientUI.PlainClientUIInstance.class);

		@Override
		public void info(int WindowNo, String AD_Message)
		{
			logger.info(AD_Message);
		}

		@Override
		public void info(int WindowNo, String AD_Message, String message)
		{
			logger.info("" + AD_Message + ": " + message);
		}

		@Override
		public IAskDialogBuilder ask()
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public boolean ask(int WindowNo, String AD_Message)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public boolean ask(int WindowNo, String AD_Message, String message)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public void warn(int WindowNo, String AD_Message)
		{
			logger.warn(AD_Message);
		}

		@Override
		public void warn(int WindowNo, String AD_Message, String message)
		{
			logger.warn("" + AD_Message + ": " + message);
		}

		@Override
		public void error(int WIndowNo, String AD_Message)
		{
			logger.warn("" + AD_Message);
		}

		@Override
		public void error(int WIndowNo, String AD_Message, String message)
		{
			logger.error("" + AD_Message + ": " + message);
		}

		@Override
		public void download(byte[] data, String contentType, String filename)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public void downloadNow(InputStream content, String contentType, String filename)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public String getClientInfo()
		{
			// implementation basically copied from SwingClientUI
			final String javaVersion = System.getProperty("java.version");
			return new StringBuilder("!! NO UI REGISTERED YET !!, java.version=").append(javaVersion).toString();
		}

		@Override
		public void showWindow(Object model)
		{
			throw new UnsupportedOperationException("Not implemented: ClientUI.showWindow()");
		}

		@Override
		public IClientUIInvoker invoke()
		{
			throw new UnsupportedOperationException("not implemented");
		}
		
		@Override
		public IClientUIAsyncInvoker invokeAsync()
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public void showURL(String url)
		{
			System.err.println("Showing URL is not supported on server side: " + url);
		}
	}
}
