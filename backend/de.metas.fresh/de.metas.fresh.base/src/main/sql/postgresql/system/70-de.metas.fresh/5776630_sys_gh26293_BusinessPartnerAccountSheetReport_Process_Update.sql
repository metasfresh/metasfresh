-- Run mode: SWING_CLIENT

-- Name: Yes_No_Empty
-- 2025-11-13T13:44:59.031Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542020,TO_TIMESTAMP('2025-11-13 14:44:58.328','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Yes_No_Empty',TO_TIMESTAMP('2025-11-13 14:44:58.328','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2025-11-13T13:44:59.104Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542020 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Yes_No_Empty
-- Value: Ja
-- ValueName: Ja
-- 2025-11-13T13:45:26.981Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542020,544061,TO_TIMESTAMP('2025-11-13 14:45:26.186','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Ja',TO_TIMESTAMP('2025-11-13 14:45:26.186','YYYY-MM-DD HH24:MI:SS.US'),100,'Ja','Ja')
;

-- 2025-11-13T13:45:27.060Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544061 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Yes_No_Empty -> Ja_Ja
-- 2025-11-13T13:45:42.782Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Yes',Updated=TO_TIMESTAMP('2025-11-13 14:45:42.782','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544061
;

-- Reference: Yes_No_Empty
-- Value: Y
-- ValueName: Ja
-- 2025-11-13T13:45:54.468Z
UPDATE AD_Ref_List SET Value='Y',Updated=TO_TIMESTAMP('2025-11-13 14:45:54.468','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=544061
;

-- Reference: Yes_No_Empty
-- Value: N
-- ValueName: Nein
-- 2025-11-13T13:46:04.790Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542020,544062,TO_TIMESTAMP('2025-11-13 14:46:04.015','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Nein',TO_TIMESTAMP('2025-11-13 14:46:04.015','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nein')
;

-- 2025-11-13T13:46:04.853Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544062 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Yes_No_Empty -> N_Nein
-- 2025-11-13T13:46:12.379Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='No',Updated=TO_TIMESTAMP('2025-11-13 14:46:12.379','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544062
;

-- Reference: Yes_No_Empty
-- Value: E
-- ValueName: Leer
-- 2025-11-13T13:46:31.172Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542020,544063,TO_TIMESTAMP('2025-11-13 14:46:30.382','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Leer',TO_TIMESTAMP('2025-11-13 14:46:30.382','YYYY-MM-DD HH24:MI:SS.US'),100,'E','Leer')
;

-- 2025-11-13T13:46:31.236Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544063 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Yes_No_Empty -> E_Leer
-- 2025-11-13T13:46:40.204Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Empty',Updated=TO_TIMESTAMP('2025-11-13 14:46:40.204','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544063
;

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: p_IsSOTrx
-- 2025-11-13T13:47:06.953Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=542020,Updated=TO_TIMESTAMP('2025-11-13 14:47:06.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;

-- 2025-11-13T10:10:17.254Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584207,0,'p_IsSOTrx',TO_TIMESTAMP('2025-11-13 11:10:16.484','YYYY-MM-DD HH24:MI:SS.US'),100,'Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden: * Ja → Nur Verkaufstransaktionen * Nein → Nur Einkaufstransaktionen * Leer → Verkaufs- und Einkaufstransaktionen','D','Legt fest, welche Transaktionsarten im Bericht berücksichtigt werden:
* Ja → Nur Verkaufstransaktionen
* Nein → Nur Einkaufstransaktionen
* Leer → Verkaufs- und Einkaufstransaktionen','Y','Verkaufstransaktion','Verkaufstransaktion',TO_TIMESTAMP('2025-11-13 11:10:16.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-11-13T10:10:17.313Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584207 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: p_IsSOTrx
-- 2025-11-13T10:10:43.258Z
UPDATE AD_Element_Trl SET Description='Determines which transaction types are included in the report: * Yes → Only sales transactions * No → Only purchase transactions * Empty → Both sales and purchase transactions', Help='Determines which transaction types are included in the report:
* Yes → Only sales transactions
* No → Only purchase transactions
* Empty → Both sales and purchase transactions', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-11-13 11:10:43.258','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=584207 AND AD_Language='en_US'
;

-- 2025-11-13T10:10:43.406Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584207,'en_US')
;

-- Process: BusinessPartnerAccountSheetReport(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- ParameterName: p_IsSOTrx
-- 2025-11-13T10:11:00.095Z
UPDATE AD_Process_Para SET AD_Element_ID=584207, ColumnName='p_IsSOTrx',Updated=TO_TIMESTAMP('2025-11-13 11:11:00.095','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=541756
;

-- 2025-11-13T10:11:00.150Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(584207)
;

-- Value: BusinessPartnerAccountSheetReport
-- Classname: de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess
-- 2025-11-13T10:29:07.439Z
UPDATE AD_Process SET SQLStatement='SELECT * FROM BusinessPartnerAccountSheetReport(@C_BPartner_ID@, ''@DateFrom@''::date, ''@DateTo@''::date, @#AD_Client_ID@, @AD_Org_ID@, ''@p_IsSOTrx/NULL@'')',Updated=TO_TIMESTAMP('2025-11-13 11:29:07.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=584661
;

-- 2025-11-13T10:17:45.774Z
UPDATE AD_Menu_Trl SET Name='Geschäftspartner Kontenblatt',Updated=TO_TIMESTAMP('2025-11-13 11:17:45.607','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Menu_ID=541440
;

-- 2025-11-13T10:17:45.830Z
UPDATE AD_Menu SET Name='Geschäftspartner Kontenblatt' WHERE AD_Menu_ID=541440
;

