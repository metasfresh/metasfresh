-- Column: C_Order_ReturnPackage.C_BPartner_ID
-- The pallet balance is kept per invoice partner: show the order's invoice partner (fallback: the order's partner)
-- 2026-11-13T15:30:00.000Z
UPDATE AD_Column SET ColumnSQL='(SELECT COALESCE(o.Bill_BPartner_ID, o.C_BPartner_ID) FROM C_Order o WHERE o.C_Order_ID = C_Order_ReturnPackage.C_Order_ID)',Updated=TO_TIMESTAMP('2026-11-13 15:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592856
;
