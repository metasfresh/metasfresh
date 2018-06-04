package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import de.metas.product.ProductId;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class VendorProductInfoTest
{
	private I_M_Product product;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("testUOMSympol");
		save(uom);

		product = newInstance(I_M_Product.class);
		product.setName("testProductName");
		product.setValue("testProductValue");
		product.setC_UOM(uom);
		save(product);
	}

	@Test
	public void fromDataRecord_without_custom_productName_and_productNo()
	{
		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		save(bPartner);

		final I_C_BPartner_Product bPartnerProduct = newInstance(I_C_BPartner_Product.class);
		bPartnerProduct.setC_BPartner(bPartner);
		bPartnerProduct.setM_Product(product);
		save(bPartnerProduct);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.fromDataRecord(bPartnerProduct);
		assertThat(vendorProductInfo.getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(vendorProductInfo.getProductNo()).isEqualTo("testProductValue");
		assertThat(vendorProductInfo.getProductName()).isEqualTo("testProductName");
	}

	@Test
	public void fromDataRecord_with_custom_productName_and_productNo()
	{
		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		save(bPartner);

		final I_C_BPartner_Product bPartnerProduct = newInstance(I_C_BPartner_Product.class);
		bPartnerProduct.setC_BPartner(bPartner);
		bPartnerProduct.setM_Product(product);
		bPartnerProduct.setVendorProductNo("bPartnerProduct.VendorProductNo");
		bPartnerProduct.setProductName("bPartnerProduct.roductName");
		save(bPartnerProduct);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.fromDataRecord(bPartnerProduct);
		assertThat(vendorProductInfo.getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
		assertThat(vendorProductInfo.getProductNo()).isEqualTo("bPartnerProduct.VendorProductNo");
		assertThat(vendorProductInfo.getProductName()).isEqualTo("bPartnerProduct.roductName");
	}
}
