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

package de.metas.project.shared;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectType;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.util.Env;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class ProjectSharedService
{
	@NonNull
	private final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);
	@NonNull
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	@NonNull
	private final ProjectTypeRepository projectTypeRepository;

	public ProjectSharedService(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final ProjectTypeRepository projectTypeRepository)
	{
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.projectTypeRepository = projectTypeRepository;
	}

	@Nullable
	public ProjectCategory getProjectCategoryFromProjectType(@Nullable final ProjectTypeId projectTypeId)
	{
		if (projectTypeId == null)
		{
			return null;
		}

		final ProjectType projectType = projectTypeRepository.getById(projectTypeId);
		return projectType.getProjectCategory();
	}

	@Nullable
	public String getValueForProjectType(@Nullable final ProjectTypeId projectTypeId)
	{
		if (projectTypeId == null)
		{
			return null;
		}

		final ProjectType projectType = projectTypeRepository.getById(projectTypeId);
		final DocSequenceId docSequenceId = projectType.getDocSequenceId();

		if (docSequenceId == null)
		{
			return null;
		}

		final DocumentSequenceInfo documentSequenceInfo = documentSequenceDAO.retriveDocumentSequenceInfo(docSequenceId);

		return documentNoBuilderFactory.createDocumentNoBuilder()
				.setDocumentSequenceInfo(documentSequenceInfo)
				.setClientId(Env.getClientId())
				.setEvaluationContext(Evaluatees.ofCtx(Env.getCtx()))
				.setFailOnError(false)
				.build();
	}
}
