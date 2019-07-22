package de.metas.ui.web.order.products_proposal.model;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import lombok.Builder;
import lombok.Getter;
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

@ToString
public class ProductProposalPrice
{
	@Getter
	private final BigDecimal userEnteredPriceValue;
	@Getter
	private final CurrencyCode currencyCode;

	private final Amount priceListPrice;
	private final ProductProposalCampaignPrice campaignPrice;

	@Getter
	private final boolean campaignPriceUsed;

	@Getter
	private final boolean priceListPriceUsed;

	@Builder(toBuilder = true)
	private ProductProposalPrice(
			@NonNull final Amount priceListPrice,
			@Nullable final ProductProposalCampaignPrice campaignPrice,
			@Nullable final BigDecimal userEnteredPriceValue)
	{
		this.priceListPrice = priceListPrice;
		this.campaignPrice = campaignPrice;

		//
		this.currencyCode = priceListPrice.getCurrencyCode();
		if (campaignPrice != null && !currencyCode.equals(campaignPrice.getCurrencyCode()))
		{
			throw new AdempiereException("" + campaignPrice + " and " + priceListPrice + " shall have the same currency");
		}

		//
		if (userEnteredPriceValue != null)
		{
			this.userEnteredPriceValue = userEnteredPriceValue;
		}
		else if (campaignPrice != null)
		{
			this.userEnteredPriceValue = campaignPrice.applyOn(priceListPrice).getAsBigDecimal();
		}
		else
		{
			this.userEnteredPriceValue = priceListPrice.getAsBigDecimal();
		}

		//
		this.priceListPriceUsed = this.priceListPrice.valueComparingEqualsTo(this.userEnteredPriceValue);
		this.campaignPriceUsed = this.campaignPrice != null
				&& !priceListPriceUsed
				&& this.campaignPrice.amountValueComparingEqualsTo(this.userEnteredPriceValue);
	}

	public Amount getUserEnteredPrice()
	{
		return Amount.of(getUserEnteredPriceValue(), getCurrencyCode());
	}

	public ProductProposalPrice withUserEnteredPriceValue(@NonNull final BigDecimal userEnteredPriceValue)
	{
		if (this.userEnteredPriceValue.equals(userEnteredPriceValue))
		{
			return this;
		}

		return toBuilder().userEnteredPriceValue(userEnteredPriceValue).build();
	}

	public ProductProposalPrice withPriceListPriceValue(@NonNull final BigDecimal priceListPriceValue)
	{
		return withPriceListPrice(Amount.of(priceListPriceValue, getCurrencyCode()));
	}

	public ProductProposalPrice withPriceListPrice(@NonNull final Amount priceListPrice)
	{
		if (this.priceListPrice.equals(priceListPrice))
		{
			return this;
		}

		return toBuilder().priceListPrice(priceListPrice).build();
	}
}
