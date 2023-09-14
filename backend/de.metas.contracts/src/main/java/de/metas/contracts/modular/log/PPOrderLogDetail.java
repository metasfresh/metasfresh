/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log;

import de.metas.contracts.model.I_ModCntr_Log;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBuilder;
import org.eevolution.api.PPCostCollectorId;

@Value
@Builder
public class PPOrderLogDetail implements ILogDetail
{
	@NonNull
	PPCostCollectorId costCollectorId;

	@Override
	public void setToLog(@NonNull final I_ModCntr_Log log)
	{
		log.setPP_Cost_Collector_ID(costCollectorId.getRepoId());
	}

	@Override
	public void setToLogQuery(@NonNull final IQueryBuilder<I_ModCntr_Log> logQuery)
	{
		logQuery.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_PP_Cost_Collector_ID, costCollectorId);
	}
}
