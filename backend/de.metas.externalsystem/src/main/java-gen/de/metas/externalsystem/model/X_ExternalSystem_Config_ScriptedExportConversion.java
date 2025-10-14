/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// Generated Model - DO NOT CHANGE
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_ScriptedExportConversion
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_ScriptedExportConversion extends org.compiere.model.PO implements I_ExternalSystem_Config_ScriptedExportConversion, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2111617938L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_ScriptedExportConversion (final Properties ctx, final int ExternalSystem_Config_ScriptedExportConversion_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ScriptedExportConversion_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_ScriptedExportConversion (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Process_OutboundData_ID (final int AD_Process_OutboundData_ID)
	{
		if (AD_Process_OutboundData_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_OutboundData_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_OutboundData_ID, AD_Process_OutboundData_ID);
	}

	@Override
	public int getAD_Process_OutboundData_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_OutboundData_ID);
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
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
	/** Speditionsauftrag/Ladeliste = MST */
	public static final String DOCBASETYPE_SpeditionsauftragLadeliste = "MST";
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
	/** AnalysisReport = QMA */
	public static final String DOCBASETYPE_AnalysisReport = "QMA";
	@Override
	public void setDocBaseType (final @Nullable String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	@Override
	public String getDocBaseType() 
	{
		return get_ValueAsString(COLUMNNAME_DocBaseType);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystem_Config_ScriptedExportConversion_ID (final int ExternalSystem_Config_ScriptedExportConversion_ID)
	{
		if (ExternalSystem_Config_ScriptedExportConversion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID, ExternalSystem_Config_ScriptedExportConversion_ID);
	}

	@Override
	public int getExternalSystem_Config_ScriptedExportConversion_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID);
	}

	@Override
	public void setExternalSystemValue (final String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setOutboundHttpEP (final @Nullable String OutboundHttpEP)
	{
		set_Value (COLUMNNAME_OutboundHttpEP, OutboundHttpEP);
	}

	@Override
	public String getOutboundHttpEP() 
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpEP);
	}

	@Override
	public void setOutboundHttpMethod (final @Nullable String OutboundHttpMethod)
	{
		set_Value (COLUMNNAME_OutboundHttpMethod, OutboundHttpMethod);
	}

	@Override
	public String getOutboundHttpMethod() 
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpMethod);
	}

	@Override
	public void setOutboundHttpToken (final @Nullable String OutboundHttpToken)
	{
		set_Value (COLUMNNAME_OutboundHttpToken, OutboundHttpToken);
	}

	@Override
	public String getOutboundHttpToken() 
	{
		return get_ValueAsString(COLUMNNAME_OutboundHttpToken);
	}

	@Override
	public void setScriptIdentifier (final String ScriptIdentifier)
	{
		set_Value (COLUMNNAME_ScriptIdentifier, ScriptIdentifier);
	}

	@Override
	public String getScriptIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_ScriptIdentifier);
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

	@Override
	public void setWhereClause (final String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	@Override
	public String getWhereClause() 
	{
		return get_ValueAsString(COLUMNNAME_WhereClause);
	}
}