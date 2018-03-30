package de.metas.inoutcandidate.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.adempiere.exceptions.DBForeignKeyConstraintException;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.Mutable;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.Query;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.quantity.Quantity;

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

		final Mutable<Integer> counter = new Mutable<>(0);

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

	private void processReceiptSchedule0(final I_M_ReceiptSchedule receiptSchedule)
	{
		if (isReceiptScheduleCanBeClosed(receiptSchedule))
		{
			receiptScheduleBL.close(receiptSchedule);
			addLog("M_ReceiptSchedule_ID={} - just closing without creating a receipt, because QtyOrdered={} and QtyMoved={}",
					receiptSchedule.getM_ReceiptSchedule_ID(), receiptSchedule.getQtyOrdered(), receiptSchedule.getQtyMoved());
			return;
		}

		if (!isCreateMovement)
		{
			receiptSchedule.setM_Warehouse_Dest_ID(0);
			save(receiptSchedule);
		}

		// create HUs
		final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);
		generateHUsIfNeeded(huReceiptSchedule);

		// Create result collector
		final boolean storeReceipts = true;
		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(storeReceipts);

		// Create Receipt producer
		final Set<Integer> selectedHUIds = null; // null means to assign all planned HUs which are assigned to the receipt schedule's
		final boolean createReceiptWithDatePromised = true; // when creating M_InOuts, use the respective C_Orders' DatePromised values
		final IInOutProducer producer = huReceiptScheduleBL.createInOutProducerFromReceiptScheduleHU(getCtx(), result, selectedHUIds, createReceiptWithDatePromised);

		// Create executor and generate receipts with it
		final ITrx trx = Services.get(ITrxManager.class).getTrx(ITrx.TRXNAME_ThreadInherited);
		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(getCtx(), trx); // run with the trx that is inherited from the outer item processor.

		Services.get(ITrxItemProcessorExecutorService.class).<I_M_ReceiptSchedule, InOutGenerateResult> createExecutor()
				.setContext(processorCtx)
				.setProcessor(producer)
				// Configure executor to fail on first error
				// NOTE: we expect max. 1 receipt, so it's fine to fail anyways
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				// Process schedules => receipt(s) will be generated
				.process(IteratorUtils.singletonIterator(receiptSchedule));

		addLog("M_ReceiptSchedule_ID={} - created a receipt; result={}", receiptSchedule.getM_ReceiptSchedule_ID(), result);

		refresh(receiptSchedule);
		final boolean rsCanBeClosedNow = isReceiptScheduleCanBeClosed(receiptSchedule);
		if (rsCanBeClosedNow)
		{
			receiptScheduleBL.close(receiptSchedule);
		}
	}

	private boolean isReceiptScheduleCanBeClosed(final I_M_ReceiptSchedule receiptSchedule)
	{
		final boolean rsCanBeClosedNow = receiptSchedule.getQtyOrdered().signum() > 0 && receiptSchedule.getQtyMoved().compareTo(receiptSchedule.getQtyOrdered()) >= 0;
		return rsCanBeClosedNow;
	}

	/**
	 * Generate it's LU-TU structure automatically
	 */
	private void generateHUsIfNeeded(final de.metas.handlingunits.model.I_M_ReceiptSchedule receiptSchedule)
	{
		// Skip Receipt schedules which are about Packing Materials
		if (receiptSchedule.isPackagingMaterial())
		{
			return;
		}

		final IHUReceiptScheduleDAO huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
		final List<I_M_ReceiptSchedule_Alloc> allocsAll = huReceiptScheduleDAO.retrieveHandlingUnitAllocations(receiptSchedule, ITrx.TRXNAME_ThreadInherited);
		if (!allocsAll.isEmpty())
		{
			addLog("M_ReceiptSchedule_ID={} - already has HUs assinged to it; not creating HUs", receiptSchedule.getM_ReceiptSchedule_ID());
		}

		try
		{
			addLog("M_ReceiptSchedule_ID={} - creating HUs and allocating HUs on the fly", receiptSchedule.getM_ReceiptSchedule_ID());

			final ReceiptScheduleHUGenerator huGenerator =//
					ReceiptScheduleHUGenerator.newInstance(getContextAware(receiptSchedule));

			huGenerator.addM_ReceiptSchedule(receiptSchedule);

			final I_M_HU_LUTU_Configuration lutuConfig = Services.get(IHUReceiptScheduleBL.class)
					.createLUTUConfigurationManager(receiptSchedule)
					.getCreateLUTUConfiguration();
			save(lutuConfig);
			huGenerator.setM_HU_LUTU_Configuration(lutuConfig);

			final BigDecimal qtyToAllocate = Services.get(IReceiptScheduleBL.class)
					.getQtyOrdered(receiptSchedule);

			huGenerator
					.setQtyToAllocateTarget(Quantity.of(qtyToAllocate, receiptSchedule.getC_UOM()))
					.generateWithinInheritedTransaction();
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
