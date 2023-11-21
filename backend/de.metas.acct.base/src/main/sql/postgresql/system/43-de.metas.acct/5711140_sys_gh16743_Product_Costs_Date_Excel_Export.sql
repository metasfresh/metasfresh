-- Value: Auswertung Produktkosten (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-11-20T18:44:33.213Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585339,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2023-11-20 19:44:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Auswertung Produktkosten (Excel)','json','N','N','xls','SELECT Date,AD_Org_ID,C_AcctSchema_ID,M_Product_Category_ID,M_Product_ID,CostElement,Cost FROM report.getCostsPerDate(''@Date@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @M_Product_ID/-1@, @M_Product_Category_ID/-1@)
','Excel',TO_TIMESTAMP('2023-11-20 19:44:32','YYYY-MM-DD HH24:MI:SS'),100,'Auswertung Produktkosten (Excel)')
;

-- Value: Auswertung Produktkosten (Excel)
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2023-11-21T12:02:54.722Z
UPDATE AD_Process SET SQLStatement='SELECT Date,param_organization AS AD_Org_ID, param_acctSchema AS C_AcctSchema_ID,ProductCategory,Product,CostElement,Cost FROM report.getCostsPerDate(''@Date@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @M_Product_ID/null@, @M_Product_Category_ID/null@) order by productcategory, product',Updated=TO_TIMESTAMP('2023-11-21 13:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585339
;

-- 2023-11-20T18:44:33.683Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585339 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-11-21T09:05:13.021Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Product costs per date (Excel)',Updated=TO_TIMESTAMP('2023-11-21 10:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585339
;

-- Process: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: DateAcct
-- 2023-11-20T18:50:39.299Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,263,0,585339,542741,15,'DateAcct',TO_TIMESTAMP('2023-11-20 19:50:38','YYYY-MM-DD HH24:MI:SS'),100,'@Date@','Accounting Date','de.metas.acct',0,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','Y','N','Buchungsdatum',10,TO_TIMESTAMP('2023-11-20 19:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T18:50:39.369Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542741 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: AD_Org_ID
-- 2023-11-20T18:51:40.589Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,585339,542742,19,'AD_Org_ID',TO_TIMESTAMP('2023-11-20 19:51:39','YYYY-MM-DD HH24:MI:SS'),100,'@AD_Org_ID@','Organisatorische Einheit des Mandanten','de.metas.acct',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Sektion',20,TO_TIMESTAMP('2023-11-20 19:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T18:51:40.659Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542742 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: C_AcctSchema_ID
-- 2023-11-20T18:52:33.874Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,585339,542743,19,'C_AcctSchema_ID',TO_TIMESTAMP('2023-11-20 19:52:33','YYYY-MM-DD HH24:MI:SS'),100,'1000000','Stammdaten für Buchhaltung','de.metas.acct',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','Y','N','Buchführungs-Schema',30,TO_TIMESTAMP('2023-11-20 19:52:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T18:52:33.949Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542743 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: M_Product_Category_ID
-- 2023-11-20T18:53:15.498Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,453,0,585339,542744,19,'M_Product_Category_ID',TO_TIMESTAMP('2023-11-20 19:53:14','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','de.metas.acct',0,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','Produkt Kategorie','@M_Product_ID@>0',40,TO_TIMESTAMP('2023-11-20 19:53:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T18:53:15.572Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542744 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: M_Product_ID
-- 2023-11-20T18:54:40.172Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585339,542745,30,540272,540554,'M_Product_ID',TO_TIMESTAMP('2023-11-20 19:54:39','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','U',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',50,TO_TIMESTAMP('2023-11-20 19:54:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T18:54:40.243Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542745 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-11-20T19:04:17.864Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582813,0,TO_TIMESTAMP('2023-11-20 20:04:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Auswertung Produktkosten (Excel)','Auswertung Produktkosten (Excel)',TO_TIMESTAMP('2023-11-20 20:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T19:04:18.244Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582813 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-11-20T19:04:38.837Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Product costs per date (Excel)', PrintName='Product costs per date (Excel)',Updated=TO_TIMESTAMP('2023-11-20 20:04:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582813 AND AD_Language='en_US'
;

-- 2023-11-20T19:04:39.004Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582813,'en_US') 
;

-- Name: Auswertung Produktkosten (Excel)
-- Action Type: R
-- Report: Auswertung Produktkosten (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2023-11-20T19:05:16.304Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,582813,542130,0,585339,TO_TIMESTAMP('2023-11-20 20:05:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Product costs per date (Excel)','Y','N','N','N','N','Auswertung Produktkosten (Excel)',TO_TIMESTAMP('2023-11-20 20:05:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-20T19:05:16.524Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542130 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-11-20T19:05:16.603Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542130, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542130)
;

-- 2023-11-20T19:05:16.684Z
/* DDL */  select update_menu_translation_from_ad_element(582813) 
;

-- Reordering children of `Reports`
-- Node name: `Next vs AfterNext Pricelist Comparison`
-- 2023-11-20T19:05:21.124Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541956 AND AD_Tree_ID=10
;

-- Node name: `Current Vs Next Pricelist Comparison Report`
-- 2023-11-20T19:05:21.194Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541452 AND AD_Tree_ID=10
;

-- Node name: `Product costs per date`
-- 2023-11-20T19:05:21.274Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542123 AND AD_Tree_ID=10
;

-- Node name: `Auswertung Produktkosten (Excel)`
-- 2023-11-20T19:05:21.343Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542130 AND AD_Tree_ID=10
;

-- 2023-11-21T08:58:10.840Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582816,0,'CostElement',TO_TIMESTAMP('2023-11-21 09:58:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Kostenart','Kostenart',TO_TIMESTAMP('2023-11-21 09:58:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-21T08:58:10.949Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582816 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CostElement
-- 2023-11-21T08:58:50.317Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cost Element', PrintName='Cost Element',Updated=TO_TIMESTAMP('2023-11-21 09:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582816 AND AD_Language='en_US'
;

-- 2023-11-21T08:58:50.428Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582816,'en_US')
;

