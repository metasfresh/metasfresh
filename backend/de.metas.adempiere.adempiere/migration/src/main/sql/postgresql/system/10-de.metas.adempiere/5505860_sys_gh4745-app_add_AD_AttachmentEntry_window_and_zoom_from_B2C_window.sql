-- 2018-11-07T14:23:43.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2018-11-07 14:23:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540875
;

-- 2018-11-07T14:59:53.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559446,0,540850,540960,554224,'F',TO_TIMESTAMP('2018-11-07 14:59:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Anhang-ID',50,0,0,TO_TIMESTAMP('2018-11-07 14:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:00:20.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575858,0,TO_TIMESTAMP('2018-11-07 15:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anhang-ID','Anhang-ID',TO_TIMESTAMP('2018-11-07 15:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:00:20.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=575858 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-11-07T15:00:22.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:00:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575858 AND AD_Language='de_CH'
;

-- 2018-11-07T15:00:22.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'de_CH') 
;

-- 2018-11-07T15:00:33.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:00:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Attachment-ID',PrintName='Attachment-ID' WHERE AD_Element_ID=575858 AND AD_Language='en_US'
;

-- 2018-11-07T15:00:33.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'en_US') 
;

-- 2018-11-07T15:01:00.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=575858, Description=NULL, Help=NULL, Name='Anhang-ID',Updated=TO_TIMESTAMP('2018-11-07 15:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559446
;

-- 2018-11-07T15:01:00.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575858) 
;

-- 2018-11-07T15:02:54.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='AD_AttachmentEntry_ID',Updated=TO_TIMESTAMP('2018-11-07 15:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554224
;

-- 2018-11-07T15:03:42.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-11-07 15:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554224
;

-- 2018-11-07T15:04:25.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Editierbarer Datensatz', PrintName='Editierbarer Datensatz',Updated=TO_TIMESTAMP('2018-11-07 15:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575858
;

-- 2018-11-07T15:04:25.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Editierbarer Datensatz', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575858) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575858)
;

-- 2018-11-07T15:04:25.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Editierbarer Datensatz', Name='Editierbarer Datensatz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575858)
;

-- 2018-11-07T15:04:29.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:04:29','YYYY-MM-DD HH24:MI:SS'),Name='Editierbarer Datensatz',PrintName='Editierbarer Datensatz' WHERE AD_Element_ID=575858 AND AD_Language='de_CH'
;

-- 2018-11-07T15:04:29.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'de_CH') 
;

-- 2018-11-07T15:04:39.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:04:39','YYYY-MM-DD HH24:MI:SS'),Name='Editable record',PrintName='Editable record' WHERE AD_Element_ID=575858 AND AD_Language='en_US'
;

-- 2018-11-07T15:04:39.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'en_US') 
;

-- 2018-11-07T15:05:02.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:05:02','YYYY-MM-DD HH24:MI:SS'),Description='Zoom to this record in for editing' WHERE AD_Element_ID=575858 AND AD_Language='en_US'
;

-- 2018-11-07T15:05:02.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'en_US') 
;

-- 2018-11-07T15:05:54.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:05:54','YYYY-MM-DD HH24:MI:SS'),Description='The record can be edited when zooming to this Field' WHERE AD_Element_ID=575858 AND AD_Language='de_CH'
;

-- 2018-11-07T15:05:54.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'de_CH') 
;

-- 2018-11-07T15:06:38.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:06:38','YYYY-MM-DD HH24:MI:SS'),Description='The record can be edited when zooming to it using this field' WHERE AD_Element_ID=575858 AND AD_Language='de_CH'
;

-- 2018-11-07T15:06:38.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'de_CH') 
;

-- 2018-11-07T15:06:41.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:06:41','YYYY-MM-DD HH24:MI:SS'),Description='The record can be edited when zooming to it using this field' WHERE AD_Element_ID=575858 AND AD_Language='en_US'
;

