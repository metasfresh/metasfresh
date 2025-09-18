/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.external.project;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystem;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExternalProjectRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalSystemRepository externalSystemRepository;

	public static ExternalProjectRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalProjectRepository(ExternalSystemRepository.newInstanceForUnitTesting());
	}

	@NonNull
	public ImmutableList<ExternalProjectReference> getByExternalSystemType(@NonNull final ExternalSystemType externalSystemType)
	{
		final ExternalSystem externalSystem = externalSystemRepository.getByType(externalSystemType);
		return queryBL.createQueryBuilder(I_S_ExternalProjectReference.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_ExternalSystem_ID, externalSystem.getId().getRepoId())
				.orderBy(I_S_ExternalProjectReference.COLUMNNAME_SeqNo)
				.create()
				.list()
				.stream()
				.map(this::buildExternalProjectReference)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Optional<ExternalProjectReference> getByRequestOptional(@NonNull final GetExternalProjectRequest getExternalProjectRequest)
	{
		return queryBL.createQueryBuilder(I_S_ExternalProjectReference.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_ExternalSystem_ID, getExternalProjectRequest.getExternalSystem().getId().getRepoId())

				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_ExternalReference, getExternalProjectRequest.getExternalReference())

				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_ExternalProjectOwner, getExternalProjectRequest.getExternalProjectOwner())
				.create()
				.firstOnlyOptional(I_S_ExternalProjectReference.class)
				.map(this::buildExternalProjectReference);
	}

	@NonNull
	public ExternalProjectReference getById(@NonNull final ExternalProjectReferenceId externalProjectReferenceId)
	{
		final I_S_ExternalProjectReference record = InterfaceWrapperHelper.load(externalProjectReferenceId, I_S_ExternalProjectReference.class);
		return buildExternalProjectReference(record);
	}

	@NonNull
	private ExternalProjectReference buildExternalProjectReference(@NonNull final I_S_ExternalProjectReference record)
	{
		final Optional<ExternalProjectType> externalProjectType = ExternalProjectType.getTypeByValue(record.getProjectType());

		if (!externalProjectType.isPresent())
		{
			throw new AdempiereException("Unknown project type!")
					.appendParametersToMessage()
					.setParameter(I_S_ExternalProjectReference.COLUMNNAME_S_ExternalProjectReference_ID, record.getS_ExternalProjectReference_ID())
					.setParameter(I_S_ExternalProjectReference.COLUMNNAME_ProjectType, record.getProjectType());
		}

		return ExternalProjectReference.builder()
				.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoId(record.getS_ExternalProjectReference_ID()))
				.externalProjectReference(record.getExternalReference())
				.projectOwner(record.getExternalProjectOwner())
				.externalProjectType(externalProjectType.get())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.build();
	}
}
