-- Error messages for production receipts whose LU/TU configuration contradicts the packing instruction

-- Quantity per TU differs from the packing instruction's quantity
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545965 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Menge pro TU ({1}) weicht von der Menge der Packvorschrift ({0}) ab. Pro TU muss genau die Menge der Packvorschrift ({0}) angegeben werden.','E',TO_TIMESTAMP('2026-10-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction');
UPDATE AD_Message SET ErrorCode='QTY_PER_TU_NOT_MATCHING_PI', Updated=TO_TIMESTAMP('2026-10-23 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545965;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545965
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The quantity per TU ({1}) differs from the packing instruction''s quantity ({0}). Each TU must hold exactly the packing instruction''s quantity ({0}).',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545965;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545965;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545965;

-- Number of TUs per LU exceeds the LU's capacity
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545966 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-23 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die TU Anzahl ({1}) überschreitet die Kapazität der Palette ({0} TU). Bitte höchstens {0} TU pro Palette angeben; für mehr Mengen die LU Anzahl erhöhen.','E',TO_TIMESTAMP('2026-10-23 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_LUTU_Configuration_QtyTU_ExceedsLUCapacity');
UPDATE AD_Message SET ErrorCode='QTY_TU_EXCEEDS_LU_CAPACITY', Updated=TO_TIMESTAMP('2026-10-23 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545966;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545966
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The number of TUs ({1}) exceeds the pallet''s capacity ({0} TUs). Enter at most {0} TUs per pallet; to receive more, raise the number of LUs.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545966;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545966;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545966;
