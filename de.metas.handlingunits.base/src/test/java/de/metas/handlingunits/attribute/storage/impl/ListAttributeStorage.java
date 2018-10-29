package de.metas.handlingunits.attribute.storage.impl;

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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.junit.Ignore;

import com.google.common.base.MoreObjects.ToStringHelper;

import de.metas.handlingunits.IMutableHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;

@Ignore
public class ListAttributeStorage extends AbstractAttributeStorage
{
	private final String id;
	private final List<IAttributeValue> initalAttributeValues;

	public ListAttributeStorage(final IAttributeStorageFactory storageFactory, final List<IAttributeValue> initalAttributeValues)
	{
		super(storageFactory);
		this.initalAttributeValues = initalAttributeValues;

		// generate a random ID
		id = UUID.randomUUID().toString();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public IAttributeStorage getParentAttributeStorage()
	{
		return null;
	}

	/**
	 * Always returns an empty list.
	 */
	@Override
	public final List<IAttributeStorage> getChildAttributeStorages(final boolean loadIfNeeded_IGNORED)
	{
		return Collections.emptyList();
	}

	@Override
	public void updateHUTrxAttribute(final IMutableHUTransactionAttribute huTrxAttribute, final IAttributeValue fromAttributeValue)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void toString(final ToStringHelper stringHelper)
	{
		stringHelper
				.add("id", id)
				.add("initalAttributeValues", initalAttributeValues);
	}

	@Override
	protected List<IAttributeValue> loadAttributeValues()
	{
		return initalAttributeValues;
	}

	@Override
	protected List<IAttributeValue> generateAndGetInitialAttributes(final IAttributeValueContext attributesCtx, final Map<AttributeId, Object> defaultAttributesValue)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Method not supported.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected void addChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	/**
	 * Method not supported.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected IAttributeStorage removeChildAttributeStorage(final IAttributeStorage childAttributeStorage)
	{
		throw new UnsupportedOperationException("Child attribute storages are not supported for " + this);
	}

	@Override
	public void saveChangesIfNeeded()
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void setSaveOnChange(final boolean saveOnChange)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String getQtyUOMTypeOrNull()
	{
		throw new UnsupportedOperationException();
	}
}
