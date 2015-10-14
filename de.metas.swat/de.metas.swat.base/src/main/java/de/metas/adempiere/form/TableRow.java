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


import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.Check;

public class TableRow
{
	private final int bPartnerLocationId;

	private final int shipmentScheduleId;

	private BigDecimal qtyToDeliver;

	private final int bpartnerId;
	private final String bPartnerValue;
	private final String bPartnerName;

	private final String bPartnerLocationName;

	private final String warehouseName;

	private String warehouseDestName;

	private int warehouseDestId;

	private final String deliveryVia;

	private final String shipper;

	private final TableRowKey key;

	private final boolean displayed;

	private String DocumentNo;

	private Date deliveryDate;
	private Date preparationDate;

	private String freighCostRule;

	private String docType;

	private int productId = -1;
	private String productName = null;

	public TableRow(
			final int bPartnerLocationId,
			final int shipmentScheduleId,
			final BigDecimal qtyToDeliver,
			final int bpartnerId,
			final String bPartnerValue,
			final String bPartnerName,
			final String bPartnerLocationName,
			final String warehouseName,
			final String deliveryVia,
			final String shipper,
			final boolean isDisplayed,
			final TableRowKey key)
	{
		super();
		this.bPartnerLocationId = bPartnerLocationId;
		this.shipmentScheduleId = shipmentScheduleId;

		this.bpartnerId = bpartnerId;
		this.bPartnerValue = bPartnerValue;
		this.bPartnerName = bPartnerName;
		this.bPartnerLocationName = bPartnerLocationName;
		this.warehouseName = warehouseName;
		this.deliveryVia = deliveryVia;
		this.shipper = shipper;
		this.displayed = isDisplayed;
		this.key = key;
		
		setQtyToDeliver(qtyToDeliver);
	}
	
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " ["
				+ "bPartnerLocationId=" + bPartnerLocationId
				+ ", shipmentScheduleId=" + shipmentScheduleId
				+ ", qtyToDeliver=" + qtyToDeliver
				+ ", bpartnerId=" + bpartnerId
				+ ", bPartnerValue=" + bPartnerValue
				+ ", bPartnerName=" + bPartnerName
				+ ", bPartnerLocationName=" + bPartnerLocationName
				+ ", warehouseName=" + warehouseName
				+ ", warehouseDestName=" + warehouseDestName
				+ ", warehouseDestId=" + warehouseDestId
				+ ", deliveryVia=" + deliveryVia
				+ ", shipper=" + shipper
				+ ", displayed=" + displayed
				+ ", DocumentNo=" + DocumentNo
				+ ", deliveryDate=" + deliveryDate
				+ ", preparationDate=" + preparationDate
				+ ", freighCostRule=" + freighCostRule
				+ ", docType=" + docType
				+ ", productId=" + productId
				+ ", productName=" + productName
				+ ", key=" + key
				+ "]";
	}

	/**
	 * @return a copy of this object
	 */
	public TableRow copy()
	{
		final TableRow copy = new TableRow(
				bPartnerLocationId,
				shipmentScheduleId,
				qtyToDeliver,
				bpartnerId, bPartnerValue, bPartnerName, bPartnerLocationName,
				warehouseName,
				deliveryVia,
				shipper,
				displayed,
				key);
		
		copy.DocumentNo = this.DocumentNo;
		copy.deliveryDate = this.deliveryDate;
		copy.preparationDate = this.preparationDate;
		copy.freighCostRule = this.freighCostRule;
		copy.docType = this.docType;
		copy.productId = this.bpartnerId;
		copy.productName = this.productName;

		return copy;
	}

	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public Date getPreparationDate()
	{
		return preparationDate;
	}

	public void setPreparationDate(Date preparationDate)
	{
		this.preparationDate = preparationDate;
	}

	public String getDocumentNo()
	{
		return DocumentNo;
	}

	public void setDocumentNo(String documentNo)
	{
		DocumentNo = documentNo;
	}

	public int getBPartnerLocationId()
	{
		return bPartnerLocationId;
	}

	public int getShipmentScheduleId()
	{
		return shipmentScheduleId;
	}

	public BigDecimal getQtyToDeliver()
	{
		return qtyToDeliver;
	}
	
	public void setQtyToDeliver(final BigDecimal qtyToDeliver)
	{
		Check.assumeNotNull(qtyToDeliver, "qtyToDeliver not null");
		this.qtyToDeliver = qtyToDeliver;
	}

	public int getBPartnerId()
	{
		return bpartnerId;
	}

	public String getBPartnerValue()
	{
		return bPartnerValue;
	}

	public String getbPartnerName()
	{
		return bPartnerName;
	}

	public String getBPartnerAddress()
	{
		return key.getBPartnerAddress();
	}

	public String getBPartnerLocationName()
	{
		return bPartnerLocationName;
	}

	public String getWarehouseName()
	{
		return warehouseName;
	}

	public String getWarehouseDestName()
	{
		return warehouseDestName;
	}

	public int getWarehouseDestId()
	{
		return warehouseDestId;
	}

	public String getDeliveryVia()
	{
		return deliveryVia;
	}

	public String getShipper()
	{
		return shipper;
	}

	public TableRowKey getKey()
	{
		return key;
	}

	public boolean isDisplayed()
	{
		return displayed;
	}

	public String getFreighCostRule()
	{
		return freighCostRule;
	}

	public void setFreighCostRule(String freighCostRule)
	{
		this.freighCostRule = freighCostRule;
	}

	public String getDocType()
	{
		return docType;
	}

	public void setDocType(String docType)
	{
		this.docType = docType;
	}

	public void setWarehouseDestName(String warehouseDestName)
	{
		this.warehouseDestName = warehouseDestName;
	}

	public void setWarehouseDestId(int warehouseDestId)
	{
		this.warehouseDestId = warehouseDestId;
	}

	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}
}
