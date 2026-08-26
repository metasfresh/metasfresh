// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Delivery_Planning_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Delivery_Planning_Alloc extends org.compiere.model.PO implements I_M_Delivery_Planning_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -893791322L;

    /** Standard Constructor */
    public X_M_Delivery_Planning_Alloc (final Properties ctx, final int M_Delivery_Planning_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, M_Delivery_Planning_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Delivery_Planning_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setLineNo (final int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, LineNo);
	}

	@Override
	public int getLineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_LineNo);
	}

	@Override
	public void setM_Delivery_Planning_Alloc_ID (final int M_Delivery_Planning_Alloc_ID)
	{
		if (M_Delivery_Planning_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Delivery_Planning_Alloc_ID, M_Delivery_Planning_Alloc_ID);
	}

	@Override
	public int getM_Delivery_Planning_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_Alloc_ID);
	}

	@Override
	public void setM_Delivery_Planning_ID (final int M_Delivery_Planning_ID)
	{
		if (M_Delivery_Planning_ID < 1) 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, null);
		else 
			set_Value (COLUMNNAME_M_Delivery_Planning_ID, M_Delivery_Planning_ID);
	}

	@Override
	public int getM_Delivery_Planning_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Delivery_Planning_ID);
	}

	@Override
	public void setM_ShipperTransportation_ID (final int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, M_ShipperTransportation_ID);
	}

	@Override
	public int getM_ShipperTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipperTransportation_ID);
	}

	@Override
	public void setM_ShippingPackage_ID (final int M_ShippingPackage_ID)
	{
		if (M_ShippingPackage_ID < 1) 
			set_Value (COLUMNNAME_M_ShippingPackage_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShippingPackage_ID, M_ShippingPackage_ID);
	}

	@Override
	public int getM_ShippingPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShippingPackage_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}