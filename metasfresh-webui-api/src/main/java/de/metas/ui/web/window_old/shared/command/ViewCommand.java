package de.metas.ui.web.window_old.shared.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window_old.shared.datatype.PropertyPath;

/*
 * #%L
 * metasfresh-webui-api
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

@SuppressWarnings("serial")
public final class ViewCommand implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private static final transient Logger logger = LogManager.getLogger(ViewCommand.class);

	@JsonProperty("propertyPath")
	private final PropertyPath propertyPath;
	@JsonProperty("commandId")
	private final String commandId;
	@JsonProperty("params")
	private final Map<String, Object> parameters;

	private ViewCommand(final Builder builder)
	{
		super();
		propertyPath = Preconditions.checkNotNull(builder.propertyPath, "propertyPath");
		commandId = Preconditions.checkNotNull(builder.commandId, "commandId");
		parameters = ImmutableMap.copyOf(builder.parameters);
	}

	private ViewCommand(@JsonProperty("propertyPath") final PropertyPath propertyPath, @JsonProperty("commandId") final String commandId, @JsonProperty("params") final Map<String, Object> parameters)
	{
		this(builder()
				.setPropertyPath(propertyPath)
				.setCommandId(commandId)
				.setParameters(parameters));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("propertyPath", propertyPath)
				.add("commandId", commandId)
				.add("parameters", parameters)
				.toString();
	}

	public PropertyPath getPropertyPath()
	{
		return propertyPath;
	}

	public String getCommandId()
	{
		return commandId;
	}

	@JsonIgnore
	public <T> T getParameter(final String parameterName)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)parameters.get(parameterName);
		return value;
	}

	public int getParameterAsInt(final String parameterName, final int defaultValue)
	{
		final Object value = parameters.get(parameterName);
		if (value == null)
		{
			return defaultValue;
		}

		if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else
		{
			try
			{
				return Integer.parseInt(value.toString());
			}
			catch (final Exception e)
			{
				logger.warn("Failed parsing {}={} to Integer", parameterName, value, e);
				return defaultValue;
			}
		}
	}

	public String getParameterAsString(final String parameterName)
	{
		final Object value = parameters.get(parameterName);
		return value == null ? null : value.toString();
	}

	private Builder toBuilder()
	{
		return new Builder(this);
	}

	public ViewCommand changePropertyPath(final PropertyPath propertyPath)
	{
		if (Objects.equals(this.propertyPath, propertyPath))
		{
			return this;
		}
		return toBuilder()
				.setPropertyPath(propertyPath)
				.build();
	}

	public static final class Builder
	{
		private PropertyPath propertyPath;
		private String commandId;
		private final Map<String, Object> parameters = new HashMap<>();

		private Builder()
		{
			super();
		}

		private Builder(final ViewCommand command)
		{
			super();
			propertyPath = command.propertyPath;
			commandId = command.commandId;
			parameters.putAll(command.parameters);
		}

		public ViewCommand build()
		{
			return new ViewCommand(this);
		}

		public Builder setPropertyPath(final PropertyPath propertyPath)
		{
			this.propertyPath = propertyPath;
			return this;
		}

		public Builder setCommandId(final String commandId)
		{
			this.commandId = commandId;
			return this;
		}

		public Builder setParameter(final String parameterName, final Object value)
		{
			if (value == null)
			{
				parameters.remove(parameterName);
			}
			else
			{
				parameters.put(parameterName, value);
			}

			return this;
		}

		private Builder setParameters(final Map<String, Object> parameters)
		{
			if (parameters == null || parameters.isEmpty())
			{
				return this;
			}

			for (final Map.Entry<String, Object> e : parameters.entrySet())
			{
				setParameter(e.getKey(), e.getValue());
			}

			return this;
		}
	}
}
