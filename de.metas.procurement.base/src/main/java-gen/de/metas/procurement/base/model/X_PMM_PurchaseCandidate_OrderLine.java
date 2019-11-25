/** Generated Model - DO NOT CHANGE */
package de.metas.procurement.base.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PMM_PurchaseCandidate_OrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PMM_PurchaseCandidate_OrderLine extends org.compiere.model.PO implements I_PMM_PurchaseCandidate_OrderLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1526874375L;

    /** Standard Constructor */
    public X_PMM_PurchaseCandidate_OrderLine (Properties ctx, int PMM_PurchaseCandidate_OrderLine_ID, String trxName)
    {
      super (ctx, PMM_PurchaseCandidate_OrderLine_ID, trxName);
      /** if (PMM_PurchaseCandidate_OrderLine_ID == 0)
        {
			setC_OrderLine_ID (0);
			setPMM_PurchaseCandidate_ID (0);
			setPMM_PurchaseCandidate_OrderLine_ID (0);
			setQtyOrdered (BigDecimal.ZERO);
			setQtyOrdered_TU (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_PMM_PurchaseCandidate_OrderLine (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Auftragsposition
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Auftragsposition
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.procurement.base.model.I_PMM_PurchaseCandidate getPMM_PurchaseCandidate()
	{
		return get_ValueAsPO(COLUMNNAME_PMM_PurchaseCandidate_ID, de.metas.procurement.base.model.I_PMM_PurchaseCandidate.class);
	}

	@Override
	public void setPMM_PurchaseCandidate(de.metas.procurement.base.model.I_PMM_PurchaseCandidate PMM_PurchaseCandidate)
	{
		set_ValueFromPO(COLUMNNAME_PMM_PurchaseCandidate_ID, de.metas.procurement.base.model.I_PMM_PurchaseCandidate.class, PMM_PurchaseCandidate);
	}

	/** Set Bestellkandidat.
		@param PMM_PurchaseCandidate_ID Bestellkandidat	  */
	@Override
	public void setPMM_PurchaseCandidate_ID (int PMM_PurchaseCandidate_ID)
	{
		if (PMM_PurchaseCandidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_PurchaseCandidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_PurchaseCandidate_ID, Integer.valueOf(PMM_PurchaseCandidate_ID));
	}

	/** Get Bestellkandidat.
		@return Bestellkandidat	  */
	@Override
	public int getPMM_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Purchase order candidate - order line.
		@param PMM_PurchaseCandidate_OrderLine_ID Purchase order candidate - order line	  */
	@Override
	public void setPMM_PurchaseCandidate_OrderLine_ID (int PMM_PurchaseCandidate_OrderLine_ID)
	{
		if (PMM_PurchaseCandidate_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PMM_PurchaseCandidate_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PMM_PurchaseCandidate_OrderLine_ID, Integer.valueOf(PMM_PurchaseCandidate_OrderLine_ID));
	}

	/** Get Purchase order candidate - order line.
		@return Purchase order candidate - order line	  */
	@Override
	public int getPMM_PurchaseCandidate_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PMM_PurchaseCandidate_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestellt/ Beauftragt.
		@param QtyOrdered 
		Bestellt/ Beauftragt
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellt/ Beauftragt.
		@return Bestellt/ Beauftragt
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellte Menge (TU).
		@param QtyOrdered_TU 
		Bestellte Menge (TU)
	  */
	@Override
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU)
	{
		set_Value (COLUMNNAME_QtyOrdered_TU, QtyOrdered_TU);
	}

	/** Get Bestellte Menge (TU).
		@return Bestellte Menge (TU)
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered_TU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered_TU);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}