/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UI_Element
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UI_Element extends org.compiere.model.PO implements I_AD_UI_Element, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1956074911L;

    /** Standard Constructor */
    public X_AD_UI_Element (Properties ctx, int AD_UI_Element_ID, String trxName)
    {
      super (ctx, AD_UI_Element_ID, trxName);
      /** if (AD_UI_Element_ID == 0)
        {
			setAD_UI_Element_ID (0);
			setIsAdvancedField (false);
// N
			setIsBasicField (true);
// Y
			setName (null);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 FROM AD_UI_Element WHERE AD_UI_ElementGroup_ID=@AD_UI_ElementGroup_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_UI_Element (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Field getAD_Field() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class);
	}

	@Override
	public void setAD_Field(org.compiere.model.I_AD_Field AD_Field)
	{
		set_ValueFromPO(COLUMNNAME_AD_Field_ID, org.compiere.model.I_AD_Field.class, AD_Field);
	}

	/** Set Feld.
		@param AD_Field_ID 
		Ein Feld einer Datenbanktabelle
	  */
	@Override
	public void setAD_Field_ID (int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_Value (COLUMNNAME_AD_Field_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Field_ID, Integer.valueOf(AD_Field_ID));
	}

	/** Get Feld.
		@return Ein Feld einer Datenbanktabelle
	  */
	@Override
	public int getAD_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UI Element.
		@param AD_UI_Element_ID UI Element	  */
	@Override
	public void setAD_UI_Element_ID (int AD_UI_Element_ID)
	{
		if (AD_UI_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_Element_ID, Integer.valueOf(AD_UI_Element_ID));
	}

	/** Get UI Element.
		@return UI Element	  */
	@Override
	public int getAD_UI_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UI_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_UI_ElementGroup getAD_UI_ElementGroup() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_UI_ElementGroup_ID, org.compiere.model.I_AD_UI_ElementGroup.class);
	}

	@Override
	public void setAD_UI_ElementGroup(org.compiere.model.I_AD_UI_ElementGroup AD_UI_ElementGroup)
	{
		set_ValueFromPO(COLUMNNAME_AD_UI_ElementGroup_ID, org.compiere.model.I_AD_UI_ElementGroup.class, AD_UI_ElementGroup);
	}

	/** Set UI Element Group.
		@param AD_UI_ElementGroup_ID UI Element Group	  */
	@Override
	public void setAD_UI_ElementGroup_ID (int AD_UI_ElementGroup_ID)
	{
		if (AD_UI_ElementGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementGroup_ID, Integer.valueOf(AD_UI_ElementGroup_ID));
	}

	/** Get UI Element Group.
		@return UI Element Group	  */
	@Override
	public int getAD_UI_ElementGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UI_ElementGroup_ID);
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

	/** Set Kommentar/Hilfe.
		@param Help 
		Comment or Hint
	  */
	@Override
	public void setHelp (java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Kommentar/Hilfe.
		@return Comment or Hint
	  */
	@Override
	public java.lang.String getHelp () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Help);
	}

	/** Set Advanced field.
		@param IsAdvancedField Advanced field	  */
	@Override
	public void setIsAdvancedField (boolean IsAdvancedField)
	{
		set_Value (COLUMNNAME_IsAdvancedField, Boolean.valueOf(IsAdvancedField));
	}

	/** Get Advanced field.
		@return Advanced field	  */
	@Override
	public boolean isAdvancedField () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdvancedField);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Basic field.
		@param IsBasicField Basic field	  */
	@Override
	public void setIsBasicField (boolean IsBasicField)
	{
		set_Value (COLUMNNAME_IsBasicField, Boolean.valueOf(IsBasicField));
	}

	/** Get Basic field.
		@return Basic field	  */
	@Override
	public boolean isBasicField () 
	{
		Object oo = get_Value(COLUMNNAME_IsBasicField);
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}