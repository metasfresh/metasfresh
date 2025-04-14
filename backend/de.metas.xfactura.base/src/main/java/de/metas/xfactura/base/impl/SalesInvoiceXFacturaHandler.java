/*
 * #%L
 * de.metas.xfactura.base
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

package de.metas.xfactura.base.impl;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.export.InvoiceToExportFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.util.Services;
import de.metas.xfactura.base.IXFacturaHandler;
import de.metas.xfactura.base.XFacturaRequest;
import de.metas.xfactura.base.XFacturaResponse;
import de.metas.xfactura.base.XFacturaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SalesInvoiceXFacturaHandler implements IXFacturaHandler
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@NonNull private final InvoiceToExportFactory invoiceToExportFactory;
	@NonNull private final XFacturaService xFacturaService;

	@Override
	public boolean applies(@NonNull final XFacturaRequest XFacturaRequest)
	{
		final TableRecordReference tableRecordReference = XFacturaRequest.getDocumentReference();
		if(!tableRecordReference.getTableName().equals(I_C_Invoice.Table_Name))
		{
			return false;
		}

		final I_C_Invoice invoiceRecord = invoiceBL.getById(InvoiceId.ofRepoId(tableRecordReference.getRecord_ID()));
		final DocBaseAndSubType docBaseAndSubType = docTypeDAO.getDocBaseAndSubTypeById(DocTypeId.ofRepoId(invoiceRecord.getC_DocType_ID()));
		return DocStatus.ofCode(invoiceRecord.getDocStatus()).isCompleted() && docBaseAndSubType.getDocBaseType().isSalesInvoice();
	}

	@Override
	public XFacturaResponse prepareData(@NonNull final XFacturaRequest XFacturaRequest)
	{
		final InvoiceId invoiceId = XFacturaRequest.getDocumentReference().getIdAssumingTableName(I_C_Invoice.Table_Name, InvoiceId::ofRepoId);
		final Optional<InvoiceToExport> invoiceToExportOptional = invoiceToExportFactory.getCreateForId(invoiceId);

		if(!invoiceToExportOptional.isPresent())
		{
			throw new AdempiereException("Failed to create invoiceToExport");
		}

		return xFacturaService.prepareData(XFacturaRequest, invoiceToExportOptional.get());
	}
}
