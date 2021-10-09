package de.metas.banking.payment.paymentallocation;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.banking.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder(toBuilder = true)
public class InvoiceToAllocateQuery
{
	/**
	 * Date used to calculate the currency conversion and discount
	 */
	@NonNull
	ZonedDateTime evaluationDate;
	
	@Nullable
	BPartnerId bpartnerId;

	@Nullable
	CurrencyId currencyId;

	@Nullable
	ClientAndOrgId clientAndOrgId;

	@NonNull
	@Singular
	ImmutableSet<InvoiceId> onlyInvoiceIds;

	@NonNull
	@Singular
	ImmutableSet<InvoiceId> excludeInvoiceIds;
}
