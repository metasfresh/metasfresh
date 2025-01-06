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

package de.metas.invoice.detail;

import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceWithDetailsService
{
	private final InvoiceWithDetailsRepository invoiceWithDetailsRepository;
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	public InvoiceWithDetailsService(@NonNull final InvoiceWithDetailsRepository invoiceWithDetailsRepository)
	{
		this.invoiceWithDetailsRepository = invoiceWithDetailsRepository;
	}

	public void copyDetailsToReversal(@NonNull final InvoiceId originalInvoiceId, @NonNull final InvoiceId reversalInvoiceId)
	{
		final List<I_C_Invoice_Detail> invoiceDetailRecords = invoiceWithDetailsRepository.getInvoiceDetailsListForInvoiceId(originalInvoiceId);

		for (final I_C_Invoice_Detail detail : invoiceDetailRecords)
		{
			final I_C_Invoice_Detail reversedDetail = InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class);
			InterfaceWrapperHelper.copyValues(detail, reversedDetail);

			if (reversedDetail.getC_InvoiceLine_ID() > 0)
			{
				reversedDetail.setC_InvoiceLine_ID(
						invoiceDAO.retrieveReversalLine(
								invoiceDAO.retrieveLineById(
										InvoiceAndLineId.ofRepoId(
											detail.getC_Invoice_ID(),
											detail.getC_InvoiceLine_ID())),
										reversalInvoiceId.getRepoId())
								.getC_InvoiceLine_ID());
			}
			reversedDetail.setC_Invoice_ID(reversalInvoiceId.getRepoId());
			InterfaceWrapperHelper.save(reversedDetail);
		}
	}
}
