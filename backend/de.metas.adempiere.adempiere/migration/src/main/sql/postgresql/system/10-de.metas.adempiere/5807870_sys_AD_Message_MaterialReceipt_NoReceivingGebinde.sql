-- me03 30334: guidance shown on the mobile manufacturing goods-receipt step when no
-- receiving Gebinde (HU/TU/LU target) can be offered for the finished product, instead of
-- an empty target area. {0} = product name. Info message (not thrown) — the backend puts
-- the localized text into the receive line's emptyReason.

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545757/*From ID Server*/,0,TO_TIMESTAMP('2026-06-15 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
        'Für {0} ist keine passende Verpackungsvorschrift hinterlegt – es kann kein Gebinde zum Vereinnahmen angeboten werden. Bitte eine passende Verpackungsvorschrift für dieses Produkt konfigurieren.',
        'I',TO_TIMESTAMP('2026-06-15 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'MaterialReceipt_NoReceivingGebinde');

-- 2. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545757
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- 3. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='No suitable packing instruction is configured for {0} – no receiving HU can be offered. Please configure a packing instruction for this product.',
       IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-15 21:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545757;

-- 4. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-15 21:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545757;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-15 21:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545757;
