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
import java.util.Date;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.quantity.Quantity;

/* package */final class AllocationRequest implements IAllocationRequest
{
	private final IHUContext huContext;

	private final I_M_Product product;
	private final Quantity quantity;
	private final Date date;
	private final boolean forceQtyAllocation;

	// Reference
	private final ITableRecordReference fromTableRecord;

	public AllocationRequest(
			final IHUContext huContext,
			final I_M_Product product,
			final Quantity quantity,
			final Date date,
			final ITableRecordReference fromTableRecord,
			final boolean forceQtyAllocation)
	{
		Check.assumeNotNull(huContext, "huContext not null");
		this.huContext = huContext;

		Check.assumeNotNull(product, "product not null");
		this.product = product;

		Check.assumeNotNull(quantity, "quantity not null");
		Check.assumeNotNull(quantity.getQty().signum() >= 0, "qty >= 0 ({})", quantity);
		this.quantity = quantity;

		Check.assumeNotNull(date, "date not null");
		this.date = (Date)date.clone();

		// Check.assumeNotNull(fromTableRecord, "fromTableRecord not null");
		this.fromTableRecord = fromTableRecord;

		this.forceQtyAllocation = forceQtyAllocation;
	}

	@Override
	public String toString()
	{
		final String fromTableRecordStr = fromTableRecord == null ? null : "" + fromTableRecord.getTableName() + "/" + fromTableRecord.getRecord_ID();
		return "AllocationRequest ["
				+ "product=" + product.getValue()
				+ ", qty=" + (isInfiniteQty() ? "inifinite" : quantity)
				+ ", date=" + date
				+ ", fromTableRecord=" + fromTableRecordStr
				+ ", forceQtyAllocation=" + forceQtyAllocation
				+ "]";
	}

	@Override
	public IHUContext getHUContext()
	{
		return huContext;
	}

	@Override
	public I_M_Product getProduct()
	{
		return product;
	}

	@Override
	public Quantity getQuantity()
	{
		return quantity;
	}

	@Override
	public BigDecimal getQty()
	{
		return quantity.getQty();
	}

	@Override
	public boolean isZeroQty()
	{
		return quantity.isZero();
	}

	@Override
	public boolean isInfiniteQty()
	{
		return quantity.isInfinite();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return quantity.getUOM();
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public ITableRecordReference getReference()
	{
		return fromTableRecord;
	}

	@Override
	public boolean isForceQtyAllocation()
	{
		return forceQtyAllocation;
	}
}
