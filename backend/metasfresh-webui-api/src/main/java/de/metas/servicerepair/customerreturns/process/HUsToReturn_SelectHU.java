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

package de.metas.servicerepair.customerreturns.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.returns.customer.CreateCustomerReturnLineReq;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnsWithoutHUsProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.servicerepair.customerreturns.HUsToReturnViewContext;
import de.metas.servicerepair.customerreturns.HUsToReturnViewFactory;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class HUsToReturn_SelectHU extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final CustomerReturnsWithoutHUsProducer customerReturnService = SpringContextHolder.instance.getBean(CustomerReturnsWithoutHUsProducer.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final HUEditorRow row = getSingleSelectedRowOrNull();
		if (row == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		if (!row.isTopLevel())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a top level HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final HUEditorRow row = getSingleSelectedRow();
		final HuId huId = row.getHuId();
		final HUsToReturnViewContext viewContext = getHUsToReturnViewContext();
		System.out.println("huId=" + huId);
		System.out.println("viewContext=" + viewContext);

		final I_M_HU clonedPlaningHU = handlingUnitsBL.copyAsPlannedHU(huId);

		final I_M_InOutLine returnLine = customerReturnService.createReturnLine(CreateCustomerReturnLineReq.builder()
				.headerId(viewContext.getCustomerReturnsId())
				.productId(row.getProductId())
				.qtyReturned(row.getQtyCUAsQuantity())
				.build());

		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	protected HUEditorView getView()
	{
		return HUEditorView.cast(super.getView());
	}

	public HUsToReturnViewContext getHUsToReturnViewContext()
	{
		return getView()
				.getParameter(HUsToReturnViewFactory.PARAM_HUsToReturnViewContext, HUsToReturnViewContext.class)
				.orElseThrow(() -> new AdempiereException("No view context"));
	}

	@Nullable
	protected HUEditorRow getSingleSelectedRowOrNull()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isSingleDocumentId())
		{
			return null;
		}

		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		return getView().getById(rowId);
	}

	@Override
	protected HUEditorRow getSingleSelectedRow()
	{
		return HUEditorRow.cast(super.getSingleSelectedRow());
	}
}
