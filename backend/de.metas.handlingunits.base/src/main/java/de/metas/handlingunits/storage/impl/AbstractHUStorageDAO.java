package de.metas.handlingunits.storage.impl;

import java.util.List;
import java.util.Objects;

import org.compiere.model.I_C_UOM;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.storage.spi.hu.IHUStorageBL;
import de.metas.uom.UOMType;
import de.metas.util.Check;

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

	@VisibleForTesting
	/* package */ final I_C_UOM getC_UOMOrNull(final List<I_M_HU_Storage> storages)
	{
		if (storages == null || storages.isEmpty())
		{
			return null;
		}

		if (storages.size() == 1)
		{
			return IHUStorageBL.extractUOM(storages.get(0));
		}

		I_C_UOM foundUOM = null;
		String foundUOMType = null;

		for (final I_M_HU_Storage storage : storages)
		{
			//
			// Retrieve storage UOM
			final I_C_UOM storageUOM = IHUStorageBL.extractUOM(storage);
			final String storageUOMType = storageUOM.getUOMType();

			if (foundUOM == null)
			{
				// This is actually the initial (and only) assignment
				foundUOM = storageUOM;
				foundUOMType = storageUOMType;
				continue;
			}
			
			if (foundUOM.getC_UOM_ID() == storageUOM.getC_UOM_ID())
			{
				// each uom is compatible with itself
				continue;
			}

			// Validated for null before with that Check
			if (Objects.equals(foundUOMType, storageUOMType))
			{
				if (Check.isEmpty(storageUOMType, true))
				{
					// if both UOMs' types are empty/null, then we have to thread them as incompatible; exit loop & return null
					return null;
				}

				// We don't care about it if it's the same UOMType
				continue;
			}

			// Incompatible UOM types encountered; exit loop & return null
			return null;
		}

		return foundUOM;

	}

	@Override
	public final UOMType getC_UOMTypeOrNull(final I_M_HU hu)
	{
		// FIXME: optimize more!

		final List<I_M_HU_Storage> storages = retrieveStorages(hu);
		if (storages.isEmpty())
		{
			//
			// TODO hardcoded (quickfix in 07088, skyped with teo - we need a compatible UOM type before storages are created when propagating WeightNet)
			return UOMType.Weight;
		}

		final I_C_UOM uom = getC_UOMOrNull(storages);
		if (uom == null)
		{
			return null;
		}
		return UOMType.ofNullableCodeOrOther(uom.getUOMType());
	}

}
