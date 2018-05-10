package de.metas.purchasecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.OptionalInt;

import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Util;

import lombok.Builder;
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
	OptionalInt bpartnerProductId;

	int vendorBPartnerId;

	int productId;
	String productNo;
	String productName;

	public static VendorProductInfo fromDataRecord(@NonNull final I_C_BPartner_Product bpartnerProduct)
	{
		return fromDataRecord(bpartnerProduct, -1);
	}

	public static VendorProductInfo fromDataRecord(@NonNull final I_C_BPartner_Product bpartnerProduct, final int bpartnerVendorIdOverride)
	{
		final de.metas.interfaces.I_C_BPartner_Product extendedBPartnerProduct = create(
				bpartnerProduct,
				de.metas.interfaces.I_C_BPartner_Product.class);

		final String productNo = Util.coalesceSuppliers(
				() -> extendedBPartnerProduct.getVendorProductNo(),
				() -> extendedBPartnerProduct.getProductNo(),
				() -> bpartnerProduct.getM_Product().getValue());

		final String productName = Util.coalesceSuppliers(
				() -> extendedBPartnerProduct.getProductName(),
				() -> bpartnerProduct.getM_Product().getName());

		final int bpartnerVendorId = Util.firstGreaterThanZero(
				bpartnerVendorIdOverride,
				bpartnerProduct.getC_BPartner_Vendor_ID(),
				bpartnerProduct.getC_BPartner_ID());

		return builder()
				.bpartnerProductId(bpartnerProduct.getC_BPartner_Product_ID())
				.vendorBPartnerId(bpartnerVendorId)
				.productId(bpartnerProduct.getM_Product_ID())
				.productNo(productNo)
				.productName(productName)
				.build();
	}

	@Builder
	private VendorProductInfo(
			final int bpartnerProductId,
			final int vendorBPartnerId,
			final int productId,
			@NonNull final String productNo,
			@NonNull final String productName)
	{
		Check.assume(vendorBPartnerId > 0, "vendorBPartnerId > 0");
		Check.assume(productId > 0, "productId > 0");

		this.bpartnerProductId = bpartnerProductId > 0 ? OptionalInt.of(bpartnerProductId) : OptionalInt.empty();
		this.vendorBPartnerId = vendorBPartnerId;
		this.productId = productId;
		this.productNo = productNo;
		this.productName = productName;
	}

}
