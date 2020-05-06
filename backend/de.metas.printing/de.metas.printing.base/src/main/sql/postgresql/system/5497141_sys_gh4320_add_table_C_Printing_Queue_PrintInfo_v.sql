-- 2018-07-04T15:07:42.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541002,'N',TO_TIMESTAMP('2018-07-04 15:07:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','N','Y','N','N','N','N','N','N','Y',0,'C_Printing_Queue_PrintInfo_v','NP','L','C_Printing_Queue_PrintInfo_v',TO_TIMESTAMP('2018-07-04 15:07:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T15:07:42.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=541002 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-07-04T15:07:55.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560636,541927,0,19,541002,'C_Printing_Queue_ID',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druck-Warteschlangendatensatz',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:55.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560636 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:55.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560637,544157,0,19,541002,'AD_Session_Printpackage_ID',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Session Printpackage ID',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:55.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:55.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560638,2671,0,19,541002,'AD_Archive_ID',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Archiv für Belege und Berichte','de.metas.printing',10,'In Abhängigkeit des Grades der Archivierungsautomatik des Mandanten werden Dokumente und Berichte archiviert und stehen zur Ansicht zur Verfügung.','Y','Y','N','N','N','N','N','N','N','N','N','Archiv',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:55.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:55.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560639,542012,0,19,541002,'C_Print_Job_Instructions_ID',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druck-Job Anweisung',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:55.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:55.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560640,544160,0,20,541002,'Status_Print_Job_Instructions',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',1,'Y','Y','N','N','N','N','N','N','N','N','N','Status Print Job Instructions',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:55.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560640 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544162,0,'created_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','created_print_job_instructions','created_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T15:07:56.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544162 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T15:07:56.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560641,544162,0,16,541002,'created_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',35,'Y','Y','N','N','N','N','N','N','N','N','N','created_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560641 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544163,0,'createdby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','createdby_print_job_instructions','createdby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T15:07:56.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544163 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T15:07:56.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560642,544163,0,11,541002,'createdby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','createdby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560642 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544164,0,'ad_org_print_job_instructions_id',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','ad_org_print_job_instructions_id','ad_org_print_job_instructions_id',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T15:07:56.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544164 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T15:07:56.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560643,544164,0,19,541002,'ad_org_print_job_instructions_id',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','ad_org_print_job_instructions_id',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560643 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544165,0,'updated_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','updated_print_job_instructions','updated_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T15:07:56.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544165 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T15:07:56.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560644,544165,0,16,541002,'updated_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',35,'Y','Y','N','N','N','N','N','N','N','N','N','updated_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544166,0,'updatedby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','updatedby_print_job_instructions','updatedby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-04T15:07:56.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544166 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-04T15:07:56.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560645,544166,0,11,541002,'updatedby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','updatedby_print_job_instructions',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560645 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560646,541955,0,19,541002,'C_Print_Package_ID',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druckpaket',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560646 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560647,541959,0,19,541002,'C_Print_PackageInfo_ID',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'Contains details for the print package, like printer, tray, pages from/to and print service name.','de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Druckpaket-Info',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560647 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:56.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560648,541930,0,19,541002,'AD_PrinterHW_ID',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Hardware-Drucker',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:56.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:57.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560649,541962,0,10,541002,'PrintServiceName',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',255,'Y','Y','N','N','N','N','N','N','N','N','N','Print service name',TO_TIMESTAMP('2018-07-04 15:07:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:57.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:57.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560650,541932,0,19,541002,'AD_PrinterHW_MediaTray_ID',TO_TIMESTAMP('2018-07-04 15:07:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Hardware-Schacht',TO_TIMESTAMP('2018-07-04 15:07:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:57.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:57.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560651,541933,0,11,541002,'TrayNumber',TO_TIMESTAMP('2018-07-04 15:07:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',10,'Y','Y','N','N','N','N','N','N','N','N','N','Schachtnummer',TO_TIMESTAMP('2018-07-04 15:07:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:57.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560651 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:07:57.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,560652,544161,0,10,541002,'PrintServiceTray',TO_TIMESTAMP('2018-07-04 15:07:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',255,'Y','Y','N','N','N','N','N','N','N','N','N','Print Service Tray',TO_TIMESTAMP('2018-07-04 15:07:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-04T15:07:57.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560652 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-04T15:09:04.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AD_Org_Print_Job_Instructions_ID', Name='Org Print Job Instructions ID', PrintName='Org Print Job Instructions ID',Updated=TO_TIMESTAMP('2018-07-04 15:09:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544164
;

-- 2018-07-04T15:09:04.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Org_Print_Job_Instructions_ID', Name='Org Print Job Instructions ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544164
;

-- 2018-07-04T15:09:04.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_Print_Job_Instructions_ID', Name='Org Print Job Instructions ID', Description=NULL, Help=NULL, AD_Element_ID=544164 WHERE UPPER(ColumnName)='AD_ORG_PRINT_JOB_INSTRUCTIONS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T15:09:04.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_Print_Job_Instructions_ID', Name='Org Print Job Instructions ID', Description=NULL, Help=NULL WHERE AD_Element_ID=544164 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T15:09:04.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Org Print Job Instructions ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544164) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544164)
;

-- 2018-07-04T15:09:04.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Org Print Job Instructions ID', Name='Org Print Job Instructions ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544164)
;

-- 2018-07-04T15:10:11.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', PrintName='Created Print Job Instructions',Updated=TO_TIMESTAMP('2018-07-04 15:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544162
;

-- 2018-07-04T15:10:11.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544162
;

-- 2018-07-04T15:10:11.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', Description=NULL, Help=NULL, AD_Element_ID=544162 WHERE UPPER(ColumnName)='CREATED_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T15:10:11.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Created_Print_Job_Instructions', Name='Created Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544162 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T15:10:11.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Created Print Job Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544162) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544162)
;

-- 2018-07-04T15:10:11.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Created Print Job Instructions', Name='Created Print Job Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544162)
;

-- 2018-07-04T15:10:58.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Createdby_Print_Job_Instructions', Name='Createdby Print Job Instructions', PrintName='Createdby Print Job Instructions',Updated=TO_TIMESTAMP('2018-07-04 15:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544163
;

-- 2018-07-04T15:10:58.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Createdby_Print_Job_Instructions', Name='Createdby Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544163
;

-- 2018-07-04T15:10:58.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Createdby_Print_Job_Instructions', Name='Createdby Print Job Instructions', Description=NULL, Help=NULL, AD_Element_ID=544163 WHERE UPPER(ColumnName)='CREATEDBY_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T15:10:58.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Createdby_Print_Job_Instructions', Name='Createdby Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544163 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T15:10:58.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Createdby Print Job Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544163) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544163)
;

