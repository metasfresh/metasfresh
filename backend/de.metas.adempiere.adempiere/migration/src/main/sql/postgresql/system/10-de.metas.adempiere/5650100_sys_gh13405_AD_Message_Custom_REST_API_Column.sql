-- 2022-08-09T08:17:25.876Z
INSERT INTO t_alter_column values('ad_column','IsRestAPICustomColumn','CHAR(1)',null,'N')
;

-- 2022-08-09T08:17:26.042Z
UPDATE AD_Column SET IsRestAPICustomColumn='N' WHERE IsRestAPICustomColumn IS NULL
;

-- 2022-08-09T05:33:35.090Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545145,0,TO_TIMESTAMP('2022-08-09 08:33:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','{0} is not a custom REST API column','E',TO_TIMESTAMP('2022-08-09 08:33:34','YYYY-MM-DD HH24:MI:SS'),100,'CUSTOM_REST_API_COLUMN')
;

-- 2022-08-09T05:33:35.098Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545145 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-08-09T05:34:18.390Z
UPDATE AD_Message_Trl SET MsgText='{0} ist keine benutzerdefinierte REST API-Spalte',Updated=TO_TIMESTAMP('2022-08-09 08:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545145
;

-- 2022-08-09T05:34:23.865Z
UPDATE AD_Message_Trl SET MsgText='{0} ist keine benutzerdefinierte REST API-Spalte',Updated=TO_TIMESTAMP('2022-08-09 08:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545145
;

-- 2022-08-09T05:34:30.112Z
UPDATE AD_Message_Trl SET MsgText='{0} ist keine benutzerdefinierte REST API-Spalte',Updated=TO_TIMESTAMP('2022-08-09 08:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545145
;

-- 2022-08-09T05:34:43.654Z
UPDATE AD_Message SET MsgText='{0} ist keine benutzerdefinierte REST API-Spalte',Updated=TO_TIMESTAMP('2022-08-09 08:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545145
;



