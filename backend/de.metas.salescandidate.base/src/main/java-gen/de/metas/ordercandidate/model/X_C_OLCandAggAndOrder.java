// Generated Model - DO NOT CHANGE
package de.metas.ordercandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_OLCandAggAndOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OLCandAggAndOrder extends org.compiere.model.PO implements I_C_OLCandAggAndOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -349204522L;

    /** Standard Constructor */
    public X_C_OLCandAggAndOrder (final Properties ctx, final int C_OLCandAggAndOrder_ID, @Nullable final String trxName)
    {
      super (ctx, C_OLCandAggAndOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OLCandAggAndOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Column getAD_Column_OLCand()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_OLCand_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column_OLCand(final org.compiere.model.I_AD_Column AD_Column_OLCand)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_OLCand_ID, org.compiere.model.I_AD_Column.class, AD_Column_OLCand);
	}

	@Override
	public void setAD_Column_OLCand_ID (final int AD_Column_OLCand_ID)
	{
		if (AD_Column_OLCand_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_OLCand_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_OLCand_ID, AD_Column_OLCand_ID);
	}

	@Override
	public int getAD_Column_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_OLCand_ID);
	}

	@Override
	public void setAD_InputDataSource_ID (final int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1) 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, AD_InputDataSource_ID);
	}

	@Override
	public int getAD_InputDataSource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_InputDataSource_ID);
	}

	@Override
	public void setAD_Reference_OLCand_ID (final int AD_Reference_OLCand_ID)
	{
		throw new IllegalArgumentException ("AD_Reference_OLCand_ID is virtual column");	}

	@Override
	public int getAD_Reference_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_OLCand_ID);
	}

	@Override
	public void setC_OLCandAggAndOrder_ID (final int C_OLCandAggAndOrder_ID)
	{
		if (C_OLCandAggAndOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandAggAndOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandAggAndOrder_ID, C_OLCandAggAndOrder_ID);
	}

	@Override
	public int getC_OLCandAggAndOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCandAggAndOrder_ID);
	}

	@Override
	public de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor()
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
	}

	@Override
	public void setC_OLCandProcessor(final de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class, C_OLCandProcessor);
	}

	@Override
	public void setC_OLCandProcessor_ID (final int C_OLCandProcessor_ID)
	{
		if (C_OLCandProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, C_OLCandProcessor_ID);
	}

	@Override
	public int getC_OLCandProcessor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCandProcessor_ID);
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
	@Override
	public void setGranularity (final @Nullable java.lang.String Granularity)
	{
		set_Value (COLUMNNAME_Granularity, Granularity);
	}

	@Override
	public java.lang.String getGranularity() 
	{
		return get_ValueAsString(COLUMNNAME_Granularity);
	}

	@Override
	public void setGroupBy (final boolean GroupBy)
	{
		set_Value (COLUMNNAME_GroupBy, GroupBy);
	}

	@Override
	public boolean isGroupBy() 
	{
		return get_ValueAsBoolean(COLUMNNAME_GroupBy);
	}

	@Override
	public void setOrderBySeqNo (final int OrderBySeqNo)
	{
		set_Value (COLUMNNAME_OrderBySeqNo, OrderBySeqNo);
	}

	@Override
	public int getOrderBySeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_OrderBySeqNo);
	}

	@Override
	public void setSplitOrder (final boolean SplitOrder)
	{
		set_Value (COLUMNNAME_SplitOrder, SplitOrder);
	}

	@Override
	public boolean isSplitOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_SplitOrder);
	}
}