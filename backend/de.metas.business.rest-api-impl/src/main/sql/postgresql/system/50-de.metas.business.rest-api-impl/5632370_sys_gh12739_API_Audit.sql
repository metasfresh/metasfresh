-- 2022-03-30T06:05:45.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580734,0,'KeepErroredRequestDays',TO_TIMESTAMP('2022-03-30 09:05:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Days keep errored request audit','Days keep errored request audit',TO_TIMESTAMP('2022-03-30 09:05:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-30T06:05:45.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580734 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-03-30T06:09:27.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tage für die Überprüfung fehlerhafter Anträge', PrintName='Tage für die Überprüfung fehlerhafter Anträge',Updated=TO_TIMESTAMP('2022-03-30 09:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580734 AND AD_Language='de_CH'
;

-- 2022-03-30T06:09:27.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580734,'de_CH') 
;

-- 2022-03-30T06:09:33.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tage für die Überprüfung fehlerhafter Anträge', PrintName='Tage für die Überprüfung fehlerhafter Anträge',Updated=TO_TIMESTAMP('2022-03-30 09:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580734 AND AD_Language='de_DE'
;

-- 2022-03-30T06:09:33.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580734,'de_DE') 
;

-- 2022-03-30T06:09:33.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580734,'de_DE') 
;

-- 2022-03-30T06:09:33.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='KeepErroredRequestDays', Name='Tage für die Überprüfung fehlerhafter Anträge', Description=NULL, Help=NULL WHERE AD_Element_ID=580734
;

-- 2022-03-30T06:09:33.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KeepErroredRequestDays', Name='Tage für die Überprüfung fehlerhafter Anträge', Description=NULL, Help=NULL, AD_Element_ID=580734 WHERE UPPER(ColumnName)='KEEPERROREDREQUESTDAYS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-30T06:09:33.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KeepErroredRequestDays', Name='Tage für die Überprüfung fehlerhafter Anträge', Description=NULL, Help=NULL WHERE AD_Element_ID=580734 AND IsCentrallyMaintained='Y'
;

-- 2022-03-30T06:09:33.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tage für die Überprüfung fehlerhafter Anträge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580734) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580734)
;

-- 2022-03-30T06:09:33.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Tage für die Überprüfung fehlerhafter Anträge', Name='Tage für die Überprüfung fehlerhafter Anträge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580734)
;

-- 2022-03-30T06:09:33.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Tage für die Überprüfung fehlerhafter Anträge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580734
;

-- 2022-03-30T06:09:33.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Tage für die Überprüfung fehlerhafter Anträge', Description=NULL, Help=NULL WHERE AD_Element_ID = 580734
;

-- 2022-03-30T06:09:33.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Tage für die Überprüfung fehlerhafter Anträge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580734
;

-- 2022-03-30T06:09:38.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tage für die Überprüfung fehlerhafter Anträge', PrintName='Tage für die Überprüfung fehlerhafter Anträge',Updated=TO_TIMESTAMP('2022-03-30 09:09:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580734 AND AD_Language='nl_NL'
;

-- 2022-03-30T06:09:38.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580734,'nl_NL') 
;

