package de.metas.contracts.refund;

import java.time.LocalDate;

import de.metas.bpartner.BPartnerId;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.product.ProductId;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
@AllArgsConstructor
public class RefundContractQuery
{
	public static RefundContractQuery of(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate)
	{
		return new RefundContractQuery(
				invoiceCandidate.getBpartnerLocationId().getBpartnerId(),
				invoiceCandidate.getShipmentBPartnerId(),
				invoiceCandidate.getProductId(),
				invoiceCandidate.getInvoiceableFrom());
	}

	public static RefundContractQuery of(@NonNull final CalculateProfitPriceActualRequest request)
	{
		return new RefundContractQuery(
				request.getBPartnerId(),
				request.getShipmentBPartnerId(),
				request.getProductId(),
				request.getDate());
	}

	/** The partner that is invoiced. */
	@NonNull
	BPartnerId bPartnerId;

	/** The partner that the goods are shipped to; {@code null} if there is none, e.g. because the invoice candidate has no order. */
	@Nullable
	BPartnerId shipmentBPartnerId;

	@NonNull
	ProductId productId;

	@NonNull
	LocalDate date;

	/** A query for the invoice partner only. */
	public RefundContractQuery(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ProductId productId,
			@NonNull final LocalDate date)
	{
		this(bPartnerId, null, productId, date);
	}
}
