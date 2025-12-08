/*
 * #%L
 * de.metas.business
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

package de.metas.project.callout;

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.project.service.ProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Component;

@Component
@Callout(I_C_Project.class)
public class C_Project
{
	private final ProjectService projectService;

	public C_Project(
			@NonNull final ProjectService projectService)
	{
		this.projectService = projectService;

		// register ourselves
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
		CopyRecordFactory.enableForTableName(I_C_Project.Table_Name);
	}

	@CalloutMethod(columnNames = I_C_Project.COLUMNNAME_C_ProjectType_ID)
	public void onC_ProjectType_ID(@NonNull final I_C_Project projectRecord)
	{
		projectService.updateFromProjectType(projectRecord);
	}
}
