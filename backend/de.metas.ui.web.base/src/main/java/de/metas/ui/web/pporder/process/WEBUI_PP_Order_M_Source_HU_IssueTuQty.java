package de.metas.ui.web.pporder.process;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.EmptyHUListener;
import org.eevolution.api.PPOrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_ProcessHelper;
import de.metas.util.Services;
import de.metas.util.StringUtils;

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

		if (singleSelectedRow.isProcessed())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is already processed; row={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		return WEBUI_PP_Order_ProcessHelper.checkIssueSourceDefaultPreconditionsApplicable(singleSelectedRow);
	}

	@Override
	protected String doIt() throws Exception
	{
		final PPOrderLineRow row = getSingleSelectedRow();

		final List<I_M_Source_HU> sourceHus = WEBUI_PP_Order_ProcessHelper.retrieveActiveSourceHus(row);
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

		final PPOrderId ppOrderId = ppOrderView.getPpOrderId();
		Services.get(IHUPPOrderBL.class)
				.createIssueProducer(ppOrderId)
				.createIssues(extractedTUs);

		getView().invalidateAll();
		ppOrderView.invalidateAll();

		return MSG_OK;
	}
}