-- 2022-03-30T06:10:22.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582644,580734,0,22,541635,'KeepErroredRequestDays',TO_TIMESTAMP('2022-03-30 09:10:22','YYYY-MM-DD HH24:MI:SS'),100,'N','7','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Tage für die Überprüfung fehlerhafter Anträge',0,0,TO_TIMESTAMP('2022-03-30 09:10:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-30T06:10:22.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-30T06:10:22.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580734) 
;

-- 2022-03-30T06:10:24.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE public.API_Audit_Config ADD COLUMN KeepErroredRequestDays NUMERIC DEFAULT 7 NOT NULL')
;

-- 2022-03-30T06:11:48.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541453,'S',TO_TIMESTAMP('2022-03-30 09:11:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.audit.request.IteratorBufferSize',TO_TIMESTAMP('2022-03-30 09:11:48','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2022-03-30T06:16:29.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582644,691574,0,543895,0,TO_TIMESTAMP('2022-03-30 09:16:29','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Tage für die Überprüfung fehlerhafter Anträge',0,20,0,1,1,TO_TIMESTAMP('2022-03-30 09:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-30T06:16:29.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-30T06:16:29.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580734) 
;

-- 2022-03-30T06:16:29.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691574
;

-- 2022-03-30T06:16:29.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691574)
;

-- 2022-03-30T06:17:18.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691574,0,543895,605272,545783,'F',TO_TIMESTAMP('2022-03-30 09:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tage für die Überprüfung fehlerhafter Anträge',50,0,0,TO_TIMESTAMP('2022-03-30 09:17:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-30T06:19:27.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=11,Updated=TO_TIMESTAMP('2022-03-30 09:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582644
;

-- 2022-03-30T06:19:30.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepErroredRequestDays','NUMERIC(10)',null,'7')
;

-- 2022-03-30T06:19:30.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepErroredRequestDays=7 WHERE KeepErroredRequestDays IS NULL
;

-- 2022-03-30T06:20:10.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','KeepErroredRequestDays','NUMERIC(10)',null,'7')
;

-- 2022-03-30T06:20:10.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE API_Audit_Config SET KeepErroredRequestDays=7 WHERE KeepErroredRequestDays IS NULL
;

-- 2022-03-30T11:36:55.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tage Aufrufrev. mit Fehler aufheben', PrintName='Tage Aufrufrev. mit Fehler aufheben',Updated=TO_TIMESTAMP('2022-03-30 14:36:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580734 AND AD_Language='de_CH'
;

-- 2022-03-30T11:36:55.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580734,'de_CH')
;

-- 2022-03-30T11:36:58.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tage Aufrufrev. mit Fehler aufheben', PrintName='Tage Aufrufrev. mit Fehler aufheben',Updated=TO_TIMESTAMP('2022-03-30 14:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580734 AND AD_Language='de_DE'
;

-- 2022-03-30T11:36:58.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580734,'de_DE')
;

-- 2022-03-30T11:36:58.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580734,'de_DE')
;

-- 2022-03-30T11:36:58.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='KeepErroredRequestDays', Name='Tage Aufrufrev. mit Fehler aufheben', Description=NULL, Help=NULL WHERE AD_Element_ID=580734
;

-- 2022-03-30T11:36:58.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KeepErroredRequestDays', Name='Tage Aufrufrev. mit Fehler aufheben', Description=NULL, Help=NULL, AD_Element_ID=580734 WHERE UPPER(ColumnName)='KEEPERROREDREQUESTDAYS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-30T11:36:58.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='KeepErroredRequestDays', Name='Tage Aufrufrev. mit Fehler aufheben', Description=NULL, Help=NULL WHERE AD_Element_ID=580734 AND IsCentrallyMaintained='Y'
;

-- 2022-03-30T11:36:58.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Tage Aufrufrev. mit Fehler aufheben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580734) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580734)
;

-- 2022-03-30T11:36:58.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Tage Aufrufrev. mit Fehler aufheben', Name='Tage Aufrufrev. mit Fehler aufheben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580734)
;

-- 2022-03-30T11:36:58.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Tage Aufrufrev. mit Fehler aufheben', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580734
;

-- 2022-03-30T11:36:58.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Tage Aufrufrev. mit Fehler aufheben', Description=NULL, Help=NULL WHERE AD_Element_ID = 580734
;

-- 2022-03-30T11:36:58.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Tage Aufrufrev. mit Fehler aufheben', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580734
;

-- 2022-03-30T11:37:04.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tage Aufrufrev. mit Fehler aufheben', PrintName='Tage Aufrufrev. mit Fehler aufheben',Updated=TO_TIMESTAMP('2022-03-30 14:37:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580734 AND AD_Language='nl_NL'
;

-- 2022-03-30T11:37:04.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580734,'nl_NL')
;

