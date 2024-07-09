-- Value: webui.userDropdown.changeWorkplace.caption
-- 2024-04-23T06:23:24.106Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545392,0,TO_TIMESTAMP('2024-04-23 09:23:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Arbeitsplatz wechseln','I',TO_TIMESTAMP('2024-04-23 09:23:23','YYYY-MM-DD HH24:MI:SS'),100,'webui.userDropdown.changeWorkplace.caption')
;

-- 2024-04-23T06:23:24.109Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545392 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.userDropdown.changeWorkplace.caption
-- 2024-04-23T06:23:27.007Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-23 09:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545392
;

-- Value: webui.userDropdown.changeWorkplace.caption
-- 2024-04-23T06:23:35.921Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Change Workplace',Updated=TO_TIMESTAMP('2024-04-23 09:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545392
;

-- Value: webui.userDropdown.changeWorkplace.caption
-- 2024-04-23T06:23:38.207Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-04-23 09:23:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545392
;

