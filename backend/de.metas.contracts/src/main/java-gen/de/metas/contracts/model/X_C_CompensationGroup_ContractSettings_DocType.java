// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_CompensationGroup_ContractSettings_DocType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_CompensationGroup_ContractSettings_DocType extends org.compiere.model.PO implements I_C_CompensationGroup_ContractSettings_DocType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -879574662L;

    /** Standard Constructor */
    public X_C_CompensationGroup_ContractSettings_DocType (final Properties ctx, final int C_CompensationGroup_ContractSettings_DocType_ID, @Nullable final String trxName)
    {
      super (ctx, C_CompensationGroup_ContractSettings_DocType_ID, trxName);
    }

    /** Load Constructor */
    public X_C_CompensationGroup_ContractSettings_DocType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_CompensationGroup_ContractSettings_DocType_ID (final int C_CompensationGroup_ContractSettings_DocType_ID)
	{
		if (C_CompensationGroup_ContractSettings_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_ContractSettings_DocType_ID, C_CompensationGroup_ContractSettings_DocType_ID);
	}

	@Override
	public int getC_CompensationGroup_ContractSettings_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_CompensationGroup_ContractSettings_DocType_ID);
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}
}