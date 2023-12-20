package de.metas.invoicecandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.params.InvoicingParams;

import java.util.Iterator;
import java.util.Properties;

/**
 * Generates from {@link I_C_Invoice}s from {@link I_C_Invoice_Candidate}s
 * <b>IMPORTANT:</b>
 * <ul>
 * <li>Candidates with IsError='Y' are ignored, even if they are part of the selection!</li>
 * <li>Candidates with IsProcessed='Y' are ignored, even if they are part of the selection!</li>
 * </ul>
 * <br>
 *
 * @author tsa
 *
 */
public interface IInvoiceGenerator
{
	/**
	 * Execute the generator
	 */
	IInvoiceGenerateResult generateInvoices(Iterator<I_C_Invoice_Candidate> invoiceCandidates);

	IInvoiceGenerator setContext(Properties ctx, String trxName);

	IInvoiceGenerator setIgnoreInvoiceSchedule(boolean ignoreInvoiceSchedule);

	IInvoiceGenerator setCollector(IInvoiceGenerateResult collector);

	IInvoiceGenerator setInvoicingParams(InvoicingParams invoicingParams);

}
