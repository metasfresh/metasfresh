package de.metas.ui.web.pickingV2.productsToPick.process;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickRow;
import de.metas.ui.web.window.datatypes.DocumentId;

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

public class ProductsToPick_SetPackingInstructions extends ProductsToPickViewBasedProcess
{
	@Autowired
	private PickingCandidateService pickingCandidateService;

	@Param(parameterName = "M_HU_PI_ID", mandatory = true)
	private int p_M_HU_PI_ID;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final List<ProductsToPickRow> selectedRows = getSelectedRows();
		if (selectedRows.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		if (!selectedRows.stream().allMatch(ProductsToPickRow::isEligibleForPacking))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not all rows are eligible");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Map<PickingCandidateId, DocumentId> rowIdsByPickingCandidateId = getSelectedRows()
				.stream()
				.filter(ProductsToPickRow::isEligibleForPacking)
				.collect(ImmutableMap.toImmutableMap(ProductsToPickRow::getPickingCandidateId, ProductsToPickRow::getId));

		final Set<PickingCandidateId> pickingCandidateIds = rowIdsByPickingCandidateId.keySet();
		final HuPackingInstructionsId huPackingInstructionsId = getHuPackingInstructionsId();
		final List<PickingCandidate> pickingCandidates = pickingCandidateService.setHuPackingInstructionId(pickingCandidateIds, huPackingInstructionsId);

		pickingCandidates.forEach(pickingCandidate -> {
			final DocumentId rowId = rowIdsByPickingCandidateId.get(pickingCandidate.getId());
			updateViewRowFromPickingCandidate(rowId, pickingCandidate);
		});

		invalidateView();

		return MSG_OK;
	}

	private HuPackingInstructionsId getHuPackingInstructionsId()
	{
		return HuPackingInstructionsId.ofRepoId(p_M_HU_PI_ID);
	}

}
