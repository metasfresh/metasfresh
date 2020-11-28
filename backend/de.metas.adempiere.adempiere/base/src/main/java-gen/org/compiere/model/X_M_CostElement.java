/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_CostElement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_CostElement extends org.compiere.model.PO implements I_M_CostElement, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -507826916L;

    /** Standard Constructor */
    public X_M_CostElement (Properties ctx, int M_CostElement_ID, String trxName)
    {
      super (ctx, M_CostElement_ID, trxName);
      /** if (M_CostElement_ID == 0)
        {
			setCostElementType (null);
			setIsCalculated (false);
			setM_CostElement_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_CostElement (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * CostElementType AD_Reference_ID=338
	 * Reference name: M_CostElement Type
	 */
	public static final int COSTELEMENTTYPE_AD_Reference_ID=338;
	/** Material = M */
	public static final String COSTELEMENTTYPE_Material = "M";
	/** Overhead = O */
	public static final String COSTELEMENTTYPE_Overhead = "O";
	/** BurdenMOverhead = B */
	public static final String COSTELEMENTTYPE_BurdenMOverhead = "B";
	/** OutsideProcessing = X */
	public static final String COSTELEMENTTYPE_OutsideProcessing = "X";
	/** Resource = R */
	public static final String COSTELEMENTTYPE_Resource = "R";
	/** Set Kostenarttyp.
		@param CostElementType 
		Type of Cost Element
	  */
	@Override
	public void setCostElementType (java.lang.String CostElementType)
	{

		set_Value (COLUMNNAME_CostElementType, CostElementType);
	}

	/** Get Kostenarttyp.
		@return Type of Cost Element
	  */
	@Override
	public java.lang.String getCostElementType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostElementType);
	}

	/** 
	 * CostingMethod AD_Reference_ID=122
	 * Reference name: C_AcctSchema Costing Method
	 */
	public static final int COSTINGMETHOD_AD_Reference_ID=122;
	/** StandardCosting = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** AveragePO = A */
	public static final String COSTINGMETHOD_AveragePO = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";
	/** LastPOPrice = p */
	public static final String COSTINGMETHOD_LastPOPrice = "p";
	/** AverageInvoice = I */
	public static final String COSTINGMETHOD_AverageInvoice = "I";
	/** LastInvoice = i */
	public static final String COSTINGMETHOD_LastInvoice = "i";
	/** UserDefined = U */
	public static final String COSTINGMETHOD_UserDefined = "U";
	/** _ = x */
	public static final String COSTINGMETHOD__ = "x";
	/** Set Kostenrechnungsmethode.
		@param CostingMethod 
		Indicates how Costs will be calculated
	  */
	@Override
	public void setCostingMethod (java.lang.String CostingMethod)
	{

		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	/** Get Kostenrechnungsmethode.
		@return Indicates how Costs will be calculated
	  */
	@Override
	public java.lang.String getCostingMethod () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CostingMethod);
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

	/** Set Berechnet.
		@param IsCalculated 
		The value is calculated by the system
	  */
	@Override
	public void setIsCalculated (boolean IsCalculated)
	{
		set_Value (COLUMNNAME_IsCalculated, Boolean.valueOf(IsCalculated));
	}

	/** Get Berechnet.
		@return The value is calculated by the system
	  */
	@Override
	public boolean isCalculated () 
	{
		Object oo = get_Value(COLUMNNAME_IsCalculated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kostenart.
		@param M_CostElement_ID 
		Product Cost Element
	  */
	@Override
	public void setM_CostElement_ID (int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
	}

	/** Get Kostenart.
		@return Product Cost Element
	  */
	@Override
	public int getM_CostElement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostElement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}
}