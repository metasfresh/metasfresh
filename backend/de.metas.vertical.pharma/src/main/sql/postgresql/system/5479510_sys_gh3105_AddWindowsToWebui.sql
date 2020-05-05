

-- 2017-12-05T19:09:20.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540903,540557,TO_TIMESTAMP('2017-12-05 19:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,TO_TIMESTAMP('2017-12-05 19:09:19','YYYY-MM-DD HH24:MI:SS'),100,'primary')
;

-- 2017-12-05T19:09:20.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540557 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-12-05T19:09:31.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540744,540557,TO_TIMESTAMP('2017-12-05 19:09:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-12-05 19:09:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:09:44.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540744,541295,TO_TIMESTAMP('2017-12-05 19:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','Name',10,TO_TIMESTAMP('2017-12-05 19:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:09:47.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-12-05 19:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541295
;

-- 2017-12-05T19:09:58.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560684,0,540903,549604,541295,'F',TO_TIMESTAMP('2017-12-05 19:09:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-12-05 19:09:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:10:28.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560682,0,540903,549605,541295,'F',TO_TIMESTAMP('2017-12-05 19:10:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2017-12-05 19:10:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:10:45.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560679,0,540903,549606,541295,'F',TO_TIMESTAMP('2017-12-05 19:10:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',30,0,0,TO_TIMESTAMP('2017-12-05 19:10:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:10:54.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560680,0,540903,549607,541295,'F',TO_TIMESTAMP('2017-12-05 19:10:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',40,0,0,TO_TIMESTAMP('2017-12-05 19:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:11:03.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-12-05 19:11:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549606
;

-- 2017-12-05T19:11:05.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-12-05 19:11:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549607
;

-- 2017-12-05T19:11:10.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-12-05 19:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549605
;

-- 2017-12-05T19:11:17.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-12-05 19:11:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549604
;

-- 2017-12-05T19:11:40.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Dosage Form',Updated=TO_TIMESTAMP('2017-12-05 19:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540992
;




-----------





-- 2017-12-05T19:14:16.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540904,540558,TO_TIMESTAMP('2017-12-05 19:14:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','Primary',10,TO_TIMESTAMP('2017-12-05 19:14:16','YYYY-MM-DD HH24:MI:SS'),100,'Primary')
;

-- 2017-12-05T19:14:16.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540558 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;


-- 2017-12-05T19:14:38.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540745,540558,TO_TIMESTAMP('2017-12-05 19:14:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-12-05 19:14:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:14:53.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540745,541296,TO_TIMESTAMP('2017-12-05 19:14:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Primary',10,'primary',TO_TIMESTAMP('2017-12-05 19:14:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:15:08.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560685,0,540904,549608,541296,'F',TO_TIMESTAMP('2017-12-05 19:15:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2017-12-05 19:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:15:16.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560686,0,540904,549609,541296,'F',TO_TIMESTAMP('2017-12-05 19:15:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2017-12-05 19:15:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:15:26.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560690,0,540904,549610,541296,'F',TO_TIMESTAMP('2017-12-05 19:15:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',30,0,0,TO_TIMESTAMP('2017-12-05 19:15:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:15:40.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,560687,0,540904,549611,541296,'F',TO_TIMESTAMP('2017-12-05 19:15:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',40,0,0,TO_TIMESTAMP('2017-12-05 19:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-05T19:16:09.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Indication',Updated=TO_TIMESTAMP('2017-12-05 19:16:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540993
;



-- 2017-12-05T19:21:18.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsCreateNew='Y', WEBUI_NameNew='New Indication', WEBUI_NameNewBreadcrumb='Indication',Updated=TO_TIMESTAMP('2017-12-05 19:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540993
;

-- 2017-12-05T19:21:41.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsCreateNew='Y', WEBUI_NameNew='New Dosage Form', WEBUI_NameNewBreadcrumb='Dosage Form',Updated=TO_TIMESTAMP('2017-12-05 19:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540992
;

-- 2017-12-05T19:23:01.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-05 19:23:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',WEBUI_NameBrowse='Dosage Form',WEBUI_NameNew='New Dosage Form' WHERE AD_Menu_ID=540992 AND AD_Language='en_US'
;

-- 2017-12-05T19:23:22.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-05 19:23:22','YYYY-MM-DD HH24:MI:SS'),WEBUI_NameBrowse='Indication',WEBUI_NameNew='New Indication' WHERE AD_Menu_ID=540993 AND AD_Language='en_US'
;



