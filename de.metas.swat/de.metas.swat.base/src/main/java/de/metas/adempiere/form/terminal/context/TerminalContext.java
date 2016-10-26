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
import java.util.ArrayList;
import java.util.HashMap;
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
import org.slf4j.Logger;

import com.google.common.collect.Lists;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.logging.LogManager;

public final class TerminalContext implements ITerminalContext, ITerminalContextReferences
{
	private static final Logger logger = LogManager.getLogger(TerminalContext.class);

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
	private TerminalContextReferences currentReferences = null;

	/**
	 * All {@link ITerminalContextReferences} which were created by this terminal context, including {@link #currentReferences}.
	 */
	private final List<TerminalContextReferences> referencesList = new ArrayList<TerminalContextReferences>();

	private boolean _disposed = false;

	/* package */ TerminalContext()
	{
		reset();
	}

	@Override
	public String toString()
	{
		return "TerminalContext ["
				+ "windowNo=" + windowNo
				+ ", adUserId=" + adUserId
				+ ", services=" + services
				+ ", defaultFontSize=" + defaultFontSize
				+ ", screenWidth=" + screenWidth
				+ ", screenHeight=" + screenHeight
				// + ", ctx=" + ctx
				// + ", factory=" + factory
				+ ", referencesList=" + referencesList
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

		assertAllReferencesDeleted();

		disposeAllServices();

		if (terminalFactory != null)
		{
			terminalFactory.dispose();
			terminalFactory = null;
		}

		terminalFactoryHardRef = null;

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
		assertCurrentReferencesNotNull();
		for (int i = referencesList.size() - 1; i >= 0; i--)
		{
			final TerminalContextReferences terminalContextReferences = referencesList.get(i);
			final IKeyLayout numericKeyLayout = terminalContextReferences.getNumericKeyLayout();
			if (numericKeyLayout != null)
			{
				return numericKeyLayout;
			}
		}
		return null;
	}

	@Override
	public void setNumericKeyLayout(final IKeyLayout keyLayout)
	{
		assertCurrentReferencesNotNull();
		getActiveReferences().setNumericKeyLayout(keyLayout);
	}

	@Override
	public IKeyLayout getTextKeyLayout()
	{
		assertCurrentReferencesNotNull();
		for (int i = referencesList.size() - 1; i >= 0; i--)
		{
			final TerminalContextReferences terminalContextReferences = referencesList.get(i);
			final IKeyLayout textKeyLayout = terminalContextReferences.getTextKeyLayout();
			if (textKeyLayout != null)
			{
				return textKeyLayout;
			}
		}
		return null;
	}

	@Override
	public void setTextKeyLayout(final IKeyLayout keyLayout)
	{
		assertCurrentReferencesNotNull();
		getActiveReferences().setTextKeyLayout(keyLayout);
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
	public TerminalContextReferences newReferences()
	{
		// Create new references instance
		final TerminalContextReferences references = new TerminalContextReferences(this);

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
		removeReferences(currentReferences, true);
	}

	@Override
	public void detachReferences(final ITerminalContextReferences references)
	{
		removeReferences(references, false);
	}

	public void removeReferences(final ITerminalContextReferences references, final boolean dispose)
	{
		Check.assumeNotNull(references, "Param 'reference' is not null; this={}", this);

		Check.errorIf(!Util.same(currentReferences, references),
				"Param 'references is not the same as currentReferences; size of referencesList={}; references={} currentReferences={}; this={}",
				referencesList == null ? "<null>" : referencesList.size(),
				references,
				currentReferences,
				this);

		if (dispose)
		{
			// Destroy given references
			currentReferences.dispose();
		}

		referencesList.remove(referencesList.size() - 1);

		if (!referencesList.isEmpty())
		{
			currentReferences = referencesList.get(referencesList.size() - 1);
		}
		else
		{
			currentReferences = null;
		}
	}

	private final void assertAllReferencesDeleted()
	{
		Check.errorIf(!referencesList.isEmpty(), "referencesList is not (yet) empty; referencesList={}", referencesList);

		Check.errorIf(currentReferences != null, "currentReferences is not (yet) null; currentReferences={}", currentReferences);
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

	public boolean isDisposed()
	{
		return _disposed;
	}

	@Override
	public WeakPropertyChangeSupport createPropertyChangeSupport(final Object sourceBean)
	{
		assertCurrentReferencesNotNull();
		return getActiveReferences().createPropertyChangeSupport(sourceBean);
	}

	@Override
	public WeakPropertyChangeSupport createPropertyChangeSupport(final Object sourceBean, final boolean weakDefault)
	{
		assertCurrentReferencesNotNull();
		return getActiveReferences().createPropertyChangeSupport(sourceBean, weakDefault);
	}

	@Override
	public void addToDisposableComponents(final IDisposable comp)
	{
		assertCurrentReferencesNotNull();
		getActiveReferences().addToDisposableComponents(comp);
	}

	private void assertCurrentReferencesNotNull()
	{
		Check.errorIf(currentReferences == null, "currentReferences of this={} is null", this);
	}

	@Override
	public void setContextValue(final String contextName, final int valueInt)
	{
		final int windowNo = getWindowNo();
		if (windowNo <= 0)
		{
			logger.warn("Skip setting context {}={} because WindowNo={}", contextName, valueInt, windowNo);
			return;
		}

		final Properties ctx = getCtx();

		// set a particular property in the context
		Env.setContext(ctx, windowNo, contextName, valueInt);
	}

	private TerminalContextReferences getActiveReferences()
	{
		for (final TerminalContextReferences ref : Lists.reverse(referencesList))
		{
			if (!ref.isReferencesClosed())
			{
				return ref;
			}
		}
		Check.errorIf(true, "Missing active references; this={}", this);
		return null;
	}

	@Override
	public void close()
	{
		dispose();
	}

	@Override
	public void closeCurrentReferences()
	{
		assertCurrentReferencesNotNull();
		currentReferences.closeReferences();
	}
}
