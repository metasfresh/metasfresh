package de.metas.adempiere.form;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

/**
 * Immutable Key to identify an individual table row.
 * 
 * @author ts
 * 
 */
public final class TableRowKey
{
	private final int bpartnerId;
	private final String bPartnerAddress;
	private final int warehouseId;
	private final int warehouseDestId;
	private final int productId;
	private final int shipperId;
	private final String deliveryVia;
	private final int singleShipmentOrderId;
	private final int seqNo;

	/* package */TableRowKey(final int bpartnerId,
			final String bPartnerAddress,
			final int warehouseId, final int warehouseDestId,
			final int shipperId,
			final String deliveryVia,
			final int singleShipmentOrderId,
			final int productId,
			final int seqNo)
	{
		super();

		this.bpartnerId = bpartnerId;
		this.bPartnerAddress = bPartnerAddress;
		this.warehouseId = warehouseId;
		this.warehouseDestId = warehouseDestId;
		this.productId = productId;
		this.shipperId = shipperId;
		this.deliveryVia = deliveryVia;
		this.singleShipmentOrderId = singleShipmentOrderId;
		this.seqNo = seqNo;
	}

	@Override
	public String toString()
	{
		return "TableRowKey ["
				+ "bPartnerAddress=" + bPartnerAddress
				+ ", warehouseId=" + warehouseId
				+ ", warehouseDestId=" + warehouseDestId
				+ ", productId=" + productId
				+ ", shipperId=" + shipperId
				+ ", deliveryVia=" + deliveryVia
				+ ", singleShipmentOrderId=" + singleShipmentOrderId
				+ ", seqNo=" + seqNo
				+ "]";
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (obj != null && obj instanceof TableRowKey)
		{
			final TableRowKey other = (TableRowKey)obj;
			return other.mkKey().equals(mkKey());
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return mkKey().hashCode();
	}

	private ArrayKey mkKey()
	{
		return Util.mkKey(bPartnerAddress,
				warehouseId,
				warehouseDestId,
				productId,
				shipperId,
				deliveryVia
				// seqNo // we are not adding this to key because we want to search for a key (without knowing the seqNo)
				);
	}

	public int getBPartnerId()
	{
		return bpartnerId;
	}

	public String getBPartnerAddress()
	{
		return bPartnerAddress;
	}

	public int getWarehouseId()
	{
		return warehouseId;
	}

	public int getWarehouseDestId()
	{
		return warehouseDestId;
	}

	public int getProductId()
	{
		return productId;
	}

	public String getDeliveryVia()
	{
		return deliveryVia;
	}

	public int getShipperId()
	{
		return shipperId;
	}

	public int getSingleShipmentOrderId()
	{
		return singleShipmentOrderId;
	}

	public int getSeqNo()
	{
		return seqNo;
	}
}
