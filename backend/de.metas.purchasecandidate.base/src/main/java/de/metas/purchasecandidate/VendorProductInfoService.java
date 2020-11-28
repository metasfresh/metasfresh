package de.metas.purchasecandidate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.Percent;
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

@Service
public class VendorProductInfoService
{
	private final IBPartnerBL bpartnerBL;
	private final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IPricingConditionsRepository pricingConditionsRepo = Services.get(IPricingConditionsRepository.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	public VendorProductInfoService(@NonNull final IBPartnerBL bpartnerBL)
	{
		this.bpartnerBL = bpartnerBL;
	}

	/**
	 * @return the default instance for the given product and org, or (if there is none) some instance; never returns null;
	 */
	public Optional<VendorProductInfo> getDefaultVendorProductInfo(
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		final List<VendorProductInfo> vendorProductInfos = getVendorProductInfos(
				productId,
				orgId);

		if (vendorProductInfos.isEmpty())
		{
			return Optional.empty();
		}

		final VendorProductInfo defaultOrFirst = vendorProductInfos
				.stream()
				.filter(VendorProductInfo::isDefaultVendor)
				.findFirst()
				.orElseGet(() -> {
					Loggables.addLog("No vendorProductInfo was flagged as default; return the first one: {}", vendorProductInfos.get(0));
					return vendorProductInfos.get(0);
				});
		return Optional.of(defaultOrFirst);
	}

	public List<VendorProductInfo> getVendorProductInfos(@NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final ProductAndCategoryAndManufacturerId product = productsRepo.retrieveProductAndCategoryAndManufacturerByProductId(productId);

		final Map<BPartnerId, Integer> discountSchemaIds = bpartnersRepo.retrieveAllDiscountSchemaIdsIndexedByBPartnerId(BPartnerType.VENDOR);
		if (discountSchemaIds.isEmpty())
		{
			return ImmutableList.of(); // TODO: fallback to productprice
		}
		final Map<BPartnerId, I_C_BPartner_Product> bpartnerProductRecords = partnerProductDAO.retrieveByVendorIds(discountSchemaIds.keySet(), productId, orgId);

		final ImmutableList.Builder<VendorProductInfo> vendorProductInfos = ImmutableList.builder();
		for (final Map.Entry<BPartnerId, Integer> entry : discountSchemaIds.entrySet())
		{
			final BPartnerId vendorId = entry.getKey();
			final PricingConditionsId pricingConditionsId = PricingConditionsId.ofRepoId(entry.getValue());

			final I_C_BPartner_Product bpartnerProductRecord = bpartnerProductRecords.get(vendorId);

			final VendorProductInfo vendorProductInfo = createVendorProductInfo(
					vendorId,
					product,
					pricingConditionsId,
					bpartnerProductRecord);
			vendorProductInfos.add(vendorProductInfo);
		}

		return vendorProductInfos.build();
	}

	public VendorProductInfo getVendorProductInfo(
			@NonNull final BPartnerId vendorId,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId)
	{
		final ProductAndCategoryAndManufacturerId product = productsRepo.retrieveProductAndCategoryAndManufacturerByProductId(productId);

		final int discountSchemaId = bpartnerBL.getDiscountSchemaId(vendorId, BPartnerType.VENDOR.getSOTrx());
		final PricingConditionsId pricingConditionsId = PricingConditionsId.ofRepoId(discountSchemaId);

		final I_C_BPartner_Product bpartnerProductRecord = partnerProductDAO.retrieveByVendorId(vendorId, productId, orgId);

		return createVendorProductInfo(
				vendorId,
				product,
				pricingConditionsId,
				bpartnerProductRecord);
	}

	private VendorProductInfo createVendorProductInfo(
			@NonNull final BPartnerId vendorId,
			@NonNull final ProductAndCategoryAndManufacturerId product,
			@NonNull final PricingConditionsId pricingConditionsId,
			@Nullable final I_C_BPartner_Product bpartnerProductRecord)
	{
		final I_C_BPartner vendorRecord = bpartnersRepo.getById(vendorId);
		final boolean aggregatePOs = vendorRecord.isAggregatePO();
		final Percent vendorFlatDiscount = Percent.of(vendorRecord.getFlatDiscount());

		final PricingConditions pricingConditions = pricingConditionsRepo.getPricingConditionsById(pricingConditionsId);

		final ProductId productId = product.getProductId();
		final String vendorProductNo = CoalesceUtil.coalesceSuppliers(
				() -> bpartnerProductRecord != null ? bpartnerProductRecord.getVendorProductNo() : null,
				() -> bpartnerProductRecord != null ? bpartnerProductRecord.getProductNo() : null,
				() -> productBL.getProductValue(productId));

		final String vendorProductName = CoalesceUtil.coalesceSuppliers(
				() -> bpartnerProductRecord != null ? bpartnerProductRecord.getProductName() : null,
				() -> productBL.getProductName(productId));

		final boolean defaultVendor = bpartnerProductRecord != null ? bpartnerProductRecord.isCurrentVendor() : false;

		return VendorProductInfo.builder()
				.vendorId(vendorId)
				.defaultVendor(defaultVendor)
				.product(product)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE) // this might change when we incorporate attribute based pricing
				.vendorProductNo(vendorProductNo)
				.vendorProductName(vendorProductName)
				.aggregatePOs(aggregatePOs)
				.vendorFlatDiscount(vendorFlatDiscount)
				.pricingConditions(pricingConditions)
				.build();
	}
}
