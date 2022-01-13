-- 2021-06-15T16:12:57.852Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545036,0,TO_TIMESTAMP('2021-06-15 19:12:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Please, set a Manufacturing Activity to the Manufacturing Workflow {}.','E',TO_TIMESTAMP('2021-06-15 19:12:56','YYYY-MM-DD HH24:MI:SS'),100,'AD_Workflow_StartNode_NotSet')
;

-- 2021-06-15T16:12:58.024Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545036 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-06-15T16:13:22.740Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bitte fügen Sie im Produktions-Arbeitsablauf {} eine Produktionsaktivität hinzu.',Updated=TO_TIMESTAMP('2021-06-15 19:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545036
;

-- 2021-06-15T16:13:33.517Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Bitte fügen Sie im Produktions-Arbeitsablauf {} eine Produktionsaktivität hinzu.',Updated=TO_TIMESTAMP('2021-06-15 19:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545036
;

-- 2021-06-15T17:19:52.622Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Please, set a Manufacturing Activity to the Manufacturing Workflow {0}.',Updated=TO_TIMESTAMP('2021-06-15 20:19:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545036
;

-- 2021-06-15T17:19:57.411Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Bitte fügen Sie im Produktions-Arbeitsablauf {0} eine Produktionsaktivität hinzu.',Updated=TO_TIMESTAMP('2021-06-15 20:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545036
;

-- 2021-06-15T17:20:03.446Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Please, set a Manufacturing Activity to the Manufacturing Workflow {0}.',Updated=TO_TIMESTAMP('2021-06-15 20:20:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545036
;

-- 2021-06-15T17:20:05.383Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Please, set a Manufacturing Activity to the Manufacturing Workflow {0}.',Updated=TO_TIMESTAMP('2021-06-15 20:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545036
;

-- 2021-06-15T17:20:09.152Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Bitte fügen Sie im Produktions-Arbeitsablauf {0} eine Produktionsaktivität hinzu.',Updated=TO_TIMESTAMP('2021-06-15 20:20:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545036
;

