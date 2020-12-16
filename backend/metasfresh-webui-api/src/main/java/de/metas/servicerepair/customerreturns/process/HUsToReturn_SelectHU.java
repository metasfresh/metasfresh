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
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.servicerepair.customerreturns.HUsToReturnViewContext;
import de.metas.servicerepair.customerreturns.HUsToReturnViewFactory;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

public class HUsToReturn_SelectHU extends ViewBasedProcessTemplate implements IProcessPrecondition
{
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
