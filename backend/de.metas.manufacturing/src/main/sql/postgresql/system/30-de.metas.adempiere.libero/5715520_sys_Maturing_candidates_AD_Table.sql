-- Table: PP_Maturing_Candidates_v
-- Table: PP_Maturing_Candidates_v
-- 2024-01-17T14:24:04.394Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542385,'N',TO_TIMESTAMP('2024-01-17 16:24:04','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','N','N','N','N','N','Y',0,'PP_Maturing_Candidates_v','NP','L','PP_Maturing_Candidates_v','DTI',TO_TIMESTAMP('2024-01-17 16:24:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:24:04.396Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542385 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Column: PP_Maturing_Candidates_v.M_HU_ID
-- Column: PP_Maturing_Candidates_v.M_HU_ID
-- 2024-01-17T14:26:14.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587795,542140,0,30,542385,'M_HU_ID',TO_TIMESTAMP('2024-01-17 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2024-01-17 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:14.402Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587795 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:14.432Z
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- Column: PP_Maturing_Candidates_v.Qty
-- Column: PP_Maturing_Candidates_v.Qty
-- 2024-01-17T14:26:14.795Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587796,526,0,29,542385,'Qty',TO_TIMESTAMP('2024-01-17 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,'Menge','U',14,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','Y','N','N','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2024-01-17 16:26:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:14.796Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587796 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:14.799Z
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- Column: PP_Maturing_Candidates_v.HUStatus
-- Column: PP_Maturing_Candidates_v.HUStatus
-- 2024-01-17T14:26:15.159Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587797,542327,0,20,542385,'HUStatus',TO_TIMESTAMP('2024-01-17 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'U',1,'Y','Y','N','N','N','N','N','N','N','N','N','Gebinde Status',TO_TIMESTAMP('2024-01-17 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:15.161Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587797 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:15.163Z
/* DDL */  select update_Column_Translation_From_AD_Element(542327) 
;

-- Column: PP_Maturing_Candidates_v.M_Product_ID
-- Column: PP_Maturing_Candidates_v.M_Product_ID
-- 2024-01-17T14:26:15.740Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587798,454,0,30,542385,'M_Product_ID',TO_TIMESTAMP('2024-01-17 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','U',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2024-01-17 16:26:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:15.741Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587798 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:15.743Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: PP_Maturing_Candidates_v.C_UOM_ID
-- Column: PP_Maturing_Candidates_v.C_UOM_ID
-- 2024-01-17T14:26:16.217Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587799,215,0,30,542385,'C_UOM_ID',TO_TIMESTAMP('2024-01-17 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','U',10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','Y','N','N','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-01-17 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:16.218Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587799 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:16.220Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Column: PP_Maturing_Candidates_v.M_Warehouse_ID
-- Column: PP_Maturing_Candidates_v.M_Warehouse_ID
-- 2024-01-17T14:26:16.652Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587800,459,0,30,542385,'M_Warehouse_ID',TO_TIMESTAMP('2024-01-17 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','U',10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','N','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2024-01-17 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:16.654Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587800 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:16.655Z
/* DDL */  select update_Column_Translation_From_AD_Element(459) 
;

-- Column: PP_Maturing_Candidates_v.M_Maturing_Configuration_ID
-- Column: PP_Maturing_Candidates_v.M_Maturing_Configuration_ID
-- 2024-01-17T14:26:17.090Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587801,582856,0,30,542385,'M_Maturing_Configuration_ID',TO_TIMESTAMP('2024-01-17 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Reifung Konfiguration',TO_TIMESTAMP('2024-01-17 16:26:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:17.092Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587801 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:17.094Z
/* DDL */  select update_Column_Translation_From_AD_Element(582856) 
;

-- Column: PP_Maturing_Candidates_v.M_Maturing_Configuration_Line_ID
-- Column: PP_Maturing_Candidates_v.M_Maturing_Configuration_Line_ID
-- 2024-01-17T14:26:17.528Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587802,582857,0,30,542385,'M_Maturing_Configuration_Line_ID',TO_TIMESTAMP('2024-01-17 16:26:17','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Zuordnung Reifeprodukte',TO_TIMESTAMP('2024-01-17 16:26:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:17.529Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587802 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:17.531Z
/* DDL */  select update_Column_Translation_From_AD_Element(582857) 
;

-- Column: PP_Maturing_Candidates_v.PP_Product_Planning_ID
-- Column: PP_Maturing_Candidates_v.PP_Product_Planning_ID
-- 2024-01-17T14:26:17.978Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587803,53268,0,30,542385,'PP_Product_Planning_ID',TO_TIMESTAMP('2024-01-17 16:26:17','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Product Planning',TO_TIMESTAMP('2024-01-17 16:26:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:17.979Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587803 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:17.981Z
/* DDL */  select update_Column_Translation_From_AD_Element(53268) 
;

-- Column: PP_Maturing_Candidates_v.PP_Order_Candidate_ID
-- Column: PP_Maturing_Candidates_v.PP_Order_Candidate_ID
-- 2024-01-17T14:26:18.761Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587805,580109,0,30,542385,'PP_Order_Candidate_ID',TO_TIMESTAMP('2024-01-17 16:26:18','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Produktionsdisposition',TO_TIMESTAMP('2024-01-17 16:26:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-17T14:26:18.762Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587805 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-17T14:26:18.764Z
/* DDL */  select update_Column_Translation_From_AD_Element(580109) 
;

-- Column: PP_Maturing_Candidates_v.HUStatus
-- Column: PP_Maturing_Candidates_v.HUStatus
-- 2024-01-17T14:38:37.300Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540478,Updated=TO_TIMESTAMP('2024-01-17 16:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587797
;

-- Column: PP_Maturing_Candidates_v.AD_Org_ID
-- Column: PP_Maturing_Candidates_v.AD_Org_ID
-- 2024-01-19T16:01:35.499Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587837,113,0,30,542385,'AD_Org_ID',TO_TIMESTAMP('2024-01-19 18:01:35','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','U',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-01-19 18:01:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-19T16:01:35.501Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587837 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-19T16:01:35.530Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: PP_Maturing_Candidates_v.AD_Client_ID
-- Column: PP_Maturing_Candidates_v.AD_Client_ID
-- 2024-01-19T16:01:36.131Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587838,102,0,30,542385,'AD_Client_ID',TO_TIMESTAMP('2024-01-19 18:01:36','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','U',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2024-01-19 18:01:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-19T16:01:36.133Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587838 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-19T16:01:36.135Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Value: PP_Order_Candidate_CreateMaturingCandidates
-- Classname: org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates
-- 2024-01-22T11:13:31.796Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585349,'Y','org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates','N',TO_TIMESTAMP('2024-01-22 13:13:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Update maturing candidates','json','Y','N','xls','Java',TO_TIMESTAMP('2024-01-22 13:13:31','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_Candidate_CreateMaturingCandidates')
;

-- 2024-01-22T11:13:31.798Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585349 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- 2024-01-22T11:13:46.326Z
UPDATE AD_Process_Trl SET Name='Update Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-22 13:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585349
;

-- Value: PP_Order_Candidate_CreateMaturingCandidates
-- Classname: org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates
-- 2024-01-22T11:13:47.720Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Update Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-22 13:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585349
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- 2024-01-22T11:13:47.714Z
UPDATE AD_Process_Trl SET Name='Update Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-22 13:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585349
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- 2024-01-22T11:14:01.030Z
UPDATE AD_Process_Trl SET Name='Update Reifende Kandidaten',Updated=TO_TIMESTAMP('2024-01-22 13:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585349
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- Table: PP_Order_Candidate
-- Tab: Reifedisposition(541756,EE01) -> Produktionsdisposition(547345,EE01)
-- Window: Reifedisposition(541756,EE01)
-- EntityType: D
-- 2024-01-22T11:15:34.030Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585349,null,541913,541456,541756,TO_TIMESTAMP('2024-01-22 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-01-22 13:15:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Column: PP_Maturing_Candidates_v.DateStartSchedule
-- Column: PP_Maturing_Candidates_v.DateStartSchedule
-- 2024-01-22T11:55:13.070Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587840,53281,0,16,542385,'DateStartSchedule',TO_TIMESTAMP('2024-01-22 13:55:12','YYYY-MM-DD HH24:MI:SS'),100,'U',29,'Y','Y','N','N','N','N','N','N','N','N','N','geplanter Beginn',TO_TIMESTAMP('2024-01-22 13:55:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-22T11:55:13.072Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587840 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-22T11:55:13.102Z
/* DDL */  select update_Column_Translation_From_AD_Element(53281) 
;

-- Column: PP_Maturing_Candidates_v.DatePromised
-- Column: PP_Maturing_Candidates_v.DatePromised
-- 2024-01-22T11:57:41.550Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587804
;

-- 2024-01-22T11:57:41.554Z
DELETE FROM AD_Column WHERE AD_Column_ID=587804
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- Table: PP_Maturing_Candidates_v
-- EntityType: D
-- 2024-01-22T14:10:19.211Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585349,542385,541457,TO_TIMESTAMP('2024-01-22 16:10:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-01-22 16:10:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- Table: PP_Maturing_Candidates_v
-- EntityType: D
-- 2024-01-22T14:24:30.682Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541457
;

-- Column: PP_Maturing_Candidates_v.M_AttributeSetInstance_ID
-- Column: PP_Maturing_Candidates_v.M_AttributeSetInstance_ID
-- 2024-01-22T15:38:20.061Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587841,2019,0,35,542385,'M_AttributeSetInstance_ID',TO_TIMESTAMP('2024-01-22 17:38:19','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','U',10,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','Y','N','N','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2024-01-22 17:38:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-22T15:38:20.063Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587841 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-22T15:38:20.066Z
/* DDL */  select update_Column_Translation_From_AD_Element(2019) 
;

-- Process: PP_Order_Candidate_CreateMaturingCandidates(org.eevolution.productioncandidate.process.PP_Order_Candidate_CreateMaturingCandidates)
-- Table: PP_Order_Candidate
-- Tab: Reifedisposition(541756,EE01) -> Produktionsdisposition(547345,EE01)
-- Window: Reifedisposition(541756,EE01)
-- EntityType: EE01
-- 2024-01-22T20:38:13.778Z
UPDATE AD_Table_Process SET EntityType='EE01', WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2024-01-22 22:38:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541456
;

-- 2024-01-22T20:41:29.078Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,0,585349,0,550109,TO_TIMESTAMP('2024-01-22 22:41:28','YYYY-MM-DD HH24:MI:SS'),100,'00 1 * * *','Runs nightly to create/update/delete maturing candidate','EE01',0,'D','Y','N',7,'N','PP_Order_Candidate_CreateMaturingCandidates','N','P','C','NEW',100,TO_TIMESTAMP('2024-01-22 22:41:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-22T21:08:51.417Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582918,0,'PP_Maturing_Candidates_v_ID',TO_TIMESTAMP('2024-01-22 23:08:51','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','PP_Maturing_Candidates_v','PP_Maturing_Candidates_v',TO_TIMESTAMP('2024-01-22 23:08:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-22T21:08:51.419Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582918 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Maturing_Candidates_v.PP_Maturing_Candidates_v_ID
-- Column: PP_Maturing_Candidates_v.PP_Maturing_Candidates_v_ID
-- 2024-01-22T21:08:51.754Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587842,582918,0,13,542385,'PP_Maturing_Candidates_v_ID',TO_TIMESTAMP('2024-01-22 23:08:51','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','Y','N','N','N','N','N','PP_Maturing_Candidates_v',TO_TIMESTAMP('2024-01-22 23:08:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-22T21:08:51.755Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587842 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-22T21:08:51.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(582918) 
;

