/*
 * #%L
 * de.metas.contracts
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.contracts.pricing.trade_margin;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class CustomerTradeMarginLine
{
	@Nullable
	CustomerTradeMarginLineId customerTradeMarginLineId;

	@NonNull
	CustomerTradeMarginId customerTradeMarginId;

	@NonNull
	Boolean active;

	@NonNull
	Integer seqNo;

	@NonNull
	Integer marginPercent;

	@Nullable
	BPartnerId customerId;

	@Nullable
	ProductId productId;

	@Nullable
	ProductCategoryId productCategoryId;

	@Builder
	CustomerTradeMarginLine(
			@Nullable final CustomerTradeMarginLineId customerTradeMarginLineId,
			@NonNull final CustomerTradeMarginId customerTradeMarginId,
			@NonNull final Boolean active,
			@NonNull final Integer seqNo,
			@NonNull final Integer marginPercent,
			@Nullable final BPartnerId customerId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId)
	{
		this.customerTradeMarginLineId = customerTradeMarginLineId;
		this.customerTradeMarginId = customerTradeMarginId;
		this.active = active;
		this.seqNo = seqNo;
		this.marginPercent = marginPercent;
		this.customerId = customerId;
		this.productId = productId;
		this.productCategoryId = productCategoryId;
	}

	@NonNull
	public CustomerTradeMarginLineId getIdNotNull()
	{
		if (this.customerTradeMarginLineId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted CustomerTradeMarginLine objects!")
					.appendParametersToMessage()
					.setParameter("CustomerTradeMarginLine", this);
		}

		return customerTradeMarginLineId;
	}

	public boolean appliesTo(@NonNull final MappingCriteria mappingCriteria)
	{
		return appliesToCustomer(mappingCriteria.getCustomerId()) 
				&& appliesToProduct(mappingCriteria.getProductId())
				&& appliesToProductCategory(mappingCriteria.getProductCategoryId());
	}

	private boolean appliesToCustomer(@NonNull final BPartnerId customerCandidateId)
	{
		if (this.customerId == null)
		{
			return true;
		}
		return this.customerId.equals(customerCandidateId);
	}

	private boolean appliesToProduct(@NonNull final ProductId productId)
	{
		if (this.productId == null)
		{
			return true;
		}
		return this.productId.equals(productId);
	}

	private boolean appliesToProductCategory(@NonNull final ProductCategoryId productCategoryId)
	{
		if (this.productCategoryId == null)
		{
			return true;
		}
		return this.productCategoryId.equals(productCategoryId);
	}

	@NonNull
	public Percent getPercent()
	{
		return Percent.of(this.marginPercent);
	}

	@Value
	@Builder
	public static class MappingCriteria
	{
		@NonNull BPartnerId customerId;

		@NonNull ProductId productId;

		@NonNull ProductCategoryId productCategoryId;
	}
}