-- 2018-11-07T15:06:41.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'en_US') 
;

-- 2018-11-07T15:07:32.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-07 15:07:32','YYYY-MM-DD HH24:MI:SS'),Description='Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.' WHERE AD_Element_ID=575858 AND AD_Language='de_CH'
;

-- 2018-11-07T15:07:32.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575858,'de_CH') 
;

-- 2018-11-07T15:07:36.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.',Updated=TO_TIMESTAMP('2018-11-07 15:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575858
;

-- 2018-11-07T15:07:36.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Editierbarer Datensatz', Description='Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575858) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575858)
;

-- 2018-11-07T15:38:16.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='7',Updated=TO_TIMESTAMP('2018-11-07 15:38:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540833
;

-- 2018-11-07T15:42:40.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540532,TO_TIMESTAMP('2018-11-07 15:42:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','Anhang','N',TO_TIMESTAMP('2018-11-07 15:42:39','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2018-11-07T15:42:40.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540532 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2018-11-07T15:42:56.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,541375,540833,540532,TO_TIMESTAMP('2018-11-07 15:42:55','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Anhang','N',10,0,TO_TIMESTAMP('2018-11-07 15:42:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:42:56.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=541375 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2018-11-07T15:43:00.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557027,570290,0,541375,TO_TIMESTAMP('2018-11-07 15:43:00','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Attachment entry',TO_TIMESTAMP('2018-11-07 15:43:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:00.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:00.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557028,570291,0,541375,TO_TIMESTAMP('2018-11-07 15:43:00','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2018-11-07 15:43:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:00.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557029,570292,0,541375,TO_TIMESTAMP('2018-11-07 15:43:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2018-11-07 15:43:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557031,570293,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,'Binärer Wert',0,'D','Das Feld "Binärwert" speichert binäre Werte.','Y','Y','N','N','N','N','N','Binärwert',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557034,570294,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.362
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570294 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557036,570295,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','Y','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557037,570296,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Content type',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557040,570297,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL',2000,'D','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','Y','N','N','N','N','N','File Name',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:01.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557041,570298,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,'Anlage zum Eintrag',10,'D','Eine "Anlage" kann jeder Dokumenten-/Datei-Typ sein und jedem Eintrag im System zugeordnet werden.','Y','Y','N','N','N','N','N','Anlage',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:01.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:02.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557319,570299,0,541375,TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','','Y','Y','N','N','N','N','N','Art',TO_TIMESTAMP('2018-11-07 15:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:02.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:02.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557320,570300,0,541375,TO_TIMESTAMP('2018-11-07 15:43:02','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',2000,'D','The URL defines an fully qualified web address like http://www.adempiere.org','Y','Y','N','N','N','N','N','URL',TO_TIMESTAMP('2018-11-07 15:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:02.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:02.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563285,570301,0,541375,TO_TIMESTAMP('2018-11-07 15:43:02','YYYY-MM-DD HH24:MI:SS'),100,1569325055,'D','Y','Y','N','N','N','N','N','Tags',TO_TIMESTAMP('2018-11-07 15:43:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:43:02.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:43:09.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2018-11-07 15:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541375
;

-- 2018-11-07T15:43:46.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541375,540938,TO_TIMESTAMP('2018-11-07 15:43:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2018-11-07 15:43:46','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-11-07T15:43:46.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540938 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-11-07T15:46:19.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541221,540938,TO_TIMESTAMP('2018-11-07 15:46:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-11-07 15:46:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:46:36.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541221,541930,TO_TIMESTAMP('2018-11-07 15:46:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2018-11-07 15:46:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:48:07.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557033,570302,0,541375,0,TO_TIMESTAMP('2018-11-07 15:48:07','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',10,10,0,1,1,TO_TIMESTAMP('2018-11-07 15:48:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:48:07.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:48:14.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557032,570303,0,541375,0,TO_TIMESTAMP('2018-11-07 15:48:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',20,20,0,1,1,TO_TIMESTAMP('2018-11-07 15:48:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:48:14.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:48:29.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557038,570304,0,541375,0,TO_TIMESTAMP('2018-11-07 15:48:29','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',0,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert',30,30,0,1,1,TO_TIMESTAMP('2018-11-07 15:48:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:48:29.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:48:37.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557039,570305,0,541375,0,TO_TIMESTAMP('2018-11-07 15:48:37','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',0,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','Y','Y','N','N','N','N','N','Aktualisiert durch',40,40,0,1,1,TO_TIMESTAMP('2018-11-07 15:48:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:48:37.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=570305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-07T15:48:56.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570303,0,541375,541930,554225,'F',TO_TIMESTAMP('2018-11-07 15:48:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Created',10,0,0,TO_TIMESTAMP('2018-11-07 15:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:49:13.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570302,0,541375,541930,554226,'F',TO_TIMESTAMP('2018-11-07 15:49:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CreatedBy',20,0,0,TO_TIMESTAMP('2018-11-07 15:49:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:49:31.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570297,0,541375,541930,554227,'F',TO_TIMESTAMP('2018-11-07 15:49:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'FileName',30,0,0,TO_TIMESTAMP('2018-11-07 15:49:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:49:51.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570295,0,541375,541930,554228,'F',TO_TIMESTAMP('2018-11-07 15:49:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',40,0,0,TO_TIMESTAMP('2018-11-07 15:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:51:27.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,570290,0,541375,541930,554229,'F',TO_TIMESTAMP('2018-11-07 15:51:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_AttachmentEntry_ID',5,0,0,TO_TIMESTAMP('2018-11-07 15:51:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:51:35.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570302
;

-- 2018-11-07T15:51:35.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570303
;

-- 2018-11-07T15:51:36.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570304
;

-- 2018-11-07T15:51:36.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570305
;

-- 2018-11-07T15:51:41.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570294
;

-- 2018-11-07T15:51:43.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570296
;

-- 2018-11-07T15:51:44.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570297
;

-- 2018-11-07T15:51:44.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570299
;

-- 2018-11-07T15:51:45.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570300
;

-- 2018-11-07T15:51:45.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570301
;

-- 2018-11-07T15:51:46.125
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570298
;

-- 2018-11-07T15:51:46.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570291
;

-- 2018-11-07T15:51:46.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570292
;

-- 2018-11-07T15:51:47.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570293
;

-- 2018-11-07T15:51:49.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-11-07 15:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=570290
;

-- 2018-11-07T15:52:14.649
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540532,Updated=TO_TIMESTAMP('2018-11-07 15:52:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540833
;

-- 2018-11-07T15:52:32.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-11-07 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554229
;

-- 2018-11-07T15:52:32.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-11-07 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554225
;

-- 2018-11-07T15:52:32.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-11-07 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554226
;

-- 2018-11-07T15:52:32.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-11-07 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554227
;

-- 2018-11-07T15:52:32.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-11-07 15:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554228
;

-- 2018-11-07T15:54:24.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,541162,0,540532,TO_TIMESTAMP('2018-11-07 15:54:23','YYYY-MM-DD HH24:MI:SS'),100,'D','AD_AttachmentEntry','Y','N','N','N','N','Anhang',TO_TIMESTAMP('2018-11-07 15:54:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-07T15:54:24.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541162 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-11-07T15:54:24.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541162, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541162)
;

-- 2018-11-07T15:54:24.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:24.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.553
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.575
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2018-11-07T15:54:27.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2018-11-07T15:57:53.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='N',Updated=TO_TIMESTAMP('2018-11-07 15:57:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557040
;

-- 2018-11-07T15:58:00.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2018-11-07 15:58:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557027
;

-- 2018-11-07T16:11:48.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2018-11-07 16:11:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540833
;

