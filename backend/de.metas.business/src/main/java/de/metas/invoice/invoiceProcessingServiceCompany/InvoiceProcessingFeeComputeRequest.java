package de.metas.invoice.invoiceProcessingServiceCompany;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZonedDateTime;

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
public class InvoiceProcessingFeeComputeRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	ZonedDateTime evaluationDate;

	@NonNull
	BPartnerId customerId;

	@NonNull
	InvoiceId invoiceId;

	@NonNull
	Amount invoiceGrandTotal;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	@Builder.Default
	OptionalBoolean serviceInvoiceWasAlreadyGenerated = OptionalBoolean.UNKNOWN;
}
