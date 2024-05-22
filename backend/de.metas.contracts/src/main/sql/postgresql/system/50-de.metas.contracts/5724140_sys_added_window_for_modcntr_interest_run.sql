-- Run mode: SWING_CLIENT

-- Table: ModCntr_Interest_V
-- 2024-05-21T20:39:12.682Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542412,'N',TO_TIMESTAMP('2024-05-21 23:39:12.453','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','Y','N','N','N','N','N','N','N','Y',0,'ModCntr_Interest_V','NP','L','ModCntr_Interest_V','DTI',TO_TIMESTAMP('2024-05-21 23:39:12.453','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:12.684Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542412 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Column: ModCntr_Interest_V.ModCntr_Interest_Run_ID
-- 2024-05-21T20:39:38.835Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588289,583097,0,30,542412,'ModCntr_Interest_Run_ID',TO_TIMESTAMP('2024-05-21 23:39:38.656','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',10,'Y','Y','N','N','N','N','N','N','N','N','N','Zinsberechnungslauf',TO_TIMESTAMP('2024-05-21 23:39:38.656','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:38.837Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588289 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:38.839Z
/* DDL */  select update_Column_Translation_From_AD_Element(583097)
;

-- Column: ModCntr_Interest_V.C_Flatrate_Term_ID
-- 2024-05-21T20:39:39.322Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588290,541447,0,30,542412,'C_Flatrate_Term_ID',TO_TIMESTAMP('2024-05-21 23:39:39.108','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',10,'Y','Y','N','N','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2024-05-21 23:39:39.108','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:39.323Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588290 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:39.325Z
/* DDL */  select update_Column_Translation_From_AD_Element(541447)
;

-- Column: ModCntr_Interest_V.Bill_BPartner_Value
-- 2024-05-21T20:39:39.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588291,581361,0,10,542412,'Bill_BPartner_Value',TO_TIMESTAMP('2024-05-21 23:39:39.584','YYYY-MM-DD HH24:MI:SS.US'),100,'','de.metas.contracts',40,'Y','Y','N','N','N','N','N','N','N','N','N','Rechnungspartner Suchschlüssel',TO_TIMESTAMP('2024-05-21 23:39:39.584','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:39.684Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588291 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:39.802Z
/* DDL */  select update_Column_Translation_From_AD_Element(581361)
;

-- Column: ModCntr_Interest_V.Bill_BPartner_Name
-- 2024-05-21T20:39:40.131Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588292,542805,0,10,542412,'Bill_BPartner_Name',TO_TIMESTAMP('2024-05-21 23:39:40.05','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',100,'Y','Y','N','N','N','N','N','N','N','N','N','Name Rechnungspartner',TO_TIMESTAMP('2024-05-21 23:39:40.05','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:40.132Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588292 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:40.259Z
/* DDL */  select update_Column_Translation_From_AD_Element(542805)
;

-- Column: ModCntr_Interest_V.InvoicingGroup_Name
-- 2024-05-21T20:39:40.626Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588293,583118,0,10,542412,'InvoicingGroup_Name',TO_TIMESTAMP('2024-05-21 23:39:40.522','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',60,'Y','Y','N','N','N','N','N','N','N','N','N','Name der Abrechnungszeilengruppe',TO_TIMESTAMP('2024-05-21 23:39:40.522','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:40.627Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588293 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:40.739Z
/* DDL */  select update_Column_Translation_From_AD_Element(583118)
;

-- Column: ModCntr_Interest_V.InterimInvoice_documentNo
-- 2024-05-21T20:39:41.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588294,583119,0,10,542412,'InterimInvoice_documentNo',TO_TIMESTAMP('2024-05-21 23:39:41.005','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',30,'Y','Y','N','N','N','N','N','N','N','N','N','Vorfinanzierungsnummer',TO_TIMESTAMP('2024-05-21 23:39:41.005','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:41.094Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588294 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:41.208Z
/* DDL */  select update_Column_Translation_From_AD_Element(583119)
;

-- Column: ModCntr_Interest_V.InterimDate
-- 2024-05-21T20:39:41.553Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588295,583086,0,16,542412,'InterimDate',TO_TIMESTAMP('2024-05-21 23:39:41.463','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',29,'Y','Y','N','N','N','N','N','N','N','N','N','Datum Vorfinanzierung',TO_TIMESTAMP('2024-05-21 23:39:41.463','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:41.554Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588295 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:41.694Z
/* DDL */  select update_Column_Translation_From_AD_Element(583086)
;

-- Column: ModCntr_Interest_V.BillingDate
-- 2024-05-21T20:39:42.096Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588296,583087,0,16,542412,'BillingDate',TO_TIMESTAMP('2024-05-21 23:39:42.012','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',29,'Y','Y','N','N','N','N','N','N','N','N','N','Abrechnungsdatum',TO_TIMESTAMP('2024-05-21 23:39:42.012','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:42.097Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588296 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:42.219Z
/* DDL */  select update_Column_Translation_From_AD_Element(583087)
;

-- Column: ModCntr_Interest_V.TotalInterest
-- 2024-05-21T20:39:42.579Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588297,583091,0,22,542412,'TotalInterest',TO_TIMESTAMP('2024-05-21 23:39:42.481','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Gesamtzins',TO_TIMESTAMP('2024-05-21 23:39:42.481','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:42.580Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588297 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:42.707Z
/* DDL */  select update_Column_Translation_From_AD_Element(583091)
;

-- Column: ModCntr_Interest_V.AddInterestDays
-- 2024-05-21T20:39:43.045Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588298,583089,0,11,542412,'AddInterestDays',TO_TIMESTAMP('2024-05-21 23:39:42.956','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',10,'Y','Y','N','N','N','N','N','N','N','N','N','Zusätzliche Zinstage',TO_TIMESTAMP('2024-05-21 23:39:42.956','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:43.046Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588298 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:43.167Z
/* DDL */  select update_Column_Translation_From_AD_Element(583089)
;

-- Column: ModCntr_Interest_V.InterestRate
-- 2024-05-21T20:39:43.504Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588299,583090,0,22,542412,'InterestRate',TO_TIMESTAMP('2024-05-21 23:39:43.419','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Zinssatz',TO_TIMESTAMP('2024-05-21 23:39:43.419','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:43.505Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588299 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:43.641Z
/* DDL */  select update_Column_Translation_From_AD_Element(583090)
;

-- Column: ModCntr_Interest_V.DateTrx
-- 2024-05-21T20:39:44Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588300,1297,0,16,542412,'DateTrx',TO_TIMESTAMP('2024-05-21 23:39:43.896','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','de.metas.contracts',29,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','Y','N','N','N','N','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2024-05-21 23:39:43.896','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:44.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588300 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:44.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297)
;

-- Column: ModCntr_Interest_V.InterimAmt
-- 2024-05-21T20:39:44.481Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588301,583100,0,12,542412,'InterimAmt',TO_TIMESTAMP('2024-05-21 23:39:44.396','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Vorfinanzierungsbetrag',TO_TIMESTAMP('2024-05-21 23:39:44.396','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:44.483Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588301 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:44.602Z
/* DDL */  select update_Column_Translation_From_AD_Element(583100)
;

-- Column: ModCntr_Interest_V.MatchedAmt
-- 2024-05-21T20:39:44.950Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588302,583099,0,12,542412,'MatchedAmt',TO_TIMESTAMP('2024-05-21 23:39:44.864','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Übereinstimmender Betrag',TO_TIMESTAMP('2024-05-21 23:39:44.864','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:44.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588302 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:45.068Z
/* DDL */  select update_Column_Translation_From_AD_Element(583099)
;

-- Column: ModCntr_Interest_V.TotalAmt
-- 2024-05-21T20:39:45.424Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588303,1539,0,12,542412,'TotalAmt',TO_TIMESTAMP('2024-05-21 23:39:45.321','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Gesamtbetrag',TO_TIMESTAMP('2024-05-21 23:39:45.321','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:45.426Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588303 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:45.553Z
/* DDL */  select update_Column_Translation_From_AD_Element(1539)
;

-- Column: ModCntr_Interest_V.InterestDays
-- 2024-05-21T20:39:45.997Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588304,583101,0,11,542412,'InterestDays',TO_TIMESTAMP('2024-05-21 23:39:45.905','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',10,'Y','Y','N','N','N','N','N','N','N','N','N','Zinstage',TO_TIMESTAMP('2024-05-21 23:39:45.905','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:45.998Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588304 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:46.120Z
/* DDL */  select update_Column_Translation_From_AD_Element(583101)
;

-- Column: ModCntr_Interest_V.InterestScore
-- 2024-05-21T20:39:46.491Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588305,583107,0,22,542412,'InterestScore',TO_TIMESTAMP('2024-05-21 23:39:46.411','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Zinsnummer',TO_TIMESTAMP('2024-05-21 23:39:46.411','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:46.492Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588305 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:46.615Z
/* DDL */  select update_Column_Translation_From_AD_Element(583107)
;

-- Column: ModCntr_Interest_V.FinalInterest
-- 2024-05-21T20:39:46.949Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588306,583102,0,22,542412,'FinalInterest',TO_TIMESTAMP('2024-05-21 23:39:46.869','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',14,'Y','Y','N','N','N','N','N','N','N','N','N','Endgültige Zinsen',TO_TIMESTAMP('2024-05-21 23:39:46.869','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-21T20:39:46.950Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588306 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-21T20:39:47.071Z
/* DDL */  select update_Column_Translation_From_AD_Element(583102)
;

-- Column: ModCntr_Interest_V.ModCntr_Interest_Run_ID
-- 2024-05-21T20:40:32.882Z
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2024-05-21 23:40:32.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588289
;

-- 2024-05-22T07:14:02.294Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583120,0,'C_FinalInvoice_ID',TO_TIMESTAMP('2024-05-22 10:14:02.018','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Schlussabrechnung','Schlussabrechnung',TO_TIMESTAMP('2024-05-22 10:14:02.018','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T07:14:02.309Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583120 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_Interest_V.C_FinalInvoice_ID
-- 2024-05-22T08:19:43.571Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588307,583120,0,30,336,542412,'C_FinalInvoice_ID',TO_TIMESTAMP('2024-05-22 11:19:43.331','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Schlussabrechnung',0,0,TO_TIMESTAMP('2024-05-22 11:19:43.331','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T08:19:43.572Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588307 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T08:19:43.602Z
/* DDL */  select update_Column_Translation_From_AD_Element(583120)
;

-- Column: ModCntr_Interest_V.ProductName
-- 2024-05-22T08:20:32.367Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588308,2659,0,10,542412,'ProductName',TO_TIMESTAMP('2024-05-22 11:20:32.246','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name des Produktes','de.metas.contracts',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Produktname',0,0,TO_TIMESTAMP('2024-05-22 11:20:32.246','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T08:20:32.369Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588308 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T08:20:32.371Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659)
;

-- Column: ModCntr_Interest_V.ModularContractHandlerType
-- 2024-05-22T08:21:33.641Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588309,582747,0,17,541838,542412,'ModularContractHandlerType',TO_TIMESTAMP('2024-05-22 11:21:33.455','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Handlertyp für modularen Vertrag',0,0,TO_TIMESTAMP('2024-05-22 11:21:33.455','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T08:21:33.642Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588309 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T08:21:33.645Z
/* DDL */  select update_Column_Translation_From_AD_Element(582747)
;

-- Window: Zinsberechnungslauf, InternalName=null
-- 2024-05-22T08:35:12.867Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583097,0,541801,TO_TIMESTAMP('2024-05-22 11:35:12.725','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','Y','N','N','N','N','Zinsberechnungslauf','N',TO_TIMESTAMP('2024-05-22 11:35:12.725','YYYY-MM-DD HH24:MI:SS.US'),100,'Q',0,0,100)
;

-- 2024-05-22T08:35:12.869Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541801 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-05-22T08:35:12.871Z
/* DDL */  select update_window_translation_from_ad_element(583097)
;

-- 2024-05-22T08:35:12.881Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541801
;

-- 2024-05-22T08:35:12.885Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541801)
;

-- Tab: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf
-- Table: ModCntr_Interest_Run
-- 2024-05-22T08:36:18.093Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583097,0,547545,542409,541801,'Y',TO_TIMESTAMP('2024-05-22 11:36:17.929','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','ModCntr_Interest_Run','Y','N','Y','Y','Y','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Zinsberechnungslauf','N',10,0,TO_TIMESTAMP('2024-05-22 11:36:17.929','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:18.096Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547545 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-05-22T08:36:18.097Z
/* DDL */  select update_tab_translation_from_ad_element(583097)
;

-- 2024-05-22T08:36:18.100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547545)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Mandant
-- Column: ModCntr_Interest_Run.AD_Client_ID
-- 2024-05-22T08:36:56.947Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588208,728721,0,547545,TO_TIMESTAMP('2024-05-22 11:36:56.769','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-05-22 11:36:56.769','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:56.949Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:56.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-05-22T08:36:57.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728721
;

-- 2024-05-22T08:36:57.985Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728721)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Organisation
-- Column: ModCntr_Interest_Run.AD_Org_ID
-- 2024-05-22T08:36:58.098Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588209,728722,0,547545,TO_TIMESTAMP('2024-05-22 11:36:58.001','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-05-22 11:36:58.001','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:58.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:58.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-05-22T08:36:58.451Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728722
;

-- 2024-05-22T08:36:58.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728722)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Aktiv
-- Column: ModCntr_Interest_Run.IsActive
-- 2024-05-22T08:36:58.550Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588212,728723,0,547545,TO_TIMESTAMP('2024-05-22 11:36:58.454','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-05-22 11:36:58.454','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:58.551Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728723 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:58.552Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-05-22T08:36:58.658Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728723
;

-- 2024-05-22T08:36:58.658Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728723)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Zinsberechnungslauf
-- Column: ModCntr_Interest_Run.ModCntr_Interest_Run_ID
-- 2024-05-22T08:36:58.746Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588215,728724,0,547545,TO_TIMESTAMP('2024-05-22 11:36:58.66','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinsberechnungslauf',TO_TIMESTAMP('2024-05-22 11:36:58.66','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:58.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728724 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:58.749Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583097)
;

-- 2024-05-22T08:36:58.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728724
;

-- 2024-05-22T08:36:58.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728724)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Gesamtzins
-- Column: ModCntr_Interest_Run.TotalInterest
-- 2024-05-22T08:36:58.847Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588216,728725,0,547545,TO_TIMESTAMP('2024-05-22 11:36:58.753','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Gesamtzins',TO_TIMESTAMP('2024-05-22 11:36:58.753','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:58.848Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728725 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:58.849Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583091)
;

-- 2024-05-22T08:36:58.850Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728725
;

-- 2024-05-22T08:36:58.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728725)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Datum Vorfinanzierung
-- Column: ModCntr_Interest_Run.InterimDate
-- 2024-05-22T08:36:58.940Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588218,728726,0,547545,TO_TIMESTAMP('2024-05-22 11:36:58.852','YYYY-MM-DD HH24:MI:SS.US'),100,7,'de.metas.contracts','Y','N','N','N','N','N','N','N','Datum Vorfinanzierung',TO_TIMESTAMP('2024-05-22 11:36:58.852','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:58.941Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728726 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:58.942Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583086)
;

-- 2024-05-22T08:36:58.945Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728726
;

-- 2024-05-22T08:36:58.946Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728726)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Abrechnungsdatum
-- Column: ModCntr_Interest_Run.BillingDate
-- 2024-05-22T08:36:59.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588219,728727,0,547545,TO_TIMESTAMP('2024-05-22 11:36:58.949','YYYY-MM-DD HH24:MI:SS.US'),100,7,'de.metas.contracts','Y','N','N','N','N','N','N','N','Abrechnungsdatum',TO_TIMESTAMP('2024-05-22 11:36:58.949','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:59.041Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728727 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:59.041Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583087)
;

-- 2024-05-22T08:36:59.043Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728727
;

-- 2024-05-22T08:36:59.044Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728727)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Rechnungsgruppe
-- Column: ModCntr_Interest_Run.ModCntr_InvoicingGroup_ID
-- 2024-05-22T08:36:59.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588238,728728,0,547545,TO_TIMESTAMP('2024-05-22 11:36:59.045','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Rechnungsgruppe',TO_TIMESTAMP('2024-05-22 11:36:59.045','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:59.124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728728 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:59.125Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582647)
;

-- 2024-05-22T08:36:59.128Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728728
;

-- 2024-05-22T08:36:59.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728728)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> Währung
-- Column: ModCntr_Interest_Run.C_Currency_ID
-- 2024-05-22T08:36:59.219Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588266,728729,0,547545,TO_TIMESTAMP('2024-05-22 11:36:59.13','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',10,'de.metas.contracts','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2024-05-22 11:36:59.13','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T08:36:59.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728729 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T08:36:59.221Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2024-05-22T08:36:59.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728729
;

-- 2024-05-22T08:36:59.309Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728729)
;

-- Column: ModCntr_Interest_V.Qty
-- 2024-05-22T09:10:05.123Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588310,526,0,29,542412,'Qty',TO_TIMESTAMP('2024-05-22 12:10:04.994','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Menge','de.metas.contracts',0,10,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Menge',0,0,TO_TIMESTAMP('2024-05-22 12:10:04.994','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T09:10:05.125Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588310 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T09:10:05.127Z
/* DDL */  select update_Column_Translation_From_AD_Element(526)
;

-- Tab: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts)
-- UI Section: main
-- 2024-05-22T09:23:13.689Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547545,546129,TO_TIMESTAMP('2024-05-22 12:23:13.456','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-05-22 12:23:13.456','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-05-22T09:23:13.692Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546129 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Column: ModCntr_Interest_V.UOM
-- 2024-05-22T09:29:07.952Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588311,577544,0,10,542412,'UOM',TO_TIMESTAMP('2024-05-22 12:29:07.801','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,60,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Mengeneinheit',0,0,TO_TIMESTAMP('2024-05-22 12:29:07.801','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T09:29:07.953Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588311 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T09:29:07.955Z
/* DDL */  select update_Column_Translation_From_AD_Element(577544)
;

-- UI Section: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main
-- UI Column: 10
-- 2024-05-22T10:00:05.823Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547486,546129,TO_TIMESTAMP('2024-05-22 13:00:05.683','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-05-22 13:00:05.683','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 10
-- UI Element Group:
-- 2024-05-22T10:00:24.322Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547486,551832,TO_TIMESTAMP('2024-05-22 13:00:24.188','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',' ',10,'primary',TO_TIMESTAMP('2024-05-22 13:00:24.188','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 10 ->  .Datum Vorfinanzierung
-- Column: ModCntr_Interest_Run.InterimDate
-- 2024-05-22T10:01:09.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728726,0,547545,551832,624742,'F',TO_TIMESTAMP('2024-05-22 13:01:09.13','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Datum Vorfinanzierung',10,0,0,TO_TIMESTAMP('2024-05-22 13:01:09.13','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 10 ->  .Abrechnungsdatum
-- Column: ModCntr_Interest_Run.BillingDate
-- 2024-05-22T10:01:18.317Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728727,0,547545,551832,624743,'F',TO_TIMESTAMP('2024-05-22 13:01:18.186','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abrechnungsdatum',20,0,0,TO_TIMESTAMP('2024-05-22 13:01:18.186','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 10 ->  .Gesamtzins
-- Column: ModCntr_Interest_Run.TotalInterest
-- 2024-05-22T10:02:25.729Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728725,0,547545,551832,624744,'F',TO_TIMESTAMP('2024-05-22 13:02:25.592','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Gesamtzins',30,0,0,TO_TIMESTAMP('2024-05-22 13:02:25.592','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 10 ->  .Währung
-- Column: ModCntr_Interest_Run.C_Currency_ID
-- 2024-05-22T10:02:34.520Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728729,0,547545,551832,624745,'F',TO_TIMESTAMP('2024-05-22 13:02:34.405','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',40,0,0,TO_TIMESTAMP('2024-05-22 13:02:34.405','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main
-- UI Column: 20
-- 2024-05-22T10:02:42.660Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547487,546129,TO_TIMESTAMP('2024-05-22 13:02:42.552','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2024-05-22 13:02:42.552','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 20
-- UI Element Group: flags
-- 2024-05-22T10:02:52.923Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547487,551833,TO_TIMESTAMP('2024-05-22 13:02:52.793','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,'primary',TO_TIMESTAMP('2024-05-22 13:02:52.793','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Interest_Run.IsActive
-- 2024-05-22T10:03:02.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728723,0,547545,551833,624746,'F',TO_TIMESTAMP('2024-05-22 13:03:02.171','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-05-22 13:03:02.171','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 20
-- UI Element Group: group
-- 2024-05-22T10:03:16.339Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547487,551834,TO_TIMESTAMP('2024-05-22 13:03:16.207','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','group',20,TO_TIMESTAMP('2024-05-22 13:03:16.207','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 20
-- UI Element Group: org
-- 2024-05-22T10:03:23.400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547487,551835,TO_TIMESTAMP('2024-05-22 13:03:23.257','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',30,TO_TIMESTAMP('2024-05-22 13:03:23.257','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 20 -> group.Rechnungsgruppe
-- Column: ModCntr_Interest_Run.ModCntr_InvoicingGroup_ID
-- 2024-05-22T10:03:39.168Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728728,0,547545,551834,624747,'F',TO_TIMESTAMP('2024-05-22 13:03:39.041','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungsgruppe',10,0,0,TO_TIMESTAMP('2024-05-22 13:03:39.041','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zinsberechnungslauf(547545,de.metas.contracts) -> main -> 20 -> org.Organisation
-- Column: ModCntr_Interest_Run.AD_Org_ID
-- 2024-05-22T10:03:52.053Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728722,0,547545,551835,624748,'F',TO_TIMESTAMP('2024-05-22 13:03:51.929','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2024-05-22 13:03:51.929','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Tab: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins
-- Table: ModCntr_Interest_V
-- 2024-05-22T10:51:37.238Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583098,0,547546,542412,541801,'Y',TO_TIMESTAMP('2024-05-22 13:51:37.022','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','ModCntr_Interest_V','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Zins','N',20,0,TO_TIMESTAMP('2024-05-22 13:51:37.022','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:37.240Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547546 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-05-22T10:51:37.241Z
/* DDL */  select update_tab_translation_from_ad_element(583098)
;

-- 2024-05-22T10:51:37.244Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547546)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Zinsberechnungslauf
-- Column: ModCntr_Interest_V.ModCntr_Interest_Run_ID
-- 2024-05-22T10:51:57.635Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588289,728730,0,547546,TO_TIMESTAMP('2024-05-22 13:51:57.506','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinsberechnungslauf',TO_TIMESTAMP('2024-05-22 13:51:57.506','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:57.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728730 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:57.638Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583097)
;

-- 2024-05-22T10:51:57.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728730
;

-- 2024-05-22T10:51:57.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728730)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Pauschale - Vertragsperiode
-- Column: ModCntr_Interest_V.C_Flatrate_Term_ID
-- 2024-05-22T10:51:57.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588290,728731,0,547546,TO_TIMESTAMP('2024-05-22 13:51:57.643','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2024-05-22 13:51:57.643','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:57.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728731 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:57.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447)
;

-- 2024-05-22T10:51:57.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728731
;

-- 2024-05-22T10:51:57.749Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728731)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Rechnungspartner Suchschlüssel
-- Column: ModCntr_Interest_V.Bill_BPartner_Value
-- 2024-05-22T10:51:57.838Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588291,728732,0,547546,TO_TIMESTAMP('2024-05-22 13:51:57.751','YYYY-MM-DD HH24:MI:SS.US'),100,'',40,'de.metas.contracts','Y','N','N','N','N','N','N','N','Rechnungspartner Suchschlüssel',TO_TIMESTAMP('2024-05-22 13:51:57.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:57.839Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728732 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:57.840Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581361)
;

-- 2024-05-22T10:51:57.843Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728732
;

-- 2024-05-22T10:51:57.844Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728732)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Name Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_Name
-- 2024-05-22T10:51:57.934Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588292,728733,0,547546,TO_TIMESTAMP('2024-05-22 13:51:57.846','YYYY-MM-DD HH24:MI:SS.US'),100,100,'de.metas.contracts','Y','N','N','N','N','N','N','N','Name Rechnungspartner',TO_TIMESTAMP('2024-05-22 13:51:57.846','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:57.934Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:57.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542805)
;

-- 2024-05-22T10:51:57.939Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728733
;

-- 2024-05-22T10:51:57.939Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728733)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Name der Abrechnungszeilengruppe
-- Column: ModCntr_Interest_V.InvoicingGroup_Name
-- 2024-05-22T10:51:58.027Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588293,728734,0,547546,TO_TIMESTAMP('2024-05-22 13:51:57.941','YYYY-MM-DD HH24:MI:SS.US'),100,60,'de.metas.contracts','Y','N','N','N','N','N','N','N','Name der Abrechnungszeilengruppe',TO_TIMESTAMP('2024-05-22 13:51:57.941','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583118)
;

-- 2024-05-22T10:51:58.032Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728734
;

-- 2024-05-22T10:51:58.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728734)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Vorfinanzierungsnummer
-- Column: ModCntr_Interest_V.InterimInvoice_documentNo
-- 2024-05-22T10:51:58.118Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588294,728735,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.034','YYYY-MM-DD HH24:MI:SS.US'),100,30,'de.metas.contracts','Y','N','N','N','N','N','N','N','Vorfinanzierungsnummer',TO_TIMESTAMP('2024-05-22 13:51:58.034','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.119Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728735 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583119)
;

-- 2024-05-22T10:51:58.123Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728735
;

-- 2024-05-22T10:51:58.124Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728735)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Datum Vorfinanzierung
-- Column: ModCntr_Interest_V.InterimDate
-- 2024-05-22T10:51:58.203Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588295,728736,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.126','YYYY-MM-DD HH24:MI:SS.US'),100,29,'de.metas.contracts','Y','N','N','N','N','N','N','N','Datum Vorfinanzierung',TO_TIMESTAMP('2024-05-22 13:51:58.126','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.204Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728736 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.205Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583086)
;

-- 2024-05-22T10:51:58.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728736
;

-- 2024-05-22T10:51:58.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728736)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Abrechnungsdatum
-- Column: ModCntr_Interest_V.BillingDate
-- 2024-05-22T10:51:58.300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588296,728737,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.208','YYYY-MM-DD HH24:MI:SS.US'),100,29,'de.metas.contracts','Y','N','N','N','N','N','N','N','Abrechnungsdatum',TO_TIMESTAMP('2024-05-22 13:51:58.208','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.301Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728737 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.301Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583087)
;

-- 2024-05-22T10:51:58.303Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728737
;

-- 2024-05-22T10:51:58.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728737)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Gesamtzins
-- Column: ModCntr_Interest_V.TotalInterest
-- 2024-05-22T10:51:58.387Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588297,728738,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.305','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Gesamtzins',TO_TIMESTAMP('2024-05-22 13:51:58.305','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.388Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728738 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583091)
;

-- 2024-05-22T10:51:58.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728738
;

-- 2024-05-22T10:51:58.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728738)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Zusätzliche Zinstage
-- Column: ModCntr_Interest_V.AddInterestDays
-- 2024-05-22T10:51:58.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588298,728739,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.393','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zusätzliche Zinstage',TO_TIMESTAMP('2024-05-22 13:51:58.393','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.481Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728739 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583089)
;

-- 2024-05-22T10:51:58.484Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728739
;

-- 2024-05-22T10:51:58.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728739)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Zinssatz
-- Column: ModCntr_Interest_V.InterestRate
-- 2024-05-22T10:51:58.557Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588299,728740,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.486','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinssatz',TO_TIMESTAMP('2024-05-22 13:51:58.486','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.558Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728740 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583090)
;

-- 2024-05-22T10:51:58.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728740
;

-- 2024-05-22T10:51:58.562Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728740)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Vorgangsdatum
-- Column: ModCntr_Interest_V.DateTrx
-- 2024-05-22T10:51:58.640Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588300,728741,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.563','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum',29,'de.metas.contracts','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2024-05-22 13:51:58.563','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.641Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728741 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.642Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297)
;

-- 2024-05-22T10:51:58.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728741
;

-- 2024-05-22T10:51:58.648Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728741)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Vorfinanzierungsbetrag
-- Column: ModCntr_Interest_V.InterimAmt
-- 2024-05-22T10:51:58.723Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588301,728742,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.649','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Vorfinanzierungsbetrag',TO_TIMESTAMP('2024-05-22 13:51:58.649','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.724Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728742 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.724Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583100)
;

-- 2024-05-22T10:51:58.727Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728742
;

-- 2024-05-22T10:51:58.727Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728742)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Übereinstimmender Betrag
-- Column: ModCntr_Interest_V.MatchedAmt
-- 2024-05-22T10:51:58.802Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588302,728743,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.729','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Übereinstimmender Betrag',TO_TIMESTAMP('2024-05-22 13:51:58.729','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728743 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583099)
;

-- 2024-05-22T10:51:58.805Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728743
;

-- 2024-05-22T10:51:58.806Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728743)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Gesamtbetrag
-- Column: ModCntr_Interest_V.TotalAmt
-- 2024-05-22T10:51:58.882Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588303,728744,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.807','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Gesamtbetrag',TO_TIMESTAMP('2024-05-22 13:51:58.807','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.882Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728744 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1539)
;

-- 2024-05-22T10:51:58.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728744
;

-- 2024-05-22T10:51:58.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728744)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Zinstage
-- Column: ModCntr_Interest_V.InterestDays
-- 2024-05-22T10:51:58.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588304,728745,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.89','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinstage',TO_TIMESTAMP('2024-05-22 13:51:58.89','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:58.973Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728745 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:58.973Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583101)
;

-- 2024-05-22T10:51:58.976Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728745
;

-- 2024-05-22T10:51:58.976Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728745)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Zinsnummer
-- Column: ModCntr_Interest_V.InterestScore
-- 2024-05-22T10:51:59.059Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588305,728746,0,547546,TO_TIMESTAMP('2024-05-22 13:51:58.978','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Zinsnummer',TO_TIMESTAMP('2024-05-22 13:51:58.978','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.060Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728746 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.060Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583107)
;

-- 2024-05-22T10:51:59.062Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728746
;

-- 2024-05-22T10:51:59.063Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728746)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Endgültige Zinsen
-- Column: ModCntr_Interest_V.FinalInterest
-- 2024-05-22T10:51:59.140Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588306,728747,0,547546,TO_TIMESTAMP('2024-05-22 13:51:59.064','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Endgültige Zinsen',TO_TIMESTAMP('2024-05-22 13:51:59.064','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.141Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728747 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583102)
;

-- 2024-05-22T10:51:59.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728747
;

-- 2024-05-22T10:51:59.144Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728747)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Schlussabrechnung
-- Column: ModCntr_Interest_V.C_FinalInvoice_ID
-- 2024-05-22T10:51:59.219Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588307,728748,0,547546,TO_TIMESTAMP('2024-05-22 13:51:59.145','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Schlussabrechnung',TO_TIMESTAMP('2024-05-22 13:51:59.145','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728748 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.221Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583120)
;

-- 2024-05-22T10:51:59.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728748
;

-- 2024-05-22T10:51:59.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728748)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Produktname
-- Column: ModCntr_Interest_V.ProductName
-- 2024-05-22T10:51:59.314Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588308,728749,0,547546,TO_TIMESTAMP('2024-05-22 13:51:59.224','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes',255,'de.metas.contracts','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2024-05-22 13:51:59.224','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.315Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728749 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.315Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2024-05-22T10:51:59.320Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728749
;

-- 2024-05-22T10:51:59.320Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728749)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Handlertyp für modularen Vertrag
-- Column: ModCntr_Interest_V.ModularContractHandlerType
-- 2024-05-22T10:51:59.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588309,728750,0,547546,TO_TIMESTAMP('2024-05-22 13:51:59.322','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.contracts','Y','N','N','N','N','N','N','N','Handlertyp für modularen Vertrag',TO_TIMESTAMP('2024-05-22 13:51:59.322','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582747)
;

-- 2024-05-22T10:51:59.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728750
;

-- 2024-05-22T10:51:59.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728750)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Menge
-- Column: ModCntr_Interest_V.Qty
-- 2024-05-22T10:51:59.488Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588310,728751,0,547546,TO_TIMESTAMP('2024-05-22 13:51:59.399','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge',10,'de.metas.contracts','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2024-05-22 13:51:59.399','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728751 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.490Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2024-05-22T10:51:59.548Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728751
;

-- 2024-05-22T10:51:59.548Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728751)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Mengeneinheit
-- Column: ModCntr_Interest_V.UOM
-- 2024-05-22T10:51:59.630Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588311,728752,0,547546,TO_TIMESTAMP('2024-05-22 13:51:59.551','YYYY-MM-DD HH24:MI:SS.US'),100,60,'de.metas.contracts','Y','N','N','N','N','N','N','N','Mengeneinheit',TO_TIMESTAMP('2024-05-22 13:51:59.551','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T10:51:59.631Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728752 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T10:51:59.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577544)
;

-- 2024-05-22T10:51:59.634Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728752
;

-- 2024-05-22T10:51:59.634Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728752)
;

-- Column: ModCntr_Interest_V.ProductValue
-- 2024-05-22T11:02:20.327Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588312,1675,0,10,542412,'ProductValue',TO_TIMESTAMP('2024-05-22 14:02:20.146','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID','de.metas.contracts',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Produktschlüssel',0,0,TO_TIMESTAMP('2024-05-22 14:02:20.146','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T11:02:20.329Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588312 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T11:02:20.331Z
/* DDL */  select update_Column_Translation_From_AD_Element(1675)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Produktschlüssel
-- Column: ModCntr_Interest_V.ProductValue
-- 2024-05-22T11:03:58.008Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588312,728753,0,547546,0,TO_TIMESTAMP('2024-05-22 14:03:57.853','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt-Identifikator; "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID',0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Produktschlüssel',0,10,0,1,1,TO_TIMESTAMP('2024-05-22 14:03:57.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T11:03:58.009Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728753 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T11:03:58.010Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1675)
;

-- 2024-05-22T11:03:58.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728753
;

-- 2024-05-22T11:03:58.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728753)
;

-- Tab: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts)
-- UI Section:
-- 2024-05-22T11:19:25.551Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547546,546130,TO_TIMESTAMP('2024-05-22 14:19:25.422','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-05-22 14:19:25.422','YYYY-MM-DD HH24:MI:SS.US'),100) RETURNING Value
;

-- 2024-05-22T11:19:25.552Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546130 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028
-- UI Column: 10
-- 2024-05-22T11:19:32.702Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547488,546130,TO_TIMESTAMP('2024-05-22 14:19:32.581','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-05-22 14:19:32.581','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10
-- UI Element Group: main
-- 2024-05-22T11:19:44.246Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547488,551836,TO_TIMESTAMP('2024-05-22 14:19:44.107','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2024-05-22 14:19:44.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Schlussabrechnung
-- Column: ModCntr_Interest_V.C_FinalInvoice_ID
-- 2024-05-22T11:34:39.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728748,0,547546,551836,624749,'F',TO_TIMESTAMP('2024-05-22 14:34:39.083','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Schlussabrechnung',10,0,0,TO_TIMESTAMP('2024-05-22 14:34:39.083','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Produktname
-- Column: ModCntr_Interest_V.ProductName
-- 2024-05-22T12:27:49.146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728749,0,547546,551836,624750,'F',TO_TIMESTAMP('2024-05-22 15:27:48.895','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',20,0,0,TO_TIMESTAMP('2024-05-22 15:27:48.895','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Interest_V.Name
-- 2024-05-22T12:28:46.794Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588313,469,0,10,542412,'Name',TO_TIMESTAMP('2024-05-22 15:28:46.672','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.contracts',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N',0,'Name',0,1,TO_TIMESTAMP('2024-05-22 15:28:46.672','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T12:28:46.795Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588313 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T12:28:46.797Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Produktname
-- 2024-05-22T12:28:53.749Z
UPDATE AD_UI_Element SET AD_Field_ID=NULL,Updated=TO_TIMESTAMP('2024-05-22 15:28:53.749','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624750
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Name
-- Column: ModCntr_Interest_V.Name
-- 2024-05-22T12:29:05.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588313,728754,0,547546,0,TO_TIMESTAMP('2024-05-22 15:29:05.038','YYYY-MM-DD HH24:MI:SS.US'),100,'',0,'de.metas.contracts','',0,'Y','Y','Y','N','N','N','N','N','N','Name',0,20,0,1,1,TO_TIMESTAMP('2024-05-22 15:29:05.038','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T12:29:05.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T12:29:05.175Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2024-05-22T12:29:05.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728754
;

-- 2024-05-22T12:29:05.324Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728754)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name
-- Column: ModCntr_Interest_V.Name
-- 2024-05-22T12:43:52.349Z
UPDATE AD_UI_Element SET AD_Field_ID=728754, Description='', Name='Name',Updated=TO_TIMESTAMP('2024-05-22 15:43:52.349','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624750
;

-- Table: ModCntr_Interest_V
-- 2024-05-22T12:45:09.390Z
UPDATE AD_Table SET AD_Window_ID=541801, PO_Window_ID=541801,Updated=TO_TIMESTAMP('2024-05-22 15:45:09.388','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542412
;

-- Table: ModCntr_Interest_V
-- 2024-05-22T12:45:31.637Z
UPDATE AD_Table SET AD_Window_ID=NULL, PO_Window_ID=NULL,Updated=TO_TIMESTAMP('2024-05-22 15:45:31.636','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542412
;

-- Table: ModCntr_Interest_Run
-- 2024-05-22T12:45:49.727Z
UPDATE AD_Table SET AD_Window_ID=541801, PO_Window_ID=541801,Updated=TO_TIMESTAMP('2024-05-22 15:45:49.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542409
;

-- Column: ModCntr_Interest_Run.ModCntr_InvoicingGroup_ID
-- 2024-05-22T12:46:31.153Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-22 15:46:31.153','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588238
;

-- Column: ModCntr_Interest_Run.C_Currency_ID
-- 2024-05-22T12:46:44.875Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-22 15:46:44.875','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588266
;

-- Column: ModCntr_Interest_Run.IsActive
-- 2024-05-22T12:47:17.620Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-22 15:47:17.62','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588212
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Pauschale - Vertragsperiode
-- Column: ModCntr_Interest_V.C_Flatrate_Term_ID
-- 2024-05-22T13:12:45.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728731,0,547546,551836,624751,'F',TO_TIMESTAMP('2024-05-22 16:12:45.617','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Pauschale - Vertragsperiode',15,0,0,TO_TIMESTAMP('2024-05-22 16:12:45.617','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Handlertyp für modularen Vertrag
-- Column: ModCntr_Interest_V.ModularContractHandlerType
-- 2024-05-22T13:17:46.560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728750,0,547546,551836,624752,'F',TO_TIMESTAMP('2024-05-22 16:17:46.427','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Handlertyp für modularen Vertrag',30,0,0,TO_TIMESTAMP('2024-05-22 16:17:46.427','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungspartner Suchschlüssel
-- Column: ModCntr_Interest_V.Bill_BPartner_Value
-- 2024-05-22T13:18:00.813Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728732,0,547546,551836,624753,'F',TO_TIMESTAMP('2024-05-22 16:18:00.685','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungspartner Suchschlüssel',40,0,0,TO_TIMESTAMP('2024-05-22 16:18:00.685','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_Name
-- 2024-05-22T13:18:20.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728733,0,547546,551836,624754,'F',TO_TIMESTAMP('2024-05-22 16:18:20.02','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name Rechnungspartner',50,0,0,TO_TIMESTAMP('2024-05-22 16:18:20.02','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name der Abrechnungszeilengruppe
-- Column: ModCntr_Interest_V.InvoicingGroup_Name
-- 2024-05-22T13:18:35.435Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728734,0,547546,551836,624755,'F',TO_TIMESTAMP('2024-05-22 16:18:35.298','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name der Abrechnungszeilengruppe',60,0,0,TO_TIMESTAMP('2024-05-22 16:18:35.298','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name der Abrechnungszeilengruppe
-- Column: ModCntr_Interest_V.InvoicingGroup_Name
-- 2024-05-22T13:18:47.451Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728734,0,547546,551836,624756,'F',TO_TIMESTAMP('2024-05-22 16:18:47.32','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name der Abrechnungszeilengruppe',70,0,0,TO_TIMESTAMP('2024-05-22 16:18:47.32','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorgangsdatum
-- Column: ModCntr_Interest_V.DateTrx
-- 2024-05-22T13:19:03.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728741,0,547546,551836,624757,'F',TO_TIMESTAMP('2024-05-22 16:19:03.721','YYYY-MM-DD HH24:MI:SS.US'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','Y','N','N','N',0,'Vorgangsdatum',80,0,0,TO_TIMESTAMP('2024-05-22 16:19:03.721','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Menge
-- Column: ModCntr_Interest_V.Qty
-- 2024-05-22T13:19:19.889Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728751,0,547546,551836,624758,'F',TO_TIMESTAMP('2024-05-22 16:19:19.76','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',90,0,0,TO_TIMESTAMP('2024-05-22 16:19:19.76','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Mengeneinheit
-- Column: ModCntr_Interest_V.UOM
-- 2024-05-22T13:19:39.197Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728752,0,547546,551836,624759,'F',TO_TIMESTAMP('2024-05-22 16:19:39.069','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Mengeneinheit',100,0,0,TO_TIMESTAMP('2024-05-22 16:19:39.069','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zusätzliche Zinstage
-- Column: ModCntr_Interest_V.AddInterestDays
-- 2024-05-22T13:20:41.026Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728739,0,547546,551836,624760,'F',TO_TIMESTAMP('2024-05-22 16:20:40.909','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zusätzliche Zinstage',110,0,0,TO_TIMESTAMP('2024-05-22 16:20:40.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinssatz
-- Column: ModCntr_Interest_V.InterestRate
-- 2024-05-22T13:21:06.453Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728740,0,547546,551836,624761,'F',TO_TIMESTAMP('2024-05-22 16:21:06.317','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinssatz',120,0,0,TO_TIMESTAMP('2024-05-22 16:21:06.317','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorfinanzierungsbetrag
-- Column: ModCntr_Interest_V.InterimAmt
-- 2024-05-22T13:21:22.940Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728742,0,547546,551836,624762,'F',TO_TIMESTAMP('2024-05-22 16:21:22.821','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Vorfinanzierungsbetrag',130,0,0,TO_TIMESTAMP('2024-05-22 16:21:22.821','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Übereinstimmender Betrag
-- Column: ModCntr_Interest_V.MatchedAmt
-- 2024-05-22T13:21:35.945Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728743,0,547546,551836,624763,'F',TO_TIMESTAMP('2024-05-22 16:21:35.832','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Übereinstimmender Betrag',140,0,0,TO_TIMESTAMP('2024-05-22 16:21:35.832','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Gesamtbetrag
-- Column: ModCntr_Interest_V.TotalAmt
-- 2024-05-22T13:21:48.659Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728744,0,547546,551836,624764,'F',TO_TIMESTAMP('2024-05-22 16:21:48.526','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Gesamtbetrag',150,0,0,TO_TIMESTAMP('2024-05-22 16:21:48.526','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinstage
-- Column: ModCntr_Interest_V.InterestDays
-- 2024-05-22T13:22:10.830Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728745,0,547546,551836,624765,'F',TO_TIMESTAMP('2024-05-22 16:22:10.669','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinstage',160,0,0,TO_TIMESTAMP('2024-05-22 16:22:10.669','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Zinsnummer
-- Column: ModCntr_Interest_V.InterestScore
-- 2024-05-22T13:22:25.162Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728746,0,547546,551836,624766,'F',TO_TIMESTAMP('2024-05-22 16:22:24.951','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinsnummer',170,0,0,TO_TIMESTAMP('2024-05-22 16:22:24.951','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Endgültige Zinsen
-- Column: ModCntr_Interest_V.FinalInterest
-- 2024-05-22T13:22:43.182Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728747,0,547546,551836,624767,'F',TO_TIMESTAMP('2024-05-22 16:22:43.056','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Endgültige Zinsen',180,0,0,TO_TIMESTAMP('2024-05-22 16:22:43.056','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: Zinsberechnungslauf
-- Action Type: W
-- Window: Zinsberechnungslauf(541801,de.metas.contracts)
-- 2024-05-22T13:27:00.809Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583097,542157,0,541801,TO_TIMESTAMP('2024-05-22 16:27:00.612','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','541801 ModCntr_Interest_Run','Y','N','N','N','N','Zinsberechnungslauf',TO_TIMESTAMP('2024-05-22 16:27:00.612','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T13:27:00.811Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542157 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-05-22T13:27:00.813Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542157, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542157)
;

-- 2024-05-22T13:27:00.823Z
/* DDL */  select update_menu_translation_from_ad_element(583097)
;

-- Reordering children of `Partner Relations`
-- Node name: `Business Partner Rules`
-- 2024-05-22T13:27:08.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=165 AND AD_Tree_ID=10
;

-- Node name: `Web`
-- 2024-05-22T13:27:08.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=372 AND AD_Tree_ID=10
;

-- Node name: `Service`
-- 2024-05-22T13:27:08.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=271 AND AD_Tree_ID=10
;

-- Node name: `Request`
-- 2024-05-22T13:27:08.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=528 AND AD_Tree_ID=10
;

-- Node name: `Sales Rep Info (AD_User)`
-- 2024-05-22T13:27:08.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=414 AND AD_Tree_ID=10
;

-- Node name: `Mail Template (R_MailText)`
-- 2024-05-22T13:27:08.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=238 AND AD_Tree_ID=10
;

-- Node name: `Mail Template Translations (R_MailText_Trl)`
-- 2024-05-22T13:27:08.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541315 AND AD_Tree_ID=10
;

-- Node name: `Send Mail Text (org.compiere.process.SendMailText)`
-- 2024-05-22T13:27:08.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=396 AND AD_Tree_ID=10
;

-- Node name: `Umsatzliste (@PREFIX@de/metas/reports/umsatzliste/report.jasper)`
-- 2024-05-22T13:27:08.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540594 AND AD_Tree_ID=10
;

-- Node name: `Import Users (I_User)`
-- 2024-05-22T13:27:08.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540964 AND AD_Tree_ID=10
;

-- Node name: `Import Request (I_Request)`
-- 2024-05-22T13:27:08.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540962 AND AD_Tree_ID=10
;

-- Node name: `Zinsberechnungslauf`
-- 2024-05-22T13:27:08.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542157 AND AD_Tree_ID=10
;

-- Reordering children of `Contract Management`
-- Node name: `Contract_OLD (C_Flatrate_Term)`
-- 2024-05-22T13:27:31.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542124 AND AD_Tree_ID=10
;

-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2024-05-22T13:27:31.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2024-05-22T13:27:31.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2024-05-22T13:27:31.349Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2024-05-22T13:27:31.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2024-05-22T13:27:31.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (ModCntr_Type)`
-- 2024-05-22T13:27:31.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2024-05-22T13:27:31.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log (ModCntr_Log)`
-- 2024-05-22T13:27:31.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Node name: `Zinsberechnungslauf`
-- 2024-05-22T13:27:31.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542157 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2024-05-22T13:27:31.353Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `C_SubscrDiscount (C_SubscrDiscount)`
-- 2024-05-22T13:27:31.353Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-05-22T13:27:31.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-05-22T13:27:31.354Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2024-05-22T13:27:31.355Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2024-05-22T13:27:31.356Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2024-05-22T13:27:31.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Settings (ModCntr_Settings)`
-- 2024-05-22T13:27:31.357Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542088 AND AD_Tree_ID=10
;

-- Node name: `Invoice Group (ModCntr_InvoicingGroup)`
-- 2024-05-22T13:27:31.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542106 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log Status (ModCntr_Log_Status)`
-- 2024-05-22T13:27:31.358Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542111 AND AD_Tree_ID=10
;

-- Node name: `Import Modular Contract Log (I_ModCntr_Log)`
-- 2024-05-22T13:27:31.359Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542117 AND AD_Tree_ID=10
;

-- Element: null
-- 2024-05-22T13:29:35.367Z
UPDATE AD_Element_Trl SET Name='Zinsen berechnen', PrintName='Zinsen berechnen',Updated=TO_TIMESTAMP('2024-05-22 16:29:35.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='de_CH'
;

-- 2024-05-22T13:29:35.370Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'de_CH')
;

-- Element: null
-- 2024-05-22T13:29:37.305Z
UPDATE AD_Element_Trl SET Name='Zinsen berechnen', PrintName='Zinsen berechnen',Updated=TO_TIMESTAMP('2024-05-22 16:29:37.305','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='de_DE'
;

-- 2024-05-22T13:29:37.307Z
UPDATE AD_Element SET Name='Zinsen berechnen', PrintName='Zinsen berechnen' WHERE AD_Element_ID=583108
;

-- 2024-05-22T13:29:37.544Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583108,'de_DE')
;

-- 2024-05-22T13:29:37.545Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'de_DE')
;

-- Element: null
-- 2024-05-22T13:29:39.529Z
UPDATE AD_Element_Trl SET Name='Zinsen berechnen', PrintName='Zinsen berechnen',Updated=TO_TIMESTAMP('2024-05-22 16:29:39.529','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='fr_CH'
;

-- 2024-05-22T13:29:39.530Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'fr_CH')
;

-- Element: null
-- 2024-05-22T13:29:41.990Z
UPDATE AD_Element_Trl SET Name='Zinsen berechnen', PrintName='Zinsen berechnen',Updated=TO_TIMESTAMP('2024-05-22 16:29:41.99','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583108 AND AD_Language='it_IT'
;

-- 2024-05-22T13:29:41.991Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583108,'it_IT')
;

-- Element: InterimDate
-- 2024-05-22T13:30:24.119Z
UPDATE AD_Element_Trl SET Name='Interim invoice date', PrintName='Interim invoice date',Updated=TO_TIMESTAMP('2024-05-22 16:30:24.119','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583086 AND AD_Language='en_US'
;

-- 2024-05-22T13:30:24.120Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583086,'en_US')
;

-- Element: MatchedAmt
-- 2024-05-22T13:31:51.096Z
UPDATE AD_Element_Trl SET Name='Reconciled amount', PrintName='Reconciled amount',Updated=TO_TIMESTAMP('2024-05-22 16:31:51.096','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583099 AND AD_Language='en_US'
;

-- 2024-05-22T13:31:51.097Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583099,'en_US')
;

-- Element: InterimAmt
-- 2024-05-22T13:32:43.850Z
UPDATE AD_Element_Trl SET Name='Interim invoice amount', PrintName='Interim invoice amount',Updated=TO_TIMESTAMP('2024-05-22 16:32:43.85','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583100 AND AD_Language='en_US'
;

-- 2024-05-22T13:32:43.851Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583100,'en_US')
;

-- Element: InterestScore
-- 2024-05-22T13:33:03.855Z
UPDATE AD_Element_Trl SET Name='Interest Calculation Numerator', PrintName='Interest Calculation Numerator',Updated=TO_TIMESTAMP('2024-05-22 16:33:03.855','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583107 AND AD_Language='en_US'
;

-- 2024-05-22T13:33:03.857Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583107,'en_US')
;

-- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- 2024-05-22T13:33:47.938Z
UPDATE AD_Message_Trl SET MsgText='Es müssen zuerst die Zinsen für die Abrechnungsgruppe {} berechnet werden. Bitte die entsprechende Aktion ausführen.',Updated=TO_TIMESTAMP('2024-05-22 16:33:47.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545398
;

-- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- 2024-05-22T13:33:49.082Z
UPDATE AD_Message_Trl SET MsgText='Es müssen zuerst die Zinsen für die Abrechnungsgruppe {} berechnet werden. Bitte die entsprechende Aktion ausführen.',Updated=TO_TIMESTAMP('2024-05-22 16:33:49.082','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545398
;

-- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- 2024-05-22T13:33:50.095Z
UPDATE AD_Message_Trl SET MsgText='Es müssen zuerst die Zinsen für die Abrechnungsgruppe {} berechnet werden. Bitte die entsprechende Aktion ausführen.',Updated=TO_TIMESTAMP('2024-05-22 16:33:50.095','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545398
;

-- 2024-05-22T13:33:50.096Z
UPDATE AD_Message SET MsgText='Es müssen zuerst die Zinsen für die Abrechnungsgruppe {} berechnet werden. Bitte die entsprechende Aktion ausführen.' WHERE AD_Message_ID=545398
;

-- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- 2024-05-22T13:33:56.293Z
UPDATE AD_Message_Trl SET MsgText='Es müssen zuerst die Zinsen für die Abrechnungsgruppe {} berechnet werden. Bitte die entsprechende Aktion ausführen.',Updated=TO_TIMESTAMP('2024-05-22 16:33:56.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545398
;

-- Value: de.metas.contracts.modular.interest.InterestNotCalculated
-- 2024-05-22T13:33:59.424Z
UPDATE AD_Message_Trl SET MsgText='The interest must first be calculated for the invoicing group {}. Please run the corresponding action.',Updated=TO_TIMESTAMP('2024-05-22 16:33:59.424','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545398
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T13:37:02.390Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545412,0,TO_TIMESTAMP('2024-05-22 16:37:02.009','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Ein neuer Zinslauf wurde für die Rechnungsgruppe {} erstellt.','I',TO_TIMESTAMP('2024-05-22 16:37:02.009','YYYY-MM-DD HH24:MI:SS.US'),100,'Event_InterestRunGenerated')
;

-- 2024-05-22T13:37:02.391Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545412 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T13:37:12.852Z
UPDATE AD_Message_Trl SET MsgText='A new interest run has been created for invoicing group {}.',Updated=TO_TIMESTAMP('2024-05-22 16:37:12.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545412
;

-- Tab: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins
-- Table: ModCntr_Interest_V
-- 2024-05-22T14:08:38.774Z
UPDATE AD_Tab SET AD_Column_ID=588289, Parent_Column_ID=588215, TabLevel=1,Updated=TO_TIMESTAMP('2024-05-22 17:08:38.774','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547546
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T14:20:45.159Z
UPDATE AD_Message_Trl SET MsgText='Es wurde ein neuer Zinslauf für die Rechnungsgruppe {} erstellt. Die früheren Läufe für diese Rechnungsgruppe wurden bereinigt.',Updated=TO_TIMESTAMP('2024-05-22 17:20:45.159','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545412
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T14:20:45.992Z
UPDATE AD_Message_Trl SET MsgText='Es wurde ein neuer Zinslauf für die Rechnungsgruppe {} erstellt. Die früheren Läufe für diese Rechnungsgruppe wurden bereinigt.',Updated=TO_TIMESTAMP('2024-05-22 17:20:45.992','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545412
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T14:20:47.142Z
UPDATE AD_Message_Trl SET MsgText='Es wurde ein neuer Zinslauf für die Rechnungsgruppe {} erstellt. Die früheren Läufe für diese Rechnungsgruppe wurden bereinigt.',Updated=TO_TIMESTAMP('2024-05-22 17:20:47.142','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545412
;

-- 2024-05-22T14:20:47.143Z
UPDATE AD_Message SET MsgText='Es wurde ein neuer Zinslauf für die Rechnungsgruppe {} erstellt. Die früheren Läufe für diese Rechnungsgruppe wurden bereinigt.' WHERE AD_Message_ID=545412
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T14:20:59.751Z
UPDATE AD_Message_Trl SET MsgText='Es wurde ein neuer Zinslauf für die Rechnungsgruppe {} erstellt. Die früheren Läufe für diese Rechnungsgruppe wurden bereinigt.',Updated=TO_TIMESTAMP('2024-05-22 17:20:59.751','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545412
;

-- Value: Event_InterestRunGenerated
-- 2024-05-22T14:21:02.223Z
UPDATE AD_Message_Trl SET MsgText='A new interest run has been created for invoicing group {}. Previous runs for this invoice group have been cleaned up.',Updated=TO_TIMESTAMP('2024-05-22 17:21:02.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545412
;

-- Tab: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins
-- Table: ModCntr_Interest_V
-- 2024-05-22T14:49:49.507Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2024-05-22 17:49:49.507','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547546
;

-- 2024-05-22T14:57:22.323Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583123,0,'C_InterimInvoice_ID',TO_TIMESTAMP('2024-05-22 17:57:22.109','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Akontorechnung','Akontorechnung',TO_TIMESTAMP('2024-05-22 17:57:22.109','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T14:57:22.326Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583123 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_InterimInvoice_ID
-- 2024-05-22T14:57:39.402Z
UPDATE AD_Element_Trl SET Name='Interim Invoice', PrintName='Interim Invoice',Updated=TO_TIMESTAMP('2024-05-22 17:57:39.402','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583123 AND AD_Language='en_US'
;

-- 2024-05-22T14:57:39.404Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583123,'en_US')
;

-- Column: ModCntr_Interest_V.Bill_BPartner_ID
-- 2024-05-22T15:06:19.809Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588314,2039,0,19,542412,'Bill_BPartner_ID',TO_TIMESTAMP('2024-05-22 18:06:19.65','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Geschäftspartner für die Rechnungsstellung','de.metas.contracts',0,10,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Rechnungspartner',0,0,TO_TIMESTAMP('2024-05-22 18:06:19.65','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T15:06:19.810Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588314 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T15:06:19.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(2039)
;

-- Column: ModCntr_Interest_V.ModCntr_InvoicingGroup_ID
-- 2024-05-22T15:06:30.441Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588315,582647,0,19,542412,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2024-05-22 18:06:30.283','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Rechnungsgruppe',0,0,TO_TIMESTAMP('2024-05-22 18:06:30.283','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T15:06:30.442Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588315 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T15:06:30.444Z
/* DDL */  select update_Column_Translation_From_AD_Element(582647)
;

-- Column: ModCntr_Interest_V.Initial_Product_ID
-- 2024-05-22T15:06:58.332Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588316,583085,0,18,540272,542412,'Initial_Product_ID',TO_TIMESTAMP('2024-05-22 18:06:58.197','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Ursprüngliches Produkt',0,0,TO_TIMESTAMP('2024-05-22 18:06:58.197','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T15:06:58.333Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588316 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T15:06:58.335Z
/* DDL */  select update_Column_Translation_From_AD_Element(583085)
;

-- Column: ModCntr_Interest_V.C_InterimInvoice_ID
-- 2024-05-22T15:07:29.824Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588317,583123,0,18,336,542412,'C_InterimInvoice_ID',TO_TIMESTAMP('2024-05-22 18:07:29.686','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Akontorechnung',0,0,TO_TIMESTAMP('2024-05-22 18:07:29.686','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T15:07:29.826Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588317 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T15:07:29.828Z
/* DDL */  select update_Column_Translation_From_AD_Element(583123)
;

-- Column: ModCntr_Interest_V.C_UOM_ID
-- 2024-05-22T15:07:44.694Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588318,215,0,19,542412,'C_UOM_ID',TO_TIMESTAMP('2024-05-22 18:07:44.571','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','de.metas.contracts',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2024-05-22 18:07:44.571','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-22T15:07:44.695Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588318 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-22T15:07:44.697Z
/* DDL */  select update_Column_Translation_From_AD_Element(215)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_ID
-- 2024-05-22T15:08:24.750Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588314,728755,0,547546,TO_TIMESTAMP('2024-05-22 18:08:24.614','YYYY-MM-DD HH24:MI:SS.US'),100,'Geschäftspartner für die Rechnungsstellung',10,'de.metas.contracts','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2024-05-22 18:08:24.614','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T15:08:24.751Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728755 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T15:08:24.752Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039)
;

-- 2024-05-22T15:08:24.758Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728755
;

-- 2024-05-22T15:08:24.759Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728755)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Rechnungsgruppe
-- Column: ModCntr_Interest_V.ModCntr_InvoicingGroup_ID
-- 2024-05-22T15:08:24.873Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588315,728756,0,547546,TO_TIMESTAMP('2024-05-22 18:08:24.761','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Rechnungsgruppe',TO_TIMESTAMP('2024-05-22 18:08:24.761','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T15:08:24.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728756 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T15:08:24.876Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582647)
;

-- 2024-05-22T15:08:24.877Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728756
;

-- 2024-05-22T15:08:24.878Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728756)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Ursprüngliches Produkt
-- Column: ModCntr_Interest_V.Initial_Product_ID
-- 2024-05-22T15:08:24.993Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588316,728757,0,547546,TO_TIMESTAMP('2024-05-22 18:08:24.879','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Ursprüngliches Produkt',TO_TIMESTAMP('2024-05-22 18:08:24.879','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T15:08:24.994Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728757 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T15:08:24.995Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583085)
;

-- 2024-05-22T15:08:24.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728757
;

-- 2024-05-22T15:08:24.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728757)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Akontorechnung
-- Column: ModCntr_Interest_V.C_InterimInvoice_ID
-- 2024-05-22T15:08:25.091Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588317,728758,0,547546,TO_TIMESTAMP('2024-05-22 18:08:24.999','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Akontorechnung',TO_TIMESTAMP('2024-05-22 18:08:24.999','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T15:08:25.092Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T15:08:25.093Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583123)
;

-- 2024-05-22T15:08:25.094Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728758
;

-- 2024-05-22T15:08:25.095Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728758)
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Maßeinheit
-- Column: ModCntr_Interest_V.C_UOM_ID
-- 2024-05-22T15:08:25.196Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588318,728759,0,547546,TO_TIMESTAMP('2024-05-22 18:08:25.097','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'de.metas.contracts','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-05-22 18:08:25.097','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-22T15:08:25.197Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728759 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-22T15:08:25.198Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2024-05-22T15:08:25.278Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728759
;

-- 2024-05-22T15:08:25.278Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728759)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Rechnungspartner Suchschlüssel
-- Column: ModCntr_Interest_V.Bill_BPartner_Value
-- 2024-05-22T15:08:49.989Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624753
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Name Rechnungspartner
-- Column: ModCntr_Interest_V.Bill_BPartner_Name
-- 2024-05-22T15:08:52.824Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624754
;

