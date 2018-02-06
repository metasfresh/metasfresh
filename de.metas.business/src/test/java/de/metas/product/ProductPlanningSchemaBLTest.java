package de.metas.product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Before;
import org.junit.Test;

import de.metas.product.model.I_M_Product_PlanningSchema;

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
		final String selector = "N";
		final I_M_Product product1 = createProduct("Product1", selector);

		final I_M_Warehouse wh1 = createWarehouse("wh1");
		final I_DD_NetworkDistribution networkDistribution1 = createNetworkDistribution("NwDist1");

		final I_M_Product_PlanningSchema schema1 = createSchema(wh1, networkDistribution1, selector);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		assertThat(defaultProductPlanningsForAllProducts).size().isOne();

		final I_PP_Product_Planning productPlanning = defaultProductPlanningsForAllProducts.get(0);

		assertThat(networkDistribution1).isEqualTo(productPlanning.getDD_NetworkDistribution());
		assertThat(product1).isEqualTo(productPlanning.getM_Product());
		assertThat(schema1.getM_Product_PlanningSchema_ID()).isEqualTo(productPlanning.getM_Product_PlanningSchema_ID());

	}

	@Test
	public void createProductPlanningForAllProducts_2Schema()
	{
		final String selector = "N";
		final I_M_Product product1 = createProduct("Product1", selector);

		final I_M_Warehouse wh1 = createWarehouse("wh1");
		final I_DD_NetworkDistribution networkDistribution1 = createNetworkDistribution("NwDist1");
		final I_M_Product_PlanningSchema schema1 = createSchema(wh1, networkDistribution1, selector);

		final I_M_Warehouse wh2 = createWarehouse("wh2");
		final I_DD_NetworkDistribution networkDistribution2 = createNetworkDistribution("NwDist2");
		final I_M_Product_PlanningSchema schema2 = createSchema(wh2, networkDistribution2, selector);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		assertThat(defaultProductPlanningsForAllProducts).size().isEqualTo(2);

		assertThat(defaultProductPlanningsForAllProducts).allSatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Product()).isEqualTo(product1);
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Warehouse()).isEqualTo(wh1);
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Warehouse()).isEqualTo(wh2);
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Product_PlanningSchema_ID()).isEqualTo(schema1.getM_Product_PlanningSchema_ID());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getM_Product_PlanningSchema_ID()).isEqualTo(schema2.getM_Product_PlanningSchema_ID());
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getDD_NetworkDistribution()).isEqualTo(networkDistribution1);
				});

		assertThat(defaultProductPlanningsForAllProducts).anySatisfy(
				productPlanning -> {
					assertThat(productPlanning.getDD_NetworkDistribution()).isEqualTo(networkDistribution2);
				});

	}

	@Test
	public void createProductPlanningsForSchema()
	{
		final String selector = "N";
		final I_M_Product product1 = createProduct("Product1", selector);

		final I_M_Warehouse wh1 = createWarehouse("wh1");
		final I_DD_NetworkDistribution networkDistribution1 = createNetworkDistribution("NwDist1");
		final I_M_Product_PlanningSchema schema1 = createSchema(wh1, networkDistribution1, selector);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createUpdateDefaultProductPlanningsForSchema(schema1);

		assertThat(defaultProductPlanningsForAllProducts).size().isOne();

		final I_PP_Product_Planning productPlanning = defaultProductPlanningsForAllProducts.get(0);

		assertThat(networkDistribution1).isEqualTo(productPlanning.getDD_NetworkDistribution());
		assertThat(product1).isEqualTo(productPlanning.getM_Product());
		assertThat(schema1.getM_Product_PlanningSchema_ID()).isEqualTo(productPlanning.getM_Product_PlanningSchema_ID());

	}

	@Test
	public void updateProductPlanningForSchema()
	{
		final String selector = "N";
		final I_M_Product product1 = createProduct("Product1", selector);

		final I_M_Warehouse wh1 = createWarehouse("wh1");
		final I_DD_NetworkDistribution networkDistribution1 = createNetworkDistribution("NwDist1");

		final I_M_Product_PlanningSchema schema1 = createSchema(wh1, networkDistribution1, selector);

		productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		final I_DD_NetworkDistribution networkDistribution2 = createNetworkDistribution("NwDist2");
		schema1.setDD_NetworkDistribution(networkDistribution2);

		save(schema1);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createUpdateDefaultProductPlanningsForSchema(schema1);

		assertThat(defaultProductPlanningsForAllProducts).size().isOne();

		final I_PP_Product_Planning productPlanning = defaultProductPlanningsForAllProducts.get(0);
		assertThat(networkDistribution2).isEqualTo(productPlanning.getDD_NetworkDistribution());
		assertThat(product1).isEqualTo(productPlanning.getM_Product());
		assertThat(schema1.getM_Product_PlanningSchema_ID()).isEqualTo(productPlanning.getM_Product_PlanningSchema_ID());

	}

	@Test
	public void updateProductPlanningForSchema_deleteSelectorFromProduct()
	{
		final String selector = "N";
		final I_M_Product product1 = createProduct("Product1", selector);

		final I_M_Warehouse wh1 = createWarehouse("wh1");
		final I_DD_NetworkDistribution networkDistribution1 = createNetworkDistribution("NwDist1");

		final I_M_Product_PlanningSchema schema1 = createSchema(wh1, networkDistribution1, selector);

		productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();

		product1.setM_ProductPlanningSchema_Selector(null);

		save(product1);

		final List<I_PP_Product_Planning> defaultProductPlanningsForAllProducts = productPlanningSchemaBL.createUpdateDefaultProductPlanningsForSchema(schema1);

		assertThat(defaultProductPlanningsForAllProducts).isEmpty();

	}

	private I_DD_NetworkDistribution createNetworkDistribution(final String name)
	{
		final I_DD_NetworkDistribution nwDist = newInstance(I_DD_NetworkDistribution.class);
		nwDist.setName(name);
		save(nwDist);
		return nwDist;
	}

	private I_M_Warehouse createWarehouse(final String name)
	{
		final I_M_Warehouse wh = newInstance(I_M_Warehouse.class);
		wh.setName(name);
		save(wh);
		return wh;
	}

	private I_M_Product_PlanningSchema createSchema(final I_M_Warehouse wh, final I_DD_NetworkDistribution nwDist, final String selector)
	{
		final I_M_Product_PlanningSchema schema = newInstance(I_M_Product_PlanningSchema.class);
		schema.setDD_NetworkDistribution(nwDist);
		schema.setM_Warehouse(wh);
		schema.setM_ProductPlanningSchema_Selector(selector);
		save(schema);
		return schema;

	}

	private I_M_Product createProduct(final String name, final String selector)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setM_ProductPlanningSchema_Selector(selector);
		save(product);
		return product;
	}
}
