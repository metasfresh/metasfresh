package de.metas.printing.client;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.metas.printing.client.ui.IUserInterface;
import de.metas.printing.client.util.Util;

public class Context implements IContext
{
	public static final String CTX_ROOT = "de.metas.printing.client";

	public static final String CTX_BeanEncoder = "de.metas.printing.client.encoder.IBeanEnconder";
	public static final String DEFAULT_BeanEncoder = de.metas.printing.client.encoder.JsonBeanEncoder.class.getCanonicalName();

	public static final String CTX_PrintConnectionEndpoint = "de.metas.printing.client.IPrintConnectionEndpoint";
	public static final String DEFAULT_PrintConnectionEndpoint = de.metas.printing.client.endpoint.RestHttpPrintConnectionEndpoint.class.getCanonicalName();

	public static final String CTX_TempDir = CTX_ROOT + ".TempDir";
	public static final String DEFAULT_TempDir = System.getProperty("java.io.tmpdir");

	public static final String CTX_SessionId = "sessionId";

	/**
	 * @deprecated To connect with the metasfresh EP (as opposed to servicemix), an API token needs to be specified instead of user/password.<br>
	 * 			See https://github.com/metasfresh/metasfresh/issues/3688
	 */
	@Deprecated
	public static final String CTX_Login_Username = CTX_ROOT + ".login.username";

	/**
	 * See {@link #CTX_Login_Username}
	 */
	@Deprecated
	public static final String CTX_Login_Password = CTX_ROOT + ".login.password";

	public static final String CTX_Login_ApiToken = CTX_ROOT + ".login.apiToken";

	public static final String CTX_Login_HostKey = CTX_ROOT + ".login.hostkey";

	// task 09618 begin
	public static final String CTX_Testing_AlwaysReturnError = CTX_ROOT + ".testing.alwaysReportError";
	public static final String DEFAULT_AlwaysReturnError = Boolean.FALSE.toString();

	public static final String CTX_Testing_ErrorMessage = CTX_ROOT + ".testing.errorMessage";
	public static final String DEFAULT_ErrorMessage = "Testing: Client returns 'ERROR' for testing purposes";

	public static final String CTX_Testing_Dont_RespondAfterPrinting = CTX_ROOT + ".testing.dontSendResponse";
	public static final String DEFAULT_Dont_RespondAfterPrinting = Boolean.FALSE.toString();
	// task 09618 end

	// task 09832 begin
	public static final String CTX_Engine_RetryCount = CTX_ROOT + ".engine.retryCount";
	public static final int DEFAULT_RetryCount = 3;

	public static final String CTX_Engine_RetryIntervalMS = CTX_ROOT + ".engine.retryIntervalMs";
	public static final int DEFAULT_RetryIntervalMS = 5000;
	// task 09832 end

	/**
	 * Used to get the {@link IUserInterface}
	 */
	public static final String CTX_UserInterface = de.metas.printing.client.ui.IUserInterface.class.getName();

	private final static Context instance = new Context();

	private final List<IContext> sources = new ArrayList<>();
	private final Properties defaults = new Properties();
	private final Properties props = new Properties();

	public static Context getContext()
	{
		return instance;
	}

	private Context()
	{
		init();
	}

	private void init()
	{
		defaults.clear();
		defaults.put(CTX_TempDir, DEFAULT_TempDir);
		defaults.put(CTX_BeanEncoder, DEFAULT_BeanEncoder);
		defaults.put(CTX_PrintConnectionEndpoint, DEFAULT_PrintConnectionEndpoint);

		props.clear();
	}

	/**
	 * Reset context, used ONLY for testing
	 */
	public void reset()
	{
		init();
	}

	public void addSource(final IContext source)
	{
		if (source == null)
		{
			return;
		}
		if (sources.contains(source))
		{
			return;
		}

		sources.add(source);
	}

	public void setProperty(final String name, final Object value)
	{
		props.put(name, value);
	}

	@Override
	public String getProperty(final String name)
	{
		final Object value = get(name);
		return value instanceof String ? value.toString() : null;
	}

	public String getProperty(final String name, final String defaultValue)
	{
		final String value = getProperty(name);
		if (value == null)
		{
			return defaultValue;
		}

		return value;
	}

	public int getPropertyAsInt(final String name, final int defaultValue)
	{
		final Object value = get(name);
		if (value == null)
		{
			return defaultValue;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else if (value instanceof String)
		{
			try
			{
				return Integer.parseInt(value.toString());
			}
			catch (final NumberFormatException e)
			{
				throw new RuntimeException("Property " + name + " has invalid integer value: " + value);
			}
		}
		else
		{
			throw new RuntimeException("Property " + name + " cannot be converted to integer: " + value + " (class=" + value.getClass() + ")");

		}
	}

	/**
	 *
	 * @param name
	 * @param interfaceClazz
	 * @return instance of given class/interface; never returns <code>null</code>
	 */
	public <T> T getInstance(final String name, final Class<T> interfaceClazz)
	{
		final Object value = get(name);
		if (value == null)
		{
			throw new RuntimeException("No class of " + interfaceClazz + " found for property " + name);
		}
		else if (value instanceof String)
		{
			final String classname = value.toString();
			return Util.getInstance(classname, interfaceClazz);
		}
		else if (value instanceof Class)
		{
			final Class<?> clazz = (Class<?>)value;
			if (interfaceClazz.isAssignableFrom(clazz))
			{
				try
				{
					@SuppressWarnings("unchecked")
					final T obj = (T)clazz.newInstance();
					return obj;
				}
				catch (final Exception e)
				{
					throw new RuntimeException(e);
				}
			}
			else
			{
				throw new RuntimeException("Invalid object " + value + " for property " + name + " (expected class: " + interfaceClazz + ")");
			}
		}
		else if (interfaceClazz.isAssignableFrom(value.getClass()))
		{
			@SuppressWarnings("unchecked")
			final T obj = (T)value;
			return obj;
		}
		else
		{
			throw new RuntimeException("Invalid object " + value + " for property " + name + " (expected class: " + interfaceClazz + ")");
		}
	}

	public <T> T get(final String name)
	{
		if (props.containsKey(name))
		{
			@SuppressWarnings("unchecked")
			final T value = (T)props.get(name);
			return value;
		}

		// Check other sources
		for (final IContext source : sources)
		{
			final String valueStr = source.getProperty(name);
			if (valueStr != null)
			{
				@SuppressWarnings("unchecked")
				final T value = (T)valueStr;
				return value;
			}
		}

		// Check defaults
		@SuppressWarnings("unchecked")
		final T value = (T)defaults.get(name);
		return value;
	}

	@Override
	public String toString()
	{
		return "Context["
				+ "sources=" + sources
				+ ", properties=" + props
				+ ", defaults=" + defaults
				+ "]";
	}
}
