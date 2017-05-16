/** Generated Model - DO NOT CHANGE */
package de.metas.inout.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_InOutLine_HU_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_InOutLine_HU_Alloc extends org.compiere.model.PO implements I_M_InOutLine_HU_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 996080131L;

    /** Standard Constructor */
    public X_M_InOutLine_HU_Alloc (Properties ctx, int M_InOutLine_HU_Alloc_ID, String trxName)
    {
      super (ctx, M_InOutLine_HU_Alloc_ID, trxName);
      /** if (M_InOutLine_HU_Alloc_ID == 0)
        {
			setM_InOutLine_HU_Alloc_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_InOutLine_HU_Alloc (Properties ctx, ResultSet rs, String trxName)
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

	

	/** Set M_InOutLine_HU_Alloc.
		@param M_InOutLine_HU_Alloc_ID M_InOutLine_HU_Alloc	  */
	@Override
	public void setM_InOutLine_HU_Alloc_ID (int M_InOutLine_HU_Alloc_ID)
	{
		if (M_InOutLine_HU_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_HU_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_HU_Alloc_ID, Integer.valueOf(M_InOutLine_HU_Alloc_ID));
	}

	/** Get M_InOutLine_HU_Alloc.
		@return M_InOutLine_HU_Alloc	  */
	@Override
	public int getM_InOutLine_HU_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_HU_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
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
}