package de.metas.invoice;

import java.util.Optional;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class BPartnerInvoicingInfo
{
	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	BPartnerLocationId billBPartnerLocationId;

	@NonNull
	@Default
	Optional<BPartnerContactId> billContactId = Optional.empty();

	@NonNull
	@Default
	Optional<PaymentRule> paymentRule = Optional.empty();

	@NonNull
	@Default
	Optional<PaymentTermId> paymentTermId = Optional.empty();

	@NonNull
	PriceListId priceListId;
	boolean taxIncluded;

	@NonNull
	CurrencyId currencyId;
}
