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

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import de.metas.issue.tracking.everhour.api.EverhourClient;
import de.metas.issue.tracking.everhour.api.model.GetTeamTimeRecordsRequest;
import de.metas.issue.tracking.everhour.api.model.TimeRecord;
import de.metas.logging.LogManager;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.external.reference.GetReferencedIdRequest;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.timebooking.TimeBookingId;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingInfo;
import de.metas.serviceprovider.timebooking.importer.ImportTimeBookingsRequest;
import de.metas.serviceprovider.timebooking.importer.TimeBookingsImporter;
import de.metas.serviceprovider.timebooking.importer.failed.FailedTimeBooking;
import de.metas.serviceprovider.timebooking.importer.failed.FailedTimeBookingRepository;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.time.LocalDateInterval;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;

import static de.metas.issue.tracking.everhour.api.EverhourConstants.TaskIdSource.GITHUB_ID;
import static de.metas.serviceprovider.everhour.EverhourImportConstants.PROCESSING_DATE_INTERVAL_SIZE;
import static de.metas.serviceprovider.timebooking.importer.ImportConstants.IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX;


@Service
public class EverhourImporterService implements TimeBookingsImporter
{
	private static final Logger log = LogManager.getLogger(EverhourImporterService.class);

	private final ReentrantLock lock = new ReentrantLock();

	private final EverhourClient everhourClient;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue;
	private final FailedTimeBookingRepository failedTimeBookingRepository;
	private final ObjectMapper objectMapper;
	private final ITrxManager iTrxManager;

	public EverhourImporterService(final EverhourClient everhourClient, final ExternalReferenceRepository externalReferenceRepository, final ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue, final FailedTimeBookingRepository failedTimeBookingRepository, final ObjectMapper objectMapper, final ITrxManager iTrxManager)
	{
		this.everhourClient = everhourClient;
		this.externalReferenceRepository = externalReferenceRepository;
		this.timeBookingImportQueue = timeBookingImportQueue;
		this.failedTimeBookingRepository = failedTimeBookingRepository;
		this.objectMapper = objectMapper;
		this.iTrxManager = iTrxManager;
	}

	public void importTimeBookings(@NonNull final ImportTimeBookingsRequest request)
	{
		try
		{
			acquireLock();

			importFailedTimeBookings();

			final LocalDateInterval localDateInterval = LocalDateInterval.of(request.getStartDate(), request.getEndDate());

			localDateInterval.divideUsingStep(PROCESSING_DATE_INTERVAL_SIZE)
					.stream()
					.map(interval -> buildGetTeamTimeRecordsRequest(request.getAuthToken(), interval))
					.map(everhourClient::getTeamTimeRecords)
					.flatMap(List::stream)
					.filter(timeRecord -> isGithubID(timeRecord.getTask().getId()))
					.forEach(this::importTimeBooking);
		}
		catch (final Exception e)
		{
			Loggables.withLogger(log, Level.ERROR).addLog(IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX + e.getMessage());

			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("ImportTimeBookingsRequest", request);
		}
		finally
		{
			releaseLock();
		}
	}

	private void importTimeBooking(@NonNull final TimeRecord timeRecord)
	{
		try
		{
			final UserId userId = UserId.ofRepoId(
					externalReferenceRepository.getReferencedRecordIdBy(
							GetReferencedIdRequest.builder()
									.externalSystem(ExternalSystem.EVERHOUR)
									.externalReference(String.valueOf(timeRecord.getUserId()))
									.externalReferenceType(ExternalReferenceType.USER_ID)
									.build()));

			final IssueId issueId = IssueId.ofRepoId(
					externalReferenceRepository.getReferencedRecordIdBy(
							GetReferencedIdRequest.builder()
									.externalSystem(ExternalSystem.GITHUB)
									.externalReference(extractGithubIssueId(timeRecord.getTask().getId()))
									.externalReferenceType(ExternalReferenceType.ISSUE_ID)
									.build()));

			final TimeBookingId timeBookingId = TimeBookingId.ofRepoIdOrNull(
					externalReferenceRepository.getReferencedRecordIdOrNullBy(
							GetReferencedIdRequest.builder()
									.externalSystem(ExternalSystem.EVERHOUR)
									.externalReference(String.valueOf(timeRecord.getId()))
									.externalReferenceType(ExternalReferenceType.TIME_BOOKING_ID)
									.build()));

			final Instant date = LocalDate.parse(timeRecord.getDate()).atStartOfDay(ZoneOffset.UTC).toInstant();

			final ImportTimeBookingInfo importTimeBookingInfo = ImportTimeBookingInfo
					.builder()
					.externalTimeBookingId(ExternalId.of(ExternalSystem.EVERHOUR, timeRecord.getId()))
					.timeBookingId(timeBookingId)
					.performingUserId(userId)
					.issueId(issueId)
					.bookedDate(date)
					.bookedSeconds(timeRecord.getTime())
					.build();

			timeBookingImportQueue.add(importTimeBookingInfo);
		}
		catch (final Exception e)
		{
			Loggables.withLogger(log, Level.ERROR)
					.addLog(" {} Exception while trying to build ImportTimeBookingInfo! Error msg: {} ",
							IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX, e.getMessage());

			iTrxManager.runInNewTrx(() -> storeFailed(timeRecord, e.getMessage()));
		}
	}

