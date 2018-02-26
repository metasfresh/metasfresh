package de.metas.material.planning.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;

public class ProductPlanningDAO_findPlantTest
{
	/** service under test */
	private ProductPlanningDAO productPlanningDAO;
	private IContextAware context;

	@Before
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
		final I_S_Resource plant = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plant);
		final int adOrgId = -1; // N/A
		final int productId = -1; // N/A
		final int attributeSetInstanceId = AttributeConstants.M_AttributeSetInstance_ID_None;

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				adOrgId,
				warehouse,
				productId,
				attributeSetInstanceId);

		Assert.assertNotNull("plant shall be found", plantActual);
		Assert.assertEquals("invalid plant", plant.getS_Resource_ID(), plantActual.getS_Resource_ID());
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
		final I_S_Resource plant = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plant);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		createProductPlanningWithPlant(org, warehouse, product);

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		Assert.assertNotNull("plant shall be found", plantActual);
		Assert.assertEquals("invalid plant", plant.getS_Resource_ID(), plantActual.getS_Resource_ID());
	}

	/**
	 * Case: warehouse has NO Plant configured, there is no product planning
	 *
	 * Expectation: exception shall be thrown.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test(expected = NoPlantForWarehouseException.class)
	public void findPlant_WarehouseWithoutPlant_NoProductPlanning()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		final int adOrgId = -1; // N/A
		final int productId = -1; // N/A

		// shall throw exception
		productPlanningDAO.findPlant(adOrgId, warehouse, productId, AttributeConstants.M_AttributeSetInstance_ID_None);
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

		Assert.assertNotNull("plant shall be found", plantActual);
		Assert.assertEquals("invalid plant", plantExpected.getS_Resource_ID(), plantActual.getS_Resource_ID());
	}

	@Test(expected = NoPlantForWarehouseException.class)
	public void findPlant_WarehouseWithoutPlant_DifferentPlantsFound()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");

		createProductPlanningWithPlant(org, warehouse, product);
		createProductPlanningWithPlant(org, warehouse, product);

		productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);
	}

	@Test
	public void findPlant_WarehouseWithoutPlant_MultipleProductPlanningButSamePlant()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct("product");
		final I_PP_Product_Planning productPlanning = createProductPlanningWithPlant(org, warehouse, product);
		final I_S_Resource plant = productPlanning.getS_Resource();

		// create some more product planning records, but with same plant and without any plant
		createProductPlanning(org, warehouse, product, plant);
		createProductPlanning(org, warehouse, product, plant);
		createProductPlanning(org, warehouse, product, null);
		createProductPlanning(org, warehouse, product, null);

		final I_S_Resource plantActual = productPlanningDAO.findPlant(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		Assert.assertNotNull("plant shall be found", plantActual);
		Assert.assertEquals("invalid plant", plant.getS_Resource_ID(), plantActual.getS_Resource_ID());
	}

	private final I_S_Resource createPlant(final String name)
	{
		final I_S_Resource plant = InterfaceWrapperHelper.newInstance(I_S_Resource.class, context);
		plant.setValue(name);
		plant.setName(name);
		plant.setManufacturingResourceType(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);
		InterfaceWrapperHelper.save(plant);
		return plant;
	}

	private final I_M_Warehouse createWarehouse(final String name, final I_S_Resource plant)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, context);
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setPP_Plant(plant);
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
		final I_S_Resource plant = createPlant("Plant_From_ProductPlanning_" + plantNo);
		return createProductPlanning(org, warehouse, product, plant);
	}

	private I_PP_Product_Planning createProductPlanning(I_AD_Org org, I_M_Warehouse warehouse, I_M_Product product, I_S_Resource plant)
	{
		final I_PP_Product_Planning pp = InterfaceWrapperHelper.newInstance(I_PP_Product_Planning.class, context);
		pp.setIsAttributeDependant(false);
		pp.setAD_Org(org);
		pp.setM_Warehouse(warehouse);
		pp.setM_Product(product);
		pp.setS_Resource(plant);
		InterfaceWrapperHelper.save(pp);
		return pp;
	}

}
