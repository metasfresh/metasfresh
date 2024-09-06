/*
 * #%L
 * de.metas.document.archive.base
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

package de.metas.document.archive.interceptor;

import de.metas.document.archive.api.impl.DocOutboundService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Component
@RequiredArgsConstructor
public class C_Invoice
{
	@NonNull
	private final DocOutboundService docOutboundService;

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Invoice.COLUMNNAME_POReference)
	public void updatePOReference(@NonNull final I_C_Invoice invoice)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(invoice);
		
		docOutboundService.updatePOReferenceIfExists(tableRecordReference, invoice.getPOReference());
	}
}
