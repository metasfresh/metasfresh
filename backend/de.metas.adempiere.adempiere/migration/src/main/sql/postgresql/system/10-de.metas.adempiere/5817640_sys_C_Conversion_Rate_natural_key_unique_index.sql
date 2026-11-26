-- Migration-script id 5817640 allocated from idserver.metas.de (TABLE=AD_MigrationScript).
-- No AD_* ids are allocated here — this is pure index DDL (no AD_Column/Element/etc. inserts).
--
-- Enforce natural-key uniqueness on C_Conversion_Rate: at most one rate per
-- (org, from-currency, to-currency, conversion-type, ValidFrom). Replaces the pre-existing NON-unique
-- index c_conversionrate_once. No WHERE IsActive predicate: the upsert reads/updates rows regardless
-- of IsActive, so the single-row-per-key invariant must cover active AND inactive rows.

DROP INDEX IF EXISTS c_conversionrate_once;

-- ValidFrom leads (newest-rate read's DISTINCT-ON sort column), then currency/type, low-cardinality AD_Org_ID last.
CREATE UNIQUE INDEX c_conversion_rate_natural_key_uq
    ON c_conversion_rate (validfrom, c_currency_id, c_currency_id_to, c_conversiontype_id, ad_org_id);
