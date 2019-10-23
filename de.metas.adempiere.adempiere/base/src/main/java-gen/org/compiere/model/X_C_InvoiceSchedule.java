/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_InvoiceSchedule
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_InvoiceSchedule extends org.compiere.model.PO implements I_C_InvoiceSchedule, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1981042217L;

    /** Standard Constructor */
    public X_C_InvoiceSchedule (Properties ctx, int C_InvoiceSchedule_ID, String trxName)
    {
      super (ctx, C_InvoiceSchedule_ID, trxName);
      /** if (C_InvoiceSchedule_ID == 0)
        {
			setAmt (BigDecimal.ZERO);
			setC_InvoiceSchedule_ID (0);
			setInvoiceDay (0); // 1
			setInvoiceDistance (0); // 1
			setInvoiceFrequency (null);
			setInvoiceWeekDay (null);
			setIsAmount (false);
			setIsDefault (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_InvoiceSchedule (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amt 
		Amount
	  */
	@Override
	public void setAmt (java.math.BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Betrag.
		@return Amount
	  */
	@Override
	public java.math.BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Terminplan Rechnung.
		@param C_InvoiceSchedule_ID 
		Schedule for generating Invoices
	  */
	@Override
	public void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID)
	{
		if (C_InvoiceSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceSchedule_ID, Integer.valueOf(C_InvoiceSchedule_ID));
	}

	/** Get Terminplan Rechnung.
		@return Schedule for generating Invoices
	  */
	@Override
	public int getC_InvoiceSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceSchedule_ID);
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

	/** Set Invoice on even weeks.
		@param EvenInvoiceWeek 
		Send invoices on even weeks
	  */
	@Override
	public void setEvenInvoiceWeek (boolean EvenInvoiceWeek)
	{
		set_Value (COLUMNNAME_EvenInvoiceWeek, Boolean.valueOf(EvenInvoiceWeek));
	}

	/** Get Invoice on even weeks.
		@return Send invoices on even weeks
	  */
	@Override
	public boolean isEvenInvoiceWeek () 
	{
		Object oo = get_Value(COLUMNNAME_EvenInvoiceWeek);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Rechnungstag.
		@param InvoiceDay 
		Day of Invoice Generation
	  */
	@Override
	public void setInvoiceDay (int InvoiceDay)
	{
		set_Value (COLUMNNAME_InvoiceDay, Integer.valueOf(InvoiceDay));
	}

	/** Get Rechnungstag.
		@return Day of Invoice Generation
	  */
	@Override
	public int getInvoiceDay () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceDay);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Letzter Tag Lieferungen.
		@param InvoiceDayCutoff 
		Last day for including shipments
	  */
	@Override
	public void setInvoiceDayCutoff (int InvoiceDayCutoff)
	{
		set_Value (COLUMNNAME_InvoiceDayCutoff, Integer.valueOf(InvoiceDayCutoff));
	}

	/** Get Letzter Tag Lieferungen.
		@return Last day for including shipments
	  */
	@Override
	public int getInvoiceDayCutoff () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceDayCutoff);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anz. Einheiten zwischen zwei Rechnungsstellungen.
		@param InvoiceDistance Anz. Einheiten zwischen zwei Rechnungsstellungen	  */
	@Override
	public void setInvoiceDistance (int InvoiceDistance)
	{
		set_Value (COLUMNNAME_InvoiceDistance, Integer.valueOf(InvoiceDistance));
	}

	/** Get Anz. Einheiten zwischen zwei Rechnungsstellungen.
		@return Anz. Einheiten zwischen zwei Rechnungsstellungen	  */
	@Override
	public int getInvoiceDistance () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_InvoiceDistance);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * InvoiceFrequency AD_Reference_ID=168
	 * Reference name: C_InvoiceSchedule InvoiceFrequency
	 */
	public static final int INVOICEFREQUENCY_AD_Reference_ID=168;
	/** Daily = D */
	public static final String INVOICEFREQUENCY_Daily = "D";
	/** Weekly = W */
	public static final String INVOICEFREQUENCY_Weekly = "W";
	/** Monthly = M */
	public static final String INVOICEFREQUENCY_Monthly = "M";
	/** TwiceMonthly = T */
	public static final String INVOICEFREQUENCY_TwiceMonthly = "T";
	/** Set Rechnungshäufigkeit.
		@param InvoiceFrequency 
		How often invoices will be generated
	  */
	@Override
	public void setInvoiceFrequency (java.lang.String InvoiceFrequency)
	{

		set_Value (COLUMNNAME_InvoiceFrequency, InvoiceFrequency);
	}

	/** Get Rechnungshäufigkeit.
		@return How often invoices will be generated
	  */
	@Override
	public java.lang.String getInvoiceFrequency () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceFrequency);
	}

	/** 
	 * InvoiceWeekDay AD_Reference_ID=167
	 * Reference name: Weekdays
	 */
	public static final int INVOICEWEEKDAY_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String INVOICEWEEKDAY_Sunday = "7";
	/** Monday = 1 */
	public static final String INVOICEWEEKDAY_Monday = "1";
	/** Tuesday = 2 */
	public static final String INVOICEWEEKDAY_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String INVOICEWEEKDAY_Wednesday = "3";
	/** Thursday = 4 */
	public static final String INVOICEWEEKDAY_Thursday = "4";
	/** Friday = 5 */
	public static final String INVOICEWEEKDAY_Friday = "5";
	/** Saturday = 6 */
	public static final String INVOICEWEEKDAY_Saturday = "6";
	/** Set Wochentag.
		@param InvoiceWeekDay 
		Day to generate invoices
	  */
	@Override
	public void setInvoiceWeekDay (java.lang.String InvoiceWeekDay)
	{

		set_Value (COLUMNNAME_InvoiceWeekDay, InvoiceWeekDay);
	}

	/** Get Wochentag.
		@return Day to generate invoices
	  */
	@Override
	public java.lang.String getInvoiceWeekDay () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceWeekDay);
	}

	/** 
	 * InvoiceWeekDayCutoff AD_Reference_ID=167
	 * Reference name: Weekdays
	 */
	public static final int INVOICEWEEKDAYCUTOFF_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String INVOICEWEEKDAYCUTOFF_Sunday = "7";
	/** Monday = 1 */
	public static final String INVOICEWEEKDAYCUTOFF_Monday = "1";
	/** Tuesday = 2 */
	public static final String INVOICEWEEKDAYCUTOFF_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String INVOICEWEEKDAYCUTOFF_Wednesday = "3";
	/** Thursday = 4 */
	public static final String INVOICEWEEKDAYCUTOFF_Thursday = "4";
	/** Friday = 5 */
	public static final String INVOICEWEEKDAYCUTOFF_Friday = "5";
	/** Saturday = 6 */
	public static final String INVOICEWEEKDAYCUTOFF_Saturday = "6";
	/** Set Letzter Wochentag Lieferungen.
		@param InvoiceWeekDayCutoff 
		Last day in the week for shipments to be included
	  */
	@Override
	public void setInvoiceWeekDayCutoff (java.lang.String InvoiceWeekDayCutoff)
	{

		set_Value (COLUMNNAME_InvoiceWeekDayCutoff, InvoiceWeekDayCutoff);
	}

	/** Get Letzter Wochentag Lieferungen.
		@return Last day in the week for shipments to be included
	  */
	@Override
	public java.lang.String getInvoiceWeekDayCutoff () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceWeekDayCutoff);
	}

	/** Set Betragsgrenze.
		@param IsAmount 
		Send invoices only if the amount exceeds the limit
IMPORTANT: currently not used;
	  */
	@Override
	public void setIsAmount (boolean IsAmount)
	{
		set_Value (COLUMNNAME_IsAmount, Boolean.valueOf(IsAmount));
	}

	/** Get Betragsgrenze.
		@return Send invoices only if the amount exceeds the limit
IMPORTANT: currently not used;
	  */
	@Override
	public boolean isAmount () 
	{
		Object oo = get_Value(COLUMNNAME_IsAmount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
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
}