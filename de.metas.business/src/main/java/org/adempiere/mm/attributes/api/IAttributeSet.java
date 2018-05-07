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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.adempiere.mm.attributes.exceptions.AttributeNotFoundException;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.compiere.model.I_M_Attribute;

import lombok.NonNull;

/**
 * Goal of this interface: get an instance from an attribute set instance, one can use in a storage context.
 *
 * @see ImmutableAttributeSet
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IAttributeSet
{
	/**
	 * @return collection of available attributes in this set
	 */
	Collection<I_M_Attribute> getAttributes();

	/**
	 * @return true if the given attribute is available for getting/setting
	 */
	boolean hasAttribute(String attribute);

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
	 * @param attributeId
	 * @return {@link I_M_Attribute} or <code>null</code>
	 */
	I_M_Attribute getAttributeByIdIfExists(int attributeId);

	/**
	 * Gets attribute's value type (see X_M_Attribute.ATTRIBUTEVALUETYPE_*)
	 *
	 * @return attribute's value type; never null
	 */
	String getAttributeValueType(I_M_Attribute attribute);

	/**
	 * @return value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	Object getValue(String attributeKey);

	default Object getValue(@NonNull final I_M_Attribute attribute)
	{
		return getValue(attribute.getValue());
	}

	/**
	 * @return BigDecimal value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	BigDecimal getValueAsBigDecimal(String attributeKey);

	default BigDecimal getValueAsBigDecimal(final I_M_Attribute attribute)
	{
		return getValueAsBigDecimal(attribute.getValue());
	}

	/**
	 * @return integer value of given attribute
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	int getValueAsInt(String attributeKey);

	default int getValueAsInt(final I_M_Attribute attribute)
	{
		return getValueAsInt(attribute.getValue());
	}

	Date getValueAsDate(String attributeKey);

	default Date getValueAsDate(final I_M_Attribute attribute)
	{
		return getValueAsDate(attribute.getValue());
	}

	String getValueAsString(String attributeKey);

	default String getValueAsString(@NonNull final I_M_Attribute attribute)
	{
		return getValueAsString(attribute.getValue());
	}

	/**
	 * Set attribute's value and propagate to its parent/child attribute sets.
	 * @throws AttributeNotFoundException if given attribute was not found or is not supported
	 */
	void setValue(String attribute, Object value);

	default void setValue(final I_M_Attribute attribute, final Object value)
	{
		setValue(attribute.getValue(), value);
	}

	/**
	 * @return {@link IAttributeValueCallout} instance; never return null
	 */
	IAttributeValueCallout getAttributeValueCallout(final I_M_Attribute attribute);

	/**
	 *
	 * @param attribute
	 * @return true if the given <code>attribute</code>'s value was newly generated
	 */
	boolean isNew(final I_M_Attribute attribute);
}
