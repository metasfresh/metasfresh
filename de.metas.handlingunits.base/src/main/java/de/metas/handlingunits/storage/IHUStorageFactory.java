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

import java.util.List;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

/**
 * Factory for HU related quantities.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUStorageFactory
{
	// void addHUStorageListener(IHUStorageListener listener);

	IHUStorage getStorage(I_M_HU hu);

	IHUItemStorage getStorage(I_M_HU_Item item);

	IHUStorageDAO getHUStorageDAO();

	/**
	 * Iterates all <code>hus</code> and collects the {@link IHUProductStorage} storages from them.
	 *
	 * NOTE: this method will collect the product storages directly from given HUs. It will not navigate them to collect the product storages from possible included HUs.
	 *
	 * @param hus
	 * @param product
	 * @return product storages
	 */
	List<IHUProductStorage> getHUProductStorages(List<I_M_HU> hus, I_M_Product product);
}
