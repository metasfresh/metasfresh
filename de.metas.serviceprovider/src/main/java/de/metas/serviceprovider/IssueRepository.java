package de.metas.serviceprovider;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.time.Duration;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;

import de.metas.bpartner.BPartnerId;
import de.metas.serviceprovider.Issue.IssueBuilder;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class IssueRepository
{
	public Issue getbyId(IssueId id)
	{
		final I_S_Issue issueRecord = loadOutOfTrx(id, I_S_Issue.class);
		return ofRecord(issueRecord);
	}

	private Issue ofRecord(@NonNull final I_S_Issue issueRecord)
	{
		final IssueBuilder result = Issue.builder()
				.billBPartnerId(BPartnerId.ofRepoId(issueRecord.getBill_BPartner_ID()))
				.productId(ServiceProviderConstants.SERVICE_PRODUCT_ID);

		final IssueId issueId = IssueId.ofRepoId(issueRecord.getS_Issue_ID());

		final Duration duration = loadTimeEffort(issueId);
		result.timeEffort(duration);

		return result.build();
	}

	private Duration loadTimeEffort(@NonNull final IssueId issueId)
	{
		Duration duration = Duration.ZERO;
		final List<I_S_TimeBooking> bookingRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_S_TimeBooking.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_TimeBooking.COLUMN_S_Issue_ID, issueId)
				.create()
				.list();
		for (final I_S_TimeBooking bookingRecord : bookingRecords)
		{
			duration = duration
					.plusHours(bookingRecord.getEffortHours())
					.plusMinutes(bookingRecord.getEffortMinutes());
		}
		return duration;
	}
}
