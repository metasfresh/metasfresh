/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.vatid;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDCheckRequest;
import de.metas.vatid.VATaxIDCheckService;
import de.metas.vatid.VATaxIDStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_VATaxID_CheckLog;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Drives {@link VATaxIDCheckService} directly and asserts what it persisted: the {@code VATaxID_CheckLog}
 * evidence rows and the denormalised status columns on the parent.
 *
 * <p>The check is invoked <b>explicitly</b> here, not via saving a partner: the save-time (after-commit)
 * trigger is a separate step of the feature and gets its own scenario. Driving the service directly keeps
 * these scenarios about the service's own contract — offline check first, not-supported without a call,
 * de-duplication, evidence, parent refresh.
 */
@RequiredArgsConstructor
public class VATaxIDCheck_StepDef
{
	@NonNull private final VATaxIDCheckService vataxIDCheckService = SpringContextHolder.instance.getBean(VATaxIDCheckService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ISessionBL sessionBL = Services.get(ISessionBL.class);

	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	/** The status {@link VATaxIDCheckService#check(VATaxIDCheckRequest)} returned, per partner identifier. */
	@NonNull private final Map<StepDefDataIdentifier, VATaxIDStatus> returnedStatuses = new HashMap<>();

	/**
	 * Establishes a real {@code AD_Session} on the current (gluecode) thread, the way an interactive
	 * WebUI/API save always has one already — direct-DB step defs such as {@code metasfresh contains
	 * C_BPartners:} otherwise run with no session in {@link Env#getCtx()} at all, which would make the
	 * after-commit trigger's {@code AD_Session_ID} capture vacuously null and unable to prove anything.
	 *
	 * <p>Test setup only: production code (the after-commit trigger) deliberately never creates a session
	 * itself — see {@code ISessionBL#getCurrentOrCreateNewSession} vs. {@code #getCurrentSession} on the
	 * trigger. This step plays the role of "a user is already logged in", which is exactly the
	 * precondition the trigger's capture relies on.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh has a current user session
	 * </pre>
	 */
	@Given("metasfresh has a current user session")
	public void metasfresh_has_a_current_user_session()
	{
		sessionBL.getCurrentOrCreateNewSession(Env.getCtx());
	}

	/**
	 * Removes the {@code VATaxID_CheckLog} rows of one VAT-ID <b>value</b>, so a scenario starts from a
	 * known-empty check history for it.
	 *
	 * <p>Needed because de-duplication is keyed on the VAT-ID value (not on the partner): a row left behind
	 * by an earlier run of this feature on the same, never-reset local database would suppress the very
	 * check under test. In production these rows are legal evidence and are never deleted — this step is
	 * test isolation only, and on a fresh database it is a no-op.
	 *
	 * <p>Any {@code C_BPartner} / {@code C_BPartner_Location} still pointing at one of those rows has its
	 * zoom reference cleared first: it is a real foreign key, so the delete would otherwise be impossible.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given no VATaxID_CheckLog records exist for VATaxID 'DE136695976'
	 * </pre>
	 */
	@Given("no VATaxID_CheckLog records exist for VATaxID {string}")
	public void no_check_log_records_exist(@NonNull final String vataxID)
	{
		final IQuery<I_VATaxID_CheckLog> checkLogQuery = queryBL.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID, vataxID)
				.create();

		// The parents' zoom reference is a real foreign key, so it has to let go before those rows can be
		// deleted. Without this, the second run of this feature against the same (never-reset) local database
		// fails right here: the partner survives from the previous run and still points at its check row.
		clearCheckLogReferencesOnBPartners(checkLogQuery);
		clearCheckLogReferencesOnBPartnerLocations(checkLogQuery);

		checkLogQuery.delete();
	}

