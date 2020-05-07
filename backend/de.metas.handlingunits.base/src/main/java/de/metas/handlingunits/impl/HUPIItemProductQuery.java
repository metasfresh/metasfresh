package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.Date;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.handlingunits.IHUPIItemProductQuery;

/**
 *
 * NOTE: when extending this class with more values, please don't set valid default values (like huUnitType=TU) because it's confusing for API user.
 *
 * @author tsa
 *
 */
/* package */final class HUPIItemProductQuery implements IHUPIItemProductQuery
{
	private int huPIItemId = -1;
	private int productId = -1;
	private int bpartnerId = -1;
	private Date date = null;
	private boolean allowAnyProduct = true;
	private boolean allowInifiniteCapacity = true;
	private String huUnitType = null;
	private boolean allowVirtualPI = true;

	private boolean oneConfigurationPerPI = false;
	private boolean allowDifferentCapacities = false;

	// 08393 possibility to allow any partner
	private boolean allowAnyPartner = false;

	private int packagingProductId; // FRESH-386

	@Override
	public String toString()
	{
		return "HUPIItemProductQuery [huPIItemId=" + huPIItemId
				+ ", productId=" + productId
				+ ", bpartnerId=" + bpartnerId
				+ ", date=" + date
				+ ", allowAnyProduct=" + allowAnyProduct
				+ ", allowInifiniteCapacity=" + allowInifiniteCapacity
				+ ", huUnitType=" + huUnitType
				+ ", allowVirtualPI=" + allowVirtualPI
				+ ", oneConfigurationPerPI=" + oneConfigurationPerPI
				+ ", allowDifferentCapacities=" + allowDifferentCapacities
				+ ", allowAnyPartner=" + allowAnyPartner
				+ ", packagingProductId=" + packagingProductId + "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(huPIItemId)
				.append(productId)
				.append(bpartnerId)
				.append(date)
				.append(allowAnyProduct)
				.append(allowInifiniteCapacity)
				.append(huUnitType)
				.append(allowVirtualPI)
				.append(oneConfigurationPerPI)
				.append(allowDifferentCapacities)
				.append(packagingProductId)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final HUPIItemProductQuery other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(huPIItemId, other.huPIItemId)
				.append(productId, other.productId)
				.append(bpartnerId, other.bpartnerId)
				.append(date, other.date)
				.append(allowAnyProduct, other.allowAnyProduct)
				.append(allowInifiniteCapacity, other.allowInifiniteCapacity)
				.append(huUnitType, other.huUnitType)
				.append(allowVirtualPI, other.allowVirtualPI)
				.append(oneConfigurationPerPI, other.oneConfigurationPerPI)
				.append(allowDifferentCapacities, other.allowDifferentCapacities)
				.append(packagingProductId, other.packagingProductId)
				.isEqual();
	}

	@Override
	public int getM_HU_PI_Item_ID()
	{
		return huPIItemId;
	}

	@Override
	public void setM_HU_PI_Item_ID(final int huPIItemId)
	{
		this.huPIItemId = huPIItemId <= 0 ? -1 : huPIItemId;
	}

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		this.productId = productId <= 0 ? -1 : productId;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public void setC_BPartner_ID(final int bpartnerId)
	{
		this.bpartnerId = bpartnerId <= 0 ? -1 : bpartnerId;
	}

	@Override
	public void setDate(final Date date)
	{
		this.date = date;
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public boolean isAllowAnyProduct()
	{
		return allowAnyProduct;
	}

	@Override
	public void setAllowAnyProduct(final boolean allowAnyProduct)
	{
		this.allowAnyProduct = allowAnyProduct;
	}

	@Override
	public String getHU_UnitType()
	{
		return huUnitType;
	}

	@Override
	public void setHU_UnitType(final String huUnitType)
	{
		this.huUnitType = huUnitType;

	}

	@Override
	public boolean isAllowVirtualPI()
	{
		return allowVirtualPI;
	}

	@Override
	public void setAllowVirtualPI(final boolean allowVirtualPI)
	{
		this.allowVirtualPI = allowVirtualPI;
	}

	@Override
	public void setOneConfigurationPerPI(final boolean oneConfigurationPerPI)
	{
		this.oneConfigurationPerPI = oneConfigurationPerPI;
	}

	@Override
	public boolean isOneConfigurationPerPI()
	{
		return oneConfigurationPerPI;
	}

	@Override
	public boolean isAllowDifferentCapacities()
	{
		return allowDifferentCapacities;
	}

	@Override
	public void setAllowDifferentCapacities(final boolean allowDifferentCapacities)
	{
		this.allowDifferentCapacities = allowDifferentCapacities;
	}

	@Override
	public boolean isAllowInfiniteCapacity()
	{
		return allowInifiniteCapacity;
	}

	@Override
	public void setAllowInfiniteCapacity(final boolean allowInfiniteCapacity)
	{
		allowInifiniteCapacity = allowInfiniteCapacity;
	}

	@Override
	public boolean isAllowAnyPartner()
	{
		return allowAnyPartner;
	}

	@Override
	public void setAllowAnyPartner(final boolean allowAnyPartner)
	{
		this.allowAnyPartner = allowAnyPartner;

	}

	@Override
	public int getM_Product_Packaging_ID()
	{
		return packagingProductId;
	}

	@Override
	public void setM_Product_Packaging_ID(int packagingProductId)
	{
		this.packagingProductId = packagingProductId;
	}
}
