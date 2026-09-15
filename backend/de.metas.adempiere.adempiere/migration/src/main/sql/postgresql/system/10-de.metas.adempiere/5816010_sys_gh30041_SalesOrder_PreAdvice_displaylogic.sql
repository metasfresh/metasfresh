-- Sales Order pre-advice flag: show only when a shipper is selected.
-- Sets DisplayLogic '@M_Shipper_ID@!0' on the IsPreAdviceRequired field (AD_Field 780683) on the
-- Sales Order window (143, header tab 186) -- consistent with the carrier fields in the same
-- advanced-edit element group (540499), which carry the identical '@M_Shipper_ID@!0' gate.
-- Pre-advice is only actionable once a carrier is chosen. The field was created without a
-- DisplayLogic; this sets it.

-- 2026-07-24T10:00:00.000Z
UPDATE AD_Field
   SET DisplayLogic='@M_Shipper_ID@!0',
       Updated=TO_TIMESTAMP('2026-07-24 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
       UpdatedBy=100
 WHERE AD_Field_ID=780683
;
