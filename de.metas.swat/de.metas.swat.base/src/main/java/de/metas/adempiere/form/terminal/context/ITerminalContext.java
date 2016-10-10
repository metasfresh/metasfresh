package de.metas.adempiere.form.terminal.context;

/*
 * #%L
 * de.metas.swat.base
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

import java.awt.Dimension;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.terminal.AbstractTerminalTextField;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.TerminalKeyDialog;

/**
 * Terminal API Context which will provide following capabilities:
 *
 * <ul>
 * <li>default settings like current user, text keyboard to be used, numeric keyboard to be used, default font etc.
 * <li>a factory for terminal services, see {@link #getService(Class)}.
 * <li>a factory that is dedicated to creating UI terminal components, see {@link #getTerminalFactory()}.
 * <li>holds references to all terminal API created components, listeners etc. When {@link #dispose()} is called, all those components are destroyed, so memory leaks are avoided.
 * See {@link #newReferences()}, {@link #addToDisposableComponents(IDisposable)} and {@link #deleteReferences(ITerminalContextReferences)}
 * </ul>
 *
 * Notes:
 * <ul>
 * <li>the methods declared by {@link ITerminalContextReferences} are mostly implemented by delegating to this context's current references.
 * See {@link #newReferences()} and {@link #deleteReferences(ITerminalContextReferences)}.</li>
 * <li>Instances of this interface shall be created and destroyed by {@link TerminalContextFactory}.</li>
 *
 * @author tsa
 *
 */
public interface ITerminalContext extends IContextAware, IPropertiesContainer
{
	@Override
	public Properties getCtx();

	/**
	 * Set a particular property
	 *
	 * @param contextName
	 * @param valueInt
	 */
	void setContextValue(String contextName, int valueInt);

	/**
	 * @return always return {@link ITrx#TRXNAME_None}.
	 */
	@Override
	public String getTrxName();

	/**
	 *
	 * @return terminal's components factory to be used
	 */
	public ITerminalFactory getTerminalFactory();

	/**
	 * Return the on-screen text keyboard to be used. If the current {@link ITerminalContextReferences} has no explicit setting,
	 * look down the stack and return the layout of the most recent references that has one.
	 *
	 * @return default on-screen text (QWERTY) keyboard to be used, according to the current {@link ITerminalContextReferences}.
	 */
	public IKeyLayout getTextKeyLayout();

	/**
	 * Set default on-screen text keyboard to be used in the current {@link ITerminalContextReferences}.
	 * Note: we store the key layout in a references instance, because property change support instances and listeners that are created in the process also end up in that same references instance.
	 *
	 * @param keyLayout
	 */
	public void setTextKeyLayout(IKeyLayout keyLayout);

	/**
	 * Analog to {@link #getTextKeyLayout()}.
	 *
	 * @return default on-screen numeric keyboard to be used, according to the current {@link ITerminalContextReferences}.
	 */
	public IKeyLayout getNumericKeyLayout();

	/**
	 * Analog to {@link #setTextKeyLayout(IKeyLayout)}
	 *
	 * @param keyLayout
	 */
	public void setNumericKeyLayout(IKeyLayout keyLayout);

	/**
	 *
	 * @return ADempiere WindowNo which was allocated to us
	 */
	public int getWindowNo();

	/**
	 * Sets ADempiere's WindowNo which was allocated to us
	 *
	 * @param windowNo
	 */
	public void setWindowNo(int windowNo);

	/**
	 * Set terminal's AD_User_ID (i.e. the user which logged in terminal)
	 *
	 * @param adUserId
	 */
	public void setAD_User_ID(final int adUserId);

	/**
	 *
	 * @return terminal's AD_User_ID (i.e. the user which logged in terminal)
	 */
	public int getAD_User_ID();

	/**
	 *
	 * @return true if we shall display debug info
	 */
	public boolean isShowDebugInfo();

	/**
	 * Programmatically register a terminal service.
	 *
	 * @param clazz
	 * @param implementation
	 */
	<T> void registerService(Class<T> clazz, T implementation);

	/**
	 * Get service implementation for given class.
	 * This is similar to {@link Services#get(Class)}, but the service returned by this method will also be configured with this context instance,
	 * <b>if</b> it implements {@link ITerminalContextService}.
	 * <p>
	 * Note that this method falls back to {@link Services#get(Class)} if there is no service registered with {@link #registerService(Class, Object)}
	 *
	 * @param clazz
	 * @return service implementation; never return <code>null</code>
	 * @throws TerminalException if service was not found
	 * @see #registerService(Class, Object)
	 */
	<T> T getService(Class<T> clazz);

	int getDefaultFontSize();

	void setDefaultFontSize(int fontSize);

