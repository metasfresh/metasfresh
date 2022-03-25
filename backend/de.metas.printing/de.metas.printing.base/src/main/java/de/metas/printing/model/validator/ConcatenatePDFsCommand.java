/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.printing.model.validator;

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.printing.async.spi.impl.PrintingQueuePDFConcatenateWorkpackageProcessor;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting;

class ConcatenatePDFsCommand
{
	private static final String QUERY_PREFIX = "de.metas.printing.pdf_file.whereClause.";

	private final Logger logger = LogManager.getLogger(ConcatenatePDFsCommand.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	//
	// Params
	private final AsyncBatchId printingQueueItemsGeneratedAsyncBatchId;
	private final ClientAndOrgId clientAndOrgId;

	@Builder
	private ConcatenatePDFsCommand(@NonNull final I_C_Async_Batch printingQueueItemsGeneratedAsyncBatch)
	{
		this.clientAndOrgId = ClientAndOrgId.ofClientAndOrg(printingQueueItemsGeneratedAsyncBatch.getAD_Client_ID(), printingQueueItemsGeneratedAsyncBatch.getAD_Org_ID());
		this.printingQueueItemsGeneratedAsyncBatchId = AsyncBatchId.ofRepoId(printingQueueItemsGeneratedAsyncBatch.getC_Async_Batch_ID());
	}

	public void execute()
	{
		final List<PrintingQueueQueryRequest> queryRequests = getPrintingQueueQueryBuilders();
		if (queryRequests.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("*** No queries / sysconfigs defined. Create sysconfigs prefixed with `{}`", QUERY_PREFIX);
			return;
		}

		for (final PrintingQueueQueryRequest queryRequest : queryRequests)
		{
			enqueuePrintQueues(queryRequest);
		}
	}

	private void enqueuePrintQueues(@NonNull final PrintingQueueQueryRequest queryRequest)
	{
		final List<I_C_Printing_Queue> printingQueues = queryRequest.getQuery().list();
		if (printingQueues.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("*** There is nothing to enqueue. Skipping it: {}", queryRequest);
			return;
		}

		final Properties ctx = Env.getCtx();

		final I_C_Async_Batch parentAsyncBatchRecord = asyncBatchBL.getAsyncBatchById(printingQueueItemsGeneratedAsyncBatchId);

		final I_C_Async_Batch asyncBatch = asyncBatchBL.newAsyncBatch()
				.setContext(ctx)
				.setC_Async_Batch_Type(C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting)
				.setName(C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting)
				.setDescription(queryRequest.getQueryName())
				.setParentAsyncBatchId(printingQueueItemsGeneratedAsyncBatchId)
				.setOrgId(OrgId.ofRepoId(parentAsyncBatchRecord.getAD_Org_ID()))
				.build();

		workPackageQueueFactory
				.getQueueForEnqueuing(ctx, PrintingQueuePDFConcatenateWorkpackageProcessor.class)
				.newWorkPackage()
				.setC_Async_Batch(asyncBatch)
				.addElements(printingQueues)
				.buildAndEnqueue();
	}

	private List<PrintingQueueQueryRequest> getPrintingQueueQueryBuilders()
	{
		Map<String, String> filtersMap = sysConfigBL.getValuesForPrefix(QUERY_PREFIX, clientAndOrgId);
		final Collection<String> keys = filtersMap.keySet();

		final ArrayList<PrintingQueueQueryRequest> queries = new ArrayList<>();

		for (final String key : keys)
		{
			final String whereClause = filtersMap.get(key);
			final IQuery<I_C_Printing_Queue> query = createPrintingQueueQuery(whereClause);
			final PrintingQueueQueryRequest request = PrintingQueueQueryRequest.builder()
					.queryName(key)
					.query(query)
					.build();
			queries.add(request);
		}

		return queries;
	}

	private IQuery<I_C_Printing_Queue> createPrintingQueueQuery(@NonNull final String whereClause)
	{
		return queryBL
				.createQueryBuilder(I_C_Printing_Queue.class)
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_AD_Client_ID, clientAndOrgId.getClientId())
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_C_Async_Batch_ID, printingQueueItemsGeneratedAsyncBatchId)
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_Processed, false)
				.filter(TypedSqlQueryFilter.of(whereClause))
				.create();
	}

}
