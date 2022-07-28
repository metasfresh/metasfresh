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

package de.metas.cucumber.stepdefs.olcand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import de.metas.common.ordercandidates.v2.response.JsonOLCandProcessResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentResponse;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.ordercandidates.impl.JsonProcessCompositeResponse;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

public class C_OLCand_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_M_InOut> shipmentTable;
	private final StepDefData<I_C_Invoice> invoiceTable;
	private final TestContext testContext;

	final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	public C_OLCand_StepDef(
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_M_InOut> shipmentTable,
			@NonNull final StepDefData<I_C_Invoice> invoiceTable,
			@NonNull final TestContext testContext)
	{
		this.orderTable = orderTable;
		this.shipmentTable = shipmentTable;
		this.invoiceTable = invoiceTable;
		this.testContext = testContext;
	}

	@Then("process metasfresh response")
	public void process_metasfresh_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonProcessCompositeResponse compositeResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonProcessCompositeResponse.class);
		assertThat(compositeResponse).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);
		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "C_Order_ID.Identifier");
		final String shipmentIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "M_InOut_ID.Identifier");
		final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "C_Invoice_ID.Identifier");

		if (orderIdentifier == null)
		{
			assertThat(compositeResponse.getOlCandProcessResponse()).isEqualTo(null);
		}
		else
		{
			processOrderResponse(compositeResponse.getOlCandProcessResponse(), orderIdentifier);
		}

		if (shipmentIdentifier == null)
		{
			assertThat(compositeResponse.getShipmentResponse()).isEqualTo(null);
		}
		else
		{
			processShipmentResponse(compositeResponse.getShipmentResponse(), shipmentIdentifier);
		}

		if (invoiceIdentifier == null)
		{
			assertThat(compositeResponse.getInvoiceInfoResponse()).isEqualTo(null);
		}
		else
		{
			processInvoiceResponse(compositeResponse.getInvoiceInfoResponse(), invoiceIdentifier);
		}
	}

	private void processOrderResponse(
			@NonNull final JsonOLCandProcessResponse response,
			@NonNull final String orderIdentifier)
	{
		final List<OrderId> generatedOrderIds = response.getJsonGenerateOrdersResponse()
				.getOrderIds()
				.stream()
				.map(JsonMetasfreshId::getValue)
				.map(OrderId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		assertThat(generatedOrderIds).isNotEmpty();

		final I_C_Order order = queryBL.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, generatedOrderIds)
				.create()
				.firstOnly(I_C_Order.class);

		assertThat(order).isNotNull();

		orderTable.putOrReplace(orderIdentifier, order);
	}

	private void processShipmentResponse(
			@NonNull final JsonCreateShipmentResponse response,
			@NonNull final String shipmentIdentifier)
	{
		final List<InOutId> generatedShipmentIds = response.getCreatedShipmentIds()
				.stream()
				.map(JsonMetasfreshId::getValue)
				.map(InOutId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		assertThat(generatedShipmentIds).isNotEmpty();

		final List<I_M_InOut> shipments = queryBL.createQueryBuilder(I_M_InOut.class)
				.addInArrayFilter(I_M_InOut.COLUMNNAME_M_InOut_ID, generatedShipmentIds)
				.create()
				.list();

		assertThat(shipments).isNotNull();

		if (shipments.size() > 1)
		{
			final List<String> identifiers = splitIdentifiers(shipmentIdentifier);

			for (int index = 0; index < shipments.size(); index++)
			{
				shipmentTable.putOrReplace(identifiers.get(index), shipments.get(index));
			}
		}
		else
		{
			shipmentTable.putOrReplace(shipmentIdentifier, shipments.get(0));
		}
	}

	private void processInvoiceResponse(
			@NonNull final List<JSONInvoiceInfoResponse> response,
			@NonNull final String invoiceIdentifier)
	{
		final List<InvoiceId> generatedInvoiceIds = response.stream()
				.map(JSONInvoiceInfoResponse::getInvoiceId)
				.filter(Objects::nonNull)
				.map(JsonMetasfreshId::getValue)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		assertThat(generatedInvoiceIds).isNotEmpty();

		final I_C_Invoice invoice = queryBL.createQueryBuilder(I_C_Invoice.class)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, generatedInvoiceIds)
				.create()
				.firstOnly(I_C_Invoice.class);

		assertThat(invoice).isNotNull();

		invoiceTable.putOrReplace(invoiceIdentifier, invoice);
	}

	@NonNull
	private List<String> splitIdentifiers(@NonNull final String identifiers)
	{
		return Arrays.asList(identifiers.split(","));
	}
}
