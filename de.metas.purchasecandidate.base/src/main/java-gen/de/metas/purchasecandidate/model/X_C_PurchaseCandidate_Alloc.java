/** Generated Model - DO NOT CHANGE */
package de.metas.purchasecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PurchaseCandidate_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PurchaseCandidate_Alloc extends org.compiere.model.PO implements I_C_PurchaseCandidate_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1211195746L;

    /** Standard Constructor */
    public X_C_PurchaseCandidate_Alloc (Properties ctx, int C_PurchaseCandidate_Alloc_ID, String trxName)
    {
      super (ctx, C_PurchaseCandidate_Alloc_ID, trxName);
      /** if (C_PurchaseCandidate_Alloc_ID == 0)
        {
			setC_PurchaseCandidate_Alloc_ID (0);
			setC_PurchaseCandidate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_PurchaseCandidate_Alloc (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_C_OrderLine getC_OrderLinePO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLinePO(org.compiere.model.I_C_OrderLine C_OrderLinePO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLinePO);
	}

	/** Set Bestellposition.
		@param C_OrderLinePO_ID Bestellposition	  */
	@Override
	public void setC_OrderLinePO_ID (int C_OrderLinePO_ID)
	{
		if (C_OrderLinePO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, Integer.valueOf(C_OrderLinePO_ID));
	}

	/** Get Bestellposition.
		@return Bestellposition	  */
	@Override
	public int getC_OrderLinePO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLinePO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_PurchaseCandidate_Alloc.
		@param C_PurchaseCandidate_Alloc_ID C_PurchaseCandidate_Alloc	  */
	@Override
	public void setC_PurchaseCandidate_Alloc_ID (int C_PurchaseCandidate_Alloc_ID)
	{
		if (C_PurchaseCandidate_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_Alloc_ID, Integer.valueOf(C_PurchaseCandidate_Alloc_ID));
	}

	/** Get C_PurchaseCandidate_Alloc.
		@return C_PurchaseCandidate_Alloc	  */
	@Override
	public int getC_PurchaseCandidate_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PurchaseCandidate_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.purchasecandidate.model.I_C_PurchaseCandidate getC_PurchaseCandidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_PurchaseCandidate_ID, de.metas.purchasecandidate.model.I_C_PurchaseCandidate.class);
	}

	@Override
	public void setC_PurchaseCandidate(de.metas.purchasecandidate.model.I_C_PurchaseCandidate C_PurchaseCandidate)
	{
		set_ValueFromPO(COLUMNNAME_C_PurchaseCandidate_ID, de.metas.purchasecandidate.model.I_C_PurchaseCandidate.class, C_PurchaseCandidate);
	}

	/** Set Purchase candidate.
		@param C_PurchaseCandidate_ID Purchase candidate	  */
	@Override
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, Integer.valueOf(C_PurchaseCandidate_ID));
	}

	/** Get Purchase candidate.
		@return Purchase candidate	  */
	@Override
	public int getC_PurchaseCandidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PurchaseCandidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}