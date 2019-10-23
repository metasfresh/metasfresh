/**
 *
 */
package de.metas.interfaces;

import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.adempiere.model.I_AD_User;

public interface I_C_BPartner extends org.compiere.model.I_C_BPartner
{
	public static final String SO_CREDITSTATUS_ONE_OPEN_INVOICE = "I";

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

	public static final String COLUMNNAME_Customer_Group_ID = "Customer_Group_ID";

	public int getCustomer_Group_ID();

	public void setCustomer_Group_ID(int Customer_Group_ID);

	public I_C_BP_Group getCustomer_Group();
}
