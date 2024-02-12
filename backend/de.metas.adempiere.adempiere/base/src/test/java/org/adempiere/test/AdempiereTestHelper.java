package org.adempiere.test;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Stopwatch;
import de.metas.JsonObjectMapperHolder;
import de.metas.adempiere.form.IClientUI;
import de.metas.cache.CacheMgt;
import de.metas.cache.interceptor.CacheInterceptor;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.IService;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.Services.IServiceImplProvider;
import de.metas.util.UnitTestServiceNamePolicy;
import de.metas.util.lang.UIDStringUtil;
import io.github.jsonSnapshot.SnapshotConfig;
import io.github.jsonSnapshot.SnapshotMatcher;
import io.github.jsonSnapshot.SnapshotMatchingStrategy;
import io.github.jsonSnapshot.matchingstrategy.JSONAssertMatchingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.impl.POJOQuery;
import org.adempiere.ad.persistence.cache.AbstractModelListCacheLocal;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.context.SwingContextProvider;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.proxy.Cached;
import org.adempiere.util.proxy.impl.JavaAssistInterceptor;
import org.adempiere.util.reflect.TestingClassInstanceProvider;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_System;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Util;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
 * Helper to be used in order to setup ANY test which depends on ADempiere.
 *
 * @author tsa
 */
public class AdempiereTestHelper
{
	private static final AdempiereTestHelper instance = new AdempiereTestHelper();

	public static final String AD_LANGUAGE = "de_DE";

	/**
	 * This config makes sure that the snapshot files end up in {@code src/test/resource/} so they make it into the test jars
	 */
	public static final SnapshotConfig SNAPSHOT_CONFIG = new SnapshotConfig()
	{
		@Override
		public String getFilePath()
		{
			return "src/test/resources/";
		}

		@Override
		public SnapshotMatchingStrategy getSnapshotMatchingStrategy()
		{
			return JSONAssertMatchingStrategy.INSTANCE_STRICT;
		}
	};

	public static AdempiereTestHelper get()
	{
		return instance;
	}

	private boolean staticInitialized = false;

	/**
	 * One time only cleanup tasks
	 */
	private final ArrayList<CleanupTask> cleanupTasks = new ArrayList<>();

	public void staticInit()
	{
		if (staticInitialized)
		{
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();

		staticInit0();

		log("staticInit", "done in " + stopwatch);
	}

	public void forceStaticInit()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		staticInit0();

		log("forceStaticInitialize", "done in " + stopwatch);
	}

	public void init()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		// First, run previously scheduled cleanup tasks
		runCleanupTasks();

		// Make sure context is clear before starting a new test
		final Properties ctx = setupContext();

		// By default we are running in client mode
		Ini.setClient(true);

		// Make sure staticInit was called
		staticInit();

		POJOQuery.clear_UUID_TO_PAGE();

		// Make sure database is clean
		POJOLookupMap.resetAll();

		// we also don't want any model interceptors to interfere, unless we explicitly test them up to do so
		Objects.requireNonNull(POJOLookupMap.get()).clear();

		//
		// POJOWrapper defaults
		POJOWrapper.setAllowRefreshingChangedModels(false);
		POJOWrapper.setDefaultStrictValues(POJOWrapper.DEFAULT_StrictValues);

		// Setup services
		{
			// Make sure we don't have custom registered services
			// Each test shall init it's services if it wants
			Services.clear();

			SpringContextHolder.instance.clearJUnitRegisteredBeans();

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

		// Reset System Time and random UUID
		SystemTime.resetTimeSource();
		UIDStringUtil.reset();

		// Caching
		AbstractModelListCacheLocal.DEBUG = true;
		CacheMgt.get().reset();

		// Logging
		LogManager.setLevel(Level.WARN);
		Loggables.temporarySetLoggable(Loggables.nop());

		// JSON
		JsonObjectMapperHolder.resetSharedJsonObjectMapper();

		createSystemRecords();

		log("init", "done in " + stopwatch + " (NOTE: it might include staticInit time too)");
	}

	private static void log(final String methodName, final String message)
	{
		System.out.println("" + AdempiereTestHelper.class.getSimpleName() + "." + methodName + ": " + message);
	}

	private static Properties setupContext()
	{
		Env.setContextProvider(new SwingContextProvider());

		final Properties ctx = Env.getCtx();
		ctx.clear();
		return ctx;
	}

	public static void setupContext_AD_Client_IfNotSet()
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

	private static void createSystemRecords()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final I_AD_System system = InterfaceWrapperHelper.newInstance(I_AD_System.class);
		system.setAD_System_ID(1234); // don't use the "normal" unit test counter or every ID in every snapshot file with need to be +1ed
		system.setName("AdempiereTestHelper");
		InterfaceWrapperHelper.saveRecord(system);

		final I_AD_Org allOrgs = newInstance(I_AD_Org.class);
		allOrgs.setAD_Org_ID(OrgId.ANY.getRepoId());
		save(allOrgs);

