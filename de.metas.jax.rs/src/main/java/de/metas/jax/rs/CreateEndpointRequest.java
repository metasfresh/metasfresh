package de.metas.jax.rs;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.db.CConnection;

import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.jax.rs
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

/**
 * Specifies a new client or server endpoint to be generated. Use {@link #builder(Class)} to get your instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T>
 */
public class CreateEndpointRequest<T>
{
	private final CConnection cconnection;
	private final long timeoutMillis;
	private final List<Class<T>> clazzes;
	private final String requestQueueName;
	private final String responseQueueName;

	public static <T> Builder<T> builder()
	{
		return new Builder<T>();
	}

	/**
	 * creates a builder which initially contains the given endpoint class.
	 *
	 * @param clazz
	 * @return
	 */
	public static <T> Builder<T> builder(final Class<T> clazz)
	{
		return new Builder<T>().addEndpointClass(clazz);
	}

	public static <T> Builder<T> builder(final CreateEndpointRequest<T> template)
	{
		return new Builder<T>(template);
	}

	private CreateEndpointRequest(final Builder<T> builder)
	{
		this.clazzes = ImmutableList.<Class<T>> copyOf(builder.clazzes);
		this.timeoutMillis = builder.timeoutMillis;
		this.cconnection = builder.cconnection;
		this.requestQueueName = builder.requestQueueName;
		this.responseQueueName = builder.responseQueueName;
	};

	public List<Class<T>> getEndpointClasses()
	{
		return clazzes;
	}

	public CConnection getCConnection()
	{
		return cconnection;
	}

	public long getTimeOutMillis()
	{
		return timeoutMillis;
	}

	public String getRequestQueue()
	{
		return requestQueueName;
	}

	public String getResponseQueue()
	{
		return responseQueueName;
	}

	@Override
	public String toString()
	{
		return "CreateEndpointRequest [cconnection=" + cconnection + ", timeoutMillis=" + timeoutMillis + ", clazzes=" + clazzes + ", requestQueueName=" + requestQueueName
				+ ", responseQueueName=" + responseQueueName + "]";
	}

	public static class Builder<T>
	{
		public final static long DEFAULT_CLIENT_TIMEOUT_MILLIS = 60000L;

		public final static String DEFAULT_JMS_QUEUE_REQUEST = IJaxRsBL.JMS_QUEUE_REQUEST;
		public final static String DEFAULT_JMS_QUEUE_RESPONSE = IJaxRsBL.JMS_QUEUE_RESPONSE;

		private final List<Class<T>> clazzes;
		private CConnection cconnection = null;
		private long timeoutMillis = DEFAULT_CLIENT_TIMEOUT_MILLIS;
		private String requestQueueName = DEFAULT_JMS_QUEUE_REQUEST;
		private String responseQueueName = DEFAULT_JMS_QUEUE_RESPONSE;

		/**
		 * Creates a new request builder.
		 *
		 */
		private Builder()
		{
			clazzes = new ArrayList<>();
		}

		private Builder(final CreateEndpointRequest<T> template)
		{
			clazzes = ImmutableList.<Class<T>> copyOf(template.getEndpointClasses());
			cconnection = template.getCConnection();
			timeoutMillis = template.getTimeOutMillis();
			requestQueueName = template.getRequestQueue();
			responseQueueName = template.getResponseQueue();
		}

		public Builder<T> addEndpointClass(final Class<T> clazz)
		{
			this.clazzes.add(clazz);
			return this;
		}

		/**
		 * @param The connection from which to get the server and port. If <code>null</code> or not set, then use {@link CConnection#get()}.<br>
		 *            Note that in the early stages of startup, we can't rely on {@link CConnection#get()} to work for us.
		 */
		public Builder<T> setCconnection(final CConnection cconnection)
		{
			this.cconnection = cconnection;
			return this;
		}

		/**
		 * @param timeOutMillis specify how long the client shall wait for a response.<br>
		 *            E.g. if the client starts, we don't want the user to wait the default timeout of one minute to find out that there is a problem connecting to the server.<br>
		 *            If not set, then the default timeout of {@value #DEFAULT_CLIENT_TIMEOUT_MILLIS} will be used.
		 *
		 */
		public Builder<T> setTimeoutMillis(final long timeoutMillis)
		{
			this.timeoutMillis = timeoutMillis;
			return this;
		}

		/**
		 *
		 * @param requestQueueName May not be <code>null</code>. If not set, then the default queue name {@value #DEFAULT_JMS_QUEUE_REQUEST} will be used.
		 * @return
		 */
		public Builder<T> setRequestQueue(final String requestQueueName)
		{
			Check.assumeNotNull(requestQueueName, "requestQueueName 'requestQueueName' is not null");
			this.requestQueueName = requestQueueName;
			return this;
		}

		/**
		 *
		 * @param requestQueueName May not be <code>null</code>. If not set, then the default queue name {@value #DEFAULT_JMS_QUEUE_RESPONSE} will be used.
		 *
		 * @return
		 */
		public Builder<T> setResponseQueue(final String responseQueueName)
		{
			Check.assumeNotNull(requestQueueName, "responseQueueName 'responseQueueName' is not null");
			this.responseQueueName = responseQueueName;
			return this;
		}

		public CreateEndpointRequest<T> build()
		{
			return new CreateEndpointRequest<>(this);
		}
	}
}
