package de.metas.manufacturing.rest_api;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_S_Resource;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.PPOrderPlanningStatus;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.X_PP_Cost_Collector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.JsonObjectMapperHolder;
import de.metas.business.BusinessTestHelper;
import de.metas.common.manufacturing.JsonRequestHULookup;
import de.metas.common.manufacturing.JsonRequestIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestSetOrderExportStatus;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.JsonResponseIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonResponseReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.Outcome;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.contracts.flatrate.interfaces.I_C_DocType;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUTestHelper.TestHelperLoadRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_BOMLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.pporder.api.IHUPPCostCollectorBL;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.manufacturing.order.exportaudit.ExportTransactionId;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.ResourceId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.manufacturing.rest-api
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

@ExtendWith(AdempiereTestWatcher.class)
public class ManufacturingOrderAPIServiceTest
{
	// Services under test:
	private ManufacturingOrderAuditRepository auditRepo;
	private ManufacturingOrderAPIService apiService;

	// Supporting services:
	private IProductBL productBL;
	private IWarehouseBL warehouseBL;
	private IPPCostCollectorDAO costCollectorDAO;
	private IHUPPCostCollectorBL costCollectorBL;
	private IHandlingUnitsBL handlingUnitsBL;
	private HUReservationService huReservationService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		productBL = Services.get(IProductBL.class);
		warehouseBL = Services.get(IWarehouseBL.class);
		costCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		costCollectorBL = Services.get(IHUPPCostCollectorBL.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		huReservationService = new HUReservationService(new HUReservationRepository());

		auditRepo = new ManufacturingOrderAuditRepository();
		apiService = ManufacturingOrderAPIService.builder()
				.orderAuditRepo(auditRepo)
				.productRepo(new ProductRepository())
				.jsonObjectMapper(JsonObjectMapperHolder.newJsonObjectMapper())
				.huReservationService(huReservationService)
				.build();
	}

	@Builder(builderMethodName = "manufacturingOrder", builderClassName = "ManufacturingOrderBuilder")
	private PPOrderId createManufacturingOrder(
			@Nullable final APIExportStatus exportStatus,
			@Nullable final Instant canBeExportedFrom,
			@Nullable ProductId productId,
			@Nullable String qtyOrdered,
			@Nullable LocatorId locatorId,
			@Nullable ResourceId plantId,
			@Nullable Instant dateOrdered,
			@Nullable OrderLineId salesOrderLineId,
			//
			@Nullable ProductId bomLine_componentId,
			@Nullable String bomLine_qtyRequired)
	{
		final I_PP_Order order = newInstance(I_PP_Order.class);
		{
			order.setIsActive(true);
			order.setProcessed(true);
			order.setDocStatus(DocStatus.Completed.getCode());
			order.setPlanningStatus(PPOrderPlanningStatus.PLANNING.getCode());

			if (exportStatus != null)
			{
				order.setExportStatus(exportStatus.getCode());
			}
			if (canBeExportedFrom != null)
			{
				order.setCanBeExportedFrom(TimeUtil.asTimestamp(canBeExportedFrom));
			}

			if (productId != null)
			{
				order.setM_Product_ID(productId.getRepoId());
			}

			if (qtyOrdered != null)
			{
				order.setQtyEntered(new BigDecimal(qtyOrdered));
				order.setQtyOrdered(new BigDecimal(qtyOrdered));

				if (productId != null)
				{
					final UomId stockUOMId = productBL.getStockUOMId(productId);
					order.setC_UOM_ID(stockUOMId.getRepoId());
				}
			}

			if (locatorId != null)
			{
				order.setM_Warehouse_ID(locatorId.getWarehouseId().getRepoId());
				order.setM_Locator_ID(locatorId.getRepoId());
			}

			if (plantId != null)
			{
				order.setS_Resource_ID(plantId.getRepoId());
			}

			if (dateOrdered != null)
			{
				order.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
				order.setDateStartSchedule(TimeUtil.asTimestamp(dateOrdered));
			}

			if (salesOrderLineId != null)
			{
				order.setC_OrderLine_ID(salesOrderLineId.getRepoId());
			}

			saveRecord(order);

			order.setDocumentNo(String.valueOf(order.getPP_Order_ID()));
			saveRecord(order);
		}

		// Order BOM
		{
			final I_PP_Order_BOM orderBOM = newInstance(I_PP_Order_BOM.class);
			orderBOM.setPP_Order_ID(order.getPP_Order_ID());
			orderBOM.setM_Product_ID(order.getM_Product_ID());
			saveRecord(orderBOM);
		}

		// Order BOM Line
		if (bomLine_componentId != null)
		{
			final UomId uomId = productBL.getStockUOMId(bomLine_componentId);

			final I_PP_Order_BOMLine orderBOMLine = newInstance(I_PP_Order_BOMLine.class);
			orderBOMLine.setPP_Order_ID(order.getPP_Order_ID());
			orderBOMLine.setComponentType(BOMComponentType.Component.getCode());
			orderBOMLine.setM_Product_ID(bomLine_componentId.getRepoId());
			orderBOMLine.setQtyRequiered(new BigDecimal(bomLine_qtyRequired));
			orderBOMLine.setC_UOM_ID(uomId.getRepoId());
			orderBOMLine.setM_Locator_ID(locatorId.getRepoId());
			saveRecord(orderBOMLine);
		}

		return PPOrderId.ofRepoId(order.getPP_Order_ID());
	}

