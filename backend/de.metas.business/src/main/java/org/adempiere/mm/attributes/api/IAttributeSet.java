package org.adempiere.mm.attributes.api;

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

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.exceptions.AttributeNotFoundException;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * Goal of this interface: get an instance from an attribute set instance, one can use in a storage context.
 * <p>
 * Also see {@link ImmutableAttributeSet} for methods to create simple instances that are not necessarily in a HU context.
 */
public interface IAttributeSet
{
	/**
	 * @return collection of available attributes in this set. Never return {@code null}.
	 */
	Collection<I_M_Attribute> getAttributes();

	/**
	 * @return true if the given attribute is available for getting/setting.<br>
	 * Note that the attribute's value might still be {@code null}!
	 */
	boolean hasAttribute(AttributeCode attributeCode);

	/**
	 * @see #hasAttribute(AttributeCode)
	 */
	default boolean hasAttribute(@NonNull final String attributeKey)
	{
		return hasAttribute(AttributeCode.ofString(attributeKey));
	}

	boolean hasAttribute(AttributeId attributeId);

	/**
	 * @return true if the given attribute is available for getting/setting
	 */
	default boolean hasAttribute(@NonNull final I_M_Attribute attribute)
	{
		return hasAttribute(attribute.getValue());
	}

	/**
	 * Gets {@link I_M_Attribute} by ID if exists in this attributes set.
	 *
	 * @return {@link I_M_Attribute} or <code>null</code>
	 */
	@Nullable
	I_M_Attribute getAttributeByIdIfExists(int attributeId);

	@Nullable
	default I_M_Attribute getAttributeByIdIfExists(@NonNull final AttributeId attributeId)
	{
		return getAttributeByIdIfExists(attributeId.getRepoId());
	}

	/**
	 * Gets attribute's value type (see X_M_Attribute.ATTRIBUTEVALUETYPE_*)
	 *
	 * @return attribute's value type; never null
	 */
	String getAttributeValueType(I_M_Attribute attribute);

	default String getAttributeValueType(@NonNull final AttributeId attributeId)
	{
		final I_M_Attribute attributeRecord = getAttributeByIdIfExists(attributeId);
		if (attributeRecord == null)
		{
			throw new AdempiereException("Attribute with M_Attribute_ID=" + attributeId.getRepoId() + " does not exist in this instance");
		}
		return getAttributeValueType(attributeRecord);
	}

	default String getAttributeName(@NonNull final AttributeId attributeId)
	{
		final I_M_Attribute attributeRecord = getAttributeByIdIfExists(attributeId);
		if (attributeRecord == null)
		{
			throw new AdempiereException("Attribute with M_Attribute_ID=" + attributeId.getRepoId() + " does not exist in this instance");
		}
		return attributeRecord.getName();
	}

	/**
	 * @return {@code M_Attribute.Value}.
	 */
	default @NonNull AttributeCode getAttributeCode(@NonNull final AttributeId attributeId)
	{
		final I_M_Attribute attributeRecord = getAttributeByIdIfExists(attributeId);
		if (attributeRecord == null)
		{
			throw new AdempiereException("Attribute with M_Attribute_ID=" + attributeId.getRepoId() + " does not exist in this instance");
		}
		return AttributeCode.ofString(attributeRecord.getValue());
	}

	/**
	 * @return value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	Object getValue(AttributeCode attributeCode);

	default Object getValue(@NonNull final String attributeKey)
	{
		return getValue(AttributeCode.ofString(attributeKey));
	}

	default Object getValue(@NonNull final I_M_Attribute attribute)
	{
		return getValue(attribute.getValue());
	}

	/**
	 * @return BigDecimal value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	BigDecimal getValueAsBigDecimal(@NonNull AttributeCode attributeCode);

	default BigDecimal getValueAsBigDecimal(@NonNull final String attributeKey)
	{
		return getValueAsBigDecimal(AttributeCode.ofString(attributeKey));
	}

	default BigDecimal getValueAsBigDecimal(@NonNull final I_M_Attribute attribute)
	{
		return getValueAsBigDecimal(attribute.getValue());
	}

	/**
	 * @return integer value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	int getValueAsInt(AttributeCode attributeCode);

	default int getValueAsInt(@NonNull final String attributeKey)
	{
		return getValueAsInt(AttributeCode.ofString(attributeKey));
	}

	default int getValueAsInt(@NonNull final I_M_Attribute attribute)
	{
		return getValueAsInt(attribute.getValue());
	}

	@Nullable
	Date getValueAsDate(AttributeCode attributeCode);

	@Nullable
	default Date getValueAsDate(@NonNull final String attributeKey)
	{
		return getValueAsDate(AttributeCode.ofString(attributeKey));
	}

	@Nullable
	default Date getValueAsDate(@NonNull final I_M_Attribute attribute)
	{
		return getValueAsDate(attribute.getValue());
	}

	@Nullable default LocalDateTime getValueAsLocalDateTime(final AttributeCode attributeCode)
	{
		return TimeUtil.asLocalDateTime(getValueAsDate(attributeCode));
	}

	@Nullable
	default LocalDate getValueAsLocalDate(final AttributeCode attributeCode)
	{
		return TimeUtil.asLocalDate(getValueAsDate(attributeCode));
	}

	@Nullable
	String getValueAsString(AttributeCode attributeCode);

	@Nullable
	String getValueAsStringOrNull(AttributeCode attributeCode);

	@Nullable
	default String getValueAsString(@NonNull final String attributeKey)
	{
		return getValueAsString(AttributeCode.ofString(attributeKey));
	}

	@Nullable
	default String getValueAsString(@NonNull final I_M_Attribute attribute)
	{
		return getValueAsString(AttributeCode.ofString(attribute.getValue()));
	}

	@Nullable
	default AttributeValueId getAttributeValueIdOrNull(final AttributeCode attributeCode)
	{
		return null;
	}

	/**
	 * Set attribute's value and propagate to its parent/child attribute sets.
	 *
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	void setValue(AttributeCode attributeCode, Object value);

	default void setValue(@NonNull final String attribute, final Object value)
	{
		setValue(AttributeCode.ofString(attribute), value);
	}

	void setValue(AttributeId attributeId, Object value);

	default void setValue(final @NonNull I_M_Attribute attribute, final Object value)
	{
		setValue(attribute.getValue(), value);
	}

	/**
	 * @return {@link IAttributeValueCallout} instance; never return null
	 */
	IAttributeValueCallout getAttributeValueCallout(final I_M_Attribute attribute);

	/**
	 * @return true if the given <code>attribute</code>'s value was newly generated
	 */
	boolean isNew(final I_M_Attribute attribute);
}
