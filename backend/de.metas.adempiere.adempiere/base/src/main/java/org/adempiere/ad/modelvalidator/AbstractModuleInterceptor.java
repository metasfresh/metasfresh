package org.adempiere.ad.modelvalidator;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.model.IModelCacheService;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.compiere.model.I_AD_Client;

import java.util.List;
import java.util.Set;

/**
 * To be extended by module/project main interceptors.
 * <p>
 * This interceptor shall be used only for:
 * <ul>
 * <li>module configuration
 * <li>sub-sequent interceptors and callouts registration
 * </ul>
 * Note that module interceptors can also be registered in the "the normal ways" like any other model interceptor. For example via {@link IModelValidationEngine#addModelValidator(Object, I_AD_Client)}.
 *
 * @author tsa
 */
public abstract class AbstractModuleInterceptor extends AbstractModelInterceptor
{
	@Override
	protected final void onInit(@NonNull final IModelValidationEngine engine, final I_AD_Client client_NOTUSED)
	{
		Check.assumeNull(client_NOTUSED, "client shall be null but it was {}", client_NOTUSED);
		Check.assume(Services.isAutodetectServices(), "We work with activated service auto detection");

		onBeforeInit();
		registerInterceptors(engine);
		registerTabCallouts(Services.get(ITabCalloutFactory.class));
		registerCallouts(Services.get(IProgramaticCalloutProvider.class));
		setupCaching(Services.get(IModelCacheService.class));
		setupEventBus();
		setupMigrationScriptsLogger();
		onAfterInit();
	}

	/**
	 * Called by {@link #onInit(IModelValidationEngine, I_AD_Client)} right before anything else.
	 */
	protected void onBeforeInit()
	{
		// nothing at this level
	}

	/**
	 * Called by {@link #onInit(IModelValidationEngine, I_AD_Client)} right before method execution is finishing.
	 */
	protected void onAfterInit()
	{
		// nothing at this level
	}

	/**
	 * Called onInit to setup module table caching
	 */
	protected void setupCaching(final IModelCacheService cachingService)
	{
		// nothing on this level
	}

	/**
	 * Called onInit to register module interceptors
	 */
	protected void registerInterceptors(@NonNull final IModelValidationEngine engine)
	{
		// nothing on this level
	}

	/**
	 * Called onInit to setup tab level callouts
	 *
	 * @param tabCalloutsRegistry
	 */
	protected void registerTabCallouts(final ITabCalloutFactory tabCalloutsRegistry)
	{
		// nothing on this level
	}

	/**
	 * Called onInit to setup module table callouts
	 *
	 * @param calloutsRegistry
	 */
	protected void registerCallouts(@NonNull final IProgramaticCalloutProvider calloutsRegistry)
	{
		// nothing on this level
	}

	private void setupEventBus()
	{
		final List<Topic> userNotificationsTopics = getAvailableUserNotificationsTopics();
		if (userNotificationsTopics != null && !userNotificationsTopics.isEmpty())
		{
			final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
			for (final Topic topic : userNotificationsTopics)
			{
				eventBusFactory.addAvailableUserNotificationsTopic(topic);
			}
		}

	}

	/**
	 * @return available user notifications topics to listen
	 */
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of();
	}

	private void setupMigrationScriptsLogger()
	{
		final Set<String> tableNames = getTableNamesToSkipOnMigrationScriptsLogging();
		if (tableNames != null && !tableNames.isEmpty())
		{
			final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
			migrationLogger.addTablesToIgnoreList(tableNames);
		}
	}

	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging() {return ImmutableSet.of();}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing
	}

	/**
	 * Does nothing. Module interceptors are not allowed to intercept models or documents
	 */
	@Override
	public final void onModelChange(final Object model, final ModelChangeType changeType)
	{
		// nothing
	}

	/**
	 * Does nothing. Module interceptors are not allowed to intercept models or documents
	 */
	@Override
	public final void onDocValidate(final Object model, final DocTimingType timing)
	{
		// nothing
	}
}
