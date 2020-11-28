-- 2018-10-18T10:48:45.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,Name,ColumnName) VALUES (10,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2018-10-18 10:48:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2018-10-18 10:48:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N',291,'N',563299,'N','N','N','N','N','N','N','N',0,0,543939,'D','N','N','External ID','ExternalId')
;

-- 2018-10-18T10:48:45.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=563299 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-10-18T10:48:56.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN ExternalId VARCHAR(255)')
;

-- 2018-10-18T13:22:07.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (CreatedBy,Processing,Created,AD_Client_ID,IsActive,IsUnique,WhereClause,AD_Table_ID,Updated,UpdatedBy,AD_Index_Table_ID,AD_Org_ID,Name,EntityType) VALUES (100,'N',TO_TIMESTAMP('2018-10-18 13:22:07','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y','IsActive=''Y'' AND COALESCE(ExternalId, 0) > 0',291,TO_TIMESTAMP('2018-10-18 13:22:07','YYYY-MM-DD HH24:MI:SS'),100,540460,0,'C_BPartner_ExternalId_Uniqe','D')
;

-- 2018-10-18T13:22:07.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540460 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-10-18T13:23:02.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND COALESCE(ExternalId, '''') != ''''',Updated=TO_TIMESTAMP('2018-10-18 13:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540460
;

-- 2018-10-18T13:23:13.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (Created,CreatedBy,Updated,AD_Client_ID,AD_Index_Table_ID,IsActive,AD_Column_ID,SeqNo,UpdatedBy,AD_Index_Column_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2018-10-18 13:23:13','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2018-10-18 13:23:13','YYYY-MM-DD HH24:MI:SS'),0,540460,'Y',563299,10,100,540902,0,'D')
;

-- 2018-10-18T13:23:21.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_BPartner_ExternalId_Uniqe ON C_BPartner (ExternalId) WHERE IsActive='Y' AND COALESCE(ExternalId, '') != ''
;

SELECT db_alter_Table('HC_Forum_Datenaustausch_Config','ALTER TABLE HC_Forum_Datenaustausch_Config RENAME COLUMN HC_Forum_Datenaustausch_ID TO HC_Forum_Datenaustausch_Config_ID');


-- 2018-10-18T15:08:28.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='forum-datenaustausch.ch config', Name='forum-datenaustausch.ch config', ColumnName='HC_Forum_Datenaustausch_Config_ID',Updated=TO_TIMESTAMP('2018-10-18 15:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544450
;

-- 2018-10-18T15:08:28.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HC_Forum_Datenaustausch_Config_ID', Name='forum-datenaustausch.ch config', Description=NULL, Help=NULL WHERE AD_Element_ID=544450
;

-- 2018-10-18T15:08:28.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HC_Forum_Datenaustausch_Config_ID', Name='forum-datenaustausch.ch config', Description=NULL, Help=NULL, AD_Element_ID=544450 WHERE UPPER(ColumnName)='HC_FORUM_DATENAUSTAUSCH_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-10-18T15:08:28.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HC_Forum_Datenaustausch_Config_ID', Name='forum-datenaustausch.ch config', Description=NULL, Help=NULL WHERE AD_Element_ID=544450 AND IsCentrallyMaintained='Y'
;

-- 2018-10-18T15:08:28.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='forum-datenaustausch.ch config', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=544450) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 544450)
;

-- 2018-10-18T15:08:28.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='forum-datenaustausch.ch config', Name='forum-datenaustausch.ch config' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=544450)
;


