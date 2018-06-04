package de.metas.order;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.money.Money;
import de.metas.payment.api.PaymentTermId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
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
@Builder
public class OrderLine
{
	@Nullable
	OrderLineId id;

	@NonNull
	OrderId orderId;

	@NonNull
	BPartnerId bPartnerId;

	@NonNull
	ProductId productId;

	@NonNull
	AttributeSetInstanceId asiId;

	@NonNull
	Money priceActual;

	@NonNull
	Quantity orderedQty;

	@NonNull
	WarehouseId warehouseId;

	int line;

	@NonNull
	PaymentTermId PaymentTermId;

	// Note: i think that the following two should go to "Order" once we have it.
	@NonNull
	OrgId orgId;

	/** note: besides the name "datePromised", it's also in the application dictionary declared as date+time, and some businesses need it that way. */
	@NonNull
	LocalDateTime datePromised;
}
