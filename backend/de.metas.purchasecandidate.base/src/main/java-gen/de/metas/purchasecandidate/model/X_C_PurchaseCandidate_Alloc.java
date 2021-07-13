// Generated Model - DO NOT CHANGE
package de.metas.purchasecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PurchaseCandidate_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PurchaseCandidate_Alloc extends org.compiere.model.PO implements I_C_PurchaseCandidate_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1921207400L;

    /** Standard Constructor */
    public X_C_PurchaseCandidate_Alloc (final Properties ctx, final int C_PurchaseCandidate_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_PurchaseCandidate_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PurchaseCandidate_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLinePO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLinePO(final org.compiere.model.I_C_OrderLine C_OrderLinePO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLinePO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLinePO);
	}

	@Override
	public void setC_OrderLinePO_ID (final int C_OrderLinePO_ID)
	{
		if (C_OrderLinePO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLinePO_ID, C_OrderLinePO_ID);
	}

	@Override
	public int getC_OrderLinePO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLinePO_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_OrderPO()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_OrderPO(final org.compiere.model.I_C_Order C_OrderPO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderPO_ID, org.compiere.model.I_C_Order.class, C_OrderPO);
	}

	@Override
	public void setC_OrderPO_ID (final int C_OrderPO_ID)
	{
		if (C_OrderPO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderPO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderPO_ID, C_OrderPO_ID);
	}

	@Override
	public int getC_OrderPO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderPO_ID);
	}

	@Override
	public void setC_PurchaseCandidate_Alloc_ID (final int C_PurchaseCandidate_Alloc_ID)
	{
		if (C_PurchaseCandidate_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_Alloc_ID, C_PurchaseCandidate_Alloc_ID);
	}

	@Override
	public int getC_PurchaseCandidate_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PurchaseCandidate_Alloc_ID);
	}

	@Override
	public de.metas.purchasecandidate.model.I_C_PurchaseCandidate getC_PurchaseCandidate()
	{
		return get_ValueAsPO(COLUMNNAME_C_PurchaseCandidate_ID, de.metas.purchasecandidate.model.I_C_PurchaseCandidate.class);
	}

	@Override
	public void setC_PurchaseCandidate(final de.metas.purchasecandidate.model.I_C_PurchaseCandidate C_PurchaseCandidate)
	{
		set_ValueFromPO(COLUMNNAME_C_PurchaseCandidate_ID, de.metas.purchasecandidate.model.I_C_PurchaseCandidate.class, C_PurchaseCandidate);
	}

	@Override
	public void setC_PurchaseCandidate_ID (final int C_PurchaseCandidate_ID)
	{
		if (C_PurchaseCandidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PurchaseCandidate_ID, C_PurchaseCandidate_ID);
	}

	@Override
	public int getC_PurchaseCandidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PurchaseCandidate_ID);
	}

	@Override
	public void setDatePromised (final @Nullable java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	@Override
	public java.sql.Timestamp getDatePromised() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DatePromised);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setRemotePurchaseOrderId (final @Nullable java.lang.String RemotePurchaseOrderId)
	{
		set_Value (COLUMNNAME_RemotePurchaseOrderId, RemotePurchaseOrderId);
	}

	@Override
	public java.lang.String getRemotePurchaseOrderId() 
	{
		return get_ValueAsString(COLUMNNAME_RemotePurchaseOrderId);
	}
}