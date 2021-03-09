/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UI_Section
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UI_Section extends org.compiere.model.PO implements I_AD_UI_Section, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 791232113L;

    /** Standard Constructor */
    public X_AD_UI_Section (Properties ctx, int AD_UI_Section_ID, String trxName)
    {
      super (ctx, AD_UI_Section_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_UI_Section (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Tab getAD_Tab()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class);
	}

	@Override
	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tab_ID, org.compiere.model.I_AD_Tab.class, AD_Tab);
	}

	@Override
	public void setAD_Tab_ID (int AD_Tab_ID)
	{
		if (AD_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tab_ID, Integer.valueOf(AD_Tab_ID));
	}

	@Override
	public int getAD_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tab_ID);
	}

	@Override
	public void setAD_UI_Section_ID (int AD_UI_Section_ID)
	{
		if (AD_UI_Section_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Section_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Section_ID, Integer.valueOf(AD_UI_Section_ID));
	}

	@Override
	public int getAD_UI_Section_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_UI_Section_ID);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setUIStyle (java.lang.String UIStyle)
	{
		set_Value (COLUMNNAME_UIStyle, UIStyle);
	}

	@Override
	public java.lang.String getUIStyle() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UIStyle);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}