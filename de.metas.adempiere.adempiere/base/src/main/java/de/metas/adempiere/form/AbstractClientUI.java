/**
 *
 */
package de.metas.adempiere.form;

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


import java.io.InputStream;

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * @author tsa
 *
 */
public abstract class AbstractClientUI implements IClientUI
{
	protected final Logger logger = LogManager.getLogger(getClass());

	/**
	 * Gets the instance in current running desktop
	 *
	 * @return
	 */
	protected abstract IClientUIInstance getCurrentInstance();

	@Override
	public void info(final int WindowNo, final String AD_Message)
	{
		getCurrentInstance().info(WindowNo, AD_Message);
	}

	@Override
	public void info(final int WindowNo, final String AD_Message, final String message)
	{
		getCurrentInstance().info(WindowNo, AD_Message, message);
	}

	@Override
	public IAskDialogBuilder ask()
	{
		return getCurrentInstance().ask();
	}

	@Override
	public boolean ask(final int WindowNo, final String AD_Message)
	{
		return getCurrentInstance().ask(WindowNo, AD_Message);
	}

	@Override
	public boolean ask(final int WindowNo, final String AD_Message, final String message)
	{
		return getCurrentInstance().ask(WindowNo, AD_Message, message);
	}

	@Override
	public void warn(final int WindowNo, final String AD_Message)
	{
		getCurrentInstance().warn(WindowNo, AD_Message);
	}

	@Override
	public void warn(final int WindowNo, final String AD_Message, final String message)
	{
		getCurrentInstance().warn(WindowNo, AD_Message, message);
	}

	@Override
	public void warn(final int WindowNo, final Throwable e)
	{
		getCurrentInstance().warn(WindowNo, e);
	}

	@Override
	public void error(final int WindowNo, final String AD_Message)
	{
		getCurrentInstance().error(WindowNo, AD_Message);
	}

	@Override
	public void error(final int WindowNo, final String AD_Message, final String message)
	{
		getCurrentInstance().error(WindowNo, AD_Message, message);
	}

	@Override
	public void error(int WindowNo, Throwable e)
	{
		getCurrentInstance().error(WindowNo, e);
	}

	@Override
	public void download(final byte[] data, final String contentType, final String filename)
	{
		getCurrentInstance().download(data, contentType, filename);
	}

	@Override
	public void downloadNow(final InputStream content, final String contentType, final String filename)
	{
		getCurrentInstance().downloadNow(content, contentType, filename);
	}

	@Override
	public void invokeLater(final int windowNo, final Runnable runnable)
	{
		getCurrentInstance().invokeLater(windowNo, runnable);
	}

	@Override
	public Thread createUserThread(final Runnable runnable, final String threadName)
	{
		return getCurrentInstance().createUserThread(runnable, threadName);
	}

	@Override
	public String getClientInfo()
	{
		return getCurrentInstance().getClientInfo();
	}

	@Override
	public void showWindow(final Object model)
	{
		getCurrentInstance().showWindow(model);
	}

	@Override
	public void executeLongOperation(final Object component, final Runnable runnable)
	{
		getCurrentInstance().executeLongOperation(component, runnable);
	}

	@Override
	public IClientUIInvoker invoke()
	{
		return getCurrentInstance().invoke();
	}
	
	@Override
	public IClientUIAsyncInvoker invokeAsync()
	{
		return getCurrentInstance().invokeAsync();
	}

	@Override
	public void showURL(String url)
	{
		getCurrentInstance().showURL(url);
	}
}
