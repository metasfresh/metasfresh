/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_DataEntry_Record
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_DataEntry_Record extends org.compiere.model.PO implements I_I_DataEntry_Record, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -31639530L;

    /** Standard Constructor */
    public X_I_DataEntry_Record (Properties ctx, int I_DataEntry_Record_ID, String trxName)
    {
      super (ctx, I_DataEntry_Record_ID, trxName);
      /** if (I_DataEntry_Record_ID == 0)
        {
			setI_DataEntry_Record_ID (0);
			setI_IsImported (null); // N
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_DataEntry_Record (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_Window getAD_Window() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	/** Set Fenster.
		@param AD_Window_ID 
		Eingabe- oder Anzeige-Fenster
	  */
	@Override
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Fenster.
		@return Eingabe- oder Anzeige-Fenster
	  */
	@Override
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Field getDataEntry_Field() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Field_ID, de.metas.dataentry.model.I_DataEntry_Field.class);
	}

	@Override
	public void setDataEntry_Field(de.metas.dataentry.model.I_DataEntry_Field DataEntry_Field)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Field_ID, de.metas.dataentry.model.I_DataEntry_Field.class, DataEntry_Field);
	}

	/** Set Dateneingabefeld.
		@param DataEntry_Field_ID Dateneingabefeld	  */
	@Override
	public void setDataEntry_Field_ID (int DataEntry_Field_ID)
	{
		if (DataEntry_Field_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_Field_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_Field_ID, Integer.valueOf(DataEntry_Field_ID));
	}

	/** Get Dateneingabefeld.
		@return Dateneingabefeld	  */
	@Override
	public int getDataEntry_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Line getDataEntry_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Line_ID, de.metas.dataentry.model.I_DataEntry_Line.class);
	}

	@Override
	public void setDataEntry_Line(de.metas.dataentry.model.I_DataEntry_Line DataEntry_Line)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Line_ID, de.metas.dataentry.model.I_DataEntry_Line.class, DataEntry_Line);
	}

	/** Set Zeile.
		@param DataEntry_Line_ID Zeile	  */
	@Override
	public void setDataEntry_Line_ID (int DataEntry_Line_ID)
	{
		if (DataEntry_Line_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_Line_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_Line_ID, Integer.valueOf(DataEntry_Line_ID));
	}

	/** Get Zeile.
		@return Zeile	  */
	@Override
	public int getDataEntry_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeile Name.
		@param DataEntry_Line_Name Zeile Name	  */
	@Override
	public void setDataEntry_Line_Name (java.lang.String DataEntry_Line_Name)
	{
		set_Value (COLUMNNAME_DataEntry_Line_Name, DataEntry_Line_Name);
	}

	/** Get Zeile Name.
		@return Zeile Name	  */
	@Override
	public java.lang.String getDataEntry_Line_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataEntry_Line_Name);
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Record getDataEntry_Record() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Record_ID, de.metas.dataentry.model.I_DataEntry_Record.class);
	}

	@Override
	public void setDataEntry_Record(de.metas.dataentry.model.I_DataEntry_Record DataEntry_Record)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Record_ID, de.metas.dataentry.model.I_DataEntry_Record.class, DataEntry_Record);
	}

	/** Set DataEntry_Record.
		@param DataEntry_Record_ID DataEntry_Record	  */
	@Override
	public void setDataEntry_Record_ID (int DataEntry_Record_ID)
	{
		if (DataEntry_Record_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_Record_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_Record_ID, Integer.valueOf(DataEntry_Record_ID));
	}

	/** Get DataEntry_Record.
		@return DataEntry_Record	  */
	@Override
	public int getDataEntry_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Section getDataEntry_Section() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Section_ID, de.metas.dataentry.model.I_DataEntry_Section.class);
	}

	@Override
	public void setDataEntry_Section(de.metas.dataentry.model.I_DataEntry_Section DataEntry_Section)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Section_ID, de.metas.dataentry.model.I_DataEntry_Section.class, DataEntry_Section);
	}

	/** Set Sektion.
		@param DataEntry_Section_ID Sektion	  */
	@Override
	public void setDataEntry_Section_ID (int DataEntry_Section_ID)
	{
		if (DataEntry_Section_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_Section_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_Section_ID, Integer.valueOf(DataEntry_Section_ID));
	}

	/** Get Sektion.
		@return Sektion	  */
	@Override
	public int getDataEntry_Section_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Section_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sektion Name.
		@param DataEntry_Section_Name Sektion Name	  */
	@Override
	public void setDataEntry_Section_Name (java.lang.String DataEntry_Section_Name)
	{
		set_Value (COLUMNNAME_DataEntry_Section_Name, DataEntry_Section_Name);
	}

	/** Get Sektion Name.
		@return Sektion Name	  */
	@Override
	public java.lang.String getDataEntry_Section_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataEntry_Section_Name);
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class);
	}

	@Override
	public void setDataEntry_SubTab(de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class, DataEntry_SubTab);
	}

	/** Set Unterregister.
		@param DataEntry_SubTab_ID Unterregister	  */
	@Override
	public void setDataEntry_SubTab_ID (int DataEntry_SubTab_ID)
	{
		if (DataEntry_SubTab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_SubTab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_SubTab_ID, Integer.valueOf(DataEntry_SubTab_ID));
	}

	/** Get Unterregister.
		@return Unterregister	  */
	@Override
	public int getDataEntry_SubTab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_SubTab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Unterregister Name.
		@param DataEntry_SubTab_Name Unterregister Name	  */
	@Override
	public void setDataEntry_SubTab_Name (java.lang.String DataEntry_SubTab_Name)
	{
		set_Value (COLUMNNAME_DataEntry_SubTab_Name, DataEntry_SubTab_Name);
	}

	/** Get Unterregister Name.
		@return Unterregister Name	  */
	@Override
	public java.lang.String getDataEntry_SubTab_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataEntry_SubTab_Name);
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Tab getDataEntry_Tab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Tab_ID, de.metas.dataentry.model.I_DataEntry_Tab.class);
	}

	@Override
	public void setDataEntry_Tab(de.metas.dataentry.model.I_DataEntry_Tab DataEntry_Tab)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Tab_ID, de.metas.dataentry.model.I_DataEntry_Tab.class, DataEntry_Tab);
	}

	/** Set Eingaberegister.
		@param DataEntry_Tab_ID Eingaberegister	  */
	@Override
	public void setDataEntry_Tab_ID (int DataEntry_Tab_ID)
	{
		if (DataEntry_Tab_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_Tab_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_Tab_ID, Integer.valueOf(DataEntry_Tab_ID));
	}

	/** Get Eingaberegister.
		@return Eingaberegister	  */
	@Override
	public int getDataEntry_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Eingaberegister Name.
		@param DataEntry_Tab_Name Eingaberegister Name	  */
	@Override
	public void setDataEntry_Tab_Name (java.lang.String DataEntry_Tab_Name)
	{
		set_Value (COLUMNNAME_DataEntry_Tab_Name, DataEntry_Tab_Name);
	}

	/** Get Eingaberegister Name.
		@return Eingaberegister Name	  */
	@Override
	public java.lang.String getDataEntry_Tab_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataEntry_Tab_Name);
	}

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	/** Set Field name.
		@param FieldName Field name	  */
	@Override
	public void setFieldName (java.lang.String FieldName)
	{
		set_Value (COLUMNNAME_FieldName, FieldName);
	}

	/** Get Field name.
		@return Field name	  */
	@Override
	public java.lang.String getFieldName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FieldName);
	}

	/** Set Field Value.
		@param FieldValue Field Value	  */
	@Override
	public void setFieldValue (java.lang.String FieldValue)
	{
		set_Value (COLUMNNAME_FieldValue, FieldValue);
	}

	/** Get Field Value.
		@return Field Value	  */
	@Override
	public java.lang.String getFieldValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FieldValue);
	}

	/** Set Import - Erweiterte Dateneingabe.
		@param I_DataEntry_Record_ID Import - Erweiterte Dateneingabe	  */
	@Override
	public void setI_DataEntry_Record_ID (int I_DataEntry_Record_ID)
	{
		if (I_DataEntry_Record_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_DataEntry_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_DataEntry_Record_ID, Integer.valueOf(I_DataEntry_Record_ID));
	}

	/** Get Import - Erweiterte Dateneingabe.
		@return Import - Erweiterte Dateneingabe	  */
	@Override
	public int getI_DataEntry_Record_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_DataEntry_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Window Internal Name.
		@param WindowInternalName Window Internal Name	  */
	@Override
	public void setWindowInternalName (java.lang.String WindowInternalName)
	{
		set_Value (COLUMNNAME_WindowInternalName, WindowInternalName);
	}

	/** Get Window Internal Name.
		@return Window Internal Name	  */
	@Override
	public java.lang.String getWindowInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WindowInternalName);
	}
}