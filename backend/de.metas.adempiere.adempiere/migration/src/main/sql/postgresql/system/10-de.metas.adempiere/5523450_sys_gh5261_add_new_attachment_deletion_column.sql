-- 2019-06-03T09:36:23.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role DROP COLUMN IF EXISTS IsAttachmentDeletionAllowed
;

-- 2019-06-03T09:36:23.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568137
;

-- 2019-06-03T09:36:23.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568137
;

-- 2019-06-03T09:53:17.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=576778
;

-- 2019-06-03T09:53:17.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=576778
;

-- 2019-06-03T10:01:36.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576782,0,'isattachmentdeletionallowed',TO_TIMESTAMP('2019-06-03 10:01:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','IsAttachmentDeletionAllowed','isattachmentdeletionallowed',TO_TIMESTAMP('2019-06-03 10:01:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-03T10:01:36.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576782 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-06-03T10:02:25.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Darf Anhänge löschen', PrintName='Darf Anhänge löschen',Updated=TO_TIMESTAMP('2019-06-03 10:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language='de_CH'
;

-- 2019-06-03T10:05:10.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-03 10:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language='de_CH'
;

-- 2019-06-03T10:07:03.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsAttachmentDeletionAllowed',Updated=TO_TIMESTAMP('2019-06-03 10:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782
;

-- 2019-06-03T10:07:03.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsAttachmentDeletionAllowed', Name='IsAttachmentDeletionAllowed', Description=NULL, Help=NULL WHERE AD_Element_ID=576782
;

-- 2019-06-03T10:07:03.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAttachmentDeletionAllowed', Name='IsAttachmentDeletionAllowed', Description=NULL, Help=NULL, AD_Element_ID=576782 WHERE UPPER(ColumnName)='ISATTACHMENTDELETIONALLOWED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-03T10:07:03.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsAttachmentDeletionAllowed', Name='IsAttachmentDeletionAllowed', Description=NULL, Help=NULL WHERE AD_Element_ID=576782 AND IsCentrallyMaintained='Y'
;

-- 2019-06-03T10:08:38.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-06-03 10:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782
;

-- 2019-06-03T10:08:44.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='IsAttachmentDeletionAllowed',Updated=TO_TIMESTAMP('2019-06-03 10:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576782
;

-- 2019-06-03T10:08:44.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsAttachmentDeletionAllowed', Name='IsAttachmentDeletionAllowed' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576782)
;

-- 2019-06-03T10:11:36.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568142,576782,0,20,156,'IsAttachmentDeletionAllowed',TO_TIMESTAMP('2019-06-03 10:11:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','IsAttachmentDeletionAllowed',0,0,TO_TIMESTAMP('2019-06-03 10:11:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-06-03T10:11:36.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568142 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-06-03T10:11:56.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('AD_Role','ALTER TABLE public.AD_Role ADD COLUMN IsAttachmentDeletionAllowed CHAR(1) DEFAULT ''N'' CHECK (IsAttachmentDeletionAllowed IN (''Y'',''N'')) NOT NULL')
;

-- 2019-06-03T10:15:20.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-03 10:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568142
;

-- 2019-06-03T10:15:24.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_role','IsAttachmentDeletionAllowed','CHAR(1)',null,'N')
;

-- 2019-06-03T10:15:24.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Role SET IsAttachmentDeletionAllowed='N' WHERE IsAttachmentDeletionAllowed IS NULL
;

-- 2019-06-03T12:51:47.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568142,580881,0,119,TO_TIMESTAMP('2019-06-03 12:51:47','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','IsAttachmentDeletionAllowed',TO_TIMESTAMP('2019-06-03 12:51:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-03T12:51:47.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-06-03T13:14:00.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,580881,0,119,559474,540242,'F',TO_TIMESTAMP('2019-06-03 13:14:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsAttachmentDeletionAllowed',60,0,0,TO_TIMESTAMP('2019-06-03 13:14:00','YYYY-MM-DD HH24:MI:SS'),100)
;