-- 2021-06-29T16:06:17.582442200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584479
;

-- 2021-06-29T16:06:17.599442400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645197
;

-- 2021-06-29T16:06:17.605450100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=645197
;

-- 2021-06-29T16:06:17.608007600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=645197
;

-- 2021-06-29T16:06:57.974162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE API_Audit_Config DROP COLUMN IF EXISTS AD_User_InCharge_ID')
;

-- 2021-06-29T16:06:58.076952900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=573791
;

-- 2021-06-29T16:06:58.085906700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=573791
;

-- 2021-06-29T16:07:54.968414200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','NotifyUserInCharge','VARCHAR(255)',null,null)
;

-- 2021-06-29T16:12:01.228610400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579404,0,'AD_UserGroup_InCharge_ID',TO_TIMESTAMP('2021-06-29 19:12:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','User group in charge','User group in charge',TO_TIMESTAMP('2021-06-29 19:12:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:12:01.278217900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579404 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-29T16:12:17.697854200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Betreuende Nutzergruppe', PrintName='Betreuende Nutzergruppe',Updated=TO_TIMESTAMP('2021-06-29 19:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579404 AND AD_Language='de_CH'
;

-- 2021-06-29T16:12:17.704078400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579404,'de_CH') 
;

-- 2021-06-29T16:12:34.952179900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Betreuende Nutzergruppe', PrintName='Betreuende Nutzergruppe',Updated=TO_TIMESTAMP('2021-06-29 19:12:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579404 AND AD_Language='de_DE'
;

-- 2021-06-29T16:12:34.953177600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579404,'de_DE') 
;

-- 2021-06-29T16:12:34.969183500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579404,'de_DE') 
;

-- 2021-06-29T16:12:34.971186800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_UserGroup_InCharge_ID', Name='Betreuende Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=579404
;

-- 2021-06-29T16:12:34.972187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_UserGroup_InCharge_ID', Name='Betreuende Nutzergruppe', Description=NULL, Help=NULL, AD_Element_ID=579404 WHERE UPPER(ColumnName)='AD_USERGROUP_INCHARGE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-29T16:12:34.973187400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_UserGroup_InCharge_ID', Name='Betreuende Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=579404 AND IsCentrallyMaintained='Y'
;

-- 2021-06-29T16:12:34.974184800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Betreuende Nutzergruppe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579404) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579404)
;

-- 2021-06-29T16:12:34.992186300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Betreuende Nutzergruppe', Name='Betreuende Nutzergruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579404)
;

-- 2021-06-29T16:12:34.994183700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Betreuende Nutzergruppe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579404
;

-- 2021-06-29T16:12:34.995185400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Betreuende Nutzergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID = 579404
;

-- 2021-06-29T16:12:34.996186400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Betreuende Nutzergruppe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579404
;

-- 2021-06-29T16:23:41.618771900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541355,TO_TIMESTAMP('2021-06-29 19:23:41','YYYY-MM-DD HH24:MI:SS'),100,'User group selection','D','Y','N','AD_User - Group',TO_TIMESTAMP('2021-06-29 19:23:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-06-29T16:23:41.621791500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541355 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-06-29T16:24:36.787939700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,564371,564370,0,541355,541187,TO_TIMESTAMP('2021-06-29 19:24:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_UserGroup.Name','N',TO_TIMESTAMP('2021-06-29 19:24:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:26:22.396792300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574810,579404,0,30,541355,541635,'AD_UserGroup_InCharge_ID',TO_TIMESTAMP('2021-06-29 19:26:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Betreuende Nutzergruppe',0,0,TO_TIMESTAMP('2021-06-29 19:26:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-29T16:26:22.399791200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574810 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-29T16:26:22.402789700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579404) 
;

-- 2021-06-29T16:26:32.132795800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE public.API_Audit_Config ADD COLUMN AD_UserGroup_InCharge_ID NUMERIC(10)')
;

-- 2021-06-29T16:26:32.145817100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE API_Audit_Config ADD CONSTRAINT ADUserGroupInCharge_APIAuditConfig FOREIGN KEY (AD_UserGroup_InCharge_ID) REFERENCES public.AD_UserGroup DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-29T16:29:09.536475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_UserGroup_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-06-29T16:29:22.292734100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2021-06-29 19:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574810
;

-- 2021-06-29T16:29:25.323532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_UserGroup_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-06-29T16:31:51.847600800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_UserGroup_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-06-29T16:34:11.284687600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2021-06-29 19:34:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574810
;

-- 2021-06-29T16:34:12.709802600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_UserGroup_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-06-29T16:34:51.619758400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-06-29 19:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574810
;

-- 2021-06-29T16:34:57.124414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_UserGroup_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-06-29T16:35:00.562957600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('api_audit_config','AD_UserGroup_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2021-06-29T16:42:47.341146700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574810,649879,0,543895,0,TO_TIMESTAMP('2021-06-29 19:42:47','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Betreuende Nutzergruppe',0,10,0,1,1,TO_TIMESTAMP('2021-06-29 19:42:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:42:47.625930600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-29T16:42:47.711531400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579404) 
;

-- 2021-06-29T16:42:47.715527700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649879
;

-- 2021-06-29T16:42:47.740454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649879)
;

-- 2021-06-29T16:43:16.253264600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,649879,0,543895,586906,545830,'F',TO_TIMESTAMP('2021-06-29 19:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Betreuende Nutzergruppe',20,0,0,TO_TIMESTAMP('2021-06-29 19:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-29T16:43:51.410135900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-06-29 19:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586906
;

