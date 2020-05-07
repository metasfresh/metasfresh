package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PMMProductBLTest
{
	private I_M_Warehouse warehouse;
	private Date date;
	private I_M_Product product1;

	/**
	 * for testing simplification I will use the same uom for all the products
	 */
	private I_C_UOM productUOM;

	private I_M_HU_PI_Item_Product hupip1;

	private I_C_BPartner partner1;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		warehouse = createWarehouse("Warehouse1");
		date = Timestamp.valueOf("2017-01-01 10:10:10.0");
		product1 = createProduct("Product1");

		partner1 = createPartner("Partner1");

		productUOM = createUOM();

		hupip1 = createHUPIP("TU1", product1, BigDecimal.TEN, productUOM);
	}

	@Test
	public void test_getPMMProductForDateProductAndASI_1Attr_SameValue()
	{

		final Timestamp validFrom1 = Timestamp.valueOf("2016-01-01 10:10:10.0");
		final Timestamp validTo1 = Timestamp.valueOf("2018-01-01 10:10:10.0");

		final I_M_AttributeSetInstance asi1 = createASI();
		final I_M_AttributeSetInstance asi2 = createASI();

		final I_M_Attribute attribute1 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute1);

		final I_M_AttributeInstance attributeInstance1 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute1.getM_Attribute_ID());
		attributeInstance1.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance1);

		final I_M_AttributeInstance attributeInstance2 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi2, attribute1.getM_Attribute_ID());
		attributeInstance2.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance2);

		final int seqNo = 10;

		final I_PMM_Product pmmProductExpected = createPMMProduct(
				product1,
				partner1,
				hupip1,
				asi1,
				seqNo,
				warehouse,
				validFrom1,
				validTo1);

		final I_PMM_Product pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				product1.getM_Product_ID(),
				partner1.getC_BPartner_ID(),
				hupip1.getM_HU_PI_Item_Product_ID(),
				asi2);

		Assert.assertEquals("pmmProductExpected != pmmProductActual ", pmmProductExpected, pmmProductActual);
	}

	@Test
	public void test_getPMMProductForDateProductAndASI_1Attr_DifferentValue()
	{

		final Timestamp validFrom1 = Timestamp.valueOf("2016-01-01 10:10:10.0");
		final Timestamp validTo1 = Timestamp.valueOf("2018-01-01 10:10:10.0");

		final I_M_AttributeSetInstance asi1 = createASI();
		final I_M_AttributeSetInstance asi2 = createASI();

		final I_M_Attribute attribute1 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute1);

		final I_M_AttributeInstance attributeInstance1 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute1.getM_Attribute_ID());
		attributeInstance1.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance1);

		final I_M_AttributeInstance attributeInstance2 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi2, attribute1.getM_Attribute_ID());
		attributeInstance2.setValue("Attr2Value");
		InterfaceWrapperHelper.save(attributeInstance2);

		final int seqNo = 10;

		// make sure there is a pmm in the db, to prove that it is not selected if it has another attribute value
		createPMMProduct(
				product1,
				partner1,
				hupip1,
				asi1,
				seqNo,
				warehouse,
				validFrom1,
				validTo1);

		final I_PMM_Product pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				product1.getM_Product_ID(),
				partner1.getC_BPartner_ID(),
				hupip1.getM_HU_PI_Item_Product_ID(),
				asi2);

		Assert.assertNull(pmmProductActual);
	}

	@Test
	public void test_getPMMProductForDateProductAndASI_2AttrASI_1AttrPMM()
	{

		final Timestamp validFrom1 = Timestamp.valueOf("2016-01-01 10:10:10.0");
		final Timestamp validTo1 = Timestamp.valueOf("2018-01-01 10:10:10.0");

		final I_M_AttributeSetInstance asi1 = createASI();
		final I_M_AttributeSetInstance asi2 = createASI();

		final I_M_Attribute attribute1 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute1);

		final I_M_Attribute attribute2 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute2);

		final I_M_AttributeInstance attributeInstance1 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute1.getM_Attribute_ID());
		attributeInstance1.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance1);

		final I_M_AttributeInstance attributeInstance2 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi2, attribute1.getM_Attribute_ID());
		attributeInstance2.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance2);

		final I_M_AttributeInstance attributeInstance3 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute2.getM_Attribute_ID());
		attributeInstance3.setValue("Attr2Value");
		InterfaceWrapperHelper.save(attributeInstance3);

		final int seqNo = 10;

		final I_PMM_Product pmmProductExpected = createPMMProduct(
				product1,
				partner1,
				hupip1,
				asi2,
				seqNo,
				warehouse,
				validFrom1,
				validTo1);

		final I_PMM_Product pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				product1.getM_Product_ID(),
				partner1.getC_BPartner_ID(),
				hupip1.getM_HU_PI_Item_Product_ID(),
				asi1);

		Assert.assertEquals("pmmProductExpected != pmmProductActual ", pmmProductExpected, pmmProductActual);
	}

	@Test
	public void test_getPMMProductForDateProductAndASI_1AttrASI_2AttrPMM()
	{

		final Timestamp validFrom1 = Timestamp.valueOf("2016-01-01 10:10:10.0");
		final Timestamp validTo1 = Timestamp.valueOf("2018-01-01 10:10:10.0");

		final I_M_AttributeSetInstance asi1 = createASI();
		final I_M_AttributeSetInstance asi2 = createASI();

		final I_M_Attribute attribute1 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute1);

		final I_M_Attribute attribute2 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute2);

		final I_M_AttributeInstance attributeInstance1 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute1.getM_Attribute_ID());
		attributeInstance1.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance1);

		final I_M_AttributeInstance attributeInstance2 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi2, attribute1.getM_Attribute_ID());
		attributeInstance2.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance2);

		final I_M_AttributeInstance attributeInstance3 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute2.getM_Attribute_ID());
		attributeInstance3.setValue("Attr2Value");
		InterfaceWrapperHelper.save(attributeInstance3);

		final int seqNo = 10;

		createPMMProduct(
				product1,
				partner1,
				hupip1,
				asi1,
				seqNo,
				warehouse,
				validFrom1,
				validTo1);

		final I_PMM_Product pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				product1.getM_Product_ID(),
				partner1.getC_BPartner_ID(),
				hupip1.getM_HU_PI_Item_Product_ID(),
				asi2);

		Assert.assertNull(pmmProductActual);
	}

	@Test
	public void test_getPMMProductForDateProductAndASI_2PMM_Seq()
	{

		final Timestamp validFrom1 = Timestamp.valueOf("2016-01-01 10:10:10.0");
		final Timestamp validTo1 = Timestamp.valueOf("2018-01-01 10:10:10.0");

		final I_M_AttributeSetInstance asi1 = createASI();
		final I_M_AttributeSetInstance asi2 = createASI();
		final I_M_AttributeSetInstance asi3 = createASI();

		final I_M_Attribute attribute1 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute1);

		final I_M_Attribute attribute2 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		InterfaceWrapperHelper.save(attribute2);

		final I_M_AttributeInstance attributeInstance1 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute1.getM_Attribute_ID());
		attributeInstance1.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance1);
		final I_M_AttributeInstance attributeInstance3 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi1, attribute2.getM_Attribute_ID());
		attributeInstance3.setValue("Attr2Value");
		InterfaceWrapperHelper.save(attributeInstance3);

		final I_M_AttributeInstance attributeInstance2 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi2, attribute1.getM_Attribute_ID());
		attributeInstance2.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attributeInstance2);

		final I_M_AttributeInstance attributeInstance4 = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi3, attribute2.getM_Attribute_ID());
		attributeInstance4.setValue("Attr2Value");
		InterfaceWrapperHelper.save(attributeInstance4);

		int seqNo = 10;
		final I_PMM_Product pmmProduct1 = createPMMProduct(
				product1,
				partner1,
				hupip1,
				asi2,
				seqNo,
				warehouse,
				validFrom1,
				validTo1);

		seqNo = 20;
		final I_PMM_Product pmmProduct2 = createPMMProduct(
				product1,
				partner1,
				hupip1,
				asi3,
				seqNo,
				warehouse,
				validFrom1,
				validTo1);

		I_PMM_Product pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				product1.getM_Product_ID(),
				partner1.getC_BPartner_ID(),
				hupip1.getM_HU_PI_Item_Product_ID(),
				asi1);

		Assert.assertEquals("pmmProductExpected != pmmProductActual ", pmmProduct1, pmmProductActual);

		// change seqNO
		pmmProduct1.setSeqNo(20);
		InterfaceWrapperHelper.save(pmmProduct1);
		
		pmmProduct2.setSeqNo(10);
		InterfaceWrapperHelper.save(pmmProduct2);
		
		pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				product1.getM_Product_ID(),
				partner1.getC_BPartner_ID(),
				hupip1.getM_HU_PI_Item_Product_ID(),
				asi1);

		Assert.assertEquals("pmmProductExpected != pmmProductActual ", pmmProduct2, pmmProductActual);
	}

	private I_M_HU_PI_Item_Product createHUPIP(
			final String tuName,
			final I_M_Product product,
			final BigDecimal capacity,
			final I_C_UOM productUOM)
	{
		final I_M_HU_PI piTU = createHUDefinition(tuName, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final I_M_HU_PI_Item piTU_Item = createHU_PI_Item_Material(piTU);
		final I_M_HU_PI_Item_Product hupip = assignProduct(piTU_Item, product, capacity, productUOM, null);

		return hupip;

	}

	private I_M_AttributeSetInstance createASI()
	{
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);

		return asi;
	}

	private I_M_Warehouse createWarehouse(String name)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);

		warehouse.setName(name);

		InterfaceWrapperHelper.save(warehouse);

		return warehouse;
	}

	private I_PMM_Product createPMMProduct(
			final I_M_Product product,
			final I_C_BPartner partner,
			final I_M_HU_PI_Item_Product hupip,
			final I_M_AttributeSetInstance asi,
			final int seqNo,
			final I_M_Warehouse warehouse,
			final Timestamp validFrom,
			final Timestamp validTo)
	{
		final I_PMM_Product pmmProduct = InterfaceWrapperHelper.newInstance(I_PMM_Product.class);
		pmmProduct.setM_Product(product);
		pmmProduct.setC_BPartner(partner);
		pmmProduct.setM_HU_PI_Item_Product(hupip);
		pmmProduct.setM_AttributeSetInstance(asi);
		pmmProduct.setM_Warehouse(warehouse);
		pmmProduct.setValidFrom(validFrom);
		pmmProduct.setValidTo(validTo);
		InterfaceWrapperHelper.save(pmmProduct);

		return pmmProduct;
	}

	private I_C_BPartner createPartner(String value)
	{
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);

		partner.setValue(value);
		partner.setName(value);
		InterfaceWrapperHelper.save(partner);

		return partner;

	}

	private I_M_Product createProduct(final String value)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(value);
		product.setName(value);

		InterfaceWrapperHelper.save(product);

		return product;
	}

	public I_M_HU_PI createHUDefinition(final String name, final String huUnitType)
	{
		final I_M_HU_PI hu = InterfaceWrapperHelper.newInstance(I_M_HU_PI.class);
		hu.setName(name);
		InterfaceWrapperHelper.save(hu);

		// // Create some several dummy versions
		// createVersion(hu, false);
		// createVersion(hu, false);

		// Create the current version
		final Integer huPIVersionId = null;
		createVersion(hu, true, huUnitType, huPIVersionId);

		return hu;
	}

	public I_M_HU_PI_Version createVersion(final I_M_HU_PI handlingUnit, final boolean current)
	{
		final String huUnitType = null;
		final Integer huPIVersionId = null;
		return createVersion(handlingUnit, current, huUnitType, huPIVersionId);
	}

	private I_M_HU_PI_Version createVersion(final I_M_HU_PI pi, final boolean current, final String huUnitType, final Integer huPIVersionId)
	{
		final I_M_HU_PI_Version version = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		version.setName(pi.getName());
		version.setM_HU_PI(pi);
		version.setIsCurrent(current);
		if (huUnitType != null)
		{
			version.setHU_UnitType(huUnitType);
		}
		if (huPIVersionId != null)
		{
			version.setM_HU_PI_Version_ID(huPIVersionId);
		}
		InterfaceWrapperHelper.save(version);
		return version;
	}

	public I_C_UOM createUOM()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setName("Kg");
		uom.setUOMType(X_C_UOM.UOMTYPE_Weigth);

		uom.setStdPrecision(3);
		uom.setCostingPrecision(0);

		uom.setX12DE355("KGM");

		InterfaceWrapperHelper.save(uom);

		return uom;
	}

	public I_M_HU_PI_Item createHU_PI_Item_Material(final I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(pi);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, version);
		piItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		piItem.setM_HU_PI_Version(version);

		InterfaceWrapperHelper.save(piItem);
		return piItem;
	}

	public I_M_HU_PI_Item_Product assignProduct(final I_M_HU_PI_Item itemPI, final I_M_Product product, final BigDecimal qty, final I_C_UOM uom, final I_C_BPartner bpartner)
	{
		final I_M_HU_PI_Item_Product itemDefProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class, itemPI);
		itemDefProduct.setM_HU_PI_Item(itemPI);
		itemDefProduct.setM_Product(product);
		itemDefProduct.setQty(qty);
		itemDefProduct.setC_UOM(uom);
		itemDefProduct.setValidFrom(TimeUtil.getDay(1970, 1, 1));
		itemDefProduct.setC_BPartner(bpartner);
		InterfaceWrapperHelper.save(itemDefProduct);
		return itemDefProduct;
	}

}
