-- 2017-07-19T16:14:51.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544446,0,TO_TIMESTAMP('2017-07-19 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine Zeilen ausgewählt','I',TO_TIMESTAMP('2017-07-19 16:14:51','YYYY-MM-DD HH24:MI:SS'),100,'ProcessPreconditionsResolution_NoRowsSelected')
;

-- 2017-07-19T16:14:51.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544446 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-19T16:15:06.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 16:15:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='No items selected' WHERE AD_Message_ID=544446 AND AD_Language='en_US'
;

-- 2017-07-19T16:18:47.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544447,0,TO_TIMESTAMP('2017-07-19 16:18:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nur eine Zeile soll ausgewählt sein','I',TO_TIMESTAMP('2017-07-19 16:18:47','YYYY-MM-DD HH24:MI:SS'),100,'ProcessPreconditionsResolution_OnlyOneSelectedRowAllowed')
;

-- 2017-07-19T16:18:47.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544447 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-19T16:18:59.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-19 16:18:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Only one item shall be selected' WHERE AD_Message_ID=544447 AND AD_Language='en_US'
;




--
-- Bonus: remove a not-helpful and very specific description from the "status" AD_Element
--

-- 2017-07-20T07:01:28.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='',Updated=TO_TIMESTAMP('2017-07-20 07:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=3020
;

-- 2017-07-20T07:01:28.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Status', Name='Status', Description='', Help='' WHERE AD_Element_ID=3020 AND IsCentrallyMaintained='Y'
;

-- 2017-07-20T07:01:28.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Status', Description='', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=3020) AND IsCentrallyMaintained='Y'
;



