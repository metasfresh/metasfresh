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
-- The CREATE UNIQUE INDEX itself fails loudly if the (client-less) natural key already has
-- duplicates on this instance, so a bad rollout is caught here — no separate duplicate-guard needed.

DROP INDEX IF EXISTS c_conversionrate_once;

-- ValidFrom leads (the newest-rate read's leading ORDER BY / DISTINCT-ON sort column, and the
-- natural lead for future ValidFrom range queries), then the business currency/type columns, and
-- the low-cardinality AD_Org_ID last (composite-index ordering rule).
CREATE UNIQUE INDEX c_conversion_rate_natural_key_uq
    ON c_conversion_rate (validfrom, c_currency_id, c_currency_id_to, c_conversiontype_id, ad_org_id);