	private I_PP_Order getOrderRecord(final PPOrderId orderId)
	{
		return load(orderId, I_PP_Order.class);
	}

	//
	//
	//
	// -----------------------
	//
	//
	//

	@Nested
	public class exportOrders
	{
		private I_C_UOM uomEach;
		private ProductId productId;

		private PPOrderId orderId1;
		private PPOrderId orderId2;
		private PPOrderId orderId3;

		@BeforeEach
		public void beforeEach()
		{
			uomEach = BusinessTestHelper.createUomEach();
			productId = BusinessTestHelper.createProductId("product", uomEach);

			final ManufacturingOrderBuilder orderBuilder = manufacturingOrder()
					.exportStatus(APIExportStatus.Pending)
					.productId(productId)
					.dateOrdered(SystemTime.asInstant()) // does not matter
			;
			orderId1 = orderBuilder.canBeExportedFrom(Instant.parse("2020-09-07T00:00:00.00Z")).qtyOrdered("10").build();
			orderId2 = orderBuilder.canBeExportedFrom(Instant.parse("2020-09-08T00:00:00.00Z")).qtyOrdered("11").build();
			orderId3 = orderBuilder.canBeExportedFrom(Instant.parse("2020-09-09T00:00:00.00Z")).qtyOrdered("12").build();
		}

		private JsonMetasfreshId toJsonMetasfreshId(final PPOrderId orderId)
		{
			return JsonMetasfreshId.of(orderId.getRepoId());
		}

		@Nested
		public class exportWithLimit
		{
			@Test
			public void export_1_of_3()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-10T00:00:00.00Z"),
						QueryLimit.ofInt(1),
						"en_US");

