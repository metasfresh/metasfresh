package de.metas.project.interceptor;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.I_C_Project;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
@Callout(I_C_Project.class)
public class C_Project
{
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final ProjectTypeRepository projectTypeRepository;

	public C_Project(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProjectTypeRepository projectTypeRepository)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.projectTypeRepository = projectTypeRepository;

		// register ourselves
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
		CopyRecordFactory.enableForTableName(I_C_Project.Table_Name);
	}

	@CalloutMethod(columnNames = I_C_Project.COLUMNNAME_C_ProjectType_ID)
	public void onC_ProjectType_ID(@NonNull final I_C_Project projectRecord)
	{
		final ProjectTypeId projectTypeId = ProjectTypeId.ofRepoIdOrNull(projectRecord.getC_ProjectType_ID());
		if (projectTypeId == null)
		{
			return;
		}

		final String value = computeNextProjectValue(projectRecord);
		projectRecord.setValue(value);

		final ProjectType projectType = projectTypeRepository.getById(projectTypeId);
		projectRecord.setProjectCategory(projectType.getProjectCategory());
	}

	@Nullable
	private String computeNextProjectValue(final I_C_Project projectRecord)
	{
		return documentNoBuilderFactory
				.createValueBuilderFor(projectRecord)
				.setFailOnError(false)
				.build();
	}
}
