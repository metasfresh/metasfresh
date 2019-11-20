/*
 * #%L
 * de.metas.business
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

package de.metas.pricing.conditions.service;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@Builder
public class CreatePriceListSchemaRequest
{
	@NonNull
	PricingConditionsId discountSchemaId;

	int seqNo;

	@Nullable
	ProductId productId;
	@Nullable
	ProductCategoryId productCategoryId;
	@Nullable
	BPartnerId bPartnerId;

	@Nullable
	TaxCategoryId taxCategoryId;
	@NonNull
	BigDecimal std_AddAmt;
	@NonNull
	String std_Rounding;
	@Nullable
	TaxCategoryId taxCategoryTargetId;

	@NonNull
	Timestamp conversionDate;

	@NonNull
	String list_Base;
	@NonNull
	CurrencyConversionTypeId c_ConversionType_ID;

	@NonNull
	BigDecimal limit_AddAmt;
	@NonNull
	String limit_Base;
	@NonNull
	BigDecimal limit_Discount;
	@NonNull
	BigDecimal limit_MaxAmt;
	@NonNull
	BigDecimal limit_MinAmt;
	@NonNull
	String limit_Rounding;

	@NonNull
	BigDecimal list_AddAmt;
	@NonNull
	BigDecimal list_Discount;
	@NonNull
	BigDecimal list_MaxAmt;
	@NonNull
	BigDecimal list_MinAmt;
	@NonNull
	String list_Rounding;

	@NonNull
	String std_Base;
	@NonNull
	BigDecimal std_Discount;
	@NonNull
	BigDecimal std_MaxAmt;
	@NonNull
	BigDecimal std_MinAmt;
}
