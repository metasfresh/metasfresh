package de.metas.ui.web.pporder.util;

import java.util.List;

import org.adempiere.util.StringUtils;
import org.eevolution.model.I_PP_Order;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLineRow;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class WEBUI_PP_Order_ProcessHelper
{

	public final ProcessPreconditionsResolution checkPreconditionsApplicable(	final PPOrderLineRow ppOrderLineRow)
	{		
		if (!ppOrderLineRow.isIssue())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is not an issue-row; row={}", ppOrderLineRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}
		if (ppOrderLineRow.isProcessed())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is already processed; row={}", ppOrderLineRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		final List<I_M_Source_HU> sourceHus = retrieveActiveSourceHus(ppOrderLineRow);
		if (sourceHus.isEmpty())
		{
			final String internalReason = StringUtils.formatMessage("There are no sourceHU records for the selected row; row={}", ppOrderLineRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}
		return ProcessPreconditionsResolution.accept();
	}
	

	public static List<I_M_Source_HU> retrieveActiveSourceHus(@NonNull final PPOrderLineRow row)
	{
		final I_PP_Order ppOrder = load(row.getPP_Order_ID(), I_PP_Order.class);

		final MatchingSourceHusQuery query = MatchingSourceHusQuery.builder()
				.productId(row.getM_Product_ID())
				.warehouseId(ppOrder.getM_Warehouse_ID()).build();
		return SourceHUsService.get().retrieveMatchingSourceHuMarkers(query);
	}
}
