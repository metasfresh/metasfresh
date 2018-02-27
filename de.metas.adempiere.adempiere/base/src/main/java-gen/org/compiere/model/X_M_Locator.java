/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Locator
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Locator extends org.compiere.model.PO implements I_M_Locator, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 860324491L;

    /** Standard Constructor */
    public X_M_Locator (Properties ctx, int M_Locator_ID, String trxName)
    {
      super (ctx, M_Locator_ID, trxName);
      /** if (M_Locator_ID == 0)
        {
			setIsDefault (false);
			setM_Locator_ID (0);
			setM_Warehouse_ID (0);
			setPriorityNo (0); // 50
			setValue (null);
			setX (null);
			setX1 (null);
			setY (null);
			setZ (null);
        } */
    }

    /** Load Constructor */
    public X_M_Locator (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Lagerort.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Warehouse Locator
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Storage Warehouse and Service Point
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Relative Priorität.
		@param PriorityNo 
		Where inventory should be picked from first
	  */
	@Override
	public void setPriorityNo (int PriorityNo)
	{
		set_Value (COLUMNNAME_PriorityNo, Integer.valueOf(PriorityNo));
	}

	/** Get Relative Priorität.
		@return Where inventory should be picked from first
	  */
	@Override
	public int getPriorityNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PriorityNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Gang.
		@param X 
		X-Dimension, z.B. Gang
	  */
	@Override
	public void setX (java.lang.String X)
	{
		set_Value (COLUMNNAME_X, X);
	}

	/** Get Gang.
		@return X-Dimension, z.B. Gang
	  */
	@Override
	public java.lang.String getX () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X);
	}

	/** Set Regal.
		@param X1 Regal	  */
	@Override
	public void setX1 (java.lang.String X1)
	{
		set_Value (COLUMNNAME_X1, X1);
	}

	/** Get Regal.
		@return Regal	  */
	@Override
	public java.lang.String getX1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X1);
	}

	/** Set Fach.
		@param Y 
		Y-Dimension, z.B. Fach
	  */
	@Override
	public void setY (java.lang.String Y)
	{
		set_Value (COLUMNNAME_Y, Y);
	}

	/** Get Fach.
		@return Y-Dimension, z.B. Fach
	  */
	@Override
	public java.lang.String getY () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Y);
	}

	/** Set Ebene.
		@param Z 
		Z-Dimension, z.B. Ebene
	  */
	@Override
	public void setZ (java.lang.String Z)
	{
		set_Value (COLUMNNAME_Z, Z);
	}

	/** Get Ebene.
		@return Z-Dimension, z.B. Ebene
	  */
	@Override
	public java.lang.String getZ () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Z);
	}
}