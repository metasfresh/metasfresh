package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

import java.time.Instant;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.invoice.InvoiceLineId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	InvoiceLineId id;

	@NonNull
	ProductId productId;

	@NonNull
	CommissionPoints forecastCommissionPoints;

	@NonNull
	CommissionPoints commissionPointsToInvoice;

	@NonNull
	CommissionPoints invoicedCommissionPoints;

	@NonNull
	Percent tradedCommissionPercent;

	@NonNull
	Instant updated;
}
