package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_S_Resource;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.pporder.LiberoException;

public class MRPSegmentsCollectorTest
{
	private IContextAware context;
	private I_AD_Org orgNULL = null;
	private I_AD_Org org1;
	private I_AD_Org org2;
	private I_AD_Org org3;
	private I_AD_Org org4;
	private I_S_Resource plantNULL = null;
	private I_S_Resource plant1;
	private I_S_Resource plant2;
	private I_S_Resource plant3;
	private I_S_Resource plant4;
	private I_M_Warehouse warehouseNULL = null;
	private I_M_Warehouse warehouse1;
	private I_M_Warehouse warehouse2;
	private I_M_Warehouse warehouse3;
	private I_M_Warehouse warehouse4;
	private I_M_Product productNULL = null;
	private I_M_Product product1;
	private I_M_Product product2;
	private I_M_Product product3;
	private I_M_Product product4;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		this.context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);
		final I_AD_Client client = InterfaceWrapperHelper.newInstance(I_AD_Client.class, context);
		InterfaceWrapperHelper.save(client);
		Env.setContext(context.getCtx(), "#AD_Client_ID", client.getAD_Client_ID());

		// Master data
		this.org1 = createOrg("Org1");
		this.org2 = createOrg("Org2");
		this.org3 = createOrg("Org3");
		this.org4 = createOrg("Org4");
		this.plant1 = createPlant("Plant1");
		this.plant2 = createPlant("Plant2");
		this.plant3 = createPlant("Plant3");
		this.plant4 = createPlant("Plant4");
		this.warehouse1 = createWarehouse("Warehouse1");
		this.warehouse2 = createWarehouse("Warehouse2");
		this.warehouse3 = createWarehouse("Warehouse3");
		this.warehouse4 = createWarehouse("Warehouse4");
		this.productNULL = null;
		this.product1 = createProduct("Product1");
		this.product2 = createProduct("Product2");
		this.product3 = createProduct("Product3");
		this.product4 = createProduct("Product4");
	}

	@Test(expected = LiberoException.class)
	public void test_AddingNotFullyDefinedSegment()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		final MRPSegment segment = createMRPSegment(org1, warehouse1, plantNULL, productNULL);

		// expect exception because segment is not fully defined
		collector.addSegment(segment);
	}

	@Test
	public void test_AddingDifferentSegments()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();
		final List<IMRPSegment> segmentsExpected = new ArrayList<IMRPSegment>();

		for (final I_AD_Org org : Arrays.asList(org1, org2, org3, org4))
		{
			for (final I_S_Resource plant : Arrays.asList(plant1, plant2, plant3, plant4))
			{
				for (final I_M_Warehouse warehouse : Arrays.asList(warehouse1, warehouse2, warehouse3, warehouse4))
				{
					final MRPSegment segment = createMRPSegment(org, warehouse, plant, productNULL);
					collector.addSegment(segment);
					segmentsExpected.add(segment);
				}
			}
		}

		Assert.assertEquals("Invalid " + collector,
				segmentsExpected, // expected
				collector.getMRPSegments() // actual
		);

	}

	@Test
	public void test_AddingDuplicateSegments()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		final MRPSegment segment1 = createMRPSegment(org1, warehouse1, plant1, productNULL);
		final MRPSegment segment2 = createMRPSegment(org1, warehouse1, plant1, productNULL);

		collector.addSegment(segment1);
		collector.addSegment(segment2);

		Assert.assertEquals("Invalid " + collector,
				Arrays.asList(segment1), // expected
				collector.getMRPSegments() // actual
		);
	}

	@Test
	public void test_AddingSameSegments()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		final MRPSegment segment1 = createMRPSegment(org1, warehouse1, plant1, productNULL);

		collector.addSegment(segment1);
		collector.addSegment(segment1);

		Assert.assertEquals("Invalid " + collector,
				Arrays.asList(segment1), // expected
				collector.getMRPSegments() // actual
		);
	}

	@Test
	public void test_AddingASegmentWhichIsIncludedInAnExistingSegment()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		final MRPSegment segment1 = createMRPSegment(org1, warehouse1, plant1, productNULL);
		collector.addSegment(segment1);

		//
		// Add segments which are already included in "segment1".
		// => nothing shall happen
		for (final I_M_Product product : Arrays.asList(product1, product2, product3, product4))
		{
			final MRPSegment segment2 = createMRPSegment(org1, warehouse1, plant1, product);
			collector.addSegment(segment2);
			Assert.assertEquals("Invalid " + collector,
					Arrays.asList(segment1), // expected
					collector.getMRPSegments() // actual
			);
		}
	}

	@Test
	public void test_AddingASegmentWhichIncludesExistingSegments()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		final MRPSegment segment1 = createMRPSegment(org1, warehouse1, plant1, product1);
		final MRPSegment segment2 = createMRPSegment(org1, warehouse1, plant1, product2);
		final MRPSegment segment3 = createMRPSegment(org1, warehouse1, plant1, product3);
		final MRPSegment segment4 = createMRPSegment(org1, warehouse1, plant1, product4);
		collector.addSegment(segment1);
		collector.addSegment(segment2);
		collector.addSegment(segment3);
		collector.addSegment(segment4);
		Assert.assertEquals("Invalid " + collector,
				Arrays.asList(segment1, segment2, segment3, segment4), // expected
				collector.getMRPSegments() // actual
		);

		//
		// Add the motherfucker segment which includes all of the above
		final MRPSegment segmentWhichIncludesAllOthers = createMRPSegment(org1, warehouse1, plant1, productNULL);
		collector.addSegment(segmentWhichIncludesAllOthers);
		Assert.assertEquals("Invalid " + collector,
				Arrays.asList(segmentWhichIncludesAllOthers), // expected
				collector.getMRPSegments() // actual
		);
	}

	@Test
	public void test_includes()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		collector.addSegment(createMRPSegment(org1, warehouse1, plant1, productNULL));
		collector.addSegment(createMRPSegment(org1, warehouse2, plant2, productNULL));
		collector.addSegment(createMRPSegment(org1, warehouse3, plant3, productNULL));

		//
		// Test includes
		for (final IMRPSegment mrpSegment : Arrays.asList(
				createMRPSegment(org1, warehouse1, plant1, productNULL),
				createMRPSegment(org1, warehouse2, plant2, productNULL),
				createMRPSegment(org1, warehouse3, plant3, productNULL),
				createMRPSegment(org1, warehouse1, plant1, product1),
				createMRPSegment(org1, warehouse2, plant2, product1),
				createMRPSegment(org1, warehouse3, plant3, product1)
				))
		{
			Assert.assertEquals("Invalid includes answer for " + mrpSegment, true, collector.includes(mrpSegment));
		}

		//
		// Test NOT includes
		for (final IMRPSegment mrpSegment : Arrays.asList(
				createMRPSegment(org1, warehouse1, plant2, productNULL),
				createMRPSegment(org1, warehouse2, plant3, productNULL),
				createMRPSegment(org1, warehouse3, plant1, productNULL),
				createMRPSegment(org1, warehouse2, plant1, product1),
				createMRPSegment(org1, warehouse3, plant2, product1),
				createMRPSegment(org1, warehouse1, plant3, product1)
				))
		{
			Assert.assertEquals("Invalid includes answer for " + mrpSegment, false, collector.includes(mrpSegment));
		}

	}

	@Test
	public void test_hasSegmentsIncludedIn()
	{
		final MRPSegmentsCollector collector = new MRPSegmentsCollector();

		//
		// Add initial segments
		collector.addSegments(Arrays.<IMRPSegment> asList(
				createMRPSegment(org1, warehouse1, plant1, productNULL),
				createMRPSegment(org1, warehouse2, plant2, productNULL),
				createMRPSegment(org1, warehouse3, plant3, productNULL),
				createMRPSegment(org1, warehouse4, plant4, product4)
				));

		//
		// Test "hasSegmentsIncludedIn"
		for (final IMRPSegment parentSegment : Arrays.asList(
				createMRPSegment(org1, warehouse1, plant1, productNULL),
				createMRPSegment(org1, warehouse2, plant2, productNULL),
				createMRPSegment(org1, warehouse3, plant3, productNULL),
				createMRPSegment(org1, warehouse4, plant4, product4),
				//
				createMRPSegment(org1, warehouse4, plant4, productNULL),
				createMRPSegment(org1, warehouse2, plantNULL, productNULL),
				createMRPSegment(org1, warehouse4, plantNULL, product4),
				createMRPSegment(org1, warehouseNULL, plantNULL, productNULL),
				createMRPSegment(orgNULL, warehouseNULL, plantNULL, productNULL),
				createMRPSegment(orgNULL, warehouseNULL, plantNULL, product4)
				))
		{
			Assert.assertEquals("Invalid hasSegmentsIncludedIn() answer for " + parentSegment, true, collector.hasSegmentsIncludedIn(parentSegment));
		}

		//
		// Test NOT "hasSegmentsIncludedIn"
		for (final IMRPSegment parentSegment : Arrays.asList(
				createMRPSegment(org1, warehouse1, plant2, productNULL),
				createMRPSegment(org1, warehouse2, plant3, productNULL),
				createMRPSegment(org1, warehouse3, plant1, productNULL),
				createMRPSegment(org1, warehouse2, plant1, product1),
				createMRPSegment(org1, warehouse3, plant2, product1),
				createMRPSegment(org1, warehouse1, plant3, product1),
				createMRPSegment(orgNULL, warehouseNULL, plantNULL, product3)
				))
		{
			Assert.assertEquals("Invalid includes answer for " + parentSegment, false, collector.hasSegmentsIncludedIn(parentSegment));
		}

	}

	private MRPSegment createMRPSegment(I_AD_Org adOrg, I_M_Warehouse warehouse, I_S_Resource plant, I_M_Product product)
	{
		final int adClientId = Env.getAD_Client_ID(context.getCtx());
		return new MRPSegment(adClientId, adOrg, warehouse, plant, product);
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

	private final I_M_Warehouse createWarehouse(final String name)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, context);
		warehouse.setValue(name);
		warehouse.setName(name);
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

}
