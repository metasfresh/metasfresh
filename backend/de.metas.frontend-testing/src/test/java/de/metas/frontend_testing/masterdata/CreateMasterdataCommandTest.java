package de.metas.frontend_testing.masterdata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.dd_order.JsonDDOrderRequest;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHURequest;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsRequest;
import de.metas.frontend_testing.masterdata.huQRCodes.JsonGenerateHUQRCodeRequest;
import de.metas.frontend_testing.masterdata.inventory.JsonInventoryRequest;
import de.metas.frontend_testing.masterdata.mobile_configuration.JsonMobileConfigRequest;
import de.metas.frontend_testing.masterdata.picking_slot.JsonPickingSlotCreateRequest;
import de.metas.frontend_testing.masterdata.pp_order.JsonPPOrderRequest;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductRequest;
import de.metas.frontend_testing.masterdata.product_planning.JsonCreateProductPlanningRequest;
import de.metas.frontend_testing.masterdata.resource.JsonCreateResourceRequest;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateRequest;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseRequest;
import de.metas.frontend_testing.masterdata.workplace.JsonWorkplaceRequest;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2025 metas GmbH
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

public class CreateMasterdataCommandTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void request_builder_withEmptyRequest_shouldBuildSuccessfully()
	{
		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder().build();

		// then
		assertThat(request).isNotNull();
		assertThat(request.getLogin()).isNull();
		assertThat(request.getBpartners()).isNull();
		assertThat(request.getProducts()).isNull();
		assertThat(request.getResources()).isNull();
		assertThat(request.getWarehouses()).isNull();
		assertThat(request.getPickingSlots()).isNull();
		assertThat(request.getWorkplaces()).isNull();
		assertThat(request.getProductPlannings()).isNull();
		assertThat(request.getPackingInstructions()).isNull();
		assertThat(request.getMobileConfig()).isNull();
		assertThat(request.getHandlingUnits()).isNull();
		assertThat(request.getGeneratedHUQRCodes()).isNull();
		assertThat(request.getSalesOrders()).isNull();
		assertThat(request.getDistributionOrders()).isNull();
		assertThat(request.getManufacturingOrders()).isNull();
		assertThat(request.getInventories()).isNull();
		assertThat(request.getCustomQRCodeFormats()).isNull();
	}

	@Test
	public void request_builder_withAllFields_shouldBuildSuccessfully()
	{
		// given
		final Map<String, Object> context = new HashMap<>();
		context.put("key1", "value1");

		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.context(context)
				.login(ImmutableMap.of("user1", JsonLoginUserRequest.builder().build()))
				.bpartners(ImmutableMap.of("bp1", JsonCreateBPartnerRequest.builder().build()))
				.products(ImmutableMap.of("product1", JsonCreateProductRequest.builder().build()))
				.resources(ImmutableMap.of("resource1", JsonCreateResourceRequest.builder().type("type").build()))
				.warehouses(ImmutableMap.of("warehouse1", JsonWarehouseRequest.builder().build()))
				.pickingSlots(ImmutableMap.of("slot1", JsonPickingSlotCreateRequest.builder().build()))
				.workplaces(ImmutableMap.of("workplace1", JsonWorkplaceRequest.builder().build()))
				.productPlannings(ImmutableMap.of("planning1", JsonCreateProductPlanningRequest.builder().product(Identifier.ofString("product")).build()))
				.packingInstructions(ImmutableMap.of("pi1", JsonPackingInstructionsRequest.builder().build()))
				.mobileConfig(JsonMobileConfigRequest.builder().build())
				.handlingUnits(ImmutableMap.of("hu1", JsonCreateHURequest.builder().build()))
				.generatedHUQRCodes(ImmutableMap.of("qr1", JsonGenerateHUQRCodeRequest.builder()
						.product(Identifier.ofString("product"))
						.packingInstructions(Identifier.ofString("pi1")).build()))
				.salesOrders(ImmutableMap.of("so1", JsonSalesOrderCreateRequest.builder()
						.warehouse(Identifier.ofString("warehouse1"))
						.datePromised(ZonedDateTime.now())
						.lines(ImmutableList.of())
						.build()))
				.distributionOrders(ImmutableMap.of("dd1", JsonDDOrderRequest.builder()
						.warehouseFrom(Identifier.ofString("warehouseFrom"))
						.warehouseTo(Identifier.ofString("warehouseTo"))
						.warehouseInTransit(Identifier.ofString("warehouseInTransit"))
						.lines(ImmutableList.of())
						.build()))
				.manufacturingOrders(ImmutableMap.of("pp1", JsonPPOrderRequest.builder()
						.warehouse(Identifier.ofString("warehouse"))
						.qty(BigDecimal.ONE)
						.product(Identifier.ofString("product"))
						.datePromised(ZonedDateTime.now())
						.build()))
				.inventories(ImmutableMap.of("inv1", JsonInventoryRequest.builder()
						.warehouse(Identifier.ofString("warehouse"))
						.products(ImmutableSet.of(Identifier.ofString("product")))
						.build()))
				.build();

		// then
		assertThat(request).isNotNull();
		assertThat(request.getContext()).isEqualTo(context);
		assertThat(request.getLogin()).hasSize(1);
		assertThat(request.getBpartners()).hasSize(1);
		assertThat(request.getProducts()).hasSize(1);
		assertThat(request.getResources()).hasSize(1);
		assertThat(request.getWarehouses()).hasSize(1);
		assertThat(request.getPickingSlots()).hasSize(1);
		assertThat(request.getWorkplaces()).hasSize(1);
		assertThat(request.getProductPlannings()).hasSize(1);
		assertThat(request.getPackingInstructions()).hasSize(1);
		assertThat(request.getMobileConfig()).isNotNull();
		assertThat(request.getHandlingUnits()).hasSize(1);
		assertThat(request.getGeneratedHUQRCodes()).hasSize(1);
		assertThat(request.getSalesOrders()).hasSize(1);
		assertThat(request.getDistributionOrders()).hasSize(1);
		assertThat(request.getManufacturingOrders()).hasSize(1);
		assertThat(request.getInventories()).hasSize(1);
	}

	@Test
	public void request_builder_withContext_shouldPreserveContext()
	{
		// given
		final Map<String, Object> contextMap = new HashMap<>();
		contextMap.put("key1", "value1");
		contextMap.put("key2", 123);
		contextMap.put("key3", true);

		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.context(contextMap)
				.build();

		// then
		assertThat(request.getContext()).isNotNull();
		assertThat(request.getContext()).containsKeys("key1", "key2", "key3");
		assertThat(request.getContext().get("key1")).isEqualTo("value1");
		assertThat(request.getContext().get("key2")).isEqualTo(123);
		assertThat(request.getContext().get("key3")).isEqualTo(true);
	}

	@Test
	public void request_builder_withMultipleLoginUsers_shouldBuildSuccessfully()
	{
		// given
		final Map<String, JsonLoginUserRequest> loginRequests = new HashMap<>();
		loginRequests.put("user1", JsonLoginUserRequest.builder().build());
		loginRequests.put("user2", JsonLoginUserRequest.builder().build());
		loginRequests.put("user3", JsonLoginUserRequest.builder().build());

		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.login(loginRequests)
				.build();

		// then
		assertThat(request.getLogin()).hasSize(3);
		assertThat(request.getLogin()).containsKeys("user1", "user2", "user3");
	}

	@Test
	public void request_builder_withMultipleBPartners_shouldBuildSuccessfully()
	{
		// given
		final Map<String, JsonCreateBPartnerRequest> bpartnerRequests = new HashMap<>();
		bpartnerRequests.put("bp1", JsonCreateBPartnerRequest.builder().build());
		bpartnerRequests.put("bp2", JsonCreateBPartnerRequest.builder().build());

		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.bpartners(bpartnerRequests)
				.build();

		// then
		assertThat(request.getBpartners()).hasSize(2);
		assertThat(request.getBpartners()).containsKeys("bp1", "bp2");
	}

	@Test
	public void request_builder_withMultipleProducts_shouldBuildSuccessfully()
	{
		// given
		final Map<String, JsonCreateProductRequest> productRequests = new HashMap<>();
		productRequests.put("product1", JsonCreateProductRequest.builder().build());
		productRequests.put("product2", JsonCreateProductRequest.builder().build());
		productRequests.put("product3", JsonCreateProductRequest.builder().build());

		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.products(productRequests)
				.build();

		// then
		assertThat(request.getProducts()).hasSize(3);
		assertThat(request.getProducts()).containsKeys("product1", "product2", "product3");
	}

	@Test
	public void request_builder_withMobileConfig_shouldBuildSuccessfully()
	{
		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.mobileConfig(JsonMobileConfigRequest.builder().build())
				.build();

		// then
		assertThat(request.getMobileConfig()).isNotNull();
	}

	@Test
	public void request_builder_withNullFields_shouldBuildSuccessfully()
	{
		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.login(null)
				.bpartners(null)
				.products(null)
				.resources(null)
				.warehouses(null)
				.pickingSlots(null)
				.workplaces(null)
				.productPlannings(null)
				.packingInstructions(null)
				.mobileConfig(null)
				.handlingUnits(null)
				.generatedHUQRCodes(null)
				.salesOrders(null)
				.distributionOrders(null)
				.manufacturingOrders(null)
				.inventories(null)
				.customQRCodeFormats(null)
				.build();

		// then
		assertThat(request).isNotNull();
		assertThat(request.getLogin()).isNull();
		assertThat(request.getBpartners()).isNull();
		assertThat(request.getProducts()).isNull();
	}

	@Test
	public void request_builder_withEmptyMaps_shouldBuildSuccessfully()
	{
		// when
		final JsonCreateMasterdataRequest request = JsonCreateMasterdataRequest.builder()
				.login(ImmutableMap.of())
				.bpartners(ImmutableMap.of())
				.products(ImmutableMap.of())
				.resources(ImmutableMap.of())
				.warehouses(ImmutableMap.of())
				.pickingSlots(ImmutableMap.of())
				.workplaces(ImmutableMap.of())
				.productPlannings(ImmutableMap.of())
				.packingInstructions(ImmutableMap.of())
				.handlingUnits(ImmutableMap.of())
				.generatedHUQRCodes(ImmutableMap.of())
				.salesOrders(ImmutableMap.of())
				.distributionOrders(ImmutableMap.of())
				.manufacturingOrders(ImmutableMap.of())
				.inventories(ImmutableMap.of())
				.customQRCodeFormats(ImmutableList.of())
				.build();

		// then
		assertThat(request).isNotNull();
		assertThat(request.getLogin()).isEmpty();
		assertThat(request.getBpartners()).isEmpty();
		assertThat(request.getProducts()).isEmpty();
		assertThat(request.getResources()).isEmpty();
		assertThat(request.getWarehouses()).isEmpty();
		assertThat(request.getPickingSlots()).isEmpty();
		assertThat(request.getWorkplaces()).isEmpty();
		assertThat(request.getProductPlannings()).isEmpty();
		assertThat(request.getPackingInstructions()).isEmpty();
		assertThat(request.getHandlingUnits()).isEmpty();
		assertThat(request.getGeneratedHUQRCodes()).isEmpty();
		assertThat(request.getSalesOrders()).isEmpty();
		assertThat(request.getDistributionOrders()).isEmpty();
		assertThat(request.getManufacturingOrders()).isEmpty();
		assertThat(request.getInventories()).isEmpty();
		assertThat(request.getCustomQRCodeFormats()).isEmpty();
	}

	@Test
	public void response_builder_withAllFields_shouldBuildSuccessfully()
	{
		// given
		final Map<String, Object> context = new HashMap<>();
		context.put("responseKey", "responseValue");

		// when
		final JsonCreateMasterdataResponse response = JsonCreateMasterdataResponse.builder()
				.context(context)
				.login(ImmutableMap.of())
				.bpartners(ImmutableMap.of())
				.products(ImmutableMap.of())
				.resources(ImmutableMap.of())
				.warehouses(ImmutableMap.of())
				.pickingSlots(ImmutableMap.of())
				.workplaces(ImmutableMap.of())
				.productPlannings(ImmutableMap.of())
				.packingInstructions(ImmutableMap.of())
				.handlingUnits(ImmutableMap.of())
				.generatedHUQRCodes(ImmutableMap.of())
				.salesOrders(ImmutableMap.of())
				.purchaseOrders(ImmutableMap.of())
				.distributionOrders(ImmutableMap.of())
				.manufacturingOrders(ImmutableMap.of())
				.inventories(ImmutableMap.of())
				.build();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getContext()).isEqualTo(context);
		assertThat(response.getLogin()).isEmpty();
		assertThat(response.getBpartners()).isEmpty();
		assertThat(response.getProducts()).isEmpty();
	}
}
