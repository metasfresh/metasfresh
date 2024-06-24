// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Workstation_UserAssign
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Workstation_UserAssign extends org.compiere.model.PO implements I_PP_Workstation_UserAssign, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1053625593L;

    /** Standard Constructor */
    public X_PP_Workstation_UserAssign (final Properties ctx, final int PP_Workstation_UserAssign_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Workstation_UserAssign_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Workstation_UserAssign (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setPP_Workstation_UserAssign_ID (final int PP_Workstation_UserAssign_ID)
	{
		if (PP_Workstation_UserAssign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Workstation_UserAssign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Workstation_UserAssign_ID, PP_Workstation_UserAssign_ID);
	}

	@Override
	public int getPP_Workstation_UserAssign_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Workstation_UserAssign_ID);
	}

	@Override
	public org.compiere.model.I_S_Resource getWorkStation()
	{
		return get_ValueAsPO(COLUMNNAME_WorkStation_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setWorkStation(final org.compiere.model.I_S_Resource WorkStation)
	{
		set_ValueFromPO(COLUMNNAME_WorkStation_ID, org.compiere.model.I_S_Resource.class, WorkStation);
	}

	@Override
	public void setWorkStation_ID (final int WorkStation_ID)
	{
		if (WorkStation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WorkStation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WorkStation_ID, WorkStation_ID);
	}

	@Override
	public int getWorkStation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WorkStation_ID);
	}
}