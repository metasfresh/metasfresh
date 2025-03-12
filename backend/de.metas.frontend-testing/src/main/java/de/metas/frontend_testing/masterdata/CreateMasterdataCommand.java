package de.metas.frontend_testing.masterdata;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyRepository;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.dd_order.DDOrderCommand;
import de.metas.frontend_testing.masterdata.dd_order.JsonDDOrderRequest;
import de.metas.frontend_testing.masterdata.dd_order.JsonDDOrderResponse;
import de.metas.frontend_testing.masterdata.hu.CreateHUCommand;
import de.metas.frontend_testing.masterdata.hu.CreatePackingInstructionsCommand;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHURequest;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHUResponse;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsRequest;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsResponse;
import de.metas.frontend_testing.masterdata.mobile_configuration.JsonMobileConfigResponse;
import de.metas.frontend_testing.masterdata.mobile_configuration.MobileConfigCommand;
import de.metas.frontend_testing.masterdata.picking_slot.JsonPickingSlotCreateRequest;
import de.metas.frontend_testing.masterdata.picking_slot.JsonPickingSlotCreateResponse;
import de.metas.frontend_testing.masterdata.picking_slot.PickingSlotCreateCommand;
import de.metas.frontend_testing.masterdata.pp_order.JsonPPOrderRequest;
import de.metas.frontend_testing.masterdata.pp_order.JsonPPOrderResponse;
import de.metas.frontend_testing.masterdata.pp_order.PPOrderCommand;
import de.metas.frontend_testing.masterdata.product.CreateProductCommand;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductRequest;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.frontend_testing.masterdata.product_planning.CreateProductPlanningCommand;
import de.metas.frontend_testing.masterdata.product_planning.JsonCreateProductPlanningRequest;
import de.metas.frontend_testing.masterdata.product_planning.JsonCreateProductPlanningResponse;
import de.metas.frontend_testing.masterdata.resource.CreateResourceCommand;
import de.metas.frontend_testing.masterdata.resource.JsonCreateResourceRequest;
import de.metas.frontend_testing.masterdata.resource.JsonCreateResourceResponse;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateRequest;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateResponse;
import de.metas.frontend_testing.masterdata.sales_order.SalesOrderCreateCommand;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserRequest;
import de.metas.frontend_testing.masterdata.user.JsonLoginUserResponse;
import de.metas.frontend_testing.masterdata.user.LoginUserCommand;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseRequest;
import de.metas.frontend_testing.masterdata.warehouse.JsonWarehouseResponse;
import de.metas.frontend_testing.masterdata.warehouse.WarehouseCommand;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.mobile.MobileConfigService;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseDAO;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.BiFunction;

