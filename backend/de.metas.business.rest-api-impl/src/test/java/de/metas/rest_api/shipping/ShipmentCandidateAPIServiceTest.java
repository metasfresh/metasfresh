/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.business.BusinessTestHelper;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.shipping.JsonRequestCandidateResult;
import de.metas.common.shipping.JsonRequestCandidateResults;
import de.metas.common.shipping.JsonRequestCandidateResults.JsonRequestCandidateResultsBuilder;
import de.metas.common.shipping.Outcome;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates;
import de.metas.common.util.time.SystemTime;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleAuditRepository;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Item;
import de.metas.location.CountryId;
import de.metas.product.ProductRepository;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportError;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.Exported;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndForwarded;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.Pending;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentCandidateAPIServiceTest
{
	private ShipmentCandidateAPIService shipmentCandidateAPIService;
	private I_C_BPartner bPartner;
	private I_C_BPartner_Location bPartnerLocation;
	private I_C_BPartner bpartnerOverride;
	private I_C_BPartner_Location bPartnerLocationOverride;
	private I_C_UOM uom;
	private I_M_Product product;
	private I_C_Location location;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());

		bPartner = BusinessTestHelper.createBPartner("bpartner");
		bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);

		bpartnerOverride = BusinessTestHelper.createBPartner("bpartnerOverride");
		bPartnerLocationOverride = BusinessTestHelper.createBPartnerLocation(bpartnerOverride);

		final CountryId countryId = BusinessTestHelper.createCountry("DE");

		location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		saveRecord(location);
		bPartnerLocationOverride.setC_Location_ID(location.getC_Location_ID());
		saveRecord(bPartnerLocationOverride);

		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);

		bpartnerOverride.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		saveRecord(bpartnerOverride);

		uom = BusinessTestHelper.createUOM("stockUOM");
		product = BusinessTestHelper.createProduct("product", uom);

		final ShipmentCandidateExportSequenceNumberProvider exportSequenceNumberProvider = Mockito.mock(ShipmentCandidateExportSequenceNumberProvider.class);
		Mockito.doReturn(1).when(exportSequenceNumberProvider).provideNextShipmentCandidateSeqNo();

		shipmentCandidateAPIService = new ShipmentCandidateAPIService(
				new ShipmentScheduleAuditRepository(),
				new ShipmentScheduleRepository(),
				new BPartnerCompositeRepository(new MockLogEntriesRepository()),
				new ProductRepository(),
				exportSequenceNumberProvider);
	}

	@Test
	void exportShipmentCandidates_ExportError()
	{
		// given
		// that shipmentSchedule alone won't work, thus we expect an error
		final I_M_ShipmentSchedule shipmentScheduleRecord = createShipmentScheduleRecord(null,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ofInt(500));

		// then
		assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).isEmpty();

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());
		assertThat(exportAudits.get(0).getExportStatus()).isEqualTo(ExportError.getCode());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(1);
		assertThat(exportAuditItems.get(0).getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleRecord.getM_ShipmentSchedule_ID());
		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(ExportError.getCode());
	}

	@Test
	void exportShipmentCandidates()
	{
		exportShipmentCandidates_performTest();
	}

	private JsonResponseShipmentCandidates exportShipmentCandidates_performTest()
	{
		// given
		final I_M_ShipmentSchedule shipmentScheduleRecord = createShipmentScheduleRecord(null,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// now also create a location; otherwise there would be an error
		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ofInt(500));

		// then
		refresh(shipmentScheduleRecord);
		assertThat(shipmentScheduleRecord.getExportStatus()).isEqualTo(Exported.getCode());

		assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).hasSize(1);
		assertThat(result.getItems().get(0).getShipBPartner().getCompanyName()).isEqualTo("bpartnerOverride"); // expecting C_BPartner.Name because companyName is not set

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());
		assertThat(exportAudits.get(0).getExportStatus()).isEqualTo(Exported.getCode());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(1);
		assertThat(exportAuditItems.get(0).getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleRecord.getM_ShipmentSchedule_ID());
		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(Exported.getCode());

		return result;
	}

	@Test
	void updateStatus()
	{
		// given
		final JsonResponseShipmentCandidates jsonResponseShipmentCandidates = exportShipmentCandidates_performTest();
		final JsonMetasfreshId scheduleId = jsonResponseShipmentCandidates.getItems().get(0).getId();

		final JsonRequestCandidateResultsBuilder resultsBuilder = JsonRequestCandidateResults.builder();

		resultsBuilder.forwardedData("forwardedData")
				.transactionKey(jsonResponseShipmentCandidates.getTransactionKey())
				.item(JsonRequestCandidateResult.builder()
						.outcome(Outcome.OK)
						.scheduleId(scheduleId)
						.build());

		// when
		shipmentCandidateAPIService.updateStatus(resultsBuilder.build());

		// then
		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(jsonResponseShipmentCandidates.getTransactionKey());
		assertThat(exportAudits.get(0).getForwardedData()).isEqualTo("forwardedData");
		assertThat(exportAudits.get(0).getExportStatus()).isEqualTo(ExportedAndForwarded.getCode()); // since we gave a positive result, it's now also forwarded

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(1);
		assertThat(exportAuditItems.get(0).getM_ShipmentSchedule_ID()).isEqualTo(scheduleId.getValue());
		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(ExportedAndForwarded.getCode());
	}

	@Test
	void exportShipmentCandidates_2Schedules_2Orders_NoLimit()
	{
		// given
		final I_C_Currency eur = createCurrency("EUR", 2);
		final I_M_PriceList pricelist = createPricelist(eur);

		final I_C_Order order1 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine1 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1 = createShipmentScheduleRecord(orderLine1,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		final I_C_Order order2 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine2 = createOrderLine(order2);

		final I_M_ShipmentSchedule shipmentScheduleRecord2 = createShipmentScheduleRecord(orderLine2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ofInt(500));

		// then
		refresh(shipmentScheduleRecord1);
		assertThat(shipmentScheduleRecord1.getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord2);
		assertThat(shipmentScheduleRecord2.getExportStatus()).isEqualTo(Exported.getCode());

		assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).hasSize(2);
		assertThat(result.getItems().get(0).getShipBPartner().getCompanyName()).isEqualTo("bpartnerOverride"); // expecting C_BPartner.Name because companyName is not set

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudit = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudit).hasSize(1);
		assertThat(exportAudit.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(2);

		assertThat(exportAuditItems.get(0).getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleRecord1.getM_ShipmentSchedule_ID());
		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(Exported.getCode());

		assertThat(exportAuditItems.get(1).getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleRecord2.getM_ShipmentSchedule_ID());
		assertThat(exportAuditItems.get(1).getExportStatus()).isEqualTo(Exported.getCode());
	}

	@Test
	void exportShipmentCandidates_2Schedules_2Orders_Limit1()
	{
		// given
		final I_C_Currency eur = createCurrency("EUR", 2);
		final I_M_PriceList pricelist = createPricelist(eur);

		final I_C_Order order1 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine1 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1 = createShipmentScheduleRecord(orderLine1,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		final I_C_Order order2 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine2 = createOrderLine(order2);

		final I_M_ShipmentSchedule shipmentScheduleRecord2 = createShipmentScheduleRecord(orderLine2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ONE);

		// then
		refresh(shipmentScheduleRecord1);
		assertThat(shipmentScheduleRecord1.getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord2);
		assertThat(shipmentScheduleRecord2.getExportStatus()).isEqualTo(Pending.getCode());

		// assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).hasSize(1);
		assertThat(result.getItems().get(0).getShipBPartner().getCompanyName()).isEqualTo("bpartnerOverride"); // expecting C_BPartner.Name because companyName is not set

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());
		assertThat(exportAudits.get(0).getExportStatus()).isEqualTo(Exported.getCode());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(1);
		assertThat(exportAuditItems.get(0).getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleRecord1.getM_ShipmentSchedule_ID());
	}

	@Test
	void exportShipmentCandidates_2Schedules_2Orders_Limit1_InTheFuture()
	{
		// given
		final I_C_Currency eur = createCurrency("EUR", 2);
		final I_M_PriceList pricelist = createPricelist(eur);

		final I_C_Order order1 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine1 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1 = createShipmentScheduleRecord(orderLine1,
				TimeUtil.asTimestamp(SystemTime.asInstant().plusMillis(1000)));

		final I_C_Order order2 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine2 = createOrderLine(order2);

		final I_M_ShipmentSchedule shipmentScheduleRecord2 = createShipmentScheduleRecord(orderLine2,
				TimeUtil.asTimestamp(SystemTime.asInstant().plusMillis(1000)));

		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ONE);

		// then
		refresh(shipmentScheduleRecord1);
		assertThat(shipmentScheduleRecord1.getExportStatus()).isEqualTo(Pending.getCode());

		refresh(shipmentScheduleRecord2);
		assertThat(shipmentScheduleRecord2.getExportStatus()).isEqualTo(Pending.getCode());

		assertThat(result.getItems()).isEmpty();

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).isEmpty();

	}

	@Test
	void exportShipmentCandidates_O1_S5_Past_O2_S1_Past_Limit1()
	{
		// given
		final I_C_Currency eur = createCurrency("EUR", 2);
		final I_M_PriceList pricelist = createPricelist(eur);

		final I_C_Order order1 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);

		// Schedule 1
		final I_C_OrderLine orderLine1_1 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_1 = createShipmentScheduleRecord(orderLine1_1,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 2
		final I_C_OrderLine orderLine1_2 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_2 = createShipmentScheduleRecord(orderLine1_2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 3
		final I_C_OrderLine orderLine1_3 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_3 = createShipmentScheduleRecord(orderLine1_3,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 4
		final I_C_OrderLine orderLine1_4 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_4 = createShipmentScheduleRecord(orderLine1_4,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 5
		final I_C_OrderLine orderLine1_5 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_5 = createShipmentScheduleRecord(orderLine1_5,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		final I_C_Order order2 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine2 = createOrderLine(order2);

		final I_M_ShipmentSchedule shipmentScheduleRecord2 = createShipmentScheduleRecord(orderLine2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ONE);

		// assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).hasSize(5);
		assertThat(result.getItems().get(0).getShipBPartner().getCompanyName()).isEqualTo("bpartnerOverride"); // expecting C_BPartner.Name because companyName is not set

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(5);

		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(1).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(2).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(3).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(4).getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord1_1);
		assertThat(shipmentScheduleRecord1_1.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_2);
		assertThat(shipmentScheduleRecord1_2.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_3);
		assertThat(shipmentScheduleRecord1_3.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_3);
		assertThat(shipmentScheduleRecord1_3.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_4);
		assertThat(shipmentScheduleRecord1_4.getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord2);
		assertThat(shipmentScheduleRecord2.getExportStatus()).isEqualTo(Pending.getCode());

		final ImmutableList<Integer> expectedScheduleIds = ImmutableList.of(shipmentScheduleRecord1_1.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_2.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_3.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_4.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_5.getM_ShipmentSchedule_ID());

		final ImmutableList<Integer> exportedShipmentScheduleIDs = exportAuditItems.stream()
				.map(exportAudit -> exportAudit.getM_ShipmentSchedule_ID())
				.collect(ImmutableList.toImmutableList());

		assertThat(exportedShipmentScheduleIDs).containsAll(expectedScheduleIds);

	}

	@Test
	void exportShipmentCandidates_O1_S5_Past_O2_S1_Past_Limit2()
	{
		// given
		final I_C_Currency eur = createCurrency("EUR", 2);
		final I_M_PriceList pricelist = createPricelist(eur);

		final I_C_Order order1 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);

		// Schedule 1
		final I_C_OrderLine orderLine1_1 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_1 = createShipmentScheduleRecord(orderLine1_1,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 2
		final I_C_OrderLine orderLine1_2 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_2 = createShipmentScheduleRecord(orderLine1_2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 3
		final I_C_OrderLine orderLine1_3 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_3 = createShipmentScheduleRecord(orderLine1_3,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 4
		final I_C_OrderLine orderLine1_4 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_4 = createShipmentScheduleRecord(orderLine1_4,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 5
		final I_C_OrderLine orderLine1_5 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_5 = createShipmentScheduleRecord(orderLine1_5,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		final I_C_Order order2 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine2 = createOrderLine(order2);

		final I_M_ShipmentSchedule shipmentScheduleRecord2 = createShipmentScheduleRecord(orderLine2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.TWO);

		// assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).hasSize(6);
		assertThat(result.getItems().get(0).getShipBPartner().getCompanyName()).isEqualTo("bpartnerOverride"); // expecting C_BPartner.Name because companyName is not set

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(6);

		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(1).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(2).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(3).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(4).getExportStatus()).isEqualTo(Exported.getCode());
		assertThat(exportAuditItems.get(5).getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord1_1);
		assertThat(shipmentScheduleRecord1_1.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_2);
		assertThat(shipmentScheduleRecord1_2.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_3);
		assertThat(shipmentScheduleRecord1_3.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_3);
		assertThat(shipmentScheduleRecord1_3.getExportStatus()).isEqualTo(Exported.getCode());
		refresh(shipmentScheduleRecord1_4);
		assertThat(shipmentScheduleRecord1_4.getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord2);
		assertThat(shipmentScheduleRecord2.getExportStatus()).isEqualTo(Exported.getCode());

		final ImmutableList<Integer> expectedScheduleIds = ImmutableList.of(shipmentScheduleRecord1_1.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_2.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_3.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_4.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord1_5.getM_ShipmentSchedule_ID(),
				shipmentScheduleRecord2.getM_ShipmentSchedule_ID());

		final ImmutableList<Integer> exportedShipmentScheduleIDs = exportAuditItems.stream()
				.map(exportAudit -> exportAudit.getM_ShipmentSchedule_ID())
				.collect(ImmutableList.toImmutableList());

		assertThat(exportedShipmentScheduleIDs).containsAll(expectedScheduleIds);

	}

	@Test
	void exportShipmentCandidates_O1_S4_Past_S1_Future_O2_S1_Past_Limit2()
	{
		// given
		final I_C_Currency eur = createCurrency("EUR", 2);
		final I_M_PriceList pricelist = createPricelist(eur);

		final I_C_Order order1 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);

		// Schedule 1
		final I_C_OrderLine orderLine1_1 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_1 = createShipmentScheduleRecord(orderLine1_1,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 2
		final I_C_OrderLine orderLine1_2 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_2 = createShipmentScheduleRecord(orderLine1_2,
				TimeUtil.asTimestamp(SystemTime.asInstant().plusMillis(1000)));

		// Schedule 3
		final I_C_OrderLine orderLine1_3 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_3 = createShipmentScheduleRecord(orderLine1_3,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 4
		final I_C_OrderLine orderLine1_4 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_4 = createShipmentScheduleRecord(orderLine1_4,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		// Schedule 5
		final I_C_OrderLine orderLine1_5 = createOrderLine(order1);

		final I_M_ShipmentSchedule shipmentScheduleRecord1_5 = createShipmentScheduleRecord(orderLine1_5,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		final I_C_Order order2 = createOrder(X_C_Order.DOCSTATUS_Completed, pricelist);
		final I_C_OrderLine orderLine2 = createOrderLine(order2);

		final I_M_ShipmentSchedule shipmentScheduleRecord2 = createShipmentScheduleRecord(orderLine2,
				TimeUtil.asTimestamp(SystemTime.asInstant().minusMillis(1000)));

		location.setAddress1("Teststrasse 2a");
		location.setPostal("postal");
		location.setCity("city");
		saveRecord(location);

		// when
		final JsonResponseShipmentCandidates result = shipmentCandidateAPIService.exportShipmentCandidates(QueryLimit.ONE);

		// assertThat(result.isHasMoreItems()).isFalse();
		assertThat(result.getItems()).hasSize(1);
		assertThat(result.getItems().get(0).getShipBPartner().getCompanyName()).isEqualTo("bpartnerOverride"); // expecting C_BPartner.Name because companyName is not set

		final List<I_M_ShipmentSchedule_ExportAudit> exportAudits = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit.class);
		assertThat(exportAudits).hasSize(1);
		assertThat(exportAudits.get(0).getTransactionIdAPI()).isEqualTo(result.getTransactionKey());
		assertThat(exportAudits.get(0).getExportStatus()).isEqualTo(Exported.getCode());

		final List<I_M_ShipmentSchedule_ExportAudit_Item> exportAuditItems = POJOLookupMap.get().getRecords(I_M_ShipmentSchedule_ExportAudit_Item.class);
		assertThat(exportAuditItems).hasSize(1);
		assertThat(exportAuditItems.get(0).getExportStatus()).isEqualTo(Exported.getCode());

		refresh(shipmentScheduleRecord1_1);
		assertThat(shipmentScheduleRecord1_1.getExportStatus()).isEqualTo(Pending.getCode());
		refresh(shipmentScheduleRecord1_2);
		assertThat(shipmentScheduleRecord1_2.getExportStatus()).isEqualTo(Pending.getCode());
		refresh(shipmentScheduleRecord1_3);
		assertThat(shipmentScheduleRecord1_3.getExportStatus()).isEqualTo(Pending.getCode());
		refresh(shipmentScheduleRecord1_3);
		assertThat(shipmentScheduleRecord1_3.getExportStatus()).isEqualTo(Pending.getCode());
		refresh(shipmentScheduleRecord1_4);
		assertThat(shipmentScheduleRecord1_4.getExportStatus()).isEqualTo(Pending.getCode());
		refresh(shipmentScheduleRecord1_5);
		assertThat(shipmentScheduleRecord1_5.getExportStatus()).isEqualTo(Pending.getCode());

		refresh(shipmentScheduleRecord2);
		assertThat(shipmentScheduleRecord2.getExportStatus()).isEqualTo(Exported.getCode());

		assertThat(exportAuditItems.get(0).getM_ShipmentSchedule_ID()).isEqualTo(shipmentScheduleRecord2.getM_ShipmentSchedule_ID());

	}

	private I_M_ShipmentSchedule createShipmentScheduleRecord(final I_C_OrderLine orderLineRecord,
			final Timestamp canBeExportedFrom)
	{
		final I_M_ShipmentSchedule record = newInstance(I_M_ShipmentSchedule.class);

		record.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		record.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		record.setC_BPartner_Override_ID(bpartnerOverride.getC_BPartner_ID());
		record.setC_BP_Location_Override_ID(bPartnerLocationOverride.getC_BPartner_Location_ID());
		record.setM_Product_ID(product.getM_Product_ID());
		record.setCanBeExportedFrom(canBeExportedFrom);
		record.setExportStatus(APIExportStatus.Pending.getCode());
		record.setQtyOrdered(new BigDecimal("10"));
		record.setQtyDelivered(new BigDecimal("9"));
		record.setQtyToDeliver(new BigDecimal("8"));
		if (orderLineRecord != null)
		{
			record.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());
			record.setC_Order_ID(orderLineRecord.getC_Order_ID());
		}
		saveRecord(record);

		return record;
	}

	private I_C_Currency createCurrency(final String code, final int precision)
	{
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code(code);
		currency.setStdPrecision(precision);
		saveRecord(currency);

		return currency;
	}

	private I_M_PriceList createPricelist(final I_C_Currency currency)
	{
		final I_M_PriceList record = newInstance(I_M_PriceList.class);
		record.setC_Currency_ID(currency.getC_Currency_ID());
		record.setPricePrecision(2);
		saveRecord(record);

		return record;
	}

	private I_C_Order createOrder(final String docStatus, final I_M_PriceList pricelist)
	{
		final I_C_Order record = newInstance(I_C_Order.class);
		record.setDocStatus(docStatus);
		record.setM_PriceList_ID(pricelist.getM_PriceList_ID());
		saveRecord(record);

		return record;
	}

	private I_C_OrderLine createOrderLine(final I_C_Order order)
	{
		final I_C_OrderLine record = newInstance(I_C_OrderLine.class);
		record.setC_Order_ID(order.getC_Order_ID());

		saveRecord(record);

		return record;
	}

}