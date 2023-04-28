/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim;

import de.metas.calendar.standard.CalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class InterimInvoiceFlatrateTerm
{
	@Nullable
	InterimInvoiceFlatrateTermId id;
	@NonNull FlatrateTermId flatrateTermId;
	@NonNull OrderLineId orderLineId;
	@Nullable
	InvoiceCandidateId withholdingInvoiceCandidateId;
	@Nullable
	InvoiceCandidateId interimInvoiceCandidateId;
	@NonNull
	UomId uomId;
	@NonNull
	ProductId productId;
	@Nullable
	CurrencyId currencyId;
	@Nullable
	CalendarId calendarId;
	@Nullable
	Quantity qtyOrdered;
	@Nullable
	Quantity qtyDelivered;
	@Nullable
	Quantity qtyInvoiced;
	@Nullable
	Money priceActual;
}
