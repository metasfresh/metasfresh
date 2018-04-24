/**
 *
 */
package org.adempiere.pricing.api;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;

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
	private final I_M_DiscountSchema schema;
	private final I_M_DiscountSchemaBreak forceSchemaBreak;
	
	private final BigDecimal qty;
	private final BigDecimal price;
	private final int productId;
	private final BigDecimal bpartnerFlatDiscount;
	private final List<I_M_AttributeInstance> attributeInstances;
	private final IPricingContext pricingCtx;

	@Builder
	private CalculateDiscountRequest(
			@NonNull final I_M_DiscountSchema schema,
			final I_M_DiscountSchemaBreak forceSchemaBreak,
			final BigDecimal qty,
			final BigDecimal price,
			final int productId,
			final BigDecimal bpartnerFlatDiscount,
			final List<I_M_AttributeInstance> attributeInstances,
			final IPricingContext pricingCtx)
	{
		if(forceSchemaBreak != null && forceSchemaBreak.getM_DiscountSchema_ID() != schema.getM_DiscountSchema_ID())
		{
			throw new AdempiereException("Schema and schema break does not match")
					.setParameter("schema", schema)
					.setParameter("forceSchemaBreak", forceSchemaBreak)
					.appendParametersToMessage();
		}
		
		this.schema = schema;
		this.forceSchemaBreak = forceSchemaBreak;
		this.qty = qty;
		this.price = price;
		this.productId = productId;
		this.bpartnerFlatDiscount = bpartnerFlatDiscount != null ? bpartnerFlatDiscount : BigDecimal.ZERO;
		this.attributeInstances = attributeInstances != null ? ImmutableList.copyOf(attributeInstances) : ImmutableList.of();
		this.pricingCtx = pricingCtx;
	}
}
