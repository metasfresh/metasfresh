/** Generated Model - DO NOT CHANGE */
package de.metas.ordercandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_OLCandAggAndOrder
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_OLCandAggAndOrder extends org.compiere.model.PO implements I_C_OLCandAggAndOrder, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2068366896L;

    /** Standard Constructor */
    public X_C_OLCandAggAndOrder (Properties ctx, int C_OLCandAggAndOrder_ID, String trxName)
    {
      super (ctx, C_OLCandAggAndOrder_ID, trxName);
      /** if (C_OLCandAggAndOrder_ID == 0)
        {
			setAD_Column_OLCand_ID (0);
			setC_OLCandAggAndOrder_ID (0);
			setC_OLCandProcessor_ID (0);
			setGroupBy (false); // N
			setSplitOrder (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_OLCandAggAndOrder (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column_OLCand() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_OLCand_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column_OLCand(org.compiere.model.I_AD_Column AD_Column_OLCand)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_OLCand_ID, org.compiere.model.I_AD_Column.class, AD_Column_OLCand);
	}

	/** Set Auftragskand. Spalte.
		@param AD_Column_OLCand_ID Auftragskand. Spalte	  */
	@Override
	public void setAD_Column_OLCand_ID (int AD_Column_OLCand_ID)
	{
		if (AD_Column_OLCand_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_OLCand_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_OLCand_ID, Integer.valueOf(AD_Column_OLCand_ID));
	}

	/** Get Auftragskand. Spalte.
		@return Auftragskand. Spalte	  */
	@Override
	public int getAD_Column_OLCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_OLCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Eingabequelle.
		@param AD_InputDataSource_ID Eingabequelle	  */
	@Override
	public void setAD_InputDataSource_ID (int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1) 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, Integer.valueOf(AD_InputDataSource_ID));
	}

	/** Get Eingabequelle.
		@return Eingabequelle	  */
	@Override
	public int getAD_InputDataSource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InputDataSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz-ID der Auftragskand. Spalte.
		@param AD_Reference_OLCand_ID Referenz-ID der Auftragskand. Spalte	  */
	@Override
	public void setAD_Reference_OLCand_ID (int AD_Reference_OLCand_ID)
	{
		throw new IllegalArgumentException ("AD_Reference_OLCand_ID is virtual column");	}

	/** Get Referenz-ID der Auftragskand. Spalte.
		@return Referenz-ID der Auftragskand. Spalte	  */
	@Override
	public int getAD_Reference_OLCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_OLCand_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragskand. Aggregieren.
		@param C_OLCandAggAndOrder_ID Auftragskand. Aggregieren	  */
	@Override
	public void setC_OLCandAggAndOrder_ID (int C_OLCandAggAndOrder_ID)
	{
		if (C_OLCandAggAndOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandAggAndOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandAggAndOrder_ID, Integer.valueOf(C_OLCandAggAndOrder_ID));
	}

	/** Get Auftragskand. Aggregieren.
		@return Auftragskand. Aggregieren	  */
	@Override
	public int getC_OLCandAggAndOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCandAggAndOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
	}

	@Override
	public void setC_OLCandProcessor(de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class, C_OLCandProcessor);
	}

	/** Set Auftragskand. Verarb..
		@param C_OLCandProcessor_ID Auftragskand. Verarb.	  */
	@Override
	public void setC_OLCandProcessor_ID (int C_OLCandProcessor_ID)
	{
		if (C_OLCandProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, Integer.valueOf(C_OLCandProcessor_ID));
	}

	/** Get Auftragskand. Verarb..
		@return Auftragskand. Verarb.	  */
	@Override
	public int getC_OLCandProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCandProcessor_ID);
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

	/** 
	 * Granularity AD_Reference_ID=540141
	 * Reference name: Granularity OLCandAggAndOrder
	 */
	public static final int GRANULARITY_AD_Reference_ID=540141;
	/** Tag = D */
	public static final String GRANULARITY_Tag = "D";
	/** Woche = W */
	public static final String GRANULARITY_Woche = "W";
	/** Monat = M */
	public static final String GRANULARITY_Monat = "M";
	/** Set Agg.-Ebene.
		@param Granularity Agg.-Ebene	  */
	@Override
	public void setGranularity (java.lang.String Granularity)
	{

		set_Value (COLUMNNAME_Granularity, Granularity);
	}

	/** Get Agg.-Ebene.
		@return Agg.-Ebene	  */
	@Override
	public java.lang.String getGranularity () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Granularity);
	}

	/** Set Aggregieren.
		@param GroupBy Aggregieren	  */
	@Override
	public void setGroupBy (boolean GroupBy)
	{
		set_Value (COLUMNNAME_GroupBy, Boolean.valueOf(GroupBy));
	}

	/** Get Aggregieren.
		@return Aggregieren	  */
	@Override
	public boolean isGroupBy () 
	{
		Object oo = get_Value(COLUMNNAME_GroupBy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sortier-Reihenfolge.
		@param OrderBySeqNo Sortier-Reihenfolge	  */
	@Override
	public void setOrderBySeqNo (int OrderBySeqNo)
	{
		set_Value (COLUMNNAME_OrderBySeqNo, Integer.valueOf(OrderBySeqNo));
	}

	/** Get Sortier-Reihenfolge.
		@return Sortier-Reihenfolge	  */
	@Override
	public int getOrderBySeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OrderBySeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set In eig. Auftrag.
		@param SplitOrder In eig. Auftrag	  */
	@Override
	public void setSplitOrder (boolean SplitOrder)
	{
		set_Value (COLUMNNAME_SplitOrder, Boolean.valueOf(SplitOrder));
	}

	/** Get In eig. Auftrag.
		@return In eig. Auftrag	  */
	@Override
	public boolean isSplitOrder () 
	{
		Object oo = get_Value(COLUMNNAME_SplitOrder);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}