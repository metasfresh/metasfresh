/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.invoice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import de.metas.JsonObjectMapperHolder;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.api.REST_API_StepDef;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponseItem;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the invoice-candidate status endpoint {@code POST api/v2/invoices/status}.
 * <p>
 * This class owns the wait for the <b>asynchronous</b> invoice-candidate materialization behind that
 * endpoint, so feature files assert the status response directly and never need their own
 * "wait for the invoice candidates" steps.
 */
@RequiredArgsConstructor
public class C_Invoice_Candidate_StefDef
{
	private static final Logger logger = LogManager.getLogger(C_Invoice_Candidate_StefDef.class);

	/**
	 * The endpoint this step def validates. It is intrinsic to the step (the step is named after that
	 * endpoint's response), which is what lets this step re-issue the request itself instead of making
	 * every feature file wait for the invoice candidates beforehand.
	 * <p>
	 * Feature files calling {@code validate invoice candidate status response} must POST to this same
	 * path — the two are not linked mechanically. If this step is ever reused against a second
	 * endpoint, take the path from the scenario instead of this constant.
	 */
	private static final String STATUS_ENDPOINT_PATH = "api/v2/invoices/status";
	private static final int STATUS_ENDPOINT_EXPECTED_STATUS_CODE = 200;
	private static final long TIMEOUT_SEC = 60;
	private static final long CHECK_INTERVAL_MS = 1000;

	@NonNull private final TestContext testContext;
	@NonNull private final REST_API_StepDef restApiStepDef;

	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	/**
	 * Validates the response of {@code POST api/v2/invoices/status} against the expected invoice-candidate rows.
	 * <p>
	 * The invoice candidates behind that endpoint are materialized <b>asynchronously</b> after the order is
	 * completed, so the first response can legitimately still be missing rows. This step therefore polls: it
	 * re-issues the same status request (payload taken from {@link TestContext#getRequestPayload()}) every
	 * {@value #CHECK_INTERVAL_MS}ms for up to {@value #TIMEOUT_SEC}s until an item exists for every expected
	 * {@code ExternalLineId}, and only then asserts the values.
	 * <p>
	 * Because the wait lives here — in the step that <i>consumes</i> the async result — feature files must NOT
	 * add their own "wait for the invoice candidates" steps before calling this one.
	 *
	 * @cucumber.columns
	 *   <b>ExternalHeaderId</b> — (required) expected external header id of the response item<br>
	 *   <b>ExternalLineId</b> — (required) expected external line id; also the key the response is matched on<br>
	 *   <b>QtyEntered</b> — (required) expected ordered quantity<br>
	 *   <b>QtyToInvoice</b> — (required) expected quantity to invoice<br>
	 *   <b>QtyInvoiced</b> — (required) expected already-invoiced quantity<br>
	 *   <b>Processed</b> — (required) expected processed flag<br>
	 * @cucumber.example
	 * <pre>
	 * Then validate invoice candidate status response
	 *   | ExternalHeaderId | ExternalLineId | QtyEntered | QtyToInvoice | QtyInvoiced | Processed |
	 *   | ExtHeader_1      | ExtLine_1      | 5          | 0            | 5           | true      |
	 * </pre>
	 */
	@Then("validate invoice candidate status response")
	public void validateInvoiceCandidateStatusResponse(@NonNull final DataTable table) throws InterruptedException
	{
		final DataTableRows rows = DataTableRows.of(table);

		try
		{
			StepDefUtil.tryAndWait(TIMEOUT_SEC, CHECK_INTERVAL_MS, () -> checkExpectedItemsPresentAndReissueIfNot(rows));
		}
		catch (final AssertionError timedOut)
		{
			// Only the poll timeout can reach here: every other AssertionError raised inside the poll body
			// (a failed HTTP-status assertion in reissueStatusRequest(), an unparseable or null response in
			// extractResponseItemMap()) is converted to an AdempiereException, so a real error is never
			// mistaken for a timeout. Falling through lets assertResponseMatches() name the exact missing
			// ExternalLineId, which is more useful than the generic "worker didn't succeed" message.
			logger.warn("Invoice-candidate status did not become complete within {}s; asserting the last response", TIMEOUT_SEC, timedOut);
		}

		assertResponseMatches(rows);
	}

	/**
	 * One poll iteration: checks the last status response and, when it is still incomplete, re-issues the
	 * request so the next iteration sees a fresh one.
	 *
	 * @return {@code true} once the last response holds an item for every expected {@code ExternalLineId}
	 */
	private boolean checkExpectedItemsPresentAndReissueIfNot(@NonNull final DataTableRows rows)
	{
		if (allExpectedItemsArePresent(rows))
		{
			return true;
		}

		reissueStatusRequest();
		return false;
	}

