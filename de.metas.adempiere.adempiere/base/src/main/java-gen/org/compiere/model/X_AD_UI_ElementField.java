/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_UI_ElementField
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_UI_ElementField extends org.compiere.model.PO implements I_AD_UI_ElementField, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -427476289L;

    /** Standard Constructor */
    public X_AD_UI_ElementField (Properties ctx, int AD_UI_ElementField_ID, String trxName)
    {
      super (ctx, AD_UI_ElementField_ID, trxName);
      /** if (AD_UI_ElementField_ID == 0)
        {
			setAD_Field_ID (0);
			setAD_UI_ElementField_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo), 0) + 10 FROM AD_UI_ElementField WHERE AD_UI_Element_ID=@AD_UI_Element_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_UI_ElementField (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_UI_Element getAD_UI_Element() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_UI_Element_ID, org.compiere.model.I_AD_UI_Element.class);
	}

	@Override
	public void setAD_UI_Element(org.compiere.model.I_AD_UI_Element AD_UI_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_UI_Element_ID, org.compiere.model.I_AD_UI_Element.class, AD_UI_Element);
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

	/** Set UI Element field.
		@param AD_UI_ElementField_ID UI Element field	  */
	@Override
	public void setAD_UI_ElementField_ID (int AD_UI_ElementField_ID)
	{
		if (AD_UI_ElementField_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementField_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UI_ElementField_ID, Integer.valueOf(AD_UI_ElementField_ID));
	}

	/** Get UI Element field.
		@return UI Element field	  */
	@Override
	public int getAD_UI_ElementField_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_UI_ElementField_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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