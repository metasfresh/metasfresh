package org.adempiere.test;

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


import java.util.Properties;

import org.adempiere.ad.persistence.cache.AbstractModelListCacheLocal;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.context.SwingContextProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.UnitTestServiceNamePolicy;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.adempiere.util.reflect.TestingClassInstanceProvider;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Client;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Util;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.util.cache.CacheInterceptor;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;

/**
 * Helper to be used in order to setup ANY test which depends on ADempiere.
 *
 * @author tsa
 *
 */
public class AdempiereTestHelper
{
	private static final AdempiereTestHelper instance = new AdempiereTestHelper();

	public static final String AD_LANGUAGE = "de_DE";

	public static AdempiereTestHelper get()
	{
		return instance;
	}

	private boolean staticInitialized = false;

	public void staticInit()
	{
		if (staticInitialized)
		{
			return;
		}

		Adempiere.enableUnitTestMode();

		Check.setDefaultExClass(AdempiereException.class);

		Util.setClassInstanceProvider(TestingClassInstanceProvider.instance);

		//
		// Configure services; note the this is not the place to register individual services, see init() for that.
		Services.setAutodetectServices(true);
		Services.setServiceNameAutoDetectPolicy(new UnitTestServiceNamePolicy()); // 04113

		//
		// Make sure cache is empty
		CacheMgt.get().clear();

		staticInitialized = true;
	}

	public void init()
	{
		// Make sure context is clear before starting a new test
		final Properties ctx = setupContext();

		// By default we are running in client mode
		Ini.setClient(true);

		// Make sure staticInit was called
		staticInit();

		// Make sure database is clean
		POJOLookupMap.resetAll();

		//
		// POJOWrapper defaults
		POJOWrapper.setAllowRefreshingChangedModels(false);
		POJOWrapper.setDefaultStrictValues(POJOWrapper.DEFAULT_StrictValues);

		// Setup services
		{
			// Make sure we don't have custom registered services
			// Each test shall init it's services if it wants
			Services.clear();

			//
			// Register our cache interceptor
			// NOTE: in normal run, it is registered from org.compiere.Adempiere.startup(RunMode)
			Services.getInterceptor().registerInterceptor(Cached.class, new CacheInterceptor()); // task 06952
			JavaAssistInterceptor.FAIL_ON_ERROR = true;

			Services.registerService(IClientUI.class, new TestClientUI());
		}

		// Base Language
		Language.setBaseLanguage(() -> AD_LANGUAGE);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, AD_LANGUAGE);

		// Reset System Time
		SystemTime.setTimeSource(null);

		// Caching
		AbstractModelListCacheLocal.DEBUG = true;
		CacheMgt.get().reset();

		// Logging
		LogManager.setLevel(Level.WARN);
}

	private static Properties setupContext()
	{
		Env.setContextProvider(new SwingContextProvider());

		final Properties ctx = Env.getCtx();
		ctx.clear();
		return ctx;
	}

	public void setupContext_AD_Client_IfNotSet()
	{
		final Properties ctx = Env.getCtx();

		// Do nothing if already set
		if (Env.getAD_Client_ID(ctx) > 0)
		{
			return;
		}

		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(ctx);
		final I_AD_Client adClient = InterfaceWrapperHelper.newInstance(I_AD_Client.class, contextProvider);
		adClient.setValue("Test");
		adClient.setName("Test");
		adClient.setAD_Language(AD_LANGUAGE);
		InterfaceWrapperHelper.save(adClient);

		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClient.getAD_Client_ID());
	}
}
