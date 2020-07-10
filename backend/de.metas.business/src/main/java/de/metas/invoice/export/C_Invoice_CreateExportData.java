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

package de.metas.invoice.export;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.Adempiere;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.export.InvoiceExportService;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

public class C_Invoice_CreateExportData extends JavaProcess
{
	private final InvoiceExportService invoiceExportService =  Adempiere.getBean(InvoiceExportService.class);


	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Invoice> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final ImmutableList<InvoiceId> invoiceIdsToExport = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice.class)
				.filter(queryFilter)
				.create()
				.listIds()
				.stream()
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		invoiceExportService.exportInvoices(invoiceIdsToExport);

		return MSG_OK;
	}
}
