// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_Picking_BPartner
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_Picking_BPartner extends org.compiere.model.PO implements I_MobileUI_UserProfile_Picking_BPartner, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1677845357L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_Picking_BPartner (final Properties ctx, final int MobileUI_UserProfile_Picking_BPartner_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_Picking_BPartner_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_Picking_BPartner (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_BPartner_ID (final int MobileUI_UserProfile_Picking_BPartner_ID)
	{
		if (MobileUI_UserProfile_Picking_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_Picking_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_Picking_BPartner_ID, MobileUI_UserProfile_Picking_BPartner_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_MobileUI_UserProfile_Picking getMobileUI_UserProfile_Picking()
	{
		return get_ValueAsPO(COLUMNNAME_MobileUI_UserProfile_Picking_ID, org.compiere.model.I_MobileUI_UserProfile_Picking.class);
	}

	@Override
	public void setMobileUI_UserProfile_Picking(final org.compiere.model.I_MobileUI_UserProfile_Picking MobileUI_UserProfile_Picking)
	{
		set_ValueFromPO(COLUMNNAME_MobileUI_UserProfile_Picking_ID, org.compiere.model.I_MobileUI_UserProfile_Picking.class, MobileUI_UserProfile_Picking);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_ID (final int MobileUI_UserProfile_Picking_ID)
	{
		if (MobileUI_UserProfile_Picking_ID < 1) 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_ID, null);
		else 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_ID, MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public org.compiere.model.I_MobileUI_UserProfile_Picking_Job getMobileUI_UserProfile_Picking_Job()
	{
		return get_ValueAsPO(COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID, org.compiere.model.I_MobileUI_UserProfile_Picking_Job.class);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_Job(final org.compiere.model.I_MobileUI_UserProfile_Picking_Job MobileUI_UserProfile_Picking_Job)
	{
		set_ValueFromPO(COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID, org.compiere.model.I_MobileUI_UserProfile_Picking_Job.class, MobileUI_UserProfile_Picking_Job);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_Job_ID (final int MobileUI_UserProfile_Picking_Job_ID)
	{
		if (MobileUI_UserProfile_Picking_Job_ID < 1) 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID, null);
		else 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID, MobileUI_UserProfile_Picking_Job_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID);
	}
}