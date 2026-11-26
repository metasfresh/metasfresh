-- Umsatzsteuervoranmeldung: enter the standard German VAT-advance-return codes (Kennziffern) as
-- C_VAT_Code entries on the standard tax records in the base accounting schema 1000000.
--
-- This supersedes the removed 5825450_sys_C_VAT_Code_standard_UStVA_codes.sql. That script carried a
-- single fixed C_VAT_Code_ID per refcode row and emitted it once per matching tax; on instances where a
-- signature matched more than one tax (e.g. a current AND a legacy "bis 2020" 19% DE tax, identical on
-- every signature column) the fixed id was inserted twice and the primary-key collision aborted the
-- whole migration run. Two changes make this deterministic on every instance:
--   1. Each refcode signature resolves to exactly ONE canonical tax (DISTINCT ON, preferring the
--      active / most-current tax, deterministic tie-break by C_Tax_ID) -- so a fixed id is used at
--      most once per instance and there is no fan-out.
--   2. Only the ACTIVE base accounting schema 1000000 is targeted (s.isactive='Y'); instances that
--      deactivated schema 1000000 (org-schema customers) are a clean no-op here.
-- It also removes the rows the old script inserted (old fixed ids + schema 1000000 + CreatedBy=100) and
-- re-creates them with fresh central-ID-server ids, because the old ids 540031-540034 / 540040-540041
-- were not free c_vat_code ids (they are allocated C_Tax ids).
--
-- Matching is by the tax SEMANTIC SIGNATURE (Rate, C_TaxCategory_ID, C_Country_ID, TypeOfDestCountry,
-- EN16931VATCategory, IsReverseCharge). A signature with no matching tax on the instance inserts
-- nothing there. Two intra-community models are covered so the ig.-Lieferung codes 89/41/61 land
-- regardless of how an instance models it:
--   * reverse-charge model:     19% / Normale / WITHIN / AE / revcharge=Y -> 89/41/61
--   * exempt-with-VAT-ID model:  0% / Normale / WITHIN / K  / revcharge=N -> 89/41/61   (base / new customers)
--     (reduced-rate K -> 41 only; the reference set attests no reduced ig.-supply 89/61)
-- The domestic-19% Transport category -> 81/66 (as the reference set coded its Transport taxes).
-- AD_Org_ID=0 (client-level, as the source). ValidFrom=1999-12-31 / ValidTo=NULL ("always valid").
--
-- PK: each refcode row carries a fixed C_VAT_Code_ID from the central ID server (idserver.metas.de,
-- TABLE=c_vat_code), allocated 2026-09-22: 540154-540168. Central-server ids are < 1000000 and never
-- collide with instance-local (native-sequence) c_vat_code ids, which start at 1000000.

-- back up before touching business/config rows
SELECT backup_table('c_vat_code', '_UStVA_canonical_fix');

-- remove the rows the superseded 5825450 inserted (no-op on instances where it never applied)
DELETE FROM c_vat_code
WHERE c_acctschema_id = 1000000
  AND createdby       = 100
  AND c_vat_code_id IN (540144,540145,540146,540147,540148,540149,540150,540151,540152,
                        540031,540032,540033,540034,540040,540041);

WITH refcode(rate, c_taxcategory_id, c_country_id, typeofdestcountry, en16931vatcategory, isreversecharge, amounttype, issotrx, vatcode, c_vat_code_id) AS (
    VALUES
        -- 19% (DE) domestic, standard rate (EN16931 S)
        (19, 1000009, 101, 'DOMESTIC',             'S',  'N', 'N', 'Y', '81', 540154),
        (19, 1000009, 101, 'DOMESTIC',             'S',  'N', 'T', 'N', '66', 540155),
        -- 0% Steuerbefreit (Deutschland) domestic (EN16931 E)
        ( 0, 1000011, 101, 'DOMESTIC',             'E',  'N', 'N', 'Y', '48', 540156),
        -- innergemeinschaftliche Lieferung 19% (EU), reverse-charge (EN16931 AE)
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'N', 'N', '89', 540157),
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'N', 'Y', '41', 540158),
        (19, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'AE', 'Y', 'T', 'N', '61', 540159),
        -- Steuerbefreit 0% (EU) (EN16931 K)
        ( 0, 1000011, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540160),
        -- Normaler Steuersatz 0% (Drittland/Welt) (EN16931 E)
        ( 0, 1000009, 101, 'OUTSIDE_COUNTRY_AREA', 'E',  'N', 'N', 'Y', '43', 540161),
        -- Steuerbefreit 0% (Drittland/Welt) (EN16931 E)
        ( 0, 1000011, 101, 'OUTSIDE_COUNTRY_AREA', 'E',  'N', 'N', 'Y', '43', 540162),
        -- === base / new-customer model (exempt-with-VAT-ID ig. supply + Transport), same codes as the reference ===
        -- innergemeinschaftliche Lieferung, exempt-with-VAT-ID model, normal rate (EN16931 K)
        ( 0, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540163),
        ( 0, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'N', '89', 540164),
        ( 0, 1000009, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'T', 'N', '61', 540165),
        -- reduced-rate 0% exempt with VAT-ID (EN16931 K): only 41 is attested in the reference set
        ( 0, 1000010, 101, 'WITHIN_COUNTRY_AREA',  'K',  'N', 'N', 'Y', '41', 540166),
        -- domestic 19%, Transport category (like the reference Transport taxes)
        (19, 1000012, 101, 'DOMESTIC',             'S',  'N', 'N', 'Y', '81', 540167),
        (19, 1000012, 101, 'DOMESTIC',             'S',  'N', 'T', 'N', '66', 540168)
),
-- resolve each refcode signature to exactly ONE canonical tax on the ACTIVE base schema 1000000:
-- prefer the active tax, then the most-current (open-ended / latest ValidTo, then latest ValidFrom),
-- deterministic tie-break by the newest C_Tax_ID. DISTINCT ON runs over the full match set (before the
-- existence guard below), so the pick is stable across runs.
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
         AND s.c_acctschema_id  = 1000000                                   -- base schema only (org schemas handled in the customer repo)
         AND s.isactive         = 'Y'
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
       TO_TIMESTAMP('2026-09-22 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-09-22 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100
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
