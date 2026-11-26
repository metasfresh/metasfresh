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

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.invoice.service.InvoiceLineTaxLocationProvider;
import de.metas.invoice.service.TaxLocationAndFromCountry;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_InvoiceLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * A sales invoice line created from invoice candidates (with {@code C_Invoice_Line_Alloc} rows) was taxed from the tax location of its candidates:
 * that of the allocated candidate with the lowest {@code C_Invoice_Candidate_ID} (all candidates of one line share the stored VAT IDs).
 * For an order-line candidate that is the order line's delivery location, for an order candidate its drop-ship / buyer location;
 * see {@link IInvoiceCandidateHandlerBL#getTaxLocation}.
 */
@Component
public class InvoiceCandidateInvoiceLineTaxLocationProvider implements InvoiceLineTaxLocationProvider
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	@Override
	public Optional<TaxLocationAndFromCountry> getTaxLocation(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		// ordered by C_Invoice_Candidate_ID
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLine);
		if (invoiceCandidates.isEmpty())
		{
			return Optional.empty();
		}

		final TaxLocation taxLocation = invoiceCandidateHandlerBL.getTaxLocation(invoiceCandidates.get(0), null);
		return Optional.of(new TaxLocationAndFromCountry(
				BPartnerLocationAndCaptureId.of(taxLocation.getBpartnerLocationId()),
				taxLocation.getFromCountryId(),
				taxLocation.getWarehouseId()));
	}
}
