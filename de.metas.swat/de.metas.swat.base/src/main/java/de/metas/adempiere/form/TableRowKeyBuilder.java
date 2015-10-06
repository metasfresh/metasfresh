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


import org.adempiere.util.Check;

public class TableRowKeyBuilder
{
	private int bpartnerId = -1;
	private String bPartnerAddress;
	private int warehouseId = -1;
	private int warehouseDestId = -1;
	private int productId = -1;
	private int shipperId = -1;
	private String deliveryVia;
	private int singleShipmentOrderId = -1;
	private int representativeShipmentScheduleId = -1;
	private int seqNo = -1;

	public TableRowKeyBuilder()
	{
		super();
	}

	public TableRowKey createTableRowKey()
	{
		final TableRowKey key = new TableRowKey(
				bpartnerId,
				Check.isEmpty(bPartnerAddress, true) ? null : bPartnerAddress.trim(),
				warehouseId <= 0 ? -1 : warehouseId,
				warehouseDestId <= 0 ? -1 : warehouseDestId,
				shipperId <= 0 ? -1 : shipperId,
				Check.isEmpty(deliveryVia, true) ? null : deliveryVia.trim(),
				singleShipmentOrderId <= 0 ? -1 : singleShipmentOrderId,
				productId <= 0 ? -1 : productId,
				seqNo
				);
		return key;
	}

	public int getBPartnerId()
	{
		return bpartnerId;
	}

	public void setBPartnerId(final int bpartnerId)
	{
		this.bpartnerId = bpartnerId;
	}

	public String getBPartnerAddress()
	{
		return bPartnerAddress;
	}

	public void setBPartnerAddress(final String bPartnerAddress)
	{
		this.bPartnerAddress = bPartnerAddress;
	}

	public int getWarehouseId()
	{
		return warehouseId;
	}

	public void setWarehouseId(final int warehouseId)
	{
		this.warehouseId = warehouseId;
	}

	public int getWarehouseDestId()
	{
		return warehouseDestId;
	}

	public void setWarehouseDestId(final int warehouseDestId)
	{
		this.warehouseDestId = warehouseDestId;
	}

	public int getProductId()
	{
		return productId;
	}

	public void setProductId(final int productId)
	{
		this.productId = productId;
	}

	public int getShipperId()
	{
		return shipperId;
	}

	public void setShipperId(final int shipperId)
	{
		this.shipperId = shipperId;
	}

	public String getDeliveryVia()
	{
		return deliveryVia;
	}

	public void setDeliveryVia(final String deliveryVia)
	{
		this.deliveryVia = deliveryVia;
	}

	public int getSingleShipmentOrderId()
	{
		return singleShipmentOrderId;
	}

	public void setSingleShipmentOrderId(final int singleShipmentOrderId)
	{
		this.singleShipmentOrderId = singleShipmentOrderId;
	}

	public int getRepresentativeShipmentScheduleId()
	{
		return representativeShipmentScheduleId;
	}

	public void setRepresentativeShipmentScheduleId(final int representativeShipmentScheduleId)
	{
		this.representativeShipmentScheduleId = representativeShipmentScheduleId;
	}

	public int getSeqNo()
	{
		return seqNo;
	}

	public void setSeqNo(final int seqNo)
	{
		this.seqNo = seqNo;
	}
}
