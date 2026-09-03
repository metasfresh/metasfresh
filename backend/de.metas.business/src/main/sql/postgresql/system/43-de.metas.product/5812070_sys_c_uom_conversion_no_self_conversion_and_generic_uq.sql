-- Install DB-level guards on C_UOM_Conversion against rows that crash the product REST API.
--
-- Problem: a row with C_UOM_ID = C_UOM_To_ID (identity), or an active duplicate UOM pair,
-- makes the per-product UOM-conversion map build throw (Guava Maps.uniqueIndex duplicate
-- key), so GET /api/v2/material/products fails.
--
-- This migration ONLY installs the guards. It does NOT modify data: cleaning up existing
-- identity / duplicate rows is a deliberate manual operation (rates may differ; which row
-- to keep is a data decision). If such rows exist, this migration FAILS LOUD with a clear
-- message so the identity/duplicate cleanup is run manually on the instance first, then this
-- is re-applied. Idempotent + re-run-safe.
--
-- The existing product-scope index c_uom_conversion_product (gh18349, migration 5727690) is
-- LEFT UNTOUCHED on purpose: it already enforces product-scope uniqueness AND is the access
-- path for the by-product conversion load. It is only extended here by (a) the generic-scope
-- unique index that closes the NULL-distinctness gap, and (b) the identity CHECK constraint.

-- STEP 1 — FAIL LOUD if existing data would violate the guards. No data is modified here.
DO $$
DECLARE
    identity_rows int;
    dup_groups    text;
BEGIN
    -- Checks ALL rows (active AND inactive): the CHECK constraint below applies to both.
    SELECT count(*) INTO identity_rows
    FROM c_uom_conversion
    WHERE c_uom_id = c_uom_to_id;
    IF identity_rows > 0 THEN
        RAISE EXCEPTION 'C_UOM_Conversion: % identity row(s) (C_UOM_ID = C_UOM_To_ID) exist. Run the manual identity-conversion cleanup on this instance, then re-apply this migration.', identity_rows;
    END IF;

    -- Active duplicate pairs. GROUP BY treats NULL M_Product_ID as equal (unlike a unique
    -- index), so this also catches generic duplicates that the new generic index would reject.
    SELECT string_agg(format('(M_Product_ID=%s, %s->%s : ids %s)',
                             COALESCE(m_product_id::text, 'NULL'), c_uom_id, c_uom_to_id, ids), '; ')
    INTO dup_groups
    FROM (
        SELECT m_product_id, c_uom_id, c_uom_to_id,
               array_agg(c_uom_conversion_id ORDER BY c_uom_conversion_id) AS ids
        FROM c_uom_conversion
        WHERE isactive = 'Y' AND c_uom_id <> c_uom_to_id
        GROUP BY m_product_id, c_uom_id, c_uom_to_id
        HAVING count(*) > 1
    ) d;
    IF dup_groups IS NOT NULL THEN
        RAISE EXCEPTION 'C_UOM_Conversion: active duplicate conversion pair(s) exist; resolve manually (rates may differ), then re-apply: %', dup_groups;
    END IF;
END $$;

-- STEP 2 — GENERIC-scope unique index (new). Closes the generic-duplicate gap: the existing
-- c_uom_conversion_product index keys on (c_uom_id, c_uom_to_id, m_product_id), and Postgres
-- treats a NULL M_Product_ID as DISTINCT in a unique index, so two active generic rows for the
-- same UOM pair coexist and crash getGenericConversions. This partial index also serves the
-- generic load (WHERE M_Product_ID IS NULL AND IsActive='Y').
DROP INDEX IF EXISTS c_uom_conversion_generic_uq;
CREATE UNIQUE INDEX c_uom_conversion_generic_uq
    ON c_uom_conversion (c_uom_id, c_uom_to_id)
    WHERE isactive = 'Y' AND m_product_id IS NULL;

-- STEP 3 — CHECK: block identity rows at the DB, incl. ORM-bypassing paths.
ALTER TABLE c_uom_conversion DROP CONSTRAINT IF EXISTS c_uom_conversion_no_self_conversion;
ALTER TABLE c_uom_conversion ADD  CONSTRAINT c_uom_conversion_no_self_conversion CHECK (c_uom_id <> c_uom_to_id);
