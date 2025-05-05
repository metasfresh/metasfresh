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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.api.IParams;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * {@link IParams} implementation for {@link ProcessInfoParameter}.
 *
 * @author tsa
 *
 */
@ToString
public class ProcessParams implements IRangeAwareParams
{
	public static ProcessParams of(final ProcessInfoParameter parameter)
	{
		return new ProcessParams(ImmutableList.of(parameter));
	}

	public static ProcessParams of(final String parameterName, final java.util.Date parameterValue, final java.util.Date parameterValueTo)
	{
		final ProcessInfoParameter parameter = ProcessInfoParameter.of(parameterName, parameterValue, parameterValueTo);
		return new ProcessParams(ImmutableList.of(parameter));
	}

	public static ProcessParams ofValueObject(final String parameterName, final Object parameterValue)
	{
		final ProcessInfoParameter parameter = ProcessInfoParameter.ofValueObject(parameterName, parameterValue);
		return new ProcessParams(ImmutableList.of(parameter));
	}

	public static ProcessParams of(final List<ProcessInfoParameter> parameters)
	{
		return new ProcessParams(parameters);
	}

	private final IReference<List<ProcessInfoParameter>> _parametersLoader;
	private Map<String, ProcessInfoParameter> _parameterName2parameter;

	public ProcessParams(@NonNull final List<ProcessInfoParameter> parameters)
	{
		_parametersLoader = ImmutableReference.valueOf(parameters);
	}

	/**
	 * Lazy loading constructor
	 *
	 * @param parametersLoader loader which will provide the parameters. It will be called ONLY when needed
	 */
	public ProcessParams(@NonNull final IReference<List<ProcessInfoParameter>> parametersLoader)
	{
		_parametersLoader = parametersLoader;
	}

	private Map<String, ProcessInfoParameter> getParametersMap()
	{
		Map<String, ProcessInfoParameter> parameterName2parameter = _parameterName2parameter;
		if (parameterName2parameter == null)
		{
			final List<ProcessInfoParameter> processInfoParameters = _parametersLoader.getValue();
			parameterName2parameter = _parameterName2parameter = Maps.uniqueIndex(processInfoParameters.iterator(), ProcessInfoParameter::getParameterName);
		}
		return parameterName2parameter;
	}

	private ProcessInfoParameter getProcessInfoParameterOrNull(final String parameterName)
	{
		return getParametersMap().get(parameterName);
	}

	@Override
	public final boolean hasParameter(final String parameterName)
	{
		return getProcessInfoParameterOrNull(parameterName) != null;
	}

	@Override
	public Object getParameterAsObject(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameter();
	}

	@Override
	public Object getParameter_ToAsObject(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameter_To();
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
	public String getParameter_ToAsString(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameter_ToAsString();
	}

	@Override
	public final int getParameterAsInt(final String parameterName, final int defaultValue)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return defaultValue;
		}
		return processInfoParameter.getParameterAsInt(defaultValue);
	}
		
	@Override
	public <T extends RepoIdAware> T getParameterAsId(final String parameterName, final Class<T> type)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameterAsRepoId(type);
	}

	@Override
	public int getParameter_ToAsInt(final String parameterName, final int defaultValue)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return defaultValue;
		}
		return processInfoParameter.getParameter_ToAsInt(defaultValue);
	}

	@Override
	public BigDecimal getParameterAsBigDecimal(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		if (processInfoParameter == null)
		{
			return null;
		}
		return processInfoParameter.getParameterAsBigDecimal();
	}

	@Override
	public BigDecimal getParameter_ToAsBigDecimal(final String parameterName)
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
		//noinspection ConstantConditions
		return getParameterAsBoolean(parameterName, false);
	}

	@Nullable
	@Override
	public Boolean getParameterAsBoolean(final String parameterName, @Nullable final Boolean defaultValue)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameterAsBoolean(defaultValue) : defaultValue;
	}

	@Override
	public boolean getParameter_ToAsBool(final String parameterName)
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
		return processInfoParameter != null ? processInfoParameter.getParameterAsTimestamp() : null;
	}

	@Override
	public LocalDate getParameterAsLocalDate(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameterAsLocalDate() : null;
	}

	@Override
	public ZonedDateTime getParameterAsZonedDateTime(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameterAsZonedDateTime() : null;
	}

	@Override
	public Instant getParameterAsInstant(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameterAsInstant() : null;
	}
	
	@Override
	public Timestamp getParameter_ToAsTimestamp(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameter_ToAsTimestamp() : null;
	}

	@Override
	public LocalDate getParameter_ToAsLocalDate(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameter_ToAsLocalDate() : null;
	}

	@Override
	public ZonedDateTime getParameter_ToAsZonedDateTime(final String parameterName)
	{
		final ProcessInfoParameter processInfoParameter = getProcessInfoParameterOrNull(parameterName);
		return processInfoParameter != null ? processInfoParameter.getParameter_ToAsZonedDateTime() : null;
	}

	/**
	 * Returns an immutable collection of the wrapped process parameter names.
	 */
	@Override
	public Collection<String> getParameterNames()
	{
		return ImmutableSet.copyOf(getParametersMap().keySet());
	}
}
