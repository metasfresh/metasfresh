/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_KPI_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_KPI_Field extends org.compiere.model.PO implements I_WEBUI_KPI_Field, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 636163434L;

    /** Standard Constructor */
    public X_WEBUI_KPI_Field (Properties ctx, int WEBUI_KPI_Field_ID, String trxName)
    {
      super (ctx, WEBUI_KPI_Field_ID, trxName);
      /** if (WEBUI_KPI_Field_ID == 0)
        {
			setAD_Element_ID (0);
			setAD_Reference_ID (0);
			setES_FieldPath (null);
			setIsGroupBy (false); // N
			setName (null);
			setWEBUI_KPI_Field_ID (0);
			setWEBUI_KPI_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_KPI_Field (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Element getAD_Element() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	/** Set System-Element.
		@param AD_Element_ID 
		Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System-Element.
		@return Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	  */
	@Override
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	/** Set Referenz.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return Systemreferenz und Validierung
	  */
	@Override
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Color.
		@param Color Color	  */
	@Override
	public void setColor (java.lang.String Color)
	{
		set_Value (COLUMNNAME_Color, Color);
	}

	/** Get Color.
		@return Color	  */
	@Override
	public java.lang.String getColor () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Color);
	}

	/** Set Elasticsearch field path.
		@param ES_FieldPath Elasticsearch field path	  */
	@Override
	public void setES_FieldPath (java.lang.String ES_FieldPath)
	{
		set_Value (COLUMNNAME_ES_FieldPath, ES_FieldPath);
	}

	/** Get Elasticsearch field path.
		@return Elasticsearch field path	  */
	@Override
	public java.lang.String getES_FieldPath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ES_FieldPath);
	}

	/** Set Gruppieren.
		@param IsGroupBy 
		After a group change, totals, etc. are printed
	  */
	@Override
	public void setIsGroupBy (boolean IsGroupBy)
	{
		set_Value (COLUMNNAME_IsGroupBy, Boolean.valueOf(IsGroupBy));
	}

	/** Get Gruppieren.
		@return After a group change, totals, etc. are printed
	  */
	@Override
	public boolean isGroupBy () 
	{
		Object oo = get_Value(COLUMNNAME_IsGroupBy);
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

	/** Set Offset name.
		@param OffsetName Offset name	  */
	@Override
	public void setOffsetName (java.lang.String OffsetName)
	{
		set_Value (COLUMNNAME_OffsetName, OffsetName);
	}

	/** Get Offset name.
		@return Offset name	  */
	@Override
	public java.lang.String getOffsetName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OffsetName);
	}

	/** Set Symbol.
		@param UOMSymbol 
		Symbol für die Maßeinheit
	  */
	@Override
	public void setUOMSymbol (java.lang.String UOMSymbol)
	{
		set_Value (COLUMNNAME_UOMSymbol, UOMSymbol);
	}

	/** Get Symbol.
		@return Symbol für die Maßeinheit
	  */
	@Override
	public java.lang.String getUOMSymbol () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UOMSymbol);
	}

	/** Set WEBUI_KPI_Field.
		@param WEBUI_KPI_Field_ID WEBUI_KPI_Field	  */
	@Override
	public void setWEBUI_KPI_Field_ID (int WEBUI_KPI_Field_ID)
	{
		if (WEBUI_KPI_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_Field_ID, Integer.valueOf(WEBUI_KPI_Field_ID));
	}

	/** Get WEBUI_KPI_Field.
		@return WEBUI_KPI_Field	  */
	@Override
	public int getWEBUI_KPI_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_KPI_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_KPI getWEBUI_KPI() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_KPI_ID, de.metas.ui.web.base.model.I_WEBUI_KPI.class);
	}

	@Override
	public void setWEBUI_KPI(de.metas.ui.web.base.model.I_WEBUI_KPI WEBUI_KPI)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_KPI_ID, de.metas.ui.web.base.model.I_WEBUI_KPI.class, WEBUI_KPI);
	}

	/** Set KPI.
		@param WEBUI_KPI_ID KPI	  */
	@Override
	public void setWEBUI_KPI_ID (int WEBUI_KPI_ID)
	{
		if (WEBUI_KPI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_ID, Integer.valueOf(WEBUI_KPI_ID));
	}

	/** Get KPI.
		@return KPI	  */
	@Override
	public int getWEBUI_KPI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_KPI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}