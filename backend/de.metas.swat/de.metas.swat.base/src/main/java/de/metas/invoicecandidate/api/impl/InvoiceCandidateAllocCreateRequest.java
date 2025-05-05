/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.invoicecandidate.api.impl;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class InvoiceCandidateAllocCreateRequest
{
	@NonNull I_C_Invoice_Candidate invoiceCand;
	@NonNull I_C_InvoiceLine invoiceLine;
	@NonNull StockQtyAndUOMQty qtysInvoiced;
	
/**
	 * May be null or empty. Use it to provide a user-friendly note that can be displayed to the customer admin/user
	 */	@Nullable String note;

	@NonNull InvoiceLineAllocType invoiceLineAllocType;

}
