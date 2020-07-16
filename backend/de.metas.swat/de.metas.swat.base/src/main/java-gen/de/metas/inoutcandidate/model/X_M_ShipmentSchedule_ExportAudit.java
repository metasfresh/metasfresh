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

	private static final long serialVersionUID = 1555114912L;

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