// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BPartner_Location
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Location extends org.compiere.model.PO implements I_C_BPartner_Location, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -860447504L;

    /** Standard Constructor */
    public X_C_BPartner_Location (final Properties ctx, final int C_BPartner_Location_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Location_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Location (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAddress (final @Nullable java.lang.String Address)
	{
		set_Value (COLUMNNAME_Address, Address);
	}

	@Override
	public java.lang.String getAddress() 
	{
		return get_ValueAsString(COLUMNNAME_Address);
	}

	@Override
	public org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class);
	}

	@Override
	public void setAD_Org_Mapping(final org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping)
	{
		set_ValueFromPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class, AD_Org_Mapping);
	}

	@Override
	public void setAD_Org_Mapping_ID (final int AD_Org_Mapping_ID)
	{
		if (AD_Org_Mapping_ID < 1) 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, AD_Org_Mapping_ID);
	}

	@Override
	public int getAD_Org_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Mapping_ID);
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
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
	public org.compiere.model.I_C_SalesRegion getC_SalesRegion()
	{
		return get_ValueAsPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class);
	}

	@Override
	public void setC_SalesRegion(final org.compiere.model.I_C_SalesRegion C_SalesRegion)
	{
		set_ValueFromPO(COLUMNNAME_C_SalesRegion_ID, org.compiere.model.I_C_SalesRegion.class, C_SalesRegion);
	}

	@Override
	public void setC_SalesRegion_ID (final int C_SalesRegion_ID)
	{
		if (C_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_C_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_C_SalesRegion_ID, C_SalesRegion_ID);
	}

	@Override
	public int getC_SalesRegion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SalesRegion_ID);
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
	public void setEMail2 (final @Nullable java.lang.String EMail2)
	{
		set_Value (COLUMNNAME_EMail2, EMail2);
	}

	@Override
	public java.lang.String getEMail2() 
	{
		return get_ValueAsString(COLUMNNAME_EMail2);
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
	public void setFax2 (final @Nullable java.lang.String Fax2)
	{
		set_Value (COLUMNNAME_Fax2, Fax2);
	}

	@Override
	public java.lang.String getFax2() 
	{
		return get_ValueAsString(COLUMNNAME_Fax2);
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
	public void setIsCommissionTo (final boolean IsCommissionTo)
	{
		set_Value (COLUMNNAME_IsCommissionTo, IsCommissionTo);
	}

	@Override
	public boolean isCommissionTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommissionTo);
	}

	@Override
	public void setIsCommissionToDefault (final boolean IsCommissionToDefault)
	{
		set_Value (COLUMNNAME_IsCommissionToDefault, IsCommissionToDefault);
	}

	@Override
	public boolean isCommissionToDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCommissionToDefault);
	}

	@Override
	public void setISDN (final @Nullable java.lang.String ISDN)
	{
		set_Value (COLUMNNAME_ISDN, ISDN);
	}

	@Override
	public java.lang.String getISDN() 
	{
		return get_ValueAsString(COLUMNNAME_ISDN);
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
	public void setIsPayFrom (final boolean IsPayFrom)
	{
		set_Value (COLUMNNAME_IsPayFrom, IsPayFrom);
	}

	@Override
	public boolean isPayFrom() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPayFrom);
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
	public void setIsSubscriptionTo (final boolean IsSubscriptionTo)
	{
		set_Value (COLUMNNAME_IsSubscriptionTo, IsSubscriptionTo);
	}

	@Override
	public boolean isSubscriptionTo() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubscriptionTo);
	}

	@Override
	public void setIsSubscriptionToDefault (final boolean IsSubscriptionToDefault)
	{
		set_Value (COLUMNNAME_IsSubscriptionToDefault, IsSubscriptionToDefault);
	}

	@Override
	public boolean isSubscriptionToDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSubscriptionToDefault);
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