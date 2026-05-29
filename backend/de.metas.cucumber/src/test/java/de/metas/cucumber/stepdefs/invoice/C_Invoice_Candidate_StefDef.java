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
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponse;
import de.metas.rest_api.invoicecandidates.response.JsonCheckInvoiceCandidatesStatusResponseItem;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.assertj.core.api.SoftAssertions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class C_Invoice_Candidate_StefDef
{
	private final TestContext testContext;
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	public C_Invoice_Candidate_StefDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@Then("validate invoice candidate status response")
	public void validateInvoiceCandidateStatusResponse(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonCheckInvoiceCandidatesStatusResponse statusResponse = objectMapper.readValue(testContext.getApiResponse().getContent(), JsonCheckInvoiceCandidatesStatusResponse.class);
		assertThat(statusResponse).isNotNull();

		final List<JsonCheckInvoiceCandidatesStatusResponseItem> responseItemList = statusResponse.getInvoiceCandidates();
		assertThat(responseItemList).isNotNull();

		final Map<String, JsonCheckInvoiceCandidatesStatusResponseItem> responseItemMap = Maps.uniqueIndex(responseItemList,
				(item) -> item.getExternalLineId().getValue()
		);

		final SoftAssertions softly = new SoftAssertions();

		DataTableRows.of(table)
				.forEach(row -> {
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
