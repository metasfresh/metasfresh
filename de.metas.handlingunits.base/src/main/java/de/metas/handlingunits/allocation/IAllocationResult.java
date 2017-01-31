package de.metas.handlingunits.allocation;

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


import java.math.BigDecimal;
import java.util.List;

import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionAttribute;

/**
 * Result of an {@link IAllocationRequest}
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAllocationResult
{
	/**
	 *
	 * @return true if the request was fully satisfied (i.e. {@link #getQtyToAllocate()} equals with {@link #getQtyAllocated()})
	 */
	boolean isCompleted();

	/**
	 *
	 * @return quantity that was initially requested to allocate/deallocate
	 */
	BigDecimal getQtyToAllocate();

	/**
	 *
	 * @return quantity which was actually allocated/deallocate
	 */
	BigDecimal getQtyAllocated();

	/**
	 *
	 * @return true if there was nothing allocated in this result
	 */
	boolean isZeroAllocated();

	/**
	 * Gets the {@link IHUTransaction}s that were created in order to allocate/deallocate requested qty
	 *
	 * @return {@link IHUTransaction}s; never return null
	 */
	List<IHUTransaction> getTransactions();

	/**
	 *
	 * @return attribute transactions; never return null
	 */
	List<IHUTransactionAttribute> getAttributeTransactions();
}
