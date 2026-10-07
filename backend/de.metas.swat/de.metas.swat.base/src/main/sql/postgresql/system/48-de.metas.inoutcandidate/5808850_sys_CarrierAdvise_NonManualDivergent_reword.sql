-- 2026-06-18T00:00:00.000Z
-- Reword AD_Message de.metas.picking.CarrierAdvise_NonManualDivergentOnHU (AD_Message_ID 545755).
-- The E2 carrier-divergence guard now fires only when the HU's shipper has Carrier_Config.IsSelectionRules='N'
-- (selection rules off → nShift cannot auto-resolve the carrier). The reworded text states plainly that
-- completion is blocked because the carrier products/goods-types on the HU diverge while selection rules are
-- off, so the picker must make the carrier consistent (re-advise is no longer the resolution path here).
-- {0} = HU document number / identifier
-- Base language is German (base column); English is the en_US _Trl override.

UPDATE AD_Message SET MsgText='HU {0}: unterschiedliche Lieferweg-Produkte oder Warenarten auf einer HU bei ausgeschalteten Auswahlregeln (nShift kann den Lieferweg nicht automatisch bestimmen). Bitte den Lieferweg auf der HU vereinheitlichen.',Updated=TO_TIMESTAMP('2026-06-18 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545755
;

UPDATE AD_Message_Trl SET MsgText='HU {0}: unterschiedliche Lieferweg-Produkte oder Warenarten auf einer HU bei ausgeschalteten Auswahlregeln (nShift kann den Lieferweg nicht automatisch bestimmen). Bitte den Lieferweg auf der HU vereinheitlichen.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-18 00:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545755
;

UPDATE AD_Message_Trl SET MsgText='HU {0}: unterschiedliche Lieferweg-Produkte oder Warenarten auf einer HU bei ausgeschalteten Auswahlregeln (nShift kann den Lieferweg nicht automatisch bestimmen). Bitte den Lieferweg auf der HU vereinheitlichen.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-18 00:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545755
;

UPDATE AD_Message_Trl SET MsgText='HU {0}: divergent carrier products or goods-types on one HU while selection rules are off (nShift cannot auto-resolve the carrier). Make the carrier on the HU consistent.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-18 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545755
;
