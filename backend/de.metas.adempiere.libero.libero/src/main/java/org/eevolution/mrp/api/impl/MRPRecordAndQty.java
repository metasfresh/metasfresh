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
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.collections.Converter;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_MRP;

import de.metas.material.planning.pporder.LiberoException;

/**
 * Pair of {@link I_PP_MRP} and "Quantity Available".
 * 
 * @author tsa
 *
 */
/* package */class MRPRecordAndQty implements IMutableMRPRecordAndQty
{
	/** Converts {@link I_PP_MRP} to {@link IMutableMRPRecordAndQty} */
	private static final Converter<IMutableMRPRecordAndQty, I_PP_MRP> MRPRecord2MRPAndQtyConverter = new Converter<IMutableMRPRecordAndQty, I_PP_MRP>()
	{

		@Override
		public IMutableMRPRecordAndQty convert(final I_PP_MRP mrpSupply)
		{
			return new MRPRecordAndQty(mrpSupply, mrpSupply.getQty());
		}
	};

	public static List<IMutableMRPRecordAndQty> asMutableMRPRecordAndQtyList(final List<I_PP_MRP> mrps)
	{
		return ListUtils.convert(mrps, MRPRecord2MRPAndQtyConverter);
	}

	private final BigDecimal _qtyRequired;
	private final BigDecimal _qtyInitial;
	private BigDecimal _qty = BigDecimal.ZERO;
	private final I_PP_MRP _mrp;

	public MRPRecordAndQty(final I_PP_MRP mrp)
	{
		this(mrp, mrp.getQty());
	}

	public MRPRecordAndQty(final I_PP_MRP mrp, final BigDecimal qty)
	{
		super();
		Check.assumeNotNull(mrp, "mrp not null");
		this._mrp = mrp;
		this._qtyRequired = mrp.getQtyRequiered();
		this._qtyInitial = qty;
		setQty(qty);
	}

	public MRPRecordAndQty(final IMRPRecordAndQty mrp, final BigDecimal qty)
	{
		this(mrp.getPP_MRP(), qty);
	}

	public MRPRecordAndQty(final IMRPRecordAndQty mrp)
	{
		this(mrp.getPP_MRP(), mrp.getQty());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public I_PP_MRP getPP_MRP()
	{
		return _mrp;
	}

	@Override
	public BigDecimal getQtyRequired()
	{
		return _qtyRequired;
	}

	@Override
	public BigDecimal getQtyInitial()
	{
		return _qtyInitial;
	}

	/**
	 * 
	 * @return Qty; never returns <code>null</code>
	 */
	@Override
	public final BigDecimal getQty()
	{
		return _qty;
	}

	@Override
	public void setQty(BigDecimal qty)
	{
		Check.assumeNotNull(qty, LiberoException.class, "qty not null");
		Check.assume(qty.signum() >= 0, LiberoException.class, "qty >= 0 but it was {}", qty);
		this._qty = qty;
	}

	@Override
	public void subtractQty(final BigDecimal qtyToRemove)
	{
		Check.assumeNotNull(qtyToRemove, LiberoException.class, "qtyToRemove not null");
		if (qtyToRemove.signum() == 0)
		{
			return;
		}

		final BigDecimal qtyNew = getQty().subtract(qtyToRemove);
		setQty(qtyNew);
	}

	@Override
	public void addQty(final BigDecimal qtyToAdd)
	{
		Check.assumeNotNull(qtyToAdd, LiberoException.class, "qtyToAdd not null");
		if (qtyToAdd.signum() == 0)
		{
			return;
		}

		final BigDecimal qtyNew = getQty().add(qtyToAdd);
		setQty(qtyNew);
	}

	@Override
	public boolean isZeroQty()
	{
		return getQty().signum() == 0;
	}
}
