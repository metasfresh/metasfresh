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

import com.google.common.collect.ImmutableList;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginLine.MappingCriteria;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@Value
public class CustomerTradeMargin
{
	@Nullable
	CustomerTradeMarginId customerTradeMarginId;

	@NonNull
	ProductId commissionProductId;

	@NonNull
	Integer pointsPrecision;

	@NonNull
	String name;

	@Nullable
	String description;

	@NonNull
	ImmutableList<CustomerTradeMarginLine> lines;

	@Builder
	CustomerTradeMargin(
			@Nullable final CustomerTradeMarginId customerTradeMarginId,
			@NonNull final ProductId commissionProductId,
			@NonNull final Integer pointsPrecision,
			@NonNull final String name,
			@Nullable final String description,
			@NonNull final ImmutableList<CustomerTradeMarginLine> lines)
	{
		this.customerTradeMarginId = customerTradeMarginId;
		this.commissionProductId = commissionProductId;
		this.pointsPrecision = pointsPrecision;
		this.name = name;
		this.description = description;
		this.lines = lines;
	}

	@NonNull
	public CustomerTradeMarginId getIdNotNull()
	{
		if (this.customerTradeMarginId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted CustomerTradeMargin objects!")
					.appendParametersToMessage()
					.setParameter("CustomerTradeMargin", this);
		}

		return customerTradeMarginId;
	}

	@NonNull
	public Optional<CustomerTradeMarginLine> getLineForCustomer(
			@NonNull final MappingCriteria mappingCriteria)
	{
		return lines.stream()
				.sorted(Comparator.comparingInt(CustomerTradeMarginLine::getSeqNo))
				.filter(line -> line.appliesTo(mappingCriteria))
				.findFirst();
 	}
}
