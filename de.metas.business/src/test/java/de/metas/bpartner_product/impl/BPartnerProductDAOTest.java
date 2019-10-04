package de.metas.bpartner_product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

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

	private OrgId org0;
	private OrgId org1;
	private OrgId org2;
	private I_C_BPartner partner1;
	private I_M_Product product1;
	private I_C_BPartner_Product bpProduct0;
	private I_C_BPartner_Product bpProduct1;

	private IBPartnerProductDAO bpartnerProductsRepo;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerProductsRepo = Services.get(IBPartnerProductDAO.class);
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
		partner1 = createPartner("Partner1", org0);
		product1 = createProduct("Product1", org1);
		bpProduct0 = createBPProduct(partner1, product1, OrgId.ANY);
		bpProduct1 = createBPProduct(partner1, product1, org1);

	}

	private I_C_BPartner_Product createBPProduct(
			@NonNull final I_C_BPartner partner,
			@NonNull final I_M_Product product,
			@NonNull final OrgId orgId)
	{
		final I_C_BPartner_Product bpProduct = newInstance(I_C_BPartner_Product.class);

		bpProduct.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpProduct.setM_Product_ID(product.getM_Product_ID());
		bpProduct.setAD_Org_ID(orgId.getRepoId());
		bpProduct.setUsedForVendor(true);

		bpProduct.setUsedForCustomer(true);
		bpProduct.setC_BPartner_Vendor_ID(partner.getC_BPartner_ID());

		saveRecord(bpProduct);

		return bpProduct;
	}

	private OrgId createOrg(final String name)
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		org.setName(name);
		saveRecord(org);
		return OrgId.ofRepoId(org.getAD_Org_ID());
	}

	private I_C_BPartner createPartner(final String name, final OrgId orgId)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		partner.setAD_Org_ID(orgId.getRepoId());
		saveRecord(partner);
		return partner;
	}

	private I_M_Product createProduct(final String name, final OrgId orgId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName(name);
		product.setAD_Org_ID(orgId.getRepoId());
		saveRecord(product);
		return product;
	}

	private void test_BPartnerProductAssociation(final I_C_BPartner_Product bpProductexpected,
			// input :
			final I_C_BPartner partnerInput,
			final I_M_Product productInput,
			final OrgId orgId)
	{

		final I_C_BPartner_Product bpProductActual = bpartnerProductsRepo.retrieveBPartnerProductAssociation(partnerInput, productInput, orgId);

		final String errmsg = "Invalid C_BPartner_product entry retrieved for"
				+ "\n Partner " + partnerInput.getName()
				+ "\n Product " + productInput.getName()
				+ "\n Org " + orgId;

		Assert.assertEquals(errmsg, bpProductexpected.getC_BPartner_Product_ID(), bpProductActual.getC_BPartner_Product_ID());
	}

	private void test_BPartnerForProduct(final I_C_BPartner_Product bpProductexpected,
			// input :
			final I_C_BPartner partnerInput,
			final I_M_Product productInput,
			final OrgId orgId)
	{
		final List<I_C_BPartner_Product> bpProductActual = bpartnerProductsRepo.retrieveBPartnerForProduct(
				Env.getCtx(),
				BPartnerId.ofRepoId(partnerInput.getC_BPartner_ID()),
				ProductId.ofRepoId(productInput.getM_Product_ID()),
				orgId);

		final String errmsg = "Invalid C_BPartner_product entry retrieved for"
				+ "\n Partner " + partnerInput.getName()
				+ "\n Product " + productInput.getName()
				+ "\n Org " + orgId;

		Assert.assertTrue(errmsg, bpProductActual.contains(bpProductexpected));
	}

	private void test_BPProductForCustomer(final I_C_BPartner_Product bpProductexpected,
			// input :
			final I_C_BPartner partnerInput,
			final I_M_Product productInput,
			final OrgId orgId)
	{
		final I_C_BPartner_Product bpProductActual = bpartnerProductsRepo.retrieveBPProductForCustomer(
				partnerInput,
				productInput,
				orgId);

		final String errmsg = "Invalid C_BPartner_product entry retrieved for"
				+ "\n Partner " + partnerInput.getName()
				+ "\n Product " + productInput.getName()
				+ "\n Org " + orgId;

		Assert.assertEquals(errmsg, bpProductexpected.getC_BPartner_Product_ID(), bpProductActual.getC_BPartner_Product_ID());

	}

	@Test
	public void test_getProductIdByCustomerProductNo()
	{
		final I_C_BPartner partnerRecord = createPartner("Partner1", OrgId.ANY);
		final BPartnerId partnerId = BPartnerId.ofRepoId(partnerRecord.getC_BPartner_ID());

		final I_M_Product productRecord = createProduct("Product1", OrgId.ANY);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_C_BPartner_Product record = newInstance(I_C_BPartner_Product.class);
		record.setC_BPartner_ID(partnerId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setUsedForCustomer(true);
		record.setProductNo("customerProductNo");
		saveRecord(record);

		assertThat(bpartnerProductsRepo.getProductIdByCustomerProductNo(partnerId, "dummy")).isNotPresent();

		assertThat(bpartnerProductsRepo.getProductIdByCustomerProductNo(partnerId, "customerProductNo").orElse(null))
				.isEqualTo(productId);
	}
}
