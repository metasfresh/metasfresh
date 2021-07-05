package de.metas.adempiere.model;

import java.sql.Timestamp;

public interface I_C_Order extends org.compiere.model.I_C_Order, I_OrderOrInOut
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
	//String COLUMNNAME_OfferValidDate = "OfferValidDate";
	Timestamp getOfferValidDate();
	void setOfferValidDate(Timestamp OfferValidDate);
	//@formatter:on
}
