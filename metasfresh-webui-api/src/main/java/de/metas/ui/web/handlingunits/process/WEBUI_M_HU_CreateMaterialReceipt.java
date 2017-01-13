package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.ui.web.handlingunits.HUDocumentViewSelection;
import de.metas.ui.web.process.ProcessInstance;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.DocumentCollection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WEBUI_M_HU_CreateMaterialReceipt extends JavaProcess
{
	@Autowired
	private IDocumentViewsRepository documentViewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;

	@Param(parameterName = ProcessInstance.PARAM_ViewId)
	private String p_WebuiViewId;

	public WEBUI_M_HU_CreateMaterialReceipt()
	{
		super();
		Adempiere.autowire(this);
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = ImmutableList.of(getM_ReceiptSchedule());
		final Set<I_M_HU> selectedHUs = retrieveHUsToReceive();
		final boolean storeReceipts = true;

		final InOutGenerateResult result = Services.get(IHUReceiptScheduleBL.class).processReceiptSchedules(getCtx(), receiptSchedules, selectedHUs, storeReceipts);

		// result.getInOuts();

		return MSG_OK;
	}

	private I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		final HUDocumentViewSelection view = documentViewsRepo.getView(p_WebuiViewId, HUDocumentViewSelection.class);
		final DocumentPath referencingDocumentPath = view.getReferencingDocumentPath();
		
		return documentsCollection.getTableRecordReference(referencingDocumentPath)
				.getModel(this, I_M_ReceiptSchedule.class);
	}

	private Set<I_M_HU> retrieveHUsToReceive()
	{
		final List<I_M_HU> hus = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU.class, this)
				.filter(getProcessInfo().getQueryFilter())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_HU.class);

		return ImmutableSet.copyOf(hus);
	}
}
