-- 2020-05-18T08:42:50.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540501,'AD_Tab.AD_Window_ID=@AD_Window_ID@',TO_TIMESTAMP('2020-05-18 11:42:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','AD_Tab in Window for Labels','S',TO_TIMESTAMP('2020-05-18 11:42:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-05-18T08:45:17.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_Tab.AD_Window_ID=@#AD_Window_ID@ ',Updated=TO_TIMESTAMP('2020-05-18 11:45:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540501
;

-- 2020-05-18T08:45:36.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540501,Updated=TO_TIMESTAMP('2020-05-18 11:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557050
;

-- 2020-05-18T08:46:12.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_Tab.AD_Window_ID=@AD_Window_ID@ ',Updated=TO_TIMESTAMP('2020-05-18 11:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540501
;

-- 2020-05-18T08:54:37.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_Tab.AD_Window_ID =  (select t.ad_window_id from AD_Tab t where t.ad_tab_id = @AD_Tab_ID@)',Updated=TO_TIMESTAMP('2020-05-18 11:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540501
;



-- 2020-05-18T09:04:10.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_UI_ElementType@=''L''',Updated=TO_TIMESTAMP('2020-05-18 12:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557050
;

-- 2020-05-18T09:04:18.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@AD_UI_ElementType@=''L''',Updated=TO_TIMESTAMP('2020-05-18 12:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557051
;


-- 2020-05-18T09:18:22.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@AD_UI_ElementType@=''L''', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2020-05-18 12:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557050
;

