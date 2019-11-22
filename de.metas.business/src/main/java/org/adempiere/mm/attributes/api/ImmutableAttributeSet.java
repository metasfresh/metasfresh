package org.adempiere.mm.attributes.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.NullAttributeValueCallout;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Immutable {@link IAttributeSet} implementation.
 *
 * To get your instance for an {@link AttributeSetInstanceId},
 * you can use {@link IAttributeDAO#getImmutableAttributeSetById(AttributeSetInstanceId)}.
 */
public final class ImmutableAttributeSet implements IAttributeSet
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static ImmutableAttributeSet ofValuesByAttributeIdMap(@Nullable final Map<Object, Object> valuesByAttributeIdMap)
	{
		if (valuesByAttributeIdMap == null || valuesByAttributeIdMap.isEmpty())
		{
			return EMPTY;
		}

		final Builder builder = builder();

		valuesByAttributeIdMap.forEach((attributeIdObj, value) -> {
			final AttributeId attributeId = AttributeId.ofRepoIdObj(attributeIdObj);
			builder.attributeValue(attributeId, value);
		});

		return builder.build();
	}

	public static ImmutableAttributeSet createSubSet(
			@NonNull final IAttributeSet attributeSet,
			@NonNull final Predicate<I_M_Attribute> filter)
	{

		final Builder builder = builder();
		attributeSet.getAttributes()
				.stream()
				.filter(filter)
				.forEach(attribute -> {
					final Object value = attributeSet.getValue(attribute);
					final AttributeValueId attributeValueId = attributeSet.getAttributeValueIdOrNull(attribute.getValue());
					builder.attributeValue(attribute, value, attributeValueId);
				});

		return builder.build();
	}

	public static final ImmutableAttributeSet EMPTY = new ImmutableAttributeSet();

	private final ImmutableMap<AttributeId, I_M_Attribute> attributesById;
	private final ImmutableMap<String, I_M_Attribute> attributesByKey;
	private final ImmutableMap<String, Object> valuesByAttributeKey;
	private final ImmutableMap<String, AttributeValueId> valueIdsByAttributeKey;

	private ImmutableAttributeSet(
			@NonNull final ImmutableMap<AttributeId, I_M_Attribute> attributesById,
			@NonNull final ImmutableMap<String, I_M_Attribute> attributesByKey,
			@NonNull final ImmutableMap<String, Object> valuesByAttributeKey,
			@NonNull final ImmutableMap<String, AttributeValueId> valueIdsByAttributeKey)
	{
		this.attributesById = attributesById;
		this.attributesByKey = attributesByKey;
		this.valuesByAttributeKey = valuesByAttributeKey;
		this.valueIdsByAttributeKey = valueIdsByAttributeKey;
	}

	private ImmutableAttributeSet()
	{
		attributesById = ImmutableMap.of();
		attributesByKey = ImmutableMap.of();
		valuesByAttributeKey = ImmutableMap.of();
		valueIdsByAttributeKey = ImmutableMap.of();
	}

	@Override
	@Deprecated
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("values", valuesByAttributeKey)
				.add("valueIds", valueIdsByAttributeKey)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(valuesByAttributeKey);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof ImmutableAttributeSet)
		{
			ImmutableAttributeSet other = (ImmutableAttributeSet)obj;
			return Objects.equals(valuesByAttributeKey, other.valuesByAttributeKey);
		}
		else
		{
			return false;
		}
	}

	public boolean isEmpty()
	{
		return attributesByKey.isEmpty();
	}

	@Override
	public Collection<I_M_Attribute> getAttributes()
	{
		return attributesByKey.values();
	}

	public Set<AttributeId> getAttributeIds()
	{
		return attributesById.keySet();
	}

	@Override
	public boolean hasAttribute(final String attributeKey)
	{
		return attributesByKey.containsKey(attributeKey);
	}

	@Override
	public boolean hasAttribute(final AttributeId attributeId)
	{
		return attributesById.containsKey(attributeId);
	}

	private void assertAttributeExists(final String attributeKey)
	{
		if (!hasAttribute(attributeKey))
		{
			throw new AdempiereException("Attribute does not exist: " + attributeKey);
		}
	}

	private void assertAttributeExists(final AttributeId attributeId)
	{
		if (!hasAttribute(attributeId))
		{
			throw new AdempiereException("Attribute does not exist: " + attributeId);
		}
	}

	@Override
	public I_M_Attribute getAttributeByIdIfExists(final int attributeId)
	{
		return getAttributeByIdIfExists(AttributeId.ofRepoIdOrNull(attributeId));
	}

	@Override
	public I_M_Attribute getAttributeByIdIfExists(final AttributeId attributeId)
	{
		return attributesById.get(attributeId);
	}

	@Override
	public String getAttributeValueType(@NonNull final I_M_Attribute attribute)
	{
		assertAttributeExists(attribute.getValue());
		return attribute.getAttributeValueType();
	}

	@Override
	public Object getValue(@NonNull final String attributeKey)
	{
		assertAttributeExists(attributeKey);
		return valuesByAttributeKey.get(attributeKey);
	}

	public Object getValue(@NonNull final AttributeId attributeId)
	{
		assertAttributeExists(attributeId);
		final I_M_Attribute attribute = getAttributeByIdIfExists(attributeId);
		return getValue(attribute);
	}

	@Override
	public BigDecimal getValueAsBigDecimal(final String attributeKey)
	{
		final Object valueObj = getValue(attributeKey);
		return invokeWithAttributeKey(attributeKey, () -> toBigDecimal(valueObj));
	}

	public BigDecimal getValueAsBigDecimal(@NonNull final AttributeId attributeId)
	{
		return invokeWithAttributeId(attributeId, () -> toBigDecimal(getValue(attributeId)));
	}

	private static BigDecimal toBigDecimal(final Object valueObj)
	{
		if (valueObj == null)
		{
			return null;
		}

		if ("".equals(valueObj))
		{
			return null;
		}

		final BigDecimal valueBD = NumberUtils.asBigDecimal(valueObj, null);
		if (valueBD == null)
		{
			throw new AdempiereException("Cannot convert '" + valueObj + "' (" + valueObj.getClass() + ") to BigDecimal");
		}
		return valueBD;
	}

	@Override
	public int getValueAsInt(final String attributeKey)
	{
		final Object valueObj = getValue(attributeKey);
		if (valueObj == null)
		{
			return 0;
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
				return 0;
			}

			return invokeWithAttributeKey(attributeKey, () -> Integer.parseInt(valueStr));
		}
	}

	@Override
	public Date getValueAsDate(final String attributeKey)
	{
		final Object valueObj = getValue(attributeKey);
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof Date)
		{
			return (Date)valueObj;
		}
		else if (valueObj instanceof Number)
		{
			return new Date(((Number)valueObj).longValue());
		}
		else if (TimeUtil.isDateOrTimeObject(valueObj))
		{
			return invokeWithAttributeKey(attributeKey, () -> TimeUtil.asDate(valueObj));
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}
			try
			{
				return invokeWithAttributeKey(attributeKey, () -> Env.parseTimestamp(valueStr));
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("valueObj", valueObj)
						.setParameter("valueObj.class", valueObj != null ? valueObj.getClass() : null)
						.appendParametersToMessage();
			}
		}
	}

	@Override
	public LocalDate getValueAsLocalDate(final String attributeKey)
	{
		return invokeWithAttributeKey(attributeKey, () -> TimeUtil.asLocalDate(getValueAsDate(attributeKey)));
	}

	private <T> T invokeWithAttributeKey(
			@Nullable final String attributeKey,
			@NonNull final Supplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (final Exception e)
		{
			throw AdempiereException
					.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("immutableAttributeSet", this)
					.setParameter("attributeKey", Check.isEmpty(attributeKey, true) ? "<EMPTY>" : attributeKey);
		}
	}

	private <T> T invokeWithAttributeId(
			@NonNull final AttributeId attributeId,
			@NonNull final Supplier<T> supplier)
	{
		try
		{
			return supplier.get();
		}
		catch (final Exception e)
		{
			throw AdempiereException
					.wrapIfNeeded(e)
					.setParameter("immutableAttributeSet", this)
					.setParameter("attributeId", attributeId);
		}
	}

	public Optional<LocalDate> getValueAsLocalDateIfExists(final String attributeKey)
	{
		if (hasAttribute(attributeKey))
		{
			return Optional.ofNullable(getValueAsLocalDate(attributeKey));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public String getValueAsString(final String attributeKey)
	{
		final Object valueObj = getValue(attributeKey);
		return valueObj != null ? valueObj.toString() : null;
	}

	public Optional<String> getValueAsStringIfExists(final String attributeKey)
	{
		if (hasAttribute(attributeKey))
		{
			return Optional.ofNullable(getValueAsString(attributeKey));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public AttributeValueId getAttributeValueIdOrNull(final String attributeKey)
	{
		return valueIdsByAttributeKey.get(attributeKey);
	}

	public boolean hasAttributeValueIds()
	{
		return !valueIdsByAttributeKey.isEmpty();
	}

	public boolean hasAttributeValueId(final AttributeValueId attributeValueId)
	{
		return valueIdsByAttributeKey.containsValue(attributeValueId);
	}

	public Boolean getValueAsBoolean(final String attributeKey)
	{
		return invokeWithAttributeKey(attributeKey, () -> StringUtils.toBoolean(getValueAsString(attributeKey), null));
	}

	public Optional<Boolean> getValueAsBooleanIfExists(final String attributeKey)
	{
		if (hasAttribute(attributeKey))
		{
			return Optional.ofNullable(getValueAsBoolean(attributeKey));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public void setValue(final String attributeKey, final Object value)
	{
		throw new AdempiereException("Attribute set is immutable: " + this);
	}

	@Override
	public void setValue(final AttributeId attributeId, final Object value)
	{
		throw new AdempiereException("Attribute set is immutable: " + this);
	}

	@Override
	public IAttributeValueCallout getAttributeValueCallout(final I_M_Attribute attribute)
	{
		return NullAttributeValueCallout.instance;
	}

	@Override
	public boolean isNew(final I_M_Attribute attribute)
	{
		return false;
	}

	//
	//
	// -------------------------
	//
	//
	public static final class Builder
	{
		private IAttributeDAO _attributesRepo; // lazy

		private final LinkedHashMap<AttributeId, I_M_Attribute> attributesById = new LinkedHashMap<>();
		private final LinkedHashMap<String, I_M_Attribute> attributesByKey = new LinkedHashMap<>();
		private final LinkedHashMap<String, Object> valuesByAttributeKey = new LinkedHashMap<>();
		private final LinkedHashMap<String, AttributeValueId> valueIdsByAttributeKey = new LinkedHashMap<>();

		private Builder()
		{
		}

		public ImmutableAttributeSet build()
		{
			if (attributesById.isEmpty())
			{
				return EMPTY;
			}
			return new ImmutableAttributeSet(
					ImmutableMap.copyOf(attributesById),
					ImmutableMap.copyOf(attributesByKey),
					ImmutableMap.copyOf(valuesByAttributeKey),
					ImmutableMap.copyOf(valueIdsByAttributeKey));
		}

		private IAttributeDAO attributesRepo()
		{
			if (_attributesRepo == null)
			{
				_attributesRepo = Services.get(IAttributeDAO.class);
			}
			return _attributesRepo;
		}

		public Builder attributeValue(@NonNull final AttributeId attributeId, final Object attributeValue)
		{
			final AttributeValueId attributeValueId = null;
			attributeValue(attributeId, attributeValue, attributeValueId);
			return this;
		}

		public Builder attributeValue(
				@NonNull final AttributeId attributeId,
				final Object attributeValue,
				final AttributeValueId attributeValueId)
		{
			final I_M_Attribute attribute = attributesRepo().getAttributeById(attributeId);
			attributeValue(attribute, attributeValue, attributeValueId);
			return this;
		}

		public Builder attributeValue(@NonNull final String attributeCode, final Object attributeValue)
		{
			final I_M_Attribute attribute = attributesRepo().retrieveAttributeByValue(attributeCode);
			final AttributeValueId attributeValueId = null;
			attributeValue(attribute, attributeValue, attributeValueId);
			return this;
		}

		public Builder attributeValue(@NonNull final AttributeListValue attributeValue)
		{
			final I_M_Attribute attribute = attributesRepo().getAttributeById(attributeValue.getAttributeId());
			final String value = attributeValue.getValue();
			final AttributeValueId attributeValueId = attributeValue.getId();

			attributeValue(attribute, value, attributeValueId);
			return this;
		}

		public Builder attributeValues(@NonNull final AttributeListValue... attributeValues)
		{
			for (AttributeListValue attributeValue : attributeValues)
			{
				attributeValue(attributeValue);
			}
			return this;
		}

		public Builder attributeValue(@NonNull final I_M_Attribute attribute, final Object attributeValue)
		{
			final AttributeValueId attributeValueId = null;
			attributeValue(attribute, attributeValue, attributeValueId);
			return this;
		}

		public Builder attributeValue(
				@NonNull final I_M_Attribute attribute,
				@Nullable final Object attributeValue,
				@Nullable final AttributeValueId attributeValueId)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
			attributesById.put(attributeId, attribute);

			final String attributeKey = attribute.getValue();
			attributesByKey.put(attributeKey, attribute);

			if (attributeValue == null)
			{
				valuesByAttributeKey.remove(attributeKey);
			}
			else
			{
				valuesByAttributeKey.put(attributeKey, attributeValue);
			}

			if (attributeValueId == null)
			{
				valueIdsByAttributeKey.remove(attributeKey);
			}
			else
			{
				valueIdsByAttributeKey.put(attributeKey, attributeValueId);
			}

			return this;
		}

	}
}
