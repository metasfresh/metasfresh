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

package de.metas.serviceprovider.external.reference;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.model.I_S_ExternalReference;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

@Repository
public class ExternalReferenceRepository
{
	private final IQueryBL queryBL;

	public ExternalReferenceRepository(final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	public int getReferencedRecordIdBy(@NonNull final GetReferencedIdRequest getReferencedIdRequest)
	{
		final Optional<ExternalReference> externalReferenceEntity =
				getOptionalExternalReferenceBy(getReferencedIdRequest);

		if (!externalReferenceEntity.isPresent())
		{
			throw new AdempiereException("Missing ExternalReference!")
					.appendParametersToMessage()
					.setParameter("ExternalSystem",getReferencedIdRequest.getExternalSystem().getValue())
					.setParameter("ExternalReferenceType",getReferencedIdRequest.getExternalReferenceType())
					.setParameter("ExternalReference", getReferencedIdRequest.getExternalReference());
		}

		return externalReferenceEntity.get().getRecordId();
	}

	@Nullable
	public Integer getReferencedRecordIdOrNullBy(final @NonNull GetReferencedIdRequest getReferencedIdRequest)
	{
		return getOptionalExternalReferenceBy(getReferencedIdRequest)
				.map(ExternalReference::getRecordId)
				.orElse(null);
	}

	public ExternalReferenceId save(@NonNull final ExternalReference externalReference)
	{
		final I_S_ExternalReference record = InterfaceWrapperHelper.newInstance(I_S_ExternalReference.class);

		record.setAD_Org_ID(externalReference.getOrgId().getRepoId());
		record.setExternalReference(externalReference.getExternalReference());
		record.setExternalSystem(externalReference.getExternalSystem().getValue());
		record.setType(externalReference.getExternalReferenceType().getCode());
		record.setRecord_ID(externalReference.getRecordId());

		InterfaceWrapperHelper.saveRecord(record);

		return ExternalReferenceId.ofRepoId(record.getS_ExternalReference_ID());
	}

	public void deleteByRecordIdAndType(final int recordId, @NonNull final ExternalReferenceType type)
	{
		queryBL.createQueryBuilder(I_S_ExternalReference.class)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, type.getCode())
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Record_ID, recordId)
				.create()
				.list()
				.forEach(InterfaceWrapperHelper::delete);
	}

	private Optional<ExternalReference> getOptionalExternalReferenceBy(@NonNull final GetReferencedIdRequest getReferencedIdRequest)
	{
		return queryBL.createQueryBuilder(I_S_ExternalReference.class)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem,
						getReferencedIdRequest.getExternalSystem().getValue())

				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type,
						getReferencedIdRequest.getExternalReferenceType().getCode())

				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference,
						getReferencedIdRequest.getExternalReference())
				.create()
				.firstOnlyOptional(I_S_ExternalReference.class)
				.map(this::buildExternalReference);
	}

	@NonNull
	private ExternalReference buildExternalReference(@NonNull final I_S_ExternalReference record)
	{
		final ExternalReferenceType type = ExternalReferenceType.ofCode(record.getType());

		final Optional<ExternalSystem> externalSystem = ExternalSystem.of(record.getExternalSystem());
		if (!externalSystem.isPresent())
		{
			throw new AdempiereException("Unknown ExternalSystem: 'system'.")
					.appendParametersToMessage()
					.setParameter("system", record.getExternalSystem());
		}

		return ExternalReference.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.externalReference(record.getExternalReference())
				.externalReferenceType(type)
				.externalSystem(externalSystem.get())
				.recordId(record.getRecord_ID())
				.build();
	}
}
