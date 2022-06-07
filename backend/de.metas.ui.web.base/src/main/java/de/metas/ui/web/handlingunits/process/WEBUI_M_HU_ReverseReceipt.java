package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inout.ReceiptCorrectHUsProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.springframework.context.annotation.Profile;

import java.util.Collection;
import java.util.List;

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
 */
@Profile(value = Profiles.PROFILE_Webui)
public class WEBUI_M_HU_ReverseReceipt extends WEBUI_M_HU_Receipt_Base implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final DocumentCollection documentsCollection = SpringContextHolder.instance.getBean(DocumentCollection.class);

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
		if (getReceiptScheduleIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("There are no receipt schedules");
		}
		return null;
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Collection<I_M_ReceiptSchedule> receiptSchedules = getM_ReceiptSchedules();
		final List<HuId> huIdsToReverse = retrieveHUsToReverse();

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

				final List<Integer> asRepoIds = RepoIdAwares.asRepoIds(huIdsToReverse);
				final List<I_M_InOut> receiptsToReverse = processor.getReceiptsToReverseFromHUIds(asRepoIds);

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
				viewsRepo.notifyRecordsChangedAsync(TableRecordReferenceSet.of(TableRecordReference.ofSet(receiptSchedules)));
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

	private Collection<I_M_ReceiptSchedule> getM_ReceiptSchedules()
	{
		final ImmutableSet<ReceiptScheduleId> receiptScheduleIds = getReceiptScheduleIds();
		return receiptScheduleDAO.getByIds(receiptScheduleIds, I_M_ReceiptSchedule.class).values();
	}

	private ImmutableSet<ReceiptScheduleId> getReceiptScheduleIds()
	{
		return getView()
				.getReferencingDocumentPaths().stream()
				.map(documentsCollection::getTableRecordReference)
				.filter(recordRef -> I_M_ReceiptSchedule.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> ReceiptScheduleId.ofRepoId(recordRef.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<HuId> retrieveHUsToReverse()
	{
		// gh #1955: prevent an OutOfMemoryError
		final IQueryFilter<I_M_HU> processFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		return queryBL.createQueryBuilder(I_M_HU.class, this)
				.filter(processFilter)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds()
				.stream()
				.map(HuId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
	}
}
