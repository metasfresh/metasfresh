package de.metas.frontend_testing.masterdata.pp_order;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.model.I_PP_Order;

import java.time.Instant;

@Builder
public class PPOrderCommand
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	@NonNull MasterdataContext context;
	@NonNull JsonPPOrderRequest request;
	@NonNull Identifier identifier;

	public JsonPPOrderResponse execute()
	{
		final ProductId productId = context.getId(request.getProduct(), ProductId.class);
		final Quantity quantity = Quantity.of(request.getQty(), productBL.getStockUOM(productId));
		final Instant datePromised = request.getDatePromised().toInstant();
		final I_PP_Order ppOrder = ppOrderBL.createOrder(
				PPOrderCreateRequest.builder()
						.docBaseType(PPOrderDocBaseType.MANUFACTURING_ORDER)
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(MasterdataContext.CLIENT_ID, MasterdataContext.ORG_ID))
						.plantId(MasterdataContext.DEFAULT_PLANT_ID)
						.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
						.productId(productId)
						.routingId(PPRoutingId.ofRepoId(540118)) // Default Workflow for mobile UI Manufacturing
						.qtyRequired(quantity)
						.dateOrdered(datePromised)
						.datePromised(datePromised)
						.dateStartSchedule(datePromised)
						.completeDocument(true)
						.build()
		);

		return JsonPPOrderResponse.builder()
				.documentNo(ppOrder.getDocumentNo())
				.build();
	}
}
