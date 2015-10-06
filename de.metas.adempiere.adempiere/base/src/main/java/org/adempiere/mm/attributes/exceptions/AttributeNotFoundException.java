package org.adempiere.mm.attributes.exceptions;

/*
 * #%L
 * ADempiere ERP - Base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

public class AttributeNotFoundException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7800379395702483714L;

	private final I_M_Attribute attribute;
	private final IAttributeSet attributeSet;

	public AttributeNotFoundException(final I_M_Attribute attribute, final Object attributeSetObj)
	{
		super(buildMsg(attribute, attributeSetObj));

		this.attribute = attribute;
		if (attributeSetObj instanceof IAttributeSet)
		{
			this.attributeSet = (IAttributeSet)attributeSetObj;
		}
		else
		{
			this.attributeSet = null;
		}
	}

	public AttributeNotFoundException(final String attributeValueKey, final Object attributeSetObj)
	{
		super(buildMsg(attributeValueKey, attributeSetObj));
		this.attribute = null;
		if (attributeSetObj instanceof IAttributeSet)
		{
			this.attributeSet = (IAttributeSet)attributeSetObj;
		}
		else
		{
			this.attributeSet = null;
		}
	}

	private static final String toString(final I_M_Attribute attribute)
	{
		if (attribute == null)
		{
			return "<NULL>";
		}
		else
		{
			return attribute.getName();
		}

	}

	private static final String buildMsg(final I_M_Attribute attribute, final Object attributeSetObj)
	{
		final String attributeStr = toString(attribute);
		return buildMsg(attributeStr, attributeSetObj);
	}

	private static final String buildMsg(final String attributeStr, final Object attributeSetObj)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("Attribute ");

		if (attributeStr == null)
		{
			sb.append("<NULL>");
		}
		else
		{
			sb.append("'").append(attributeStr).append("'");

		}

		sb.append(" was not found");

		if (attributeSetObj != null)
		{
			sb.append(" for ").append(attributeSetObj);
		}

		return sb.toString();
	}

	public I_M_Attribute getM_Attribute()
	{
		return attribute;
	}

	public IAttributeSet getAttributeSet()
	{
		return attributeSet;
	}
}
