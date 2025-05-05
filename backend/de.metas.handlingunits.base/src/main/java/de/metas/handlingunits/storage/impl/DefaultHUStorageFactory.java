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

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@ToString
public class DefaultHUStorageFactory implements IHUStorageFactory
{
	private final IHUStorageDAO storageDAO;

	public DefaultHUStorageFactory()
	{
		this(new HUStorageDAO());
	}

	public DefaultHUStorageFactory(@NonNull final IHUStorageDAO storageDAO)
	{
		this.storageDAO = storageDAO;
	}

	@Override
	public IHUStorage getStorage(@NonNull final I_M_HU hu)
	{
		return new HUStorage(this, hu);
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
	@NonNull
	public List<IHUProductStorage> getHUProductStorages(@NonNull final List<I_M_HU> hus, final ProductId productId)
	{
		return hus.stream()
				.map(this::getStorage)
				.map(huStorage -> huStorage.getProductStorageOrNull(productId))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Stream<IHUProductStorage> streamHUProductStorages(@NonNull final List<I_M_HU> hus)
	{
		return hus.stream()
				.map(this::getStorage)
				.flatMap(IHUStorage::streamProductStorages);
	}

	@Override
	public boolean isSingleProductWithQtyEqualsTo(@NonNull final I_M_HU hu, @NonNull final ProductId productId, @NonNull final Quantity qty)
	{
		return getStorage(hu).isSingleProductWithQtyEqualsTo(productId, qty);
	}

	@Override
	public boolean isSingleProductStorageMatching(@NonNull final I_M_HU hu, @NonNull final ProductId productId)
	{
		return getStorage(hu).isSingleProductStorageMatching(productId);
	}

	@NonNull
	public IHUProductStorage getSingleHUProductStorage(@NonNull final I_M_HU hu)
	{
		return getStorage(hu).getSingleHUProductStorage();
	}
}
