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

public class H100 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2062467075160764132L;

	private String record;
	private String partner;
	private String messageNo;
	private String messageTyp;
	private String messageVersion;
	private String messageRelease;
	private String controllingAgency;
	private String associationCode;
	private String documentCode;
	private String documentNo;
	private Date messageDate;
	private Date deliveryDate;
	private String buyerID;
	private String supplierID;
	private String deliveryID;
	private String storeNumber;
	private String invoiceID;
	private String orderNumber;
	private Date orderDate;
	private String despatchNoteNumber;
	private String referenceCode;
	private String prodGrpCode;
	private String supplierNo;
	private String transportMode;
	private String transportReference;
	private String transportCode;
	private String transportName;
	private String rampeID;
	private String deliveryName;
	private String storeName;

	private P050 p050;

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

	public String getMessageTyp()
	{
		return messageTyp;
	}

	public void setMessageTyp(final String messageTyp)
	{
		this.messageTyp = messageTyp;
	}

	public String getMessageVersion()
	{
		return messageVersion;
	}

	public void setMessageVersion(final String messageVersion)
	{
		this.messageVersion = messageVersion;
	}

	public String getMessageRelease()
	{
		return messageRelease;
	}

	public void setMessageRelease(final String messageRelease)
	{
		this.messageRelease = messageRelease;
	}

	public String getControllingAgency()
	{
		return controllingAgency;
	}

	public void setControllingAgency(final String controllingAgency)
	{
		this.controllingAgency = controllingAgency;
	}

	public String getAssociationCode()
	{
		return associationCode;
	}

	public void setAssociationCode(final String associationCode)
	{
		this.associationCode = associationCode;
	}

	public String getDocumentCode()
	{
		return documentCode;
	}

	public void setDocumentCode(final String documentCode)
	{
		this.documentCode = documentCode;
	}

	public String getDocumentNo()
	{
		return documentNo;
	}

	public void setDocumentNo(final String documentNo)
	{
		this.documentNo = documentNo;
	}

	public Date getMessageDate()
	{
		return messageDate;
	}

	public void setMessageDate(final Date messageDate)
	{
		this.messageDate = messageDate;
	}

	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(final Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public String getBuyerID()
	{
		return buyerID;
	}

	public void setBuyerID(final String buyerID)
	{
		this.buyerID = buyerID;
	}

	public String getSupplierID()
	{
		return supplierID;
	}

	public void setSupplierID(final String supplierID)
	{
		this.supplierID = supplierID;
	}

	public String getDeliveryID()
	{
		return deliveryID;
	}

	public void setDeliveryID(final String deliveryID)
	{
		this.deliveryID = deliveryID;
	}

	public String getStoreNumber()
	{
		return storeNumber;
	}

	public void setStoreNumber(final String storeNumber)
	{
		this.storeNumber = storeNumber;
	}

	public String getInvoiceID()
	{
		return invoiceID;
	}

	public void setInvoiceID(final String invoiceID)
	{
		this.invoiceID = invoiceID;
	}

	public String getOrderNumber()
	{
		return orderNumber;
	}

	public void setOrderNumber(final String orderNumber)
	{
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate()
	{
		return orderDate;
	}

	public void setOrderDate(final Date orderDate)
	{
		this.orderDate = orderDate;
	}

	public String getDespatchNoteNumber()
	{
		return despatchNoteNumber;
	}

	public void setDespatchNoteNumber(final String despatchNoteNumber)
	{
		this.despatchNoteNumber = despatchNoteNumber;
	}

	public String getReferenceCode()
	{
		return referenceCode;
	}

	public void setReferenceCode(final String referenceCode)
	{
		this.referenceCode = referenceCode;
	}

	public String getProdGrpCode()
	{
		return prodGrpCode;
	}

	public void setProdGrpCode(final String prodGrpCode)
	{
		this.prodGrpCode = prodGrpCode;
	}

	public String getSupplierNo()
	{
		return supplierNo;
	}

	public void setSupplierNo(final String supplierNo)
	{
		this.supplierNo = supplierNo;
	}

	public String getTransportMode()
	{
		return transportMode;
	}

	public void setTransportMode(final String transportMode)
	{
		this.transportMode = transportMode;
	}

	public String getTransportReference()
	{
		return transportReference;
	}

	public void setTransportReference(final String transportReference)
	{
		this.transportReference = transportReference;
	}

	public String getTransportCode()
	{
		return transportCode;
	}

	public void setTransportCode(final String transportCode)
	{
		this.transportCode = transportCode;
	}

	public String getTransportName()
	{
		return transportName;
	}

	public void setTransportName(final String transportName)
	{
		this.transportName = transportName;
	}

	public String getRampeID()
	{
		return rampeID;
	}

	public void setRampeID(final String rampeID)
	{
		this.rampeID = rampeID;
	}

	public String getDeliveryName()
	{
		return deliveryName;
	}

	public void setDeliveryName(final String deliveryName)
	{
		this.deliveryName = deliveryName;
	}

	public String getStoreName()
	{
		return storeName;
	}

	public void setStoreName(final String storeName)
	{
		this.storeName = storeName;
	}

	public P050 getP050()
	{
		return p050;
	}

	public void setP050(final P050 p050)
	{
		this.p050 = p050;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (associationCode == null ? 0 : associationCode.hashCode());
		result = prime * result + (buyerID == null ? 0 : buyerID.hashCode());
		result = prime * result + (controllingAgency == null ? 0 : controllingAgency.hashCode());
		result = prime * result + (deliveryDate == null ? 0 : deliveryDate.hashCode());
		result = prime * result + (deliveryID == null ? 0 : deliveryID.hashCode());
		result = prime * result + (deliveryName == null ? 0 : deliveryName.hashCode());
		result = prime * result + (despatchNoteNumber == null ? 0 : despatchNoteNumber.hashCode());
		result = prime * result + (documentCode == null ? 0 : documentCode.hashCode());
		result = prime * result + (documentNo == null ? 0 : documentNo.hashCode());
		result = prime * result + (invoiceID == null ? 0 : invoiceID.hashCode());
		result = prime * result + (messageDate == null ? 0 : messageDate.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (messageRelease == null ? 0 : messageRelease.hashCode());
		result = prime * result + (messageTyp == null ? 0 : messageTyp.hashCode());
		result = prime * result + (messageVersion == null ? 0 : messageVersion.hashCode());
		result = prime * result + (orderDate == null ? 0 : orderDate.hashCode());
		result = prime * result + (orderNumber == null ? 0 : orderNumber.hashCode());
		result = prime * result + (p050 == null ? 0 : p050.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (prodGrpCode == null ? 0 : prodGrpCode.hashCode());
		result = prime * result + (rampeID == null ? 0 : rampeID.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (referenceCode == null ? 0 : referenceCode.hashCode());
		result = prime * result + (storeName == null ? 0 : storeName.hashCode());
		result = prime * result + (storeNumber == null ? 0 : storeNumber.hashCode());
		result = prime * result + (supplierID == null ? 0 : supplierID.hashCode());
		result = prime * result + (supplierNo == null ? 0 : supplierNo.hashCode());
		result = prime * result + (transportCode == null ? 0 : transportCode.hashCode());
		result = prime * result + (transportMode == null ? 0 : transportMode.hashCode());
		result = prime * result + (transportName == null ? 0 : transportName.hashCode());
		result = prime * result + (transportReference == null ? 0 : transportReference.hashCode());
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
		final H100 other = (H100)obj;
		if (associationCode == null)
		{
			if (other.associationCode != null)
			{
				return false;
			}
		}
		else if (!associationCode.equals(other.associationCode))
		{
			return false;
		}
		if (buyerID == null)
		{
			if (other.buyerID != null)
			{
				return false;
			}
		}
		else if (!buyerID.equals(other.buyerID))
		{
			return false;
		}
		if (controllingAgency == null)
		{
			if (other.controllingAgency != null)
			{
				return false;
			}
		}
		else if (!controllingAgency.equals(other.controllingAgency))
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
		if (deliveryID == null)
		{
			if (other.deliveryID != null)
			{
				return false;
			}
		}
		else if (!deliveryID.equals(other.deliveryID))
		{
			return false;
		}
		if (deliveryName == null)
		{
			if (other.deliveryName != null)
			{
				return false;
			}
		}
		else if (!deliveryName.equals(other.deliveryName))
		{
			return false;
		}
		if (despatchNoteNumber == null)
		{
			if (other.despatchNoteNumber != null)
			{
				return false;
			}
		}
		else if (!despatchNoteNumber.equals(other.despatchNoteNumber))
		{
			return false;
		}
		if (documentCode == null)
		{
			if (other.documentCode != null)
			{
				return false;
			}
		}
		else if (!documentCode.equals(other.documentCode))
		{
			return false;
		}
		if (documentNo == null)
		{
			if (other.documentNo != null)
			{
				return false;
			}
		}
		else if (!documentNo.equals(other.documentNo))
		{
			return false;
		}
		if (invoiceID == null)
		{
			if (other.invoiceID != null)
			{
				return false;
			}
		}
		else if (!invoiceID.equals(other.invoiceID))
		{
			return false;
		}
		if (messageDate == null)
		{
			if (other.messageDate != null)
			{
				return false;
			}
		}
		else if (!messageDate.equals(other.messageDate))
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
		if (messageRelease == null)
		{
			if (other.messageRelease != null)
			{
				return false;
			}
		}
		else if (!messageRelease.equals(other.messageRelease))
		{
			return false;
		}
		if (messageTyp == null)
		{
			if (other.messageTyp != null)
			{
				return false;
			}
		}
		else if (!messageTyp.equals(other.messageTyp))
		{
			return false;
		}
		if (messageVersion == null)
		{
			if (other.messageVersion != null)
			{
				return false;
			}
		}
		else if (!messageVersion.equals(other.messageVersion))
		{
			return false;
		}
		if (orderDate == null)
		{
			if (other.orderDate != null)
			{
				return false;
			}
		}
		else if (!orderDate.equals(other.orderDate))
		{
			return false;
		}
		if (orderNumber == null)
		{
			if (other.orderNumber != null)
			{
				return false;
			}
		}
		else if (!orderNumber.equals(other.orderNumber))
		{
			return false;
		}
		if (p050 == null)
		{
			if (other.p050 != null)
			{
				return false;
			}
		}
		else if (!p050.equals(other.p050))
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
		if (prodGrpCode == null)
		{
			if (other.prodGrpCode != null)
			{
				return false;
			}
		}
		else if (!prodGrpCode.equals(other.prodGrpCode))
		{
			return false;
		}
		if (rampeID == null)
		{
			if (other.rampeID != null)
			{
				return false;
			}
		}
		else if (!rampeID.equals(other.rampeID))
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
		if (referenceCode == null)
		{
			if (other.referenceCode != null)
			{
				return false;
			}
		}
		else if (!referenceCode.equals(other.referenceCode))
		{
			return false;
		}
		if (storeName == null)
		{
			if (other.storeName != null)
			{
				return false;
			}
		}
		else if (!storeName.equals(other.storeName))
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
		if (supplierID == null)
		{
			if (other.supplierID != null)
			{
				return false;
			}
		}
		else if (!supplierID.equals(other.supplierID))
		{
			return false;
		}
		if (supplierNo == null)
		{
			if (other.supplierNo != null)
			{
				return false;
			}
		}
		else if (!supplierNo.equals(other.supplierNo))
		{
			return false;
		}
		if (transportCode == null)
		{
			if (other.transportCode != null)
			{
				return false;
			}
		}
		else if (!transportCode.equals(other.transportCode))
		{
			return false;
		}
		if (transportMode == null)
		{
			if (other.transportMode != null)
			{
				return false;
			}
		}
		else if (!transportMode.equals(other.transportMode))
		{
			return false;
		}
		if (transportName == null)
		{
			if (other.transportName != null)
			{
				return false;
			}
		}
		else if (!transportName.equals(other.transportName))
		{
			return false;
		}
		if (transportReference == null)
		{
			if (other.transportReference != null)
			{
				return false;
			}
		}
		else if (!transportReference.equals(other.transportReference))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "H100 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", messageTyp=" + messageTyp + ", messageVersion=" + messageVersion + ", messageRelease="
				+ messageRelease + ", controllingAgency=" + controllingAgency + ", associationCode=" + associationCode + ", documentCode=" + documentCode + ", documentNo=" + documentNo
				+ ", messageDate=" + messageDate + ", deliveryDate=" + deliveryDate + ", buyerID=" + buyerID + ", supplierID=" + supplierID + ", deliveryID=" + deliveryID + ", storeNumber="
				+ storeNumber + ", invoiceID=" + invoiceID + ", orderNumber=" + orderNumber + ", orderDate=" + orderDate + ", despatchNoteNumber=" + despatchNoteNumber + ", referenceCode="
				+ referenceCode + ", prodGrpCode=" + prodGrpCode + ", supplierNo=" + supplierNo + ", transportMode=" + transportMode + ", transportReference=" + transportReference
				+ ", transportCode=" + transportCode + ", transportName=" + transportName + ", rampeID=" + rampeID + ", deliveryName=" + deliveryName + ", storeName=" + storeName + ", p050=" + p050
				+ "]";
	}
}
