/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_HU_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Assignment extends org.compiere.model.PO implements I_M_HU_Assignment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1338709188L;

    /** Standard Constructor */
    public X_M_HU_Assignment (Properties ctx, int M_HU_Assignment_ID, String trxName)
    {
      super (ctx, M_HU_Assignment_ID, trxName);
      /** if (M_HU_Assignment_ID == 0)
        {
			setAD_Table_ID (0);
			setIsTransferPackingMaterials (true);
// Y
			setM_HU_Assignment_ID (0);
			setM_HU_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Assignment (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set Transfer Packing Materials.
		@param IsTransferPackingMaterials 
		Shall we transfer packing materials along with the HU
	  */
	@Override
	public void setIsTransferPackingMaterials (boolean IsTransferPackingMaterials)
	{
		set_Value (COLUMNNAME_IsTransferPackingMaterials, Boolean.valueOf(IsTransferPackingMaterials));
	}

	/** Get Transfer Packing Materials.
		@return Shall we transfer packing materials along with the HU
	  */
	@Override
	public boolean isTransferPackingMaterials () 
	{
		Object oo = get_Value(COLUMNNAME_IsTransferPackingMaterials);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set M_HU_Assignment.
		@param M_HU_Assignment_ID M_HU_Assignment	  */
	@Override
	public void setM_HU_Assignment_ID (int M_HU_Assignment_ID)
	{
		if (M_HU_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Assignment_ID, Integer.valueOf(M_HU_Assignment_ID));
	}

	/** Get M_HU_Assignment.
		@return M_HU_Assignment	  */
	@Override
	public int getM_HU_Assignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Assignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_LU_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_LU_HU(de.metas.handlingunits.model.I_M_HU M_LU_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_LU_HU);
	}

	/** Set Handling Unit (LU).
		@param M_LU_HU_ID 
		Handling Unit (Loading Unit)
	  */
	@Override
	public void setM_LU_HU_ID (int M_LU_HU_ID)
	{
		if (M_LU_HU_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_ID, Integer.valueOf(M_LU_HU_ID));
	}

	/** Get Handling Unit (LU).
		@return Handling Unit (Loading Unit)
	  */
	@Override
	public int getM_LU_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LU_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_TU_HU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_TU_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_TU_HU(de.metas.handlingunits.model.I_M_HU M_TU_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_TU_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_TU_HU);
	}

	/** Set Handling Unit (TU).
		@param M_TU_HU_ID 
		Handling Unit of type Tranding Unit
	  */
	@Override
	public void setM_TU_HU_ID (int M_TU_HU_ID)
	{
		if (M_TU_HU_ID < 1) 
			set_Value (COLUMNNAME_M_TU_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_TU_HU_ID, Integer.valueOf(M_TU_HU_ID));
	}

	/** Get Handling Unit (TU).
		@return Handling Unit of type Tranding Unit
	  */
	@Override
	public int getM_TU_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TU_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkte.
		@param Products Produkte	  */
	@Override
	public void setProducts (java.lang.String Products)
	{
		throw new IllegalArgumentException ("Products is virtual column");	}

	/** Get Produkte.
		@return Produkte	  */
	@Override
	public java.lang.String getProducts () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Products);
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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

	@Override
	public de.metas.handlingunits.model.I_M_HU getVHU() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setVHU(de.metas.handlingunits.model.I_M_HU VHU)
	{
		set_ValueFromPO(COLUMNNAME_VHU_ID, de.metas.handlingunits.model.I_M_HU.class, VHU);
	}

	/** Set Virtual Handling Unit (VHU).
		@param VHU_ID Virtual Handling Unit (VHU)	  */
	@Override
	public void setVHU_ID (int VHU_ID)
	{
		if (VHU_ID < 1) 
			set_Value (COLUMNNAME_VHU_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_ID, Integer.valueOf(VHU_ID));
	}

	/** Get Virtual Handling Unit (VHU).
		@return Virtual Handling Unit (VHU)	  */
	@Override
	public int getVHU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VHU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}