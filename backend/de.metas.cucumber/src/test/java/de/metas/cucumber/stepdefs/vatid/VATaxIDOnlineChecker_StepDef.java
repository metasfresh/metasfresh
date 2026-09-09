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
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_VATaxID_CheckLog;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
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
	/**
	 * Resolved on first use, to not interfere with this class's {@code @Before} hook.
	 * This instance is created in {@link VATaxIDTestServiceConfiguration}.
	 */
	@NonNull private final SpringContextHolder.Lazy<VATaxIDOnlineChecker> onlineCheckerMock = SpringContextHolder.lazyBean(VATaxIDOnlineChecker.class);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * VAT-IDs the shared checker mock was asked about while this scenario's stub was installed, but which
	 * this scenario never stubbed. Almost always a straggler from an earlier scenario whose async check
	 * outlived its own stub; see the answer that fills it.
	 *
	 * <p>One instance per scenario (cucumber-picocontainer instantiates the glue per scenario), so it is
	 * cleared in {@link #clearUnexpectedVATaxIDsBeforeScenario()} and NOT in the stub steps: a scenario that
	 * stubs more than once must not lose what its earlier stub recorded. Concurrent because the answer that
	 * fills it runs on async work-package threads.
	 */
	@NonNull private final Set<String> unexpectedVATaxIDs = ConcurrentHashMap.newKeySet();

	/**
	 * Stubs the online checker to answer exactly the listed VAT-IDs. A check for any other value is recorded
	 * in {@link #unexpectedVATaxIDs} and answered {@link VATaxIDStatus#ServiceUnavailable}, and
	 * {@link #assertNoUnexpectedOnlineChecksAfterScenario()} then fails the scenario at its end. An
	 * unexpected online check is a defect — the format check, the not-supported short-circuit or the
	 * de-duplication did not stop it — so it never passes silently.
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

		reset(onlineCheckerMock.get());

		when(onlineCheckerMock.get().check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenAnswer(invocation -> {
					final VATIdentifier vatId = invocation.getArgument(0);
					final VATaxIDCheckResult result = results.get(vatId.getAsString());
					if (result == null)
					{
						// Recorded, NOT thrown. This answer is often produced on a work-package thread
						// belonging to an EARLIER scenario, whose check outlived the stub it was set up
						// with. Throwing there killed that check's own log-row write, so the scenario that
						// visibly failed was whichever one happened to be waiting on that row -- never the
						// one that leaked. Recording lets the owning scenario fail on its own terms in
						// assertNoUnexpectedOnlineChecksAfterScenario(), and leaves everyone else's data intact.
						unexpectedVATaxIDs.add(vatId.getAsString());
						return VATaxIDCheckResult.builder()
								.status(VATaxIDStatus.ServiceUnavailable)
								.build();
					}
					return result;
				});
		when(onlineCheckerMock.get().getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
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
		reset(onlineCheckerMock.get());

		when(onlineCheckerMock.get().check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenReturn(VATaxIDCheckResult.builder().status(VATaxIDStatus.ServiceUnavailable).build());
		when(onlineCheckerMock.get().getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
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

		when(onlineCheckerMock.get().getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of(unavailableCountryCode));
	}

	/**
	 * Stubs the checker leniently: a VAT-ID listed in {@code dataTable} gets its ordinary answer, any other
	 * gets {@link VATaxIDStatus#ServiceUnavailable} without being recorded as unexpected — which is what
	 * {@link #stubOnlineChecker(DataTable)} does, and fails the scenario for. For scenarios running the
	 * selection-less nightly shape, which reaches every VAT-ID in the local database — harmless there, as long as
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

		reset(onlineCheckerMock.get());

		when(onlineCheckerMock.get().check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenAnswer(invocation -> {
					final VATIdentifier vatId = invocation.getArgument(0);
					final VATaxIDCheckResult result = results.get(vatId.getAsString());
					return result != null ? result : VATaxIDCheckResult.builder().status(VATaxIDStatus.ServiceUnavailable).build();
				});
		when(onlineCheckerMock.get().getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
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
		verify(onlineCheckerMock.get(), never()).check(any(VATIdentifier.class), any(VATaxIDConfig.class));
	}

	/**
	 * Asserts the online checker WAS asked about {@code vataxID} — the direct evidence that a check was
	 * actually attempted, as opposed to the after-commit trigger having been wired but never firing.
	 *
	 * <p>Requires the check to have fully landed — a terminal {@code VATaxID_CheckLog} row that its parent
	 * record already points at. Two kinds of scenario cannot reach that state and must use
	 * {@link #onlineCheckWasAttempted(String)} instead, or they burn the full 60 s timeout: a checker
	 * stubbed to THROW, which never completes the log row; and a scenario that changes the record's
	 * {@code VATaxID} while the check is in flight, where
	 * {@code VATaxIDCheckService#updateParentStatusIfStillCurrent} deliberately abandons the verdict and the
	 * completed row stays unreferenced for good.
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
		//
		// Polls the mock invocation AND the persisted outcome — and specifically the outcome AS THE
		// FOLLOWING ASSERTIONS READ IT. check() does not finish when the checker returns: it completes the
		// VATaxID_CheckLog row, and only afterwards, in a SEPARATE and independently committed save, writes
		// the verdict onto the parent (C_BPartner / C_BPartner_Location: VATaxIDStatus, VATaxIDCheckedAt,
		// VATaxID_CheckLog_ID). Every assertion step after this one reads that PARENT, once, without
		// retrying.
		//
		// So the wait covers the parent write too, by waiting for the completed log row to be REFERENCED BY
		// its parent — the parent's three columns are written as one set (VATaxIDParentStatusRepository),
		// so a parent already pointing at the terminal row necessarily carries that row's status as well.
		// That closes the window by construction rather than shrinking it.
		//
		// That window is wide, not negligible: the parent write is a full C_BPartner save — every model
		// interceptor, an enqueued external-system sync, change-log rows. Measured from the instant
		// completeCheck() commits, it is 15-20 ms on an idle developer box and 80-200 ms on a loaded CI
		// executor. A 500 ms poll grid is wide enough to wake inside it, so a predicate satisfied by the log
		// row alone hands a green light to an assertion that then reads a still-NotChecked parent.
		StepDefUtil.tryAndWait(60, 500, () -> wasCalledFor(vataxID) && completedCheckIsReferencedByItsParent(vataxID));

		verify(onlineCheckerMock.get(), atLeastOnce())
				.check(argThat(checked -> checked != null && checked.getAsString().equals(vataxID)), any(VATaxIDConfig.class));
	}

	/**
	 * The counterpart of {@link #onlineCheckerWasCalled(String)} for a checker that never answers: waits
	 * until the checker was asked about {@code vataxID} and the attempt is on record, whatever became of it.
	 *
	 * <p>Needed because {@link #onlineCheckerWasCalled(String)} is structurally unsatisfiable when the
	 * checker throws. That step waits for the {@code VATaxID_CheckLog} row to have LEFT
	 * {@code RequestSent} and to be referenced by its parent — i.e. for {@code completeCheck(...)} and the
	 * parent save that follows it — and a throwing checker unwinds {@code VATaxIDCheckService#check} before
	 * either is ever reached, so the row stays at {@code RequestSent} forever, unreferenced, and the step
	 * can only ever time out.
	 *
	 * <p>Relaxing that step's own predicate to accept a still-{@code RequestSent} row was the alternative,
	 * and was rejected: what it waits for is precisely what keeps the other scenarios' follow-up
	 * assertions — which read the database once, without retrying — from racing a check that is still in
	 * flight. Weakening it would hand that race back to every one of them to buy this one scenario its
	 * wait.
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

		verify(onlineCheckerMock.get(), atLeastOnce())
				.check(argThat(checked -> checked != null && checked.getAsString().equals(vataxID)), any(VATaxIDConfig.class));
	}

	/**
	 * @return whether a {@code VATaxID_CheckLog} row for {@code vataxID} exists at all — {@code RequestSent}
	 * included. The deliberately weaker sibling of
	 * {@link #completedCheckIsReferencedByItsParent(String)}, for the case where the check cannot reach a
	 * terminal status because the checker threw.
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
	 * @return whether a {@code VATaxID_CheckLog} row for {@code vataxID} has left {@code RequestSent} AND is
	 * the row its parent record now points at — i.e. the check finished, committed, and was denormalised
	 * onto the {@code C_BPartner} / {@code C_BPartner_Location} that the assertion steps go on to read.
	 *
	 * <p>Strictly stronger than "the log row has left {@code RequestSent}", which it still requires: the
	 * candidate rows are the same set, only narrowed to those a parent references. The parent's three check
	 * columns are written as one set, so a parent pointing at a terminal row also carries that row's
	 * status — which is what makes this the right thing to wait for.
	 *
	 * <p><b>Scoped by VAT-ID value, not by parent record</b> — it asks whether SOME parent references a
	 * completed row for this value. That is exact for every caller today because each scenario puts the
	 * checked value on exactly one record, and the {@code no VATaxID_CheckLog records exist for VATaxID}
	 * step clears prior rows and releases the value from any earlier holder first. A scenario that put the
	 * SAME VAT-ID on two records — plausible, since a conclusive result is deliberately shared across
	 * parents by value — would go green on the first parent's write while the second was still in flight,
	 * reintroducing exactly the race this closes. Give such a scenario two distinct VAT-IDs, or scope the
	 * wait to the record.
	 */
	private boolean completedCheckIsReferencedByItsParent(@NonNull final String vataxID)
	{
		// Both parent types, because both carry the column and both have scenarios: C_BPartner_Location is
		// the parent whenever the checked VAT-ID sits on a location rather than on the partner header.
		return isReferencedByAParent(I_C_BPartner.class, I_C_BPartner.COLUMNNAME_VATaxID_CheckLog_ID, vataxID)
				|| isReferencedByAParent(I_C_BPartner_Location.class, I_C_BPartner_Location.COLUMNNAME_VATaxID_CheckLog_ID, vataxID);
	}

	private <T> boolean isReferencedByAParent(
			@NonNull final Class<T> parentType,
			@NonNull final String checkLogColumnName,
			@NonNull final String vataxID)
	{
		return queryBL.createQueryBuilder(parentType)
				.addInSubQueryFilter(checkLogColumnName, I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID, completedCheckLogsFor(vataxID))
				.create()
				.anyMatch();
	}

	/**
	 * @return the {@code VATaxID_CheckLog} rows for {@code vataxID} that have left {@code RequestSent}, as a
	 * sub-query. Built fresh per call rather than shared between the two parent tables, so neither query can
	 * inherit anything from the other's execution.
	 */
	@NonNull
	private IQuery<I_VATaxID_CheckLog> completedCheckLogsFor(@NonNull final String vataxID)
	{
		return queryBL.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID, vataxID)
				.addNotEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.RequestSent)
				.create();
	}

	private boolean wasCalledFor(@NonNull final String vataxID)
	{
		try
		{
			verify(onlineCheckerMock.get(), atLeastOnce())
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
		reset(onlineCheckerMock.get());

		when(onlineCheckerMock.get().check(any(VATIdentifier.class), any(VATaxIDConfig.class)))
				.thenThrow(new RuntimeException("Simulated online checker failure (test-only, VATaxIDOnlineChecker_StepDef)"));
		when(onlineCheckerMock.get().getUnavailableCountryCodes(any(VATaxIDConfig.class))).thenReturn(ImmutableSet.of());
	}

	/**
	 * Fails the scenario that OWNS the leak, naming the VAT-IDs involved — the diagnosis the old
	 * throw-on-the-async-thread behaviour destroyed by breaking a different scenario's data instead.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then no unexpected VAT-ID online checks happened
	 * </pre>
	 */
	@Then("no unexpected VAT-ID online checks happened")
	public void assertNoUnexpectedOnlineChecks()
	{
		assertThat(unexpectedVATaxIDs)
				.as("the online checker was asked about VAT-IDs this scenario never stubbed; an async check "
						+ "from an earlier scenario most likely outlived its stub")
				.isEmpty();
	}

	/**
	 * Empties the collector at scenario start, so the {@code @After} guard below can only ever see what THIS
	 * scenario recorded.
	 */
	@Before
	public void clearUnexpectedVATaxIDsBeforeScenario()
	{
		unexpectedVATaxIDs.clear();
	}

	/**
	 * Makes {@link #assertNoUnexpectedOnlineChecks()} unconditional for every scenario using
	 * {@link #stubOnlineChecker(DataTable)}, instead of only for those that remember to end with the step. A
	 * trailing Gherkin step would not do: Cucumber skips the remaining steps once one fails, i.e. on exactly
	 * the runs where a stray check is the likeliest explanation.
	 *
	 * <p>A free no-op for every other scenario: it reads the in-memory collector only, so it resolves no bean
	 * and hits no database.
	 */
	@After
	public void assertNoUnexpectedOnlineChecksAfterScenario()
	{
		assertNoUnexpectedOnlineChecks();
	}
}
