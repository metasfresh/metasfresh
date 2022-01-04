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

import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.organization.ClientAndOrgId;
import de.metas.printing.async.spi.impl.PrintingQueuePDFConcatenateWorkpackageProcessor;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static de.metas.async.Async_Constants.C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting;

@UtilityClass
public class AsyncBatchNotificationHelper
{
	private final IQueryBL iQueryBL = Services.get(IQueryBL.class);
	private final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final IAsyncBatchListeners asyncBatchListeners = Services.get(IAsyncBatchListeners.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	protected void notify(final I_C_Async_Batch asyncBatch)
	{
		asyncBatchListeners.notify(asyncBatch);
	}

	protected void runPrintingProcess(@NonNull final I_C_Async_Batch asyncBatch)
	{
		List<IQueryBuilder<I_C_Printing_Queue>> queries = fetchPrintingQueues(asyncBatch);
		for (final IQueryBuilder<I_C_Printing_Queue> pqs : queries)
		{
			enqueuePrintQueues(pqs, asyncBatch);
		}
	}

	private void enqueuePrintQueues(@NonNull final IQueryBuilder<I_C_Printing_Queue> pqs, @NonNull final I_C_Async_Batch parentAsyncBatch)
	{
		final List<I_C_Printing_Queue> printing_queues = pqs.create().list();
		if (printing_queues.isEmpty())
		{
			return;
		}

		final AsyncBatchId asyncBatchId = createAsyncBatch();
		final Properties ctx = InterfaceWrapperHelper.getCtx(parentAsyncBatch);

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, PrintingQueuePDFConcatenateWorkpackageProcessor.class);
		queue.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.setC_Async_Batch(asyncBatchBL.getAsyncBatchById(asyncBatchId))
				.addElements(printing_queues)
				.build();
	}

	private AsyncBatchId createAsyncBatch()
	{
		return asyncBatchBL.newAsyncBatch(C_Async_Batch_InternalName_AutomaticallyInvoicePdfPrinting);
	}

	private List<IQueryBuilder<I_C_Printing_Queue>> fetchPrintingQueues(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final Map<String, String> valuesForPrefix = sysConfigBL.getValuesForPrefix("UserQuery_", ClientAndOrgId.SYSTEM);
		final Collection<String> clauses = valuesForPrefix.values();
		final List<IQueryBuilder<I_C_Printing_Queue>> queries = new ArrayList<>();
		for (final String whereClause : clauses)
		{
			IQueryBuilder<I_C_Printing_Queue> queryBuilder = createPrinitngQueueQueryBuilder(asyncBatch, whereClause);
			queries.add(queryBuilder);
		}

		return queries;
	}

	private IQueryBuilder<I_C_Printing_Queue> createPrinitngQueueQueryBuilder(@NonNull final I_C_Async_Batch asyncBatch, @NonNull final String whereClause)
	{
		final IQueryFilter<I_C_Printing_Queue> userQueryFilter = TypedSqlQueryFilter.of(whereClause);

		final IQueryBuilder<I_C_Printing_Queue> queryBuilder = iQueryBL
				.createQueryBuilder(I_C_Printing_Queue.class)
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_C_Async_Batch_ID, asyncBatch.getC_Async_Batch_ID())
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_Processed, false)
				.filter(userQueryFilter);

		return queryBuilder;
	}
}
