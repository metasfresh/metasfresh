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

package org.eevolution.productioncandidate.process;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order_Candidate;

import java.math.BigDecimal;

public class PP_Order_Candidate_EnqueuePastForOrdering extends PP_Order_Candidate_EnqueueSelectionForOrdering
{
	@Override
	protected IQueryBuilder<I_PP_Order_Candidate> createOCQueryBuilder()
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_Candidate.class)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_Processed, false)
				.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_IsClosed, false)
				.addCompareFilter(I_PP_Order_Candidate.COLUMNNAME_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
				.addCompareFilter(I_PP_Order_Candidate.COLUMNNAME_DatePromised, CompareQueryFilter.Operator.LESS_OR_EQUAL, Env.getDate())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_SeqNo)
				.orderBy(I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID);
	}
}
