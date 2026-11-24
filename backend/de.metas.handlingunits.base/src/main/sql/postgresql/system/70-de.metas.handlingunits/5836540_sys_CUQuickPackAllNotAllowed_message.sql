-- CU quantity entry: Quick Pack All (pick all) is refused when a line of the picking job has a label-based CU quantity entry mode.
-- New: AD_Message de.metas.handlingunits.picking.job.CU_QUICK_PACK_ALL_NOT_ALLOWED (+ AD_Message_Trl de_DE / en_US / de_CH)

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546020 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-19 01:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Schnelldruck ist nicht erlaubt, wenn eine Zeile des Auftrags mit ''Jede CU einzeln scannen'' oder ''Einmal scannen, Menge eingeben'' kommissioniert wird. Es wurde nichts gebucht.','E',TO_TIMESTAMP('2026-11-19 01:10:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_QUICK_PACK_ALL_NOT_ALLOWED')
;
UPDATE AD_Message SET ErrorCode='CU_QUICK_PACK_ALL_NOT_ALLOWED', Updated=TO_TIMESTAMP('2026-11-19 01:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546020
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546020 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='Quick Pack is not allowed when a line of the job is picked with ''Scan each CU'' or ''Scan once, enter quantity''. Nothing was booked.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-19 01:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546020
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-19 01:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546020
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-19 01:10:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546020
;
