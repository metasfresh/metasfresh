-- 11.08.2016 08:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543939,0,TO_TIMESTAMP('2016-08-11 08:54:51','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.fresh','Y','de.metas.fresh.picking.form.swing.PickingHUEditorPanel.ConsiderAttributesToggle','I',TO_TIMESTAMP('2016-08-11 08:54:51','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.fresh.picking.form.swing.PickingHUEditorPanel.ConsiderAttributesToggle')
;

-- 11.08.2016 08:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543939 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 11.08.2016 10:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Attribute berücksichtigen',Updated=TO_TIMESTAMP('2016-08-11 10:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543939
;

-- 11.08.2016 10:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543939
;

