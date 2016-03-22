package org.adempiere.webui.session;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * ADempiere WebUI session configurator
 * 
 * @author tsa
 * 
 */
public class WebUIHttpSessionListener implements javax.servlet.http.HttpSessionListener
{
	private static final transient Logger logger = LogManager.getLogger(WebUIHttpSessionListener.class);

	public static final String SYSCONFIG_ZkSessionTimeout = "org.adempiere.webui.session.SessionTimeout";
	public static final int DEFAULT_ZkSessionTimeout = 60 * 60; // 1hour

	@Override
	public void sessionCreated(final HttpSessionEvent se)
	{
		final HttpSession session = se.getSession();

		//
		// Config Session Timeout
		final int sessionTimeoutSec = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_ZkSessionTimeout, DEFAULT_ZkSessionTimeout);
		session.setMaxInactiveInterval(sessionTimeoutSec);
		logger.info("SysConfig {}={}", new Object[] { SYSCONFIG_ZkSessionTimeout, sessionTimeoutSec });

		if (logger.isInfoEnabled())
		{
			logger.info("Session created: ID={}, CreationTime={}, MaxInactiveInterval(sec)={}",
					new Object[] {
							session.getId(),
							new Date(session.getCreationTime()),
							session.getMaxInactiveInterval()
					});
		}
	}

	@Override
	public void sessionDestroyed(final HttpSessionEvent se)
	{
		if (logger.isInfoEnabled())
		{
			final HttpSession session = se.getSession();
			logger.info("Session destroyed: ID={}, CreationTime={}, LastAccessedTime{}=",
					new Object[] {
							session.getId(),
							new Date(session.getCreationTime()),
							new Date(session.getLastAccessedTime())
					});
		}
	}
}
