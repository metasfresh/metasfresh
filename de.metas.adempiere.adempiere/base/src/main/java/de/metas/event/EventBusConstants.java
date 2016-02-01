package de.metas.event;

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


import java.lang.management.ManagementFactory;
import java.util.UUID;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.compiere.Adempiere;
import org.compiere.util.CLogger;

/**
 * Misc {@link IEventBus} related constants.
 *
 * @author tsa
 *
 */
public final class EventBusConstants
{
	private static final String LOGGER_NAME = CLogger.createModuleLoggerName(EventBusConstants.class.getPackage().getName() + ".EventBus");

	public static CLogger getLogger()
	{
		return CLogger.getCLogger(LOGGER_NAME);
	}

	public static final String SYSCONFIG_Enabled = "de.metas.event.Enabled";
	private static boolean distributedEventsEnabled = true;

	/**
	 * @return true if distributed event system is enabled
	 */
	public static boolean isEnabled()
	{
		if (!distributedEventsEnabled)
		{
			return false;
		}
		final boolean defaultValue = false; // default false, also because we don't want to affect unit tests
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_Enabled, defaultValue);
	}

	/**
	 * Locally disable distributed events.
	 *
	 * So, EventBus system will work, but all busses will be local, nothing will be broadcasted on network.
	 *
	 * To be used by tools build on top of ADempiere, which require only a minimal set of functionalities.
	 */
	public static void disableDistributedEvents()
	{
		if (!distributedEventsEnabled)
		{
			return;
		}

		distributedEventsEnabled = false;
		getLogger().info("Distributed events broadcasting disabled");
	}

	private static final String SYSCONFIG_JMS_URL = "de.metas.event.jms.URL";

	public static final String getJmsURL()
	{
		final String jmsURL = Services.get(ISysConfigBL.class).getValue(EventBusConstants.SYSCONFIG_JMS_URL, ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);
		Check.assumeNotEmpty(jmsURL, "sysconfig '{0}' shall not be empty", EventBusConstants.SYSCONFIG_JMS_URL);
		return jmsURL;
	}

	private static final String SYSCONFIG_JMS_USER = "de.metas.event.jms.User";

	public static final String getJmsUser()
	{
		final String jmsUser = Services.get(ISysConfigBL.class).getValue(EventBusConstants.SYSCONFIG_JMS_USER, ActiveMQConnectionFactory.DEFAULT_USER);
		return jmsUser;
	}

	private static final String SYSCONFIG_JMS_PASSWORD = "de.metas.event.jms.Password";

	public static final String getJmsPassword()
	{
		final String jmsPassword = Services.get(ISysConfigBL.class).getValue(EventBusConstants.SYSCONFIG_JMS_PASSWORD, ActiveMQConnectionFactory.DEFAULT_PASSWORD);
		return jmsPassword;
	}

	private static final String SYSCONFIG_JMS_UseEmbeddedBroker = "de.metas.event.jms.UseEmbeddedBroker";

	/**
	 * @return true if we shall start and embedded JMS broker (i.e. a JMS server), using {@link #getJmsURL()} as the URL where it shall be bound
	 */
	public static boolean isUseEmbeddedBroker()
	{
		final boolean defaultValue = false; // by default don't start the embedded broker if we were not asked
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_JMS_UseEmbeddedBroker, defaultValue);
	}

	/**
	 * Topic used for general notifications. To be used mainly for broadcasting messages to everybody.
	 */
	public static final Topic TOPIC_GeneralNotifications = Topic.builder()
			.setName("de.metas.event.GeneralNotifications")
			.setType(Type.REMOTE)
			.build();
	/**
	 * Topic used for general notifications inside this JVM instance.
	 *
	 * Compared to {@link #TOPIC_GeneralNotifications}, this topic is NOT broadcasting the events remotely.
	 */
	public static final Topic TOPIC_GeneralNotificationsLocal = Topic.builder()
			.setName("de.metas.event.GeneralNotifications")
			.setType(Type.LOCAL)
			.build();

	public static final String JMX_BASE_NAME = "de.metas.event.EventBus";

	/** World wide unique Sender ID of this JVM instance */
	private static final String SENDER_ID = ManagementFactory.getRuntimeMXBean().getName() + "-" + UUID.randomUUID().toString();

	/** @return world wide unique Sender ID of this JVM instance */
	public static final String getSenderId()
	{
		return SENDER_ID;
	}

	/** @return true of calls to {@link IEventBus#postEvent(Event)} shall be performed asynchronously */
	public static final boolean isEventBusPostEventsAsync()
	{
		// NOTE: in case of unit tests which are checking what notifications were arrived,
		// allowing the events to be posted async could be a problem because the event might arrive after the check.
		if (Adempiere.isUnitTestMode())
		{
			return false;
		}

		// Default: yes, we go async all the way!
		return true;
	}

	private EventBusConstants()
	{
		super();
	}
}
