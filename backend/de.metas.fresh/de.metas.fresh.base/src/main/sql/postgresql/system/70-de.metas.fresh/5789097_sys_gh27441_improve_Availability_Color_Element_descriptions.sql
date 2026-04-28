-- Run mode: SWING_CLIENT

-- gh#27441: Improve AD_Element 584543 descriptions for InsufficientQtyAvailableForSalesColor_ID field.
-- Explain when the red dot is shown vs not shown, and the underlying formula.

-- 1. Update DE (base language) description + help
-- 2026-02-18T17:00:00.000Z
UPDATE AD_Element_Trl SET
Description='Roter Punkt: Die verfügbare Menge (Lagerbestand abzgl. offener Lieferungen anderer Aufträge) reicht nicht aus, um diese Position vollständig zu bedienen. Kein Punkt: Ausreichend Bestand vorhanden.',
Help='Berechnung: Verfügbare Menge = Lagerbestand − (offene Liefermengen − bestellte Menge dieser Position). Der rote Punkt erscheint, wenn die verfügbare Menge kleiner ist als die bestellte Menge dieser Auftragsposition. Die Werte werden beim Anlegen oder Ändern der Auftragsposition berechnet und können bei nachfolgenden Aufträgen oder Lieferungen für dasselbe Produkt veralten.',
Updated=TO_TIMESTAMP('2026-02-18 17:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=584543 AND AD_Language='de_DE'
;

-- 2026-02-18T17:00:00.100Z
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584543, 'de_DE')
;

-- 2026-02-18T17:00:00.200Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584543, 'de_DE')
;

-- 2. Update EN translation
-- 2026-02-18T17:00:00.300Z
UPDATE AD_Element_Trl SET
Description='Red dot: the available quantity (on-hand stock minus pending shipments from other orders) is insufficient to fulfill this order line. No dot: enough stock is available.',
Help='Calculation: Available Qty = On-Hand Stock − (Pending Shipments − This Line''s Ordered Qty). The red dot appears when Available Qty < Ordered Qty of this line. Values are calculated when the order line is created or modified and may become stale as other orders or shipments affect the same product.',
Updated=TO_TIMESTAMP('2026-02-18 17:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=584543 AND AD_Language='en_US'
;

-- 2026-02-18T17:00:00.400Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584543, 'en_US')
;
