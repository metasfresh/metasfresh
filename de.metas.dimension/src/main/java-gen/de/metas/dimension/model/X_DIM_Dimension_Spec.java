/** Generated Model - DO NOT CHANGE */
package de.metas.dimension.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DIM_Dimension_Spec
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Spec extends org.compiere.model.PO implements I_DIM_Dimension_Spec, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2072828496L;

    /** Standard Constructor */
    public X_DIM_Dimension_Spec (Properties ctx, int DIM_Dimension_Spec_ID, String trxName)
    {
      super (ctx, DIM_Dimension_Spec_ID, trxName);
      /** if (DIM_Dimension_Spec_ID == 0)
        {
			setDIM_Dimension_Spec_ID (0);
			setDIM_Dimension_Type_ID (0);
			setIsIncludeEmpty (false); // N
			setIsIncludeOtherGroup (false); // N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_DIM_Dimension_Spec (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.dimension.model.I_DIM_Dimension_Type getDIM_Dimension_Type() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DIM_Dimension_Type_ID, de.metas.dimension.model.I_DIM_Dimension_Type.class);
	}

	@Override
	public void setDIM_Dimension_Type(de.metas.dimension.model.I_DIM_Dimension_Type DIM_Dimension_Type)
	{
		set_ValueFromPO(COLUMNNAME_DIM_Dimension_Type_ID, de.metas.dimension.model.I_DIM_Dimension_Type.class, DIM_Dimension_Type);
	}

	/** Set Dimensionstyp.
		@param DIM_Dimension_Type_ID Dimensionstyp	  */
	@Override
	public void setDIM_Dimension_Type_ID (int DIM_Dimension_Type_ID)
	{
		if (DIM_Dimension_Type_ID < 1) 
			set_Value (COLUMNNAME_DIM_Dimension_Type_ID, null);
		else 
			set_Value (COLUMNNAME_DIM_Dimension_Type_ID, Integer.valueOf(DIM_Dimension_Type_ID));
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

	/** Set DIM_Dimension_Type_InternalName.
		@param DIM_Dimension_Type_InternalName DIM_Dimension_Type_InternalName	  */
	@Override
	public void setDIM_Dimension_Type_InternalName (java.lang.String DIM_Dimension_Type_InternalName)
	{
		throw new IllegalArgumentException ("DIM_Dimension_Type_InternalName is virtual column");	}

	/** Get DIM_Dimension_Type_InternalName.
		@return DIM_Dimension_Type_InternalName	  */
	@Override
	public java.lang.String getDIM_Dimension_Type_InternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DIM_Dimension_Type_InternalName);
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

	/** Set inkl. "leer"-Eintrag.
		@param IsIncludeEmpty 
		Legt fest, ob die Dimension einen dezidierten "Leer" Eintrag enthalten soll
	  */
	@Override
	public void setIsIncludeEmpty (boolean IsIncludeEmpty)
	{
		set_Value (COLUMNNAME_IsIncludeEmpty, Boolean.valueOf(IsIncludeEmpty));
	}

	/** Get inkl. "leer"-Eintrag.
		@return Legt fest, ob die Dimension einen dezidierten "Leer" Eintrag enthalten soll
	  */
	@Override
	public boolean isIncludeEmpty () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeEmpty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set inkl. "sonstige"-Eintrag.
		@param IsIncludeOtherGroup 
		Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll
	  */
	@Override
	public void setIsIncludeOtherGroup (boolean IsIncludeOtherGroup)
	{
		set_Value (COLUMNNAME_IsIncludeOtherGroup, Boolean.valueOf(IsIncludeOtherGroup));
	}

	/** Get inkl. "sonstige"-Eintrag.
		@return Legt fest, ob die Dimension einen dezidierten "Sonstige" Eintrag enthalten soll
	  */
	@Override
	public boolean isIncludeOtherGroup () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeOtherGroup);
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