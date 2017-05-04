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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import com.google.common.collect.ImmutableList;

import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;

/**
 * To be extended by module/project main interceptors.
 *
 * This interceptor shall be used only for:
 * <ul>
 * <li>module configuration
 * <li>sub-sequent interceptors and callouts registration
 * </ul>
 * Note that module interceptors can also be registered in the "the normal ways" like any other model insterceptor. For example  via {@link IModelValidationEngine#addModelValidator(Object, I_AD_Client)}.
 *
 * @author tsa
 *
 */
public abstract class AbstractModuleInterceptor extends AbstractModelInterceptor
{
	/**
	 * Called when module is about to be initialized
	 *
	 * @param engine
	 * @param client
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		Check.assume(Services.isAutodetectServices(), "We work with activated service auto detection");

		registerInterceptors(engine, client);
		registerTabCallouts(Services.get(ITabCalloutFactory.class));
		registerCallouts(Services.get(IProgramaticCalloutProvider.class));
		setupCaching(Services.get(IModelCacheService.class));
		setupEventBus();
		onAfterInit();
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
	 *
	 * @param cachingService
	 */
	protected void setupCaching(final IModelCacheService cachingService)
	{
		// nothing on this level
	}

	/**
	 * Called onInit to register module interceptors
	 *
	 * @param engine
	 * @param client
	 */
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
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
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		// nothing on this level
	}

	private final void setupEventBus()
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

	/** @return available user notifications topics to listen */
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of();
	}

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing
	}

	/** Does nothing. Module interceptors are not allowed to intercept models or documents */
	@Override
	public final void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		// nothing
	}

	/** Does nothing. Module interceptors are not allowed to intercept models or documents */
	@Override
	public final void onDocValidate(final Object model, final DocTimingType timing) throws Exception
	{
		// nothing
	}
}
