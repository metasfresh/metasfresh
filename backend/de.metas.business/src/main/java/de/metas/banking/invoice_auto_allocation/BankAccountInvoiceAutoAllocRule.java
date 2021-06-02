/*
 * #%L
 * de.metas.business
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

package de.metas.banking.invoice_auto_allocation;

import de.metas.document.DocTypeId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class BankAccountInvoiceAutoAllocRule
{
	private final DocTypeId invoiceDocTypeId;

	@Builder
	public BankAccountInvoiceAutoAllocRule(
			@NonNull final DocTypeId invoiceDocTypeId)
	{
		this.invoiceDocTypeId = invoiceDocTypeId;
	}

	public boolean isMatching(@NonNull final DocTypeId invoiceDocTypeId)
	{
		return DocTypeId.equals(this.invoiceDocTypeId, invoiceDocTypeId);
	}
}
