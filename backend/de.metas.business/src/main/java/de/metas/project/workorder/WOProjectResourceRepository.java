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

package de.metas.project.workorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.calendar.util.CalendarDateRange;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.workorder.calendar.WOProjectResourceQuery;
import de.metas.util.InSetPredicate;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.time.DurationUtils;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Project_WO_Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

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
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, ids)
				.stream()
				.map(WOProjectResourceRepository::fromRecord)
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
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectIds)
				.stream()
				.map(WOProjectResourceRepository::fromRecord)
				.collect(ImmutableListMultimap.toImmutableListMultimap(WOProjectResource::getProjectId, Function.identity()));

		return projectIds.stream()
				.map(projectId -> WOProjectResources.builder()
						.projectId(projectId)
						.resources(byProjectId.get(projectId))
						.build())
				.collect(WOProjectResourcesCollection.collect());
	}

	public ImmutableSet<ResourceId> getResourceIdsByProjectResourceIds(@NonNull final Set<WOProjectResourceId> projectResourceIds)
	{
		if (projectResourceIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableList<Integer> resourceRepoIds = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, projectResourceIds)
				.create()
				.listDistinct(I_C_Project_WO_Resource.COLUMNNAME_S_Resource_ID, Integer.class);

		return ResourceId.ofRepoIds(resourceRepoIds);
	}

	public InSetPredicate<WOProjectResourceId> getProjectResourceIdsPredicate(@NonNull final WOProjectResourceQuery query)
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

	@Nullable
	private IQuery<I_C_Project_WO_Resource> toSqlQuery(@NonNull final WOProjectResourceQuery query)
	{
		final InSetPredicate<ResourceId> resourceIds = query.getResourceIds();
		final InSetPredicate<ProjectId> projectIds = query.getProjectIds();

		if (resourceIds.isNone() || projectIds.isNone())
		{
			return null;
		}

		final IQueryBuilder<I_C_Project_WO_Resource> sqlQuery = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_S_Resource_ID, resourceIds)
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, projectIds);

		if (query.getStartDate() != null || query.getEndDate() != null)
		{
			sqlQuery.addIntervalIntersection(
					I_C_Project_WO_Resource.COLUMNNAME_AssignDateFrom, I_C_Project_WO_Resource.COLUMNNAME_AssignDateTo,
					query.getStartDate(), query.getEndDate());
		}

		return sqlQuery.create();
	}

	private static WOProjectResourceId toWOProjectResourceId(final Map<String, Object> row)
	{
		return WOProjectResourceId.ofRepoId(
				NumberUtils.asInt(row.get(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID)),
				NumberUtils.asInt(row.get(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID)));
	}

	public WOProjectResources getByProjectId(@NonNull final ProjectId projectId)
	{
		return getByProjectIds(ImmutableSet.of(projectId)).getByProjectId(projectId);
	}

	public Stream<WOProjectResource> streamByResourceIds(
			@NonNull final Set<ResourceId> resourceIds,
			@Nullable final Set<ProjectId> onlyProjectIds)
	{
		if (resourceIds.isEmpty())
		{
			return Stream.empty();
		}

		final IQueryBuilder<I_C_Project_WO_Resource> queryBuilder = queryBL.createQueryBuilder(I_C_Project_WO_Resource.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_S_Resource_ID, resourceIds);

		if (onlyProjectIds != null && !onlyProjectIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_ID, onlyProjectIds);
		}

		return queryBuilder.stream()
				.map(WOProjectResourceRepository::fromRecord);
	}

	public static WOProjectResource fromRecord(@NonNull final I_C_Project_WO_Resource record)
	{
		final TemporalUnit durationUnit = WFDurationUnit.ofCode(record.getDurationUnit()).getTemporalUnit();

		return WOProjectResource.builder()
				.id(extractWOProjectResourceId(record))
				.stepId(WOProjectStepId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Step_ID()))
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.dateRange(CalendarDateRange.builder()
						.startDate(record.getAssignDateFrom().toInstant())
						.endDate(record.getAssignDateTo().toInstant())
						.allDay(record.isAllDay())
						.build())
				.durationUnit(durationUnit)
				.duration(DurationUtils.fromBigDecimal(record.getDuration(), durationUnit))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.build();
	}

	private static void updateRecord(final I_C_Project_WO_Resource record, WOProjectResource from)
	{
		// don't change:
		// id
		// projectId
		// stepId
		// resourceId

		record.setAssignDateFrom(Timestamp.from(from.getDateRange().getStartDate()));
		record.setAssignDateTo(Timestamp.from(from.getDateRange().getEndDate()));
		record.setIsAllDay(from.getDateRange().isAllDay());
		record.setDurationUnit(WFDurationUnit.ofTemporalUnit(from.getDurationUnit()).getCode());
		record.setDuration(DurationUtils.toBigDecimal(from.getDuration(), from.getDurationUnit()));
		record.setDescription(from.getDescription());
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
				.addInArrayFilter(I_C_Project_WO_Resource.COLUMNNAME_C_Project_WO_Resource_ID, projectResourceIds)
				.forEach(record -> {
					final WOProjectResource projectResource = fromRecord(record);
					final WOProjectResource projectResourceChanged = mapper.apply(projectResource);
					if (!Objects.equals(projectResource, projectResourceChanged))
					{
						updateRecord(record, projectResourceChanged);
						InterfaceWrapperHelper.saveRecord(record);
					}
				});
	}
}
