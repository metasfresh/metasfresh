-- 2018-08-30T12:44:11.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (20,'N','N','N','N','N',0,'Y',100,543430,'Y','N','N','N','N','N','N','N','Y','N','N','N',540783,'N','If selected, the importer will check for multiline text','IsMultiLine',560853,'N','Y','N','N','N','N','',0,100,'Multi Line','D','N',1,0,0,TO_TIMESTAMP('2018-08-30 12:44:11','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-08-30 12:44:11','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-08-30T12:44:11.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560853 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-30T12:44:17.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- moved to 5500230_sys_gh1032webui_AD_UI_Element_IsMultiLine_fields_DDL.sql
--/* DDL */ SELECT public.db_alter_table('AD_UI_Element','ALTER TABLE public.AD_UI_Element ADD COLUMN IsMultiLine CHAR(1) DEFAULT ''N'' CHECK (IsMultiLine IN (''Y'',''N'')) NOT NULL')
--;

-- 2018-08-30T12:44:40.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (540754,'N','N','N','N','N',0,'Y',100,'N','D','If selected, the importer will check for multiline text',568307,'N',560853,'',0,'Multi Line',100,1,TO_TIMESTAMP('2018-08-30 12:44:40','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-08-30 12:44:40','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-30T12:44:40.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-30T12:45:23.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2018-08-30 12:45:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568307
;

-- 2018-08-30T12:58:19.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Lines Count','D','MultiLine_LinesCount',544226,0,'Lines Count',100,TO_TIMESTAMP('2018-08-30 12:58:19','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-08-30 12:58:19','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-30T12:58:19.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544226 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-30T12:58:37.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (11,'','N','N','N','N',0,'Y',100,544226,'Y','N','N','N','N','N','N','N','Y','N','N','N',540783,'N','MultiLine_LinesCount',560854,'N','N','N','N','N','N',0,100,'Lines Count','D','N',10,0,0,TO_TIMESTAMP('2018-08-30 12:58:37','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-08-30 12:58:37','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-08-30T12:58:37.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560854 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-30T12:58:43.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- move to _DDL file
--/* DDL */ SELECT public.db_alter_table('AD_UI_Element','ALTER TABLE public.AD_UI_Element ADD COLUMN MultiLine_LinesCount NUMERIC(10)')
;

-- 2018-08-30T12:59:01.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (540754,'N','N','N','N','N',0,'Y',100,'N','D',568308,'N',560854,0,'Lines Count',100,10,TO_TIMESTAMP('2018-08-30 12:59:01','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-08-30 12:59:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-08-30T12:59:01.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-08-30T12:59:09.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2018-08-30 12:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568308
;

-- 2018-08-30T12:59:28.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsMultiLine/N@=Y', IsSameLine='Y',Updated=TO_TIMESTAMP('2018-08-30 12:59:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568308
;

