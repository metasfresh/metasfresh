/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.project;

import de.metas.cache.CCache;
import de.metas.document.sequence.DocSequenceId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ProjectType;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectTypeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProjectTypeId, ProjectType> projectTypes = CCache.<ProjectTypeId, ProjectType>builder()
			.tableName(I_C_ProjectType.Table_Name)
			.build();

	public ProjectType getById(@NonNull final ProjectTypeId id)
	{
		return projectTypes.getOrLoad(id, this::retrieveById);
	}

	private ProjectType retrieveById(@NonNull final ProjectTypeId id)
	{
		final I_C_ProjectType record = InterfaceWrapperHelper.loadOutOfTrx(id, I_C_ProjectType.class);
		return toProjectType(record);
	}

	private static ProjectType toProjectType(@NonNull final I_C_ProjectType record)
	{
		return ProjectType.builder()
				.id(ProjectTypeId.ofRepoId(record.getC_ProjectType_ID()))
				.projectCategory(ProjectCategory.ofCode(record.getProjectCategory()))
				.docSequenceId(DocSequenceId.ofRepoIdOrNull(record.getAD_Sequence_ProjectValue_ID()))
				.build();
	}

	public ProjectTypeId getFirstIdByProjectCategoryAndOrg(
			@NonNull final ProjectCategory projectCategory,
			@NonNull final OrgId orgId)
	{
		final ProjectTypeId projectTypeId = queryBL.createQueryBuilderOutOfTrx(I_C_ProjectType.class)
				.addEqualsFilter(I_C_ProjectType.COLUMNNAME_ProjectCategory, projectCategory.getCode())
				.addInArrayFilter(I_C_ProjectType.COLUMNNAME_AD_Org_ID, OrgId.ANY, orgId)
				.orderByDescending(I_C_ProjectType.COLUMNNAME_AD_Org_ID)
				.orderBy(I_C_ProjectType.COLUMNNAME_C_ProjectType_ID)
				.create()
				.firstId(ProjectTypeId::ofRepoIdOrNull);

		if (projectTypeId == null)
		{
			throw new AdempiereException("@NotFound@ @C_ProjectType_ID@")
					.appendParametersToMessage()
					.setParameter("ProjectCategory", projectCategory)
					.setParameter("AD_Org_ID", orgId.getRepoId());
		}

		return projectTypeId;
	}

}