	/**
	 * Gets screen resolution to be considered by all terminal components.
	 *
	 * This screen resolution could differ from the screen resolution of current running system.
	 *
	 * @return screen resolution.
	 */
	public Dimension getScreenResolution();

	/**
	 * Sets screen resolution to be considered by all terminal components.
	 *
	 * @param screenResolution
	 */
	void setScreenResolution(Dimension screenResolution);

	/**
	 * Creates a new {@link WeakPropertyChangeSupport} instance, using current {@link ITerminalContextReferences}.
	 *
	 * A link to this instance will be remembered so {@link #dispose()} will be able to clear the listeners.
	 *
	 * @param sourceBean
	 * @return {@link WeakPropertyChangeSupport} instance
	 */
	WeakPropertyChangeSupport createPropertyChangeSupport(Object sourceBean);

	/**
	 * Creates a new {@link WeakPropertyChangeSupport} instance, using current {@link ITerminalContextReferences}.
	 *
	 * A link to this instance will be remembered so {@link #dispose()} will be able to clear the listeners.
	 *
	 * @param sourceBean
	 * @param weakDefault
	 * @return {@link WeakPropertyChangeSupport} instance
	 */
	WeakPropertyChangeSupport createPropertyChangeSupport(Object sourceBean, boolean weakDefault);

	/**
	 * Add the given component to the internal list of disposable components of the current {@link ITerminalContextReferences}.
	 * Also see the javadoc in {@link IDisposable}.
	 *
	 * @param comp
	 */
	void addToDisposableComponents(IDisposable comp);

	/**
	 * Create a new references instance and set it as current one. Older references instances are retained in a kind of stack.
	 * <p>
	 * A common use case when you will want to create a set of references is for example when you want to open a child window.
	 * When that child window is closed, you want to make sure all components created in that window are destroyed.
	 * But you want to destroy <b>only</b> those child window's components and not the components which were created in the main window.
	 * <p>
	 * It makes sense to call this method in a <code>try-with-resources</code> statement whenever possible, since {@link ITerminalContextReferences} implements {@link AutoCloseable}.
	 *
	 * @return newly created references instance
	 */
	ITerminalContextReferences newReferences();

	/**
	 * Destroy the currentReferences instance and sets a new current one (stack-like).
	 * <p>
	 * Hint: if possible, don't use this method directly, but rather call {@link #newReferences()} in conjunction if a <code>try-with-resources</code> statement.
	 *
	 * @param currentReferences may not be <code>null</code>. We insist on this parameter so that the method's implementation can verify that the caller really knows which one is the currentReferences.
	 *            Without this, a caller might blindly assume that its own {@link ITerminalContextReferences} instance was the top-most, while in reality it is not.
	 */
	void deleteReferences(ITerminalContextReferences currentReferences);

	/**
	 * Close the current/topmost {@link ITerminalContextReferences} so that further changes like {@link #setNumericKeyLayout(IKeyLayout)} or {@link #addToDisposableComponents(IDisposable)}
	 * will not be forwarded to the current/topmost references instance, but to the one below (unless the one below is also already closed, etc).
	 * <p>
	 * Use case: {@link TerminalKeyDialog}s that are started from {@link AbstractTerminalTextField} have their own references instance (see {@link AbstractTerminalTextField#showKeyboard()} for why that is).<br>
	 * But some actions performed with that keyboard cause other components to be created <i>outside</i> the on-screen keyboard (e.g. partner location buttons for a partner that is entered via the keyboard).<br>
	 * Those disposable components shall not be added to the on-screen keyboard's references because they shall live on after the keyboard was closed.<br>
	 * To achieve this, the on-screen keyboard calls this method after it created all its components.
	 * <p>
	 * Note that this method is unrelated to {@link ITerminalContextReferences#close()} which is declared by {@link AutoCloseable} in order to allow us to do <code>try-with-resources</code>.
	 */
	void closeCurrentReferences();

	/**
	 * Removes the currentReferences instance from the stack without destroying/disposing it and sets a new current one (stack-like).<br>
	 * So, it's like {@link #deleteReferences(ITerminalContextReferences)} but without actually destroying/disposing the stuff that is stored in the given <code>currentReferences</code>.
	 * <p>
	 * Use this method if you created a bunch of {@link IDisposable}s within a long-living component and you want to be able to remove and recreate them later on, while the long-living component shall remain.
	 *
	 * @param currentReferences may not be <code>null</code>. We insist on this parameter so that the method's implementation can verify that the caller really knows which one is the currentReferences.
	 *            Without this, a caller might blindly assume that its own {@link ITerminalContextReferences} instance was the top-most, while in reality it is not.
	 * @see KeyLayout for the intended usage.
	 * @task https://github.com/metasfresh/metasfresh/issues/458
	 */
	void detachReferences(ITerminalContextReferences currentReferences);
}
