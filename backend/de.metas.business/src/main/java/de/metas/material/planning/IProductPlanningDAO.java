package de.metas.material.planning;

import de.metas.material.maturing.MaturingConfigLineId;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.product.ResourceId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.ProductBOMVersionsId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface IProductPlanningDAO extends ISingletonService
{
	Optional<ProductPlanning> retrieveManufacturingOrTradingPlanning(@NonNull ProductId productId, @NonNull OrgId orgId);

	@Value
	class ProductPlanningQuery
	{
		@Nullable OrgId orgId;
		@Nullable WarehouseId warehouseId;
		@Nullable ResourceId plantId;
		@Nullable ProductId productId;
		@Nullable MaturingConfigLineId maturingConfigLineId;
		@NonNull AttributeSetInstanceId attributeSetInstanceId;

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
				@Nullable final MaturingConfigLineId maturingConfigLineId,
				@Nullable final AttributeSetInstanceId attributeSetInstanceId)
		{
			this.orgId = orgId;
			this.warehouseId = warehouseId;
			this.plantId = plantId;
			this.productId = productId;
			this.maturingConfigLineId = maturingConfigLineId;
			this.attributeSetInstanceId = attributeSetInstanceId != null ? attributeSetInstanceId : AttributeSetInstanceId.NONE;
		}
	}

	ProductPlanning getById(ProductPlanningId id);

	void deleteById(@NonNull ProductPlanningId id);

	/**
	 * Find best matching product planning.
	 */
	Optional<ProductPlanning> find(ProductPlanningQuery productPlanningQuery);

	/**
	 * Search product plannings to find out which is the plant({@link I_S_Resource}) for given Org/Warehouse/Product.
	 *
	 * @throws NoPlantForWarehouseException if there was no plant found or if there was more then one plant found.
	 */
	ResourceId findPlant(final int adOrgId, final I_M_Warehouse warehouse, final int productId, int attributeSetInstanceId);

	ProductPlanning save(@NonNull ProductPlanning productPlanning);

	void setProductBOMVersionsIdIfAbsent(ProductId productId, ProductBOMVersionsId bomVersionsId);

	Set<ProductBOMVersionsId> retrieveAllPickingBOMVersionsIds();

	List<ProductPlanning> retrieveProductPlanningForBomVersions(final ProductBOMVersionsId bomId);

	List<ProductPlanning> retrieveActiveProductPlanningsBySchemaId(@NonNull ProductPlanningSchemaId schemaId);

	Optional<ProductPlanning> retrieveActiveProductPlanningByProductAndSchemaId(@NonNull ProductId productId, @NonNull ProductPlanningSchemaId schemaId);

	/**
	 * @return Products that don't have PP_ProductPlanning entries
	 */
	Stream<I_M_Product> streamProductsWithNoProductPlanningButWithSchemaSelector();
}
