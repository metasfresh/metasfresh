delete from ad_message where value in ('webui.window.notFound.title', 'webui.window.notFound.description');

-- Value: webui.window.notFound.title
-- 2023-11-27T09:15:42.918Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545361,0,TO_TIMESTAMP('2023-11-27 11:15:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','404','I',TO_TIMESTAMP('2023-11-27 11:15:42','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.notFound.title')
;

-- 2023-11-27T09:15:42.933Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545361 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.window.notFound.description
-- 2023-11-27T09:18:12.078Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545362,0,TO_TIMESTAMP('2023-11-27 11:18:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','%(what)s not found.','I',TO_TIMESTAMP('2023-11-27 11:18:11','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.notFound.description')
;

-- 2023-11-27T09:18:12.079Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545362 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.window.notFound.description
-- 2023-11-27T09:18:20.973Z
UPDATE AD_Message SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2023-11-27 11:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545362
;

-- Value: webui.window.notFound.title
-- 2023-11-27T09:18:26.286Z
UPDATE AD_Message SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2023-11-27 11:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545361
;

