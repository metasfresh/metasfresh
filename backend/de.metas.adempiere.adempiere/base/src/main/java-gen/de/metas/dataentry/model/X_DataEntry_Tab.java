// Generated Model - DO NOT CHANGE
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DataEntry_Tab
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DataEntry_Tab extends org.compiere.model.PO implements I_DataEntry_Tab, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -749657992L;

    /** Standard Constructor */
    public X_DataEntry_Tab (final Properties ctx, final int DataEntry_Tab_ID, @Nullable final String trxName)
    {
      super (ctx, DataEntry_Tab_ID, trxName);
    }

    /** Load Constructor */
    public X_DataEntry_Tab (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAvailableInAPI (final boolean AvailableInAPI)
	{
		set_Value (COLUMNNAME_AvailableInAPI, AvailableInAPI);
	}

	@Override
	public boolean isAvailableInAPI() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AvailableInAPI);
	}

	@Override
	public void setDataEntry_Tab_ID (final int DataEntry_Tab_ID)
	{
		if (DataEntry_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Tab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Tab_ID, DataEntry_Tab_ID);
	}

	@Override
	public int getDataEntry_Tab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DataEntry_Tab_ID);
	}

	@Override
	public org.compiere.model.I_AD_Window getDataEntry_TargetWindow()
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_TargetWindow_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setDataEntry_TargetWindow(final org.compiere.model.I_AD_Window DataEntry_TargetWindow)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_TargetWindow_ID, org.compiere.model.I_AD_Window.class, DataEntry_TargetWindow);
	}

	@Override
	public void setDataEntry_TargetWindow_ID (final int DataEntry_TargetWindow_ID)
	{
		if (DataEntry_TargetWindow_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_TargetWindow_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_TargetWindow_ID, DataEntry_TargetWindow_ID);
	}

	@Override
	public int getDataEntry_TargetWindow_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DataEntry_TargetWindow_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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

	@Override
	public void setTabName (final java.lang.String TabName)
	{
		set_Value (COLUMNNAME_TabName, TabName);
	}

	@Override
	public java.lang.String getTabName() 
	{
		return get_ValueAsString(COLUMNNAME_TabName);
	}
}