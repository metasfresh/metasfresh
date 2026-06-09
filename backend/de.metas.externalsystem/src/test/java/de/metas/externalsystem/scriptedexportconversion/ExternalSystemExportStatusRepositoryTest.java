/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.externalsystem.scriptedexportconversion;

import de.metas.externalsystem.ExternalSystemExportStatus;
import de.metas.externalsystem.model.I_ExternalSystem_ScriptedExportConversion_Status;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD test for {@link ExternalSystemExportStatusRepository}.
 *
 * <p>R2.1 coverage:
 * <ul>
 *   <li>upsert creates one row then updates-in-place on the same (config,table,record) key</li>
 *   <li>{@link ExternalSystemExportStatusRepository#updateLatestByPInstanceId} applies the operator to the pInstance-bound row</li>
 *   <li>{@link ExternalSystemExportStatusRepository#fromRecord} / {@link ExternalSystemExportStatusRepository#updateRecord} round-trip</li>
 *   <li>httpResponseCode and adIssueId are persisted and reloaded correctly</li>
 *   <li>getConfigsWithNonSentAttemptBySourceRecord filters correctly</li>
 *   <li>getLatestByPInstanceId finds the row by pInstance after upsert</li>
 * </ul>
 *
 * <p>TODO(R2.2): add typed HttpStatus / AdIssueId assertions once VO fields are promoted to typed wrappers.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class ExternalSystemExportStatusRepositoryTest
{
	private ExternalSystemExportStatusRepository repo;
	private ExternalSystemScriptedExportConversionConfigId configId;
	private TableRecordReference sourceRecord;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		repo = ExternalSystemExportStatusRepository.newInstanceForUnitTesting();

		// Create a minimal config ID (no DB record needed for repo tests — we use the int value directly)
		configId = ExternalSystemScriptedExportConversionConfigId.ofRepoId(1001);

		// Source record: M_InOut table + record ID
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
		sourceRecord = TableRecordReference.of(tableId, 5001);
	}

	// -----------------------------------------------------------------------
	// 1. upsert — creates one row, then updates in-place on the same key
	// -----------------------------------------------------------------------

	@Test
	void upsert_createsOneRow()
	{
		final ExternalSystemExportStatusLogEntry entry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(null)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.build();

		final ExternalSystemExportStatusLogEntry saved = repo.upsert(entry);

		// One row should now exist in the DB
		assertThat(saved.getLogId()).isGreaterThan(0);
		final long rowCount = countStatusRows(configId, sourceRecord);
		assertThat(rowCount).isEqualTo(1);
	}

	@Test
	void upsert_updateInPlace_onSameKey()
	{
		// First upsert: Pending, no pInstance
		final ExternalSystemExportStatusLogEntry pendingEntry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(null)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending)
				.build();
		final ExternalSystemExportStatusLogEntry saved = repo.upsert(pendingEntry);
		final int originalLogId = saved.getLogId();

		// Second upsert on same key: Enqueued + pInstance
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(777);
		final ExternalSystemExportStatusLogEntry enqueuedEntry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Enqueued)
				.build();
		final ExternalSystemExportStatusLogEntry updated = repo.upsert(enqueuedEntry);

		// Still only ONE row (in-place update)
		assertThat(countStatusRows(configId, sourceRecord)).isEqualTo(1);

		// The same PK
		assertThat(updated.getLogId()).isEqualTo(originalLogId);

		// Status updated
		final Optional<ExternalSystemExportStatusLogEntry> reloaded = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(reloaded).isPresent();
		assertThat(reloaded.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);
		assertThat(reloaded.get().getPInstanceId()).isEqualTo(pInstanceId);
	}

	// -----------------------------------------------------------------------
	// 2. updateLatestByPInstanceId — applies operator to pInstance-bound row
	// -----------------------------------------------------------------------

	@Test
	void updateLatestByPInstanceId_appliesOperator()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(888);
		final ExternalSystemExportStatusLogEntry entry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Enqueued)
				.build();
		repo.upsert(entry);

		// Apply operator: Enqueued → Sent with httpResponseCode=200
		repo.updateLatestByPInstanceId(pInstanceId,
				e -> e.withStatus(ExternalSystemExportStatus.Sent).withHttpResponseCode(200));

		final Optional<ExternalSystemExportStatusLogEntry> result = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(result).isPresent();
		assertThat(result.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(result.get().getHttpResponseCode()).isEqualTo(200);
	}

	@Test
	void updateLatestByPInstanceId_noopWhenNoRow()
	{
		// Should not throw when no row exists for the pInstance
		repo.updateLatestByPInstanceId(PInstanceId.ofRepoId(99999),
				e -> e.withStatus(ExternalSystemExportStatus.Sent));
		// No assertion needed — just must not throw
	}

	// -----------------------------------------------------------------------
	// 3. fromRecord / updateRecord round-trip
	// -----------------------------------------------------------------------

	@Test
	void fromRecord_updateRecord_roundTrip()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(999);

		// Build VO
		final ExternalSystemExportStatusLogEntry original = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Error)
				.httpResponseCode(500)
				.adIssueId(42)
				.statusMessage("Something went wrong")
				.isResend(true)
				.build();

		// updateRecord into a fresh record
		final I_ExternalSystem_ScriptedExportConversion_Status record =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Status.class);
		ExternalSystemExportStatusRepository.updateRecord(record, original);
		InterfaceWrapperHelper.saveRecord(record);

		// fromRecord back to VO
		final ExternalSystemExportStatusLogEntry reloaded = ExternalSystemExportStatusRepository.fromRecord(record);

		assertThat(reloaded.getPInstanceId()).isEqualTo(pInstanceId);
		assertThat(reloaded.getConfigId()).isEqualTo(configId);
		assertThat(reloaded.getSourceRecord()).isEqualTo(sourceRecord);
		assertThat(reloaded.getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(reloaded.getHttpResponseCode()).isEqualTo(500);
		assertThat(reloaded.getAdIssueId()).isEqualTo(42);
		assertThat(reloaded.getStatusMessage()).isEqualTo("Something went wrong");
		assertThat(reloaded.isResend()).isTrue();
	}

	// -----------------------------------------------------------------------
	// 4. httpResponseCode and adIssueId persisted correctly
	// -----------------------------------------------------------------------

	@Test
	void upsert_persists_httpResponseCode_and_adIssueId()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1234);
		final ExternalSystemExportStatusLogEntry entry = ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Error)
				.httpResponseCode(503)
				.adIssueId(77)
				.statusMessage("service unavailable")
				.build();

		repo.upsert(entry);

		final Optional<ExternalSystemExportStatusLogEntry> loaded = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(loaded).isPresent();
		assertThat(loaded.get().getHttpResponseCode()).isEqualTo(503);
		assertThat(loaded.get().getAdIssueId()).isEqualTo(77);
		assertThat(loaded.get().getStatusMessage()).isEqualTo("service unavailable");
	}

	// -----------------------------------------------------------------------
	// 5. getConfigsWithNonSentAttemptBySourceRecord — filters correctly
	// -----------------------------------------------------------------------

	@Test
	void getConfigsWithNonSentAttempt_returnsNonSentConfig()
	{
		// Config 1 in Error state (non-Sent)
		repo.upsert(ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Error)
				.build());

		final ExternalSystemScriptedExportConversionConfigId configId2 = ExternalSystemScriptedExportConversionConfigId.ofRepoId(1002);
		// Config 2 in Sent state
		repo.upsert(ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.configId(configId2)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Sent)
				.build());

		final List<ExternalSystemScriptedExportConversionConfigId> nonSent =
				repo.getConfigsWithNonSentAttemptBySourceRecord(sourceRecord);

		assertThat(nonSent).containsExactly(configId);
		assertThat(nonSent).doesNotContain(configId2);
	}

	// -----------------------------------------------------------------------
	// 6. getLatestByPInstanceId — returns the single row matching the pInstance
	// -----------------------------------------------------------------------

	@Test
	void getLatestByPInstanceId_returnsRow_afterUpsert()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(4242);

		repo.upsert(ExternalSystemExportStatusLogEntry.builder()
				.logId(0)
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Sent)
				.httpResponseCode(200)
				.build());

		final Optional<ExternalSystemExportStatusLogEntry> result = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(result).isPresent();
		assertThat(result.get().getPInstanceId()).isEqualTo(pInstanceId);
		assertThat(result.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(result.get().getHttpResponseCode()).isEqualTo(200);
	}

	@Test
	void getLatestByPInstanceId_returnsEmpty_whenNoRow()
	{
		final Optional<ExternalSystemExportStatusLogEntry> result =
				repo.getLatestByPInstanceId(PInstanceId.ofRepoId(88888));
		assertThat(result).isEmpty();
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private long countStatusRows(
			final ExternalSystemScriptedExportConversionConfigId cfgId,
			final TableRecordReference srcRecord)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_ExternalSystem_ScriptedExportConversion_Status.class)
				.addEqualsFilter(
						I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID,
						cfgId.getRepoId())
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_AD_Table_ID, srcRecord.getAD_Table_ID())
				.addEqualsFilter(I_ExternalSystem_ScriptedExportConversion_Status.COLUMNNAME_Record_ID, srcRecord.getRecord_ID())
				.create()
				.count();
	}
}