	private String extractGithubIssueId(@NonNull final String taskId)
	{
		final Matcher matcher = GITHUB_ID.getPattern().matcher(taskId);

		if (!matcher.matches())
		{
			throw new AdempiereException("GitHub issue ID couldn't be extracted from given taskId!")
					.appendParametersToMessage()
					.setParameter("taskID", taskId);
		}

		return matcher.group(1);
	}

	private boolean isGithubID(@NonNull final String taskId)
	{
		return GITHUB_ID.getPattern().matcher(taskId).matches();
	}

	private GetTeamTimeRecordsRequest buildGetTeamTimeRecordsRequest(@NonNull final String apiKey,
			@NonNull final LocalDateInterval interval)
	{
		return GetTeamTimeRecordsRequest.builder()
				.apiKey(apiKey)
				.from(interval.getStartDate())
				.to(interval.getEndDate())
				.build();
	}

	private void storeFailed(@NonNull final TimeRecord timeRecord, @NonNull final String errorMsg)
	{
		final StringBuilder errorMessage = new StringBuilder(errorMsg);

		String jsonValue = null;
		try
		{
			jsonValue = objectMapper.writeValueAsString(timeRecord);
		}
		catch (final JsonProcessingException e)
		{
			Loggables.withLogger(log, Level.ERROR)
					.addLog(IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX + e.getMessage());

			errorMessage.append("\n Error while trying to write TimeRecord as JSON: ").append(e.getMessage());
		}

		final FailedTimeBooking failedTimeBooking = FailedTimeBooking.builder()
				.jsonValue(jsonValue)
				.externalId(timeRecord.getId())
				.externalSystem(ExternalSystem.EVERHOUR)
				.errorMsg(errorMessage.toString())
				.build();

		failedTimeBookingRepository.save(failedTimeBooking);
	}

	private void importFailedTimeBookings()
	{
		Loggables.withLogger(log, Level.DEBUG).addLog(" {} start importing failed time bookings",
				IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX);

		final Stopwatch stopWatch = Stopwatch.createStarted();

		failedTimeBookingRepository.listBySystem(ExternalSystem.EVERHOUR)
				.stream()
				.filter(failedTimeBooking -> Check.isNotBlank(failedTimeBooking.getJsonValue()))
				.map(this::getTimeRecordFromFailed)
				.forEach(this::importTimeBooking);

		Loggables.withLogger(log, Level.DEBUG).addLog(" {} finished importing failed time bookings! elapsed time: {}",
				IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX, stopWatch);
	}

	private TimeRecord getTimeRecordFromFailed(@NonNull final FailedTimeBooking failedTimeBooking)
	{
		try
		{
			return objectMapper.readValue(failedTimeBooking.getJsonValue(), TimeRecord.class);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed to read value from failed time booking!")
					.appendParametersToMessage()
					.setParameter("failedTimeBooking.getJsonValue()", failedTimeBooking.getJsonValue());
		}
	}

	private void acquireLock()
	{
		final boolean lockAcquired = lock.tryLock();

		if (!lockAcquired)
		{
			throw new AdempiereException("The import is already running!");
		}

		log.debug(" {} EverhourImporterService: lock acquired, starting the import!", IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX);
	}

	private void releaseLock()
	{
		lock.unlock();

		log.debug(" {} EverhourImporterService: lock released!", IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX);
	}
}
