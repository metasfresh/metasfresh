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

package de.metas.edi.esb.ordersimport.compudata;

import java.io.Serializable;

public class H100 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -583857195330912059L;

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
	private String responseType;
	private String messageDate;
	private String deliveryDate;
	private String buyerID;
	private String supplierID;
	private String deliveryID;
	private String invoiceID;
	private String storeNumber;
	private String actionOrderNumber;
	private String crossdockingOrderNo;
	private String currency;
	private String referenceCode;
	private String prodGrpCode;
	private String prodGrpName;
	private String orderContact;
	private String supplierNo;
	private String warehouseContact;
	private String customerReference;
	private String confirmationDate;
	private String actionBeginDate;
	private String actionEndDate;
	private String deliveryDateLatest;
	private String despatchDate;
	private String deliveryPlace;
	private String storeName1;
	private String storeName2;
	private String zsnNumber;
	private String torangabe;
	private String rampeID;

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

	public final String getMessageTyp()
	{
		return messageTyp;
	}

	public final void setMessageTyp(final String messageTyp)
	{
		this.messageTyp = messageTyp;
	}

	public final String getMessageVersion()
	{
		return messageVersion;
	}

	public final void setMessageVersion(final String messageVersion)
	{
		this.messageVersion = messageVersion;
	}

	public final String getMessageRelease()
	{
		return messageRelease;
	}

	public final void setMessageRelease(final String messageRelease)
	{
		this.messageRelease = messageRelease;
	}

	public final String getControllingAgency()
	{
		return controllingAgency;
	}

	public final void setControllingAgency(final String controllingAgency)
	{
		this.controllingAgency = controllingAgency;
	}

	public final String getAssociationCode()
	{
		return associationCode;
	}

	public final void setAssociationCode(final String associationCode)
	{
		this.associationCode = associationCode;
	}

	public final String getDocumentCode()
	{
		return documentCode;
	}

	public final void setDocumentCode(final String documentCode)
	{
		this.documentCode = documentCode;
	}

	public final String getDocumentNo()
	{
		return documentNo;
	}

	public final void setDocumentNo(final String documentNo)
	{
		this.documentNo = documentNo;
	}

	public final String getResponseType()
	{
		return responseType;
	}

	public final void setResponseType(final String responseType)
	{
		this.responseType = responseType;
	}

	public final String getMessageDate()
	{
		return messageDate;
	}

	public final void setMessageDate(final String messageDate)
	{
		this.messageDate = messageDate;
	}

	public final String getDeliveryDate()
	{
		return deliveryDate;
	}

	public final void setDeliveryDate(final String deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public final String getBuyerID()
	{
		return buyerID;
	}

	public final void setBuyerID(final String buyerID)
	{
		this.buyerID = buyerID;
	}

	public final String getSupplierID()
	{
		return supplierID;
	}

	public final void setSupplierID(final String supplierID)
	{
		this.supplierID = supplierID;
	}

	public final String getDeliveryID()
	{
		return deliveryID;
	}

	public final void setDeliveryID(final String deliveryID)
	{
		this.deliveryID = deliveryID;
	}

	public final String getInvoiceID()
	{
		return invoiceID;
	}

	public final void setInvoiceID(final String invoiceID)
	{
		this.invoiceID = invoiceID;
	}

	public final String getStoreNumber()
	{
		return storeNumber;
	}

	public final void setStoreNumber(final String storeNumber)
	{
		this.storeNumber = storeNumber;
	}

	public final String getActionOrderNumber()
	{
		return actionOrderNumber;
	}

	public final void setActionOrderNumber(final String actionOrderNumber)
	{
		this.actionOrderNumber = actionOrderNumber;
	}

	public final String getCrossdockingOrderNo()
	{
		return crossdockingOrderNo;
	}

	public final void setCrossdockingOrderNo(final String crossdockingOrderNo)
	{
		this.crossdockingOrderNo = crossdockingOrderNo;
	}

	public final String getCurrency()
	{
		return currency;
	}

	public final void setCurrency(final String currency)
	{
		this.currency = currency;
	}

	public final String getReferenceCode()
	{
		return referenceCode;
	}

	public final void setReferenceCode(final String referenceCode)
	{
		this.referenceCode = referenceCode;
	}

	public final String getProdGrpCode()
	{
		return prodGrpCode;
	}

	public final void setProdGrpCode(final String prodGrpCode)
	{
		this.prodGrpCode = prodGrpCode;
	}

	public final String getProdGrpName()
	{
		return prodGrpName;
	}

	public final void setProdGrpName(final String prodGrpName)
	{
		this.prodGrpName = prodGrpName;
	}

	public final String getOrderContact()
	{
		return orderContact;
	}

	public final void setOrderContact(final String orderContact)
	{
		this.orderContact = orderContact;
	}

	public final String getSupplierNo()
	{
		return supplierNo;
	}

	public final void setSupplierNo(final String supplierNo)
	{
		this.supplierNo = supplierNo;
	}

	public final String getWarehouseContact()
	{
		return warehouseContact;
	}

	public final void setWarehouseContact(final String warehouseContact)
	{
		this.warehouseContact = warehouseContact;
	}

	public final String getCustomerReference()
	{
		return customerReference;
	}

	public final void setCustomerReference(final String customerReference)
	{
		this.customerReference = customerReference;
	}

	public final String getConfirmationDate()
	{
		return confirmationDate;
	}

	public final void setConfirmationDate(final String confirmationDate)
	{
		this.confirmationDate = confirmationDate;
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

	public final String getDeliveryDateLatest()
	{
		return deliveryDateLatest;
	}

	public final void setDeliveryDateLatest(final String deliveryDateLatest)
	{
		this.deliveryDateLatest = deliveryDateLatest;
	}

	public final String getDespatchDate()
	{
		return despatchDate;
	}

	public final void setDespatchDate(final String despatchDate)
	{
		this.despatchDate = despatchDate;
	}

	public final String getDeliveryPlace()
	{
		return deliveryPlace;
	}

	public final void setDeliveryPlace(final String deliveryPlace)
	{
		this.deliveryPlace = deliveryPlace;
	}

	public final String getStoreName1()
	{
		return storeName1;
	}

	public final void setStoreName1(final String storeName1)
	{
		this.storeName1 = storeName1;
	}

	public final String getStoreName2()
	{
		return storeName2;
	}

	public final void setStoreName2(final String storeName2)
	{
		this.storeName2 = storeName2;
	}

	public final String getZsnNumber()
	{
		return zsnNumber;
	}

	public final void setZsnNumber(final String zsnNumber)
	{
		this.zsnNumber = zsnNumber;
	}

	public final String getTorangabe()
	{
		return torangabe;
	}

	public final void setTorangabe(final String torangabe)
	{
		this.torangabe = torangabe;
	}

	public final String getRampeID()
	{
		return rampeID;
	}

	public final void setRampeID(final String rampeID)
	{
		this.rampeID = rampeID;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (actionBeginDate == null ? 0 : actionBeginDate.hashCode());
		result = prime * result + (actionEndDate == null ? 0 : actionEndDate.hashCode());
		result = prime * result + (actionOrderNumber == null ? 0 : actionOrderNumber.hashCode());
		result = prime * result + (associationCode == null ? 0 : associationCode.hashCode());
		result = prime * result + (buyerID == null ? 0 : buyerID.hashCode());
		result = prime * result + (confirmationDate == null ? 0 : confirmationDate.hashCode());
		result = prime * result + (controllingAgency == null ? 0 : controllingAgency.hashCode());
		result = prime * result + (crossdockingOrderNo == null ? 0 : crossdockingOrderNo.hashCode());
		result = prime * result + (currency == null ? 0 : currency.hashCode());
		result = prime * result + (customerReference == null ? 0 : customerReference.hashCode());
		result = prime * result + (deliveryDate == null ? 0 : deliveryDate.hashCode());
		result = prime * result + (deliveryDateLatest == null ? 0 : deliveryDateLatest.hashCode());
		result = prime * result + (deliveryID == null ? 0 : deliveryID.hashCode());
		result = prime * result + (deliveryPlace == null ? 0 : deliveryPlace.hashCode());
		result = prime * result + (despatchDate == null ? 0 : despatchDate.hashCode());
		result = prime * result + (documentCode == null ? 0 : documentCode.hashCode());
		result = prime * result + (documentNo == null ? 0 : documentNo.hashCode());
		result = prime * result + (invoiceID == null ? 0 : invoiceID.hashCode());
		result = prime * result + (messageDate == null ? 0 : messageDate.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (messageRelease == null ? 0 : messageRelease.hashCode());
		result = prime * result + (messageTyp == null ? 0 : messageTyp.hashCode());
		result = prime * result + (messageVersion == null ? 0 : messageVersion.hashCode());
		result = prime * result + (orderContact == null ? 0 : orderContact.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (prodGrpCode == null ? 0 : prodGrpCode.hashCode());
		result = prime * result + (prodGrpName == null ? 0 : prodGrpName.hashCode());
		result = prime * result + (rampeID == null ? 0 : rampeID.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (referenceCode == null ? 0 : referenceCode.hashCode());
		result = prime * result + (responseType == null ? 0 : responseType.hashCode());
		result = prime * result + (storeName1 == null ? 0 : storeName1.hashCode());
		result = prime * result + (storeName2 == null ? 0 : storeName2.hashCode());
		result = prime * result + (storeNumber == null ? 0 : storeNumber.hashCode());
		result = prime * result + (supplierID == null ? 0 : supplierID.hashCode());
		result = prime * result + (supplierNo == null ? 0 : supplierNo.hashCode());
		result = prime * result + (torangabe == null ? 0 : torangabe.hashCode());
		result = prime * result + (warehouseContact == null ? 0 : warehouseContact.hashCode());
		result = prime * result + (zsnNumber == null ? 0 : zsnNumber.hashCode());
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
		if (actionOrderNumber == null)
		{
			if (other.actionOrderNumber != null)
			{
				return false;
			}
		}
		else if (!actionOrderNumber.equals(other.actionOrderNumber))
		{
			return false;
		}
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
		if (confirmationDate == null)
		{
			if (other.confirmationDate != null)
			{
				return false;
			}
		}
		else if (!confirmationDate.equals(other.confirmationDate))
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
		if (crossdockingOrderNo == null)
		{
			if (other.crossdockingOrderNo != null)
			{
				return false;
			}
		}
		else if (!crossdockingOrderNo.equals(other.crossdockingOrderNo))
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
		if (customerReference == null)
		{
			if (other.customerReference != null)
			{
				return false;
			}
		}
		else if (!customerReference.equals(other.customerReference))
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
		if (deliveryDateLatest == null)
		{
			if (other.deliveryDateLatest != null)
			{
				return false;
			}
		}
		else if (!deliveryDateLatest.equals(other.deliveryDateLatest))
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
		if (deliveryPlace == null)
		{
			if (other.deliveryPlace != null)
			{
				return false;
			}
		}
		else if (!deliveryPlace.equals(other.deliveryPlace))
		{
			return false;
		}
		if (despatchDate == null)
		{
			if (other.despatchDate != null)
			{
				return false;
			}
		}
		else if (!despatchDate.equals(other.despatchDate))
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
		if (orderContact == null)
		{
			if (other.orderContact != null)
			{
				return false;
			}
		}
		else if (!orderContact.equals(other.orderContact))
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
		if (prodGrpName == null)
		{
			if (other.prodGrpName != null)
			{
				return false;
			}
		}
		else if (!prodGrpName.equals(other.prodGrpName))
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
		if (responseType == null)
		{
			if (other.responseType != null)
			{
				return false;
			}
		}
		else if (!responseType.equals(other.responseType))
		{
			return false;
		}
		if (storeName1 == null)
		{
			if (other.storeName1 != null)
			{
				return false;
			}
		}
		else if (!storeName1.equals(other.storeName1))
		{
			return false;
		}
		if (storeName2 == null)
		{
			if (other.storeName2 != null)
			{
				return false;
			}
		}
		else if (!storeName2.equals(other.storeName2))
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
		if (torangabe == null)
		{
			if (other.torangabe != null)
			{
				return false;
			}
		}
		else if (!torangabe.equals(other.torangabe))
		{
			return false;
		}
		if (warehouseContact == null)
		{
			if (other.warehouseContact != null)
			{
				return false;
			}
		}
		else if (!warehouseContact.equals(other.warehouseContact))
		{
			return false;
		}
		if (zsnNumber == null)
		{
			if (other.zsnNumber != null)
			{
				return false;
			}
		}
		else if (!zsnNumber.equals(other.zsnNumber))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "H100 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", messageTyp=" + messageTyp + ", messageVersion=" + messageVersion
				+ ", messageRelease="
				+ messageRelease + ", controllingAgency=" + controllingAgency + ", associationCode=" + associationCode + ", documentCode=" + documentCode + ", documentNo="
				+ documentNo
				+ ", responseType=" + responseType + ", messageDate=" + messageDate + ", deliveryDate=" + deliveryDate + ", buyerID=" + buyerID + ", supplierID=" + supplierID
				+ ", deliveryID="
				+ deliveryID + ", invoiceID=" + invoiceID + ", storeNumber=" + storeNumber + ", actionOrderNumber=" + actionOrderNumber + ", crossdockingOrderNo="
				+ crossdockingOrderNo
				+ ", currency=" + currency + ", referenceCode=" + referenceCode + ", prodGrpCode=" + prodGrpCode + ", prodGrpName=" + prodGrpName + ", orderContact="
				+ orderContact + ", supplierNo="
				+ supplierNo + ", warehouseContact=" + warehouseContact + ", customerReference=" + customerReference + ", confirmationDate=" + confirmationDate
				+ ", actionBeginDate="
				+ actionBeginDate + ", actionEndDate=" + actionEndDate + ", deliveryDateLatest=" + deliveryDateLatest + ", despatchDate=" + despatchDate + ", deliveryPlace="
				+ deliveryPlace
				+ ", storeName1=" + storeName1 + ", storeName2=" + storeName2 + ", zsnNumber=" + zsnNumber + ", torangabe=" + torangabe + ", rampeID=" + rampeID + "]";
	}
}
