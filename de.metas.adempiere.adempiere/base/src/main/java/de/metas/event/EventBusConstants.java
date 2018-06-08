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

import java.lang.management.ManagementFactory;
import java.util.UUID;

import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Misc {@link IEventBus} related constants.
 *
 * @author tsa
 *
 */
public final class EventBusConstants
{
	public static Logger getLogger(final Class<?> clazz)
	{
		return LogManager.getLogger(clazz);
	}

	private static boolean distributedEventsEnabled = true;


	private EventBusConstants()
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
		getLogger(EventBusConstants.class).info("Distributed events broadcasting disabled");
	}

	/**
	 * Topic used for general notifications. To be used mainly for broadcasting messages to everybody.
	 */
	public static final Topic TOPIC_GeneralNotifications = Topic.builder()
			.name("de.metas.event.GeneralNotifications")
			.type(Type.REMOTE)
			.build();
	/**
	 * Topic used for general notifications inside this JVM instance.
	 *
	 * Compared to {@link #TOPIC_GeneralNotifications}, this topic is NOT broadcasting the events remotely.
	 */
	public static final Topic TOPIC_GeneralNotificationsLocal = Topic.builder()
			.name("de.metas.event.GeneralNotifications")
			.type(Type.LOCAL)
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

}
