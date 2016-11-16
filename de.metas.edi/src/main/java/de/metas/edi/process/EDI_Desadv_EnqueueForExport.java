package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

import de.metas.adempiere.form.IClientUI;
import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.X_EDI_Desadv;
import de.metas.process.RunOutOfTrx;
import de.metas.process.SvrProcess;

/**
 * Send EDI documents for selected desadv entries.
 *
 * @task 08646
 *
 */
public class EDI_Desadv_EnqueueForExport extends SvrProcess
{
	private static final String MSG_DESADV_PerformEnqueuing = "DESADV_PerformEnqueuing";
	private static final String MSG_EDI_DESADV_RefuseSending = "EDI_DESADV_RefuseSending";

	/**
	 * Minimum Sum Percentage set in the sys config 'de.metas.esb.edi.DefaultMinimumPercentage'
	 */
	private final BigDecimal minimumSumPercentage = Services.get(IDesadvDAO.class).retrieveMinimumSumPercentage();

	private final List<I_EDI_Desadv> desadvsToSkip = new ArrayList<I_EDI_Desadv>();

	final IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		checkPerformEnqueuing();
	}

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();

		final IWorkPackageQueue queue = workPackageQueueFactory.getQueueForEnqueuing(ctx, EDIWorkpackageProcessor.class);

		// final I_C_Queue_Block block = queue.enqueueBlock(ctx);
		final IWorkPackageBlockBuilder builder = queue.newBlock()
				.setAD_PInstance_Creator_ID(getAD_PInstance_ID())
				.setContext(getCtx());

		// Enqueue selected desadvs as workpackages
		final Iterator<I_EDI_Desadv> desadvs = createIterator();

		trxItemProcessorExecutorService
				.<I_EDI_Desadv, Void> createExecutor()
				.setContext(getCtx(), ITrx.TRXNAME_None)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_EDI_Desadv, Void>()
				{
					@Override
					public void process(final I_EDI_Desadv desadv) throws Exception
					{
						// make sure the desadvs that don't meet the sum percentage requirement won't get enqueued
						final BigDecimal currentSumPercentage = desadv.getEDI_DESADV_SumPercentage();
						if (currentSumPercentage.compareTo(minimumSumPercentage) < 0)
						{
							desadvsToSkip.add(desadv);
						}
						else
						{
							// note: in here, the desadv has the item processor's trxName (as of this task)
							enqueueDesadv0(builder, desadv);
						}
					}
				})
				.process(desadvs);

		builder.build(); // in case every single desadv was skipped, store our empty block now, just for reference and suppord.

		// display the desadvs that didn't meet the sum percentage requirement
		if (!desadvsToSkip.isEmpty())
		{
			logSkippedLines();
		}

		return "Success";
	}

	private void logSkippedLines()
	{

		final StringBuilder skippedDesadvsString = new StringBuilder();

		for (final I_EDI_Desadv desadv : desadvsToSkip)
		{
			skippedDesadvsString.append("#")
					.append(desadv.getDocumentNo())
					.append(" - ")
					.append(desadv.getEDI_DESADV_SumPercentage())
					.append("\n");
		}

		// log a message that includes all the skipped lines'documentNo and percentage
		final String logMessage = Services.get(IMsgBL.class).getMsg(getCtx(),
				MSG_EDI_DESADV_RefuseSending,
				new Object[] { minimumSumPercentage, skippedDesadvsString.toString() });

		addLog(logMessage);
	}

	private void enqueueDesadv0(
			final IWorkPackageBlockBuilder builder,
			final I_EDI_Desadv desadv)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(desadv);

		builder.newWorkpackage()
				.bindToTrxName(trxName)
				.addElement(desadv)
				.build();

		desadv.setEDI_ExportStatus(X_EDI_Desadv.EDI_EXPORTSTATUS_Enqueued);
		InterfaceWrapperHelper.save(desadv);
	}

	private IQueryBuilder<I_EDI_Desadv> createEDIDesadvQueryBuilder()
	{
		final IQueryFilter<I_EDI_Desadv> processQueryFilter = getProcessInfo().getQueryFilter();

		final IQueryBuilder<I_EDI_Desadv> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus, X_EDI_Desadv.EDI_EXPORTSTATUS_Error, X_EDI_Desadv.EDI_EXPORTSTATUS_Pending)
				.filter(processQueryFilter);

		queryBuilder.orderBy()
				.addColumn(I_EDI_Desadv.COLUMNNAME_POReference)
				.addColumn(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID);

		return queryBuilder;
	}

	/**
	 * Ask the user if he really wants to enqueue the {@link I_EDI_Desadv} documents even if they contain lines with qty delivered 0
	 *
	 * @task 08961
	 * @throws ProcessCanceledException if user canceled
	 */
	private void checkPerformEnqueuing() throws ProcessCanceledException
	{
		// total number of desadv entries containing lines with qtydelivered = 0
		final int counterQty0 = countDESADVWithLinesQty0();
		if (counterQty0 <= 0)
		{
			// don't ask
			return;
		}

		// total number of desadv entries in selection
		final int totalCounter = countTotalLines();

		final boolean performEnqueuing = Services.get(IClientUI.class).ask()
				.setParentWindowNo(getProcessInfo().getWindowNo())
				.setAD_Message(MSG_DESADV_PerformEnqueuing, totalCounter, counterQty0)
				.setDefaultAnswer(false)
				.getAnswer();
		if (!performEnqueuing)
		{
			throw new ProcessCanceledException();
		}

	}

	private Iterator<I_EDI_Desadv> createIterator()
	{
		final IQueryBuilder<I_EDI_Desadv> queryBuilder = createEDIDesadvQueryBuilder();

		return queryBuilder
				.create()
				.iterate(I_EDI_Desadv.class);
	}

	private int countTotalLines()
	{
		return createEDIDesadvQueryBuilder().create().count();
	}

	/**
	 * Returns the number of desadv records that have at least one line with qty 0.
	 *
	 * @return
	 */
	private int countDESADVWithLinesQty0()
	{
		return createEDIDesadvQueryBuilder()
				.andCollectChildren(I_EDI_DesadvLine.COLUMN_EDI_Desadv_ID, I_EDI_DesadvLine.class)
				.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInUOM, BigDecimal.ZERO)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_EDI_Desadv.COLUMN_EDI_Desadv_ID, I_EDI_Desadv.class)
				.create()
				.count();
	}
}
