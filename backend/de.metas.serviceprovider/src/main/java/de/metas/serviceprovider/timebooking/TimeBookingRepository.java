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

package de.metas.serviceprovider.timebooking;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class TimeBookingRepository
{
	private final IQueryBL queryBL;

	public TimeBookingRepository(final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	public TimeBookingId save(@NonNull final TimeBooking timeBooking)
	{
		final I_S_TimeBooking record = InterfaceWrapperHelper.loadOrNew(timeBooking.getTimeBookingId(), I_S_TimeBooking.class);

		record.setAD_Org_ID(timeBooking.getOrgId().getRepoId());
		record.setAD_User_Performing_ID(timeBooking.getPerformingUserId().getRepoId());
		record.setS_Issue_ID(timeBooking.getIssueId().getRepoId());

		record.setHoursAndMinutes(timeBooking.getHoursAndMins());
		record.setBookedSeconds(BigDecimal.valueOf(timeBooking.getBookedSeconds()));
		record.setBookedDate(Timestamp.from(timeBooking.getBookedDate()));

		InterfaceWrapperHelper.saveRecord(record);

		return TimeBookingId.ofRepoId(record.getS_TimeBooking_ID());
	}

	public Optional<TimeBooking> getByIdOptional(@NonNull final TimeBookingId timeBookingId)
	{
		return queryBL
				.createQueryBuilder(I_S_TimeBooking.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_TimeBooking.COLUMNNAME_S_TimeBooking_ID, timeBookingId.getRepoId())
				.create()
				.firstOnlyOptional(I_S_TimeBooking.class)
				.map(this::buildTimeBooking);
	}

	public ImmutableList<TimeBooking> getAllByIssueId(@NonNull final IssueId issueId)
	{
		return queryBL
				.createQueryBuilder(I_S_TimeBooking.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_TimeBooking.COLUMNNAME_S_Issue_ID, issueId.getRepoId())
				.create()
				.list()
				.stream()
				.map(this::buildTimeBooking)
				.collect(ImmutableList.toImmutableList());
	}

	private TimeBooking buildTimeBooking(@NonNull final I_S_TimeBooking record)
	{
		return TimeBooking.builder()
				.timeBookingId(TimeBookingId.ofRepoId(record.getS_TimeBooking_ID()))
				.issueId(IssueId.ofRepoId(record.getS_Issue_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.performingUserId(UserId.ofRepoId(record.getAD_User_Performing_ID()))
				.bookedDate(record.getBookedDate().toInstant())
				.bookedSeconds(record.getBookedSeconds().longValue())
				.hoursAndMins(record.getHoursAndMinutes())
				.build();
	}
}
