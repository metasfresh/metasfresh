package de.metas.handlingunits.allocation.impl;

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
import java.util.Collections;
import java.util.List;

import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.hutransaction.IHUTransactionAttribute;

/**
 * Null {@link IAllocationResult} implementation.
 *
 * @author tsa
 *
 */
public final class NullAllocationResult implements IAllocationResult
{
	public static final NullAllocationResult instance = new NullAllocationResult();

	private NullAllocationResult()
	{
		super();
	}

	@Override
	public BigDecimal getQtyToAllocate()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public boolean isZeroAllocated()
	{
		return true;
	}

	/**
	 * @return true always
	 */
	@Override
	public boolean isCompleted()
	{
		return true;
	}

	@Override
	public List<IHUTransactionCandidate> getTransactions()
	{
		return Collections.emptyList();
	}

	@Override
	public List<IHUTransactionAttribute> getAttributeTransactions()
	{
		return Collections.emptyList();
	}
}
