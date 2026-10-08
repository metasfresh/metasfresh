// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_ContractSettings_TakeOver
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_ContractSettings_TakeOver extends org.compiere.model.PO implements I_C_CompensationGroup_ContractSettings_TakeOver, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 910034252L;

    /** Standard Constructor */
    public X_C_CompensationGroup_ContractSettings_TakeOver (final Properties ctx, final int C_CompensationGroup_ContractSettings_TakeOver_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_ContractSettings_TakeOver_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_ContractSettings_TakeOver (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_CompensationGroup_ContractSettings_ID (final int C_CompensationGroup_ContractSettings_ID)
	{
		if (C_CompensationGroup_ContractSettings_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_ContractSettings_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_ContractSettings_ID, C_CompensationGroup_ContractSettings_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_ID);
	}

	@Override
	public void setC_CompensationGroup_ContractSettings_TakeOver_ID (final int C_CompensationGroup_ContractSettings_TakeOver_ID)
	{
		if (C_CompensationGroup_ContractSettings_TakeOver_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID, C_CompensationGroup_ContractSettings_TakeOver_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_TakeOver_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}
}