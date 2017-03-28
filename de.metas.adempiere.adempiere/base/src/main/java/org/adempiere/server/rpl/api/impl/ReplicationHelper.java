package org.adempiere.server.rpl.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.compiere.util.Env;

public class ReplicationHelper
{
	public static final String MSG_XMLInvalidContext = "XMLInvalidContext";

	/**
	 * Method sets the given context values to the given context.
	 * 
	 * @param ctx the context to be updated
	 * @param name the name of the context value to be updated
	 * @param value the actual new value
	 * @param overwrite if <code>true</code> then the given <code>value</code> is set, even if there is already a different value. Otherwise, a {@link ReplicationException} is thrown.
	 * @throws ReplicationException if the name is already set to a different value.
	 */
	public static void setReplicationCtx(final Properties ctx,
			final String name,
			final Object value,
			final boolean overwrite)
	{
		if (value instanceof Integer)
		{
			final Integer valueInt = (Integer)value;
			final Integer valueOldInt = Env.containsKey(ctx, name) ? Env.getContextAsInt(ctx, name) : null;
			if (Objects.equals(valueInt, valueOldInt))
			{
				// nothing to do
				return;
			}
			else if (overwrite || valueOldInt == null)
			{
				Env.setContext(ctx, name, valueInt);
			}
			else
			{
				throw new ReplicationException(MSG_XMLInvalidContext)
						.setParameter("AttributeName", name)
						.setParameter("Value", valueInt)
						.setParameter("ValueOld", valueOldInt);
			}
		}
		else if (value instanceof Timestamp)
		{
			final Timestamp valueTS = (Timestamp)value;
			final Timestamp valueOldTS = Env.containsKey(ctx, name) ? Env.getContextAsDate(ctx, name) : null;
			if (Objects.equals(valueTS, valueOldTS))
			{
				// nothing to do
				return;
			}
			else if (overwrite || valueOldTS == null)
			{
				Env.setContext(ctx, name, valueTS);
			}
			else
			{
				throw new ReplicationException(MSG_XMLInvalidContext)
						.setParameter("AttributeName", name)
						.setParameter("Value", valueTS)
						.setParameter("ValueOld", valueOldTS);
			}
		}
		else
		{
			final String valueStr = value == null ? null : value.toString();
			final String valueOldStr = Env.containsKey(ctx, name) ? Env.getContext(ctx, name) : null;
			if (Objects.equals(valueStr, valueOldStr))
			{
				// nothing to do
				return;
			}
			else if (overwrite || valueOldStr == null)
			{
				Env.setContext(ctx, name, valueStr);
			}
			else
			{
				throw new ReplicationException(MSG_XMLInvalidContext)
						.setParameter("AttributeName", name)
						.setParameter("Value", valueStr)
						.setParameter("ValueOld", valueOldStr);
			}
		}
	}
}
