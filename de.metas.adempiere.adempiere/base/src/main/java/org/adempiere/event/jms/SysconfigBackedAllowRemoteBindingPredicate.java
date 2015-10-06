package org.adempiere.event.jms;

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


import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.event.EventBusConstants;
import org.adempiere.event.IEventBus;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.eventbus.EventBus;

/**
 * Predicate used to determine if an {@link EventBus} shall be bound to a remote connection.
 *
 * @author tsa
 *
 */
class SysconfigBackedAllowRemoteBindingPredicate implements Predicate<IEventBus>
{
	// services
	private static final transient CLogger logger = EventBusConstants.getLogger();
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * Defines the prefix for our remote subscription allowance rules.
	 *
	 * A rule, is one sysconfig record, with following format (Name = Value):
	 *
	 * <pre>
	 * topicname.loginUserId = Y - Allow / N - Deny
	 * </pre>
	 *
	 */
	static final String SYSCONFIG_PREFIX_AllowedSubscriptions = "org.adempiere.event.remote.subscription.";

	static final String AnyTopicName = "*";
	static final int AnyLoginUserId = Integer.MIN_VALUE;

	/**
	 * value to return in case no rule was matched
	 */
	private final boolean defaultAllowance = true;

	@Override
	public boolean apply(final IEventBus eventBus)
	{
		// If running on server side, then always bind
		if (!Ini.isClient())
		{
			return true;
		}

		try
		{
			final String topicName = eventBus.getName();
			return applyForTopicNameAndLoginUser(topicName);
		}
		catch (final Exception e)
		{
			logger.log(Level.WARNING, "Failed checking the rules table for " + eventBus + ". Returning DISALLOW", e);
			return false; // disallow
		}
	}

	@VisibleForTesting
	boolean applyForTopicNameAndLoginUser(final String topicName)
	{
		//
		// Retrieve the allowance map
		// The entries shall be in following format:
		// topicName.loginUserId = Y/N
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final Map<String, String> allowanceMap = sysConfigBL.getValuesForPrefix(
				SYSCONFIG_PREFIX_AllowedSubscriptions,
				true, // removePrefix,
				adClientId,
				adOrgId);
		// If there are no rules, there is no point to check forward
		if (allowanceMap.isEmpty())
		{
			return defaultAllowance;
		}

		//
		// Get selectors
		final int loginUserId = Env.getAD_User_ID(ctx);

		// Check: topicName.loginUserId
		{
			final Boolean allowed = checkAllowed(allowanceMap, topicName, loginUserId);
			if (allowed != null)
			{
				return allowed;
			}
		}

		// Check: topicName.AnyUser
		{
			final Boolean allowed = checkAllowed(allowanceMap, topicName, AnyLoginUserId);
			if (allowed != null)
			{
				return allowed;
			}
		}

		// Check: AnyTopic.loginUserId
		{
			final Boolean allowed = checkAllowed(allowanceMap, AnyTopicName, loginUserId);
			if (allowed != null)
			{
				return allowed;
			}
		}

		// Check: AnyTopic.AnyUser
		{
			final Boolean allowed = checkAllowed(allowanceMap, AnyTopicName, AnyLoginUserId);
			if (allowed != null)
			{
				return allowed;
			}
		}

		return defaultAllowance;
	}

	private static final Boolean checkAllowed(final Map<String, String> map, final String topicName, final int loginUserId)
	{
		final String selector = createSelector(topicName, loginUserId);
		if (!map.containsKey(selector))
		{
			return null; // unknown
		}

		final boolean allowed = DisplayType.toBoolean(map.get(selector), false);
		logger.log(Level.FINE, "Rule matched {0} => allow={1}", new Object[] { selector, allowed });
		return allowed;
	}

	@VisibleForTesting
	static final String createSelector(final String topicName, final int loginUserId)
	{
		return (topicName == AnyTopicName ? "*" : topicName)
				+ "." + (loginUserId == AnyLoginUserId ? "*" : loginUserId);
	}
}
