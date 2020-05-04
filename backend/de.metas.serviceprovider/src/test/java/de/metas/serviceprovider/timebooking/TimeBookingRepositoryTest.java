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

import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import static de.metas.serviceprovider.TestConstants.MOCK_BOOKED_SECONDS;
import static de.metas.serviceprovider.TestConstants.MOCK_HOURS_AND_MINUTES;
import static de.metas.serviceprovider.TestConstants.MOCK_INSTANT_FROM_DATE;
import static de.metas.serviceprovider.TestConstants.MOCK_ISSUE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static org.junit.Assert.assertEquals;

public class TimeBookingRepositoryTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final TimeBookingRepository timeBookingRepository = new TimeBookingRepository(queryBL);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void save()
	{
		//given
		final TimeBooking mockTimeBooking = getMockTimeBooking();

		//when
		final TimeBookingId timeBookingId = timeBookingRepository.save(mockTimeBooking);

		final TimeBooking storedTimeBooking = timeBookingRepository.getByIdOptional(timeBookingId).get();

		//then
		final TimeBooking mockTimeBookingWithId = mockTimeBooking.toBuilder().timeBookingId(timeBookingId).build();

		assertEquals(mockTimeBookingWithId, storedTimeBooking);
	}

	private TimeBooking getMockTimeBooking()
	{
		return TimeBooking
				.builder()
				.bookedDate(MOCK_INSTANT_FROM_DATE)
				.bookedSeconds(MOCK_BOOKED_SECONDS)
				.hoursAndMins(MOCK_HOURS_AND_MINUTES)
				.issueId(MOCK_ISSUE_ID)
				.performingUserId(MOCK_USER_ID)
				.orgId(MOCK_ORG_ID)
				.build();
	}
}
