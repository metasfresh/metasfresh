-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2025-12-12T09:27:01.609Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2025-12-12 09:27:01.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589435
;

-- UI Element: Lieferdisposition OLD(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Mindesthaltbarkeitsdatum
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2025-12-12T09:31:53.634Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=627351
;

-- 2025-12-12T09:32:38.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734027
;

-- Field: Lieferdisposition OLD(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> MHD
-- Column: M_ShipmentSchedule.BestBeforeDate
-- 2025-12-12T09:32:39.235Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=734027
;

-- 2025-12-12T09:32:39.722Z
DELETE FROM AD_Field WHERE AD_Field_ID=734027
;

