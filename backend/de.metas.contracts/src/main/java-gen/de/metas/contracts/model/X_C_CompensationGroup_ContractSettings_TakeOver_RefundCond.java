// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_ContractSettings_TakeOver_RefundCond
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_ContractSettings_TakeOver_RefundCond extends org.compiere.model.PO implements I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -273935697L;

    /** Standard Constructor */
    public X_C_CompensationGroup_ContractSettings_TakeOver_RefundCond (final Properties ctx, final int C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_ContractSettings_TakeOver_RefundCond (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_CompensationGroup_ContractSettings_TakeOver_ID (final int C_CompensationGroup_ContractSettings_TakeOver_ID)
	{
		if (C_CompensationGroup_ContractSettings_TakeOver_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, C_CompensationGroup_ContractSettings_TakeOver_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_TakeOver_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID);
	}

	@Override
	public void setC_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID (final int C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID)
	{
		if (C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID, C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID);
	}

	@Override
	public void setC_Flatrate_Conditions_ID (final int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, C_Flatrate_Conditions_ID);
	}

	@Override
	public int getC_Flatrate_Conditions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Conditions_ID);
	}
}