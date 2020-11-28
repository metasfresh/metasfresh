package de.metas.ui.web.pickingV2.productsToPick.rows.factory;

import org.adempiere.exceptions.AdempiereException;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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


@ToString class ReservableStorage
{
	private final ProductId productId;
	private Quantity qtyFreeToReserve;

	ReservableStorage(
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyFreeToReserve)
	{
		this.productId = productId;
		this.qtyFreeToReserve = qtyFreeToReserve.toZeroIfNegative();
	}

	public Quantity reserve(@NonNull final AllocablePackageable allocable)
	{
		final Quantity qtyToReserve = computeEffectiveQtyToReserve(allocable.getQtyToAllocate());
		return reserve(allocable, qtyToReserve);
	}

	public Quantity reserve(@NonNull final AllocablePackageable allocable, @NonNull final Quantity qtyToReserve)
	{
		assertSameProductId(allocable);

		final Quantity qtyReserved = reserveQty(qtyToReserve);
		allocable.allocateQty(qtyReserved);
		return qtyReserved;
	}

	private void assertSameProductId(final AllocablePackageable allocable)
	{
		if (!ProductId.equals(productId, allocable.getProductId()))
		{
			throw new AdempiereException("ProductId not matching")
					.appendParametersToMessage()
					.setParameter("allocable", allocable)
					.setParameter("storage", this);
		}
	}

	private Quantity computeEffectiveQtyToReserve(@NonNull final Quantity qtyToReserve)
	{
		if (qtyToReserve.signum() <= 0)
		{
			return qtyToReserve.toZero();
		}
		if (qtyFreeToReserve.signum() <= 0)
		{
			return qtyToReserve.toZero();
		}

		return qtyToReserve.min(qtyFreeToReserve);
	}

	private Quantity reserveQty(@NonNull final Quantity qtyToReserve)
	{
		if (qtyToReserve.signum() <= 0)
		{
			return qtyToReserve.toZero();
		}
		if (qtyFreeToReserve.signum() <= 0)
		{
			return qtyToReserve.toZero();
		}

		final Quantity qtyToReserveEffective = qtyToReserve.min(qtyFreeToReserve);
		qtyFreeToReserve = qtyFreeToReserve.subtract(qtyToReserveEffective);

		return qtyToReserveEffective;
	}
}