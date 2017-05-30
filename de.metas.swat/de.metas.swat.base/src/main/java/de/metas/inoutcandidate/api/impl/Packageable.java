package de.metas.inoutcandidate.api.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.inoutcandidate.api.IPackageable;

/**
 * Plain {@link IPackageable} implementation
 * 
 * @author tsa
 * 
 */
// NOTE: class is public only for testing purposes
public class Packageable implements IPackageable
{
	private int bPartnerLocationId;

	private int shipmentScheduleId;

	private BigDecimal qtyToDeliver;

	private int bpartnerId;
	private String bPartnerValue;
	private String bPartnerName;

	private String bPartnerLocationName;

	private String bPartnerAddress;

	private int warehouseId;
	private String warehouseName;

	private String deliveryVia;

	private int shipperId;
	private String shipperName;

	private boolean isDisplayed;

	private String DocumentNo;

	private Timestamp deliveryDate;
	private Timestamp preparationDate;

	private String freighCostRule;

	private String docType;

	private int productId = -1;
	private String productName = null;

	private int orderId = -1;
	private String docSubType;

	/* package */Packageable()
	{
		super();
	}

	@Override
	public int getBPartnerId()
	{
		return bpartnerId;
	}

	public void setBpartnerId(int bpartnerId)
	{
		this.bpartnerId = bpartnerId;
	}

	@Override
	public int getBPartnerLocationId()
	{
		return bPartnerLocationId;
	}

	public void setBPartnerLocationId(int bPartnerLocationId)
	{
		this.bPartnerLocationId = bPartnerLocationId;
	}

	@Override
	public int getShipmentScheduleId()
	{
		return shipmentScheduleId;
	}

	public void setShipmentScheduleId(int shipmentScheduleId)
	{
		this.shipmentScheduleId = shipmentScheduleId;
	}

	@Override
	public BigDecimal getQtyToDeliver()
	{
		return qtyToDeliver;
	}

	public void setQtyToDeliver(BigDecimal qtyToDeliver)
	{
		this.qtyToDeliver = qtyToDeliver;
	}

	@Override
	public String getBPartnerValue()
	{
		return bPartnerValue;
	}

	public void setBPartnerValue(String bPartnerValue)
	{
		this.bPartnerValue = bPartnerValue;
	}

	@Override
	public String getBPartnerName()
	{
		return bPartnerName;
	}

	public void setBPartnerName(String bPartnerName)
	{
		this.bPartnerName = bPartnerName;
	}

	@Override
	public String getBPartnerLocationName()
	{
		return bPartnerLocationName;
	}

	public void setBPartnerLocationName(String bPartnerLocationName)
	{
		this.bPartnerLocationName = bPartnerLocationName;
	}

	@Override
	public String getWarehouseName()
	{
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName)
	{
		this.warehouseName = warehouseName;
	}

	@Override
	public String getWarehouseDestName()
	{
		// NOTE: this is a legacy getter that we inherited from a legacy project
		throw new UnsupportedOperationException("WarehouseDestName is not supported");
	}

	@Override
	public int getWarehouseDestId()
	{
		// NOTE: this is a legacy getter that we inherited from a legacy project
		throw new UnsupportedOperationException("WarehouseDestId is not supported");
	}

	@Override
	public String getDeliveryVia()
	{
		return deliveryVia;
	}

	public void setDeliveryVia(String deliveryVia)
	{
		this.deliveryVia = deliveryVia;
	}

	@Override
	public int getShipperId()
	{
		return shipperId;
	}

	public void setShipperId(int shipperId)
	{
		this.shipperId = shipperId;
	}

	@Override
	public String getShipperName()
	{
		return shipperName;
	}

	public void setShipperName(String shipperName)
	{
		this.shipperName = shipperName;
	}

	@Override
	public boolean isDisplayed()
	{
		return isDisplayed;
	}

	public void setDisplayed(boolean isDisplayed)
	{
		this.isDisplayed = isDisplayed;
	}

	@Override
	public String getDocumentNo()
	{
		return DocumentNo;
	}

	public void setDocumentNo(String documentNo)
	{
		DocumentNo = documentNo;
	}

	@Override
	public Timestamp getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}
	
	@Override
	public Timestamp getPreparationDate()
	{
		return preparationDate;
	}

	public void setPreparationTime(Timestamp preparationDate)
	{
		this.preparationDate = preparationDate;
	}

	@Override
	public String getFreightCostRule()
	{
		return freighCostRule;
	}

	public void setFreightCostRule(String freighCostRule)
	{
		this.freighCostRule = freighCostRule;
	}

	@Override
	public String getDocType()
	{
		return docType;
	}

	public void setDocType(String docType)
	{
		this.docType = docType;
	}

	@Override
	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	@Override
	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	@Override
	public String getBPartnerAddress()
	{
		return bPartnerAddress;
	}

	public void setBPartnerAddress(String bPartnerAddress)
	{
		this.bPartnerAddress = bPartnerAddress;
	}

	@Override
	public int getWarehouseId()
	{
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId)
	{
		this.warehouseId = warehouseId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	@Override
	public int getOrderId()
	{
		return this.orderId;
	}

	public void setDocSubType(String docSubType)
	{
		this.docSubType = docSubType;
	}

	@Override
	public String getDocSubType()
	{
		return docSubType;
	}
}
