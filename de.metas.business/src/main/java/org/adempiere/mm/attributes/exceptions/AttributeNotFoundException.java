package org.adempiere.mm.attributes.exceptions;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;

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
			attributeSet = (IAttributeSet)attributeSetObj;
		}
		else
		{
			attributeSet = null;
		}
	}

	public AttributeNotFoundException(final String attributeValueKey, final Object attributeSetObj)
	{
		super(buildMsg(attributeValueKey, attributeSetObj));
		attribute = null;
		if (attributeSetObj instanceof IAttributeSet)
		{
			attributeSet = (IAttributeSet)attributeSetObj;
		}
		else
		{
			attributeSet = null;
		}
	}

	public AttributeNotFoundException(final AttributeId attributeId, final Object attributeSetObj)
	{
		this(attributeId.toString(), attributeSetObj);
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

	private static final ITranslatableString buildMsg(final I_M_Attribute attribute, final Object attributeSetObj)
	{
		final String attributeStr = toString(attribute);
		return buildMsg(attributeStr, attributeSetObj);
	}

	private static final ITranslatableString buildMsg(final String attributeStr, final Object attributeSetObj)
	{
		final TranslatableStringBuilder builder = TranslatableStrings.builder();
		builder.append("Attribute ");

		if (attributeStr == null)
		{
			builder.append("<NULL>");
		}
		else
		{
			builder.append("'").append(attributeStr).append("'");

		}

		builder.append(" was not found");

		if (attributeSetObj != null)
		{
			builder.append(" for ").append(attributeSetObj.toString());
		}

		return builder.build();
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
