-- Fix: IsDropShipWarehouse field label shows German base name on English UI because the
-- earlier propagation to AD_Field_Trl / AD_UI_Element_Trl did not flip en_US IsTranslated to 'Y'
-- (or did not run at all on this branch).
--
-- Set the English translation directly on the field's translation row and (if present) on the
-- AD_UI_Element_Trl row so the WebUI picks the English label on en_US.

-- AD_Field_Trl en_US
UPDATE AD_Field_Trl
SET Name        = 'Dropship Warehouse',
    Description = 'If Yes, sales orders on this warehouse are handled as dropship. On sales-order completion a single purchase order is automatically created for the vendor.',
    Help        = 'Marks this warehouse as a dropship warehouse. On such warehouses, completing a sales order automatically creates exactly one purchase order per sales order — bypassing the normal material-disposition / purchase-candidate path. Per-line vendor selection is enforced more strictly for dropship warehouses.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-05-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy   = 100
WHERE AD_Field_ID = 779180 AND AD_Language = 'en_US';

-- AD_UI_Element_Trl en_US (the WebUI label is read from here when present; falls back to AD_Field_Trl otherwise)
UPDATE AD_UI_Element_Trl
SET Name         = 'Dropship Warehouse',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-13 10:00:01','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_UI_Element_ID = 651164 AND AD_Language = 'en_US';

-- AD_Column_Trl en_US (kept consistent — read by some legacy paths)
UPDATE AD_Column_Trl
SET Name         = 'Dropship Warehouse',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-05-13 10:00:02','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Column_ID = 592508 AND AD_Language = 'en_US';
