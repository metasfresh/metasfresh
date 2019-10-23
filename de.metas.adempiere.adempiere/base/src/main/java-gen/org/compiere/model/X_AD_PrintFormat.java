/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrintFormat
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrintFormat extends org.compiere.model.PO implements I_AD_PrintFormat, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -155035237L;

    /** Standard Constructor */
    public X_AD_PrintFormat (Properties ctx, int AD_PrintFormat_ID, String trxName)
    {
      super (ctx, AD_PrintFormat_ID, trxName);
      /** if (AD_PrintFormat_ID == 0)
        {
			setAD_PrintColor_ID (0);
			setAD_PrintFont_ID (0);
			setAD_Printformat_Group (null); // None
			setAD_PrintFormat_ID (0); // 0
			setAD_PrintPaper_ID (0);
			setAD_Table_ID (0);
			setFooterMargin (0);
			setHeaderMargin (0);
			setIsDefault (false);
			setIsForm (false);
			setIsStandardHeaderFooter (true); // Y
			setIsTableBased (true); // Y
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_PrintFormat (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_PrintColor getAD_PrintColor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class);
	}

	@Override
	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class, AD_PrintColor);
	}

	/** Set Druck - Farbe.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	@Override
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Druck - Farbe.
		@return Color used for printing and display
	  */
	@Override
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PrintFont getAD_PrintFont() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFont_ID, org.compiere.model.I_AD_PrintFont.class);
	}

	@Override
	public void setAD_PrintFont(org.compiere.model.I_AD_PrintFont AD_PrintFont)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFont_ID, org.compiere.model.I_AD_PrintFont.class, AD_PrintFont);
	}

	/** Set Druck - Schrift.
		@param AD_PrintFont_ID 
		Maintain Print Font
	  */
	@Override
	public void setAD_PrintFont_ID (int AD_PrintFont_ID)
	{
		if (AD_PrintFont_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFont_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFont_ID, Integer.valueOf(AD_PrintFont_ID));
	}

	/** Get Druck - Schrift.
		@return Maintain Print Font
	  */
	@Override
	public int getAD_PrintFont_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFont_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * AD_Printformat_Group AD_Reference_ID=504470
	 * Reference name: AD_PrintFormat_Group
	 */
	public static final int AD_PRINTFORMAT_GROUP_AD_Reference_ID=504470;
	/** Lieferschein = LS */
	public static final String AD_PRINTFORMAT_GROUP_Lieferschein = "LS";
	/** Keine = None */
	public static final String AD_PRINTFORMAT_GROUP_Keine = "None";
	/** Rechnung (Lieferant) = PI */
	public static final String AD_PRINTFORMAT_GROUP_RechnungLieferant = "PI";
	/** Lieferantenauftrag = PO */
	public static final String AD_PRINTFORMAT_GROUP_Lieferantenauftrag = "PO";
	/** Rechnung (Kunde) = SI */
	public static final String AD_PRINTFORMAT_GROUP_RechnungKunde = "SI";
	/** Kundenauftrag = SO */
	public static final String AD_PRINTFORMAT_GROUP_Kundenauftrag = "SO";
	/** Wareneingang = WE */
	public static final String AD_PRINTFORMAT_GROUP_Wareneingang = "WE";
	/** Speditionsauftrag/Ladeliste = ST */
	public static final String AD_PRINTFORMAT_GROUP_SpeditionsauftragLadeliste = "ST";
	/** Set Formatgruppe.
		@param AD_Printformat_Group 
		Gruppe von Druckformaten, die die für den Druck des selben Beleges in Frage kommen
	  */
	@Override
	public void setAD_Printformat_Group (java.lang.String AD_Printformat_Group)
	{

		set_Value (COLUMNNAME_AD_Printformat_Group, AD_Printformat_Group);
	}

	/** Get Formatgruppe.
		@return Gruppe von Druckformaten, die die für den Druck des selben Beleges in Frage kommen
	  */
	@Override
	public java.lang.String getAD_Printformat_Group () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Printformat_Group);
	}

	/** Set Druck - Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	@Override
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Druck - Format.
		@return Data Print Format
	  */
	@Override
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PrintPaper getAD_PrintPaper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintPaper_ID, org.compiere.model.I_AD_PrintPaper.class);
	}

	@Override
	public void setAD_PrintPaper(org.compiere.model.I_AD_PrintPaper AD_PrintPaper)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintPaper_ID, org.compiere.model.I_AD_PrintPaper.class, AD_PrintPaper);
	}

	/** Set Druck - Papier.
		@param AD_PrintPaper_ID 
		Printer paper definition
	  */
	@Override
	public void setAD_PrintPaper_ID (int AD_PrintPaper_ID)
	{
		if (AD_PrintPaper_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintPaper_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintPaper_ID, Integer.valueOf(AD_PrintPaper_ID));
	}

	/** Get Druck - Papier.
		@return Printer paper definition
	  */
	@Override
	public int getAD_PrintPaper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintPaper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PrintTableFormat getAD_PrintTableFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintTableFormat_ID, org.compiere.model.I_AD_PrintTableFormat.class);
	}

	@Override
	public void setAD_PrintTableFormat(org.compiere.model.I_AD_PrintTableFormat AD_PrintTableFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintTableFormat_ID, org.compiere.model.I_AD_PrintTableFormat.class, AD_PrintTableFormat);
	}

	/** Set Druck - Tabellenformat.
		@param AD_PrintTableFormat_ID 
		Table Format in Reports
	  */
	@Override
	public void setAD_PrintTableFormat_ID (int AD_PrintTableFormat_ID)
	{
		if (AD_PrintTableFormat_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintTableFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintTableFormat_ID, Integer.valueOf(AD_PrintTableFormat_ID));
	}

	/** Get Druck - Tabellenformat.
		@return Table Format in Reports
	  */
	@Override
	public int getAD_PrintTableFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintTableFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_ReportView getAD_ReportView() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class);
	}

	@Override
	public void setAD_ReportView(org.compiere.model.I_AD_ReportView AD_ReportView)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReportView_ID, org.compiere.model.I_AD_ReportView.class, AD_ReportView);
	}

	/** Set Berichts-View.
		@param AD_ReportView_ID 
		View used to generate this report
	  */
	@Override
	public void setAD_ReportView_ID (int AD_ReportView_ID)
	{
		if (AD_ReportView_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_ReportView_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_ReportView_ID, Integer.valueOf(AD_ReportView_ID));
	}

	/** Get Berichts-View.
		@return View used to generate this report
	  */
	@Override
	public int getAD_ReportView_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReportView_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Args.
		@param Args Args	  */
	@Override
	public void setArgs (java.lang.String Args)
	{
		set_Value (COLUMNNAME_Args, Args);
	}

	/** Get Args.
		@return Args	  */
	@Override
	public java.lang.String getArgs () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Args);
	}

	/** Set Create Copy.
		@param CreateCopy Create Copy	  */
	@Override
	public void setCreateCopy (java.lang.String CreateCopy)
	{
		set_Value (COLUMNNAME_CreateCopy, CreateCopy);
	}

	/** Get Create Copy.
		@return Create Copy	  */
	@Override
	public java.lang.String getCreateCopy () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateCopy);
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

	/** Set Footer Margin.
		@param FooterMargin 
		Margin of the Footer in 1/72 of an inch
	  */
	@Override
	public void setFooterMargin (int FooterMargin)
	{
		set_Value (COLUMNNAME_FooterMargin, Integer.valueOf(FooterMargin));
	}

	/** Get Footer Margin.
		@return Margin of the Footer in 1/72 of an inch
	  */
	@Override
	public int getFooterMargin () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FooterMargin);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Header Margin.
		@param HeaderMargin 
		Margin of the Header in 1/72 of an inch
	  */
	@Override
	public void setHeaderMargin (int HeaderMargin)
	{
		set_Value (COLUMNNAME_HeaderMargin, Integer.valueOf(HeaderMargin));
	}

	/** Get Header Margin.
		@return Margin of the Header in 1/72 of an inch
	  */
	@Override
	public int getHeaderMargin () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HeaderMargin);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Fenster (nicht dynamisch).
		@param IsForm 
		If Selected, a Form is printed, if not selected a columnar List report
	  */
	@Override
	public void setIsForm (boolean IsForm)
	{
		set_Value (COLUMNNAME_IsForm, Boolean.valueOf(IsForm));
	}

	/** Get Fenster (nicht dynamisch).
		@return If Selected, a Form is printed, if not selected a columnar List report
	  */
	@Override
	public boolean isForm () 
	{
		Object oo = get_Value(COLUMNNAME_IsForm);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard Header/Footer.
		@param IsStandardHeaderFooter 
		The standard Header and Footer is used
	  */
	@Override
	public void setIsStandardHeaderFooter (boolean IsStandardHeaderFooter)
	{
		set_Value (COLUMNNAME_IsStandardHeaderFooter, Boolean.valueOf(IsStandardHeaderFooter));
	}

	/** Get Standard Header/Footer.
		@return The standard Header and Footer is used
	  */
	@Override
	public boolean isStandardHeaderFooter () 
	{
		Object oo = get_Value(COLUMNNAME_IsStandardHeaderFooter);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Table Based.
		@param IsTableBased 
		Table based List Reporting
	  */
	@Override
	public void setIsTableBased (boolean IsTableBased)
	{
		set_ValueNoCheck (COLUMNNAME_IsTableBased, Boolean.valueOf(IsTableBased));
	}

	/** Get Table Based.
		@return Table based List Reporting
	  */
	@Override
	public boolean isTableBased () 
	{
		Object oo = get_Value(COLUMNNAME_IsTableBased);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_AD_Process getJasperProcess() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_JasperProcess_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setJasperProcess(org.compiere.model.I_AD_Process JasperProcess)
	{
		set_ValueFromPO(COLUMNNAME_JasperProcess_ID, org.compiere.model.I_AD_Process.class, JasperProcess);
	}

	/** Set Jasper Process.
		@param JasperProcess_ID 
		The Jasper Process used by the printengine if any process defined
	  */
	@Override
	public void setJasperProcess_ID (int JasperProcess_ID)
	{
		if (JasperProcess_ID < 1) 
			set_Value (COLUMNNAME_JasperProcess_ID, null);
		else 
			set_Value (COLUMNNAME_JasperProcess_ID, Integer.valueOf(JasperProcess_ID));
	}

	/** Get Jasper Process.
		@return The Jasper Process used by the printengine if any process defined
	  */
	@Override
	public int getJasperProcess_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_JasperProcess_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Drucker.
		@param PrinterName 
		Name of the Printer
	  */
	@Override
	public void setPrinterName (java.lang.String PrinterName)
	{
		set_Value (COLUMNNAME_PrinterName, PrinterName);
	}

	/** Get Drucker.
		@return Name of the Printer
	  */
	@Override
	public java.lang.String getPrinterName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrinterName);
	}
}