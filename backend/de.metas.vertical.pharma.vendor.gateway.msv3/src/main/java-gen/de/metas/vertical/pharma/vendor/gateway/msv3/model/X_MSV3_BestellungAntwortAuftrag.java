/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.msv3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_BestellungAntwortAuftrag
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_BestellungAntwortAuftrag extends org.compiere.model.PO implements I_MSV3_BestellungAntwortAuftrag, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1781439913L;

    /** Standard Constructor */
    public X_MSV3_BestellungAntwortAuftrag (Properties ctx, int MSV3_BestellungAntwortAuftrag_ID, String trxName)
    {
      super (ctx, MSV3_BestellungAntwortAuftrag_ID, trxName);
      /** if (MSV3_BestellungAntwortAuftrag_ID == 0)
        {
			setMSV3_Auftragsart (null);
			setMSV3_AuftragsSupportID (0);
			setMSV3_BestellungAntwortAuftrag_ID (0);
			setMSV3_BestellungAntwort_ID (0);
			setMSV3_Id (null);
        } */
    }

    /** Load Constructor */
    public X_MSV3_BestellungAntwortAuftrag (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_Order getC_OrderPO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderPO(org.compiere.model.I_C_Order C_OrderPO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class, C_OrderPO);
	}

	/** Set Bestellung.
		@param C_OrderPO_ID 
		Bestellung
	  */
	@Override
	public void setC_OrderPO_ID (int C_OrderPO_ID)
	{
		if (C_OrderPO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderPO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderPO_ID, Integer.valueOf(C_OrderPO_ID));
	}

	/** Get Bestellung.
		@return Bestellung
	  */
	@Override
	public int getC_OrderPO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderPO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MSV3_Auftragsart AD_Reference_ID=540825
	 * Reference name: MSV3_Auftragsart
	 */
	public static final int MSV3_AUFTRAGSART_AD_Reference_ID=540825;
	/** Normal = NORMAL */
	public static final String MSV3_AUFTRAGSART_Normal = "NORMAL";
	/** Stapel = SONDER */
	public static final String MSV3_AUFTRAGSART_Stapel = "SONDER";
	/** Sonder = STAPEL */
	public static final String MSV3_AUFTRAGSART_Sonder = "STAPEL";
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

	@Override
	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo getMSV3_Auftragsfehler() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_Auftragsfehler_ID, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo.class);
	}

	@Override
	public void setMSV3_Auftragsfehler(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo MSV3_Auftragsfehler)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_Auftragsfehler_ID, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo.class, MSV3_Auftragsfehler);
	}

	/** Set Auftragsfehler.
		@param MSV3_Auftragsfehler_ID Auftragsfehler	  */
	@Override
	public void setMSV3_Auftragsfehler_ID (int MSV3_Auftragsfehler_ID)
	{
		if (MSV3_Auftragsfehler_ID < 1) 
			set_Value (COLUMNNAME_MSV3_Auftragsfehler_ID, null);
		else 
			set_Value (COLUMNNAME_MSV3_Auftragsfehler_ID, Integer.valueOf(MSV3_Auftragsfehler_ID));
	}

	/** Get Auftragsfehler.
		@return Auftragsfehler	  */
	@Override
	public int getMSV3_Auftragsfehler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Auftragsfehler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set MSV3_BestellungAntwortAuftrag.
		@param MSV3_BestellungAntwortAuftrag_ID MSV3_BestellungAntwortAuftrag	  */
	@Override
	public void setMSV3_BestellungAntwortAuftrag_ID (int MSV3_BestellungAntwortAuftrag_ID)
	{
		if (MSV3_BestellungAntwortAuftrag_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID, Integer.valueOf(MSV3_BestellungAntwortAuftrag_ID));
	}

	/** Get MSV3_BestellungAntwortAuftrag.
		@return MSV3_BestellungAntwortAuftrag	  */
	@Override
	public int getMSV3_BestellungAntwortAuftrag_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwortAuftrag_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort getMSV3_BestellungAntwort() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MSV3_BestellungAntwort_ID, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort.class);
	}

	@Override
	public void setMSV3_BestellungAntwort(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort MSV3_BestellungAntwort)
	{
		set_ValueFromPO(COLUMNNAME_MSV3_BestellungAntwort_ID, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAntwort.class, MSV3_BestellungAntwort);
	}

	/** Set MSV3_BestellungAntwort.
		@param MSV3_BestellungAntwort_ID MSV3_BestellungAntwort	  */
	@Override
	public void setMSV3_BestellungAntwort_ID (int MSV3_BestellungAntwort_ID)
	{
		if (MSV3_BestellungAntwort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_BestellungAntwort_ID, Integer.valueOf(MSV3_BestellungAntwort_ID));
	}

	/** Get MSV3_BestellungAntwort.
		@return MSV3_BestellungAntwort	  */
	@Override
	public int getMSV3_BestellungAntwort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_BestellungAntwort_ID);
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