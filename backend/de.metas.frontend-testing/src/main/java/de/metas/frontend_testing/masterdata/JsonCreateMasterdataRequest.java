package de.metas.frontend_testing.masterdata;

import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.hu.JsonCreateHURequest;
import de.metas.frontend_testing.masterdata.hu.JsonPackingInstructionsRequest;
import de.metas.frontend_testing.masterdata.mobile_configuration.JsonMobileConfigRequest;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductRequest;
import de.metas.frontend_testing.masterdata.sales_order.JsonSalesOrderCreateRequest;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCreateMasterdataRequest
{
	@Nullable JsonMobileConfigRequest mobileConfig;
	@Nullable Map<String, JsonCreateBPartnerRequest> bpartners;
	@Nullable Map<String, JsonCreateProductRequest> products;
	@Nullable Map<String, JsonPackingInstructionsRequest> packingInstructions;
	@Nullable Map<String, JsonCreateHURequest> handlingUnits;
	@Nullable Map<String, JsonSalesOrderCreateRequest> salesOrders;
}
