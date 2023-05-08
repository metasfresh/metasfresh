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

package de.metas.resource;

import de.metas.calendar.util.CalendarDateRange;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.OldAndNewValues;
import org.compiere.model.I_S_Resource_Group_Assignment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Repository
class ResourceGroupAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Stream<ResourceGroupAssignment> query(@NonNull final ResourceGroupAssignmentQuery query)
	{
		return toSqlQueryBuilder(query)
				.create()
				.stream()
				.map(ResourceGroupAssignmentRepository::fromRecord);
	}

	private IQueryBuilder<I_S_Resource_Group_Assignment> toSqlQueryBuilder(@NonNull final ResourceGroupAssignmentQuery query)
	{
		final IQueryBuilder<I_S_Resource_Group_Assignment> sqlQueryBuilder = queryBL.createQueryBuilder(I_S_Resource_Group_Assignment.class)
				.addOnlyActiveRecordsFilter();

		//
		// Filter by Resource IDs
		if (query.getOnlyResourceGroupIds() != null && !query.getOnlyResourceGroupIds().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_S_Resource_Group_Assignment.COLUMNNAME_S_Resource_Group_ID, query.getOnlyResourceGroupIds());
		}

		//
		// Filter by date range
		// the resource assignment shall overlap with our query interval
		if (query.getStartDate() != null || query.getEndDate() != null)
		{
			sqlQueryBuilder.addIntervalIntersection(
					I_S_Resource_Group_Assignment.COLUMNNAME_AssignDateFrom,
					I_S_Resource_Group_Assignment.COLUMNNAME_AssignDateTo,
					query.getStartDate(),
					query.getEndDate());
		}

		return sqlQueryBuilder;
	}

	private static ResourceGroupAssignment fromRecord(@NonNull final I_S_Resource_Group_Assignment record)
	{
		return ResourceGroupAssignment.builder()
				.id(ResourceGroupAssignmentId.ofRepoId(record.getS_Resource_Group_Assignment_ID()))
				.resourceGroupId(ResourceGroupId.ofRepoId(record.getS_Resource_Group_ID()))
				.name(record.getName())
				.description(record.getDescription())
				.dateRange(extractDateRange(record))
				.build();
	}

	private static CalendarDateRange extractDateRange(@NonNull final I_S_Resource_Group_Assignment record)
	{
		return CalendarDateRange.builder()
				.startDate(record.getAssignDateFrom().toInstant())
				.endDate(CoalesceUtil.coalesceSuppliersNotNull(
						() -> TimeUtil.asInstant(record.getAssignDateTo()),
						() -> LocalDate.MAX.atTime(LocalTime.MAX).atZone(SystemTime.zoneId()).toInstant()))
				.allDay(record.isAllDay())
				.build();
	}

	private static void updateRecordFromDateRange(@NonNull final I_S_Resource_Group_Assignment record, @NonNull final CalendarDateRange from)
	{
		record.setAssignDateFrom(Timestamp.from(from.getStartDate()));
		record.setAssignDateTo(Timestamp.from(from.getEndDate()));
		record.setIsAllDay(from.isAllDay());
	}

	public ResourceGroupAssignment create(@NonNull final ResourceGroupAssignmentCreateRequest request)
	{
		final I_S_Resource_Group_Assignment record = InterfaceWrapperHelper.newInstance(I_S_Resource_Group_Assignment.class);
		record.setS_Resource_Group_ID(request.getResourceGroupId().getRepoId());
		updateRecordFromDateRange(record, request.getDateRange());
		record.setName(request.getName());
		record.setDescription(request.getDescription());
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	public OldAndNewValues<ResourceGroupAssignment> changeById(@NonNull final ResourceGroupAssignmentId id, @NonNull final UnaryOperator<ResourceGroupAssignment> mapper)
	{
		final I_S_Resource_Group_Assignment record = getRecordById(id);

		final ResourceGroupAssignment assignment = fromRecord(record);
		final ResourceGroupAssignment changedAssignment = mapper.apply(assignment);

		record.setS_Resource_Group_ID(changedAssignment.getResourceGroupId().getRepoId());
		record.setName(changedAssignment.getName());
		record.setDescription(changedAssignment.getDescription());
		updateRecordFromDateRange(record, changedAssignment.getDateRange());

		InterfaceWrapperHelper.saveRecord(record);

		return OldAndNewValues.ofOldAndNewValues(assignment, changedAssignment);
	}

	@NonNull
	private I_S_Resource_Group_Assignment getRecordById(final @NonNull ResourceGroupAssignmentId id)
	{
		final I_S_Resource_Group_Assignment record = InterfaceWrapperHelper.load(id, I_S_Resource_Group_Assignment.class);
		if (record == null)
		{
			throw new AdempiereException("No resource assignment found for id: " + id);
		}
		return record;
	}

	public void deleteById(@NonNull final ResourceGroupAssignmentId id)
	{
		final I_S_Resource_Group_Assignment record = getRecordById(id);
		InterfaceWrapperHelper.delete(record);
	}
}
