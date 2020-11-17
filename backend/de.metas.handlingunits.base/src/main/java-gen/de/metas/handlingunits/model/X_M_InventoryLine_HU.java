/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InventoryLine_HU
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_InventoryLine_HU extends org.compiere.model.PO implements I_M_InventoryLine_HU, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1008110903L;

    /** Standard Constructor */
    public X_M_InventoryLine_HU (Properties ctx, int M_InventoryLine_HU_ID, String trxName)
    {
      super (ctx, M_InventoryLine_HU_ID, trxName);
      /** if (M_InventoryLine_HU_ID == 0)
        {
			setC_UOM_ID (0);
			setM_InventoryLine_HU_ID (0);
			setM_InventoryLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_InventoryLine_HU (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Unit.
		@param M_HU_ID Handling Unit	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Unit.
		@return Handling Unit	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_InventoryLine_HU.
		@param M_InventoryLine_HU_ID M_InventoryLine_HU	  */
	@Override
	public void setM_InventoryLine_HU_ID (int M_InventoryLine_HU_ID)
	{
		if (M_InventoryLine_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_HU_ID, Integer.valueOf(M_InventoryLine_HU_ID));
	}

	/** Get M_InventoryLine_HU.
		@return M_InventoryLine_HU	  */
	@Override
	public int getM_InventoryLine_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InventoryLine_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_InventoryLine getM_InventoryLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class);
	}

	@Override
	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InventoryLine_ID, org.compiere.model.I_M_InventoryLine.class, M_InventoryLine);
	}

	/** Set Inventur-Position.
		@param M_InventoryLine_ID 
		Eindeutige Position in einem Inventurdokument
	  */
	@Override
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	/** Get Inventur-Position.
		@return Eindeutige Position in einem Inventurdokument
	  */
	@Override
	public int getM_InventoryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InventoryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Buchmenge.
		@param QtyBook 
		Buchmenge
	  */
	@Override
	public void setQtyBook (java.math.BigDecimal QtyBook)
	{
		set_Value (COLUMNNAME_QtyBook, QtyBook);
	}

	/** Get Buchmenge.
		@return Buchmenge
	  */
	@Override
	public java.math.BigDecimal getQtyBook () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBook);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zählmenge.
		@param QtyCount 
		Gezählte Menge
	  */
	@Override
	public void setQtyCount (java.math.BigDecimal QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, QtyCount);
	}

	/** Get Zählmenge.
		@return Gezählte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyCount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Internal Use Qty.
		@param QtyInternalUse 
		Internal Use Quantity removed from Inventory
	  */
	@Override
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse)
	{
		set_Value (COLUMNNAME_QtyInternalUse, QtyInternalUse);
	}

	/** Get Internal Use Qty.
		@return Internal Use Quantity removed from Inventory
	  */
	@Override
	public java.math.BigDecimal getQtyInternalUse () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInternalUse);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}