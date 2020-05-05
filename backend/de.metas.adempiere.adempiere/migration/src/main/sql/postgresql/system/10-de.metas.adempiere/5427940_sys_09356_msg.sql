-- 23.09.2015 13:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543626,0,TO_TIMESTAMP('2015-09-23 13:44:43','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','ToggleGridColumns','I',TO_TIMESTAMP('2015-09-23 13:44:43','YYYY-MM-DD HH24:MI:SS'),0,'ToggleGridColumns')
;

-- 23.09.2015 13:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543626 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 23.09.2015 14:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D', MsgText='Spaltenanzeige ändern', MsgType='M',Updated=TO_TIMESTAMP('2015-09-23 14:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543626
;

-- 23.09.2015 14:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543626
;

