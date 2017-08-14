package de.metas.adempiere.form;

import org.adempiere.util.Check;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUIInvoker.OnFail;
import de.metas.logging.LogManager;

/**
 * Implements some common methods which are not directly related to a particular user interface
 *
 * @author tsa
 *
 */
public abstract class AbstractClientUIInstance implements IClientUIInstance
{
	protected final Logger logger = LogManager.getLogger(getClass());

	@Override
	public void warn(final int WindowNo, final Throwable e)
	{
		final String AD_Message = "Error";

		final String message = buildErrorMessage(e);

		logger.warn(message, e);
		warn(WindowNo, AD_Message, message);
	}

	@Override
	public void error(final int WindowNo, final Throwable e)
	{
		final String AD_Message = "Error";

		final String message = buildErrorMessage(e);

		// Log the error to console
		// NOTE: we need to do that because in case something went wrong we need the stacktrace to debug the actual issue
		// Before removing this please consider that you need to provide an alternative from where the support guys can grab their detailed exception info.
		logger.warn(message, e);

		error(WindowNo, AD_Message, message);
	}

	protected final String buildErrorMessage(final Throwable e)
	{
		String message = e == null ? "@UnknownError@" : e.getLocalizedMessage();
		if (Check.isEmpty(message, true) && e != null)
		{
			message = e.toString();
		}
		return message;
	}

	@Override
	public Thread createUserThread(final Runnable runnable, final String threadName)
	{
		final Thread thread;
		if (threadName == null)
		{
			thread = new Thread(runnable);
		}
		else
		{
			thread = new Thread(runnable, threadName);
		}

		thread.setDaemon(true);

		return thread;
	}

	@Override
	public final void executeLongOperation(final Object component, final Runnable runnable)
	{
		invoke()
				.setParentComponent(component)
				.setLongOperation(true)
				.setOnFail(OnFail.ThrowException) // backward compatibility
				.invoke(runnable);
	}

	@Override
	public void invokeLater(final int windowNo, final Runnable runnable)
	{
		invoke()
				.setParentComponentByWindowNo(windowNo)
				.setInvokeLater(true)
				.setOnFail(OnFail.ThrowException) // backward compatibility
				.invoke(runnable);
	}

	/**
	 * This method does nothing.
	 *
	 * @deprecated please check out the deprecation notice in {@link IClientUIInstance#hideBusyDialog()}.
	 */
	@Deprecated
	@Override
	public void hideBusyDialog()
	{
		// nothing
	}

	/**
	 * This method does nothing.
	 *
	 * @deprecated please check out the deprecation notice in {@link IClientUIInstance#disableServerPush()}.
	 */
	@Deprecated
	@Override
	public void disableServerPush()
	{
		// nothing
	}

	/**
	 * This method throws an UnsupportedOperationException.
	 *
	 * @deprecated please check out the deprecation notice in {@link IClientUIInstance#infoNoWait(int, String)}.
	 * @throws UnsupportedOperationException
	 */
	@Deprecated
	@Override
	public void infoNoWait(int WindowNo, String AD_Message)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
