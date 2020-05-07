package de.metas.adempiere.model;

import java.sql.Timestamp;

import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentHandOverLocation;

public interface I_C_Order extends org.compiere.model.I_C_Order, I_OrderOrInOut, IDocumentBillLocation, IDocumentDeliveryLocation, IDocumentHandOverLocation
{
	String FREIGHTCOSTRULE_Versandkostenpauschale = "P";

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
	@Override
	int getHandOver_Partner_ID();
	void setHandOver_Partner_ID(int HandOver_Partner_ID);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";
	@Override
	int getHandOver_Location_ID();
	void setHandOver_Location_ID(int HandOver_Location_ID);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_HandOver_User_ID = "HandOver_User_ID";
	@Override
	int getHandOver_User_ID();
	void setHandOver_User_ID(int HandOver_User_ID);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_IsUseHandOver_Location = "IsUseHandOver_Location";
	boolean getIsUseHandOver_Location();
	void setIsUseHandOver_Location(boolean IsUseHandOver_Location);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_HandOverAddress = "HandOverAddress";
	@Override
	String getHandOverAddress();
	@Override
	void setHandOverAddress(String address);
	//@formatter:on
}
