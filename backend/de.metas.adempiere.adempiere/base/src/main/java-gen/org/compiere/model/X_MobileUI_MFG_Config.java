// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_MFG_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_MFG_Config extends org.compiere.model.PO implements I_MobileUI_MFG_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1368610276L;

    /** Standard Constructor */
    public X_MobileUI_MFG_Config (final Properties ctx, final int MobileUI_MFG_Config_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_MFG_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_MFG_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsAllowIssuingAnyHU (final boolean IsAllowIssuingAnyHU)
	{
		set_Value (COLUMNNAME_IsAllowIssuingAnyHU, IsAllowIssuingAnyHU);
	}

	@Override
	public boolean isAllowIssuingAnyHU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowIssuingAnyHU);
	}

	@Override
	public void setIsAllowReceiveToLU (final boolean IsAllowReceiveToLU)
	{
		set_Value (COLUMNNAME_IsAllowReceiveToLU, IsAllowReceiveToLU);
	}

	@Override
	public boolean isAllowReceiveToLU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowReceiveToLU);
	}

	@Override
	public void setIsAllowReceiveToTU (final boolean IsAllowReceiveToTU)
	{
		set_Value (COLUMNNAME_IsAllowReceiveToTU, IsAllowReceiveToTU);
	}

	@Override
	public boolean isAllowReceiveToTU() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowReceiveToTU);
	}

	@Override
	public void setIsBestBeforeDateEditable (final boolean IsBestBeforeDateEditable)
	{
		set_Value (COLUMNNAME_IsBestBeforeDateEditable, IsBestBeforeDateEditable);
	}

	@Override
	public boolean isBestBeforeDateEditable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBestBeforeDateEditable);
	}

	@Override
	public void setIsCaptureCatchWeightAtReceipt (final boolean IsCaptureCatchWeightAtReceipt)
	{
		set_Value (COLUMNNAME_IsCaptureCatchWeightAtReceipt, IsCaptureCatchWeightAtReceipt);
	}

	@Override
	public boolean isCaptureCatchWeightAtReceipt() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCaptureCatchWeightAtReceipt);
	}

	@Override
	public void setIsLotNumberEditable (final boolean IsLotNumberEditable)
	{
		set_Value (COLUMNNAME_IsLotNumberEditable, IsLotNumberEditable);
	}

	@Override
	public boolean isLotNumberEditable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsLotNumberEditable);
	}

	@Override
	public void setIsScanResourceRequired (final boolean IsScanResourceRequired)
	{
		set_Value (COLUMNNAME_IsScanResourceRequired, IsScanResourceRequired);
	}

	@Override
	public boolean isScanResourceRequired() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsScanResourceRequired);
	}

	@Override
	public void setIsSkipReceiveTargetStep (final boolean IsSkipReceiveTargetStep)
	{
		set_Value (COLUMNNAME_IsSkipReceiveTargetStep, IsSkipReceiveTargetStep);
	}

	@Override
	public boolean isSkipReceiveTargetStep() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSkipReceiveTargetStep);
	}

	@Override
	public void setMobileUI_MFG_Config_ID (final int MobileUI_MFG_Config_ID)
	{
		if (MobileUI_MFG_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_MFG_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_MFG_Config_ID, MobileUI_MFG_Config_ID);
	}

	@Override
	public int getMobileUI_MFG_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_MFG_Config_ID);
	}

	/** 
	 * ReceiveUnitType AD_Reference_ID=542051
	 * Reference name: ReceiveUnitType
	 */
	public static final int RECEIVEUNITTYPE_AD_Reference_ID=542051;
	/** CU = CU */
	public static final String RECEIVEUNITTYPE_CU = "CU";
	/** TU = TU */
	public static final String RECEIVEUNITTYPE_TU = "TU";
	@Override
	public void setReceiveUnitType (final java.lang.String ReceiveUnitType)
	{
		set_Value (COLUMNNAME_ReceiveUnitType, ReceiveUnitType);
	}

	@Override
	public java.lang.String getReceiveUnitType() 
	{
		return get_ValueAsString(COLUMNNAME_ReceiveUnitType);
	}
}