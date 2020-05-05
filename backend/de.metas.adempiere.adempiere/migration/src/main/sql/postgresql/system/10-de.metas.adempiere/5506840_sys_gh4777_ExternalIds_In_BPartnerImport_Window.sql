

-- 2018-11-22T12:57:47.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563606,570691,0,441,TO_TIMESTAMP('2018-11-22 12:57:47','YYYY-MM-DD HH24:MI:SS'),100,225,'D','Y','N','N','N','N','N','N','N','AD_User_ExternalId',TO_TIMESTAMP('2018-11-22 12:57:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T12:57:47.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570691 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-22T12:57:47.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563607,570692,0,441,TO_TIMESTAMP('2018-11-22 12:57:47','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','C_BPartner_ExternalId',TO_TIMESTAMP('2018-11-22 12:57:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T12:57:47.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-22T12:57:47.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563608,570693,0,441,TO_TIMESTAMP('2018-11-22 12:57:47','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','C_BPartner_Location_ExternalId',TO_TIMESTAMP('2018-11-22 12:57:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T12:57:47.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=570693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-11-22T12:58:27.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570693,0,441,554399,541264,'F',TO_TIMESTAMP('2018-11-22 12:58:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_Location_ExternalId',100,0,0,TO_TIMESTAMP('2018-11-22 12:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T12:59:12.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570692,0,441,554400,541263,'F',TO_TIMESTAMP('2018-11-22 12:59:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_ExternalId',15,0,0,TO_TIMESTAMP('2018-11-22 12:59:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T12:59:43.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,570691,0,441,554401,541263,'F',TO_TIMESTAMP('2018-11-22 12:59:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'AD_User_ExternalId',16,0,0,TO_TIMESTAMP('2018-11-22 12:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-22T13:06:33.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Contact ExternalId',Updated=TO_TIMESTAMP('2018-11-22 13:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554401
;

-- 2018-11-22T13:06:47.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Partner ExternalId',Updated=TO_TIMESTAMP('2018-11-22 13:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554400
;

-- 2018-11-22T13:07:02.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Location ExternalId',Updated=TO_TIMESTAMP('2018-11-22 13:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=554399
;

-- 2018-11-22T13:10:58.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:10:58','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Contact ExternalId',PrintName='Contact ExternalId' WHERE AD_Element_ID=575886 AND AD_Language='de_DE'
;

-- 2018-11-22T13:10:58.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575886,'de_DE') 
;

-- 2018-11-22T13:10:58.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575886,'de_DE') 
;

-- 2018-11-22T13:10:58.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_User_ExternalId', Name='Contact ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575886
;

-- 2018-11-22T13:10:58.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_ExternalId', Name='Contact ExternalId', Description=NULL, Help=NULL, AD_Element_ID=575886 WHERE UPPER(ColumnName)='AD_USER_EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-22T13:10:58.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_ExternalId', Name='Contact ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575886 AND IsCentrallyMaintained='Y'
;

-- 2018-11-22T13:10:58.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Contact ExternalId', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575886) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575886)
;

-- 2018-11-22T13:10:58.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Contact ExternalId', Name='Contact ExternalId' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575886)
;

-- 2018-11-22T13:10:58.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Contact ExternalId', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575886
;

-- 2018-11-22T13:10:58.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Contact ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID = 575886
;

-- 2018-11-22T13:10:58.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Contact ExternalId', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575886
;

-- 2018-11-22T13:11:54.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:11:54','YYYY-MM-DD HH24:MI:SS'),Name='Benutzer ExternalId',PrintName='Benutzer ExternalId' WHERE AD_Element_ID=575886 AND AD_Language='de_DE'
;

-- 2018-11-22T13:11:54.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575886,'de_DE') 
;

-- 2018-11-22T13:11:54.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575886,'de_DE') 
;

-- 2018-11-22T13:11:54.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_User_ExternalId', Name='Benutzer ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575886
;

-- 2018-11-22T13:11:54.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_ExternalId', Name='Benutzer ExternalId', Description=NULL, Help=NULL, AD_Element_ID=575886 WHERE UPPER(ColumnName)='AD_USER_EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-22T13:11:54.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_User_ExternalId', Name='Benutzer ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575886 AND IsCentrallyMaintained='Y'
;

-- 2018-11-22T13:11:54.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benutzer ExternalId', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575886) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575886)
;

-- 2018-11-22T13:11:54.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Benutzer ExternalId', Name='Benutzer ExternalId' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575886)
;

-- 2018-11-22T13:11:54.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Benutzer ExternalId', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575886
;

-- 2018-11-22T13:11:54.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Benutzer ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID = 575886
;

-- 2018-11-22T13:11:54.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Benutzer ExternalId', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575886
;

-- 2018-11-22T13:12:06.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:12:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Contact ExternalId',PrintName='Contact ExternalId' WHERE AD_Element_ID=575886 AND AD_Language='en_US'
;

-- 2018-11-22T13:12:06.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575886,'en_US') 
;

-- 2018-11-22T13:12:33.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:12:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Standort ExternalId',PrintName='Standort ExternalId' WHERE AD_Element_ID=575887 AND AD_Language='de_DE'
;

-- 2018-11-22T13:12:33.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575887,'de_DE') 
;

-- 2018-11-22T13:12:33.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575887,'de_DE') 
;

