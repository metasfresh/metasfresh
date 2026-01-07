/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.esb.remadvimport.ecosio;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Optional;

@Getter
enum RemittanceAdviceInvoiceType
{
	/** RG - Invoice */
	SALES_INVOICE("380", "ARI"),

	/**
	 * GS - InvoiceCreditMemo
	 * and
	 * REKLA-RBEL - InvoiceCreditMemo
	 */
	CREDIT_MEMO("381", "ARC"),

	/**
	 * REKLA-BELA - DebitNote
	 */
	PURCHASE_INVOICE_1("383", "API"),

	/**
	 * RVG-BELA - DebitNoteForFinancialAdjustment
	 */
	PURCHASE_INVOICE_2("84", "API");
	;

	@NonNull
	private final String ediCode;

	@Getter
	@NonNull
	private final String metasDocBaseType;

	RemittanceAdviceInvoiceType(final @NonNull String ediCode, final @NonNull String metasDocBaseType)
	{
		this.ediCode = ediCode;
		this.metasDocBaseType = metasDocBaseType;
	}

	@NonNull
	public static Optional<RemittanceAdviceInvoiceType> getByEdiCode(@NonNull final String ediCode)
	{
		return Arrays.stream(values())
				.filter(type -> type.getEdiCode().equals(ediCode))
				.findFirst();
	}
}
