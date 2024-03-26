/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.document.export.process;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.model.X_C_Doc_Outbound_Log;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceHandlerFactory;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceRequest;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceResponse;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceService;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;

import java.util.Objects;
import java.util.stream.Collectors;

public class C_Doc_Outbound_Log_Export_To_Post_Finance extends JavaProcess
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final PostFinanceYbInvoiceHandlerFactory postFinanceYbInvoiceHandlerFactory = SpringContextHolder.instance.getBean(PostFinanceYbInvoiceHandlerFactory.class);
	private final PostFinanceYbInvoiceService postFinanceYbInvoiceService = SpringContextHolder.instance.getBean(PostFinanceYbInvoiceService.class);
	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_Doc_Outbound_Log> queryFilter = getProcessInfo()
				.getQueryFilterOrElseTrue();
		Check.assumeNotNull(queryFilter, "queryFilter is not null");

		queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(
						I_C_Doc_Outbound_Log.COLUMNNAME_PostFinance_Export_Status,
						X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_Error,
						X_C_Doc_Outbound_Log.POSTFINANCE_EXPORT_STATUS_NotSent
				)
				.filter(queryFilter)
				.orderBy(I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID)
				.create()
				.iterateAndStream()
				.map(this::toPostFinanceExportRequest)
				.map(postFinanceYbInvoiceHandlerFactory::prepareYbInvoices)
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(PostFinanceYbInvoiceResponse::getBillerId))
				.forEach(postFinanceYbInvoiceService::exportToPostFinance);

		return MSG_OK;
	}

	private PostFinanceYbInvoiceRequest toPostFinanceExportRequest(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		return PostFinanceYbInvoiceRequest.builder()
				.documentReference(TableRecordReference.of(docOutboundLog.getAD_Table_ID(), docOutboundLog.getRecord_ID()))
				.pInstanceReference(TableRecordReference.of(I_AD_PInstance.Table_Name, getPinstanceId()))
				.docOutboundLogReference(TableRecordReference.of(I_C_Doc_Outbound_Log.Table_Name, docOutboundLog.getC_Doc_Outbound_Log_ID()))
				.build();
	}
}
