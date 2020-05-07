/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DunningLevel
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DunningLevel extends org.compiere.model.PO implements I_C_DunningLevel, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2062827134L;

    /** Standard Constructor */
    public X_C_DunningLevel (Properties ctx, int C_DunningLevel_ID, String trxName)
    {
      super (ctx, C_DunningLevel_ID, trxName);
      /** if (C_DunningLevel_ID == 0)
        {
			setC_Dunning_ID (0);
			setC_DunningLevel_ID (0);
			setChargeFee (false);
			setChargeInterest (false);
			setDaysAfterDue (BigDecimal.ZERO);
			setDaysBetweenDunning (0);
			setIsDeliveryStop (false); // N
			setIsSetCreditStop (false);
			setIsSetPaymentTerm (false);
			setIsShowAllDue (false);
			setIsShowNotDue (false); // N
			setIsStatement (false); // N
			setIsWriteOff (false); // N
			setName (null);
			setPrintName (null);
        } */
    }

    /** Load Constructor */
    public X_C_DunningLevel (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_Dunning getC_Dunning() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class);
	}

	@Override
	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_ID, org.compiere.model.I_C_Dunning.class, C_Dunning);
	}

	/** Set Mahnung.
		@param C_Dunning_ID 
		Dunning Rules for overdue invoices
	  */
	@Override
	public void setC_Dunning_ID (int C_Dunning_ID)
	{
		if (C_Dunning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Dunning_ID, Integer.valueOf(C_Dunning_ID));
	}

	/** Get Mahnung.
		@return Dunning Rules for overdue invoices
	  */
	@Override
	public int getC_Dunning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mahnstufe.
		@param C_DunningLevel_ID Mahnstufe	  */
	@Override
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Mahnstufe.
		@return Mahnstufe	  */
	@Override
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class);
	}

	@Override
	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentTerm_ID, org.compiere.model.I_C_PaymentTerm.class, C_PaymentTerm);
	}

	/** Set Zahlungsbedingung.
		@param C_PaymentTerm_ID 
		Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Zahlungsbedingung.
		@return Die Bedingungen für die Bezahlung dieses Vorgangs
	  */
	@Override
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mahngebühr.
		@param ChargeFee 
		Indicates if fees will be charged for overdue invoices
	  */
	@Override
	public void setChargeFee (boolean ChargeFee)
	{
		set_Value (COLUMNNAME_ChargeFee, Boolean.valueOf(ChargeFee));
	}

	/** Get Mahngebühr.
		@return Indicates if fees will be charged for overdue invoices
	  */
	@Override
	public boolean isChargeFee () 
	{
		Object oo = get_Value(COLUMNNAME_ChargeFee);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mahnzinsen.
		@param ChargeInterest 
		Indicates if interest will be charged on overdue invoices
	  */
	@Override
	public void setChargeInterest (boolean ChargeInterest)
	{
		set_Value (COLUMNNAME_ChargeInterest, Boolean.valueOf(ChargeInterest));
	}

	/** Get Mahnzinsen.
		@return Indicates if interest will be charged on overdue invoices
	  */
	@Override
	public boolean isChargeInterest () 
	{
		Object oo = get_Value(COLUMNNAME_ChargeInterest);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Days after due date.
		@param DaysAfterDue 
		Days after due date to dun (if negative days until due)
	  */
	@Override
	public void setDaysAfterDue (java.math.BigDecimal DaysAfterDue)
	{
		set_Value (COLUMNNAME_DaysAfterDue, DaysAfterDue);
	}

	/** Get Days after due date.
		@return Days after due date to dun (if negative days until due)
	  */
	@Override
	public java.math.BigDecimal getDaysAfterDue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DaysAfterDue);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Tage zwischen Mahnungen.
		@param DaysBetweenDunning 
		Days between sending dunning notices
	  */
	@Override
	public void setDaysBetweenDunning (int DaysBetweenDunning)
	{
		set_Value (COLUMNNAME_DaysBetweenDunning, Integer.valueOf(DaysBetweenDunning));
	}

	/** Get Tage zwischen Mahnungen.
		@return Days between sending dunning notices
	  */
	@Override
	public int getDaysBetweenDunning () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DaysBetweenDunning);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public org.compiere.model.I_AD_PrintFormat getDunning_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Dunning_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setDunning_PrintFormat(org.compiere.model.I_AD_PrintFormat Dunning_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_Dunning_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, Dunning_PrintFormat);
	}

	/** Set Dunning Print Format.
		@param Dunning_PrintFormat_ID 
		Print Format for printing Dunning Letters
	  */
	@Override
	public void setDunning_PrintFormat_ID (int Dunning_PrintFormat_ID)
	{
		if (Dunning_PrintFormat_ID < 1) 
			set_Value (COLUMNNAME_Dunning_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_Dunning_PrintFormat_ID, Integer.valueOf(Dunning_PrintFormat_ID));
	}

	/** Get Dunning Print Format.
		@return Print Format for printing Dunning Letters
	  */
	@Override
	public int getDunning_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Dunning_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mahnpauschale.
		@param FeeAmt 
		Pauschale Mahngebühr
	  */
	@Override
	public void setFeeAmt (java.math.BigDecimal FeeAmt)
	{
		set_Value (COLUMNNAME_FeeAmt, FeeAmt);
	}

	/** Get Mahnpauschale.
		@return Pauschale Mahngebühr
	  */
	@Override
	public java.math.BigDecimal getFeeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FeeAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Interest in percent.
		@param InterestPercent 
		Percentage interest to charge on overdue invoices
	  */
	@Override
	public void setInterestPercent (java.math.BigDecimal InterestPercent)
	{
		set_Value (COLUMNNAME_InterestPercent, InterestPercent);
	}

	/** Get Interest in percent.
		@return Percentage interest to charge on overdue invoices
	  */
	@Override
	public java.math.BigDecimal getInterestPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InterestPercent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * InvoiceCollectionType AD_Reference_ID=394
	 * Reference name: C_Invoice InvoiceCollectionType
	 */
	public static final int INVOICECOLLECTIONTYPE_AD_Reference_ID=394;
	/** Dunning = D */
	public static final String INVOICECOLLECTIONTYPE_Dunning = "D";
	/** CollectionAgency = C */
	public static final String INVOICECOLLECTIONTYPE_CollectionAgency = "C";
	/** LegalProcedure = L */
	public static final String INVOICECOLLECTIONTYPE_LegalProcedure = "L";
	/** Uncollectable = U */
	public static final String INVOICECOLLECTIONTYPE_Uncollectable = "U";
	/** Set Collection Status.
		@param InvoiceCollectionType 
		Invoice Collection Status
	  */
	@Override
	public void setInvoiceCollectionType (java.lang.String InvoiceCollectionType)
	{

		set_Value (COLUMNNAME_InvoiceCollectionType, InvoiceCollectionType);
	}

	/** Get Collection Status.
		@return Invoice Collection Status
	  */
	@Override
	public java.lang.String getInvoiceCollectionType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceCollectionType);
	}

	/** Set Delivery stop.
		@param IsDeliveryStop Delivery stop	  */
	@Override
	public void setIsDeliveryStop (boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, Boolean.valueOf(IsDeliveryStop));
	}

	/** Get Delivery stop.
		@return Delivery stop	  */
	@Override
	public boolean isDeliveryStop () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeliveryStop);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kredit Stop.
		@param IsSetCreditStop 
		Set the business partner to credit stop
	  */
	@Override
	public void setIsSetCreditStop (boolean IsSetCreditStop)
	{
		set_Value (COLUMNNAME_IsSetCreditStop, Boolean.valueOf(IsSetCreditStop));
	}

	/** Get Kredit Stop.
		@return Set the business partner to credit stop
	  */
	@Override
	public boolean isSetCreditStop () 
	{
		Object oo = get_Value(COLUMNNAME_IsSetCreditStop);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Set Payment Term.
		@param IsSetPaymentTerm 
		Set the payment term of the Business Partner
	  */
	@Override
	public void setIsSetPaymentTerm (boolean IsSetPaymentTerm)
	{
		set_Value (COLUMNNAME_IsSetPaymentTerm, Boolean.valueOf(IsSetPaymentTerm));
	}

	/** Get Set Payment Term.
		@return Set the payment term of the Business Partner
	  */
	@Override
	public boolean isSetPaymentTerm () 
	{
		Object oo = get_Value(COLUMNNAME_IsSetPaymentTerm);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Show All Due.
		@param IsShowAllDue 
		Show/print all due invoices
	  */
	@Override
	public void setIsShowAllDue (boolean IsShowAllDue)
	{
		set_Value (COLUMNNAME_IsShowAllDue, Boolean.valueOf(IsShowAllDue));
	}

	/** Get Show All Due.
		@return Show/print all due invoices
	  */
	@Override
	public boolean isShowAllDue () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowAllDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Show Not Due.
		@param IsShowNotDue 
		Show/print all invoices which are not due (yet).
	  */
	@Override
	public void setIsShowNotDue (boolean IsShowNotDue)
	{
		set_Value (COLUMNNAME_IsShowNotDue, Boolean.valueOf(IsShowNotDue));
	}

	/** Get Show Not Due.
		@return Show/print all invoices which are not due (yet).
	  */
	@Override
	public boolean isShowNotDue () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowNotDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Statement.
		@param IsStatement 
		Dunning Level is a definition of a statement
	  */
	@Override
	public void setIsStatement (boolean IsStatement)
	{
		set_Value (COLUMNNAME_IsStatement, Boolean.valueOf(IsStatement));
	}

	/** Get Is Statement.
		@return Dunning Level is a definition of a statement
	  */
	@Override
	public boolean isStatement () 
	{
		Object oo = get_Value(COLUMNNAME_IsStatement);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Massenaustritt.
		@param IsWriteOff Massenaustritt	  */
	@Override
	public void setIsWriteOff (boolean IsWriteOff)
	{
		set_Value (COLUMNNAME_IsWriteOff, Boolean.valueOf(IsWriteOff));
	}

	/** Get Massenaustritt.
		@return Massenaustritt	  */
	@Override
	public boolean isWriteOff () 
	{
		Object oo = get_Value(COLUMNNAME_IsWriteOff);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Notiz.
		@param Note 
		Optional additional user defined information
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional additional user defined information
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Notiz Header.
		@param NoteHeader 
		Optional weitere Information für ein Dokument
	  */
	@Override
	public void setNoteHeader (java.lang.String NoteHeader)
	{
		set_Value (COLUMNNAME_NoteHeader, NoteHeader);
	}

	/** Get Notiz Header.
		@return Optional weitere Information für ein Dokument
	  */
	@Override
	public java.lang.String getNoteHeader () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_NoteHeader);
	}

	/** Set Drucktext.
		@param PrintName 
		The label text to be printed on a document or correspondence.
	  */
	@Override
	public void setPrintName (java.lang.String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Drucktext.
		@return The label text to be printed on a document or correspondence.
	  */
	@Override
	public java.lang.String getPrintName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintName);
	}
}