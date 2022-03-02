package de.metas.material.planning;

import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_Planning;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IProductPlanningDAO extends ISingletonService
{
	@Value
	public static class ProductPlanningQuery
	{
		OrgId orgId;
		WarehouseId warehouseId;
		ResourceId plantId;
		ProductId productId;
		AttributeSetInstanceId attributeSetInstanceId;

		/**
		 * @param orgId                  may be null which means only the * org
		 * @param warehouseId            may be null which means "no warehouse" (not any warehouse!)
		 * @param plantId                may be null which means "no plantId"
		 * @param productId              mandatory
		 * @param attributeSetInstanceId mandatory, but might contain the 0-ASI-Id;
		 */
		@Builder
		private ProductPlanningQuery(
				@Nullable final OrgId orgId,
				@Nullable final WarehouseId warehouseId,
				@Nullable final ResourceId plantId,
				@Nullable final ProductId productId,
				@NonNull final AttributeSetInstanceId attributeSetInstanceId)
		{
			this.orgId = orgId;
			this.warehouseId = warehouseId;
			this.plantId = plantId;
			this.productId = productId;
			this.attributeSetInstanceId = attributeSetInstanceId;
		}
	}

	I_PP_Product_Planning getById(ProductPlanningId ppProductPlanningId);

	/**
	 * Find best matching product planning.
	 */
	Optional<I_PP_Product_Planning> find(ProductPlanningQuery productPlanningQuery);

	/**
	 * Search product plannings to find out which is the plant({@link I_S_Resource}) for given Org/Warehouse/Product.
	 *
	 * @throws NoPlantForWarehouseException if there was no plant found or if there was more then one plant found.
	 */
	I_S_Resource findPlant(final int adOrgId, final I_M_Warehouse warehouse, final int productId, int attributeSetInstanceId);

	void save(I_PP_Product_Planning productPlanningRecord);

	void setProductBOMVersionsIdIfAbsent(ProductId productId, ProductBOMVersionsId bomVersionsId);

	Set<ProductBOMVersionsId> retrieveAllPickingBOMVersionsIds();

	List<I_PP_Product_Planning> retrieveProductPlanningForBomVersions(final ProductBOMVersionsId bomId);
}
