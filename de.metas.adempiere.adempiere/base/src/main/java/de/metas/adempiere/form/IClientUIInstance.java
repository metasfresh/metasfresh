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

import de.metas.adempiere.form.IClientUIAsyncInvoker.IClientUIAsyncRunnable;

/**
 * It's the Client User Interface in a particular user session.
 *
 * You can pass instances of this interface to non-UI working-threads and all calls will go to Desktop used in construction time.
 *
 * Example:
 * <ul>
 * <li>for Swing we will have only one client instance because Swing runs desktop client side
 * <li>in a web based solution each user has it's own Session and Desktop. So an IClientUIInstance is bound to a particular desktop
 * </ul>
 *
 * @author tsa
 *
 */
public interface IClientUIInstance
{
	void info(int WindowNo, String AD_Message);

	void info(int WindowNo, String AD_Message, String message);

	IAskDialogBuilder ask();

	/**
	 * create dialog window
	 *
	 * @param WindowNo
	 * @param AD_Message
	 * @return
	 */
	boolean ask(int WindowNo, String AD_Message);

	boolean ask(int WindowNo, String AD_Message, String message);

	void warn(int WindowNo, String AD_Message);

	void warn(int WindowNo, String AD_Message, String message);

	void warn(int WindowNo, Throwable e);

	void error(int WIndowNo, String AD_Message);

	void error(int WIndowNo, String AD_Message, String message);

	void error(int WindowNo, Throwable e);

	/**
	 * Trigger a download preview of given data
	 *
	 * @param data
	 * @param contentType
	 * @param filename
	 */
	void download(byte[] data, String contentType, String filename);

	/**
	 * Trigger a download (instantly) for given date
	 *
	 * @param content
	 * @param contentType
	 * @param filename
	 */
	void downloadNow(InputStream content, String contentType, String filename);

	/**
	 * Retrieves Client Info (e.g. web browser name/version, java version etc)
	 *
	 * @return client info string
	 */
	String getClientInfo();

	/**
	 * Opens a window which will show given <code>model</code>.
	 *
	 * @param model
	 */
	void showWindow(Object model);

	/**
	 * Execute <code>runnable</code> while displaying "loading" cursor. Implementations may open a separate thread for this.
	 *
	 * @param component could be
	 *            <ul>
	 *            <li>UI specific component (e.g. java.awt.Component etc)
	 *            <li>WindowNo ({@link Integer})
	 *            </ul>
	 * @param runnable
	 * @see #invoke()
	 * @see IClientUIInvoker#setLongOperation(boolean)
	 * 
	 * @deprecated Please consider using {@link #invokeAsync()}.
	 */
	@Deprecated
	void executeLongOperation(Object component, Runnable runnable);

	/**
	 * Call given runnable in a separate UI event.
	 *
	 * @param windowNo
	 * @param runnable
	 * @see #invoke()
	 * @see IClientUIInvoker#setInvokeLater(boolean)
	 */
	void invokeLater(int windowNo, Runnable runnable);

	/**
	 * Creates user thread. An user thread is a thread which will be interrupted on user logout.
	 *
	 * @param runnable
	 * @param threadName
	 * @return
	 */
	Thread createUserThread(Runnable runnable, String threadName);

	/**
	 * Creates the UI invoker helper class which allows you to execute a {@link Runnable} with different options (invokeLater, longOperation, show popup on error etc).
	 *
	 * @return UI invoker helper
	 */
	IClientUIInvoker invoke();
	
	/**
	 * Creates an UI invoker which is able to execute a long running {@link IClientUIAsyncRunnable} in a background thread.
	 * 
	 * @return async invoker
	 */
	IClientUIAsyncInvoker invokeAsync();

	/**
	 * Display given URL.
	 *
	 * Depends on implementation, this method could
	 * <ul>
	 * <li>open default browser to display it (e.g. swing clients)
	 * <li>show it embedded in UI (e.g. ZK client)
	 * <li>don't show it at all (e.g. server side)
	 * </ul>
	 *
	 * @param url
	 */
	void showURL(final String url);
}
