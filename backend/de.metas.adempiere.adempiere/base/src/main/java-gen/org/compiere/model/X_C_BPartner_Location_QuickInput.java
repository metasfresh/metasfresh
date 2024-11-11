// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Location_QuickInput
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Location_QuickInput extends org.compiere.model.PO implements I_C_BPartner_Location_QuickInput, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2057545653L;

    /** Standard Constructor */
    public X_C_BPartner_Location_QuickInput (final Properties ctx, final int C_BPartner_Location_QuickInput_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Location_QuickInput_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Location_QuickInput (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setC_BPartner_Location_QuickInput_ID (final int C_BPartner_Location_QuickInput_ID)
	{
		if (C_BPartner_Location_QuickInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_QuickInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_QuickInput_ID, C_BPartner_Location_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_Location_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_QuickInput_ID);
	}

	@Override
	public org.compiere.model.I_C_BPartner_QuickInput getC_BPartner_QuickInput()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class);
	}

	@Override
	public void setC_BPartner_QuickInput(final org.compiere.model.I_C_BPartner_QuickInput C_BPartner_QuickInput)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_QuickInput_ID, org.compiere.model.I_C_BPartner_QuickInput.class, C_BPartner_QuickInput);
	}

	@Override
	public void setC_BPartner_QuickInput_ID (final int C_BPartner_QuickInput_ID)
	{
		if (C_BPartner_QuickInput_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_QuickInput_ID, C_BPartner_QuickInput_ID);
	}

	@Override
	public int getC_BPartner_QuickInput_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_QuickInput_ID);
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(final org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	@Override
	public void setC_Location_ID (final int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, C_Location_ID);
	}

	@Override
	public int getC_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Location_ID);
	}

	@Override
	public void setEMail (final @Nullable java.lang.String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
	}

	@Override
	public void setFax (final @Nullable java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	@Override
	public java.lang.String getFax() 
	{
		return get_ValueAsString(COLUMNNAME_Fax);
	}

	@Override
	public void setGLN (final @Nullable java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
		return get_ValueAsString(COLUMNNAME_GLN);
	}

	@Override
	public void setIsBillTo (final boolean IsBillTo)
	{
		set_Value (COLUMNNAME_IsBillTo, IsBillTo);
	}

	@Override
	public boolean isBillTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillTo);
	}

	@Override
	public void setIsBillToDefault (final boolean IsBillToDefault)
	{
		set_Value (COLUMNNAME_IsBillToDefault, IsBillToDefault);
	}

	@Override
	public boolean isBillToDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBillToDefault);
	}

	@Override
	public void setIsHandOverLocation (final boolean IsHandOverLocation)
	{
		set_Value (COLUMNNAME_IsHandOverLocation, IsHandOverLocation);
	}

	@Override
	public boolean isHandOverLocation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHandOverLocation);
	}

	@Override
	public void setIsRemitTo (final boolean IsRemitTo)
	{
		set_Value (COLUMNNAME_IsRemitTo, IsRemitTo);
	}

	@Override
	public boolean isRemitTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRemitTo);
	}

	@Override
	public void setIsReplicationLookupDefault (final boolean IsReplicationLookupDefault)
	{
		set_Value (COLUMNNAME_IsReplicationLookupDefault, IsReplicationLookupDefault);
	}

	@Override
	public boolean isReplicationLookupDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReplicationLookupDefault);
	}

	@Override
	public void setIsShipTo (final boolean IsShipTo)
	{
		set_Value (COLUMNNAME_IsShipTo, IsShipTo);
	}

	@Override
	public boolean isShipTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipTo);
	}

	@Override
	public void setIsShipToDefault (final boolean IsShipToDefault)
	{
		set_Value (COLUMNNAME_IsShipToDefault, IsShipToDefault);
	}

	@Override
	public boolean isShipToDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShipToDefault);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setPhone (final @Nullable java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
		return get_ValueAsString(COLUMNNAME_Phone);
	}

	@Override
	public void setPhone2 (final @Nullable java.lang.String Phone2)
	{
		set_Value (COLUMNNAME_Phone2, Phone2);
	}

	@Override
	public java.lang.String getPhone2() 
	{
		return get_ValueAsString(COLUMNNAME_Phone2);
	}

	@Override
	public void setSetup_Place_No (final @Nullable java.lang.String Setup_Place_No)
	{
		set_Value (COLUMNNAME_Setup_Place_No, Setup_Place_No);
	}

	@Override
	public java.lang.String getSetup_Place_No() 
	{
		return get_ValueAsString(COLUMNNAME_Setup_Place_No);
	}

	@Override
	public void setVisitorsAddress (final boolean VisitorsAddress)
	{
		set_Value (COLUMNNAME_VisitorsAddress, VisitorsAddress);
	}

	@Override
	public boolean isVisitorsAddress() 
	{
		return get_ValueAsBoolean(COLUMNNAME_VisitorsAddress);
	}
}