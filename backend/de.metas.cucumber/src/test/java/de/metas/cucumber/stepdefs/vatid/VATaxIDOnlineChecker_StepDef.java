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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.tax.api.VATIdentifier;
import de.metas.vatid.VATaxIDCheckResult;
import de.metas.vatid.VATaxIDConfig;
import de.metas.vatid.VATaxIDOnlineChecker;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDStatus;
import io.cucumber.datatable.DataTable;
import de.metas.cucumber.stepdefs.StepDefUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_VATaxID_CheckLog;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Programs the stubbed {@link VATaxIDOnlineChecker} (see {@link VATaxIDTestServiceConfiguration}) with the
 * answers a scenario expects from the external service.
 *
 * <p>Every stub step {@link org.mockito.Mockito#reset(Object[]) resets} the shared mock first: it is a
 * singleton for the whole cucumber JVM, so otherwise a previous scenario's answers and recorded invocations
 * would leak into {@link #onlineCheckerWasNotCalled()}.
 */
public class VATaxIDOnlineChecker_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final VATaxIDOnlineChecker onlineCheckerMock = SpringContextHolder.instance.getBean(VATaxIDOnlineChecker.class);

	/**
	 * Stubs the online checker to answer exactly the listed VAT-IDs. A check for any other value fails the
	 * scenario loudly instead of returning Mockito's {@code null} default — an unexpected online check is a
	 * defect (it means the format check, the not-supported short-circuit or the de-duplication did not
	 * stop it), and a {@code NullPointerException} deep inside the service would hide that.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>VATaxID</b>           — (required) the VAT-ID value the service is expected to ask about<br>
	 *   <b>VATaxIDStatus</b>     — (required) the status the service answers with: {@code Valid},
	 *                              {@code Invalid}, {@code NotSupported} or {@code ServiceUnavailable}<br>
	 *   <b>RequestIdentifier</b> — (optional) the consultation number returned with the answer
	 * @cucumber.example
	 * <pre>
	 * Given the VAT-ID online checker is stubbed to answer:
	 *   | VATaxID     | VATaxIDStatus | RequestIdentifier |
	 *   | DE136695976 | Valid         | WAPIAAAAWkGa5Fka  |
	 * </pre>
	 */
	@Given("the VAT-ID online checker is stubbed to answer:")
	public void stubOnlineChecker(@NonNull final DataTable dataTable)
	{
		final ImmutableMap.Builder<String, VATaxIDCheckResult> resultsByVATaxID = ImmutableMap.builder();
		DataTableRows.of(dataTable).forEach(row -> resultsByVATaxID.put(
				row.getAsString(I_VATaxID_CheckLog.COLUMNNAME_VATaxID),
				VATaxIDCheckResult.builder()
						.status(row.getAsEnum(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.class))
						.requestIdentifier(row.getAsOptionalString(I_VATaxID_CheckLog.COLUMNNAME_RequestIdentifier).orElse(null))
						.build()));
		final Map<String, VATaxIDCheckResult> results = resultsByVATaxID.build();

		reset(onlineCheckerMock);

		when(onlineCheckerMock.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenAnswer(invocation -> {
					final VATIdentifier vatId = invocation.getArgument(0);
					final VATaxIDCheckResult result = results.get(vatId.getAsString());
					if (result == null)
					{
						throw new AssertionError("Unexpected online check for VAT-ID `" + vatId.getAsString()
								+ "`; this scenario stubbed only " + results.keySet());
					}
					return result;
				});
		when(onlineCheckerMock.getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
	}

	/**
	 * Stubs the online checker exactly as its SPI contract prescribes for an unreachable service: every
	 * check answers {@link VATaxIDStatus#ServiceUnavailable}, and the availability endpoint reports nothing
	 * as known-down (an unreachable availability endpoint must not silently suppress every check).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given the VAT-ID online checker is stubbed to be unreachable
	 * </pre>
	 */
	@Given("the VAT-ID online checker is stubbed to be unreachable")
	public void stubOnlineCheckerUnreachable()
	{
		reset(onlineCheckerMock);

		when(onlineCheckerMock.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.ServiceUnavailable).build());
		when(onlineCheckerMock.getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
	}

	/**
	 * Stubs the checker as its SPI contract prescribes for a member state reporting itself unavailable via
	 * {@code GET /check-status}, while any VAT-ID listed in {@code dataTable} still gets its ordinary answer.
	 * The unavailable state's own VAT-ID is deliberately absent from the table, so a run that failed to
	 * pre-filter and called it anyway trips the same "unexpected online check" guard.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>VATaxID</b>       — (required) a VAT-ID the service is expected to still be asked about<br>
	 *   <b>VATaxIDStatus</b> — (required) the status the service answers with for that VAT-ID
	 * @cucumber.example
	 * <pre>
	 * Given the VAT-ID online checker is stubbed to report member state 'EL' unavailable, and to answer:
	 *   | VATaxID     | VATaxIDStatus |
	 *   | DE136695976 | Valid         |
	 * </pre>
	 */
	@Given("the VAT-ID online checker is stubbed to report member state {string} unavailable, and to answer:")
	public void stubOnlineCheckerMemberStateUnavailable(@NonNull final String unavailableCountryCode, @NonNull final DataTable dataTable)
	{
		stubOnlineChecker(dataTable);

		when(onlineCheckerMock.getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of(unavailableCountryCode));
	}

	/**
	 * Stubs the checker leniently: a VAT-ID listed in {@code dataTable} gets its ordinary answer, any other
	 * gets {@link VATaxIDStatus#ServiceUnavailable} rather than the loud failure
	 * {@link #stubOnlineChecker(DataTable)} raises. For scenarios running the selection-less nightly shape,
	 * which reaches every VAT-ID in the local database — harmless there, as long as
	 * {@code OnServiceUnavailable} is left at its fail-open default.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>VATaxID</b>       — (required) a VAT-ID the service is expected to be asked about<br>
	 *   <b>VATaxIDStatus</b> — (required) the status the service answers with for that VAT-ID
	 * @cucumber.example
	 * <pre>
	 * Given the VAT-ID online checker is stubbed to answer known VAT-IDs, and to report unavailable for the rest:
	 *   | VATaxID     | VATaxIDStatus |
	 *   | DE136695976 | Valid         |
	 * </pre>
	 */
	@Given("the VAT-ID online checker is stubbed to answer known VAT-IDs, and to report unavailable for the rest:")
	public void stubOnlineCheckerLeniently(@NonNull final DataTable dataTable)
	{
		final ImmutableMap.Builder<String, VATaxIDCheckResult> resultsByVATaxID = ImmutableMap.builder();
		DataTableRows.of(dataTable).forEach(row -> resultsByVATaxID.put(
				row.getAsString(I_VATaxID_CheckLog.COLUMNNAME_VATaxID),
				VATaxIDCheckResult.builder()
						.status(row.getAsEnum(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.class))
						.build()));
		final Map<String, VATaxIDCheckResult> results = resultsByVATaxID.build();

		reset(onlineCheckerMock);

		when(onlineCheckerMock.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenAnswer(invocation -> {
					final VATIdentifier vatId = invocation.getArgument(0);
					final VATaxIDCheckResult result = results.get(vatId.getAsString());
					return result != null ? result : VATaxIDCheckResult.builder().status(VATaxIDStatus.ServiceUnavailable).build();
				});
		when(onlineCheckerMock.getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
	}

	/**
	 * Asserts no VAT-ID was checked against the online service since the last stub step — the direct
	 * evidence that de-duplication skipped the call rather than making it and discarding the answer.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID online checker was not called
	 * </pre>
	 */
	@Then("the VAT-ID online checker was not called")
	public void onlineCheckerWasNotCalled()
	{
		verify(onlineCheckerMock, never()).check(any(VATIdentifier.class), any(VATaxIDConfig.class));
	}

	/**
	 * Asserts the online checker WAS asked about {@code vataxID} — the direct evidence that a check was
	 * actually attempted, as opposed to the after-commit trigger having been wired but never firing.
	 *
	 * <p>Requires the check to have reached a terminal status. For a checker stubbed to THROW, which never
	 * can, use {@link #onlineCheckWasAttempted(String)} instead.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID online checker was called for VATaxID 'DE136695976'
	 * </pre>
	 */
	@Then("the VAT-ID online checker was called for VATaxID {string}")
	public void onlineCheckerWasCalled(@NonNull final String vataxID) throws InterruptedException
	{
		// Waits rather than asserting once. A save-triggered check runs in a work package, so the call
		// happens on the async processor's thread some time after the save step returned; asserting
		// immediately would be a race that passes on an idle machine and fails on a loaded CI executor. A
		// check driven explicitly by a step is already done by the time we get here, so for that case the
		// wait simply succeeds on its first poll.
		// Polls the mock invocation AND the persisted outcome. Waiting on the invocation alone would race
		// badly: check() carries on after the checker returns — it completes the log row and then updates
		// the parent's status columns — and the assertion steps that follow read the database once, without
		// retrying.
		//
		// This NARROWS the race rather than eliminating it, and the distinction is worth stating. The poll
		// goes green the moment completeCheck() commits, which is still one local load-and-save before
		// updateParentStatus() commits the parent's columns. What remains is two adjacent same-thread
		// commits with no third-party I/O between them — sub-millisecond against a 500 ms poll — where
		// before it was an unbounded wait on a network call. Negligible, not impossible.
		StepDefUtil.tryAndWait(60, 500, () -> wasCalledFor(vataxID) && checkIsRecordedFor(vataxID));

		verify(onlineCheckerMock, atLeastOnce())
				.check(argThat(checked -> checked != null && checked.getAsString().equals(vataxID)), any(VATaxIDConfig.class));
	}

	/**
	 * The counterpart of {@link #onlineCheckerWasCalled(String)} for a checker that never answers: waits
	 * until the checker was asked about {@code vataxID} and the attempt is on record, whatever became of it.
	 *
	 * <p>Needed because {@link #onlineCheckerWasCalled(String)} is structurally unsatisfiable when the
	 * checker throws. That step also waits for the {@code VATaxID_CheckLog} row to have LEFT
	 * {@code RequestSent} — i.e. for {@code completeCheck(...)} — and a throwing checker unwinds
	 * {@code VATaxIDCheckService#check} before that call is ever reached, so the row stays at
	 * {@code RequestSent} forever and the step can only ever time out.
	 *
	 * <p>Relaxing that step's own predicate to accept a still-{@code RequestSent} row was the alternative,
	 * and was rejected: "has left {@code RequestSent}" is precisely what keeps the other scenarios'
	 * follow-up assertions — which read the database once, without retrying — from racing a check that is
	 * still in flight. Weakening it would hand that race back to every one of them to buy this one scenario
	 * its wait.
	 *
	 * <p>What this step still proves is what the throwing scenario needs: the after-commit trigger genuinely
	 * fired and reached the service (the mock recorded the call), and the service committed its pre-call
	 * evidence row ({@code writeRequestSent} runs in its own transaction, so it survives the exception).
	 * Nothing is left racing afterwards either — a checker that throws never reaches
	 * {@code updateParentStatus}, so the parent's status columns stay untouched however long we look.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID online check for VATaxID 'IE6433435F' was attempted, whatever its outcome
	 * </pre>
	 */
	@Then("the VAT-ID online check for VATaxID {string} was attempted, whatever its outcome")
	public void onlineCheckWasAttempted(@NonNull final String vataxID) throws InterruptedException
	{
		StepDefUtil.tryAndWait(60, 500, () -> wasCalledFor(vataxID) && checkAttemptIsRecordedFor(vataxID));

		verify(onlineCheckerMock, atLeastOnce())
				.check(argThat(checked -> checked != null && checked.getAsString().equals(vataxID)), any(VATaxIDConfig.class));
	}

	/**
	 * @return whether a {@code VATaxID_CheckLog} row for {@code vataxID} exists at all — {@code RequestSent}
	 * included. The deliberately weaker sibling of {@link #checkIsRecordedFor(String)}, for the case where
	 * the check cannot reach a terminal status because the checker threw.
	 */
	private boolean checkAttemptIsRecordedFor(@NonNull final String vataxID)
	{
		return queryBL.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID, vataxID)
				.create()
				.anyMatch();
	}

	/**
	 * @return whether a {@code VATaxID_CheckLog} row for {@code vataxID} has left {@code RequestSent} — i.e.
	 * the check finished and committed, not merely started.
	 */
	private boolean checkIsRecordedFor(@NonNull final String vataxID)
	{
		return queryBL.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID, vataxID)
				.addNotEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.RequestSent.getCode())
				.create()
				.anyMatch();
	}

	private boolean wasCalledFor(@NonNull final String vataxID)
	{
		try
		{
			verify(onlineCheckerMock, atLeastOnce())
					.check(argThat(checked -> checked != null && checked.getAsString().equals(vataxID)), any(VATaxIDConfig.class));
			return true;
		}
		catch (final AssertionError notYet)
		{
			return false;
		}
	}

	/**
	 * Stubs the online checker to THROW instead of answering — the "client blew up" case, as opposed to
	 * {@link #stubOnlineCheckerUnreachable()} which answers {@link VATaxIDStatus#ServiceUnavailable}, a
	 * normal SPI outcome {@link de.metas.vatid.VATaxIDCheckService} already knows how to record. Used to
	 * prove that the after-commit trigger's own try/catch keeps such an exception from failing the save
	 * that scheduled the check.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given the VAT-ID online checker is stubbed to throw an exception
	 * </pre>
	 */
	@Given("the VAT-ID online checker is stubbed to throw an exception")
	public void stubOnlineCheckerThrows()
	{
		reset(onlineCheckerMock);

		when(onlineCheckerMock.check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenThrow(new RuntimeException("Simulated online checker failure (test-only, VATaxIDOnlineChecker_StepDef)"));
		when(onlineCheckerMock.getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
	}
}
