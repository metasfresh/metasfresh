/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.OldAndNewValues;
import org.compiere.model.I_S_ResourceAssignment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Repository
class ResourceAssignmentRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Stream<ResourceAssignment> query(@NonNull final ResourceAssignmentQuery query)
	{
		return toSqlQueryBuilder(query)
				.create()
				.stream()
				.map(ResourceAssignmentRepository::fromRecord);
	}

	private IQueryBuilder<I_S_ResourceAssignment> toSqlQueryBuilder(@NonNull final ResourceAssignmentQuery query)
	{
		final IQueryBuilder<I_S_ResourceAssignment> sqlQueryBuilder = queryBL.createQueryBuilder(I_S_ResourceAssignment.class)
				.addOnlyActiveRecordsFilter();

		//
		// Filter by Resource IDs
		if (query.getOnlyResourceIds() != null && !query.getOnlyResourceIds().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_S_ResourceAssignment.COLUMNNAME_S_Resource_ID, query.getOnlyResourceIds());
		}

		//
		// Filter by date range
		// the resource assignment shall overlap with our query interval
		if (query.getStartDate() != null || query.getEndDate() != null)
		{
			sqlQueryBuilder.addIntervalIntersection(
					I_S_ResourceAssignment.COLUMNNAME_AssignDateFrom,
					I_S_ResourceAssignment.COLUMNNAME_AssignDateTo,
					query.getStartDate(),
					query.getEndDate());
		}

		return sqlQueryBuilder;
	}

	private static ResourceAssignment fromRecord(@NonNull final I_S_ResourceAssignment record)
	{
		return ResourceAssignment.builder()
				.id(ResourceAssignmentId.ofRepoId(record.getS_ResourceAssignment_ID()))
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.name(record.getName())
				.description(record.getDescription())
				.dateRange(extractDateRange(record))
				.build();
	}

	private static CalendarDateRange extractDateRange(@NonNull final I_S_ResourceAssignment record)
	{
		return CalendarDateRange.builder()
				.startDate(record.getAssignDateFrom().toInstant())
				.endDate(CoalesceUtil.coalesceSuppliersNotNull(
						() -> TimeUtil.asInstant(record.getAssignDateTo()),
						() -> LocalDate.MAX.atTime(LocalTime.MAX).atZone(SystemTime.zoneId()).toInstant()))
				.allDay(record.isAllDay())
				.build();
	}

	private static void updateRecordFromDateRange(@NonNull final I_S_ResourceAssignment record, @NonNull final CalendarDateRange from)
	{
		record.setAssignDateFrom(Timestamp.from(from.getStartDate()));
		record.setAssignDateTo(Timestamp.from(from.getEndDate()));
		record.setIsAllDay(from.isAllDay());
	}

	public ResourceAssignment create(@NonNull final ResourceAssignmentCreateRequest request)
	{
		final I_S_ResourceAssignment record = InterfaceWrapperHelper.newInstance(I_S_ResourceAssignment.class);
		record.setS_Resource_ID(request.getResourceId().getRepoId());
		updateRecordFromDateRange(record, request.getDateRange());
		record.setName(request.getName());
		record.setDescription(request.getDescription());
		record.setQty(BigDecimal.ONE); // backward compatibility
		record.setIsConfirmed(false);
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	public OldAndNewValues<ResourceAssignment> changeById(@NonNull final ResourceAssignmentId id, @NonNull final UnaryOperator<ResourceAssignment> mapper)
	{
		final I_S_ResourceAssignment record = getRecordById(id);

		final ResourceAssignment initialResourceAssignment = fromRecord(record);
		final ResourceAssignment changedResourceAssignment = mapper.apply(initialResourceAssignment);
		record.setS_Resource_ID(changedResourceAssignment.getResourceId().getRepoId());
		record.setName(changedResourceAssignment.getName());
		record.setDescription(changedResourceAssignment.getDescription());

		updateRecordFromDateRange(record, changedResourceAssignment.getDateRange());

		InterfaceWrapperHelper.saveRecord(record);

		return OldAndNewValues.ofOldAndNewValues(initialResourceAssignment, changedResourceAssignment);
	}

	@NonNull
	private I_S_ResourceAssignment getRecordById(final @NonNull ResourceAssignmentId id)
	{
		final I_S_ResourceAssignment record = InterfaceWrapperHelper.load(id, I_S_ResourceAssignment.class);
		if (record == null)
		{
			throw new AdempiereException("No resource assignment found for id: " + id);
		}
		return record;
	}

	public void deleteById(@NonNull final ResourceAssignmentId id)
	{
		final I_S_ResourceAssignment record = getRecordById(id);
		InterfaceWrapperHelper.delete(record);
	}
}
