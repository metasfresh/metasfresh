package de.metas.customs;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.common.collect.SetMultimap;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.inout.InOutAndLineId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
@Builder
public class CustomsInvoiceRequest
{
	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	@NonNull
	String bpartnerAddress;

	@Nullable
	UserId userId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	SetMultimap<ProductId, InOutAndLineId> linesToExportMap;

	@NonNull
	LocalDate invoiceDate;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	String documentNo;

}
