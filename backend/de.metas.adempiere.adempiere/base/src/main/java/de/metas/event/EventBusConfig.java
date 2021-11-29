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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.UUID;

/**
 * Misc {@link IEventBus} related constants.
 *
 * @author tsa
 */
public final class EventBusConfig
{
	public static Logger getLogger(final Class<?> clazz)
	{
		return LogManager.getLogger(clazz);
	}

	private static boolean distributedEventsEnabled = true;

	private EventBusConfig()
	{
	}

	/**
	 * @return true if distributed event system is enabled
	 */
	public static boolean isEnabled()
	{
		return distributedEventsEnabled;
	}

	/**
	 * Locally disable distributed events.
	 * <p>
	 * So, EventBus system will work, but all busses will be local, nothing will be broadcasted on network.
	 * <p>
	 * To be used by tools build on top of ADempiere, which require only a minimal set of functionalities.
	 */
	public static void disableDistributedEvents()
	{
		if (!distributedEventsEnabled)
		{
			return;
		}

		distributedEventsEnabled = false;
		getLogger(EventBusConfig.class).info("Distributed events broadcasting disabled");
	}

	/**
	 * Topic used for general notifications. To be used mainly for broadcasting messages to everybody.
	 */
	public static final Topic TOPIC_GeneralUserNotifications = Topic.remote("de.metas.event.GeneralNotifications");

	/**
	 * Topic used for general notifications inside this JVM instance.
	 * <p>
	 * Compared to {@link #TOPIC_GeneralUserNotifications}, this topic is NOT broadcasting the events remotely.
	 */
	public static final Topic TOPIC_GeneralUserNotificationsLocal = TOPIC_GeneralUserNotifications.toLocal();

	public static final String JMX_BASE_NAME = "de.metas.event.EventBus";

	/** World wide unique Sender ID of this JVM instance */
	private static final String SENDER_ID = ManagementFactory.getRuntimeMXBean().getName() + "-" + UUID.randomUUID().toString();

	/** @return world wide unique Sender ID of this JVM instance */
	public static String getSenderId()
	{
		return SENDER_ID;
	}

	/** @return true of calls to {@link IEventBus#postEvent(Event)} shall be performed asynchronously */
	public static boolean isEventBusPostAsync(@NonNull final Topic topic)
	{
		// NOTE: in case of unit tests which are checking what notifications were arrived,
		// allowing the events to be posted async could be a problem because the event might arrive after the check.
		if (Adempiere.isUnitTestMode())
		{
			return false;
		}

		final String nameForAllTopics = "de.metas.event.asyncEventBus";
		final Map<String, String> valuesForPrefix = Services.get(ISysConfigBL.class).getValuesForPrefix(nameForAllTopics, ClientAndOrgId.SYSTEM);

		final String keyForTopic = nameForAllTopics + ".topic_" + topic.getName();
		final String valueForTopic = valuesForPrefix.get(keyForTopic);
		if (Check.isNotBlank(valueForTopic))
		{
			getLogger(EventBusConfig.class).debug("SysConfig returned value={} for keyForTopic={}", valueForTopic, keyForTopic);
			return StringUtils.toBoolean(valueForTopic, false);
		}

		final String standardValue = valuesForPrefix.get(nameForAllTopics);
		getLogger(EventBusConfig.class).debug("SysConfig returned value={} for keyForTopic={}", standardValue, keyForTopic);
		return StringUtils.toBoolean(standardValue, false);
	}

	public static boolean isMonitorIncomingEvents()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue("de.metas.event.MonitorIncomingEvents", false);
	}

}
