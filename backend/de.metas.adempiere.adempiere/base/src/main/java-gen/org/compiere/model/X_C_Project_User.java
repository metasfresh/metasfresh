// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Project_User
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Project_User extends org.compiere.model.PO implements I_C_Project_User, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1904000910L;

    /** Standard Constructor */
    public X_C_Project_User (final Properties ctx, final int C_Project_User_ID, @Nullable final String trxName)
    {
      super (ctx, C_Project_User_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Project_User (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_Location_ID (final int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Bill_Location_ID);
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setBill_User_ID (final int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Bill_User_ID);
	}

	@Override
	public int getBill_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID);
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
	public void setC_Project_ID (final int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, C_Project_ID);
	}

	@Override
	public int getC_Project_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_ID);
	}

	/** 
	 * C_Project_Role AD_Reference_ID=540905
	 * Reference name: c_project_role
	 */
	public static final int C_PROJECT_ROLE_AD_Reference_ID=540905;
	/** Project Manager = PM */
	public static final String C_PROJECT_ROLE_ProjectManager = "PM";
	/** Project Team Member = PT */
	public static final String C_PROJECT_ROLE_ProjectTeamMember = "PT";
	/** Dozent = SD */
	public static final String C_PROJECT_ROLE_Dozent = "SD";
	/** Teilnehmer = ST */
	public static final String C_PROJECT_ROLE_Teilnehmer = "ST";
	/** Ansprechpartner Hotel = SH */
	public static final String C_PROJECT_ROLE_AnsprechpartnerHotel = "SH";
	/** Aussendienst = SA */
	public static final String C_PROJECT_ROLE_Aussendienst = "SA";
	@Override
	public void setC_Project_Role (final @Nullable java.lang.String C_Project_Role)
	{
		set_Value (COLUMNNAME_C_Project_Role, C_Project_Role);
	}

	@Override
	public java.lang.String getC_Project_Role() 
	{
		return get_ValueAsString(COLUMNNAME_C_Project_Role);
	}

	@Override
	public void setC_Project_User_ID (final int C_Project_User_ID)
	{
		if (C_Project_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Project_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Project_User_ID, C_Project_User_ID);
	}

	@Override
	public int getC_Project_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Project_User_ID);
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

	@Override
	public void setIsAccommodationBooking (final boolean IsAccommodationBooking)
	{
		set_Value (COLUMNNAME_IsAccommodationBooking, IsAccommodationBooking);
	}

	@Override
	public boolean isAccommodationBooking() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAccommodationBooking);
	}

	@Override
	public org.compiere.model.I_R_Status getR_Status()
	{
		return get_ValueAsPO(COLUMNNAME_R_Status_ID, org.compiere.model.I_R_Status.class);
	}

	@Override
	public void setR_Status(final org.compiere.model.I_R_Status R_Status)
	{
		set_ValueFromPO(COLUMNNAME_R_Status_ID, org.compiere.model.I_R_Status.class, R_Status);
	}

	@Override
	public void setR_Status_ID (final int R_Status_ID)
	{
		if (R_Status_ID < 1) 
			set_Value (COLUMNNAME_R_Status_ID, null);
		else 
			set_Value (COLUMNNAME_R_Status_ID, R_Status_ID);
	}

	@Override
	public int getR_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_Status_ID);
	}
}