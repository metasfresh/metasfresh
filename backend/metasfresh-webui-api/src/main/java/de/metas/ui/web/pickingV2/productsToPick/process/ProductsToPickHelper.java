/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pickingV2.productsToPick.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.ui.web.pickingV2.config.PickingConfigRepositoryV2;
import de.metas.ui.web.pickingV2.config.PickingConfigV2;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.adempiere.util.lang.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Component
class ProductsToPickHelper
{
	private final PickingCandidateService pickingCandidateService;
	private final ProductsToPickRowsService productsToPickRowsService;

	private final PickingConfigRepositoryV2 pickingConfigRepo;
	private PickingConfigV2 _pickingConfig; // lazy

	protected final PickingConfigV2 getPickingConfig()
	{
		PickingConfigV2 pickingConfig = _pickingConfig;
		if (pickingConfig == null)
		{
			pickingConfig = _pickingConfig = pickingConfigRepo.getPickingConfig();
		}
		return pickingConfig;
	}

	ProductsToPickHelper(final PickingCandidateService pickingCandidateService,
			final ProductsToPickRowsService productsToPickRowsService,
			final PickingConfigRepositoryV2 pickingConfigRepo)
	{
		this.pickingCandidateService = pickingCandidateService;
		this.productsToPickRowsService = productsToPickRowsService;
		this.pickingConfigRepo = pickingConfigRepo;
	}

	@NonNull
	public ImmutableList<ImmutablePair<DocumentId, PickingCandidate>> pick(final List<ProductsToPickRow> selectedRows)
	{
		return streamRowsEligibleForPicking(selectedRows)
				.map(row -> {
					final PickHUResult result = pickingCandidateService.pickHU(createPickRequest(row));
					return ImmutablePair.of(row.getId(), result.getPickingCandidate());
				})
				.collect(GuavaCollectors.toImmutableList());
	}

	public ImmutableList<ImmutablePair<DocumentId, PickingCandidate>> setPackingInstruction(final List<ProductsToPickRow> selectedRows, final HuPackingInstructionsId huPackingInstructionsId)
	{

		final Map<PickingCandidateId, DocumentId> rowIdsByPickingCandidateId = streamRowsEligibleForPacking(selectedRows)
				.collect(ImmutableMap.toImmutableMap(ProductsToPickRow::getPickingCandidateId, ProductsToPickRow::getId));

		final Set<PickingCandidateId> pickingCandidateIds = rowIdsByPickingCandidateId.keySet();
		final List<PickingCandidate> pickingCandidates = pickingCandidateService.setHuPackingInstructionId(pickingCandidateIds, huPackingInstructionsId);

		return pickingCandidates.stream()
				.map(cand -> ImmutablePair.of(rowIdsByPickingCandidateId.get(cand.getId()), cand))
				.collect(GuavaCollectors.toImmutableList());

	}

	public boolean anyRowsEligibleForPacking(final List<ProductsToPickRow> selectedRows)
	{
		return streamRowsEligibleForPacking(selectedRows).findAny().isPresent();
	}

	private Stream<ProductsToPickRow> streamRowsEligibleForPacking(final List<ProductsToPickRow> selectedRows)
	{
		return selectedRows
				.stream()
				.filter(ProductsToPickRow::isEligibleForPacking);
	}


	public boolean anyRowsEligibleForPicking(final List<ProductsToPickRow> selectedRows)
	{
		return streamRowsEligibleForPicking(selectedRows).findAny().isPresent();
	}

	@NonNull
	private Stream<ProductsToPickRow> streamRowsEligibleForPicking(final List<ProductsToPickRow> selectedRows)
	{
		return selectedRows
				.stream()
				.filter(ProductsToPickRow::isEligibleForPicking);
	}

	private PickRequest createPickRequest(final ProductsToPickRow row)
	{
		final PickingConfigV2 pickingConfig = getPickingConfig();
		return productsToPickRowsService.createPickRequest(row, pickingConfig.isPickingReviewRequired());
	}

}
