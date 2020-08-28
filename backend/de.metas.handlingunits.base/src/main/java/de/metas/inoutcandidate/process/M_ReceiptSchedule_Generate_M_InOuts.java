package de.metas.inoutcandidate.process;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Iterator;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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

		warehouseId = params.getParameterAsInt(PARA_M_WAREHOUSE_ID, -1);
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
							Loggables.withLogger(logger, Level.DEBUG).addLog("Processed {} M_ReceiptSchedules", counter.getValue());
						}
					}
				}).process(receiptScheds);

		return "@Success@: @Processed@ " + counter.getValue() + " @M_ReceiptSchedule_ID@";
	}

	private Iterator<I_M_ReceiptSchedule> createIterator()
	{
		// in case we were called from the window, then only process the current selection
		final IQueryFilter<I_M_ReceiptSchedule> processInfoFilter = getProcessInfo().getQueryFilterOrElseTrue();

		// only process records with an effective qty > 0
		final ICompositeQueryFilter<I_M_ReceiptSchedule> overrideQtyFilter = queryBL
				.createCompositeQueryFilter(I_M_ReceiptSchedule.class)
				.addCompareFilter(I_M_ReceiptSchedule.COLUMNNAME_QtyToMove_Override, Operator.GREATER, ZERO);
		final ICompositeQueryFilter<I_M_ReceiptSchedule> qtyFilter = queryBL
				.createCompositeQueryFilter(I_M_ReceiptSchedule.class)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_QtyToMove_Override, null)
				.addCompareFilter(I_M_ReceiptSchedule.COLUMNNAME_QtyToMove, Operator.GREATER, ZERO);
		final ICompositeQueryFilter<I_M_ReceiptSchedule> effectiveQtyFilter = queryBL
				.createCompositeQueryFilter(I_M_ReceiptSchedule.class)
				.setJoinOr()
				.addFilter(overrideQtyFilter)
				.addFilter(qtyFilter);

		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class, getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.filter(processInfoFilter)
				.filter(effectiveQtyFilter);
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
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // just to be sure
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_M_ReceiptSchedule.class);
	}

	private void processReceiptSchedule0(final I_M_ReceiptSchedule receiptSchedule)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		if (isReceiptScheduleCanBeClosed(receiptSchedule))
		{
			receiptScheduleBL.close(receiptSchedule);
			loggable.addLog("M_ReceiptSchedule_ID={} - just closing without creating a receipt, because QtyOrdered={} and QtyMoved={}",
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
		huReceiptScheduleBL.generateHUsIfNeeded(huReceiptSchedule, getCtx());

		final CreateReceiptsParameters parameters = CreateReceiptsParameters.builder()
				.ctx(getCtx())
				.selectedHuIds(null) // null means to assign all planned HUs which are assigned to the receipt schedule's
				.movementDateRule(ReceiptMovementDateRule.ORDER_DATE_PROMISED) // when creating M_InOuts, use the respective C_Orders' DatePromised values
				.commitEachReceiptIndividually(true)
				.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
				.receiptSchedules(ImmutableList.of(receiptSchedule))
				.printReceiptLabels(false)
				.build();

		final InOutGenerateResult result = huReceiptScheduleBL.processReceiptSchedules(parameters);

		loggable.addLog("M_ReceiptSchedule_ID={} - created a receipt; result={}", receiptSchedule.getM_ReceiptSchedule_ID(), result);

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
}
