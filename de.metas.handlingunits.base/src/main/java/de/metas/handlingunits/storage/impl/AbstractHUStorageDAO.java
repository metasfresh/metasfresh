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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_UOM;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IHUStorageDAO;

/* package */abstract class AbstractHUStorageDAO implements IHUStorageDAO
{
	@Override
	public final I_C_UOM getC_UOMOrNull(final I_M_HU hu)
	{
		if (hu == null)
		{
			//
			// Null HU; consider that the UOMType is incompatible
			return null;
		}

		final List<I_M_HU_Storage> storages = retrieveStorages(hu);
		return getC_UOMOrNull(storages);
	}

	private final I_C_UOM getC_UOMOrNull(final Collection<I_M_HU_Storage> storages)
	{
		if (storages == null || storages.isEmpty())
		{
			return null;
		}

		I_C_UOM foundUOM = null;
		String foundUOMType = null;

		for (final I_M_HU_Storage storage : storages)
		{
			//
			// Retrieve storage UOM
			final I_C_UOM storageUOM = getC_UOM(storage);
			final String storageUOMType = storageUOM.getUOMType();
			if (Check.isEmpty(storageUOMType, true))
			{
				// UOM (or it's type) not specified for this storage; exit loop & return null
				return null;
			}

			if (foundUOM != null)
			{
				//
				// Validated for null before with that Check
				if (Objects.equals(foundUOMType, storageUOMType))
				{
					//
					// We don't care about it if it's the same UOMType
					continue;
				}

				//
				// Incompatible UOM types encountered; exit loop & return null
				return null;
			}

			//
			// This is actually the initial (and only) assignment
			foundUOM = storageUOM;
			foundUOMType = storageUOMType;
		}

		return foundUOM;
	}

	@Override
	public final String getC_UOMTypeOrNull(final I_M_HU hu)
	{
		// FIXME: optimize more!

		final List<I_M_HU_Storage> storages = retrieveStorages(hu);
		if (storages.isEmpty())
		{
			//
			// TODO hardcoded (quickfix in 07088, skyped with teo - we need a compatible UOM type before storages are created when propagating WeightNet)
			return X_C_UOM.UOMTYPE_Weigth;
		}

		final I_C_UOM uom = getC_UOMOrNull(storages);
		if (uom == null)
		{
			return null;
		}
		return uom.getUOMType();
	}

}
