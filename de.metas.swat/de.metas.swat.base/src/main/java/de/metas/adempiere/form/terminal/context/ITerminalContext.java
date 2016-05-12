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
import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalException;

/**
 * Terminal API Context which will provide following capabilities
 * 
 * <ul>
 * <li>default settings like current user, text keyboard to be use, numberic keyboard to be used, default font etc.
 * <li>a factory for terminal services (see {@link #getService(Class)}).
 * <li>holds references to all terminal API created components, listeners etc. When {@link #dispose()} is called, all those components are destroyed, so memory leaks are avoided.
 * </ul>
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
	 * 
	 * @return default on-screen text (QWERTY) keyboard to be used
	 */
	public IKeyLayout getTextKeyLayout();

	/**
	 * Sets default on-screen text (QWERTY) keyboard to be used
	 * 
	 * @param keyLayout
	 */
	public void setTextKeyLayout(IKeyLayout keyLayout);

	/**
	 * 
	 * @return default on-screen numeric keyboard to be used
	 */
	public IKeyLayout getNumericKeyLayout();

	/**
	 * Sets default on-screen numeric keyboard to be used
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
	 * Programatically register a terminal service.
	 * 
	 * @param clazz
	 * @param implementation
	 */
	<T> void registerService(Class<T> clazz, T implementation);

	/**
	 * Get service implementation for given class.
	 * 
	 * @param clazz
	 * @return service implementation; never return null
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
	 * Destroy this context. i.e.
	 * 
	 * <ul>
	 * <li>all settings will be reset to their initial/default values
	 * <li>all services will be removed
	 * <li>all references will be destroyed
	 * </ul>
	 * 
	 * After calling this method, this context will not be usable anymore.
	 */
	void dispose();

	/**
	 * 
	 * @return true if this context was already disposed
	 * @see #dispose()
	 */
	boolean isDisposed();

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
	 * Adds given component to the internal list of disposable components of current {@link ITerminalContextReferences}.
	 * 
	 * @param comp
	 */
	void addToDisposableComponents(IDisposable comp);

	/**
	 * Create a new references instance and set it as current one.
	 * 
	 * A common use case when you will want to create a set of references is for example when you want to open a child window and when that child window is closed you want to make sure all components
	 * created in that window are destroyed. But only those, you don't want to destroy the components which were created in the main window.
	 * 
	 * @return newly created references instance
	 */
	ITerminalContextReferences newReferences();

	/**
	 * Destroy given references instance.
	 * 
	 * If given references are the current ones then the current ones will be set to last ones (excluding this one).
	 * 
	 * @param references
	 */
	void deleteReferences(ITerminalContextReferences references);
}
