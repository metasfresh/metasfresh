// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_RelationType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_RelationType extends org.compiere.model.PO implements I_AD_RelationType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2036240777L;

    /** Standard Constructor */
    public X_AD_RelationType (final Properties ctx, final int AD_RelationType_ID, @Nullable final String trxName)
    {
      super (ctx, AD_RelationType_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_RelationType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Reference getAD_Reference_Source()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Source_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Source(final org.compiere.model.I_AD_Reference AD_Reference_Source)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Source_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Source);
	}

	@Override
	public void setAD_Reference_Source_ID (final int AD_Reference_Source_ID)
	{
		if (AD_Reference_Source_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Source_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Source_ID, AD_Reference_Source_ID);
	}

	@Override
	public int getAD_Reference_Source_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Source_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Target()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Target_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Target(final org.compiere.model.I_AD_Reference AD_Reference_Target)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Target_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Target);
	}

	@Override
	public void setAD_Reference_Target_ID (final int AD_Reference_Target_ID)
	{
		if (AD_Reference_Target_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Target_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Target_ID, AD_Reference_Target_ID);
	}

	@Override
	public int getAD_Reference_Target_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Target_ID);
	}

	@Override
	public void setAD_RelationType_ID (final int AD_RelationType_ID)
	{
		if (AD_RelationType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_RelationType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_RelationType_ID, AD_RelationType_ID);
	}

	@Override
	public int getAD_RelationType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_RelationType_ID);
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
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setInternalName (final @Nullable java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	@Override
	public java.lang.String getInternalName() 
	{
		return get_ValueAsString(COLUMNNAME_InternalName);
	}

	@Override
	public void setIsTableRecordIdTarget (final boolean IsTableRecordIdTarget)
	{
		set_Value (COLUMNNAME_IsTableRecordIdTarget, IsTableRecordIdTarget);
	}

	@Override
	public boolean isTableRecordIdTarget() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTableRecordIdTarget);
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

	/** 
	 * Role_Source AD_Reference_ID=53331
	 * Reference name: AD_RelationType Role
	 */
	public static final int ROLE_SOURCE_AD_Reference_ID=53331;
	/** Auftrag = Order */
	public static final String ROLE_SOURCE_Auftrag = "Order";
	/** Rechnung = Invoice */
	public static final String ROLE_SOURCE_Rechnung = "Invoice";
	/** Abo = Abo */
	public static final String ROLE_SOURCE_Abo = "Abo";
	/** Lieferung = Shipment */
	public static final String ROLE_SOURCE_Lieferung = "Shipment";
	/** Gutschrift Rechnung = Credit Memo */
	public static final String ROLE_SOURCE_GutschriftRechnung = "Credit Memo";
	/** Cockpit = Cockpit */
	public static final String ROLE_SOURCE_Cockpit = "Cockpit";
	/** Zahlung = Payment */
	public static final String ROLE_SOURCE_Zahlung = "Payment";
	/** Packstück = Package */
	public static final String ROLE_SOURCE_Packstueck = "Package";
	/** Lieferdispo = ShipmentSchedule */
	public static final String ROLE_SOURCE_Lieferdispo = "ShipmentSchedule";
	/** Prov-Abrechnung = ComCalc */
	public static final String ROLE_SOURCE_Prov_Abrechnung = "ComCalc";
	/** Prov-Korrektur = ComCorr */
	public static final String ROLE_SOURCE_Prov_Korrektur = "ComCorr";
	/** Auftragskandidat = OLCand */
	public static final String ROLE_SOURCE_Auftragskandidat = "OLCand";
	/** Auftragspos. = C_OrderLine */
	public static final String ROLE_SOURCE_Auftragspos = "C_OrderLine";
	/** Bestelldispo = PurchaseSchedule */
	public static final String ROLE_SOURCE_Bestelldispo = "PurchaseSchedule";
	/** Bestellung = PurchaseOrder */
	public static final String ROLE_SOURCE_Bestellung = "PurchaseOrder";
	/** Bestellung (offen) = PurchaseOrder_Current */
	public static final String ROLE_SOURCE_BestellungOffen = "PurchaseOrder_Current";
	/** Bestellung (hist.) = PurchaseOrder_Done */
	public static final String ROLE_SOURCE_BestellungHist = "PurchaseOrder_Done";
	/** Auftrag (offen) = Order_Current */
	public static final String ROLE_SOURCE_AuftragOffen = "Order_Current";
	/** Auftrag (hist.) = Order_hist */
	public static final String ROLE_SOURCE_AuftragHist = "Order_hist";
	/** Bedarf = ReqLine */
	public static final String ROLE_SOURCE_Bedarf = "ReqLine";
	/** Beziehungen Geschäftspartner = C_BP_Relation */
	public static final String ROLE_SOURCE_BeziehungenGeschaeftspartner = "C_BP_Relation";
	/** Zuordnung = C_AllocationLine */
	public static final String ROLE_SOURCE_Zuordnung = "C_AllocationLine";
	/** Bankauszug = C_BankStatement */
	public static final String ROLE_SOURCE_Bankauszug = "C_BankStatement";
	/** Eingangsrechnung = PO_Invoice */
	public static final String ROLE_SOURCE_Eingangsrechnung = "PO_Invoice";
	/** ESR Import = ESR_Import */
	public static final String ROLE_SOURCE_ESRImport = "ESR_Import";
	/** MD_Candidate_PRODUCTION = MD_Candidate_PRODUCTION */
	public static final String ROLE_SOURCE_MD_Candidate_PRODUCTION = "MD_Candidate_PRODUCTION";
	/** RefundInvoiceCandidate = RefundInvoiceCandidate */
	public static final String ROLE_SOURCE_RefundInvoiceCandidate = "RefundInvoiceCandidate";
	/** Effort sub-issues = Effort sub-issues */
	public static final String ROLE_SOURCE_EffortSub_Issues = "Effort sub-issues";
	/** Budget sub-issues = Budget sub-issues */
	public static final String ROLE_SOURCE_BudgetSub_Issues = "Budget sub-issues";
	/** Parent effort issue = Parent effort issue */
	public static final String ROLE_SOURCE_ParentEffortIssue = "Parent effort issue";
	/** Parent budget issue = Parent budget issue */
	public static final String ROLE_SOURCE_ParentBudgetIssue = "Parent budget issue";
	@Override
	public void setRole_Source (final @Nullable java.lang.String Role_Source)
	{
		set_Value (COLUMNNAME_Role_Source, Role_Source);
	}

	@Override
	public java.lang.String getRole_Source() 
	{
		return get_ValueAsString(COLUMNNAME_Role_Source);
	}

	/** 
	 * Role_Target AD_Reference_ID=53331
	 * Reference name: AD_RelationType Role
	 */
	public static final int ROLE_TARGET_AD_Reference_ID=53331;
	/** Auftrag = Order */
	public static final String ROLE_TARGET_Auftrag = "Order";
	/** Rechnung = Invoice */
	public static final String ROLE_TARGET_Rechnung = "Invoice";
	/** Abo = Abo */
	public static final String ROLE_TARGET_Abo = "Abo";
	/** Lieferung = Shipment */
	public static final String ROLE_TARGET_Lieferung = "Shipment";
	/** Gutschrift Rechnung = Credit Memo */
	public static final String ROLE_TARGET_GutschriftRechnung = "Credit Memo";
	/** Cockpit = Cockpit */
	public static final String ROLE_TARGET_Cockpit = "Cockpit";
	/** Zahlung = Payment */
	public static final String ROLE_TARGET_Zahlung = "Payment";
	/** Packstück = Package */
	public static final String ROLE_TARGET_Packstueck = "Package";
	/** Lieferdispo = ShipmentSchedule */
	public static final String ROLE_TARGET_Lieferdispo = "ShipmentSchedule";
	/** Prov-Abrechnung = ComCalc */
	public static final String ROLE_TARGET_Prov_Abrechnung = "ComCalc";
	/** Prov-Korrektur = ComCorr */
	public static final String ROLE_TARGET_Prov_Korrektur = "ComCorr";
	/** Auftragskandidat = OLCand */
	public static final String ROLE_TARGET_Auftragskandidat = "OLCand";
	/** Auftragspos. = C_OrderLine */
	public static final String ROLE_TARGET_Auftragspos = "C_OrderLine";
	/** Bestelldispo = PurchaseSchedule */
	public static final String ROLE_TARGET_Bestelldispo = "PurchaseSchedule";
	/** Bestellung = PurchaseOrder */
	public static final String ROLE_TARGET_Bestellung = "PurchaseOrder";
	/** Bestellung (offen) = PurchaseOrder_Current */
	public static final String ROLE_TARGET_BestellungOffen = "PurchaseOrder_Current";
	/** Bestellung (hist.) = PurchaseOrder_Done */
	public static final String ROLE_TARGET_BestellungHist = "PurchaseOrder_Done";
	/** Auftrag (offen) = Order_Current */
	public static final String ROLE_TARGET_AuftragOffen = "Order_Current";
	/** Auftrag (hist.) = Order_hist */
	public static final String ROLE_TARGET_AuftragHist = "Order_hist";
	/** Bedarf = ReqLine */
	public static final String ROLE_TARGET_Bedarf = "ReqLine";
	/** Beziehungen Geschäftspartner = C_BP_Relation */
	public static final String ROLE_TARGET_BeziehungenGeschaeftspartner = "C_BP_Relation";
	/** Zuordnung = C_AllocationLine */
	public static final String ROLE_TARGET_Zuordnung = "C_AllocationLine";
	/** Bankauszug = C_BankStatement */
	public static final String ROLE_TARGET_Bankauszug = "C_BankStatement";
	/** Eingangsrechnung = PO_Invoice */
	public static final String ROLE_TARGET_Eingangsrechnung = "PO_Invoice";
	/** ESR Import = ESR_Import */
	public static final String ROLE_TARGET_ESRImport = "ESR_Import";
	/** MD_Candidate_PRODUCTION = MD_Candidate_PRODUCTION */
	public static final String ROLE_TARGET_MD_Candidate_PRODUCTION = "MD_Candidate_PRODUCTION";
	/** RefundInvoiceCandidate = RefundInvoiceCandidate */
	public static final String ROLE_TARGET_RefundInvoiceCandidate = "RefundInvoiceCandidate";
	/** Effort sub-issues = Effort sub-issues */
	public static final String ROLE_TARGET_EffortSub_Issues = "Effort sub-issues";
	/** Budget sub-issues = Budget sub-issues */
	public static final String ROLE_TARGET_BudgetSub_Issues = "Budget sub-issues";
	/** Parent effort issue = Parent effort issue */
	public static final String ROLE_TARGET_ParentEffortIssue = "Parent effort issue";
	/** Parent budget issue = Parent budget issue */
	public static final String ROLE_TARGET_ParentBudgetIssue = "Parent budget issue";
	@Override
	public void setRole_Target (final @Nullable java.lang.String Role_Target)
	{
		set_Value (COLUMNNAME_Role_Target, Role_Target);
	}

	@Override
	public java.lang.String getRole_Target() 
	{
		return get_ValueAsString(COLUMNNAME_Role_Target);
	}
}