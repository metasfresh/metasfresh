package org.compiere.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Immutable Context Name. Use the methods for {@link CtxNames} to obtain instances.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class CtxName
{
	private final String name;
	private final List<String> modifiers;
	private final String defaultValue;
	private Optional<Integer> defaultValueInt; // lazy
	private Optional<Boolean> defaultValueBoolean; // lazy
	private Optional<BigDecimal> defaultValueBigDecimal; // lazy
	private Optional<java.util.Date> defaultValueDate; // lazy
	private transient volatile String cachedToStringWithTagMarkers = null;
	private transient volatile String cachedToStringWithoutTagMarkers = null;

	private Integer _hashcode; // lazy

	/* package */ CtxName(
			@NonNull final String name,
			@Nullable final List<String> modifiers,
			@Nullable final String defaultValue)
	{
		if (Check.isEmpty(name))
		{
			throw new IllegalArgumentException("Name may be not empty");
		}

		this.name = name;
		if (modifiers != null && !modifiers.isEmpty())
		{
			this.modifiers = ImmutableList.copyOf(modifiers);
		}
		else
		{
			this.modifiers = null;
		}
		this.defaultValue = defaultValue;
	}

	public String getName()
	{
		return name;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	private Integer getDefaultValueAsInteger()
	{
		if (defaultValueInt == null)
		{
			defaultValueInt = Optional.ofNullable(Evaluatee.convertToInteger(name, defaultValue, null));
		}
		return defaultValueInt.orElse(null);
	}

	private Boolean getDefaultValueAsBoolean()
	{
		if (defaultValueBoolean == null)
		{
			defaultValueBoolean = Optional.ofNullable(DisplayType.toBoolean(defaultValue, (Boolean)null));
		}
		return defaultValueBoolean.orElse(null);
	}

	private BigDecimal getDefaultValueAsBigDecimal()
	{
		if (defaultValueBigDecimal == null)
		{
			defaultValueBigDecimal = Optional.ofNullable(Evaluatee.convertToBigDecimal(name, defaultValue, null));
		}
		return defaultValueBigDecimal.orElse(null);
	}

	private java.util.Date getDefaultValueAsDate()
	{
		if (defaultValueDate == null)
		{
			if (defaultValue != null && "NULL".equalsIgnoreCase(defaultValue.trim()))
			{
				defaultValueDate = Optional.empty();
			}
			else
			{
				defaultValueDate = Optional.ofNullable(Evaluatee.convertToDate(name, defaultValue, null));
			}
		}
		return defaultValueDate.orElse(null);
	}

	private boolean isOld()
	{
		return hasModifier(CtxNames.MODIFIER_Old);
	}

	private boolean hasModifier(final String modifier)
	{
		return modifiers != null && modifiers.contains(modifier);
	}

	/**
	 * @return true if this context name is an explicit global variable (i.e. starts with # or $)
	 */
	public boolean isExplicitGlobal()
	{
		return isExplicitGlobal(name);
	}

	/**
	 * @return true if this context name is an explicit global variable (i.e. starts with # or $)
	 * @name context name
	 */
	public static boolean isExplicitGlobal(final String name)
	{
		if (name == null)
		{
			return false;
		}
		return name.startsWith("#") || name.startsWith("$");
	}

	/**
	 * Evaluates this context name and gets it's value from given source/context.
	 * <p>
	 * In case the source/context is <code>null</code> then {@link #getDefaultValue()} will be returned.
	 *
	 * @param source evaluation context/source; <code>null</code> is accept
	 * @return {@link Evaluatee}'s variable value or {@link #VALUE_NULL}
	 */
	public String getValueAsString(final Evaluatee source)
	{
		if (source == null)
		{
			return getDefaultValue();
		}

		final String sourceResult;
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;

			if (!source2.has_Variable(name))
			{
				return getDefaultValue();
			}

			if (!isOld())
			{
				sourceResult = source2.get_ValueAsString(name);
			}
			else
			{
				sourceResult = source2.get_ValueOldAsString(name);
			}
		}
		else
		{
			final String value = source.get_ValueAsString(name);
			if (Env.isPropertyValueNull(name, value))
			{
				return getDefaultValue();
			}
			else
			{
				sourceResult = value;
			}
		}

		if (sourceResult != null)
		{
			if (hasModifier(CtxNames.MODIFIER_QuotedIfNotDefault))
			{
				return "'" + sourceResult + "'";
			}

			if (hasModifier(CtxNames.MODIFIER_AsJsonString))
			{
				try
				{
					return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(sourceResult);
				}
				catch (JsonProcessingException e)
				{
					throw new AdempiereException("Failed to json escape context variable `" + this + "`: `" + sourceResult + "`");
				}
			}

			return sourceResult;
		}

		return getDefaultValue();
	}

	public Integer getValueAsInteger(final Evaluatee source)
	{
		final Integer defaultValueAsInteger = getDefaultValueAsInteger();

		if (source == null)
		{
			return defaultValueAsInteger;
		}

		final Integer sourceResult;
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;

			if (!source2.has_Variable(name))
			{
				return defaultValueAsInteger;
			}

			if (!isOld())
			{
				sourceResult = source2.get_ValueAsInt(name, defaultValueAsInteger);
			}
			else
			{
				sourceResult = source2.get_ValueOldAsInt(name, defaultValueAsInteger);
			}
		}
		else
		{
			sourceResult = source.get_ValueAsInt(name, defaultValueAsInteger);
		}

		if (sourceResult != null)
		{
			return sourceResult;
		}
		return defaultValueAsInteger;
	}

	public Boolean getValueAsBoolean(final Evaluatee source)
	{
		final Boolean defaultValueAsBoolean = getDefaultValueAsBoolean();

		if (source == null)
		{
			return defaultValueAsBoolean;
		}

		final Boolean sourceResult;
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;

			if (!source2.has_Variable(name))
			{
				return defaultValueAsBoolean;
			}

			if (!isOld())
			{
				sourceResult = source2.get_ValueAsBoolean(name, defaultValueAsBoolean);
			}
			else
			{
				sourceResult = source2.get_ValueOldAsBoolean(name, defaultValueAsBoolean);
			}
		}
		else
		{
			sourceResult = source.get_ValueAsBoolean(name, defaultValueAsBoolean);
		}

		if (sourceResult != null)
		{
			return sourceResult;
		}
		return defaultValueAsBoolean;
	}

	public BigDecimal getValueAsBigDecimal(final Evaluatee source)
	{
		final BigDecimal defaultValueAsBigDecimal = getDefaultValueAsBigDecimal();

		if (source == null)
		{
			return defaultValueAsBigDecimal;
		}

		final BigDecimal sourceResult;
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;
			if (!source2.has_Variable(name))
			{
				return defaultValueAsBigDecimal;
			}

			if (!isOld())
			{
				sourceResult = source2.get_ValueAsBigDecimal(name, defaultValueAsBigDecimal);
			}
			else
			{
				sourceResult = source2.get_ValueOldAsBigDecimal(name, defaultValueAsBigDecimal);
			}
		}
		else
		{
			sourceResult = source.get_ValueAsBigDecimal(name, defaultValueAsBigDecimal);
		}

		if (sourceResult != null)
		{
			return sourceResult;
		}
		return defaultValueAsBigDecimal;
	}

	public ZonedDateTime getValueAsZonedDateTime(final Evaluatee source)
	{
		return TimeUtil.asZonedDateTime(getValueAsDate(source));
	}

	public LocalDate getValueAsLocalDate(final Evaluatee source)
	{
		return TimeUtil.asLocalDate(getValueAsDate(source));
	}

	public java.util.Date getValueAsDate(final Evaluatee source)
	{
		final java.util.Date defaultValueAsDate = getDefaultValueAsDate();

		if (source == null)
		{
			return defaultValueAsDate;
		}

		final java.util.Date sourceResult;
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;
			if (!source2.has_Variable(name))
			{
				return defaultValueAsDate;
			}

			if (!isOld())
			{
				sourceResult = source2.get_ValueAsDate(name, defaultValueAsDate);
			}
			else
			{
				sourceResult = source2.get_ValueOldAsDate(name, defaultValueAsDate);
			}
		}
		else
		{
			sourceResult = source.get_ValueAsDate(name, defaultValueAsDate);
		}

		if (sourceResult != null)
		{
			return sourceResult;
		}
		return defaultValueAsDate;
	}

	public String toString(final boolean includeTagMarkers)
	{
		return includeTagMarkers ? toStringWithMarkers() : toStringWithoutMarkers();
	}

	public String toStringWithMarkers()
	{
		if (cachedToStringWithTagMarkers == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(CtxNames.NAME_Marker).append(toStringWithoutMarkers()).append(CtxNames.NAME_Marker);
			cachedToStringWithTagMarkers = sb.toString();
		}
		return cachedToStringWithTagMarkers;
	}

	public String toStringWithoutMarkers()
	{
		if (cachedToStringWithoutTagMarkers == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(name);
			if (modifiers != null && !modifiers.isEmpty())
			{
				for (final String m : modifiers)
				{
					sb.append(CtxNames.SEPARATOR).append(m);
				}
			}
			if (!Check.isEmpty(defaultValue))
			{
				sb.append(CtxNames.SEPARATOR).append(defaultValue);
			}
			cachedToStringWithoutTagMarkers = sb.toString();
		}
		return cachedToStringWithoutTagMarkers;
	}

	@Override
	public String toString()
	{
		return toStringWithoutMarkers();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + (defaultValue == null ? 0 : defaultValue.hashCode());
			result = prime * result + (modifiers == null ? 0 : modifiers.hashCode());
			result = prime * result + (name == null ? 0 : name.hashCode());
			_hashcode = result;
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final CtxName other = (CtxName)obj;
		if (defaultValue == null)
		{
			if (other.defaultValue != null)
			{
				return false;
			}
		}
		else if (!defaultValue.equals(other.defaultValue))
		{
			return false;
		}
		if (modifiers == null)
		{
			if (other.modifiers != null)
			{
				return false;
			}
		}
		else if (!modifiers.equals(other.modifiers))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		return true;
	}
}
