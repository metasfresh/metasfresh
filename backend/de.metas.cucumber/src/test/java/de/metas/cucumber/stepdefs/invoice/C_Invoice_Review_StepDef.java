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
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.impl.InvoiceReview;
import de.metas.invoice.service.impl.InvoiceReviewRepository;
import de.metas.po.CustomColumnService;
import de.metas.rest_api.v2.invoice.JsonCreateInvoiceReviewResponse;
import de.metas.rest_api.v2.invoice.JsonCreateInvoiceReviewResponseResult;
import de.metas.rest_api.v2.invoice.JsonInvoiceReviewUpsertItem;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Invoice_Review;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_Invoice_Review.COLUMNNAME_C_Invoice_ID;
import static org.compiere.model.I_C_Invoice_Review.COLUMNNAME_C_Invoice_Review_ID;
import static org.compiere.model.I_C_Invoice_Review.COLUMNNAME_ExternalId;

public class C_Invoice_Review_StepDef
{
	private final ObjectMapper mapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final CustomColumnService customColumnService;

	private final C_Invoice_Review_StepDefData invoiceReviewTable;
	private final C_Invoice_StepDefData invoiceTable;

	private final TestContext testContext;

	public C_Invoice_Review_StepDef(@NonNull final CustomColumnService customColumnService,
			@NonNull final C_Invoice_Review_StepDefData invoiceReviewTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final TestContext testContext)
	{
		this.customColumnService = customColumnService;
		this.invoiceReviewTable = invoiceReviewTable;
		this.invoiceTable = invoiceTable;
		this.testContext = testContext;
	}

	@And("the user creates a JsonInvoiceReviewUpsertItem and stores it in the context")
	public void creteJsonInvoiceReviewUpsertItem(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		assertThat(tableRows.size()).isEqualTo(1);
		final Map<String, String> tableRow = tableRows.get(0);

		final String orgCode = tableRow.get("orgCode");
		final String customColumn = tableRow.get("customColumn");

		final JsonInvoiceReviewUpsertItem.JsonInvoiceReviewUpsertItemBuilder payloadBuilder = JsonInvoiceReviewUpsertItem.builder();

		if (tableRow.containsKey("OPT." + COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER))
		{
			final I_C_Invoice invoice = invoiceTable.get(tableRow.get("OPT." + COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER));
			payloadBuilder.invoiceId(invoice.getC_Invoice_ID());
		}
		else if (tableRow.containsKey("OPT." + COLUMNNAME_ExternalId))
		{
			payloadBuilder.externalId(tableRow.get("OPT." + COLUMNNAME_ExternalId));
		}
		else
		{
			throw new AdempiereException("Don't know how to identify invoice to review");
		}

		final JsonInvoiceReviewUpsertItem payload = payloadBuilder
				.orgCode(orgCode)
				.extendedProps(Collections.singletonMap("customColumn", customColumn))
				.build();

		testContext.setRequestPayload(JsonObjectMapperHolder.newJsonObjectMapper().writeValueAsString(payload));
	}

	@Then("validate invoice reviews and store them with their identifiers")
	public void validateInvoiceReviewAndStoreItsIdentifier(@NonNull final DataTable dataTable)
	{
		final InvoiceReviewRepository invoiceReviewRepository = new InvoiceReviewRepository(customColumnService);

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
			final int invoiceId = invoiceTable.get(invoiceIdentifier).getC_Invoice_ID();

			final InvoiceReview review = invoiceReviewRepository.getByInvoiceId(InvoiceId.ofRepoId(invoiceId));

			// soft assertion makes no sense here, because if this fails we can't test any further and will run into an NPE
			assertThat(review).as("Missing InvoiceReview for invoiceId %s (identifier=%s)", invoiceId, invoiceIdentifier).isNotNull();

			final I_C_Invoice_Review reviewRecord = InterfaceWrapperHelper.load(review.getId(), I_C_Invoice_Review.class);

			validateInvoiceReview(reviewRecord, row);
			invoiceReviewTable.put(invoiceIdentifier, reviewRecord);
		}
	}

	@And("validate invoice reviews")
	public void validate_invoice_reviews(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String identifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Review_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_C_Invoice_Review review = invoiceReviewTable.get(identifier);

			validateInvoiceReview(review, row);
		}
	}

	private void validateInvoiceReview(final I_C_Invoice_Review review, final Map<String, String> row)
	{
		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer invoiceId = invoiceTable.get(invoiceIdentifier).getC_Invoice_ID();
		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(invoiceId).isEqualTo(review.getC_Invoice_ID());
		final Map<String, Object> customColumnsMap = customColumnService.getCustomColumnsAsMap(InterfaceWrapperHelper.getPO(review));

		for (final String key : customColumnsMap.keySet())
		{
			final String expectedValue = DataTableUtil.extractStringForColumnName(row,"OPT." + key);
			if (Check.isEmpty(expectedValue))
			{
				assertThat(customColumnsMap.get(key)).isNull();
			}
			else
			{
				assertThat(customColumnsMap.get(key)).isNotNull();
				assertThat(customColumnsMap.get(key).toString()).isEqualTo(expectedValue);
			}
		}

		softly.assertAll();
	}

	@And("process invoice review create response")
	public void process_invoice_review_create_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonCreateInvoiceReviewResponse jsonCreateInvoiceResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonCreateInvoiceReviewResponse.class);

		final JsonCreateInvoiceReviewResponseResult result = jsonCreateInvoiceResponse.getResult();
		assertThat(result).as("the received jsonCreateInvoiceResponse has a null-result; response=%s", jsonCreateInvoiceResponse).isNotNull();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(result.getReviewId()).as("result.reviewId").isNotNull();
		softly.assertThat(jsonCreateInvoiceResponse.getErrors()).as("result.errors").isNull();
		softly.assertAll();

		final I_C_Invoice_Review review = InterfaceWrapperHelper.load(result.getReviewId().getValue(), I_C_Invoice_Review.class);
		assertThat(review).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);

		final String invoiceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Review_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		invoiceReviewTable.put(invoiceIdentifier, review);
	}

	@Then("validate invoice review api response error message")
	public void validate_api_response_error_message(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String message = DataTableUtil.extractStringForColumnName(row, "JsonErrorItem.message");

			final SoftAssertions softly = new SoftAssertions();

			final JsonCreateInvoiceReviewResponse jsonCreateInvoiceResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonCreateInvoiceReviewResponse.class);
			softly.assertThat(jsonCreateInvoiceResponse.getResult()).isNull();

			softly.assertThat(jsonCreateInvoiceResponse.getErrors()).isNotNull();
			softly.assertThat(jsonCreateInvoiceResponse.getErrors()).hasSize(1);
			softly.assertAll();

			assertThat(jsonCreateInvoiceResponse.getErrors().get(0).getMessage()).contains(message);
		}
	}
}
