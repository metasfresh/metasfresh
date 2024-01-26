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

package de.metas.project.workorder.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProjectResourceId;
import de.metas.project.workorder.calendar.WOProjectResourceCalendarQuery;
import de.metas.project.workorder.step.WOProjectStepId;
import de.metas.util.InSetPredicate;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ExternalId;
import de.metas.util.time.DurationUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class WOProjectResourceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ImmutableList<WOProjectResource> getByIds(@NonNull final InSetPredicate<WOProjectResourceId> ids)
	{
		if (ids.isNone())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, ids)
				.stream()
				.map(WOProjectResourceRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public WOProjectResourcesCollection getByProjectIds(@NonNull final Set<ProjectId> projectIds)
	{
		if (projectIds.isEmpty())
		{
			return WOProjectResourcesCollection.EMPTY;
		}

		final ImmutableListMultimap<ProjectId, WOProjectResource> byProjectId = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectIds)
				.stream()
				.map(WOProjectResourceRepository::ofRecord)
				.collect(ImmutableListMultimap.toImmutableListMultimap(WOProjectResource::getProjectId, Function.identity()));

		return projectIds.stream()
				.map(projectId -> WOProjectResources.builder()
						.projectId(projectId)
						.resources(byProjectId.get(projectId))
						.build())
				.collect(WOProjectResourcesCollection.collect());
	}

	public ImmutableSet<ResourceIdAndType> getResourceIdsByProjectResourceIds(@NonNull final Set<WOProjectResourceId> projectResourceIds)
	{
		if (projectResourceIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, projectResourceIds)
				.stream()
				.map(WOProjectResourceRepository::extractResourceIdAndType)
				.collect(ImmutableSet.toImmutableSet());
	}

	public InSetPredicate<WOProjectResourceId> getProjectResourceIdsPredicate(@NonNull final WOProjectResourceCalendarQuery query)
	{
		if (query.isAny())
		{
			return InSetPredicate.any();
		}

		final IQuery<I_C_Project_WO_Resource> sqlQuery = toSqlQuery(query);
		if (sqlQuery == null)
		{
			return InSetPredicate.none();
		}

		final ImmutableSet<WOProjectResourceId> projectResourceIds = sqlQuery
				.listDistinct(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID)
				.stream()
				.map(WOProjectResourceRepository::toWOProjectResourceId)
				.collect(ImmutableSet.toImmutableSet());

		return InSetPredicate.only(projectResourceIds);
	}

	public WOProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		return getByProjectIds(ImmutableSet.of(projectId)).getByProjectId(projectId);
	}

	public Stream<WOProjectResource> streamByResourceIds(
			@NonNull final Set<ResourceIdAndType> resourceIdAndTypes,
			@Nullable final Set<ProjectId> onlyProjectIds)
	{
		if (resourceIdAndTypes.isEmpty())
		{
			return Stream.empty();
		}

		final IQuery<I_C_Project_WO_Resource> sqlQuery = toSqlQuery(WOProjectResourceCalendarQuery.builder()
				.resourceIds(InSetPredicate.only(resourceIdAndTypes))
				.projectIds(onlyProjectIds != null && !onlyProjectIds.isEmpty() ? InSetPredicate.only(onlyProjectIds) : InSetPredicate.any())
				.build());
		if (sqlQuery == null)
		{
			return Stream.empty();
		}

		return sqlQuery.stream()
				.map(WOProjectResourceRepository::ofRecord);
	}

	@NonNull
	public static WOProjectResourceId extractWOProjectResourceId(final @NonNull I_C_Project_WO_Resource record)
	{
		return WOProjectResourceId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Resource_ID());
	}

	public void updateProjectResourcesByIds(
			@NonNull final Set<WOProjectResourceId> projectResourceIds,
			@NonNull final UnaryOperator<WOProjectResource> mapper)
	{
		if (projectResourceIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, projectResourceIds)
				.forEach(record -> {
					final WOProjectResource projectResource = ofRecord(record);
					final WOProjectResource projectResourceChanged = mapper.apply(projectResource);
					if (!Objects.equals(projectResource, projectResourceChanged))
					{
						updateRecord(projectResourceChanged, record, true);
					}
				});
	}

	@NonNull
	public List<WOProjectResource> listByStepIds(@NonNull final Set<WOProjectStepId> woProjectStepIds)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Step_ID, woProjectStepIds)
				.create()
				.stream()
				.map(WOProjectResourceRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<WOProjectResource> updateAll(@NonNull final Collection<WOProjectResource> projectResourceList)
	{
		final Set<Integer> woResourceIds = projectResourceList.stream()
				.map(WOProjectResource::getWoProjectResourceId)
				.map(WOProjectResourceId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_C_Project_WO_Resource> resourceRecords = InterfaceWrapperHelper.loadByIds(woResourceIds, I_C_Project_WO_Resource.class);

		final Map<Integer, I_C_Project_WO_Resource> projectResourceId2Record = Maps.uniqueIndex(resourceRecords, I_C_Project_WO_Resource::getC_Project_WO_Resource_ID);

		final ImmutableList.Builder<WOProjectResource> savedProjectResource = ImmutableList.builder();

		for (final WOProjectResource projectResource : projectResourceList)
		{
			final I_C_Project_WO_Resource resourceRecord = projectResourceId2Record.get(projectResource.getWoProjectResourceId().getRepoId());

			updateRecord(projectResource, resourceRecord, false);

			savedProjectResource.add(ofRecord(resourceRecord));
		}

		return savedProjectResource.build();
	}

	@NonNull
	public WOProjectResource create(@NonNull final CreateWOProjectResourceRequest createWOProjectResourceRequest)
	{
		final I_C_Project_WO_Resource resourceRecord = InterfaceWrapperHelper.newInstance(I_C_Project_WO_Resource.class);
		resourceRecord.setAD_Org_ID(createWOProjectResourceRequest.getOrgId().getRepoId());
		resourceRecord.setExternalId(ExternalId.toValue(createWOProjectResourceRequest.getExternalId()));

		resourceRecord.setAssignDateFrom(TimeUtil.asTimestamp(createWOProjectResourceRequest.getAssignDateFrom()));
		resourceRecord.setAssignDateTo(TimeUtil.asTimestamp(createWOProjectResourceRequest.getAssignDateTo()));

		resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(createWOProjectResourceRequest.getWoProjectStepId()));
		resourceRecord.setS_Resource_ID(ResourceId.toRepoId(createWOProjectResourceRequest.getResourceId()));
		resourceRecord.setC_Project_ID(ProjectId.toRepoId(createWOProjectResourceRequest.getWoProjectStepId().getProjectId()));

		resourceRecord.setWOTestFacilityGroupName(createWOProjectResourceRequest.getTestFacilityGroupName());

		if (createWOProjectResourceRequest.getIsActive() != null)
		{
			resourceRecord.setIsActive(createWOProjectResourceRequest.getIsActive());
		}
		if (createWOProjectResourceRequest.getIsAllDay() != null)
		{
			resourceRecord.setIsAllDay(createWOProjectResourceRequest.getIsAllDay());
		}

		if (createWOProjectResourceRequest.getDuration() == null || createWOProjectResourceRequest.getDurationUnit() == null)
		{
			resourceRecord.setDuration(BigDecimal.ZERO);
			resourceRecord.setDurationUnit(WFDurationUnit.Hour.getCode());
		}
		else
		{
			resourceRecord.setDuration(createWOProjectResourceRequest.getDuration());
			resourceRecord.setDurationUnit(createWOProjectResourceRequest.getDurationUnit().getCode());
		}

		saveRecord(resourceRecord);

		return ofRecord(resourceRecord);
	}

	@NonNull
	public WOProjectResource getById(@NonNull final WOProjectResourceId woProjectResourceId)
	{
		final I_C_Project_WO_Resource resource = Optional.ofNullable(load(woProjectResourceId, I_C_Project_WO_Resource.class))
				.orElseThrow(() -> new AdempiereException("Couldn't find any WOResource for given C_Project_WO_Resource_ID :" + woProjectResourceId.getRepoId()));

		return ofRecord(resource);
	}

	@NonNull
	public Stream<WOProjectResource> streamUnresolvedForProjectIds(@NonNull final ImmutableSet<ProjectId> projectIds)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectIds)
				.create()
				.stream()
				.map(WOProjectResourceRepository::ofRecord)
				.filter(WOProjectResource::isNotFullyResolved);
	}

	@NonNull
	public Stream<WOProjectResource> streamForResourceIds(@NonNull final ImmutableSet<WOProjectResourceId> resourceIds)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, resourceIds)
				.create()
				.stream()
				.map(WOProjectResourceRepository::ofRecord);
	}

	public boolean existsResourcesForProject(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.anyMatch();
	}

	@NonNull
	public static WOProjectResource ofRecord(@NonNull final I_C_Project_WO_Resource resourceRecord)
	{
		final OrgId orgId = OrgId.ofRepoId(resourceRecord.getAD_Org_ID());

		final Instant assignDateFrom = TimeUtil.asInstant(resourceRecord.getAssignDateFrom());
		final Instant assignDateTo = TimeUtil.asInstant(resourceRecord.getAssignDateTo());

		final CalendarDateRange dateRange;
		if (assignDateTo == null || assignDateFrom == null)
		{
			dateRange = null;
		}
		else
		{
			dateRange = CalendarDateRange.builder()
					.startDate(assignDateFrom)
					.endDate(assignDateTo)
					.allDay(resourceRecord.isAllDay())
					.build();
		}

		final ProjectId projectId = ProjectId.ofRepoId(resourceRecord.getC_Project_ID());
		final WFDurationUnit durationUnit = WFDurationUnit.ofCode(resourceRecord.getDurationUnit());

		return WOProjectResource.builder()
				.orgId(orgId)
				.woProjectResourceId(WOProjectResourceId.ofRepoId(projectId, resourceRecord.getC_Project_WO_Resource_ID()))
				.externalId(ExternalId.ofOrNull(resourceRecord.getExternalId()))

				.resourceIdAndType(extractResourceIdAndType(resourceRecord))
				.woProjectStepId(WOProjectStepId.ofRepoId(projectId, resourceRecord.getC_Project_WO_Step_ID()))

				.dateRange(dateRange)
				.duration(DurationUtils.fromBigDecimal(resourceRecord.getDuration(), durationUnit.getTemporalUnit()))
				.durationUnit(durationUnit)

				.isActive(resourceRecord.isActive())

				.budgetProjectId(ProjectId.ofRepoIdOrNull(resourceRecord.getBudget_Project_ID()))
				.projectResourceBudgetId(BudgetProjectResourceId.ofRepoIdOrNull(projectId.getRepoId(), resourceRecord.getC_Project_Resource_Budget_ID()))

				.testFacilityGroupName(resourceRecord.getWOTestFacilityGroupName())
				.description(StringUtils.trimBlankToNull(resourceRecord.getDescription()))
				.resolvedHours(Duration.ofHours(resourceRecord.getResolvedHours()))
				.build();
	}

	public static ResourceIdAndType extractResourceIdAndType(final @NonNull I_C_Project_WO_Resource resourceRecord)
	{
		return ResourceIdAndType.of(
				ResourceId.ofRepoId(resourceRecord.getS_Resource_ID()),
				WOResourceType.optionalOfNullableCode(resourceRecord.getResourceType()).orElse(WOResourceType.MACHINE)
		);
	}

	@Nullable
	private IQuery<I_C_Project_WO_Resource> toSqlQuery(@NonNull final WOProjectResourceCalendarQuery query)
	{
		final InSetPredicate<ResourceIdAndType> resourceIdAndTypes = query.getResourceIds();
		final InSetPredicate<ProjectId> projectIds = query.getProjectIds();

		if (resourceIdAndTypes.isNone() && projectIds.isNone())
		{
			return null;
		}

		final IQueryBuilder<I_C_Project_WO_Resource> sqlQuery = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectIds);

		if (resourceIdAndTypes.isOnly())
		{
			final ICompositeQueryFilter<I_C_Project_WO_Resource> resourcesFilter = sqlQuery.addCompositeQueryFilter().setJoinOr();
			for (final ResourceIdAndType resourceIdAndType : resourceIdAndTypes.toSet())
			{
				resourcesFilter.addCompositeQueryFilter().setJoinAnd()
						.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_S_Resource_ID, resourceIdAndType.getResourceId())
						.addEqualsFilter(I_C_Project_WO_Resource.COLUMNNAME_ResourceType, resourceIdAndType.getType());
			}
		}

		if (query.getStartDate() != null || query.getEndDate() != null)
		{
			sqlQuery.addIntervalIntersection(
					I_C_Project_WO_Resource.COLUMNNAME_AssignDateFrom, I_C_Project_WO_Resource.COLUMNNAME_AssignDateTo,
					query.getStartDate(), query.getEndDate());
		}

		return sqlQuery.create();
	}

	private void updateRecord(
			@NonNull final WOProjectResource projectResource,
			@Nullable final I_C_Project_WO_Resource resourceRecord,
			final boolean isSimulation)
	{
		if (resourceRecord == null)
		{
			throw new AdempiereException("Missing C_Project_WO_Resource for repoId:" + projectResource.getWoProjectResourceId());
		}

		if (!isSimulation)
		{
			resourceRecord.setC_Project_WO_Step_ID(WOProjectStepId.toRepoId(projectResource.getWoProjectStepId()));
			resourceRecord.setS_Resource_ID(projectResource.getResourceIdAndType().getResourceId().getRepoId());
			resourceRecord.setResourceType(projectResource.getResourceIdAndType().getType().getCode());
			resourceRecord.setExternalId(ExternalId.toValue(projectResource.getExternalId()));
			resourceRecord.setC_Project_ID(ProjectId.toRepoId(projectResource.getWoProjectStepId().getProjectId()));
		}

		resourceRecord.setIsActive(Boolean.TRUE.equals(projectResource.getIsActive()));
		resourceRecord.setIsAllDay(projectResource.isAllDay());

		resourceRecord.setAssignDateFrom(projectResource.getStartDate()
				.map(TimeUtil::asTimestamp)
				.orElse(null));

		resourceRecord.setAssignDateTo(projectResource.getEndDate()
				.map(TimeUtil::asTimestamp)
				.orElse(null));

		resourceRecord.setWOTestFacilityGroupName(projectResource.getTestFacilityGroupName());

		resourceRecord.setDuration(DurationUtils.toBigDecimal(projectResource.getDuration(), projectResource.getDurationUnit().getTemporalUnit()));
		resourceRecord.setDurationUnit(projectResource.getDurationUnit().getCode());
		resourceRecord.setResolvedHours((int)projectResource.getResolvedHours().toHours());

		saveRecord(resourceRecord);
	}

	private static WOProjectResourceId toWOProjectResourceId(final Map<String, Object> row)
	{
		return WOProjectResourceId.ofRepoId(
				NumberUtils.asInt(row.get(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID)),
				NumberUtils.asInt(row.get(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID)));
	}
}
