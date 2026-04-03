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

package de.metas.cucumber.stepdefs.externalsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.api.APIRequest;
import de.metas.cucumber.stepdefs.api.RESTUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.model.I_C_Invoice;
import de.metas.edi.model.I_M_InOut;
import de.metas.externalsystem.ExternalSystemErrorContext;
import de.metas.process.PInstanceId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

/**
 * Step definitions for simulating external system error responses.
 * Used to test error handling in external system integrations.
 */
@RequiredArgsConstructor
public class ExternalSystem_Error_StepDef
{
	@NonNull private final M_InOut_StepDefData inoutTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;

	@And("the external system sends an error response for the shipment")
	public void sendErrorResponseForShipments(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::sendErrorResponseForShipment);
	}

	private void sendErrorResponseForShipment(@NonNull final DataTableRow row) throws IOException
	{
		final StepDefDataIdentifier inoutIdentifier = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID);


		final org.compiere.model.I_M_InOut inout = inoutTable.get(inoutIdentifier);
		assertThat(inout).isNotNull();

		final I_M_InOut ediInout = InterfaceWrapperHelper.create(inout, I_M_InOut.class);
		final PInstanceId pInstanceId = PInstanceId.ofRepoIdOrNull(ediInout.getEDI_AD_PInstance_ID());

		assertThat(pInstanceId)
				.as("EDI_AD_PInstance_ID should be set on M_InOut %s", inoutIdentifier)
				.isNotNull();

		sendErrorResponse(row, pInstanceId);
	}

	private void sendErrorResponse(@NonNull final DataTableRow row, @NonNull final PInstanceId pInstanceId) throws IOException
	{
		final String errorMessage = row.getAsString("ErrorMessage");

		final JsonError jsonError = JsonError.builder()
				.error(JsonErrorItem.builder()
						.message(errorMessage)
						.errorContext(ExternalSystemErrorContext.EDI.getCode())
						.build())
				.build();

		// Serialize to JSON
		final String jsonPayload = serializeToJson(jsonError);

		// Send error to metasfresh REST API endpoint
		final String endpointPath = "/api/v2/externalsystem/externalstatus/" + pInstanceId.getRepoId() + "/error";

		// Get auth token (assumes test setup has already created a token via "receives a random a API token" step)
		final String authToken = RESTUtil.getAuthToken("metasfresh", "WebUI");

		// Perform POST request
		RESTUtil.performHTTPRequest(
				APIRequest.builder()
						.authToken(authToken)
						.endpointPath(endpointPath)
						.method("POST")
						.payload(jsonPayload)
						.expectedStatusCode(200)
						.build()
		);
	}

	@NonNull
	private String serializeToJson(@NonNull final JsonError jsonError)
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(jsonError);
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException("Failed to serialize JsonError to JSON", e);
		}
	}

	@And("the external system sends an error response for the invoice")
	public void sendErrorResponseForInvoices(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::sendErrorResponseForInvoice);
	}

	private void sendErrorResponseForInvoice(@NonNull final DataTableRow row) throws IOException
	{
		final StepDefDataIdentifier invoiceIdentifier = row.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID);


		final org.compiere.model.I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);
		assertThat(invoice).isNotNull();

		final I_C_Invoice ediInvoice = InterfaceWrapperHelper.create(invoice, I_C_Invoice.class);
		final PInstanceId pInstanceId = PInstanceId.ofRepoIdOrNull(ediInvoice.getEDI_AD_PInstance_ID());

		assertThat(pInstanceId)
				.as("EDI_AD_PInstance_ID should be set on C_Invoice %s", invoiceIdentifier)
				.isNotNull();

		sendErrorResponse(row, pInstanceId);
	}
}
