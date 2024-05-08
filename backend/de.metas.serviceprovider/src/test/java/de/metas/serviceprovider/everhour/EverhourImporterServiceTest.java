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

package de.metas.serviceprovider.everhour;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.issue.tracking.everhour.api.EverhourClient;
import de.metas.issue.tracking.everhour.api.model.GetTeamTimeRecordsRequest;
import de.metas.issue.tracking.everhour.api.model.Task;
import de.metas.issue.tracking.everhour.api.model.TimeRecord;
import de.metas.serviceprovider.ImportQueue;
import de.metas.externalreference.ExternalId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingInfo;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingsRequest;
import de.metas.serviceprovider.timebooking.importer.failed.FailedTimeBooking;
import de.metas.serviceprovider.timebooking.importer.failed.FailedTimeBookingRepository;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static de.metas.serviceprovider.TestConstants.MOCK_AUTH_TOKEN;
import static de.metas.serviceprovider.TestConstants.MOCK_BOOKED_SECONDS;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_01;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_07;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_08;
import static de.metas.serviceprovider.TestConstants.MOCK_DATE_2020_03_12;
import static de.metas.serviceprovider.TestConstants.MOCK_ERROR_MESSAGE;
import static de.metas.serviceprovider.TestConstants.MOCK_EV_TASK_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_EXTERNAL_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_GH_TASK_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_ISSUE_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_RECORD_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_USER_ID;
import static de.metas.serviceprovider.timebooking.importer.ImportConstants.IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX;
import static de.metas.serviceprovider.timebooking.importer.ImportConstants.TIME_BOOKING_QUEUE_CAPACITY;
import static org.junit.Assert.assertEquals;

public class EverhourImporterServiceTest
{
	private ExternalReferenceRepository externalReferenceRepository;

	private final ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue =
			new ImportQueue<>(TIME_BOOKING_QUEUE_CAPACITY, IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX);

	private final FailedTimeBookingRepository failedTimeBookingRepository =
			new FailedTimeBookingRepository(Services.get(IQueryBL.class));

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private EverhourClient mockEverhourClient;

	private EverhourImporterService everhourImporterService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();
		externalReferenceTypes.registerType(ExternalUserReferenceType.USER_ID);
		externalReferenceTypes.registerType(ExternalServiceReferenceType.ISSUE_ID);

		final ExternalSystems externalSystems = new ExternalSystems();
		externalSystems.registerExternalSystem(ExternalSystem.EVERHOUR);
		externalSystems.registerExternalSystem(ExternalSystem.GITHUB);

		externalReferenceRepository = new ExternalReferenceRepository(Services.get(IQueryBL.class), externalSystems, externalReferenceTypes);

		mockEverhourClient = Mockito.mock(EverhourClient.class);

