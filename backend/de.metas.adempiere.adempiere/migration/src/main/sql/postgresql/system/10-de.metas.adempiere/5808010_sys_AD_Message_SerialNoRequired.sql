-- Serial No Picking
-- AD_Message shown when a serial number is required (product IsSerialNoPicked + HU supports SerialNo) but missing at pick time.

INSERT INTO AD_Message (AD_Message_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,MsgText,MsgType,ErrorCode,EntityType)
VALUES (545758 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-06-16 02:50:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-16 02:50:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.SERIAL_NO_REQUIRED','Bitte Seriennummer scannen.','E','SERIAL_NO_REQUIRED','D')
;

-- seed _Trl rows for every active system language (copies the German base text)
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545758
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- en_US override
UPDATE AD_Message_Trl SET MsgText='Please scan the serial number.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-16 02:50:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545758
;

-- de_DE / de_CH carry the German base text; mark as actively translated
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-16 02:50:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545758
;
