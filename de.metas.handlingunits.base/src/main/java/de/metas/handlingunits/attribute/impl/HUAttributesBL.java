package de.metas.handlingunits.attribute.impl;

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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUStorageFactory;

public class HUAttributesBL implements IHUAttributesBL
{
	@Override
	public I_M_HU getM_HU_OrNull(final IAttributeSet attributeSet)
	{
		if (attributeSet instanceof IHUAware)
		{
			final IHUAware huAware = (IHUAware)attributeSet;
			return huAware.getM_HU();
		}
		else
		{
			return null;
		}
	}

	@Override
	public I_M_HU getM_HU(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = getM_HU_OrNull(attributeSet);
		if (hu == null)
		{
			throw new IllegalArgumentException("Cannot get M_HU from " + attributeSet);
		}

		return hu;
	}

	@Override
	public void updateHUAttributeRecursive(final I_M_HU hu,
			final I_M_Attribute attribute,
			final Object attributeValue,
			final String onlyHUStatus)
	{
		final ILoggable loggable = Loggables.get();

		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);

		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IAttributeStorageFactory huAttributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory(null); // huContext==null
		huAttributeStorageFactory.setHUStorageFactory(storageFactory);

		final HUIterator iterator = new HUIterator();
		// I'm not 100% sure which time to pick, but i think for the iterator itself it makes no difference, and i also don't need it in the beforeHU implementation.
		iterator.setDate(SystemTime.asTimestamp());
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result beforeHU(final IMutable<I_M_HU> currentHUMutable)
			{
				final I_M_HU currentHU = currentHUMutable.getValue();
				if (Check.isEmpty(onlyHUStatus) || onlyHUStatus.equals(currentHU.getHUStatus()))
				{
					final IAttributeStorage attributeStorage = huAttributeStorageFactory.getAttributeStorage(currentHU);

					attributeStorage.setValueNoPropagate(attribute, attributeValue);
					attributeStorage.saveChangesIfNeeded();
					final String msg = "Updated IAttributeStorage " + attributeStorage + " of M_HU " + currentHU;
					loggable.addLog(msg);
				}

				return Result.CONTINUE;
			}
		});
		iterator.iterate(hu);
	}
}
