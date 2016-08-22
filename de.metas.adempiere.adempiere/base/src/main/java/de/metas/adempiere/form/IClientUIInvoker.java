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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Helper class to easily invoke {@link Runnable}s wrapping them with UI specific features like:
 * <ul>
 * <li>{@link #setInvokeLater(boolean)} - if set the runnable will be added to UI's event queue, and it will be executed later (see UI's event queue thread concept)
 * <li>{@link #setLongOperation(boolean)} - if set, a waiting mouse cursor will be showed while the runnable is executed
 * </ul>
 * 
 * @author tsa
 *
 */
public interface IClientUIInvoker
{
	/** What to do if runnable fails by throwing an exception */
	enum OnFail
	{
		/** Ignore the exceptions. Don't even log it */
		SilentlyIgnore,  //
		/** Show error popup */
		ShowErrorPopup,  //
		/** Propagate the exception */
		ThrowException,  //
		/** Use provided exception handler. See {@link IClientUIInvoker#setExceptionHandler(IExceptionHandler)}. */
		UseHandler,
	}

	/** Exception handler */
	public static interface IExceptionHandler
	{
		void handleException(Exception e);
	}

	/**
	 * Invoke it using given runnable.
	 * 
	 * @param runnable
	 */
	void invoke(final Runnable runnable);

	/**
	 * Invoke it by using configured runnable.
	 * 
	 * @see #setRunnable(Runnable)
	 */
	void invoke();

	/**
	 * Sets the runnable that shall be used by {@link #invoke()} method.
	 * 
	 * @param runnable
	 */
	IClientUIInvoker setRunnable(Runnable runnable);

	/**
	 * Sets parent UI component by <code>windowNo</code>
	 * 
	 * @param windowNo
	 */
	IClientUIInvoker setParentComponentByWindowNo(final int windowNo);

	/**
	 * Sets parent UI component.
	 * 
	 * @param parentComponent
	 */
	IClientUIInvoker setParentComponent(final Object parentComponent);

	/**
	 * Sets what to do in case the runnable will fail by throwing an exception
	 * 
	 * @param onFail
	 */
	IClientUIInvoker setOnFail(OnFail onFail);

	IClientUIInvoker setExceptionHandler(final IExceptionHandler exceptionHandler);

	/**
	 * Advice that given runnable will be a long operation. In case of long operations the Waiting cursor will be shown.
	 * 
	 * NOTE: Implementations may open a separate thread for this.
	 * 
	 * @param longOperation
	 * @deprecated Please consider using {@link IClientUI#invokeAsync()}.
	 */
	@Deprecated
	IClientUIInvoker setLongOperation(final boolean longOperation);

	/**
	 * Advice the UI to show a glass pane before running the given runnable.
	 * 
	 * @param showGlassPane
	 */
	IClientUIInvoker setShowGlassPane(boolean showGlassPane);

	/**
	 * Advice the UI to call the runnable in a separate UI event, EVEN IF this method was called from the UI thread.
	 * 
	 * @param invokeLater
	 */
	IClientUIInvoker setInvokeLater(final boolean invokeLater);
}
