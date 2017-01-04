package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
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
	
	@Test
	public void test_getPMMProductForDateProductAndASI()
	{
		final HUTestHelper huTestHelper = new HUTestHelper(false);
		huTestHelper.setInitAdempiere(false);
		huTestHelper.init();
		
		final Date date = Timestamp.valueOf("2017-01-01 10:10:10.0");

		final I_M_Product product = huTestHelper.pTomato;

		final int productId = product.getM_Product_ID();

		final I_C_UOM productUOM = huTestHelper.uomKg;

		//
		final I_M_HU_PI piTU = huTestHelper.createHUDefinition("TU1", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
		final I_M_HU_PI_Item_Product hupip = huTestHelper.assignProduct(piTU_Item, product, BigDecimal.TEN, productUOM);
		final int hupipId = hupip.getM_HU_PI_Item_Product_ID();

		final I_C_BPartner partner = createPartner("Partner1");
		final int partnerId = partner.getC_BPartner_ID();

		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.newInstance(I_M_AttributeSetInstance.class);
		InterfaceWrapperHelper.save(asi);

		final I_M_Attribute attribute1 = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute1.setValue("Attr1Value");
		InterfaceWrapperHelper.save(attribute1);

		final I_M_AttributeInstance attributeInstance = Services.get(IAttributeSetInstanceBL.class).getCreateAttributeInstance(asi, attribute1.getM_Attribute_ID());
		InterfaceWrapperHelper.save(attributeInstance);

		final int seqNo = 10;

		final I_PMM_Product pmmProductExpected = createPMMProducts(product, partner, hupip, asi, seqNo);

		final I_PMM_Product pmmProductActual = Services.get(IPMMProductBL.class).getPMMProductForDateProductAndASI(
				date,
				productId,
				partnerId,
				hupipId,
				asi);
		
		Assert.assertEquals("pmmProductExpected != pmmProductActual ", pmmProductExpected, pmmProductActual);
	}

	private I_PMM_Product createPMMProducts(I_M_Product product, I_C_BPartner partner, I_M_HU_PI_Item_Product hupip, I_M_AttributeSetInstance asi, int seqNo)
	{
		final I_PMM_Product pmmProduct = InterfaceWrapperHelper.newInstance(I_PMM_Product.class);
		pmmProduct.setM_Product(product);
		pmmProduct.setC_BPartner(partner);
		pmmProduct.setM_HU_PI_Item_Product(hupip);
		pmmProduct.setM_AttributeSetInstance(asi);

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

}
