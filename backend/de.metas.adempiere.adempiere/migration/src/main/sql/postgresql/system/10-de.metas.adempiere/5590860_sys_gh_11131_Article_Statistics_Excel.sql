-- 2021-05-20T17:03:16.801Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,540563,541992,30,'C_BP_Group_ID',TO_TIMESTAMP('2021-05-20 20:03:15','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','de.metas.fresh',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',35,TO_TIMESTAMP('2021-05-20 20:03:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:03:16.954Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541992 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:16:53.290Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584831,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2021-05-20 20:16:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','Y','Y','Y','',0,'Artikelstatistik','json','N','Y','','Excel',TO_TIMESTAMP('2021-05-20 20:16:52','YYYY-MM-DD HH24:MI:SS'),100,'Artikelstatistik (Excel)')
;

-- 2021-05-20T17:16:53.630Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584831 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-05-20T17:17:17.484Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584831,541993,30,'AD_Org_ID',TO_TIMESTAMP('2021-05-20 20:17:17','YYYY-MM-DD HH24:MI:SS'),100,'@AD_Org_ID@','Organisatorische Einheit des Mandanten','D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','N','Organisation','1=1',3,TO_TIMESTAMP('2021-05-20 20:17:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:17.518Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541993 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:17.585Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541993
;

-- 2021-05-20T17:17:17.617Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541993, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541106
;

-- 2021-05-20T17:17:17.928Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,223,0,584831,541994,19,'C_Year_ID',TO_TIMESTAMP('2021-05-20 20:17:17','YYYY-MM-DD HH24:MI:SS'),100,'Kalenderjahr','D',0,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','N','Y','N','N','N','Jahr',5,TO_TIMESTAMP('2021-05-20 20:17:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:17.958Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541994 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:18.020Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541994
;

-- 2021-05-20T17:17:18.053Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541994, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540695
;

-- 2021-05-20T17:17:18.355Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,206,0,584831,541995,18,540658,199,'C_Period_ID',TO_TIMESTAMP('2021-05-20 20:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Letzte Periode die der Bericht ausgeben soll. Es werden außerdem 11 vorgänder Perioden ausgewertet','D',0,'Letzte Periode die der Bericht ausgeben soll. Es werden außerdem 11 vorgänder Perioden ausgewertet','Y','N','Y','N','Y','N','Periode bis',10,TO_TIMESTAMP('2021-05-20 20:17:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:18.388Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541995 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:18.454Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541995
;

-- 2021-05-20T17:17:18.486Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541995, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540688
;

-- 2021-05-20T17:17:18.784Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,584831,541996,20,'IsSOTrx',TO_TIMESTAMP('2021-05-20 20:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist eine Verkaufstransaktion','D',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','Verkaufstransaktion',20,TO_TIMESTAMP('2021-05-20 20:17:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:18.815Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541996 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:18.880Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541996
;

-- 2021-05-20T17:17:18.911Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541996, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540689
;

-- 2021-05-20T17:17:19.236Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,584831,541997,30,'C_BPartner_ID',TO_TIMESTAMP('2021-05-20 20:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','D',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','Y','N','N','N','Geschäftspartner',30,TO_TIMESTAMP('2021-05-20 20:17:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:19.270Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541997 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:19.333Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541997
;

-- 2021-05-20T17:17:19.363Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541997, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540690
;

-- 2021-05-20T17:17:19.673Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,584831,541998,30,'C_BP_Group_ID',TO_TIMESTAMP('2021-05-20 20:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','D',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',35,TO_TIMESTAMP('2021-05-20 20:17:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:19.705Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541998 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:19.771Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541998
;

-- 2021-05-20T17:17:19.801Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541998, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541992
;

-- 2021-05-20T17:17:20.110Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,584831,541999,30,541138,'M_Product_ID',TO_TIMESTAMP('2021-05-20 20:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','Y','N','N','N','Produkt',50,TO_TIMESTAMP('2021-05-20 20:17:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:20.142Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541999 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:20.196Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 541999
;

-- 2021-05-20T17:17:20.220Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541999, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540692
;

-- 2021-05-20T17:17:20.530Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2019,0,584831,542000,35,'M_AttributeSetInstance_ID',TO_TIMESTAMP('2021-05-20 20:17:20','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','D',0,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','Merkmale',70,TO_TIMESTAMP('2021-05-20 20:17:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:17:20.559Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542000 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-05-20T17:17:20.621Z
-- URL zum Konzept
DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 542000
;

-- 2021-05-20T17:17:20.651Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 542000, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = 540694
;

-- 2021-05-20T17:18:01.702Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=577422, ColumnName='Parameter_M_Product_ID',Updated=TO_TIMESTAMP('2021-05-20 20:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541999
;

-- 2021-05-20T17:18:48.360Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=577421, ColumnName='Parameter_C_BPartner_ID',Updated=TO_TIMESTAMP('2021-05-20 20:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541997
;

-- -- 2021-05-20T17:22:12.976Z
-- -- URL zum Konzept
-- INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
 -- VALUES (0,579198,0,'Parameter_AD_Org_ID',TO_TIMESTAMP('2021-05-20 20:22:12','YYYY-MM-DD HH24:MI:SS'),
 -- 100,'Organisatorische Einheit des Mandanten','D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Organisation','Organisation',TO_TIMESTAMP('2021-05-20 20:22:12','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- -- 2021-05-20T17:22:13.071Z
-- -- URL zum Konzept
-- INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579198 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
-- ;

-- 2021-05-20T17:22:39.220Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=579198, ColumnName='Parameter_AD_Org_ID', DefaultValue='@#AD_Org_ID@', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-20 20:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541993
;

-- 2021-05-20T17:23:26.852Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_report(
	@C_Period_ID/-1@,
	@IsSOTrx@,
	@Parameter_C_BPartner_ID/-1@,
	@C_BP_Group_ID/-1@,
	@C_Activity_ID/-1@,
	@Parameter_M_Product_ID/-1@,
	@M_Product_Category_ID/-1@,
	@M_AttributeSetInstance_ID/-1@,
	@Parameter_AD_Org_ID/-1@,
	''@ad_language@'')',Updated=TO_TIMESTAMP('2021-05-20 20:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-20T17:25:11.070Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579199,0,TO_TIMESTAMP('2021-05-20 20:25:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Artikelstatistik (Excel)','Artikelstatistik (Excel)',TO_TIMESTAMP('2021-05-20 20:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:25:11.170Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579199 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-20T17:25:43.236Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,579199,541711,0,584831,TO_TIMESTAMP('2021-05-20 20:25:42','YYYY-MM-DD HH24:MI:SS'),100,'U','Artikelstatistik (Excel)','Y','N','N','N','N','Artikelstatistik (Excel)',TO_TIMESTAMP('2021-05-20 20:25:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-20T17:25:43.304Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541711 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-05-20T17:25:43.336Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541711, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541711)
;

-- 2021-05-20T17:25:43.420Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(579199) 
;

-- 2021-05-20T17:25:45.712Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541547 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.759Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541562 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.790Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.813Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541482 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.844Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.875Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541441 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.913Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.944Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:45.976Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.007Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.029Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.061Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.107Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.142Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.164Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.195Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.230Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.261Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:46.292Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.222Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541547 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.270Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541562 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.294Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.325Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541482 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.361Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.392Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.423Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541441 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.445Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.492Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.523Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.545Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.577Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.608Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.645Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.677Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.708Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.745Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.783Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:51.812Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.310Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541547 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.341Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541562 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.364Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540928 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541711 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.442Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541482 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.480Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540930 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.511Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541441 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.548Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540933 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.580Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540926 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.611Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540927 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.642Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540738 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.664Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540925 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.696Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540923 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.727Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540922 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.765Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540931 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.796Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540947 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.827Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540932 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.856Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540924 AND AD_Tree_ID=10
;

-- 2021-05-20T17:25:53.888Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000048, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540957 AND AD_Tree_ID=10
;

-- 2021-05-21T09:00:46.612Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=322,Updated=TO_TIMESTAMP('2021-05-21 12:00:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541993
;

-- 2021-05-21T09:01:29.097Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541252,Updated=TO_TIMESTAMP('2021-05-21 12:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541997
;

-- 2021-05-21T09:04:11.028Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2021-05-21 12:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541997
;

-- 2021-05-21T09:04:21.378Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2021-05-21 12:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541999
;

-- 2021-05-21T09:05:48.704Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_report( @C_Period_ID/-1@, @IsSOTrx@, @Parameter_C_BPartner_ID/-1@, @C_BP_Group_ID/-1@, @C_Activity_ID/-1@, @Parameter_M_Product_ID/-1@, @M_Product_Category_ID/-1@, @M_AttributeSetInstance_ID/-1@, @Parameter_AD_Org_ID/-1@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2021-05-21 12:05:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T09:06:23.786Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_report( @C_Period_ID/-1@, ''@IsSOTrx@'', @Parameter_C_BPartner_ID/-1@, @C_BP_Group_ID/-1@, @C_Activity_ID/-1@, @Parameter_M_Product_ID/-1@, @M_Product_Category_ID/-1@, @M_AttributeSetInstance_ID/-1@, @Parameter_AD_Org_ID/-1@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2021-05-21 12:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T09:06:57.647Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_non0_report( @C_Period_ID/-1@, ''@IsSOTrx@'', @Parameter_C_BPartner_ID/-1@, @C_BP_Group_ID/-1@, @C_Activity_ID/-1@, @Parameter_M_Product_ID/-1@, @M_Product_Category_ID/-1@, @M_AttributeSetInstance_ID/-1@, @Parameter_AD_Org_ID/-1@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2021-05-21 12:06:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T09:09:49.059Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_non0_report( @C_Period_ID@, ''@IsSOTrx@'', @Parameter_C_BPartner_ID@, @C_BP_Group_ID@, @C_Activity_ID@, @Parameter_M_Product_ID@, @M_Product_Category_ID@, @M_AttributeSetInstance_ID@, @Parameter_AD_Org_ID@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2021-05-21 12:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T09:10:48.577Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_non0_report( @C_Period_ID@, ''@IsSOTrx@'', @Parameter_C_BPartner_ID/null@, @C_BP_Group_ID@, @C_Activity_ID@, @Parameter_M_Product_ID@, @M_Product_Category_ID@, @M_AttributeSetInstance_ID@, @Parameter_AD_Org_ID@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2021-05-21 12:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T09:11:23.554Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_product_statistics_non0_report( @C_Period_ID/null@, ''@IsSOTrx@'', @Parameter_C_BPartner_ID/null@, @C_BP_Group_ID/null@, @C_Activity_ID/null@, @Parameter_M_Product_ID/null@, @M_Product_Category_ID/null@, @M_AttributeSetInstance_ID/null@, @Parameter_AD_Org_ID/null@, ''@#AD_Language@'')',Updated=TO_TIMESTAMP('2021-05-21 12:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T12:39:42.045Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Artikelstatistik (Excel)',Updated=TO_TIMESTAMP('2021-05-21 15:39:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584831
;

-- 2021-05-21T12:39:48.099Z
-- URL zum Konzept
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Artikelstatistik (Excel)',Updated=TO_TIMESTAMP('2021-05-21 15:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584831
;

-- 2021-05-21T12:39:47.831Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Artikelstatistik (Excel)',Updated=TO_TIMESTAMP('2021-05-21 15:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584831
;

-- 2021-05-21T12:39:50.982Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Name='Artikelstatistik (Excel)',Updated=TO_TIMESTAMP('2021-05-21 15:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584831
;

-- -- 2021-05-21T12:41:31.579Z
-- -- URL zum Konzept
-- UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-05-21 15:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540692
-- ;


-- 2021-05-21T14:02:01.072Z
-- URL zum Konzept
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=542001
;

-- 2021-05-21T14:02:01.277Z
-- URL zum Konzept
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=542001
;

-- 2021-05-21T16:41:07.190Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID@', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-05-21 19:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541106
;










-- 2021-06-02T14:56:47.010Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,453,0,584831,542020,30,'M_Product_Category_ID',TO_TIMESTAMP('2021-06-02 16:56:47','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','U',0,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','Produkt Kategorie',80,TO_TIMESTAMP('2021-06-02 16:56:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T14:56:47.011Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542020 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-02T14:56:53.211Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=60,Updated=TO_TIMESTAMP('2021-06-02 16:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542020
;

-- 2021-06-02T14:57:04.084Z
-- URL zum Konzept
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2021-06-02 16:57:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542020
;








