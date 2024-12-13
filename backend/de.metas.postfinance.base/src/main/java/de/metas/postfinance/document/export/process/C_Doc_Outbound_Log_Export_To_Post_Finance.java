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
import de.metas.document.archive.postfinance.PostFinanceStatus;
import de.metas.i18n.ExplainedOptional;
import de.metas.organization.ClientAndOrgId;
import de.metas.postfinance.document.export.IPostFinanceYbInvoiceHandler;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceHandlerFactory;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceRequest;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceResponse;
import de.metas.postfinance.document.export.PostFinanceYbInvoiceService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StreamUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class C_Doc_Outbound_Log_Export_To_Post_Finance extends JavaProcess
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final PostFinanceYbInvoiceHandlerFactory postFinanceYbInvoiceHandlerFactory = SpringContextHolder.instance.getBean(PostFinanceYbInvoiceHandlerFactory.class);
	private final PostFinanceYbInvoiceService postFinanceYbInvoiceService = SpringContextHolder.instance.getBean(PostFinanceYbInvoiceService.class);

	private static final String SYS_CFG_INVOICE_UPLOAD_BATCH_SIZE = "de.metas.postfinance.document.export.process.C_Doc_Outbound_Log_Export_To_Post_Finance.PostFinanceUploadInvoiceBatchSize";

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final int chunkSize = getChunkSize();
		StreamUtils.dice(
						streamDocOutboundLogs()
								.map(this::toPostFinanceExportRequest)
								.map(this::preflightEvaluate)
								.filter(Objects::nonNull),
						chunkSize)
				.forEach(this::sendChunk);

		return MSG_OK;
	}

	private int getChunkSize() {return sysConfigBL.getIntValue(SYS_CFG_INVOICE_UPLOAD_BATCH_SIZE, 20);}

	private Stream<I_C_Doc_Outbound_Log> streamDocOutboundLogs()
	{
		final IQueryFilter<I_C_Doc_Outbound_Log> queryFilter = getProcessInfo()
				.getQueryFilterOrElseTrue();
		Check.assumeNotNull(queryFilter, "queryFilter is not null");

		return queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(
						I_C_Doc_Outbound_Log.COLUMNNAME_PostFinance_Export_Status,
						PostFinanceStatus.NOT_SEND,
						PostFinanceStatus.TRANSMISSION_ERROR
				)
				.filter(queryFilter)
				.orderBy(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Org_ID)
				.orderBy(I_C_Doc_Outbound_Log.COLUMNNAME_C_Doc_Outbound_Log_ID)
				.create()
				.iterateAndStream();
	}

	private PostFinanceYbInvoiceRequest toPostFinanceExportRequest(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		return PostFinanceYbInvoiceRequest.builder()
				.documentReference(TableRecordReference.of(docOutboundLog.getAD_Table_ID(), docOutboundLog.getRecord_ID()))
				.pInstanceReference(TableRecordReference.of(I_AD_PInstance.Table_Name, getPinstanceId()))
				.docOutboundLogReference(TableRecordReference.of(I_C_Doc_Outbound_Log.Table_Name, docOutboundLog.getC_Doc_Outbound_Log_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(docOutboundLog.getAD_Client_ID(), docOutboundLog.getAD_Org_ID()))
				.build();
	}

	@Nullable
	private PostFinanceYbInvoiceRequest preflightEvaluate(PostFinanceYbInvoiceRequest request)
	{
		final ExplainedOptional<IPostFinanceYbInvoiceHandler> eligibleHandler = postFinanceYbInvoiceHandlerFactory.getEligibleHandler(request);
		if (!eligibleHandler.isPresent())
		{
			postFinanceYbInvoiceService.setPostFinanceStatusForSkipped(request.getDocOutboundLogReference(), eligibleHandler.getExplanation());
			return null;
		}

		if (!postFinanceYbInvoiceService.isPostFinanceActive(request))
		{
			postFinanceYbInvoiceService.setPostFinanceStatusForSkipped(request.getDocOutboundLogReference(), "Skipped because not active for Org or BPGroup/BPartner");
			return null;
		}

		// Possible valid request 
		return request;
	}

	private void sendChunk(final List<PostFinanceYbInvoiceRequest> requests)
	{
		trxManager.assertThreadInheritedTrxNotExists();
		trxManager.runInThreadInheritedTrx(() -> {
			// NOTE: we assume errors are caught by service methods, so at this point if an error is thrown, we are letting it flow.
			
			final List<PostFinanceYbInvoiceResponse> invoices = postFinanceYbInvoiceHandlerFactory.prepareYbInvoices(requests);
			postFinanceYbInvoiceService.exportToPostFinance(invoices);
		});
	}
}
