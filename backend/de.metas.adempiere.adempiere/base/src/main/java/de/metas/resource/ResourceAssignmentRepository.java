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

import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_ResourceAssignment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

@Repository
public class ResourceAssignmentRepository
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
		final IQueryBuilder<I_S_ResourceAssignment> sqlQueryBuilder = queryBL.createQueryBuilder(I_S_ResourceAssignment.class);

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
			final ICompositeQueryFilter<I_S_ResourceAssignment> rangeFilters = sqlQueryBuilder.addCompositeQueryFilter();
			rangeFilters.setJoinOr();
			rangeFilters.addBetweenFilter(I_S_ResourceAssignment.COLUMNNAME_AssignDateFrom, query.getStartDate(), query.getEndDate());
			rangeFilters.addBetweenFilter(I_S_ResourceAssignment.COLUMNNAME_AssignDateTo, query.getStartDate(), query.getEndDate());

			//                                 +-------- RA -----+
			//                                         +------ Q -----+

			// TODO .............
		}

		return sqlQueryBuilder;
	}

	private static ResourceAssignment fromRecord(final I_S_ResourceAssignment record)
	{
		return ResourceAssignment.builder()
				.id(ResourceAssignmentId.ofRepoId(record.getS_ResourceAssignment_ID()))
				.resourceId(ResourceId.ofRepoId(record.getS_Resource_ID()))
				.startDate(TimeUtil.asZonedDateTime(record.getAssignDateFrom()))
				.endDate(CoalesceUtil.coalesceSuppliers(
						() -> TimeUtil.asZonedDateTime(record.getAssignDateTo()),
						() -> LocalDate.MAX.atTime(LocalTime.MAX).atZone(SystemTime.zoneId())))
				.name(record.getName())
				.description(record.getDescription())
				.build();
	}

	public ResourceAssignment create(@NonNull final ResourceAssignmentCreateRequest request)
	{
		final I_S_ResourceAssignment record = InterfaceWrapperHelper.newInstance(I_S_ResourceAssignment.class);
		record.setS_Resource_ID(request.getResourceId().getRepoId());
		record.setAssignDateFrom(TimeUtil.asTimestamp(request.getStartDate()));
		record.setAssignDateTo(TimeUtil.asTimestamp(request.getEndDate()));
		record.setName(request.getName());
		record.setDescription(request.getDescription());
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}
}
