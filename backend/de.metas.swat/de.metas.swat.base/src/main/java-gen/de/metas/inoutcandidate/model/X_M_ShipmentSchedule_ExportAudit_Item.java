// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ShipmentSchedule_ExportAudit_Item
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShipmentSchedule_ExportAudit_Item extends org.compiere.model.PO implements I_M_ShipmentSchedule_ExportAudit_Item, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1952174665L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_ExportAudit_Item (final Properties ctx, final int M_ShipmentSchedule_ExportAudit_Item_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShipmentSchedule_ExportAudit_Item_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_ExportAudit_Item (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setExportSequenceNumber (final @Nullable BigDecimal ExportSequenceNumber)
	{
		throw new IllegalArgumentException ("ExportSequenceNumber is virtual column");	}

	@Override
	public BigDecimal getExportSequenceNumber() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ExportSequenceNumber);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ExportStatus AD_Reference_ID=541161
	 * Reference name: API_ExportStatus
	 */
	public static final int EXPORTSTATUS_AD_Reference_ID=541161;
	/** PENDING = PENDING */
	public static final String EXPORTSTATUS_PENDING = "PENDING";
	/** EXPORTED = EXPORTED */
	public static final String EXPORTSTATUS_EXPORTED = "EXPORTED";
	/** EXPORTED_AND_FORWARDED = EXPORTED_AND_FORWARDED */
	public static final String EXPORTSTATUS_EXPORTED_AND_FORWARDED = "EXPORTED_AND_FORWARDED";
	/** EXPORTED_FORWARD_ERROR = EXPORTED_FORWARD_ERROR */
	public static final String EXPORTSTATUS_EXPORTED_FORWARD_ERROR = "EXPORTED_FORWARD_ERROR";
	/** EXPORT_ERROR = EXPORT_ERROR */
	public static final String EXPORTSTATUS_EXPORT_ERROR = "EXPORT_ERROR";
	/** DONT_EXPORT = DONT_EXPORT */
	public static final String EXPORTSTATUS_DONT_EXPORT = "DONT_EXPORT";
	@Override
	public void setExportStatus (final @Nullable java.lang.String ExportStatus)
	{
		set_Value (COLUMNNAME_ExportStatus, ExportStatus);
	}

	@Override
	public java.lang.String getExportStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ExportStatus);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit getM_ShipmentSchedule_ExportAudit()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit.class);
	}

	@Override
	public void setM_ShipmentSchedule_ExportAudit(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit M_ShipmentSchedule_ExportAudit)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit.class, M_ShipmentSchedule_ExportAudit);
	}

	@Override
	public void setM_ShipmentSchedule_ExportAudit_ID (final int M_ShipmentSchedule_ExportAudit_ID)
	{
		if (M_ShipmentSchedule_ExportAudit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID, M_ShipmentSchedule_ExportAudit_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ExportAudit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID);
	}

	@Override
	public void setM_ShipmentSchedule_ExportAudit_Item_ID (final int M_ShipmentSchedule_ExportAudit_Item_ID)
	{
		if (M_ShipmentSchedule_ExportAudit_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ExportAudit_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ExportAudit_Item_ID, M_ShipmentSchedule_ExportAudit_Item_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ExportAudit_Item_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ExportAudit_Item_ID);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public void setM_ShipmentSchedule(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class, M_ShipmentSchedule);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setTransactionIdAPI (final @Nullable java.lang.String TransactionIdAPI)
	{
		throw new IllegalArgumentException ("TransactionIdAPI is virtual column");	}

	@Override
	public java.lang.String getTransactionIdAPI() 
	{
		return get_ValueAsString(COLUMNNAME_TransactionIdAPI);
	}
}