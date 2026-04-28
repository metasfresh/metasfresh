package de.metas.handlingunits.reservation;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class ReserveHUsRequest
{

	/**
	 * The quantity to reserve.
	 */
	@NonNull Quantity qtyToReserve;

	@NonNull HUReservationDocRef documentRef;

	@NonNull ProductId productId;

	@Nullable BPartnerId customerId;

	/**
	 * The HUs from which the respective {@link #qtyToReserve} shall be reserved. can be higher-level-HUs;
	 * The actual reservation is done on VHU level.
	 */
	@NonNull ImmutableSet<HuId> huIds;

	/**
	 * If true, the expectation is that the selected HUs are reserved as-is, if possible.
	 * If false, reserve qty up to the qtyToReserve value from selected HUs.
	 */
	boolean reserveActualHUs;

	@Builder
	private ReserveHUsRequest(
			@NonNull final Quantity qtyToReserve,
			@NonNull final HUReservationDocRef documentRef,
			@NonNull final ProductId productId,
			@Nullable final BPartnerId customerId,
			@Singular @NonNull final ImmutableSet<HuId> huIds,
			final boolean reserveActualHUs)
	{
		Check.assumeNotEmpty(huIds, "huIds needs to be not empty; this={}", this);
		Check.assume(qtyToReserve.isPositive(), "qtyToReserve={} shall be >0; this={}",qtyToReserve, this);

		this.qtyToReserve = qtyToReserve;
		this.documentRef = documentRef;
		this.productId = productId;
		this.customerId = customerId;
		this.huIds = huIds;
		this.reserveActualHUs = reserveActualHUs;
	}
}
