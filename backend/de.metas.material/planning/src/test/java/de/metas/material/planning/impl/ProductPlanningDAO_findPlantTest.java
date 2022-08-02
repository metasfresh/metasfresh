package de.metas.material.planning.impl;

import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.product.ResourceId;
import de.metas.resource.ManufacturingResourceType;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	 * Task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithPlant()
	{
		final ResourceId plantId = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plantId);
		final int adOrgId = -1; // N/A
		final int productId = -1; // N/A
		final int attributeSetInstanceId = AttributeConstants.M_AttributeSetInstance_ID_None;

		final ResourceId plantIdActual = productPlanningDAO.findPlantId(
				adOrgId,
				warehouse,
				productId,
				attributeSetInstanceId);

		assertThat(plantIdActual).isEqualTo(plantId);
	}

	/**
	 * Case: warehouse has a Plant configured, we also have a valid product planning configured.
	 *
	 * Expectation: plant from warehouse shall be taken.
	 *
	 * Task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithPlant_ValidProductPlanning()
	{
		final ResourceId plantId = createPlant("Plant");
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", plantId);
		final I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct();
		createProductPlanningWithPlant(org, warehouse, product);

		final ResourceId plantIdActual = productPlanningDAO.findPlantId(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantIdActual).isEqualTo(plantId);
	}

	/**
	 * Case: warehouse has NO Plant configured, there is no product planning
	 *
	 * Expectation: exception shall be thrown.
	 *
	 * Task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithoutPlant_NoProductPlanning()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		final int adOrgId = 1;
		final int productId = 2;

		assertThatThrownBy(() -> productPlanningDAO.findPlantId(adOrgId, warehouse, productId, AttributeConstants.M_AttributeSetInstance_ID_None))
				.isInstanceOf(NoPlantForWarehouseException.class);
	}

	/**
	 * Case: warehouse has NO Plant configured, we also have a valid product planning configured.
	 *
	 * Expectation: plant from product planning shall be taken.
	 *
	 * Task http://dewiki908/mediawiki/index.php/07900_Ressource_not_set_in_MRP_Info_%28102098673699%29
	 */
	@Test
	public void findPlant_WarehouseWithoutPlant_ValidProductPlanning()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		final I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct();
		final I_PP_Product_Planning productPlanning = createProductPlanningWithPlant(org, warehouse, product);

		final ResourceId plantIdActual = productPlanningDAO.findPlantId(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantIdActual).as("plant").isNotNull();
		assertThat(plantIdActual.getRepoId()).isEqualTo(productPlanning.getS_Resource_ID());
	}

	@Test
	public void findPlant_WarehouseWithoutPlant_DifferentPlantsFound()
	{
		final I_M_Warehouse warehouse = createWarehouse("Warehouse", null);
		final I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct();

		createProductPlanningWithPlant(org, warehouse, product);
		createProductPlanningWithPlant(org, warehouse, product);

		assertThatThrownBy(() -> productPlanningDAO.findPlantId(
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
		final I_AD_Org org = createOrg("org");
		final I_M_Product product = createProduct();
		final I_PP_Product_Planning productPlanning = createProductPlanningWithPlant(org, warehouse, product);
		final ResourceId plantId = ResourceId.ofRepoIdOrNull(productPlanning.getS_Resource_ID());

		// create some more product planning records, but with same plant and without any plant
		createProductPlanning(org, warehouse, product, plantId);
		createProductPlanning(org, warehouse, product, plantId);
		createProductPlanning(org, warehouse, product, null);
		createProductPlanning(org, warehouse, product, null);

		final ResourceId plantIdActual = productPlanningDAO.findPlantId(
				org.getAD_Org_ID(),
				warehouse,
				product.getM_Product_ID(),
				AttributeConstants.M_AttributeSetInstance_ID_None);

		assertThat(plantIdActual).isEqualTo(plantId);
	}

	private ResourceId createPlant(final String name)
	{
		final I_S_Resource plant = InterfaceWrapperHelper.newInstance(I_S_Resource.class, context);
		plant.setValue(name);
		plant.setName(name);
		plant.setManufacturingResourceType(ManufacturingResourceType.Plant.getCode());
		InterfaceWrapperHelper.save(plant);
		return ResourceId.ofRepoId(plant.getS_Resource_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private I_M_Warehouse createWarehouse(final String name, @Nullable final ResourceId plantId)
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

	private I_M_Product createProduct()
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, context);
		product.setValue("product");
		product.setName("product");
		InterfaceWrapperHelper.save(product);
		return product;
	}

	private final AtomicInteger createProductPlanningWithPlant_NextPlantNo = new AtomicInteger(1);

	private I_PP_Product_Planning createProductPlanningWithPlant(final I_AD_Org org, final I_M_Warehouse warehouse, final I_M_Product product)
	{
		final int plantNo = createProductPlanningWithPlant_NextPlantNo.getAndIncrement();
		final ResourceId plantId = createPlant("Plant_From_ProductPlanning_" + plantNo);
		return createProductPlanning(org, warehouse, product, plantId);
	}

	private I_PP_Product_Planning createProductPlanning(final I_AD_Org org, final I_M_Warehouse warehouse, final I_M_Product product, @Nullable final ResourceId plantId)
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
