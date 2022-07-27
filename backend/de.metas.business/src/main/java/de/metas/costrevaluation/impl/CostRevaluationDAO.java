/*
 * #%L
 * de.metas.business
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

package de.metas.costrevaluation.impl;

import de.metas.costrevaluation.ICostRevaluationDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.compiere.model.I_M_CostRevaluationLine;

import java.util.List;

public class CostRevaluationDAO implements ICostRevaluationDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public List<I_M_CostRevaluationLine> retrieveLinesByCostRevaluationId(@NonNull final CostRevaluationId costRevaluationId)
	{
		return queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMN_M_CostRevaluation_ID, costRevaluationId)
				.filter(ActiveRecordQueryFilter.getInstance(I_M_CostRevaluationLine.class))
				.orderBy(I_M_CostRevaluationLine.COLUMN_M_CostRevaluationLine_ID)
				.create()
				.list();
	}

}
