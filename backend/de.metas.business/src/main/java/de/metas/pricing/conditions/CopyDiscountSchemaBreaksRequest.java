package de.metas.pricing.conditions;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_M_DiscountSchemaBreak;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

@Builder
@Value
public class CopyDiscountSchemaBreaksRequest
{
	public enum Direction 
	{
	    SourceTarget,
	    TargetSource
	}
	
	@NonNull 
	IQueryFilter<I_M_DiscountSchemaBreak> filter;
	
	@NonNull 
	PricingConditionsId pricingConditionsId;
	
	@Nullable ProductId productId;
	
	boolean allowCopyToSameSchema;
	
	@NonNull 
	Direction direction;
	
	@Nullable Boolean makeTargetAsSource;
}
