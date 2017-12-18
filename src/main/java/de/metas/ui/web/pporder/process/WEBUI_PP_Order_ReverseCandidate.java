package de.metas.ui.web.pporder.process;

import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyBL;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
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

/**
 * Reverse(and deletes) a draft manufacturing order issue/receipt candidate.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/356
 */
public class WEBUI_PP_Order_ReverseCandidate
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition
{
	// services
	private final transient IHUPPOrderQtyBL huPPOrderQtyBL = Services.get(IHUPPOrderQtyBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.reject("Select one line");
		}

		final PPOrderLineRow row = getSingleSelectedRow();
		if(row.isSourceHU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not available for source HU line");
		}

		if (row.getPP_Order_Qty_ID() <= 0)
		{
			return ProcessPreconditionsResolution.reject("Not an issue/receipt line");
		}

		if (row.isProcessed())
		{
			return ProcessPreconditionsResolution.reject("Only not processed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final int ppOrderQtyId = getSingleSelectedRow().getPP_Order_Qty_ID();
		final I_PP_Order_Qty candidate = huPPOrderQtyDAO.retrieveById(ppOrderQtyId);

		huPPOrderQtyBL.reverseDraftCandidate(candidate);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		getView().invalidateAll();
	}
}
