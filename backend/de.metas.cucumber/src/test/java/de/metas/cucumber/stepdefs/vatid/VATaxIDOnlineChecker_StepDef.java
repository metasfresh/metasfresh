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
import de.metas.vatid.VATaxIDStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
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
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the VAT-ID online checker was called for VATaxID 'DE136695976'
	 * </pre>
	 */
	@Then("the VAT-ID online checker was called for VATaxID {string}")
	public void onlineCheckerWasCalled(@NonNull final String vataxID)
	{
		verify(onlineCheckerMock, atLeastOnce())
				.check(argThat(checked -> checked != null && checked.getAsString().equals(vataxID)), any(VATaxIDConfig.class));
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
