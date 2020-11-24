package de.metas.printing.process;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintJobBL.ContextForAsyncProcessing;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.JavaProcess;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * Process all {@link I_C_Printing_Queue} items from selection (see {@link #createSelection()}) and creates corresponding {@link I_C_Print_Job}s.
 *
 * @author tsa
 *
 */
public abstract class AbstractPrintJobCreate extends JavaProcess
{
	private static final Logger logger = LogManager.getLogger(AbstractPrintJobCreate.class);

	private final static String MSG_INVOICE_GENERATE_NO_PRINTING_QUEUE_0P = "CreatePrintJobs_No_Printing_Queue_Selected";

	private final PrintOutputFacade printOutputFacade = SpringContextHolder.instance.getBean(PrintOutputFacade.class);

	/**
	 * Create {@link I_C_Printing_Queue} selection by using {@link #getPinstanceId()} as current instance
	 *
	 * @return number of records selected
	 */
	protected abstract int createSelection();

	@Override
	protected String doIt()
	{
		// NOTE: we need to clone the context which is passed to the other thread because else context (in ZK is enhanced) will be lost when this thread will end
		// and as a result we will create entries with AD_Client_ID/AD_Org_ID=0
		final Properties ctxToUse = (Properties)getCtx().clone();

		final List<IPrintingQueueSource> sources = createPrintingQueueSources(ctxToUse);

		for (final IPrintingQueueSource source : sources)
		{
			try
			{
				final ContextForAsyncProcessing printJobContext = ContextForAsyncProcessing.builder()
						.adPInstanceId(getPinstanceId())
						.build();

				printOutputFacade.print(source,printJobContext);
			}
			catch (final RuntimeException ex)
			{
				Loggables.withLogger(logger, Level.WARN).addLog("Failed creating print job for IPrintingQueueSource={}; exception={}", source, ex);
				throw ex;
			}
		}

		return MSG_OK;
	}

	// each one with their own users to print user to print
	protected List<IPrintingQueueSource> createPrintingQueueSources(final Properties ctxToUse)
	{
		// NOTE: we create the selection out of transaction because methods which are polling the printing queue are working out of transaction
		final int selectionLength = createSelection();
		if (selectionLength <= 0)
		{
			throw new AdempiereException("@" + MSG_INVOICE_GENERATE_NO_PRINTING_QUEUE_0P + "@");
		}

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);

		final IPrintingQueueQuery query = printingQueueBL.createPrintingQueueQuery();
		query.setFilterByProcessedQueueItems(false);
		query.setOnlyAD_PInstance_ID(getPinstanceId());

		return printingQueueBL.createPrintingQueueSources(ctxToUse, query);
	}
}
