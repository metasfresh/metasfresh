/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.shipmentschedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.shipping.v2.JsonProduct;
import de.metas.common.shipping.v2.shipmentcandidate.JsonCustomer;
import de.metas.common.shipping.v2.shipmentcandidate.JsonResponseShipmentCandidate;
import de.metas.common.shipping.v2.shipmentcandidate.JsonResponseShipmentCandidates;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import de.metas.material.event.commons.AttributesKey;
import de.metas.rest_api.v2.attributes.JsonAttributeService;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit.COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class M_ShipmentSchedule_StepDef
{
	private static final String SHIP_BPARTNER = "shipBPartner";
	private static final String BILL_BPARTNER = "billBPartner";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final StepDefData<I_AD_User> userTable;
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_C_BPartner_Location> bpartnerLocationTable;
	private final StepDefData<I_C_Order> orderTable;
	private final StepDefData<I_C_OrderLine> orderLineTable;
	private final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable;
	private final StepDefData<I_M_Shipper> shipperTable;
	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_M_ShipmentSchedule_ExportAudit> shipmentScheduleExportAuditTable;
	private final StepDefData<I_M_AttributeSetInstance> attributeSetInstanceTable;
	private final TestContext testContext;
	private final JsonAttributeService jsonAttributeService;

	final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	public M_ShipmentSchedule_StepDef(
			@NonNull final StepDefData<I_AD_User> userTable,
			@NonNull final StepDefData<I_C_BPartner> bpartnerTable,
			@NonNull final StepDefData<I_C_BPartner_Location> bpartnerLocationTable,
			@NonNull final StepDefData<I_C_Order> orderTable,
			@NonNull final StepDefData<I_C_OrderLine> orderLineTable,
			@NonNull final StepDefData<I_M_ShipmentSchedule> shipmentScheduleTable,
			@NonNull final StepDefData<I_M_Shipper> shipperTable,
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_M_ShipmentSchedule_ExportAudit> shipmentScheduleExportAuditTable,
			@NonNull final StepDefData<I_M_AttributeSetInstance> attributeSetInstanceTable,
			@NonNull final TestContext testContext)
	{
		this.userTable = userTable;
		this.bpartnerTable = bpartnerTable;
		this.bpartnerLocationTable = bpartnerLocationTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.shipperTable = shipperTable;
		this.productTable = productTable;
		this.shipmentScheduleExportAuditTable = shipmentScheduleExportAuditTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.testContext = testContext;

		this.jsonAttributeService = SpringContextHolder.instance.getBean(JsonAttributeService.class);
	}

	/**
	 * Match the shipment scheds and load them with their identifier into the shipmentScheduleTable.
	 */
	@Then("^after not more than (.*)s, M_ShipmentSchedules are found:$")
	public void thereAreShipmentSchedules(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		// create query per table row; run the queries repeatedly until they succeed or the timeout is exceeded
		// if they succeed, put them into shipmentScheduleTable
		final ShipmentScheduleQueries shipmentScheduleQueries = createShipmentScheduleQueries(dataTable);

		final Supplier<Boolean> shipmentScheduleQueryExecutor = () -> {
			if (shipmentScheduleQueries.isAllDone())
			{
				return true;
			}

			shipmentScheduleTable.putAll(shipmentScheduleQueries.executeAllRemaining());
			return shipmentScheduleQueries.isAllDone();
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, shipmentScheduleQueryExecutor);

		assertThat(shipmentScheduleQueries.isAllDone()).as("Not all M_ShipmentSchedules were created within the %s second timout", timeoutSec).isTrue();
	}

	@And("^the shipment schedule identified by (.*) is processed after not more than (.*) seconds$")
	public void processedShipmentScheduleByIdentifier(@NonNull final String identifier, final int timeoutSec) throws InterruptedException
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(identifier);

		final Supplier<Boolean> isShipmentScheduleProcessed = () -> {

			final I_M_ShipmentSchedule record = queryBL
					.createQueryBuilder(I_M_ShipmentSchedule.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
					.create()
					.firstOnlyNotNull(I_M_ShipmentSchedule.class);

			return record.isProcessed();
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isShipmentScheduleProcessed);

		assertThat(isShipmentScheduleProcessed.get())
				.as("M_ShipmentSchedules with identifier %s was not processed within the %s second timout", identifier, timeoutSec)
				.isTrue();
	}

	@Then("the shipment-schedule is closed")
	public void assertShipmentScheduleIsClosed(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			assertShipmentScheduleIsClosed(tableRow);
		}
	}

	@And("validate that there are no M_ShipmentSchedule_Recompute records after no more than {int} seconds for order {string}")
	public void validate_no_records(final int timeoutSec, final String orderIdentifier) throws InterruptedException
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final ImmutableList<Integer> shipmentScheduleIds = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.stream()
				.map(I_M_ShipmentSchedule::getM_ShipmentSchedule_ID)
				.collect(ImmutableList.toImmutableList());

		assertThat(shipmentScheduleIds.size()).isGreaterThan(0);

		final Supplier<Boolean> noRecords = () -> {
			final List<I_M_ShipmentSchedule_Recompute> records = queryBL.createQueryBuilder(I_M_ShipmentSchedule_Recompute.class)
					.addInArrayFilter(I_M_ShipmentSchedule_Recompute.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
					.create()
					.list();

			return records.size() == 0;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, noRecords);

		assertThat(noRecords.get())
				.as("There are still records in M_ShipmentSchedules_Recompute after %s second timeout", timeoutSec)
				.isTrue();
	}

	@And("validate M_ShipmentSchedule:")
	public void validate_M_ShipmentSchedule(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			validateSchedule(row);
		}
	}

	@And("validate JsonResponseShipmentCandidates.JsonCustomer")
	public void validate_JsonCustomer(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonResponseShipmentCandidates jsonResponseShipmentCandidates = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseShipmentCandidates.class);
		assertThat(jsonResponseShipmentCandidates).isNotNull();

		final List<JsonResponseShipmentCandidate> items = jsonResponseShipmentCandidates.getItems();
		assertThat(items.size()).isEqualTo(1);

		final JsonResponseShipmentCandidate item = items.get(0);

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String qualifier = DataTableUtil.extractStringForColumnName(row, "qualifier");

			if (qualifier.equals(SHIP_BPARTNER))
			{
				validateJsonCustomer(item.getShipBPartner(), row);
			}
			else if (qualifier.equals(BILL_BPARTNER))
			{
				validateJsonCustomer(item.getBillBPartner(), row);
			}
		}
	}

	@And("validate JsonResponseShipmentCandidates")
	public void validate_JsonResponseShipmentCandidates(@NonNull final DataTable dataTable) throws JsonProcessingException
	{
		final JsonResponseShipmentCandidates jsonResponseShipmentCandidates = mapper.readValue(testContext.getApiResponse().getContent(), JsonResponseShipmentCandidates.class);
		assertThat(jsonResponseShipmentCandidates).isNotNull();

		final List<JsonResponseShipmentCandidate> items = jsonResponseShipmentCandidates.getItems();
		assertThat(items.size()).isEqualTo(1);

		final JsonResponseShipmentCandidate item = items.get(0);
		final Map<String, String> row = dataTable.asMaps().get(0);

		final I_M_ShipmentSchedule_ExportAudit scheduleExportAudit = queryBL.createQueryBuilder(I_M_ShipmentSchedule_ExportAudit.class)
				.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit.COLUMNNAME_TransactionIdAPI, jsonResponseShipmentCandidates.getTransactionKey())
				.create()
				.firstOnlyNotNull(I_M_ShipmentSchedule_ExportAudit.class);

		final String scheduleExportAuditIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID + "." + TABLECOLUMN_IDENTIFIER);
		shipmentScheduleExportAuditTable.put(scheduleExportAuditIdentifier, scheduleExportAudit);

		final String scheduleIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(scheduleIdentifier);
		assertThat(item.getId().getValue()).isEqualTo(schedule.getM_ShipmentSchedule_ID());

		//product
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.Table_Name + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);

		final JsonProduct jsonProduct = item.getProduct();

		assertThat(jsonProduct.getProductNo()).isEqualTo(product.getValue());
		assertThat(jsonProduct.getName()).isEqualTo(product.getName());
		assertThat(jsonProduct.getDescription()).isEqualTo(product.getDescription());
		assertThat(jsonProduct.isStocked()).isEqualTo(product.isStocked());

		final String shipperIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_Shipper.Table_Name + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipperIdentifier))
		{
			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			assertThat(item.getShipperInternalSearchKey()).isEqualTo(shipper.getInternalName());
		}

		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_POReference);
		if (Check.isNotBlank(poReference))
		{
			assertThat(item.getPoReference()).isEqualTo(poReference);
		}

		final BigDecimal orderedQty = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered);
		final String uomCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_UOM.COLUMNNAME_X12DE355);
		if (orderedQty != null)
		{
			assertThat(item.getOrderedQty()).isNotEmpty();
			assertThat(item.getOrderedQty().get(0).getQty()).isEqualTo(orderedQty);
			assertThat(item.getOrderedQty().get(0).getUomCode()).isEqualTo(uomCode);
		}

		final int numberOfItemsForSameShipment = DataTableUtil.extractIntOrMinusOneForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_NrOfOLCandsWithSamePOReference);
		if (numberOfItemsForSameShipment > 0)
		{
			assertThat(item.getNumberOfItemsForSameShipment()).isEqualTo(numberOfItemsForSameShipment);
		}

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMN_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(asiIdentifier))
		{
			final JsonAttributeSetInstance jsonAttributeSetInstance = item.getAttributeSetInstance();

			final AttributeSetInstanceId actualASI = jsonAttributeService.computeAttributeSetInstanceFromJson(jsonAttributeSetInstance)
					.orElse(null);
			assertThat(actualASI).isNotNull();

			final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(asiIdentifier);
			assertThat(expectedASI).isNotNull();

			final AttributesKey actualAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(actualASI).orElse(AttributesKey.NONE);
			final AttributesKey expectedAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			assertThat(actualAttributesKeys).isEqualTo(expectedAttributesKeys);
		}

		final BigDecimal orderedQtyNetPrice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT.OrderedQtyNetPrice");
		if (orderedQtyNetPrice != null)
		{
			assertThat(item.getOrderedQtyNetPrice()).isEqualTo(orderedQtyNetPrice);
		}

		final BigDecimal qtyToDeliverNetPrice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT.QtyToDeliverNetPrice");
		if (qtyToDeliverNetPrice != null)
		{
			assertThat(item.getQtyToDeliverNetPrice()).isEqualTo(qtyToDeliverNetPrice);
		}

		final BigDecimal deliveredQtyNetPrice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT.DeliveredQtyNetPrice");
		if (deliveredQtyNetPrice != null)
		{
			assertThat(item.getDeliveredQtyNetPrice()).isEqualTo(deliveredQtyNetPrice);
		}
	}

	@And("deactivate all M_ShipmentSchedule records")
	public void deactivate_M_ShipmentSchedule()
	{
		final ICompositeQueryUpdater<I_M_ShipmentSchedule> updater = queryBL
				.createCompositeQueryUpdater(I_M_ShipmentSchedule.class)
				.addSetColumnValue(I_M_ShipmentSchedule.COLUMNNAME_IsActive, false);

		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.update(updater);
	}

	private ShipmentScheduleQueries createShipmentScheduleQueries(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final ShipmentScheduleQueries.ShipmentScheduleQueriesBuilder queries = ShipmentScheduleQueries.builder();
		for (final Map<String, String> tableRow : tableRows)
		{
			final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class);

			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID + ".Identifier");

			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
			queryBuilder.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());

			final int warehouseId = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT.Warehouse_ID");

			if (warehouseId > 0)
			{
				queryBuilder.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID, warehouseId);
			}

			final IQuery<I_M_ShipmentSchedule> query = queryBuilder.create();

			final String isToRecompute = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_M_ShipmentSchedule.COLUMNNAME_IsToRecompute);

			final ShipmentScheduleQuery shipmentScheduleQuery = ShipmentScheduleQuery.builder()
					.shipmentScheduleIdentifier(DataTableUtil.extractRecordIdentifier(tableRow, COLUMNNAME_M_ShipmentSchedule_ID))
					.query(query)
					.isToRecompute(StringUtils.toBoolean(isToRecompute, null))
					.build();
			queries.query(shipmentScheduleQuery);
		}
		return queries.build();
	}

	@Value
	private static class ShipmentScheduleQueries
	{
		ImmutableList<ShipmentScheduleQuery> allQueries;

		HashSet<ShipmentScheduleQuery> remainingQueries;

		@Builder
		private ShipmentScheduleQueries(@Singular final ImmutableList<ShipmentScheduleQuery> queries)
		{
			this.allQueries = queries;
			this.remainingQueries = new HashSet<>(queries);
		}

		public Map<String, I_M_ShipmentSchedule> executeAllRemaining()
		{
			final ImmutableMap.Builder<String, I_M_ShipmentSchedule> result = ImmutableMap.builder();
			for (final ShipmentScheduleQuery query : allQueries)
			{
				if (!remainingQueries.contains(query))
				{
					continue;
				}
				final I_M_ShipmentSchedule shipmentSchedule = query.execute();
				if (shipmentSchedule != null)
				{
					remainingQueries.remove(query);
					result.put(query.shipmentScheduleIdentifier, shipmentSchedule);
				}
			}
			return result.build();
		}

		public boolean isAllDone()
		{
			return remainingQueries.isEmpty();
		}
	}

	@Value
	@Builder
	private static class ShipmentScheduleQuery
	{
		String shipmentScheduleIdentifier;

		IQuery<I_M_ShipmentSchedule> query;

		Boolean isToRecompute;

		IShipmentScheduleInvalidateBL scheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);

		@Nullable
		public I_M_ShipmentSchedule execute()
		{
			final I_M_ShipmentSchedule shipmentScheduleRecord = query.firstOnly(I_M_ShipmentSchedule.class);
			if (shipmentScheduleRecord == null)
			{
				return null;
			}

			if (isToRecompute != null)
			{
				final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentScheduleRecord.getM_ShipmentSchedule_ID());
				final boolean flaggedForRecompute = scheduleInvalidateBL.isFlaggedForRecompute(shipmentScheduleId);
				return flaggedForRecompute == isToRecompute ? shipmentScheduleRecord : null;
			}
			return shipmentScheduleRecord;
		}
	}

	private void assertShipmentScheduleIsClosed(@NonNull final Map<String, String> tableRow)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_ShipmentSchedule_ID + ".Identifier");
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final I_M_ShipmentSchedule refreshedSchedule = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.create()
				.firstOnlyNotNull(I_M_ShipmentSchedule.class);

		assertNotNull(shipmentSchedule);
		assertEquals(Boolean.TRUE, refreshedSchedule.isClosed());
	}

	private void validateSchedule(@NonNull final Map<String, String> row)
	{
		final String scheduleIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(scheduleIdentifier);
		InterfaceWrapperHelper.refresh(schedule);
		assertThat(schedule).isNotNull();

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
		assertThat(schedule.getC_BPartner_ID()).isEqualTo(bPartner.getC_BPartner_ID());

		final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bpLocation = bpartnerLocationTable.get(bpLocationIdentifier);
		assertThat(schedule.getC_BPartner_Location_ID()).isEqualTo(bpLocation.getC_BPartner_Location_ID());

		final String billBPIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner billBP = bpartnerTable.get(billBPIdentifier);
		assertThat(schedule.getBill_BPartner_ID()).isEqualTo(billBP.getC_BPartner_ID());

		final String billBPLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location billBPLocation = bpartnerLocationTable.get(billBPLocationIdentifier);
		assertThat(schedule.getBill_Location_ID()).isEqualTo(billBPLocation.getC_BPartner_Location_ID());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(schedule.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

		final String exportStatus = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_ExportStatus);
		assertThat(schedule.getExportStatus()).isEqualTo(exportStatus);

		final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderIdentifier))
		{
			final I_C_Order order = orderTable.get(orderIdentifier);
			assertThat(schedule.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
		}

		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderLineIdentifier))
		{
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
			assertThat(schedule.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		}

		final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(userIdentifier))
		{
			final I_AD_User user = userTable.get(userIdentifier);
			assertThat(schedule.getAD_User_ID()).isEqualTo(user.getAD_User_ID());
		}

		final String billUserIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billUserIdentifier))
		{
			final I_AD_User billUser = userTable.get(billUserIdentifier);
			assertThat(schedule.getBill_User_ID()).isEqualTo(billUser.getAD_User_ID());
		}

		final String shipperIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(shipperIdentifier))
		{
			final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
			assertThat(schedule.getM_Shipper_ID()).isEqualTo(shipper.getM_Shipper_ID());
		}

		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(attributeSetInstanceIdentifier))
		{
			final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);

			final AttributesKey actualAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(schedule.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);
			final AttributesKey expectedAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			assertThat(actualAttributesKeys).isEqualTo(expectedAttributesKeys);
		}
	}

	private void validateJsonCustomer(
			@NonNull final JsonCustomer jsonCustomer,
			@NonNull final Map<String, String> row)
	{
		final String companyName = DataTableUtil.extractStringForColumnName(row, "companyName");
		final String contactName = DataTableUtil.extractStringOrNullForColumnName(row, "contactName");
		final String contactEmail = DataTableUtil.extractStringOrNullForColumnName(row, "contactEmail");
		final String contactPhone = DataTableUtil.extractStringOrNullForColumnName(row, "contactPhone");
		final String street = DataTableUtil.extractStringForColumnName(row, "street");
		final String streetNo = DataTableUtil.extractStringForColumnName(row, "streetNo");
		final String postal = DataTableUtil.extractStringForColumnName(row, "postal");
		final String city = DataTableUtil.extractStringForColumnName(row, "city");
		final String countryCode = DataTableUtil.extractStringForColumnName(row, "countryCode");
		final String language = DataTableUtil.extractStringForColumnName(row, "language");
		final boolean isCompany = DataTableUtil.extractBooleanForColumnName(row, "company");

		assertThat(jsonCustomer.getStreet()).isEqualTo(street);
		assertThat(jsonCustomer.getStreetNo()).isEqualTo(streetNo);
		assertThat(jsonCustomer.getPostal()).isEqualTo(postal);
		assertThat(jsonCustomer.getCity()).isEqualTo(city);
		assertThat(jsonCustomer.getCountryCode()).isEqualTo(countryCode);
		assertThat(jsonCustomer.getContactName()).isEqualTo(contactName);
		assertThat(jsonCustomer.getContactEmail()).isEqualTo(contactEmail);
		assertThat(jsonCustomer.getContactPhone()).isEqualTo(contactPhone);
		assertThat(jsonCustomer.getLanguage()).isEqualTo(language);
		assertThat(jsonCustomer.isCompany()).isEqualTo(isCompany);
		assertThat(jsonCustomer.getCompanyName()).isEqualTo(companyName);
	}
}
