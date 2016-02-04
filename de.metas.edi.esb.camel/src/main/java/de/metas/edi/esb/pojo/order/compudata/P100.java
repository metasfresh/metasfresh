package de.metas.edi.esb.pojo.order.compudata;

/*
 * #%L
 * de.metas.edi.esb
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


import java.io.Serializable;

@SuppressWarnings("PMD.ExcessiveClassLength")
public class P100 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4766634448020424455L;

	private String record;
	private String partner;
	private String messageNo;
	private String positionNo;
	private String eanArtNo;
	private String buyerArtNo;
	private String supplierArtNo;
	private String orderQty;
	private String orderUnit;
	private String cuperTU;
	private String currency;
	private String buyerPrice;
	private String buyerPriceTypCode;
	private String buyerPriceTypQual;
	private String buyerPriceBase;
	private String buyerPriceUnit;
	private String detailPrice;
	private String detailPriceTypCode;
	private String detailPriceTypQual;
	private String detailPriceBase;
	private String detailPriceUnit;
	private String actionPrice;
	private String actionPriceTypQual;
	private String deliveryDate;
	private String taxRate;
	private String positionAmount;
	private String articleClass;
	private String customerRefNo;
	private String promoDealNo;
	private String bossWorld;
	private String customerOrderNo;
	private String customerPosNo;
	private String artDescription;
	private String priceCode;
	private String stmQty1;
	private String stmCode1;
	private String actionBeginDate;
	private String actionEndDate;
	private String filialIDEan;
	private String filialQty;
	private String filialQtyUnit;
	private String unitCode;

	public final String getRecord()
	{
		return record;
	}

	public final void setRecord(final String record)
	{
		this.record = record;
	}

	public final String getPartner()
	{
		return partner;
	}

	public final void setPartner(final String partner)
	{
		this.partner = partner;
	}

	public final String getMessageNo()
	{
		return messageNo;
	}

	public final void setMessageNo(final String messageNo)
	{
		this.messageNo = messageNo;
	}

	public final String getPositionNo()
	{
		return positionNo;
	}

	public final void setPositionNo(final String positionNo)
	{
		this.positionNo = positionNo;
	}

	public final String getEanArtNo()
	{
		return eanArtNo;
	}

	public final void setEanArtNo(final String eanArtNo)
	{
		this.eanArtNo = eanArtNo;
	}

	public final String getBuyerArtNo()
	{
		return buyerArtNo;
	}

	public final void setBuyerArtNo(final String buyerArtNo)
	{
		this.buyerArtNo = buyerArtNo;
	}

	public final String getSupplierArtNo()
	{
		return supplierArtNo;
	}

	public final void setSupplierArtNo(final String supplierArtNo)
	{
		this.supplierArtNo = supplierArtNo;
	}

	public final String getOrderQty()
	{
		return orderQty;
	}

	public final void setOrderQty(final String orderQty)
	{
		this.orderQty = orderQty;
	}

	public final String getOrderUnit()
	{
		return orderUnit;
	}

	public final void setOrderUnit(final String orderUnit)
	{
		this.orderUnit = orderUnit;
	}

	public final String getCuperTU()
	{
		return cuperTU;
	}

	public final void setCuperTU(final String cuperTU)
	{
		this.cuperTU = cuperTU;
	}

	public final String getCurrency()
	{
		return currency;
	}

	public final void setCurrency(final String currency)
	{
		this.currency = currency;
	}

	public final String getBuyerPrice()
	{
		return buyerPrice;
	}

	public final void setBuyerPrice(final String buyerPrice)
	{
		this.buyerPrice = buyerPrice;
	}

	public final String getBuyerPriceTypCode()
	{
		return buyerPriceTypCode;
	}

	public final void setBuyerPriceTypCode(final String buyerPriceTypCode)
	{
		this.buyerPriceTypCode = buyerPriceTypCode;
	}

	public final String getBuyerPriceTypQual()
	{
		return buyerPriceTypQual;
	}

	public final void setBuyerPriceTypQual(final String buyerPriceTypQual)
	{
		this.buyerPriceTypQual = buyerPriceTypQual;
	}

	public final String getBuyerPriceBase()
	{
		return buyerPriceBase;
	}

	public final void setBuyerPriceBase(final String buyerPriceBase)
	{
		this.buyerPriceBase = buyerPriceBase;
	}

	public final String getBuyerPriceUnit()
	{
		return buyerPriceUnit;
	}

	public final void setBuyerPriceUnit(final String buyerPriceUnit)
	{
		this.buyerPriceUnit = buyerPriceUnit;
	}

	public final String getDetailPrice()
	{
		return detailPrice;
	}

	public final void setDetailPrice(final String detailPrice)
	{
		this.detailPrice = detailPrice;
	}

	public final String getDetailPriceTypCode()
	{
		return detailPriceTypCode;
	}

	public final void setDetailPriceTypCode(final String detailPriceTypCode)
	{
		this.detailPriceTypCode = detailPriceTypCode;
	}

	public final String getDetailPriceTypQual()
	{
		return detailPriceTypQual;
	}

	public final void setDetailPriceTypQual(final String detailPriceTypQual)
	{
		this.detailPriceTypQual = detailPriceTypQual;
	}

	public final String getDetailPriceBase()
	{
		return detailPriceBase;
	}

	public final void setDetailPriceBase(final String detailPriceBase)
	{
		this.detailPriceBase = detailPriceBase;
	}

	public final String getDetailPriceUnit()
	{
		return detailPriceUnit;
	}

	public final void setDetailPriceUnit(final String detailPriceUnit)
	{
		this.detailPriceUnit = detailPriceUnit;
	}

	public final String getActionPrice()
	{
		return actionPrice;
	}

	public final void setActionPrice(final String actionPrice)
	{
		this.actionPrice = actionPrice;
	}

	public final String getActionPriceTypQual()
	{
		return actionPriceTypQual;
	}

	public final void setActionPriceTypQual(final String actionPriceTypQual)
	{
		this.actionPriceTypQual = actionPriceTypQual;
	}

	public final String getDeliveryDate()
	{
		return deliveryDate;
	}

	public final void setDeliveryDate(final String deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public final String getTaxRate()
	{
		return taxRate;
	}

	public final void setTaxRate(final String taxRate)
	{
		this.taxRate = taxRate;
	}

	public final String getPositionAmount()
	{
		return positionAmount;
	}

	public final void setPositionAmount(final String positionAmount)
	{
		this.positionAmount = positionAmount;
	}

	public final String getArticleClass()
	{
		return articleClass;
	}

	public final void setArticleClass(final String articleClass)
	{
		this.articleClass = articleClass;
	}

	public final String getCustomerRefNo()
	{
		return customerRefNo;
	}

	public final void setCustomerRefNo(final String customerRefNo)
	{
		this.customerRefNo = customerRefNo;
	}

	public final String getPromoDealNo()
	{
		return promoDealNo;
	}

	public final void setPromoDealNo(final String promoDealNo)
	{
		this.promoDealNo = promoDealNo;
	}

	public final String getBossWorld()
	{
		return bossWorld;
	}

	public final void setBossWorld(final String bossWorld)
	{
		this.bossWorld = bossWorld;
	}

	public final String getCustomerOrderNo()
	{
		return customerOrderNo;
	}

	public final void setCustomerOrderNo(final String customerOrderNo)
	{
		this.customerOrderNo = customerOrderNo;
	}

	public final String getCustomerPosNo()
	{
		return customerPosNo;
	}

	public final void setCustomerPosNo(final String customerPosNo)
	{
		this.customerPosNo = customerPosNo;
	}

	public final String getArtDescription()
	{
		return artDescription;
	}

	public final void setArtDescription(final String artDescription)
	{
		this.artDescription = artDescription;
	}

	public final String getPriceCode()
	{
		return priceCode;
	}

	public final void setPriceCode(final String priceCode)
	{
		this.priceCode = priceCode;
	}

	public final String getStmQty1()
	{
		return stmQty1;
	}

	public final void setStmQty1(final String stmQty1)
	{
		this.stmQty1 = stmQty1;
	}

	public final String getStmCode1()
	{
		return stmCode1;
	}

	public final void setStmCode1(final String stmCode1)
	{
		this.stmCode1 = stmCode1;
	}

	public final String getActionBeginDate()
	{
		return actionBeginDate;
	}

	public final void setActionBeginDate(final String actionBeginDate)
	{
		this.actionBeginDate = actionBeginDate;
	}

	public final String getActionEndDate()
	{
		return actionEndDate;
	}

	public final void setActionEndDate(final String actionEndDate)
	{
		this.actionEndDate = actionEndDate;
	}

	public final String getFilialIDEan()
	{
		return filialIDEan;
	}

	public final void setFilialIDEan(final String filialIDEan)
	{
		this.filialIDEan = filialIDEan;
	}

	public final String getFilialQty()
	{
		return filialQty;
	}

	public final void setFilialQty(final String filialQty)
	{
		this.filialQty = filialQty;
	}

	public final String getFilialQtyUnit()
	{
		return filialQtyUnit;
	}

	public final void setFilialQtyUnit(final String filialQtyUnit)
	{
		this.filialQtyUnit = filialQtyUnit;
	}

	public final String getUnitCode()
	{
		return unitCode;
	}

	public final void setUnitCode(final String unitCode)
	{
		this.unitCode = unitCode;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (actionBeginDate == null ? 0 : actionBeginDate.hashCode());
		result = prime * result + (actionEndDate == null ? 0 : actionEndDate.hashCode());
		result = prime * result + (actionPrice == null ? 0 : actionPrice.hashCode());
		result = prime * result + (actionPriceTypQual == null ? 0 : actionPriceTypQual.hashCode());
		result = prime * result + (artDescription == null ? 0 : artDescription.hashCode());
		result = prime * result + (articleClass == null ? 0 : articleClass.hashCode());
		result = prime * result + (bossWorld == null ? 0 : bossWorld.hashCode());
		result = prime * result + (buyerArtNo == null ? 0 : buyerArtNo.hashCode());
		result = prime * result + (buyerPrice == null ? 0 : buyerPrice.hashCode());
		result = prime * result + (buyerPriceBase == null ? 0 : buyerPriceBase.hashCode());
		result = prime * result + (buyerPriceTypCode == null ? 0 : buyerPriceTypCode.hashCode());
		result = prime * result + (buyerPriceTypQual == null ? 0 : buyerPriceTypQual.hashCode());
		result = prime * result + (buyerPriceUnit == null ? 0 : buyerPriceUnit.hashCode());
		result = prime * result + (cuperTU == null ? 0 : cuperTU.hashCode());
		result = prime * result + (currency == null ? 0 : currency.hashCode());
		result = prime * result + (customerOrderNo == null ? 0 : customerOrderNo.hashCode());
		result = prime * result + (customerPosNo == null ? 0 : customerPosNo.hashCode());
		result = prime * result + (customerRefNo == null ? 0 : customerRefNo.hashCode());
		result = prime * result + (deliveryDate == null ? 0 : deliveryDate.hashCode());
		result = prime * result + (detailPrice == null ? 0 : detailPrice.hashCode());
		result = prime * result + (detailPriceBase == null ? 0 : detailPriceBase.hashCode());
		result = prime * result + (detailPriceTypCode == null ? 0 : detailPriceTypCode.hashCode());
		result = prime * result + (detailPriceTypQual == null ? 0 : detailPriceTypQual.hashCode());
		result = prime * result + (detailPriceUnit == null ? 0 : detailPriceUnit.hashCode());
		result = prime * result + (eanArtNo == null ? 0 : eanArtNo.hashCode());
		result = prime * result + (filialIDEan == null ? 0 : filialIDEan.hashCode());
		result = prime * result + (filialQty == null ? 0 : filialQty.hashCode());
		result = prime * result + (filialQtyUnit == null ? 0 : filialQtyUnit.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (orderQty == null ? 0 : orderQty.hashCode());
		result = prime * result + (orderUnit == null ? 0 : orderUnit.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (positionAmount == null ? 0 : positionAmount.hashCode());
		result = prime * result + (positionNo == null ? 0 : positionNo.hashCode());
		result = prime * result + (priceCode == null ? 0 : priceCode.hashCode());
		result = prime * result + (promoDealNo == null ? 0 : promoDealNo.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (stmCode1 == null ? 0 : stmCode1.hashCode());
		result = prime * result + (stmQty1 == null ? 0 : stmQty1.hashCode());
		result = prime * result + (supplierArtNo == null ? 0 : supplierArtNo.hashCode());
		result = prime * result + (taxRate == null ? 0 : taxRate.hashCode());
		result = prime * result + (unitCode == null ? 0 : unitCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) // NOPMD by al
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final P100 other = (P100)obj;
		if (actionBeginDate == null)
		{
			if (other.actionBeginDate != null)
			{
				return false;
			}
		}
		else if (!actionBeginDate.equals(other.actionBeginDate))
		{
			return false;
		}
		if (actionEndDate == null)
		{
			if (other.actionEndDate != null)
			{
				return false;
			}
		}
		else if (!actionEndDate.equals(other.actionEndDate))
		{
			return false;
		}
		if (actionPrice == null)
		{
			if (other.actionPrice != null)
			{
				return false;
			}
		}
		else if (!actionPrice.equals(other.actionPrice))
		{
			return false;
		}
		if (actionPriceTypQual == null)
		{
			if (other.actionPriceTypQual != null)
			{
				return false;
			}
		}
		else if (!actionPriceTypQual.equals(other.actionPriceTypQual))
		{
			return false;
		}
		if (artDescription == null)
		{
			if (other.artDescription != null)
			{
				return false;
			}
		}
		else if (!artDescription.equals(other.artDescription))
		{
			return false;
		}
		if (articleClass == null)
		{
			if (other.articleClass != null)
			{
				return false;
			}
		}
		else if (!articleClass.equals(other.articleClass))
		{
			return false;
		}
		if (bossWorld == null)
		{
			if (other.bossWorld != null)
			{
				return false;
			}
		}
		else if (!bossWorld.equals(other.bossWorld))
		{
			return false;
		}
		if (buyerArtNo == null)
		{
			if (other.buyerArtNo != null)
			{
				return false;
			}
		}
		else if (!buyerArtNo.equals(other.buyerArtNo))
		{
			return false;
		}
		if (buyerPrice == null)
		{
			if (other.buyerPrice != null)
			{
				return false;
			}
		}
		else if (!buyerPrice.equals(other.buyerPrice))
		{
			return false;
		}
		if (buyerPriceBase == null)
		{
			if (other.buyerPriceBase != null)
			{
				return false;
			}
		}
		else if (!buyerPriceBase.equals(other.buyerPriceBase))
		{
			return false;
		}
		if (buyerPriceTypCode == null)
		{
			if (other.buyerPriceTypCode != null)
			{
				return false;
			}
		}
		else if (!buyerPriceTypCode.equals(other.buyerPriceTypCode))
		{
			return false;
		}
		if (buyerPriceTypQual == null)
		{
			if (other.buyerPriceTypQual != null)
			{
				return false;
			}
		}
		else if (!buyerPriceTypQual.equals(other.buyerPriceTypQual))
		{
			return false;
		}
		if (buyerPriceUnit == null)
		{
			if (other.buyerPriceUnit != null)
			{
				return false;
			}
		}
		else if (!buyerPriceUnit.equals(other.buyerPriceUnit))
		{
			return false;
		}
		if (cuperTU == null)
		{
			if (other.cuperTU != null)
			{
				return false;
			}
		}
		else if (!cuperTU.equals(other.cuperTU))
		{
			return false;
		}
		if (currency == null)
		{
			if (other.currency != null)
			{
				return false;
			}
		}
		else if (!currency.equals(other.currency))
		{
			return false;
		}
		if (customerOrderNo == null)
		{
			if (other.customerOrderNo != null)
			{
				return false;
			}
		}
		else if (!customerOrderNo.equals(other.customerOrderNo))
		{
			return false;
		}
		if (customerPosNo == null)
		{
			if (other.customerPosNo != null)
			{
				return false;
			}
		}
		else if (!customerPosNo.equals(other.customerPosNo))
		{
			return false;
		}
		if (customerRefNo == null)
		{
			if (other.customerRefNo != null)
			{
				return false;
			}
		}
		else if (!customerRefNo.equals(other.customerRefNo))
		{
			return false;
		}
		if (deliveryDate == null)
		{
			if (other.deliveryDate != null)
			{
				return false;
			}
		}
		else if (!deliveryDate.equals(other.deliveryDate))
		{
			return false;
		}
		if (detailPrice == null)
		{
			if (other.detailPrice != null)
			{
				return false;
			}
		}
		else if (!detailPrice.equals(other.detailPrice))
		{
			return false;
		}
		if (detailPriceBase == null)
		{
			if (other.detailPriceBase != null)
			{
				return false;
			}
		}
		else if (!detailPriceBase.equals(other.detailPriceBase))
		{
			return false;
		}
		if (detailPriceTypCode == null)
		{
			if (other.detailPriceTypCode != null)
			{
				return false;
			}
		}
		else if (!detailPriceTypCode.equals(other.detailPriceTypCode))
		{
			return false;
		}
		if (detailPriceTypQual == null)
		{
			if (other.detailPriceTypQual != null)
			{
				return false;
			}
		}
		else if (!detailPriceTypQual.equals(other.detailPriceTypQual))
		{
			return false;
		}
		if (detailPriceUnit == null)
		{
			if (other.detailPriceUnit != null)
			{
				return false;
			}
		}
		else if (!detailPriceUnit.equals(other.detailPriceUnit))
		{
			return false;
		}
		if (eanArtNo == null)
		{
			if (other.eanArtNo != null)
			{
				return false;
			}
		}
		else if (!eanArtNo.equals(other.eanArtNo))
		{
			return false;
		}
		if (filialIDEan == null)
		{
			if (other.filialIDEan != null)
			{
				return false;
			}
		}
		else if (!filialIDEan.equals(other.filialIDEan))
		{
			return false;
		}
		if (filialQty == null)
		{
			if (other.filialQty != null)
			{
				return false;
			}
		}
		else if (!filialQty.equals(other.filialQty))
		{
			return false;
		}
		if (filialQtyUnit == null)
		{
			if (other.filialQtyUnit != null)
			{
				return false;
			}
		}
		else if (!filialQtyUnit.equals(other.filialQtyUnit))
		{
			return false;
		}
		if (messageNo == null)
		{
			if (other.messageNo != null)
			{
				return false;
			}
		}
		else if (!messageNo.equals(other.messageNo))
		{
			return false;
		}
		if (orderQty == null)
		{
			if (other.orderQty != null)
			{
				return false;
			}
		}
		else if (!orderQty.equals(other.orderQty))
		{
			return false;
		}
		if (orderUnit == null)
		{
			if (other.orderUnit != null)
			{
				return false;
			}
		}
		else if (!orderUnit.equals(other.orderUnit))
		{
			return false;
		}
		if (partner == null)
		{
			if (other.partner != null)
			{
				return false;
			}
		}
		else if (!partner.equals(other.partner))
		{
			return false;
		}
		if (positionAmount == null)
		{
			if (other.positionAmount != null)
			{
				return false;
			}
		}
		else if (!positionAmount.equals(other.positionAmount))
		{
			return false;
		}
		if (positionNo == null)
		{
			if (other.positionNo != null)
			{
				return false;
			}
		}
		else if (!positionNo.equals(other.positionNo))
		{
			return false;
		}
		if (priceCode == null)
		{
			if (other.priceCode != null)
			{
				return false;
			}
		}
		else if (!priceCode.equals(other.priceCode))
		{
			return false;
		}
		if (promoDealNo == null)
		{
			if (other.promoDealNo != null)
			{
				return false;
			}
		}
		else if (!promoDealNo.equals(other.promoDealNo))
		{
			return false;
		}
		if (record == null)
		{
			if (other.record != null)
			{
				return false;
			}
		}
		else if (!record.equals(other.record))
		{
			return false;
		}
		if (stmCode1 == null)
		{
			if (other.stmCode1 != null)
			{
				return false;
			}
		}
		else if (!stmCode1.equals(other.stmCode1))
		{
			return false;
		}
		if (stmQty1 == null)
		{
			if (other.stmQty1 != null)
			{
				return false;
			}
		}
		else if (!stmQty1.equals(other.stmQty1))
		{
			return false;
		}
		if (supplierArtNo == null)
		{
			if (other.supplierArtNo != null)
			{
				return false;
			}
		}
		else if (!supplierArtNo.equals(other.supplierArtNo))
		{
			return false;
		}
		if (taxRate == null)
		{
			if (other.taxRate != null)
			{
				return false;
			}
		}
		else if (!taxRate.equals(other.taxRate))
		{
			return false;
		}
		if (unitCode == null)
		{
			if (other.unitCode != null)
			{
				return false;
			}
		}
		else if (!unitCode.equals(other.unitCode))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "P100 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", positionNo=" + positionNo + ", eanArtNo=" + eanArtNo + ", buyerArtNo="
				+ buyerArtNo
				+ ", supplierArtNo=" + supplierArtNo + ", orderQty=" + orderQty + ", orderUnit=" + orderUnit + ", cuperTU=" + cuperTU + ", currency=" + currency
				+ ", buyerPrice=" + buyerPrice
				+ ", buyerPriceTypCode=" + buyerPriceTypCode + ", buyerPriceTypQual=" + buyerPriceTypQual + ", buyerPriceBase=" + buyerPriceBase + ", buyerPriceUnit="
				+ buyerPriceUnit
				+ ", detailPrice=" + detailPrice + ", detailPriceTypCode=" + detailPriceTypCode + ", detailPriceTypQual=" + detailPriceTypQual + ", detailPriceBase="
				+ detailPriceBase
				+ ", detailPriceUnit=" + detailPriceUnit + ", actionPrice=" + actionPrice + ", actionPriceTypQual=" + actionPriceTypQual + ", deliveryDate=" + deliveryDate
				+ ", taxRate=" + taxRate
				+ ", positionAmount=" + positionAmount + ", articleClass=" + articleClass + ", customerRefNo=" + customerRefNo + ", promoDealNo=" + promoDealNo + ", bossWorld="
				+ bossWorld
				+ ", customerOrderNo=" + customerOrderNo + ", customerPosNo=" + customerPosNo + ", artDescription=" + artDescription + ", priceCode=" + priceCode + ", stmQty1="
				+ stmQty1
				+ ", stmCode1=" + stmCode1 + ", actionBeginDate=" + actionBeginDate + ", actionEndDate=" + actionEndDate + ", filialIDEan=" + filialIDEan + ", filialQty="
				+ filialQty
				+ ", filialQtyUnit=" + filialQtyUnit + ", unitCode=" + unitCode + "]";
	}
}
