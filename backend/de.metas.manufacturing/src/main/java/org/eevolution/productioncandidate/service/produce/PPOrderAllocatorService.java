/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.productioncandidate.service.produce;

import de.metas.material.planning.IResourceProductService;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Order_Candidate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PPOrderAllocatorService
{
	@NonNull
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull
	private final IResourceProductService resourceDAO = Services.get(IResourceProductService.class);

	@NonNull
	public PPOrderAllocator buildAllocator(@NonNull final PPOrderCandidateToAllocate ppOrderCandidateToAllocate)
	{
		final Quantity capacity = getCapacityPerProductionCycle(ppOrderCandidateToAllocate.getPpOrderCandidate());

		final String headerAggKey = ppOrderCandidateToAllocate.getHeaderAggregationKey();

		return PPOrderAllocator.builder()
				.capacityPerProductionCycle(capacity)
				.headerAggKey(headerAggKey)
				.build();
	}

	@NonNull
	private Quantity getCapacityPerProductionCycle(@NonNull final I_PP_Order_Candidate ppOrderCandidate)
	{
		final UomId candidateUomId = UomId.ofRepoId(ppOrderCandidate.getC_UOM_ID());
		final Quantity capacityOverride = Quantitys.of(ppOrderCandidate.getCapacityPerProductionCycleOverride(),
														   candidateUomId);
		if (capacityOverride.isPositive())
		{
			return capacityOverride;
		}

		final I_S_Resource resource = resourceDAO.getById(ResourceId.ofRepoId(ppOrderCandidate.getS_Resource_ID()));

		if (resource.getCapacityPerProductionCycle().signum() == 0)
		{
			return Quantitys.of(BigDecimal.ZERO, candidateUomId);
		}

		if (resource.getCapacityPerProductionCycle_UOM_ID() <= 0)
		{
			throw new AdempiereException("Unit of measurement for capacity per production cycle cannot be missing if capacity is provided!")
					.appendParametersToMessage()
					.setParameter("S_Resource_ID", resource.getS_Resource_ID());
		}

		final Quantity capacityQty = Quantitys.of(resource.getCapacityPerProductionCycle(),
												  UomId.ofRepoId(resource.getCapacityPerProductionCycle_UOM_ID()));

		return uomConversionBL
				.convertQuantityTo(
						capacityQty,
						ProductId.ofRepoId(ppOrderCandidate.getM_Product_ID()),
						candidateUomId);
	}
}
