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
import de.metas.common.ordercandidates.v2.response.JsonGenerateOrdersResponse;
import de.metas.common.ordercandidates.v2.response.JsonOLCand;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentResponse;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.issue.AD_Issue_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.model.I_AD_InputDataSource;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.rest_api.v2.invoice.impl.JSONInvoiceInfoResponse;
import de.metas.rest_api.v2.ordercandidates.impl.JsonProcessCompositeResponse;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_OLCand_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_ErrorMsg;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_IsError;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_ExternalLineId;
import static org.assertj.core.api.Assertions.*;

public class C_OLCand_StepDef
{
	public static final JsonCreateShipmentResponse EMPTY_SHIPMENT_RESPONSE = JsonCreateShipmentResponse.builder().createdShipmentIds(ImmutableList.of()).build();

	private static final Logger logger = LogManager.getLogger(C_OLCand_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Order_StepDefData orderTable;
	private final M_InOut_StepDefData shipmentTable;
	private final C_Invoice_StepDefData invoiceTable;
	private final C_OLCand_StepDefData olCandTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	private final M_Product_StepDefData productTable;
	private final AD_Issue_StepDefData issueTable;
	private final TestContext testContext;

	final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	public C_OLCand_StepDef(
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final C_Invoice_StepDefData invoiceTable,
			@NonNull final C_OLCand_StepDefData olCandTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bpartnerLocationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final AD_Issue_StepDefData issueTable,
			@NonNull final TestContext testContext)
	{
		this.orderTable = orderTable;
		this.shipmentTable = shipmentTable;
		this.invoiceTable = invoiceTable;
		this.olCandTable = olCandTable;
		this.bpartnerTable = bpartnerTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.productTable = productTable;
		this.issueTable = issueTable;
		this.testContext = testContext;
	}

	@Then("process metasfresh response")
	public void process_metasfresh_response(@NonNull final DataTable table) throws JsonProcessingException
	{
		try
		{
			processResponse(table);
		}
		catch (final Exception e)
		{
			final StringBuilder message = new StringBuilder();
			message.append("C_OLCand records could not be processed. See:\n");

			final JsonOLCandProcessRequest request = mapper.readValue(testContext.getRequestPayload(), JsonOLCandProcessRequest.class);
			assertThat(request).isNotNull();

			logOLCandidateRecords(message, request);

			throw e;
		}
	}

	@Then("validate order line allocated 'line'")
	public void validate_OrderLine_Allocated_Line(@NonNull final DataTable table)
	{
		final List<Map<String, String>> tableRows = table.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateOrderLineAllocatedLine(tableRow);
		}
	}

	@And("process metasfresh response JsonOLCandCreateBulkResponse")
	public void process_JsonOLCandCreateBulkResponse(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonOLCandCreateBulkResponse olCandCreateBulkResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonOLCandCreateBulkResponse.class);
		assertThat(olCandCreateBulkResponse).isNotNull();

		final List<JsonOLCand> jsonOLCands = olCandCreateBulkResponse.getResult();
		assertThat(jsonOLCands).isNotEmpty();

		final Map<String, String> row = dataTable.asMaps().get(0);
		final String olCandIdentifiers = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_C_OLCand_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final List<String> identifiers = StepDefUtil.splitIdentifiers(olCandIdentifiers);
		assertThat(jsonOLCands.size()).isEqualTo(identifiers.size());

