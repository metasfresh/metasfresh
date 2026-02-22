package de.metas.frontend_testing.masterdata.pp_order;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderCreateRequest;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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
		final OrderLineId salesOrderLineId = request.getSalesOrderLine() != null
				? context.getId(request.getSalesOrderLine(), OrderLineId.class)
				: null;
		final I_PP_Order ppOrder = ppOrderBL.createOrder(
				PPOrderCreateRequest.builder()
						.docBaseType(PPOrderDocBaseType.MANUFACTURING_ORDER)
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(MasterdataContext.CLIENT_ID, MasterdataContext.ORG_ID))
						.plantId(MasterdataContext.DEFAULT_PLANT_ID)
						.warehouseId(context.getId(request.getWarehouse(), WarehouseId.class))
						.productId(productId)
						.routingId(MasterdataContext.DEFAULT_ROUTING_ID)
						.qtyRequired(quantity)
						.dateOrdered(datePromised)
						.datePromised(datePromised)
						.dateStartSchedule(datePromised)
						.salesOrderLineId(salesOrderLineId)
						.completeDocument(true)
						.build()
		);

		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		context.putIdentifier(identifier, ppOrderId);

		if (request.getLutuConfigurationTU() != null)
		{
			setLUTUConfiguration(ppOrder, productId, quantity.getUomId());
		}

		if (request.getPiItemProduct() != null)
		{
			setPIItemProduct(ppOrder);
		}

		checkBOMLines(ppOrderId);

		return JsonPPOrderResponse.builder()
				.documentNo(ppOrder.getDocumentNo())
				.build();
	}

	private void setLUTUConfiguration(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		final HUPIItemProductId tuPIItemProductId = context.getId(request.getLutuConfigurationTU(), HUPIItemProductId.class);
		final I_M_HU_PI_Item_Product tuPIItemProduct = Services.get(IHUPIItemProductDAO.class).getRecordById(tuPIItemProductId);

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				productId,
				uomId,
				null,
				true);
		lutuConfigurationFactory.save(lutuConfiguration);

		final de.metas.handlingunits.model.I_PP_Order huPPOrder = InterfaceWrapperHelper.create(ppOrder, de.metas.handlingunits.model.I_PP_Order.class);
		huPPOrder.setM_HU_LUTU_Configuration(lutuConfiguration);
		InterfaceWrapperHelper.save(huPPOrder);
	}

	private void setPIItemProduct(@NonNull final I_PP_Order ppOrder)
	{
		final HUPIItemProductId piItemProductId = context.getId(request.getPiItemProduct(), HUPIItemProductId.class);
		ppOrder.setM_HU_PI_Item_Product_ID(piItemProductId.getRepoId());
		InterfaceWrapperHelper.save(ppOrder);
	}

	private void checkBOMLines(final PPOrderId ppOrderId)
	{
		if (request.getLines() == null || request.getLines().isEmpty()) {return;}

		final ArrayList<I_PP_Order_BOMLine> lines = new ArrayList<>(ppOrderBL.getOrderBOMLines(ppOrderId));
		for (Map.Entry<String, JsonPPOrderRequest.Line> lineIdentifierAndRequest : request.getLines().entrySet())
		{
			final Identifier lineIdentifier = Identifier.ofString(lineIdentifierAndRequest.getKey());
			final JsonPPOrderRequest.Line lineRequest = lineIdentifierAndRequest.getValue();

			final I_PP_Order_BOMLine line = findAndRemoveMatchingBOMLine(lines, lineRequest);

			assertExpectedPickingInstructionMatching(lineRequest, line);

			context.putIdentifier(lineIdentifier, PPOrderBOMLineId.ofRepoId(line.getPP_Order_BOMLine_ID()));
		}
	}

	private static void assertExpectedPickingInstructionMatching(final JsonPPOrderRequest.Line lineRequest, final I_PP_Order_BOMLine line)
	{
		if (lineRequest.getExpectedPickingInstruction() == null) {return;}

		final String expectedPickingInstruction = StringUtils.trimBlankToNull(lineRequest.getExpectedPickingInstruction());
		final String actualPickingInstruction = StringUtils.trimBlankToNull(line.getPickingInstruction());
		if (!Objects.equals(actualPickingInstruction, expectedPickingInstruction))
		{
			throw new AdempiereException("PickingInstruction not matching")
					.setParameter("expected", lineRequest.getExpectedPickingInstruction())
					.setParameter("actual", line.getPickingInstruction())
					.setParameter("line", line)
					.setParameter("lineRequest", lineRequest);
		}
	}

	private I_PP_Order_BOMLine findAndRemoveMatchingBOMLine(
			@NonNull final ArrayList<I_PP_Order_BOMLine> lines,
			@NonNull final JsonPPOrderRequest.Line lineRequest)
	{
		final ProductId lineProductId = context.getId(lineRequest.getProduct(), ProductId.class);

		final I_PP_Order_BOMLine line = lines.stream()
				.filter(l -> l.getM_Product_ID() == lineProductId.getRepoId())
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No BOM lines found for " + lineProductId + " in " + lines));
		lines.remove(line);
		return line;
	}
}
