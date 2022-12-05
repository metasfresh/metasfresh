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
import com.google.common.collect.ImmutableList;
import de.metas.externalreference.ExternalId;
import de.metas.externalreference.ExternalReferenceQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.issue.tracking.everhour.api.EverhourClient;
import de.metas.issue.tracking.everhour.api.model.GetTeamTimeRecordsRequest;
import de.metas.issue.tracking.everhour.api.model.Task;
import de.metas.issue.tracking.everhour.api.model.TimeRecord;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.everhour.task.TimeBookingTask;
import de.metas.serviceprovider.everhour.user.EverhourUserId;
import de.metas.serviceprovider.everhour.user.UserImporterService;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.github.service.GithubIssueService;
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
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

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
	private final ITrxManager trxManager;
	private final UserImporterService userImporterService;
	private final GithubIssueService githubIssueService;

	public EverhourImporterService(
			@NonNull final EverhourClient everhourClient,
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue,
			@NonNull final FailedTimeBookingRepository failedTimeBookingRepository,
			@NonNull final ObjectMapper objectMapper,
			@NonNull final ITrxManager trxManager,
			@NonNull final UserImporterService userImporterService,
			@NonNull final GithubIssueService githubIssueService)
	{
		this.everhourClient = everhourClient;
		this.externalReferenceRepository = externalReferenceRepository;
		this.timeBookingImportQueue = timeBookingImportQueue;
		this.failedTimeBookingRepository = failedTimeBookingRepository;
		this.objectMapper = objectMapper;
		this.trxManager = trxManager;
		this.userImporterService = userImporterService;
		this.githubIssueService = githubIssueService;
	}

	public void importTimeBookings(@NonNull final ImportTimeBookingsRequest request)
	{
		try
		{
			acquireLock();

			importFailedTimeBookings();

			final LocalDateInterval localDateInterval = LocalDateInterval.of(request.getStartDate(), request.getEndDate());

			final ImmutableList<LocalDateInterval> localDateIntervals = localDateInterval.divideUsingStep(PROCESSING_DATE_INTERVAL_SIZE);
			localDateIntervals
					.stream()
					.map(interval -> buildGetTeamTimeRecordsRequest(request.getAuthToken(), interval))
					.map(everhourClient::getTeamTimeRecords)
					.flatMap(List::stream)
					.filter(timeBooking -> timeBooking.getTask() != null && TimeBookingTask.TimeBookingTaskId.of(timeBooking.getTask().getId()).isGithubTask())
					.forEach(timeBooking -> importTimeBooking(timeBooking, request.getOrgId()));
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

	private void importTimeBooking(
			@NonNull final TimeRecord timeRecord,
			@NonNull final OrgId orgId)
	{
		try
		{
			final TimeBookingTask timeBookingTask = buildTimeBookingTask(timeRecord.getTask());

			final IssueId issueId = resolveIssue(orgId, timeBookingTask)
					.orElseThrow(() -> new AdempiereException("Fail to import Everhour task")
							.appendParametersToMessage()
							.setParameter("taskId", timeBookingTask.getId())
							.setParameter("taskUrl", timeBookingTask.getUrl()));

			final EverhourUserId everhourUserId = EverhourUserId.ofId(timeRecord.getUserId());
			final UserId userId = userImporterService.resolveUser(orgId, everhourUserId);

			final TimeBookingId timeBookingId = TimeBookingId.ofRepoIdOrNull(
					externalReferenceRepository.getReferencedRecordIdOrNullBy(
							ExternalReferenceQuery.builder()
									.orgId(orgId)
									.externalSystem(ExternalSystem.EVERHOUR)
									.externalReference(String.valueOf(timeRecord.getId()))
									.externalReferenceType(ExternalServiceReferenceType.TIME_BOOKING_ID)
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
					.addLog(" {} Exception trying to build ImportTimeBookingInfo! Message: {} ",
							IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX, e.getMessage());

			trxManager.runInNewTrx(() -> storeFailed(timeRecord, e.getMessage(), orgId));
		}
	}

	@NonNull
	private GetTeamTimeRecordsRequest buildGetTeamTimeRecordsRequest(
			@NonNull final String apiKey,
			@NonNull final LocalDateInterval interval)
	{
		return GetTeamTimeRecordsRequest.builder()
				.apiKey(apiKey)
				.from(interval.getStartDate())
				.to(interval.getEndDate())
				.build();
	}

	private void storeFailed(
			@NonNull final TimeRecord timeRecord, @NonNull final String errorMsg, @NonNull OrgId orgId)
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
				.orgId(orgId)
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

		final ImmutableList<FailedTimeBooking> failedTimeBookings = failedTimeBookingRepository.listBySystem(ExternalSystem.EVERHOUR);
		for (FailedTimeBooking failedTimeBooking : failedTimeBookings)
		{
			if (Check.isBlank(failedTimeBooking.getJsonValue()))
			{
				continue;
			}
			importTimeBooking(getTimeRecordFromFailed(failedTimeBooking), failedTimeBooking.getOrgId());
		}

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

	@NonNull
	private Optional<IssueId> resolveIssue(
			@NonNull final OrgId orgId,
			@NonNull final TimeBookingTask timeBookingTask)
	{
		if (timeBookingTask.isSourceUnknown())
		{
			return Optional.empty();
		}

		final Optional<IssueId> existingIssueId = resolveTaskExternalReference(orgId, timeBookingTask.getId());

		if (existingIssueId.isPresent())
		{
			return existingIssueId;
		}

		switch (timeBookingTask.getId().getSource())
		{
			case GITHUB:
				return githubIssueService.importByURL(orgId, timeBookingTask.getUrl());
			default:
				throw new AdempiereException("Unsupported task source!")
						.appendParametersToMessage()
						.setParameter("source", timeBookingTask.getId().getSource())
						.setParameter("rawIdentifier", timeBookingTask.getId().getRawId());
		}
	}

	@NonNull
	private Optional<IssueId> resolveTaskExternalReference(
			@NonNull final OrgId orgId,
			@NonNull final TimeBookingTask.TimeBookingTaskId id)
	{
		if (id.getSource().isUnknown())
		{
			throw new AdempiereException("Cannot resolve a task identifier from unknown source!")
					.appendParametersToMessage()
					.setParameter("rawIdentifier", id.getRawId());
		}

		final ExternalReferenceQuery query = ExternalReferenceQuery.builder()
				.orgId(orgId)
				.externalSystem(id.getSource().getExternalSystem())
				.externalReference(id.getExtractedId())
				.externalReferenceType(ExternalServiceReferenceType.ISSUE_ID)
				.build();

		final IssueId existingIssueId = IssueId.ofRepoIdOrNull(externalReferenceRepository.getReferencedRecordIdOrNullBy(query));

		return Optional.ofNullable(existingIssueId);
	}

	@NonNull
	private static TimeBookingTask buildTimeBookingTask(@NonNull final Task everhourTask)
	{
		final TimeBookingTask.TimeBookingTaskId timeBookingTaskId = TimeBookingTask.TimeBookingTaskId.of(everhourTask.getId());

		return TimeBookingTask.of(timeBookingTaskId, everhourTask.getUrl());
	}
}
