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

import de.metas.error.AdIssueId;
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
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TDD test for {@link ExternalSystemExportStatusRepository}.
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

		configId = ExternalSystemScriptedExportConversionConfigId.ofRepoId(1001);

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(I_M_InOut.Table_Name);
		sourceRecord = TableRecordReference.of(tableId, 5001);
	}

	private ScriptedExportConversionStatusCreateRequest.ScriptedExportConversionStatusCreateRequestBuilder requestBuilder()
	{
		return ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Pending);
	}

	// -----------------------------------------------------------------------
	// upsert — creates one row, then updates in-place on the same key
	// -----------------------------------------------------------------------

	@Test
	void upsert_createsOneRow()
	{
		repo.upsert(requestBuilder().build());

		assertThat(countStatusRows(configId, sourceRecord)).isEqualTo(1);
	}

	@Test
	void upsert_updateInPlace_onSameKey()
	{
		repo.upsert(requestBuilder().status(ExternalSystemExportStatus.Pending).build());

		final PInstanceId pInstanceId = PInstanceId.ofRepoId(777);
		repo.upsert(requestBuilder()
				.pInstanceId(pInstanceId)
				.status(ExternalSystemExportStatus.Enqueued)
				.build());

		assertThat(countStatusRows(configId, sourceRecord)).isEqualTo(1);

		final Optional<ScriptedExportConversionStatus> reloaded = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(reloaded).isPresent();
		assertThat(reloaded.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Enqueued);
		assertThat(reloaded.get().getPInstanceId()).isEqualTo(pInstanceId);
	}

	// -----------------------------------------------------------------------
	// updateLatestByPInstanceId — applies operator to pInstance-bound row
	// -----------------------------------------------------------------------

	@Test
	void updateLatestByPInstanceId_appliesOperator()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(888);
		repo.upsert(requestBuilder()
				.pInstanceId(pInstanceId)
				.status(ExternalSystemExportStatus.Enqueued)
				.build());

		repo.updateLatestByPInstanceId(pInstanceId,
				e -> e.withStatus(ExternalSystemExportStatus.Sent).withHttpResponseCode(HttpStatus.OK));

		final Optional<ScriptedExportConversionStatus> result = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(result).isPresent();
		assertThat(result.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(result.get().getHttpResponseCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void updateLatestByPInstanceId_noopWhenNoRow()
	{
		repo.updateLatestByPInstanceId(PInstanceId.ofRepoId(99999),
				e -> e.withStatus(ExternalSystemExportStatus.Sent));
	}

	// -----------------------------------------------------------------------
	// fromRecord / updateRecord round-trip
	// -----------------------------------------------------------------------

	@Test
	void fromRecord_updateRecord_roundTrip()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(999);

		final ScriptedExportConversionStatus original = ScriptedExportConversionStatus.builder()
				.pInstanceId(pInstanceId)
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Error)
				.httpResponseCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.adIssueId(AdIssueId.ofRepoId(42))
				.statusMessage("Something went wrong")
				.isResend(true)
				.build();

		final I_ExternalSystem_ScriptedExportConversion_Status record =
				InterfaceWrapperHelper.newInstance(I_ExternalSystem_ScriptedExportConversion_Status.class);
		ExternalSystemExportStatusRepository.updateRecord(record, original);
		InterfaceWrapperHelper.saveRecord(record);

		final ScriptedExportConversionStatus reloaded = ExternalSystemExportStatusRepository.fromRecord(record);

		assertThat(reloaded.getPInstanceId()).isEqualTo(pInstanceId);
		assertThat(reloaded.getConfigId()).isEqualTo(configId);
		assertThat(reloaded.getSourceRecord()).isEqualTo(sourceRecord);
		assertThat(reloaded.getStatus()).isEqualTo(ExternalSystemExportStatus.Error);
		assertThat(reloaded.getHttpResponseCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		assertThat(reloaded.getAdIssueId()).isEqualTo(AdIssueId.ofRepoId(42));
		assertThat(reloaded.getStatusMessage()).isEqualTo("Something went wrong");
		assertThat(reloaded.isResend()).isTrue();
	}

	// -----------------------------------------------------------------------
	// httpResponseCode and adIssueId persisted correctly
	// -----------------------------------------------------------------------

	@Test
	void upsert_persists_httpResponseCode_and_adIssueId()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(1234);
		repo.upsert(requestBuilder()
				.pInstanceId(pInstanceId)
				.status(ExternalSystemExportStatus.Error)
				.httpResponseCode(HttpStatus.SERVICE_UNAVAILABLE)
				.adIssueId(AdIssueId.ofRepoId(77))
				.statusMessage("service unavailable")
				.build());

		final Optional<ScriptedExportConversionStatus> loaded = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(loaded).isPresent();
		assertThat(loaded.get().getHttpResponseCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
		assertThat(loaded.get().getAdIssueId()).isEqualTo(AdIssueId.ofRepoId(77));
		assertThat(loaded.get().getStatusMessage()).isEqualTo("service unavailable");
	}

	// -----------------------------------------------------------------------
	// getConfigsWithNonSentAttemptBySourceRecord — filters correctly
	// -----------------------------------------------------------------------

	@Test
	void getConfigsWithNonSentAttempt_returnsNonSentConfig()
	{
		repo.upsert(ScriptedExportConversionStatusCreateRequest.builder()
				.configId(configId)
				.sourceRecord(sourceRecord)
				.status(ExternalSystemExportStatus.Error)
				.build());

		final ExternalSystemScriptedExportConversionConfigId configId2 = ExternalSystemScriptedExportConversionConfigId.ofRepoId(1002);
		repo.upsert(ScriptedExportConversionStatusCreateRequest.builder()
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
	// getLatestByPInstanceId
	// -----------------------------------------------------------------------

	@Test
	void getLatestByPInstanceId_returnsRow_afterUpsert()
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(4242);

		repo.upsert(requestBuilder()
				.pInstanceId(pInstanceId)
				.status(ExternalSystemExportStatus.Sent)
				.httpResponseCode(HttpStatus.OK)
				.build());

		final Optional<ScriptedExportConversionStatus> result = repo.getLatestByPInstanceId(pInstanceId);
		assertThat(result).isPresent();
		assertThat(result.get().getPInstanceId()).isEqualTo(pInstanceId);
		assertThat(result.get().getStatus()).isEqualTo(ExternalSystemExportStatus.Sent);
		assertThat(result.get().getHttpResponseCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void getLatestByPInstanceId_returnsEmpty_whenNoRow()
	{
		final Optional<ScriptedExportConversionStatus> result =
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
