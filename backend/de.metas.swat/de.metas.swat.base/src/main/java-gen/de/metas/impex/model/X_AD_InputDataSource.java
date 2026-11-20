// Generated Model - DO NOT CHANGE
package de.metas.impex.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_InputDataSource
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_InputDataSource extends org.compiere.model.PO implements I_AD_InputDataSource, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -746862744L;

    /** Standard Constructor */
    public X_AD_InputDataSource (final Properties ctx, final int AD_InputDataSource_ID, @Nullable final String trxName)
    {
      super (ctx, AD_InputDataSource_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_InputDataSource (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_InputDataSource_ID (final int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_InputDataSource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_InputDataSource_ID, AD_InputDataSource_ID);
	}

	@Override
	public int getAD_InputDataSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_InputDataSource_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsDestination (final boolean IsDestination)
	{
		set_Value (COLUMNNAME_IsDestination, IsDestination);
	}

	@Override
	public boolean isDestination() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDestination);
	}

	@Override
	public void setIsEDIChannel (final boolean IsEDIChannel)
	{
		set_Value (COLUMNNAME_IsEDIChannel, IsEDIChannel);
	}

	@Override
	public boolean isEDIChannel() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEDIChannel);
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
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}