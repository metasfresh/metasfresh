-- 2017-08-04T15:45:08.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_system','IsAutoErrorReport','CHAR(1)',null,'Y')
;

-- 2017-08-04T15:45:08.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_System SET IsAutoErrorReport='Y' WHERE IsAutoErrorReport IS NULL
;

-- 2017-08-04T16:20:18.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543394,0,'DBVersion',TO_TIMESTAMP('2017-08-04 16:20:18','YYYY-MM-DD HH24:MI:SS'),100,'version of the SQL migration scripts that were last rolled out','D','https://github.com/metasfresh/metasfresh/issues/2111','Y','DBVersion','DBVersion',TO_TIMESTAMP('2017-08-04 16:20:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-04T16:20:18.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543394 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-08-04T16:20:48.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557043,543394,0,10,531,'N','DBVersion',TO_TIMESTAMP('2017-08-04 16:20:48','YYYY-MM-DD HH24:MI:SS'),100,'N','version of the SQL migration scripts that were last rolled out','D',50,'https://github.com/metasfresh/metasfresh/issues/2111','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','DBVersion',0,TO_TIMESTAMP('2017-08-04 16:20:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-08-04T16:20:48.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557043 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Creating the column in a migration script makes no sense, becase the rollout tool needs this column to function
-- Therefor the rollout tool takes care of creating this column by itself if it needs to
-- 2017-08-04T16:21:01.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('ad_system','ALTER TABLE public.AD_System ADD COLUMN DBVersion VARCHAR(50)')
--;

