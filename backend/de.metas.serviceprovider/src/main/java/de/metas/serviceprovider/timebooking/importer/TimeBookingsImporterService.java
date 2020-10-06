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

package de.metas.serviceprovider.timebooking.importer;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_AD_User;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.ImportQueue;
import de.metas.serviceprovider.external.ExternalId;
import de.metas.serviceprovider.external.reference.ExternalReference;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueRepository;
import de.metas.serviceprovider.timebooking.TimeBooking;
import de.metas.serviceprovider.timebooking.TimeBookingId;
import de.metas.serviceprovider.timebooking.TimeBookingRepository;
import de.metas.serviceprovider.timebooking.importer.failed.FailedTimeBooking;
import de.metas.serviceprovider.timebooking.importer.failed.FailedTimeBookingRepository;
import de.metas.user.api.IUserDAO;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.time.HmmUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static de.metas.serviceprovider.timebooking.importer.ImportConstants.IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX;

@Service
public class TimeBookingsImporterService
{
	private static final Logger log = LogManager.getLogger(TimeBookingsImporterService.class);

	private final ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue;
	private final ExternalReferenceRepository externalReferenceRepository;
	private final TimeBookingRepository timeBookingRepository;
	private final IssueRepository issueRepository;
	private final ITrxManager trxManager;
	private final FailedTimeBookingRepository failedTimeBookingRepository;

	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	public TimeBookingsImporterService(final ImportQueue<ImportTimeBookingInfo> timeBookingImportQueue, final ExternalReferenceRepository externalReferenceRepository, final TimeBookingRepository timeBookingRepository, final IssueRepository issueRepository, final ITrxManager trxManager, final FailedTimeBookingRepository failedTImeBookingRepository)
	{
		this.timeBookingImportQueue = timeBookingImportQueue;
		this.externalReferenceRepository = externalReferenceRepository;
		this.timeBookingRepository = timeBookingRepository;
		this.issueRepository = issueRepository;
		this.trxManager = trxManager;
		this.failedTimeBookingRepository = failedTImeBookingRepository;
	}

	public void importTimeBookings(@NonNull final TimeBookingsImporter timeBookingsImporter,
			                       @NonNull final ImportTimeBookingsRequest request)
	{
		final CompletableFuture completableFuture =
				CompletableFuture.runAsync(() -> timeBookingsImporter.importTimeBookings(request));

		while (!completableFuture.isDone() || !timeBookingImportQueue.isEmpty())
		{
			final ImmutableList<ImportTimeBookingInfo> importTimeBookingInfos = timeBookingImportQueue.drainAll();
			importTimeBookingInfos.forEach(timeBookingInfo -> trxManager.runInNewTrx( () -> importTimeBooking(timeBookingInfo)));
		}

		if (completableFuture.isCompletedExceptionally())
		{
				extractAndPropagateAdempiereException(completableFuture);
		}
	}

	private void importTimeBooking(@NonNull final ImportTimeBookingInfo importTimeInfo)
	{
		try
		{
			final boolean isNewRecord = importTimeInfo.getTimeBookingId() == null;

			final IssueEntity issueEntity = issueRepository.getById(importTimeInfo.getIssueId());

			checkIfTheEffortCanBeBooked(importTimeInfo, issueEntity);

			final TimeBooking timeBooking = TimeBooking
					.builder()
					.timeBookingId(importTimeInfo.getTimeBookingId())
					.issueId(importTimeInfo.getIssueId())
					.performingUserId(importTimeInfo.getPerformingUserId())
					.orgId(issueEntity.getOrgId())
					.bookedDate(importTimeInfo.getBookedDate())
					.bookedSeconds(importTimeInfo.getBookedSeconds())
					.hoursAndMins(HmmUtils.secondsToHmm(importTimeInfo.getBookedSeconds()))
					.build();

			final TimeBookingId timeBookingId = timeBookingRepository.save(timeBooking);

			if (isNewRecord)
			{
				insertTimeBookingExternalRef(timeBookingId, importTimeInfo.getExternalTimeBookingId(), timeBooking.getOrgId());
			}

			deleteCorrespondingFailedRecordIfExists(importTimeInfo.getExternalTimeBookingId());
		}
		catch (final Exception e)
		{
			Loggables.withLogger(log, Level.ERROR)
					.addLog(" {} *** Error while importing timeBooking: {}, errorMessage: {}",
							IMPORT_TIME_BOOKINGS_LOG_MESSAGE_PREFIX, importTimeInfo.toString(), e.getMessage(), e);

			storeFailed(importTimeInfo, e.getMessage());
		}
	}

	private void insertTimeBookingExternalRef(@NonNull final TimeBookingId timeBookingId,
											  @NonNull final ExternalId externalId,
			                                  @NonNull final OrgId orgId)
	{
		final ExternalReference externalReference = ExternalReference.builder()
				.orgId(orgId)
				.externalReference(externalId.getId())
				.recordId(timeBookingId.getRepoId())
				.externalSystem(externalId.getExternalSystem())
				.externalReferenceType(ExternalReferenceType.TIME_BOOKING_ID)
				.build();

		externalReferenceRepository.save(externalReference);
	}

	private void deleteCorrespondingFailedRecordIfExists(@NonNull final ExternalId externalId)
	{
		failedTimeBookingRepository.getOptionalByExternalIdAndSystem(externalId.getExternalSystem(), externalId.getId())
				.ifPresent(failedTimeBooking -> failedTimeBookingRepository.delete(failedTimeBooking.getFailedTimeBookingId()));
	}

	private void extractAndPropagateAdempiereException(final CompletableFuture completableFuture)
	{
		try
		{
			completableFuture.get();
		}
		catch (final ExecutionException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex.getCause());
		}
		catch (final InterruptedException ex1)
		{
			throw AdempiereException.wrapIfNeeded(ex1);
		}
	}

	private void checkIfTheEffortCanBeBooked(@NonNull final ImportTimeBookingInfo importTimeBookingInfo, @NonNull final IssueEntity targetIssue)
	{
		if (!targetIssue.isProcessed() || targetIssue.getProcessedTimestamp() == null)
		{
			return;//nothing else to check ( issues that are not processed yet are used for booking effort )
		}

		final LocalDate effortBookedDate = TimeUtil.asLocalDate(importTimeBookingInfo.getBookedDate());
		final LocalDate processedIssueDate = TimeUtil.asLocalDate(targetIssue.getProcessedTimestamp());

		final boolean effortWasBookedOnAProcessedIssue = effortBookedDate.isAfter(processedIssueDate);

		if (effortWasBookedOnAProcessedIssue)
		{
			final I_AD_User performingUser = userDAO.getById(importTimeBookingInfo.getPerformingUserId());

			throw new AdempiereException("Time bookings cannot be added for already processed issues!")
					.appendParametersToMessage()
					.setParameter("ImportTimeBookingInfo", importTimeBookingInfo)
					.setParameter("Performing user", performingUser.getName())
					.setParameter("Note", "This FailedTimeBooking record will not be automatically deleted!");
		}
	}

	private void storeFailed(@NonNull final ImportTimeBookingInfo importTimeBookingInfo, @NonNull final String errorMsg)
	{

		final FailedTimeBooking failedTimeBooking = FailedTimeBooking.builder()
				.externalId(importTimeBookingInfo.getExternalTimeBookingId().getId())
				.externalSystem(importTimeBookingInfo.getExternalTimeBookingId().getExternalSystem())
				.errorMsg(errorMsg)
				.build();

		failedTimeBookingRepository.save(failedTimeBooking);
	}
}
