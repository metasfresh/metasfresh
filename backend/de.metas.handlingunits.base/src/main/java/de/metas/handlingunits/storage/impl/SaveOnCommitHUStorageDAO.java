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

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_UOM;

import com.google.common.base.Supplier;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.product.ProductId;
import de.metas.uom.UOMType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class SaveOnCommitHUStorageDAO implements IHUStorageDAO
{
	private static final String TRX_PROPERTY_SaveDecoupledHUStorageDAO = SaveDecoupledHUStorageDAO.class.getName();
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final HUStorageDAO dbStorageDAO;

	public SaveOnCommitHUStorageDAO()
	{
		dbStorageDAO = new HUStorageDAO();
	}

	/**
	 * For the current (inherited) trx, this method gets the {@link SaveDecoupledHUStorageDAO} that is added to the trx using {@link #TRX_PROPERTY_SaveDecoupledHUStorageDAO}.
	 * If there isn't one added yet, it add one and also registers a {@link ITrxListener} that will invoke {@link SaveDecoupledHUStorageDAO#flush()} when the current trx is committed.
	 *
	 * @param contextProvider
	 * @return
	 */
	private final SaveDecoupledHUStorageDAO getDelegate(final Object contextProvider)
	{
		final String trxName = trxManager.getThreadInheritedTrxName();
		final ITrx trx = trxManager.getTrx(trxName);
		Check.assumeNotNull(trx, "Transaction shall exist: {}", trxName);

		return trx.getProperty(TRX_PROPERTY_SaveDecoupledHUStorageDAO, (Supplier<SaveDecoupledHUStorageDAO>)() -> {
			final SaveDecoupledHUStorageDAO huStorageDAO = new SaveDecoupledHUStorageDAO(dbStorageDAO);

			// Listen this transaction for COMMIT events
			// Before committing the transaction, this listener makes sure we are also saving all storages
			trx.getTrxListenerManager()
					.newEventListener(TrxEventTiming.BEFORE_COMMIT)
					.invokeMethodJustOnce(false) // we need this to happen on every last single commit
					.registerHandlingMethod(innerTrx -> {
						// Get and remove the save-decoupled HU Storage DAO
						final SaveDecoupledHUStorageDAO innerHuStorageDAO = innerTrx.setProperty(TRX_PROPERTY_SaveDecoupledHUStorageDAO, null);
						if (innerHuStorageDAO == null)
						{
							return;
						}

						// Save everything to database
						innerHuStorageDAO.flush();
					});

			return huStorageDAO;
		});
	}

	@Override
	public <T> T newInstance(final Class<T> modelClass, final Object contextProvider)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(contextProvider);
		return delegate.newInstance(modelClass, contextProvider);
	}

	@Override
	public void initHUStorages(final I_M_HU hu)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(hu);
		delegate.initHUStorages(hu);
	}

	@Override
	public void initHUItemStorages(final I_M_HU_Item item)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(item);
		delegate.initHUItemStorages(item);
	}

	@Override
	public I_M_HU_Storage retrieveStorage(final I_M_HU hu, final ProductId productId)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(hu);
		return delegate.retrieveStorage(hu, productId);
	}

	@Override
	public void save(final I_M_HU_Storage storage)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(storage);
		delegate.save(storage);
	}

	@Override
	public List<I_M_HU_Storage> retrieveStorages(final I_M_HU hu)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(hu);
		return delegate.retrieveStorages(hu);
	}

	@Override
	public void save(final I_M_HU_Item_Storage storageLine)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(storageLine);
		delegate.save(storageLine);
	}

	@Override
	public List<I_M_HU_Item_Storage> retrieveItemStorages(final I_M_HU_Item item)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(item);
		return delegate.retrieveItemStorages(item);
	}

	@Override
	public I_M_HU_Item_Storage retrieveItemStorage(final I_M_HU_Item item, @NonNull final ProductId productId)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(item);
		return delegate.retrieveItemStorage(item, productId);
	}

	@Override
	public void save(final I_M_HU_Item item)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(item);
		delegate.save(item);
	}

	@Override
	public I_C_UOM getC_UOMOrNull(final I_M_HU hu)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(hu);
		return delegate.getC_UOMOrNull(hu);
	}

	@Override
	public UOMType getC_UOMTypeOrNull(final I_M_HU hu)
	{
		final SaveDecoupledHUStorageDAO delegate = getDelegate(hu);
		return delegate.getC_UOMTypeOrNull(hu);
	}

}
