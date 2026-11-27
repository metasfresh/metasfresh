/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoicecandidate.vatid;

import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.experimental.UtilityClass;
import org.compiere.SpringContextHolder;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

/**
 * For invoicing unit tests whose fixtures lack the master data a real sales candidate has (partner group,
 * shipment location, taxes per country): registers an {@link InvoiceCandidateVATaxIdResolver} that answers
 * like the real one for a partner without any VAT ID — empty VAT IDs for a sales candidate, {@code null} for a
 * purchase candidate. So these tests still run the VAT ID part of the aggregation key and the header's
 * VAT ID check, with "no VAT IDs".
 */
@UtilityClass
public class NoVATaxIdsResolver
{
	public static void register()
	{
		final InvoiceCandidateVATaxIdResolver resolver = Mockito.mock(InvoiceCandidateVATaxIdResolver.class);
		Mockito.when(resolver.resolve(any(), any(), any()))
				.thenAnswer(invocation -> invocation.<I_C_Invoice_Candidate>getArgument(0).isSOTrx()
						? InvoiceVATaxIds.NONE
						: null);
		SpringContextHolder.registerJUnitBean(InvoiceCandidateVATaxIdResolver.class, resolver);
	}
}
