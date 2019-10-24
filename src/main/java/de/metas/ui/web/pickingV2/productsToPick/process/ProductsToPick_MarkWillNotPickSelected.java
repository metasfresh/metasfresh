package de.metas.ui.web.pickingV2.productsToPick.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.candidate.commands.RejectPickingResult;
import de.metas.handlingunits.picking.requests.RejectPickingRequest;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;

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

public class ProductsToPick_MarkWillNotPickSelected extends ProductsToPickViewBasedProcess
{
	@Autowired
	private PickingCandidateService pickingCandidatesService;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final List<ProductsToPickRow> selectedRows = getSelectedRows();
		if (selectedRows.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		if (!selectedRows.stream().allMatch(ProductsToPickRow::isEligibleForRejectPicking))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select only rows that can be picked");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		getSelectedRows()
				.stream()
				.filter(ProductsToPickRow::isEligibleForRejectPicking)
				.forEach(this::markAsWillNotPick);

		invalidateView();

		return MSG_OK;
	}

	private void markAsWillNotPick(final ProductsToPickRow row)
	{
		final RejectPickingResult result = pickingCandidatesService.rejectPicking(RejectPickingRequest.builder()
				.shipmentScheduleId(row.getShipmentScheduleId())
				.qtyToReject(row.getQtyEffective())
				.rejectPickingFromHuId(row.getPickFromHUId())
				.existingPickingCandidateId(row.getPickingCandidateId())
				.build());

		updateViewRowFromPickingCandidate(row.getId(), result.getPickingCandidate());
	}
}
