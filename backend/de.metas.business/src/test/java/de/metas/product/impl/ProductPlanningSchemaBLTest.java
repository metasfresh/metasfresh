package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.OnMaterialReceiptWithDestWarehouse;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchema;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.product.model.X_M_Product_PlanningSchema;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductPlanningSchemaBLTest
{
	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createProductPlanningForAllProducts_1Schema()
	{
		final ProductPlanningSchemaSelector selector = ProductPlanningSchemaSelector.NORMAL;
		final ProductId productId1 = createProduct("Product1", selector);

		final WarehouseId warehouseId = createWarehouse("wh1");
		final DistributionNetworkId distributionNetworkId = createNetworkDistribution("NwDist1");

		final ProductPlanningSchema schema1 = createSchema(warehouseId, distributionNetworkId, selector);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		assertThat(defaultProductPlanningsForAllProducts).size().isOne();

		final I_PP_Product_Planning productPlanning = defaultProductPlanningsForAllProducts.get(0);

		assertThat(distributionNetworkId.getRepoId()).isEqualTo(productPlanning.getDD_NetworkDistribution_ID());
		assertThat(productId1.getRepoId()).isEqualTo(productPlanning.getM_Product_ID());
		assertThat(schema1.getId().getRepoId()).isEqualTo(productPlanning.getM_Product_PlanningSchema_ID());

	}

	@Test
	public void createProductPlanningForAllProducts_2Schema()
	{
		final ProductPlanningSchemaSelector selector = ProductPlanningSchemaSelector.NORMAL;
		final ProductId productId1 = createProduct("Product1", selector);

		final WarehouseId warehouseId1 = createWarehouse("wh1");
		final DistributionNetworkId distributionNetworkId1 = createNetworkDistribution("NwDist1");
		final ProductPlanningSchema schema1 = createSchema(warehouseId1, distributionNetworkId1, selector);

		final WarehouseId warehouseId2 = createWarehouse("wh2");
		final DistributionNetworkId distributionNetworkId2 = createNetworkDistribution("NwDist2");
		final ProductPlanningSchema schema2 = createSchema(warehouseId2, distributionNetworkId2, selector);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		assertThat(defaultProductPlanningsForAllProducts).size().isEqualTo(2);

		assertThat(defaultProductPlanningsForAllProducts).allSatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Product_ID()).isEqualTo(productId1.getRepoId());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Warehouse_ID()).isEqualTo(warehouseId1.getRepoId());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Warehouse_ID()).isEqualTo(warehouseId2.getRepoId());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Product_PlanningSchema_ID()).isEqualTo(schema1.getId().getRepoId());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Product_PlanningSchema_ID()).isEqualTo(schema2.getId().getRepoId());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getDD_NetworkDistribution_ID()).isEqualTo(distributionNetworkId1.getRepoId());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getDD_NetworkDistribution_ID()).isEqualTo(distributionNetworkId2.getRepoId());
				});

	}

	@Test
	public void createProductPlanningsForSchema()
	{
		final ProductPlanningSchemaSelector selector = ProductPlanningSchemaSelector.NORMAL;
		final ProductId productId1 = createProduct("Product1", selector);

		final WarehouseId warehouseId1 = createWarehouse("wh1");
		final DistributionNetworkId distributionNetworkId = createNetworkDistribution("NwDist1");
		final ProductPlanningSchema schema1 = createSchema(warehouseId1, distributionNetworkId, selector);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createOrUpdateDefaultProductPlanningsForSchemaId(schema1.getId());

		assertThat(defaultProductPlanningsForAllProducts).size().isOne();

		final I_PP_Product_Planning productPlanning = defaultProductPlanningsForAllProducts.get(0);

		assertThat(distributionNetworkId.getRepoId()).isEqualTo(productPlanning.getDD_NetworkDistribution_ID());
		assertThat(productId1.getRepoId()).isEqualTo(productPlanning.getM_Product_ID());
		assertThat(schema1.getId().getRepoId()).isEqualTo(productPlanning.getM_Product_PlanningSchema_ID());
		assertThat(productPlanning.getOnMaterialReceiptWithDestWarehouse()).isEqualTo(X_M_Product_PlanningSchema.ONMATERIALRECEIPTWITHDESTWAREHOUSE_CreateDistributionOrder);

	}

	@Test
	public void updateProductPlanningForSchema()
	{
		final ProductPlanningSchemaSelector selector = ProductPlanningSchemaSelector.NORMAL;
		final ProductId productId1 = createProduct("Product1", selector);

		final WarehouseId warehouseId1 = createWarehouse("wh1");
		final DistributionNetworkId distributionNetworkId1 = createNetworkDistribution("NwDist1");

		ProductPlanningSchema schema1 = createSchema(warehouseId1, distributionNetworkId1, selector);

		productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		final DistributionNetworkId distributionNetworkId2 = createNetworkDistribution("NwDist2");
		schema1 = schema1.toBuilder()
				.distributionNetworkId(distributionNetworkId2)
				.build();
		ProductPlanningSchemaDAO.save(schema1);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createOrUpdateDefaultProductPlanningsForSchemaId(schema1.getId());

		assertThat(defaultProductPlanningsForAllProducts).size().isOne();

		final I_PP_Product_Planning productPlanning = defaultProductPlanningsForAllProducts.get(0);
		assertThat(distributionNetworkId2.getRepoId()).isEqualTo(productPlanning.getDD_NetworkDistribution_ID());
		assertThat(productId1.getRepoId()).isEqualTo(productPlanning.getM_Product_ID());
		assertThat(schema1.getId().getRepoId()).isEqualTo(productPlanning.getM_Product_PlanningSchema_ID());

	}

	@Test
	public void updateProductPlanningForSchema_deleteSelectorFromProduct()
	{
		final ProductPlanningSchemaSelector selector = ProductPlanningSchemaSelector.NORMAL;
		final ProductId productId1 = createProduct("Product1", selector);

		final WarehouseId warehouseId1 = createWarehouse("wh1");
		final DistributionNetworkId distributionNetworkId = createNetworkDistribution("NwDist1");

		final ProductPlanningSchema schema1 = createSchema(warehouseId1, distributionNetworkId, selector);

		productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		setProductSelector(productId1, null);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createOrUpdateDefaultProductPlanningsForSchemaId(schema1.getId());

		assertThat(defaultProductPlanningsForAllProducts).isEmpty();

	}

	private DistributionNetworkId createNetworkDistribution(final String name)
	{
		final I_DD_NetworkDistribution nwDist = newInstance(I_DD_NetworkDistribution.class);
		nwDist.setName(name);
		saveRecord(nwDist);
		return DistributionNetworkId.ofRepoId(nwDist.getDD_NetworkDistribution_ID());
	}

	private WarehouseId createWarehouse(final String name)
	{
		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		wh.setName(name);
		save(wh);
		return WarehouseId.ofRepoId(wh.getM_Warehouse_ID());
	}

	private ProductPlanningSchema createSchema(
			final WarehouseId warehouseId,
			final DistributionNetworkId distributionNetworkId,
			final ProductPlanningSchemaSelector selector)
	{
		final ProductPlanningSchema schema = ProductPlanningSchema.builder()
				.selector(selector)
				.orgId(OrgId.ANY)
				.warehouseId(warehouseId)
				.distributionNetworkId(distributionNetworkId)
				.onMaterialReceiptWithDestWarehouse(OnMaterialReceiptWithDestWarehouse.CREATE_DISTRIBUTION_ORDER)
				.build();

		ProductPlanningSchemaDAO.save(schema);

		// NOTE: to indirectly test it, we are loading it again and returning instead of just return it directly
		return ProductPlanningSchemaDAO.getById(schema.getId());
	}

	private ProductId createProduct(final String name, final ProductPlanningSchemaSelector selector)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setM_ProductPlanningSchema_Selector(selector.getCode());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private void setProductSelector(final ProductId productId, ProductPlanningSchemaSelector selector)
	{
		I_M_Product product = Services.get(IProductDAO.class).getById(productId);
		product.setM_ProductPlanningSchema_Selector(selector != null ? selector.getCode() : null);
		saveRecord(product);
	}
}