		for (int index = 0; index < identifiers.size(); index++)
		{
			final int olCandId = jsonOLCands.get(index).getId();

			final I_C_OLCand olCandRecord = InterfaceWrapperHelper.load(olCandId, I_C_OLCand.class);
			assertThat(olCandRecord).isNotNull();

			final String olCandIdentifier = identifiers.get(index);
			olCandTable.putOrReplace(olCandIdentifier, olCandRecord);
		}
	}

	@And("validate C_OLCand:")
	public void validate_C_OLCand(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : rows)
		{
			final String olCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_C_OLCand_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_OLCand olCand = olCandTable.get(olCandIdentifier);
			InterfaceWrapperHelper.refresh(olCand);
			assertThat(olCand).isNotNull();

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			assertThat(olCand.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());

			final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bpartnerLocationTable.get(bpLocationIdentifier);
			assertThat(olCand.getC_BPartner_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(olCand.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

			final String deliveryRule = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_DeliveryRule);
			assertThat(olCand.getDeliveryRule()).isEqualTo(deliveryRule);

			final String deliveryViaRule = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_DeliveryViaRule);
			assertThat(olCand.getDeliveryViaRule()).isEqualTo(deliveryViaRule);

			final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_POReference);
			if (Check.isNotBlank(poReference))
			{
				assertThat(olCand.getPOReference()).isEqualTo(poReference);
			}

			final String adInputDataSourceName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_Name);
			if (Check.isNotBlank(adInputDataSourceName))
			{
				final I_AD_InputDataSource inputDataSource = InterfaceWrapperHelper.load(olCand.getAD_InputDataSource_ID(), I_AD_InputDataSource.class);
				assertThat(inputDataSource.getName()).isEqualTo(adInputDataSourceName);
			}

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_OLCand.COLUMNNAME_QtyEntered);
			assertThat(olCand.getQtyEntered()).isEqualTo(qtyEntered);

			final Boolean isError = DataTableUtil.extractBooleanForColumnName(row, I_C_OLCand.COLUMNNAME_IsError);
			assertThat(olCand.isError()).isEqualTo(isError);

			final Boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_OLCand.COLUMNNAME_Processed, false);
			assertThat(olCand.isProcessed()).isEqualTo(processed);

			final String externalHeaderId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_ExternalHeaderId);
			if (Check.isNotBlank(externalHeaderId))
			{
				assertThat(olCand.getExternalHeaderId()).isEqualTo(externalHeaderId);
			}

			final String externalLineId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_ExternalLineId);
			if (Check.isNotBlank(externalLineId))
			{
				assertThat(olCand.getExternalLineId()).isEqualTo(externalLineId);
			}

			final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_PriceActual);
			if (priceActual != null)
			{
				assertThat(olCand.getPriceActual()).isEqualTo(priceActual);
			}

			final String issueIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_AD_Issue_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(issueIdentifier))
			{
				final I_AD_Issue issue = InterfaceWrapperHelper.load(olCand.getAD_Issue_ID(), I_AD_Issue.class);
				assertThat(issue).isNotNull();

				issueTable.putOrReplace(issueIdentifier, issue);
			}
		}
	}

	private void validateOrderLineAllocatedLine(@NonNull final Map<String, String> row)
	{
		final String externalLineId = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ExternalLineId);
		final int olCandLine = DataTableUtil.extractIntForColumnName(row, I_C_OLCand.Table_Name + "." + I_C_OLCand.COLUMNNAME_Line);
		final int olLine = DataTableUtil.extractIntForColumnName(row, I_C_OrderLine.Table_Name + "." + I_C_OrderLine.COLUMNNAME_Line);

		final I_C_OLCand olCand = queryBL.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_ExternalLineId, externalLineId)
				.create()
				.firstOnlyNotNull(I_C_OLCand.class);

		final I_C_Order_Line_Alloc orderLineAlloc = queryBL.createQueryBuilder(I_C_Order_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_C_OLCand_ID, olCand.getC_OLCand_ID())
				.create()
				.firstOnlyNotNull(I_C_Order_Line_Alloc.class);

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.load(orderLineAlloc.getC_OrderLine_ID(), I_C_OrderLine.class);

		assertThat(olCand.getLine()).isEqualTo(olCandLine);
		assertThat(orderLine.getLine()).isEqualTo(olLine);
	}

	private void processOrderResponse(
			@NonNull final JsonGenerateOrdersResponse response,
			@NonNull final String orderIdentifier)
	{
		final List<OrderId> generatedOrderIds = response
				.getOrderIds()
				.stream()
				.map(JsonMetasfreshId::getValue)
				.sorted()
				.map(OrderId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		assertThat(generatedOrderIds).isNotEmpty();

		final List<I_C_Order> orders = queryBL.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, generatedOrderIds)
				.orderBy(I_C_Order.COLUMNNAME_C_Order_ID)
				.create()
				.list();

		assertThat(orders).isNotEmpty();

		if (orders.size() > 1)
		{
			final List<String> identifiers = StepDefUtil.splitIdentifiers(orderIdentifier);

			for (int index = 0; index < identifiers.size(); index++)
			{
				orderTable.putOrReplace(identifiers.get(index), orders.get(index));
			}
		}
		else
		{
			orderTable.putOrReplace(orderIdentifier, orders.get(0));
		}
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
				.orderBy(I_M_InOut.COLUMNNAME_M_InOut_ID) // important to avoid mixing up shipments
				.create()
				.list();

		assertThat(shipments).isNotEmpty();

		final List<String> identifiers = StepDefUtil.splitIdentifiers(shipmentIdentifier);
		assertThat(identifiers).hasSameSizeAs(shipments);

		if (shipments.size() > 1)
		{
			for (int index = 0; index < shipments.size(); index++)
			{
				final String identifier = identifiers.get(index);
				final I_M_InOut record = shipments.get(index);
				shipmentTable.putOrReplace(identifier, record);
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

	private void processResponse(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonProcessCompositeResponse compositeResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonProcessCompositeResponse.class);
		assertThat(compositeResponse).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);
		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "C_Order_ID.Identifier");
		final String shipmentIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "M_InOut_ID.Identifier");
		final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "C_Invoice_ID.Identifier");

		if (orderIdentifier == null)
		{
			assertThat(compositeResponse.getOrderResponse()).isEqualTo(null);
		}
		else
		{
			processOrderResponse(compositeResponse.getOrderResponse(), orderIdentifier);
		}

		if (shipmentIdentifier == null)
		{
			// we expect that there are no infos about any generated shipments
			if (compositeResponse.getShipmentResponse() != null)
			{
				assertThat(compositeResponse.getShipmentResponse()).isEqualTo(EMPTY_SHIPMENT_RESPONSE);
			}
		}
		else
		{
			processShipmentResponse(compositeResponse.getShipmentResponse(), shipmentIdentifier);
		}

		if (invoiceIdentifier == null)
		{
			// we expect that there are no infos about any generated invoice
			if (compositeResponse.getInvoiceInfoResponse() != null)
			{
				assertThat(compositeResponse.getInvoiceInfoResponse()).isEmpty();
			}
		}
		else
		{
			processInvoiceResponse(compositeResponse.getInvoiceInfoResponse(), invoiceIdentifier);
		}
	}

	private void logOLCandidateRecords(@NonNull final StringBuilder message, @NonNull final JsonOLCandProcessRequest request)
	{
		message.append("C_OLCand infos:").append("\n");

		queryBL.createQueryBuilder(I_C_OLCand.class)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_ExternalHeaderId, request.getExternalHeaderId())
				.create()
				.stream(I_C_OLCand.class)
				.forEach(olCandRecord -> message
						.append(COLUMNNAME_C_OLCand_ID).append(" : ").append(olCandRecord.getC_OLCand_ID()).append(" ; ")
						.append(COLUMNNAME_IsError).append(" : ").append(olCandRecord.isError()).append(" ; ")
						.append(COLUMNNAME_ErrorMsg).append(" : ").append(olCandRecord.getErrorMsg()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while processing C_OLCand records, see current context: \n" + message);
	}
}
