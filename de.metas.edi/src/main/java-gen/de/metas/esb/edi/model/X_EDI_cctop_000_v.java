/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_000_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_000_v extends org.compiere.model.PO implements I_EDI_cctop_000_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1437630575L;

    /** Standard Constructor */
    public X_EDI_cctop_000_v (Properties ctx, int EDI_cctop_000_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_000_v_ID, trxName);
      /** if (EDI_cctop_000_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_000_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI_cctop_000_v.
		@param EDI_cctop_000_v_ID EDI_cctop_000_v	  */
	@Override
	public void setEDI_cctop_000_v_ID (int EDI_cctop_000_v_ID)
	{
		if (EDI_cctop_000_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_000_v_ID, Integer.valueOf(EDI_cctop_000_v_ID));
	}

	/** Get EDI_cctop_000_v.
		@return EDI_cctop_000_v	  */
	@Override
	public int getEDI_cctop_000_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_000_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EDI-ID des DESADV-Empfängers.
		@param EdiRecipientGLN EDI-ID des DESADV-Empfängers	  */
	@Override
	public void setEdiRecipientGLN (java.lang.String EdiRecipientGLN)
	{
		set_Value (COLUMNNAME_EdiRecipientGLN, EdiRecipientGLN);
	}

	/** Get EDI-ID des DESADV-Empfängers.
		@return EDI-ID des DESADV-Empfängers	  */
	@Override
	public java.lang.String getEdiRecipientGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EdiRecipientGLN);
	}
}