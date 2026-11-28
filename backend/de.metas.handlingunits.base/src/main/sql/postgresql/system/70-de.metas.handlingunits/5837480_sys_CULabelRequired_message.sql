-- CU quantity entry: a pick in a label mode (Scan once / Scan each CU) that arrives without a label (e.g. a cached app version after the picking profile was switched to such a mode) is refused with a German message, not an English technical text; the technical reason goes to the server log only.
-- New: AD_Message de.metas.handlingunits.picking.job.CU_LABEL_REQUIRED (ErrorCode CU_LABEL_REQUIRED), de_DE / de_CH German, en_US English.

-- AD_Message CU_LABEL_REQUIRED
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546037 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-27 01:30:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für diese Kommissionierung ist ein Etikett erforderlich. Bitte das Etikett scannen. Es wurde nichts gebucht.','E',TO_TIMESTAMP('2026-11-27 01:30:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_LABEL_REQUIRED')
;
UPDATE AD_Message SET ErrorCode='CU_LABEL_REQUIRED', Updated=TO_TIMESTAMP('2026-11-27 01:30:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546037
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546037 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='A label is required for this pick. Please scan the label. Nothing was booked.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-27 01:30:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546037
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-27 01:30:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546037
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-27 01:30:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546037
;
