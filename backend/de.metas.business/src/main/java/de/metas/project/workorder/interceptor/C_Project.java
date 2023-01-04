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

package de.metas.project.workorder.interceptor;

import de.metas.project.ProjectCategory;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Interceptor(I_C_Project.class)
@Component
public class C_Project
{
	/**
	 * If the given work order project has no values for certain columns, then take them from the parent-project
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Project.COLUMNNAME_C_Project_Parent_ID, I_C_Project.COLUMNNAME_ProjectCategory })
	public void updateFromParent(@NonNull final I_C_Project project)
	{
		if (!ProjectCategory.ofNullableCodeOrGeneral(project.getProjectCategory()).isWorkOrder())
		{
			return; // not our business
		}
		if (project.getC_Project_Parent_ID() <= 0)
		{
			return; // nothing for us to do
		}

		final ArrayList<String> updatedColumns = new ArrayList<>();
		final I_C_Project parentProjectRecord = InterfaceWrapperHelper.load(project.getC_Project_Parent_ID(), I_C_Project.class);
		if (project.getC_BPartner_ID() <= 0)
		{
			project.setC_BPartner_ID(parentProjectRecord.getC_BPartner_ID());
			updatedColumns.add(I_C_Project.COLUMNNAME_C_BPartner_ID);
		}
		if (project.getSalesRep_ID() <= 0)
		{
			project.setSalesRep_ID(parentProjectRecord.getSalesRep_ID());
			updatedColumns.add(I_C_Project.COLUMNNAME_SalesRep_ID);
		}
		if (project.getSpecialist_Consultant_ID() <= 0)
		{
			project.setSpecialist_Consultant_ID(parentProjectRecord.getSpecialist_Consultant_ID());
			updatedColumns.add(I_C_Project.COLUMNNAME_Specialist_Consultant_ID);
		}
		if (updatedColumns.size() > 0) // log this, particularly in case the change is done via API 
		{
			Loggables.get().addLog("Set the following columns from C_Project_Parent_ID={}: {}", project.getC_Project_Parent_ID(), updatedColumns);
		}
	}
}
