package de.metas.purchasing.api.impl;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import de.metas.purchasing.api.IBPartnerProductDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class BPartnerProductDAOTest
{
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private I_AD_Org org0;

	private I_AD_Org org1;

	private I_AD_Org org2;

	private I_C_BPartner partner1;

	private I_M_Product product1;

	private I_C_BPartner_Product bpProduct0;

	private I_C_BPartner_Product bpProduct1;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void BPartnerProductAssociation_Test()
	{

		createData();

		test_BPartnerProductAssociation(bpProduct0, partner1, product1, org0);

		test_BPartnerProductAssociation(bpProduct1, partner1, product1, org1);

		test_BPartnerProductAssociation(bpProduct0, partner1, product1, org2);

		// de.metas.purchasing.api.impl.BPartnerProductDAO.retrieveBPartnerProductAssociation(I_C_BPartner, I_M_Product, I_AD_Org)

	}

	@Test
	public void BPartnerForProduct_Test()
	{
		createData();

		test_BPartnerForProduct(bpProduct0, partner1, product1, org0);
		test_BPartnerForProduct(bpProduct0, partner1, product1, org1);
		test_BPartnerForProduct(bpProduct0, partner1, product1, org2);

		test_BPartnerForProduct(bpProduct1, partner1, product1, org1);

	}

	@Test
	public void BPProductForCustomer_Test()
	{
		createData();

		test_BPProductForCustomer(bpProduct0, partner1, product1, org0);

		test_BPProductForCustomer(bpProduct1, partner1, product1, org1);

		test_BPProductForCustomer(bpProduct0, partner1, product1, org2);
	}

	private void createData()
	{
		org0 = createOrg("Org0");

		org1 = createOrg("Org1");

		org2 = createOrg("Org3");

		partner1 = createParnter("Partner1", org0);

		product1 = createProduct("Product1", org1);

		bpProduct0 = createBPProduct(partner1, product1, 0);

		bpProduct1 = createBPProduct(partner1, product1, org1.getAD_Org_ID());

	}

	private I_C_BPartner_Product createBPProduct(final I_C_BPartner partner, final I_M_Product product, final int adOrgID)
	{
		final I_C_BPartner_Product bpProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);

		bpProduct.setC_BPartner(partner);
		bpProduct.setM_Product(product);
		bpProduct.setAD_Org_ID(adOrgID);
		bpProduct.setUsedForVendor(true);

		bpProduct.setUsedForCustomer(true);
		bpProduct.setC_BPartner_Vendor(partner);

		InterfaceWrapperHelper.save(bpProduct);

		return bpProduct;
	}

	private I_AD_Org createOrg(final String name)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);

		org.setName(name);
		InterfaceWrapperHelper.save(org);

		return org;
	}

	private I_C_BPartner createParnter(final String name, final I_AD_Org org)
	{
		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);

		partner.setName(name);
		partner.setAD_Org(org);

		InterfaceWrapperHelper.save(partner);

		return partner;
	}

	private I_M_Product createProduct(final String name, final I_AD_Org org)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);

		product.setName(name);
		product.setAD_Org(org);

		InterfaceWrapperHelper.save(product);

		return product;
	}

	private void test_BPartnerProductAssociation(final I_C_BPartner_Product bpProductexpected,
			// input :
			final I_C_BPartner partnerInput,
			final I_M_Product productInput,
			final I_AD_Org orgInput)
	{

		final OrgId orgId = OrgId.ofRepoId(orgInput.getAD_Org_ID());
		final I_C_BPartner_Product bpProductActual = Services.get(IBPartnerProductDAO.class).retrieveBPartnerProductAssociation(partnerInput, productInput, orgId);

		final String errmsg = "Invalid C_BPartner_product entry retrieved for"
				+ "\n Partner " + partnerInput.getName()
				+ "\n Product " + productInput.getName()
				+ "\n Org " + orgInput.getName();

		Assert.assertEquals(errmsg, bpProductexpected.getC_BPartner_Product_ID(), bpProductActual.getC_BPartner_Product_ID());
	}

	private void test_BPartnerForProduct(final I_C_BPartner_Product bpProductexpected,
			// input :
			final I_C_BPartner partnerInput,
			final I_M_Product productInput,
			final I_AD_Org orgInput)
	{
		final List<I_C_BPartner_Product> bpProductActual = Services.get(IBPartnerProductDAO.class).retrieveBPartnerForProduct(
				Env.getCtx(),
				BPartnerId.ofRepoId(partnerInput.getC_BPartner_ID()),
				ProductId.ofRepoId(productInput.getM_Product_ID()),
				OrgId.ofRepoId(orgInput.getAD_Org_ID()));

		final String errmsg = "Invalid C_BPartner_product entry retrieved for"
				+ "\n Partner " + partnerInput.getName()
				+ "\n Product " + productInput.getName()
				+ "\n Org " + orgInput.getName();

		Assert.assertTrue(errmsg, bpProductActual.contains(bpProductexpected));
	}

	private void test_BPProductForCustomer(final I_C_BPartner_Product bpProductexpected,
			// input :
			final I_C_BPartner partnerInput,
			final I_M_Product productInput,
			final I_AD_Org orgInput)
	{

		final OrgId orgId = OrgId.ofRepoId(orgInput.getAD_Org_ID());

		final I_C_BPartner_Product bpProductActual = Services.get(IBPartnerProductDAO.class).retrieveBPProductForCustomer(
				partnerInput,
				productInput,
				orgId);

		final String errmsg = "Invalid C_BPartner_product entry retrieved for"
				+ "\n Partner " + partnerInput.getName()
				+ "\n Product " + productInput.getName()
				+ "\n Org " + orgInput.getName();

		Assert.assertEquals(errmsg, bpProductexpected.getC_BPartner_Product_ID(), bpProductActual.getC_BPartner_Product_ID());

	}

}
