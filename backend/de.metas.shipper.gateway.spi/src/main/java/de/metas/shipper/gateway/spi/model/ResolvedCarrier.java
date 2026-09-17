/*
 * #%L
 * de.metas.shipper.gateway.spi
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipper.gateway.spi.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.shipping.CarrierProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

/**
 * The carrier (product + goods-type + services) resolved for a single shipment schedule, ready to be packed
 * into a {@code DeliveryOrderKey}.
 * <p>
 * This is a plain data carrier so the carrier can be RESOLVED from the shipment schedule in
 * {@code de.metas.handlingunits.base} and passed into {@code de.metas.shipper.gateway.commons} on the
 * {@link DeliveryOrderCreateRequest} — the commons module must not depend on the handlingunits module
 * (that would create a dependency cycle).
 * <p>
 * {@code manual} carries whether the schedule was manually advised ({@code CarrierAdvisingStatus.Manual}): a
 * manual carrier is a human override and must win over an automatic one when a package's schedules diverge.
 */
@Value
@Builder
public class ResolvedCarrier
{
	@Nullable CarrierProductId carrierProductId;
	@Nullable CarrierGoodsTypeId carrierGoodsTypeId;
	@NonNull @Singular ImmutableSet<CarrierServiceId> carrierServices;
	boolean manual;

	/**
	 * True if any of the given carriers was manually advised. A manual carrier is final (nShift does not re-resolve
	 * it), so this distinguishes "carrier already known" from "nShift resolves it at ship time". Central so every
	 * caller (delivery-order grouping, picking consistency check) agrees on what "manual" means.
	 */
	public static boolean hasManual(@NonNull final List<ResolvedCarrier> carriers)
	{
		return carriers.stream().anyMatch(ResolvedCarrier::isManual);
	}

	/**
	 * The DISTINCT manual carriers among the given ones (by product + goods-type + services). A package must not
	 * carry more than one — {@code > 1} is the "mixed manual" reject condition (enforced by callers with their own
	 * exception type). Central so the delivery-order carrier reducer and the picking consistency check agree.
	 */
	@NonNull
	public static ImmutableSet<ResolvedCarrier> distinctManualCarriers(@NonNull final List<ResolvedCarrier> carriers)
	{
		return carriers.stream()
				.filter(ResolvedCarrier::isManual)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Manual-wins: if any carrier is manual, only the (distinct) manual carriers are authoritative and override the
	 * non-manual ones; otherwise all the given carriers apply. Callers reject when {@link #distinctManualCarriers}
	 * yields more than one. Central so the delivery-order carrier reducer and the picking consistency check agree.
	 */
	@NonNull
	public static ImmutableList<ResolvedCarrier> manualWinningCarriers(@NonNull final List<ResolvedCarrier> carriers)
	{
		final ImmutableSet<ResolvedCarrier> distinctManual = distinctManualCarriers(carriers);
		return distinctManual.isEmpty() ? ImmutableList.copyOf(carriers) : distinctManual.asList();
	}
}
