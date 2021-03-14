/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.pricing.productprice;

import de.metas.common.rest_api.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestProductPrice
{
	@ApiModelProperty(required = true)
	private String orgCode;

	@ApiModelProperty(required = true)
	private String productId;

	@ApiModelProperty(hidden = true)
	private boolean productIdSet;

	@ApiModelProperty(required = true)
	private BigDecimal priceLimit;

	@ApiModelProperty(hidden = true)
	private boolean priceLimitSet;

	@ApiModelProperty(required = true)
	private BigDecimal priceList;

	@ApiModelProperty(hidden = true)
	private boolean priceListSet;

	@ApiModelProperty(required = true)
	private BigDecimal priceStd;

	@ApiModelProperty(hidden = true)
	private boolean priceStdSet;

	@ApiModelProperty(required = true)
	private String seqNo;

	@ApiModelProperty(hidden = true)
	private boolean seqNoSet;

	@ApiModelProperty(required = true)
	private TaxCategory taxCategory;

	@ApiModelProperty(hidden = true)
	private boolean taxCategorySet;

	@ApiModelProperty(required = true)
	private String active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	private SyncAdvise syncAdvise;

	@ApiModelProperty(hidden = true)
	private boolean syncAdviseSet;

	public void setOrgCode(final String orgCode)
	{
		this.orgCode = orgCode;
	}

	public void setProductId(final String productId)
	{
		this.productId = productId;
		this.productIdSet = true;
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
		this.priceStdSet = true;
	}

	public void setSeqNo(final String seqNo)
	{
		this.seqNo = seqNo;
		this.seqNoSet = true;
	}

	public void setTaxCategory(final TaxCategory taxCategory)
	{
		this.taxCategory = taxCategory;
		this.taxCategorySet = true;
	}

	public void setActive(final String active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}
}
