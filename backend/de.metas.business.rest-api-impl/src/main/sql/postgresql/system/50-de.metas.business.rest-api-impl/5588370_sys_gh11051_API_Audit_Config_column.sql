-- 2021-05-13T11:01:34.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541313,TO_TIMESTAMP('2021-05-13 14:01:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Notify_List',TO_TIMESTAMP('2021-05-13 14:01:34','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-05-13T11:01:34.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541313 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-05-13T11:02:11.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542499,541313,TO_TIMESTAMP('2021-05-13 14:02:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Never',TO_TIMESTAMP('2021-05-13 14:02:11','YYYY-MM-DD HH24:MI:SS'),100,'NEVER','Never')
;

-- 2021-05-13T11:02:11.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542499 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-13T11:02:29.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542500,541313,TO_TIMESTAMP('2021-05-13 14:02:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invocations with error',TO_TIMESTAMP('2021-05-13 14:02:29','YYYY-MM-DD HH24:MI:SS'),100,'ONLY_ON_ERROR','Invocations with error')
;

-- 2021-05-13T11:02:29.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542500 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-13T11:04:38.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542501,541313,TO_TIMESTAMP('2021-05-13 14:04:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','All invocations',TO_TIMESTAMP('2021-05-13 14:04:38','YYYY-MM-DD HH24:MI:SS'),100,'ALWAYS','All invocations')
;

-- 2021-05-13T11:04:38.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542501 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-05-13T11:05:16.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Allen Aufrufen',Updated=TO_TIMESTAMP('2021-05-13 14:05:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542501
;

-- 2021-05-13T11:05:21.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Allen Aufrufen',Updated=TO_TIMESTAMP('2021-05-13 14:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542501
;

-- 2021-05-13T11:05:40.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Niemals',Updated=TO_TIMESTAMP('2021-05-13 14:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542499
;

-- 2021-05-13T11:05:44.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Niemals',Updated=TO_TIMESTAMP('2021-05-13 14:05:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542499
;

-- 2021-05-13T11:06:11.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Aufrufen mit Fehler',Updated=TO_TIMESTAMP('2021-05-13 14:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542500
;

-- 2021-05-13T11:06:17.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Aufrufen mit Fehler',Updated=TO_TIMESTAMP('2021-05-13 14:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542500
;

-- 2021-05-13T11:07:36.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579152,0,'NotifyUserInCharge',TO_TIMESTAMP('2021-05-13 14:07:36','YYYY-MM-DD HH24:MI:SS'),100,'Specifies if and when the responsible user shall be notified about API invocations','D','Y','Notify on','Notify on',TO_TIMESTAMP('2021-05-13 14:07:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-13T11:07:36.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579152 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-13T11:08:02.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Name='Benachrichtigung bei', PrintName='Benachrichtigung bei',Updated=TO_TIMESTAMP('2021-05-13 14:08:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579152 AND AD_Language='de_CH'
;

-- 2021-05-13T11:08:02.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579152,'de_CH') 
;

-- 2021-05-13T11:08:21.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Name='Benachrichtigung bei', PrintName='Benachrichtigung bei',Updated=TO_TIMESTAMP('2021-05-13 14:08:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579152 AND AD_Language='de_DE'
;

-- 2021-05-13T11:08:21.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579152,'de_DE') 
;

-- 2021-05-13T11:08:21.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579152,'de_DE') 
;

-- 2021-05-13T11:08:21.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NotifyUserInCharge', Name='Benachrichtigung bei', Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Help=NULL WHERE AD_Element_ID=579152
;

-- 2021-05-13T11:08:21.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NotifyUserInCharge', Name='Benachrichtigung bei', Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Help=NULL, AD_Element_ID=579152 WHERE UPPER(ColumnName)='NOTIFYUSERINCHARGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-13T11:08:21.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NotifyUserInCharge', Name='Benachrichtigung bei', Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Help=NULL WHERE AD_Element_ID=579152 AND IsCentrallyMaintained='Y'
;

-- 2021-05-13T11:08:21.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benachrichtigung bei', Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579152) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579152)
;

-- 2021-05-13T11:08:21.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Benachrichtigung bei', Name='Benachrichtigung bei' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579152)
;

-- 2021-05-13T11:08:21.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Benachrichtigung bei', Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579152
;

-- 2021-05-13T11:08:21.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Benachrichtigung bei', Description='Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', Help=NULL WHERE AD_Element_ID = 579152
;

-- 2021-05-13T11:08:21.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Benachrichtigung bei', Description = 'Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579152
;

-- 2021-05-13T11:10:22.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573843,579152,0,17,541313,541635,'NotifyUserInCharge',TO_TIMESTAMP('2021-05-13 14:10:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Benachrichtigung bei',0,0,TO_TIMESTAMP('2021-05-13 14:10:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-13T11:10:22.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573843 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-13T11:10:22.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579152) 
;

-- 2021-05-13T11:10:28.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('API_Audit_Config','ALTER TABLE public.API_Audit_Config ADD COLUMN NotifyUserInCharge VARCHAR(255)')
;

-- 2021-05-13T11:11:40.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543834,545830,TO_TIMESTAMP('2021-05-13 14:11:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',20,TO_TIMESTAMP('2021-05-13 14:11:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-13T11:11:43.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-13 14:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545784
;

-- 2021-05-13T11:14:58.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573843,645694,0,543895,TO_TIMESTAMP('2021-05-13 14:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll',255,'D','Y','N','N','N','N','N','N','N','Benachrichtigung bei',TO_TIMESTAMP('2021-05-13 14:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-13T11:14:58.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645694 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-13T11:14:58.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579152) 
;

-- 2021-05-13T11:14:58.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645694
;

-- 2021-05-13T11:14:59.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645694)
;

-- 2021-05-13T11:15:20.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645694,0,543895,584838,545830,'F',TO_TIMESTAMP('2021-05-13 14:15:20','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob und bei welchen API-Aufrufen der Betreuer informiert werden soll','Y','N','N','Y','N','N','N',0,'Benachrichtigung bei',10,0,0,TO_TIMESTAMP('2021-05-13 14:15:20','YYYY-MM-DD HH24:MI:SS'),100)
;

