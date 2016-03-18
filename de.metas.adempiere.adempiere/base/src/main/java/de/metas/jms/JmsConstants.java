package de.metas.jms;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.compiere.util.CLogger;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class JmsConstants
{
	public static final String TCP_HOSTNAME_PORT = "tcp://{0}:{1}";

	private static final String LOGGER_NAME = CLogger.createModuleLoggerName(JmsConstants.class.getPackage().getName() + ".Jms");

	public static CLogger getLogger()
	{
		return CLogger.getCLogger(LOGGER_NAME);
	}

	public static final String SYSCONFIG_JMS_URL = "de.metas.jms.URL";



	private static final String SYSCONFIG_JMS_USER = "de.metas.jms.User";

	public static final String getJmsUser()
	{
		final String jmsUser = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_JMS_USER, ActiveMQConnectionFactory.DEFAULT_USER);
		if (Check.isEmpty(jmsUser, true) || "-".equals(jmsUser.trim()))
		{
			return null;
		}
		return jmsUser;
	}

	private static final String SYSCONFIG_JMS_PASSWORD = "de.metas.jms.Password";

	public static final String getJmsPassword()
	{
		final String jmsPassword = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_JMS_PASSWORD, ActiveMQConnectionFactory.DEFAULT_PASSWORD);
		if (Check.isEmpty(jmsPassword, true) || "-".equals(jmsPassword.trim()))
		{
			return null;
		}
		return jmsPassword;
	}

	private static final String SYSCONFIG_JMS_UseEmbeddedBroker = "de.metas.jms.UseEmbeddedBroker";

	/**
	 * @return true if we shall start and embedded JMS broker (i.e. a JMS server), using {@link #getJmsURL()} as the URL where it shall be bound
	 */
	public static boolean isUseEmbeddedBroker()
	{
		final boolean defaultValue = false; // by default don't start the embedded broker if we were not asked
		return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_JMS_UseEmbeddedBroker, defaultValue);
	}
}
