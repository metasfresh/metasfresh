package de.metas.pricing.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.IPricingAttribute;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.service.PricingConditionsResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * NOTEs to developers:
 * <ul>
 * <li>if you want to add a new field here which will be copied from {@link IPricingContext}, please check {@link de.metas.pricing.service.impl.PricingBL#createInitialResult(IPricingContext)}.
 * </ul>
 *
 * @author tsa
 */
@ToString
@Data
final class PricingResult implements IPricingResult
{
	private boolean calculated;

	private PricingSystemId pricingSystemId;
	@Nullable
	private PriceListId priceListId;
	@Nullable
	private PriceListVersionId priceListVersionId;
	@Nullable
	private CurrencyId currencyId;
	private UomId priceUomId;
	private CurrencyPrecision precision;

	@Nullable
	private ProductId productId;
	private ProductCategoryId productCategoryId;

	private TaxCategoryId taxCategoryId;
	private boolean taxIncluded = false;

	@Nullable
	private PricingConditionsResult pricingConditions;

	private BigDecimal priceList = BigDecimal.ZERO;
	@Nullable
	private BigDecimal priceStd = BigDecimal.ZERO;
	private BigDecimal priceLimit = BigDecimal.ZERO;
	private Percent discount = Percent.ZERO;

	@NonNull
	private BooleanWithReason enforcePriceLimit = BooleanWithReason.FALSE;

	private boolean usesDiscountSchema = false;
	private boolean disallowDiscount;

	private final LocalDate priceDate;

	private boolean priceEditable = true;

	private boolean discountEditable = true;

	private boolean campaignPrice = false;

	private boolean isDiscountCalculated;

	private InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.NominalWeight;

	@Getter(AccessLevel.NONE)
	private final List<IPricingRule> rulesApplied = new ArrayList<>();

	@Getter(AccessLevel.NONE)
	private final List<IPricingAttribute> pricingAttributes = new ArrayList<>();

	@Getter
	private ImmutableList<String> loggableMessages;

	private BigDecimal baseCommissionPointsPerPriceUOM;

	private Percent tradedCommissionPercent = Percent.ZERO;

	@Builder
	private PricingResult(
			@NonNull final LocalDate priceDate,
			//
			@Nullable final PricingSystemId pricingSystemId,
			@Nullable final PriceListId priceListId,
			@Nullable final PriceListVersionId priceListVersionId,
			@Nullable final CurrencyId currencyId,
			//
			@Nullable final ProductId productId,
			//
			final boolean disallowDiscount,
			final boolean isDiscountCalculated)
	{
		this.calculated = false;

		this.priceDate = priceDate;

		this.pricingSystemId = pricingSystemId;
		this.priceListId = priceListId;
		this.priceListVersionId = priceListVersionId;
		this.currencyId = currencyId;

		this.productId = productId;

		this.disallowDiscount = disallowDiscount;
		this.isDiscountCalculated = isDiscountCalculated;
	}

	@Override
	public Money getPriceStdAsMoney()
	{
		return Money.of(getPriceStd(), getCurrencyId());
	}

	/**
	 * @return discount, never {@code null}
	 */
	@Override
	@NonNull
	public Percent getDiscount()
	{
		return CoalesceUtil.coalesce(discount, Percent.ZERO);
	}

	@Override
	public void setDiscount(@NonNull final Percent discount)
	{
		if (isDisallowDiscount())
		{
			throw new AdempiereException("Attempt to set the discount although isDisallowDiscount()==true")
					.appendParametersToMessage()
					.setParameter("this", this);
		}
		this.discount = discount;
		this.isDiscountCalculated = true;
	}

	@Override
	public void addPricingRuleApplied(@NonNull final IPricingRule rule)
	{
		rulesApplied.add(rule);
	}

	@Override
	public List<IPricingAttribute> getPricingAttributes()
	{
		return pricingAttributes;
	}

	@Override
	public void addPricingAttributes(final Collection<IPricingAttribute> pricingAttributesToAdd)
	{
		if (pricingAttributesToAdd == null || pricingAttributesToAdd.isEmpty())
		{
			return;
		}

		pricingAttributes.addAll(pricingAttributesToAdd);
	}

	/**
	 * Supposed to be called by the pricing engine.
	 * <p>
	 * task https://github.com/metasfresh/metasfresh/issues/4376
	 */
	public void updatePriceScales()
	{
		priceStd = scaleToPrecision(priceStd);
		priceLimit = scaleToPrecision(priceLimit);
		priceList = scaleToPrecision(priceList);
	}

	@Nullable
	private BigDecimal scaleToPrecision(@Nullable final BigDecimal priceToRound)
	{
		if (priceToRound == null || precision == null)
		{
			return priceToRound;
		}

		return precision.round(priceToRound);
	}

	@Override
	public boolean isCampaignPrice()
	{
		return campaignPrice;
	}

	@Override
	public IPricingResult setLoggableMessages(@NonNull final ImmutableList<String> loggableMessages)
	{
		this.loggableMessages = loggableMessages;
		return this;
	}
}
