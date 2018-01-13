package de.metas.handlingunits.storage.impl;

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

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import lombok.NonNull;

public class DefaultHUStorageFactory implements IHUStorageFactory
{
	private final IHUStorageDAO storageDAO;

	public DefaultHUStorageFactory()
	{
		this(new HUStorageDAO());
	}

	public DefaultHUStorageFactory(final IHUStorageDAO storageDAO)
	{
		super();

		Check.assumeNotNull(storageDAO, "storageDAO not null");
		this.storageDAO = storageDAO;
	}

	@Override
	public IHUStorage getStorage(@NonNull final I_M_HU hu)
	{
		final HUStorage huStorage = new HUStorage(this, hu);
		return huStorage;
	}

	@Override
	public IHUItemStorage getStorage(final I_M_HU_Item item)
	{
		return new HUItemStorage(this, item);
	}

	@Override
	public IHUStorageDAO getHUStorageDAO()
	{
		return storageDAO;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public List<IHUProductStorage> getHUProductStorages(@NonNull final List<I_M_HU> hus, final int productId)
	{
		return hus.stream()
				.map(this::getStorage)
				.map(huStorage -> huStorage.getProductStorageOrNull(productId))
				.filter(productStorage -> productStorage != null)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Stream<IHUProductStorage> streamHUProductStorages(@NonNull final List<I_M_HU> hus)
	{
		return hus.stream()
				.map(this::getStorage)
				.flatMap(IHUStorage::streamProductStorages);
	}

}
