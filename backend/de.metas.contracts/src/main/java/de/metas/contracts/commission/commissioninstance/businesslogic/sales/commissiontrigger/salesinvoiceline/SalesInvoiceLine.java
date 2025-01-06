package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class SalesInvoiceLine
{
	@NonNull
	InvoiceAndLineId id;

	@NonNull
	ProductId productId;

	@NonNull
	CommissionPoints invoicedCommissionPoints;

	@NonNull
	Instant updated;

	@NonNull
	Quantity invoicedQty;

	@NonNull
	CurrencyId currencyId;
}
