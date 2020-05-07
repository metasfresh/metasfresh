/** Generated Model - DO NOT CHANGE */
package de.metas.payment.esr.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ESR_Import
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ESR_Import extends org.compiere.model.PO implements I_ESR_Import, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1770319772L;

    /** Standard Constructor */
    public X_ESR_Import (Properties ctx, int ESR_Import_ID, String trxName)
    {
      super (ctx, ESR_Import_ID, trxName);
      /** if (ESR_Import_ID == 0)
        {
			setC_BP_BankAccount_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setESR_Import_ID (0);
			setIsReceipt (true); // Y
			setIsValid (false); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_ESR_Import (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Attachment entry.
		@param AD_AttachmentEntry_ID Attachment entry	  */
	@Override
	public void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID)
	{
		if (AD_AttachmentEntry_ID < 1) 
			set_Value (COLUMNNAME_AD_AttachmentEntry_ID, null);
		else 
			set_Value (COLUMNNAME_AD_AttachmentEntry_ID, Integer.valueOf(AD_AttachmentEntry_ID));
	}

	/** Get Attachment entry.
		@return Attachment entry	  */
	@Override
	public int getAD_AttachmentEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_AttachmentEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DataType AD_Reference_ID=540728
	 * Reference name: ESR_Import_DataType
	 */
	public static final int DATATYPE_AD_Reference_ID=540728;
	/** V11 = V11 */
	public static final String DATATYPE_V11 = "V11";
	/** camt.54 = camt.54 */
	public static final String DATATYPE_Camt54 = "camt.54";
	/** Set Daten-Typ.
		@param DataType 
		Art der Daten
	  */
	@Override
	public void setDataType (java.lang.String DataType)
	{

		set_Value (COLUMNNAME_DataType, DataType);
	}

	/** Get Daten-Typ.
		@return Art der Daten
	  */
	@Override
	public java.lang.String getDataType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataType);
	}

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	@Override
	public void setDateDoc (java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	@Override
	public java.sql.Timestamp getDateDoc () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Gesamtbetrag.
		@param ESR_Control_Amount Gesamtbetrag	  */
	@Override
	public void setESR_Control_Amount (java.math.BigDecimal ESR_Control_Amount)
	{
		set_Value (COLUMNNAME_ESR_Control_Amount, ESR_Control_Amount);
	}

	/** Get Gesamtbetrag.
		@return Gesamtbetrag	  */
	@Override
	public java.math.BigDecimal getESR_Control_Amount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ESR_Control_Amount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Anzahl Transaktionen (kontr.).
		@param ESR_Control_Trx_Qty 
		Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)
	  */
	@Override
	public void setESR_Control_Trx_Qty (java.math.BigDecimal ESR_Control_Trx_Qty)
	{
		set_Value (COLUMNNAME_ESR_Control_Trx_Qty, ESR_Control_Trx_Qty);
	}

	/** Get Anzahl Transaktionen (kontr.).
		@return Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)
	  */
	@Override
	public java.math.BigDecimal getESR_Control_Trx_Qty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ESR_Control_Trx_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set ESR Zahlungsimport.
		@param ESR_Import_ID ESR Zahlungsimport	  */
	@Override
	public void setESR_Import_ID (int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_Import_ID, Integer.valueOf(ESR_Import_ID));
	}

	/** Get ESR Zahlungsimport.
		@return ESR Zahlungsimport	  */
	@Override
	public int getESR_Import_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ESR_Import_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anzahl Transaktionen.
		@param ESR_Trx_Qty 
		Anzahl der importierten Zeilen
	  */
	@Override
	public void setESR_Trx_Qty (java.math.BigDecimal ESR_Trx_Qty)
	{
		throw new IllegalArgumentException ("ESR_Trx_Qty is virtual column");	}

	/** Get Anzahl Transaktionen.
		@return Anzahl der importierten Zeilen
	  */
	@Override
	public java.math.BigDecimal getESR_Trx_Qty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ESR_Trx_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Hash.
		@param Hash Hash	  */
	@Override
	public void setHash (java.lang.String Hash)
	{
		set_Value (COLUMNNAME_Hash, Hash);
	}

	/** Get Hash.
		@return Hash	  */
	@Override
	public java.lang.String getHash () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Hash);
	}

	/** Set Zahlungseingang.
		@param IsReceipt 
		Dies ist eine Verkaufs-Transaktion (Zahlungseingang)
	  */
	@Override
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Zahlungseingang.
		@return Dies ist eine Verkaufs-Transaktion (Zahlungseingang)
	  */
	@Override
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set GĂĽltig.
		@param IsValid 
		Element ist gĂĽltig
	  */
	@Override
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get GĂĽltig.
		@return Element ist gĂĽltig
	  */
	@Override
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
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
		throw new IllegalArgumentException ("Processing is virtual column");	}

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
}