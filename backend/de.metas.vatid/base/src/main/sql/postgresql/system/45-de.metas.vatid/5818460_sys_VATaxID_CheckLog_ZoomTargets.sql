-- VAT-ID online check: enable the free generic reverse-zoom from C_BPartner / C_BPartner_Location
-- to their VATaxID_CheckLog rows, so a partner's check history is reachable from the partner record
-- via the Related-Documents panel without any extra window/relation-type work. AD_Column defaults
-- IsExcludeFromZoomTargets to 'Y', which was left at the default on both FK columns.

UPDATE AD_Column
SET IsExcludeFromZoomTargets = 'N',
    Updated = TO_TIMESTAMP('2026-08-12 12:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID IN (593172, 593173);
