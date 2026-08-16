/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_VATaxID_CheckLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers what {@link VATaxIDCheckService#check(VATaxIDCheckRequest)} does with a verdict once it has one:
 * whether it writes it onto the parent at all, and whether that write refreshes the partner's open orders'
 * tax.
 *
 * <p><b>The order-line-tax refresh.</b> It fires on whichever path wrote a status that DIFFERS from the one
 * the record already held — the save-triggered asynchronous check just as much as the
 * {@code C_BPartner_VATaxID_Check} process, because both converge here. Comparing against the record's
 * STORED status rather than a caller-supplied snapshot is what makes that true: were the comparison to sit in
 * {@code VATaxIDCheckRunService} instead, a save-triggered check would silently consume the transition and the
 * process, running second, would find no change left to react to.
 *
 * <p><b>The stale-verdict guard.</b> The other thing covered here is the one an ASYNCHRONOUS check must
 * never do: write a verdict onto a record whose {@code VATaxID} is no longer the value that verdict was
 * obtained for.
 *
 * <p>{@code VATaxIDCheckWorkpackageProcessor} reads the VAT-ID from the work-package parameter captured at
 * ENQUEUE time, while the parent columns are written onto the record as it stands at PROCESSING time. Between
 * the two the user can clear or correct the field — the trigger correctly enqueues nothing for a cleared
 * value, but the already-queued package still runs — and two in-flight packages for the same record have no
 * ordering guarantee, so a superseded older answer can otherwise overwrite a newer one.
 *
 * <p>{@link VATaxIDParentStatusRepository} and {@link VATaxIDCheckRepository} are the REAL implementations
 * over in-memory records, deliberately: the assertion is that the three parent columns are genuinely not
 * written while the {@code VATaxID_CheckLog} row genuinely is, and a mocked repository would only prove which
 * methods were called.
 */
class VATaxIDCheckServiceTest
{
	/** The value the check is performed for — captured when the work package was enqueued. */
	private static final VATIdentifier CHECKED_VATAXID = VATIdentifier.of("DE240387012");

	/** What the user put on the record afterwards, before the queue got round to the check. */
	private static final VATIdentifier CORRECTED_VATAXID = VATIdentifier.of("DE811569869");

	private VATaxIDConfigRepository configRepository;
	private VATaxIDCheckRepository checkRepository;
	private VATaxIDOnlineChecker onlineChecker;
	private VATaxIDOrderTaxRefresher orderTaxRefresher;
	private VATaxIDCheckService checkService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		configRepository = mock(VATaxIDConfigRepository.class);
		checkRepository = VATaxIDCheckRepository.newInstanceForUnitTesting();
		onlineChecker = mock(VATaxIDOnlineChecker.class);
		orderTaxRefresher = mock(VATaxIDOrderTaxRefresher.class);

		checkService = new VATaxIDCheckService(
				configRepository,
				checkRepository,
				new VATaxIDParentStatusRepository(),
				onlineChecker,
				orderTaxRefresher);
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	/**
	 * @param recheckAfterDays {@code 0} disables de-duplication, so the online checker is actually asked.
	 */
	private void givenConfig(final int recheckAfterDays)
	{
		when(configRepository.getByOrgId(any(OrgId.class))).thenReturn(VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(1_000_000))
				.formatCheckEnabled(true)
				.viesCheckEnabled(true)
				.restApiBaseURL("https://ec.europa.eu/taxation_customs/vies/rest-api")
				.recheckAfterDays(recheckAfterDays)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build());
	}

	@NonNull
	private static BPartnerId givenBPartnerWithVATaxID(@Nullable final VATIdentifier vataxID)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		record.setVATaxID(vataxID != null ? vataxID.getAsString() : null);
		InterfaceWrapperHelper.saveRecord(record);
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	/**
	 * A partner that has ALREADY been checked once and carries the resulting status — the starting point of
	 * every re-check, and the only way to tell a status that changed from one that was merely reconfirmed.
	 */
	@NonNull
	private static BPartnerId givenBPartnerWithVATaxIDAndStatus(
			@NonNull final VATIdentifier vataxID,
			@NonNull final VATaxIDStatus status)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		record.setVATaxID(vataxID.getAsString());
		record.setVATaxIDStatus(status.getCode());
		InterfaceWrapperHelper.saveRecord(record);
		return BPartnerId.ofRepoId(record.getC_BPartner_ID());
	}

	private static void changeVATaxIDTo(@NonNull final BPartnerId bpartnerId, @Nullable final VATIdentifier vataxID)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.load(bpartnerId, I_C_BPartner.class);
		record.setVATaxID(vataxID != null ? vataxID.getAsString() : null);
		InterfaceWrapperHelper.saveRecord(record);
	}

	@NonNull
	private static BPartnerLocationId givenBPartnerLocationWithVATaxID(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final VATIdentifier vataxID)
	{
		final I_C_BPartner_Location record = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setVATaxID(vataxID != null ? vataxID.getAsString() : null);
		InterfaceWrapperHelper.saveRecord(record);
		return BPartnerLocationId.ofRepoId(bpartnerId, record.getC_BPartner_Location_ID());
	}

	private static void changeLocationVATaxIDTo(
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@Nullable final VATIdentifier vataxID)
	{
		final I_C_BPartner_Location record = InterfaceWrapperHelper.load(bpartnerLocationId, I_C_BPartner_Location.class);
		record.setVATaxID(vataxID != null ? vataxID.getAsString() : null);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * An earlier CONCLUSIVE check of {@code vataxID}, completed just now and therefore still fresh under any
	 * positive {@code RecheckAfterDays} — what sends {@link VATaxIDCheckService#check(VATaxIDCheckRequest)}
	 * down its de-duplication branch instead of to the online checker.
	 *
	 * <p>Obtained for a DIFFERENT partner that happens to carry the same value, which is what makes that branch
	 * able to change the status of the record under test at all: de-duplication is keyed on the VAT-ID value,
	 * not on the parent.
	 */
	private void givenStillFreshConclusiveCheck(
			@NonNull final VATIdentifier vataxID,
			@NonNull final VATaxIDStatus status)
	{
		final BPartnerId otherBPartnerId = givenBPartnerWithVATaxID(vataxID);
		final VATaxIDCheckLogId earlierCheckLogId = checkRepository.writeRequestSent(VATaxIDCheckRequest.builder()
				.bpartnerId(otherBPartnerId)
				.vataxID(vataxID)
				.build());
		checkRepository.completeCheck(earlierCheckLogId, VATaxIDCheckResult.builder()
				.status(status)
				.build());
	}

	@NonNull
	private static I_C_BPartner reload(@NonNull final BPartnerId bpartnerId)
	{
		return InterfaceWrapperHelper.load(bpartnerId, I_C_BPartner.class);
	}

	@NonNull
	private static ImmutableList<I_VATaxID_CheckLog> allCheckLogs()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_VATaxID_CheckLog.class)
				.create()
				.listImmutable(I_VATaxID_CheckLog.class);
	}

	private static void assertParentColumnsUntouched(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner record = reload(bpartnerId);
		assertThat(record.getVATaxIDStatus()).as("VATaxIDStatus").isNull();
		assertThat(record.getVATaxIDCheckedAt()).as("VATaxIDCheckedAt").isNull();
		assertThat(record.getVATaxID_CheckLog_ID()).as("VATaxID_CheckLog_ID").isLessThanOrEqualTo(0);
	}

	@Test
	void aClearedVATaxID_getsNoVerdictFromTheCheckAlreadyInFlightForTheOldValue()
	{
		givenConfig(0);
		final BPartnerId bpartnerId = givenBPartnerWithVATaxID(CHECKED_VATAXID);

		// The user clears the field and saves again while the check for the old value is still queued. The
		// trigger enqueues nothing for a cleared value -- but the FIRST work package still runs.
		changeVATaxIDTo(bpartnerId, null);

		when(onlineChecker.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder()
						.status(VATaxIDStatus.Valid)
						.requestIdentifier("WAPIAAAAStale1")
						.rawResponse("{\"valid\":true}")
						.build());

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		// Stamping Valid here would hand a tax certificate to a record that carries no VAT-ID at all.
		assertParentColumnsUntouched(bpartnerId);

		// ... and the caller is told the status the RECORD has, not the one the abandoned answer produced --
		// otherwise VATaxIDCheckRunService would see a status change and refresh the partner's order tax on
		// the strength of a verdict that was never stored.
		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.NotChecked);

		// ... and no order tax was refreshed either: an abandoned verdict changed no status, so there is
		// nothing for a tax rule to have started reading differently.
		verify(orderTaxRefresher, never()).refreshOrderLinesTaxForBPartner(any(BPartnerId.class));

		// The evidence is kept regardless: the attempt genuinely happened, and VATaxID_CheckLog is
		// append-only historical evidence by design.
		assertThat(allCheckLogs())
				.as("VATaxID_CheckLog rows")
				.hasSize(1)
				.allSatisfy(log -> {
					assertThat(log.getVATaxID()).isEqualTo(CHECKED_VATAXID.getAsString());
					assertThat(log.getVATaxIDStatus()).isEqualTo(VATaxIDStatus.Valid.getCode());
					assertThat(log.getRequestIdentifier()).isEqualTo("WAPIAAAAStale1");
				});
	}

	/**
	 * The same guard on the OTHER path into the parent write: de-duplication, where no request is sent at all
	 * because this VAT-ID's answer is still fresh from an earlier check (possibly of a different parent
	 * carrying the same value). A superseded value must not collect that answer either.
	 */
	@Test
	void aCorrectedVATaxID_getsNoVerdictFromAStillFreshCheckOfTheSupersededValue()
	{
		givenConfig(30);

		// An earlier, still-fresh conclusive check of the OLD value, obtained for some other partner.
		givenStillFreshConclusiveCheck(CHECKED_VATAXID, VATaxIDStatus.Valid);

		final BPartnerId bpartnerId = givenBPartnerWithVATaxID(CHECKED_VATAXID);
		// The user corrects the number to a different one before the queued check for the old one runs.
		changeVATaxIDTo(bpartnerId, CORRECTED_VATAXID);

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		assertParentColumnsUntouched(bpartnerId);
		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.NotChecked);
		verify(onlineChecker, never()).check(any(VATIdentifier.class), any(VATaxIDConfig.class));
		verify(orderTaxRefresher, never()).refreshOrderLinesTaxForBPartner(any(BPartnerId.class));
	}

	/**
	 * The guard on the OTHER parent type. {@link VATaxIDParentStatusRepository} carries a separate code block
	 * for {@code C_BPartner_Location} on both the read and the write, so a divergence there — comparing or
	 * writing the wrong record — would be invisible to every partner-header test above.
	 */
	@Test
	void aCorrectedLocationVATaxID_getsNoVerdictOnTheLocationEither()
	{
		givenConfig(0);
		// The header carries a DIFFERENT, untouched VAT-ID on purpose: were the guard to compare against the
		// header instead of the location, it would read CHECKED_VATAXID there and wave the write through.
		final BPartnerId bpartnerId = givenBPartnerWithVATaxID(CHECKED_VATAXID);
		final BPartnerLocationId bpartnerLocationId = givenBPartnerLocationWithVATaxID(bpartnerId, CHECKED_VATAXID);

		changeLocationVATaxIDTo(bpartnerLocationId, CORRECTED_VATAXID);

		when(onlineChecker.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build());

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpartnerLocationId)
				.vataxID(CHECKED_VATAXID)
				.build());

		final I_C_BPartner_Location location = InterfaceWrapperHelper.load(bpartnerLocationId, I_C_BPartner_Location.class);
		assertThat(location.getVATaxIDStatus()).as("location VATaxIDStatus").isNull();
		assertThat(location.getVATaxIDCheckedAt()).as("location VATaxIDCheckedAt").isNull();
		assertThat(location.getVATaxID_CheckLog_ID()).as("location VATaxID_CheckLog_ID").isLessThanOrEqualTo(0);
		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.NotChecked);

		// The header must not be collateral damage: a location check never writes the header's columns.
		assertParentColumnsUntouched(bpartnerId);

		verify(orderTaxRefresher, never()).refreshOrderLinesTaxForBPartner(any(BPartnerId.class));
	}

	/**
	 * The positive control the two tests above are worthless without: an UNCHANGED record still gets its
	 * verdict. Without this, deleting the parent write altogether would pass both of them.
	 */
	@Test
	void anUnchangedVATaxID_stillGetsItsVerdictWrittenOntoTheParent()
	{
		givenConfig(0);
		final BPartnerId bpartnerId = givenBPartnerWithVATaxID(CHECKED_VATAXID);

		when(onlineChecker.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build());

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		final I_C_BPartner record = reload(bpartnerId);
		assertThat(record.getVATaxIDStatus()).as("VATaxIDStatus").isEqualTo(VATaxIDStatus.Valid.getCode());
		assertThat(record.getVATaxIDCheckedAt()).as("VATaxIDCheckedAt").isNotNull();
		assertThat(record.getVATaxID_CheckLog_ID()).as("VATaxID_CheckLog_ID").isGreaterThan(0);
		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.Valid);
	}

	/**
	 * The defect this class's order-tax coverage exists for, in the sequence the feature owner actually hit:
	 * a partner with an open sales order whose VAT-ID is corrected, so the SAVE-triggered check — not the
	 * process — is the one that writes the new status. The open order's tax must be refreshed off the back of
	 * that write.
	 *
	 * <p>A scenario driven through the {@code C_BPartner_VATaxID_Check} process cannot stand in for this one:
	 * the save-triggered check has by then already stored the status the process would react to, so the process
	 * legitimately finds nothing changed. The save path has to be exercised in its own right.
	 *
	 * <p>The refresher itself is mocked: what belongs here is "the seam is invoked, once, for this partner",
	 * and it is the {@code de.metas.business} implementation behind that seam — plus the cucumber scenario in
	 * {@code vatIdCheckProcessCorrectsOrderTax.feature} — that own whether the resulting {@code C_Tax_ID} is
	 * right.
	 */
	@Test
	void aSaveTriggeredCheckThatChangesTheStoredStatus_refreshesTheOpenOrdersTax()
	{
		givenConfig(0);
		final BPartnerId bpartnerId = givenBPartnerWithVATaxIDAndStatus(CHECKED_VATAXID, VATaxIDStatus.Invalid);

		when(onlineChecker.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build());

		// No pinstanceId: this is the work-package path, exactly as VATaxIDCheckWorkpackageProcessor builds it.
		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.Valid);
		assertThat(reload(bpartnerId).getVATaxIDStatus()).as("VATaxIDStatus").isEqualTo(VATaxIDStatus.Valid.getCode());

		verify(orderTaxRefresher).refreshOrderLinesTaxForBPartner(bpartnerId);
	}

	/**
	 * The guard against the opposite failure — refreshing on every check. A re-check that reconfirms the stored
	 * status changes nothing a tax rule reads, so re-deriving the tax of every open order of the partner would
	 * be pure write amplification: the nightly run re-checks every VAT-ID in the database, and each refresh
	 * saves every line of every not-yet-processed order.
	 */
	@Test
	void aCheckThatReconfirmsTheStoredStatus_doesNotRefreshTheOrderTax()
	{
		givenConfig(0);
		final BPartnerId bpartnerId = givenBPartnerWithVATaxIDAndStatus(CHECKED_VATAXID, VATaxIDStatus.Valid);

		when(onlineChecker.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build());

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.Valid);
		verify(orderTaxRefresher, never()).refreshOrderLinesTaxForBPartner(any(BPartnerId.class));
	}

	/**
	 * The same transition on the OTHER branch that writes a verdict: de-duplication, where the answer is taken
	 * from a still-fresh check log and no online call is made at all. That branch is the everyday one in
	 * production — every partner re-checked inside {@code RecheckAfterDays} goes through it — and it can change
	 * this record's status just as much as a fresh call can, because the fresh answer it reuses may have been
	 * obtained for a different parent carrying the same VAT-ID.
	 *
	 * <p>Nothing else covers it. The online-call branch has its own call to the refresher, so passing there
	 * proves nothing here; and the cucumber scenarios in {@code vatIdCheckProcessCorrectsOrderTax.feature}
	 * open by clearing the {@code VATaxID_CheckLog} of every VAT-ID they use, which leaves de-duplication
	 * nothing to find and forces a fresh online call by construction.
	 */
	@Test
	void aDeDuplicatedCheckThatChangesTheStoredStatus_refreshesTheOpenOrdersTax()
	{
		givenConfig(30);
		givenStillFreshConclusiveCheck(CHECKED_VATAXID, VATaxIDStatus.Valid);

		final BPartnerId bpartnerId = givenBPartnerWithVATaxIDAndStatus(CHECKED_VATAXID, VATaxIDStatus.Invalid);

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		// De-duplication really did take over: no request was sent, ...
		verify(onlineChecker, never()).check(any(VATIdentifier.class), any(VATaxIDConfig.class));
		// ... and the reused answer was still written onto the record, changing the status it held.
		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.Valid);
		assertThat(reload(bpartnerId).getVATaxIDStatus()).as("VATaxIDStatus").isEqualTo(VATaxIDStatus.Valid.getCode());

		// So the partner's open orders must be re-taxed off the back of THAT write -- for this partner, not for
		// the unrelated one the reused answer was originally obtained for.
		verify(orderTaxRefresher).refreshOrderLinesTaxForBPartner(bpartnerId);
	}

	/**
	 * The de-duplication branch's half of the "do not refresh on every check" guard, the counterpart of
	 * {@link #aCheckThatReconfirmsTheStoredStatus_doesNotRefreshTheOrderTax()}: the still-fresh answer equals
	 * what the record already holds, so the write reconfirms the status rather than changing it and no tax
	 * input moved. This is the single most common outcome of a nightly run, and refreshing here would re-save
	 * every line of every open order of nearly every partner in the database.
	 */
	@Test
	void aDeDuplicatedCheckThatReconfirmsTheStoredStatus_doesNotRefreshTheOrderTax()
	{
		givenConfig(30);
		givenStillFreshConclusiveCheck(CHECKED_VATAXID, VATaxIDStatus.Valid);

		final BPartnerId bpartnerId = givenBPartnerWithVATaxIDAndStatus(CHECKED_VATAXID, VATaxIDStatus.Valid);

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.Valid);
		verify(onlineChecker, never()).check(any(VATIdentifier.class), any(VATaxIDConfig.class));
		verify(orderTaxRefresher, never()).refreshOrderLinesTaxForBPartner(any(BPartnerId.class));
	}

	/**
	 * The third case the refresh must stay out of, alongside a reconfirmed status and an abandoned verdict: the
	 * organisation has the online check switched off, so {@code check} returns before it records or writes
	 * anything at all. Nothing changed, so nothing may be refreshed.
	 */
	@Test
	void aCheckWithTheOnlineCheckDisabled_writesNothingAndDoesNotRefreshTheOrderTax()
	{
		when(configRepository.getByOrgId(any(OrgId.class))).thenReturn(VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(1_000_000))
				.formatCheckEnabled(true)
				.viesCheckEnabled(false)
				.restApiBaseURL("https://ec.europa.eu/taxation_customs/vies/rest-api")
				.recheckAfterDays(0)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build());
		final BPartnerId bpartnerId = givenBPartnerWithVATaxIDAndStatus(CHECKED_VATAXID, VATaxIDStatus.Invalid);

		final VATaxIDStatus returnedStatus = checkService.check(VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());

		assertThat(returnedStatus).as("returned status").isEqualTo(VATaxIDStatus.Invalid);
		verify(onlineChecker, never()).check(any(VATIdentifier.class), any(VATaxIDConfig.class));
		assertThat(allCheckLogs()).as("VATaxID_CheckLog rows").isEmpty();
		verify(orderTaxRefresher, never()).refreshOrderLinesTaxForBPartner(any(BPartnerId.class));
	}

	/**
	 * The failure branch the "status write and refresh are one unit of work" arrangement exists for: a throwing
	 * refresher must bring the check down with it rather than be swallowed, because a status that commits
	 * without its refresh is a partner whose open orders keep the tax of a status the record no longer has.
	 *
	 * <p>The message must say the CHECK ITSELF SUCCEEDED. {@code VATaxIDCheckRunService} logs one per-target
	 * line for whatever comes out of here, and without that wording an operator reads a refresh failure as a
	 * VAT-ID that could not be checked — and goes looking at the checking service instead of at the orders.
	 *
	 * <p><b>Only the propagation and the message are asserted.</b> Whether the parent-status write is genuinely
	 * rolled back is not provable at this tier: {@code AdempiereTestHelper}'s in-memory POJO model has no real
	 * transaction, so {@code callInThreadInheritedTrx} has nothing to roll back and the write survives here
	 * regardless. That half of the guarantee rests on the transaction reasoning documented on
	 * {@code VATaxIDCheckService#storeVerdictAndRefreshOrderTax}, and proving it would need a
	 * database-backed test with an injectable refresher failure — which no seam currently offers.
	 */
	@Test
	void aFailingOrderTaxRefresh_bringsTheCheckDownWithIt_sayingTheCheckItselfSucceeded()
	{
		givenConfig(0);
		final BPartnerId bpartnerId = givenBPartnerWithVATaxIDAndStatus(CHECKED_VATAXID, VATaxIDStatus.Invalid);

		when(onlineChecker.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.Valid).build());
		doThrow(new RuntimeException("simulated order-tax refresh failure (test-only, VATaxIDCheckServiceTest)"))
				.when(orderTaxRefresher).refreshOrderLinesTaxForBPartner(bpartnerId);

		final VATaxIDCheckRequest request = VATaxIDCheckRequest.builder()
				.bpartnerId(bpartnerId)
				.vataxID(CHECKED_VATAXID)
				.build();

		assertThatThrownBy(() -> checkService.check(request))
				.as("exception out of check()")
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("C_BPartner_ID=" + bpartnerId.getRepoId())
				.hasMessageContaining("succeeded (status Invalid -> Valid)")
				.hasMessageContaining("refreshing its open orders' tax failed");
	}
}
