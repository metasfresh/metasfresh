-- 2019-07-22T06:44:05.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Description='Empties Receive',Updated=TO_TIMESTAMP('2019-07-22 09:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540323
;

-- 2019-07-22T06:44:05.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsActive='Y', Name='Leergut Rücknahme', Description='Empties Receive',Updated=TO_TIMESTAMP('2019-07-22 09:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540781
;

-- 2019-07-22T06:48:40.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,Name,Description,EntityType) VALUES (540782,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-07-22 09:48:40','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-07-22 09:48:40','YYYY-MM-DD HH24:MI:SS'),100,582175,'Y',410,420,1,1,55303,0,'Reversal ID','ID of document reversal','D')
;

-- 2019-07-22T06:48:40.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-22T06:48:40.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457) 
;

-- 2019-07-22T06:48:41.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582175
;

-- 2019-07-22T06:48:41.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582175)
;

-- 2019-07-22T06:50:15.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,560182,582175,'Y',0,TO_TIMESTAMP('2019-07-22 09:50:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',430,540110,TO_TIMESTAMP('2019-07-22 09:50:15','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540782,'F','N','N',0,'Reversal_id')
;

-- 2019-07-22T06:50:26.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Reversal_ID',Updated=TO_TIMESTAMP('2019-07-22 09:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560182
;

-- 2019-07-22T06:50:35.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-07-22 09:50:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560182
;

-- 2019-07-22T06:50:38.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-22 09:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560182
;

-- 2019-07-22T06:53:31.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Description='Empties Return',Updated=TO_TIMESTAMP('2019-07-22 09:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540322
;

-- 2019-07-22T06:53:31.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsActive='Y', Name='Leergut Ausgabe', Description='Empties Return',Updated=TO_TIMESTAMP('2019-07-22 09:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540783
;

-- 2019-07-22T06:54:02.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,Name,Description,EntityType) VALUES (540778,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-07-22 09:54:02','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-07-22 09:54:02','YYYY-MM-DD HH24:MI:SS'),100,582176,'Y',390,390,1,1,55303,0,'Reversal ID','ID of document reversal','D')
;

-- 2019-07-22T06:54:02.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-22T06:54:02.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457) 
;

-- 2019-07-22T06:54:02.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582176
;

-- 2019-07-22T06:54:02.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582176)
;

-- 2019-07-22T06:55:41.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,560183,582176,'Y',0,TO_TIMESTAMP('2019-07-22 09:55:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',40,540116,TO_TIMESTAMP('2019-07-22 09:55:41','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540778,'F','N','N',0,'Reversal_ID')
;

-- 2019-07-22T06:57:26.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-07-22 09:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560183
;

-- 2019-07-22T06:59:18.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560183
;

-- 2019-07-22T07:00:48.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,560184,582176,'Y',0,TO_TIMESTAMP('2019-07-22 10:00:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',380,540108,TO_TIMESTAMP('2019-07-22 10:00:48','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540778,'F','N','N',0,'Reversal_ID')
;

-- 2019-07-22T07:01:34.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Storno-Gegenbeleg', IsTranslated='Y', Name='Storno-Gegenbeleg',Updated=TO_TIMESTAMP('2019-07-22 10:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=53457
;

-- 2019-07-22T07:01:34.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53457,'de_CH') 
;

-- 2019-07-22T07:01:42.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Storno-Gegenbeleg', IsTranslated='Y', Name='Storno-Gegenbeleg',Updated=TO_TIMESTAMP('2019-07-22 10:01:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=53457
;

-- 2019-07-22T07:01:42.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53457,'de_DE') 
;

-- 2019-07-22T07:01:42.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(53457,'de_DE') 
;

-- 2019-07-22T07:01:42.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Reversal_ID', Name='Storno-Gegenbeleg', Description='ID of document reversal', Help=NULL WHERE AD_Element_ID=53457
;

-- 2019-07-22T07:01:42.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Reversal_ID', Name='Storno-Gegenbeleg', Description='ID of document reversal', Help=NULL, AD_Element_ID=53457 WHERE UPPER(ColumnName)='REVERSAL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-22T07:01:42.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Reversal_ID', Name='Storno-Gegenbeleg', Description='ID of document reversal', Help=NULL WHERE AD_Element_ID=53457 AND IsCentrallyMaintained='Y'
;

-- 2019-07-22T07:01:42.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Storno-Gegenbeleg', Description='ID of document reversal', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53457) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53457)
;

-- 2019-07-22T07:01:42.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Storno-Gegenbeleg', Name='Storno-Gegenbeleg' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=53457)
;

-- 2019-07-22T07:01:42.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Storno-Gegenbeleg', Description='ID of document reversal', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 53457
;

-- 2019-07-22T07:01:42.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Storno-Gegenbeleg', Description='ID of document reversal', Help=NULL WHERE AD_Element_ID = 53457
;

-- 2019-07-22T07:01:42.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Storno-Gegenbeleg', Description = 'ID of document reversal', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53457
;

-- 2019-07-22T07:11:22.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-07-22 10:11:22','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-07-22 10:11:22','YYYY-MM-DD HH24:MI:SS'),100,541018,'T','Empties Return',0,'U')
;

-- 2019-07-22T07:11:22.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541018 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-07-22T07:12:53.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541018,3521,0,'Y',TO_TIMESTAMP('2019-07-22 10:12:53','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-22 10:12:53','YYYY-MM-DD HH24:MI:SS'),'N',540322,100,319,0,'D')
;

-- 2019-07-22T07:13:52.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Empties Return_Reversal_ID', EntityType='D',Updated=TO_TIMESTAMP('2019-07-22 10:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541018
;

-- 2019-07-22T07:14:20.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Empties_Return_Reversal_ID',Updated=TO_TIMESTAMP('2019-07-22 10:14:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541018
;

-- 2019-07-22T07:14:42.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541018,Updated=TO_TIMESTAMP('2019-07-22 10:14:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582176
;

-- 2019-07-22T07:16:58.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-07-22 10:16:57','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-07-22 10:16:57','YYYY-MM-DD HH24:MI:SS'),100,541019,'T','Empties_Receive_Reversal_ID',0,'D')
;

-- 2019-07-22T07:16:58.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541019 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-07-22T07:17:38.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541019,3521,0,'Y',TO_TIMESTAMP('2019-07-22 10:17:38','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-07-22 10:17:38','YYYY-MM-DD HH24:MI:SS'),'N',540323,100,319,0,'D')
;

-- 2019-07-22T07:18:10.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=18, AD_Reference_Value_ID=541019,Updated=TO_TIMESTAMP('2019-07-22 10:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582175
;

