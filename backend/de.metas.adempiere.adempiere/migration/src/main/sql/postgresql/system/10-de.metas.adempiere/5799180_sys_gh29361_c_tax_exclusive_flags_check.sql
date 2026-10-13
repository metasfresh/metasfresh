-- Source DDL: (none — CHECK constraint declared directly on C_Tax; no separate ddl/ file)
-- C_Tax exclusive-flag invariant: at most one of IsTaxExempt, IsReverseCharge, IsWholeTax = 'Y'.
-- Last-mile defence against non-Java writes (bulk SQL INSERT/UPDATE, REST that bypasses interceptors).
-- Java paths are auto-resolved by org.adempiere.tax.model.validator.C_Tax before reaching the DB.

DO $$
DECLARE v_count integer;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM C_Tax
    WHERE ( (CASE WHEN IsTaxExempt     = 'Y' THEN 1 ELSE 0 END)
          + (CASE WHEN IsReverseCharge = 'Y' THEN 1 ELSE 0 END)
          + (CASE WHEN IsWholeTax      = 'Y' THEN 1 ELSE 0 END) ) > 1;
    IF v_count > 0 THEN
        RAISE EXCEPTION 'Cannot add C_Tax exclusive-flags CHECK: % rows violate it. '
                        'Fix them (unset all but one of IsTaxExempt/IsReverseCharge/IsWholeTax) before re-running.',
                        v_count;
    END IF;
END $$;

-- Two-step: ADD ... NOT VALID acquires only a brief ACCESS EXCLUSIVE lock (no table scan),
-- then VALIDATE CONSTRAINT uses SHARE UPDATE EXCLUSIVE which doesn't block concurrent writes.
-- The preflight DO-block above guarantees there are no violating rows, so VALIDATE is safe.
ALTER TABLE C_Tax
    ADD CONSTRAINT c_tax_exclusive_tax_flags
    CHECK ( ( (CASE WHEN IsTaxExempt     = 'Y' THEN 1 ELSE 0 END)
            + (CASE WHEN IsReverseCharge = 'Y' THEN 1 ELSE 0 END)
            + (CASE WHEN IsWholeTax      = 'Y' THEN 1 ELSE 0 END) ) <= 1 ) NOT VALID;

ALTER TABLE C_Tax
    VALIDATE CONSTRAINT c_tax_exclusive_tax_flags;
