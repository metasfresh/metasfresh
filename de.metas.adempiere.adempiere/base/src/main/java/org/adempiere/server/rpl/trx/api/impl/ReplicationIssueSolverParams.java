package org.adempiere.server.rpl.trx.api.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

import org.adempiere.server.rpl.trx.api.IReplicationIssueSolverParams;
import org.adempiere.util.Check;
import org.adempiere.util.api.IParams;

/* package */class ReplicationIssueSolverParams implements IReplicationIssueSolverParams
{
	private final IParams params;

	public ReplicationIssueSolverParams(final IParams params)
	{
		Check.assumeNotNull(params, "Parameter 'params' is not null");
		this.params = params;
	}

	@Override
	public boolean hasParameter(final String parameterName)
	{
		return params.hasParameter(parameterName);
	}

	@Override
	public String getParameterAsString(final String parameterName)
	{
		return params.getParameterAsString(parameterName);
	}

	@Override
	public int getParameterAsInt(final String parameterName)
	{
		return params.getParameterAsInt(parameterName);
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(String parameterName)
	{
		return params.getParameterAsBigDecimal(parameterName);
	}

	@Override
	public Timestamp getParameterAsTimestamp(final String parameterName)
	{
		return params.getParameterAsTimestamp(parameterName);
	}

	@Override
	public boolean getParameterAsBool(final String parameterName)
	{
		return params.getParameterAsBool(parameterName);
	}

	/**
	 * Just delegates to the wrapped {@link IParams} instance.
	 */
	@Override
	public Collection<String> getParameterNames()
	{
		return params.getParameterNames();
	}
}
