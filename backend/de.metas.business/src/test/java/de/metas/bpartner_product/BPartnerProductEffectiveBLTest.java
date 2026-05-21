/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.bpartner_product;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class BPartnerProductEffectiveBLTest
{
	private BPartnerProductEffectiveBL bpartnerProductEffectiveBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		bpartnerProductEffectiveBL = new BPartnerProductEffectiveBL(BPartnerEffectiveBL.newInstanceForUnitTesting());
	}

	@Test
	public void getPurchaseTransportDays_bpartnerProductHasValue_returnsBPartnerProductValue()
	{
		final BPartnerId vendorId = createVendor(3);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setC_BPartner_ID(vendorId.getRepoId());
		bpartnerProduct.setM_Product_ID(42);
		bpartnerProduct.setDeliveryTime_Promised(7);
		saveRecord(bpartnerProduct);

		assertThat(bpartnerProductEffectiveBL.getPurchaseTransportDays(vendorId, ProductId.ofRepoId(42), OrgId.ANY)).isEqualTo(7);
	}

	@Test
	public void getPurchaseTransportDays_bpartnerProductHasNoValue_fallsBackToBPartner()
	{
		final BPartnerId vendorId = createVendor(3);

		final I_C_BPartner_Product bpartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
		bpartnerProduct.setC_BPartner_ID(vendorId.getRepoId());
		bpartnerProduct.setM_Product_ID(42);
		// DeliveryTime_Promised not set → null in DB
		saveRecord(bpartnerProduct);

		assertThat(bpartnerProductEffectiveBL.getPurchaseTransportDays(vendorId, ProductId.ofRepoId(42), OrgId.ANY)).isEqualTo(3);
	}

	@Test
	public void getPurchaseTransportDays_noBPartnerProduct_fallsBackToBPartner()
	{
		final BPartnerId vendorId = createVendor(5);

		// no C_BPartner_Product record at all
		assertThat(bpartnerProductEffectiveBL.getPurchaseTransportDays(vendorId, ProductId.ofRepoId(42), OrgId.ANY)).isEqualTo(5);
	}

	@Test
	public void getPurchaseTransportDays_noBPartnerProductNoBPartnerValue_returns0()
	{
		final BPartnerId vendorId = createVendor(null);

		assertThat(bpartnerProductEffectiveBL.getPurchaseTransportDays(vendorId, ProductId.ofRepoId(42), OrgId.ANY)).isEqualTo(0);
	}

	private BPartnerId createVendor(final Integer poTransportDays)
	{
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);

		final I_C_BPartner vendor = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		vendor.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		if (poTransportDays != null)
		{
			vendor.setPO_TransportDays(poTransportDays);
		}
		saveRecord(vendor);
		return BPartnerId.ofRepoId(vendor.getC_BPartner_ID());
	}
}
