-- Value: RV_AccountSheet_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-11-28T11:48:54.433117Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,
                        IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,
                        IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value)
VALUES ('3',0,0,585341,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-11-28 13:48:54.022','YYYY-MM-DD HH24:MI:SS.US'),100,'Debitoren-/ Kreditoren-/ Sachkontenauszug','U','Y','N','N','N','Y',
        'N','N','Y','Y',
        'Y',0,'Kontenauszug','json','N','N','xls','SELECT * FROM de_metas_acct.AccountSheet_Report(p_Account_ID := @Account_ID@, p_C_AcctSchema_ID := @C_AcctSchema_ID@, p_DateAcctFrom := @DateAcctFrom/NULL@, p_DateAcctTo := @DateAcctTo/NULL@)','Excel',TO_TIMESTAMP('2023-11-28 13:48:54.022','YYYY-MM-DD HH24:MI:SS.US'),100,'RV_AccountSheet_Report')
;

-- 2023-11-28T11:48:54.440411100Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585341 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: RV_AccountSheet_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-11-28T11:48:57.019655500Z
UPDATE AD_Process SET IsTranslateExcelHeaders='N',Updated=TO_TIMESTAMP('2023-11-28 13:48:57.017','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585341
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-11-28T11:49:16.314055600Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585341,542757,30,'C_AcctSchema_ID',TO_TIMESTAMP('2023-11-28 13:49:16.155','YYYY-MM-DD HH24:MI:SS.US'),100,'Stammdaten für Buchhaltung','U',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','Y','N','Buchführungs-Schema',10,TO_TIMESTAMP('2023-11-28 13:49:16.155','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-28T11:49:16.317677400Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542757 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-11-28T11:49:16.358913300Z
-- /* DDL */  select update_Process_Para_Translation_From_AD_Element(181)
-- ;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2023-11-28T11:49:32.646596900Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,148,0,585341,542758,30,'Account_ID',TO_TIMESTAMP('2023-11-28 13:49:32.521','YYYY-MM-DD HH24:MI:SS.US'),100,'Verwendetes Konto','U',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','Y','N','Konto',20,TO_TIMESTAMP('2023-11-28 13:49:32.521','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-28T11:49:32.648164900Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542758 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-11-28T11:49:32.650239300Z
-- /* DDL */  select update_Process_Para_Translation_From_AD_Element(148)
-- ;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateAcctFrom
-- 2023-11-28T11:49:55.944385200Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543873,0,585341,542759,15,'DateAcctFrom',TO_TIMESTAMP('2023-11-28 13:49:55.811','YYYY-MM-DD HH24:MI:SS.US'),100,'U',0,'Y','N','Y','N','Y','N','Buchungsdatum von',30,TO_TIMESTAMP('2023-11-28 13:49:55.811','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-28T11:49:55.946475800Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542759 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- -- 2023-11-28T11:49:55.948571Z
-- /* DDL */  select update_Process_Para_Translation_From_AD_Element(543873)
-- ;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateAcctTo
-- 2023-11-28T11:50:08.571888400Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543874,0,585341,542760,15,'DateAcctTo',TO_TIMESTAMP('2023-11-28 13:50:08.442','YYYY-MM-DD HH24:MI:SS.US'),100,'U',0,'Y','N','Y','N','Y','N','Buchungsdatum bis',40,TO_TIMESTAMP('2023-11-28 13:50:08.442','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-28T11:50:08.573456Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542760 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- -- 2023-11-28T11:50:08.576051300Z
-- /* DDL */  select update_Process_Para_Translation_From_AD_Element(543874)
-- ;

-- Value: RV_AccountSheet_Report
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-11-28T12:17:44.605612600Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM de_metas_acct.AccountSheet_Report(p_Account_ID := @Account_ID@, p_C_AcctSchema_ID := @C_AcctSchema_ID@, p_DateAcctFrom := ''@DateAcctFrom@'', p_DateAcctTo := ''@DateAcctTo@'')',Updated=TO_TIMESTAMP('2023-11-28 14:17:44.602','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=585341
;




--
--
--
--

-- 2023-11-28T11:58:18.350265300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582820,0,'RV_AccountSheet_Report',TO_TIMESTAMP('2023-11-28 13:58:18.205','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Kontenauszug','Kontenauszug',TO_TIMESTAMP('2023-11-28 13:58:18.205','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-28T11:58:18.356989700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582820 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: Kontenauszug
-- Action Type: P
-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-11-28T11:59:08.169197700Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,582820,542131,0,585341,TO_TIMESTAMP('2023-11-28 13:59:08.038','YYYY-MM-DD HH24:MI:SS.US'),100,'U','RV_AccountSheet_Report','Y','N','N','N','N','Kontenauszug',TO_TIMESTAMP('2023-11-28 13:59:08.038','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-28T11:59:08.172870700Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542131 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-11-28T11:59:08.177024Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542131, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542131)
;

-- 2023-11-28T11:59:08.188939600Z
/* DDL */  select update_menu_translation_from_ad_element(582820)
;

-- Reordering children of `Forum Datenaustausch`
-- Node name: `Forum Datenaustausch Config (HC_Forum_Datenaustausch_Config)`
-- 2023-11-28T11:59:08.828628500Z
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541145 AND AD_Tree_ID=10
;

-- Node name: `Invoice rejection detail (C_Invoice_Rejection_Detail)`
-- 2023-11-28T11:59:08.838951800Z
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541278 AND AD_Tree_ID=10
;

-- Node name: `Import health insurance invoice responses (de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse)`
-- 2023-11-28T11:59:08.839993Z
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541228 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug`
-- 2023-11-28T11:59:08.841652300Z
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Kontenauszug`
-- 2023-11-28T11:59:21.387072100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `Open Items (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-11-28T11:59:21.388627200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542075 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2023-11-28T11:59:21.390179100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2023-11-28T11:59:21.392248Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2023-11-28T11:59:21.393286Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2023-11-28T11:59:21.394860Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2023-11-28T11:59:21.395896300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2023-11-28T11:59:21.396930400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2023-11-28T11:59:21.398483600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2023-11-28T11:59:21.399519600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2023-11-28T11:59:21.401075200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2023-11-28T11:59:21.402112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2023-11-28T11:59:21.403148Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2023-11-28T11:59:21.404189Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2023-11-28T11:59:21.405774600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2023-11-28T11:59:21.406813500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2023-11-28T11:59:21.407856Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2023-11-28T11:59:21.408889400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2023-11-28T11:59:21.410462100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2023-11-28T11:59:21.411498700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2023-11-28T11:59:21.412531500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2023-11-28T11:59:21.413566100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2023-11-28T11:59:21.415116Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2023-11-28T11:59:21.416162900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2023-11-28T11:59:21.417204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-11-28T11:59:21.418761Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-11-28T11:59:21.419798200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2023-11-28T11:59:21.420834100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2023-11-28T11:59:21.421868100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2023-11-28T11:59:21.422914100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2023-11-28T11:59:21.424471300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2023-11-28T11:59:21.425271500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2023-11-28T11:59:21.426831Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2023-11-28T11:59:21.427869100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2023-11-28T11:59:21.428906400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2023-11-28T11:59:21.429941100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2023-11-28T11:59:21.431493100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2023-11-28T11:59:21.432527600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2023-11-28T11:59:21.433565400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-11-28T11:59:21.434601900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2023-11-28T11:59:21.435638300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-11-28T11:59:21.436667700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-11-28T11:59:21.438209300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2023-11-28T11:59:21.439257400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2023-11-28T11:59:21.440322700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Foreign Exchange Contract (C_ForeignExchangeContract)`
-- 2023-11-28T11:59:21.441878400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542042 AND AD_Tree_ID=10
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: Account_ID
-- 2023-11-28T13:49:40.025410Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540666,Updated=TO_TIMESTAMP('2023-11-28 15:49:40.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542758
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-11-28T13:54:57.640354600Z
UPDATE AD_Process_Para SET DefaultValue='@$C_AcctSchema_ID@',Updated=TO_TIMESTAMP('2023-11-28 15:54:57.639','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542757
;

-- Process: RV_AccountSheet_Report(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2024-10-04T09:47:26.526Z
UPDATE AD_Process_Trl SET Description='Custmer Receivables/Vendor Liability Accounting transactions export', IsTranslated='Y', Name='Accounting transactions export',Updated=TO_TIMESTAMP('2024-10-04 10:47:26.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585341
;

-- 2024-10-04T09:49:58.445Z
UPDATE AD_Menu_Trl SET Description='Custmer Receivables/Vendor Liability Accounting transactions export', Name='Accounting transactions export',Updated=TO_TIMESTAMP('2024-10-04 10:49:58.286','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Menu_ID=542131
;

--
select role_access_update();

