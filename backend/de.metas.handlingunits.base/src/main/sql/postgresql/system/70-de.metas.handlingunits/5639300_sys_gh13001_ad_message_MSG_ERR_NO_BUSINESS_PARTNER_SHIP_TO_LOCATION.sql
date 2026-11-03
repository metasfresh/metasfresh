-- 2022-05-17T17:24:00.729Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545124,0,TO_TIMESTAMP('2022-05-17 20:24:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','No shipping address found for the customer: {0}_{1}.','E',TO_TIMESTAMP('2022-05-17 20:24:00','YYYY-MM-DD HH24:MI:SS'),100,'MSG_ERR_NO_BUSINESS_PARTNER_SHIP_TO_LOCATION')
;

-- 2022-05-17T17:24:00.744Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545124 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-05-17T17:24:50.328Z
UPDATE AD_Message_Trl SET MsgText='Es wurde keine Lieferadresse für den Kunden gefunden: {0}_{1}.',Updated=TO_TIMESTAMP('2022-05-17 20:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545124
;

-- 2022-05-17T17:24:53.388Z
UPDATE AD_Message_Trl SET MsgText='Es wurde keine Lieferadresse für den Kunden gefunden: {0}_{1}.',Updated=TO_TIMESTAMP('2022-05-17 20:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545124
;

-- 2022-05-17T17:24:57.876Z
UPDATE AD_Message_Trl SET MsgText='Es wurde keine Lieferadresse für den Kunden gefunden: {0}_{1}.',Updated=TO_TIMESTAMP('2022-05-17 20:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545124
;

-- 2022-05-17T17:25:02.494Z
UPDATE AD_Message SET MsgText='Es wurde keine Lieferadresse für den Kunden gefunden: {0}_{1}.',Updated=TO_TIMESTAMP('2022-05-17 20:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545124
;

