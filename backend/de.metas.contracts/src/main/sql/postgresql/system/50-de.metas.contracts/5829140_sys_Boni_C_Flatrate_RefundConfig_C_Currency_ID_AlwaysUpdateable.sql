-- Boni: the currency of a per-unit refund line can be corrected after its contract conditions are completed, until the line has issued a refund
-- (the refund config interceptor refuses the change after that). AD_MigrationScript 5829140 from the central ID server.

-- Column: C_Flatrate_RefundConfig.C_Currency_ID
UPDATE AD_Column SET IsAlwaysUpdateable='Y', Updated=TO_TIMESTAMP('2026-10-10 15:31:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=560757
;
