// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_CostElement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_CostElement extends org.compiere.model.PO implements I_M_CostElement, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 561967543L;

    /** Standard Constructor */
    public X_M_CostElement (final Properties ctx, final int M_CostElement_ID, @Nullable final String trxName)
    {
      super (ctx, M_CostElement_ID, trxName);
    }

    /** Load Constructor */
    public X_M_CostElement (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
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
	@Override
	public void setCostElementType (final java.lang.String CostElementType)
	{
		set_Value (COLUMNNAME_CostElementType, CostElementType);
	}

	@Override
	public java.lang.String getCostElementType() 
	{
		return get_ValueAsString(COLUMNNAME_CostElementType);
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
	/** MovingAverageInvoice = M */
	public static final String COSTINGMETHOD_MovingAverageInvoice = "M";
	@Override
	public void setCostingMethod (final java.lang.String CostingMethod)
	{
		set_Value (COLUMNNAME_CostingMethod, CostingMethod);
	}

	@Override
	public java.lang.String getCostingMethod() 
	{
		return get_ValueAsString(COLUMNNAME_CostingMethod);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIsCalculated (final boolean IsCalculated)
	{
		set_Value (COLUMNNAME_IsCalculated, IsCalculated);
	}

	@Override
	public boolean isCalculated() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCalculated);
	}

	@Override
	public void setM_CostElement_ID (final int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_CostElement_ID, M_CostElement_ID);
	}

	@Override
	public int getM_CostElement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_CostElement_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}
}