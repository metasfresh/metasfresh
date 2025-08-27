-- Run mode: SWING_CLIENT

-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2025-08-27T08:22:32.501Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2025-08-27 08:22:32.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=579326
;


-- Field: Wareneingangsdisposition(540196,de.metas.inoutcandidate) -> Wareneingangsdisposition(540526,de.metas.inoutcandidate) -> Zugesagter Termin abw.
-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2025-08-27T08:31:56.757Z
UPDATE AD_Field SET IsAlwaysUpdateable='Y', IsReadOnly='N',Updated=TO_TIMESTAMP('2025-08-27 08:31:56.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=680646
;

