package de.metas.ui.web.vaadin;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

import de.metas.adempiere.form.AbstractClientUI;
import de.metas.adempiere.form.AbstractClientUIInstance;
import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;
import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class VaadinClientUI extends AbstractClientUI
{
	public static final transient VaadinClientUI instance = new VaadinClientUI();
	
	private static final Logger logger = LogManager.getLogger(VaadinClientUI.class);

	private final LoadingCache<UI, VaadinClientUIInstance> uiClientInstances = CacheBuilder.newBuilder()
			.weakKeys()
			.build(new CacheLoader<UI, VaadinClientUIInstance>()
			{

				@Override
				public VaadinClientUIInstance load(final UI key) throws Exception
				{
					return new VaadinClientUIInstance();
				}

			});
	
	private VaadinClientUI()
	{
		super();
	}

	@Override
	public IClientUIInstance createInstance()
	{
		return getCurrentInstance();
	}

	@Override
	protected IClientUIInstance getCurrentInstance()
	{
		final UI ui = UI.getCurrent();
		if (ui == null)
		{
			return VaadinServerInstance.instance;
		}

		try
		{
			return uiClientInstances.get(ui);
		}
		catch (final ExecutionException e)
		{
			logger.error("Error encounted while getting the ClientUI for {}", ui, e);

			// fallback
			return VaadinServerInstance.instance;
		}
	}

	public static class VaadinClientUIInstance extends AbstractClientUIInstance
	{
		@Override
		public void info(final int WindowNo, final String AD_Message)
		{
			final String caption = Services.get(IMsgBL.class).getMsg(Env.getCtx(), AD_Message);
			Notification.show(caption, Type.TRAY_NOTIFICATION);
		}

		@Override
		public void info(final int WindowNo, final String AD_Message, final String message)
		{
			final String caption = Services.get(IMsgBL.class).getMsg(Env.getCtx(), AD_Message);
			Notification.show(caption, message, Type.TRAY_NOTIFICATION);
		}

		@Override
		public IAskDialogBuilder ask()
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean ask(final int WindowNo, final String AD_Message)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean ask(final int WindowNo, final String AD_Message, final String message)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public void warn(final int WindowNo, final String AD_Message)
		{
			warn(WindowNo, AD_Message, "");
		}

		@Override
		public void warn(final int WindowNo, final String AD_Message, final String message)
		{
			final String caption = Services.get(IMsgBL.class).getMsg(Env.getCtx(), AD_Message);
			Notification.show(caption, message, Type.WARNING_MESSAGE);
		}

		@Override
		public void error(final int WindowNo, final String AD_Message)
		{
			error(WindowNo, AD_Message, "");
		}

		@Override
		public void error(final int WindowNo, final String AD_Message, final String message)
		{
			final String caption = Services.get(IMsgBL.class).getMsg(Env.getCtx(), AD_Message);
			Notification.show(caption, message, Type.ERROR_MESSAGE);
		}

		@Override
		public void download(final byte[] data, final String contentType, final String filename)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public void downloadNow(final InputStream content, final String contentType, final String filename)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public String getClientInfo()
		{
			final String vaadinVersion = com.vaadin.shared.Version.getFullVersion();
			final UI ui = UI.getCurrent();
			final String uiId = ui == null ? "-" : ui.getId();
			return "Vaadin " + vaadinVersion //
					+ ", UI=" + uiId //
					;
		}

		@Override
		public void showWindow(final Object model)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public IClientUIInvoker invoke()
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}

		@Override
		public void showURL(final String url)
		{
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException();
		}
	}

	public static class VaadinServerInstance extends AbstractClientUIInstance
	{
		public static final transient VaadinServerInstance instance = new VaadinServerInstance();

		private static final Logger logger = LogManager.getLogger(VaadinServerInstance.class);

		private VaadinServerInstance()
		{
			super();
		}

		@Override
		public void info(final int WindowNo, final String AD_Message)
		{
			logger.info(AD_Message);
		}

		@Override
		public void info(final int WindowNo, final String AD_Message, final String message)
		{
			logger.info("" + AD_Message + ": " + message);
		}

		@Override
		public IAskDialogBuilder ask()
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public boolean ask(final int WindowNo, final String AD_Message)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public boolean ask(final int WindowNo, final String AD_Message, final String message)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public void warn(final int WindowNo, final String AD_Message)
		{
			logger.warn(AD_Message);
		}

		@Override
		public void warn(final int WindowNo, final String AD_Message, final String message)
		{
			logger.warn("" + AD_Message + ": " + message);
		}

		@Override
		public void error(final int WIndowNo, final String AD_Message)
		{
			logger.warn("" + AD_Message);
		}

		@Override
		public void error(final int WIndowNo, final String AD_Message, final String message)
		{
			logger.error("" + AD_Message + ": " + message);
		}

		@Override
		public void download(final byte[] data, final String contentType, final String filename)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public void downloadNow(final InputStream content, final String contentType, final String filename)
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
		public void showWindow(final Object model)
		{
			throw new UnsupportedOperationException("Not implemented: ClientUI.showWindow()");
		}

		@Override
		public IClientUIInvoker invoke()
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public void showURL(final String url)
		{
			System.err.println("Showing URL is not supported on server side: " + url);
		}
	}

}
