
-- 2017-07-19T16:27:00.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544448,0,TO_TIMESTAMP('2017-07-19 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Es muss eine HU-Zeile ausgewählt sein.','I',TO_TIMESTAMP('2017-07-19 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_AddQtyToHU_SelectHU')
;

-- 2017-07-19T16:27:00.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544448 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-19T16:27:25.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 16:27:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='A HU item shall be selected' WHERE AD_Message_ID=544448 AND AD_Language='en_US'
;

-- 2017-07-19T16:34:59.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=544445
;

-- 2017-07-19T16:34:59.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Message WHERE AD_Message_ID=544445
;

-- 2017-07-19T16:43:01.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='WEBUI_Picking_SelectHU',Updated=TO_TIMESTAMP('2017-07-19 16:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544448
;

-- 2017-07-19T16:49:05.835
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_PickToNewHU', EntityType='de.metas.picking', Name='In neue HU kommissionieren', Value='WEBUI_Picking_PickToNewHU',Updated=TO_TIMESTAMP('2017-07-19 16:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540807
;

-- 2017-07-19T16:49:22.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 16:49:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pick to new HU' WHERE AD_Process_ID=540807 AND AD_Language='en_US'
;

-- 2017-07-19T17:17:25.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.ui.web.picking.process.WEBUI_Picking_PickToExistingHU', Value='WEBUI_Picking_PickToExistingHU',Updated=TO_TIMESTAMP('2017-07-19 17:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540808
;

-- 2017-07-19T17:17:39.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='In bestehende HU kommissionieren',Updated=TO_TIMESTAMP('2017-07-19 17:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540808
;

-- 2017-07-19T17:17:48.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 17:17:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540808 AND AD_Language='en_US'
;

-- 2017-07-19T17:18:05.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 17:18:05','YYYY-MM-DD HH24:MI:SS'),Name='Pick to existing HU' WHERE AD_Process_ID=540808 AND AD_Language='en_US'
;
