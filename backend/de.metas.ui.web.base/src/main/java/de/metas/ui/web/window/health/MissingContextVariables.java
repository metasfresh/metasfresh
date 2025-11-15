package de.metas.ui.web.window.health;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;
import de.metas.ui.web.window.health.json.JsonContextVariablesResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Map;

class MissingContextVariables
{
	private static final ImmutableMap<String, String> KNOWN_MISSING_CONTEXT_VARIABLES_DEFAULTS = ImmutableMap.<String, String>builder()
			.put("AD_Process/165.IsOneInstanceOnly.DisplayLogic", "IsServerProcess")
			.put("AD_Process/165.IsOneInstanceOnly.MandatoryLogic", "IsServerProcess")
			.put("AD_Sequence/112.DateColumn.MandatoryLogic", "StartNewYear")
			.put("A_Asset/251.A_Asset_Change/53158.AD_User_ID.DisplayLogic", "Ad_User_ID")
			.put("A_Asset/251.A_Asset_Change/53158.C_BPartner_ID.DisplayLogic", "C_Bpartner_ID")
			.put("A_Asset/251.A_Asset_Change/53158.SerNo.DisplayLogic", "Serno")
			.put("A_Asset/251.A_Asset_Change/53158.VersionNo.DisplayLogic", "Versionno")
			.put("C_Async_Batch_Type/540248.NotificationType.DisplayLogic", "IsSendNotification")
			.put("C_BPartner/123.C_BPartner_Location/222.Previous_ID.DisplayLogic", "ValidFrom")
			.put("C_BPartner/123.C_BPartner_Location/222.Previous_ID.MandatoryLogic", "ValidFrom")
			.put("C_BPartner/123.C_BPartner_Location/548422.Previous_ID.DisplayLogic", "ValidFrom")
			.put("C_BPartner/123.C_BPartner_Location/548422.Previous_ID.MandatoryLogic", "ValidFrom")
			.put("C_BankStatementLine_Ref/540355.C_Payment_ID.DisplayLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540355.C_Payment_ID.MandatoryLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540868.C_Payment_ID.DisplayLogic", "IsMultiplePayment")
			.put("C_BankStatementLine_Ref/540868.C_Payment_ID.MandatoryLogic", "IsMultiplePayment")
			.put("C_Country_Trl/540447.RegionName.DisplayLogic", "HasRegion")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540860.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540861.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540862.Date_Reported.ReadonlyLogic", "IsReadyForInvoicing")
			.put("C_HierarchyCommissionSettings/540742.IsSubtractLowerLevelCommissionFromBase.ReadonlyLogic", "IsConditionsProcessed")
			.put("C_Invoice/167.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/167.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Invoice/167.CreateAdjustmentCharge.DisplayLogic", "Ref_AdjustmentCharge_ID")
			.put("C_Invoice/167.CreateCreditMemo.DisplayLogic", "Ref_CreditMemo_ID")
			.put("C_Invoice/167.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_Invoice/183.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/183.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Invoice/183.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_Invoice/262.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Invoice/262.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/143.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/143.C_OrderLine/187.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/143.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/143.IsEdiEnabled.ReadonlyLogic", "IsEdiDesadvRecipient")
			.put("C_Order/181.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/181.C_OrderLine/293.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/181.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/181.IsEdiEnabled.ReadonlyLogic", "IsEdiDesadvRecipient")
			.put("C_Order/540072.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("C_Order/540072.C_OrderLine/540212.AD_OrgTrx_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Activity_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Campaign_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Charge_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Project_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Tax_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_Tax_ID.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_UOM_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.C_UOM_ID.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.DateOrdered.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.DateOrdered.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.DatePromised.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.Discount.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.FreightAmt.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.FreightAmt.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.M_AttributeSetInstance_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.M_Shipper_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.M_Warehouse_ID.DisplayLogic", "DirectShip")
			.put("C_Order/540072.C_OrderLine/540212.OrderDiscount.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceActual.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceActual.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceList.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.PriceList.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyDelivered.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyDelivered.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyInvoiced.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyInvoiced.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyLostSales.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyLostSales.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyOrdered.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyOrdered.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyReserved.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.QtyReserved.MandatoryLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.S_ResourceAssignment_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.User1_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.C_OrderLine/540212.User2_ID.DisplayLogic", "IsComment")
			.put("C_Order/540072.ChargeAmt.DisplayLogic", "HasCharges")
			.put("C_Order/540072.IncotermLocation.DisplayLogic", "Incoterm")
			.put("C_OrderLine/540019.C_Flatrate_Term_ID.MandatoryLogic", "DocSubType")
			.put("ESR_Import/540159.ESR_ImportLine/540443.C_BPartner_ID.ReadonlyLogic", "ErrorMsg")
			.put("Fact_Acct_Transactions_View/162.AD_OrgTrx_ID.DisplayLogic", "$Element_TO")
			.put("M_AttributeValue/540825.IsActive.ReadonlyLogic", "IsReadOnlyValues")
			.put("M_AttributeValue/540825.Name.ReadonlyLogic", "IsReadOnlyValues")
			.put("M_DiscountSchemaBreak/540612.DisplayLogic", "DiscountType")
			.put("M_DiscountSchemaBreak/540612.M_DiscountSchemaBreak_ID.DisplayLogic", "DiscountType")
			.put("M_DiscountSchemaBreak/540612.M_DiscountSchemaBreak_ID.MandatoryLogic", "DiscountType")
			.put("M_InOut/169.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/169.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/169.IncotermLocation.DisplayLogic", "Incoterm")
			.put("M_InOut/184.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/184.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/184.IncotermLocation.DisplayLogic", "Incoterm")
			.put("M_InOut/53097.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/53097.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/53098.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/53098.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/540322.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/540322.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/540323.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/540323.ChargeAmt.DisplayLogic", "HasCharges")
			.put("M_InOut/541014.C_Charge_ID.DisplayLogic", "HasCharges")
			.put("M_InOut/541014.ChargeAmt.DisplayLogic", "HasCharges")
			.put("PP_Order/53009.PP_Order_Node/53036.IsMilestone.DisplayLogic", "WorkflowType")
			.put("PP_Order/53009.PP_Order_Node/53036.IsSubcontracting.DisplayLogic", "WorkflowType")
			.put("PP_Order/53009.PP_Order_Node/53036.S_Resource_ID.DisplayLogic", "WorkflowType")
			.put("PP_Product_BOMLine/540720.Feature.DisplayLogic", "BOMType")
			.put("PP_Product_BOMLine/540720.Forecast.DisplayLogic", "BOMUse")
			.put("PP_Product_Planning/540750.MaxManufacturedQtyPerOrderDispo_UOM_ID.MandatoryLogic", "MaxManufacturedQtyPerOrder")
			.put("S_Resource/236.M_Product/417.S_ExpenseType_ID.DisplayLogic", "ProductTypeType")
			.build();

	private static final ImmutableMap<String, String> KNOWN_MISSING_CONTEXT_VARIABLES_IN_LOOKUPS = ImmutableMap.<String, String>builder()
			.put("AD_Find/221.AD_Column_ID.LookupDescriptor", "Find_Table_ID")
			.put("AD_User/293.C_Order/564.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("AD_User/293.C_Order/564.HandOver_User_ID.LookupDescriptor", "HandOver_Partner_Override_ID")
			.put("AD_WF_Node/541047.AD_Column_ID.LookupDescriptor", "AD_Table_ID")
			.put("AD_WF_NodeNext/540339.AD_WF_Next_ID.LookupDescriptor", "AD_Workflow_ID")
			.put("C_BP_BankAccount/540337.C_BP_BankAccount_InvoiceAutoAllocateRule/543994.C_DocTypeInvoice_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BP_Group/192.C_BPartner/778.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/123.C_BPartner/223.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/123.C_BPartner/224.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/123.C_BPartner/541154.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/123.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/176.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/242.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/254.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/291.C_Order/551.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_BPartner/291.C_Order/551.HandOver_User_ID.LookupDescriptor", "HandOver_Partner_Override_ID")
			.put("C_BPartner/291.M_InOut/552.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_BPartner/291.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/336.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/53033.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540354.C_BPartner/540845.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540354.C_BPartner/540846.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540354.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540366.C_Flatrate_Term/540876.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_BPartner/540366.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540409.C_BPartner/541014.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540409.C_BPartner/541015.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540409.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner/540676.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_BPartner_QuickInput/540327.C_DocTypeTarget_ID.LookupDescriptor", "IsSOTrx")
			.put("C_DunningDoc/540155.C_DunningDoc_Line/540431.C_DunningLevel_ID.LookupDescriptor", "C_DunningRun_ID,C_Dunning_ID")
			.put("C_Flatrate_Data/540112.C_Flatrate_Term/540340.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Flatrate_Data/540112.C_Flatrate_Term/544605.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540860.C_Period_ID.LookupDescriptor", "C_Calendar_Invoicing_ID")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540860.C_UOM_ID.LookupDescriptor", "UOMType")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540861.C_Period_ID.LookupDescriptor", "C_Calendar_Invoicing_ID")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540861.C_UOM_ID.LookupDescriptor", "UOMType")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540862.C_Period_ID.LookupDescriptor", "C_Calendar_Invoicing_ID")
			.put("C_Flatrate_Term/540359.C_Flatrate_DataEntry/540862.C_UOM_ID.LookupDescriptor", "UOMType")
			.put("C_Flatrate_Term/540359.C_SubscriptionProgress/540880.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Flatrate_Term/540359.C_SubscriptionProgress/540886.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Flatrate_Term/540359.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Invoice_Candidate/540092.M_PricingSystem_ID.LookupDescriptor", "C_BPartner_Location_ID")
			.put("C_Invoice_Candidate/540093.M_PricingSystem_ID.LookupDescriptor", "C_BPartner_Location_ID")
			.put("C_Invoice_Candidate/540126.M_PricingSystem_ID.LookupDescriptor", "C_BPartner_Location_ID")
			.put("C_Invoice_Candidate/540983.M_PricingSystem_ID.LookupDescriptor", "C_BPartner_Location_ID")
			.put("C_OLCand/540095.Bill_BPartner_ID.LookupDescriptor", "IsSOTrx")
			.put("C_OLCand/540127.Bill_BPartner_ID.LookupDescriptor", "IsSOTrx")
			.put("C_OLCand/541952.Bill_BPartner_ID.LookupDescriptor", "IsSOTrx")
			.put("C_OLCandProcessor/540079.M_Warehouse_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Order/143.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Order/143.HandOver_User_ID.LookupDescriptor", "HandOver_Partner_Override_ID")
			.put("C_Order/181.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Order/181.HandOver_User_ID.LookupDescriptor", "HandOver_Partner_Override_ID")
			.put("C_Order/540071.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Order/540071.HandOver_User_ID.LookupDescriptor", "HandOver_Partner_Override_ID")
			.put("C_Order/540072.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("C_Order/540072.HandOver_User_ID.LookupDescriptor", "HandOver_Partner_Override_ID")
			.put("C_OrderLine/540019.C_Charge_ID.LookupDescriptor", "C_DocTypeTarget_ID")
			.put("C_OrderLine/540019.C_Flatrate_Conditions_ID.LookupDescriptor", "IsSOTrx")
			.put("C_OrderLine/540019.M_HU_PI_Item_Product_ID.LookupDescriptor", "M_PriceList_ID")
			.put("C_OrderLine/540019.M_Product_ID.LookupDescriptor", "M_PriceList_ID")
			.put("C_OrderLine/540019.M_Warehouse_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Phonecall_Schedule/540607.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Project/130.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Project/286.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Project/540668.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Project/540680.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_Project/541015.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_RecurrentPayment/540030.C_RecurrentPaymentLine/540118.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_RfQ/315.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_SalesRegion/152.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("C_SubscriptionProgress/540361.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("DD_Order_Candidate/541807.M_HU_PI_Item_Product_ID.LookupDescriptor", "DD_Order_ID")
			.put("DD_Order_Candidate/541807.M_LocatorTo_ID.LookupDescriptor", "M_Warehouse_To_ID")
			.put("EDI_Desadv/540256.M_InOut/540664.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_DiscountSchema/233.DiscountType.LookupDescriptor", "IsSOTrx")
			.put("M_DiscountSchema/337.DiscountType.LookupDescriptor", "IsSOTrx")
			.put("M_DiscountSchema/540433.DiscountType.LookupDescriptor", "IsSOTrx")
			.put("M_ForecastLine/541900.C_Period_ID.LookupDescriptor", "C_Year_ID")
			.put("M_ForecastLine/541900.M_HU_PI_Item_Product_ID.LookupDescriptor", "M_PriceList_ID")
			.put("M_InOut/169.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InOut/184.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InOut/53097.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InOut/53098.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InOut/540322.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InOut/540323.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InOut/541014.DropShip_User_ID.LookupDescriptor", "DropShip_BPartner_Override_ID")
			.put("M_InventoryLine/540458.C_Charge_ID.LookupDescriptor", "C_DocType_ID")
			.put("M_InventoryLine/540458.M_HU_PI_Item_Product_ID.LookupDescriptor", "C_BPartner_ID,MovementDate")
			.put("M_InventoryLine/540458.M_Locator_ID.LookupDescriptor", "M_Warehouse_ID")
			.put("M_Material_Tracking/540226.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("M_Movement/170.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("M_Product/140.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("M_Product/344.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("M_Product/53010.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("M_Product/540410.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("M_Product_Category/144.M_Product/407.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("PP_Cost_Collector/53011.PP_Order_Node_ID.LookupDescriptor", "PP_Order_Workflow_ID")
			.put("PP_Cost_Collector/53014.PP_Order_Node_ID.LookupDescriptor", "PP_Order_Workflow_ID")
			.put("PP_Order_Candidate/541316.M_HU_PI_Item_Product_ID.LookupDescriptor", "C_BPartner_ID,M_PriceList_ID")
			.put("PP_Order_Candidate/541756.M_HU_PI_Item_Product_ID.LookupDescriptor", "C_BPartner_ID,M_PriceList_ID")
			.put("RV_R_Group_Prospect/540013.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("S_ExpenseType/234.M_Product/411.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("S_Resource/236.M_Product/417.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.put("W_Store/350.SalesRep_ID.LookupDescriptor", "IsSOTrx")
			.build();

	private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

	@NonNull private final ImmutableSetMultimap<ContextPath, String> all;
	@NonNull private final TreeMultimap<ContextPath, String> reportedMissingButNotUsed;
	@NonNull private final TreeMultimap<ContextPath, String> foundMissing = TreeMultimap.create();
	@NonNull private final TreeMultimap<ContextPath, String> reportedMissingButExists = TreeMultimap.create();
	@Nullable @Setter private AdWindowIdSelection adWindowIdsInScope;

	@Builder
	private MissingContextVariables(
			@Nullable final Map<String, String> knownMissing)
	{
		final HashMultimap<ContextPath, String> all = HashMultimap.create();
		addAll(all, KNOWN_MISSING_CONTEXT_VARIABLES_DEFAULTS);
		addAll(all, KNOWN_MISSING_CONTEXT_VARIABLES_IN_LOOKUPS);
		addAll(all, knownMissing);
		this.all = ImmutableSetMultimap.copyOf(all);
		
		this.reportedMissingButNotUsed = TreeMultimap.create(this.all);
	}

	private static void addAll(final HashMultimap<ContextPath, String> target, @Nullable Map<String, String> source)
	{
		if (source == null || source.isEmpty()) {return;}

		source.forEach((pathStr, contextVariables) -> {
			final ContextPath path = ContextPath.ofJson(pathStr);
			COMMA_SPLITTER.splitToList(contextVariables)
					.forEach(missingContextVariable -> target.put(path, missingContextVariable));
		});
	}

	public void recordContextVariableUsed(final ContextPath path, final String contextVariable, boolean wasFound)
	{
		reportedMissingButNotUsed.remove(path, contextVariable);

		final boolean isKnownAsMissing = isKnownAsMissing(path, contextVariable);
		if (wasFound)
		{
			if (isKnownAsMissing)
			{
				reportedMissingButExists.put(path, contextVariable);
			}
		}
		else // not found
		{
			if (!isKnownAsMissing)
			{
				foundMissing.put(path, contextVariable);
			}
		}
	}

	public boolean isKnownAsMissing(final ContextPath path, String contextVariable)
	{
		return all.containsEntry(path, contextVariable);
	}

	public JsonContextVariablesResponse toJsonContextVariablesResponse()
	{
		return JsonContextVariablesResponse.builder()
				.missing(toKeyAndCommaSeparatedValues(foundMissing, adWindowIdsInScope))
				.reportedMissingButNotUsed(toKeyAndCommaSeparatedValues(reportedMissingButNotUsed, adWindowIdsInScope))
				.reportedMissingButExists(toKeyAndCommaSeparatedValues(reportedMissingButExists, adWindowIdsInScope))
				.build();
	}

	private static Map<String, String> toKeyAndCommaSeparatedValues(
			@NonNull final SetMultimap<ContextPath, String> multimap,
			@Nullable final AdWindowIdSelection adWindowIdsInScope)
	{
		final ImmutableMap.Builder<String, String> result = ImmutableMap.builder();
		multimap.asMap().forEach((path, values) -> {
			if (adWindowIdsInScope != null && !adWindowIdsInScope.contains(path))
			{
				return;
			}

			result.put(path.toJson(), Joiner.on(',').join(values));
		});
		return result.build();
	}

}