	private void clearCheckLogReferencesOnBPartners(@NonNull final IQuery<I_VATaxID_CheckLog> checkLogQuery)
	{
		queryBL.createQueryBuilder(I_C_BPartner.class)
				.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_VATaxID_CheckLog_ID, I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID, checkLogQuery)
				.create()
				.list()
				.forEach(bpartnerRecord -> {
					bpartnerRecord.setVATaxID_CheckLog_ID(0);
					InterfaceWrapperHelper.saveRecord(bpartnerRecord);
				});
	}

	private void clearCheckLogReferencesOnBPartnerLocations(@NonNull final IQuery<I_VATaxID_CheckLog> checkLogQuery)
	{
		queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addInSubQueryFilter(I_C_BPartner_Location.COLUMNNAME_VATaxID_CheckLog_ID, I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID, checkLogQuery)
				.create()
				.list()
				.forEach(locationRecord -> {
					locationRecord.setVATaxID_CheckLog_ID(0);
					InterfaceWrapperHelper.saveRecord(locationRecord);
				});
	}

	/**
	 * Runs the VAT-ID check for each listed partner, using the VAT-ID currently stored on it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) the partner whose VAT-ID is checked
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the VAT-ID check runs for C_BPartner:
	 *   | C_BPartner_ID |
	 *   | bp_vies1      |
	 * </pre>
	 */
	@When("the VAT-ID check runs for C_BPartner:")
	public void vataxID_check_runs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
			final I_C_BPartner bpartnerRecord = bpartnerTable.get(identifier);

			final VATIdentifier vataxID = VATIdentifier.ofNullable(bpartnerRecord.getVATaxID());
			assertThat(vataxID).as("VATaxID of C_BPartner `%s`", identifier).isNotNull();

			final VATaxIDStatus returnedStatus = vataxIDCheckService.check(VATaxIDCheckRequest.builder()
					.bpartnerId(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
					.vataxID(vataxID)
					.build());

			returnedStatuses.put(identifier, returnedStatus);
		});
	}

	/**
	 * Asserts the status the service RETURNED, which is not the same assertion as the status it
	 * PERSISTED — a dedup skip returns the previous result without writing anything, so checking only
	 * the stored column would pass even if the service had returned something else.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID check returned status 'Valid' for C_BPartner 'bp_vies1'
	 * </pre>
	 */
	@Then("the VAT-ID check returned status {string} for C_BPartner {string}")
	public void vataxID_check_returned_status(@NonNull final String expectedStatusCode, @NonNull final String bpartnerIdentifier)
	{
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(bpartnerIdentifier);
		assertThat(returnedStatuses.get(identifier))
				.as("status returned by the VAT-ID check for C_BPartner `%s`", identifier)
				.isEqualTo(VATaxIDStatus.ofCode(expectedStatusCode));
	}

	/**
	 * Asserts the denormalised status columns the parent carries after a check — the columns tax
	 * determination and the business partner windows read.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BPartner_ID</b>       — (required, identifier-ref) the partner to validate<br>
	 *   <b>VATaxIDStatus</b>       — (required) expected status code<br>
	 *   <b>VATaxIDCheckedAt</b>    — (optional) expected check timestamp<br>
	 *   <b>HasTaxCertificate</b>   — (optional) expected {@code VATaxIDStatus.hasTaxCertificate()} of the
	 *                                stored status, i.e. whether the partner still counts as holding a tax
	 *                                certificate<br>
	 *   <b>VATaxID_CheckLog_ID</b> — (optional) {@code true} if the zoom reference to the latest check
	 *                                record must be set
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate C_BPartner VAT-ID status:
	 *   | C_BPartner_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
	 *   | bp_vies1      | Valid         | 2026-06-19T10:00:00 | true              |
	 * </pre>
	 */
	@Then("validate C_BPartner VAT-ID status:")
	public void validate_C_BPartner_VATaxID_status(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
			final I_C_BPartner bpartnerRecord = bpartnerTable.get(identifier);
			// the service writes in its own transaction, so the cached model instance is stale by now
			InterfaceWrapperHelper.refresh(bpartnerRecord);

			final VATaxIDStatus actualStatus = VATaxIDStatus.ofNullableCode(bpartnerRecord.getVATaxIDStatus());

			softly.assertThat(actualStatus)
					.as("VATaxIDStatus of C_BPartner `%s`", identifier)
					.isEqualTo(row.getAsEnum(I_C_BPartner.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.class));

			row.getAsOptionalInstant(I_C_BPartner.COLUMNNAME_VATaxIDCheckedAt)
					.ifPresent(expectedCheckedAt -> softly.assertThat(toInstantOrNull(bpartnerRecord.getVATaxIDCheckedAt()))
							.as("VATaxIDCheckedAt of C_BPartner `%s`", identifier)
							.isEqualTo(expectedCheckedAt));

			row.getAsOptionalBoolean("HasTaxCertificate")
					.ifPresent(expectedHasTaxCertificate -> softly.assertThat(actualStatus != null && actualStatus.hasTaxCertificate())
							.as("hasTaxCertificate() of VATaxIDStatus `%s` of C_BPartner `%s`", actualStatus, identifier)
							.isEqualTo(expectedHasTaxCertificate));

			row.getAsOptionalBoolean(I_C_BPartner.COLUMNNAME_VATaxID_CheckLog_ID)
					.ifPresent(expectedSet -> softly.assertThat(bpartnerRecord.getVATaxID_CheckLog_ID() > 0)
							.as("VATaxID_CheckLog_ID is set on C_BPartner `%s`", identifier)
							.isEqualTo(expectedSet));
		});

		softly.assertAll();
	}

	/**
	 * The {@code C_BPartner_Location} counterpart of {@link #validate_C_BPartner_VATaxID_status(DataTable)}
	 * — same three denormalised columns, same assertions, on the location instead of the partner header.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_BPartner_Location_ID</b> — (required, identifier-ref) the location to validate<br>
	 *   <b>VATaxIDStatus</b>          — (required) expected status code<br>
	 *   <b>VATaxIDCheckedAt</b>       — (optional) expected check timestamp<br>
	 *   <b>HasTaxCertificate</b>      — (optional) expected {@code VATaxIDStatus.hasTaxCertificate()} of the
	 *                                   stored status<br>
	 *   <b>VATaxID_CheckLog_ID</b>    — (optional) {@code true} if the zoom reference to the latest check
	 *                                   record must be set
	 * @cucumber.depends StepDefData: C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate C_BPartner_Location VAT-ID status:
	 *   | C_BPartner_Location_ID | VATaxIDStatus | VATaxIDCheckedAt    | HasTaxCertificate |
	 *   | bpl_vies1              | Valid         | 2026-06-19T10:00:00 | true              |
	 * </pre>
	 */
	@Then("validate C_BPartner_Location VAT-ID status:")
	public void validate_C_BPartner_Location_VATaxID_status(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier identifier = row.getAsIdentifier(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);
			final I_C_BPartner_Location bpartnerLocationRecord = bpartnerLocationTable.get(identifier);
			// the service writes in its own transaction, so the cached model instance is stale by now
			InterfaceWrapperHelper.refresh(bpartnerLocationRecord);

			final VATaxIDStatus actualStatus = VATaxIDStatus.ofNullableCode(bpartnerLocationRecord.getVATaxIDStatus());

			softly.assertThat(actualStatus)
					.as("VATaxIDStatus of C_BPartner_Location `%s`", identifier)
					.isEqualTo(row.getAsEnum(I_C_BPartner_Location.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.class));

			row.getAsOptionalInstant(I_C_BPartner_Location.COLUMNNAME_VATaxIDCheckedAt)
					.ifPresent(expectedCheckedAt -> softly.assertThat(toInstantOrNull(bpartnerLocationRecord.getVATaxIDCheckedAt()))
							.as("VATaxIDCheckedAt of C_BPartner_Location `%s`", identifier)
							.isEqualTo(expectedCheckedAt));

			row.getAsOptionalBoolean("HasTaxCertificate")
					.ifPresent(expectedHasTaxCertificate -> softly.assertThat(actualStatus != null && actualStatus.hasTaxCertificate())
							.as("hasTaxCertificate() of VATaxIDStatus `%s` of C_BPartner_Location `%s`", actualStatus, identifier)
							.isEqualTo(expectedHasTaxCertificate));

			row.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_VATaxID_CheckLog_ID)
					.ifPresent(expectedSet -> softly.assertThat(bpartnerLocationRecord.getVATaxID_CheckLog_ID() > 0)
							.as("VATaxID_CheckLog_ID is set on C_BPartner_Location `%s`", identifier)
							.isEqualTo(expectedSet));
		});

		softly.assertAll();
	}

	/**
	 * Asserts the complete, ordered list of {@code VATaxID_CheckLog} rows of one partner. The row count is
	 * asserted too — that is how a scenario proves no additional check was recorded (de-duplication) as well
	 * as that the evidence row exists at all.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>VATaxID</b>           — (required) the value as checked<br>
	 *   <b>VATaxIDStatus</b>     — (required) the recorded status<br>
	 *   <b>RequestDate</b>       — (optional) when the request was sent<br>
	 *   <b>ResponseDate</b>      — (optional) when the answer arrived<br>
	 *   <b>RequestIdentifier</b> — (optional) the consultation number<br>
	 *   <b>AD_Session_ID</b>     — (optional) {@code true} if the row must carry the acting user session
	 *                              (a save-driven, after-commit check), {@code false} if it must be empty
	 *                              (e.g. a check run explicitly by this feature's own step, or by a process
	 *                              via {@code AD_PInstance_ID} instead)<br>
	 *   <b>AD_PInstance_ID</b>   — (optional) {@code true} if the row must carry the process instance that
	 *                              caused it, {@code false} if it must be empty — a save-driven check never
	 *                              sets this, only a process run does
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate VATaxID_CheckLog records of C_BPartner 'bp_vies1':
	 *   | VATaxID     | VATaxIDStatus | RequestDate         | ResponseDate        | RequestIdentifier |
	 *   | DE136695976 | Valid         | 2026-06-19T10:00:00 | 2026-06-19T10:00:00 | WAPIAAAAWkGa5Fka  |
	 * </pre>
	 */
	@Then("validate VATaxID_CheckLog records of C_BPartner {string}:")
	public void validate_VATaxID_CheckLog_records(@NonNull final String bpartnerIdentifier, @NonNull final DataTable dataTable)
	{
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(bpartnerIdentifier);
		final I_C_BPartner bpartnerRecord = bpartnerTable.get(identifier);

		final ImmutableList<I_VATaxID_CheckLog> checkLogRecords = queryBL
				.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_C_BPartner_ID, bpartnerRecord.getC_BPartner_ID())
				.orderBy(I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID)
				.create()
				.listImmutable(I_VATaxID_CheckLog.class);

		// asserted hard, not softly: the per-row assertions below index into this list
		assertThat(checkLogRecords)
				.as("VATaxID_CheckLog records of C_BPartner `%s`", identifier)
				.hasSize(dataTable.asMaps().size());

		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(dataTable).forEach((row, rowIndex) -> {
			final I_VATaxID_CheckLog checkLogRecord = checkLogRecords.get(rowIndex);

			softly.assertThat(checkLogRecord.getVATaxID())
					.as("VATaxID of check log #%s", rowIndex)
					.isEqualTo(row.getAsString(I_VATaxID_CheckLog.COLUMNNAME_VATaxID));

			softly.assertThat(VATaxIDStatus.ofNullableCode(checkLogRecord.getVATaxIDStatus()))
					.as("VATaxIDStatus of check log #%s", rowIndex)
					.isEqualTo(row.getAsEnum(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.class));

			row.getAsOptionalInstant(I_VATaxID_CheckLog.COLUMNNAME_RequestDate)
					.ifPresent(expectedRequestDate -> softly.assertThat(toInstantOrNull(checkLogRecord.getRequestDate()))
							.as("RequestDate of check log #%s", rowIndex)
							.isEqualTo(expectedRequestDate));

			row.getAsOptionalInstant(I_VATaxID_CheckLog.COLUMNNAME_ResponseDate)
					.ifPresent(expectedResponseDate -> softly.assertThat(toInstantOrNull(checkLogRecord.getResponseDate()))
							.as("ResponseDate of check log #%s", rowIndex)
							.isEqualTo(expectedResponseDate));

			row.getAsOptionalString(I_VATaxID_CheckLog.COLUMNNAME_RequestIdentifier)
					.ifPresent(expectedRequestIdentifier -> softly.assertThat(checkLogRecord.getRequestIdentifier())
							.as("RequestIdentifier of check log #%s", rowIndex)
							.isEqualTo(expectedRequestIdentifier));

			row.getAsOptionalBoolean(I_VATaxID_CheckLog.COLUMNNAME_AD_Session_ID)
					.ifPresent(expectedSet -> softly.assertThat(checkLogRecord.getAD_Session_ID() > 0)
							.as("AD_Session_ID is set on check log #%s", rowIndex)
							.isEqualTo(expectedSet));

			row.getAsOptionalBoolean(I_VATaxID_CheckLog.COLUMNNAME_AD_PInstance_ID)
					.ifPresent(expectedSet -> softly.assertThat(checkLogRecord.getAD_PInstance_ID() > 0)
							.as("AD_PInstance_ID is set on check log #%s", rowIndex)
							.isEqualTo(expectedSet));
		});

		softly.assertAll();
	}

	@Nullable
	private static Instant toInstantOrNull(@Nullable final Timestamp timestamp)
	{
		return timestamp != null ? timestamp.toInstant() : null;
	}
}
