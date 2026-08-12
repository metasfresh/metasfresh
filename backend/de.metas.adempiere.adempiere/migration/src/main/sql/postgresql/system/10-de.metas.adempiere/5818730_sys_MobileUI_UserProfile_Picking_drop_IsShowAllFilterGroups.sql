-- Run mode: SWING_CLIENT

-- Removes MobileUI_UserProfile_Picking.IsShowAllFilterGroups again, together with its field on the
-- picking-profile window (541743 / tab 547258) and its element.
--
-- Justification for the DROP COLUMN: the setting offered every configured filter group at once instead of
-- revealing them as the operator narrows down. In practice that is a step backwards — the filter options
-- narrow only in the configured group order, so with every group on screen at once, selecting a value in a
-- later group visibly narrows nothing. The same need (reaching the delivery-date filter without first
-- choosing a customer) is met by configuration alone: give the DeliveryDate filter row the lowest sequence
-- number. Nothing reads the column any more, and a settings checkbox that does nothing is worse than none.
--
-- Added by 5818150; that migration's AD ids are the ones removed here.

SELECT backup_table('mobileui_userprofile_picking', '_drop_isshowallfiltergroups');

-- UI element on the picking-profile window
-- 2026-08-12T10:10:00.000Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=652815
;

-- 2026-08-12T10:10:00.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781902
;

-- 2026-08-12T10:10:00.200Z
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID=781902
;

-- 2026-08-12T10:10:00.300Z
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID=781902
;

-- 2026-08-12T10:10:00.400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID=781902
;

-- 2026-08-12T10:10:00.450Z
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID=781902
;

-- 2026-08-12T10:10:00.460Z
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID=781902
;

-- 2026-08-12T10:10:00.500Z
DELETE FROM AD_Field WHERE AD_Field_ID=781902
;

-- Column
-- 2026-08-12T10:10:01.000Z
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=593131
;

-- 2026-08-12T10:10:01.100Z
DELETE FROM AD_Column WHERE AD_Column_ID=593131
;

-- 2026-08-12T10:10:02.000Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking DROP COLUMN IF EXISTS IsShowAllFilterGroups')
;

-- Element
-- 2026-08-12T10:10:03.000Z
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=585163
;

-- 2026-08-12T10:10:03.100Z
DELETE FROM AD_Element WHERE AD_Element_ID=585163
;
