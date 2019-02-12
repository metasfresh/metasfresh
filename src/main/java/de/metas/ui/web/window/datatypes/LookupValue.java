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
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.RepoIdAware;
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
		else if (numericKey)
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
			else if (idObj instanceof RepoIdAware)
			{
				return ((RepoIdAware)idObj).getRepoId();
			}
			else
			{
				final String idStr = idObj.toString().trim();
				if (idStr.isEmpty())
				{
					return null;
				}

				final int idInt = Integer.parseInt(idStr);
				if (idInt < 0)
				{
					return null;
				}
				return idInt;
			}
		}
		else // string key
		{
			if (idObj instanceof ReferenceListAwareEnum)
			{
				return ((ReferenceListAwareEnum)idObj).getCode();
			}
			else
			{
				return idObj.toString();
			}
		}
	}

	public static final LookupValue fromObject(@Nullable final Object id, final String displayName)
	{
		if (id == null)
		{
			return null;
		}
		if (id instanceof Integer)
		{
			return new IntegerLookupValue((int)id, ImmutableTranslatableString.constant(displayName), null/* helpText */, null/* attributes */, null/* active */);
		}
		else
		{
			return new StringLookupValue(id.toString(), ImmutableTranslatableString.constant(displayName), null/* helpText */, null/* attributes */, null/* active */);
		}
	}

	public static final LookupValue fromNamePair(@Nullable final NamePair namePair)
	{
		final String adLanguage = null;
		final LookupValue defaultValue = null;
		return fromNamePair(namePair, adLanguage, defaultValue);
	}

	public static final LookupValue fromNamePair(
			@Nullable final NamePair namePair,
			@Nullable final String adLanguage)
	{
		final LookupValue defaultValue = null;
		return fromNamePair(namePair, adLanguage, defaultValue);
	}

	public static final LookupValue fromNamePair(
			@Nullable final NamePair namePair,
			@Nullable final String adLanguage,
			@Nullable final LookupValue defaultValue)
	{
		if (namePair == null)
		{
			return defaultValue;
		}

		final ITranslatableString displayNameTrl;
		final ITranslatableString descriptionTrl;
		if (adLanguage == null)
		{
			displayNameTrl = ImmutableTranslatableString.anyLanguage(namePair.getName());
			descriptionTrl = ImmutableTranslatableString.anyLanguage(namePair.getDescription());
		}
		else
		{
			displayNameTrl = ImmutableTranslatableString.singleLanguage(adLanguage, namePair.getName());
			descriptionTrl = ImmutableTranslatableString.singleLanguage(adLanguage, namePair.getDescription());
		}

		if (namePair instanceof ValueNamePair)
		{
			final ValueNamePair vnp = (ValueNamePair)namePair;
			return StringLookupValue.of(vnp.getValue(), displayNameTrl, descriptionTrl);
		}
		else if (namePair instanceof KeyNamePair)
		{
			final KeyNamePair knp = (KeyNamePair)namePair;
			return IntegerLookupValue.of(knp.getKey(), displayNameTrl, descriptionTrl);
		}
		else
		{
			// shall not happen
			throw new IllegalArgumentException("Unknown namePair: " + namePair + " (" + namePair.getClass() + ")");
		}
	}

	protected final Object id;
	protected final ITranslatableString displayName;
	protected final ITranslatableString description;
	private final Boolean active;

	private final ImmutableMap<String, Object> additionalAttributes;

	private LookupValue(
			@NonNull final Object id,
			@Nullable final ITranslatableString displayName,
			@Nullable final ITranslatableString description,
			@Nullable final Map<String, Object> additionalAttributes,
			@Nullable final Boolean active)
	{
		this.id = id;
		this.displayName = displayName == null ? ImmutableTranslatableString.empty() : displayName;
		this.description = description == null ? ImmutableTranslatableString.empty() : description;
		this.additionalAttributes = additionalAttributes != null && !additionalAttributes.isEmpty() ? ImmutableMap.copyOf(additionalAttributes) : null;
		this.active = active;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("displayName", displayName)
				.add("description", description)
				.add("additionalAttributes", additionalAttributes)
				.add("active", active)
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

	public ITranslatableString getDescriptionTrl()
	{
		return description;
	}

	public final boolean isActive()
	{
		final Boolean active = getActive();
		return active == null || active.booleanValue();
	}

	protected Boolean getActive()
	{
		return active;
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

	public <T extends RepoIdAware> T getIdAs(@NonNull final Function<Integer, T> idMapper)
	{
		return idMapper.apply(getIdAsInt());
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
			return new StringLookupValue(
					value,
					ImmutableTranslatableString.constant(displayName),
					null/* helpText */,
					null/* attributes */,
					true/* active */);
		}

		public static final StringLookupValue of(
				final String id,
				final ITranslatableString displayName,
				final ITranslatableString helpText)
		{
			return new StringLookupValue(id, displayName, helpText, null/* attributes */, null/* active */);
		}

		public static final StringLookupValue unknown(final String value)
		{
			return new StringLookupValue(
					value,
					ImmutableTranslatableString.constant("<" + value + ">"),
					null/* description */,
					null/* attributes */,
					false/* not active */);
		}

		private Integer idInt; // lazy

		@Builder
		private StringLookupValue(
				@NonNull final String id,
				@Nullable final ITranslatableString displayName,
				@Nullable final ITranslatableString description,
				@Singular final Map<String, Object> attributes,
				final Boolean active)
		{
			super(id,
					displayName,
					description,
					attributes,
					active);
		}

		@Override
		public int getIdAsInt()
		{
			Integer idInt = this.idInt;
			if (idInt == null)
			{
				idInt = this.idInt = Integer.parseInt((String)id);
			}
			return idInt;
		}
	}

	public static final class IntegerLookupValue extends LookupValue
	{
		/**
		 * Create a new value using the given {@code displayName} as a constant string that is valid in any language and thus never needs translation.
		 * Note: without "anyLanguage", you can run into trouble when combining {@link ProcessParamLookupValuesProvider} and {@link IProcessDefaultParametersProvider} in one {@link JavaProcess} implementation.
		 */
		public static final IntegerLookupValue of(final int id, final String displayName)
		{
			return new IntegerLookupValue(
					id,
					ImmutableTranslatableString.anyLanguage(displayName),
					null /* helpText */,
					null/* attributes */,
					null/* active */);
		}

		public static final IntegerLookupValue of(
				final int id,
				@Nullable final ITranslatableString displayName,
				@Nullable final ITranslatableString helpText)
		{
			return new IntegerLookupValue(
					id,
					displayName,
					helpText,
					null/* attributes */,
					null/* active */);
		}

		public static final IntegerLookupValue of(
				@NonNull final RepoIdAware id,
				@Nullable final ITranslatableString displayName,
				@Nullable final ITranslatableString helpText)
		{
			return new IntegerLookupValue(
					id.getRepoId(),
					displayName,
					helpText,
					null/* attributes */,
					null/* active */);
		}

		public static final IntegerLookupValue of(final StringLookupValue stringLookupValue)
		{
			if (stringLookupValue == null)
			{
				return null;
			}

			return new IntegerLookupValue(
					stringLookupValue.getIdAsInt(),
					stringLookupValue.displayName,
					stringLookupValue.description,
					null /* attributes */,
					stringLookupValue.getActive());
		}

		public static final IntegerLookupValue unknown(final int id)
		{
			return new IntegerLookupValue(
					id,
					ImmutableTranslatableString.constant("<" + id + ">"),
					null/* description */,
					null/* attributes */,
					false/* not active */);
		}

		@Builder
		private IntegerLookupValue(
				final int id,
				@Nullable final ITranslatableString displayName,
				@Nullable final ITranslatableString description,
				@Singular final Map<String, Object> attributes,
				final Boolean active)
		{
			super(id,
					displayName,
					description,
					attributes,
					active);
		}

		@Override
		public int getIdAsInt()
		{
			return (Integer)id;
		}
	}

}
