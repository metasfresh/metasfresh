/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.PaySelectionId;
import de.metas.banking.PaySelectionLineId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.PaymentsViewFactory;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelectionLine;

import javax.annotation.Nullable;

import static de.metas.process.ProcessExecutionResult.WebuiViewToOpen;

public class PaymentView_Launcher_From_PaySelection extends JavaProcess implements IProcessPrecondition
{
	private final IViewsRepository viewsFactory = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		final PaySelectionId paySelectionId = PaySelectionId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (paySelectionId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		if (context.getSelectedIncludedRecords().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No lines selected");
		}

		final DocStatus docStatus = getDocStatus(paySelectionId);
		if (!docStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	private DocStatus getDocStatus(@Nullable final PaySelectionId paySelectionId)
	{
		if (paySelectionId == null)
		{
			return DocStatus.Unknown;
		}

		return paySelectionBL.getById(paySelectionId)
				.map(paySelection -> DocStatus.ofCode(paySelection.getDocStatus()))
				.orElse(DocStatus.Unknown);
	}

	@Override
	protected String doIt()
	{
		assertDraftPaySelection();

		final BPartnerId bpartnerId = getBPartnerIdFromSelectedLines();
		openViewForBPartner(bpartnerId);

		return MSG_OK;
	}

	private void assertDraftPaySelection()
	{
		final PaySelectionId paySelectionId = getPaySelectionId();
		final DocStatus docStatus = getDocStatus(paySelectionId);
		if (!docStatus.isDraftedOrInProgress())
		{
			throw new AdempiereException("Invalid doc status");
		}
	}

	@NonNull
	private PaySelectionId getPaySelectionId()
	{
		return PaySelectionId.ofRepoId(getRecord_ID());
	}

	private BPartnerId getBPartnerIdFromSelectedLines()
	{
		final PaySelectionId paySelectionId = getPaySelectionId();
		final ImmutableSet<PaySelectionLineId> paySelectionLineIds = getSelectedIncludedRecordIds(I_C_PaySelectionLine.class, repoId -> PaySelectionLineId.ofRepoId(paySelectionId, repoId));
		final ImmutableSet<BPartnerId> bpartnerIds = paySelectionBL.getBPartnerIdsFromPaySelectionLineIds(paySelectionLineIds);
		if (bpartnerIds.isEmpty())
		{
			throw new AdempiereException("No BPartners");
		}
		else if (bpartnerIds.size() != 1)
		{
			throw new AdempiereException("More than one BPartner selected");
		}
		else
		{
			return bpartnerIds.iterator().next();
		}
	}

	private void openViewForBPartner(final BPartnerId bpartnerId)
	{
		final ViewId viewId = viewsFactory.createView(
						CreateViewRequest.builder(PaymentsViewFactory.WINDOW_ID)
								.setParameter(PaymentsViewFactory.PARAMETER_TYPE_BPARTNER_ID, bpartnerId)
								.build())
				.getViewId();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.modalOverlay(viewId.getViewId()));
	}

}
