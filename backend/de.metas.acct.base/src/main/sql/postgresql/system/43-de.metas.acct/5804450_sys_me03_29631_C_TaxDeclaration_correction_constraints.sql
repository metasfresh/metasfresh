-- Tax Declaration: enforce Correction invariants at DB level.
-- Iter 7 of EPIC https://github.com/metasfresh/me03/issues/28717 — Corrections lifecycle.
--
-- 1. Star-topology CHECK: at-most-N-Corrections-per-Original is enforced by the FK alone,
--    but the *shape* invariant ("if it's an Original it has no Original_ID; if it's a
--    Correction it must have one") needs an explicit CHECK.
-- 2. The Iter 5/6 unique partial index (one PROCESSED row per (AcctSchema, Period))
--    must now exclude Corrections — otherwise a Correction with the same period as its
--    Original would be blocked.

-- ====================================================================================
-- Section 1: Star-topology CHECK
-- ====================================================================================
ALTER TABLE C_TaxDeclaration
    ADD CONSTRAINT C_TaxDeclaration_StarTopology_check
    CHECK (
        (IsCorrection = 'N' AND C_TaxDeclaration_Original_ID IS NULL)
     OR (IsCorrection = 'Y' AND C_TaxDeclaration_Original_ID IS NOT NULL)
    );

-- ====================================================================================
-- Section 2: Tighten the period-uniqueness partial unique index to Originals only
-- Original index from migration 5803480 keyed on (AcctSchema, Period) WHERE Processed='Y'
--   AND IsActive='Y' AND C_Period_ID IS NOT NULL.
-- ====================================================================================
DROP INDEX IF EXISTS C_TaxDeclaration_acctschema_period_unique;
CREATE UNIQUE INDEX C_TaxDeclaration_acctschema_period_unique
    ON C_TaxDeclaration(C_AcctSchema_ID, C_Period_ID)
    WHERE C_Period_ID IS NOT NULL
      AND IsActive    = 'Y'
      AND Processed   = 'Y'
      AND IsCorrection = 'N';
