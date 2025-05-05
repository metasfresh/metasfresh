-- Element: Default_OrgCode
-- 2022-09-09T06:50:41.161Z
UPDATE AD_Element_Trl SET Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.',Updated=TO_TIMESTAMP('2022-09-09 09:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581431 AND AD_Language='en_US'
;

-- 2022-09-09T06:50:41.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581431,'en_US') 
;

-- Element: Default_OrgCode
-- 2022-09-09T06:50:50.775Z
UPDATE AD_Element_Trl SET Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.',Updated=TO_TIMESTAMP('2022-09-09 09:50:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581431 AND AD_Language='de_DE'
;

-- 2022-09-09T06:50:50.798Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581431,'de_DE') 
;

-- 2022-09-09T06:50:50.831Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581431,'de_DE') 
;

-- 2022-09-09T06:50:50.832Z
UPDATE AD_Column SET ColumnName='Default_OrgCode', Name='Default Org Suchschlüssel', Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', Help=NULL WHERE AD_Element_ID=581431
;

-- 2022-09-09T06:50:50.834Z
UPDATE AD_Process_Para SET ColumnName='Default_OrgCode', Name='Default Org Suchschlüssel', Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', Help=NULL, AD_Element_ID=581431 WHERE UPPER(ColumnName)='DEFAULT_ORGCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-09T06:50:50.851Z
UPDATE AD_Process_Para SET ColumnName='Default_OrgCode', Name='Default Org Suchschlüssel', Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', Help=NULL WHERE AD_Element_ID=581431 AND IsCentrallyMaintained='Y'
;

-- 2022-09-09T06:50:50.851Z
UPDATE AD_Field SET Name='Default Org Suchschlüssel', Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581431) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581431)
;

-- 2022-09-09T06:50:50.878Z
UPDATE AD_Tab SET Name='Default Org Suchschlüssel', Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581431
;

-- 2022-09-09T06:50:50.880Z
UPDATE AD_WINDOW SET Name='Default Org Suchschlüssel', Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', Help=NULL WHERE AD_Element_ID = 581431
;

-- 2022-09-09T06:50:50.882Z
UPDATE AD_Menu SET   Name = 'Default Org Suchschlüssel', Description = 'Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581431
;

-- Element: Default_OrgCode
-- 2022-09-09T06:50:53.102Z
UPDATE AD_Element_Trl SET Description='Fallback org value used for importing invoice candidates when there is no org-code set in the file. Its value is set from a constant configured in the import format.',Updated=TO_TIMESTAMP('2022-09-09 09:50:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581431 AND AD_Language='de_CH'
;

-- 2022-09-09T06:50:53.104Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581431,'de_CH') 
;

-- Element: Default_OrgCode
-- 2022-09-09T06:53:01.229Z
UPDATE AD_Element_Trl SET Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.',Updated=TO_TIMESTAMP('2022-09-09 09:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581431 AND AD_Language='de_CH'
;

-- 2022-09-09T06:53:01.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581431,'de_CH') 
;

-- Element: Default_OrgCode
-- 2022-09-09T06:53:04.705Z
UPDATE AD_Element_Trl SET Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.',Updated=TO_TIMESTAMP('2022-09-09 09:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581431 AND AD_Language='de_DE'
;

-- 2022-09-09T06:53:04.706Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581431,'de_DE') 
;

-- 2022-09-09T06:53:04.711Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581431,'de_DE') 
;

-- 2022-09-09T06:53:04.712Z
UPDATE AD_Column SET ColumnName='Default_OrgCode', Name='Default Org Suchschlüssel', Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', Help=NULL WHERE AD_Element_ID=581431
;

-- 2022-09-09T06:53:04.713Z
UPDATE AD_Process_Para SET ColumnName='Default_OrgCode', Name='Default Org Suchschlüssel', Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', Help=NULL, AD_Element_ID=581431 WHERE UPPER(ColumnName)='DEFAULT_ORGCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-09T06:53:04.713Z
UPDATE AD_Process_Para SET ColumnName='Default_OrgCode', Name='Default Org Suchschlüssel', Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', Help=NULL WHERE AD_Element_ID=581431 AND IsCentrallyMaintained='Y'
;

-- 2022-09-09T06:53:04.714Z
UPDATE AD_Field SET Name='Default Org Suchschlüssel', Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581431) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581431)
;

-- 2022-09-09T06:53:04.755Z
UPDATE AD_Tab SET Name='Default Org Suchschlüssel', Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581431
;

-- 2022-09-09T06:53:04.757Z
UPDATE AD_WINDOW SET Name='Default Org Suchschlüssel', Description='Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', Help=NULL WHERE AD_Element_ID = 581431
;

-- 2022-09-09T06:53:04.757Z
UPDATE AD_Menu SET   Name = 'Default Org Suchschlüssel', Description = 'Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581431
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Default Org Suchschlüssel
-- Column: I_Invoice_Candidate.Default_OrgCode
-- 2022-09-09T06:53:48.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584273,707135,0,546594,0,TO_TIMESTAMP('2022-09-09 09:53:48','YYYY-MM-DD HH24:MI:SS'),100,'Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Default Org Suchschlüssel',0,20,0,1,1,TO_TIMESTAMP('2022-09-09 09:53:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-09T06:53:48.231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-09T06:53:48.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581431) 
;

-- 2022-09-09T06:53:48.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707135
;

-- 2022-09-09T06:53:48.245Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707135)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Org Code
-- Column: I_Invoice_Candidate.OrgCode
-- 2022-09-09T06:54:01.933Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=612917
;

-- UI Column: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10
-- UI Element Group: org
-- 2022-09-09T06:54:12.945Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546347,549918,TO_TIMESTAMP('2022-09-09 09:54:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2022-09-09 09:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> org.Org Suchschlüssel
-- Column: I_Invoice_Candidate.OrgCode
-- 2022-09-09T06:54:25.262Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707009,0,546594,612949,549918,'F',TO_TIMESTAMP('2022-09-09 09:54:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Org Suchschlüssel',10,0,0,TO_TIMESTAMP('2022-09-09 09:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> org.Default Org Suchschlüssel
-- Column: I_Invoice_Candidate.Default_OrgCode
-- 2022-09-09T06:54:34.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707135,0,546594,612950,549918,'F',TO_TIMESTAMP('2022-09-09 09:54:34','YYYY-MM-DD HH24:MI:SS'),100,'Fallback-Org-Suchschlüssel, der für den Import von Rechnungskandidaten verwendet wird, wenn in der Datei kein Org-Code angegeben ist. Sein Wert wird anhand einer im Importformat konfigurierten Konstante festgelegt.','Y','N','N','Y','N','N','N',0,'Default Org Suchschlüssel',20,0,0,TO_TIMESTAMP('2022-09-09 09:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Default Org Suchschlüssel
-- Column: I_Invoice_Candidate.Default_OrgCode
-- 2022-09-09T06:56:08.757Z
UPDATE AD_Field SET EntityType='D', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-09 09:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707135
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Org Suchschlüssel
-- Column: I_Invoice_Candidate.OrgCode
-- 2022-09-09T06:56:31.201Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-09-09 09:56:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707009
;

