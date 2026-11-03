-- Umsatzsteuervoranmeldung: enter the standard German VAT-advance-return codes (Kennziffern) as
-- C_VAT_Code entries on the standard tax records in the base accounting schema 1000000, REGARDLESS of
-- whether that schema is active.
--
-- Companion to 5825840_sys_C_VAT_Code_standard_UStVA_codes_canonical.sql. That script joins only the
-- ACTIVE base schema 1000000 (s.isactive='Y'), on the assumption that an instance which deactivated
-- schema 1000000 (org-schema customers) does not want it coded. That assumption is wrong for instances
-- that keep schema 1000000 inactive but still need the standard UStVA codes on it (e.g. flaming_merlin):
-- there the s.isactive='Y' join matched nothing and the base-schema codes were never inserted.
--
-- This script removes the schema-active restriction entirely and codes the base schema 1000000 whether
-- it is active or inactive. It is safe on every instance 5825840 already covered: the NOT EXISTS guard
-- below skips any natural key already coded there (active schema 1000000), so this is a no-op there and
-- the fresh ids are never consumed; on instances where 5825840 inserted nothing (inactive schema
-- 1000000) this fills the gap. Fresh ids are used so nothing collides with 5825840's rows.
--
-- Matching is by the tax SEMANTIC SIGNATURE (Rate, C_TaxCategory_ID, C_Country_ID, TypeOfDestCountry,
-- EN16931VATCategory, IsReverseCharge) and resolves to exactly ONE canonical tax per signature
-- (DISTINCT ON, preferring the active / most-current tax, deterministic tie-break by C_Tax_ID); a
-- signature with no matching tax on the instance inserts nothing there. AD_Org_ID=0 (client-level).
-- ValidFrom=1999-12-31 / ValidTo=NULL ("always valid").
--
-- PK: each refcode row carries a fixed C_VAT_Code_ID from the central ID server (idserver.metas.de,
-- TABLE=c_vat_code), allocated 2026-09-22: 540169-540183. Central-server ids are < 1000000 and never
-- collide with instance-local (native-sequence) c_vat_code ids, which start at 1000000.

WITH refcode(rate, c_taxcategory_id, c_country_id, typeofdestcountry, en16931vatcategory, isreversecharge, amounttype, issotrx, vatcode, c_vat_code_id) AS (
    VALUES
        -- 19% (DE) domestic, standard rate (EN16931 S)
        (19, 1000009, 101, 'DOMESTIC',             'S',  'N', 'N', 'Y', '81', 540169),
        (19, 1000009, 101, 'DOMESTIC',             'S',  'N', 'T', 'N', '66', 540170),
        -- 0% Steuerbefreit (Deutschland) domestic (EN16931 E)
        ( 0, 1000011, 101, 'DOMESTIC',             'E',  'N', 'N', 'Y', '48', 540171),
        -- innergemeinschaftliche Lieferung 19% (EU), reverse-charge (EN16931 AE)
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'N', 'N', '89', 540172),
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'N', 'Y', '41', 540173),
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'T', 'N', '61', 540174),
        -- Steuerbefreit 0% (EU) (EN16931 K)
        ( 0, 1000011, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540175),
        -- Normaler Steuersatz 0% (Drittland/Welt) (EN16931 E)
        ( 0, 1000009, 101, 'OUTSIDE_COUNTRY_AREA', 'E',  'N', 'N', 'Y', '43', 540176),
        -- Steuerbefreit 0% (Drittland/Welt) (EN16931 E)
        ( 0, 1000011, 101, 'OUTSIDE_COUNTRY_AREA', 'E',  'N', 'N', 'Y', '43', 540177),
        -- === base / new-customer model (exempt-with-VAT-ID ig. supply + Transport), same codes as the reference ===
        -- innergemeinschaftliche Lieferung, exempt-with-VAT-ID model, normal rate (EN16931 K)
        ( 0, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540178),
        ( 0, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'N', '89', 540179),
        ( 0, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'T', 'N', '61', 540180),
        -- reduced-rate 0% exempt with VAT-ID (EN16931 K): only 41 is attested in the reference set
        ( 0, 1000010, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540181),
        -- domestic 19%, Transport category (like the reference Transport taxes)
        (19, 1000012, 101, 'DOMESTIC',             'S',  'N', 'N', 'Y', '81', 540182),
        (19, 1000012, 101, 'DOMESTIC',             'S',  'N', 'T', 'N', '66', 540183)
),
-- resolve each refcode signature to exactly ONE canonical tax on the base schema 1000000 (active OR
-- inactive): prefer the active tax, then the most-current (open-ended / latest ValidTo, then latest
-- ValidFrom), deterministic tie-break by the newest C_Tax_ID. DISTINCT ON runs over the full match set
-- (before the existence guard below), so the pick is stable across runs.
matched AS (
    SELECT DISTINCT ON (r.c_vat_code_id)
           r.c_vat_code_id, r.vatcode, r.amounttype, r.issotrx,
           t.ad_client_id, t.c_tax_id, s.c_acctschema_id
    FROM refcode r
    JOIN c_tax t
          ON t.rate               = r.rate
         AND t.c_taxcategory_id   = r.c_taxcategory_id
         AND t.c_country_id       = r.c_country_id
         AND t.typeofdestcountry  = r.typeofdestcountry
         AND t.en16931vatcategory = r.en16931vatcategory
         AND t.isreversecharge    = r.isreversecharge
    JOIN c_acctschema s
          ON s.ad_client_id     = t.ad_client_id
         AND s.c_acctschema_id  = 1000000                                   -- base schema only, active OR inactive (org schemas handled in the customer repo)
    ORDER BY r.c_vat_code_id, t.isactive DESC, t.validto DESC NULLS FIRST, t.validfrom DESC, t.c_tax_id DESC
)
INSERT INTO c_vat_code (
    c_vat_code_id, ad_client_id, ad_org_id, c_acctschema_id, c_tax_id,
    vatcode, amounttype, issotrx, validfrom, validto, description, isactive,
    created, createdby, updated, updatedby
)
SELECT m.c_vat_code_id /*From ID Server*/,
       m.ad_client_id, 0, m.c_acctschema_id, m.c_tax_id,
       m.vatcode, m.amounttype, m.issotrx,
       TO_TIMESTAMP('1999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), NULL, NULL, 'Y',
       TO_TIMESTAMP('2026-09-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-22 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM matched m
WHERE NOT EXISTS (
        SELECT 1
        FROM c_vat_code x
        WHERE x.c_acctschema_id = m.c_acctschema_id
          AND x.c_tax_id        = m.c_tax_id
          AND x.amounttype      = m.amounttype
          AND x.issotrx         = m.issotrx
          AND x.vatcode         = m.vatcode
          AND x.isactive        = 'Y'
      );
