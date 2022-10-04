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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/* package */final class AllocationRequest implements IAllocationRequest
{
	@Getter
	private final IHUContext huContext;
	@Getter
	private final ProductId productId;
	@Getter
	private final Quantity quantity;
	@Getter
	private final ZonedDateTime date;
	@Getter
	private final boolean forceQtyAllocation;
	@Getter
	@Nullable
	private final ClearanceStatusInfo clearanceStatusInfo;

	// Reference
	private final TableRecordReference fromTableRecord;

	public AllocationRequest(
			@NonNull final IHUContext huContext,
			@NonNull final ProductId productId,
			@NonNull final Quantity quantity,
			@NonNull final ZonedDateTime date,
			final TableRecordReference fromTableRecord,
			final boolean forceQtyAllocation,
			@Nullable final ClearanceStatusInfo clearanceStatusInfo)
	{
		Check.assumeNotNull(quantity.signum() >= 0, "qty >= 0 ({})", quantity);

		this.huContext = huContext;
		this.productId = productId;
		this.quantity = quantity;
		this.date = date;

		// Check.assumeNotNull(fromTableRecord, "fromTableRecord not null");
		this.fromTableRecord = fromTableRecord;

		this.forceQtyAllocation = forceQtyAllocation;
		this.clearanceStatusInfo = clearanceStatusInfo;
	}

	@Override
	public String toString()
	{
		final String fromTableRecordStr = fromTableRecord == null ? null : "" + fromTableRecord.getTableName() + "/" + fromTableRecord.getRecord_ID();
		return "AllocationRequest ["
				+ "product=" + productId
				+ ", qty=" + (isInfiniteQty() ? "inifinite" : quantity)
				+ ", date=" + date
				+ ", fromTableRecord=" + fromTableRecordStr
				+ ", forceQtyAllocation=" + forceQtyAllocation
				+ ", clearanceStatusInfo=" + clearanceStatusInfo
				+ "]";
	}

	@Override
	public BigDecimal getQty()
	{
		return quantity.toBigDecimal();
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
	public TableRecordReference getReference()
	{
		return fromTableRecord;
	}

}
