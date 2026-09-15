-- Migration: Set InternalName for all active AD_RelationType records that don't have one
-- Purpose: Enables language-independent E2E test selectors using data-cy="reference-{InternalName}"
-- Naming Convention: {SourceTable}_to_{TargetTable}[_{Qualifier}]
-- Qualifiers: _SO (Sales), _PO (Purchase), _Counter, etc.

-- =====================================================
-- Core Document Relations (Orders, Invoices, InOuts)
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_to_C_OLCand' WHERE AD_RelationType_ID = 540155 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_OLCand_to_C_Order' WHERE AD_RelationType_ID = 540156 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_C_Order_SO' WHERE AD_RelationType_ID = 540157 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_C_Order_PO' WHERE AD_RelationType_ID = 540158 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_to_M_InOut_SO' WHERE AD_RelationType_ID = 540159 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_to_C_Invoice_SO' WHERE AD_RelationType_ID = 540160 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_to_C_Invoice_PO' WHERE AD_RelationType_ID = 540161 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_C_Order_SO' WHERE AD_RelationType_ID = 540162 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_C_Order_PO' WHERE AD_RelationType_ID = 540163 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_C_Order_PO' WHERE AD_RelationType_ID = 540164 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_PO_to_C_Order_SO' WHERE AD_RelationType_ID = 540165 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_M_InOut_SO' WHERE AD_RelationType_ID = 540166 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_M_InOut_PO' WHERE AD_RelationType_ID = 540167 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_C_Invoice_SO' WHERE AD_RelationType_ID = 540168 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_C_Invoice_PO' WHERE AD_RelationType_ID = 540169 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Shipment & Receipt Schedules
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'M_ShipmentSchedule_to_M_InOut' WHERE AD_RelationType_ID = 540115 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_M_ShipmentSchedule' WHERE AD_RelationType_ID = 540118 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Invoice Candidates
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_C_Invoice_Candidate_SO' WHERE AD_RelationType_ID = 540119 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_C_Invoice_Candidate_PO' WHERE AD_RelationType_ID = 540266 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Inventory_to_C_Invoice_Candidate' WHERE AD_RelationType_ID = 540182 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_Candidate_to_M_Inventory' WHERE AD_RelationType_ID = 540183 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_FlatRate_Term_to_C_Invoice_Candidate' WHERE AD_RelationType_ID = 540190 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_Candidate_to_C_FlatRate_Term' WHERE AD_RelationType_ID = 540194 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_Candidate_to_Refund' WHERE AD_RelationType_ID = 540204 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_Candidate_to_C_Commission_Deed' WHERE AD_RelationType_ID = 540291 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Counter Documents (Inter-Org)
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_C_Order_PO_Counter' WHERE AD_RelationType_ID = 540348 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_PO_to_C_Order_SO_Counter' WHERE AD_RelationType_ID = 540349 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_PO_to_M_InOut_SO_Counter' WHERE AD_RelationType_ID = 540350 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_SO_to_M_InOut_PO_Counter' WHERE AD_RelationType_ID = 540351 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_SO_to_C_Invoice_PO_Counter' WHERE AD_RelationType_ID = 540352 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_PO_to_C_Invoice_SO_Counter' WHERE AD_RelationType_ID = 540353 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Referenced Invoices
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_Ref_C_Invoice_SO' WHERE AD_RelationType_ID = 540184 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Ref_C_Invoice_to_C_Invoice_SO' WHERE AD_RelationType_ID = 540185 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_Ref_C_Invoice_PO' WHERE AD_RelationType_ID = 540186 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Ref_C_Invoice_to_C_Invoice_PO' WHERE AD_RelationType_ID = 540187 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_SO_to_Ref_C_Invoice_PO' WHERE AD_RelationType_ID = 540330 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Ref_C_Invoice_PO_to_C_Invoice_SO' WHERE AD_RelationType_ID = 540331 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Projects
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Invoice_SO_to_C_Project' WHERE AD_RelationType_ID = 540252 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_SO_to_C_Project' WHERE AD_RelationType_ID = 540253 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_C_Project' WHERE AD_RelationType_ID = 540254 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Project_to_M_InOut_SO' WHERE AD_RelationType_ID = 540256 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Project_to_C_Invoice_SO' WHERE AD_RelationType_ID = 540257 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Fact_Acct_to_C_Project' WHERE AD_RelationType_ID = 540258 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Project_to_Fact_Acct' WHERE AD_RelationType_ID = 540259 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_PO_to_C_Project' WHERE AD_RelationType_ID = 540267 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_PO_to_C_Project' WHERE AD_RelationType_ID = 540268 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_PO_to_C_Project' WHERE AD_RelationType_ID = 540269 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Project_to_M_InOut_PO' WHERE AD_RelationType_ID = 540271 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Project_to_C_Invoice_PO' WHERE AD_RelationType_ID = 540272 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_C_Project_Service' WHERE AD_RelationType_ID = 540334 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Production / Manufacturing
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'M_Product_to_PP_Product_Planning' WHERE AD_RelationType_ID = 540127 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_to_M_Material_Tracking' WHERE AD_RelationType_ID = 540135 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_to_MD_Candidate_Prod_Detail' WHERE AD_RelationType_ID = 540218 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Product_to_PP_Product_BOMLine' WHERE AD_RelationType_ID = 540246 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_to_PP_Order_Candidate_Mfg' WHERE AD_RelationType_ID = 540328 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_Candidate_to_PP_Order' WHERE AD_RelationType_ID = 540329 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_PP_Order_Candidate' WHERE AD_RelationType_ID = 540345 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_to_PP_Order_Candidate_Maturing' WHERE AD_RelationType_ID = 540436 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_Candidate_Parent_to_Child' WHERE AD_RelationType_ID = 540444 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_to_DD_Order_Candidate' WHERE AD_RelationType_ID = 540451 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'MD_Candidate_to_PP_Order_Candidate' WHERE AD_RelationType_ID = 540457 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'PP_Order_Candidate_to_MD_Candidate' WHERE AD_RelationType_ID = 540458 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Material_Needs_Planner_to_PP_Order_Candidate' WHERE AD_RelationType_ID = 540461 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Distribution / DD_Order
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'DD_Order_to_DD_Order_Candidate' WHERE AD_RelationType_ID = 540442 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'DD_Order_Candidate_to_DD_Order' WHERE AD_RelationType_ID = 540443 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Payments & Banking
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_PaySelection_to_C_BankStatement' WHERE AD_RelationType_ID = 540147 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_PO_to_C_PaySelection' WHERE AD_RelationType_ID = 540148 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_PaySelection_to_C_Payment' WHERE AD_RelationType_ID = 540149 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_SO_to_C_PaySelection' WHERE AD_RelationType_ID = 540206 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BankStatement_to_C_PaySelection' WHERE AD_RelationType_ID = 540241 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BankStatement_to_ESR_Import' WHERE AD_RelationType_ID = 540247 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'ESR_Import_to_C_Payment' WHERE AD_RelationType_ID = 540248 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Payment_to_ESR_Import' WHERE AD_RelationType_ID = 540249 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'ESR_Import_to_C_BankStatement' WHERE AD_RelationType_ID = 540250 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Payment_to_C_PaySelection' WHERE AD_RelationType_ID = 540303 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Material Tracking
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_to_M_Material_Tracking' WHERE AD_RelationType_ID = 540136 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_M_Material_Tracking' WHERE AD_RelationType_ID = 540137 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Material_Tracking_to_C_Order' WHERE AD_RelationType_ID = 540310 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Material_Tracking_to_M_InOut' WHERE AD_RelationType_ID = 540311 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Dunning
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Dunning_Candidate_to_C_DunningDoc' WHERE AD_RelationType_ID = 540150 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_DunningDoc_to_C_Dunning_Candidate' WHERE AD_RelationType_ID = 540307 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Returns & Inventory
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_PO_to_M_InventoryLine' WHERE AD_RelationType_ID = 540174 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InventoryLine_to_C_Order_PO' WHERE AD_RelationType_ID = 540175 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_PO_to_M_InOutLine_VendorReturn' WHERE AD_RelationType_ID = 540176 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOutLine_VendorReturn_to_C_Order_PO' WHERE AD_RelationType_ID = 540177 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_M_InOutLine_CustomerReturn' WHERE AD_RelationType_ID = 540180 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOutLine_CustomerReturn_to_C_Order_SO' WHERE AD_RelationType_ID = 540181 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Flatrate / Contracts
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_to_C_FlatRate_Term' WHERE AD_RelationType_ID = 540191 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BPartner_to_C_FlatRate_Term' WHERE AD_RelationType_ID = 540192 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Quotations
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_Quotation_to_C_Order_SO' WHERE AD_RelationType_ID = 540260 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_SO_to_C_Order_Quotation' WHERE AD_RelationType_ID = 540261 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Cockpit / Legacy
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'C_Order_to_Cockpit_Suche' WHERE AD_RelationType_ID = 540028 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Cockpit_Daten_to_C_Order' WHERE AD_RelationType_ID = 540029 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Cockpit_C_BP_Relation_to_C_BP_Relation' WHERE AD_RelationType_ID = 540045 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'Cockpit_Suche_to_C_Order' WHERE AD_RelationType_ID = 1000005 AND (InternalName IS NULL OR InternalName = '');

