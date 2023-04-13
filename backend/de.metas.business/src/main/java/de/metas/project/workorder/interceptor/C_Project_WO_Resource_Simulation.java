/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.interceptor;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Project_WO_Resource_Conflict;
import org.compiere.model.I_C_Project_WO_Resource_Simulation;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project_WO_Resource_Simulation.class)
public class C_Project_WO_Resource_Simulation
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteWoResourceSimulationConflicts(@NonNull final I_C_Project_WO_Resource_Simulation record)
	{
		queryBL.createQueryBuilder(I_C_Project_WO_Resource_Conflict.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_SimulationPlan_ID, record.getC_SimulationPlan_ID())
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_ID, record.getC_Project_ID())
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource_ID, record.getC_Project_WO_Resource_ID())
				.create()
				.delete();
	}
}
