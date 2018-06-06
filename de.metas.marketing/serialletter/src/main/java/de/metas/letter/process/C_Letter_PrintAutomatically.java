/**
 *
 */
package de.metas.letter.process;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintJobBL.ContextForAsyncProcessing;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.marketing.serialletter
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

public class C_Letter_PrintAutomatically extends JavaProcess
{

	// Services
	final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	public static final String MSG_C_Letter_PrintAutomatically = "C_Letter_PrintAutomatically";

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID)
	private int asyncBacthId;

	@Override
	protected String doIt() throws Exception
	{
		if (asyncBacthId > 0)
		{
			final I_AD_PInstance pinstance = InterfaceWrapperHelper.create(getCtx(), getAD_PInstance_ID(), I_AD_PInstance.class, ITrx.TRXNAME_None);
			pinstance.setAD_User_ID(getProcessInfo().getAD_User_ID());
			InterfaceWrapperHelper.save(pinstance);

			final List<IPrintingQueueSource> sources = createPrintingQueueSource();
			for (final IPrintingQueueSource source : sources)
			{
				source.setName("Print letter for C_Async_Batch_ID = " + asyncBacthId);
				print(source);
			}
			}

		return "OK";
	}

	private void print(final IPrintingQueueSource source)
	{

		Services.get(ITrxManager.class).run(get_TrxName(), (TrxRunnable)localTrxName -> {
			try
			{

				final ContextForAsyncProcessing printJobContext = ContextForAsyncProcessing.builder()
						.adPInstanceId(getAD_PInstance_ID())
						.parentAsyncBatchId(asyncBacthId)
						.build();

				Services.get(IPrintJobBL.class).createPrintJobs(source, printJobContext);
			}
			catch (final Exception ex)
			{
				log.error("Failed creating print job for {}", source, ex);
				Services.get(INotificationBL.class).send(UserNotificationRequest.builder()
						.topic(Printing_Constants.USER_NOTIFICATIONS_TOPIC)
						.recipientUserId(Env.getAD_User_ID(getCtx()))
						.contentPlain(ex.getLocalizedMessage())
						.build());
			}

		});
	}

	/**
	 * Create Printing queue source<bR>
	 * <ul>
	 * Contains printing queues for the letters that belong to a specific <code>C_Async_Batch_ID</code>
	 *
	 * @param ctxToUse
	 * @return
	 */
	private List<IPrintingQueueSource> createPrintingQueueSource()
	{
		final IQuery<I_C_Printing_Queue> query = Services.get(IQueryBL.class).createQueryBuilder(I_C_Printing_Queue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_C_Async_Batch_ID, asyncBacthId)
				.create();

		final int selectionLength = query.createSelection(getAD_PInstance_ID());

		if (selectionLength <= 0)
		{
			log.info("Nothing to print!");
		}

		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final IPrintingQueueQuery printingQuery = printingQueueBL.createPrintingQueueQuery();
		printingQuery.setIsPrinted(false);
		printingQuery.setOnlyAD_PInstance_ID(getAD_PInstance_ID());

		final Properties ctx = getProcessInfo().getCtx();
		return printingQueueBL.createPrintingQueueSources(ctx, printingQuery);
	}
}
