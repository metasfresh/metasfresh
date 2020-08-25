package de.metas.handlingunits.attribute.exceptions;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;

/**
 * Exception thrown when a given attribute was not found or is not supported for a given {@link IAttributeStorage} or {@link IAttributeSet}.
 *
 * @author tsa
 *
 */
@SuppressWarnings("serial")
public class AttributeNotFoundException extends org.adempiere.mm.attributes.exceptions.AttributeNotFoundException
{
	public AttributeNotFoundException(final I_M_Attribute attribute, final IAttributeStorage attributeStorage)
	{
		super(attribute, attributeStorage);
	}

	public AttributeNotFoundException(final AttributeCode attributeCode, final IAttributeStorage attributeStorage)
	{
		super(
				attributeCode != null ? attributeCode.getCode() : "?",
				attributeStorage);
	}

	public AttributeNotFoundException(final AttributeId attributeId, final IAttributeStorage attributeStorage)
	{
		super(attributeId, attributeStorage);
	}

	public IAttributeStorage getAttributeStorage()
	{
		return (IAttributeStorage)super.getAttributeSet();
	}
}
