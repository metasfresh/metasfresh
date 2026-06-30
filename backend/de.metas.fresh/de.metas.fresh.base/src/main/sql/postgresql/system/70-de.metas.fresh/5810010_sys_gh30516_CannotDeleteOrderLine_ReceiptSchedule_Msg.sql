-- Run mode: SWING_CLIENT

-- Value: CannotDeleteOrderLine_ReceiptSchedule
-- 2026-06-29T17:12:20.319Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545771,0,TO_TIMESTAMP('2026-06-29 17:12:20.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Auftragsposition kann nicht gelöscht werden: Für den verknüpften Belegplan existiert bereits ein Beleg.','E',TO_TIMESTAMP('2026-06-29 17:12:20.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CannotDeleteOrderLine_ReceiptSchedule')
;

-- 2026-06-29T17:12:20.325Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545771 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotDeleteOrderLine_ReceiptSchedule
-- 2026-06-29T17:13:19.290Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Cannot delete order line: a receipt already exists for the linked receipt schedule. Please reverse the associated receipt before deleting this order line.',Updated=TO_TIMESTAMP('2026-06-29 17:13:19.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545771
;

-- 2026-06-29T17:13:19.291Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: CannotDeleteOrderLine_ReceiptSchedule (DE recovery hint)
UPDATE AD_Message SET MsgText='Auftragsposition kann nicht gelöscht werden: Für den verknüpften Belegplan existiert bereits ein Beleg. Bitte stornieren Sie den zugehörigen Beleg, bevor Sie diese Auftragsposition löschen.' WHERE AD_Message_ID=545771 AND getBaseLanguage()='de_DE'
;
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Auftragsposition kann nicht gelöscht werden: Für den verknüpften Belegplan existiert bereits ein Beleg. Bitte stornieren Sie den zugehörigen Beleg, bevor Sie diese Auftragsposition löschen.' WHERE AD_Language='de_DE' AND AD_Message_ID=545771
;

