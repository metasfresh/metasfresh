// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_Approval_Request
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_WF_Approval_Request extends org.compiere.model.PO implements I_AD_WF_Approval_Request, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1526813161L;

    /** Standard Constructor */
    public X_AD_WF_Approval_Request (final Properties ctx, final int AD_WF_Approval_Request_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_Approval_Request_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_Approval_Request (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setAD_WF_Activity_ID (final int AD_WF_Activity_ID)
	{
		if (AD_WF_Activity_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Activity_ID, AD_WF_Activity_ID);
	}

	@Override
	public int getAD_WF_Activity_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Activity_ID);
	}

	@Override
	public void setAD_WF_Approval_Request_ID (final int AD_WF_Approval_Request_ID)
	{
		if (AD_WF_Approval_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Approval_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Approval_Request_ID, AD_WF_Approval_Request_ID);
	}

	@Override
	public int getAD_WF_Approval_Request_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Approval_Request_ID);
	}

	@Override
	public void setAD_WF_Process_ID (final int AD_WF_Process_ID)
	{
		if (AD_WF_Process_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Process_ID, AD_WF_Process_ID);
	}

	@Override
	public int getAD_WF_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Process_ID);
	}

	@Override
	public void setDateRequest (final java.sql.Timestamp DateRequest)
	{
		set_ValueNoCheck (COLUMNNAME_DateRequest, DateRequest);
	}

	@Override
	public java.sql.Timestamp getDateRequest() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateRequest);
	}

	@Override
	public void setDateResponse (final @Nullable java.sql.Timestamp DateResponse)
	{
		set_Value (COLUMNNAME_DateResponse, DateResponse);
	}

	@Override
	public java.sql.Timestamp getDateResponse() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateResponse);
	}

	/** 
	 * DocBaseType AD_Reference_ID=183
	 * Reference name: C_DocType DocBaseType
	 */
	public static final int DOCBASETYPE_AD_Reference_ID=183;
	/** GLJournal = GLJ */
	public static final String DOCBASETYPE_GLJournal = "GLJ";
	/** GLDocument = GLD */
	public static final String DOCBASETYPE_GLDocument = "GLD";
	/** APInvoice = API */
	public static final String DOCBASETYPE_APInvoice = "API";
	/** APPayment = APP */
	public static final String DOCBASETYPE_APPayment = "APP";
	/** ARInvoice = ARI */
	public static final String DOCBASETYPE_ARInvoice = "ARI";
	/** ARReceipt = ARR */
	public static final String DOCBASETYPE_ARReceipt = "ARR";
	/** SalesOrder = SOO */
	public static final String DOCBASETYPE_SalesOrder = "SOO";
	/** ARProFormaInvoice = ARF */
	public static final String DOCBASETYPE_ARProFormaInvoice = "ARF";
	/** MaterialDelivery = MMS */
	public static final String DOCBASETYPE_MaterialDelivery = "MMS";
	/** MaterialReceipt = MMR */
	public static final String DOCBASETYPE_MaterialReceipt = "MMR";
	/** MaterialMovement = MMM */
	public static final String DOCBASETYPE_MaterialMovement = "MMM";
	/** PurchaseOrder = POO */
	public static final String DOCBASETYPE_PurchaseOrder = "POO";
	/** PurchaseRequisition = POR */
	public static final String DOCBASETYPE_PurchaseRequisition = "POR";
	/** MaterialPhysicalInventory = MMI */
	public static final String DOCBASETYPE_MaterialPhysicalInventory = "MMI";
	/** APCreditMemo = APC */
	public static final String DOCBASETYPE_APCreditMemo = "APC";
	/** ARCreditMemo = ARC */
	public static final String DOCBASETYPE_ARCreditMemo = "ARC";
	/** BankStatement = CMB */
	public static final String DOCBASETYPE_BankStatement = "CMB";
	/** CashJournal = CMC */
	public static final String DOCBASETYPE_CashJournal = "CMC";
	/** PaymentAllocation = CMA */
	public static final String DOCBASETYPE_PaymentAllocation = "CMA";
	/** MatchInvoice = MXI */
	public static final String DOCBASETYPE_MatchInvoice = "MXI";
	/** MatchPO = MXP */
	public static final String DOCBASETYPE_MatchPO = "MXP";
	/** ProjectIssue = PJI */
	public static final String DOCBASETYPE_ProjectIssue = "PJI";
	/** MaintenanceOrder = MOF */
	public static final String DOCBASETYPE_MaintenanceOrder = "MOF";
	/** ManufacturingOrder = MOP */
	public static final String DOCBASETYPE_ManufacturingOrder = "MOP";
	/** QualityOrder = MQO */
	public static final String DOCBASETYPE_QualityOrder = "MQO";
	/** Payroll = HRP */
	public static final String DOCBASETYPE_Payroll = "HRP";
	/** DistributionOrder = DOO */
	public static final String DOCBASETYPE_DistributionOrder = "DOO";
	/** ManufacturingCostCollector = MCC */
	public static final String DOCBASETYPE_ManufacturingCostCollector = "MCC";
	/** Gehaltsrechnung (Angestellter) = AEI */
	public static final String DOCBASETYPE_GehaltsrechnungAngestellter = "AEI";
	/** Interne Rechnung (Lieferant) = AVI */
	public static final String DOCBASETYPE_InterneRechnungLieferant = "AVI";
	/** ShipperTransportation = MST */
	public static final String DOCBASETYPE_ShipperTransportation = "MST";
	/** CustomerContract = CON */
	public static final String DOCBASETYPE_CustomerContract = "CON";
	/** DunningDoc = DUN */
	public static final String DOCBASETYPE_DunningDoc = "DUN";
	/** Shipment Declaration = SDD */
	public static final String DOCBASETYPE_ShipmentDeclaration = "SDD";
	/** Shipment Declaration Correction = SDC */
	public static final String DOCBASETYPE_ShipmentDeclarationCorrection = "SDC";
	/** Customs Invoice = CUI */
	public static final String DOCBASETYPE_CustomsInvoice = "CUI";
	/** ServiceRepairOrder = MRO */
	public static final String DOCBASETYPE_ServiceRepairOrder = "MRO";
	/** Remittance Advice = RMA */
	public static final String DOCBASETYPE_RemittanceAdvice = "RMA";
	/** BOM & Formula = BOM */
	public static final String DOCBASETYPE_BOMFormula = "BOM";
	/** Cost Revaluation = CRD */
	public static final String DOCBASETYPE_CostRevaluation = "CRD";
	@Override
	public void setDocBaseType (final @Nullable java.lang.String DocBaseType)
	{
		set_ValueNoCheck (COLUMNNAME_DocBaseType, DocBaseType);
	}

	@Override
	public java.lang.String getDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_DocBaseType);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	/** 
	 * Status AD_Reference_ID=541819
	 * Reference name: AD_WF_Approval_Request_Status
	 */
	public static final int STATUS_AD_Reference_ID=541819;
	/** Pending = P */
	public static final String STATUS_Pending = "P";
	/** Approved = A */
	public static final String STATUS_Approved = "A";
	/** Rejected = R */
	public static final String STATUS_Rejected = "R";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}