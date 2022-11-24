// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ILCandHandler
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ILCandHandler extends org.compiere.model.PO implements I_C_ILCandHandler, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 323164106L;

    /** Standard Constructor */
    public X_C_ILCandHandler (final Properties ctx, final int C_ILCandHandler_ID, @Nullable final String trxName)
    {
      super (ctx, C_ILCandHandler_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ILCandHandler (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_InCharge_ID (final int AD_User_InCharge_ID)
	{
		if (AD_User_InCharge_ID < 1) 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_InCharge_ID, AD_User_InCharge_ID);
	}

	@Override
	public int getAD_User_InCharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_InCharge_ID);
	}

	@Override
	public void setC_ILCandHandler_ID (final int C_ILCandHandler_ID)
	{
		if (C_ILCandHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ILCandHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ILCandHandler_ID, C_ILCandHandler_ID);
	}

	@Override
	public int getC_ILCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ILCandHandler_ID);
	}

	@Override
	public void setClassname (final java.lang.String Classname)
	{
		set_Value (COLUMNNAME_Classname, Classname);
	}

	@Override
	public java.lang.String getClassname() 
	{
		return get_ValueAsString(COLUMNNAME_Classname);
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
	public void setIs_AD_User_InCharge_UI_Setting (final boolean Is_AD_User_InCharge_UI_Setting)
	{
		set_Value (COLUMNNAME_Is_AD_User_InCharge_UI_Setting, Is_AD_User_InCharge_UI_Setting);
	}

	@Override
	public boolean is_AD_User_InCharge_UI_Setting() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Is_AD_User_InCharge_UI_Setting);
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
	public void setTableName (final java.lang.String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	@Override
	public java.lang.String getTableName() 
	{
		return get_ValueAsString(COLUMNNAME_TableName);
	}
}