-- gh29437: remove the AD metadata for the now-dropped backend-only filter column
-- M_Packageable_V.IsPickQtyOnDraftShipment (added by 5805870_sys_gh29437_..._DML.sql).
-- The physical view column is removed in 5806850_sys_gh29437_M_Packageable_v_drop_IsPickQtyOnDraftShipment.sql.
-- No AD_Field / AD_UI_Element / AD_Element_Link ever referenced this column (backend filter only),
-- so the delete is limited to AD_Column(+_Trl) and AD_Element(+_Trl).
--   AD_Column  592700  M_Packageable_V.IsPickQtyOnDraftShipment
--   AD_Element 584934  IsPickQtyOnDraftShipment

-- FK-safe order: _Trl children first, then AD_Column (FK to AD_Element), then AD_Element(+_Trl).
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=592700
;
DELETE FROM AD_Column WHERE AD_Column_ID=592700
;
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=584934
;
DELETE FROM AD_Element WHERE AD_Element_ID=584934
;
