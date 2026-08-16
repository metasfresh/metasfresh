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
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_VATaxID_CheckLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Covers the one thing an ASYNCHRONOUS check must never do: write a verdict onto a record whose
 * {@code VATaxID} is no longer the value that verdict was obtained for.
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
	private VATaxIDCheckService checkService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		configRepository = mock(VATaxIDConfigRepository.class);
		checkRepository = VATaxIDCheckRepository.newInstanceForUnitTesting();
		onlineChecker = mock(VATaxIDOnlineChecker.class);

		checkService = new VATaxIDCheckService(
				configRepository,
				checkRepository,
				new VATaxIDParentStatusRepository(),
				onlineChecker);
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

	private static void changeVATaxIDTo(@NonNull final BPartnerId bpartnerId, @Nullable final VATIdentifier vataxID)
	{
		final I_C_BPartner record = InterfaceWrapperHelper.load(bpartnerId, I_C_BPartner.class);
		record.setVATaxID(vataxID != null ? vataxID.getAsString() : null);
		InterfaceWrapperHelper.saveRecord(record);
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
		final BPartnerId otherBPartnerId = givenBPartnerWithVATaxID(CHECKED_VATAXID);
		final VATaxIDCheckLogId earlierCheckLogId = checkRepository.writeRequestSent(VATaxIDCheckRequest.builder()
				.bpartnerId(otherBPartnerId)
				.vataxID(CHECKED_VATAXID)
				.build());
		checkRepository.completeCheck(earlierCheckLogId, VATaxIDCheckResult.builder()
				.status(VATaxIDStatus.Valid)
				.build());

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
}
