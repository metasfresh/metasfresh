/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.invoicecandidate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.iinvoicecandidate.I_Invoice_Candidate_List_StepDefData;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.rest_api.invoicecandidates.v2.request.JsonEnqueueForInvoicingRequest;
import de.metas.rest_api.invoicecandidates.v2.request.JsonInvoiceCandidateReference;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_ExternalHeaderId;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_List_StepDef
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	private final I_Invoice_Candidate_List_StepDefData iInvoiceCandidateListTable;
	private final C_Invoice_Candidate_List_StepDefData invoiceCandidateListTable;
	private final M_Product_StepDefData productTable;
	private final TestContext testContext;

	public C_Invoice_Candidate_List_StepDef(
			@NonNull final I_Invoice_Candidate_List_StepDefData iInvoiceCandidateListTable,
			@NonNull final C_Invoice_Candidate_List_StepDefData invoiceCandidateListTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final TestContext testContext)
	{
		this.iInvoiceCandidateListTable = iInvoiceCandidateListTable;
		this.invoiceCandidateListTable = invoiceCandidateListTable;
		this.productTable = productTable;
		this.testContext = testContext;
	}

	@And("locate C_Invoice_Candidate records for each import record from list")
	public void locate_invoice_candidate_list_by_reference(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String iInvoiceCandListIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "_List." + TABLECOLUMN_IDENTIFIER);
			final List<I_I_Invoice_Candidate> iIInvoiceCandidateList = iInvoiceCandidateListTable.get(iInvoiceCandListIdentifier);

			final List<I_C_Invoice_Candidate> invoiceCandidates = iIInvoiceCandidateList.stream()
					.map(TableRecordReference::of)
					.map(invoiceCandDAO::retrieveReferencing)
					.flatMap(List::stream)
					.collect(ImmutableList.toImmutableList());

			assertThat(invoiceCandidates.size()).as("I_C_Invoice_Candidate.list.size").isEqualTo(iIInvoiceCandidateList.size());

			final String invoiceCandidateListIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "_List." + TABLECOLUMN_IDENTIFIER);
			invoiceCandidateListTable.putOrReplace(invoiceCandidateListIdentifier, invoiceCandidates);
		}
	}

	@And("update invoice candidate list: filter by product")
	public void update_invoice_candidate_list_filter_by_product(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String invoiceCandidateListIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Invoice_Candidate_ID + "_List." + TABLECOLUMN_IDENTIFIER);
			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateListTable.get(invoiceCandidateListIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final String externalHeaderId = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_ExternalHeaderId);

			final List<I_C_Invoice_Candidate> invoiceCandidateList = invoiceCandidates
					.stream()
					.filter(candidate -> candidate.getM_Product_ID() == product.getM_Product_ID())
					.peek(invoiceCandidate -> {
						if (externalHeaderId != null)
						{
							invoiceCandidate.setExternalHeaderId(externalHeaderId);
						}

						InterfaceWrapperHelper.saveRecord(invoiceCandidate);
					})
					.collect(ImmutableList.toImmutableList());

			invoiceCandidateListTable.putOrReplace(invoiceCandidateListIdentifier, invoiceCandidateList);
		}
	}

	@And("build enqueue invoice candidates REST-API request")
	public void enqueue_invoice_candidates_list_for_invoicing_via_API(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String invoiceCandidateListIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Invoice_Candidate_ID + "_List." + TABLECOLUMN_IDENTIFIER);
		final LocalDate dateInvoiced = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT.DateInvoiced");

		final List<I_C_Invoice_Candidate> invoiceCandidateList = invoiceCandidateListTable.get(invoiceCandidateListIdentifier);

		final List<JsonInvoiceCandidateReference> invoiceCandidateReferences = invoiceCandidateList.stream()
				.map(I_C_Invoice_Candidate::getExternalHeaderId)
				.distinct()
				.map(externalHeaderId -> JsonInvoiceCandidateReference.builder()
						.externalHeaderId(JsonExternalId.of(externalHeaderId))
						.build())
				.collect(ImmutableList.toImmutableList());

		final JsonEnqueueForInvoicingRequest enqueueForInvoicingRequest = JsonEnqueueForInvoicingRequest.builder()
				.dateInvoiced(dateInvoiced)
				.invoiceCandidates(invoiceCandidateReferences)
				.build();

		testContext.setRequestPayload(objectMapper.writeValueAsString(enqueueForInvoicingRequest));
	}
}
