-- Value: webui.window.filters.noActiveFilter.caption
-- 2022-11-02T17:54:17.519Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,545191,0,TO_TIMESTAMP('2022-11-02 18:54:17.311','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Filter','Filter','I',TO_TIMESTAMP('2022-11-02 18:54:17.311','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.window.filters.noActiveFilter.caption')
;

-- 2022-11-02T17:54:17.521Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545191 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.window.filters.noActiveFilter.caption
-- 2022-11-02T17:54:34.108Z
UPDATE AD_Message_Trl SET MsgText='Filtre', MsgTip='Filtre',Updated=TO_TIMESTAMP('2022-11-02 18:54:34.107','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545191
;

