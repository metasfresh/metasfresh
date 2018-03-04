package de.metas.ui.web.handlingunits.process;

import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.handlingunits.inout.ReceiptCorrectHUsProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.view.IViewsRepository;
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
@Profile(value = Profiles.PROFILE_Webui)
public class WEBUI_M_HU_ReverseReceipt extends WEBUI_M_HU_Receipt_Base implements IProcessPrecondition
{
	@Autowired
	private IViewsRepository viewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;

	/**
	 * Only allows rows whose HUs are in the "active" status.
	 */
	@Override
	final ProcessPreconditionsResolution rejectResolutionOrNull(final HUEditorRow document)
	{
		if (!document.isHUStatusActive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Only active HUs can be reversed");
		}
		final List<I_M_ReceiptSchedule> receiptSchedules = getM_ReceiptSchedules();
		if(receiptSchedules.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Thre are no receipt schedules");
		}
		return null;
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
				getView().removeHUIdsAndInvalidate(huIdsToReverse);

				// Notify all active views that given receipt schedules were changed
				viewsRepo.notifyRecordsChanged(TableRecordReference.ofSet(receiptSchedules));
			}
		}

		if (!hasChanges)
		{
			throw new AdempiereException("@NotFound@ @M_InOut_ID@");
		}

		return MSG_OK;
	}

	@Override
	protected HUEditorView getView()
	{
		return getView(HUEditorView.class);
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
		// gh #1955: prevent an OutOfMemoryError
		final IQueryFilter<I_M_HU> processFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU.class, this)
				.filter(processFilter)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();
	}
}
