package de.metas.handlingunits.attribute;

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


import java.util.List;

import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;

/**
 * Used to collect and process {@link IHUTransactionAttribute}s while processing HUs.
 * 
 * Implementations of this interface are responsible for registering the needed listeners in order to collect the {@link IHUTransactionAttribute}s.
 * 
 * When using any implementation of this interface, please:
 * <ul>
 * <li>minimize the scope of it's usage; keep it as local as possible
 * <li>don't create hard refereces to it or at least make sure to clear them when you are done
 * <li>don't forget to call {@link #dispose()} which will remove all registered listners
 * </ul>
 * 
 * @author tsa
 *
 */
public interface IHUTransactionAttributeBuilder
{
	IAttributeStorageFactory getAttributeStorageFactory();

	/**
	 * Dispose this builder:
	 * <ul>
	 * <li>asserts there are no pending transactions
	 * <li>unregisters all listeners
	 * <li>flags it as disposed, so no more collecting is allowed.
	 * </ul>
	 */
	void dispose();

	/**
	 * Gets currently logged {@link IHUTransactionAttribute}s and then clears them.
	 *
	 * As an effect, subsequent calls to this method will return an empty list if nothing new was logged in meantime.
	 *
	 * @return currently logged transactions
	 */
	List<IHUTransactionAttribute> getAndClearTransactions();

	/**
	 * Clears all collected transactions if any.
	 */
	void clearTransactions();

	/**
	 * Checks if there are any collected transactions, which were not cleared/processed by methods like {@link #getAndClearTransactions()}.
	 * 
	 * @return true if contains pending attribute transactions which were not cleared/processed yet.
	 */
	boolean hasTransactions();

	/**
	 * Transfer matching attributes for given {@link IHUAttributeTransferRequest}.
	 *
	 * @param request
	 * @param fromAttributes
	 * @param toAttributes
	 */
	void transferAttributes(IHUAttributeTransferRequest request);

	/**
	 * Creates an {@link IAllocationResult} for current transactions.
	 *
	 * Because this method is calling {@link #getAndClearTransactions()}, after this method is invoked, all current transactions will be cleared.
	 *
	 * @return allocation result
	 */
	IAllocationResult createAllocationResult();

	/**
	 * Creates an allocation result (see {@link #createAllocationResult()}) and process it
	 *
	 * @return allocation result that was created and processed
	 */
	IAllocationResult createAndProcessAllocationResult();
}
