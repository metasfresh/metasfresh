-- 2020-04-23T15:47:19.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Scheduler SET EntityType='de.metas.serviceprovider',Updated=TO_TIMESTAMP('2020-04-23 18:47:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550062
;


-- 2020-04-24T13:23:59.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,EntityType) VALUES (11,'0',14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-04-24 16:23:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-04-24 16:23:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541466,'N','"Reihenfolge" bestimmt die Reihenfolge der Einträge',570632,'N','Y','N','N','N','N','N','N',0,'N','N','SeqNo','N','Reihenfolge',0,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','N',0,0,566,'de.metas.serviceprovider')
;

-- 2020-04-24T13:23:59.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-04-24T13:23:59.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2020-04-24T13:24:01.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('S_ExternalProjectReference','ALTER TABLE public.S_ExternalProjectReference ADD COLUMN SeqNo NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- 2020-04-24T16:50:20.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-04-24 19:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=604478
;

-- 2020-04-24T16:50:28.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-04-24 19:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=604479
;

-- 2020-04-27T14:36:29.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540527,0,541468,TO_TIMESTAMP('2020-04-27 17:36:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Y','IDX_S_Issue_ExternalURL','N',TO_TIMESTAMP('2020-04-27 17:36:29','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.IssueURL IS NOT NULL')
;

-- 2020-04-27T14:36:29.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540527 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-27T14:36:55.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570199,541000,540527,0,TO_TIMESTAMP('2020-04-27 17:36:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-27 17:36:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T14:53:29.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_Issue_ExternalURL ON S_Issue (IssueURL) WHERE S_Issue.IssueURL IS NOT NULL
;

-- 2020-04-27T14:56:00.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540528,0,541466,TO_TIMESTAMP('2020-04-27 17:55:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Y','IDX_S_ExternalProjectReference_Reference_Owner_System','N',TO_TIMESTAMP('2020-04-27 17:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T14:56:00.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540528 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-27T14:56:16.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570146,541001,540528,0,TO_TIMESTAMP('2020-04-27 17:56:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-27 17:56:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T14:56:38.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570144,541002,540528,0,TO_TIMESTAMP('2020-04-27 17:56:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',20,TO_TIMESTAMP('2020-04-27 17:56:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T14:56:52.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570158,541003,540528,0,TO_TIMESTAMP('2020-04-27 17:56:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',30,TO_TIMESTAMP('2020-04-27 17:56:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-27T14:57:04.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalProjectReference_Reference_Owner_System ON S_ExternalProjectReference (ExternalProjectOwner,ExternalReference,ExternalSystem)
;
