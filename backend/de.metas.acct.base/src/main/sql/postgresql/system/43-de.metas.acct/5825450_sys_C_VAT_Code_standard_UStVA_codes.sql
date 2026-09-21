-- Umsatzsteuervoranmeldung: enter the standard German VAT-advance-return codes (Kennziffern) as
-- C_VAT_Code entries on the standard tax records in the base accounting schema 1000000.
--
-- We do NOT match by C_Tax_ID
-- (proven unstable across instances: base 1000025 is "0% Steuer-ID EU" while dt/fm 1000025 is
-- intra-community reverse-charge). We match by the tax's SEMANTIC SIGNATURE:
--   (Rate, C_TaxCategory_ID, C_Country_ID, TypeOfDestCountry, EN16931VATCategory, IsReverseCharge)
-- A signature with no matching tax on the instance inserts nothing there (e.g. base has no reverse-charge
-- AE tax, so codes 89/41/61 do not land on base). Only taxes we have a code for are touched.
--
-- SCOPE: this CORE migration covers ONLY the base accounting schema 1000000 (present on every
-- instance, so this ships the standard German codes to base / all new customers). All matching taxes,
-- incl. inactive taxes. The per-organization accounting schemas are fm-specific and are handled in a
-- SEPARATE fm206 customer-repo migration (they must not be imposed on other customers' org schemas).
-- AD_Org_ID=0 (client-level, as the source). ValidFrom=1999-12-31 / ValidTo=NULL ("always valid",
-- as the source). Idempotent: NOT EXISTS guard (ignores soft-deleted rows).
--
-- PK: each refcode row carries a fixed C_VAT_Code_ID from the central ID server (idserver.metas.de,
-- TABLE=c_vat_code) -- migration scripts must not use nextval() (metasfresh-db rule). Because schema
-- 1000000 is unique per instance and each signature resolves to at most one tax, each id is used at
-- most once per instance; a signature that (anomalously) matched two taxes would collide on the id and
-- fail the migration loud, rather than silently duplicating the code.
-- IDs allocated from idserver.metas.de on 2026-09-21 (TABLE=c_vat_code): 540144-540152.

WITH refcode(rate, c_taxcategory_id, c_country_id, typeofdestcountry, en16931vatcategory, isreversecharge, amounttype, issotrx, vatcode, c_vat_code_id) AS (
    VALUES
        -- 19% (DE) domestic, standard rate (EN16931 S)
        (19, 1000009, 101, 'DOMESTIC',             'S',  'N', 'N', 'Y', '81', 540144),
        (19, 1000009, 101, 'DOMESTIC',             'S',  'N', 'T', 'N', '66', 540145),
        -- 0% Steuerbefreit (Deutschland) domestic (EN16931 E)
        ( 0, 1000011, 101, 'DOMESTIC',             'E',  'N', 'N', 'Y', '48', 540146),
        -- innergemeinschaftliche Lieferung 19% (EU), reverse-charge (EN16931 AE)
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'N', 'N', '89', 540147),
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'N', 'Y', '41', 540148),
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'T', 'N', '61', 540149),
        -- Steuerbefreit 0% (EU) (EN16931 K)
        ( 0, 1000011, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540150),
        -- Normaler Steuersatz 0% (Drittland/Welt) (EN16931 E)
        ( 0, 1000009, 101, 'OUTSIDE_COUNTRY_AREA', 'E',  'N', 'N', 'Y', '43', 540151),
        -- Steuerbefreit 0% (Drittland/Welt) (EN16931 E)
        ( 0, 1000011, 101, 'OUTSIDE_COUNTRY_AREA', 'E',  'N', 'N', 'Y', '43', 540152)
)
INSERT INTO c_vat_code (
    c_vat_code_id, ad_client_id, ad_org_id, c_acctschema_id, c_tax_id,
    vatcode, amounttype, issotrx, validfrom, validto, description, isactive,
    created, createdby, updated, updatedby
)
SELECT r.c_vat_code_id /*From ID Server*/,
       t.ad_client_id, 0, s.c_acctschema_id, t.c_tax_id,
       r.vatcode, r.amounttype, r.issotrx,
       TO_TIMESTAMP('1999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), NULL, NULL, 'Y',
       TO_TIMESTAMP('2026-09-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM refcode r
JOIN c_tax t
      ON t.rate               = r.rate
     AND t.c_taxcategory_id   = r.c_taxcategory_id
     AND t.c_country_id       = r.c_country_id
     AND t.typeofdestcountry  = r.typeofdestcountry
     AND t.en16931vatcategory = r.en16931vatcategory
     AND t.isreversecharge    = r.isreversecharge
JOIN c_acctschema s
      ON s.ad_client_id = t.ad_client_id
     AND s.c_acctschema_id = 1000000                                     -- base schema only (org schemas: fm206 repo)
WHERE NOT EXISTS (
        SELECT 1
        FROM c_vat_code x
        WHERE x.c_acctschema_id = s.c_acctschema_id
          AND x.c_tax_id        = t.c_tax_id
          AND x.amounttype      = r.amounttype
          AND x.issotrx         = r.issotrx
          AND x.vatcode         = r.vatcode
          AND x.isactive        = 'Y'
      );
