-- Tax Declaration: the period-uniqueness index should only block COMPLETED declarations,
-- not drafts. Originally 5801970 had the (correct) filter `WHERE Processed='Y' AND IsActive='Y'`
-- on the old (AcctSchema, DateFrom, DateTo) index. When we switched the index to
-- (AcctSchema, C_Period_ID) in 5803040, the Processed filter got lost and the unique
-- started blocking two draft declarations sharing the same period — which is a legitimate
-- thing to want (parallel test scenarios, side-by-side what-ifs, recovery from broken
-- declarations, etc.).
-- Restore the original semantics: only one PROCESSED declaration per (AcctSchema, Period).

DROP INDEX IF EXISTS C_TaxDeclaration_acctschema_period_unique;

CREATE UNIQUE INDEX IF NOT EXISTS C_TaxDeclaration_acctschema_period_unique
    ON C_TaxDeclaration(C_AcctSchema_ID, C_Period_ID)
    WHERE C_Period_ID IS NOT NULL AND IsActive = 'Y' AND Processed = 'Y';
