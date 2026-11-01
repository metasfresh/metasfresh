-- 2026-06-15T00:00:00.000Z
-- Add three error messages for carrier advise validation on HU picking completion.
-- These messages are referenced by the picking completion guard logic.
-- {0} = HU document number / identifier

-- IDs allocated from idserver.metas.de on 2026-06-15:
--   AD_MigrationScript 5807750 (filename prefix)
--   AD_Message 545754 (CarrierAdvise_ManualInconsistentOnHU)
--   AD_Message 545755 (CarrierAdvise_NonManualDivergentOnHU)

-- Message 1: Manual advise is inconsistent (mixed manual/automatic or different product/goods-type/service)
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value) VALUES (0,545754 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-15 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','HU {0}: manuelle Lieferweg-Abfrage ist uneinheitlich (gemischt manuell/automatisch oder unterschiedliches Produkt/Warenart/Service). Bitte die manuelle Carrier-Konfiguration korrigieren.','E','CARRIER_ADVISE_MANUAL_INCONSISTENT_ON_HU',TO_TIMESTAMP('2026-06-15 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking.CarrierAdvise_ManualInconsistentOnHU')
;

-- Message 2: Non-manual advise has divergent carrier products on one HU
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,ErrorCode,Updated,UpdatedBy,Value) VALUES (0,545755 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-15 00:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','HU {0}: unterschiedliche Lieferweg-Produkte auf einer HU. Bitte erneut abfragen (Re-Advise), um zu vereinheitlichen.','E','CARRIER_ADVISE_NONMANUAL_DIVERGENT_ON_HU',TO_TIMESTAMP('2026-06-15 00:00:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking.CarrierAdvise_NonManualDivergentOnHU')
;

-- Create skeleton AD_Message_Trl rows for all active system languages
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID IN (545754,545755) AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Set English translations for all three messages
UPDATE AD_Message_Trl SET MsgText='HU {0}: manual carrier advise is inconsistent (mixed manual/automatic, or differing product/goods-type/service). Fix the manual carrier setup.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-15 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545754
;

UPDATE AD_Message_Trl SET MsgText='HU {0}: divergent carrier products on one HU. Use re-advise to unify them.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-15 00:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545755
;

-- Set German translations (de_DE and de_CH)
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU {0}: manuelle Lieferweg-Abfrage ist uneinheitlich (gemischt manuell/automatisch oder unterschiedliches Produkt/Warenart/Service). Bitte die manuelle Carrier-Konfiguration korrigieren.',Updated=TO_TIMESTAMP('2026-06-15 00:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545754
;

UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU {0}: unterschiedliche Lieferweg-Produkte auf einer HU. Bitte erneut abfragen (Re-Advise), um zu vereinheitlichen.',Updated=TO_TIMESTAMP('2026-06-15 00:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545755
;

UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU {0}: manuelle Lieferweg-Abfrage ist uneinheitlich (gemischt manuell/automatisch oder unterschiedliches Produkt/Warenart/Service). Bitte die manuelle Carrier-Konfiguration korrigieren.',Updated=TO_TIMESTAMP('2026-06-15 00:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545754
;

UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='HU {0}: unterschiedliche Lieferweg-Produkte auf einer HU. Bitte erneut abfragen (Re-Advise), um zu vereinheitlichen.',Updated=TO_TIMESTAMP('2026-06-15 00:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545755
;
