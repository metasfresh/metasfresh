package de.metas.material.planning.impl;

import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

public class ProductPlanningDAO_findPlantTest
{
	/**
	 * service under test
	 */
	private ProductPlanningDAO productPlanningDAO;
	private IContextAware context;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		this.context = PlainContextAware.newOutOfTrx();
		this.productPlanningDAO = (ProductPlanningDAO)Services.get(IProductPlanningDAO.class);
	}

	/**
	 * Case: warehouse has a Plant configured, no product planning defined.
	 * <p>
	 * Expectation: plant from warehouse shall be taken.
	 *
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29">task</a>
	 */
	@Test
	public void findPlant_WarehouseWithPlant()
	{
		final ResourceId plantId = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plantId);
		final int adOrgId = -1; // N/A
		final int productId = -1; // N/A
		final int attributeSetInstanceId = AttributeConstants.M_AttributeSetInstance_ID_None;

		final ResourceId plantActual = productPlanningDAO.findPlant(
				adOrgId,
				warehouse,
				productId,
				attributeSetInstanceId);

		assertThat(plantActual).isEqualTo(plantId);
	}

	/**
	 * Case: warehouse has a Plant configured, we also have a valid product planning configured.
	 * <p>
	 * Expectation: plant from warehouse shall be taken.
	 *
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29">task</a>
	 */
	@Test
	public void findPlant_WarehouseWithPlant_ValidProductPlanning()
	{
		final ResourceId plantId = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plantId);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		createProductPlanningWithPlant(org, warehouse, product);

		final ResourceId plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantActual).isEqualTo(plantId);
	}

	/**
	 * Case: warehouse has NO Plant configured, there is no product planning
	 * <p>
	 * Expectation: exception shall be thrown.
	 *
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29">task</a>
	 */
	@Test
	public void findPlant_WarehouseWithoutPlant_NoProductPlanning()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		final int adOrgId = 1;
		final int productId = 2;

		assertThatThrownBy(() -> productPlanningDAO.findPlant(adOrgId, warehouse, productId, AttributeConstants.M_AttributeSetInstance_ID_None))
				.isInstanceOf(NoPlantForWarehouseException.class);
	}

	/**
	 * Case: warehouse has NO Plant configured, we also have a valid product planning configured.
	 * <p>
	 * Expectation: plant from product planning shall be taken.
	 *
	 * @implSpec <a href="http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29">task</a>
	 */
	@Test
	public void findPlant_WarehouseWithoutPlant_ValidProductPlanning()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		final ProductPlanning productPlanning = createProductPlanningWithPlant(org, warehouse, product);

		final ResourceId plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantActual).isEqualTo(productPlanning.getPlantId());
	}

	@Test
	public void findPlant_WarehouseWithoutPlant_DifferentPlantsFound()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");

		createProductPlanningWithPlant(org, warehouse, product);
		createProductPlanningWithPlant(org, warehouse, product);

		assertThatThrownBy(() -> productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None))
				//
				.isInstanceOf(NoPlantForWarehouseException.class);
	}

	@Test
	public void findPlant_WarehouseWithoutPlant_MultipleProductPlanningButSamePlant()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		final ProductPlanning productPlanning = createProductPlanningWithPlant(org, warehouse, product);
		final ResourceId plantId = productPlanning.getPlantId();

		// create some more product planning records, but with same plant and without any plant
		createProductPlanning(org, warehouse, product, plantId);
		createProductPlanning(org, warehouse, product, plantId);
		createProductPlanning(org, warehouse, product, null);
		createProductPlanning(org, warehouse, product, null);

		final ResourceId plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantActual).isEqualTo(plantId);
	}

	private ResourceId createPlant(final String name)
	{
		final I_S_Resource plant = InterfaceWrapperHelper.newInstance(I_S_Resource.class, context);
		plant.setValue(name);
		plant.setName(name);
		plant.setManufacturingResourceType(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);
		InterfaceWrapperHelper.save(plant);
		return ResourceId.ofRepoId(plant.getS_Resource_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private I_M_Warehouse createWarehouse(final String name, final ResourceId plantId)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, context);
		warehouse.setValue(name);
		warehouse.setName(name);
		if (plantId != null)
		{
			warehouse.setPP_Plant_ID(plantId.getRepoId());
		}
		InterfaceWrapperHelper.save(warehouse);
		return warehouse;
	}

	@SuppressWarnings("SameParameterValue")
	private I_AD_Org createOrg(final String name)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, context);
		org.setValue(name);
		org.setName(name);
		InterfaceWrapperHelper.save(org);
		return org;
	}

	@SuppressWarnings("SameParameterValue")
	private I_M_Product createProduct(final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, context);
		product.setValue(name);
		product.setName(name);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private final AtomicInteger createProductPlanningWithPlant_NextPlantNo = new AtomicInteger(1);

	private ProductPlanning createProductPlanningWithPlant(I_AD_Org org, I_M_Warehouse warehouse, I_M_Product product)
	{
		final int plantNo = createProductPlanningWithPlant_NextPlantNo.getAndIncrement();
		final ResourceId plantId = createPlant("Plant_From_ProductPlanning_" + plantNo);
		return createProductPlanning(org, warehouse, product, plantId);
	}

	private ProductPlanning createProductPlanning(I_AD_Org org, I_M_Warehouse warehouse, I_M_Product product, ResourceId plantId)
	{
		return productPlanningDAO.save(ProductPlanning.builder()
				.isAttributeDependant(false)
				.orgId(OrgId.ofRepoIdOrAny(org.getAD_Org_ID()))
				.warehouseId(warehouse != null ? WarehouseId.ofRepoIdOrNull(warehouse.getM_Warehouse_ID()) : null)
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.plantId(plantId)
				.build());
	}

}
