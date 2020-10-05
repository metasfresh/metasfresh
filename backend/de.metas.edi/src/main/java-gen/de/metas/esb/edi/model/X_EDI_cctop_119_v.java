/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_119_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_119_v extends org.compiere.model.PO implements I_EDI_cctop_119_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 931156786L;

    /** Standard Constructor */
    public X_EDI_cctop_119_v (Properties ctx, int EDI_cctop_119_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_119_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_119_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAddress1 (java.lang.String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	@Override
	public java.lang.String getAddress1() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}

	@Override
	public void setAddress2 (java.lang.String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	@Override
	public java.lang.String getAddress2() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
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
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setCity (java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}

	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	@Override
	public void seteancom_locationtype (java.lang.String eancom_locationtype)
	{
		set_Value (COLUMNNAME_eancom_locationtype, eancom_locationtype);
	}

	@Override
	public java.lang.String geteancom_locationtype() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_eancom_locationtype);
	}

	@Override
	public void setEDI_cctop_119_v_ID (int EDI_cctop_119_v_ID)
	{
		if (EDI_cctop_119_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_119_v_ID, Integer.valueOf(EDI_cctop_119_v_ID));
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
	public void setEDI_cctop_invoic_v(de.metas.esb.edi.model.I_EDI_cctop_invoic_v EDI_cctop_invoic_v)
	{
		set_ValueFromPO(COLUMNNAME_EDI_cctop_invoic_v_ID, de.metas.esb.edi.model.I_EDI_cctop_invoic_v.class, EDI_cctop_invoic_v);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setFax (java.lang.String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	@Override
	public java.lang.String getFax() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Fax);
	}

	@Override
	public void setGLN (java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	@Override
	public java.lang.String getGLN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setName2 (java.lang.String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	@Override
	public java.lang.String getName2() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name2);
	}

	@Override
	public void setPhone (java.lang.String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	@Override
	public java.lang.String getPhone() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Phone);
	}

	@Override
	public void setPostal (java.lang.String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	@Override
	public java.lang.String getPostal() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}

	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	@Override
	public java.lang.String getReferenceNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
	}

	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	@Override
	public void setVATaxID (java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
	}
}