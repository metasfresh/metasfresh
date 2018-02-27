/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_DosageForm
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DosageForm extends org.compiere.model.PO implements I_M_DosageForm, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1350345018L;

    /** Standard Constructor */
    public X_M_DosageForm (Properties ctx, int M_DosageForm_ID, String trxName)
    {
      super (ctx, M_DosageForm_ID, trxName);
      /** if (M_DosageForm_ID == 0)
        {
			setM_DosageForm_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_DosageForm (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Dosage Form.
		@param M_DosageForm_ID Dosage Form	  */
	@Override
	public void setM_DosageForm_ID (int M_DosageForm_ID)
	{
		if (M_DosageForm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DosageForm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DosageForm_ID, Integer.valueOf(M_DosageForm_ID));
	}

	/** Get Dosage Form.
		@return Dosage Form	  */
	@Override
	public int getM_DosageForm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DosageForm_ID);
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
}