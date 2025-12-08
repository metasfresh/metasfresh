package de.metas.rest_api.bpartner_pricelist.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.currency.CurrencyCode;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JsonResponsePrice
{
	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `M_Product.M_Product_ID`.")
	@NonNull
	ProductId productId;

	@NonNull
	String productCode;

	@NonNull
	BigDecimal price;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.String", //
			value = "Currency code (3 letters)")
	@NonNull
	CurrencyCode currencyCode;

	@ApiModelProperty( //
			allowEmptyValue = false, //
			dataType = "java.lang.Integer", //
			value = "This translates to `C_TaxCategory_ID`.")
	@NonNull
	TaxCategoryId taxCategoryId;
}
