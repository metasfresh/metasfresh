-- Name: Invoice Accounting Overrides
-- Action Type: W
-- Window: Invoice Accounting Overrides(541659,D)
-- 2023-01-12T13:01:34.368Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581915,542034,0,541659,TO_TIMESTAMP('2023-01-12 15:01:34','YYYY-MM-DD HH24:MI:SS'),100,'D','C_Invoice_Acct','Y','N','N','N','N','Invoice Accounting Overrides',TO_TIMESTAMP('2023-01-12 15:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T13:01:34.371Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542034 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-12T13:01:34.375Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542034, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542034)
;

-- 2023-01-12T13:01:34.390Z
/* DDL */  select update_menu_translation_from_ad_element(581915) 
;

-- Reordering children of `Organization Rules`
-- Node name: `Organization Type (AD_OrgType)`
-- 2023-01-12T13:01:35.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=441 AND AD_Tree_ID=10
;

-- Node name: `Organization (AD_Org)`
-- 2023-01-12T13:01:35.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=149 AND AD_Tree_ID=10
;

-- Node name: `Dashboard Content Edit (PA_DashboardContent)`
-- 2023-01-12T13:01:35.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50010 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Matcher (C_BankStatementMatcher)`
-- 2023-01-12T13:01:35.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=437 AND AD_Tree_ID=10
;

-- Node name: `Cashbook (C_CashBook)`
-- 2023-01-12T13:01:35.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=240 AND AD_Tree_ID=10
;

-- Node name: `Recurring (C_Recurring)`
-- 2023-01-12T13:01:35.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=361 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2023-01-12T13:01:35.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=175, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2023-01-12T13:01:45.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2023-01-12T13:01:45.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2023-01-12T13:01:45.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2023-01-12T13:01:45.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2023-01-12T13:01:45.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2023-01-12T13:01:45.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2023-01-12T13:01:45.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2023-01-12T13:01:45.630Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2023-01-12T13:01:45.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2023-01-12T13:01:45.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2023-01-12T13:01:45.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2023-01-12T13:01:45.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2023-01-12T13:01:45.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2023-01-12T13:01:45.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2023-01-12T13:01:45.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2023-01-12T13:01:45.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2023-01-12T13:01:45.641Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2023-01-12T13:01:45.642Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2023-01-12T13:01:45.643Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2023-01-12T13:01:45.644Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2023-01-12T13:01:45.645Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2023-01-12T13:01:45.646Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-01-12T13:01:45.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-01-12T13:01:45.648Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2023-01-12T13:01:45.649Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2023-01-12T13:01:45.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2023-01-12T13:01:45.651Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2023-01-12T13:01:45.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2023-01-12T13:01:45.653Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2023-01-12T13:01:45.654Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2023-01-12T13:01:45.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2023-01-12T13:01:45.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2023-01-12T13:01:45.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2023-01-12T13:01:45.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-01-12T13:01:45.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2023-01-12T13:01:45.661Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-01-12T13:01:45.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-01-12T13:01:45.663Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2023-01-12T13:01:45.664Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides`
-- 2023-01-12T13:01:45.665Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

