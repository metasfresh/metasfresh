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

package de.metas.cucumber.stepdefs.olcand;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.ordercandidates.v2.response.JsonGenerateOrdersResponse;
import de.metas.common.ordercandidates.v2.response.JsonOLCand;
import de.metas.common.ordercandidates.v2.response.JsonOLCandCreateBulkResponse;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.shipping.v2.shipment.JsonCreateShipmentResponse;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.edi.impprocessor.IMP_Processor_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.issue.AD_Issue_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.edi.model.I_AD_InputDataSource;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_C_OLCand_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DeliveryRule;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DeliveryViaRule;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_DropShip_Location_ID;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_ErrorMsg;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_ExternalHeaderId;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_ExternalLineId;
import static de.metas.ordercandidate.model.I_C_OLCand.COLUMNNAME_IsError;
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
	private final M_HU_PI_Item_Product_StepDefData huItemProductTable;
	private final IMP_Processor_StepDefData impProcessorTable;

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
			@NonNull final M_HU_PI_Item_Product_StepDefData huItemProductTable,
			@NonNull final IMP_Processor_StepDefData impProcessorTable,
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
		this.huItemProductTable = huItemProductTable;
		this.impProcessorTable = impProcessorTable;
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
		final String olCandIdentifiers = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OLCand_ID + "." + TABLECOLUMN_IDENTIFIER);

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

	@And("validate C_OLCand is with error")
	public void validate_C_OLCand_has_error(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : rows)
		{
			validateOLCandError(row);
		}
	}

	private void validateOLCandError(@NonNull final Map<String, String> row)
	{
		final String olCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_C_OLCand_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_OLCand olCand = olCandTable.get(olCandIdentifier);
		InterfaceWrapperHelper.refresh(olCand);
		assertThat(olCand).isNotNull();

		assertThat(olCand.isError()).isTrue();

		final String errorMsg = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_ErrorMsg);
		assertThat(olCand.getErrorMsg()).contains(errorMsg);
	}

	@And("validate C_OLCand:")
	public void validate_C_OLCand(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : rows)
		{
			final SoftAssertions softly = new SoftAssertions();

			final String olCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OLCand_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_OLCand olCand = olCandTable.get(olCandIdentifier);
			InterfaceWrapperHelper.refresh(olCand);
			softly.assertThat(olCand).isNotNull();

			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			softly.assertThat(olCand.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());

			final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bpartnerLocationTable.get(bpLocationIdentifier);
			softly.assertThat(olCand.getC_BPartner_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);
			softly.assertThat(olCand.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

			final String deliveryRule = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_DeliveryRule);
			softly.assertThat(olCand.getDeliveryRule()).isEqualTo(deliveryRule);

			final String deliveryViaRule = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_DeliveryViaRule);
			softly.assertThat(olCand.getDeliveryViaRule()).isEqualTo(deliveryViaRule);

			final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_POReference);
			if (Check.isNotBlank(poReference))
			{
				softly.assertThat(olCand.getPOReference()).isEqualTo(poReference);
			}

			final String adInputDataSourceName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_AD_InputDataSource_ID + "." + I_AD_InputDataSource.COLUMNNAME_Name);
			if (Check.isNotBlank(adInputDataSourceName))
			{
				final I_AD_InputDataSource inputDataSource = InterfaceWrapperHelper.load(olCand.getAD_InputDataSource_ID(), I_AD_InputDataSource.class);
				softly.assertThat(inputDataSource.getName()).isEqualTo(adInputDataSourceName);
			}

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_OLCand.COLUMNNAME_QtyEntered);
			softly.assertThat(olCand.getQtyEntered()).isEqualTo(qtyEntered);

			final Boolean isError = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsError);
			softly.assertThat(olCand.isError()).isEqualTo(isError);

			final Boolean processed = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_OLCand.COLUMNNAME_Processed, false);
			softly.assertThat(olCand.isProcessed()).isEqualTo(processed);

			final String externalHeaderId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ExternalHeaderId);
			if (Check.isNotBlank(externalHeaderId))
			{
				softly.assertThat(olCand.getExternalHeaderId()).isEqualTo(externalHeaderId);
			}

			final String externalLineId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_ExternalLineId);
			if (Check.isNotBlank(externalLineId))
			{
				softly.assertThat(olCand.getExternalLineId()).isEqualTo(externalLineId);
			}

			final BigDecimal priceActual = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_PriceActual);
			if (priceActual != null)
			{
				softly.assertThat(olCand.getPriceActual()).isEqualTo(priceActual);
			}

			final String issueIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_AD_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(issueIdentifier))
			{
				final I_AD_Issue issue = InterfaceWrapperHelper.load(olCand.getAD_Issue_ID(), I_AD_Issue.class);
				softly.assertThat(issue).isNotNull();

				issueTable.putOrReplace(issueIdentifier, issue);
			}

			final String huItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + de.metas.edi.model.I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(huItemProductIdentifier))
			{
				final int huPiItemProductId = huItemProductTable.getOptional(huItemProductIdentifier)
						.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
						.orElseGet(() -> Integer.parseInt(huItemProductIdentifier));

				softly.assertThat(olCand.getM_HU_PI_Item_Product_ID()).isEqualTo(huPiItemProductId);
			}

			final String dropShipBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_DropShip_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipBPartnerIdentifier))
			{
				final I_C_BPartner dropShipBPartner = bpartnerTable.get(dropShipBPartnerIdentifier);
				softly.assertThat(dropShipBPartner).isNotNull();

				softly.assertThat(olCand.getDropShip_BPartner_ID()).isEqualTo(dropShipBPartner.getC_BPartner_ID());
			}

			final String dropShipLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_DropShip_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipLocationIdentifier))
			{
				final I_C_BPartner_Location dropShipLocation = bpartnerLocationTable.get(dropShipLocationIdentifier);
				softly.assertThat(dropShipLocation).isNotNull();

				softly.assertThat(olCand.getDropShip_Location_ID()).isEqualTo(dropShipLocation.getC_BPartner_Location_ID());
			}

			final String handOverBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_HandOver_Partner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(handOverBPartnerIdentifier))
			{
				final I_C_BPartner handOverBPartner = bpartnerTable.get(handOverBPartnerIdentifier);
				softly.assertThat(handOverBPartner).isNotNull();

				softly.assertThat(olCand.getHandOver_Partner_ID()).isEqualTo(handOverBPartner.getC_BPartner_ID());
			}

			final String handOverLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_HandOver_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(handOverLocationIdentifier))
			{
				final I_C_BPartner_Location handOverLocation = bpartnerLocationTable.get(handOverLocationIdentifier);
				softly.assertThat(handOverLocation).isNotNull();

				softly.assertThat(olCand.getHandOver_Location_ID()).isEqualTo(handOverLocation.getC_BPartner_Location_ID());
			}

			softly.assertAll();
		}
	}

	@And("^after not more than (.*)s, C_OLCand is found")
	public void load_OLCand(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> isOLCandFound(row));
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

		assertThat(generatedOrderIds).as("missing generatedOrderIds for orderIdentifier=%s", orderIdentifier).isNotEmpty();

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

		final List<I_C_Invoice> invoices = queryBL.createQueryBuilder(I_C_Invoice.class)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, generatedInvoiceIds)
				.create()
				.list();

		assertThat(invoices).isNotEmpty();

		final List<String> identifiers = StepDefUtil.splitIdentifiers(invoiceIdentifier);
		assertThat(invoices).hasSameSizeAs(identifiers);

		if (invoices.size() > 1)
		{
			for (int index = 0; index < invoices.size(); index++)
			{
				final String identifier = identifiers.get(index);
				final I_C_Invoice record = invoices.get(index);
				invoiceTable.putOrReplace(identifier, record);
			}
		}
		else
		{
			invoiceTable.putOrReplace(invoiceIdentifier, invoices.get(0));
		}
	}

	private void processResponse(@NonNull final DataTable table) throws JsonProcessingException
	{
		final JsonProcessCompositeResponse compositeResponse = mapper.readValue(testContext.getApiResponse().getContent(), JsonProcessCompositeResponse.class);
		assertThat(compositeResponse).isNotNull();

		final Map<String, String> row = table.asMaps().get(0);
		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "C_Order_ID.Identifier");
		final String shipmentIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "M_InOut_ID.Identifier");
		final String invoiceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "C_Invoice_ID.Identifier");

		if (Check.isBlank(orderIdentifier))
		{
			assertThat(compositeResponse.getOrderResponse()).isEqualTo(null);
		}
		else
		{
			processOrderResponse(compositeResponse.getOrderResponse(), orderIdentifier);
		}

		if (Check.isBlank(shipmentIdentifier))
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

		if (Check.isBlank(invoiceIdentifier))
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
				.addEqualsFilter(COLUMNNAME_ExternalHeaderId, request.getExternalHeaderId())
				.create()
				.stream(I_C_OLCand.class)
				.forEach(olCandRecord -> message
						.append(COLUMNNAME_C_OLCand_ID).append(" : ").append(olCandRecord.getC_OLCand_ID()).append(" ; ")
						.append(COLUMNNAME_IsError).append(" : ").append(olCandRecord.isError()).append(" ; ")
						.append(COLUMNNAME_ErrorMsg).append(" : ").append(olCandRecord.getErrorMsg()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while processing C_OLCand records, see current context: \n" + message);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_OLCand> isOLCandFound(@NonNull final Map<String, String> row)
	{
		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_OLCand.COLUMNNAME_QtyEntered);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		final IQueryBuilder<I_C_OLCand> queryBuilder = queryBL.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_QtyEntered, qtyEntered)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_M_Product_ID, product.getM_Product_ID());

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpartnerIdentifier))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			assertThat(bPartner).isNotNull();

			queryBuilder.addEqualsFilter(COLUMNNAME_C_BPartner_ID, bPartner.getC_BPartner_ID());
		}

		final String huItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(huItemProductIdentifier))
		{
			final int huPiItemProductId = huItemProductTable.getOptional(huItemProductIdentifier)
					.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
					.orElseGet(() -> Integer.parseInt(huItemProductIdentifier));

			queryBuilder.addEqualsFilter(I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID, huPiItemProductId);
		}

		final Optional<I_C_OLCand> olCand = queryBuilder
				.create()
				.firstOnlyOptional(I_C_OLCand.class);

		if (olCand.isPresent())
		{
			final String olCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_OLCand_ID + "." + TABLECOLUMN_IDENTIFIER);
			olCandTable.putOrReplace(olCandIdentifier, olCand.get());

			return ItemProvider.ProviderResult.resultWasFound(olCand.get());
		}

		return ItemProvider.ProviderResult.resultWasNotFound(getCurrentContext(row));
	}

	@NonNull
	private String getCurrentContext(@NonNull final Map<String, String> row)
	{
		final StringBuilder message = new StringBuilder();

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_C_OLCand.COLUMNNAME_QtyEntered);
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_OLCand.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();

		message.append("Looking for instance with:").append("\n")
				.append(I_C_OLCand.COLUMNNAME_M_Product_ID).append(" : ").append(product.getM_Product_ID()).append("\n")
				.append(I_C_OLCand.COLUMNNAME_QtyEntered).append(" : ").append(qtyEntered).append("\n");

		final String bpartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(bpartnerIdentifier))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			assertThat(bPartner).isNotNull();

			message.append(COLUMNNAME_C_BPartner_ID).append(" : ").append(bPartner.getC_BPartner_ID()).append("\n");
		}

		final String huItemProductIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(huItemProductIdentifier))
		{
			final int huPiItemProductId = huItemProductTable.getOptional(huItemProductIdentifier)
					.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
					.orElseGet(() -> Integer.parseInt(huItemProductIdentifier));

			message.append(I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID).append(" : ").append(huPiItemProductId).append("\n");
		}

		final String impProcessorIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_IMP_Processor.COLUMNNAME_IMP_Processor_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(impProcessorIdentifier))
		{
			final I_IMP_Processor impProcessor = impProcessorTable.get(impProcessorIdentifier);
			assertThat(impProcessor).isNotNull();

			message.append(I_IMP_Processor.COLUMNNAME_DateLastRun).append(" : ").append(impProcessor.getDateLastRun()).append("\n")
					.append(I_IMP_Processor.COLUMNNAME_DateNextRun).append(" : ").append(impProcessor.getDateNextRun()).append("\n");
		}

		message.append("See all C_OLCand records for M_Product_ID = ").append(product.getM_Product_ID()).append(":\n");

		queryBL.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.stream()
				.forEach(olCand -> message
						.append("-->").append(I_C_OLCand.COLUMNNAME_QtyEntered).append(" : ").append(olCand.getQtyEntered()).append(" ; ")
						.append("-->").append(COLUMNNAME_C_BPartner_ID).append(" : ").append(olCand.getC_BPartner_ID()).append(" ; ")
						.append("-->").append(I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID).append(" : ").append(olCand.getM_HU_PI_Item_Product_ID()).append(" ; "));

		return message.toString();
	}
}
