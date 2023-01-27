/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.request;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class CreateManualInvoiceRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	BPartnerLocationId billBPartnerLocationId;

	@NonNull
	PriceListId priceListId;

	@Nullable
	BPartnerContactId billContactId;

	@NonNull
	Instant dateInvoiced;

	@NonNull
	Instant dateAcct;

	@NonNull
	Instant dateOrdered;

	@NonNull
	String externalHeaderId;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	String poReference;

	@NonNull
	SOTrx soTrx;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	ImmutableList<CreateManualInvoiceLineRequest> lines;
	
	@NonNull
	public BPartnerId getBillBPartnerId()
	{
		return billBPartnerLocationId.getBpartnerId();
	}
}
