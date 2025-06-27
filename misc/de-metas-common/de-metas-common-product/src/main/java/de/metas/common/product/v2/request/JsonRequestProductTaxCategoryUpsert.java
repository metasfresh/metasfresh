/*
 * #%L
 * de-metas-common-product
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.common.product.v2.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Contains the actual product tax category to insert or update.")
public class JsonRequestProductTaxCategoryUpsert
{
	@ApiModelProperty
	private String taxCategory;

	@ApiModelProperty(hidden = true)
	private boolean taxCategorySet;

	@ApiModelProperty(required = true)
	private String countryCode;

	@ApiModelProperty
	private Instant validFrom;

	@ApiModelProperty(hidden = true)
	private boolean validFromSet;

	@ApiModelProperty
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;
	
	public void setTaxCategory(final String taxCategory)
	{
		this.taxCategory = taxCategory;
		this.taxCategorySet = true;
	}

	public void setValidFrom(final Instant validFrom)
	{
		this.validFrom = validFrom;
		this.validFromSet = true;
	}

	public void setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}
}
