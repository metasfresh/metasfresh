<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_119_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_119_v extends org.compiere.model.PO implements I_EDI_cctop_119_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 931156786L;

    /** Standard Constructor */
    public X_EDI_cctop_119_v (Properties ctx, int EDI_cctop_119_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_119_v extends org.compiere.model.PO implements I_EDI_cctop_119_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1699061030L;

    /** Standard Constructor */
    public X_EDI_cctop_119_v (final Properties ctx, final int EDI_cctop_119_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_119_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_119_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_119_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
<<<<<<< HEAD
	protected org.compiere.model.POInfo initPO(Properties ctx)
=======
	protected org.compiere.model.POInfo initPO(final Properties ctx)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
<<<<<<< HEAD
	public void setAddress1 (java.lang.String Address1)
=======
	public void setAddress1 (final @Nullable java.lang.String Address1)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	@Override
	public java.lang.String getAddress1() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	@Override
	public void setAddress2 (java.lang.String Address2)
=======
		return get_ValueAsString(COLUMNNAME_Address1);
	}

	@Override
	public void setAddress2 (final @Nullable java.lang.String Address2)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	@Override
	public java.lang.String getAddress2() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
=======
		return get_ValueAsString(COLUMNNAME_Address2);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
=======
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
=======
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID)
=======
	public void setC_Invoice_ID (final int C_Invoice_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
=======
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
<<<<<<< HEAD
	public void setCity (java.lang.String City)
=======
	public void setCity (final @Nullable java.lang.String City)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	@Override
	public void setCountryCode (java.lang.String CountryCode)
=======
		return get_ValueAsString(COLUMNNAME_City);
	}

	@Override
	public void setContact (final @Nullable java.lang.String Contact)
	{
		set_ValueNoCheck (COLUMNNAME_Contact, Contact);
	}

	@Override
	public java.lang.String getContact() 
	{
		return get_ValueAsString(COLUMNNAME_Contact);
	}

	@Override
	public void setCountryCode (final @Nullable java.lang.String CountryCode)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	@Override
	public void seteancom_locationtype (java.lang.String eancom_locationtype)
=======
		return get_ValueAsString(COLUMNNAME_CountryCode);
	}

	@Override
	public void seteancom_locationtype (final @Nullable java.lang.String eancom_locationtype)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_eancom_locationtype, eancom_locationtype);
	}

	@Override
	public java.lang.String geteancom_locationtype() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_eancom_locationtype);
	}

	@Override
	public void setEDI_cctop_119_v_ID (int EDI_cctop_119_v_ID)
=======
		return get_ValueAsString(COLUMNNAME_eancom_locationtype);
	}

	@Override
	public void setEDI_cctop_119_v_ID (final int EDI_cctop_119_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_119_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, Integer.valueOf(EDI_cctop_119_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, EDI_cctop_119_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getEDI_cctop_119_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_119_v_ID);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_cctop_invoic_v getEDI_cctop_invoic_v()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
=======
	public void setEDI_cctop_invoic_v(final de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
=======
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
=======
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
<<<<<<< HEAD
	public void setFax (java.lang.String Fax)
=======
	public void setFax (final @Nullable java.lang.String Fax)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	@Override
	public java.lang.String getFax() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	@Override
	public void setGLN (java.lang.String GLN)
=======
		return get_ValueAsString(COLUMNNAME_Fax);
	}

	@Override
	public void setGLN (final @Nullable java.lang.String GLN)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
=======
		return get_ValueAsString(COLUMNNAME_GLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
=======
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOut_ID (int M_InOut_ID)
=======
	public void setM_InOut_ID (final int M_InOut_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
=======
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
<<<<<<< HEAD
	public void setName (java.lang.String Name)
=======
	public void setName (final @Nullable java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (java.lang.String Name2)
=======
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (final @Nullable java.lang.String Name2)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	@Override
	public void setPhone (java.lang.String Phone)
=======
		return get_ValueAsString(COLUMNNAME_Name2);
	}

	@Override
	public void setPhone (final @Nullable java.lang.String Phone)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	@Override
	public void setPostal (java.lang.String Postal)
=======
		return get_ValueAsString(COLUMNNAME_Phone);
	}

	@Override
	public void setPostal (final @Nullable java.lang.String Postal)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	@Override
	public java.lang.String getPostal() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
=======
		return get_ValueAsString(COLUMNNAME_Postal);
	}

	@Override
	public void setReferenceNo (final @Nullable java.lang.String ReferenceNo)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setValue (java.lang.String Value)
=======
		return get_ValueAsString(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setSetup_Place_No (final @Nullable java.lang.String Setup_Place_No)
	{
		set_ValueNoCheck (COLUMNNAME_Setup_Place_No, Setup_Place_No);
	}

	@Override
	public java.lang.String getSetup_Place_No() 
	{
		return get_ValueAsString(COLUMNNAME_Setup_Place_No);
	}

	@Override
	public void setSiteName (final @Nullable java.lang.String SiteName)
	{
		set_ValueNoCheck (COLUMNNAME_SiteName, SiteName);
	}

	@Override
	public java.lang.String getSiteName() 
	{
		return get_ValueAsString(COLUMNNAME_SiteName);
	}

	@Override
	public void setValue (final @Nullable java.lang.String Value)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	@Override
	public void setVATaxID (java.lang.String VATaxID)
=======
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setVATaxID (final @Nullable java.lang.String VATaxID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
=======
		return get_ValueAsString(COLUMNNAME_VATaxID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}