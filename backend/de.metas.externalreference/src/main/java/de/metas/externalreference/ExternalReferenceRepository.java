/*
 * #%L
 * de.metas.externalreference
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

package de.metas.externalreference;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.CoalesceUtil;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Access;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ExternalReferenceRepository
{
	private final IQueryBL queryBL;

	private final ExternalReferenceTypes externalReferenceTypes;
	private final ExternalSystems externalSystems;

	public ExternalReferenceRepository(
			@NonNull final IQueryBL queryBL,
			@NonNull final ExternalSystems externalSystems,
			@NonNull final ExternalReferenceTypes externalReferenceTypes)
	{
		this.queryBL = queryBL;
		this.externalReferenceTypes = externalReferenceTypes;
		this.externalSystems = externalSystems;
	}

	public int getReferencedRecordIdBy(@NonNull final ExternalReferenceQuery query)
	{
		final Optional<ExternalReference> externalReferenceEntity =
				getOptionalExternalReferenceBy(query);

		if (!externalReferenceEntity.isPresent())
		{
			throw new AdempiereException("ExternalReference not found in metasfresh")
					.appendParametersToMessage()
					.setParameter("AD_Org_ID,", query.getOrgId().getRepoId())
					.setParameter("ExternalSystem", query.getExternalSystem().getCode())
					.setParameter("ExternalReferenceType", query.getExternalReferenceType())
					.setParameter("ExternalReference", query.getExternalReference());
		}

		return externalReferenceEntity.get().getRecordId();
	}

	@Nullable
	public Integer getReferencedRecordIdOrNullBy(final @NonNull ExternalReferenceQuery query)
	{
		return getOptionalExternalReferenceBy(query)
				.map(ExternalReference::getRecordId)
				.orElse(null);
	}

	public ExternalReferenceId save(@NonNull final ExternalReference externalReference)
	{
		final I_S_ExternalReference record = InterfaceWrapperHelper.loadOrNew(externalReference.getExternalReferenceId(), I_S_ExternalReference.class);

		record.setAD_Org_ID(externalReference.getOrgId().getRepoId());
		record.setExternalReference(externalReference.getExternalReference());
		record.setExternalSystem(externalReference.getExternalSystem().getCode());
		record.setType(externalReference.getExternalReferenceType().getCode());
		record.setRecord_ID(externalReference.getRecordId());
		record.setVersion(externalReference.getVersion());
		record.setExternalReferenceURL(externalReference.getExternalReferenceUrl());

		InterfaceWrapperHelper.saveRecord(record);

		return ExternalReferenceId.ofRepoId(record.getS_ExternalReference_ID());
	}

	public void deleteByRecordIdAndType(final int recordId, @NonNull final IExternalReferenceType type)
	{
		listIncludingInactiveBy(recordId, type).forEach(InterfaceWrapperHelper::delete);
	}

	/**
	 * If and when the externally referenced record switches orgs, we also need to change the reference-record to follow.
	 * That's because the org is relevant when we look up external references.
	 */
	public void updateOrgIdByRecordIdAndType(
			final int recordId,
			@NonNull final IExternalReferenceType type,
			@NonNull final OrgId orgId)
	{
		listIncludingInactiveBy(recordId, type)
				.stream()
				.peek(record -> record.setAD_Org_ID(orgId.getRepoId()))
				.forEach(InterfaceWrapperHelper::saveRecord);
	}

	/**
	 * @return a map with one entry for each given {@link ExternalReferenceQuery}.
	 */
	public ImmutableMap<ExternalReferenceQuery, ExternalReference> getExternalReferences(@NonNull final Set<ExternalReferenceQuery> queries)
	{
		final IQueryBuilder<I_S_ExternalReference> queryBuilder = queryBL.createQueryBuilder(I_S_ExternalReference.class).setJoinOr()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true);

		for (final ExternalReferenceQuery query : queries)
		{
			queryBuilder.filter(createFilterFor(query));
		}

		final Map<ExternalReferenceQuery, ExternalReference> query2reference =
				queryBuilder.create().stream()
						.collect(Collectors.toMap(
								this::buildExternalReferenceQuery, // key
								this::buildExternalReference // value
						));

		final ImmutableMap.Builder<ExternalReferenceQuery, ExternalReference> result = ImmutableMap.builder();
		for (final ExternalReferenceQuery query : queries)
		{
			final ExternalReference externalReference = query2reference.get(query);
			result.put(query, CoalesceUtil.coalesce(externalReference, ExternalReference.NULL));
		}

		return result.build();
	}

	@NonNull
	public Optional<ExternalReference> getExternalReferenceByMFReference(@NonNull final GetExternalReferenceByRecordIdReq request)
	{
		return queryBL.createQueryBuilder(I_S_ExternalReference.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Record_ID, request.getRecordId())
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, request.getExternalReferenceType().getCode())
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem, request.getExternalSystem().getCode())
				.create()
				.firstOnlyOptional(I_S_ExternalReference.class)
				.map(this::buildExternalReference);
	}

	private ExternalReferenceQuery buildExternalReferenceQuery(final I_S_ExternalReference record)
	{
		final IExternalReferenceType type = extractType(record);
		final IExternalSystem externalSystem = extractSystem(record);

		return ExternalReferenceQuery.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.externalReferenceType(type)
				.externalSystem(externalSystem)
				.externalReference(record.getExternalReference())
				.build();
	}

	private Optional<ExternalReference> getOptionalExternalReferenceBy(@NonNull final ExternalReferenceQuery query)
	{
		return queryBL.createQueryBuilder(I_S_ExternalReference.class)
				.addOnlyActiveRecordsFilter()

				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_AD_Org_ID,
						query.getOrgId())

				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem,
						query.getExternalSystem().getCode())

				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type,
						query.getExternalReferenceType().getCode())

				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference,
						query.getExternalReference())
				.create()
				.setRequiredAccess(Access.READ)
				.firstOnlyOptional(I_S_ExternalReference.class)
				.map(this::buildExternalReference);
	}

	private ICompositeQueryFilter<I_S_ExternalReference> createFilterFor(@NonNull final ExternalReferenceQuery query)
	{
		return queryBL.createCompositeQueryFilter(I_S_ExternalReference.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalSystem, query.getExternalSystem().getCode())
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, query.getExternalReferenceType().getCode())
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_ExternalReference, query.getExternalReference());
	}

	private List<I_S_ExternalReference> listIncludingInactiveBy(final int recordId, @NonNull final IExternalReferenceType type)
	{
		return queryBL.createQueryBuilder(I_S_ExternalReference.class)
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Type, type.getCode())
				.addEqualsFilter(I_S_ExternalReference.COLUMNNAME_Record_ID, recordId)
				.create()
				.list();
	}

	@NonNull
	private ExternalReference buildExternalReference(@NonNull final I_S_ExternalReference record)
	{
		final IExternalReferenceType type = extractType(record);
		final IExternalSystem externalSystem = extractSystem(record);

		return ExternalReference.builder()
				.externalReferenceId(ExternalReferenceId.ofRepoId(record.getS_ExternalReference_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.externalReference(record.getExternalReference())
				.externalReferenceType(type)
				.externalSystem(externalSystem)
				.recordId(record.getRecord_ID())
				.version(record.getVersion())
				.externalReferenceUrl(record.getExternalReferenceURL())
				.build();
	}

	private IExternalSystem extractSystem(@NonNull final I_S_ExternalReference record)
	{
		return externalSystems.ofCode(record.getExternalSystem()).orElseThrow(() ->
				new AdempiereException("Unknown ExternalSystem=" + record.getExternalSystem())
						.appendParametersToMessage()
						.setParameter("system", record.getExternalSystem())
						.setParameter("S_ExternalReference", record));
	}

	private IExternalReferenceType extractType(@NonNull final I_S_ExternalReference record)
	{
		return externalReferenceTypes.ofCode(record.getType())
				.orElseThrow(() -> new AdempiereException("Unknown Type=" + record.getType())
						.appendParametersToMessage()
						.setParameter("type", record.getType())
						.setParameter("S_ExternalReference", record));
	}
}
