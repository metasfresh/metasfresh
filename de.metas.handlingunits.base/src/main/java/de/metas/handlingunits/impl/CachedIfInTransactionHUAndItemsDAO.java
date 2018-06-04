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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import lombok.NonNull;

/**
 * This implementation delegated to either {@link CachedHUAndItemsDAO} or directly to {@link HUAndItemsDAO}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CachedIfInTransactionHUAndItemsDAO implements IHUAndItemsDAO
{
	private static final String TRX_PROPERTY_CachedHUAndItemsDAO = CachedHUAndItemsDAO.class.getName();
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	private final HUAndItemsDAO dbHUAndItemsDAO = HUAndItemsDAO.instance;

	private final IHUAndItemsDAO getDelegate(final Object contextProvider)
	{
		final ITrx trx = extractAndValidateTrx(contextProvider);
		if (trx == null)
		{
			return dbHUAndItemsDAO;
		}

		//
		// Get an existing DAO from transaction
		CachedHUAndItemsDAO huAndItemsDAO = trx.getProperty(TRX_PROPERTY_CachedHUAndItemsDAO);
		if (huAndItemsDAO != null)
		{
			return huAndItemsDAO;
		}

		//
		// No existing DAO was found in transaction
		// => create a new one and register it as transaction property
		huAndItemsDAO = new CachedHUAndItemsDAO();
		trx.setProperty(TRX_PROPERTY_CachedHUAndItemsDAO, huAndItemsDAO);

		return huAndItemsDAO;
	}

	private final ITrx extractAndValidateTrx(final Object contextProvider)
	{
		final String trxName = trxManager.getThreadInheritedTrxName();

		final String contextTrxName = InterfaceWrapperHelper.getTrxName(contextProvider);
		if (trxManager.isNull(contextTrxName))
		{
			return null;
		}
		if (trxManager.isNull(trxName))
		{
			return null;
		}

		if (!trxManager.isSameTrxName(trxName, contextTrxName))
		{
			throw new HUException("Context transaction and thread inherited transactions don't match"
					+ "\n Thread Inherited TrxName: " + trxName
					+ "\n Context TrxName: " + contextTrxName
					+ "\n Context: " + contextProvider);
		}

		final ITrx trx = trxManager.getTrx(trxName);
		Check.assumeNotNull(trx, "trx not null for trxName={}", trxName);

		return trx;
	}

	@Override
	public void saveHU(final I_M_HU hu)
	{
		getDelegate(hu).saveHU(hu);
	}

	@Override
	public void delete(final I_M_HU hu)
	{
		getDelegate(hu).delete(hu);
	}

	@Override
	public I_M_HU retrieveParent(final I_M_HU hu)
	{
		return getDelegate(hu).retrieveParent(hu);
	}

	@Override
	public int retrieveParentId(final I_M_HU hu)
	{
		return getDelegate(hu).retrieveParentId(hu);
	}

	@Override
	public I_M_HU_Item retrieveParentItem(final I_M_HU hu)
	{
		return getDelegate(hu).retrieveParentItem(hu);
	}

	@Override
	public void setParentItem(final I_M_HU hu, final I_M_HU_Item parentItem)
	{
		// TODO: shall we check if HU and parentItem have the same trxName
		getDelegate(hu).setParentItem(hu, parentItem);
	}

	@Override
	public List<I_M_HU> retrieveIncludedHUs(final I_M_HU_Item item)
	{
		return getDelegate(item).retrieveIncludedHUs(item);
	}

	@Override
	public List<I_M_HU_Item> retrieveItems(final I_M_HU hu)
	{
		return getDelegate(hu).retrieveItems(hu);
	}

	@Override
	public I_M_HU_Item retrieveItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		return getDelegate(hu).retrieveItem(hu, piItem);
	}

	@Override
	public I_M_HU_Item createHUItem(final I_M_HU hu, final I_M_HU_PI_Item piItem)
	{
		return getDelegate(hu).createHUItem(hu, piItem);
	}

	@Override
	public I_M_HU_Item createAggregateHUItem(@NonNull final I_M_HU hu)
	{
		return getDelegate(hu).createAggregateHUItem(hu);
	}

	@Override
	public I_M_HU_Item createChildHUItem(@NonNull final I_M_HU hu)
	{
		return getDelegate(hu).createChildHUItem(hu);
	}

	@Override
	public I_M_HU_Item retrieveAggregatedItemOrNull(I_M_HU hu)
	{
		return getDelegate(hu).retrieveAggregatedItemOrNull(hu);
	}
}
