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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.process.export.enqueue.DesadvEnqueuer;
import de.metas.edi.process.export.enqueue.EnqueueDesadvRequest;
import de.metas.edi.process.export.enqueue.EnqueueDesadvResult;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.X_EDI_Desadv;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/**
 * Send EDI documents for selected desadv entries.
 *
 * @task 08646
 *
 */
public class EDI_Desadv_EnqueueForExport extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);


	private final DesadvEnqueuer desadvEnqueuer = SpringContextHolder.instance.getBean(DesadvEnqueuer.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (sysConfigBL.getBooleanValue(EDIWorkpackageProcessor.SYS_CONFIG_OneDesadvPerShipment, false))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		checkPerformEnqueuing();
	}

	@Override
	protected String doIt() throws Exception
	{
		// Enqueue selected desadvs as workpackages
		final EnqueueDesadvRequest enqueueDesadvRequest = EnqueueDesadvRequest.builder()
				.pInstanceId(getPinstanceId())
				.ctx(getCtx())
				.desadvIterator(createIterator())
				.build();

		final EnqueueDesadvResult result = desadvEnqueuer.enqueue(enqueueDesadvRequest);

		final List<I_EDI_Desadv> skippedDesadvList = result.getSkippedDesadvList();

		// display the desadvs that didn't meet the sum percentage requirement
		if (!skippedDesadvList.isEmpty())
		{
			desadvBL.createMsgsForDesadvsBelowMinimumFulfilment(ImmutableList.copyOf(skippedDesadvList));
		}

		return MSG_OK;
	}

	private IQueryBuilder<I_EDI_Desadv> createEDIDesadvQueryBuilder()
	{
		final IQueryFilter<I_EDI_Desadv> processQueryFilter = getProcessInfo().getQueryFilterOrElseFalse();

		final IQueryBuilder<I_EDI_Desadv> queryBuilder = queryBL.createQueryBuilder(I_EDI_Desadv.class, getCtx(), get_TrxName())
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus, X_EDI_Desadv.EDI_EXPORTSTATUS_Error, X_EDI_Desadv.EDI_EXPORTSTATUS_Pending)
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
	}

	private Iterator<I_EDI_Desadv> createIterator()
	{
		final IQueryBuilder<I_EDI_Desadv> queryBuilder = createEDIDesadvQueryBuilder();

		return queryBuilder
				.create()
				.iterate(I_EDI_Desadv.class);
	}

	/**
	 * Returns the number of desadv records that have at least one line with qty 0.
	 */
	private int countDESADVWithLinesQty0()
	{
		return createEDIDesadvQueryBuilder()
				.andCollectChildren(I_EDI_DesadvLine.COLUMN_EDI_Desadv_ID, I_EDI_DesadvLine.class)
				.addEqualsFilter(I_EDI_DesadvLine.COLUMNNAME_QtyDeliveredInStockingUOM, BigDecimal.ZERO)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_EDI_Desadv.COLUMN_EDI_Desadv_ID, I_EDI_Desadv.class)
				.create()
				.count();
	}
}
