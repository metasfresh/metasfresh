package de.metas.handlingunits.impl;

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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.impl.SaveOnCommitHUAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.impl.DefaultHUStorageFactory;
import de.metas.handlingunits.storage.impl.SaveOnCommitHUStorageDAO;

public class HUContextFactory implements IHUContextFactory
{
	@Override
	public IMutableHUContext createMutableHUContextForProcessing(final IContextAware contextProvider)
	{
		final MutableHUContext huContext = new MutableHUContext(contextProvider);

		configureProcessingContext(huContext);

		return huContext;
	}

	@Override
	public IMutableHUContext createMutableHUContextForProcessing(final Properties ctx)
	{
		final IContextAware contextProvider = new PlainContextAware(ctx, ITrx.TRXNAME_None);
		return createMutableHUContextForProcessing(contextProvider);
	}

	private void configureProcessingContext(final IMutableHUContext huContext)
	{
		if (!HUConstants.isUseCacheWhenCreatingHUs())
		{
			return;
		}

		final Boolean configured = huContext.getProperty(IHUContext.PROPERTY_Configured);
		if (configured != null && configured)
		{
			return;
		}

		final IHUStorageDAO storageDAO = new SaveOnCommitHUStorageDAO();
		final DefaultHUStorageFactory huStorageFactory = new DefaultHUStorageFactory(storageDAO);
		huContext.setHUStorageFactory(huStorageFactory);

		final SaveOnCommitHUAttributesDAO huAttributesDAO = new SaveOnCommitHUAttributesDAO();
		final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
		final IAttributeStorageFactory attributesStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory(huAttributesDAO);
		huContext.setHUAttributeStorageFactory(attributesStorageFactory);
	}

	@Override
	public IMutableHUContext createMutableHUContext(final Properties ctx)
	{
		return new MutableHUContext(ctx, ITrx.TRXNAME_None);
	}

	@Override
	public IMutableHUContext createMutableHUContext(final Properties ctx, final String trxName)
	{
		final PlainContextAware contextProvider = new PlainContextAware(ctx, trxName);
		return new MutableHUContext(contextProvider);
	}

	@Override
	public IMutableHUContext createMutableHUContext(final IContextAware contextProvider)
	{
		return new MutableHUContext(contextProvider);
	}

	@Override
	public final IHUContext deriveWithTrxName(final IHUContext huContext, final String trxNameNew)
	{
		// FIXME: handle the case of SaveOnCommit Storage and Attributes when changing transaction name

		//
		// Check: if transaction name was not changed, do nothing, return the old context
		final String contextTrxName = huContext.getTrxName();
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (trxManager.isSameTrxName(contextTrxName, trxNameNew))
		{
			return huContext;
		}

		final IMutableHUContext huContextNew = huContext.copyAsMutable();
		huContextNew.setTrxName(trxNameNew);

		configureProcessingContext(huContextNew);

		return huContextNew;
	}

	@Override
	public String toString()
	{
		return "HUContextFactory []";
	}
}
