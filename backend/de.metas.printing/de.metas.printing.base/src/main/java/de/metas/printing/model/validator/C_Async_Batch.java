/**
 *
 */
package de.metas.printing.model.validator;

import de.metas.async.AsyncBatchId;
import de.metas.async.Async_Constants;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.organization.ClientAndOrgId;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.async.spi.impl.InvoicePDFConcatenateWorkpackageProcessor;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Printing_Queue;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.apps.search.UserQueryRepository;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_UserQuery;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting;

/*
 * #%L
 * marketing-serialleter
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
@Interceptor(I_C_Async_Batch.class)
@Component
public class C_Async_Batch
{
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ISessionBL sessionBL = Services.get(ISessionBL.class);
	private UserQueryRepository userQueriesRepository = SpringContextHolder.instance.getBean(UserQueryRepository.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Async_Batch.COLUMNNAME_Processed)
	public void print(@NonNull final I_C_Async_Batch asyncBatch)
	{

		if (asyncBatch.isProcessed()
				&& asyncBatch.getC_Async_Batch_Type_ID() > 0
				&& Async_Constants.C_Async_Batch_InternalName_InvoiceCandidate_Processing.equals(asyncBatch.getC_Async_Batch_Type().getInternalName()))
		{
			runPrintingProcess(asyncBatch); 
		}
	}

	private void runPrintingProcess(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final List<I_AD_UserQuery> userQueries = fetchUserQueries();
		for (final I_AD_UserQuery query : userQueries)
		{
			final List<IPrintingQueueSource> sources = createPrintingQueueSource(asyncBatch, query.getCode());
			enqueuePrintQueues(sources, asyncBatch);
		}
	}

	public void enqueuePrintQueues(@NonNull final List<IPrintingQueueSource> sources, @NonNull final I_C_Async_Batch parentAsyncBatch)
	{
		final AsyncBatchId asyncBatchId = createAsyncBatch();
		final Properties ctx = InterfaceWrapperHelper.getCtx(parentAsyncBatch);

		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, InvoicePDFConcatenateWorkpackageProcessor.class);
		queue.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.setC_Async_Batch(asyncBatchBL.getAsyncBatchById(asyncBatchId))
				.addElements(sources)
			.build();
	}

	private AsyncBatchId createAsyncBatch()
	{
		return  asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting);
	}

	private List<I_AD_UserQuery> fetchUserQueries()
	{
		final Map<String, String> valuesForPrefix = Services.get(ISysConfigBL.class).getValuesForPrefix("UserQuery_", ClientAndOrgId.SYSTEM);
		final Collection<String> names = valuesForPrefix.values();
		final List<I_AD_UserQuery> queries = new ArrayList<>();
		for (final String name : names)
		{
			queries.add(userQueriesRepository.getAD_UserQueryByName(name));
		}

		return  queries;
	}


	private List<IPrintingQueueSource> createPrintingQueueSource(@NonNull final I_C_Async_Batch asyncBatch, @NonNull final String whereClause)
	{
		final IQueryFilter<I_C_Printing_Queue> userQueryFilter = TypedSqlQueryFilter.of(whereClause);

		final IQuery<I_C_Printing_Queue> query = queryBL.createQueryBuilder(I_C_Printing_Queue.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_C_Async_Batch_ID, asyncBatch.getC_Async_Batch_ID())
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_ItemName, X_C_Printing_Queue.ITEMNAME_Rechnung)
				.filter(userQueryFilter)
				.create();

		final PInstanceId pinstanceId = PInstanceId.ofRepoId(asyncBatch.getAD_PInstance_ID());
		final int selectionLength = query.createSelection(pinstanceId);

		if (selectionLength <= 0)
		{
			return Collections.emptyList();
		}

		final IPrintingQueueQuery printingQuery = printingQueueBL.createPrintingQueueQuery();
		printingQuery.setFilterByProcessedQueueItems(false);
		printingQuery.setOnlyAD_PInstance_ID(pinstanceId);

		final Properties ctx = Env.getCtx();

		// we need to make sure exists AD_Session_ID in context; if not, a new session will be created
		sessionBL.getCurrentOrCreateNewSession(ctx);

		return printingQueueBL.createPrintingQueueSources(ctx, printingQuery);
	}
}