		final org.compiere.model.I_AD_User systemUser = newInstance(I_AD_User.class);
		systemUser.setAD_User_ID(UserId.SYSTEM.getRepoId());
		save(systemUser);

		final I_M_AttributeSetInstance noAsi = newInstance(I_M_AttributeSetInstance.class);
		noAsi.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		save(noAsi);

		log("createSystemRecords", "done in " + stopwatch);
	}

	public static void createClientInfo()
	{
		final Properties ctx = Env.getCtx();

		final int clientId = Env.getAD_Client_ID(ctx);
		if (clientId <= 0)
		{
			return;
		}

		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(ctx);

		final I_AD_ClientInfo clientInfo = InterfaceWrapperHelper.newInstance(I_AD_ClientInfo.class, contextProvider);
		InterfaceWrapperHelper.setValue(clientInfo, I_AD_ClientInfo.COLUMNNAME_AD_Client_ID, clientId);
		InterfaceWrapperHelper.save(clientInfo);
	}

	public static OrgId createOrgWithTimeZone(@NonNull final String nameAndValue)
	{
		return createOrgWithTimeZone(nameAndValue, ZoneId.of("Europe/Berlin"));
	}

	public static OrgId createOrgWithTimeZone()
	{
		return createOrgWithTimeZone("org", ZoneId.of("Europe/Berlin"));
	}

	public static OrgId createOrgWithTimeZone(@NonNull final ZoneId timeZone)
	{
		return createOrgWithTimeZone("org", timeZone);
	}

	public static OrgId createOrgWithTimeZone(
			@NonNull final String nameAndValue,
			@NonNull final ZoneId timeZone)
	{
		final I_AD_Org orgRecord = newInstanceOutOfTrx(I_AD_Org.class);
		orgRecord.setValue(nameAndValue);
		orgRecord.setName(nameAndValue);
		saveRecord(orgRecord);

		final I_AD_OrgInfo orgInfoRecord = newInstanceOutOfTrx(I_AD_OrgInfo.class);
		orgInfoRecord.setAD_Org_ID(orgRecord.getAD_Org_ID());
		orgInfoRecord.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		orgInfoRecord.setTimeZone(timeZone.getId());
		saveRecord(orgInfoRecord);

		return OrgId.ofRepoId(orgRecord.getAD_Org_ID());
	}

	/**
	 * Create JSON serialization function to be used by {@link SnapshotMatcher#start(SnapshotConfig, Function)}.
	 * <p>
	 * The function is using our {@link JsonObjectMapperHolder#newJsonObjectMapper()} with a pretty printer.
	 *
	 * @deprecated Consider using de.metas.test.SnapshotFunctionFactory
	 */
	@Deprecated
	public static Function<Object, String> createSnapshotJsonFunction()
	{
		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final ObjectWriter writerWithDefaultPrettyPrinter = jsonObjectMapper.writerWithDefaultPrettyPrinter();
		return object -> {
			try
			{
				return writerWithDefaultPrettyPrinter.writeValueAsString(object);
			}
			catch (final JsonProcessingException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		};
	}

	public void onCleanup(@NonNull String name, @NonNull Runnable runnable)
	{
		final CleanupTask task = new CleanupTask(name, runnable);
		cleanupTasks.add(task);
		log("onCleanup", "Scheduled task: " + task.getName());
	}

	private void runCleanupTasks()
	{
		for (final Iterator<CleanupTask> it = cleanupTasks.iterator(); it.hasNext(); )
		{
			final CleanupTask task = it.next();

			task.run();
			log("runCleanupTasks", "Executed task: " + task.getName());

			it.remove();
		}
	}

	@AllArgsConstructor
	@ToString(of = "name")
	private static class CleanupTask
	{
		@Getter @NonNull private final String name;
		@NonNull private final Runnable runnable;

		public void run() {runnable.run();}
	}

	private void staticInit0()
	{
		Adempiere.enableUnitTestMode();
		Language.setUseJUnitFixedFormats(false);
		POJOLookupMap.resetToDefaultNextIdSupplier();

		Check.setDefaultExClass(AdempiereException.class);

		Util.setClassInstanceProvider(TestingClassInstanceProvider.instance);

		//
		// Configure services; note the this is not the place to register individual services, see init() for that.
		Services.setAutodetectServices(true);
		Services.setServiceNameAutoDetectPolicy(new UnitTestServiceNamePolicy()); // 04113
		Services.setExternalServiceImplProvider(new IServiceImplProvider()
		{
			@Override
			public <T extends IService> T provideServiceImpl(final Class<T> serviceClazz)
			{
				return SpringContextHolder.instance.getBeanOr(serviceClazz, null);
			}
		});

		//
		// Make sure cache is empty
		CacheMgt.get().reset();

		staticInitialized = true;
	}
}
