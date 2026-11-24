-- MobileUI HU consolidation: message when the pallet was closed (or the job completed) and shipped, but its label could not be printed.
-- Creates AD_Message de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED; {0} = why the label was not printed.

-- Value: de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED
-- 2026-11-24 13:05
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546023 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-24 13:05','YYYY-MM-DD HH24:MI'),100,'D','Y','Abgeschlossen und versendet. Nur das Etikett der Palette wurde nicht gedruckt: {0}','E',TO_TIMESTAMP('2026-11-24 13:05','YYYY-MM-DD HH24:MI'),100,'de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=546023
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Done and shipped. Only the pallet label was not printed: {0}',Updated=TO_TIMESTAMP('2026-11-24 13:05','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546023
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-24 13:05','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=546023
;
