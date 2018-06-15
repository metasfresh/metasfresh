package de.metas.purchasecandidate;

import java.math.BigDecimal;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import de.metas.lang.Percent;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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
	AttributeSetInstanceId attributeSetInstanceId;

	String vendorProductNo;
	String vendorProductName;

	boolean aggregatePOs;

	Percent vendorFlatDiscount;
	private PricingConditions pricingConditions;

	boolean defaultVendor;

	@Builder
	private VendorProductInfo(
			@NonNull final BPartnerId vendorId,
			@NonNull final Boolean defaultVendor,
			//
			@NonNull final ProductAndCategoryId productAndCategoryId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,

			@NonNull final String vendorProductNo,
			@NonNull final String vendorProductName,
			//
			final boolean aggregatePOs,
			//
			final Percent vendorFlatDiscount,
			@NonNull final PricingConditions pricingConditions)
	{
		this.vendorId = vendorId;
		this.defaultVendor = defaultVendor;

		this.productAndCategoryId = productAndCategoryId;
		this.attributeSetInstanceId = attributeSetInstanceId;

		this.vendorProductNo = vendorProductNo;
		this.vendorProductName = vendorProductName;

		this.aggregatePOs = aggregatePOs;

		this.vendorFlatDiscount = vendorFlatDiscount != null ? vendorFlatDiscount : Percent.ZERO;
		this.pricingConditions = pricingConditions;
	}

	public ProductId getProductId()
	{
		return getProductAndCategoryId().getProductId();
	}

	public PricingConditionsBreak getPricingConditionsBreakOrNull(final Quantity qtyToDeliver)
	{
		final PricingConditionsBreakQuery query = createPricingConditionsBreakQuery(qtyToDeliver);
		return getPricingConditions().pickApplyingBreak(query);
	}

	private PricingConditionsBreakQuery createPricingConditionsBreakQuery(final Quantity qtyToDeliver)
	{
		return PricingConditionsBreakQuery.builder()
				.productAndCategoryId(getProductAndCategoryId())
				// .attributeInstances(attributeInstances)// TODO
				.qty(qtyToDeliver.getAsBigDecimal())
				.price(BigDecimal.ZERO) // N/A
				.build();
	}
}
