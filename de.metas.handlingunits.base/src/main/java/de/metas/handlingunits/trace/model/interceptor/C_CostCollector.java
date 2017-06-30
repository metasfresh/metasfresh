package de.metas.handlingunits.trace.model.interceptor;

import java.util.stream.Stream;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPCostCollectorBL;

import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEvent.HUTraceEventBuilder;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.handlingunits.trace.HUTraceUtil;
import lombok.NonNull;

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
@Interceptor(I_PP_Cost_Collector.class)
public class C_CostCollector
{

	@DocValidate(timings =
		{
				ModelValidator.TIMING_AFTER_CLOSE,
				ModelValidator.TIMING_AFTER_COMPLETE,
				ModelValidator.TIMING_AFTER_REACTIVATE,
				ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
				ModelValidator.TIMING_AFTER_REVERSECORRECT,
				ModelValidator.TIMING_AFTER_UNCLOSE,
				ModelValidator.TIMING_AFTER_VOID
		}, afterCommit = true)
	public void addTraceEvent(@NonNull final I_PP_Cost_Collector costCollector)
	{
		final HUTraceEventBuilder builder = HUTraceEvent.builder()
				.costCollectorId(costCollector.getPP_Order_ID())
				.docTypeId(costCollector.getC_DocType_ID())
				.docStatus(costCollector.getDocStatus());

		final IPPCostCollectorBL costCollectorBL = Services.get(IPPCostCollectorBL.class);
		if (costCollectorBL.isMaterialIssue(costCollector, true))
		{
			builder.type(HUTraceType.PRODUCTION_ISSUE);
		}
		else
		{
			builder.type(HUTraceType.PRODUCTION_RECEIPT);
		}
		HUTraceUtil.createAndAddEvents(builder, Stream.of(costCollector));
	}
}
