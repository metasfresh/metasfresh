/** Generated Model - DO NOT CHANGE */
package de.metas.dimension.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DIM_Dimension_Spec_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Spec_Assignment extends org.compiere.model.PO implements I_DIM_Dimension_Spec_Assignment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 813927437L;

    /** Standard Constructor */
    public X_DIM_Dimension_Spec_Assignment (Properties ctx, int DIM_Dimension_Spec_Assignment_ID, String trxName)
    {
      super (ctx, DIM_Dimension_Spec_Assignment_ID, trxName);
      /** if (DIM_Dimension_Spec_Assignment_ID == 0)
        {
			setAD_Column_ID (0);
			setDIM_Dimension_Spec_Assignment_ID (0);
			setDIM_Dimension_Spec_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DIM_Dimension_Spec_Assignment (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Spalte in der Tabelle
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Spalte in der Tabelle
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dimensionsspezifikationszuordnung.
		@param DIM_Dimension_Spec_Assignment_ID Dimensionsspezifikationszuordnung	  */
	@Override
	public void setDIM_Dimension_Spec_Assignment_ID (int DIM_Dimension_Spec_Assignment_ID)
	{
		if (DIM_Dimension_Spec_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_Assignment_ID, Integer.valueOf(DIM_Dimension_Spec_Assignment_ID));
	}

	/** Get Dimensionsspezifikationszuordnung.
		@return Dimensionsspezifikationszuordnung	  */
	@Override
	public int getDIM_Dimension_Spec_Assignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_Assignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dimension.model.I_DIM_Dimension_Spec getDIM_Dimension_Spec()
	{
		return get_ValueAsPO(COLUMNNAME_DIM_Dimension_Spec_ID, de.metas.dimension.model.I_DIM_Dimension_Spec.class);
	}

	@Override
	public void setDIM_Dimension_Spec(de.metas.dimension.model.I_DIM_Dimension_Spec DIM_Dimension_Spec)
	{
		set_ValueFromPO(COLUMNNAME_DIM_Dimension_Spec_ID, de.metas.dimension.model.I_DIM_Dimension_Spec.class, DIM_Dimension_Spec);
	}

	/** Set Dimensionsspezifikation.
		@param DIM_Dimension_Spec_ID Dimensionsspezifikation	  */
	@Override
	public void setDIM_Dimension_Spec_ID (int DIM_Dimension_Spec_ID)
	{
		if (DIM_Dimension_Spec_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_ID, Integer.valueOf(DIM_Dimension_Spec_ID));
	}

	/** Get Dimensionsspezifikation.
		@return Dimensionsspezifikation	  */
	@Override
	public int getDIM_Dimension_Spec_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}