package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Util;

import com.google.common.annotations.VisibleForTesting;

// import de.metas.interfaces.I_C_BPartner_Product;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class VendorProductInfo
{
	int bPartnerProductId;
	int vendorBPartnerId;
	int M_Product_ID;

	String productNo;

	String productName;

	public static VendorProductInfo fromDataRecord(@NonNull final I_C_BPartner_Product bPartnerProduct)
	{
		final de.metas.interfaces.I_C_BPartner_Product extendedBPartnerProduct = create(
				bPartnerProduct,
				de.metas.interfaces.I_C_BPartner_Product.class);

		final String productNo = Util.coalesceSuppliers(
				() -> extendedBPartnerProduct.getVendorProductNo(),
				() -> extendedBPartnerProduct.getProductNo(),
				() -> bPartnerProduct.getM_Product().getValue());

		final String productName = Util.coalesceSuppliers(
				() -> extendedBPartnerProduct.getProductName(),
				() -> bPartnerProduct.getM_Product().getName());

		final int bPartnerVendorId = Util.firstGreaterThanZero(
				bPartnerProduct.getC_BPartner_Vendor_ID(),
				bPartnerProduct.getC_BPartner_ID());

		return new VendorProductInfo(
				bPartnerProduct.getC_BPartner_Product_ID(),
				bPartnerVendorId,
				bPartnerProduct.getM_Product_ID(),
				productNo,
				productName);
	}

	@VisibleForTesting
	VendorProductInfo(
			int bPartnerProductId,
			int vendorBPartnerId,
			int m_Product_ID,
			@NonNull String productNo,
			@NonNull String productName)
	{
		Check.assume(bPartnerProductId > 0, "bPartnerProductId > 0");
		Check.assume(vendorBPartnerId > 0, "vendorBPartnerId > 0");
		Check.assume(m_Product_ID > 0, "m_Product_ID > 0");

		this.bPartnerProductId = bPartnerProductId;
		this.vendorBPartnerId = vendorBPartnerId;
		this.M_Product_ID = m_Product_ID;
		this.productNo = productNo;
		this.productName = productName;
	}

}
