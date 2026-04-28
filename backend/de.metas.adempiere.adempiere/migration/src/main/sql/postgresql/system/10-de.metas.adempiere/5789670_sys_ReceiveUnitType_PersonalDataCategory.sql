-- Fix: set PersonalDataCategory for ReceiveUnitType columns (missing from 5789610)
UPDATE AD_Column
SET PersonalDataCategory='NP',
    Updated=TO_TIMESTAMP('2026-02-22 16:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=0
WHERE AD_Column_ID IN (592054, 592055)
;
