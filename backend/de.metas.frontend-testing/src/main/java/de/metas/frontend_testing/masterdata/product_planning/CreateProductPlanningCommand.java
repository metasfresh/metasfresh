package de.metas.frontend_testing.masterdata.product_planning;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.ProductBOMVersionsId;

@Builder
public class CreateProductPlanningCommand
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateProductPlanningRequest request;
	@NonNull private final Identifier identifier;

	public JsonCreateProductPlanningResponse execute()
	{
		final ProductPlanning.ProductPlanningBuilder builder = ProductPlanning.builder()
				.seqNo(request.getSeqNo())
				.orgId(MasterdataContext.ORG_ID)
				.plantId(request.getPlant() != null ? context.getId(request.getPlant(), ResourceId.class) : MasterdataContext.DEFAULT_PLANT_ID)
				.warehouseId(request.getWarehouse() != null ? context.getId(request.getWarehouse(), WarehouseId.class) : null)
				.productId(context.getId(request.getProduct(), ProductId.class))
				.isAttributeDependant(false)
				.isPurchased(false);

		boolean isManufactured = request.getBom() != null || request.isPickingOrder();
		if (isManufactured)
		{
			final Identifier bomIdentifier = request.getBom() != null ? request.getBom() : request.getProduct();

			builder.isManufactured(true)
					.workflowId(MasterdataContext.DEFAULT_ROUTING_ID)
					.bomVersionsId(context.getId(bomIdentifier, ProductBOMVersionsId.class))
					.isPickingOrder(request.isPickingOrder());
		}

		final ProductPlanning productPlanning = productPlanningDAO.save(builder.build());
		context.putIdentifier(identifier, productPlanning.getIdNotNull());

		return JsonCreateProductPlanningResponse.builder()
				.build();
	}
}
