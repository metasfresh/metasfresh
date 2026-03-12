/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocSubType;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static de.metas.contracts.modular.ComputingMethodType.DEFINITIVE_INVOICE_SPECIFIC_METHODS;
import static de.metas.contracts.modular.ComputingMethodType.PURCHASE_FINAL_INVOICE_SPECIFIC_METHODS;
import static de.metas.contracts.modular.ComputingMethodType.SALES_FINAL_INVOICE_SPECIFIC_METHODS;

@Getter
@RequiredArgsConstructor
public enum ModCntrInvoiceType
{
	PurchaseFinal(DocSubType.FinalInvoice, DocSubType.FinalCreditMemo, PURCHASE_FINAL_INVOICE_SPECIFIC_METHODS),
	Definitive(DocSubType.DefinitiveInvoice, DocSubType.DefinitiveCreditMemo, DEFINITIVE_INVOICE_SPECIFIC_METHODS),
	SalesFinal(DocSubType.FinalInvoice, DocSubType.FinalCreditMemo, SALES_FINAL_INVOICE_SPECIFIC_METHODS);

	@NonNull final DocSubType positiveAmtDocSubType;
	@NonNull final DocSubType negativAmtDocSubType;
	@NonNull final ImmutableSet<ComputingMethodType> computingMethodTypes;
}
