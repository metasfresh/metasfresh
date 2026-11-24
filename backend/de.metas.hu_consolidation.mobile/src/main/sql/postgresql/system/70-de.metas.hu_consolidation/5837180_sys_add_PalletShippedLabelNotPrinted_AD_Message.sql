-- MobileUI HU consolidation: message when the pallet was closed (or the job completed) and its shipment requested (created asynchronously), but its label could not be printed;
-- and the message when a pallet type has no item for the scanned crate type.
-- de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED: {0} = the short reason below (the technical error goes to the log / AD_Issue).

-- Value: de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,546023 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-24 13:05:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abgeschlossen, Lieferung wird erstellt. Etikett nicht gedruckt: {0}','E','HUCONS_PALLET_SHIPPED_LABEL_NOT_PRINTED',TO_TIMESTAMP('2026-11-24 13:05:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=546023
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Done, the shipment is being created. Label not printed: {0}',Updated=TO_TIMESTAMP('2026-11-24 13:05:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546023
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-24 13:05:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=546023
;

-- Value: de.metas.hu_consolidation.mobile.job.LABEL_NOT_PRINTED_REASON_PRINT_FAILED
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546025 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-24 13:05:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fehler beim Drucken.','I',TO_TIMESTAMP('2026-11-24 13:05:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.hu_consolidation.mobile.job.LABEL_NOT_PRINTED_REASON_PRINT_FAILED')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=546025
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Error while printing.',Updated=TO_TIMESTAMP('2026-11-24 13:05:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546025
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-24 13:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=546025
;

-- Value: de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE ({0} = pallet type, {1} = crate type)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value)
VALUES (0,546026 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-24 13:05:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Palettentyp {0} nimmt Kistentyp {1} nicht auf.','E','HUCONS_PALLET_TYPE_REFUSES_CRATE',TO_TIMESTAMP('2026-11-24 13:05:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.hu_consolidation.mobile.job.PALLET_TYPE_DOES_NOT_TAKE_CRATE_TYPE')
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=546026
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='Pallet type {0} does not take crate type {1}.',Updated=TO_TIMESTAMP('2026-11-24 13:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=546026
;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-24 13:05:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=546026
;