-- 2018-11-22T13:12:33.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_ExternalId', Name='Standort ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575887
;

-- 2018-11-22T13:12:33.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_ExternalId', Name='Standort ExternalId', Description=NULL, Help=NULL, AD_Element_ID=575887 WHERE UPPER(ColumnName)='C_BPARTNER_EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-22T13:12:33.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_ExternalId', Name='Standort ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575887 AND IsCentrallyMaintained='Y'
;

-- 2018-11-22T13:12:33.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Standort ExternalId', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575887) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575887)
;

-- 2018-11-22T13:12:33.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Standort ExternalId', Name='Standort ExternalId' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575887)
;

-- 2018-11-22T13:12:33.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Standort ExternalId', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575887
;

-- 2018-11-22T13:12:33.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Standort ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID = 575887
;

-- 2018-11-22T13:12:33.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Standort ExternalId', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575887
;

-- 2018-11-22T13:12:39.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:12:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575887 AND AD_Language='en_US'
;

-- 2018-11-22T13:12:39.590
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575887,'en_US') 
;

-- 2018-11-22T13:12:50.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:12:50','YYYY-MM-DD HH24:MI:SS'),Name='Geschaftspartner ExternalId',PrintName='Geschaftspartner ExternalId' WHERE AD_Element_ID=575887 AND AD_Language='de_DE'
;

-- 2018-11-22T13:12:50.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575887,'de_DE') 
;

-- 2018-11-22T13:12:50.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575887,'de_DE') 
;

-- 2018-11-22T13:12:50.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_ExternalId', Name='Geschaftspartner ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575887
;

-- 2018-11-22T13:12:50.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_ExternalId', Name='Geschaftspartner ExternalId', Description=NULL, Help=NULL, AD_Element_ID=575887 WHERE UPPER(ColumnName)='C_BPARTNER_EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-22T13:12:50.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_ExternalId', Name='Geschaftspartner ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575887 AND IsCentrallyMaintained='Y'
;

-- 2018-11-22T13:12:50.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschaftspartner ExternalId', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575887) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575887)
;

-- 2018-11-22T13:12:50.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschaftspartner ExternalId', Name='Geschaftspartner ExternalId' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575887)
;

-- 2018-11-22T13:12:50.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschaftspartner ExternalId', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575887
;

-- 2018-11-22T13:12:50.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschaftspartner ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID = 575887
;

-- 2018-11-22T13:12:50.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Geschaftspartner ExternalId', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575887
;

-- 2018-11-22T13:13:04.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:13:04','YYYY-MM-DD HH24:MI:SS'),Name='Partner ExternalId',PrintName='Partner ExternalId' WHERE AD_Element_ID=575887 AND AD_Language='en_US'
;

-- 2018-11-22T13:13:04.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575887,'en_US') 
;

-- 2018-11-22T13:13:32.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:13:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Standort ExternalId',PrintName='Standort ExternalId' WHERE AD_Element_ID=575888 AND AD_Language='de_DE'
;

-- 2018-11-22T13:13:32.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575888,'de_DE') 
;

-- 2018-11-22T13:13:32.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575888,'de_DE') 
;

-- 2018-11-22T13:13:32.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_Location_ExternalId', Name='Standort ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575888
;

-- 2018-11-22T13:13:32.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Location_ExternalId', Name='Standort ExternalId', Description=NULL, Help=NULL, AD_Element_ID=575888 WHERE UPPER(ColumnName)='C_BPARTNER_LOCATION_EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-22T13:13:32.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Location_ExternalId', Name='Standort ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID=575888 AND IsCentrallyMaintained='Y'
;

-- 2018-11-22T13:13:32.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Standort ExternalId', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575888) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575888)
;

-- 2018-11-22T13:13:32.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Standort ExternalId', Name='Standort ExternalId' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575888)
;

-- 2018-11-22T13:13:32.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Standort ExternalId', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575888
;

-- 2018-11-22T13:13:32.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Standort ExternalId', Description=NULL, Help=NULL WHERE AD_Element_ID = 575888
;

-- 2018-11-22T13:13:32.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Standort ExternalId', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575888
;

-- 2018-11-22T13:13:45.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:13:45','YYYY-MM-DD HH24:MI:SS'),Name='Location ExternalId',PrintName='Location ExternalId' WHERE AD_Element_ID=575888 AND AD_Language='en_US'
;

-- 2018-11-22T13:13:45.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575888,'en_US') 
;

-- 2018-11-22T13:13:48.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-22 13:13:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575888 AND AD_Language='en_US'
;

-- 2018-11-22T13:13:48.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575888,'en_US') 
;

