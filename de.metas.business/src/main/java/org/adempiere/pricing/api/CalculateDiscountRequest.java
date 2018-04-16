/**
 *
 */
package org.adempiere.pricing.api;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Immutable
public class CalculateDiscountRequest
{
	@NonNull
	private final I_M_DiscountSchema schema;
	private final BigDecimal qty;
	private final BigDecimal Price;
	private final int M_Product_ID;
	private final int M_Product_Category_ID;
	private final BigDecimal bPartnerFlatDiscount;
	private final List<I_M_AttributeInstance> instances;
	private final IPricingContext pricingCtx;

	@Builder
	private CalculateDiscountRequest(
			@NonNull final I_M_DiscountSchema schema,
			final BigDecimal qty,
			final BigDecimal Price,
			final int M_Product_ID,
			final int M_Product_Category_ID,
			final BigDecimal bPartnerFlatDiscount,
			final List<I_M_AttributeInstance> instances,
			final IPricingContext pricingCtx)
	{
		this.schema = schema;
		this.qty = qty;
		this.Price = Price;
		this.M_Product_ID = M_Product_ID;
		this.M_Product_Category_ID = M_Product_Category_ID;
		this.bPartnerFlatDiscount = bPartnerFlatDiscount;
		this.instances = instances != null ? ImmutableList.copyOf(instances) : ImmutableList.of();
		this.pricingCtx = pricingCtx;
	}
}
