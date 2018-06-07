package de.metas.purchasecandidate;

import org.adempiere.bpartner.BPartnerId;

import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import lombok.Builder;
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
	BPartnerId vendorId;

	ProductAndCategoryId productAndCategoryId;
	String vendorProductNo;
	String vendorProductName;

	boolean aggregatePOs;

	private PricingConditions pricingConditions;

	// public static VendorProductInfo fromDataRecord(@NonNull final I_C_BPartner_Product bpartnerProductRecord)
	// {
	// return builderFromDataRecord()
	// .bpartnerProductRecord(bpartnerProductRecord)
	// .build();
	// }
	//
	// @Builder(builderMethodName = "builderFromDataRecord", builderClassName = "FromDataRecordBuilder")
	// public static VendorProductInfo buildFromDataRecord(
	// final I_C_BPartner_Product bpartnerProductRecord,
	// final ProductId productIdOverride,
	// final BPartnerId bpartnerVendorIdOverride,
	// final Boolean aggregatePOsOverride)
	// {
	// final ProductId productId = Util.coalesceSuppliers(
	// () -> productIdOverride,
	// () -> bpartnerProductRecord != null ? ProductId.ofRepoId(bpartnerProductRecord.getM_Product_ID()) : null);
	// if (productId == null)
	// {
	// throw new AdempiereException("Cannot extract ProductId from bpartnerProductRecord=" + bpartnerProductRecord + ", productIdOverride=" + productIdOverride);
	// }
	//
	// final String productNo = Util.coalesceSuppliers(
	// () -> bpartnerProductRecord != null ? bpartnerProductRecord.getVendorProductNo() : null,
	// () -> bpartnerProductRecord != null ? bpartnerProductRecord.getProductNo() : null,
	// () -> Services.get(IProductBL.class).getProductValue(productId));
	//
	// final String productName = Util.coalesceSuppliers(
	// () -> bpartnerProductRecord != null ? bpartnerProductRecord.getProductName() : null,
	// () -> Services.get(IProductBL.class).getProductName(productId));
	//
	// final BPartnerId vendorId = Util.coalesceSuppliers(
	// () -> bpartnerVendorIdOverride,
	// () -> bpartnerProductRecord != null ? BPartnerId.ofRepoIdOrNull(bpartnerProductRecord.getC_BPartner_ID()) : null);
	// if (vendorId == null)
	// {
	// throw new AdempiereException("Cannot extract ProductId from bpartnerProductRecord=" + bpartnerProductRecord + ", bpartnerVendorIdOverride=" + bpartnerVendorIdOverride);
	// }
	//
	// final I_C_BPartner vendorBPartnerRecord = Services.get(IBPartnerDAO.class).getById(vendorId);
	// final PaymentTermId paymentTermId = retrievePaymentTermIdOrNull(vendorBPartnerRecord);
	//
	// final boolean aggregatePOs;
	// if (aggregatePOsOverride != null)
	// {
	// aggregatePOs = aggregatePOsOverride;
	// }
	// else
	// {
	// final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(vendorId);
	// aggregatePOs = bpartner.isAggregatePO();
	// }
	//
	// return builder()
	// .id(bpartnerProductRecord != null ? VendorProductInfoId.ofRepoIdOrNull(bpartnerProductRecord.getC_BPartner_Product_ID()) : null)
	// .vendorId(vendorId)
	// .paymentTermId(paymentTermId)
	// .productId(productId)
	// .productNo(productNo)
	// .productName(productName)
	// .aggregatePOs(aggregatePOs)
	// .build();
	// }
	//
	// private static PaymentTermId retrievePaymentTermIdOrNull(@NonNull final I_C_BPartner bpartnerRecord)
	// {
	// if (bpartnerRecord.getPO_PaymentTerm_ID() > 0)
	// {
	// return PaymentTermId.ofRepoId(bpartnerRecord.getPO_PaymentTerm_ID());
	// }
	//
	// return Services
	// .get(IPaymentTermRepository.class)
	// .getDefaultPaymentTermIdOrNull();
	// }

	@Builder
	private VendorProductInfo(
			@NonNull final BPartnerId vendorId,
			//
			@NonNull final ProductAndCategoryId productAndCategoryId,
			@NonNull final String vendorProductNo,
			@NonNull final String vendorProductName,
			//
			final boolean aggregatePOs,
			//
			@NonNull final PricingConditions pricingConditions)
	{
		this.vendorId = vendorId;

		this.productAndCategoryId = productAndCategoryId;
		this.vendorProductNo = vendorProductNo;
		this.vendorProductName = vendorProductName;

		this.aggregatePOs = aggregatePOs;

		this.pricingConditions = pricingConditions;
	}

	public ProductId getProductId()
	{
		return getProductAndCategoryId().getProductId();
	}

	public PricingConditionsBreak getPricingConditionsBreakOrNull(final PricingConditionsBreakQuery query)
	{
		return getPricingConditions().pickApplyingBreak(query);
	}
}