@Builder
public class CreateMasterdataCommand
{
	@NonNull private final IProductDAO productDAO = Services.get(IProductDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
	@NonNull private final MobileConfigService mobileConfigService;
	@NonNull private final MobileUIPickingUserProfileRepository mobilePickingConfigRepository;
	@NonNull private final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final DDOrderService ddOrderService;

	@NonNull private final JsonCreateMasterdataRequest request;

	private MasterdataContext context;

	public JsonCreateMasterdataResponse execute()
	{
		this.context = new MasterdataContext();

		// IMPORTANT: the order is very important
		final ImmutableMap<String, JsonLoginUserResponse> login = createLoginUsers();
		final ImmutableMap<String, JsonCreateBPartnerResponse> bpartners = createBPartners();
		final ImmutableMap<String, JsonCreateProductResponse> products = createProducts();
		final ImmutableMap<String, JsonCreateResourceResponse> resources = createResources();
		final ImmutableMap<String, JsonWarehouseResponse> warehouses = createWarehouses();
		final ImmutableMap<String, JsonCreateProductPlanningResponse> productPlannings = createProductPlannings();
		final Map<String, JsonPackingInstructionsResponse> packingInstructions = createPackingInstructions();
		final ImmutableMap<String, JsonPickingSlotCreateResponse> pickingSlots = createPickingSlots();
		final JsonMobileConfigResponse mobileConfig = createMobileConfiguration();
		final ImmutableMap<String, JsonCreateHUResponse> hus = createHUs();
		final ImmutableMap<String, JsonSalesOrderCreateResponse> salesOrders = createSalesOrders();
		final ImmutableMap<String, JsonDDOrderResponse> distributionOrders = createDistributionOrders();
		final ImmutableMap<String, JsonPPOrderResponse> manufacturingOrders = createManufacturingOrders();

		return JsonCreateMasterdataResponse.builder()
				.mobileConfig(mobileConfig)
				.login(login)
				.bpartners(bpartners)
				.products(products)
				.resources(resources)
				.productPlannings(productPlannings)
				.pickingSlots(pickingSlots)
				.warehouses(warehouses)
				.packingInstructions(packingInstructions)
				.handlingUnits(hus)
				.salesOrders(salesOrders)
				.distributionOrders(distributionOrders)
				.manufacturingOrders(manufacturingOrders)
				.build();
	}

	private <REQUEST, RESPONSE> ImmutableMap<String, RESPONSE> process(
			@Nullable final Map<String, REQUEST> requests,
			@NonNull final BiFunction<String, REQUEST, RESPONSE> mapper)
	{
		if (requests == null || requests.isEmpty())
		{
			return ImmutableMap.of();
		}

		return CollectionUtils.mapValues(ImmutableMap.copyOf(requests), mapper);
	}

	private ImmutableMap<String, JsonLoginUserResponse> createLoginUsers()
	{
		return process(request.getLogin(), this::createLoginUser);
	}

	private JsonLoginUserResponse createLoginUser(final String identifier, final JsonLoginUserRequest request)
	{
		return LoginUserCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build().execute();
	}

	private ImmutableMap<String, JsonCreateBPartnerResponse> createBPartners()
	{
		return process(request.getBpartners(), this::createBPartner);
	}

	private JsonCreateBPartnerResponse createBPartner(String identifier, JsonCreateBPartnerRequest request)
	{
		return CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(request)
				.identifier(identifier)
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonCreateProductResponse> createProducts()
	{
		return process(request.getProducts(), this::createProduct);
	}

	private JsonCreateProductResponse createProduct(String identifier, JsonCreateProductRequest request)
	{
		return CreateProductCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonCreateResourceResponse> createResources()
	{
		return process(request.getResources(), this::createResource);
	}

	private JsonCreateResourceResponse createResource(String identifier, JsonCreateResourceRequest request)
	{
		return CreateResourceCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonCreateProductPlanningResponse> createProductPlannings()
	{
		return process(request.getProductPlannings(), this::createProductPlanning);
	}

	private JsonCreateProductPlanningResponse createProductPlanning(String identifier, JsonCreateProductPlanningRequest request)
	{
		return CreateProductPlanningCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonPickingSlotCreateResponse> createPickingSlots()
	{
		return process(request.getPickingSlots(), this::createPickingSlot);
	}

	private JsonPickingSlotCreateResponse createPickingSlot(String identifier, JsonPickingSlotCreateRequest request)
	{
		return PickingSlotCreateCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build().execute();
	}

	private ImmutableMap<String, JsonWarehouseResponse> createWarehouses()
	{
		return process(request.getWarehouses(), this::createWarehouse);
	}

	private JsonWarehouseResponse createWarehouse(String identifier, JsonWarehouseRequest request)
	{
		return WarehouseCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	private @NonNull Map<String, JsonPackingInstructionsResponse> createPackingInstructions()
	{
		return process(request.getPackingInstructions(), this::createPackingInstruction);
	}

	private JsonPackingInstructionsResponse createPackingInstruction(String identifier, JsonPackingInstructionsRequest request)
	{
		return CreatePackingInstructionsCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build().execute();
	}

	private JsonMobileConfigResponse createMobileConfiguration()
	{
		if (request.getMobileConfig() == null)
		{
			return null;
		}

		return MobileConfigCommand.builder()
				.mobileConfigService(mobileConfigService)
				.mobilePickingConfigRepository(mobilePickingConfigRepository)
				.mobileDistributionConfigRepository(mobileDistributionConfigRepository)
				//
				.context(context)
				.request(request.getMobileConfig())
				//
				.build().execute();
	}

	private ImmutableMap<String, JsonCreateHUResponse> createHUs()
	{
		return process(request.getHandlingUnits(), this::createHU);
	}

	private JsonCreateHUResponse createHU(final String identifier, final JsonCreateHURequest request)
	{
		return CreateHUCommand.builder()
				.inventoryService(inventoryService)
				.huQRCodesService(huQRCodesService)
				.context(context)
				.request(request)
				.identifier(identifier)
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonSalesOrderCreateResponse> createSalesOrders()
	{
		return process(request.getSalesOrders(), this::createSalesOrder);
	}

	private JsonSalesOrderCreateResponse createSalesOrder(String identifier, JsonSalesOrderCreateRequest request)
	{
		return SalesOrderCreateCommand.builder()
				.context(context)
				.request(request)
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonDDOrderResponse> createDistributionOrders()
	{
		return process(request.getDistributionOrders(), this::createDistributionOrder);
	}

	private JsonDDOrderResponse createDistributionOrder(String identifier, JsonDDOrderRequest request)
	{
		return DDOrderCommand.builder()
				.ddOrderService(ddOrderService)
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

	private ImmutableMap<String, JsonPPOrderResponse> createManufacturingOrders()
	{
		return process(request.getManufacturingOrders(), this::createManufacturingOrder);
	}

	private JsonPPOrderResponse createManufacturingOrder(String identifier, JsonPPOrderRequest request)
	{
		return PPOrderCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

}
