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

package org.eevolution.productioncandidate.service;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.productioncandidate.agg.key.impl.PPOrderCandidateHeaderAggregationKeyBuilder;

@UtilityClass
public class PPOrderCandidateHeaderAggregationKeyHelper
{
	@NonNull
	public String generateHeaderAggregationKey(@NonNull final I_PP_Order_Candidate orderCandidateRecord)
	{
		final PPOrderCandidateHeaderAggregationKeyBuilder orderCandidateKeyBuilder = mkPPOrderCandidateHeaderAggregationKeyBuilder();
		return orderCandidateKeyBuilder.buildKey(orderCandidateRecord);
	}

	@NonNull
	public PPOrderCandidateHeaderAggregationKeyBuilder mkPPOrderCandidateHeaderAggregationKeyBuilder()
	{
		return new PPOrderCandidateHeaderAggregationKeyBuilder();
	}
}
