package de.metas.ui.web.window.datatypes;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class LookupValue
{
	public static final Object normalizeId(final Object idObj, final boolean numericKey)
	{
		if (idObj == null)
		{
			return null;
		}

		if (numericKey)
		{
			if (idObj instanceof Number)
			{
				final int idInt = ((Number)idObj).intValue();
				if (idInt < 0)
				{
					return null;
				}
				return idInt;
			}

			final String idStr = idObj.toString().trim();
			if (idStr.isEmpty())
			{
				return null;
			}

			final int idInt = Integer.parseInt(idObj.toString());
			if (idInt < 0)
			{
				return null;
			}
			return idInt;
		}
		else
		{
			return idObj.toString();
		}
	}

	public static final LookupValue fromObject(final Object id, final String displayName)
	{
		if (id == null)
		{
			return null;
		}
		if (id instanceof Integer)
		{
			final Map<String, Object> attributes = null;
			return new IntegerLookupValue((int)id, ImmutableTranslatableString.constant(displayName), attributes);
		}
		else
		{
			final Map<String, Object> attributes = null;
			return new StringLookupValue(id.toString(), ImmutableTranslatableString.constant(displayName), attributes);
		}
	}

	public static final LookupValue fromNamePair(final NamePair namePair)
	{
		final String adLanguage = null;
		final LookupValue defaultValue = null;
		return fromNamePair(namePair, adLanguage, defaultValue);
	}

	public static final LookupValue fromNamePair(final NamePair namePair, final String adLanguage)
	{
		final LookupValue defaultValue = null;
		return fromNamePair(namePair, adLanguage, defaultValue);
	}

	public static final LookupValue fromNamePair(final NamePair namePair, final String adLanguage, final LookupValue defaultValue)
	{
		if (namePair == null)
		{
			return defaultValue;
		}

		final ITranslatableString displayNameTrl;
		if (adLanguage == null)
		{
			displayNameTrl = ImmutableTranslatableString.anyLanguage(namePair.getName());
		}
		else
		{
			displayNameTrl = ImmutableTranslatableString.singleLanguage(adLanguage, namePair.getName());
		}

		if (namePair instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)namePair;
			return StringLookupValue.of(vnp.getValue(), displayNameTrl);
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			return IntegerLookupValue.of(knp.getKey(), displayNameTrl);
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Unknown namePair: " + namePair + " (" + namePair.getClass() + ")");
		}
	}

	protected final Object id;
	protected final ITranslatableString displayName;
	private final ImmutableMap<String, Object> additionalAttributes;

	private LookupValue(@NonNull final Object id, @Nullable final ITranslatableString displayName, final Map<String, Object> additionalAttributes)
	{
		this.id = id;
		this.displayName = displayName == null ? ImmutableTranslatableString.empty() : displayName;
		this.additionalAttributes = additionalAttributes != null && !additionalAttributes.isEmpty() ? ImmutableMap.copyOf(additionalAttributes) : null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("displayName", displayName)
				.add("additionalAttributes", additionalAttributes)
				.toString();
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
		if (!(obj instanceof LookupValue))
		{
			return false;
		}

		final LookupValue other = (LookupValue)obj;

		// NOTE: only the ID is considered on hashCode and equals
		return DataTypes.equals(id, other.id);
	}

	@Override
	public int hashCode()
	{
		// NOTE: only the ID is considered on hashCode and equals
		return Objects.hashCode(id);
	}

	public String getDisplayName()
	{
		return displayName.getDefaultValue();
	}

	public String getDisplayName(final String adLanguage)
	{
		return displayName.translate(adLanguage);
	}

	public ITranslatableString getDisplayNameTrl()
	{
		return displayName;
	}

	public final Object getId()
	{
		return id;
	}

	public abstract int getIdAsInt();

	public String getIdAsString()
	{
		return id.toString();
	}

	public final <T> T transform(final Function<LookupValue, T> transformation)
	{
		return transformation.apply(this);
	}

	public Map<String, Object> getAttributes()
	{
		return additionalAttributes != null ? additionalAttributes : ImmutableMap.of();
	}

	public <T> T getAttribute(final String name)
	{
		if (additionalAttributes == null)
		{
			return null;
		}
		@SuppressWarnings("unchecked")
		final T value = (T)additionalAttributes.get(name);
		return value;
	}

	public int getAttributeAsInt(final String name, final int defaultValue)
	{
		final Object valueObj = getAttribute(name);
		if (valueObj == null)
		{
			return defaultValue;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).intValue();
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return defaultValue;
			}

			return Integer.parseInt(valueStr);
		}
	}

	public Set<Integer> getAttributeAsIntSet(final String name)
	{
		final Object valueObj = getAttribute(name);
		if (valueObj == null)
		{
			return ImmutableSet.of();
		}
		else if (valueObj instanceof Collection)
		{
			@SuppressWarnings("unchecked")
			final Collection<Integer> coll = (Collection<Integer>)valueObj;
			return ImmutableSet.copyOf(coll);
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return ImmutableSet.of();
			}

			try
			{
				return Splitter.on(",").omitEmptyStrings()
						.trimResults()
						.splitToList(valueStr).stream()
						.map(Integer::parseInt)
						.collect(ImmutableSet.toImmutableSet());
			}
			catch (final Exception ex)
			{
				throw new AdempiereException("Cannot convert attribute value to integer Set", ex)
						.setParameter("attributeName", name)
						.setParameter("attributeValue", valueStr)
						.setParameter("lookupValue", this)
						.appendParametersToMessage();
			}
		}
	}

	public static final class StringLookupValue extends LookupValue
	{
		public static final StringLookupValue of(final String value, final String displayName)
		{
			final Map<String, Object> attributes = null;
			return new StringLookupValue(value, ImmutableTranslatableString.constant(displayName), attributes);
		}

		public static final StringLookupValue of(final String value, final ITranslatableString displayName)
		{
			final Map<String, Object> attributes = null;
			return new StringLookupValue(value, displayName, attributes);
		}

		public static final StringLookupValue unknown(final String value)
		{
			final Map<String, Object> attributes = null;
			return new StringLookupValue(value, ImmutableTranslatableString.constant("<" + value + ">"), attributes);
		}

		private Integer idInt; // lazy

		@Builder
		private StringLookupValue(
				@NonNull final String id,
				@Nullable final ITranslatableString displayName,
				@Singular final Map<String, Object> attributes)
		{
			super(id, displayName, attributes);
		}

		@Override
		public int getIdAsInt()
		{
			if (idInt == null)
			{
				idInt = Integer.parseInt((String)id);
			}
			return idInt;
		}
	}

	public static final class IntegerLookupValue extends LookupValue
	{
		/**
		 * Create a new value using the given {@code displayName} as a constant string that is valid in any language and thus never needs translation.
		 * Note: without "anyLanguage", you can run into trouble when combining {@link ProcessParamLookupValuesProvider} and {@link IProcessDefaultParametersProvider} in one {@link JavaProcess} implementation.
		 * 
		 * @param id
		 * @param displayName
		 * @return
		 */
		public static final IntegerLookupValue of(final int id, final String displayName)
		{
			final Map<String, Object> attributes = null;
			return new IntegerLookupValue(id, ImmutableTranslatableString.anyLanguage(displayName), attributes);
		}

		public static final IntegerLookupValue of(final int id, final ITranslatableString displayName)
		{
			final Map<String, Object> attributes = null;
			return new IntegerLookupValue(id, displayName, attributes);
		}

		public static final IntegerLookupValue of(final StringLookupValue stringLookupValue)
		{
			if (stringLookupValue == null)
			{
				return null;
			}
			final Map<String, Object> attributes = null;
			return new IntegerLookupValue(stringLookupValue.getIdAsInt(), stringLookupValue.displayName, attributes);
		}

		public static final IntegerLookupValue unknown(final int id)
		{
			final Map<String, Object> attributes = null;
			return new IntegerLookupValue(id, ImmutableTranslatableString.constant("<" + id + ">"), attributes);
		}

		@Builder
		private IntegerLookupValue(
				final int id,
				final ITranslatableString displayName,
				@Singular final Map<String, Object> attributes)
		{
			super(id, displayName, attributes);
		}

		@Override
		public int getIdAsInt()
		{
			return (Integer)id;
		}
	}

}
