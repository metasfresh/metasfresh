-- 2020-12-16T13:17:59.005Z
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545019,0,TO_TIMESTAMP('2020-12-16 10:17:56','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','The product''s haddex check has to be renewed','E',TO_TIMESTAMP('2020-12-16 10:17:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order.producthadexerror')
;
-- 2020-12-16T13:17:59.234Z
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545019 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-12-16T13:21:30.016Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Die Haddex-Prüfung für das Produkt muss erneuert werden.',Updated=TO_TIMESTAMP('2020-12-16 10:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545019
;

-- 2020-12-16T13:21:51.881Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Die Haddex-Prüfung für das Produkt muss erneuert werden.',Updated=TO_TIMESTAMP('2020-12-16 10:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545019
;

-- 2020-12-17T18:56:43.930Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='The product''s haddex check has to be renewed',Updated=TO_TIMESTAMP('2020-12-17 15:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545019
;

-- 2020-12-16T13:22:30.034Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='The product''s haddex check has to be renewed',Updated=TO_TIMESTAMP('2020-12-16 10:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545019
;

-- 2020-12-16T13:27:36.733Z
-- URL zum Konzept
UPDATE AD_Message SET Value='de.metas.order.producthadexerror',Updated=TO_TIMESTAMP('2020-12-16 10:27:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545019
;

-- 2020-12-17T18:56:13.691Z
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Der Haddex-Check für das Produkt muss erneuert werden',Updated=TO_TIMESTAMP('2020-12-17 15:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545019
;

-- 2020-12-17T18:56:37.882Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2020-12-17 15:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545019
;

-- 2020-12-17T18:56:43.930Z
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-17 15:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545019
;