-- =====================================================
-- Miscellaneous
-- =====================================================

UPDATE AD_RelationType SET InternalName = 'AD_Role_Included_to_AD_Role' WHERE AD_RelationType_ID = 540143 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'AD_Sequence_to_C_DocType' WHERE AD_RelationType_ID = 540144 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Aggregation_to_C_AggregationItem' WHERE AD_RelationType_ID = 540146 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Invoice_to_M_Shipment_Constraint' WHERE AD_RelationType_ID = 540202 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Shipment_Constraint_to_C_Invoice' WHERE AD_RelationType_ID = 540203 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Printing_Queue_to_C_Print_Job' WHERE AD_RelationType_ID = 540207 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_HU_to_M_HU_Trx_Line' WHERE AD_RelationType_ID = 540208 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'AD_PInstance_to_AD_SchedulerLog' WHERE AD_RelationType_ID = 540209 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_PriceList_Version_to_M_ProductPrice' WHERE AD_RelationType_ID = 540210 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_PurchaseCandidate_to_MSV3_BestellungAntwortPos' WHERE AD_RelationType_ID = 540213 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_OrderLine_PO_to_MSV3_BestellungAnteil' WHERE AD_RelationType_ID = 540216 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'MD_Candidate_Parent_to_MD_Candidate' WHERE AD_RelationType_ID = 540217 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_C_Customs_Invoice' WHERE AD_RelationType_ID = 540225 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_ElementValue_to_Fact_Acct' WHERE AD_RelationType_ID = 540234 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Customs_Invoice_to_M_InOut' WHERE AD_RelationType_ID = 540236 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'S_Issue_Parent_to_S_Issue_Effort' WHERE AD_RelationType_ID = 540242 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'S_Issue_Parent_to_S_Issue_Budget' WHERE AD_RelationType_ID = 540243 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'S_Issue_to_S_Issue_Parent_Effort' WHERE AD_RelationType_ID = 540244 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'S_Issue_to_S_Issue_Parent_Budget' WHERE AD_RelationType_ID = 540245 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BPartner_OrgMasterdata_to_C_BPartner' WHERE AD_RelationType_ID = 540265 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_to_M_Forecast' WHERE AD_RelationType_ID = 540276 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BPartner_Related_to_C_BPartner' WHERE AD_RelationType_ID = 540277 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'ExternalSystem_Config_to_Log' WHERE AD_RelationType_ID = 540279 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BPartner_to_C_BPartner_OtherOrg' WHERE AD_RelationType_ID = 540282 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BPartner_to_AD_OrgChange_History' WHERE AD_RelationType_ID = 540283 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_DiscountSchemaBreak_V_to_M_DiscountSchemaBreak' WHERE AD_RelationType_ID = 540284 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Product_to_M_Product_Relationship' WHERE AD_RelationType_ID = 540292 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_ElementValue_to_C_Element' WHERE AD_RelationType_ID = 540301 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_PriceListSchemaLine_to_M_PriceListSchema' WHERE AD_RelationType_ID = 540308 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Queue_WorkPackage_to_C_Async_Batch' WHERE AD_RelationType_ID = 540309 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Print_Job_to_C_Printing_Queue' WHERE AD_RelationType_ID = 540312 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_FrameAgreement_to_C_Order' WHERE AD_RelationType_ID = 540318 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_MFGWarehouse_to_C_Printing_Queue' WHERE AD_RelationType_ID = 540321 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_to_PMM_PurchaseCandidate' WHERE AD_RelationType_ID = 540325 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Product_to_M_Product_Marketplace' WHERE AD_RelationType_ID = 540333 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Product_to_AD_Product_Certification' WHERE AD_RelationType_ID = 540335 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_BPartner_Product_to_AD_Product_Certification' WHERE AD_RelationType_ID = 540336 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_CallOrder_to_C_CallOrderSummary' WHERE AD_RelationType_ID = 540340 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_SO_to_EDI_Desadv_Pack' WHERE AD_RelationType_ID = 540380 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_InOut_to_DHL_ShipmentOrder' WHERE AD_RelationType_ID = 540434 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Product_to_M_Maturing_Config_Line' WHERE AD_RelationType_ID = 540435 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_HU_to_M_HU_QRCode' WHERE AD_RelationType_ID = 540437 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'DHL_ShipmentOrder_to_DHL_ShipmentOrder_Log' WHERE AD_RelationType_ID = 540454 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'MD_Candidate_to_M_Forecast' WHERE AD_RelationType_ID = 540456 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_to_M_Package' WHERE AD_RelationType_ID = 540460 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_Material_Needs_Planner_to_C_PurchaseCandidate' WHERE AD_RelationType_ID = 540462 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'C_Order_PO_to_M_ShipperTransportation' WHERE AD_RelationType_ID = 540463 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'QtyDemand_QtySupply_V_to_M_ShipmentSchedule' WHERE AD_RelationType_ID = 540464 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'QtyDemand_QtySupply_V_to_M_ReceiptSchedule' WHERE AD_RelationType_ID = 540465 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'QtyDemand_QtySupply_V_to_M_Forecast' WHERE AD_RelationType_ID = 540466 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'QtyDemand_QtySupply_V_to_PP_Order_Candidate' WHERE AD_RelationType_ID = 540467 AND (InternalName IS NULL OR InternalName = '');
UPDATE AD_RelationType SET InternalName = 'M_ShipperTransportation_to_C_Order_PO' WHERE AD_RelationType_ID = 540468 AND (InternalName IS NULL OR InternalName = '');
