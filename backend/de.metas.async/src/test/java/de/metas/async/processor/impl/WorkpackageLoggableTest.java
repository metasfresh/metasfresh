package de.metas.async.processor.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.api.IWorkpackageLogsRepository;
import de.metas.async.api.WorkpackageLogEntry;
import de.metas.error.AdIssueId;
import de.metas.user.UserId;
import lombok.Setter;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2020 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
class WorkpackageLoggableTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void addLog_and_flush()
	{
		final MockedWorkpackageLogsRepository logsRepository = new MockedWorkpackageLogsRepository();
		final WorkpackageLoggable loggable = WorkpackageLoggable.builder()
				.logsRepository(logsRepository)
				.workpackageId(QueueWorkPackageId.ofRepoId(1))
				.adClientId(ClientId.METASFRESH)
				.userId(UserId.METASFRESH)
				.bufferSize(5)
				.build();

		IntStream.range(1, 8).forEach(i -> loggable.addLog("msg-" + i));
		assertThat(logsRepository.getLogMessagesFlushed())
				.isEqualTo(ImmutableList.of(
						ImmutableList.of("msg-1", "msg-2", "msg-3", "msg-4", "msg-5") //
				));

		IntStream.range(8, 15).forEach(i -> loggable.addLog("msg-" + i));
		assertThat(logsRepository.getLogMessagesFlushed())
				.isEqualTo(ImmutableList.of(
						ImmutableList.of("msg-1", "msg-2", "msg-3", "msg-4", "msg-5"), //
						ImmutableList.of("msg-6", "msg-7", "msg-8", "msg-9", "msg-10") //
				));

		loggable.flush();
		assertThat(logsRepository.getLogMessagesFlushed())
				.isEqualTo(ImmutableList.of(
						ImmutableList.of("msg-1", "msg-2", "msg-3", "msg-4", "msg-5"), //
						ImmutableList.of("msg-6", "msg-7", "msg-8", "msg-9", "msg-10"), //
						ImmutableList.of("msg-11", "msg-12", "msg-13", "msg-14") //
				));
	}

	@Test
	void saveLogsFailure()
	{
		final MockedWorkpackageLogsRepository logsRepository = new MockedWorkpackageLogsRepository();
		logsRepository.setSaveLogsExceptionSupplier(() -> new AdempiereException("saveLogs programatic failure"));

		final WorkpackageLoggable loggable = WorkpackageLoggable.builder()
				.logsRepository(logsRepository)
				.workpackageId(QueueWorkPackageId.ofRepoId(1))
				.adClientId(ClientId.METASFRESH)
				.userId(UserId.METASFRESH)
				.bufferSize(5)
				.build();

		// assume no log entries were flushed BUT the loggable didn't fail
		IntStream.range(1, 8).forEach(i -> loggable.addLog("msg-" + i));
		assertThat(logsRepository.getLogMessagesFlushed()).isEqualTo(ImmutableList.of());
	}

	@Test
	void addLog_with_Throwable()
	{
		final MockedWorkpackageLogsRepository logsRepository = new MockedWorkpackageLogsRepository();
		final WorkpackageLoggable loggable = WorkpackageLoggable.builder()
				.logsRepository(logsRepository)
				.workpackageId(QueueWorkPackageId.ofRepoId(1))
				.adClientId(ClientId.METASFRESH)
				.userId(UserId.METASFRESH)
				.bufferSize(5)
				.build();

		final AdempiereException exception = new AdempiereException("test exception");
		assertThat(exception.getAdIssueId()).isNull();

		loggable.addLog("Message with param1={} and an exception", 1, exception);
		final AdIssueId adIssueId = exception.getAdIssueId();
		assertThat(adIssueId).isNotNull();

		loggable.flush();
		final WorkpackageLogEntry logEntry = logsRepository.getFirstLogEntryFlushed().get();
		assertThat(logEntry.getMessage()).isEqualTo("Message with param1=1 and an exception");
		assertThat(logEntry.getAdIssueId()).isEqualTo(adIssueId);

		final I_AD_Issue adIssueRecord = InterfaceWrapperHelper.load(adIssueId, I_AD_Issue.class);
		assertThat(adIssueRecord).isNotNull();
		assertThat(adIssueRecord.getIssueSummary()).isEqualTo("test exception");
	}

	private static class MockedWorkpackageLogsRepository implements IWorkpackageLogsRepository
	{
		private final ArrayList<List<WorkpackageLogEntry>> logEntriesFlushed = new ArrayList<>();

		@Setter
		private Supplier<RuntimeException> saveLogsExceptionSupplier;

		@Override
		public void saveLogs(List<WorkpackageLogEntry> logEntries)
		{
			if (saveLogsExceptionSupplier != null)
			{
				throw saveLogsExceptionSupplier.get();
			}

			logEntriesFlushed.add(ImmutableList.copyOf(logEntries));
		}

		@Override
		public void deleteLogsInTrx(QueueWorkPackageId workpackageId)
		{
		}

		public List<List<String>> getLogMessagesFlushed()
		{
			return logEntriesFlushed.stream()
					.map(logEntries -> logEntries.stream()
							.map(WorkpackageLogEntry::getMessage)
							.collect(Collectors.toList()))
					.collect(Collectors.toList());
		}

		public Optional<WorkpackageLogEntry> getFirstLogEntryFlushed()
		{
			return logEntriesFlushed.stream()
					.flatMap(List::stream)
					.findFirst();
		}
	}
}
