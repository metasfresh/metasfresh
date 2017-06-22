package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.MutableInt;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
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
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
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
		final ViewAsPreconditionsContext viewContext = ViewAsPreconditionsContext.castOrNull(context);
		if (viewContext == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("webui view not available");
		}

		if (viewContext.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final MutableInt checkedDocumentsCount = new MutableInt(0);
		final ProcessPreconditionsResolution firstRejection = viewContext.getView(HUEditorView.class)
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

	private static final ProcessPreconditionsResolution rejectResolutionOrNull(final HUEditorRow document)
	{
		if (!document.isHUStatusPlanning())
		{
			return ProcessPreconditionsResolution.reject("Only planning HUs can be received"); // TODO: trl
		}

		return null;
	}

	@Autowired
	private IViewsRepository viewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;

	@Param(parameterName = ViewBasedProcessTemplate.PARAM_ViewWindowId, mandatory = true)
	private String p_WebuiViewWindowId;

	@Param(parameterName = ViewBasedProcessTemplate.PARAM_ViewId, mandatory = true)
	private String p_WebuiViewIdStr;

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

		viewsRepo.notifyRecordsChanged(TableRecordReference.ofSet(receiptSchedules));

		return MSG_OK;
	}

	private HUEditorView getView()
	{
		final ViewId viewId = ViewId.of(p_WebuiViewWindowId, p_WebuiViewIdStr);
		return viewsRepo.getView(viewId, HUEditorView.class);
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
		final IQuery<I_M_HU> query = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU.class, this)

				// https://github.com/metasfresh/metasfresh/issues/1863
				// if the queryFilter is empty, then *do not* return everything to avoid an OOME
				.filter(getProcessInfo().getQueryFilterOrElse(
						ConstantQueryFilter.of(false)))

				.addOnlyActiveRecordsFilter()
				.create();
		final List<I_M_HU> hus = query.list(I_M_HU.class);
		if (hus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@ @M_HU_ID@")
					.setParameter("query", query);
		}

		return ImmutableSet.copyOf(hus);
	}
}
