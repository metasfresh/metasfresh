package de.metas.handlingunits.attribute.strategy.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.List;

import org.compiere.model.I_M_Attribute;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitRequest;

public class MutableAttributeSplitRequest implements IAttributeSplitRequest
{
	private final IAttributeStorage parentAttributeStorage;
	private final List<IAttributeStorage> attributeStorages;
	private final I_M_Attribute attribute;

	private IAttributeStorage attributeStorageCurrent;
	private Integer attributeStorageCurrentIndex = null;
	private Object valueInitial;
	private Object valueToSplit;

	public MutableAttributeSplitRequest(final IAttributeStorage parentAttributeStorage,
			final List<IAttributeStorage> attributeStorages,
			final org.compiere.model.I_M_Attribute attribute)
	{
		super();
		Check.assumeNotNull(parentAttributeStorage, "parentAttributeStorage not null");
		this.parentAttributeStorage = parentAttributeStorage;

		Check.assumeNotNull(attributeStorages, "attributeStorages not null");
		this.attributeStorages = Collections.unmodifiableList(attributeStorages);

		Check.assumeNotNull(attribute, "attribute not null");
		this.attribute = InterfaceWrapperHelper.create(attribute, I_M_Attribute.class);
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		return parentAttributeStorage;
	}

	@Override
	public List<IAttributeStorage> getAttributeStorages()
	{
		return attributeStorages;
	}

	@Override
	public IAttributeStorage getAttributeStorageCurrent()
	{
		Check.assumeNotNull(attributeStorageCurrent, "attributeStorageCurrent shall be set before");
		return attributeStorageCurrent;
	}

	@Override
	public I_M_Attribute getM_Attribute()
	{
		return attribute;
	}

	public void setAttributeStorageCurrent(final IAttributeStorage attributeStorageCurrent)
	{
		this.attributeStorageCurrent = attributeStorageCurrent;
	}

	public void setAttributeStorageCurrentIndex(final int attributeStorageCurrentIndex)
	{
		this.attributeStorageCurrentIndex = attributeStorageCurrentIndex;
	}

	@Override
	public int getAttributeStorageCurrentIndex()
	{
		Check.assumeNotNull(attributeStorageCurrentIndex, "attributeStorageCurrentIndex shall be set before");
		return attributeStorageCurrentIndex;
	}

	@Override
	public Object getValueInitial()
	{
		return valueInitial;
	}

	public void setValueInitial(final Object valueInitial)
	{
		this.valueInitial = valueInitial;
	}

	@Override
	public Object getValueToSplit()
	{
		return valueToSplit;
	}

	public void setValueToSplit(final Object valueToSplit)
	{
		this.valueToSplit = valueToSplit;
	}
}
