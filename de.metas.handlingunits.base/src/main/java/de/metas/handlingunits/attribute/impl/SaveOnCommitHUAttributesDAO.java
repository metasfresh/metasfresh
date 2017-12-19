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
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_M_Attribute;

import com.google.common.base.Supplier;

import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;

public class SaveOnCommitHUAttributesDAO implements IHUAttributesDAO
{
	private static final String TRX_PROPERTY_SaveDecoupledHUAttributesDAO = SaveDecoupledHUAttributesDAO.class.getName();
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final HUAttributesDAO dbHUAttributesDAO;

	public SaveOnCommitHUAttributesDAO()
	{
		dbHUAttributesDAO = HUAttributesDAO.instance;
	}

	private final SaveDecoupledHUAttributesDAO getDelegate(final Object contextProvider)
	{
		final String trxName = trxManager.getThreadInheritedTrxName();
		final ITrx trx = trxManager.getTrx(trxName);
		Check.assumeNotNull(trx, "trx not null for trxName={}", trxName);

		//
		// Get an existing storage DAO from transaction.
		// If no storage DAO exists => create a new one
		return trx.getProperty(TRX_PROPERTY_SaveDecoupledHUAttributesDAO, new Supplier<SaveDecoupledHUAttributesDAO>()
		{
			@Override
			public SaveDecoupledHUAttributesDAO get()
			{
				// Create a new attributes storage
				final SaveDecoupledHUAttributesDAO huAttributesDAO = new SaveDecoupledHUAttributesDAO(dbHUAttributesDAO);

				// Listen this transaction for COMMIT events
				// Before committing the transaction, this listener makes sure we are also saving all storages
				trx.getTrxListenerManager()
						.newEventListener(TrxEventTiming.BEFORE_COMMIT)
						.registerHandlingMethod(innerTrx -> {

							// Get and remove the save-decoupled HU Storage DAO
							final SaveDecoupledHUAttributesDAO innerHuAttributesDAO = innerTrx.setProperty(TRX_PROPERTY_SaveDecoupledHUAttributesDAO, null);
							if (innerHuAttributesDAO == null)
							{
								// shall not happen, because this handlerMetghod is invoked only once, 
								// but silently ignore it
								return;
							}

							// Save everything to database
							innerHuAttributesDAO.flush();
						});

				return huAttributesDAO;
			}
		});
	}

	@Override
	public I_M_HU_Attribute newHUAttribute(final Object contextProvider)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate(contextProvider);
		return delegate.newHUAttribute(contextProvider);
	}

	@Override
	public void save(final I_M_HU_Attribute huAttribute)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate(huAttribute);
		delegate.save(huAttribute);
	}

	@Override
	public void delete(final I_M_HU_Attribute huAttribute)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate(huAttribute);
		delegate.delete(huAttribute);
	}

	@Override
	public void initHUAttributes(final I_M_HU hu)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate(hu);
		delegate.initHUAttributes(hu);
	}

	@Override
	public List<I_M_HU_Attribute> retrieveAttributesOrdered(final I_M_HU hu)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate(hu);
		return delegate.retrieveAttributesOrdered(hu);
	}

	@Override
	public I_M_HU_Attribute retrieveAttribute(final I_M_HU hu, final I_M_Attribute attribute)
	{
		final SaveDecoupledHUAttributesDAO delegate = getDelegate(hu);
		return delegate.retrieveAttribute(hu, attribute);
	}

	/**
	 * @return {@link NullAutoCloseable} always
	 */
	@Override
	public IAutoCloseable temporaryDisableAutoflush()
	{
		// NOTE: disabling "autoflush" is not supported because in order to decide with which delegate we need to work,
		// we need the HU or at least which is the transaction.
		return NullAutoCloseable.instance;
	}

	@Override
	public void flushAndClearCache()
	{
		// NOTE: clearing the underlying cache is not supported because in order to decide with which delegate we need to work,
		// we need the HU or at least which is the transaction.
	}

}
