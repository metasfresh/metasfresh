// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Responsible
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_Responsible extends org.compiere.model.PO implements I_AD_WF_Responsible, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 454491763L;

    /** Standard Constructor */
    public X_AD_WF_Responsible (final Properties ctx, final int AD_WF_Responsible_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Responsible_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Responsible (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_WF_Responsible_ID (final int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Responsible_ID, AD_WF_Responsible_ID);
	}

	@Override
	public int getAD_WF_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Responsible_ID);
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	/** 
	 * ResponsibleType AD_Reference_ID=304
	 * Reference name: WF_Participant Type
	 */
	public static final int RESPONSIBLETYPE_AD_Reference_ID=304;
	/** Organisation = O */
	public static final String RESPONSIBLETYPE_Organisation = "O";
	/** Human = H */
	public static final String RESPONSIBLETYPE_Human = "H";
	/** Rolle = R */
	public static final String RESPONSIBLETYPE_Rolle = "R";
	/** Systemressource = S */
	public static final String RESPONSIBLETYPE_Systemressource = "S";
	/** Other = X */
	public static final String RESPONSIBLETYPE_Other = "X";
	@Override
	public void setResponsibleType (final java.lang.String ResponsibleType)
	{
		set_Value (COLUMNNAME_ResponsibleType, ResponsibleType);
	}

	@Override
	public java.lang.String getResponsibleType() 
	{
		return get_ValueAsString(COLUMNNAME_ResponsibleType);
	}
}