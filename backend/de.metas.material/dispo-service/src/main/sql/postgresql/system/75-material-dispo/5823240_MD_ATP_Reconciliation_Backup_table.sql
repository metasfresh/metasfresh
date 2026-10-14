-- New table holding the durable backup + run log for the ATP reconciliation command: one row per STOCK
-- candidate a reconciliation run touched, capturing its quantity immediately before the run (recoverable
-- after the run ends) and, once known, the quantity after. Rows sharing a ReconciliationRunUUID belong to
-- the same run.

-- AD_Table
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy)
VALUES ('3',0,0,0,542645 /*From ID Server*/,'N',TO_TIMESTAMP('2026-09-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','N','Y','N','N','N','Y','N','N','N','N',0,'MD_ATP_Reconciliation_Backup','NP','L','MD_ATP_Reconciliation_Backup','DTI',TO_TIMESTAMP('2026-09-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Table_ID=542645
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Primary key column, its own new AD_Element
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585438 /*From ID Server*/,0,'MD_ATP_Reconciliation_Backup_ID',TO_TIMESTAMP('2026-09-09 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','MD_ATP_Reconciliation_Backup','MD_ATP_Reconciliation_Backup',TO_TIMESTAMP('2026-09-09 10:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585438
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593513 /*From ID Server*/,585438,0,13,542645,'MD_ATP_Reconciliation_Backup_ID',TO_TIMESTAMP('2026-09-09 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','MD_ATP_Reconciliation_Backup','NP',10,TO_TIMESTAMP('2026-09-09 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593513
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(585438);

-- Standard columns (shared elements: AD_Client_ID=102, AD_Org_ID=113, Created=245, CreatedBy=246, Updated=607, UpdatedBy=608, IsActive=348)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593514 /*From ID Server*/,102,0,19,542645,'AD_Client_ID',TO_TIMESTAMP('2026-09-09 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',20,TO_TIMESTAMP('2026-09-09 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593514
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(102);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593515 /*From ID Server*/,113,0,30,542645,'AD_Org_ID',TO_TIMESTAMP('2026-09-09 10:02:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',30,TO_TIMESTAMP('2026-09-09 10:02:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593515
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(113);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593516 /*From ID Server*/,245,0,16,542645,'Created',TO_TIMESTAMP('2026-09-09 10:02:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',40,TO_TIMESTAMP('2026-09-09 10:02:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593516
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(245);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593517 /*From ID Server*/,246,0,18,110,542645,'CreatedBy',TO_TIMESTAMP('2026-09-09 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',50,TO_TIMESTAMP('2026-09-09 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593517
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(246);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593518 /*From ID Server*/,607,0,16,542645,'Updated',TO_TIMESTAMP('2026-09-09 10:02:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',29,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',60,TO_TIMESTAMP('2026-09-09 10:02:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593518
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(607);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593519 /*From ID Server*/,608,0,18,110,542645,'UpdatedBy',TO_TIMESTAMP('2026-09-09 10:02:50','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',70,TO_TIMESTAMP('2026-09-09 10:02:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593519
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(608);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593520 /*From ID Server*/,348,0,20,542645,'IsActive',TO_TIMESTAMP('2026-09-09 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',1,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',80,TO_TIMESTAMP('2026-09-09 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593520
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(348);

-- Run identity: new element
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585439 /*From ID Server*/,0,'ReconciliationRunUUID',TO_TIMESTAMP('2026-09-09 10:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Kennung, die alle bei einem ATP-Abstimmungslauf gesicherten Zeilen gruppiert.','de.metas.material.dispo','Y','Abstimmungslauf-Kennung','Abstimmungslauf-Kennung',TO_TIMESTAMP('2026-09-09 10:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585439
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Reconciliation run id', Description='Unique identifier grouping every row a single ATP reconciliation run backed up.', PrintName='Reconciliation run id', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:04:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585439 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585439,'en_US');

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593521 /*From ID Server*/,585439,0,10,542645,'ReconciliationRunUUID',TO_TIMESTAMP('2026-09-09 10:04:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',36,'Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Abstimmungslauf-Kennung','NP',90,TO_TIMESTAMP('2026-09-09 10:04:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593521
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585439);

-- Reused elements from MD_Candidate: MD_Candidate_ID=543308, DateProjected=543309, M_Warehouse_ID=459, M_Product_ID=454, StorageAttributesKey=543463
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593522 /*From ID Server*/,543308,0,19,542645,'MD_Candidate_ID',TO_TIMESTAMP('2026-09-09 10:05:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Dispositionskandidat','NP',100,TO_TIMESTAMP('2026-09-09 10:05:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593522
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(543308);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593523 /*From ID Server*/,543309,0,16,542645,'DateProjected',TO_TIMESTAMP('2026-09-09 10:05:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',7,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Datum','NP',110,TO_TIMESTAMP('2026-09-09 10:05:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593523
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(543309);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593524 /*From ID Server*/,459,0,30,542645,'M_Warehouse_ID',TO_TIMESTAMP('2026-09-09 10:05:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Lager','NP',120,TO_TIMESTAMP('2026-09-09 10:05:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593524
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(459);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593525 /*From ID Server*/,454,0,30,542645,'M_Product_ID',TO_TIMESTAMP('2026-09-09 10:05:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Produkt','NP',130,TO_TIMESTAMP('2026-09-09 10:05:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593525
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(454);

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593526 /*From ID Server*/,543463,0,10,542645,'StorageAttributesKey',TO_TIMESTAMP('2026-09-09 10:05:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',1024,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Lagerattribute','NP',140,TO_TIMESTAMP('2026-09-09 10:05:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593526
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(543463);

-- QtyBefore: new element, nullable (a run that CREATES a candidate has nothing to back up for it)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585440 /*From ID Server*/,0,'ATPReconciliationBackupQtyBefore',TO_TIMESTAMP('2026-09-09 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,'Menge des Bestandskandidaten unmittelbar vor der Aenderung durch die ATP-Abstimmung.','de.metas.material.dispo','Y','Menge vor Aenderung','Menge vor Aenderung',TO_TIMESTAMP('2026-09-09 10:06:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585440
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Qty before change', Description='The candidate quantity immediately before the ATP reconciliation changed it.', PrintName='Qty before change', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:06:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585440 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585440,'en_US');

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593527 /*From ID Server*/,585440,0,29,542645,'QtyBefore',TO_TIMESTAMP('2026-09-09 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','Menge vor Aenderung','NP',150,TO_TIMESTAMP('2026-09-09 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593527
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585440);

-- QtyAfter: new element, mandatory (only ever written once the change is known, together with QtyBefore's row)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585441 /*From ID Server*/,0,'ATPReconciliationBackupQtyAfter',TO_TIMESTAMP('2026-09-09 10:07:00','YYYY-MM-DD HH24:MI:SS'),100,'Menge des Bestandskandidaten nach der Aenderung durch die ATP-Abstimmung.','de.metas.material.dispo','Y','Menge nach Aenderung','Menge nach Aenderung',TO_TIMESTAMP('2026-09-09 10:07:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585441
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Qty after change', Description='The candidate quantity after the ATP reconciliation changed it.', PrintName='Qty after change', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 10:07:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585441 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585441,'en_US');

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,593528 /*From ID Server*/,585441,0,29,542645,'QtyAfter',TO_TIMESTAMP('2026-09-09 10:07:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Menge nach Aenderung','NP',160,TO_TIMESTAMP('2026-09-09 10:07:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593528
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585441);

-- Physical table
CREATE TABLE public.MD_ATP_Reconciliation_Backup
(
	MD_ATP_Reconciliation_Backup_ID NUMERIC(10) NOT NULL,
	AD_Client_ID NUMERIC(10) NOT NULL,
	AD_Org_ID NUMERIC(10) NOT NULL,
	Created TIMESTAMP WITH TIME ZONE NOT NULL,
	CreatedBy NUMERIC(10) NOT NULL,
	Updated TIMESTAMP WITH TIME ZONE NOT NULL,
	UpdatedBy NUMERIC(10) NOT NULL,
	IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL,
	ReconciliationRunUUID VARCHAR(36) NOT NULL,
	MD_Candidate_ID NUMERIC(10) NOT NULL,
	DateProjected TIMESTAMP WITH TIME ZONE NOT NULL,
	M_Warehouse_ID NUMERIC(10) NOT NULL,
	M_Product_ID NUMERIC(10) NOT NULL,
	StorageAttributesKey VARCHAR(1024) NOT NULL,
	QtyBefore NUMERIC,
	QtyAfter NUMERIC NOT NULL,
	CONSTRAINT MD_ATP_Reconciliation_Backup_Key PRIMARY KEY (MD_ATP_Reconciliation_Backup_ID)
)
;

CREATE INDEX MD_ATP_Reconciliation_Backup_RunUUID_idx ON public.MD_ATP_Reconciliation_Backup (ReconciliationRunUUID);
CREATE INDEX MD_ATP_Reconciliation_Backup_Candidate_idx ON public.MD_ATP_Reconciliation_Backup (MD_Candidate_ID);
