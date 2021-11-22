-- 2021-05-19T20:08:15.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579194,0,'AlbertaTitle',TO_TIMESTAMP('2021-05-19 23:08:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Title','Title in Alberta',TO_TIMESTAMP('2021-05-19 23:08:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-19T20:08:15.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579194 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-19T20:08:34.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Titel', PrintName='Alberta-Titel',Updated=TO_TIMESTAMP('2021-05-19 23:08:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579194 AND AD_Language='de_CH'
;

-- 2021-05-19T20:08:34.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579194,'de_CH') 
;

-- 2021-05-19T20:08:47.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Titel', PrintName='Alberta-Titel',Updated=TO_TIMESTAMP('2021-05-19 23:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579194 AND AD_Language='de_DE'
;

-- 2021-05-19T20:08:47.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579194,'de_DE') 
;

-- 2021-05-19T20:08:47.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579194,'de_DE') 
;

-- 2021-05-19T20:08:47.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AlbertaTitle', Name='Titel', Description=NULL, Help=NULL WHERE AD_Element_ID=579194
;

-- 2021-05-19T20:08:47.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AlbertaTitle', Name='Titel', Description=NULL, Help=NULL, AD_Element_ID=579194 WHERE UPPER(ColumnName)='ALBERTATITLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-19T20:08:47.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AlbertaTitle', Name='Titel', Description=NULL, Help=NULL WHERE AD_Element_ID=579194 AND IsCentrallyMaintained='Y'
;

-- 2021-05-19T20:08:47.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Titel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579194) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579194)
;

-- 2021-05-19T20:08:47.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta-Titel', Name='Titel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579194)
;

-- 2021-05-19T20:08:47.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Titel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579194
;

-- 2021-05-19T20:08:47.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Titel', Description=NULL, Help=NULL WHERE AD_Element_ID = 579194
;

-- 2021-05-19T20:08:47.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Titel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579194
;

-- 2021-05-19T20:10:07.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573945,579194,0,17,541318,114,'AlbertaTitle','(select title from AD_User_Alberta ua where ua.ad_user_id = AD_User.ad_user_id)',TO_TIMESTAMP('2021-05-19 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Titel',0,0,TO_TIMESTAMP('2021-05-19 23:10:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-19T20:10:07.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573945 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-19T20:10:07.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579194) 
;


-- 2021-05-19T20:10:49.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='de.metas.vertical.healthcare.alberta',Updated=TO_TIMESTAMP('2021-05-19 23:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579159;



-- 2021-09-14T08:39:34.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 579814, 0, 'Alberta_Gender', TO_TIMESTAMP('2021-09-14 11:39:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'Geschlecht', 'Geschlecht', TO_TIMESTAMP('2021-09-14 11:39:34', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-09-14T08:39:34.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 579814
  AND NOT EXISTS(SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;


-- 2021-05-19T20:11:46.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated,
                       UpdatedBy, Version)
VALUES (0, 573946, 579814, 0, 17, 541317, 114, 'Alberta_Gender', '(select gender from AD_User_Alberta ua where ua.ad_user_id = AD_User.ad_user_id)', TO_TIMESTAMP('2021-05-19 23:11:46', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'de.metas.vertical.healthcare.alberta', 0, 255, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 0, 'Geschlecht', 0, 0, TO_TIMESTAMP('2021-05-19 23:11:46', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- 2021-05-19T20:11:46.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573946 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-19T20:11:46.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579159) 
;

-- 2021-05-19T20:12:59.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573947,579160,0,16,114,'Timestamp','(select timestamp from AD_User_Alberta ua where ua.ad_user_id = AD_User.ad_user_id)',TO_TIMESTAMP('2021-05-19 23:12:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,29,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Zeitstempel',0,0,TO_TIMESTAMP('2021-05-19 23:12:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-19T20:12:59.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573947 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-19T20:12:59.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579160) 
;

