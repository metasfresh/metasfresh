package de.metas.fresh.mrp_productinfo.process;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.api.LoggableTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoBL;
import de.metas.process.Param;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Optionally runs the DB-Function {@value #DB_FUNCTION_X_MRP_PRODUCT_INFO_DETAIL_INSERT_FALLBACK} (depenting on the process parameter <code>FirstCreateFallBack</code>).<br>
 * Then selects all {@link I_X_MRP_ProductInfo_Detail_MV} records for a certain date, and updates them using {@link IMRPProductInfoBL#updateItems(org.adempiere.model.IContextAware, java.util.List, org.adempiere.util.api.IParams)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/213
 */
public class X_MRP_ProductInfo_Detail_MV_Update_Records extends JavaProcess
{
	public static final String DB_FUNCTION_X_MRP_PRODUCT_INFO_DETAIL_INSERT_FALLBACK = "X_MRP_ProductInfo_Detail_Insert_Fallback";

	private final transient ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(mandatory = false, parameterName = I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_DateGeneral)
	private Timestamp p_dateGeneral;

	@Param(mandatory = false, parameterName = "DaysAhead")
	private int daysAhead;

	@Param(mandatory = true, parameterName = "FirstCreateFallBack")
	private boolean firstCreateFallBack = false;

	@Override
	protected String doIt() throws Exception
	{
		final Timestamp dateGeneral = getDateGeneralToUse();

		if (firstCreateFallBack)
		{
			addLog("Calling X_MRP_ProductInfo_Detail_Insert_Fallback with date={}", dateGeneral);
			DB.executeFunctionCallEx(getTrxName(), "select " + DB_FUNCTION_X_MRP_PRODUCT_INFO_DETAIL_INSERT_FALLBACK + "( ? )",
					new Object[] { dateGeneral });
			addLog("Done calling X_MRP_ProductInfo_Detail_Insert_Fallback");
		}

		addLog("Retrieving and updating I_X_MRP_ProductInfo_Detail_MV with dateGeneral={}", dateGeneral);
		final Iterator<I_X_MRP_ProductInfo_Detail_MV> itemsToUpdate = queryBL
				.createQueryBuilder(I_X_MRP_ProductInfo_Detail_MV.class, this)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_DateGeneral, dateGeneral)
				.orderBy().addColumn(I_X_MRP_ProductInfo_Detail_MV.COLUMNNAME_X_MRP_ProductInfo_Detail_MV_ID)
				.endOrderBy()
				.create()
				.iterate(I_X_MRP_ProductInfo_Detail_MV.class);

		final int processedRecords = trxItemProcessorExecutorService.<I_X_MRP_ProductInfo_Detail_MV, Integer> createExecutor()
				.setContext(getCtx(), getTrxName())
				.setProcessor(new TrxItemProcessorAdapter<I_X_MRP_ProductInfo_Detail_MV, Integer>()
				{
					private int counter = 0;

					@Override
					public void process(final I_X_MRP_ProductInfo_Detail_MV item) throws Exception
					{
						final IMRPProductInfoBL mrpProductInfoBL = Services.get(IMRPProductInfoBL.class);
						mrpProductInfoBL.updateItems(
								PlainContextAware.newWithTrxName(getCtx(), getTrxName()), 
								Collections.singletonList(item));
						counter++;
					}

					@Override
					public Integer getResult()
					{
						return counter;
					}
				})
				.setItemsPerBatch(50)
				.setOnItemErrorPolicy(ITrxItemExecutorBuilder.OnItemErrorPolicy.ContinueChunkAndCommit)
				.setExceptionHandler(LoggableTrxItemExceptionHandler.instance)
				.process(itemsToUpdate);

		addLog("Done retrieving and updating {} I_X_MRP_ProductInfo_Detail_MV records", processedRecords);
		return MSG_OK;
	}

	private Timestamp getDateGeneralToUse()
	{
		final Timestamp dateGeneral = p_dateGeneral != null
				? p_dateGeneral
				: SystemTime.asDayTimestamp();

		return TimeUtil.addDays(dateGeneral, daysAhead);
	}

}
