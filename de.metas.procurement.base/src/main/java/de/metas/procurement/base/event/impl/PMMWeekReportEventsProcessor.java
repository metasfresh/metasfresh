package de.metas.procurement.base.event.impl;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.procurement.base.model.I_PMM_WeekReport_Event;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMMWeekReportEventsProcessor
{
	public static final PMMWeekReportEventsProcessor newInstance()
	{
		return new PMMWeekReportEventsProcessor();
	}

	private final transient ITrxItemProcessorExecutorService trxItemProcessorExecutorService = Services.get(ITrxItemProcessorExecutorService.class);

	private PMMWeekReportEventsProcessor()
	{
		super();
	}

	public void processAll()
	{
		final Properties ctx = Env.getCtx();
		final EventSource<I_PMM_WeekReport_Event> events = new EventSource<>(ctx, I_PMM_WeekReport_Event.class);
		final PMMWeekReportEventTrxItemProcessor itemProcessor = new PMMWeekReportEventTrxItemProcessor();

		trxItemProcessorExecutorService.<I_PMM_WeekReport_Event, Void> createExecutor()
				.setContext(ctx, ITrx.TRXNAME_ThreadInherited)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(itemProcessor)
				.process(events);

		Loggables.get().addLog(itemProcessor.getProcessSummary());
	}

}
