package org.adempiere.ui.notifications;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.Set;

import org.adempiere.util.jmx.IJMXNameAware;

import de.metas.event.EventBusConfig;

public class JMXSwingEventNotifierService implements JMXSwingEventNotifierServiceMBean, IJMXNameAware
{
	private final String jmxName;

	public JMXSwingEventNotifierService()
	{
		super();
		this.jmxName = EventBusConfig.JMX_BASE_NAME + ":type=SwingNotifier";
	}

	@Override
	public String getJMXName()
	{
		return jmxName;
	}
	
	@Override
	public boolean isStarted()
	{
		return SwingEventNotifierService.getInstance().isRunning();
	}
	
	@Override
	public void start()
	{
		SwingEventNotifierService.getInstance().start();
	}

	@Override
	public void stop()
	{
		SwingEventNotifierService.getInstance().stop();
	}

	@Override
	public String[] getSubscribedTopicNames()
	{
		final Set<String> subscribedTopicNames = SwingEventNotifierService.getInstance().getSubscribedTopicNames();
		return subscribedTopicNames.toArray(new String[subscribedTopicNames.size()]);
	}

}
