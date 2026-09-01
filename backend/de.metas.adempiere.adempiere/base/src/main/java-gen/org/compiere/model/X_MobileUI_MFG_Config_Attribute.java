// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_MFG_Config_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_MFG_Config_Attribute extends org.compiere.model.PO implements I_MobileUI_MFG_Config_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 209182793L;

    /** Standard Constructor */
    public X_MobileUI_MFG_Config_Attribute (final Properties ctx, final int MobileUI_MFG_Config_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_MFG_Config_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_MFG_Config_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public void setMobileUI_MFG_Config_Attribute_ID (final int MobileUI_MFG_Config_Attribute_ID)
	{
		if (MobileUI_MFG_Config_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_MFG_Config_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_MFG_Config_Attribute_ID, MobileUI_MFG_Config_Attribute_ID);
	}

	@Override
	public int getMobileUI_MFG_Config_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_MFG_Config_Attribute_ID);
	}

	@Override
	public void setMobileUI_MFG_Config_ID (final int MobileUI_MFG_Config_ID)
	{
		if (MobileUI_MFG_Config_ID < 1) 
			set_Value (COLUMNNAME_MobileUI_MFG_Config_ID, null);
		else 
			set_Value (COLUMNNAME_MobileUI_MFG_Config_ID, MobileUI_MFG_Config_ID);
	}

	@Override
	public int getMobileUI_MFG_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_MFG_Config_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}