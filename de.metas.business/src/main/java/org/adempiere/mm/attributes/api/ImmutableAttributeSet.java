package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.NullAttributeValueCallout;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class ImmutableAttributeSet implements IAttributeSet
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final ImmutableAttributeSet ofValuesIndexByAttributeId(
			@Nullable final Map<Object, Object> valuesByAttributeIdObj)
	{
		if (valuesByAttributeIdObj == null || valuesByAttributeIdObj.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableMap.Builder<Integer, I_M_Attribute> attributes = ImmutableMap.builder();
		final ImmutableMap.Builder<Integer, Object> valuesByAttributeId = ImmutableMap.builder();

		valuesByAttributeIdObj.forEach((attributeIdObj, value) -> {
			final int attributeId = Integer.parseInt(attributeIdObj.toString());
			final I_M_Attribute attribute = load(attributeId, I_M_Attribute.class);

			attributes.put(attributeId, attribute);
			valuesByAttributeId.put(attributeId, value);
		});

		return new ImmutableAttributeSet(attributes.build(), valuesByAttributeId.build());
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
					builder.attributeValue(attribute, value);
				});

		return builder.build();
	}

	public static final ImmutableAttributeSet EMPTY = new ImmutableAttributeSet();

	private final ImmutableMap<Integer, I_M_Attribute> attributes;
	private final ImmutableMap<Integer, Object> valuesByAttributeId;

	private ImmutableAttributeSet(
			@NonNull final ImmutableMap<Integer, I_M_Attribute> attributes,
			@NonNull final ImmutableMap<Integer, Object> valuesByAttributeId)
	{
		this.attributes = attributes;
		this.valuesByAttributeId = valuesByAttributeId;
	}

	private ImmutableAttributeSet()
	{
		attributes = ImmutableMap.of();
		valuesByAttributeId = ImmutableMap.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(valuesByAttributeId)
				.toString();
	}

	public boolean isEmpty()
	{
		return attributes.isEmpty();
	}

	@Override
	public Collection<I_M_Attribute> getAttributes()
	{
		return attributes.values();
	}

	@Override
	public boolean hasAttribute(final I_M_Attribute attribute)
	{
		return attributes.containsKey(attribute.getM_Attribute_ID());
	}

	private final void assertAttributeExists(final I_M_Attribute attribute)
	{
		if (!hasAttribute(attribute))
		{
			throw new AdempiereException("Attribute does not exist: " + attribute);
		}
	}

	@Override
	public I_M_Attribute getAttributeByIdIfExists(final int attributeId)
	{
		return attributes.get(attributeId);
	}

	@Override
	public String getAttributeValueType(final I_M_Attribute attribute)
	{
		assertAttributeExists(attribute);
		return attribute.getAttributeValueType();
	}

	@Override
	public Object getValue(final I_M_Attribute attribute)
	{
		assertAttributeExists(attribute);
		return valuesByAttributeId.get(attribute.getM_Attribute_ID());
	}

	@Override
	public BigDecimal getValueAsBigDecimal(final I_M_Attribute attribute)
	{
		final Object valueObj = getValue(attribute);
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof BigDecimal)
		{
			return (BigDecimal)valueObj;
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}

			return new BigDecimal(valueStr);
		}
	}

	@Override
	public int getValueAsInt(final I_M_Attribute attribute)
	{
		final Object valueObj = getValue(attribute);
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

			return Integer.parseInt(valueStr);
		}
	}

	@Override
	public Date getValueAsDate(final I_M_Attribute attribute)
	{
		final Object valueObj = getValue(attribute);
		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof Number)
		{
			return new Date(((Number)valueObj).longValue());
		}
		else
		{
			final String valueStr = valueObj.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}

			return Env.parseTimestamp(valueStr);
		}
	}

	@Override
	public String getValueAsString(final I_M_Attribute attribute)
	{
		final Object valueObj = getValue(attribute);
		return valueObj != null ? valueObj.toString() : null;
	}

	@Override
	public void setValue(final I_M_Attribute attribute, final Object value)
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
		private final LinkedHashMap<Integer, Object> valuesByAttributeId = new LinkedHashMap<>();
		private final LinkedHashMap<Integer, I_M_Attribute> attributes = new LinkedHashMap<>();

		private Builder()
		{
		}

		public ImmutableAttributeSet build()
		{
			if (attributes.isEmpty())
			{
				return EMPTY;
			}
			return new ImmutableAttributeSet(
					ImmutableMap.copyOf(attributes),
					ImmutableMap.copyOf(valuesByAttributeId));
		}

		public Builder attributeValue(final int attributeId, final Object attributeValue)
		{
			final I_M_Attribute attribute = loadOutOfTrx(attributeId, I_M_Attribute.class);
			attributeValue(attribute, attributeValue);
			return this;
		}

		public Builder attributeValue(@NonNull final I_M_Attribute attribute, final Object attributeValue)
		{
			final int attributeId = attribute.getM_Attribute_ID();
			attributes.put(attributeId, attribute);

			if (attributeValue == null)
			{
				valuesByAttributeId.remove(attributeId);
			}
			else
			{
				valuesByAttributeId.put(attributeId, attributeValue);
			}

			return this;
		}

	}
}
