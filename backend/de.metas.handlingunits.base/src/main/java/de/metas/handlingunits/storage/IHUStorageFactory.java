package de.metas.handlingunits.storage;

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
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

/**
 * Factory for HU related quantities.<br>
 * Use {@link IHandlingUnitsBL#getStorageFactory()}, unless you have a {@link IHUContext} to get it from.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUStorageFactory
{
	IHUStorage getStorage(I_M_HU hu);

	IHUItemStorage getStorage(I_M_HU_Item item);

	IHUStorageDAO getHUStorageDAO();

	/**
	 * Iterate all <code>hus</code> and collect the {@link IHUProductStorage} storages from them.
	 *
	 * NOTE: Collect the product storages directly from given HUs. Don't navigate them to collect the product storages from possible included HUs.
	 *
	 * @return product storages; never return {@code null}. Only return items for existing storages. E.g. if none of the given {@code hus} has a storage, return an empty list.
	 */
	List<IHUProductStorage> getHUProductStorages(List<I_M_HU> hus, ProductId productId);

	Stream<IHUProductStorage> streamHUProductStorages(List<I_M_HU> hus);

	default Stream<IHUProductStorage> streamHUProductStorages(@NonNull final I_M_HU hu)
	{
		return streamHUProductStorages(ImmutableList.of(hu));
	}

	boolean isSingleProductWithQtyEqualsTo(I_M_HU hu, ProductId productId, Quantity qty);

	boolean isSingleProductStorageMatching(@NonNull I_M_HU hu, @NonNull ProductId productId);

	@NonNull
	IHUProductStorage getSingleHUProductStorage(@NonNull I_M_HU hu);
}
