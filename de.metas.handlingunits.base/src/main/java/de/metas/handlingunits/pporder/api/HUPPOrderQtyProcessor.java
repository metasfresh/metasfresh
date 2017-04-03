package de.metas.handlingunits.pporder.api;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderBOMBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.api.impl.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Processes given {@link I_PP_Order_Qty}.
 *
 * By default, {@link #process()} will process each record in it's own transaction/savepoint.
 *
 * Processing one {@link I_PP_Order_Qty} means:
 * <ul>
 * <li>generate cost collector
 * <li>link the HU to cost collector
 * <li>in case of material receipt activate the HU
 * <li>in case of material issue: TODO
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUPPOrderQtyProcessor
{
	public static final HUPPOrderQtyProcessor newInstance()
	{
		return new HUPPOrderQtyProcessor();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(HUPPOrderQtyProcessor.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxItemProcessorExecutorService trxItemProcessorService = Services.get(ITrxItemProcessorExecutorService.class);
	//
	private final transient IHUPPCostCollectorBL huPPCostCollectorBL = Services.get(IHUPPCostCollectorBL.class);
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

	private static final Supplier<List<I_PP_Order_Qty>> RecordsToProcessSupplier_NONE = () -> ImmutableList.of();
	private Supplier<List<I_PP_Order_Qty>> recordsToProcessSupplier = RecordsToProcessSupplier_NONE;

	private HUPPOrderQtyProcessor()
	{
	}

	public void process()
	{
		trxItemProcessorService.<I_PP_Order_Qty, Void> createExecutor()
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setProcessor(this::processRecord)
				.process(getRecordsToProcess().iterator());
	}

	private void processRecord(final I_PP_Order_Qty record)
	{
		if (record.isProcessed())
		{
			logger.debug("Skip processing {} because it was already processed", record);
			return;
		}

		InterfaceWrapperHelper.setThreadInheritedTrxName(record); // just to be sure

		if (isMaterialReceipt(record))
		{
			processMaterialReceipt(record);
		}
		else
		{
			processMaterialIssue(record);
		}
	}

	private boolean isMaterialReceipt(final I_PP_Order_Qty record)
	{
		final I_PP_Order_BOMLine ppOrderBOMLine = record.getPP_Order_BOMLine();
		return ppOrderBOMLine == null || ppOrderBOMBL.isReceipt(ppOrderBOMLine);
	}

	private void processMaterialReceipt(final I_PP_Order_Qty record)
	{
		final I_M_HU hu = record.getM_HU();
		Preconditions.checkNotNull(hu, "No HU for %s", record);

		//
		// Create material receipt and activate the HU
		final IReceiptCostCollectorCandidate candidate = ReceiptCostCollectorCandidate.builder()
				.PP_Order(record.getPP_Order())
				.PP_Order_BOMLine(record.getPP_Order_BOMLine())
				.movementDate(record.getMovementDate())
				.qtyToReceive(record.getQty())
				.M_Product(record.getM_Product())
				.C_UOM(record.getC_UOM())
				.M_Locator_ID(record.getM_Locator_ID())
				.build();
		final I_PP_Cost_Collector cc = huPPCostCollectorBL.createReceipt(candidate, hu);

		//
		// Update the (planning) qty record
		record.setPP_Cost_Collector(cc);
		record.setProcessed(true);
		InterfaceWrapperHelper.save(record);
	}

	private void processMaterialIssue(final I_PP_Order_Qty record)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public HUPPOrderQtyProcessor setRecordsToProcess(final Supplier<List<I_PP_Order_Qty>> recordsToProcessSupplier)
	{
		Preconditions.checkNotNull(recordsToProcessSupplier, "recordsToProcessSupplier");
		this.recordsToProcessSupplier = recordsToProcessSupplier;
		return this;
	}

	public HUPPOrderQtyProcessor setRecordsToProcessByPPOrderQtyId(final Collection<Integer> ppOrderQtyIds)
	{
		if (ppOrderQtyIds == null || ppOrderQtyIds.isEmpty())
		{
			setRecordsToProcess(RecordsToProcessSupplier_NONE);
		}
		else
		{
			setRecordsToProcess(() -> queryBL.createQueryBuilder(I_PP_Order_Qty.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
					.addInArrayFilter(I_PP_Order_Qty.COLUMN_PP_Order_Qty_ID, ppOrderQtyIds)
					.addEqualsFilter(I_PP_Order_Qty.COLUMN_Processed, false) // not already processed
					.addOnlyActiveRecordsFilter()
					.create()
					.listImmutable(I_PP_Order_Qty.class));
		}

		return this;
	}

	private List<I_PP_Order_Qty> getRecordsToProcess()
	{
		return recordsToProcessSupplier.get();
	}
}
