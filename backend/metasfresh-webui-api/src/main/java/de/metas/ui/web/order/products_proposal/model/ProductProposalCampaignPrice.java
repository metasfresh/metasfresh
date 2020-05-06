package de.metas.ui.web.order.products_proposal.model;

import java.math.BigDecimal;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Builder
@EqualsAndHashCode
@ToString
public final class ProductProposalCampaignPrice
{
	@NonNull
	private final Amount amount;
	private final boolean applyOnlyIfLessThanStandardPrice;

	public Amount applyOn(@NonNull final Amount standardPrice)
	{
		if (applyOnlyIfLessThanStandardPrice)
		{
			return this.amount.min(standardPrice);
		}
		else
		{
			return amount;
		}
	}

	public boolean amountValueComparingEqualsTo(@NonNull final BigDecimal other)
	{
		return amount.valueComparingEqualsTo(other);
	}

	public CurrencyCode getCurrencyCode()
	{
		return amount.getCurrencyCode();
	}
}
