-- 2018-08-30T17:12:52.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (20,'N','N','N','N','N',0,'Y',100,543865,'Y','N','N','N','N','N','N','N','Y','N','N','N',100,'N','IsEnableRemoteCacheInvalidation',560855,'N','Y','N','N','N','N',0,100,'Enable remote cache invalidation','D','N',1,0,0,TO_TIMESTAMP('2018-08-30 17:12:52','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-08-30 17:12:52','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-08-30T17:12:52.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-30T17:13:01.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- moved to 5500280_sys_gh4536_AD_Table_IsEnableRemoteCacheInvalidation_DDL.sql
--/* DDL */ SELECT public.db_alter_table('AD_Table','ALTER TABLE public.AD_Table ADD COLUMN IsEnableRemoteCacheInvalidation CHAR(1) DEFAULT ''N'' CHECK (IsEnableRemoteCacheInvalidation IN (''Y'',''N'')) NOT NULL')
--;

-- 2018-08-30T17:13:23.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (100,'N','N','N','N','N',0,'Y',100,'N','D',568309,'N',560855,0,'Enable remote cache invalidation',100,1,TO_TIMESTAMP('2018-08-30 17:13:23','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-08-30 17:13:23','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-30T17:13:23.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-30T17:13:43.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2018-08-30 17:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568309
;

-- 2018-08-30T17:13:43.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2018-08-30 17:13:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=100
;

