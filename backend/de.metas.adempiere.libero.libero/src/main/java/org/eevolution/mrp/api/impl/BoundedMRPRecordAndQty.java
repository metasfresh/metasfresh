package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.ConcurrentModificationException;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_MRP;

import de.metas.material.planning.pporder.LiberoException;

/**
 * Wraps an {@link IMutableMRPRecordAndQty} and makes sure a minimum qty is reserved and not allowed to be subtracted.
 * 
 * For that purpose the {@link #getQty()} and {@link #setQty(BigDecimal)} will deal with a quantity which is offset.
 * 
 * NOTE:
 * <ul>
 * <li>changing the wrapped record's Qty while using this wrapper will throw {@link ConcurrentModificationException} on any method call. This is because we want to prevent the case when developer is
 * changing the values in both objects.
 * </ul>
 * 
 * Example 1.
 * <ul>
 * <li>we are wrapping an MRP record which has Qty=230, using qtyToSubtractMax=100
 * <li>{@link #getQty()} will return 100
 * <li>calling wrapper's {@link #setQty(BigDecimal)} with ZERO will set underlying qty to 130 (230 - 100)
 * </ul>
 * 
 * Example 2.
 * <ul>
 * <li>we are wrapping an MRP record which has Qty=230, using qtyToSubtractMax=300
 * <li>{@link #getQty()} will return 230
 * <li>calling wrapper's {@link #setQty(BigDecimal)} with ZERO will set underlying qty to 0 (230 - 230)
 * </ul>
 * 
 * @author tsa
 *
 */
public class BoundedMRPRecordAndQty implements IMutableMRPRecordAndQty
{
	private BigDecimal _qty;
	private final BigDecimal qtyReserved;
	private BigDecimal _delegateQty;
	private final IMutableMRPRecordAndQty delegate;

	public BoundedMRPRecordAndQty(final IMutableMRPRecordAndQty delegate, final BigDecimal qtyToSubtractMax)
	{
		super();
		Check.assumeNotNull(delegate, "delegate not null");
		this.delegate = delegate;
		this._delegateQty = delegate.getQty();

		//
		// Calculate how much quantity we shall reverve (and not allow to go beyond) on delegate
		Check.assume(qtyToSubtractMax != null && qtyToSubtractMax.signum() >= 0, "qtyToSubtractMax >= 0: {}", qtyToSubtractMax);
		BigDecimal qtyReserved = _delegateQty.subtract(qtyToSubtractMax);
		if (qtyReserved.signum() < 0)
		{
			qtyReserved = BigDecimal.ZERO;
		}
		this.qtyReserved = qtyReserved;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** Make sure the quantity from underlying delegate has not changed */
	private final void assertDelegateNotChanged()
	{
		final BigDecimal delegateQtyNow = delegate.getQty();
		if (_delegateQty.compareTo(delegateQtyNow) != 0)
		{
			throw new ConcurrentModificationException("Underlying MRP record was changed."
					+ "\nUnderlying MRP: " + delegate
					+ "\nQty: " + delegateQtyNow
					+ "\nQty(expected): " + _delegateQty);
		}
	}

	@Override
	public I_PP_MRP getPP_MRP()
	{
		return delegate.getPP_MRP();
	}

	@Override
	public BigDecimal getQtyRequired()
	{
		return delegate.getQtyRequired();
	}

	@Override
	public BigDecimal getQtyInitial()
	{
		return delegate.getQtyInitial();
	}

	@Override
	public BigDecimal getQty()
	{
		assertDelegateNotChanged();
		if (_qty == null)
		{
			_qty = _delegateQty.subtract(qtyReserved);
		}
		return _qty;
	}

	@Override
	public void setQty(final BigDecimal qty)
	{
		Check.assumeNotNull(qty, LiberoException.class, "qty not null");
		Check.assume(qty.signum() >= 0, LiberoException.class, "qty >= 0 but it was {}", qty);
		assertDelegateNotChanged();

		final BigDecimal delegateQtyNew = qty.add(qtyReserved);

		delegate.setQty(delegateQtyNew);
		_delegateQty = delegateQtyNew;
		_qty = qty;
	}

	@Override
	public boolean isZeroQty()
	{
		return getQty().signum() == 0;
	}

	@Override
	public void subtractQty(final BigDecimal qtyToRemove)
	{
		Check.assume(qtyToRemove.signum() >= 0, "qtyToRemove >= 0: {}", qtyToRemove);
		final BigDecimal qty = getQty();
		final BigDecimal qtyNew = qty.subtract(qtyToRemove);
		setQty(qtyNew);
	}

	@Override
	public void addQty(final BigDecimal qtyToAdd)
	{
		Check.assume(qtyToAdd.signum() >= 0, "qtyToAdd >= 0: {}", qtyToAdd);
		final BigDecimal qty = getQty();
		final BigDecimal qtyNew = qty.add(qtyToAdd);
		setQty(qtyNew);
	}
}