				assertThat(result.getTransactionKey()).isNotNull();
				assertThat(result.getItems()).hasSize(1);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.isHasMoreItems()).isTrue();
			}

			@Test
			public void export_2_of_3()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-10T00:00:00.00Z"),
						QueryLimit.ofInt(2),
						"en_US");

				assertThat(result.getTransactionKey()).isNotNull();
				assertThat(result.getItems()).hasSize(2);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.isHasMoreItems()).isTrue();
			}

			@Test
			public void export_3_of_3()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-10T00:00:00.00Z"),
						QueryLimit.ofInt(3),
						"en_US");

				assertThat(result.getItems()).hasSize(3);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.getItems().get(2).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId3));
				assertThat(result.isHasMoreItems()).isTrue();

				final ExportTransactionId transactionKey = ExportTransactionId.ofString(result.getTransactionKey());
				final ManufacturingOrderExportAudit audit = auditRepo.getByTransactionId(transactionKey);
				assertAllOrdersWereExported(audit);
			}

			private void assertAllOrdersWereExported(final ManufacturingOrderExportAudit audit)
			{
				assertThat(audit.getItems()).hasSize(3);
				assertThat(audit.getByOrderId(orderId1).getExportStatus()).isEqualTo(APIExportStatus.Exported);
				assertThat(audit.getByOrderId(orderId2).getExportStatus()).isEqualTo(APIExportStatus.Exported);
				assertThat(audit.getByOrderId(orderId3).getExportStatus()).isEqualTo(APIExportStatus.Exported);
			}

			@Test
			public void export_4_of_3()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-10T00:00:00.00Z"),
						QueryLimit.ofInt(4),
						"en_US");

				assertThat(result.getTransactionKey()).isNotNull();
				assertThat(result.getItems()).hasSize(3);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.getItems().get(2).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId3));
				assertThat(result.isHasMoreItems()).isFalse();
			}

			@Test
			public void export_INFINIT_of_3()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-10T00:00:00.00Z"),
						QueryLimit.NO_LIMIT,
						"en_US");

				assertThat(result.getTransactionKey()).isNotNull();
				assertThat(result.getItems()).hasSize(3);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.getItems().get(2).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId3));
				assertThat(result.isHasMoreItems()).isFalse();
			}
		}

		@Nested
		public class exportUntilDate
		{
			@Test
			public void exportOn_2020_09_06()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-06T00:00:00.00Z"),
						QueryLimit.NO_LIMIT,
						"en_US");
				assertThat(result.getItems()).hasSize(0);
			}

			@Test
			public void exportOn_2020_09_07()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-07T00:00:00.00Z"),
						QueryLimit.NO_LIMIT,
						"en_US");
				assertThat(result.getItems()).hasSize(1);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.isHasMoreItems()).isFalse();
			}

			@Test
			public void exportOn_2020_09_08()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-08T00:00:00.00Z"),
						QueryLimit.NO_LIMIT,
						"en_US");
				assertThat(result.getItems()).hasSize(2);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.isHasMoreItems()).isFalse();
			}

			@Test
			public void exportOn_2020_09_09()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-09T00:00:00.00Z"),
						QueryLimit.NO_LIMIT,
						"en_US");
				assertThat(result.getItems()).hasSize(3);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.getItems().get(2).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId3));
				assertThat(result.isHasMoreItems()).isFalse();
			}

			@Test
			public void exportOn_2020_09_10()
			{
				final JsonResponseManufacturingOrdersBulk result = apiService.exportOrders(
						Instant.parse("2020-09-10T00:00:00.00Z"),
						QueryLimit.NO_LIMIT,
						"en_US");
				assertThat(result.getItems()).hasSize(3);
				assertThat(result.getItems().get(0).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId1));
				assertThat(result.getItems().get(1).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId2));
				assertThat(result.getItems().get(2).getOrderId()).isEqualTo(toJsonMetasfreshId(orderId3));
				assertThat(result.isHasMoreItems()).isFalse();
			}

		}
	}

	@Nested
	public class setExportStatus
	{
		@Test
		public void ok()
		{
			final PPOrderId orderId = manufacturingOrder().build();

			apiService.setExportStatus(JsonRequestSetOrdersExportStatusBulk.builder()
					.transactionKey("trx1")
					.item(JsonRequestSetOrderExportStatus.builder()
							.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
							.outcome(Outcome.OK)
							.build())
					.build());

			final I_PP_Order orderRecord = getOrderRecord(orderId);
			assertThat(orderRecord.getExportStatus()).isEqualTo(APIExportStatus.ExportedAndForwarded.getCode());

			final ManufacturingOrderExportAudit audit = auditRepo.getByTransactionId(ExportTransactionId.ofString("trx1"));
			assertThat(audit.getItems()).hasSize(1);
			assertThat(audit.getItems().get(0).getExportStatus()).isEqualTo(APIExportStatus.ExportedAndForwarded);
			assertThat(audit.getItems().get(0).getIssueId()).isNull();
		}

		@Test
		public void error()
		{
			final PPOrderId orderId = manufacturingOrder().build();

			apiService.setExportStatus(JsonRequestSetOrdersExportStatusBulk.builder()
					.transactionKey("trx1")
					.item(JsonRequestSetOrderExportStatus.builder()
							.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
							.outcome(Outcome.ERROR)
							.error(JsonError.ofSingleItem(JsonErrorItem.builder()
									.message("some error")
									.build()))
							.build())
					.build());

			final I_PP_Order orderRecord = getOrderRecord(orderId);
			assertThat(orderRecord.getExportStatus()).isEqualTo(APIExportStatus.ExportedAndError.getCode());

			final ManufacturingOrderExportAudit audit = auditRepo.getByTransactionId(ExportTransactionId.ofString("trx1"));
			assertThat(audit.getItems()).hasSize(1);
			assertThat(audit.getItems().get(0).getExportStatus()).isEqualTo(APIExportStatus.ExportedAndError);
			assertThat(audit.getItems().get(0).getIssueId()).isNotNull();
		}
	}

	@Nested
	public class report
	{
		private HUTestHelper huTestHelper;
		private I_C_UOM uomEach;
		private LocatorId locatorId;
		private ResourceId plantId;

		@BeforeEach
		public void beforeEach()
		{
			huTestHelper = HUTestHelper.newInstanceOutOfTrx();
			uomEach = huTestHelper.uomEach;

			plantId = createPlant("plant");

			huTestHelper.createDimensionSpec_PP_Order_ProductAttribute_To_Transfer();

			createDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);

			final WarehouseId warehouseId = WarehouseId.ofRepoId(huTestHelper.defaultWarehouse.getM_Warehouse_ID());
			locatorId = warehouseBL.getDefaultLocatorId(warehouseId);
		}

		public ResourceId createPlant(final String name)
		{
			final I_S_Resource plant = newInstance(I_S_Resource.class);
			plant.setIsManufacturingResource(true);
			plant.setManufacturingResourceType(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);
			plant.setValue(name);
			plant.setName(name);
			saveRecord(plant);
			return ResourceId.ofRepoId(plant.getS_Resource_ID());
		}

		private void createDocType(final String docBaseType)
		{
			final I_C_DocType docType = newInstance(I_C_DocType.class);
			docType.setName(docBaseType);
			docType.setDocBaseType(docBaseType);
			saveRecord(docType);
		}

		@Builder(builderMethodName = "vhu", builderClassName = "VHUBuilder")
		private HuId createVHU(
				@NonNull final ProductId productId,
				@NonNull final String qty,
				@Nullable final String lotNumber,
				@Nullable final String bestBeforeDate)
		{
			final I_C_UOM uom = productBL.getStockUOM(productId);

			final IHUProducerAllocationDestination producer = HUProducerDestination.ofVirtualPI()
					.setHUStatus(X_M_HU.HUSTATUS_Active);
			huTestHelper.load(TestHelperLoadRequest.builder()
					.producer(producer)
					.cuProductId(productId)
					.loadCuQty(new BigDecimal(qty))
					.loadCuUOM(uom)
					.build());

			final I_M_HU vhu = producer.getSingleCreatedHU().get();

			if (lotNumber != null
					|| bestBeforeDate != null)
			{
				final IMutableHUContext huContext = huTestHelper.getHUContext();
				final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory()
						.getAttributeStorage(vhu);
				huAttributes.setSaveOnChange(true);

				huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumber);
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, LocalDate.parse(bestBeforeDate));
			}

			return HuId.ofRepoId(vhu.getM_HU_ID());
		}

		@Test
		public void receive()
		{
			final ProductId productId = BusinessTestHelper.createProductId("product", uomEach);
			final OrderLineId salesOrderLineId = OrderLineId.ofRepoId(111);
			final PPOrderId orderId = manufacturingOrder()
					.productId(productId)
					.qtyOrdered("100")
					.locatorId(locatorId)
					.plantId(plantId)
					.salesOrderLineId(salesOrderLineId)
					.build();

			final JsonResponseManufacturingOrdersReport result = apiService.report(
					JsonRequestManufacturingOrdersReport.builder()
							.receipt(JsonRequestReceiveFromManufacturingOrder.builder()
									.requestId("req1")
									.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
									.date(SystemTime.asZonedDateTime())
									.qtyToReceiveInStockUOM(new BigDecimal("10"))
									.lotNumber("lot123")
									.bestBeforeDate(LocalDate.parse("2027-06-07"))
									.build())
							.build());

			//
			// Check result
			assertThat(result.getIssues()).isEmpty();
			assertThat(result.getReceipts())
					.containsExactly(
							JsonResponseReceiveFromManufacturingOrder.builder().requestId("req1").outcome(Outcome.OK).build());

			//
			// Check cost collector
			final List<I_PP_Cost_Collector> costCollectors = costCollectorDAO.getByOrderId(orderId);
			assertThat(costCollectors).hasSize(1);
			final I_PP_Cost_Collector costCollector = costCollectors.get(0);
			assertThat(costCollector.getCostCollectorType()).isEqualTo(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt);
			assertThat(costCollector.getMovementQty()).isEqualTo("10");
			assertThat(costCollector.getDocStatus()).isEqualTo(DocStatus.Completed.getCode());

			//
			// Check HU
			final List<I_M_HU> hus = costCollectorBL.getTopLevelHUs(costCollector);
			assertThat(hus).hasSize(1);
			final I_M_HU hu = hus.get(0);
			assertThat(handlingUnitsBL.getHU_UnitType(hu)).isEqualTo(X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI);
			assertThat(hu.getHUStatus()).isEqualTo(X_M_HU.HUSTATUS_Active);
			assertThat(hu.getM_Locator_ID()).isEqualTo(locatorId.getRepoId());

			//
			// Check HU storage
			final IHUStorage huStorage = handlingUnitsBL.getStorageFactory().getStorage(hu);
			assertThat(huStorage.getQty(productId, uomEach)).isEqualByComparingTo("10");

			//
			// Check HU attributes
			final IAttributeStorage huAttributes = huTestHelper.getHUContext().getHUAttributeStorageFactory().getAttributeStorage(hu);
			assertThat(huAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber)).isEqualTo("lot123");
			assertThat(huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate)).isEqualTo("2027-06-07");

			//
			// Check HU reservation
			assertThat(huReservationService.isVhuIdReservedToSalesOrderLineId(HuId.ofRepoId(hu.getM_HU_ID()), salesOrderLineId))
					.isTrue();
		}

		@Test
		public void issue()
		{
			final ProductId productId = BusinessTestHelper.createProductId("product", uomEach);
			final ProductId componentId = BusinessTestHelper.createProductId("component1", uomEach);

			final PPOrderId orderId = manufacturingOrder()
					.productId(productId)
					.qtyOrdered("100")
					.locatorId(locatorId)
					.plantId(plantId)
					//
					.bomLine_componentId(componentId)
					.bomLine_qtyRequired("10")
					//
					.build();

			final HuId vhuId = vhu().productId(componentId)
					.qty("40")
					.lotNumber("lot1")
					.bestBeforeDate("2022-02-02")
					.build();

			final JsonResponseManufacturingOrdersReport result = apiService.report(
					JsonRequestManufacturingOrdersReport.builder()
							.issue(JsonRequestIssueToManufacturingOrder.builder()
									.requestId("req1")
									.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
									.date(SystemTime.asZonedDateTime())
									.qtyToIssueInStockUOM(new BigDecimal("2"))
									.productNo("product")
									.handlingUnit(JsonRequestHULookup.builder()
											.lotNumber("lot1")
											.bestBeforeDate(LocalDate.parse("2022-02-02"))
											.build())
									.build())
							.build());

			//
			// Check result
			assertThat(result.getReceipts()).isEmpty();
			assertThat(result.getIssues())
					.containsExactly(
							JsonResponseIssueToManufacturingOrder.builder().requestId("req1").outcome(Outcome.OK).build());

			//
			// Check HU
			{
				final I_M_HU vhu = handlingUnitsBL.getById(vhuId);
				final IHUStorage huStorage = handlingUnitsBL.getStorageFactory().getStorage(vhu);
				assertThat(huStorage.getQty(componentId, uomEach)).isEqualByComparingTo("38");
			}
		}

		@Test
		public void issueWithErrors()
		{
			final ProductId productId = BusinessTestHelper.createProductId("product", uomEach);
			final ProductId componentId = BusinessTestHelper.createProductId("component1", uomEach);

			final PPOrderId orderId = manufacturingOrder()
					.productId(productId)
					.qtyOrdered("100")
					.locatorId(locatorId)
					.plantId(plantId)
					//
					.bomLine_componentId(componentId)
					.bomLine_qtyRequired("10")
					//
					.build();

			final JsonResponseManufacturingOrdersReport result = apiService.report(
					JsonRequestManufacturingOrdersReport.builder()
							.issue(JsonRequestIssueToManufacturingOrder.builder()
									.requestId("req1")
									.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
									.date(SystemTime.asZonedDateTime())
									.qtyToIssueInStockUOM(new BigDecimal("2"))
									.productNo("product")
									.handlingUnit(JsonRequestHULookup.builder()
											.lotNumber("missing-lot")
											.bestBeforeDate(LocalDate.parse("2022-02-02"))
											.build())
									.build())
							.build());

			//
			// Check result
			assertThat(result.getReceipts()).isEmpty();
			assertThat(result.getIssues()).hasSize(1);

			final JsonResponseIssueToManufacturingOrder issueResponse = result.getIssues().get(0);
			assertThat(issueResponse.getRequestId()).isEqualTo("req1");
			assertThat(issueResponse.getOutcome()).isEqualTo(Outcome.ERROR);

			final JsonErrorItem jsonErrorItem = issueResponse.getError().getErrors().get(0);
			assertThat(jsonErrorItem.getMessage()).startsWith("No HU found ");
			assertThat(jsonErrorItem.getAdIssueId()).isNotNull();
		}
	}
}
