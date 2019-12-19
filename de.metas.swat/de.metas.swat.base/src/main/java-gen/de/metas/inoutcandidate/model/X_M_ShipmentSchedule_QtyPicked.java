/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule_QtyPicked
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule_QtyPicked extends org.compiere.model.PO implements I_M_ShipmentSchedule_QtyPicked, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1183144845L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_QtyPicked (Properties ctx, int M_ShipmentSchedule_QtyPicked_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_QtyPicked_ID, trxName);
      /** if (M_ShipmentSchedule_QtyPicked_ID == 0)
        {
			setisAnonymousHuPickedOnTheFly (false); // N
			setM_ShipmentSchedule_QtyPicked_ID (0);
			setProcessed (false); // N
			setQtyPicked (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_QtyPicked (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Catch Einheit.
		@param Catch_UOM_ID 
		Aus dem Produktstamm übenommene Catch Weight Einheit.
	  */
	@Override
	public void setCatch_UOM_ID (int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_Value (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Catch_UOM_ID, Integer.valueOf(Catch_UOM_ID));
	}

	/** Get Catch Einheit.
		@return Aus dem Produktstamm übenommene Catch Weight Einheit.
	  */
	@Override
	public int getCatch_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Catch_UOM_ID);
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

	/** Set Anonymous HU Picked On the Fly.
		@param isAnonymousHuPickedOnTheFly Anonymous HU Picked On the Fly	  */
	@Override
	public void setisAnonymousHuPickedOnTheFly (boolean isAnonymousHuPickedOnTheFly)
	{
		set_Value (COLUMNNAME_isAnonymousHuPickedOnTheFly, Boolean.valueOf(isAnonymousHuPickedOnTheFly));
	}

	/** Get Anonymous HU Picked On the Fly.
		@return Anonymous HU Picked On the Fly	  */
	@Override
	public boolean isAnonymousHuPickedOnTheFly () 
	{
		Object oo = get_Value(COLUMNNAME_isAnonymousHuPickedOnTheFly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	/** Set Versand-/Wareneingangsposition.
		@param M_InOutLine_ID 
		Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Versand-/Wareneingangsposition.
		@return Position auf Versand- oder Wareneingangsbeleg
	  */
	@Override
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class, M_ShipmentSchedule);
	}

	/** Set Lieferdisposition.
		@param M_ShipmentSchedule_ID Lieferdisposition	  */
	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/** Get Lieferdisposition.
		@return Lieferdisposition	  */
	@Override
	public int getM_ShipmentSchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ShipmentSchedule QtyPicked.
		@param M_ShipmentSchedule_QtyPicked_ID ShipmentSchedule QtyPicked	  */
	@Override
	public void setM_ShipmentSchedule_QtyPicked_ID (int M_ShipmentSchedule_QtyPicked_ID)
	{
		if (M_ShipmentSchedule_QtyPicked_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID, Integer.valueOf(M_ShipmentSchedule_QtyPicked_ID));
	}

	/** Get ShipmentSchedule QtyPicked.
		@return ShipmentSchedule QtyPicked	  */
	@Override
	public int getM_ShipmentSchedule_QtyPicked_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
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

	/** Set Geliefert Catch.
		@param QtyDeliveredCatch 
		Tatsächlich gelieferte Menge
	  */
	@Override
	public void setQtyDeliveredCatch (java.math.BigDecimal QtyDeliveredCatch)
	{
		set_Value (COLUMNNAME_QtyDeliveredCatch, QtyDeliveredCatch);
	}

	/** Get Geliefert Catch.
		@return Tatsächlich gelieferte Menge
	  */
	@Override
	public java.math.BigDecimal getQtyDeliveredCatch () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDeliveredCatch);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge (Lagereinheit).
		@param QtyPicked Menge (Lagereinheit)	  */
	@Override
	public void setQtyPicked (java.math.BigDecimal QtyPicked)
	{
		set_Value (COLUMNNAME_QtyPicked, QtyPicked);
	}

	/** Get Menge (Lagereinheit).
		@return Menge (Lagereinheit)	  */
	@Override
	public java.math.BigDecimal getQtyPicked () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPicked);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}