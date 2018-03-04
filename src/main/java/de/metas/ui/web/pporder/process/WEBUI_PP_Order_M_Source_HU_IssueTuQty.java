package de.metas.ui.web.pporder.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import lombok.NonNull;

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

public class WEBUI_PP_Order_M_Source_HU_IssueTuQty
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition
{
	@Param(parameterName = "QtyTU", mandatory = true)
	private BigDecimal qtyTU;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		final PPOrderLineRow singleSelectedRow = getSingleSelectedRow();
		if (!singleSelectedRow.isIssue())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is not an issue-row; row={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}
		if (singleSelectedRow.isProcessed())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is already processed; row={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		final List<I_M_Source_HU> sourceHus = retrieveActiveSourceHus(singleSelectedRow);
		if (sourceHus.isEmpty())
		{
			final String internalReason = StringUtils.formatMessage("There are no sourceHU records for the selected row; row={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PPOrderLineRow row = getSingleSelectedRow();

		final List<I_M_Source_HU> sourceHus = retrieveActiveSourceHus(row);
		if (sourceHus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final Map<Integer, I_M_Source_HU> huId2SourceHu = new HashMap<>();

		final ImmutableList<I_M_HU> husThatAreFlaggedAsSource = sourceHus.stream()
				.peek(sourceHu -> huId2SourceHu.put(sourceHu.getM_HU_ID(), sourceHu))
				.map(I_M_Source_HU::getM_HU)
				.collect(ImmutableList.toImmutableList());

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.builder()
				.sourceHUs(husThatAreFlaggedAsSource)
				.qtyTU(qtyTU.intValue())
				.build();

		EmptyHUListener emptyHUListener = EmptyHUListener
				.doBeforeDestroyed(hu -> {
					if (huId2SourceHu.containsKey(hu.getM_HU_ID()))
					{
						SourceHUsService.get().snapshotSourceHU(huId2SourceHu.get(hu.getM_HU_ID()));
					}
				}, "Create snapshot of source-HU before it is destroyed");

		final List<I_M_HU> extractedTUs = HUTransformService.builder()
				.emptyHUListener(emptyHUListener)
				.build()
				.husToNewTUs(request);

		final PPOrderLinesView ppOrderView = getView();

		final int ppOrderId = ppOrderView.getPP_Order_ID();
		Services.get(IHUPPOrderBL.class)
				.createIssueProducer()
				.setTargetOrderBOMLinesByPPOrderId(ppOrderId)
				.createDraftIssues(extractedTUs);

		getView().invalidateAll();
		ppOrderView.invalidateAll();

		return MSG_OK;
	}

	private static List<I_M_Source_HU> retrieveActiveSourceHus(@NonNull final PPOrderLineRow row)
	{
		final I_PP_Order ppOrder = load(row.getPP_Order_ID(), I_PP_Order.class);

		final MatchingSourceHusQuery query = MatchingSourceHusQuery.builder()
				.productId(row.getM_Product_ID())
				.warehouseId(ppOrder.getM_Warehouse_ID()).build();
		return SourceHUsService.get().retrieveMatchingSourceHuMarkers(query);
	}
}
