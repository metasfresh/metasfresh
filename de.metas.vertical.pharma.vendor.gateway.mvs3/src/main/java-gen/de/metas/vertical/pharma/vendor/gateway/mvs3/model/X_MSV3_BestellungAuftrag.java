/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_BestellungAuftrag
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_BestellungAuftrag extends org.compiere.model.PO implements I_MSV3_BestellungAuftrag, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 161243315L;

    /** Standard Constructor */
    public X_MSV3_BestellungAuftrag (Properties ctx, int MSV3_BestellungAuftrag_ID, String trxName)
    {
      super (ctx, MSV3_BestellungAuftrag_ID, trxName);
      /** if (MSV3_BestellungAuftrag_ID == 0)
        {
			setMSV3_Auftragsart (null);
			setMSV3_BestellungAuftrag_ID (0);
			setMSV3_Bestellung_ID (0);
			setMSV3_Id (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_BestellungAuftrag (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * MSV3_Auftragsart AD_Reference_ID=540825
	 * Reference name: MSV3_Auftragsart
	 */
	public static final int MSV3_AUFTRAGSART_AD_Reference_ID=540825;
	/** Normal = NORMAL */
	public static final String MSV3_AUFTRAGSART_Normal = "NORMAL";
	/** Stapel = STAPEL */
	public static final String MSV3_AUFTRAGSART_Stapel = "STAPEL";
	/** Sonder = SONDER */
	public static final String MSV3_AUFTRAGSART_Sonder = "SONDER";
	/** Versand = VERSAND */
	public static final String MSV3_AUFTRAGSART_Versand = "VERSAND";
	/** Set Auftragsart.
		@param MSV3_Auftragsart Auftragsart	  */
	@Override
	public void setMSV3_Auftragsart (java.lang.String MSV3_Auftragsart)
	{

		set_Value (COLUMNNAME_MSV3_Auftragsart, MSV3_Auftragsart);
	}

	/** Get Auftragsart.
		@return Auftragsart	  */
	@Override
	public java.lang.String getMSV3_Auftragsart () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Auftragsart);
	}

	/** Set Auftragskennung.
		@param MSV3_Auftragskennung Auftragskennung	  */
	@Override
	public void setMSV3_Auftragskennung (java.lang.String MSV3_Auftragskennung)
	{
		set_Value (COLUMNNAME_MSV3_Auftragskennung, MSV3_Auftragskennung);
	}

	/** Get Auftragskennung.
		@return Auftragskennung	  */
	@Override
	public java.lang.String getMSV3_Auftragskennung () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Auftragskennung);
	}

	/** Set AuftragsSupportID.
		@param MSV3_AuftragsSupportID AuftragsSupportID	  */
	@Override
	public void setMSV3_AuftragsSupportID (int MSV3_AuftragsSupportID)
	{
		set_Value (COLUMNNAME_MSV3_AuftragsSupportID, Integer.valueOf(MSV3_AuftragsSupportID));
	}

	/** Get AuftragsSupportID.
		@return AuftragsSupportID	  */
	@Override
	public int getMSV3_AuftragsSupportID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_AuftragsSupportID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MSV3_BestellungAuftrag.
		@param MSV3_BestellungAuftrag_ID MSV3_BestellungAuftrag	  */
	@Override
	public void setMSV3_BestellungAuftrag_ID (int MSV3_BestellungAuftrag_ID)
	{
		if (MSV3_BestellungAuftrag_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAuftrag_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAuftrag_ID, Integer.valueOf(MSV3_BestellungAuftrag_ID));
	}

	/** Get MSV3_BestellungAuftrag.
		@return MSV3_BestellungAuftrag	  */
	@Override
	public int getMSV3_BestellungAuftrag_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAuftrag_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung getMSV3_Bestellung() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_Bestellung_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung.class);
	}

	@Override
	public void setMSV3_Bestellung(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung MSV3_Bestellung)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_Bestellung_ID, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung.class, MSV3_Bestellung);
	}

	/** Set MSV3_Bestellung.
		@param MSV3_Bestellung_ID MSV3_Bestellung	  */
	@Override
	public void setMSV3_Bestellung_ID (int MSV3_Bestellung_ID)
	{
		if (MSV3_Bestellung_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Bestellung_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Bestellung_ID, Integer.valueOf(MSV3_Bestellung_ID));
	}

	/** Get MSV3_Bestellung.
		@return MSV3_Bestellung	  */
	@Override
	public int getMSV3_Bestellung_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Bestellung_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set GebindeId.
		@param MSV3_GebindeId GebindeId	  */
	@Override
	public void setMSV3_GebindeId (java.lang.String MSV3_GebindeId)
	{
		set_Value (COLUMNNAME_MSV3_GebindeId, MSV3_GebindeId);
	}

	/** Get GebindeId.
		@return GebindeId	  */
	@Override
	public java.lang.String getMSV3_GebindeId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_GebindeId);
	}

	/** Set Id.
		@param MSV3_Id Id	  */
	@Override
	public void setMSV3_Id (java.lang.String MSV3_Id)
	{
		set_Value (COLUMNNAME_MSV3_Id, MSV3_Id);
	}

	/** Get Id.
		@return Id	  */
	@Override
	public java.lang.String getMSV3_Id () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_Id);
	}
}