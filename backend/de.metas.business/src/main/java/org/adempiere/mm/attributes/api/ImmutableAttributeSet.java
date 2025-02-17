package org.adempiere.mm.attributes.api;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.NullAttributeValueCallout;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
 * <p>
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

	public static ImmutableAttributeSet copyOf(@NonNull final IAttributeSet attributeSet)
	{
		if (attributeSet instanceof ImmutableAttributeSet)
		{
			return (ImmutableAttributeSet)attributeSet;
		}

		return createSubSet(attributeSet, attribute -> true);
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
					final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
					final Object value = attributeSet.getValue(attributeCode);
					final AttributeValueId attributeValueId = attributeSet.getAttributeValueIdOrNull(attributeCode);
					builder.attributeValue(attribute, value, attributeValueId);
				});

		return builder.build();
	}

	public static final ImmutableAttributeSet EMPTY = new ImmutableAttributeSet();

	private final ImmutableMap<AttributeId, I_M_Attribute> attributesById;
	private final ImmutableMap<AttributeCode, I_M_Attribute> attributesByCode;
	private final ImmutableMap<AttributeCode, Object> valuesByAttributeCode;
	private final ImmutableMap<AttributeCode, AttributeValueId> valueIdsByAttributeCode;

	private ImmutableAttributeSet(
			@NonNull final ImmutableMap<AttributeId, I_M_Attribute> attributesById,
			@NonNull final ImmutableMap<AttributeCode, I_M_Attribute> attributesByCode,
			@NonNull final ImmutableMap<AttributeCode, Object> valuesByAttributeCode,
			@NonNull final ImmutableMap<AttributeCode, AttributeValueId> valueIdsByAttributeCode)
	{
		this.attributesById = attributesById;
		this.attributesByCode = attributesByCode;
		this.valuesByAttributeCode = valuesByAttributeCode;
		this.valueIdsByAttributeCode = valueIdsByAttributeCode;
	}

	private ImmutableAttributeSet()
	{
		attributesById = ImmutableMap.of();
		attributesByCode = ImmutableMap.of();
		valuesByAttributeCode = ImmutableMap.of();
		valueIdsByAttributeCode = ImmutableMap.of();
	}

	@Override
	@Deprecated
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("values", valuesByAttributeCode)
				.add("valueIds", valueIdsByAttributeCode)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(valuesByAttributeCode);
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
			final ImmutableAttributeSet other = (ImmutableAttributeSet)obj;
			return Objects.equals(valuesByAttributeCode, other.valuesByAttributeCode);
		}
		else
		{
			return false;
		}
	}

	public boolean isEmpty()
	{
		return attributesByCode.isEmpty();
	}

	@Override
	public ImmutableCollection<I_M_Attribute> getAttributes()
	{
		return attributesByCode.values();
	}

	public ImmutableSet<AttributeCode> getAttributeCodes()
	{
		return attributesByCode.keySet();
	}

	public ImmutableSet<AttributeId> getAttributeIds()
	{
		return attributesById.keySet();
	}

	public AttributeId getAttributeIdByCode(@NonNull final AttributeCode attributeCode)
	{
		return AttributeId.ofRepoId(getAttributeByCode(attributeCode).getM_Attribute_ID());
	}

	@Override
	public boolean hasAttribute(final AttributeCode attributeCode)
	{
		return attributesByCode.containsKey(attributeCode);
	}

	@Override
	public boolean hasAttribute(final AttributeId attributeId)
	{
		return attributesById.containsKey(attributeId);
	}

	private void assertAttributeExists(final AttributeCode attributeCode)
	{
		if (!hasAttribute(attributeCode))
		{
			throw new AdempiereException("Attribute does not exist: " + attributeCode);
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
		return getAttributeByIdIfExists(AttributeId.ofRepoId(attributeId));
	}

	@Override
	public I_M_Attribute getAttributeByIdIfExists(final @NonNull AttributeId attributeId)
	{
		return attributesById.get(attributeId);
	}

	public I_M_Attribute getAttributeByCode(final @NonNull AttributeCode attributeCode)
	{
		final I_M_Attribute attribute = attributesByCode.get(attributeCode);
		if (attribute == null)
		{
			throw new AdempiereException("Attribute does not exist: " + attributeCode);
		}
		return attribute;
	}

	@Override
	public String getAttributeValueType(@NonNull final I_M_Attribute attribute)
	{
		assertAttributeExists(AttributeCode.ofString(attribute.getValue()));
		return attribute.getAttributeValueType();
	}

	public AttributeValueType getAttributeValueType(@NonNull final AttributeCode attributeCode)
	{
		final I_M_Attribute attribute = getAttributeByCode(attributeCode);
		return AttributeValueType.ofCode(attribute.getAttributeValueType());
	}

	@Override
	public Object getValue(@NonNull final AttributeCode attributeCode)
	{
		assertAttributeExists(attributeCode);
		return valuesByAttributeCode.get(attributeCode);
	}

	public Object getValue(@NonNull final AttributeId attributeId)
	{
		assertAttributeExists(attributeId);
		final I_M_Attribute attribute = getAttributeByIdIfExists(attributeId);
		if (attribute == null)
		{
			throw new AdempiereException("Attribute does not exist: " + attributeId);
		}
		return getValue(attribute);
	}

	@Override
	public BigDecimal getValueAsBigDecimal(final @NonNull AttributeCode attributeCode)
	{
		final Object valueObj = getValue(attributeCode);
		return invokeWithAttributeKey(attributeCode, () -> toBigDecimal(valueObj));
	}

	public BigDecimal getValueAsBigDecimal(@NonNull final AttributeId attributeId)
	{
		return invokeWithAttributeId(attributeId, () -> toBigDecimal(getValue(attributeId)));
	}

	@Nullable
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
	public int getValueAsInt(final AttributeCode attributeCode)
	{
		final Object valueObj = getValue(attributeCode);
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

			return invokeWithAttributeKey(attributeCode, () -> Integer.parseInt(valueStr));
		}
	}

	@Nullable
	@Override
	public Date getValueAsDate(final AttributeCode attributeCode)
	{
		final Object valueObj = getValue(attributeCode);
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
			return invokeWithAttributeKey(attributeCode, () -> TimeUtil.asDate(valueObj));
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
				return invokeWithAttributeKey(attributeCode, () -> Env.parseTimestamp(valueStr));
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("valueObj", valueObj)
						.setParameter("valueObj.class", valueObj.getClass())
						.appendParametersToMessage();
			}
		}
	}

	@Override
	public LocalDate getValueAsLocalDate(final AttributeCode attributeCode)
	{
		return invokeWithAttributeKey(attributeCode, () -> TimeUtil.asLocalDate(getValueAsDate(attributeCode)));
	}

	private <T> T invokeWithAttributeKey(
			@Nullable final AttributeCode attributeCode,
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
					.setParameter("attributeCode", attributeCode == null ? "<EMPTY>" : attributeCode.getCode());
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

	public Optional<LocalDate> getValueAsLocalDateIfExists(final AttributeCode attributeCode)
	{
		if (hasAttribute(attributeCode))
		{
			return Optional.ofNullable(getValueAsLocalDate(attributeCode));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	@Nullable
	public String getValueAsString(@NonNull final AttributeCode attributeCode)
	{
		final Object valueObj = getValue(attributeCode);
		return valueObj != null ? valueObj.toString() : null;
	}

	@Override
	@Nullable
	public String getValueAsStringOrNull(@NonNull final AttributeCode attributeCode)
	{
		return getValueAsString(attributeCode);
	}

	public Optional<String> getValueAsStringIfExists(final AttributeCode attributeCode)
	{
		if (hasAttribute(attributeCode))
		{
			return Optional.ofNullable(getValueAsString(attributeCode));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public AttributeValueId getAttributeValueIdOrNull(final AttributeCode attributeCode)
	{
		return valueIdsByAttributeCode.get(attributeCode);
	}

	public boolean hasAttributeValueIds()
	{
		return !valueIdsByAttributeCode.isEmpty();
	}

	public boolean hasAttributeValueId(final AttributeValueId attributeValueId)
	{
		return valueIdsByAttributeCode.containsValue(attributeValueId);
	}

	public Boolean getValueAsBoolean(final AttributeCode attributeCode)
	{
		return invokeWithAttributeKey(attributeCode, () -> StringUtils.toBoolean(getValueAsString(attributeCode), null));
	}

	public Optional<Boolean> getValueAsBooleanIfExists(final AttributeCode attributeCode)
	{
		if (hasAttribute(attributeCode))
		{
			return Optional.ofNullable(getValueAsBoolean(attributeCode));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public void setValue(final AttributeCode attributeCode, final Object value)
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

	public ImmutableAttributeSet filterOnlyStorageRelevantAttributes()
	{
		return createSubSet(this, I_M_Attribute::isStorageRelevant);
	}

	public boolean containsAttributeValues(@NonNull final ImmutableAttributeSet targetImmutableAttributeSet)
	{
		return targetImmutableAttributeSet.getAttributeIds()
				.stream()
				.allMatch(attributeId -> {
					if (!this.hasAttribute(attributeId))
					{
						return false;
					}

					final String targetValueType = targetImmutableAttributeSet.getAttributeValueType(attributeId);
					final String actualValueType = this.getAttributeValueType(attributeId);

					if (!targetValueType.equals(actualValueType))
					{
						return false;
					}

					final Object targetValue = targetImmutableAttributeSet.getValue(attributeId);
					final Object exitingValue = this.getValue(attributeId);

					return Objects.equals(targetValue, exitingValue);
				});
	}

	//
	//
	// -------------------------
	//
	//
	public static final class Builder
	{
		private IAttributeDAO _attributesRepo; // lazy

		private final LinkedHashMap<AttributeId, I_M_Attribute> attributesById;
		private final LinkedHashMap<AttributeCode, I_M_Attribute> attributesByCode;
		private final LinkedHashMap<AttributeCode, Object> valuesByAttributeCode;
		private final LinkedHashMap<AttributeCode, AttributeValueId> valueIdsByAttributeCode;

		private Builder()
		{
			attributesById = new LinkedHashMap<>();
			attributesByCode = new LinkedHashMap<>();
			valuesByAttributeCode = new LinkedHashMap<>();
			valueIdsByAttributeCode = new LinkedHashMap<>();
		}

		public Builder(@NonNull final Builder other)
		{
			this.attributesById = new LinkedHashMap<>(other.attributesById);
			this.attributesByCode = new LinkedHashMap<>(other.attributesByCode);
			this.valuesByAttributeCode = new LinkedHashMap<>(other.valuesByAttributeCode);
			this.valueIdsByAttributeCode = new LinkedHashMap<>(other.valueIdsByAttributeCode);
		}

		public ImmutableAttributeSet build()
		{
			if (attributesById.isEmpty())
			{
				return EMPTY;
			}
			return new ImmutableAttributeSet(
					ImmutableMap.copyOf(attributesById),
					ImmutableMap.copyOf(attributesByCode),
					ImmutableMap.copyOf(valuesByAttributeCode),
					ImmutableMap.copyOf(valueIdsByAttributeCode));
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
				@Nullable final AttributeValueId attributeValueId)
		{
			final I_M_Attribute attribute = attributesRepo().getAttributeById(attributeId);
			attributeValue(attribute, attributeValue, attributeValueId);
			return this;
		}

		public Builder attributeValue(@NonNull final AttributeCode attributeCode, @Nullable final Object attributeValue)
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
			for (final AttributeListValue attributeValue : attributeValues)
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

			final AttributeCode attributeCode = AttributeCode.ofString(attribute.getValue());
			attributesByCode.put(attributeCode, attribute);

			if (attributeValue == null)
			{
				valuesByAttributeCode.remove(attributeCode);
			}
			else
			{
				valuesByAttributeCode.put(attributeCode, attributeValue);
			}

			if (attributeValueId == null)
			{
				valueIdsByAttributeCode.remove(attributeCode);
			}
			else
			{
				valueIdsByAttributeCode.put(attributeCode, attributeValueId);
			}

			return this;
		}

		public Builder createCopy()
		{
			return new Builder(this);
		}
	}
}
