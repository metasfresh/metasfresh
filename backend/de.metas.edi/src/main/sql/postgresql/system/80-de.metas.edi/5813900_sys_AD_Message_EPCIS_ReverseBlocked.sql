-- AD_Message for the M_InOut reverse/reactivate/void guard (M_InOut.forbidReverseWhenEpcisTransmitted):
-- a user-friendly, localized message shown when the action is blocked because the shipment's EPCIS
-- SSCC events were already transmitted (or are in-flight) to the receiver.

-- 1. the message (base text = German)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545774 /*From ID Server*/,0,TO_TIMESTAMP('2026-07-14 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',
        'Die EPCIS-SSCC-Ereignisse dieser Lieferung wurden bereits an den Empfänger übermittelt (oder werden gerade übermittelt); ein Stornieren, Reaktivieren oder Ungültigmachen würde diese Ereignisse beim Empfänger duplizieren. Deaktivieren Sie zuerst die übermittelte-SSCC-Zeile (oder eine hängengebliebene In-flight-Exportstatus-Zeile) im EPCIS-Reiter der Lieferung, falls dies beabsichtigt ist.',
        'E',TO_TIMESTAMP('2026-07-14 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'EPCIS_ReverseBlocked_SSCCTransmitted');

-- 2. short ErrorCode
UPDATE AD_Message SET ErrorCode='EPCIS_ReverseBlocked', Updated=TO_TIMESTAMP('2026-07-14 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545774;

-- 3. seed AD_Message_Trl for ALL active system languages with the base (DE) text, IsTranslated='N'
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545774
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);

-- 4. en_US override (the real English text) + IsTranslated='Y'
UPDATE AD_Message_Trl SET MsgText='This shipment''s EPCIS SSCC events were already transmitted (or are currently being transmitted) to the receiver; reversing, reactivating or voiding it would duplicate those events at the receiver. Deactivate the transmitted-SSCC ledger row (or a stuck in-flight export-status row) on the shipment''s EPCIS tab first if this is intentional.',
       IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-14 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545774;

-- 5. flip de_DE + de_CH to IsTranslated='Y' (their text already equals the DE base)
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-14 12:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545774;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-14 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545774;
