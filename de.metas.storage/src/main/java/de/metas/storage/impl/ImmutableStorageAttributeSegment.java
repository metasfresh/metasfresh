package de.metas.storage.impl;

/*
 * #%L
 * de.metas.storage
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


import org.adempiere.util.lang.ObjectUtils;

import de.metas.storage.IStorageAttributeSegment;

public class ImmutableStorageAttributeSegment implements IStorageAttributeSegment
{

	private final int attributeSetInstanceId;
	private final int attributeId;

	public ImmutableStorageAttributeSegment(final int attributeSetInstanceId, final int attributeId)
	{
		super();
		this.attributeSetInstanceId = attributeSetInstanceId <= 0 ? -1 : attributeSetInstanceId;
		this.attributeId = attributeId <= 0 ? -1 : attributeId;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return attributeSetInstanceId;
	}

	@Override
	public int getM_Attribute_ID()
	{
		return attributeId;
	}

}
