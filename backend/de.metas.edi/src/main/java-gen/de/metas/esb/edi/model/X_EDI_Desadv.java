/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_Desadv
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_Desadv extends org.compiere.model.PO implements I_EDI_Desadv, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -480879331L;

    /** Standard Constructor */
    public X_EDI_Desadv (Properties ctx, int EDI_Desadv_ID, String trxName)
    {
      super (ctx, EDI_Desadv_ID, trxName);
      /** if (EDI_Desadv_ID == 0)
        {
			setEDI_Desadv_ID (0);
			setProcessing (false); // N
        } */
    }

    /** Load Constructor */
    public X_EDI_Desadv (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Rechnungsstandort.
		@param Bill_Location_ID 
		Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Rechnungsstandort.
		@return Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragsdatum.
		@param DateOrdered 
		Datum des Auftrags
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Datum des Auftrags
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set DESADV.
		@param EDI_Desadv_ID DESADV	  */
	@Override
	public void setEDI_Desadv_ID (int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, Integer.valueOf(EDI_Desadv_ID));
	}

	/** Get DESADV.
		@return DESADV	  */
	@Override
	public int getEDI_Desadv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_Desadv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geliefert % Minimum.
		@param EDI_DESADV_MinimumSumPercentage Geliefert % Minimum	  */
	@Override
	public void setEDI_DESADV_MinimumSumPercentage (java.math.BigDecimal EDI_DESADV_MinimumSumPercentage)
	{
		set_Value (COLUMNNAME_EDI_DESADV_MinimumSumPercentage, EDI_DESADV_MinimumSumPercentage);
	}

	/** Get Geliefert % Minimum.
		@return Geliefert % Minimum	  */
	@Override
	public java.math.BigDecimal getEDI_DESADV_MinimumSumPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EDI_DESADV_MinimumSumPercentage);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Geliefert %.
		@param EDI_DESADV_SumPercentage Geliefert %	  */
	@Override
	public void setEDI_DESADV_SumPercentage (java.math.BigDecimal EDI_DESADV_SumPercentage)
	{
		throw new IllegalArgumentException ("EDI_DESADV_SumPercentage is virtual column");	}

	/** Get Geliefert %.
		@return Geliefert %	  */
	@Override
	public java.math.BigDecimal getEDI_DESADV_SumPercentage () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EDI_DESADV_SumPercentage);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set EDI Fehlermeldung.
		@param EDIErrorMsg EDI Fehlermeldung	  */
	@Override
	public void setEDIErrorMsg (java.lang.String EDIErrorMsg)
	{
		set_Value (COLUMNNAME_EDIErrorMsg, EDIErrorMsg);
	}

	/** Get EDI Fehlermeldung.
		@return EDI Fehlermeldung	  */
	@Override
	public java.lang.String getEDIErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDIErrorMsg);
	}

	/** 
	 * EDI_ExportStatus AD_Reference_ID=540381
	 * Reference name: EDI_ExportStatus
	 */
	public static final int EDI_EXPORTSTATUS_AD_Reference_ID=540381;
	/** Invalid = I */
	public static final String EDI_EXPORTSTATUS_Invalid = "I";
	/** Pending = P */
	public static final String EDI_EXPORTSTATUS_Pending = "P";
	/** Sent = S */
	public static final String EDI_EXPORTSTATUS_Sent = "S";
	/** SendingStarted = D */
	public static final String EDI_EXPORTSTATUS_SendingStarted = "D";
	/** Error = E */
	public static final String EDI_EXPORTSTATUS_Error = "E";
	/** Enqueued = U */
	public static final String EDI_EXPORTSTATUS_Enqueued = "U";
	/** DontSend = N */
	public static final String EDI_EXPORTSTATUS_DontSend = "N";
	/** Set EDI Status Exportieren.
		@param EDI_ExportStatus EDI Status Exportieren	  */
	@Override
	public void setEDI_ExportStatus (java.lang.String EDI_ExportStatus)
	{

		set_Value (COLUMNNAME_EDI_ExportStatus, EDI_ExportStatus);
	}

	/** Get EDI Status Exportieren.
		@return EDI Status Exportieren	  */
	@Override
	public java.lang.String getEDI_ExportStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EDI_ExportStatus);
	}

	/** Set Übergabeadresse.
		@param HandOver_Location_ID Übergabeadresse	  */
	@Override
	public void setHandOver_Location_ID (int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, Integer.valueOf(HandOver_Location_ID));
	}

	/** Get Übergabeadresse.
		@return Übergabeadresse	  */
	@Override
	public int getHandOver_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bewegungsdatum.
		@param MovementDate 
		Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Bewegungsdatum.
		@return Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public java.sql.Timestamp getMovementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set UserFlag.
		@param UserFlag 
		Can be used to flag records and thus make them selectable from the UI via advanced search.
	  */
	@Override
	public void setUserFlag (java.lang.String UserFlag)
	{
		set_Value (COLUMNNAME_UserFlag, UserFlag);
	}

	/** Get UserFlag.
		@return Can be used to flag records and thus make them selectable from the UI via advanced search.
	  */
	@Override
	public java.lang.String getUserFlag () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserFlag);
	}
}