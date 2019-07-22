package de.metas.invoice.order.restart;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsRequest;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class VoidInvoicesRelatedToOrderHandler implements VoidOrderAndRelatedDocsHandler
{

	@Override
	public RecordsToHandleKey getRecordsToHandleKey()
	{
		return RecordsToHandleKey.of(I_C_Invoice.Table_Name);
	}

	@Override
	public void handleOrderVoided(@NonNull final VoidOrderAndRelatedDocsRequest request)
	{
		final IPair<RecordsToHandleKey, List<ITableRecordReference>> recordsToHandle = request.getRecordsToHandle();

		final List<I_C_Invoice> invoiceRecordsToHandle = TableRecordReference.getModels(recordsToHandle.getRight(), I_C_Invoice.class);

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		for (final I_C_Invoice invoiceRecord : invoiceRecordsToHandle)
		{
			final DocStatus invoiceDocStatus = DocStatus.ofCode(invoiceRecord.getDocStatus());
			if (invoiceDocStatus.isReversedOrVoided())
			{
				continue; // nothing to do
			}
			if (invoiceDocStatus.isCompleted())
			{
				documentBL.processEx(invoiceRecord, IDocument.ACTION_Reverse_Correct, DocStatus.Reversed.getCode());
				saveRecord(invoiceRecord);
			}
			else
			{
				final ITranslatableString errorMsg = VoidOrderAndRelatedDocsHandler.createInvalidDocStatusErrorMessage(
						request.getOrderId(),
						I_C_Invoice.COLUMNNAME_C_Invoice_ID,
						invoiceRecord.getDocumentNo(),
						invoiceDocStatus);
				throw new AdempiereException(errorMsg);
			}
		}
	}
}
