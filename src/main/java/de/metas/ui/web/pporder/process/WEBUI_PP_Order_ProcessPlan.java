package de.metas.ui.web.pporder.process;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;

import de.metas.handlingunits.pporder.api.HUPPOrderQtyProcessor;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLineRow;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_PP_Order_ProcessPlan
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!streamRowsToProcess().anyMatch(row -> true))
		{
			return ProcessPreconditionsResolution.reject("Nothing to process");
		}
		return ProcessPreconditionsResolution.accept();
	}

	private Stream<PPOrderLineRow> streamRowsToProcess()
	{
		return getView()
				.streamAllRecursive()
				.filter(row -> !row.isProcessed())
				.filter(row -> row.getPP_Order_Qty_ID() > 0);
	}

	@Override
	protected String doIt() throws Exception
	{
		final int ppOrderId = getView().getPP_Order_ID();
		final List<Integer> ppOrderQtyIds = streamRowsToProcess()
				.map(row -> row.getPP_Order_Qty_ID())
				.collect(GuavaCollectors.toImmutableList());

		HUPPOrderQtyProcessor.newInstance()
				.setRecordsToProcessByPPOrderId(ppOrderId, ppOrderQtyIds)
				.process();

		getView().invalidateAll();
		return MSG_OK;
	}

}
