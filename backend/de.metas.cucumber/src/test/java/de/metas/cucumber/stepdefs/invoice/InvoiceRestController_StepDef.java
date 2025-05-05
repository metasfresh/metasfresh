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

package de.metas.cucumber.stepdefs.invoice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.rest_api.v2.invoice.JsonCreateInvoiceResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class InvoiceRestController_StepDef
{
	private final TestContext testContext;

	public InvoiceRestController_StepDef(@NonNull final TestContext testContext)
	{
		this.testContext = testContext;
	}

	@Then("validate invoice-api response error message")
	public void validate_api_response_error_message(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final JsonCreateInvoiceResponse jsonErrorItem;
		final String responseContent = testContext.getApiResponse().getContent();
		try
		{
			jsonErrorItem = objectMapper.readValue(responseContent, JsonCreateInvoiceResponse.class);
		}
		catch (final RuntimeException rte)
		{
			fail("Error parsing string into class " + JsonErrorItem.class + "; string=" + responseContent);
			throw rte;
		}

		final List<JsonErrorItem> errors = jsonErrorItem.getErrors();
		assertThat(errors).as("errors").isNotNull();

		final SoftAssertions softly = new SoftAssertions();

		final List<Map<String, String>> dataTableAsMaps = dataTable.asMaps();
		softly.assertThat(errors).as("number of errors").hasSize(dataTableAsMaps.size());

		int i = 0;
		for (final Map<String, String> row : dataTableAsMaps)
		{
			final String message = DataTableUtil.extractStringForColumnName(row, "JsonErrorItem.message");

			if (errors.size() > i)
			{
				softly.assertThat(errors.get(i).getMessage()).startsWith(message);
			}
			else
			{
				softly.fail("missing error for row=" + row);
			}
			i++;
		}
		softly.assertAll();
	}
}