-- Lock down the Produktkosten (AD_Window_ID=344) M_Cost tab (AD_Tab_ID=701) cost-price fields:
-- CurrentCostPrice and FutureCostPrice must be READ-ONLY unconditionally, so manual editing there
-- can no longer bypass the Cost Revaluation document, the single forward-only path for changing
-- standard cost. Setting AD_Field.IsReadOnly='Y' short-circuits the shared AD_Column.ReadOnlyLogic
-- ('@CostingMethod@!x & @CostingMethod@!S' / '@CostingMethod@!S') for these two fields only
-- (window 344 / tab 701); the underlying AD_Column is left untouched since it is shared by other
-- windows/tabs.
UPDATE AD_Field
SET IsReadOnly = 'Y',
    Updated = TO_TIMESTAMP('2026-09-28 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Field_ID IN (11350, 11352);
