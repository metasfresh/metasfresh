/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_HU_Trx_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Trx_Line extends org.compiere.model.PO implements I_M_HU_Trx_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1498438348L;

    /** Standard Constructor */
    public X_M_HU_Trx_Line (Properties ctx, int M_HU_Trx_Line_ID, String trxName)
    {
      super (ctx, M_HU_Trx_Line_ID, trxName);
      /** if (M_HU_Trx_Line_ID == 0)
        {
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setM_HU_Trx_Hdr_ID (0);
			setM_HU_Trx_Line_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_Trx_Line (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
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

	/** Set Vorgangsdatum.
		@param DateTrx 
		Vorgangsdatum
	  */
	@Override
	public void setDateTrx (java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Vorgangsdatum.
		@return Vorgangsdatum
	  */
	@Override
	public java.sql.Timestamp getDateTrx () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Set Gebinde Status.
		@param HUStatus Gebinde Status	  */
	@Override
	public void setHUStatus (java.lang.String HUStatus)
	{

		set_Value (COLUMNNAME_HUStatus, HUStatus);
	}

	/** Get Gebinde Status.
		@return Gebinde Status	  */
	@Override
	public java.lang.String getHUStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUStatus);
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
	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class);
	}

	@Override
	public void setM_HU_Item(de.metas.handlingunits.model.I_M_HU_Item M_HU_Item)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class, M_HU_Item);
	}

	/** Set Handling Units Item.
		@param M_HU_Item_ID Handling Units Item	  */
	@Override
	public void setM_HU_Item_ID (int M_HU_Item_ID)
	{
		if (M_HU_Item_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Item_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Item_ID, Integer.valueOf(M_HU_Item_ID));
	}

	/** Get Handling Units Item.
		@return Handling Units Item	  */
	@Override
	public int getM_HU_Item_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Item_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Hdr getM_HU_Trx_Hdr() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class);
	}

	@Override
	public void setM_HU_Trx_Hdr(de.metas.handlingunits.model.I_M_HU_Trx_Hdr M_HU_Trx_Hdr)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Trx_Hdr_ID, de.metas.handlingunits.model.I_M_HU_Trx_Hdr.class, M_HU_Trx_Hdr);
	}

	/** Set HU-Transaktionskopf.
		@param M_HU_Trx_Hdr_ID HU-Transaktionskopf	  */
	@Override
	public void setM_HU_Trx_Hdr_ID (int M_HU_Trx_Hdr_ID)
	{
		if (M_HU_Trx_Hdr_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Hdr_ID, Integer.valueOf(M_HU_Trx_Hdr_ID));
	}

	/** Get HU-Transaktionskopf.
		@return HU-Transaktionskopf	  */
	@Override
	public int getM_HU_Trx_Hdr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Hdr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HU-Transaktionszeile.
		@param M_HU_Trx_Line_ID HU-Transaktionszeile	  */
	@Override
	public void setM_HU_Trx_Line_ID (int M_HU_Trx_Line_ID)
	{
		if (M_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Trx_Line_ID, Integer.valueOf(M_HU_Trx_Line_ID));
	}

	/** Get HU-Transaktionszeile.
		@return HU-Transaktionszeile	  */
	@Override
	public int getM_HU_Trx_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Trx_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Lagerort im Lager
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
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getParent_HU_Trx_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Parent_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setParent_HU_Trx_Line(de.metas.handlingunits.model.I_M_HU_Trx_Line Parent_HU_Trx_Line)
	{
		set_ValueFromPO(COLUMNNAME_Parent_HU_Trx_Line_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, Parent_HU_Trx_Line);
	}

	/** Set Eltern-Transaktionszeile.
		@param Parent_HU_Trx_Line_ID Eltern-Transaktionszeile	  */
	@Override
	public void setParent_HU_Trx_Line_ID (int Parent_HU_Trx_Line_ID)
	{
		if (Parent_HU_Trx_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Parent_HU_Trx_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Parent_HU_Trx_Line_ID, Integer.valueOf(Parent_HU_Trx_Line_ID));
	}

	/** Get Eltern-Transaktionszeile.
		@return Eltern-Transaktionszeile	  */
	@Override
	public int getParent_HU_Trx_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_HU_Trx_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
	public de.metas.handlingunits.model.I_M_HU_Trx_Line getReversalLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_ReversalLine_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class);
	}

	@Override
	public void setReversalLine(de.metas.handlingunits.model.I_M_HU_Trx_Line ReversalLine)
	{
		set_ValueFromPO(COLUMNNAME_ReversalLine_ID, de.metas.handlingunits.model.I_M_HU_Trx_Line.class, ReversalLine);
	}

	/** Set Storno-Zeile.
		@param ReversalLine_ID Storno-Zeile	  */
	@Override
	public void setReversalLine_ID (int ReversalLine_ID)
	{
		if (ReversalLine_ID < 1) 
			set_Value (COLUMNNAME_ReversalLine_ID, null);
		else 
			set_Value (COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/** Get Storno-Zeile.
		@return Storno-Zeile	  */
	@Override
	public int getReversalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Item getVHU_Item() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_VHU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class);
	}

	@Override
	public void setVHU_Item(de.metas.handlingunits.model.I_M_HU_Item VHU_Item)
	{
		set_ValueFromPO(COLUMNNAME_VHU_Item_ID, de.metas.handlingunits.model.I_M_HU_Item.class, VHU_Item);
	}

	/** Set Virtual Handling Units Item.
		@param VHU_Item_ID Virtual Handling Units Item	  */
	@Override
	public void setVHU_Item_ID (int VHU_Item_ID)
	{
		if (VHU_Item_ID < 1) 
			set_Value (COLUMNNAME_VHU_Item_ID, null);
		else 
			set_Value (COLUMNNAME_VHU_Item_ID, Integer.valueOf(VHU_Item_ID));
	}

	/** Get Virtual Handling Units Item.
		@return Virtual Handling Units Item	  */
	@Override
	public int getVHU_Item_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_VHU_Item_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}