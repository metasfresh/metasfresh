package de.metas.inoutcandidate.process;

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


import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.Query;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/**
 * Processes all
 * 
 * @author ts
 * @task 08648
 *
 */
public class M_ReceiptSchedule_Generate_M_InOuts extends JavaProcess
{

	private static final transient Logger logger = LogManager.getLogger(M_ReceiptSchedule_Generate_M_InOuts.class);

	private static final String PARA_IS_CREATE_MOVEMENT = "IsCreateMovement";
	private static final String PARA_DATE_TO = "DateTo";
	private static final String PARA_DATE_FROM = "DateFrom";
	private static final String PARA_M_WAREHOUSE_ID = "M_Warehouse_ID";

	private final ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private int warehouseId;
	private Timestamp dateFrom;
	private Timestamp dateTo;
	private boolean isCreateMovement;

	@Override
	protected void prepare()
	{
		final IParams params = getParameterAsIParams();

		warehouseId = params.getParameterAsInt(PARA_M_WAREHOUSE_ID);
		dateFrom = params.getParameterAsTimestamp(PARA_DATE_FROM);
		dateTo = params.getParameterAsTimestamp(PARA_DATE_TO);
		isCreateMovement = params.getParameterAsBool(PARA_IS_CREATE_MOVEMENT);
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_M_ReceiptSchedule> receiptScheds = createIterator();

		final Mutable<Integer> counter = new Mutable<Integer>(0);

		trxItemProcessorExecutorService
				.<I_M_ReceiptSchedule, Void> createExecutor()
				.setContext(getCtx(), ITrx.TRXNAME_None) // this processor shall run with a local transaction, so there will be a commit after each item (i.e. in this case each chunk)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(new TrxItemProcessorAdapter<I_M_ReceiptSchedule, Void>()
				{
					@Override
					public void process(final I_M_ReceiptSchedule item) throws Exception
					{
						processReceiptSchedule0(item);

						counter.setValue(counter.getValue() + 1);
						if (counter.getValue() % 100 == 0)
						{
							logger.warn("Processed " + counter.getValue() + " M_ReceiptSchedules");
						}
					}
				}).process(receiptScheds);

		return "@Success@: @Processed@ " + counter.getValue() + " @M_ReceiptSchedule_ID@";
	}

	private Iterator<I_M_ReceiptSchedule> createIterator()
	{
		// in case we were called from the window, then only process the current selection
		final IQueryFilter<I_M_ReceiptSchedule> processInfoFilter = getProcessInfo().getQueryFilter();

		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class, getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addOnlyActiveRecordsFilter()
				.filter(processInfoFilter);
		if (warehouseId > 0)
		{
			queryBuilder.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID, warehouseId);
		}
		if (dateFrom != null)
		{
			queryBuilder.addCompareFilter(I_M_ReceiptSchedule.COLUMNNAME_DateOrdered, Operator.GREATER_OR_EQUAL, dateFrom);
		}
		if (dateTo != null)
		{
			queryBuilder.addCompareFilter(I_M_ReceiptSchedule.COLUMNNAME_DateOrdered, Operator.LESS_OR_EQUAL, dateTo);
		}

		// return them roughly chronologically
		queryBuilder
				.orderBy()
				.addColumn(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);

		return queryBuilder
				.create()
				.setOption(Query.OPTION_GuaranteedIteratorRequired, true) // just to be sure
				.setOption(Query.OPTION_IteratorBufferSize, 1000)
				.iterate(I_M_ReceiptSchedule.class);
	}

	private void processReceiptSchedule0(final I_M_ReceiptSchedule item)
	{
		final ITrx trx = Services.get(ITrxManager.class).getTrx(ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.setTrxName(item, trx.getTrxName()); // this should be not neccesary anymore after we merged uat_, because there we are temporarily setting the item's trx to the
																	// processor's trx for the time of the processing.

		if (isReceiptScheduleCanBeClosed(item))
		{
			receiptScheduleBL.close(item);
			addLog("Closing M_ReceiptSchedule " + item + " without having created a receipt, because QtyOrdered=" + item.getQtyOrdered() + " and QtyMoved=" + item.getQtyMoved());
			return;
		}

		if (!item.isHUPrepared())
		{
			addLog("Skipping M_ReceiptSchedule " + item + " because of HUPrepared=N");
			return;
		}

		if (!isCreateMovement)
		{
			item.setM_Warehouse_Dest_ID(0);
			InterfaceWrapperHelper.save(item);
		}

		final boolean storeReceipts = true;

		// Create result collector
		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createInOutGenerateResult(storeReceipts); // referenceReceipts

		// Create Receipt producer
		final Set<Integer> selectedHUIds = null; // null means to assign all planned HUs which are assigned to the receipt schedule's
		final boolean createReceiptWithDatePromised = true; // when creating M_InOuts, use the respective C_Orders' DatePromised values
		final IInOutProducer producer = huReceiptScheduleBL.createInOutProducerFromReceiptScheduleHU(getCtx(), result, selectedHUIds, createReceiptWithDatePromised);

		// Create executor and generate receipts with it
		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(getCtx(), trx); // run with the trx that is inherited from the outer item processor.
		executorService.createExecutor(processorCtx, producer)
				//
				// Configure executor to fail on first error
				// NOTE: we expect max. 1 receipt, so it's fine to fail anyways
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				// Process schedules => receipt(s) will be generated
				.execute(IteratorUtils.singletonIterator(item));
		InterfaceWrapperHelper.refresh(item);
		final boolean rsCanBeClosedNow = isReceiptScheduleCanBeClosed(item);
		if (rsCanBeClosedNow)
		{
			receiptScheduleBL.close(item);
		}
	}

	private boolean isReceiptScheduleCanBeClosed(final I_M_ReceiptSchedule receiptSchedule)
	{
		final boolean rsCanBeClosedNow = receiptSchedule.getQtyOrdered().signum() > 0 && receiptSchedule.getQtyMoved().compareTo(receiptSchedule.getQtyOrdered()) >= 0;
		return rsCanBeClosedNow;
	}

}
