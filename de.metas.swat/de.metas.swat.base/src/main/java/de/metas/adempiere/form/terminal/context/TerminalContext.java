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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.IService;
import org.adempiere.util.Services;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.adempiere.util.proxy.WeakWrapper;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.logging.LogManager;

public final class TerminalContext implements ITerminalContext, ITerminalContextReferences
{
	private Properties ctx;

	private final Map<String, Object> _contextProperties = new HashMap<>();

	/**
	 * Terminal factory in use (weak reference).
	 */
	private ITerminalFactory terminalFactory;

	/**
	 * Keeps a hard-link to current {@link ITerminalFactory}. This reference is not used, it's just for preventing the garbage collecting.
	 */
	@SuppressWarnings("unused")
	private ITerminalFactory terminalFactoryHardRef;

	private IKeyLayout keyLayoutNumberic;
	private IKeyLayout keyLayoutText;
	private int windowNo;
	private int adUserId;
	private final Map<Class<?>, Object> services = new HashMap<Class<?>, Object>();

	private final int DEFAULT_FontSize = 20;
	private int defaultFontSize;

	private final int DEFAULT_ScreenWidth = 1024;
	private final int DEFAULT_ScreenHeight = 740;
	private int screenWidth;
	private int screenHeight;

	/**
	 * Current {@link ITerminalContextReferences}.
	 *
	 * Methods like {@link #addToDisposableComponents(IDisposable)}, {@link #createPropertyChangeSupport(Object)} etc, will operate on this references.
	 */
	private ITerminalContextReferences currentReferences = null;

	/**
	 * All {@link ITerminalContextReferences} which were created by this terminal context, including {@link #currentReferences}.
	 */
	private final List<ITerminalContextReferences> referencesList = new ArrayList<ITerminalContextReferences>();

	private boolean _disposed = false;

	/* package */TerminalContext()
	{
		super();
		reset();
	}

	@Override
	public String toString()
	{
		return "TerminalContext ["
				+ "keyLayoutNumberic=" + keyLayoutNumberic
				+ ", keyLayoutText=" + keyLayoutText
				+ ", windowNo=" + windowNo
				+ ", adUserId=" + adUserId
				+ ", services=" + services
				+ ", defaultFontSize=" + defaultFontSize
				+ ", screenWidth=" + screenWidth
				+ ", screenHeight=" + screenHeight
				// + ", ctx=" + ctx
				// + ", factory=" + factory
				+ "]";
	}

	@Override
	public Object setProperty(final String propertyName, final Object value)
	{
		return _contextProperties.put(propertyName, value);
	}

	@Override
	public <T> T getProperty(final String propertyName)
	{
		@SuppressWarnings("unchecked")
		final T propertyValue = (T)_contextProperties.get(propertyName);
		return propertyValue;
	}

	@Override
	protected void finalize() throws Throwable
	{
		TerminalContextFactory.get().destroy(this);
	}