		everhourImporterService = new EverhourImporterService(mockEverhourClient, externalReferenceRepository,
				timeBookingImportQueue, failedTimeBookingRepository, objectMapper, trxManager);
	}

	/**
	 * Covers:
	 * 1. importing previously failed time booking
	 * 2. ignoring non github related time bookings
	 * 3. storing failed time booking
	 * 4. storing valid time bookings from different time windows.
	 */
	@Test
	public void importTimeBookings() throws JsonProcessingException
	{
		//given
		final TimeRecord previouslyFailedTimeRecord = getMockTimeRecord(MOCK_DATE_2020_03_07, MOCK_GH_TASK_ID);
		final TimeRecord evTimeRecord = getMockTimeRecord(MOCK_DATE_2020_03_01, MOCK_EV_TASK_ID);
		final TimeRecord ghValidTimeRecord_01_07 = getMockTimeRecord(MOCK_DATE_2020_03_01, MOCK_GH_TASK_ID);
		final TimeRecord ghValidTimeRecord_08_12 = getMockTimeRecord(MOCK_DATE_2020_03_08, MOCK_GH_TASK_ID);
		final TimeRecord ghInvalidTimeRecord = getMockTimeRecord(MOCK_DATE_2020_03_08, MOCK_GH_TASK_ID + "1");

		prepareDataContext(previouslyFailedTimeRecord);

		final ImmutableList<TimeRecord> records_01_07 = ImmutableList.of(evTimeRecord, ghValidTimeRecord_01_07);
		final ImmutableList<TimeRecord> records_08_12 = ImmutableList.of(ghValidTimeRecord_08_12, ghInvalidTimeRecord);

		final GetTeamTimeRecordsRequest getTeamTimeRecordsRequest_01_07 = getMockGetTeamTimeRecordsRequest(MOCK_DATE_2020_03_01, MOCK_DATE_2020_03_07);
		final GetTeamTimeRecordsRequest getTeamTimeRecordsRequest_08_12 = getMockGetTeamTimeRecordsRequest(MOCK_DATE_2020_03_08, MOCK_DATE_2020_03_12);

		Mockito.when(mockEverhourClient.getTeamTimeRecords(getTeamTimeRecordsRequest_01_07)).thenReturn(records_01_07);
		Mockito.when(mockEverhourClient.getTeamTimeRecords(getTeamTimeRecordsRequest_08_12)).thenReturn(records_08_12);

		//when
		everhourImporterService.importTimeBookings(getMockImportTBookingReq());

		//then
		final ImmutableList<ImportTimeBookingInfo> importTimeBookingInfoImmutableList = timeBookingImportQueue.drainAll();

		assertEquals(importTimeBookingInfoImmutableList.size(), 3);

		assertEqual(importTimeBookingInfoImmutableList.get(0), previouslyFailedTimeRecord);
		assertEqual(importTimeBookingInfoImmutableList.get(1), ghValidTimeRecord_01_07);
		assertEqual(importTimeBookingInfoImmutableList.get(2), ghValidTimeRecord_08_12);

		final ImmutableList<FailedTimeBooking> failedTimeBookings = failedTimeBookingRepository.listBySystem(ExternalSystem.EVERHOUR);

		assertEquals(failedTimeBookings.size(), 2);

		if (failedTimeBookings.get(0).getExternalId().equals(previouslyFailedTimeRecord.getId()))
		{
			assertEquals(failedTimeBookings.get(0).getJsonValue(), objectMapper.writeValueAsString(previouslyFailedTimeRecord));
			assertEquals(failedTimeBookings.get(1).getJsonValue(), objectMapper.writeValueAsString(ghInvalidTimeRecord));
		}
		else
		{
			assertEquals(failedTimeBookings.get(0).getJsonValue(), objectMapper.writeValueAsString(ghInvalidTimeRecord));
			assertEquals(failedTimeBookings.get(1).getJsonValue(), objectMapper.writeValueAsString(previouslyFailedTimeRecord));
		}
	}

	private void assertEqual(final ImportTimeBookingInfo timeBookingInfo, final TimeRecord timeRecord)
	{
		assertEquals(timeBookingInfo.getBookedSeconds(), timeRecord.getTime());
		assertEquals(timeBookingInfo.getBookedDate(), LocalDate.parse(timeRecord.getDate()).atStartOfDay(ZoneOffset.UTC).toInstant());
		assertEquals(timeBookingInfo.getIssueId(), MOCK_ISSUE_ID);
		assertEquals(timeBookingInfo.getPerformingUserId(), MOCK_USER_ID);
		assertEquals(timeBookingInfo.getExternalTimeBookingId(), ExternalId.of(ExternalSystem.EVERHOUR, timeRecord.getId()));
	}

	private ImportTimeBookingsRequest getMockImportTBookingReq()
	{
		return ImportTimeBookingsRequest
				.builder()
				.orgId(MOCK_ORG_ID)
				.authToken(MOCK_AUTH_TOKEN)
				.startDate(MOCK_DATE_2020_03_01)
				.endDate(MOCK_DATE_2020_03_12)
				.build();
	}

	private GetTeamTimeRecordsRequest getMockGetTeamTimeRecordsRequest(final LocalDate from, final LocalDate to)
	{
		return GetTeamTimeRecordsRequest
				.builder()
				.from(from)
				.to(to)
				.apiKey(MOCK_AUTH_TOKEN)
				.build();
	}

	private TimeRecord getMockTimeRecord(final LocalDate date, final String taskId)
	{
		return TimeRecord.builder()
				.id(MOCK_EXTERNAL_ID + date.toString())
				.date(date.toString())
				.userId(MOCK_USER_ID.getRepoId())
				.task(Task.builder().id(taskId).build())
				.time(MOCK_BOOKED_SECONDS)
				.build();
	}

	public void prepareDataContext(final TimeRecord previouslyFailedTimeRecord) throws JsonProcessingException
	{
		//1. add external ref for Everhour user
		final ExternalReference userRef = ExternalReference
				.builder()
				.orgId(MOCK_ORG_ID)
				.recordId(MOCK_RECORD_ID)
				.externalReference(String.valueOf(MOCK_USER_ID.getRepoId()))
				.externalSystem(ExternalSystem.EVERHOUR)
				.externalReferenceType(ExternalUserReferenceType.USER_ID)
				.build();

		externalReferenceRepository.save(userRef);

		//2. add external ref for GitHub issue ID
		final ExternalReference issueRef = ExternalReference
				.builder()
				.orgId(MOCK_ORG_ID)
				.recordId(MOCK_RECORD_ID)
				.externalReference(String.valueOf(MOCK_ISSUE_ID.getRepoId()))
				.externalSystem(ExternalSystem.GITHUB)
				.externalReferenceType(ExternalServiceReferenceType.ISSUE_ID)
				.build();

		externalReferenceRepository.save(issueRef);

		//3 add failed time booking
		final FailedTimeBooking failedTimeBooking = FailedTimeBooking
				.builder()
				.orgId(MOCK_ORG_ID)
				.errorMsg(MOCK_ERROR_MESSAGE)
				.externalId(previouslyFailedTimeRecord.getId())
				.externalSystem(ExternalSystem.EVERHOUR)
				.jsonValue(objectMapper.writeValueAsString(previouslyFailedTimeRecord))
				.build();

		failedTimeBookingRepository.save(failedTimeBooking);
	}
}
