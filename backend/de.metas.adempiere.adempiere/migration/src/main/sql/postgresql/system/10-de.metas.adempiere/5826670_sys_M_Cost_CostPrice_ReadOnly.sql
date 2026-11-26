-- Produktkosten (window 344, M_Cost tab 701): CurrentCostPrice and FutureCostPrice read-only for every costing method,
-- so a cost price is changed only through the Cost Revaluation document. The shared AD_Column is left untouched.
UPDATE AD_Field
SET IsReadOnly = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID IN (11350, 11352);
