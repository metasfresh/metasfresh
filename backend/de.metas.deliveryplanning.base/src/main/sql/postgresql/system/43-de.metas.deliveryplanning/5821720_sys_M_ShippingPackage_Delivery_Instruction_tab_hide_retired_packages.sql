-- Delivery Planning: hide retired shipping packages on the delivery instruction's Versandpaket tab
-- (AD_Tab 546736, window Lieferanweisungen 541657).
--
-- Releasing an allocation DEACTIVATES its shipping package rather than deleting it, so the record of what was
-- once planned survives. Without a WhereClause the tab renders those retired rows next to the live ones, and
-- the planner cannot tell what is on the truck from what came off it. On the local stack 103 of 297 packages
-- are already IsActive='N'.
--
-- Only this tab. The transport-order window's own Versandpackung tab (AD_Tab 540097, window 540020) is
-- deliberately left alone: the two documents share M_ShippingPackage, and the transport-order role is
-- explicitly not changed by this work.
--
-- IDs allocated from idserver.metas.de on 2026-09-01:
--   AD_MigrationScript 5821720 (this file)

UPDATE AD_Tab
SET WhereClause = 'M_ShippingPackage.IsActive=''Y''',
    Updated = TO_TIMESTAMP('2026-09-01 18:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Tab_ID = 546736
;
