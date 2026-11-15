// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Carrier_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Carrier_Config extends org.compiere.model.PO implements I_Carrier_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1470195178L;

    /** Standard Constructor */
    public X_Carrier_Config (final Properties ctx, final int Carrier_Config_ID, @Nullable final String trxName)
    {
      super (ctx, Carrier_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_Carrier_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setActorId (final @Nullable java.lang.String ActorId)
	{
		set_Value (COLUMNNAME_ActorId, ActorId);
	}

	@Override
	public java.lang.String getActorId() 
	{
		return get_ValueAsString(COLUMNNAME_ActorId);
	}

	/** 
	 * AdviseType AD_Reference_ID=542106
	 * Reference name: AdviseType
	 */
	public static final int ADVISETYPE_AD_Reference_ID=542106;
	/** Ship = S */
	public static final String ADVISETYPE_Ship = "S";
	/** Order = O */
	public static final String ADVISETYPE_Order = "O";
	@Override
	public void setAdviseType (final java.lang.String AdviseType)
	{
		set_Value (COLUMNNAME_AdviseType, AdviseType);
	}

	@Override
	public java.lang.String getAdviseType() 
	{
		return get_ValueAsString(COLUMNNAME_AdviseType);
	}

	@Override
	public void setBase_url (final @Nullable java.lang.String Base_url)
	{
		set_Value (COLUMNNAME_Base_url, Base_url);
	}

	@Override
	public java.lang.String getBase_url() 
	{
		return get_ValueAsString(COLUMNNAME_Base_url);
	}

	@Override
	public void setCarrier_Config_ID (final int Carrier_Config_ID)
	{
		if (Carrier_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Carrier_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Carrier_Config_ID, Carrier_Config_ID);
	}

	@Override
	public int getCarrier_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Carrier_Config_ID);
	}

	@Override
	public void setClient_Id (final @Nullable java.lang.String Client_Id)
	{
		set_Value (COLUMNNAME_Client_Id, Client_Id);
	}

	@Override
	public java.lang.String getClient_Id() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Id);
	}

	@Override
	public void setClient_Secret (final @Nullable java.lang.String Client_Secret)
	{
		set_Value (COLUMNNAME_Client_Secret, Client_Secret);
	}

	@Override
	public java.lang.String getClient_Secret() 
	{
		return get_ValueAsString(COLUMNNAME_Client_Secret);
	}

	@Override
	public void setCustomValueString1 (final @Nullable java.lang.String CustomValueString1)
	{
		set_Value (COLUMNNAME_CustomValueString1, CustomValueString1);
	}

	@Override
	public java.lang.String getCustomValueString1() 
	{
		return get_ValueAsString(COLUMNNAME_CustomValueString1);
	}

	@Override
	public void setCustomValueString2 (final @Nullable java.lang.String CustomValueString2)
	{
		set_Value (COLUMNNAME_CustomValueString2, CustomValueString2);
	}

	@Override
	public java.lang.String getCustomValueString2() 
	{
		return get_ValueAsString(COLUMNNAME_CustomValueString2);
	}

	@Override
	public void setCustomValueString3 (final @Nullable java.lang.String CustomValueString3)
	{
		set_Value (COLUMNNAME_CustomValueString3, CustomValueString3);
	}

	@Override
	public java.lang.String getCustomValueString3() 
	{
		return get_ValueAsString(COLUMNNAME_CustomValueString3);
	}

	@Override
	public void setIsCreateDraftShipmentOnly (final boolean IsCreateDraftShipmentOnly)
	{
		set_Value (COLUMNNAME_IsCreateDraftShipmentOnly, IsCreateDraftShipmentOnly);
	}

	@Override
	public boolean isCreateDraftShipmentOnly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCreateDraftShipmentOnly);
	}

	@Override
	public void setIsSelectionRules (final boolean IsSelectionRules)
	{
		set_Value (COLUMNNAME_IsSelectionRules, IsSelectionRules);
	}

	@Override
	public boolean isSelectionRules() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSelectionRules);
	}

	@Override
	public void setIsTestMode (final boolean IsTestMode)
	{
		set_Value (COLUMNNAME_IsTestMode, IsTestMode);
	}

	@Override
	public boolean isTestMode() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTestMode);
	}

	@Override
	public void setM_Shipper_ID (final int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, M_Shipper_ID);
	}

	@Override
	public int getM_Shipper_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipper_ID);
	}

	@Override
	public void setPassword (final @Nullable java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	@Override
	public java.lang.String getPassword() 
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setServiceLevel (final @Nullable java.lang.String ServiceLevel)
	{
		set_Value (COLUMNNAME_ServiceLevel, ServiceLevel);
	}

	@Override
	public java.lang.String getServiceLevel() 
	{
		return get_ValueAsString(COLUMNNAME_ServiceLevel);
	}

	/** 
	 * ShipType AD_Reference_ID=542106
	 * Reference name: AdviseType
	 */
	public static final int SHIPTYPE_AD_Reference_ID=542106;
	/** Ship = S */
	public static final String SHIPTYPE_Ship = "S";
	/** Order = O */
	public static final String SHIPTYPE_Order = "O";
	@Override
	public void setShipType (final java.lang.String ShipType)
	{
		set_Value (COLUMNNAME_ShipType, ShipType);
	}

	@Override
	public java.lang.String getShipType() 
	{
		return get_ValueAsString(COLUMNNAME_ShipType);
	}

	@Override
	public void setTestMode_Attention (final @Nullable java.lang.String TestMode_Attention)
	{
		set_Value (COLUMNNAME_TestMode_Attention, TestMode_Attention);
	}

	@Override
	public java.lang.String getTestMode_Attention() 
	{
		return get_ValueAsString(COLUMNNAME_TestMode_Attention);
	}

	@Override
	public void setUserName (final @Nullable java.lang.String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	@Override
	public java.lang.String getUserName() 
	{
		return get_ValueAsString(COLUMNNAME_UserName);
	}
}