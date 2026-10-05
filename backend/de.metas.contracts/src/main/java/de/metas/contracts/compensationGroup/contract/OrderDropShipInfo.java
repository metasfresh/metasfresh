package de.metas.contracts.compensationGroup.contract;

import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.contracts
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

/** The drop-ship facts of an order that decide whether it takes over the contract discounts of a linked sales order. */
@Value
@Builder
public class OrderDropShipInfo
{
	@NonNull SOTrx soTrx;
	boolean dropShip;
	@Nullable OrderId linkedOrderId;

	/** @return the linked sales order, if this is a drop-ship purchase order */
	public Optional<OrderId> getDropShipLinkedSalesOrderId()
	{
		return soTrx.isPurchase() && dropShip ? Optional.ofNullable(linkedOrderId) : Optional.empty();
	}
}
