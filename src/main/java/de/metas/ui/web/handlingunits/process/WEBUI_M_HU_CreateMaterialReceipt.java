package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.MutableInt;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.WebRestApiApplication;
import de.metas.ui.web.handlingunits.HUDocumentView;
import de.metas.ui.web.handlingunits.HUDocumentViewSelection;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.ProcessInstance;
import de.metas.ui.web.view.IDocumentViewsRepository;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Profile(WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_CreateMaterialReceipt extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final DocumentViewAsPreconditionsContext viewContext = DocumentViewAsPreconditionsContext.castOrNull(context);
		if (viewContext == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("webui view not available");
		}

		if (viewContext.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final MutableInt checkedDocumentsCount = new MutableInt(0);
		final ProcessPreconditionsResolution firstRejection = viewContext.getView(HUDocumentViewSelection.class)
				.streamByIds(viewContext.getSelectedDocumentIds())
				.filter(document -> document.isPureHU())
				//
				.peek(document -> checkedDocumentsCount.incrementAndGet()) // count checked documents
				.map(document -> rejectResolutionOrNull(document)) // create reject resolution if any
				.filter(resolution -> resolution != null) // filter out those which are not errors
				.findFirst()
				.orElse(null);
		if (firstRejection != null)
		{
			// found a record which is not eligible => don't run the process
			return firstRejection;
		}
		if (checkedDocumentsCount.getValue() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no eligible rows");
		}

		// Safe to run the process
		return ProcessPreconditionsResolution.accept();
	}

	private static final ProcessPreconditionsResolution rejectResolutionOrNull(final HUDocumentView document)
	{
		if (!document.isHUStatusPlanning())
		{
			return ProcessPreconditionsResolution.reject("Only planning HUs can be received"); // TODO: trl
		}

		return null;
	}

	@Autowired
	private IDocumentViewsRepository documentViewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;

	@Param(parameterName = ProcessInstance.PARAM_ViewId, mandatory = true)
	private String p_WebuiViewId;

	public WEBUI_M_HU_CreateMaterialReceipt()
	{
		super();
		Adempiere.autowire(this);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{

		//
		// Generate material receipts
		final List<I_M_ReceiptSchedule> receiptSchedules = getM_ReceiptSchedules();
		final Set<I_M_HU> selectedHUs = retrieveHUsToReceive();
		final boolean collectGeneratedInOuts = true;
		Services.get(IHUReceiptScheduleBL.class).processReceiptSchedules(getCtx(), receiptSchedules, selectedHUs, collectGeneratedInOuts);
		// NOTE: we assume user was already notified about generated material receipts

		//
		// Reset the view's affected HUs
		getView().invalidateAll();

		documentViewsRepo.notifyRecordsChanged(TableRecordReference.ofSet(receiptSchedules));

		return MSG_OK;
	}

	private HUDocumentViewSelection getView()
	{
		return documentViewsRepo.getView(p_WebuiViewId, HUDocumentViewSelection.class);
	}

	private List<I_M_ReceiptSchedule> getM_ReceiptSchedules()
	{
		return getView()
				.getReferencingDocumentPaths().stream()
				.map(referencingDocumentPath -> documentsCollection.getTableRecordReference(referencingDocumentPath).getModel(this, I_M_ReceiptSchedule.class))
				.collect(GuavaCollectors.toImmutableList());
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
