package de.metas.material.planning.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.product.ResourceId;
import de.metas.util.Services;

public class ProductPlanningDAO_findPlantTest
{
	/** service under test */
	private ProductPlanningDAO productPlanningDAO;
	private IContextAware context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.context = PlainContextAware.newOutOfTrx();
		this.productPlanningDAO = (ProductPlanningDAO)Services.get(IProductPlanningDAO.class);
	}

	/**
	 * Case: warehouse has a Plant configured, no product planning defined.
	 *
	 * Expectation: plant from warehouse shall be taken.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithPlant()
	{
		final ResourceId plantId = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plantId);
		final int adOrgId = -1; // N/A
		final int productId = -1; // N/A
		final int attributeSetInstanceId = AttributeConstants.M_AttributeSetInstance_ID_None;

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				adOrgId,
				warehouse,
				productId,
				attributeSetInstanceId);

		assertThat(plantActual).as("plant").isNotNull();
		assertThat(plantActual.getS_Resource_ID()).isEqualTo(plantId.getRepoId());
	}

	/**
	 * Case: warehouse has a Plant configured, we also have a valid product planning configured.
	 *
	 * Expectation: plant from warehouse shall be taken.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithPlant_ValidProductPlanning()
	{
		final ResourceId plantId = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plantId);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		createProductPlanningWithPlant(org, warehouse, product);

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantActual).as("plant").isNotNull();
		assertThat(plantActual.getS_Resource_ID()).isEqualTo(plantId.getRepoId());
	}

	/**
	 * Case: warehouse has NO Plant configured, there is no product planning
	 *
	 * Expectation: exception shall be thrown.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
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
	 *
	 * Expectation: plant from product planning shall be taken.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithoutPlant_ValidProductPlanning()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		final I_PP_Product_Planning productPlanning = createProductPlanningWithPlant(org, warehouse, product);
		final I_S_Resource plantExpected = productPlanning.getS_Resource();

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantActual).as("plant").isNotNull();
		assertThat(plantActual.getS_Resource_ID()).isEqualTo(plantExpected.getS_Resource_ID());
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
		final I_PP_Product_Planning productPlanning = createProductPlanningWithPlant(org, warehouse, product);
		final ResourceId plantId = ResourceId.ofRepoIdOrNull(productPlanning.getS_Resource_ID());

		// create some more product planning records, but with same plant and without any plant
		createProductPlanning(org, warehouse, product, plantId);
		createProductPlanning(org, warehouse, product, plantId);
		createProductPlanning(org, warehouse, product, null);
		createProductPlanning(org, warehouse, product, null);

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantActual).as("plant").isNotNull();
		assertThat(plantActual.getS_Resource_ID()).isEqualTo(plantId.getRepoId());
	}

	private final ResourceId createPlant(final String name)
	{
		final I_S_Resource plant = InterfaceWrapperHelper.newInstance(I_S_Resource.class, context);
		plant.setValue(name);
		plant.setName(name);
		plant.setManufacturingResourceType(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);
		InterfaceWrapperHelper.save(plant);
		return ResourceId.ofRepoId(plant.getS_Resource_ID());
	}

	private final I_M_Warehouse createWarehouse(final String name, final ResourceId plantId)
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

	private final I_AD_Org createOrg(final String name)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, context);
		org.setValue(name);
		org.setName(name);
		InterfaceWrapperHelper.save(org);
		return org;
	}

	private I_M_Product createProduct(final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, context);
		product.setValue(name);
		product.setName(name);
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private AtomicInteger createProductPlanningWithPlant_NextPlantNo = new AtomicInteger(1);

	private I_PP_Product_Planning createProductPlanningWithPlant(I_AD_Org org, I_M_Warehouse warehouse, I_M_Product product)
	{
		final int plantNo = createProductPlanningWithPlant_NextPlantNo.getAndIncrement();
		final ResourceId plantId = createPlant("Plant_From_ProductPlanning_" + plantNo);
		return createProductPlanning(org, warehouse, product, plantId);
	}

	private I_PP_Product_Planning createProductPlanning(I_AD_Org org, I_M_Warehouse warehouse, I_M_Product product, ResourceId plantId)
	{
		final I_PP_Product_Planning pp = InterfaceWrapperHelper.newInstance(I_PP_Product_Planning.class, context);
		pp.setIsAttributeDependant(false);
		pp.setAD_Org_ID(org.getAD_Org_ID());
		pp.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		pp.setM_Product_ID(product.getM_Product_ID());
		if (plantId != null)
		{
			pp.setS_Resource_ID(plantId.getRepoId());
		}
		InterfaceWrapperHelper.save(pp);
		return pp;
	}

}
