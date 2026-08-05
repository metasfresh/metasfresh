-- Migration-script id 5817640 allocated from idserver.metas.de (TABLE=AD_MigrationScript).
-- No AD_* ids are allocated here — this is pure index DDL (no AD_Column/Element/etc. inserts).
--
-- Enforce natural-key uniqueness on C_Conversion_Rate: at most one rate per
-- (org, from-currency, to-currency, conversion-type, ValidFrom) — client is intentionally NOT part
-- of the key. Replaces the pre-existing NON-unique index c_conversionrate_once (which led with
-- AD_Client_ID). The rate upsert finds-then-updates in place and reads the row without an IsActive
-- filter, so the single-row-per-key invariant must cover active AND inactive rows: no partial
-- WHERE IsActive predicate here.
--
-- Fail loudly if the (client-less) natural key already has duplicates on this instance, so a bad
-- rollout is caught here rather than silently picking an arbitrary row at runtime.
DO $$
DECLARE
    v_dupes int;
BEGIN
    SELECT count(*) INTO v_dupes FROM (
        SELECT 1
        FROM c_conversion_rate
        GROUP BY ad_org_id, c_currency_id, c_currency_id_to, c_conversiontype_id, validfrom
        HAVING count(*) > 1
    ) d;
    IF v_dupes > 0 THEN
        RAISE EXCEPTION 'C_Conversion_Rate has % duplicate natural key(s) on (ad_org_id, c_currency_id, c_currency_id_to, c_conversiontype_id, validfrom); resolve the duplicates before this unique index can be created', v_dupes;
    END IF;
END $$;

DROP INDEX IF EXISTS c_conversionrate_once;

-- Selective business columns first, low-cardinality AD_Org_ID last (composite-index ordering rule).
CREATE UNIQUE INDEX c_conversion_rate_natural_key_uq
    ON c_conversion_rate (c_currency_id, c_currency_id_to, c_conversiontype_id, validfrom, ad_org_id);
