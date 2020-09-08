/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.desadvexport.compudata;

import java.io.Serializable;
import java.util.Date;

public class P102 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7098092225351128380L;

	private String record;
	private String partner;
	private String messageNo;
	private String positionNo;
	private String eanArtNo;
	private String buyerArtNo;
	private String supplierArtNo;
	private String artDescription;
	private String deliverQual;
	private String deliverQTY;
	private String deliverUnit;
	private String orderNo;
	private String orderPosNo;
	private String cUperTU;
	private String currency;
	private String detailPrice;
	private Date deliveryDate;
	private Date bestBeforeDate;
	private String chargenNo;
	private String articleClass;
	private String differenceQTY;
	private String discrepancyCode;
	private Date diffDeliveryDate;
	private String eanTU;
	private String storeNumber;
	private String unitCode;

	public String getRecord()
	{
		return record;
	}

	public void setRecord(final String record)
	{
		this.record = record;
	}

	public String getPartner()
	{
		return partner;
	}

	public void setPartner(final String partner)
	{
		this.partner = partner;
	}

	public String getMessageNo()
	{
		return messageNo;
	}

	public void setMessageNo(final String messageNo)
	{
		this.messageNo = messageNo;
	}

	public String getPositionNo()
	{
		return positionNo;
	}

	public void setPositionNo(final String positionNo)
	{
		this.positionNo = positionNo;
	}

	public String getEanArtNo()
	{
		return eanArtNo;
	}

	public void setEanArtNo(final String eanArtNo)
	{
		this.eanArtNo = eanArtNo;
	}

	public String getBuyerArtNo()
	{
		return buyerArtNo;
	}

	public void setBuyerArtNo(final String buyerArtNo)
	{
		this.buyerArtNo = buyerArtNo;
	}

	public String getSupplierArtNo()
	{
		return supplierArtNo;
	}

	public void setSupplierArtNo(final String supplierArtNo)
	{
		this.supplierArtNo = supplierArtNo;
	}

	public String getArtDescription()
	{
		return artDescription;
	}

	public void setArtDescription(final String artDescription)
	{
		this.artDescription = artDescription;
	}

	public String getDeliverQual()
	{
		return deliverQual;
	}

	public void setDeliverQual(final String deliverQual)
	{
		this.deliverQual = deliverQual;
	}

	public String getDeliverQTY()
	{
		return deliverQTY;
	}

	public void setDeliverQTY(final String deliverQTY)
	{
		this.deliverQTY = deliverQTY;
	}

	public String getDeliverUnit()
	{
		return deliverUnit;
	}

	public void setDeliverUnit(final String deliverUnit)
	{
		this.deliverUnit = deliverUnit;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(final String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getOrderPosNo()
	{
		return orderPosNo;
	}

	public void setOrderPosNo(final String orderPosNo)
	{
		this.orderPosNo = orderPosNo;
	}

	public String getcUperTU()
	{
		return cUperTU;
	}

	public void setcUperTU(final String cUperTU)
	{
		this.cUperTU = cUperTU;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(final String currency)
	{
		this.currency = currency;
	}

	public String getDetailPrice()
	{
		return detailPrice;
	}

	public void setDetailPrice(final String detailPrice)
	{
		this.detailPrice = detailPrice;
	}

	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(final Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public Date getBestBeforeDate()
	{
		return bestBeforeDate;
	}

	public void setBestBeforeDate(final Date bestBeforeDate)
	{
		this.bestBeforeDate = bestBeforeDate;
	}

	public String getChargenNo()
	{
		return chargenNo;
	}

	public void setChargenNo(final String chargenNo)
	{
		this.chargenNo = chargenNo;
	}

	public String getArticleClass()
	{
		return articleClass;
	}

	public void setArticleClass(final String articleClass)
	{
		this.articleClass = articleClass;
	}

	public String getDifferenceQTY()
	{
		return differenceQTY;
	}

	public void setDifferenceQTY(final String differenceQTY)
	{
		this.differenceQTY = differenceQTY;
	}

	public String getDiscrepancyCode()
	{
		return discrepancyCode;
	}

	public void setDiscrepancyCode(final String discrepancyCode)
	{
		this.discrepancyCode = discrepancyCode;
	}

	public Date getDiffDeliveryDate()
	{
		return diffDeliveryDate;
	}

	public void setDiffDeliveryDate(final Date diffDeliveryDate)
	{
		this.diffDeliveryDate = diffDeliveryDate;
	}

	public String getEanTU()
	{
		return eanTU;
	}

	public void setEanTU(final String eanTU)
	{
		this.eanTU = eanTU;
	}

	public String getStoreNumber()
	{
		return storeNumber;
	}

	public void setStoreNumber(final String storeNumber)
	{
		this.storeNumber = storeNumber;
	}

	public String getUnitCode()
	{
		return unitCode;
	}

	public void setUnitCode(final String unitCode)
	{
		this.unitCode = unitCode;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (artDescription == null ? 0 : artDescription.hashCode());
		result = prime * result + (articleClass == null ? 0 : articleClass.hashCode());
		result = prime * result + (bestBeforeDate == null ? 0 : bestBeforeDate.hashCode());
		result = prime * result + (buyerArtNo == null ? 0 : buyerArtNo.hashCode());
		result = prime * result + (cUperTU == null ? 0 : cUperTU.hashCode());
		result = prime * result + (chargenNo == null ? 0 : chargenNo.hashCode());
		result = prime * result + (currency == null ? 0 : currency.hashCode());
		result = prime * result + (deliverQTY == null ? 0 : deliverQTY.hashCode());
		result = prime * result + (deliverQual == null ? 0 : deliverQual.hashCode());
		result = prime * result + (deliverUnit == null ? 0 : deliverUnit.hashCode());
		result = prime * result + (deliveryDate == null ? 0 : deliveryDate.hashCode());
		result = prime * result + (detailPrice == null ? 0 : detailPrice.hashCode());
		result = prime * result + (diffDeliveryDate == null ? 0 : diffDeliveryDate.hashCode());
		result = prime * result + (differenceQTY == null ? 0 : differenceQTY.hashCode());
		result = prime * result + (discrepancyCode == null ? 0 : discrepancyCode.hashCode());
		result = prime * result + (eanArtNo == null ? 0 : eanArtNo.hashCode());
		result = prime * result + (eanTU == null ? 0 : eanTU.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (orderNo == null ? 0 : orderNo.hashCode());
		result = prime * result + (orderPosNo == null ? 0 : orderPosNo.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (positionNo == null ? 0 : positionNo.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (storeNumber == null ? 0 : storeNumber.hashCode());
		result = prime * result + (supplierArtNo == null ? 0 : supplierArtNo.hashCode());
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
		final P102 other = (P102)obj;
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
		if (bestBeforeDate == null)
		{
			if (other.bestBeforeDate != null)
			{
				return false;
			}
		}
		else if (!bestBeforeDate.equals(other.bestBeforeDate))
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
		if (cUperTU == null)
		{
			if (other.cUperTU != null)
			{
				return false;
			}
		}
		else if (!cUperTU.equals(other.cUperTU))
		{
			return false;
		}
		if (chargenNo == null)
		{
			if (other.chargenNo != null)
			{
				return false;
			}
		}
		else if (!chargenNo.equals(other.chargenNo))
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
		if (deliverQTY == null)
		{
			if (other.deliverQTY != null)
			{
				return false;
			}
		}
		else if (!deliverQTY.equals(other.deliverQTY))
		{
			return false;
		}
		if (deliverQual == null)
		{
			if (other.deliverQual != null)
			{
				return false;
			}
		}
		else if (!deliverQual.equals(other.deliverQual))
		{
			return false;
		}
		if (deliverUnit == null)
		{
			if (other.deliverUnit != null)
			{
				return false;
			}
		}
		else if (!deliverUnit.equals(other.deliverUnit))
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
		if (diffDeliveryDate == null)
		{
			if (other.diffDeliveryDate != null)
			{
				return false;
			}
		}
		else if (!diffDeliveryDate.equals(other.diffDeliveryDate))
		{
			return false;
		}
		if (differenceQTY == null)
		{
			if (other.differenceQTY != null)
			{
				return false;
			}
		}
		else if (!differenceQTY.equals(other.differenceQTY))
		{
			return false;
		}
		if (discrepancyCode == null)
		{
			if (other.discrepancyCode != null)
			{
				return false;
			}
		}
		else if (!discrepancyCode.equals(other.discrepancyCode))
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
		if (eanTU == null)
		{
			if (other.eanTU != null)
			{
				return false;
			}
		}
		else if (!eanTU.equals(other.eanTU))
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
		if (orderNo == null)
		{
			if (other.orderNo != null)
			{
				return false;
			}
		}
		else if (!orderNo.equals(other.orderNo))
		{
			return false;
		}
		if (orderPosNo == null)
		{
			if (other.orderPosNo != null)
			{
				return false;
			}
		}
		else if (!orderPosNo.equals(other.orderPosNo))
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
		if (storeNumber == null)
		{
			if (other.storeNumber != null)
			{
				return false;
			}
		}
		else if (!storeNumber.equals(other.storeNumber))
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
		return "P102 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", positionNo=" + positionNo + ", eanArtNo=" + eanArtNo + ", buyerArtNo=" + buyerArtNo
				+ ", supplierArtNo=" + supplierArtNo + ", artDescription=" + artDescription + ", deliverQual=" + deliverQual + ", deliverQTY=" + deliverQTY + ", deliverUnit=" + deliverUnit
				+ ", orderNo=" + orderNo + ", orderPosNo=" + orderPosNo + ", cUperTU=" + cUperTU + ", currency=" + currency + ", detailPrice=" + detailPrice + ", deliveryDate=" + deliveryDate
				+ ", bestBeforeDate=" + bestBeforeDate + ", chargenNo=" + chargenNo + ", articleClass=" + articleClass + ", differenceQTY=" + differenceQTY + ", discrepancyCode=" + discrepancyCode
				+ ", diffDeliveryDate=" + diffDeliveryDate + ", eanTU=" + eanTU + ", storeNumber=" + storeNumber + ", unitCode=" + unitCode + "]";
	}
}
