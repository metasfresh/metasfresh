/** Generated Model - DO NOT CHANGE */
package de.metas.dimension.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DIM_Dimension_Type
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Type extends org.compiere.model.PO implements I_DIM_Dimension_Type, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1963103620L;

    /** Standard Constructor */
    public X_DIM_Dimension_Type (Properties ctx, int DIM_Dimension_Type_ID, String trxName)
    {
      super (ctx, DIM_Dimension_Type_ID, trxName);
      /** if (DIM_Dimension_Type_ID == 0)
        {
			setDIM_Dimension_Type_ID (0);
			setInternalName (null);
        } */
    }

    /** Load Constructor */
    public X_DIM_Dimension_Type (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Dimensionstyp.
		@param DIM_Dimension_Type_ID Dimensionstyp	  */
	@Override
	public void setDIM_Dimension_Type_ID (int DIM_Dimension_Type_ID)
	{
		if (DIM_Dimension_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Type_ID, Integer.valueOf(DIM_Dimension_Type_ID));
	}

	/** Get Dimensionstyp.
		@return Dimensionstyp	  */
	@Override
	public int getDIM_Dimension_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Interner Name.
		@param InternalName 
		Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Generally used to give records a name that can be safely referenced from code.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}