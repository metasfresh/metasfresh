-- The net amount of the dashboard KPIs "Purchase order net amount" (1000006) and "Sales order net amount" (1000007)
-- is a currency amount, but their value fields were typed Integer (AD_Reference_ID 11), so the dashboard tiles
-- rounded e.g. 350.50 to 351. Type them Amount (AD_Reference_ID 12) so the tiles show the 2 decimals.
UPDATE WEBUI_KPI_Field
SET AD_Reference_ID = 12 /* Amount */,
    Updated = TO_TIMESTAMP('2026-11-01 20:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE WEBUI_KPI_Field_ID IN (1000073, 1000074)
  AND WEBUI_KPI_ID IN (1000006, 1000007);
