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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintJobBL.ContextForAsyncProcessing;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.JavaProcess;

/**
 * Process all {@link I_C_Printing_Queue} items from selection (see {@link #createSelection()}) and creates corresponding {@link I_C_Print_Job}s.
 *
 * @author tsa
 *
 */
public abstract class AbstractPrintJobCreate extends JavaProcess
{
	private final static String MSG_INVOICE_GENERATE_NO_PRINTING_QUEUE_0P = "CreatePrintJobs_No_Printing_Queue_Selected";

	/**
	 * Create {@link I_C_Printing_Queue} selection by using {@link #getAD_PInstance_ID()} as current instance
	 *
	 * @param trxName transaction to be used when creating the selection
	 * @return number of records selected
	 */
	protected abstract int createSelection(final String trxName);

	@Override
	protected String doIt()
	{
		// NOTE: we need to clone the context which is passed to the other thread because else context (in ZK is enhanced) will be lost when this thread will end
		// and as a result we will create entries with AD_Client_ID/AD_Org_ID=0
		final Properties ctxToUse = (Properties)getCtx().clone();

		final List<IPrintingQueueSource> sources = createPrintingQueueSources(ctxToUse);

		final IClientUIInstance clientUI = Services.get(IClientUI.class).createInstance();

		for (final IPrintingQueueSource source : sources)
		{
			final Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{

					try
					{
						final ContextForAsyncProcessing printJobContext = ContextForAsyncProcessing.builder()
								.adPInstanceId(getAD_PInstance_ID())
								.build();

						Services.get(IPrintJobBL.class).createPrintJobs(source, printJobContext);
					}
					catch (final Exception ex)
					{
						log.error("Failed creating print job for {}", source, ex);
						Services.get(INotificationBL.class).notifyRecipient(UserNotificationRequest.builder()
								.topic(Printing_Constants.USER_NOTIFICATIONS_TOPIC)
								.recipientUserId(Env.getAD_User_ID(getCtx()))
								.contentPlain(ex.getLocalizedMessage())
								.build());
					}

				}
			};

			// FIXME: use some thread pool executors
			final Thread thread = clientUI.createUserThread(runnable, Thread.currentThread().getName() + "_PrintJobsProducer");
			thread.setDaemon(true);
			thread.start();
		}

		return "@Started@";
	}

	// each one with their own users to print user to print
	protected List<IPrintingQueueSource> createPrintingQueueSources(final Properties ctxToUse)
	{
		// NOTE: we create the selection out of transaction because methods which are polling the printing queue are working out of transaction
		final int selectionLength = createSelection(ITrx.TRXNAME_None);
		if (selectionLength <= 0)
		{
			throw new AdempiereException("@" + MSG_INVOICE_GENERATE_NO_PRINTING_QUEUE_0P + "@");
		}

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);

		final IPrintingQueueQuery query = printingQueueBL.createPrintingQueueQuery();
		query.setIsPrinted(false);
		query.setOnlyAD_PInstance_ID(getAD_PInstance_ID());

		return printingQueueBL.createPrintingQueueSources(ctxToUse, query);
	}
}