	/**
	 * Pure check against the <b>last</b> status response — issues no request and has no side effects.
	 */
	private boolean allExpectedItemsArePresent(@NonNull final DataTableRows rows)
	{
		final Map<String, JsonCheckInvoiceCandidatesStatusResponseItem> responseItemMap = extractResponseItemMap();

		return rows.stream()
				.allMatch(row -> responseItemMap.containsKey(row.getAsString(I_C_Invoice_Candidate.COLUMNNAME_ExternalLineId)));
	}

	private void reissueStatusRequest()
	{
		try
		{
			restApiStepDef.performHTTPRequest(
					restApiStepDef.newAPIRequest()
							.endpointPath(STATUS_ENDPOINT_PATH)
							.method("POST")
							.expectedStatusCode(STATUS_ENDPOINT_EXPECTED_STATUS_CODE)
							.payload(testContext.getRequestPayload())
							.build()
			);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed to re-issue the request to " + STATUS_ENDPOINT_PATH, e);
		}
		catch (final AssertionError httpStatusMismatch)
		{
			// The status assertion inside performHTTPRequest fails with an AssertionError, which the caller
			// treats as the poll-timeout signal and swallows. Rethrowing as an AdempiereException keeps a
			// genuine server error (e.g. a 500 during materialization) loud and correctly attributed.
			throw new AdempiereException("Re-issued request to " + STATUS_ENDPOINT_PATH + " failed: " + httpStatusMismatch.getMessage(), httpStatusMismatch);
		}
	}

	@NonNull
	private Map<String, JsonCheckInvoiceCandidatesStatusResponseItem> extractResponseItemMap()
	{
		final JsonCheckInvoiceCandidatesStatusResponse statusResponse;
		try
		{
			statusResponse = objectMapper.readValue(testContext.getApiResponse().getContent(), JsonCheckInvoiceCandidatesStatusResponse.class);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed to parse the " + STATUS_ENDPOINT_PATH + " response", e);
		}

		if (statusResponse == null)
		{
			// Must NOT be an assertion: this runs inside the poll body, and an AssertionError here would be
			// mistaken for the poll timeout and swallowed, ending the retry loop after one iteration.
			throw new AdempiereException("Got a null response from " + STATUS_ENDPOINT_PATH);
		}

		// getInvoiceCandidates() cannot be null: the response DTO's @JsonCreator takes a @NonNull list,
		// so Jackson fails above with a JsonProcessingException before we get here.
		return Maps.uniqueIndex(statusResponse.getInvoiceCandidates(), (item) -> item.getExternalLineId().getValue());
	}

	private void assertResponseMatches(@NonNull final DataTableRows rows)
	{
		final Map<String, JsonCheckInvoiceCandidatesStatusResponseItem> responseItemMap = extractResponseItemMap();

		final SoftAssertions softly = new SoftAssertions();

		rows.forEach(row -> {
					final String externalHeaderId = row.getAsString(I_C_Invoice_Candidate.COLUMNNAME_ExternalHeaderId);
					final String externalLineId = row.getAsString(I_C_Invoice_Candidate.COLUMNNAME_ExternalLineId);
					final BigDecimal qtyEntered = row.getAsBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_QtyEntered);
					final BigDecimal qtyToInvoice = row.getAsBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
					final BigDecimal qtyInvoiced = row.getAsBigDecimal(I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced);
					final boolean processed = row.getAsBoolean(I_C_Invoice_Candidate.COLUMNNAME_Processed);

					final JsonCheckInvoiceCandidatesStatusResponseItem responseItem = responseItemMap.get(externalLineId);
					assertThat(responseItem).as("responseItem for externalLineId=%s", externalLineId).isNotNull();

					softly.assertThat(responseItem.getExternalHeaderId().getValue()).as("externalHeaderId").isEqualTo(externalHeaderId);
					softly.assertThat(responseItem.getExternalLineId().getValue()).as("externalLineId").isEqualTo(externalLineId);
					softly.assertThat(responseItem.getQtyToInvoice()).as("qtyToInvoice").isEqualTo(qtyToInvoice);
					softly.assertThat(responseItem.getQtyInvoiced()).as("qtyInvoiced").isEqualTo(qtyInvoiced);
					softly.assertThat(responseItem.getQtyEntered()).as("qtyEntered").isEqualTo(qtyEntered);
					softly.assertThat(responseItem.isProcessed()).as("processed").isEqualTo(processed);
				}
		);

		softly.assertAll();
	}
}
