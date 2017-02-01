package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBForeignKeyConstraintException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.logging.LogManager;

public class M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final transient Logger logger = LogManager.getLogger(M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor.class);

	@Override
	public boolean isRunInTransaction()
	{
		// run this out of transaction. We will manage the transaction by our selfs
		return false;
	}

	public static void createWorkpackage(final I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");

		//
		// In case we are running unit tests, we want to generate the planning HUs right away.
		if (Adempiere.isUnitTestMode())
		{
			new M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor().generatePlanningHUs(receiptSchedule);
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(receiptSchedule);

		final IWorkPackageQueueFactory workpackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
		final IWorkPackageQueue queue = workpackageQueueFactory.getQueueForEnqueuing(ctx, M_ReceiptSchedule_GeneratePlanningHUs_WorkpackageProcessor.class);

		//
		// Create an enqueue workpackage
		// Mark the workpackage as ready for processing when receipt schedule's transaction is committed,
		// because before that, it could be that the receipt schedule is not available
		final String trxName = InterfaceWrapperHelper.getTrxName(receiptSchedule);
		queue.newBlock()
				.setContext(ctx)
				.newWorkpackage()
				.addElement(receiptSchedule)
				.bindToTrxName(trxName)
				.build();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final ILoggable loggable = Services.get(IWorkPackageBL.class).createLoggable(workpackage);

		// maybe the underlying receipt schedule was deleted meanwhile, when the order was reactivated. Using retrieveItemsSkipMissing() because we don't need to make a fuss about that.
		final List<I_M_ReceiptSchedule> receiptSchedules = queueDAO.retrieveItemsSkipMissing(workpackage, I_M_ReceiptSchedule.class, ITrx.TRXNAME_None);
		loggable.addLog("Retrieved " + receiptSchedules.size() + " M_ReceiptSchedules");

		for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
		{
			generatePlanningHUs(receiptSchedule);
		}
		return Result.SUCCESS;
	}

	/**
	 * Generate it's LU-TU structure automatically, but don't do any transaction commits
	 *
	 * @param schedule
	 */
	private void generatePlanningHUs(final I_M_ReceiptSchedule schedule)
	{
		//
		// Skip Receipt schedules which are about Packing Materials
		if (schedule.isPackagingMaterial())
		{
			return;
		}

		//
		// Skip Receipt schedules which were already prepared (i.e. HUs were generated)
		if (schedule.isHUPrepared())
		{
			return;
		}

		try
		{
			ReceiptScheduleHUGenerator.newInstance(InterfaceWrapperHelper.getContextAware(schedule))
					.addM_ReceiptSchedule(schedule)
					.generateAllPlanningHUs_InChunks();
		}
		catch (final DBForeignKeyConstraintException e)
		{
			// task 09016: this case happens from time to time (aprox. 90 times in the first 6 months), if the M_ReceiptsScheulde is deleted due to an order reactivation
			// don't rethrow the exception;
			final String msg = "Detected a FK constraint vialoation; We assume that everything was rolled back, but we do not let the processing fail. Check the java comments for details";
			Loggables.get().withLogger(logger, Level.WARN).addLog(msg);
		}
	}

}
