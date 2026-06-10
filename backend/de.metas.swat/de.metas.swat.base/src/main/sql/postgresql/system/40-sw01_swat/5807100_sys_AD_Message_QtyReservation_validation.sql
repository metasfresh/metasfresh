-- Qty reservation — translatable user-validation messages for MakeQtyReservationCommand.
-- Base text is German (fallback language); en_US carries the English override.
-- Used via AdMessageKey from de.metas.inoutcandidate.qty_reservation.MakeQtyReservationCommand.

-- =====================================================================================
-- Message 1: order line already fully reserved (remaining ordered qty <= 0)
-- =====================================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545749 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-10 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reservierung nicht möglich: Die Auftragsposition ist bereits vollständig reserviert.','E',TO_TIMESTAMP('2026-06-10 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ERR_QTY_RESERVATION_LINE_FULLY_RESERVED')
;

UPDATE AD_Message SET ErrorCode='QTY_RESERVATION_LINE_FULLY_RESERVED', Updated=TO_TIMESTAMP('2026-06-10 12:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545749
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545749 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Cannot reserve: the sales order line is already fully reserved.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-10 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545749
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-10 12:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545749
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-10 12:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545749
;

-- =====================================================================================
-- Message 2: order line has no packing-item capacity (cannot derive CU per TU)
-- =====================================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545750 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-10 12:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reservierung in Transporteinheiten nicht möglich: Für die Auftragsposition ist keine Gebinde-Kapazität hinterlegt.','E',TO_TIMESTAMP('2026-06-10 12:00:05','YYYY-MM-DD HH24:MI:SS'),100,'ERR_QTY_RESERVATION_NO_PACKING_CAPACITY')
;

UPDATE AD_Message SET ErrorCode='QTY_RESERVATION_NO_PACKING_CAPACITY', Updated=TO_TIMESTAMP('2026-06-10 12:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545750
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545750 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Cannot reserve in TUs: the order line has no packing-item capacity.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-10 12:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545750
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-10 12:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545750
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-10 12:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545750
;
