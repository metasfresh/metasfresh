package org.adempiere.server.rpl.exceptions;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

/**
 * 
 * @author ad
 * @task http://dewiki908/mediawiki/index.php/03000:_ADempiere_Replication_understandable_Errormessages_% 282012071810000029%29
 */
public class ReplicationException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8608172237424907859L;

	private final String adMessage;
	private final Map<String, Object> params = new HashMap<String, Object>();
	private final Throwable cause;

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param adMessage the detail message.
	 */
	public ReplicationException(final String adMessage)
	{
		super("@" + adMessage + "@");
		this.adMessage = adMessage;
		cause = null;
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * 
	 * @param adMessage
	 * @param cause
	 */
	public ReplicationException(final String adMessage, final Throwable cause)
	{
		super("@" + adMessage + "@", cause);
		this.adMessage = adMessage;
		this.cause = cause;
	}

	public ReplicationException addParameter(final String parameterName, final Object value)
	{
		params.put(parameterName, value);
		resetMessageBuilt();
		return this;
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder sbParams = new StringBuilder();
		for (final Map.Entry<String, Object> e : params.entrySet())
		{
			final String name = Services.get(IMsgBL.class).translate(getCtx(), e.getKey());
			final Object value = e.getValue();

			if (sbParams.length() > 0)
			{
				sbParams.append(", ");
			}

			sbParams.append(name).append(':').append(value);
		}

		final StringBuilder sb = new StringBuilder();

		sb.append(Services.get(IMsgBL.class).translate(getCtx(), adMessage));
		if (sbParams.length() > 0)
		{
			sb.append(" (").append(sbParams).append(")");
		}
		
		if (cause != null)
		{
			sb.append(" ").append(cause.getLocalizedMessage());
		}

		return sb.toString();
	}
}
