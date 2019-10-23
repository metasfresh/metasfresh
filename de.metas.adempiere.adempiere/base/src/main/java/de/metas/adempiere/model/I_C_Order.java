package de.metas.adempiere.model;

import java.sql.Timestamp;

import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;

public interface I_C_Order extends org.compiere.model.I_C_Order, I_OrderOrInOut, IDocumentBillLocation, IDocumentDeliveryLocation
{
	String FREIGHTCOSTRULE_Versandkostenpauschale = "P";

	//@formatter:off
	String COLUMNNAME_C_Sponsor_ID = "C_Sponsor_ID";
	int getC_Sponsor_ID();
	void setC_Sponsor_ID(int C_Sponsor_ID);
	//@formatter:off

	//@formatter:off
	String COLUMNNAME_Ref_RMA_ID = "Ref_RMA_ID";
	int getRef_RMA_ID();
	void setRef_RMA_ID(int Ref_RMA_ID);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_OfferValidDays = "OfferValidDays";
	int getOfferValidDays();
	void setOfferValidDays(int OfferValidDays);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_OfferValidDate = "OfferValidDate";
	Timestamp getOfferValidDate();
	void setOfferValidDate(Timestamp OfferValidDate);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_IsUseDeliveryToAddress = "IsUseDeliveryToAddress";
	boolean isUseDeliveryToAddress();
	void setIsUseDeliveryToAddress(boolean IsUseDeliveryToAddress);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_DeliveryToAddress = "DeliveryToAddress";
	@Override
	String getDeliveryToAddress();
	@Override
	void setDeliveryToAddress(String address);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";
	int getHandOver_Partner_ID();
	void setHandOver_Partner_ID(int HandOver_Partner_ID);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";
	int getHandOver_Location_ID();
	void setHandOver_Location_ID(int HandOver_Location_ID);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_IsUseHandOver_Location = "IsUseHandOver_Location";
	boolean getIsUseHandOver_Location();
	void setIsUseHandOver_Location(boolean IsUseHandOver_Location);
	//@formatter:on
}
