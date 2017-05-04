package de.metas.lock.exceptions;

/*
 * #%L
 * de.metas.async
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


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;

/**
 * General exception thrown on any locking/unlocking error.
 * 
 * This is the root of all locking exceptions.
 * 
 * Please avoid throwing this one directly but prefer using a specific exception
 * 
 * @author tsa
 *
 */
public abstract class LockException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2526563115872381426L;

	private String sql;
	private Object[] sqlParams;
	private ITableRecordReference record;

	private Map<String, Object> parameters = null;

	public LockException(final String message)
	{
		super(message);
	}

	public LockException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	@Override
	protected final String buildMessage()
	{
		final StringBuilder message = new StringBuilder();

		message.append(super.buildMessage());

		if (record != null)
		{
			message.append("\n Record: ").append(record);
		}

		if (sql != null)
		{
			message.append("\n SQL: ").append(sql);
		}
		if (sqlParams != null)
		{
			message.append("\n SQL Params: ").append(Arrays.asList(sqlParams));
		}

		if (parameters != null)
		{
			for (final Map.Entry<String, Object> paramName2Value : parameters.entrySet())
			{
				message.append("\n ").append(paramName2Value.getKey()).append(": ").append(paramName2Value.getValue());
			}
		}

		return message.toString();
	}

	@OverridingMethodsMustInvokeSuper
	public LockException setSql(final String sql, final Object[] sqlParams)
	{
		this.sql = sql;
		this.sqlParams = sqlParams;
		resetMessageBuilt();
		return this;
	}

	@OverridingMethodsMustInvokeSuper
	public LockException setRecord(final ITableRecordReference record)
	{
		this.record = record;
		resetMessageBuilt();
		return this;
	}

	@OverridingMethodsMustInvokeSuper
	public LockException setParameter(final String name, final Object value)
	{
		if (parameters == null)
		{
			parameters = new LinkedHashMap<>();
		}

		parameters.put(name, value);
		resetMessageBuilt();

		return this;
	}
}
