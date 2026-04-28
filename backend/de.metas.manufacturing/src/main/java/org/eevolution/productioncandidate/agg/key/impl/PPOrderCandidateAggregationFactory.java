/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2024 metas GmbH
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

package org.eevolution.productioncandidate.agg.key.impl;

import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationKey;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_Candidate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

import static de.metas.aggregation.model.X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header;

@Component
public class PPOrderCandidateAggregationFactory
{
	private final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);

	public String buildAggregationKey(@NonNull final I_PP_Order_Candidate candidate)
	{
		return Optional.ofNullable(ProductPlanningId.ofRepoIdOrNull(candidate.getPP_Product_Planning_ID()))
				.map(productPlanningsRepo::getById)
				.map(this::getAggregationKeyBuilderOrDefault)
				.map(keyBuilder -> keyBuilder.buildAggregationKey(candidate))
				.map(AggregationKey::getAggregationKeyString)
				.orElseGet(() -> PPOrderCandidateHeaderAggregationKeyHelper.generateHeaderAggregationKey(candidate));
	}

	@Nullable
	private IAggregationKeyBuilder<I_PP_Order_Candidate> getAggregationKeyBuilderOrDefault(@NonNull final ProductPlanning productPlanning)
	{
		final AggregationId aggregationId = AggregationId.ofRepoIdOrNull(productPlanning.getManufacturingAggregationId());
		if (aggregationId != null)
		{
			return aggregationFactory.getAggregationKeyBuilder(Env.getCtx(), I_PP_Order_Candidate.class, aggregationId);
		}
		else
		{
			return aggregationFactory.getDefaultAggregationKeyBuilderOrNull(Env.getCtx(), I_PP_Order_Candidate.class, true, AGGREGATIONUSAGELEVEL_Header);
		}
	}
}