-- 2018-07-04T15:10:58.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Createdby Print Job Instructions', Name='Createdby Print Job Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544163)
;

-- 2018-07-04T15:12:05.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Updated_Print_Job_Instructions', Name='Updated Print Job Instructions', PrintName='Updated Print Job Instructions',Updated=TO_TIMESTAMP('2018-07-04 15:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544165
;

-- 2018-07-04T15:12:05.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Updated_Print_Job_Instructions', Name='Updated Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544165
;

-- 2018-07-04T15:12:05.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Updated_Print_Job_Instructions', Name='Updated Print Job Instructions', Description=NULL, Help=NULL, AD_Element_ID=544165 WHERE UPPER(ColumnName)='UPDATED_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T15:12:05.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Updated_Print_Job_Instructions', Name='Updated Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544165 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T15:12:05.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Updated Print Job Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544165) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544165)
;

-- 2018-07-04T15:12:05.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Updated Print Job Instructions', Name='Updated Print Job Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544165)
;

-- 2018-07-04T15:12:35.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Updatedby_Print_Job_Instructions', Name='Updatedby Print Job Instructions', PrintName='Updatedby Print Job Instructions',Updated=TO_TIMESTAMP('2018-07-04 15:12:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544166
;

-- 2018-07-04T15:12:35.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Updatedby_Print_Job_Instructions', Name='Updatedby Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544166
;

-- 2018-07-04T15:12:35.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Updatedby_Print_Job_Instructions', Name='Updatedby Print Job Instructions', Description=NULL, Help=NULL, AD_Element_ID=544166 WHERE UPPER(ColumnName)='UPDATEDBY_PRINT_JOB_INSTRUCTIONS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-07-04T15:12:35.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Updatedby_Print_Job_Instructions', Name='Updatedby Print Job Instructions', Description=NULL, Help=NULL WHERE AD_Element_ID=544166 AND IsCentrallyMaintained='Y'
;

-- 2018-07-04T15:12:35.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Updatedby Print Job Instructions', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544166) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544166)
;

-- 2018-07-04T15:12:35.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Updatedby Print Job Instructions', Name='Updatedby Print Job Instructions' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544166)
;

