package org.adempiere.util.jmx;

/*
 * #%L
 * de.metas.util
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

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.adempiere.util.jmx.exceptions.JMXException;

import de.metas.util.Check;

/**
 * Helper class for registering and un-registering JMX MBeans.
 * 
 * @author tsa
 *
 */
public class JMXRegistry
{
	public static final transient JMXRegistry instance = new JMXRegistry();

	public static JMXRegistry get()
	{
		return instance;
	}

	/**
	 * What to do when the JMX we want to register already exists
	 */
	public static enum OnJMXAlreadyExistsPolicy
	{
		/**
		 * Fail by throwning an {@link JMXException}.
		 */
		Fail,
		/**
		 * Skip registration.
		 */
		Ignore,
		/**
		 * Replace already existing JMX bean with our bean.
		 */
		Replace,
	}

	private JMXRegistry()
	{
		super();
	}

	/**
	 * Register given JMX Bean.
	 * 
	 * @param jmxBean JMX bean to be registered
	 * @param onJMXAlreadyExistsPolicy what to do if a bean with same JMX Name already exists
	 * @return JMX's {@link ObjectName} which was registered
	 * @throws JMXException if registration failed
	 */
	public <T extends IJMXNameAware> ObjectName registerJMX(final T jmxBean, final OnJMXAlreadyExistsPolicy onJMXAlreadyExistsPolicy)
	{
		Check.assumeNotNull(jmxBean, "jmxBean not null");

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		//
		// Create JMX ObjectName
		final String jmxName = jmxBean.getJMXName();
		final ObjectName jmxObjectName;
		try
		{
			jmxObjectName = new ObjectName(jmxName);
		}
		catch (MalformedObjectNameException e)
		{
			throw new JMXException("Unable to create JMX ObjectName for JMX Name '" + jmxName + "'", e);
		}

		try
		{
			synchronized (mbs)
			{
				//
				// Check if JMX Bean was already registered
				if (mbs.isRegistered(jmxObjectName))
				{
					switch (onJMXAlreadyExistsPolicy)
					{
						case Ignore:
							return null;
						case Replace:
							mbs.unregisterMBean(jmxObjectName);
							break;
						case Fail:
						default:
							throw new JMXException("A JMX bean was already registed for " + jmxName);
					}
				}

				//
				// Try registering or JMX bean
				mbs.registerMBean(jmxBean, jmxObjectName);
			}
		}
		catch (JMXException e)
		{
			throw e;
		}
		catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | InstanceNotFoundException e)
		{
			throw new JMXException("Unable to register mbean for JMX Name'" + jmxName + "'", e);
		}

		return jmxObjectName;
	}

	/**
	 * Unregister JMX Bean for given <code>jmxObjectName</code>
	 * 
	 * @param jmxObjectName
	 * @param failOnError
	 * @return true if successfully unregistered
	 * @throws JMXException if unregistration failed and <code>failOnError</code> is <code>true</code>.
	 */
	public boolean unregisterJMX(final ObjectName jmxObjectName, final boolean failOnError)
	{
		if (jmxObjectName == null)
		{
			if (failOnError)
			{
				throw new JMXException("Invalid JMX ObjectName: " + jmxObjectName);
			}
			return false;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		boolean success = false;
		try
		{
			mbs.unregisterMBean(jmxObjectName);
			success = true;
		}
		catch (Exception e)
		{
			if (failOnError)
			{
				throw new JMXException("Cannot unregister " + jmxObjectName, e);
			}
			success = false;
		}

		return success;
	}

}
