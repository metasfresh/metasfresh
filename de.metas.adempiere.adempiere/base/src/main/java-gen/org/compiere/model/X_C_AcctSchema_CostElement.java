/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_AcctSchema_CostElement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AcctSchema_CostElement extends org.compiere.model.PO implements I_C_AcctSchema_CostElement, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -966805190L;

    /** Standard Constructor */
    public X_C_AcctSchema_CostElement (Properties ctx, int C_AcctSchema_CostElement_ID, String trxName)
    {
      super (ctx, C_AcctSchema_CostElement_ID, trxName);
      /** if (C_AcctSchema_CostElement_ID == 0)
        {
			setC_AcctSchema_CostElement_ID (0);
			setC_AcctSchema_ID (0);
			setM_CostElement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AcctSchema_CostElement (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Accounting Schema Cost Element.
		@param C_AcctSchema_CostElement_ID Accounting Schema Cost Element	  */
	@Override
	public void setC_AcctSchema_CostElement_ID (int C_AcctSchema_CostElement_ID)
	{
		if (C_AcctSchema_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_CostElement_ID, Integer.valueOf(C_AcctSchema_CostElement_ID));
	}

	/** Get Accounting Schema Cost Element.
		@return Accounting Schema Cost Element	  */
	@Override
	public int getC_AcctSchema_CostElement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_CostElement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Stammdaten für Buchhaltung
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Stammdaten für Buchhaltung
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_CostElement getM_CostElement() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class);
	}

	@Override
	public void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement)
	{
		set_ValueFromPO(COLUMNNAME_M_CostElement_ID, org.compiere.model.I_M_CostElement.class, M_CostElement);
	}

	/** Set Kostenart.
		@param M_CostElement_ID 
		Produkt-Kostenart
	  */
	@Override
	public void setM_CostElement_ID (int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_Value (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
	}

	/** Get Kostenart.
		@return Produkt-Kostenart
	  */
	@Override
	public int getM_CostElement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostElement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}