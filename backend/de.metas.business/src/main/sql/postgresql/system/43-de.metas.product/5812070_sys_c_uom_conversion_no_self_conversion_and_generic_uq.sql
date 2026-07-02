-- Make C_UOM_Conversion self-guarding against rows that crash the product REST API.
--
-- Problem: a row with C_UOM_ID = C_UOM_To_ID (an "identity" conversion) or an
-- active duplicate UOM pair makes the per-product UOM-conversion map build throw
-- (Guava Maps.uniqueIndex duplicate key), so GET /api/v2/material/products fails.
-- An application interceptor already rejects identity rows on ORM save; this adds
-- DB-level guards so ORM-bypassing paths (raw SQL, DB functions) cannot introduce
-- them either, and closes the generic-duplicate gap the existing product index
-- misses (Postgres treats NULL M_Product_ID as DISTINCT in a unique index).
--
-- Idempotent + re-run-safe (also runnable as a manual hotfix on a target instance).

-- Backup before the destructive cleanup (non-AD data table).
SELECT backup_table('c_uom_conversion', '_uom_identity_cleanup');

-- STEP 1 — remove the unambiguous junk: non-catch identity rows (active + inactive).
-- An identity conversion (from == to) is always redundant; the code serves X->X
-- synthetically. Catch-weight rows are excluded and handled fail-loud in STEP 2.
DELETE FROM c_uom_conversion
WHERE c_uom_id = c_uom_to_id
  AND iscatchuomforproduct = 'N';

-- STEP 2 — FAIL LOUD on the ambiguous cases (data decisions, not mechanical):
--   (a) catch-weight identity rows (should never exist; deleting one has
--       shipment-schedule side effects -> needs manual review);
--   (b) active duplicate UOM pairs (product OR generic) with from <> to — the two
--       rows may carry different rates, so which to keep is a master-data decision.
-- Raising aborts the (transactional) migration cleanly for manual resolution.
DO $$
DECLARE
    catch_identity int;
    dup_groups     text;
BEGIN
    SELECT count(*) INTO catch_identity
    FROM c_uom_conversion
    WHERE c_uom_id = c_uom_to_id AND iscatchuomforproduct = 'Y';
    IF catch_identity > 0 THEN
        RAISE EXCEPTION 'C_UOM_Conversion: % catch-weight identity row(s) (C_UOM_ID = C_UOM_To_ID, IsCatchUOMForProduct=Y) need manual review before the CHECK can be added', catch_identity;
    END IF;

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
        RAISE EXCEPTION 'C_UOM_Conversion: active duplicate conversion pair(s) need manual resolution (rates may differ): %', dup_groups;
    END IF;
END $$;

-- STEP 3 — PRODUCT-scope unique index. Replaces gh18349's c_uom_conversion_product.
-- Same uniqueness (identical column set), but M_Product_ID leading gives a true
-- index seek for the by-product load (WHERE M_Product_ID=? AND IsActive='Y').
DROP INDEX IF EXISTS c_uom_conversion_product;
DROP INDEX IF EXISTS c_uom_conversion_product_uom_uq;
CREATE UNIQUE INDEX c_uom_conversion_product_uom_uq
    ON c_uom_conversion (m_product_id, c_uom_id, c_uom_to_id)
    WHERE isactive = 'Y' AND m_product_id IS NOT NULL;

-- STEP 4 — GENERIC-scope unique index (new). Closes the generic-duplicate gap and
-- serves the generic-conversion load (WHERE M_Product_ID IS NULL AND IsActive='Y').
DROP INDEX IF EXISTS c_uom_conversion_generic_uq;
CREATE UNIQUE INDEX c_uom_conversion_generic_uq
    ON c_uom_conversion (c_uom_id, c_uom_to_id)
    WHERE isactive = 'Y' AND m_product_id IS NULL;

-- STEP 5 — CHECK: block identity rows at the DB, incl. ORM-bypassing paths.
ALTER TABLE c_uom_conversion DROP CONSTRAINT IF EXISTS c_uom_conversion_no_self_conversion;
ALTER TABLE c_uom_conversion ADD  CONSTRAINT c_uom_conversion_no_self_conversion CHECK (c_uom_id <> c_uom_to_id);
