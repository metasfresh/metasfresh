-- 2022-08-21T08:35:52.446Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545150,0,TO_TIMESTAMP('2022-08-21 11:35:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','The {0} role is not assigned to user: {1}.','E',TO_TIMESTAMP('2022-08-21 11:35:52','YYYY-MM-DD HH24:MI:SS'),100,'External_Systems_Authorization_Role_Not_Found_For_User')
;

-- 2022-08-21T08:35:52.459Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545150 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-08-21T08:36:47.853Z
UPDATE AD_Message SET MsgText='Die Rolle {0} ist dem Benutzer {1} nicht zugewiesen.',Updated=TO_TIMESTAMP('2022-08-21 11:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545150
;

-- 2022-08-21T08:37:11.545Z
UPDATE AD_Message_Trl SET MsgText='Die Rolle {0} ist dem Benutzer {1} nicht zugewiesen.',Updated=TO_TIMESTAMP('2022-08-21 11:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545150
;

-- 2022-08-21T08:37:15.338Z
UPDATE AD_Message_Trl SET MsgText='Die Rolle {0} ist dem Benutzer {1} nicht zugewiesen.',Updated=TO_TIMESTAMP('2022-08-21 11:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545150
;

-- 2022-08-21T08:37:22.473Z
UPDATE AD_Message_Trl SET MsgText='Die Rolle {0} ist dem Benutzer {1} nicht zugewiesen.',Updated=TO_TIMESTAMP('2022-08-21 11:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545150
;

-- 2022-08-21T08:37:27.079Z
UPDATE AD_Message_Trl SET MsgText='Die Rolle {0} ist dem Benutzer {1} nicht zugewiesen.',Updated=TO_TIMESTAMP('2022-08-21 11:37:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545150
;

-- 2022-08-21T08:37:33.122Z
UPDATE AD_Message_Trl SET MsgText='The {0} role is not assigned to user: {1}.',Updated=TO_TIMESTAMP('2022-08-21 11:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545150
;

