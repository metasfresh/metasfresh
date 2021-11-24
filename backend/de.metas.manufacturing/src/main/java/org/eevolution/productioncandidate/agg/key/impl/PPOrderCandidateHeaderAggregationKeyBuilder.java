/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

import org.adempiere.util.lang.ObjectUtils;
import de.metas.util.agg.key.AbstractHeaderAggregationKeyBuilder;
import org.eevolution.model.I_PP_Order_Candidate;

public class PPOrderCandidateHeaderAggregationKeyBuilder extends AbstractHeaderAggregationKeyBuilder<I_PP_Order_Candidate>
{
	public static final String REGISTRATION_KEY = I_PP_Order_Candidate.Table_Name;

	public PPOrderCandidateHeaderAggregationKeyBuilder()
	{
		super(REGISTRATION_KEY);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
