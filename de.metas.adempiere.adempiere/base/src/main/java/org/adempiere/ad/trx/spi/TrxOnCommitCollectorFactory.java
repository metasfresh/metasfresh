package org.adempiere.ad.trx.spi;

import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;

import com.google.common.base.Supplier;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Template factory class for algorithms which collect items on transaction level and process the collected items on transaction commit.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <CollectorType> collector type. An instance of this class is stored in transaction properties and it will be processed when the transaction is committed.
 * @param <ItemType> item type. Items are collected in CollectorType instances.
 */
public abstract class TrxOnCommitCollectorFactory<CollectorType, ItemType>
{
	// services:
	// NOTE: this is supposed to be a long living class, so having services here is not a good idea

	/**
	 * Collects given item.
	 * <ul>
	 * <li>if <code>item</code> has no transaction, a new collector will created with given item and it will be processed directly
	 * <li>if <code>item</code> has a transaction, the item will be added to a collector instance which is stored in transaction properties. Later on, when the transaction will be committed, that
	 * collector will be processed.
	 * </ul>
	 *
	 * @param item item to be collected
	 */
	public final void collect(final ItemType item)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = extractTrxNameFromItem(item);
		final ITrx trx = trxManager.getTrx(trxName);
		if (trxManager.isNull(trx))
		{
			final CollectorType collector = newCollector(item);
			collectItem(collector, item);
			processCollector(collector);
		}
		else
		{
			//
			// Get/create the transaction level collector
			final AtomicBoolean itemWasCollected = new AtomicBoolean(false);
			final String trxProperyName = getTrxProperyName();
			final CollectorType collector = trx.getProperty(trxProperyName, new Supplier<CollectorType>()
			{
				/** @return new collector */
				@Override
				public CollectorType get()
				{
					final CollectorType collector = newCollector(item);

					// Collect item now.
					// We do it right now to avoid leaking the item in case the transaction is committed after we register the transaction listener but before the item is added in the calling method.
					collectItem(collector, item);
					itemWasCollected.set(true);

					// Register a listener which will process the collector when the transaction is committed.
					trx.getTrxListenerManager().registerListener(new TrxListenerAdapter()
					{
						@Override
						public void afterCommit(final ITrx trx)
						{
							// Get the transaction level collector.
							// The collector is removed to avoid double processing.
							// If there is no collector, do nothing.
							final CollectorType collector = trx.setProperty(trxProperyName, null);
							if (collector == null)
							{
								return;
							}

							// Process the collector.
							processCollector(collector);
						}

						@Override
						public void afterRollback(final ITrx trx)
						{
							// Get the transaction level collector.
							// The collector is removed to avoid double processing.
							// If there is no collector, do nothing.
							final CollectorType collector = trx.setProperty(trxProperyName, null);
							if (collector == null)
							{
								return;
							}

							// Process the collector.
							discardCollector(collector);
						}
					});

					// Return the newly created collector.
					return collector;
				}
			});

			// Collect item if it was not collected yet.
			// In case the collector was just created, the item was already collected.
			if (!itemWasCollected.getAndSet(true))
			{
				collectItem(collector, item);
			}
		}
	}

	/** @return the name of the property to be used for storing the collector in {@link ITrx} properties. */
	protected abstract String getTrxProperyName();

	/**
	 * Extracts and returns the trxName to be used from given item
	 *
	 * @param item
	 * @return trxName
	 */
	protected abstract String extractTrxNameFromItem(final ItemType item);

	/**
	 * Creates a new collector.
	 *
	 * It is assumed that the item IS NOT automatically added to collector. The algorithm will call {@link #collectItem(Object, Object)} after this call.
	 *
	 * @param firstItem the item which triggered this new collector request.
	 * @return new collector instance
	 */
	protected abstract CollectorType newCollector(final ItemType firstItem);

	/**
	 * Adds the given item to collector.
	 *
	 * @param collector
	 * @param item
	 */
	protected abstract void collectItem(final CollectorType collector, final ItemType item);

	/**
	 * Process the collector.
	 *
	 * This method is called on transaction commit, if there was a transaction. If the processing was executed out of transaction, the method will be called right after the collector was created and
	 * the item was added to it.
	 *
	 * @param collector
	 */
	protected abstract void processCollector(final CollectorType collector);

	/**
	 * Discard the collector.
	 *
	 * This method is called on transaction rollback.
	 * @param collector
	 */
	protected void discardCollector(final CollectorType collector)
	{
		// nothing at this level
	}

}
