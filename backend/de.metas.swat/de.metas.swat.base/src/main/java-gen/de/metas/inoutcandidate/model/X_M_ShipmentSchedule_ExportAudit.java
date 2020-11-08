/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule_ExportAudit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule_ExportAudit extends org.compiere.model.PO implements I_M_ShipmentSchedule_ExportAudit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 6876153L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_ExportAudit (Properties ctx, int M_ShipmentSchedule_ExportAudit_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_ExportAudit_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_ExportAudit (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setExportSequenceNumber (int ExportSequenceNumber)
	{
		set_Value (COLUMNNAME_ExportSequenceNumber, Integer.valueOf(ExportSequenceNumber));
	}

	@Override
	public int getExportSequenceNumber() 
	{
		return get_ValueAsInt(COLUMNNAME_ExportSequenceNumber);
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
	public void setExportStatus (java.lang.String ExportStatus)
	{

		set_Value (COLUMNNAME_ExportStatus, ExportStatus);
	}

	@Override
	public java.lang.String getExportStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExportStatus);
	}

	@Override
	public void setForwardedData (java.lang.String ForwardedData)
	{
		set_Value (COLUMNNAME_ForwardedData, ForwardedData);
	}

	@Override
	public java.lang.String getForwardedData() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ForwardedData);
	}

	@Override
	public void setM_ShipmentSchedule_ExportAudit_ID (int M_ShipmentSchedule_ExportAudit_ID)
	{
		if (M_ShipmentSchedule_ExportAudit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID, Integer.valueOf(M_ShipmentSchedule_ExportAudit_ID));
	}

	@Override
	public int getM_ShipmentSchedule_ExportAudit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID);
	}

	@Override
	public void setTransactionIdAPI (java.lang.String TransactionIdAPI)
	{
		set_Value (COLUMNNAME_TransactionIdAPI, TransactionIdAPI);
	}

	@Override
	public java.lang.String getTransactionIdAPI() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TransactionIdAPI);
	}
}