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

import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.adempiere.util.CacheTrx;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;

public class HUAttributeStorageFactory extends AbstractModelAttributeStorageFactory<I_M_HU, HUAttributeStorage>
{
	private IHUContext huContext;

	@Override
	public boolean isHandled(final Object model)
	{
		if (model == null)
		{
			return false;
		}

		return InterfaceWrapperHelper.isInstanceOf(model, I_M_HU.class);
	}

	@Override
	protected I_M_HU getModelFromObject(final Object modelObj)
	{
		final I_M_HU hu = InterfaceWrapperHelper.create(modelObj, I_M_HU.class);
		return hu;
	}

	@Override
	protected final ArrayKey mkKey(final I_M_HU model)
	{
		return Util.mkKey(model.getClass().getName(), model.getM_HU_ID());
	}

	@Override
	protected HUAttributeStorage createAttributeStorage(final I_M_HU model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		final int huId = model.getM_HU_ID();
		return createAttributeStorageCached(ctx, huId, trxName, model);
	}

	// @Cached // commented out because it's not applied anyways
	/* package */HUAttributeStorage createAttributeStorageCached(
			@CacheCtx final Properties ctx,
			final int huId,
			@CacheTrx final String trxName,
			@CacheIgnore final I_M_HU hu)
	{
		final HUAttributeStorage storage = new HUAttributeStorage(this, hu);
		return storage;
	}

	public IHUContext getHUContext()
	{
		return huContext;
	}
}
