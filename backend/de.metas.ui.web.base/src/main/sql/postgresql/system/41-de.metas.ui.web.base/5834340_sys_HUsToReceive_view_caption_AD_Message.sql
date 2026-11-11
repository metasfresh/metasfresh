-- 11.11.2026 17:30
-- Caption (modal title) of the receive-HUs view (window "husToReceive"): translatable instead of the constant "HU Editor"
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546013,0,TO_TIMESTAMP('2026-11-11 17:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','HUs vereinnahmen','I',TO_TIMESTAMP('2026-11-11 17:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.handlingunits.HUsToReceive.ViewCaption')
;

-- 11.11.2026 17:30
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=546013 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 11.11.2026 17:31
UPDATE AD_Message_Trl SET MsgText='Receive HUs', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 17:31:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546013 AND AD_Language='en_US'
;

-- 11.11.2026 17:31
UPDATE AD_Message_Trl SET MsgText='HUs vereinnahmen', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-11 17:31:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546013 AND AD_Language='de_CH'
;
