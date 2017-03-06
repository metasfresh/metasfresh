package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.MutableInt;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import de.metas.handlingunits.inout.ReceiptCorrectHUsProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
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

/**
 * Reverse the receipts which contain the selected HUs.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Profile(value = WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_ReverseReceipt extends JavaProcess implements IProcessPrecondition
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

		return ProcessPreconditionsResolution.accept();
	}

	private static final ProcessPreconditionsResolution rejectResolutionOrNull(final HUDocumentView document)
	{
		if (!document.isHUStatusActive())
		{
			return ProcessPreconditionsResolution.reject("Only active HUs can be reversed"); // TODO: trl
		}

		return null;
	}

	@Autowired
	private IDocumentViewsRepository documentViewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;

	@Param(parameterName = ProcessInstance.PARAM_ViewId, mandatory = true)
	private String p_WebuiViewId;

	public WEBUI_M_HU_ReverseReceipt()
	{
		super();
		Adempiere.autowire(this);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final List<I_M_ReceiptSchedule> receiptSchedules = getM_ReceiptSchedules();
		final List<Integer> huIdsToReverse = retrieveHUsToReverse();

		boolean hasChanges = false;
		try
		{
			for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
			{
				final ReceiptCorrectHUsProcessor processor = ReceiptCorrectHUsProcessor.builder()
						.setM_ReceiptSchedule(receiptSchedule)
						.tolerateNoHUsFound()
						.build();
				if (processor.isNoHUsFound())
				{
					continue;
				}

				final List<I_M_InOut> receiptsToReverse = processor.getReceiptsToReverseFromHUIds(huIdsToReverse);
				if (receiptsToReverse.isEmpty())
				{
					continue;
				}

				processor.reverseReceipts(receiptsToReverse);
				hasChanges = true;
			}
		}
		finally
		{
			if (hasChanges)
			{
				// Reset the view's affected HUs
				getView().invalidateAll();

				// Notify all active views that given receipt schedules were changed
				documentViewsRepo.notifyRecordsChanged(TableRecordReference.ofSet(receiptSchedules));
			}
		}

		if (!hasChanges)
		{
			throw new AdempiereException("@NotFound@ @M_InOut_ID@");
		}

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

	private List<Integer> retrieveHUsToReverse()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU.class, this)
				.filter(getProcessInfo().getQueryFilter())
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();
	}
}
