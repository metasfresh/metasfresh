-- 2019-05-14T06:17:04.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='DataEntry_Tab',Updated=TO_TIMESTAMP('2019-05-14 06:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540571
;

-- 2019-05-14T06:17:48.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='DataEntry_SubTab',Updated=TO_TIMESTAMP('2019-05-14 06:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541520
;

-- 2019-05-14T06:17:56.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET InternalName='DataEntry_Tab',Updated=TO_TIMESTAMP('2019-05-14 06:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541519
;

-- 2019-05-14T06:47:12.889
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_Tab WHERE DataEntry_TargetWindow_ID=@DataEntry_TargetWindow_ID/0@',Updated=TO_TIMESTAMP('2019-05-14 06:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564209
;

-- 2019-05-14T06:53:03.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_SubTab WHERE DataEntry_Group_ID=@DataEntry_Group_ID/0@',Updated=TO_TIMESTAMP('2019-05-14 06:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564210
;

-- 2019-05-14T06:58:13.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_SubTab WHERE DataEntry_Tab_ID=@DataEntry_Group_ID/0@',Updated=TO_TIMESTAMP('2019-05-14 06:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564210
;

-- 2019-05-14T06:58:31.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_SubTab WHERE DataEntry_Tab_ID=@DataEntry_Tab_ID/0@',Updated=TO_TIMESTAMP('2019-05-14 06:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564210
;

