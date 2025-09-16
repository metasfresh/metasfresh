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

package de.metas.serviceprovider.timebooking.importer.failed;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystem;
import de.metas.serviceprovider.model.I_S_FailedTimeBooking;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.serviceprovider.TestConstants.MOCK_ERROR_MESSAGE;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_SYSTEM_GITHUB;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_SYSTEM_EVERHOUR;
import static de.metas.serviceprovider.TestConstants.MOCK_JSON_VALUE;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FailedTimeBookingRepositoryTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final FailedTimeBookingRepository failedTimeBookingRepository = new FailedTimeBookingRepository(queryBL);

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void save()
	{
		//given
		final FailedTimeBooking mockFailedTimeBooking = getMockFailedTimeBooking(MOCK_EXTERNAL_SYSTEM_GITHUB);

		//when
		final FailedTimeBookingId failedTimeBookingId = failedTimeBookingRepository.save(mockFailedTimeBooking);

		final FailedTimeBooking storedFailedTimeBooking = failedTimeBookingRepository
				.getOptionalByExternalIdAndSystem(mockFailedTimeBooking.getExternalSystem(), mockFailedTimeBooking.getExternalId()).get();

		//then
		final FailedTimeBooking mockFailedTBookingWithId = mockFailedTimeBooking.toBuilder().failedTimeBookingId(failedTimeBookingId).build();

		assertThat(storedFailedTimeBooking).isEqualTo(mockFailedTBookingWithId);
	}

	@Test
	public void delete()
	{
		//given
		final FailedTimeBooking mockFailedTimeBooking = getMockFailedTimeBooking(MOCK_EXTERNAL_SYSTEM_GITHUB);

		final FailedTimeBookingId failedTimeBookingId = failedTimeBookingRepository.save(mockFailedTimeBooking);

		//when
		failedTimeBookingRepository.delete(failedTimeBookingId);

		//then
		assertThatThrownBy(() -> InterfaceWrapperHelper.load(failedTimeBookingId, I_S_FailedTimeBooking.class))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("de.metas.serviceprovider.model.I_S_FailedTimeBooking, id=" + failedTimeBookingId.getRepoId());
	}

	@Test
	public void listBySystem()
	{
		//given
		final FailedTimeBooking failedTimeBookingSys = getMockFailedTimeBooking(MOCK_EXTERNAL_SYSTEM_GITHUB);
		final FailedTimeBooking failedTimeBookingSys1 = getMockFailedTimeBooking(MOCK_EXTERNAL_SYSTEM_EVERHOUR);

		final FailedTimeBookingId failedTimeBookingIdSys = failedTimeBookingRepository.save(failedTimeBookingSys);

		failedTimeBookingRepository.save(failedTimeBookingSys1);
		final FailedTimeBooking failedTimeBookingSysWithId = failedTimeBookingSys.toBuilder().failedTimeBookingId(failedTimeBookingIdSys).build();

		//when
		final ImmutableList<FailedTimeBooking> failedTimeBookings = failedTimeBookingRepository.listBySystem(MOCK_EXTERNAL_SYSTEM_GITHUB);

		//then
		assertThat(failedTimeBookings)
				.isNotNull()
				.hasSize(1)
				.containsExactly(failedTimeBookingSysWithId);
	}

	private FailedTimeBooking getMockFailedTimeBooking(final ExternalSystem externalSystem)
	{
		return FailedTimeBooking
				.builder()
				.orgId(MOCK_ORG_ID)
				.errorMsg(MOCK_ERROR_MESSAGE)
				.externalId(MOCK_EXTERNAL_ID)
				.externalSystem(externalSystem)
				.jsonValue(MOCK_JSON_VALUE)
				.build();
	}
}