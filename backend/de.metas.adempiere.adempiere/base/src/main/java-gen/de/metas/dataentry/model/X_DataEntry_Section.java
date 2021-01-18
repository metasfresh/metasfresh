// Generated Model - DO NOT CHANGE
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DataEntry_Section
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DataEntry_Section extends org.compiere.model.PO implements I_DataEntry_Section, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1358390394L;

    /** Standard Constructor */
    public X_DataEntry_Section (final Properties ctx, final int DataEntry_Section_ID, @Nullable final String trxName)
    {
      super (ctx, DataEntry_Section_ID, trxName);
    }

    /** Load Constructor */
    public X_DataEntry_Section (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDataEntry_Section_ID (final int DataEntry_Section_ID)
	{
		if (DataEntry_Section_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Section_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Section_ID, DataEntry_Section_ID);
	}

	@Override
	public int getDataEntry_Section_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DataEntry_Section_ID);
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab()
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class);
	}

	@Override
	public void setDataEntry_SubTab(final de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class, DataEntry_SubTab);
	}

	@Override
	public void setDataEntry_SubTab_ID (final int DataEntry_SubTab_ID)
	{
		if (DataEntry_SubTab_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_SubTab_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_SubTab_ID, DataEntry_SubTab_ID);
	}

	@Override
	public int getDataEntry_SubTab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DataEntry_SubTab_ID);
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
	public void setIsInitiallyClosed (final boolean IsInitiallyClosed)
	{
		set_Value (COLUMNNAME_IsInitiallyClosed, IsInitiallyClosed);
	}

	@Override
	public boolean isInitiallyClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInitiallyClosed);
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
	public void setSectionName (final java.lang.String SectionName)
	{
		set_Value (COLUMNNAME_SectionName, SectionName);
	}

	@Override
	public java.lang.String getSectionName() 
	{
		return get_ValueAsString(COLUMNNAME_SectionName);
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