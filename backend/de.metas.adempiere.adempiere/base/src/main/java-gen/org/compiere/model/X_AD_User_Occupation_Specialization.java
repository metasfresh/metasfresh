// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_User_Occupation_Specialization
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_User_Occupation_Specialization extends org.compiere.model.PO implements I_AD_User_Occupation_Specialization, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 887106401L;

    /** Standard Constructor */
    public X_AD_User_Occupation_Specialization (final Properties ctx, final int AD_User_Occupation_Specialization_ID, @Nullable final String trxName)
    {
      super (ctx, AD_User_Occupation_Specialization_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_User_Occupation_Specialization (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_User_Occupation_Specialization_ID (final int AD_User_Occupation_Specialization_ID)
	{
		if (AD_User_Occupation_Specialization_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Occupation_Specialization_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Occupation_Specialization_ID, AD_User_Occupation_Specialization_ID);
	}

	@Override
	public int getAD_User_Occupation_Specialization_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Occupation_Specialization_ID);
	}

	@Override
	public org.compiere.model.I_CRM_Occupation getCRM_Occupation()
	{
		return get_ValueAsPO(COLUMNNAME_CRM_Occupation_ID, org.compiere.model.I_CRM_Occupation.class);
	}

	@Override
	public void setCRM_Occupation(final org.compiere.model.I_CRM_Occupation CRM_Occupation)
	{
		set_ValueFromPO(COLUMNNAME_CRM_Occupation_ID, org.compiere.model.I_CRM_Occupation.class, CRM_Occupation);
	}

	@Override
	public void setCRM_Occupation_ID (final int CRM_Occupation_ID)
	{
		if (CRM_Occupation_ID < 1) 
			set_Value (COLUMNNAME_CRM_Occupation_ID, null);
		else 
			set_Value (COLUMNNAME_CRM_Occupation_ID, CRM_Occupation_ID);
	}

	@Override
	public int getCRM_Occupation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_CRM_Occupation_ID);
	}
}