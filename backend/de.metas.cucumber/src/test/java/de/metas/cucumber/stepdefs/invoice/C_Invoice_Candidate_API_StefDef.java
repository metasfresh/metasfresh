/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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
import de.metas.JsonObjectMapperHolder;
import de.metas.cucumber.stepdefs.DataTableUtil;
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

import static org.assertj.core.api.Assertions.*;

public class C_Invoice_Candidate_API_StefDef
{
	private final TestContext testContext;
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	public C_Invoice_Candidate_API_StefDef(@NonNull final TestContext testContext)
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
		assertThat(responseItemList.size()).isEqualTo(1);

		final JsonCheckInvoiceCandidatesStatusResponseItem responseItem = responseItemList.get(0);

		final SoftAssertions softly = new SoftAssertions();

		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String externalHeaderId = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_ExternalHeaderId);
			final String externalLineId = DataTableUtil.extractStringForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_ExternalLineId);
			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyEntered);
			final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
			final BigDecimal qtyInvoiced = DataTableUtil.extractBigDecimalForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced);
			final boolean processed = DataTableUtil.extractBooleanForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_Processed);

			softly.assertThat(responseItem.getExternalHeaderId().getValue()).as("externalHeaderId").isEqualTo(externalHeaderId);
			softly.assertThat(responseItem.getExternalLineId().getValue()).as("externalLineId").isEqualTo(externalLineId);
			softly.assertThat(responseItem.getQtyToInvoice()).as("qtyToInvoice").isEqualTo(qtyToInvoice);
			softly.assertThat(responseItem.getQtyInvoiced()).as("qtyInvoiced").isEqualTo(qtyInvoiced);
			softly.assertThat(responseItem.getQtyEntered()).as("qtyEntered").isEqualTo(qtyEntered);
			softly.assertThat(responseItem.isProcessed()).as("processed").isEqualTo(processed);
		}

		softly.assertAll();
	}
}
