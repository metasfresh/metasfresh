/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;

import static de.metas.common.pricing.v2.constants.SwaggerDocConstants.PRODUCT_IDENTIFIER;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestProductPrice
{
	@ApiModelProperty(required = true)
	private String orgCode;

	@ApiModelProperty(required = true, value = PRODUCT_IDENTIFIER)
	private String productIdentifier;

	@ApiModelProperty(required = true)
	private TaxCategory taxCategory;

	@ApiModelProperty(required = true)
	private BigDecimal priceStd;

	private BigDecimal priceLimit;

	@ApiModelProperty(hidden = true)
	private boolean priceLimitSet;

	private BigDecimal priceList;

	@ApiModelProperty(hidden = true)
	private boolean priceListSet;

	private Integer seqNo;

	@ApiModelProperty(hidden = true)
	private boolean seqNoSet;

	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	private SyncAdvise syncAdvise;

	@ApiModelProperty(hidden = true)
	private boolean syncAdviseSet;

	@ApiModelProperty
	private String uomCode;

	@ApiModelProperty(hidden = true)
	private boolean uomCodeSet;

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	public void setProductIdentifier(final String productId)
	{
		this.productIdentifier = productId;
	}

	public void setPriceLimit(final BigDecimal priceLimit)
	{
		this.priceLimit = priceLimit;
		this.priceLimitSet = true;
	}

	public void setPriceList(final BigDecimal priceList)
	{
		this.priceList = priceList;
		this.priceListSet = true;
	}

	public void setPriceStd(final BigDecimal priceStd)
	{
		this.priceStd = priceStd;
	}

	public void setSeqNo(final Integer seqNo)
	{
		this.seqNo = seqNo;
		this.seqNoSet = true;
	}

	public void setTaxCategory(final TaxCategory taxCategory)
	{
		this.taxCategory = taxCategory;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}

	public void setUomCode(final String uomCode)
	{
		this.uomCode = uomCode;
		this.uomCodeSet = true;
	}

	@NonNull
	public String getOrgCode()
	{
		return orgCode;
	}

	@NonNull
	public String getProductIdentifier()
	{
		return productIdentifier;
	}

	@NonNull
	public TaxCategory getTaxCategory()
	{
		return taxCategory;
	}

	@NonNull
	public BigDecimal getPriceStd()
	{
		return priceStd;
	}
}
