-- Fixes failure of: 5804800_ShipperServiceLevelConfig_tab.sql
-- nShift service levels: remove the AD_Org field + UI element that 5804800 created with stale,
-- non-ID-server IDs (AD_Field 580485 / AD_UI_Element 580486). Those low values collide with
-- pre-existing rows in downstream instances. 5804801 re-adds the field + element with fresh IDs.
--
-- Every delete is scoped to our new tab (AD_Tab_ID = 549282), so on instances where 580485/580486
-- belong to a DIFFERENT (private-repo) record those rows are left untouched - only the rows that
-- 5804800 created on this tab are removed. On instances where 5804800 never created them (fresh
-- install, or the insert failed on the duplicate), these deletes are harmless no-ops.

-- UI element first (it references the field via AD_Field_ID)
DELETE FROM AD_UI_Element
WHERE AD_UI_Element_ID = 580486
  AND AD_Tab_ID = 549282
  AND AD_UI_ElementGroup_ID = 555400
  AND AD_Field_ID = 580485;

-- Labels-selector reference (rare; guarded no-op for a plain AD_Org field)
DELETE FROM AD_UI_Element
WHERE Labels_Selector_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

-- field children, only while the field still belongs to our tab
DELETE FROM AD_Element_Link
WHERE AD_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

DELETE FROM AD_Field_Trl
WHERE AD_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

-- remaining FK-chain tables (guarded no-ops for this brand-new field, included for completeness)
DELETE FROM AD_Field_ContextMenu
WHERE AD_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

DELETE FROM AD_UI_ElementField
WHERE AD_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

DELETE FROM AD_UserDef_Field
WHERE AD_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

DELETE FROM AD_User_SortPref_Line
WHERE AD_Field_ID = 580485
  AND EXISTS (SELECT 1 FROM AD_Field f WHERE f.AD_Field_ID = 580485 AND f.AD_Tab_ID = 549282);

-- the field itself
DELETE FROM AD_Field
WHERE AD_Field_ID = 580485
  AND AD_Tab_ID = 549282;
