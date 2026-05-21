-- Tax Declaration: change C_TaxDeclaration.C_Period_ID widget from Table Direct (19)
-- to Search (30). The Search widget gives a richer picker (typeahead + open in a separate
-- panel) which is better for browsing periods than the inline dropdown of Table Direct.
-- AD_Val_Rule 540787 (filter to AcctSchema's calendar) is preserved by leaving
-- AD_Reference_Value_ID intact.

UPDATE AD_Column
SET AD_Reference_ID = 30,
    Updated = TIMESTAMP '2026-05-19 00:00:00',
    UpdatedBy = 100
WHERE AD_Column_ID = 592558;
-- AD_Field 780253 keeps AD_Reference_ID NULL and inherits from the AD_Column.
