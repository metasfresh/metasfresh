/** Generated Model - DO NOT CHANGE */
package de.metas.material.dispo.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Candidate_Transaction_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Candidate_Transaction_Detail extends org.compiere.model.PO implements I_MD_Candidate_Transaction_Detail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -770068999L;

    /** Standard Constructor */
    public X_MD_Candidate_Transaction_Detail (Properties ctx, int MD_Candidate_Transaction_Detail_ID, String trxName)
    {
      super (ctx, MD_Candidate_Transaction_Detail_ID, trxName);
      /** if (MD_Candidate_Transaction_Detail_ID == 0)
        {
			setMD_Candidate_ID (0);
			setMD_Candidate_Transaction_Detail_ID (0);
			setMovementQty (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_MD_Candidate_Transaction_Detail (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.material.dispo.model.I_MD_Candidate getMD_Candidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class);
	}

	@Override
	public void setMD_Candidate(de.metas.material.dispo.model.I_MD_Candidate MD_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_MD_Candidate_ID, de.metas.material.dispo.model.I_MD_Candidate.class, MD_Candidate);
	}

	/** Set Dispositionskandidat.
		@param MD_Candidate_ID Dispositionskandidat	  */
	@Override
	public void setMD_Candidate_ID (int MD_Candidate_ID)
	{
		if (MD_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_ID, Integer.valueOf(MD_Candidate_ID));
	}

	/** Get Dispositionskandidat.
		@return Dispositionskandidat	  */
	@Override
	public int getMD_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dispo-Transaktionsdetail.
		@param MD_Candidate_Transaction_Detail_ID Dispo-Transaktionsdetail	  */
	@Override
	public void setMD_Candidate_Transaction_Detail_ID (int MD_Candidate_Transaction_Detail_ID)
	{
		if (MD_Candidate_Transaction_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Transaction_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Candidate_Transaction_Detail_ID, Integer.valueOf(MD_Candidate_Transaction_Detail_ID));
	}

	/** Get Dispo-Transaktionsdetail.
		@return Dispo-Transaktionsdetail	  */
	@Override
	public int getMD_Candidate_Transaction_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Candidate_Transaction_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bewegungs-Menge.
		@param MovementQty 
		Menge eines bewegten Produktes.
	  */
	@Override
	public void setMovementQty (java.math.BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Bewegungs-Menge.
		@return Menge eines bewegten Produktes.
	  */
	@Override
	public java.math.BigDecimal getMovementQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_M_Transaction getM_Transaction() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Transaction_ID, org.compiere.model.I_M_Transaction.class);
	}

	@Override
	public void setM_Transaction(org.compiere.model.I_M_Transaction M_Transaction)
	{
		set_ValueFromPO(COLUMNNAME_M_Transaction_ID, org.compiere.model.I_M_Transaction.class, M_Transaction);
	}

	/** Set Bestands-Transaktion.
		@param M_Transaction_ID Bestands-Transaktion	  */
	@Override
	public void setM_Transaction_ID (int M_Transaction_ID)
	{
		if (M_Transaction_ID < 1) 
			set_Value (COLUMNNAME_M_Transaction_ID, null);
		else 
			set_Value (COLUMNNAME_M_Transaction_ID, Integer.valueOf(M_Transaction_ID));
	}

	/** Get Bestands-Transaktion.
		@return Bestands-Transaktion	  */
	@Override
	public int getM_Transaction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Transaction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}