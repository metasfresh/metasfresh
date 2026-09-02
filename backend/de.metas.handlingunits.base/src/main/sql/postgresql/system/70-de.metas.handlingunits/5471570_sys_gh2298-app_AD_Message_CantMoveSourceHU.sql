-- 2017-09-13T13:38:12.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544493,0,TO_TIMESTAMP('2017-09-13 13:38:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Handling Unit {0} ist zur Zeit in der Kommissionierung als Quell-HU ausgewählt und darf daher nicht verschoben werden.','I',TO_TIMESTAMP('2017-09-13 13:38:12','YYYY-MM-DD HH24:MI:SS'),100,'CANNOT_MOVE_SOURCE_HU')
;

-- 2017-09-13T13:38:12.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544493 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

