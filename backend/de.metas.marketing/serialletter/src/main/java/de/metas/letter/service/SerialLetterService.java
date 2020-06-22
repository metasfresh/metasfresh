/**
 *
 */
package de.metas.letter.service;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import de.metas.printing.PrintOutputFacade;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintJobBL.ContextForAsyncProcessing;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * marketing-serialletter
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

@Service
public class SerialLetterService
{
	private final static transient Logger logger = LogManager.getLogger(SerialLetterService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final ISessionBL sessionBL = Services.get(ISessionBL.class);
	private final PrintOutputFacade printOutputFacade;

	public SerialLetterService(@NonNull final PrintOutputFacade printOutputFacade)
	{
		this.printOutputFacade = printOutputFacade;
	}

	public void printAutomaticallyLetters(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final List<IPrintingQueueSource> sources = createPrintingQueueSource(asyncBatch);
		if (sources.isEmpty())
		{
			throw new AdempiereException("Nothing selected");
		}
		for (final IPrintingQueueSource source : sources)
		{
			source.setName("Print letter for C_Async_Batch_ID = " + asyncBatch.getC_Async_Batch_ID());
			print(source, asyncBatch);
		}
	}

	/**
	 * Create Printing queue source.
	 *
	 * Contains printing queues for the letters that belong to a specific <code>C_Async_Batch_ID</code>
	 */
	private List<IPrintingQueueSource> createPrintingQueueSource(@NonNull final I_C_Async_Batch asyncBatch)
	{
		final IQuery<I_C_Printing_Queue> query = queryBL.createQueryBuilder(I_C_Printing_Queue.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_C_Async_Batch_ID, asyncBatch.getC_Async_Batch_ID())
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_Processed, false)
				.create();

		final PInstanceId pinstanceId = PInstanceId.ofRepoId(asyncBatch.getAD_PInstance_ID());
		final int selectionLength = query.createSelection(pinstanceId);

		if (selectionLength <= 0)
		{
			logger.info("Nothing to print!");
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

	private void print(@NonNull final IPrintingQueueSource source, @NonNull final I_C_Async_Batch asyncBatch)
	{
		final ContextForAsyncProcessing printJobContext = ContextForAsyncProcessing.builder()
				.adPInstanceId(PInstanceId.ofRepoIdOrNull(asyncBatch.getAD_PInstance_ID()))
				.parentAsyncBatchId(asyncBatch.getC_Async_Batch_ID())
				.build();
		printOutputFacade.print(source, printJobContext);
	}
}
