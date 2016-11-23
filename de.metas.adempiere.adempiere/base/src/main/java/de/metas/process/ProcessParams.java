package de.metas.process;

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
import java.util.List;
import java.util.Map;

import org.adempiere.util.Check;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 * {@link IParams} implementation for {@link ProcessInfoParameter}.
 *
 * @author tsa
 *
 */
public class ProcessParams implements IRangeAwareParams
{
	private final IReference<List<ProcessInfoParameter>> _parametersLoader;
	private Map<String, ProcessInfoParameter> _parameterName2parameter;

	public ProcessParams(final List<ProcessInfoParameter> parameters)
	{
		super();

		Check.assumeNotNull(parameters, "parameters not null");
		this._parametersLoader = ImmutableReference.valueOf(parameters);
	}

	/**
	 * Lazy loading constructor
	 *
	 * @param parametersLoader loader which will provide the paramaters. It will be called ONLY when needed
	 */
	public ProcessParams(final IReference<List<ProcessInfoParameter>> parametersLoader)
	{
		super();

		Check.assumeNotNull(parametersLoader, "parametersLoader not null");
		this._parametersLoader = parametersLoader;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private final Map<String, ProcessInfoParameter> getParametersMap()
	{
		if (_parameterName2parameter == null)
		{
			final List<ProcessInfoParameter> processInfoParameters = _parametersLoader.getValue();
			_parameterName2parameter = Maps.uniqueIndex(
					processInfoParameters.iterator()
					, new Function<ProcessInfoParameter, String>()
					{

						@Override
						public String apply(ProcessInfoParameter parameter)
						{
							return parameter.getParameterName();
						}

					});
		}
		return _parameterName2parameter;
	}

	private final ProcessInfoParameter getProcessInfoParameterOrNull(final String parameterName)
	{
		return getParametersMap().get(parameterName);
	}

	@Override
	public final boolean hasParameter(final String parameterName)
	{
		return getProcessInfoParameterOrNull(parameterName) != null;
	}

	@Override
	public final String getParameterAsString(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameterAsString();
	}

	@Override
	public String getParameter_ToAsString(String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameter_ToAsString();
	}


	@Override
	public final int getParameterAsInt(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return 0;
		}
		return processInfoParameter.getParameterAsInt();
	}

	@Override
	public int getParameter_ToAsInt(String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return 0;
		}
		return processInfoParameter.getParameter_ToAsInt();
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameterAsBigDecimal();
	}

	@Override
	public BigDecimal getParameter_ToAsBigDecimal(String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameter_ToAsBigDecimal();
	}

	@Override
	public final boolean getParameterAsBool(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return false;
		}
		return processInfoParameter.getParameterAsBoolean();
	}

	@Override
	public boolean getParameter_ToAsBool(String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return false;
		}
		return processInfoParameter.getParameter_ToAsBoolean();
	}

	@Override
	public final Timestamp getParameterAsTimestamp(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameterAsTimestamp();
	}

	@Override
	public Timestamp getParameter_ToAsTimestamp(String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameter_ToAsTimestamp();
	}
}
