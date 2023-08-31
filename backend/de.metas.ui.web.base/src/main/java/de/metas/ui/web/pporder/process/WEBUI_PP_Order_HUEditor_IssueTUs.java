package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewTUsRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import java.util.List;

import static de.metas.ui.web.handlingunits.WEBUI_HU_Constants.MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU;

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

public class WEBUI_PP_Order_HUEditor_IssueTUs
		extends WEBUI_PP_Order_HUEditor_ProcessBase
		implements IProcessPrecondition
{
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	@Param(parameterName = "QtyTU", mandatory = true)
	private int qtyTUs;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final boolean anyHuMatches = retrieveSelectedAndEligibleHUEditorRows()
				.anyMatch(huRow -> huRow.isTU() || huRow.isTopLevel());
		if (anyHuMatches)
		{
			return ProcessPreconditionsResolution.accept();
		}

		final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU);
		return ProcessPreconditionsResolution.reject(reason);
	}

	@Override
	protected String doIt() throws Exception
	{
		final HUEditorRow row = getSingleSelectedRow();

		final I_M_HU sourceLUorTU = row.getM_HU();

		final HUsToNewTUsRequest request = HUsToNewTUsRequest.forSourceHuAndQty(sourceLUorTU, qtyTUs);
		final List<I_M_HU> extractedTUs = HUTransformService.newInstance().husToNewTUs(request);
		if (extractedTUs.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final PPOrderLinesView ppOrderView = getPPOrderView().get();

		final PPOrderId ppOrderId = ppOrderView.getPpOrderId();
		final HUPPOrderIssueProducer issueProducer = huPPOrderBL.createIssueProducer(ppOrderId);
		final PPOrderBOMLineId selectedOrderBOMLineId = getSelectedOrderBOMLineId();
		if (selectedOrderBOMLineId != null)
		{
			issueProducer.targetOrderBOMLine(selectedOrderBOMLineId);
		}

		issueProducer.createIssues(extractedTUs);

		getView().invalidateAll();
		ppOrderView.invalidateAll();

		return MSG_OK;
	}

}
