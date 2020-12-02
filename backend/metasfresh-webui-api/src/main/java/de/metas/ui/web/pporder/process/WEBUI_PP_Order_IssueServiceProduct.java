/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.PPOrderIssueServiceProductRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.util.Services;

public class WEBUI_PP_Order_IssueServiceProduct
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition

{
	private final IHUPPOrderBL ppOrderBL = Services.get(IHUPPOrderBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLineRow ppOrderLineRow = getSingleSelectedRow();
		if (!ppOrderLineRow.isIssue())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not an issue line");
		}

		final ProductId productId = ppOrderLineRow.getProductId();
		if (productId == null || productBL.isStocked(productId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a service product");
		}

		//
		// OK
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PPOrderLinesView ppOrder = getView();
		final PPOrderLineRow issueRow = getSingleSelectedRow();

		ppOrderBL.issueServiceProduct(PPOrderIssueServiceProductRequest.builder()
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderBOMLineId(issueRow.getOrderBOMLineId())
				.build());

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
	}
}
