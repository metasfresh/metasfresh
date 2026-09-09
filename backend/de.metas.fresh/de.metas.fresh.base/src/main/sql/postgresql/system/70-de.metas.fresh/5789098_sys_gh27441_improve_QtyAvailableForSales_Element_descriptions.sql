-- Run mode: SWING_CLIENT

-- gh#27441: Add descriptions to AD_Element 584542 ("Verfügbare Menge" / "Available Quantity")
-- which is used as AD_Name on all QtyAvailableForSales fields.
-- Explains what the value means, when it is updated, and when it can go stale.

-- 1. Update DE (base language) description + help
-- 2026-02-18T17:30:00.000Z
UPDATE AD_Element_Trl SET
Description='Lagerbestand abzüglich offener Liefermengen anderer Aufträge für dasselbe Produkt, in der Maßeinheit der Auftragsposition. Wird beim Anlegen oder Ändern der Auftragsposition berechnet; danach nicht automatisch aktualisiert.',
Help='Berechnung: Lagerbestand − (offene Liefermengen aller Aufträge − bestellte Menge dieser Position). ''Offene Liefermengen'' umfasst alle nicht-ausgelieferten Lieferdispo-Einträge für dasselbe Produkt. Der Wert ist eine Momentaufnahme zum Zeitpunkt der letzten Änderung dieser Auftragsposition. Er wird NICHT automatisch aktualisiert, wenn sich der Lagerbestand ändert oder andere Aufträge für dasselbe Produkt angelegt werden.',
Updated=TO_TIMESTAMP('2026-02-18 17:30:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=584542 AND AD_Language='de_DE'
;

-- 2026-02-18T17:30:00.100Z
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584542, 'de_DE')
;

-- 2026-02-18T17:30:00.200Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584542, 'de_DE')
;

-- 2. Update EN translation
-- 2026-02-18T17:30:00.300Z
UPDATE AD_Element_Trl SET
Description='On-hand stock minus pending shipments of other orders for the same product, in the order line''s UOM. Calculated when the order line is created or modified; not automatically refreshed afterwards.',
Help='Calculation: On-Hand Stock − (Pending Shipments of all orders − This Line''s Ordered Qty). "Pending Shipments" includes all undelivered shipment schedule entries for the same product. The value is a snapshot from when this order line was last changed. It is NOT automatically updated when stock levels change or other orders for the same product are created.',
Updated=TO_TIMESTAMP('2026-02-18 17:30:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE AD_Element_ID=584542 AND AD_Language='en_US'
;

-- 2026-02-18T17:30:00.400Z
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584542, 'en_US')
;
