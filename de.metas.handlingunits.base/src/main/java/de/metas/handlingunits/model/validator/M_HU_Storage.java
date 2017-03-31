package de.metas.handlingunits.model.validator;

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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.storage.spi.hu.impl.StorageSegmentFromHUStorage;

@Validator(I_M_HU_Storage.class)
public class M_HU_Storage
{
	public static M_HU_Storage INSTANCE = new M_HU_Storage();

	private M_HU_Storage()
	{
	};

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, ifColumnsChanged = {
			I_M_HU_Storage.COLUMNNAME_IsActive,
			I_M_HU_Storage.COLUMNNAME_M_Product_ID,
			I_M_HU_Storage.COLUMNNAME_C_UOM_ID,
			I_M_HU_Storage.COLUMNNAME_Qty
	})
	public void fireStorageSegmentChanged(final I_M_HU_Storage huStorage)
	{
		// TODO: notify segment changed only after transaction is commited.
		// And in this case, it would be nice if we could aggregate the notifications and fire them all together at once.
		final IStorageListeners storageListeners = Services.get(IStorageListeners.class);

		final IStorageSegment storageSegment = new StorageSegmentFromHUStorage(huStorage);
		storageListeners.notifyStorageSegmentChanged(storageSegment);
	}
}
