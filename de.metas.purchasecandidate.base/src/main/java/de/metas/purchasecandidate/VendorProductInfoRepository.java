package de.metas.purchasecandidate;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.BPartnerType;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.service.OrgId;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.lang.Percent;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.purchasing.api.IBPartnerProductDAO;
import lombok.NonNull;

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

@Repository
public class VendorProductInfoRepository
{
	private final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	public List<VendorProductInfo> getVendorProductInfos(@NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final ProductAndCategoryId productAndCategoryId = productsRepo.retrieveProductAndCategoryIdByProductId(productId);

		final Map<BPartnerId, Integer> discountSchemaIds = bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(BPartnerType.VENDOR);
		final Map<BPartnerId, I_C_BPartner_Product> bpartnerProductRecords = partnerProductDAO.retrieveByVendorIds(discountSchemaIds.keySet(), productId, orgId);

		final ImmutableList.Builder<VendorProductInfo> vendorProductInfos = ImmutableList.builder();
		for (final Map.Entry<BPartnerId, Integer> entry : discountSchemaIds.entrySet())
		{
			final BPartnerId vendorId = entry.getKey();
			final PricingConditionsId pricingConditionsId = PricingConditionsId.ofDiscountSchemaId(entry.getValue());

			final I_C_BPartner_Product bpartnerProductRecord = bpartnerProductRecords.get(vendorId);

			final VendorProductInfo vendorProductInfo = createVendorProductInfo(vendorId, productAndCategoryId, pricingConditionsId, bpartnerProductRecord);
			vendorProductInfos.add(vendorProductInfo);
		}

		return vendorProductInfos.build();
	}

	public VendorProductInfo getVendorProductInfo(@NonNull final BPartnerId vendorId, @NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final ProductAndCategoryId productAndCategoryId = productsRepo.retrieveProductAndCategoryIdByProductId(productId);

		final int discountSchemaId = bpartnerBL.getDiscountSchemaId(vendorId, BPartnerType.VENDOR.getSOTrx());
		final PricingConditionsId pricingConditionsId = PricingConditionsId.ofDiscountSchemaId(discountSchemaId);

		final I_C_BPartner_Product bpartnerProductRecord = partnerProductDAO.retrieveByVendorId(vendorId, productId, orgId);

		return createVendorProductInfo(vendorId, productAndCategoryId, pricingConditionsId, bpartnerProductRecord);
	}

	private VendorProductInfo createVendorProductInfo(
			@NonNull final BPartnerId vendorId,
			@NonNull final ProductAndCategoryId productAndCategoryId,
			@NonNull final PricingConditionsId pricingConditionsId,
			@Nullable final I_C_BPartner_Product bpartnerProductRecord)
	{
		final I_C_BPartner vendorRecord = bpartnersRepo.getById(vendorId);
		final boolean aggregatePOs = vendorRecord.isAggregatePO();
		final Percent vendorFlatDiscount = Percent.of(vendorRecord.getFlatDiscount());

		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(pricingConditionsId);

		final ProductId productId = productAndCategoryId.getProductId();
		final String vendorProductNo = Util.coalesceSuppliers(
				() -> bpartnerProductRecord != null ? bpartnerProductRecord.getVendorProductNo() : null,
				() -> bpartnerProductRecord != null ? bpartnerProductRecord.getProductNo() : null,
				() -> productBL.getProductValue(productId));

		final String vendorProductName = Util.coalesceSuppliers(
				() -> bpartnerProductRecord != null ? bpartnerProductRecord.getProductName() : null,
				() -> productBL.getProductName(productId));

		return VendorProductInfo.builder()
				.vendorId(vendorId)
				.productAndCategoryId(productAndCategoryId)
				.vendorProductNo(vendorProductNo)
				.vendorProductName(vendorProductName)
				.aggregatePOs(aggregatePOs)
				.vendorFlatDiscount(vendorFlatDiscount)
				.pricingConditions(pricingConditions)
				.build();
	}
}
