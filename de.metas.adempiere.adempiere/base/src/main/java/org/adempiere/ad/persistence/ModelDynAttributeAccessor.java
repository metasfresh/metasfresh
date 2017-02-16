package org.adempiere.ad.persistence;

import java.util.Objects;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/**
 * Convenient model's dynamic attribute accessor. This instance provides type-safe access to a model's dynamic attributes.
 * The it's recommended to use it rather that directly calling {@link InterfaceWrapperHelper#setDynAttribute(Object, String, Object)} etc.
 * Neither the model not the attribute value is referenced by instance of this class.
 *
 * @author tsa
 *
 * @param <ModelType>
 * @param <AttributeType>
 */
public final class ModelDynAttributeAccessor<ModelType, AttributeType>
{
	private final String attributeName;
	private final Class<AttributeType> attributeType;

	public ModelDynAttributeAccessor(final Class<AttributeType> attributeTypeClass)
	{
		super();

		Check.assumeNotNull(attributeTypeClass, "attributeTypeClass not null");

		this.attributeName = attributeTypeClass.getName();
		this.attributeType = attributeTypeClass;
	}

	public ModelDynAttributeAccessor(final String attributeName, final Class<AttributeType> attributeTypeClass)
	{
		super();

		Check.assumeNotEmpty(attributeName, "attributeName not empty");
		Check.assumeNotNull(attributeTypeClass, "attributeTypeClass not null");

		this.attributeName = attributeName;
		this.attributeType = attributeTypeClass;
	}

	public ModelDynAttributeAccessor(final String attributeGroupName, final String attributeName, final Class<AttributeType> attributeTypeClass)
	{
		this(attributeGroupName + "#" + attributeName // attributeName
				, attributeTypeClass);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public String getAttributeName()
	{
		return attributeName;
	}

	public Class<AttributeType> getAttributeType()
	{
		Check.assumeNotNull(attributeType, "attributeType not null");
		return attributeType;
	}

	public AttributeType getValue(final ModelType model)
	{
		final AttributeType attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return attributeValue;
	}

	public AttributeType getValue(final ModelType model, final AttributeType defaultValueIfNull)
	{
		final AttributeType attributeValue = getValue(model);
		if (attributeValue == null)
		{
			return defaultValueIfNull;
		}
		return attributeValue;
	}

	public void setValue(final ModelType model, final AttributeType attributeValue)
	{
		InterfaceWrapperHelper.setDynAttribute(model, attributeName, attributeValue);
	}

	public boolean isSet(final ModelType model)
	{
		final Object attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return attributeValue != null;
	}

	public boolean isNull(final ModelType model)
	{
		final Object attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return attributeValue == null;
	}

	/** @return true if given <code>model</code>'s attribute equals with <code>expectedValue</code> */
	public boolean is(final ModelType model, final AttributeType expectedValue)
	{
		final Object attributeValue = InterfaceWrapperHelper.getDynAttribute(model, attributeName);
		return Objects.equals(attributeValue, expectedValue);
	}

	public void reset(final ModelType model)
	{
		final AttributeType attributeValue = null;
		setValue(model, attributeValue);
	}
}
