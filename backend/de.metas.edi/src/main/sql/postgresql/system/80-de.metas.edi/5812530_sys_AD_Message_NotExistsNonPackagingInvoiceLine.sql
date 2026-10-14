-- AD_Message for EDIDocumentBL.isValidInvoice guard: an invoice whose lines are all packaging
-- material has no exportable INVOIC line and must fail EDI validation loudly (resolved via the
-- @NotExistsNonPackagingInvoiceLineError@ marker in EDIMissingDependencyException).

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545773 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-07 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
        'Die Rechnung enthält nur Verpackungsmaterial-Positionen und keine exportierbare Position; ein EDI-Versand ist nicht möglich.',
        'E',TO_TIMESTAMP('2026-07-07 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'NotExistsNonPackagingInvoiceLineError');

-- 2. short ErrorCode
UPDATE AD_Message SET ErrorCode='NonPackagingInvoiceLineMissing', Updated=TO_TIMESTAMP('2026-07-07 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545773;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545773
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='The invoice contains only packaging-material lines and no exportable line; an EDI export is not possible.',
       IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-07 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545773;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-07 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545773;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-07 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545773;
