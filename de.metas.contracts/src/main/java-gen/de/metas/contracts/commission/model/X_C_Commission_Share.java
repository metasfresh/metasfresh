/** Generated Model - DO NOT CHANGE */
package de.metas.contracts.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Commission_Share
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Commission_Share extends org.compiere.model.PO implements I_C_Commission_Share, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -50619823L;

    /** Standard Constructor */
    public X_C_Commission_Share (Properties ctx, int C_Commission_Share_ID, String trxName)
    {
      super (ctx, C_Commission_Share_ID, trxName);
      /** if (C_Commission_Share_ID == 0)
        {
			setC_BPartner_SalesRep_ID (0);
			setC_Commission_Instance_ID (0);
			setC_Commission_Share_ID (0);
			setC_Flatrate_Term_ID (0);
			setLevelHierarchy (0);
			setPointsSum_Forecasted (BigDecimal.ZERO); // 0
			setPointsSum_Invoiceable (BigDecimal.ZERO); // 0
			setPointsSum_Invoiced (BigDecimal.ZERO); // 0
			setPointsSum_Settled (BigDecimal.ZERO); // 0
			setPointsSum_ToSettle (BigDecimal.ZERO); // 0
        } */
    }

    /** Load Constructor */
    public X_C_Commission_Share (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zugeordneter Vertriebspartner.
		@param C_BPartner_SalesRep_ID Zugeordneter Vertriebspartner	  */
	@Override
	public void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID)
	{
		if (C_BPartner_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_SalesRep_ID, Integer.valueOf(C_BPartner_SalesRep_ID));
	}

	/** Get Zugeordneter Vertriebspartner.
		@return Zugeordneter Vertriebspartner	  */
	@Override
	public int getC_BPartner_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.contracts.commission.model.I_C_Commission_Instance getC_Commission_Instance()
	{
		return get_ValueAsPO(COLUMNNAME_C_Commission_Instance_ID, de.metas.contracts.commission.model.I_C_Commission_Instance.class);
	}

	@Override
	public void setC_Commission_Instance(de.metas.contracts.commission.model.I_C_Commission_Instance C_Commission_Instance)
	{
		set_ValueFromPO(COLUMNNAME_C_Commission_Instance_ID, de.metas.contracts.commission.model.I_C_Commission_Instance.class, C_Commission_Instance);
	}

	/** Set C_Commission_Instance.
		@param C_Commission_Instance_ID C_Commission_Instance	  */
	@Override
	public void setC_Commission_Instance_ID (int C_Commission_Instance_ID)
	{
		if (C_Commission_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Instance_ID, Integer.valueOf(C_Commission_Instance_ID));
	}

	/** Get C_Commission_Instance.
		@return C_Commission_Instance	  */
	@Override
	public int getC_Commission_Instance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Commission_Share.
		@param C_Commission_Share_ID C_Commission_Share	  */
	@Override
	public void setC_Commission_Share_ID (int C_Commission_Share_ID)
	{
		if (C_Commission_Share_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Commission_Share_ID, Integer.valueOf(C_Commission_Share_ID));
	}

	/** Get C_Commission_Share.
		@return C_Commission_Share	  */
	@Override
	public int getC_Commission_Share_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Commission_Share_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Hierarchie-Ebene.
		@param LevelHierarchy Hierarchie-Ebene	  */
	@Override
	public void setLevelHierarchy (int LevelHierarchy)
	{
		set_ValueNoCheck (COLUMNNAME_LevelHierarchy, Integer.valueOf(LevelHierarchy));
	}

	/** Get Hierarchie-Ebene.
		@return Hierarchie-Ebene	  */
	@Override
	public int getLevelHierarchy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LevelHierarchy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prognostizierte Punktzahl.
		@param PointsSum_Forecasted Prognostizierte Punktzahl	  */
	@Override
	public void setPointsSum_Forecasted (java.math.BigDecimal PointsSum_Forecasted)
	{
		set_Value (COLUMNNAME_PointsSum_Forecasted, PointsSum_Forecasted);
	}

	/** Get Prognostizierte Punktzahl.
		@return Prognostizierte Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_Forecasted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Forecasted);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Gelieferte Punktzahl.
		@param PointsSum_Invoiceable Gelieferte Punktzahl	  */
	@Override
	public void setPointsSum_Invoiceable (java.math.BigDecimal PointsSum_Invoiceable)
	{
		set_ValueNoCheck (COLUMNNAME_PointsSum_Invoiceable, PointsSum_Invoiceable);
	}

	/** Get Gelieferte Punktzahl.
		@return Gelieferte Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_Invoiceable () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Invoiceable);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Fakturierte Punktzahl.
		@param PointsSum_Invoiced Fakturierte Punktzahl	  */
	@Override
	public void setPointsSum_Invoiced (java.math.BigDecimal PointsSum_Invoiced)
	{
		set_Value (COLUMNNAME_PointsSum_Invoiced, PointsSum_Invoiced);
	}

	/** Get Fakturierte Punktzahl.
		@return Fakturierte Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_Invoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Invoiced);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abgerechnete Punktzahl.
		@param PointsSum_Settled Abgerechnete Punktzahl	  */
	@Override
	public void setPointsSum_Settled (java.math.BigDecimal PointsSum_Settled)
	{
		set_Value (COLUMNNAME_PointsSum_Settled, PointsSum_Settled);
	}

	/** Get Abgerechnete Punktzahl.
		@return Abgerechnete Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_Settled () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_Settled);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Abzurechnende Punktzahl.
		@param PointsSum_ToSettle Abzurechnende Punktzahl	  */
	@Override
	public void setPointsSum_ToSettle (java.math.BigDecimal PointsSum_ToSettle)
	{
		set_Value (COLUMNNAME_PointsSum_ToSettle, PointsSum_ToSettle);
	}

	/** Get Abzurechnende Punktzahl.
		@return Abzurechnende Punktzahl	  */
	@Override
	public java.math.BigDecimal getPointsSum_ToSettle () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PointsSum_ToSettle);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}