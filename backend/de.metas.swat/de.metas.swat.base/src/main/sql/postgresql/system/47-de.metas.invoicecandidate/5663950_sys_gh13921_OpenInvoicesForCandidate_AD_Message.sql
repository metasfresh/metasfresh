-- Value: DRAFT_INVOICES_EXISTS_FOR_CANDIDATE
-- 2022-11-11T06:31:41.866Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545209,0,TO_TIMESTAMP('2022-11-11 08:31:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','There are invoices drafted for this candidate. ({0})','I',TO_TIMESTAMP('2022-11-11 08:31:41','YYYY-MM-DD HH24:MI:SS'),100,'DRAFT_INVOICES_EXISTS_FOR_CANDIDATE')
;

-- 2022-11-11T06:31:41.872Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545209 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: DRAFT_INVOICES_EXISTS_FOR_CANDIDATE
-- 2022-11-11T06:31:47.998Z
UPDATE AD_Message_Trl SET MsgText='Für diesen Kandidaten gibt es bereits laufende Rechnungen. ({0})',Updated=TO_TIMESTAMP('2022-11-11 08:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545209
;

-- Value: DRAFT_INVOICES_EXISTS_FOR_CANDIDATE
-- 2022-11-11T06:31:51.362Z
UPDATE AD_Message_Trl SET MsgText='Für diesen Kandidaten gibt es bereits laufende Rechnungen. ({0})',Updated=TO_TIMESTAMP('2022-11-11 08:31:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545209
;

-- Value: DRAFT_INVOICES_EXISTS_FOR_CANDIDATE
-- 2022-11-11T06:31:56.853Z
UPDATE AD_Message_Trl SET MsgText='Für diesen Kandidaten gibt es bereits laufende Rechnungen. ({0})',Updated=TO_TIMESTAMP('2022-11-11 08:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545209
;

-- Value: DRAFT_INVOICES_EXISTS_FOR_CANDIDATE
-- 2022-11-11T06:31:59.574Z
UPDATE AD_Message SET MsgText='Für diesen Kandidaten gibt es bereits laufende Rechnungen. ({0})',Updated=TO_TIMESTAMP('2022-11-11 08:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545209
;

