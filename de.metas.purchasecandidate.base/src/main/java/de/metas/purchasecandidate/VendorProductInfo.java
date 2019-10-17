package de.metas.purchasecandidate;

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.mm.attributes.AttributeSetInstanceId;

import de.metas.bpartner.BPartnerId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.lang.Percent;

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

	ProductAndCategoryAndManufacturerId product;
	AttributeSetInstanceId attributeSetInstanceId;

	String vendorProductNo;
	String vendorProductName;

	boolean aggregatePOs;

	/** vendor specific discount that comes from the {@code C_BPartner} record. I.e. not related to "flat-percent" pricing conditions. */
	Percent vendorFlatDiscount;

	private PricingConditions pricingConditions;

	boolean defaultVendor;

	@Builder
	private VendorProductInfo(
			@NonNull final BPartnerId vendorId,
			@NonNull final Boolean defaultVendor,
			//
			@NonNull final ProductAndCategoryAndManufacturerId product,
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

		this.product = product;
		this.attributeSetInstanceId = attributeSetInstanceId;

		this.vendorProductNo = vendorProductNo;
		this.vendorProductName = vendorProductName;

		this.aggregatePOs = aggregatePOs;

		this.vendorFlatDiscount = vendorFlatDiscount != null ? vendorFlatDiscount : Percent.ZERO;
		this.pricingConditions = pricingConditions;
	}

	public ProductId getProductId()
	{
		return getProduct().getProductId();
	}

	public PricingConditionsBreak getPricingConditionsBreakOrNull(final Quantity qtyToDeliver)
	{
		final PricingConditionsBreakQuery query = createPricingConditionsBreakQuery(qtyToDeliver);
		return getPricingConditions().pickApplyingBreak(query);
	}

	public VendorProductInfo assertThatAttributeSetInstanceIdCompatibleWith(@NonNull final AttributeSetInstanceId otherId)
	{
		if (AttributeSetInstanceId.NONE.equals(attributeSetInstanceId))
		{
			return this;
		}
		Check.errorUnless(Objects.equals(otherId, attributeSetInstanceId),
				"The given atributeSetInstanceId is not compatible with our id; otherId={}; this={}", otherId, this);
		return this;
	}

	private PricingConditionsBreakQuery createPricingConditionsBreakQuery(final Quantity qtyToDeliver)
	{
		return PricingConditionsBreakQuery.builder()
				.product(getProduct())
				// .attributeInstances(attributeInstances)// TODO
				.qty(qtyToDeliver.toBigDecimal())
				.price(BigDecimal.ZERO) // N/A
				.build();
	}
}
