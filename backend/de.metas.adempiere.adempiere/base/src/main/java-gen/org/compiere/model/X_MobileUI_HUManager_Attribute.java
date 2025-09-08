// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileUI_HUManager_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_HUManager_Attribute extends org.compiere.model.PO implements I_MobileUI_HUManager_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1824647476L;

    /** Standard Constructor */
    public X_MobileUI_HUManager_Attribute (final Properties ctx, final int MobileUI_HUManager_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_HUManager_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_HUManager_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setMobileUI_HUManager_Attribute_ID (final int MobileUI_HUManager_Attribute_ID)
	{
		if (MobileUI_HUManager_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_HUManager_Attribute_ID, MobileUI_HUManager_Attribute_ID);
	}

	@Override
	public int getMobileUI_HUManager_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_HUManager_Attribute_ID);
	}

	@Override
	public org.compiere.model.I_MobileUI_HUManager getMobileUI_HUManager()
	{
		return get_ValueAsPO(COLUMNNAME_MobileUI_HUManager_ID, org.compiere.model.I_MobileUI_HUManager.class);
	}

	@Override
	public void setMobileUI_HUManager(final org.compiere.model.I_MobileUI_HUManager MobileUI_HUManager)
	{
		set_ValueFromPO(COLUMNNAME_MobileUI_HUManager_ID, org.compiere.model.I_MobileUI_HUManager.class, MobileUI_HUManager);
	}

	@Override
	public void setMobileUI_HUManager_ID (final int MobileUI_HUManager_ID)
	{
		if (MobileUI_HUManager_ID < 1) 
			set_Value (COLUMNNAME_MobileUI_HUManager_ID, null);
		else 
			set_Value (COLUMNNAME_MobileUI_HUManager_ID, MobileUI_HUManager_ID);
	}

	@Override
	public int getMobileUI_HUManager_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_HUManager_ID);
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