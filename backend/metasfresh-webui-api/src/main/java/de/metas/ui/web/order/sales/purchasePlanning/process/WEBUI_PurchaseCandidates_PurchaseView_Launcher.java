package de.metas.ui.web.order.sales.purchasePlanning.process;

import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseCandidates2PurchaseViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public class WEBUI_PurchaseCandidates_PurchaseView_Launcher
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final Set<TableRecordReference> purchaseCandidateRefs = getSelectedRowIds()
				.stream()
				.map(DocumentId::toInt)
				.map(recordId -> TableRecordReference.of(I_C_PurchaseCandidate.Table_Name, recordId))
				.collect(ImmutableSet.toImmutableSet());

		if (purchaseCandidateRefs.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		getResult().setRecordsToOpen(
				purchaseCandidateRefs,
				PurchaseCandidates2PurchaseViewFactory.WINDOW_ID_STRING);

		return MSG_OK;
	}
}
