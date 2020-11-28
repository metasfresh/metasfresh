-- 2019-06-14T15:43:41.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576840,0,'IsNewUIElementsOnly',TO_TIMESTAMP('2019-06-14 15:43:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','New UI Elements Only','New UI Elements Only',TO_TIMESTAMP('2019-06-14 15:43:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-14T15:43:41.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576840 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-06-14T15:44:01.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,576840,0,541143,541483,20,'IsNewUIElementsOnly',TO_TIMESTAMP('2019-06-14 15:44:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ui.web',0,'Y','N','Y','N','Y','N','New UI Elements Only',30,TO_TIMESTAMP('2019-06-14 15:44:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-14T15:44:01.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541483 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-06-14T15:44:14.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=100,Updated=TO_TIMESTAMP('2019-06-14 15:44:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541472
;

-- 2019-06-14T15:44:19.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2019-06-14 15:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541483
;

-- 2019-06-14T16:21:05.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (
    select 1
    from AD_UI_ElementGroup g
    inner join AD_UI_Column c on c.ad_ui_column_id=g.ad_ui_column_id
    inner join AD_UI_Section s on s.ad_ui_section_id=c.ad_ui_section_id
    where g.ad_ui_elementgroup_id=@AD_UI_ElementGroup_ID@
          and s.ad_tab_id=ad_field.ad_tab_id
)
and (
    @IsNewUIElementsOnly/N@=''N''
    or not exists(select 1 from AD_UI_Element uie where uie.AD_Field_ID=AD_Field.AD_Field_ID)
)
', Description='used for AD_UI_ElementGroup_AddField process', Name='AD_Field by AD_UI_ElementGroup_ID_and_IsNewUIElementsOnly',Updated=TO_TIMESTAMP('2019-06-14 16:21:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540442
;

-- 2019-06-14T16:22:37.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (
    select 1
    from AD_UI_ElementGroup g
    inner join AD_UI_Column c on c.ad_ui_column_id=g.ad_ui_column_id
    inner join AD_UI_Section s on s.ad_ui_section_id=c.ad_ui_section_id
    where g.ad_ui_elementgroup_id=@AD_UI_ElementGroup_ID@
          and s.ad_tab_id=ad_field.ad_tab_id
)
and (
    ''@IsNewUIElementsOnly/N@''=''N''
    or not exists(select 1 from AD_UI_Element uie where uie.AD_Field_ID=AD_Field.AD_Field_ID)
)
',Updated=TO_TIMESTAMP('2019-06-14 16:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540442
;

