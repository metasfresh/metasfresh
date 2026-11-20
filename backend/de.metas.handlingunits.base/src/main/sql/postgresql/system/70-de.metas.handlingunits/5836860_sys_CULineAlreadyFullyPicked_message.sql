-- CU quantity entry, Scan each CU: a scan into a line that is already fully picked is refused with a short message (no technical values; they are logged only).
-- New: AD_Message de.metas.handlingunits.picking.job.CU_LINE_ALREADY_FULLY_PICKED (+ AD_Message_Trl de_DE / en_US / de_CH)

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546021 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-20 21:15:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Zeile ist bereits vollständig kommissioniert. Es wurde nichts gebucht.','E',TO_TIMESTAMP('2026-11-20 21:15:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_LINE_ALREADY_FULLY_PICKED')
;
UPDATE AD_Message SET ErrorCode='CU_LINE_ALREADY_FULLY_PICKED', Updated=TO_TIMESTAMP('2026-11-20 21:15:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546021
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546021 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The line is already fully picked. Nothing was booked.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-20 21:15:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546021
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-20 21:15:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546021
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-20 21:15:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546021
;
