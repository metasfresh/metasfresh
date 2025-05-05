-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.settings.interceptor.ERROR_ADDED_SUBTRACTED_VALUE_ON_INTERIM
-- 2024-05-21T06:41:23.802Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545410,0,TO_TIMESTAMP('2024-05-21 09:41:22.655','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','AddValueOnInterim/SubtractValueOnInterim kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!','E',TO_TIMESTAMP('2024-05-21 09:41:22.655','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.ERROR_ADDED_SUBTRACTED_VALUE_ON_INTERIM')
;

-- 2024-05-21T06:41:23.827Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545410 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.ERROR_ADDED_SUBTRACTED_VALUE_ON_INTERIM
-- 2024-05-21T06:42:09.839Z
UPDATE AD_Message_Trl SET MsgText='AddValueOnInterim/SubtractValueOnInterim cannot be set for an invoicing group other than {}!',Updated=TO_TIMESTAMP('2024-05-21 09:42:09.839','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545410
;

