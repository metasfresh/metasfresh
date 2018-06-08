/**
 *
 */
package de.metas.interfaces;

import org.compiere.model.I_M_PricingSystem;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BP_Group;
import de.metas.adempiere.model.I_C_BPartner_Location;

public interface I_C_BPartner extends org.compiere.model.I_C_BPartner
{

	public static final String SO_CREDITSTATUS_ONE_OPEN_INVOICE = "I";

	public static final String COLUMNNAME_IsShippingNotificationEmail = "IsShippingNotificationEmail";

	/** Set IsShippingNotificationEmail. */
	@Override
	public void setIsShippingNotificationEmail(boolean IsShippingNotificationEmail);

	/** Get IsShippingNotificationEmail. */
	@Override
	public boolean isShippingNotificationEmail();

	public static final String COLUMNNAME_AllowConsolidateInOut = "AllowConsolidateInOut";

	@Override
	public boolean isAllowConsolidateInOut();

	@Override
	public void setAllowConsolidateInOut(boolean AllowConsolidateInOut);

	public static final String COLUMNNAME_IsCompany = "isCompany";

	@Override
	public boolean isCompany();

	@Override
	public void setIsCompany(boolean IsCompany);

	public static final String COLUMNNAME_CompanyName = "CompanyName";

	@Override
	public String getCompanyName();

	@Override
	public void setCompanyName(String CompanyName);

	public static final String COLUMNNAME_Memo = "Memo";

	public String getMemo();

	public void setMemo(String memo);

	public static final String COLUMNNAME_Default_User_ID = "Default_User_ID";

	public int getDefault_User_ID();

	public void setDefault_User_ID(int Default_User_ID);

	public I_AD_User getDefault_User();

	public static final String COLUMNNAME_Default_Bill_Location_ID = "Default_Bill_Location_ID";

	public int getDefault_Bill_Location_ID();

	public void setDefault_Bill_Location_ID(int Default_Bill_Location_ID);

	public I_C_BPartner_Location getDefault_Bill_Location();

	public static final String COLUMNNAME_Default_Ship_Location_ID = "Default_Ship_Location_ID";

	public int getDefault_Ship_Location_ID();

	public void setDefault_Ship_Location_ID(int Default_Ship_Location_ID);

	public I_C_BPartner_Location getDefault_Ship_Location();

	public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	@Override
	public int getM_PricingSystem_ID();

	@Override
	public I_M_PricingSystem getM_PricingSystem();

	@Override
	public void setM_PricingSystem_ID(int M_PricingSystem_ID);

	public static final String COLUMNNAME_PO_PRICING_SYSTEM_ID = "PO_PricingSystem_ID";

	@Override
	int getPO_PricingSystem_ID();

	@Override
	public I_M_PricingSystem getPO_PricingSystem();

	public static final String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	public int getM_Shipper_ID();

	public void setM_Shipper_ID(int M_Shipper_ID);

	public I_M_Shipper getM_Shipper();

	public static final String COLUMNNAME_VATaxID = "VATaxID";

	@Override
	String getVATaxID();

	@Override
	void setVATaxID(String VATaxID);

	public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	@Override
	public int getM_Warehouse_ID();

	@Override
	public void setM_Warehouse_ID(int M_Warehouse_ID);

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public static final String COLUMNNAME_PO_DeliveryViaRule = "PO_DeliveryViaRule";

	@Override
	public String getPO_DeliveryViaRule();

	@Override
	public void setPO_DeliveryViaRule(String PO_DeliveryViaRule);

	public static final String COLUMNNAME_Customer_Group_ID = "Customer_Group_ID";

	public int getCustomer_Group_ID();

	public void setCustomer_Group_ID(int Customer_Group_ID);

	public I_C_BP_Group getCustomer_Group();
}