	/**
	 * Reset the context to it's initial state (all settings have default values, services are removed etc).
	 *
	 * NOTE:
	 * <ul>
	 * <li>after invoking this method, the context will not be usable anymore because it needs to be reconfigured.
	 * <li>you can call this method right before destroying the terminal context
	 * </ul>
	 */
	private void reset()
	{
		ctx = null;

		//
		// Cleanup references
		disposeAllReferences();
		disposeAllServices();

		if (terminalFactory != null)
		{
			terminalFactory.dispose();
			terminalFactory = null;
		}

		terminalFactoryHardRef = null;
		keyLayoutNumberic = null;
		keyLayoutText = null;

		windowNo = Env.WINDOW_MAIN;
		adUserId = -1;

		defaultFontSize = DEFAULT_FontSize;

		screenWidth = DEFAULT_ScreenWidth;
		screenHeight = DEFAULT_ScreenHeight;
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	protected void setCtx(final Properties ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public final String getTrxName()
	{
		return ITrx.TRXNAME_None;
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return terminalFactory;
	}

	protected void setTerminalFactory(final ITerminalFactory factory)
	{
		Check.assumeNotNull(factory, "factory not null");
		terminalFactoryHardRef = WeakWrapper.unwrap(factory);
		terminalFactory = WeakWrapper.asWeak(factory, ITerminalFactory.class);
	}

	@Override
	public IKeyLayout getNumericKeyLayout()
	{
		return keyLayoutNumberic;
	}

	@Override
	public void setNumericKeyLayout(final IKeyLayout keyLayout)
	{
		keyLayoutNumberic = keyLayout;
	}

	@Override
	public IKeyLayout getTextKeyLayout()
	{
		return keyLayoutText;
	}

	@Override
	public void setTextKeyLayout(final IKeyLayout keyLayout)
	{
		keyLayoutText = keyLayout;
	}

	@Override
	public int getWindowNo()
	{
		return windowNo;
	}

	@Override
	public void setWindowNo(final int windowNo)
	{
		this.windowNo = windowNo;
	}

	@Override
	public void setAD_User_ID(final int adUserId)
	{
		this.adUserId = adUserId;
	}

	@Override
	public int getAD_User_ID()
	{
		return adUserId;
	}

	@Override
	public boolean isShowDebugInfo()
	{
		if (LogManager.isLevelFinest())
		{
			return true;
		}
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			return true;
		}

		return false;
	}

	@Override
	public <T> void registerService(final Class<T> clazz, final T implementation)
	{
		Check.assumeNotNull(clazz, "Param 'clazz' not null");
		Check.assumeNotNull(implementation, "Param 'implementation' not null");
		Check.assume(clazz.isInterface(), "Param 'clazz' shall be an interface: {}", clazz);

		//
		// If our service is an ITerminalContextService, configure it
		if (implementation instanceof ITerminalContextService)
		{
			final ITerminalContextService terminalContextService = (ITerminalContextService)implementation;
			configureTerminalContextService(terminalContextService);
		}

		services.put(clazz, implementation);
	}

	/**
	 * Configures given {@link ITerminalContextService}.
	 *
	 * @param terminalContextService
	 */
	private final void configureTerminalContextService(final ITerminalContextService terminalContextService)
	{
		// Sets terminal context (as a weak reference)
		terminalContextService.setTerminalContext(WeakWrapper.asWeak(this, ITerminalContext.class));
	}

	@Override
	public <T> T getService(final Class<T> clazz)
	{
		Check.assumeNotNull(clazz, "Param 'clazz' not null");
		Check.assume(clazz.isInterface(), "Param 'clazz' shall be an interface: {}", clazz);

		//
		// Get service implementation from already registered services
		@SuppressWarnings("unchecked")
		final T service = (T)services.get(clazz);

		//
		// Fallback to global registered services
		if (service == null && IService.class.isAssignableFrom(clazz))
		{
			@SuppressWarnings("unchecked")
			final Class<? extends IService> serviceInterface = (Class<? extends IService>)clazz;

			@SuppressWarnings("unchecked")
			final T serviceImpl = (T)Services.get(serviceInterface);
			if (serviceImpl != null)
			{
				if (serviceImpl instanceof ITerminalContextService)
				{
					final ITerminalContextService terminalServiceImpl = (ITerminalContextService)serviceImpl;
					configureTerminalContextService(terminalServiceImpl);

					// Add this service to our services map, because we don't want to reconfigure it next time
					services.put(clazz, serviceImpl);
				}

				return serviceImpl;
			}
		}

		//
		// If there was no service found, fail miseably.
		if (service == null)
		{
			throw new TerminalException("No service found for " + clazz + " in terminal context: " + this);
		}

		return service;
	}

	@Override
	public int getDefaultFontSize()
	{
		return defaultFontSize;
	}

	@Override
	public void setDefaultFontSize(final int fontSize)
	{
		if (fontSize <= 0)
		{
			throw new IllegalArgumentException("fontSize shall be greather or equal to zero");
		}
		defaultFontSize = fontSize;
	}

	@Override
	public Dimension getScreenResolution()
	{
		return new Dimension(screenWidth, screenHeight);
	}

	@Override
	public void setScreenResolution(final Dimension screenResolution)
	{
		Check.assumeNotNull(screenResolution, "screenResolution not null");
		screenWidth = (int)screenResolution.getWidth();
		screenHeight = (int)screenResolution.getHeight();
	}

	@Override
	public ITerminalContextReferences newReferences()
	{
		// Create new references instane
		final TerminalContextReferences references = new TerminalContextReferences();

		// Add it to references list
		referencesList.add(references);

		// Set it as the current one
		currentReferences = references;

		// return it
		return references;
	}

	@Override
	public void deleteReferences(final ITerminalContextReferences references)
	{
		// If references is null, there is nothing to destroy
		if (references == null)
		{
			return;
		}

		//
		// Iterate all references and remove ours from the list
		final Iterator<ITerminalContextReferences> referencesIterator = referencesList.iterator();
		while (referencesIterator.hasNext())
		{
			final ITerminalContextReferences r = referencesIterator.next();
			if (Util.same(r, references))
			{
				referencesIterator.remove();
			}
		}

		//
		// If our "references" is actually the current one, we need to change the "current references" to the last added one
		// because ours will be destroyed
		if (Util.same(references, currentReferences))
		{
			if (!referencesList.isEmpty())
			{
				currentReferences = referencesList.get(referencesList.size() - 1);
			}
			else
			{
				// shall not happen...
				currentReferences = newReferences();
			}
		}

		//
		// Destroy given references
		references.dispose();
	}

	/**
	 * Destroys all {@link ITerminalContextReferences}, including the current one.
	 *
	 * This method will re-initialize the {@link #currentReferences} to leave the context in a consistent state.
	 */
	private final void disposeAllReferences()
	{
		// Iterate all references and dispose them
		for (final ITerminalContextReferences references : referencesList)
		{
			references.dispose();
		}
		referencesList.clear();

		// Make sure current references were also destroyed
		// We do this just to be sure we destroyed everything, even this is pointless because "current references" were contained in references list
		if (currentReferences != null)
		{
			currentReferences.dispose();
			currentReferences = null;
		}

		// Re-initialize current references, just to leave the context in a consistent state
		currentReferences = newReferences();
	}

	private final void disposeAllServices()
	{
		for (final Object service : services.values())
		{
			if (service instanceof ITerminalContextService)
			{
				final ITerminalContextService teminalContextService = (ITerminalContextService)service;
				teminalContextService.dispose();
			}
		}
		services.clear();
	}

	@Override
	public void dispose()
	{
		reset();

		_disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return _disposed;
	}

	@Override
	public WeakPropertyChangeSupport createPropertyChangeSupport(final Object sourceBean)
	{
		return currentReferences.createPropertyChangeSupport(sourceBean);
	}

	@Override
	public WeakPropertyChangeSupport createPropertyChangeSupport(final Object sourceBean, final boolean weakDefault)
	{
		return currentReferences.createPropertyChangeSupport(sourceBean, weakDefault);
	}

	@Override
	public void addToDisposableComponents(final IDisposable comp)
	{
		currentReferences.addToDisposableComponents(comp);
	}
}
