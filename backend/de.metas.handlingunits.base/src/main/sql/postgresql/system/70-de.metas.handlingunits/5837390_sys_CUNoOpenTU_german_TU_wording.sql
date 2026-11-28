-- CU quantity entry: the German wording says "TU", not "Kiste" (the button is "Weiteres TU").
-- Changed: AD_Message de.metas.handlingunits.picking.job.CU_NO_OPEN_TU, German text (AD_Message + AD_Message_Trl de_DE / de_CH). English text unchanged.
-- A new script instead of editing 5836530_sys_PackId_attribute_and_messages.sql, which can be applied already.

UPDATE AD_Message SET MsgText='Bitte öffnen Sie zuerst ein TU (GRAI scannen oder ''Weiteres TU'').', Updated=TO_TIMESTAMP('2026-11-26 16:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE Value='de.metas.handlingunits.picking.job.CU_NO_OPEN_TU'
;
UPDATE AD_Message_Trl SET MsgText='Bitte öffnen Sie zuerst ein TU (GRAI scannen oder ''Weiteres TU'').', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-26 16:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID IN (SELECT AD_Message_ID FROM AD_Message WHERE Value='de.metas.handlingunits.picking.job.CU_NO_OPEN_TU')
;
