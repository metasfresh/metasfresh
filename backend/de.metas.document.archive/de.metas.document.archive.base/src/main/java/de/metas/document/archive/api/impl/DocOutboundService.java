/*
 * #%L
 * de.metas.document.archive.base
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

package de.metas.document.archive.api.impl;

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log_Line;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.archive.api.IPDFArchiveProvider;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;

@Service
public class DocOutboundService implements IPDFArchiveProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocOutboundDAO archiveDAO = Services.get(IDocOutboundDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	@Override
	public <T extends I_AD_Archive> Optional<T> getPDFArchiveForModel(
			@NonNull final TableRecordReference recordRef,
			@NonNull final Class<T> archiveClass)
	{
		final AdTableId adTableId = recordRef.getAdTableId();
		final int recordId = recordRef.getRecord_ID();

		final IQueryBuilder<I_C_Doc_Outbound_Log> docOutboundLogQueryBuilder = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMN_Record_ID, recordId);

		final IQueryBuilder<I_C_Doc_Outbound_Log_Line> docOutboundLogLineQueryBuilder = docOutboundLogQueryBuilder
				.andCollectChildren(I_C_Doc_Outbound_Log_Line.COLUMN_C_Doc_Outbound_Log_ID, I_C_Doc_Outbound_Log_Line.class);

		archiveDAO.addPDFArchiveLogLineFilters(docOutboundLogLineQueryBuilder);

		final I_C_Doc_Outbound_Log_Line pdfLine = docOutboundLogLineQueryBuilder.create()
				.first();

		if (pdfLine == null)
		{
			return Optional.empty();
		}
		final T archive = create(pdfLine.getAD_Archive(), archiveClass);
		return Optional.of(archive);
	}

	@Nullable
	public String getDocumentEmail(@NonNull I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final TableRecordReference recordRef = TableRecordReference.of(docOutboundLogRecord.getAD_Table_ID(), docOutboundLogRecord.getRecord_ID());

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Invoice.class))
		{
			final I_C_Invoice invoice = load(InvoiceId.ofRepoId(recordRef.getRecord_ID()), I_C_Invoice.class);

			return invoice.getEMail();
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Order.class))
		{
			final I_C_Order order = load(OrderId.ofRepoId(recordRef.getRecord_ID()), I_C_Order.class);

			return order.getEMail();
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_M_InOut.class))
		{
			final I_M_InOut inout = load(InOutId.ofRepoId(recordRef.getRecord_ID()), I_M_InOut.class);
			return inout.getEMail();
		}

		return null;
	}

	@Nullable
	public String getLocationEmail(@NonNull I_C_Doc_Outbound_Log docOutboundLogRecord)
	{
		final TableRecordReference recordRef = TableRecordReference.of(docOutboundLogRecord.getAD_Table_ID(), docOutboundLogRecord.getRecord_ID());

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Invoice.class))
		{
			return invoiceBL.getLocationEmail(InvoiceId.ofRepoId(recordRef.getRecord_ID()));
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_C_Order.class))
		{
			return orderBL.getLocationEmail(OrderId.ofRepoId(recordRef.getRecord_ID()));
		}

		if (recordRef.getAD_Table_ID() == getTableId(I_M_InOut.class))
		{
			return inoutBL.getLocationEmail(InOutId.ofRepoId(recordRef.getRecord_ID()));
		}

		return null;
	}

}
